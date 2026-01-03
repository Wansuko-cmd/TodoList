package com.wsr.ui.memo.index.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.common.composable.LoadingScreen
import com.wsr.common.composable.dialog.CheckConfirmDangerDialog
import com.wsr.common.composable.dialog.SettingsMemoTitleDialog
import com.wsr.common.effect.ObserveNavigateEffect
import com.wsr.common.effect.ObserveToastEffect
import com.wsr.memo.MemoId
import com.wsr.ui.R
import com.wsr.ui.memo.index.IsShowingCheckIfDeleteMemoDialog
import com.wsr.ui.memo.index.IsShowingEditMemoTitleDialog
import com.wsr.ui.memo.index.MemoIndexUiState
import com.wsr.ui.memo.index.MemoIndexViewModel
import com.wsr.ui.memo.index.section.MemoIndexMemoSection

@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoIndexViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMemosAndUpdateUiState()
    }

    MemoIndexScreen(
        modifier = modifier,
        uiState = uiState,
        onClickSetting = viewModel::onClickSetting,
        onClickCreateMemo = viewModel::showCreateMemoDialog,
        onClickMemo = viewModel::onClickMemo,
        onClickUpdateMemoTitle = viewModel::showEditMemoTitleDialog,
        onClickDeleteMemo = viewModel::showCheckIfDeleteMemoDialog,
    )

    if (uiState.isShowingCreateMemoDialog) {
        SettingsMemoTitleDialog(
            onDismiss = viewModel::dismissCreateMemoDialog,
            onConfirm = viewModel::createMemo,
        )
    }

    if (uiState.isShowingEditMemoTitleDialog is IsShowingEditMemoTitleDialog.True) {
        val (memoId, title) =
            (uiState.isShowingEditMemoTitleDialog as IsShowingEditMemoTitleDialog.True)
        SettingsMemoTitleDialog(
            initialValue = title,
            onDismiss = viewModel::dismissEditMemoTitleDialog,
            onConfirm = { viewModel.updateMemoTitle(memoId, it) },
        )
    }
    if (uiState.isShowingCheckIfDeleteMemoDialog is IsShowingCheckIfDeleteMemoDialog.True) {
        val memoId =
            (uiState.isShowingCheckIfDeleteMemoDialog as IsShowingCheckIfDeleteMemoDialog.True).memoId
        CheckConfirmDangerDialog(
            message = stringResource(R.string.memo_index_dialog_check_if_delete_memo_message),
            onDismiss = viewModel::dismissCheckIfDeleteMemoDialog,
            onConfirm = { viewModel.deleteMemo(memoId) },
        )
    }

    ObserveToastEffect(viewModel.toastEffect)
    ObserveNavigateEffect(navController, viewModel.navigateEffect)
}

@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    uiState: MemoIndexUiState,
    onClickSetting: () -> Unit,
    onClickCreateMemo: () -> Unit,
    onClickMemo: (MemoId) -> Unit,
    onClickUpdateMemoTitle: (memoId: String) -> Unit,
    onClickDeleteMemo: (memoId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MemoIndexTopBar(onClickSettings = onClickSetting) },
        floatingActionButton = {
            MemoIndexFloatActionButton(
                onClick = onClickCreateMemo,
            )
        },
    ) { innerPadding ->
        MemoIndexMemoSection(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onClickTile = onClickMemo,
            onClickUpdateMemoTitleButton = onClickUpdateMemoTitle,
            onClickDeleteButton = onClickDeleteMemo,
        )
        if (uiState.isLoading) LoadingScreen()
    }
}
