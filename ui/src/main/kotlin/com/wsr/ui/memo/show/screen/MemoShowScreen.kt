package com.wsr.ui.memo.show.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.common.effect.observeToastEffect
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.section.MemoShowItemSection
import com.wsr.ui.memo.show.viewmodel.MemoShowViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoShowViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMemoAndUpdateUiState()
    }

    when (uiState) {
        is MemoShowUiState.Loading -> Unit
        is MemoShowUiState.Success ->
            MemoShowScreen(
                modifier = modifier,
                uiState = uiState as MemoShowUiState.Success,
                navController = navController,
                onChecked = viewModel::changeItemChecked,
                onChangeContent = viewModel::changeItemContent,
                addItem = viewModel::addItem,
                deleteCheckedItems = viewModel::deleteCheckedItem,
                onMoveItem = viewModel::swapItem,
            )
    }

    observeToastEffect(viewModel.toastEffect)
}

@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState.Success,
    navController: NavHostController,
    onChecked: (itemId: String) -> Unit,
    onChangeContent: (itemId: String, content: String) -> Unit,
    addItem: () -> Unit,
    deleteCheckedItems: () -> Unit,
    onMoveItem: (from: String, to: String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MemoShowTopBar(
                navController = navController,
                memoTitle = uiState.title,
                deleteCheckedItems = deleteCheckedItems,
            )
        },
        floatingActionButton = {
            MemoShowFloatActionButton(onClick = addItem)
        }
    ) { innerPadding ->
        MemoShowItemSection(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onChecked = onChecked,
            onChangeContent = onChangeContent,
            onMoveItem = onMoveItem,
        )
    }
}
