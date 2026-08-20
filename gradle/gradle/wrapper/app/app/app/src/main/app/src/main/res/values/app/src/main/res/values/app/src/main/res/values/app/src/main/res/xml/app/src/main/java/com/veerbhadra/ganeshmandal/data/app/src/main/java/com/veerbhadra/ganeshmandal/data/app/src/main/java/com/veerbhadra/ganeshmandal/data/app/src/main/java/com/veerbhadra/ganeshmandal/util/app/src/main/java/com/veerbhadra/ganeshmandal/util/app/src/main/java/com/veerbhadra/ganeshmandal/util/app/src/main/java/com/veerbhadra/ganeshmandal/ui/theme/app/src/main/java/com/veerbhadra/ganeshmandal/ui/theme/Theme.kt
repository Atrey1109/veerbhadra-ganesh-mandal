package com.veerbhadra.ganeshmandal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = MaroonPrimary,
    onPrimary = IvoryBackground,
    primaryContainer = MaroonDark,
    onPrimaryContainer = GoldLight,
    secondary = AntiqueGold,
    onSecondary = TextDark,
    background = IvoryBackground,
    surface = IvorySurface,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun VeerbhadraMandalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        content = content
    )
}
