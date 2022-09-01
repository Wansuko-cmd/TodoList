package com.wsr.ui.memo

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.wsr.Route
import com.wsr.ui.memo.index.memoIndex
import com.wsr.ui.memo.show.memoShow

fun NavGraphBuilder.memoGraph(navController: NavHostController) {
    navigation(
        startDestination = Route.Memo.Index.path,
        route = Route.Memo.root,
    ) {
        memoIndex(
            route = Route.Memo.Index.path,
            navController = navController,
        )
        memoShow(
            route = Route.Memo.Show.path,
            navController = navController,
        )
    }
}
