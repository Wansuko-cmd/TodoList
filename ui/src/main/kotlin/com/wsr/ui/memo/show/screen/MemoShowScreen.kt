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
import com.wsr.common.composable.dialog.SettingsMemoTitleDialog
import com.wsr.common.effect.ObserveNavigateEffect
import com.wsr.common.effect.ObserveToastEffect
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.memo.show.MemoShowUiState
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
        onClickArrowBack = viewModel::onClickArrowBack,
        onChecked = viewModel::changeItemChecked,
        onChangeContent = viewModel::changeItemContent,
        addItem = viewModel::addItem,
        onClickDivide = viewModel::divideItems,
        onClickTitle = viewModel::showDialog,
        shareItems = viewModel::shareItems,
        deleteCheckedItems = viewModel::deleteCheckedItems,
        onMoveItem = viewModel::swapItem,
    )

    if (uiState.isShowingEditMemoTitleDialog) {
        SettingsMemoTitleDialog(
            initialValue = uiState.title.value,
            onDismiss = viewModel::dismissDialog,
            onConfirm = viewModel::updateMemoTitle,
        )
    }

    ObserveToastEffect(viewModel.toastEffect)
    ObserveNavigateEffect(navController, viewModel.navigateEffect)
    ObserveShareItemsEffect(viewModel.sharedTextEffect)
}

@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    onClickArrowBack: () -> Unit,
    onChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
    addItem: () -> Unit,
    onClickTitle: () -> Unit,
    onClickDivide: () -> Unit,
    shareItems: () -> Unit,
    deleteCheckedItems: () -> Unit,
    onMoveItem: (from: ItemId, to: ItemId) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MemoShowTopBar(
                memoTitle = uiState.title.value,
                onClickArrowBack = onClickArrowBack,
                onClickTitle = onClickTitle,
                onClickDivide = onClickDivide,
                shareItems = shareItems,
                deleteCheckedItems = deleteCheckedItems,
            )
        },
        floatingActionButton = {
            MemoShowFloatActionButton(onClick = addItem)
        },
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
