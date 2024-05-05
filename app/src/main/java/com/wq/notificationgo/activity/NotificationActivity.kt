package com.wq.notificationgo.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wq.notificationgo.AdvancedNotificationUtil
import com.wq.notificationgo.KEY_NOTIFICATION_ID
import com.wq.notificationgo.databinding.ActivityNotificationBinding
private const val TAG = "TAG_NotificationActivity"
class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(KEY_NOTIFICATION_ID, -1)
        Log.i(TAG,"id = $id")
        cancelNotification(id)
    }

    private fun cancelNotification(notificationId: Int) {
        if (notificationId > 0) {
            AdvancedNotificationUtil.cancelNotification(mNotificationManager, notificationId)
        }
    }

}