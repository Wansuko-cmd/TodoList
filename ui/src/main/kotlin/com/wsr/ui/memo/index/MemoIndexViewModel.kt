package com.wsr.ui.memo.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.create.CreateMemoUseCase
import com.wsr.get.GetAllMemoUseCaseModel
import com.wsr.get.GetAllMemoUseCase
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
    private val fetchAllMemoUseCase: GetAllMemoUseCase,
    private val createMemoUseCase: CreateMemoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoIndexUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMemosAndUpdateUiState()
    }

    private fun getMemosAndUpdateUiState() {
        viewModelScope.launch {
            fetchAllMemoUseCase().consume(
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
}
