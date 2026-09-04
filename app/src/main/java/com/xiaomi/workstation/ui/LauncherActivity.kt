package com.xiaomi.workstation.ui

import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.xiaomi.workstation.data.AppCategory
import com.xiaomi.workstation.data.AppInfo
import com.xiaomi.workstation.data.LauncherState
import com.xiaomi.workstation.data.PrefsManager
import com.xiaomi.workstation.service.WorkstationTileService
import com.xiaomi.workstation.ui.components.AppGrid
import com.xiaomi.workstation.ui.components.CalendarWidget
import com.xiaomi.workstation.ui.components.CategoryTabs
import com.xiaomi.workstation.ui.components.ClockWidget
import com.xiaomi.workstation.ui.components.Dock
import com.xiaomi.workstation.ui.components.SearchBar
import com.xiaomi.workstation.ui.components.WeatherWidget
import com.xiaomi.workstation.ui.theme.DeepNavy
import com.xiaomi.workstation.ui.theme.MiWorkstationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LauncherActivity : ComponentActivity() {

    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        PrefsManager.getInstance(this).isWorkstationActive = true

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == WorkstationTileService.ACTION_TOGGLE_LAUNCHER) {
                    switchToDefaultLauncher()
                }
            }
        }
        val filter = IntentFilter(WorkstationTileService.ACTION_TOGGLE_LAUNCHER)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }

        setContent {
            MiWorkstationTheme {
                LauncherScreen(
                    onOpenDrawer = { openAppDrawer() },
                    onOpenSettings = { openSettings() },
                    onSwitchLauncher = { switchToDefaultLauncher() }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            receiver?.let { unregisterReceiver(it) }
        } catch (_: Exception) {}
        PrefsManager.getInstance(this).isWorkstationActive = false
    }

    private fun openAppDrawer() {
        val intent = Intent(this, AppDrawerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun switchToDefaultLauncher() {
        PrefsManager.getInstance(this).isWorkstationActive = false
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }

    @Composable
    private fun LauncherScreen(
        onOpenDrawer: () -> Unit,
        onOpenSettings: () -> Unit,
        onSwitchLauncher: () -> Unit
    ) {
        val context = LocalContext.current
        var allApps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }
        var searchQuery by remember { mutableStateOf("") }
        var selectedCategory by remember { mutableStateOf<AppCategory?>(null) }
        val prefs = remember { PrefsManager.getInstance(context) }

        val screenWidthDp = context.resources.displayMetrics.widthPixels /
                LocalDensity.current.density
        val gridColumns = when {
            screenWidthDp > 900 -> 8
            screenWidthDp > 600 -> 6
            screenWidthDp > 400 -> 4
            else -> 3
        }

        LaunchedEffect(Unit) {
            allApps = withContext(Dispatchers.IO) {
                LauncherState.loadAllApps(context)
            }
        }

        // Load wallpaper properly - convert any drawable to bitmap
        val wallpaperBitmap = remember {
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val drawable = wallpaperManager.drawable
                drawable?.let { drawableToBitmap(it) }
            } catch (e: Exception) {
                null
            }
        }

        val filteredApps = remember(allApps, searchQuery, selectedCategory) {
            allApps.filter { app ->
                val matchesSearch = searchQuery.isEmpty() ||
                        app.label.contains(searchQuery, ignoreCase = true)
                val matchesCategory = selectedCategory == null || app.category == selectedCategory
                matchesSearch && matchesCategory
            }
        }

        val dockApps = remember(allApps, prefs) {
            val savedDock = prefs.dockApps
            if (savedDock.isNotEmpty()) {
                savedDock.mapNotNull { pkg -> allApps.find { it.packageName == pkg } }
                    .take(AppInfo.MAX_DOCK_APPS)
            } else {
                val defaultPackages = LauncherState.getDefaultDockApps()
                defaultPackages.mapNotNull { pkg -> allApps.find { it.packageName == pkg } }
                    .take(5)
            }
        }

        val recentApps = remember(allApps, prefs) {
            prefs.recentApps.mapNotNull { pkg ->
                allApps.find { it.packageName == pkg }
            }.take(3)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepNavy)
        ) {
            // Wallpaper with blur overlay
            wallpaperBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(2.dp),
                    alpha = 0.5f
                )
            }

            // Dark gradient overlay for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x660A1628),  // 40% navy top
                                Color(0x990A1628),  // 60% navy middle
                                Color(0xCC0A1628)   // 80% navy bottom
                            )
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Widget row
                if (screenWidthDp > 500) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        ClockWidget(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                        WeatherWidget(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                        CalendarWidget(modifier = Modifier.weight(1f))
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        ClockWidget(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                        WeatherWidget(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search bar
                SearchBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onQueryChange = { searchQuery = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category tabs
                CategoryTabs(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // App grid
                AppGrid(
                    apps = filteredApps,
                    columns = gridColumns,
                    onAppClick = { app ->
                        launchApp(app)
                        prefs.addRecentApp(app.packageName)
                    },
                    modifier = Modifier.weight(1f)
                )

                // Dock
                Dock(
                    dockApps = dockApps,
                    recentApps = recentApps,
                    onAppClick = { app ->
                        launchApp(app)
                        prefs.addRecentApp(app.packageName)
                    },
                    onDrawerClick = onOpenDrawer,
                    onSettingsClick = onOpenSettings
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        return when (drawable) {
            is BitmapDrawable -> drawable.bitmap
            is android.graphics.drawable.ColorDrawable -> {
                val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, 1, 1)
                drawable.draw(canvas)
                bitmap
            }
            else -> {
                val width = drawable.intrinsicWidth.coerceAtLeast(1)
                val height = drawable.intrinsicHeight.coerceAtLeast(1)
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, width, height)
                drawable.draw(canvas)
                bitmap
            }
        }
    }

    private fun launchApp(app: AppInfo) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            component = app.componentName
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    Intent.FLAG_ACTIVITY_NO_ANIMATION
        }
        startActivity(intent)
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0)
    }
}
