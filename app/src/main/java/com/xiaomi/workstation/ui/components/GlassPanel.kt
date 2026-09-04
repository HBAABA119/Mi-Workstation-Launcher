package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xiaomi.workstation.ui.theme.GlassBorder
import com.xiaomi.workstation.ui.theme.GlassWhite12
import com.xiaomi.workstation.ui.theme.GlassWhite8

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    alpha: Float = 0.12f,
    borderAlpha: Float = 0.08f,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassWhite12.copy(alpha = alpha),
                        GlassWhite8.copy(alpha = alpha * 0.6f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = GlassBorder.copy(alpha = borderAlpha),
                shape = shape
            ),
        content = content
    )
}

@Composable
fun WidgetCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    GlassPanel(
        modifier = modifier,
        cornerRadius = 20.dp,
        alpha = 0.14f,
        borderAlpha = 0.1f,
        content = content
    )
}
