package com.xiaomi.workstation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class MiWorkstationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MiWorkstationApp
            private set
    }
}
