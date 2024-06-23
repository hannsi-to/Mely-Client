package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.notification.NotificationData;
import me.hannsi.melyclient.notification.NotificationType;
import me.hannsi.melyclient.util.system.debug.DebugLevel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    public NotificationType notificationType;
    public List<NotificationData> notificationDataList;
    public long notificationCount;

    public NotificationManager() {
        load();
    }

    public void load() {
        notificationType = NotificationType.Type1;
        notificationDataList = new CopyOnWriteArrayList<>();
        notificationCount = 0;
    }

    public void addNotification(String title, String description, DebugLevel debugLevel) {
        notificationDataList.add(new NotificationData(notificationType, title, description, debugLevel, 0, 0, notificationCount));
        notificationCount++;
    }

    public void removeNotification(NotificationData notificationDat) {
        notificationDataList.remove(notificationDat);
        if (notificationDataList.isEmpty()) {
            notificationCount = 0;
        }
    }

    public void unLoad() {
        MelyClient.notificationManager = null;
    }

    public List<NotificationData> getNotificationDataList() {
        return notificationDataList;
    }

    public void setNotificationDataList(List<NotificationData> notificationDataList) {
        this.notificationDataList = notificationDataList;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
