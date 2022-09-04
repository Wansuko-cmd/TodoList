package com.wsr.ui.memo.show.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MemoShowTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    memoTitle: String,
    deleteCheckedItems: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = memoTitle) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        },
        actions = {
            IconButton(onClick = deleteCheckedItems) {
                Icon(Icons.Filled.Delete, null)
            }
        },
    )
}
