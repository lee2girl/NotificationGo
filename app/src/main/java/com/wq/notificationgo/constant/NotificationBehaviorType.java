package com.wq.notificationgo.constant;

import androidx.annotation.IntDef;

@IntDef({NotificationBehaviorType.GO_ACTIVITY, NotificationBehaviorType.GO_SERVICE})
public @interface NotificationBehaviorType {
    int GO_ACTIVITY = 1;
    int GO_SERVICE = 2;
}
