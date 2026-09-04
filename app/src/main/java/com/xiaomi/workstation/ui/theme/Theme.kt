package com.xiaomi.workstation.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MiWorkstationColorScheme = darkColorScheme(
    primary = AccentBlue,
    onPrimary = TextOnAccent,
    primaryContainer = OceanBlue,
    onPrimaryContainer = TextPrimary,
    secondary = LightBlue,
    onSecondary = TextOnAccent,
    secondaryContainer = MidBlue,
    onSecondaryContainer = TextPrimary,
    tertiary = CalendarAccent,
    onTertiary = TextOnAccent,
    tertiaryContainer = MediaGradientStart,
    onTertiaryContainer = TextPrimary,
    background = DeepNavy,
    onBackground = TextPrimary,
    surface = DeepNavyDark,
    onSurface = TextPrimary,
    surfaceVariant = GlassSurface,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorder,
    outlineVariant = GlassSurfaceLight,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun MiWorkstationTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = MiWorkstationColorScheme,
        typography = MiWorkstationTypography,
        content = content
    )
}
