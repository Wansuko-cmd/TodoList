package com.wsr.ui.memo.show.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
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
import com.wsr.common.effect.observeToastEffect
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.component.MemoShowItemTile
import com.wsr.ui.memo.show.viewmodel.MemoShowViewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

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
        deleteCheckedItems = viewModel::deleteCheckedItem,
        onMoveItem = viewModel::swapItem,
    )

    observeToastEffect(viewModel.toastEffect)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoShowScreen(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    navController: NavHostController,
    onChecked: (itemId: String) -> Unit,
    onChangeContent: (itemId: String, content: String) -> Unit,
    addItem: () -> Unit,
    deleteCheckedItems: () -> Unit,
    onMoveItem: (from: String, to: String) -> Unit,
) {
    val reorderState = rememberReorderableLazyListState(onMove = { from, to ->
        onMoveItem(from.key.toString(), to.key.toString())
    })

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
        LazyColumn(
            state = reorderState.listState,
            modifier = Modifier.padding(innerPadding)
                .reorderable(reorderState)
                .detectReorderAfterLongPress(reorderState),
        ) {

            items(uiState.items, key = { it.id }) { item ->
                ReorderableItem(reorderState, key = item.id) { isDragging ->
                    MemoShowItemTile(
                        modifier = Modifier.animateItemPlacement(),
                        itemUiState = item,
                        isDragging = isDragging,
                        onChecked = onChecked,
                        onChangeContent = onChangeContent,
                    )
                }
            }
        }
    }
}
