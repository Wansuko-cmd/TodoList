package com.wsr.ui.memo.show.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun MemoShowTopBar(
    modifier: Modifier = Modifier,
    memoTitle: String,
    onClickArrowBack: () -> Unit,
    onClickTitle: () -> Unit,
    shareItems: () -> Unit,
    deleteCheckedItems: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            TextButton(onClick = onClickTitle) {
                Text(
                    text = memoTitle,
                    color = Color.White,
                    style = MaterialTheme.typography.h5,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onClickArrowBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        actions = {
            IconButton(onClick = shareItems) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null,
                )
            }
            IconButton(onClick = deleteCheckedItems) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                )
            }
        },
    )
}
