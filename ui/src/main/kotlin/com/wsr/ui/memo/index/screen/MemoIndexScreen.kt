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
import com.wsr.Route
import com.wsr.common.effect.observeToastEffect
import com.wsr.ui.memo.index.MemoIndexUiState
import com.wsr.ui.memo.index.MemoIndexViewModel
import com.wsr.ui.memo.index.component.MemoIndexCreateMemoDialog
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
        navController = navController,
        onClickFab = viewModel::showDialog,
        onClickDeleteButton = viewModel::deleteMemo,
    )

    if (uiState.showCreateMemoDialog) {
        MemoIndexCreateMemoDialog(
            onDismiss = viewModel::dismissDialog,
            onConfirm = viewModel::createMemo,
        )
    }

    observeToastEffect(viewModel.toastEffect)
}

@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    uiState: MemoIndexUiState,
    navController: NavHostController,
    onClickFab: () -> Unit,
    onClickDeleteButton: (memoId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MemoIndexTopBar(navController = navController) },
        floatingActionButton = {
            MemoIndexFloatActionButton(
                onClick = onClickFab,
            )
        }
    ) { innerPadding ->
        MemoIndexMemoSection(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onClickTile = { memoId -> navController.navigate(Route.Memo.Show.with(memoId)) },
            onClickDeleteButton = onClickDeleteButton,
        )
    }
}
