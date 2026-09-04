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
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.xiaomi.workstation.data.AppInfo
import dev.chrisbanes.haze.HazeState

@Composable
fun Dock(
    dockApps: List<AppInfo>,
    recentApps: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit,
    onDrawerClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null
) {
    DockGlass(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), hazeState = hazeState) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            dockApps.take(5).forEach { app -> DockAppItem(app = app, onClick = { onAppClick(app) }) }
            if (dockApps.isNotEmpty() && recentApps.isNotEmpty()) {
                Box(modifier = Modifier.width(1.dp).height(36.dp).background(Color(0x33FFFFFF)))
                Spacer(modifier = Modifier.width(8.dp))
            }
            recentApps.take(3).forEach { app -> DockAppItem(app = app, onClick = { onAppClick(app) }) }
            DockIconButton(icon = Icons.Filled.Apps, onClick = onDrawerClick)
            DockIconButton(icon = Icons.Filled.Settings, onClick = onSettingsClick)
        }
    }
}

@Composable
private fun DockAppItem(app: AppInfo, onClick: () -> Unit) {
    val iconShape = RoundedCornerShape(14.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick).padding(4.dp)
    ) {
        Box(
            modifier = Modifier.size(44.dp).shadow(4.dp, iconShape, ambientColor = Color.Black.copy(0.3f), spotColor = Color.Black.copy(0.2f))
                .clip(iconShape).background(Brush.verticalGradient(listOf(Color(0x33FFFFFF), Color(0x1AFFFFFF)))).border(0.5.dp, Color(0x33FFFFFF), iconShape),
            contentAlignment = Alignment.Center
        ) {
            app.icon?.let { drawable ->
                val bitmap = remember(drawable) { drawable.toBitmap(96, 96).asImageBitmap() }
                Image(bitmap = bitmap, contentDescription = app.label, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
private fun DockIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    val iconShape = RoundedCornerShape(14.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick).padding(4.dp)
    ) {
        Box(
            modifier = Modifier.size(44.dp).shadow(4.dp, iconShape, ambientColor = Color.Black.copy(0.3f), spotColor = Color.Black.copy(0.2f))
                .clip(iconShape).background(Brush.verticalGradient(listOf(Color(0x33FFFFFF), Color(0x1AFFFFFF)))).border(0.5.dp, Color(0x33FFFFFF), iconShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xB3FFFFFF), modifier = Modifier.size(22.dp))
        }
    }
}
