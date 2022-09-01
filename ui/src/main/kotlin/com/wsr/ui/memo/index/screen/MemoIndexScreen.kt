package com.wsr.ui.memo.index.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.ui.memo.index.MemoIndexUiState
import com.wsr.ui.memo.index.MemoIndexViewModel
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
    )
}
@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    uiState: MemoIndexUiState,
    navController: NavHostController,
) {
    Scaffold(modifier) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            items(uiState.memos) {
                MemoIndexMemoTile(uiState = it)
            }
        }
    }
}
