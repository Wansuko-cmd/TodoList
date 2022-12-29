package com.wsr.common.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

data class NavigateEffect(
    val route: String,
)

@Composable
fun ObserveNavigateEffect(
    navController: NavHostController,
    flow: Flow<NavigateEffect>,
) {
    LaunchedEffect(Unit) {
        flow.collectLatest {
            navController.navigate(it.route)
        }
    }
}