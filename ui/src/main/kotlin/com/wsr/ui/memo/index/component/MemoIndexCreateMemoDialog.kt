package com.wsr.ui.memo.index.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wsr.ui.R

@Composable
fun MemoIndexCreateMemoDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (title: String) -> Unit,
) {
    val (text, setText) = remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Surface {
            Column(
                modifier = modifier.padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.memo_index_create_memo_title),
                    style = MaterialTheme.typography.h5,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = text, onValueChange = setText)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.memo_index_create_memo_dismiss_button))
                    }
                    TextButton(onClick = { onConfirm(text) }) {
                        Text(text = stringResource(id = R.string.memo_index_create_memo_confirm_button))
                    }
                }
            }
        }
    }
}
