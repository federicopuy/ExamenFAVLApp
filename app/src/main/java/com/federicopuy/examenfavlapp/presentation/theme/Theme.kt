package com.federicopuy.examenfavlapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = LightBlue700,
    primaryVariant = LightBlue700,
    onPrimary = Color.White,
    secondary = Yellow500,
    secondaryVariant = Yellow700,
    onSecondary = Color.DarkGray,
    error = Green200,
    surface = LightBlue700,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = LightBlue500,
    primaryVariant = LightBlue700,
    onPrimary = Color.Black,
    secondary = Yellow500,
    secondaryVariant = Yellow700,
    error = Green700,
)

@Composable
fun ExamenFAVLAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}