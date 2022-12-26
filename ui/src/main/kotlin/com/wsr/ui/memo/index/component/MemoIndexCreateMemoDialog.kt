package com.wsr.ui.memo.index.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wsr.theme.MossGreen
import com.wsr.theme.TodoListTheme
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
                modifier = modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.memo_index_create_memo_title),
                    style = MaterialTheme.typography.h5,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = setText,
                    placeholder = { Text(text = stringResource(id = R.string.memo_index_create_memo_title)) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = MossGreen,
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = onDismiss,
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.memo_index_create_memo_dismiss_button),
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color(0xFF0099CC)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = { onConfirm(text) },
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.memo_index_create_memo_confirm_button),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Preview
@Composable
fun MemoIndexCreateMemoDialogPreview() {
    TodoListTheme {
        MemoIndexCreateMemoDialog(
            onDismiss = {},
            onConfirm = {},
        )
    }
}