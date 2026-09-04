package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.xiaomi.workstation.data.AppInfo

@Composable
fun Dock(
    dockApps: List<AppInfo>,
    recentApps: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit,
    onDrawerClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DockPanel(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pinned dock apps
            dockApps.take(5).forEach { app ->
                DockAppItem(
                    app = app,
                    onClick = { onAppClick(app) }
                )
            }

            // Divider
            if (dockApps.isNotEmpty() && recentApps.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(Color(0x33FFFFFF))
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Recent apps
            recentApps.take(3).forEach { app ->
                DockAppItem(
                    app = app,
                    onClick = { onAppClick(app) }
                )
            }

            // Drawer button
            DockIconButton(
                icon = Icons.Filled.Apps,
                label = "Apps",
                onClick = onDrawerClick
            )

            // Settings button
            DockIconButton(
                icon = Icons.Filled.Settings,
                label = "Settings",
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun DockAppItem(
    app: AppInfo,
    onClick: () -> Unit
) {
    val iconShape = RoundedCornerShape(14.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = iconShape,
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .clip(iconShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x33FFFFFF),
                            Color(0x1AFFFFFF)
                        )
                    )
                )
                .border(
                    width = 0.5.dp,
                    color = Color(0x33FFFFFF),
                    shape = iconShape
                ),
            contentAlignment = Alignment.Center
        ) {
            app.icon?.let { drawable ->
                val bitmap = remember(drawable) {
                    drawable.toBitmap(96, 96).asImageBitmap()
                }
                Image(
                    bitmap = bitmap,
                    contentDescription = app.label,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun DockIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    val iconShape = RoundedCornerShape(14.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = iconShape,
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .clip(iconShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x33FFFFFF),
                            Color(0x1AFFFFFF)
                        )
                    )
                )
                .border(
                    width = 0.5.dp,
                    color = Color(0x33FFFFFF),
                    shape = iconShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xB3FFFFFF),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
