package com.wsr.ui.memo.index.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.common.composable.LoadingScreen
import com.wsr.common.composable.dialog.SettingsMemoTitleDialog
import com.wsr.common.effect.ObserveNavigateEffect
import com.wsr.common.effect.ObserveToastEffect
import com.wsr.memo.MemoId
import com.wsr.ui.memo.index.MemoIndexUiState
import com.wsr.ui.memo.index.MemoIndexViewModel
import com.wsr.ui.memo.index.section.MemoIndexMemoSection

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoIndexViewModel,
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
        onClickDeleteMemo = viewModel::onClickDeleteMemo,
    )

    if (uiState.isShowingCreateMemoDialog) {
        SettingsMemoTitleDialog(
            onDismiss = viewModel::dismissCreateMemoDialog,
            onConfirm = viewModel::createMemo,
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
            onClickDeleteButton = onClickDeleteMemo,
        )
        if (uiState.isLoading) LoadingScreen()
    }
}
