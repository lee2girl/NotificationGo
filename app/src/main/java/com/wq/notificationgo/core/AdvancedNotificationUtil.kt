package com.wq.notificationgo.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.wq.notificationgo.FROM_FULLSCREEN_NOTIFICATION
import com.wq.notificationgo.FROM_NORMAL_NOTIFICATION
import com.wq.notificationgo.KEY_FROM
import com.wq.notificationgo.KEY_NOTIFICATION_ID
import com.wq.notificationgo.constant.NotificationBehaviorType
import com.wq.notificationgo.service.NotificationService
import com.wq.notificationgo.R
import com.wq.notificationgo.activity.NotificationActivity

object AdvancedNotificationUtil {

    /**targetSdkVersion>=33 需要动态申请权限*/
    fun showImportantNotification(
        context: Context,
        nm: NotificationManager,
        title: String,
        content: String,
        channelId: String,
        notificationId: Int
    ) {
        val pendingIntent =
            getPendingIntent(context, NotificationBehaviorType.GO_ACTIVITY, notificationId)
        applyChannel(context, nm, channelId, NotificationManager.IMPORTANCE_MAX)
        val build = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)//勿扰模式下没有声音，CATEGORY_MESSAGE也一样
            //锁屏时显示。VISIBILITY_PUBLIC： 显示通知小图标、通知标题、全部内容，
            //VISIBILITY_PRIVATE： 只显示通知小图标、标题，隐藏全部内容
            //VISIBILITY_SECRET： 不显示该条通知
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        nm.notify(notificationId, build.build())
    }

    fun showFullscreenNotification(
        context: Context,
        nm: NotificationManager,
        title: String,
        content: String,
        channelId: String,
        notificationId: Int
    ) {
        val build = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setFullScreenIntent(getFullscreenPendingIntent(context), true)
        applyChannel(context, nm, channelId, NotificationManager.IMPORTANCE_MAX)
        nm.notify(notificationId, build.build())
    }


    private fun applyChannel(
        context: Context,
        nm: NotificationManager,
        channelId: String,
        importance: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context, nm, channelId, importance)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        context: Context,
        nm: NotificationManager,
        channelId: String,
        importance: Int
    ): NotificationChannel {
        val name = context.resources.getString(R.string.importance_notification)
        val channel = NotificationChannel(channelId, name, importance)
        channel.setShowBadge(true)
//        channel.enableLights(false)
//        channel.enableVibration(false)
//        channel.setSound(null, null)
//        channel.setBypassDnd(true)
        nm.createNotificationChannel(channel)
        return channel
    }

    private fun getPendingIntent(
        context: Context,
        @NotificationBehaviorType type: Int,
        notificationId: Int
    ): PendingIntent? {
        return when (type) {
            NotificationBehaviorType.GO_ACTIVITY -> {
                val intent = Intent(
                    context,
                    NotificationActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra(KEY_NOTIFICATION_ID, notificationId)
                intent.putExtra(KEY_FROM, FROM_NORMAL_NOTIFICATION)
                PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT//必须加_CURRENT,否则发现extra内容不变
                )
            }

            NotificationBehaviorType.GO_SERVICE -> {
                val intent = Intent(context, NotificationService::class.java)
                PendingIntent.getService(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

            else -> null
        }
    }

    private fun getFullscreenPendingIntent(context: Context): PendingIntent {
        val intent = Intent(
            context,
            NotificationActivity::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(KEY_FROM, FROM_FULLSCREEN_NOTIFICATION)
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun cancelNotification(nm: NotificationManager, id: Int) {
        nm.cancel(id)
    }
}