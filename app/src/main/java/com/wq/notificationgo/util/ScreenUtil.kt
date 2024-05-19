package com.wq.notificationgo.util

import android.content.Context
import android.os.PowerManager

object ScreenUtil {
    fun wakeupScreen(context: Context) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isInteractive) {
            val wakeUp =
                pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP
                            or PowerManager.FULL_WAKE_LOCK
                            or PowerManager.ON_AFTER_RELEASE,
                    context.javaClass.name
                )
            wakeUp.acquire()
        }
    }
}