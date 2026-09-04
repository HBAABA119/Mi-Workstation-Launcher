package com.xiaomi.workstation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppDrawerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiWorkstationTheme {
                AppDrawerScreen(
                    onAppClick = { app ->
                        launchApp(app)
                        PrefsManager.getInstance(this).addRecentApp(app.packageName)
                    },
                    onClose = { finish() }
                )
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

@Composable
private fun AppDrawerScreen(
    onAppClick: (AppInfo) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var allApps by remember { mutableStateOf<List<AppInfo>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<AppCategory?>(null) }

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

    val filteredApps = remember(allApps, searchQuery, selectedCategory) {
        allApps.filter { app ->
            val matchesSearch = searchQuery.isEmpty() ||
                    app.label.contains(searchQuery, ignoreCase = true) ||
                    app.packageName.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == null || app.category == selectedCategory
            matchesSearch && matchesCategory
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            onQueryChange = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategoryTabs(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        AppGrid(
            apps = filteredApps,
            columns = gridColumns,
            onAppClick = onAppClick,
            modifier = Modifier.weight(1f)
        )
    }
}
