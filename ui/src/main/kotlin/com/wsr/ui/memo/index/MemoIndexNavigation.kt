package com.wsr.ui.memo.index

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wsr.ui.memo.index.screen.MemoIndexScreen

fun NavGraphBuilder.memoIndex(
    route: String,
    navController: NavHostController,
) {
    composable(route = route) {
        MemoIndexScreen(
            viewModel = hiltViewModel(),
            navController = navController,
        )
    }
}
