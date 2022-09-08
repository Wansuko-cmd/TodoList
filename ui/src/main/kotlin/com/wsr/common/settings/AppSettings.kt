package com.wsr.common.settings

import androidx.compose.runtime.Composable
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState

object AppSettings {
    object TextSize {
        private const val key = "textSize"
        val items = TextSizeItem.values().map { it.name }
        private val defaultValue = TextSizeItem.Normal

        @Composable
        fun getState() = rememberPreferenceIntSettingState(key = key, defaultValue = defaultValue.ordinal)

        @Composable
        fun getValue(): TextSizeItem = getState()
            .value
            .let { TextSizeItem.values().getOrElse(it) { defaultValue } }

        enum class TextSizeItem {
            Small,
            Normal,
            Large
        }
    }
}
