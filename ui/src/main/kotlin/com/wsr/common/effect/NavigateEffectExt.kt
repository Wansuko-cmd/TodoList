package com.wsr.common.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

sealed class NavigateEffect {
    data class Navigate(val route: String) : NavigateEffect()
    object PopBackStack : NavigateEffect()
}

@Composable
fun ObserveNavigateEffect(
    navController: NavHostController,
    flow: Flow<NavigateEffect>,
) {
    LaunchedEffect(Unit) {
        flow.collectLatest {
            when (it) {
                is NavigateEffect.Navigate ->
                    navController.navigate(it.route)
                is NavigateEffect.PopBackStack ->
                    navController.popBackStack()
            }
        }
    }
}
