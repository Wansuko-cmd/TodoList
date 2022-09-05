package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.common.effect.ToastEffect
import com.wsr.create.CreateItemUseCase
import com.wsr.get.GetMemoByIdUseCase
import com.wsr.get.GetMemoByIdUseCaseModel
import com.wsr.memo.MemoId
import com.wsr.result.consume
import com.wsr.ui.R
import com.wsr.ui.memo.show.MemoShowItemUiState
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.MemoShowUiState.Companion.toUpdateMemoUseCaseModel
import com.wsr.update.UpdateMemoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoShowViewModel @AssistedInject constructor(
    private val getMemoByIdUseCase: GetMemoByIdUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val createItemInstanceUsecase: CreateItemUseCase,
    @Assisted("memoId") private val memoId: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoShowUiState())
    val uiState = _uiState.asStateFlow()
        .map { uiState ->
            uiState.copy(items = uiState.items.sortedBy { it.checked })
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            MemoShowUiState(),
        )

    private val _toastEffect = MutableSharedFlow<ToastEffect>()
    val toastEffect = _toastEffect.asSharedFlow()

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

    fun changeItemChecked(itemId: String) {
        updateItem(itemId) { it.copy(checked = !it.checked) }
    }

    fun changeItemContent(itemId: String, content: String) {
        updateItem(itemId) { it.copy(content = content.replace("\n", "")) }

        // 最後のItemでEnterが押された場合、新しいItemを追加する
        if (
            content.endsWith("\n") &&
            _uiState.value.items.lastOrNull()?.id == itemId
        ) addItem()
    }

    fun addItem() {
        val newItem = MemoShowItemUiState
            .from(createItemInstanceUsecase())
            .copy(shouldFocus = true)
        updateItems { it + newItem }
    }

    fun deleteCheckedItem() {
        updateItems { items -> items.filter { !it.checked } }
    }

    fun swapItem(from: String, to: String) {
        updateItems { items ->
            val fromIndex = items.indexOfFirst { it.id == from }
            val toIndex = items.indexOfFirst { it.id == to }
            items.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveToDatabase()
    }

    private inline fun updateItem(itemId: String, block: (MemoShowItemUiState) -> MemoShowItemUiState) {
        _uiState.update { uiState ->
            uiState.copy(
                items = uiState.items
                    .map { it.copy(shouldFocus = false) }
                    .map { item -> if (item.id == itemId) block(item) else item }
            )
        }
        saveToDatabase()
    }

    private inline fun updateItems(block: (List<MemoShowItemUiState>) -> List<MemoShowItemUiState>) {
        _uiState.update { uiState ->
            uiState.copy(
                items = block(uiState.items.map { it.copy(shouldFocus = false) })
            )
        }
        saveToDatabase()
    }

    private fun saveToDatabase() {
        viewModelScope.launch {
            updateMemoUseCase(_uiState.value.toUpdateMemoUseCaseModel(memoId))
        }
    }
}
