package com.wsr.ui.memo.show.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.common.composable.LoadingScreen
import com.wsr.common.composable.dialog.CheckConfirmDangerDialog
import com.wsr.common.composable.dialog.SettingsMemoTitleDialog
import com.wsr.common.effect.ObserveNavigateEffect
import com.wsr.common.effect.ObserveToastEffect
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.R
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.effect.ObserveShareItemsEffect
import com.wsr.ui.memo.show.section.MemoShowItemSection
import com.wsr.ui.memo.show.viewmodel.MemoShowViewModel

@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoShowViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getMemoAndUpdateUiState()
    }

    MemoShowScreen(
        modifier = modifier,
        uiState = uiState,
        onClickArrowBack = viewModel::onClickArrowBack,
        onClickTitle = viewModel::showUpdateMemoTitleDialog,
        onClickShareItems = viewModel::onClickShareItems,
        onClickDivide = viewModel::showEditDivideMemoTitleDialog,
        onClickDeleteCheckedItems = viewModel::showCheckIfDeleteCheckedItemsDialog,
        onClickAddItem = viewModel::onClickAddItem,
        onChangeChecked = viewModel::onChangeChecked,
        onChangeContent = viewModel::onChangeContent,
        onMoveItem = viewModel::onMoveItem,
    )

    if (uiState.isShowingUpdateMemoTitleDialog) {
        SettingsMemoTitleDialog(
            initialValue = uiState.title.value,
            onDismiss = viewModel::dismissUpdateMemoTitleDialog,
            onConfirm = viewModel::updateMemoTitle,
        )
    }

    if (uiState.isShowingEditDivideMemoTitleDialog) {
        SettingsMemoTitleDialog(
            onDismiss = viewModel::dismissEditDivideMemoTitleDialog,
            onConfirm = viewModel::divideItems,
        )
    }

    if (uiState.isShowingCheckIfDeleteCheckedItemsDialog) {
        CheckConfirmDangerDialog(
            message = context.getString(R.string.memo_show_check_if_delete_checked_items_dialog_message),
            onDismiss = viewModel::dismissCheckIfDeleteCheckedItemsDialog,
            onConfirm = viewModel::deleteCheckedItems,
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

    // TopBar
    onClickArrowBack: () -> Unit,
    onClickTitle: () -> Unit,
    onClickShareItems: () -> Unit,
    onClickDivide: () -> Unit,
    onClickDeleteCheckedItems: () -> Unit,

    // Fab
    onClickAddItem: () -> Unit,

    // Section
    onChangeChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
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
                onClickShareItems = onClickShareItems,
                onClickDeleteCheckedItems = onClickDeleteCheckedItems,
            )
        },
        floatingActionButton = {
            MemoShowFloatActionButton(onClick = onClickAddItem)
        },
    ) { innerPadding ->
        MemoShowItemSection(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onChangeChecked = onChangeChecked,
            onChangeContent = onChangeContent,
            onMoveItem = onMoveItem,
        )
        if (uiState.isLoading) LoadingScreen()
    }
}
