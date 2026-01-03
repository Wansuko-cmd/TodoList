package com.wsr.ui.memo.show.effect

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.wsr.ui.R
import com.wsr.ui.memo.show.MemoShowItemUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

data class ShareItemsEffect(val items: List<MemoShowItemUiState>)

@Composable
fun ObserveShareItemsEffect(flow: Flow<ShareItemsEffect>) {
    val context = LocalContext.current
    val prefix = stringResource(R.string.memo_show_share_items_prefix)
    LaunchedEffect(Unit) {
        flow.collectLatest { effect ->
            val text = effect.items
                .filterNot { it.checked }
                .joinToString(prefix = prefix, separator = "\n$prefix") { it.content.value }
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(intent)
        }
    }
}
