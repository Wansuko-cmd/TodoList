package com.wsr.ui.settings.index.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alorma.compose.settings.ui.SettingsList
import com.wsr.common.settings.AppSettings
import com.wsr.ui.R

@Composable
fun SettingsIndexScreen() {
    val state = AppSettings.TextSize.getState()

    Scaffold(
        topBar = { SettingsIndexTopBar() },
    ) { innerPadding ->
        SettingsList(
            modifier = Modifier.padding(innerPadding),
            state = state,
            icon = { Icon(imageVector = Icons.Filled.FormatSize, contentDescription = null) },
            title = { Text(text = stringResource(id = R.string.setting_index_text_size)) },
            items = AppSettings.TextSize.items,
        )
    }
}
