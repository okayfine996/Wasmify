package com.github.okayfine996.wasmify.notify;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class Notifier {

    public static void notifyInfo(@Nullable Project project,
                                  String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("WasmNotificationGroup")
                .createNotification(content, NotificationType.INFORMATION)
                .notify(project);
    }

    public static void notifyError(@Nullable Project project,
                                   String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("WasmNotificationGroup")
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }

}
