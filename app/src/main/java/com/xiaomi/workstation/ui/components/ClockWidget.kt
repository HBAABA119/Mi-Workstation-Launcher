package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.workstation.ui.theme.AccentBlue
import com.xiaomi.workstation.ui.theme.TextPrimary
import com.xiaomi.workstation.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ClockWidget(modifier: Modifier = Modifier) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    val hour = remember(currentTime) {
        SimpleDateFormat("HH", Locale.getDefault()).format(Date(currentTime))
    }
    val minute = remember(currentTime) {
        SimpleDateFormat("mm", Locale.getDefault()).format(Date(currentTime))
    }
    val dateStr = remember(currentTime) {
        SimpleDateFormat("EEE, dd MMMM", Locale.getDefault()).format(Date(currentTime))
    }

    WidgetCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Large time display
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = hour,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Thin,
                    color = TextPrimary,
                    letterSpacing = (-2).sp
                )
                Text(
                    text = ":",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Thin,
                    color = AccentBlue,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
                Text(
                    text = minute,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Thin,
                    color = TextPrimary,
                    letterSpacing = (-2).sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Date
            Text(
                text = dateStr,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary
            )
        }
    }
}
