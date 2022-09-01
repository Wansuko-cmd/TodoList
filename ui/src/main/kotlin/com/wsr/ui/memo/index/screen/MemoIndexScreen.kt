package com.wsr.ui.memo.index.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MemoIndexScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Scaffold(modifier) {
        Text(text = "MemoIndex")
    }
}
