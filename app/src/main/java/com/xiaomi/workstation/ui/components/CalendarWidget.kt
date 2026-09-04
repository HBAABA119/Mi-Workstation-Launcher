package com.xiaomi.workstation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.workstation.ui.theme.AccentBlue
import com.xiaomi.workstation.ui.theme.CalendarAccent
import com.xiaomi.workstation.ui.theme.TextPrimary
import com.xiaomi.workstation.ui.theme.TextSecondary
import com.xiaomi.workstation.ui.theme.TextTertiary
import java.util.Calendar

@Composable
fun CalendarWidget(modifier: Modifier = Modifier) {
    val calendar = remember { Calendar.getInstance() }
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = remember {
        val monthNames = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        monthNames[calendar.get(Calendar.MONTH)]
    }
    val currentYear = calendar.get(Calendar.YEAR)
    val dayOfWeek = remember {
        val days = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        days[calendar.get(Calendar.DAY_OF_WEEK) - 1]
    }

    WidgetCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = dayOfWeek.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = CalendarAccent,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$currentDay",
                fontSize = 44.sp,
                fontWeight = FontWeight.Thin,
                color = TextPrimary,
                letterSpacing = (-1).sp
            )

            Text(
                text = "$currentMonth $currentYear",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "No events today",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = TextTertiary
            )
        }
    }
}
