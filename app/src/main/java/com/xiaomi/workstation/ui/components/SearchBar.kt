package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.workstation.ui.theme.TextSecondary
import com.xiaomi.workstation.ui.theme.TextTertiary
import dev.chrisbanes.haze.HazeState

@Composable
fun SearchBar(modifier: Modifier = Modifier, hazeState: HazeState? = null, onQueryChange: (String) -> Unit = {}) {
    var query by remember { mutableStateOf("") }
    LiquidGlass(modifier = modifier.fillMaxWidth().height(44.dp), hazeState = hazeState, cornerRadius = 24.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                value = query,
                onValueChange = { query = it; onQueryChange(it) },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal, color = TextSecondary),
                singleLine = true,
                cursorBrush = SolidColor(TextSecondary),
                decorationBox = { inner ->
                    if (query.isEmpty()) Text(text = "Search apps, contacts, settings...", fontSize = 13.sp, fontWeight = FontWeight.Normal, color = TextTertiary)
                    inner()
                }
            )
        }
    }
}
