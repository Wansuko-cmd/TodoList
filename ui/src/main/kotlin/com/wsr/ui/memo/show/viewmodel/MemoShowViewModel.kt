package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.CreateItemUseCase
import com.wsr.FetchMemoByIdUseCaseModel
import com.wsr.GetMemoByIdUseCase
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoShowViewModel @AssistedInject constructor(
    private val fetchMemoByIdUseCase: GetMemoByIdUseCase,
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

    init {
        getMemoAndUpdateUiState()
    }

    private fun getMemoAndUpdateUiState() {
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

    fun changeItemChecked(itemId: String) {
        updateItem(itemId) { it.copy(checked = !it.checked) }
    }

    fun changeItemContent(itemId: String, content: String) {
        updateItem(itemId) { it.copy(content = content) }
    }

    fun addItem() {
        val newItem = MemoShowItemUiState.from(createItemInstanceUsecase())
        updateItems { it + newItem }
    }

    fun deleteCheckedItem() {
        updateItems { items -> items.filter { !it.checked } }
    }

    override fun onCleared() {
        super.onCleared()
        saveToDatabase()
    }

    private fun updateItem(itemId: String, block: (MemoShowItemUiState) -> MemoShowItemUiState) {
        _uiState.update { uiState ->
            uiState.copy(
                items = uiState.items.map { item ->
                    if (item.id == itemId) block(item) else item
                }
            )
        }
        saveToDatabase()
    }

    private fun updateItems(block: (List<MemoShowItemUiState>) -> List<MemoShowItemUiState>) {
        _uiState.update { uiState ->
            uiState.copy(
                items = block(uiState.items)
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
