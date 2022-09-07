package com.wsr

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wsr.ui.memo.memoGraph
import com.wsr.ui.setting.settingGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.Memo.root,
    ) {
        memoGraph(navController)
        settingGraph(navController)
    }
}
