package com.wsr.ui.memo.show.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.wsr.common.composable.LoadingScreen
import com.wsr.common.effect.ToastEffect
import com.wsr.common.effect.observeToastEffect
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.section.MemoShowItemSection
import com.wsr.ui.memo.show.viewmodel.MemoShowViewModel
import com.wsr.ui.memo.show.viewmodel.SharedTextEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    viewModel: MemoShowViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val shouldFocusItemId by viewModel.focusEffect.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMemoAndUpdateUiState()
    }

    MemoShowScreen(
        modifier = modifier,
        uiState = uiState,
        shouldFocusItemId = shouldFocusItemId.itemId,
        navController = navController,
        onChecked = viewModel::changeItemChecked,
        onChangeContent = viewModel::changeItemContent,
        addItem = viewModel::addItem,
        createSharedText = viewModel::createSharedText,
        deleteCheckedItems = viewModel::deleteCheckedItem,
        onMoveItem = viewModel::swapItem,
    )

    observeToastEffect(viewModel.toastEffect)
    ObserveSharedTextEffect(viewModel.sharedTextEffect)
}

@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    shouldFocusItemId: ItemId?,
    navController: NavHostController,
    onChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
    addItem: () -> Unit,
    createSharedText: () -> String,
    deleteCheckedItems: () -> Unit,
    onMoveItem: (from: ItemId, to: ItemId) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MemoShowTopBar(
                navController = navController,
                memoTitle = uiState.title.value,
                createSharedText = createSharedText,
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
            shouldFocusItemId = shouldFocusItemId,
            onChecked = onChecked,
            onChangeContent = onChangeContent,
            onMoveItem = onMoveItem,
        )
        if (uiState.isLoading) LoadingScreen()
    }
}

@Composable
fun ObserveSharedTextEffect(flow: Flow<SharedTextEffect>) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        flow.collectLatest {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.text)
            }
        }
    }
}
