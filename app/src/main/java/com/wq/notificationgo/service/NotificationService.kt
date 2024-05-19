package com.wq.notificationgo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
private const val TAG="TAG_NotificationService"
class NotificationService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate...")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand...")
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind...")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind...")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy...")
    }
}