package me.hannsi.melyclient.notification;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.math.animation.Easing;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;

public class NotificationData {
    public NotificationType notificationType;
    public String title;
    public String description;
    public DebugLevel debugLevel;
    public float x;
    public float y;
    public float minWidth;
    public long count;
    public boolean firstTime;
    public boolean secondTime;
    public boolean thirdTime;
    public TimerUtil timerUtil;
    public long startTime;
    public long updateTime;
    public EasingUtil circleEasingIn;
    public EasingUtil circleEasingOut;
    public EasingUtil timeBarEasing;

    public NotificationData(NotificationType notificationType, String title, String description, DebugLevel debugLevel, float x, float y, long count) {
        this.notificationType = notificationType;
        this.title = title;
        this.description = description;
        this.debugLevel = debugLevel;
        this.x = x;
        this.y = y;
        this.minWidth = 100f;
        this.count = count;
        this.firstTime = true;
        this.secondTime = true;
        this.thirdTime = true;
        this.timerUtil = new TimerUtil();
        this.startTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis() - startTime;
        this.circleEasingIn = new EasingUtil(Easing.easeInOutSine);
        this.circleEasingOut = new EasingUtil(Easing.easeInOutSine);
        this.timeBarEasing = new EasingUtil(Easing.easeInOutSine);
    }

    public void reset() {
        timerUtil.reset();
        circleEasingIn.reset();
        circleEasingOut.reset();
        circleEasingOut.setReverse(true);
        timeBarEasing.reset();
        timeBarEasing.setReverse(true);
        setFirstTime(true);
        setSecondTime(true);
        setThirdTime(true);
    }

    public boolean isThirdTime() {
        return thirdTime;
    }

    public void setThirdTime(boolean thirdTime) {
        this.thirdTime = thirdTime;
    }

    public boolean isSecondTime() {
        return secondTime;
    }

    public void setSecondTime(boolean secondTime) {
        this.secondTime = secondTime;
    }

    public EasingUtil getTimeBarEasing() {
        return timeBarEasing;
    }

    public void setTimeBarEasing(EasingUtil timeBarEasing) {
        this.timeBarEasing = timeBarEasing;
    }

    public EasingUtil getCircleEasingOut() {
        return circleEasingOut;
    }

    public void setCircleEasingOut(EasingUtil circleEasingOut) {
        this.circleEasingOut = circleEasingOut;
    }

    public float getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public EasingUtil getCircleEasingIn() {
        return circleEasingIn;
    }

    public void setCircleEasingIn(EasingUtil circleEasingIn) {
        this.circleEasingIn = circleEasingIn;
    }

    public void upDate() {
        this.updateTime = System.currentTimeMillis() - startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public TimerUtil getTimerUtil() {
        return timerUtil;
    }

    public void setTimerUtil(TimerUtil timerUtil) {
        this.timerUtil = timerUtil;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DebugLevel getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(DebugLevel debugLevel) {
        this.debugLevel = debugLevel;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
