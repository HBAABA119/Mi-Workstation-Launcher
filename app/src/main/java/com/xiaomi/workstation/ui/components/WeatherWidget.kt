package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.workstation.ui.theme.AccentBlue
import com.xiaomi.workstation.ui.theme.TextPrimary
import com.xiaomi.workstation.ui.theme.TextSecondary
import dev.chrisbanes.haze.HazeState

@Composable
fun WeatherWidget(modifier: Modifier = Modifier, hazeState: HazeState? = null) {
    WidgetGlass(modifier = modifier, hazeState = hazeState) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.Cloud, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Cloudy", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "24", fontSize = 40.sp, fontWeight = FontWeight.Thin, color = TextPrimary, letterSpacing = (-1).sp)
                Text(text = "°", fontSize = 24.sp, fontWeight = FontWeight.Thin, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "28° / 20°", fontSize = 11.sp, fontWeight = FontWeight.Normal, color = TextSecondary)
            Text(text = "Beijing", fontSize = 11.sp, fontWeight = FontWeight.Normal, color = TextSecondary)
        }
    }
}
