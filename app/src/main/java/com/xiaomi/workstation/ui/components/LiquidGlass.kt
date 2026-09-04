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

@Composable
fun LiquidGlass(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .shadow(12.dp, shape, ambientColor = Color.Black.copy(0.4f), spotColor = Color.Black.copy(0.3f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x2AFFFFFF), Color(0x12FFFFFF), Color(0x08FFFFFF))))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x4DFFFFFF), Color(0x14FFFFFF))), shape),
        content = content
    )
}

@Composable
fun WidgetGlass(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .shadow(16.dp, shape, ambientColor = Color.Black.copy(0.5f), spotColor = Color.Black.copy(0.4f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x30FFFFFF), Color(0x16FFFFFF), Color(0x0AFFFFFF))))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x66FFFFFF), Color(0x22FFFFFF))), shape),
        content = content
    )
}

@Composable
fun DockGlass(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(28.dp)
    Box(
        modifier = modifier
            .shadow(20.dp, shape, ambientColor = Color.Black.copy(0.6f), spotColor = Color.Black.copy(0.5f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x30FFFFFF), Color(0x16FFFFFF), Color(0x0AFFFFFF))))
            .border(0.5.dp, Brush.verticalGradient(listOf(Color(0x66FFFFFF), Color(0x22FFFFFF))), shape),
        content = content
    )
}

@Composable
fun IconGlass(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier
            .shadow(6.dp, shape, ambientColor = Color.Black.copy(0.3f), spotColor = Color.Black.copy(0.2f))
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x1AFFFFFF), Color(0x0AFFFFFF))))
            .border(0.5.dp, Color(0x14FFFFFF), shape),
        content = content
    )
}
