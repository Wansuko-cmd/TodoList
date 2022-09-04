package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.GetMemoByIdUseCase
import com.wsr.FetchMemoByIdUseCaseModel
import com.wsr.UpdateMemoUseCase
import com.wsr.memo.MemoId
import com.wsr.result.consume
import com.wsr.ui.memo.show.MemoShowItemUiState
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.MemoShowUiState.Companion.toUpdateMemoUseCaseModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemoShowViewModel @AssistedInject constructor(
    private val fetchMemoByIdUseCase: GetMemoByIdUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
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

    init {
        observeLatestMemo()
        setCollectAndUpdateUiState()
    }

    private fun observeLatestMemo() {
        viewModelScope.launch {
            fetchMemoByIdUseCase(MemoId(memoId))
        }
    }

    private fun setCollectAndUpdateUiState() {
        viewModelScope.launch {
            fetchMemoByIdUseCase(MemoId(memoId)).consume(
                success = ::onSuccessFetching,
                failure = {},
            )
        }
    }

    private fun onSuccessFetching(data: FetchMemoByIdUseCaseModel) {
        viewModelScope.launch {
            _uiState.emit(MemoShowUiState.from(data))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            updateMemoUseCase(_uiState.value.toUpdateMemoUseCaseModel(memoId))
        }
    }

    private fun updateItem(itemId: String, block: (MemoShowItemUiState) -> MemoShowItemUiState) {
        val memo = _uiState.value
        val updatedItems = memo.items.map { item ->
            if (item.id == itemId) block(item) else item
        }
        _uiState.value = memo.copy(items = updatedItems)
        viewModelScope.launch {
            updateMemoUseCase(memo.copy(items = updatedItems).toUpdateMemoUseCaseModel(memoId))
        }
    }

    fun changeItemChecked(itemId: String) {
        updateItem(itemId) { it.copy(checked = !it.checked) }
    }

    fun changeItemContent(itemId: String, content: String) {
        updateItem(itemId) { it.copy(content = content) }
    }
}
