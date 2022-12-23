package com.wsr.ui.memo.show

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wsr.ui.memo.show.screen.MemoShowScreen
import com.wsr.ui.memo.show.viewmodel.memoShowViewModel

fun NavGraphBuilder.memoShow(
    route: String,
    navController: NavHostController,
) {
    composable(route = route) {
        val memoId = it.arguments?.getString("memoId")
            ?: run { navController.popBackStack(); "" }
        MemoShowScreen(
            viewModel = memoShowViewModel(memoId = memoId),
            navController = navController,
        )
    }
}
