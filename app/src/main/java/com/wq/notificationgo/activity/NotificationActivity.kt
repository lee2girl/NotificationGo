package com.wq.notificationgo.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.wq.notificationgo.FROM_CLICKABLE_NOTIFICATION
import com.wq.notificationgo.FROM_FULLSCREEN_NOTIFICATION
import com.wq.notificationgo.core.AdvancedNotificationUtil
import com.wq.notificationgo.FROM_NORMAL_NOTIFICATION
import com.wq.notificationgo.KEY_FROM
import com.wq.notificationgo.KEY_NOTIFICATION_ID
import com.wq.notificationgo.R
import com.wq.notificationgo.databinding.ActivityNotificationBinding

private const val TAG = "TAG_NotificationActivity"

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(KEY_NOTIFICATION_ID, -1)
        Log.i(TAG, "id = $id")
        addFlags()
        cancelNotification(id)
        initData()
    }

    private fun cancelNotification(notificationId: Int) {
        if (notificationId > 0) {
            AdvancedNotificationUtil.cancelNotification(mNotificationManager, notificationId)
        }
    }

    private fun initData() {
        val from = intent.getIntExtra(KEY_FROM, -1)
        val msg =
            when (from) {
                FROM_FULLSCREEN_NOTIFICATION -> resources.getString(R.string.from_fullscreen)
                else -> resources.getString(R.string.from_notification)
            }
        binding.tvContent.text = msg
    }

    private fun addFlags() {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }
}