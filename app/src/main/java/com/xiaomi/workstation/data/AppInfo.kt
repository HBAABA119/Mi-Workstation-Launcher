package com.xiaomi.workstation.data

import android.content.ComponentName
import android.graphics.drawable.Drawable

data class AppInfo(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable?,
    val category: AppCategory = AppCategory.OTHER,
    val isSystemApp: Boolean = false
) {
    val componentName: ComponentName
        get() = ComponentName(packageName, activityName)

    companion object {
        const val MAX_DOCK_APPS = 5
        const val MAX_RECENT_APPS = 8
    }
}

enum class AppCategory(val displayName: String, val icon: String) {
    SOCIAL("Social", "\uD83D\uDCAC"),
    PRODUCTIVITY("Work", "\uD83D\uDCC5"),
    MEDIA("Media", "\uD83C\uDFA5"),
    SYSTEM("System", "\u2699\uFE0F"),
    GAMES("Games", "\uD83C\uDFAE"),
    SHOPPING("Shopping", "\uD83D\uDED2"),
    UTILITIES("Tools", "\uD83D\uDD27"),
    OTHER("Other", "\u2B50");

    companion object {
        fun categorize(packageName: String): AppCategory {
            val pkg = packageName.lowercase()
            return when {
                // Social - Universal + Samsung specific
                pkg.contains("whatsapp") || pkg.contains("telegram") ||
                pkg.contains("instagram") || pkg.contains("twitter") ||
                pkg.contains("facebook") || pkg.contains("messenger") ||
                pkg.contains("discord") || pkg.contains("signal") ||
                pkg.contains("snapchat") || pkg.contains("reddit") ||
                pkg.contains("viber") || pkg.contains("line") ||
                pkg.contains("wechat") || pkg.contains("qq") ||
                pkg.contains("tiktok") || pkg.contains("threads") ||
                pkg.contains("samsung.android.messaging") -> SOCIAL

                // Productivity
                pkg.contains("docs") || pkg.contains("sheets") ||
                pkg.contains("slides") || pkg.contains("office") ||
                pkg.contains("notion") || pkg.contains("evernote") ||
                pkg.contains("trello") || pkg.contains("asana") ||
                pkg.contains("slack") || pkg.contains("teams") ||
                pkg.contains("zoom") || pkg.contains("meet") ||
                pkg.contains("calendar") || pkg.contains("task") ||
                pkg.contains("todo") || pkg.contains("work") ||
                pkg.contains("onenote") || pkg.contains("outlook") ||
                pkg.contains("google.android.apps.docs") -> PRODUCTIVITY

                // Media
                pkg.contains("youtube") || pkg.contains("netflix") ||
                pkg.contains("spotify") || pkg.contains("music") ||
                pkg.contains("video") || pkg.contains("player") ||
                pkg.contains("photos") || pkg.contains("gallery") ||
                pkg.contains("camera") || pkg.contains("podcast") ||
                pkg.contains("twitch") || pkg.contains("bilibili") ||
                pkg.contains("prime") || pkg.contains("disney") ||
                pkg.contains("hulu") || pkg.contains("hbo") ||
                pkg.contains("samsung.android.gallery") ||
                pkg.contains("com.google.android.apps.photos") ||
                pkg.contains("com.samsung.android.videoplayer") -> MEDIA

                // System - Universal + all OEM launchers
                pkg.contains("settings") || pkg.contains("systemui") ||
                pkg.contains("launcher") || pkg.contains("phone") ||
                pkg.contains("dialer") || pkg.contains("contacts") ||
                pkg.contains("messaging") || pkg.contains("sms") ||
                pkg.contains("calculator") || pkg.contains("clock") ||
                pkg.contains("file") || pkg.contains("explorer") ||
                pkg.contains("security") || pkg.contains("backup") ||
                pkg.contains("update") || pkg.contains("weather") ||
                pkg.contains("notes") || pkg.contains("recorder") ||
                pkg.contains("com.sec.android.app.launcher") ||
                pkg.contains("com.miui.home") ||
                pkg.contains("com.android.launcher") ||
                pkg.contains("com.google.android.apps.nexuslauncher") ||
                pkg.contains("com.samsung.android.dialer") ||
                pkg.contains("com.samsung.android.contacts") ||
                pkg.contains("com.sec.android.app.sbrowser") ||
                pkg.contains("com.samsung.android.app.tips") ||
                pkg.contains("com.samsung.android.vocalassistant") ||
                pkg.contains("com.samsung.android.bixby.agent") ||
                pkg.contains("com.android.settings") ||
                pkg.contains("com.sec.android.app.wallpaper") -> SYSTEM

                // Games
                pkg.contains("game") || pkg.contains("play.games") ||
                pkg.contains("pubg") || pkg.contains("cod") ||
                pkg.contains("genshin") || pkg.contains("mobile.legend") ||
                pkg.contains("freefire") || pkg.contains("roblox") ||
                pkg.contains("minecraft") || pkg.contains("among") ||
                pkg.contains("supercell") || pkg.contains("epicgames") ||
                pkg.contains("com.garena") || pkg.contains("com.activision") ->
                    GAMES

                // Shopping
                pkg.contains("amazon") || pkg.contains("flipkart") ||
                pkg.contains("ebay") || pkg.contains("shopify") ||
                pkg.contains("store") || pkg.contains("mall") ||
                pkg.contains("aliexpress") || pkg.contains("snapdeal") ||
                pkg.contains("samsung.android.spay") ||
                pkg.contains("com.samsung.android.wellbeing") -> SHOPPING

                // Utilities
                pkg.contains("calculator") || pkg.contains("converter") ||
                pkg.contains("flashlight") || pkg.contains("compass") ||
                pkg.contains("measure") || pkg.contains("vpn") ||
                pkg.contains("cleaner") || pkg.contains("scanner") ||
                pkg.contains("translate") || pkg.contains("dictionary") ||
                pkg.contains("samsung.android.app.tips") ||
                pkg.contains("com.samsung.android.themestore") ||
                pkg.contains("arzone") || pkg.contains("smartthings") ->
                    UTILITIES

                else -> OTHER
            }
        }
    }
}
