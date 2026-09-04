package com.xiaomi.workstation.ui

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.xiaomi.workstation.data.AppCategory
import com.xiaomi.workstation.data.AppInfo
import com.xiaomi.workstation.data.LauncherState
import com.xiaomi.workstation.data.PrefsManager
import com.xiaomi.workstation.ui.components.AppGrid
import com.xiaomi.workstation.ui.components.CategoryTabs
import com.xiaomi.workstation.ui.components.SearchBar
import com.xiaomi.workstation.ui.theme.DeepNavy
import com.xiaomi.workstation.ui.theme.MiWorkstationTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppDrawerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)
        setContent {
            MiWorkstationTheme {
                AppDrawerScreen(onAppClick = { app ->
                    launchApp(app)
                    PrefsManager.getInstance(this).addRecentApp(app.packageName)
                }, onClose = { finish() })
            }
        }
    }
    private fun launchApp(app: AppInfo) {
        val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER); component = app.componentName; flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_NO_ANIMATION }
        startActivity(intent)
        @Suppress("DEPRECATION") overridePendingTransition(0, 0)
    }
}

@Composable
private fun AppDrawerScreen(onAppClick: (AppInfo) -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current
    val hazeState = rememberHazeState()
    val allApps = remember { mutableStateListOf<AppInfo>() }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<AppCategory?>(null) }
    val screenWidthDp = context.resources.displayMetrics.widthPixels / LocalDensity.current.density
    val gridColumns = when {
        screenWidthDp > 900 -> 8
        screenWidthDp > 600 -> 6
        screenWidthDp > 400 -> 4
        else -> 3
    }
    LaunchedEffect(Unit) {
        val loaded = withContext(Dispatchers.IO) { LauncherState.loadAllApps(context) }
        allApps.clear(); allApps.addAll(loaded)
    }
    val wallpaperBitmap = remember {
        try {
            val wm = WallpaperManager.getInstance(context)
            val d = wm.drawable
            d?.let { drawableToBitmap(it) }
        } catch (_: Exception) { null }
    }
    val filteredApps = remember(allApps.toList(), searchQuery, selectedCategory) {
        allApps.filter { app ->
            val matchesSearch = searchQuery.isEmpty() || app.label.contains(searchQuery, ignoreCase = true) || app.packageName.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == null || app.category == selectedCategory
            matchesSearch && matchesCategory
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(DeepNavy)) {
        wallpaperBitmap?.let { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().hazeSource(state = hazeState), alpha = 0.85f)
        }
        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0x660A1628), Color(0xCC0A1628)))))
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(modifier = Modifier.padding(horizontal = 16.dp), hazeState = hazeState, onQueryChange = { searchQuery = it })
            Spacer(modifier = Modifier.height(12.dp))
            CategoryTabs(selectedCategory = selectedCategory, onCategorySelected = { selectedCategory = it })
            Spacer(modifier = Modifier.height(8.dp))
            AppGrid(
                apps = filteredApps,
                columns = gridColumns,
                onAppClick = onAppClick,
                onAppMove = { from, to ->
                    if (searchQuery.isEmpty() && selectedCategory == null) {
                        try { val item = allApps.removeAt(from); allApps.add(to, item) } catch (_: Exception) {}
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun drawableToBitmap(drawable: Drawable): Bitmap? {
    return when (drawable) {
        is BitmapDrawable -> drawable.bitmap
        is android.graphics.drawable.ColorDrawable -> {
            val b = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            drawable.setBounds(0, 0, 1, 1); drawable.draw(c); b
        }
        else -> {
            val w = drawable.intrinsicWidth.coerceAtLeast(1)
            val h = drawable.intrinsicHeight.coerceAtLeast(1)
            val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            drawable.setBounds(0, 0, w, h); drawable.draw(c); b
        }
    }
}
