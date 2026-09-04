package com.xiaomi.workstation.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
import kotlin.math.roundToInt

@Composable
fun AppGrid(
    apps: List<AppInfo>,
    columns: Int = 6,
    onAppClick: (AppInfo) -> Unit,
    onAppMove: (Int, Int) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        itemsIndexed(apps, key = { _, app -> "${app.packageName}/${app.activityName}" }) { index, app ->
            DraggableAppItem(
                app = app,
                onClick = { onAppClick(app) }
            )
        }
    }
}

@Composable
private fun DraggableAppItem(
    app: AppInfo,
    onClick: () -> Unit
) {
    var isDragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .zIndex(if (isDragging) 10f else 0f)
            .offset { IntOffset(dragOffset.x.roundToInt(), dragOffset.y.roundToInt()) }
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { if (!isDragging) onClick() }
            )
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        isDragging = true
                        scale = 1.12f
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragOffset += dragAmount
                    },
                    onDragEnd = {
                        isDragging = false
                        scale = 1f
                        dragOffset = Offset.Zero
                    },
                    onDragCancel = {
                        isDragging = false
                        scale = 1f
                        dragOffset = Offset.Zero
                    }
                )
            }
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        IconGlass(modifier = Modifier.size(56.dp)) {
            Box(contentAlignment = Alignment.Center) {
                app.icon?.let { drawable ->
                    DrawableImage(
                        drawable = drawable,
                        contentDescription = app.label,
                        modifier = Modifier.size(42.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
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
    Image(bitmap = bitmap, contentDescription = contentDescription, modifier = modifier)
}

@Composable
fun CategoryTabs(
    selectedCategory: AppCategory?,
    onCategorySelected: (AppCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(null to "All") + AppCategory.entries.map { it to it.displayName }
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
                        color = if (isSelected) bgColor.copy(alpha = 0.30f) else Color(0x0DFFFFFF),
                        shape = tabShape
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = if (isSelected) TextPrimary else TextSecondary
                )
            }
        }
    }
}
