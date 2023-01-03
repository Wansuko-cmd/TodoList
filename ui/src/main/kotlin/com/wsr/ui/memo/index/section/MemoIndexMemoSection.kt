package com.wsr.ui.memo.index.section

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
import com.wsr.memo.MemoId
import com.wsr.ui.memo.index.MemoIndexUiState
import com.wsr.ui.memo.index.component.MemoIndexMemoTile

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoIndexMemoSection(
    modifier: Modifier = Modifier,
    uiState: MemoIndexUiState,
    onClickTile: (memoId: MemoId) -> Unit,
    onClickUpdateMemoTitleButton: (memoId: String) -> Unit,
    onClickDeleteButton: (memoId: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        // itemの移動時の挙動を直すために必要
        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(uiState.memos, key = { it.id }) {
            MemoIndexMemoTile(
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                memoUiState = it,
                onClickTile = onClickTile,
                onClickUpdateMemoTitleButton = onClickUpdateMemoTitleButton,
                onClickDeleteButton = onClickDeleteButton,
            )
        }
    }
}
