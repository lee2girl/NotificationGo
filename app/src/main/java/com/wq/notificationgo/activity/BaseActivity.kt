package com.wq.notificationgo.activity

import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected lateinit var mNotificationManager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::mNotificationManager.isInitialized) {
            mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
    }
}