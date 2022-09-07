package com.wsr.ui.setting.index

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wsr.ui.setting.index.screen.SettingIndexScreen

fun NavGraphBuilder.settingIndex(
    route: String,
    navController: NavHostController,
) {
    composable(route = route) {
        SettingIndexScreen()
    }
}
