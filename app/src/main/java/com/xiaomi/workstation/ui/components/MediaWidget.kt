package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.workstation.ui.theme.AccentBlue
import com.xiaomi.workstation.ui.theme.MediaGradientEnd
import com.xiaomi.workstation.ui.theme.TextPrimary
import com.xiaomi.workstation.ui.theme.TextSecondary
import dev.chrisbanes.haze.HazeState

@Composable
fun MediaWidget(modifier: Modifier = Modifier, hazeState: HazeState? = null) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0.35f) }
    WidgetGlass(modifier = modifier, hazeState = hazeState) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.MusicNote, contentDescription = null, tint = MediaGradientEnd, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Now Playing", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Xiaomi HyperOS 4", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "Evolved Experience", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = TextSecondary)
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(3.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(2.dp)), color = AccentBlue, trackColor = TextSecondary.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { }, colors = IconButtonDefaults.iconButtonColors(contentColor = TextSecondary)) { Icon(Icons.Filled.SkipPrevious, contentDescription = "Previous", modifier = Modifier.size(24.dp)) }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { isPlaying = !isPlaying }, colors = IconButtonDefaults.iconButtonColors(contentColor = TextPrimary)) { Icon(if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow, contentDescription = if (isPlaying) "Pause" else "Play", modifier = Modifier.size(32.dp)) }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { }, colors = IconButtonDefaults.iconButtonColors(contentColor = TextSecondary)) { Icon(Icons.Filled.SkipNext, contentDescription = "Next", modifier = Modifier.size(24.dp)) }
            }
        }
    }
}
