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
        onClickTile = viewModel::onClickTile,
        onClickSetting = viewModel::onClickSetting,
        onClickFab = viewModel::showDialog,
        onClickDeleteButton = viewModel::deleteMemo,
    )

    if (uiState.isShowingCreateMemoDialog) {
        SettingsMemoTitleDialog(
            onDismiss = viewModel::dismissDialog,
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
    onClickTile: (MemoId) -> Unit,
    onClickSetting: () -> Unit,
    onClickFab: () -> Unit,
    onClickDeleteButton: (memoId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MemoIndexTopBar(onClickSetting = onClickSetting) },
        floatingActionButton = {
            MemoIndexFloatActionButton(
                onClick = onClickFab,
            )
        },
    ) { innerPadding ->
        MemoIndexMemoSection(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onClickTile = onClickTile,
            onClickDeleteButton = onClickDeleteButton,
        )
        if (uiState.isLoading) LoadingScreen()
    }
}
