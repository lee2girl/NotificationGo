package com.wq.notificationgo.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.wq.notificationgo.R
import com.wq.notificationgo.activity.NotificationActivity

/**
 * @author lee
 * @since 2024/5/4
 * @desc default notification
 */
private const val COMMON_ID = 100
object NotificationUtil {

    fun showCommonNotification(
        context: Context,
        nm: NotificationManager,
        title: String,
        content: String,
        channelId: String
    ) {
        val build = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        applyChannel(context, nm, channelId)
        nm.notify(COMMON_ID, build.build())
    }

    fun showClickableNotification(
        context: Context,
        nm: NotificationManager,
        title: String,
        content: String,
        channelId: String
    ) {
        val pendingIntent = getPendingIntent(context)
        val build = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        applyChannel(context, nm, channelId)
        nm.notify(COMMON_ID, build.build())
    }

    private fun applyChannel(
        context: Context,
        nm: NotificationManager,
        channelId: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context, nm, channelId)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        context: Context,
        nm: NotificationManager,
        channelId: String
    ): NotificationChannel {
        val name = context.resources.getString(R.string.default_notification)
        val channel = NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.setShowBadge(false)
//        channel.enableLights(false)
//        channel.enableVibration(false)
//        channel.setSound(null, null)
//        channel.setBypassDnd(true)
        nm.createNotificationChannel(channel)
        return channel
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, NotificationActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
}