package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

@Composable
fun LiquidGlass(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    val baseModifier = if (hazeState != null) {
        Modifier
            .shadow(12.dp, shape, ambientColor = Color.Black.copy(0.4f), spotColor = Color.Black.copy(0.3f))
            .clip(shape)
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = Color(0x1AFFFFFF),
                    tint = Color(0x0DFFFFFF),
                    blurRadius = 24.dp,
                    noiseFactor = 0f
                )
            )
            .background(Color(0x0DFFFFFF))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x4DFFFFFF), Color(0x14FFFFFF))), shape)
    } else {
        Modifier
            .shadow(12.dp, shape, ambientColor = Color.Black.copy(0.4f), spotColor = Color.Black.copy(0.3f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x22FFFFFF), Color(0x0DFFFFFF), Color(0x06FFFFFF))))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x4DFFFFFF), Color(0x14FFFFFF))), shape)
    }
    Box(modifier = modifier.then(baseModifier), content = content)
}

@Composable
fun WidgetGlass(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val baseModifier = if (hazeState != null) {
        Modifier
            .shadow(16.dp, shape, ambientColor = Color.Black.copy(0.5f), spotColor = Color.Black.copy(0.4f))
            .clip(shape)
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = Color(0x1FFFFFFF),
                    tint = Color(0x0FFFFFFF),
                    blurRadius = 28.dp,
                    noiseFactor = 0f
                )
            )
            .background(Color(0x14FFFFFF))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x66FFFFFF), Color(0x22FFFFFF))), shape)
    } else {
        Modifier
            .shadow(16.dp, shape, ambientColor = Color.Black.copy(0.5f), spotColor = Color.Black.copy(0.4f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x28FFFFFF), Color(0x14FFFFFF), Color(0x08FFFFFF))))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x66FFFFFF), Color(0x22FFFFFF))), shape)
    }
    Box(modifier = modifier.then(baseModifier), content = content)
}

@Composable
fun DockGlass(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(28.dp)
    val baseModifier = if (hazeState != null) {
        Modifier
            .shadow(20.dp, shape, ambientColor = Color.Black.copy(0.6f), spotColor = Color.Black.copy(0.5f))
            .clip(shape)
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = Color(0x1AFFFFFF),
                    tint = Color(0x0DFFFFFF),
                    blurRadius = 32.dp,
                    noiseFactor = 0f
                )
            )
            .background(Color(0x0FFFFFFF))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x66FFFFFF), Color(0x22FFFFFF))), shape)
    } else {
        Modifier
            .shadow(20.dp, shape, ambientColor = Color.Black.copy(0.6f), spotColor = Color.Black.copy(0.5f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x28FFFFFF), Color(0x14FFFFFF), Color(0x08FFFFFF))))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x66FFFFFF), Color(0x22FFFFFF))), shape)
    }
    Box(modifier = modifier.then(baseModifier), content = content)
}

@Composable
fun IconGlass(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    // Keep icons simple without haze - performance
    Box(
        modifier = modifier
            .shadow(6.dp, shape, ambientColor = Color.Black.copy(0.3f), spotColor = Color.Black.copy(0.2f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x1AFFFFFF), Color(0x0AFFFFFF))))
            .border(0.5.dp, Color(0x14FFFFFF), shape),
        content = content
    )
}

// Extension for wallpaper background to act as haze source
fun Modifier.liquidHazeSource(hazeState: HazeState): Modifier {
    return this.hazeSource(state = hazeState)
}
