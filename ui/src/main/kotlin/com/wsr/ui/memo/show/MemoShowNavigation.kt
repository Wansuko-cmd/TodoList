package com.wsr.ui.memo.show

import androidx.compose.material.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.memoShow(
    route: String,
    navController: NavHostController,
) {
    composable(route = route) {
        Text(text = "show")
    }
}
