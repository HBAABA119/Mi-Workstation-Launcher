package com.xiaomi.workstation.data

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.xiaomi.workstation.ui.theme.AppCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LauncherState {

    fun isDefaultLauncher(context: Context): Boolean {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        val resolveInfo = context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo?.activityInfo?.packageName == context.packageName
    }

    fun setAsDefaultLauncher(context: Context) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        context.startActivity(intent)
    }

    fun getDeviceDefaultLauncherPackage(context: Context): String {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }
        val resolveInfo = context.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val pkg = resolveInfo?.activityInfo?.packageName

        // If our launcher is currently default, find the real system launcher
        if (pkg == context.packageName) {
            return detectSystemLauncher()
        }
        return pkg ?: detectSystemLauncher()
    }

    private fun detectSystemLauncher(): String {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()

        return when {
            // Samsung
            brand.contains("samsung") || manufacturer.contains("samsung") ->
                "com.sec.android.app.launcher"

            // Xiaomi / Redmi / POCO
            manufacturer.contains("xiaomi") || manufacturer.contains("redmi") ||
            brand.contains("xiaomi") || brand.contains("redmi") || brand.contains("poco") ->
                "com.miui.home"

            // OnePlus
            manufacturer.contains("oneplus") || brand.contains("oneplus") ->
                "com.ONEPLUS.hydrogen.launcher"

            // Oppo / Realme
            manufacturer.contains("oppo") || brand.contains("oppo") ||
            manufacturer.contains("realme") || brand.contains("realme") ->
                "com.oppo.launcher"

            // Vivo
            manufacturer.contains("vivo") || brand.contains("vivo") ->
                "com.bbk.launcher2"

            // Huawei / Honor
            manufacturer.contains("huawei") || brand.contains("huawei") ||
            manufacturer.contains("honor") || brand.contains("honor") ->
                "com.huawei.android.launcher"

            // Motorola
            manufacturer.contains("motorola") || brand.contains("motorola") || brand.contains("moto") ->
                "com.motorola.launcher3"

            // LG
            manufacturer.contains("lg") || brand.contains("lg") ->
                "com.lge.launcher2"

            // Nokia
            manufacturer.contains("nokia") || brand.contains("nokia") ->
                "com.android.launcher3"

            // Sony
            manufacturer.contains("sony") || brand.contains("sony") ->
                "com.sonyericsson.home"

            // Nothing
            manufacturer.contains("nothing") || brand.contains("nothing") ->
                "com.android.launcher3"

            // Pixel / Stock Android
            brand.contains("google") || manufacturer.contains("google") ->
                "com.google.android.apps.nexuslauncher"

            // Default fallback
            else -> "com.android.launcher3"
        }
    }

    suspend fun loadAllApps(context: Context): List<AppInfo> = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                intent,
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
        }

        resolveInfos.mapNotNull { resolveInfo ->
            val activityInfo = resolveInfo.activityInfo ?: return@mapNotNull null
            val appLabel = resolveInfo.loadLabel(packageManager).toString()

            AppInfo(
                label = appLabel,
                packageName = activityInfo.packageName,
                activityName = activityInfo.name,
                icon = try {
                    resolveInfo.loadIcon(packageManager)
                } catch (e: Exception) {
                    null
                },
                category = AppCategory.categorize(activityInfo.packageName),
                isSystemApp = (activityInfo.applicationInfo.flags and
                        android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
            )
        }.sortedBy { it.label.lowercase() }
    }

    fun getDefaultDockApps(): List<String> {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()

        return when {
            brand.contains("samsung") || manufacturer.contains("samsung") -> listOf(
                "com.android.dialer",
                "com.samsung.android.messaging",
                "com.sec.android.app.sbrowser",
                "com.android.camera",
                "com.samsung.android.gallery"
            )
            manufacturer.contains("xiaomi") || brand.contains("xiaomi") || brand.contains("redmi") -> listOf(
                "com.android.dialer",
                "com.android.mms",
                "com.android.chrome",
                "com.android.camera",
                "com.miui.gallery"
            )
            manufacturer.contains("oppo") || brand.contains("oppo") ||
            manufacturer.contains("realme") || brand.contains("realme") -> listOf(
                "com.android.dialer",
                "com.android.mms",
                "com.android.chrome",
                "com.android.camera",
                "com.coloros.gallery3d"
            )
            manufacturer.contains("vivo") || brand.contains("vivo") -> listOf(
                "com.android.dialer",
                "com.android.mms",
                "com.android.chrome",
                "com.android.camera",
                "com.bbk.gallery"
            )
            else -> listOf(
                "com.android.dialer",
                "com.android.mms",
                "com.android.chrome",
                "com.android.camera",
                "com.google.android.apps.photos"
            )
        }
    }
}

enum class LauncherTarget {
    WORKSTATION,
    DEFAULT
}
