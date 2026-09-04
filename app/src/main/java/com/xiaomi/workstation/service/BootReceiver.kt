package com.xiaomi.workstation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.xiaomi.workstation.data.PrefsManager

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val prefs = PrefsManager.getInstance(context)
            // If workstation was active before reboot, it will need to be re-enabled
            // The user will need to tap the Quick Settings tile again
            prefs.isWorkstationActive = false
        }
    }
}
