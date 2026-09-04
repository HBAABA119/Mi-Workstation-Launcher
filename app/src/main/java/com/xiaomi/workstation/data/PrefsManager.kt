package com.xiaomi.workstation.data

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    var isWorkstationActive: Boolean
        get() = prefs.getBoolean(KEY_WORKSTATION_ACTIVE, false)
        set(value) = prefs.edit().putBoolean(KEY_WORKSTATION_ACTIVE, value).apply()

    var isDefaultLauncher: Boolean
        get() = prefs.getBoolean(KEY_IS_DEFAULT, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_DEFAULT, value).apply()

    var dockApps: Set<String>
        get() = prefs.getStringSet(KEY_DOCK_APPS, emptySet()) ?: emptySet()
        set(value) = prefs.edit().putStringSet(KEY_DOCK_APPS, value).apply()

    var recentApps: List<String>
        get() = prefs.getString(KEY_RECENT_APPS, "")?.split(",")?.filter { it.isNotEmpty() } ?: emptyList()
        set(value) = prefs.edit().putString(KEY_RECENT_APPS, value.joinToString(",")).apply()

    var categoryOrder: List<String>
        get() = prefs.getString(KEY_CATEGORY_ORDER, "")?.split(",")?.filter { it.isNotEmpty() }
            ?: listOf("SOCIAL", "PRODUCTIVITY", "MEDIA", "SYSTEM", "GAMES", "OTHER")
        set(value) = prefs.edit().putString(KEY_CATEGORY_ORDER, value.joinToString(",")).apply()

    var selectedCategory: String
        get() = prefs.getString(KEY_SELECTED_CATEGORY, "ALL") ?: "ALL"
        set(value) = prefs.edit().putString(KEY_SELECTED_CATEGORY, value).apply()

    fun addRecentApp(packageName: String) {
        val recent = recentApps.toMutableList()
        recent.remove(packageName)
        recent.add(0, packageName)
        if (recent.size > MAX_RECENT) {
            recent.removeLast()
        }
        recentApps = recent
    }

    companion object {
        private const val PREFS_NAME = "mi_workstation_prefs"
        private const val KEY_WORKSTATION_ACTIVE = "workstation_active"
        private const val KEY_IS_DEFAULT = "is_default_launcher"
        private const val KEY_DOCK_APPS = "dock_apps"
        private const val KEY_RECENT_APPS = "recent_apps"
        private const val KEY_CATEGORY_ORDER = "category_order"
        private const val KEY_SELECTED_CATEGORY = "selected_category"
        private const val MAX_RECENT = 12

        @Volatile
        private var instance: PrefsManager? = null

        fun getInstance(context: Context): PrefsManager {
            return instance ?: synchronized(this) {
                instance ?: PrefsManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
