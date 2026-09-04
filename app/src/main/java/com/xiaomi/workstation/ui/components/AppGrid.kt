package com.xiaomi.workstation.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.xiaomi.workstation.data.AppCategory
import com.xiaomi.workstation.data.AppInfo
import com.xiaomi.workstation.ui.theme.AccentBlue
import com.xiaomi.workstation.ui.theme.CategoryGames
import com.xiaomi.workstation.ui.theme.CategoryMedia
import com.xiaomi.workstation.ui.theme.CategorySocial
import com.xiaomi.workstation.ui.theme.CategorySystem
import com.xiaomi.workstation.ui.theme.CategoryWork
import com.xiaomi.workstation.ui.theme.TextPrimary
import com.xiaomi.workstation.ui.theme.TextSecondary

@Composable
fun AppGrid(
    apps: List<AppInfo>,
    columns: Int = 6,
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(apps, key = { "${it.packageName}/${it.activityName}" }) { app ->
            AppGridItem(
                app = app,
                onClick = { onAppClick(app) }
            )
        }
    }
}

@Composable
private fun AppGridItem(
    app: AppInfo,
    onClick: () -> Unit
) {
    val iconShape = RoundedCornerShape(18.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        // Icon container with glass effect
        Box(
            modifier = Modifier
                .size(56.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = iconShape,
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .clip(iconShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x40FFFFFF),  // top
                            Color(0x1AFFFFFF)   // bottom
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
                DrawableImage(
                    drawable = drawable,
                    contentDescription = app.label,
                    modifier = Modifier.size(42.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // App label
        Text(
            text = app.label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = TextPrimary.copy(alpha = 0.9f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(64.dp)
        )
    }
}

@Composable
private fun DrawableImage(
    drawable: Drawable,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(drawable) {
        drawable.toBitmap(128, 128).asImageBitmap()
    }
    Image(
        bitmap = bitmap,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun CategoryTabs(
    selectedCategory: AppCategory?,
    onCategorySelected: (AppCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(null to "All") +
            AppCategory.entries.map { it to it.displayName }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { (category, name) ->
            val isSelected = selectedCategory == category
            val bgColor = when (category) {
                AppCategory.SOCIAL -> CategorySocial
                AppCategory.PRODUCTIVITY -> CategoryWork
                AppCategory.MEDIA -> CategoryMedia
                AppCategory.SYSTEM -> CategorySystem
                AppCategory.GAMES -> CategoryGames
                else -> AccentBlue
            }

            val tabShape = RoundedCornerShape(20.dp)

            Box(
                modifier = Modifier
                    .clip(tabShape)
                    .background(
                        if (isSelected) bgColor.copy(alpha = 0.4f)
                        else Color(0x1AFFFFFF)
                    )
                    .border(
                        width = 0.5.dp,
                        color = if (isSelected) bgColor.copy(alpha = 0.6f) else Color(0x1AFFFFFF),
                        shape = tabShape
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${category?.icon ?: "\uD83C\uDFE0"} $name",
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = if (isSelected) TextPrimary else TextSecondary
                )
            }
        }
    }
}
