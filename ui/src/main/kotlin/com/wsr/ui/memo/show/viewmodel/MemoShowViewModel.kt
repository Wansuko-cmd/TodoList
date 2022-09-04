package com.wsr.ui.memo.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.FetchMemoByIdUseCase
import com.wsr.FetchMemoByIdUseCaseModel
import com.wsr.memo.MemoId
import com.wsr.result.consume
import com.wsr.ui.memo.show.MemoShowUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemoShowViewModel @AssistedInject constructor(
    private val fetchMemoByIdUseCase: FetchMemoByIdUseCase,
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
            fetchMemoByIdUseCase.flow.collect { data ->
                data.consume(
                    success = ::onSuccessFetching,
                    failure = {},
                )
            }
        }
    }

    private fun onSuccessFetching(data: FetchMemoByIdUseCaseModel) {
        viewModelScope.launch {
            _uiState.emit(MemoShowUiState.from(data))
        }
    }
}
