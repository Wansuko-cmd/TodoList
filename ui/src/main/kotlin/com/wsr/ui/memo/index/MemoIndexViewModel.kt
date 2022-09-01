package com.wsr.ui.memo.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.GetAllMemoUseCase
import com.wsr.GetAllMemoUseCaseModel
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoIndexUiState())
    val uiState = _uiState.asStateFlow()

    init {
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
}
