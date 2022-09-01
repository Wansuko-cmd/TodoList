package com.wsr.ui.memo.index.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wsr.theme.TodoListTheme
import com.wsr.ui.memo.index.MemoIndexMemoUiState

@Composable
fun MemoIndexMemoTile(
    modifier: Modifier = Modifier,
    memoUiState: MemoIndexMemoUiState,
    onClickTile: (memoId: String) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClickTile(memoUiState.id) },
        elevation = 4.dp,
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = memoUiState.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}
