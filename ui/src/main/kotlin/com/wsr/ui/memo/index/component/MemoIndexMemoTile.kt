package com.wsr.ui.memo.index.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wsr.memo.MemoId
import com.wsr.ui.R
import com.wsr.ui.memo.index.MemoIndexMemoUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoIndexMemoTile(
    modifier: Modifier = Modifier,
    memoUiState: MemoIndexMemoUiState,
    onClickTile: (memoId: MemoId) -> Unit,
    onClickUpdateMemoTitleButton: (memoId: String) -> Unit,
    onClickDeleteButton: (memoId: String) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClickTile(MemoId(memoUiState.id)) },
                onLongClick = { showMenu = true },
            ),
        elevation = 4.dp,
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = memoUiState.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
            )

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(
                    onClick = {
                        showMenu = false
                        onClickUpdateMemoTitleButton(memoUiState.id)
                    },
                ) {
                    Text(text = stringResource(id = R.string.memo_index_update_memo_title))
                }
                DropdownMenuItem(
                    onClick = {
                        showMenu = false
                        onClickDeleteButton(memoUiState.id)
                    },
                ) {
                    Text(text = stringResource(id = R.string.memo_index_delete_memo))
                }
            }
        }
    }
}
