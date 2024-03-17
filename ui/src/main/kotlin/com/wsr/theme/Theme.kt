package com.wsr.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.wsr.common.settings.AppSettings

private val LightColorPalette = lightColors(
    primary = MossGreen,
    primaryVariant = MossGreenVariant,
    secondary = MossGreen,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
     */
)

@Composable
fun TodoListTheme(
    textSize: AppSettings.TextSize.TextSizeItem = AppSettings.TextSize.getValue(),
    content: @Composable () -> Unit,
) {
    val typography = when (textSize) {
        AppSettings.TextSize.TextSizeItem.Small -> SmallTypography
        AppSettings.TextSize.TextSizeItem.Normal -> NormalTypography
        AppSettings.TextSize.TextSizeItem.Large -> LargeTypography
    }

    MaterialTheme(
        colors = LightColorPalette,
        typography = typography,
        shapes = Shapes,
        content = content,
    )
}
