package com.wsr.ui.settings.index.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.wsr.MainActivity
import com.wsr.ui.R

@Composable
fun SettingsIndexTopBar(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.setting_index_top_bar),
                style = MaterialTheme.typography.h5,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                MainActivity.restartActivity(context)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        }
    )
}
