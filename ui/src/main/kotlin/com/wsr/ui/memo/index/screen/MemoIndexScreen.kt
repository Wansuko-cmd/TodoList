package com.wsr.ui.memo.index.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.Route
import com.wsr.ui.memo.index.MemoIndexUiState
import com.wsr.ui.memo.index.MemoIndexViewModel
import com.wsr.ui.memo.index.component.MemoIndexCreateMemoDialog
import com.wsr.ui.memo.index.component.MemoIndexMemoTile

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoIndexViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MemoIndexScreen(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        onClickFab = viewModel::showDialog,
    )

    if (uiState.showCreateMemoDialog) {
        MemoIndexCreateMemoDialog(
            onDismiss = viewModel::dismissDialog,
            onConfirm = viewModel::createMemo,
        )
    }
}
@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    uiState: MemoIndexUiState,
    navController: NavHostController,
    onClickFab: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MemoIndexTopBar() },
        floatingActionButton = {
            MemoIndexFloatActionButton(
                onClick = onClickFab,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            items(uiState.memos) {
                MemoIndexMemoTile(
                    memoUiState = it,
                    onClickTile = { memoId ->
                        navController.navigate(Route.Memo.Show.with(memoId))
                    },
                )
            }
        }
    }
}
