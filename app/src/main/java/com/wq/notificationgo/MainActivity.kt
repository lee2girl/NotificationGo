package com.wq.notificationgo

import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.wq.notificationgo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),OnClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var mNotificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initView()
    }

    private fun initView() {
        binding.tvCommon.setOnClickListener(this)
        binding.tvCommonClickable.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
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
            R.id.tvCommonClickable ->{
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

        }
    }
}