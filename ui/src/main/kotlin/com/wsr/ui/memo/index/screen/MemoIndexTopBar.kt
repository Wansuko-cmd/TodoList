package com.wsr.ui.memo.index.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wsr.ui.R

@Composable
fun MemoIndexTopBar(
    modifier: Modifier = Modifier,
    onClickSettings: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h5,
            )
        },
        actions = {
            IconButton(
                onClick = onClickSettings,
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                )
            }
        },
    )
}
