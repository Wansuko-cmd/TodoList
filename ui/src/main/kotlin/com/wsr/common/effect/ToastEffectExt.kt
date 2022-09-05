package com.wsr.common.effect

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

data class ToastEffect(
    val messageResourceId: Int,
)

@Composable
fun observeToastEffect(flow: Flow<ToastEffect>) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        flow.collectLatest {
            Toast.makeText(
                context,
                context.getString(it.messageResourceId),
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}
