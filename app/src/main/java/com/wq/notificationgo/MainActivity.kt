package com.wq.notificationgo

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.wq.notificationgo.activity.BaseActivity
import com.wq.notificationgo.core.AdvancedNotificationUtil
import com.wq.notificationgo.core.NotificationUtil
import com.wq.notificationgo.databinding.ActivityMainBinding
import com.wq.notificationgo.util.PermissionUtil
import com.wq.notificationgo.util.ScreenUtil


const val IMPORTANT_ID = 9000
const val KEY_NOTIFICATION_ID = "key_notification_id"
const val KEY_FROM = "key_from"
const val FROM_NORMAL_NOTIFICATION = 1
const val FROM_FULLSCREEN_NOTIFICATION = 2
const val FROM_CLICKABLE_NOTIFICATION = 3
private const val TAG = "TAG_MainActivity"

class MainActivity : BaseActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer

    private var notificationId = IMPORTANT_ID

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
                PermissionUtil.requestNotificationPermission(this,
                    object : PermissionUtil.PermissionCallback {
                        override fun onGrant() {
                            showCommonNotification()
                        }

                        override fun onDenied() {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.permission_denied),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }

            R.id.tvCommonClickable -> {
                PermissionUtil.requestNotificationPermission(this,
                    object : PermissionUtil.PermissionCallback {
                        override fun onGrant() {
                            showClickableNotification()
                        }

                        override fun onDenied() {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.permission_denied),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }

            R.id.tvImportant -> {
                PermissionUtil.requestNotificationPermission(this,
                    object : PermissionUtil.PermissionCallback {
                        override fun onGrant() {
                            showImportantNotification()
                        }

                        override fun onDenied() {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.permission_denied),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }

            R.id.tvFullscreen -> {
                PermissionUtil.requestNotificationPermission(this,
                    object : PermissionUtil.PermissionCallback {
                        override fun onGrant() {
                            countDownTimer.start()
                        }

                        override fun onDenied() {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.permission_denied),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }
        }
    }

    private fun showCommonNotification(){
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

    private fun showClickableNotification(){
        val title = resources.getString(R.string.clickable_notification)
        val content = resources.getString(R.string.clickable_content)
        NotificationUtil.showClickableNotification(
            this,
            mNotificationManager,
            title,
            content,
            "clickable_common_id"
        )
    }
    private fun showImportantNotification() {
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