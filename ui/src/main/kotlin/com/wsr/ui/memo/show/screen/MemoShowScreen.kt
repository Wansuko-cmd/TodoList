package com.wsr.ui.memo.show.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.viewmodel.MemoShowViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoShowViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MemoShowScreen(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
    )
}

@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    navController: NavHostController,
) {
    Scaffold(
        modifier = modifier,
    ) {
        Text(text = uiState.title)
    }
}
