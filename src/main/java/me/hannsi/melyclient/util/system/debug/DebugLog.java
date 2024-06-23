package me.hannsi.melyclient.util.system.debug;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.system.chat.ChatData;

public class DebugLog {
    public DebugType debugType;
    public Exception exception;
    public String debugText;
    public DebugLevel debugLevel;
    public ChatData chatData;

    public DebugLog(Exception exception, DebugLevel debugLevel) {
        this.debugType = DebugType.EXCEPTION;
        this.exception = exception;
        this.debugText = null;
        this.debugLevel = debugLevel;
        this.chatData = new ChatData(exception.getMessage());

        logEvent(debugType, exception, debugText, debugLevel, chatData);
    }

    public DebugLog(String debugText, DebugLevel debugLevel) {
        this.debugType = DebugType.TEXT;
        this.exception = null;
        this.debugText = debugText;
        this.debugLevel = debugLevel;
        this.chatData = new ChatData(debugText);

        logEvent(debugType, null, debugText, debugLevel, chatData);
    }

    public static void logEvent(DebugType debugType, Exception exception, String debugText, DebugLevel debugLevel, ChatData chatData) {
        String title = debugLevel.getDisplay();
        String description = "";

        if (debugType == DebugType.EXCEPTION) {
            description = exception.getClass().getName();
        } else if (debugType == DebugType.TEXT) {
            description = debugText;
        }

        switch (debugLevel) {
            case DEBUG:
                MelyClient.logger.debug(chatData.getText());
                MelyClient.notificationManager.addNotification(title, description, debugLevel);
                break;
            case INFO:
                MelyClient.logger.info(chatData.getText());
                MelyClient.notificationManager.addNotification(title, description, debugLevel);
                break;
            case ERROR:
                MelyClient.logger.error(chatData.getText());
                MelyClient.notificationManager.addNotification(title, description, debugLevel);
                break;
            case WARNING:
                MelyClient.logger.warn(chatData.getText());
                MelyClient.notificationManager.addNotification(title, description, debugLevel);
                break;
        }
    }

    public DebugLevel getErrorLevel() {
        return debugLevel;
    }

    public void setErrorLevel(DebugLevel debugLevel) {
        this.debugLevel = debugLevel;
    }

    public ChatData getChatData() {
        return chatData;
    }

    public void setChatData(ChatData chatData) {
        this.chatData = chatData;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
