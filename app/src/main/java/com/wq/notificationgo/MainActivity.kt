package com.wq.notificationgo

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wq.notificationgo.activity.BaseActivity
import com.wq.notificationgo.databinding.ActivityMainBinding


const val IMPORTANT_ID = 1000
const val KEY_NOTIFICATION_ID = "key_notification_id"
private const val TAG = "TAG_MainActivity"
class MainActivity : BaseActivity(),OnClickListener{

    private lateinit var binding: ActivityMainBinding

    private var notificationId = IMPORTANT_ID
    private val idList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.tvCommon.setOnClickListener(this)
        binding.tvCommonClickable.setOnClickListener(this)
        binding.tvImportant.setOnClickListener(this)
        binding.tvFullscreen.setOnClickListener(this)
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
            R.id.tvImportant->{
//                if(isNotificationActive()){
//                    val tip = resources.getString(R.string.deal_important_notification)
//                    Toast.makeText(this,tip, Toast.LENGTH_LONG).show()
//                    return
//                }
                ++notificationId
                val title = resources.getString(R.string.importance_notification)+notificationId
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
            R.id.tvFullscreen ->{
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
        }
    }

    private fun cancelAll(){
        for (id in idList){
            AdvancedNotificationUtil.cancelNotification(mNotificationManager,id)
        }
        idList.clear()
    }

    private fun isNotificationActive():Boolean{
        val activeNotifications = mNotificationManager.activeNotifications
        if (activeNotifications.isEmpty()) return false
        for (item in activeNotifications){
            Log.e(TAG,"id = ${item.id}, isOnGoing = ${item.isOngoing}")
            if (item.isOngoing) return true
        }
        return false
    }
}