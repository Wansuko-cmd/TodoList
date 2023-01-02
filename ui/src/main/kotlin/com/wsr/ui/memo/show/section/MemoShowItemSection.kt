package com.wsr.ui.memo.show.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.component.MemoShowItemTile
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoShowItemSection(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    onChangeChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
    onMoveItem: (from: ItemId, to: ItemId) -> Unit,
) {
    val reorderItemState = rememberReorderableLazyListState(
        onMove = { from, to ->
            onMoveItem(ItemId(from.key.toString()), ItemId(to.key.toString()))
        },
        // 一番上のアイテムだけ動かないようにする
        canDragOver = { draggedOver, _ -> draggedOver.index != 0 },
    )

    LazyColumn(
        state = reorderItemState.listState,
        modifier = modifier
            .fillMaxSize()
            .reorderable(reorderItemState)
            .detectReorderAfterLongPress(reorderItemState),
    ) {
        // itemの移動時の挙動を直すために必要
        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(uiState.items, key = { it.id.value }) { item ->
            ReorderableItem(reorderItemState, key = item.id) { isDragging ->
                MemoShowItemTile(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    itemUiState = item,
                    isDragging = isDragging,
                    shouldFocus = item.id == uiState.shouldFocusItemId,
                    onChangeChecked = onChangeChecked,
                    onChangeContent = onChangeContent,
                )
            }
        }
    }
}
