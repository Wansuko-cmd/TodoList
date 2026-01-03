package com.wsr.ui.memo.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.Route
import com.wsr.command.CreateMemoUseCase
import com.wsr.command.DeleteMemoUseCase
import com.wsr.command.element.UpdateMemoTitleUseCase
import com.wsr.common.effect.NavigateEffect
import com.wsr.common.effect.ToastEffect
import com.wsr.get.GetAllMemoUseCase
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
    private val getAllMemoFlowUseCase: GetAllMemoUseCase,
    private val createMemoUseCase: CreateMemoUseCase,
    private val updateMemoTitleUseCase: UpdateMemoTitleUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemoIndexUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _toastEffect = MutableSharedFlow<ToastEffect>()
    val toastEffect = _toastEffect.asSharedFlow()

    private val _navigateEffect = MutableSharedFlow<NavigateEffect>()
    val navigateEffect = _navigateEffect.asSharedFlow()

    fun getMemosAndUpdateUiState() {
        viewModelScope.launch {
            getAllMemoFlowUseCase().consume(
                success = { data -> _uiState.update { MemoIndexUiState.from(data) } },
                failure = { _toastEffect.emit(ToastEffect(R.string.system_error_message)) },
            )
        }
    }

    fun onClickSetting() {
        viewModelScope.launch {
//            _navigateEffect.emit(NavigateEffect.Navigate(Route.Settings.Index.path))
        }
    }

    fun createMemo(title: String) {
        viewModelScope.launch {
            dismissCreateMemoDialog()
            createMemoUseCase(MemoTitle(title))
            getMemosAndUpdateUiState()
        }
    }

    fun onClickMemo(memoId: MemoId) {
        viewModelScope.launch {
            _navigateEffect.emit(NavigateEffect.Navigate(Route.Memo.Show.with(memoId.value)))
        }
    }

    fun updateMemoTitle(memoId: String, title: String) {
        viewModelScope.launch {
            dismissEditMemoTitleDialog()
            updateMemoTitleUseCase(MemoId(memoId), MemoTitle(title))
            getMemosAndUpdateUiState()
        }
    }

    fun deleteMemo(memoId: String) {
        viewModelScope.launch {
            deleteMemoUseCase(MemoId(memoId))
            getMemosAndUpdateUiState()
        }
    }

    fun showCreateMemoDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(isShowingCreateMemoDialog = true) }
        }
    }

    fun dismissCreateMemoDialog() {
        viewModelScope.launch {
            _uiState.update { it.copy(isShowingCreateMemoDialog = false) }
        }
    }

    fun showCheckIfDeleteMemoDialog(memoId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isShowingCheckIfDeleteMemoDialog = IsShowingCheckIfDeleteMemoDialog.True(memoId),
                )
            }
        }
    }

    fun dismissCheckIfDeleteMemoDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isShowingCheckIfDeleteMemoDialog = IsShowingCheckIfDeleteMemoDialog.False)
            }
        }
    }

    fun showEditMemoTitleDialog(memoId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isShowingEditMemoTitleDialog = IsShowingEditMemoTitleDialog.True(
                        memoId = memoId,
                        title = it.memos
                            .first { memo -> memo.id == memoId }
                            .title,
                    ),
                )
            }
        }
    }

    fun dismissEditMemoTitleDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isShowingEditMemoTitleDialog = IsShowingEditMemoTitleDialog.False)
            }
        }
    }
}
