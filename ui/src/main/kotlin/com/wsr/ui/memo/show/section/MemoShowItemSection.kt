package com.wsr.ui.memo.show.section

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.gestures.drag
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.wsr.memo.ItemContent
import com.wsr.memo.ItemId
import com.wsr.ui.memo.show.MemoShowItemUiState
import com.wsr.ui.memo.show.MemoShowUiState
import com.wsr.ui.memo.show.component.MemoShowItemTile
import kotlinx.coroutines.CancellationException

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
                detectLongPress(
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
                        val current = currentIndex
                            ?.let { state.layoutInfo.visibleItemsInfo.getOrNull(it) }
                            ?: return@detectLongPress

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
                        if (target == null) return@detectLongPress
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

/**
 * detectDragGesturesAfterLongPressを参考
 * requiredUnconsumed = trueだけ変更
 */
private suspend fun PointerInputScope.detectLongPress(
    onDragStart: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
) {
    awaitEachGesture {
        try {
            val down = awaitFirstDown(requireUnconsumed = true)
            val drag = awaitLongPressOrCancellation(down.id)
            if (drag != null) {
                onDragStart.invoke(drag.position)

                if (
                    drag(drag.id) {
                        onDrag(it, it.positionChange())
                        it.consume()
                    }
                ) {
                    // consume up if we quit drag gracefully with the up
                    currentEvent.changes.fastForEach { if (it.changedToUp()) it.consume() }
                    onDragEnd()
                } else {
                    onDragCancel()
                }
            }
        } catch (c: CancellationException) {
            onDragCancel()
            throw c
        }
    }
}
