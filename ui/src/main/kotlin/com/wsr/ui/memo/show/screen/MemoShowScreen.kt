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
import com.wsr.common.composable.LoadingScreen
import com.wsr.common.effect.ObserveToastEffect
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.component.MemoShowEditMemoTitleDialog
import com.wsr.ui.memo.show.effect.ObserveShareItemsEffect
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

    MemoShowScreen(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        onChecked = viewModel::changeItemChecked,
        onChangeContent = viewModel::changeItemContent,
        addItem = viewModel::addItem,
        onClickTitle = viewModel::showDialog,
        shareItems = viewModel::shareItems,
        deleteCheckedItems = viewModel::deleteCheckedItem,
        onMoveItem = viewModel::swapItem,
    )

    if (uiState.isShowingEditMemoTitleDialog) {
        MemoShowEditMemoTitleDialog(
            initialValue = uiState.title.value,
            onDismiss = viewModel::dismissDialog,
            onConfirm = viewModel::updateMemoTitle,
        )
    }

    ObserveToastEffect(viewModel.toastEffect)
    ObserveShareItemsEffect(viewModel.sharedTextEffect)
}

@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    navController: NavHostController,
    onChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
    addItem: () -> Unit,
    onClickTitle: () -> Unit,
    shareItems: () -> Unit,
    deleteCheckedItems: () -> Unit,
    onMoveItem: (from: ItemId, to: ItemId) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MemoShowTopBar(
                navController = navController,
                memoTitle = uiState.title.value,
                onClickTitle = onClickTitle,
                shareItems = shareItems,
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
        if (uiState.isLoading) LoadingScreen()
    }
}
