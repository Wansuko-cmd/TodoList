package com.wsr.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.wsr.Route
import com.wsr.ui.settings.index.settingsIndex

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    navigation(
        startDestination = Route.Settings.Index.path,
        route = Route.Settings.root,
    ) {
        settingsIndex(
            route = Route.Settings.Index.path,
        )
    }
}
