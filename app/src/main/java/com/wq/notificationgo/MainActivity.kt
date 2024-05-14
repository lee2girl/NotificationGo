package com.wq.notificationgo

import android.app.NotificationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wq.notificationgo.activity.BaseActivity
import com.wq.notificationgo.databinding.ActivityMainBinding


const val IMPORTANT_ID = 1000
const val KEY_NOTIFICATION_ID = "key_notification_id"
const val KEY_FROM = "key_from"
const val FROM_NORMAL_NOTIFICATION = 1
const val FROM_FULLSCREEN_NOTIFICATION = 2
private const val TAG = "TAG_MainActivity"

class MainActivity : BaseActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer

    private var notificationId = IMPORTANT_ID
    private val idList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        countDownTimer = MyCountDownTimer()
    }

    private fun initView() {
        binding.tvCommon.setOnClickListener(this)
        binding.tvCommonClickable.setOnClickListener(this)
        binding.tvImportant.setOnClickListener(this)
        binding.tvFullscreen.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tvCommon -> {
                val title = resources.getString(R.string.common_notification)
                val content = resources.getString(R.string.common_content)
                NotificationUtil.showCommonNotification(
                    this,
                    mNotificationManager,
                    title,
                    content,
                    "common_id"
                )
            }

            R.id.tvCommonClickable -> {
                val title = resources.getString(R.string.clickable_notification)
                val content = resources.getString(R.string.clickable_content)
                NotificationUtil.showClickableNotification(
                    this,
                    mNotificationManager,
                    title,
                    content,
                    "common_id"
                )
            }

            R.id.tvImportant -> {
                ++notificationId
                val title = resources.getString(R.string.importance_notification) + notificationId
                val content = resources.getString(R.string.importance_content)
                AdvancedNotificationUtil.showImportantNotification(
                    this,
                    mNotificationManager,
                    title,
                    content,
                    "important_id",
                    notificationId
                )
                idList.add(notificationId)
            }

            R.id.tvFullscreen -> {
                countDownTimer.start()
            }
        }
    }

    private fun showFullScreenNotification() {
        val title = resources.getString(R.string.fullscreen_notification)
        val content = resources.getString(R.string.fullscreen_content)
        AdvancedNotificationUtil.showFullscreenNotification(
            this,
            mNotificationManager,
            title,
            content,
            "fullscreen_id",
            ++notificationId
        )
        idList.add(notificationId)
    }

    private fun cancelAll() {
        for (id in idList) {
            AdvancedNotificationUtil.cancelNotification(mNotificationManager, id)
        }
        idList.clear()
    }

    private fun isNotificationActive(): Boolean {
        val activeNotifications = mNotificationManager.activeNotifications
        if (activeNotifications.isEmpty()) return false
        for (item in activeNotifications) {
            Log.e(TAG, "id = ${item.id}, isOnGoing = ${item.isOngoing}")
            if (item.isOngoing) return true
        }
        return false
    }

    inner class MyCountDownTimer : CountDownTimer(5000L, 1000L) {
        override fun onTick(mill: Long) {
            binding.tvFullscreen.isEnabled = false
            binding.tvFullscreen.text = "${mill / 1000}"
        }

        override fun onFinish() {
            binding.tvFullscreen.isEnabled = true
            binding.tvFullscreen.text =
                this@MainActivity.resources.getString(R.string.fullscreen_notification)
            showFullScreenNotification()
            ScreenUtil.wakeupScreen(this@MainActivity)
        }

    }
}