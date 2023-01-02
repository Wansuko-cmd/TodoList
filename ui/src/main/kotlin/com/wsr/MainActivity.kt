package com.wsr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wsr.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colors.primaryVariant
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = false,
                    )
                }

                val navController = rememberNavController()
                MainNavHost(navController = navController)
            }
        }
    }

    companion object {
        fun restartActivity(context: Context) = context
            .let { it as Activity }
            .also { it.finish() }
            .let { Intent(it, MainActivity::class.java) }
            .let { context.startActivity(it) }
    }
}
