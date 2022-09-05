package com.wsr.ui.memo.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.common.effect.ToastEffect
import com.wsr.create.CreateMemoUseCase
import com.wsr.delete.DeleteMemoUseCase
import com.wsr.exception.DomainException
import com.wsr.get.GetAllMemoUseCase
import com.wsr.get.GetAllMemoUseCaseModel
import com.wsr.memo.MemoId
import com.wsr.memo.MemoTitle
import com.wsr.result.consume
import com.wsr.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _toastEffect = MutableSharedFlow<ToastEffect>()
    val toastEffect = _toastEffect.asSharedFlow()

    fun getMemosAndUpdateUiState() {
        viewModelScope.launch {
            getAllMemoUseCase().consume(
                success = ::onSuccessGetting,
                failure = { onFailureGetting() },
            )
        }
    }

    private fun onSuccessGetting(memos: List<GetAllMemoUseCaseModel>) {
        _uiState.update { MemoIndexUiState.from(memos) }
    }

    private fun onFailureGetting() {
        viewModelScope.launch {
            _toastEffect.emit(ToastEffect(R.string.system_error_message))
        }
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
