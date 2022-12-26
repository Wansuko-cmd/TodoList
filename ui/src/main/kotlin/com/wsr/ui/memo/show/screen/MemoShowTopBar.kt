package com.wsr.ui.memo.show.screen

import androidx.compose.material.*
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
    navController: NavController,
    memoTitle: String,
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
            IconButton(onClick = { navController.popBackStack() }) {
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
