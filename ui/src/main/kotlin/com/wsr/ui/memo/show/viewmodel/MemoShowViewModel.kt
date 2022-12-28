package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.command.AddItemUseCase
import com.wsr.command.ChangeItemCheckedUseCase
import com.wsr.command.DeleteCheckedItemsUseCase
import com.wsr.command.DivideMemoUseCase
import com.wsr.command.SwapItemUseCase
import com.wsr.command.UpdateItemContentUseCase
import com.wsr.common.effect.ToastEffect
import com.wsr.get.GetMemoByIdUseCase
import com.wsr.get.GetMemoByIdUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import com.wsr.result.consume
import com.wsr.ui.R
import com.wsr.ui.memo.show.MemoShowItemUiState
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.effect.ShareItemsEffect
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoShowViewModel @AssistedInject constructor(
    private val getMemoFlowByIdUseCase: GetMemoByIdUseCase,
    private val changeItemCheckedUseCase: ChangeItemCheckedUseCase,
    private val updateItemContentUseCase: UpdateItemContentUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val deleteCheckedItemsUseCase: DeleteCheckedItemsUseCase,
    private val swapItemUseCase: SwapItemUseCase,
    private val divideMemoUseCase: DivideMemoUseCase,
    @Assisted("memoId") private val memoId: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoShowUiState(isLoading = true))
    val uiState = _uiState
        .map { uiState ->
            uiState.mapItems { items -> items.sortedBy { it.checked } }
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            MemoShowUiState(isLoading = true),
        )

    private val _toastEffect = MutableSharedFlow<ToastEffect>()
    val toastEffect = _toastEffect.asSharedFlow()

    private val _sharedTextEffect = MutableSharedFlow<ShareItemsEffect>()
    val sharedTextEffect = _sharedTextEffect.asSharedFlow()

    fun getMemoAndUpdateUiState() {
        viewModelScope.launch {
            getMemoFlowByIdUseCase(MemoId(memoId)).collect { data ->
                data.consume(
                    success = ::onSuccessGetting,
                    failure = { onFailureGetting() },
                )
            }
        }
    }

    private fun onSuccessGetting(data: GetMemoByIdUseCaseModel) {
        viewModelScope.launch {
            _uiState.emit(MemoShowUiState.from(data))
        }
    }

    private fun onFailureGetting() {
        viewModelScope.launch {
            _toastEffect.emit(ToastEffect(R.string.system_error_message))
        }
    }

    fun changeItemChecked(itemId: ItemId) {
        viewModelScope.launch {
            changeItemCheckedUseCase(MemoId(memoId), itemId)
        }
    }

    fun changeItemContent(itemId: ItemId, content: ItemContent) {
        viewModelScope.launch {
            updateItemContentUseCase(MemoId(memoId), itemId, content)
        }

        // 最後のItemでEnterが押された場合、新しいItemを追加する
        if (
            content.value.endsWith("\n") &&
            _uiState.value.items.lastOrNull()?.id == itemId
        ) addItem()
    }

    fun addItem() {
        // TODO: Focusをつける
        viewModelScope.launch {
            addItemUseCase(MemoId(memoId))
        }
    }

    fun deleteCheckedItems() {
        viewModelScope.launch {
            deleteCheckedItemsUseCase(MemoId(memoId))
        }
    }

    fun swapItem(from: ItemId, to: ItemId) {
        viewModelScope.launch {
            swapItemUseCase(MemoId(memoId), from, to)
        }
    }

    fun shareItems() {
        viewModelScope.launch {
            when {
                _uiState.value.isLoading ->
                    _toastEffect.emit(ToastEffect(R.string.memo_show_share_items_on_loading_error_message))
                _uiState.value.items.isEmpty() ->
                    _toastEffect.emit(ToastEffect(R.string.memo_show_share_items_no_items_error_message))
                else ->
                    _sharedTextEffect.emit(ShareItemsEffect(_uiState.value.items))
            }
        }
    }

    fun divideItems() {
        viewModelScope.launch {
            divideMemoUseCase(MemoId(memoId), MemoTitle("Test"))
            getMemoAndUpdateUiState()
        }
    }

    fun showDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(isShowingEditMemoTitleDialog = true) }
        }
    }

    fun dismissDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(isShowingEditMemoTitleDialog = false) }
        }
    }

    fun updateMemoTitle(title: String) {
        dismissDialog()
        _uiState.update { uiState -> uiState.copy(title = MemoTitle(title)) }
    }
}
