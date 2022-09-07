package com.wsr.ui.setting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.wsr.Route
import com.wsr.ui.setting.index.settingIndex

fun NavGraphBuilder.settingGraph(navController: NavHostController) {
    navigation(
        startDestination = Route.Setting.Index.path,
        route = Route.Setting.root,
    ) {
        settingIndex(
            route = Route.Setting.Index.path,
            navController = navController,
        )
    }
}