package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xiaomi.workstation.ui.theme.GlassBorder
import com.xiaomi.workstation.ui.theme.GlassSurface
import com.xiaomi.workstation.ui.theme.GlassSurfaceLight

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.2f)
            )
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x40FFFFFF),  // top - brighter
                        Color(0x1AFFFFFF),  // middle - subtle
                        Color(0x0DFFFFFF)   // bottom - darker
                    )
                )
            )
            .border(
                width = 0.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x4DFFFFFF),  // top border - visible
                        Color(0x1AFFFFFF)   // bottom border - subtle
                    )
                ),
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
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.4f),
                spotColor = Color.Black.copy(alpha = 0.3f)
            )
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x4DFFFFFF),  // 30% white - strong top
                        Color(0x26FFFFFF),  // 15% white - middle
                        Color(0x1AFFFFFF)   // 10% white - bottom
                    )
                )
            )
            .border(
                width = 0.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x66FFFFFF),  // 40% white - top edge
                        Color(0x33FFFFFF)   // 20% white - bottom edge
                    )
                ),
                shape = shape
            ),
        content = content
    )
}

@Composable
fun DockPanel(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(28.dp)
    Box(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.5f),
                spotColor = Color.Black.copy(alpha = 0.4f)
            )
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x4DFFFFFF),  // 30% white - top
                        Color(0x26FFFFFF),  // 15% white - bottom
                        Color(0x1AFFFFFF)   // 10% white - very bottom
                    )
                )
            )
            .border(
                width = 0.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x66FFFFFF),  // 40% white - top edge
                        Color(0x33FFFFFF)   // 20% white - bottom edge
                    )
                ),
                shape = shape
            ),
        content = content
    )
}
