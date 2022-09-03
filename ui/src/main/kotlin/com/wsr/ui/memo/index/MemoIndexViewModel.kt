package com.wsr.ui.memo.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.FetchAllMemoUseCase
import com.wsr.FetchAllMemoUseCaseModel
import com.wsr.result.consume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoIndexViewModel @Inject constructor(
    private val getAllMemoUseCase: FetchAllMemoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoIndexUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeLatestMemos()
        setCollectAndUpdateUiState()
    }

    private fun observeLatestMemos() {
        viewModelScope.launch {
            getAllMemoUseCase()
        }
    }

    private fun setCollectAndUpdateUiState() {
        viewModelScope.launch {
            getAllMemoUseCase.flow.collect { data ->
                data.consume(
                    success = ::onSuccessGetting,
                    failure = {},
                )
            }
        }
    }

    private fun onSuccessGetting(memos: List<FetchAllMemoUseCaseModel>) {
        _uiState.update { MemoIndexUiState.from(memos) }
    }
}
