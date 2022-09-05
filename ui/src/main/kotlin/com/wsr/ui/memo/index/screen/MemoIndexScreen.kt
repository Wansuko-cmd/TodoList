package com.wsr.ui.memo.index.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.Route
import com.wsr.common.effect.observeToastEffect
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

@OptIn(ExperimentalFoundationApi::class)
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
        topBar = { MemoIndexTopBar() },
        floatingActionButton = {
            MemoIndexFloatActionButton(
                onClick = onClickFab,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize(),
        ) {
            // TODO:いい感じのComposableを入れることで一番上の要素をクリックしたときの挙動修正
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(uiState.memos, key = { it.id }) {
                MemoIndexMemoTile(
                    modifier = Modifier.animateItemPlacement(),
                    memoUiState = it,
                    onClickTile = { memoId ->
                        navController.navigate(Route.Memo.Show.with(memoId))
                    },
                    onClickDeleteButton = onClickDeleteButton,
                )
            }
        }
    }
}
