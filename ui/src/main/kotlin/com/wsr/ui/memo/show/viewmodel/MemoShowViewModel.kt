package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.common.effect.ToastEffect
import com.wsr.create.CreateItemUseCase
import com.wsr.get.GetMemoByIdUseCase
import com.wsr.get.GetMemoByIdUseCaseModel
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.result.consume
import com.wsr.ui.R
import com.wsr.ui.memo.show.MemoShowItemUiState
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.effect.ShareItemsEffect
import com.wsr.update.UpdateMemoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MemoShowViewModel @AssistedInject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val createItemInstanceUsecase: CreateItemUseCase,
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
            getMemoByIdUseCase(MemoId(memoId)).consume(
                success = ::onSuccessGetting,
                failure = { onFailureGetting() },
            )
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
        updateItem(itemId) { it.copy(checked = !it.checked) }
    }

    fun changeItemContent(itemId: ItemId, content: ItemContent) {
        updateItem(itemId) {
            it.copy(
                content = ItemContent(
                    content.value.replace("\n", ""),
                )
            )
        }

        // 最後のItemでEnterが押された場合、新しいItemを追加する
        if (
            content.value.endsWith("\n") &&
            _uiState.value.items.lastOrNull()?.id == itemId
        ) addItem()
    }

    fun addItem() {
        val newItem = MemoShowItemUiState
            .from(createItemInstanceUsecase())
        updateItems { it + newItem }
        _uiState.update { it.copy(shouldFocusItemId = newItem.id) }
    }

    fun deleteCheckedItem() {
        updateItems { items -> items.filter { !it.checked } }
    }

    fun swapItem(from: ItemId, to: ItemId) {
        updateItems { items ->
            val fromIndex = items.indexOfFirst { it.id == from }
            val toIndex = items.indexOfFirst { it.id == to }
            if (fromIndex != -1 && toIndex != -1) {
                items.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
            } else items
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

    override fun onCleared() {
        super.onCleared()
        saveToDatabase()
    }

    private inline fun updateItem(
        itemId: ItemId,
        crossinline block: (MemoShowItemUiState) -> MemoShowItemUiState,
    ) {
        _uiState.update { uiState ->
            uiState.mapItems { items ->
                items
                    .map { item -> if (item.id == itemId) block(item) else item }
            }
        }
        saveToDatabase()
    }

    private inline fun updateItems(
        crossinline block: (List<MemoShowItemUiState>) -> List<MemoShowItemUiState>,
    ) {
        _uiState.update { uiState ->
            uiState.mapItems { items -> block(items) }
        }
        saveToDatabase()
    }

    private fun saveToDatabase() {
        viewModelScope.launch {
            if (!_uiState.value.isLoading) {
                updateMemoUseCase(_uiState.value.toUpdateMemoUseCaseModel(memoId))
            }
        }
    }
}
