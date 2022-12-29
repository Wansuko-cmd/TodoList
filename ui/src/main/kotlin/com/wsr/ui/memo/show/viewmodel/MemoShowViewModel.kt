package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.MemoUseCaseModel
import com.wsr.Route
import com.wsr.command.DivideMemoUseCase
import com.wsr.command.element.AddItemUseCase
import com.wsr.command.element.DeleteCheckedItemsUseCase
import com.wsr.command.element.SwapItemUseCase
import com.wsr.command.element.UpdateItemCheckedUseCase
import com.wsr.command.element.UpdateItemContentUseCase
import com.wsr.command.element.UpdateMemoTitleUseCase
import com.wsr.common.effect.NavigateEffect
import com.wsr.common.effect.ToastEffect
import com.wsr.get.GetMemoByIdUseCase
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import com.wsr.result.consume
import com.wsr.result.map
import com.wsr.ui.R
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
import kotlinx.coroutines.runBlocking

class MemoShowViewModel @AssistedInject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val updateItemCheckedUseCase: UpdateItemCheckedUseCase,
    private val updateItemContentUseCase: UpdateItemContentUseCase,
    private val updateMemoTitleUseCase: UpdateMemoTitleUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val deleteCheckedItemsUseCase: DeleteCheckedItemsUseCase,
    private val swapItemUseCase: SwapItemUseCase,
    private val divideMemoUseCase: DivideMemoUseCase,
    @Assisted("memoId") private val memoId: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoShowUiState(isLoading = true))
    val uiState = _uiState
        .map { uiState -> uiState.copy(items = uiState.items.sortedBy { it.checked }) }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            MemoShowUiState(isLoading = true),
        )

    private val _toastEffect = MutableSharedFlow<ToastEffect>()
    val toastEffect = _toastEffect.asSharedFlow()

    private val _navigateEffect = MutableSharedFlow<NavigateEffect>()
    val navigateEffect = _navigateEffect.asSharedFlow()

    private val _sharedTextEffect = MutableSharedFlow<ShareItemsEffect>()
    val sharedTextEffect = _sharedTextEffect.asSharedFlow()

    fun getMemoAndUpdateUiState() {
        viewModelScope.launch {
            getMemoByIdUseCase(MemoId(memoId)).consume(
                success = { _uiState.emit(MemoShowUiState.from(it)) },
                failure = { _toastEffect.emit(ToastEffect(R.string.system_error_message)) },
            )
        }
    }

    fun changeItemChecked(itemId: ItemId) {
        updateMemo { updateItemCheckedUseCase(it, itemId) }
    }

    fun changeItemContent(itemId: ItemId, content: ItemContent) {
        updateMemoWithRunBlocking { updateItemContentUseCase(it, itemId, content) }

        // 最後のItemでEnterが押された場合、新しいItemを追加する
        if (
            content.value.endsWith("\n") &&
            uiState.value.items.lastOrNull { !it.checked }?.id == itemId
        ) addItem()
    }

    fun addItem() {
        // Focusを与える処理が必要なためupdateMemo関数を使わない
        viewModelScope.launch {
            _uiState.update { uiState ->
                val itemIds = uiState.items.map { it.id }
                addItemUseCase(uiState.toUseCaseModel(MemoId(memoId)))
                    .let { MemoShowUiState.from(it) }
                    .let { beforeAttachFocusUiState ->
                        beforeAttachFocusUiState.copy(
                            shouldFocusItemId = beforeAttachFocusUiState
                                .items
                                .firstOrNull { !itemIds.contains(it.id) }
                                ?.id,
                        )
                    }
            }
        }
    }

    fun deleteCheckedItems() {
        updateMemo { deleteCheckedItemsUseCase(it) }
    }

    fun swapItem(from: ItemId, to: ItemId) {
        updateMemo { swapItemUseCase(it, from, to) }
    }

    fun shareItems() {
        viewModelScope.launch {
            when {
                uiState.value.isLoading ->
                    _toastEffect.emit(ToastEffect(R.string.memo_show_share_items_on_loading_error_message))
                uiState.value.items.isEmpty() ->
                    _toastEffect.emit(ToastEffect(R.string.memo_show_share_items_no_items_error_message))
                else ->
                    _sharedTextEffect.emit(ShareItemsEffect(uiState.value.items))
            }
        }
    }

    fun divideItems() {
        viewModelScope.launch {
            divideMemoUseCase(MemoId(memoId), MemoTitle("Test"))
                .map {
                    _navigateEffect.emit(
                        NavigateEffect.Navigate(Route.Memo.Show.with(it.id.value)),
                    )
                }
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
        updateMemo { updateMemoTitleUseCase(it, MemoTitle(title)) }
    }

    private inline fun updateMemo(
        crossinline block: suspend (MemoUseCaseModel) -> MemoUseCaseModel,
    ) {
        viewModelScope.launch {
            _uiState.update { uiState ->
                block(uiState.toUseCaseModel(MemoId(memoId)))
                    .let { MemoShowUiState.from(it) }
            }
        }
    }

    /**
     * 即座に画面に反映させないと挙動がおかしくなる時はこちらの関数を利用
     * 例：文字の変更を反映させる動作
     */
    private inline fun updateMemoWithRunBlocking(
        crossinline block: suspend (MemoUseCaseModel) -> MemoUseCaseModel,
    ) {
        runBlocking {
            _uiState.update { uiState ->
                block(uiState.toUseCaseModel(MemoId(memoId)))
                    .let { MemoShowUiState.from(it) }
            }
        }
    }

    fun onClickArrowBack() {
        viewModelScope.launch {
            _navigateEffect.emit(NavigateEffect.PopBackStack)
        }
    }
}
