package com.xiaomi.workstation.service

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.xiaomi.workstation.data.LauncherState
import com.xiaomi.workstation.data.PrefsManager
import com.xiaomi.workstation.ui.LauncherActivity

class WorkstationTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()

        val prefs = PrefsManager.getInstance(applicationContext)
        val isActive = LauncherState.isDefaultLauncher(applicationContext)

        if (isActive) {
            // Currently using Workstation, switch to system default
            prefs.isWorkstationActive = false
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivityAndCollapse(intent)
        } else {
            // Currently using system launcher, switch to Workstation
            prefs.isWorkstationActive = true
            val intent = Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName(applicationContext, LauncherActivity::class.java)
                addCategory(Intent.CATEGORY_HOME)
                addCategory(Intent.CATEGORY_LAUNCHER)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivityAndCollapse(intent)
        }

        updateTileState()
    }

    private fun updateTileState() {
        val tile = qsTile ?: return
        val isActive = LauncherState.isDefaultLauncher(applicationContext)

        tile.state = if (isActive) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.label = "Workstation"
        tile.contentDescription = if (isActive)
            "Workstation active - Tap to switch to default launcher"
        else
            "Tap to enable Workstation mode"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.stateDescription = if (isActive) "Active" else "Inactive"
        }

        tile.updateTile()
    }

    companion object {
        const val ACTION_TOGGLE_LAUNCHER = "com.xiaomi.workstation.ACTION_TOGGLE_LAUNCHER"
    }
}
