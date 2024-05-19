package com.wq.notificationgo.util

import android.content.Context
import android.widget.Toast
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.wq.notificationgo.R

object PermissionUtil {

    fun requestNotificationPermission(context: Context, callback: PermissionCallback) {
        XXPermissions.with(context)
            // 申请单个权限
            .permission(Permission.POST_NOTIFICATIONS)
            .request(object : OnPermissionCallback {

                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (!allGranted) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.permission_grant_partially),
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    callback.onGrant()
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.permission_forbidden),
                            Toast.LENGTH_SHORT
                        ).show()
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(context, permissions)
                    } else {
                        callback.onDenied()
                    }
                }
            })
    }

    interface PermissionCallback {
        fun onGrant()

        fun onDenied()
    }


}