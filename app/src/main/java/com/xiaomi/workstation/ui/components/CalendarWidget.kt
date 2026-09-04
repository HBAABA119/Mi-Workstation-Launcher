package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.workstation.ui.theme.CalendarAccent
import com.xiaomi.workstation.ui.theme.CalendarRed
import com.xiaomi.workstation.ui.theme.TextPrimary
import com.xiaomi.workstation.ui.theme.TextSecondary
import java.util.Calendar

@Composable
fun CalendarWidget(modifier: Modifier = Modifier) {
    val calendar = remember { Calendar.getInstance() }
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = remember {
        val monthNames = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        monthNames[calendar.get(Calendar.MONTH)]
    }
    val dayOfWeek = remember {
        val days = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
        days[calendar.get(Calendar.DAY_OF_WEEK) - 1]
    }

    WidgetCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Day of week header
            Text(
                text = dayOfWeek,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = CalendarRed,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Large day number
            Text(
                text = "$currentDay",
                fontSize = 40.sp,
                fontWeight = FontWeight.Thin,
                color = TextPrimary,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Month and year
            Text(
                text = "$currentMonth",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Events placeholder
            Text(
                text = "No events today",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary.copy(alpha = 0.6f)
            )
        }
    }
}
