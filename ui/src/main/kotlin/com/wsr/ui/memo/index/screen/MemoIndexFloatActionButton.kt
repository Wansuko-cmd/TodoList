package com.wsr.ui.memo.index.screen

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MemoIndexFloatActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Icon(Icons.Filled.Add, contentDescription = null)
}