package com.wsr.ui.memo.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.create.CreateMemoUseCase
import com.wsr.delete.DeleteMemoUseCase
import com.wsr.get.GetAllMemoUseCase
import com.wsr.get.GetAllMemoUseCaseModel
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import com.wsr.result.consume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoIndexViewModel @Inject constructor(
    private val getAllMemoUseCase: GetAllMemoUseCase,
    private val createMemoUseCase: CreateMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoIndexUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMemosAndUpdateUiState()
    }

    private fun getMemosAndUpdateUiState() {
        viewModelScope.launch {
            getAllMemoUseCase().consume(
                success = ::onSuccessGetting,
                failure = {},
            )
        }
    }

    private fun onSuccessGetting(memos: List<GetAllMemoUseCaseModel>) {
        _uiState.update { MemoIndexUiState.from(memos) }
    }

    fun createMemo(title: String) {
        viewModelScope.launch {
            dismissDialog()
            createMemoUseCase(MemoTitle(title))
            getMemosAndUpdateUiState()
        }
    }

    fun showDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(showCreateMemoDialog = true) }
        }
    }

    fun dismissDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(showCreateMemoDialog = false) }
        }
    }

    fun deleteMemo(memoId: String) {
        viewModelScope.launch {
            deleteMemoUseCase(MemoId(memoId))
            getMemosAndUpdateUiState()
        }
    }
}
