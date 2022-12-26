package com.wsr.ui.memo.show.effect

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

data class ShareItemsEffect(val text: String)

@Composable
fun ObserveShareItemsEffect(flow: Flow<ShareItemsEffect>) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        flow.collectLatest {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.text)
            }
            context.startActivity(intent)
        }
    }
}
