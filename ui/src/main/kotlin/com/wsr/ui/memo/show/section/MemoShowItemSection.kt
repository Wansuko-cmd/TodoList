package com.wsr.ui.memo.show.section

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.memo.show.MemoShowItemUiState
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.component.MemoShowItemTile

@SuppressLint("FrequentlyChangingValue")
@Composable
fun MemoShowItemSection(
    modifier: Modifier = Modifier,
    uiState: MemoShowUiState,
    onChangeChecked: (itemId: ItemId) -> Unit,
    onChangeContent: (itemId: ItemId, content: ItemContent) -> Unit,
    onMoveItem: (from: ItemId, to: ItemId) -> Unit,
) {
    val state = rememberLazyListState()
    var currentIndex: Int? by remember { mutableStateOf(null) }

    LaunchedEffect(uiState.shouldFocusItemId) {
        val index = uiState.items
            .indexOfFirst { it.id == uiState.shouldFocusItemId }
            .takeIf { it != -1 } ?: return@LaunchedEffect
        state.scrollToItem(index)
    }

    LazyColumn(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(state.layoutInfo.visibleItemsInfo.size) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset ->
                        currentIndex = state.layoutInfo
                            .visibleItemsInfo
                            .find { info ->
                                offset.y.toInt() in info.offset..info.offset + info.size
                            }
                            ?.index
                    },
                    onDragEnd = {
                        currentIndex = null
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val current = currentIndex
                            ?.let { state.layoutInfo.visibleItemsInfo.getOrNull(it) }
                            ?: return@detectDragGesturesAfterLongPress

                        val target = if (dragAmount.y < 0f) {
                            state.layoutInfo
                                .visibleItemsInfo
                                .take(current.index)
                                .find { change.position.y < it.offset + it.size / 2 }
                        } else {
                            state.layoutInfo
                                .visibleItemsInfo
                                .drop(current.index + 1)
                                .find { change.position.y > it.offset + it.size / 2 }
                        }
                        if (target == null) return@detectDragGesturesAfterLongPress
                        val from = ItemId(current.key.toString())
                        val to = ItemId(target.key.toString())
                        onMoveItem(from, to)
                        currentIndex = target.index
                    },
                )
            },
    ) {
        itemsIndexed(
            items = uiState.items,
            key = { _, item -> item.id.value },
        ) { index: Int, item: MemoShowItemUiState ->
            MemoShowItemTile(
                modifier = Modifier
                    .animateItem()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                itemUiState = item,
                isDragging = index == currentIndex,
                shouldFocus = item.id == uiState.shouldFocusItemId,
                onChangeChecked = onChangeChecked,
                onChangeContent = onChangeContent,
            )
        }
    }
}
