package com.wsr.ui.settings.index

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wsr.ui.settings.index.screen.SettingsIndexScreen

fun NavGraphBuilder.settingsIndex(
    route: String,
) {
    composable(route = route) {
        SettingsIndexScreen()
    }
}
