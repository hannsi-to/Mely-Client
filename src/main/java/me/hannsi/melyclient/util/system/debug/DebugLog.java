package me.hannsi.melyclient.util.system.debug;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.system.chat.ChatData;

public class DebugLog {
    public Exception exception;
    public DebugLevel debugLevel;
    public ChatData chatData;

    public DebugLog(Exception exception, DebugLevel debugLevel) {
        this.exception = exception;
        this.debugLevel = debugLevel;
        this.chatData = new ChatData(exception.getMessage());

        logEvent(exception, debugLevel,chatData);
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

    public static void logEvent(Exception exception, DebugLevel debugLevel, ChatData chatData){
        switch (debugLevel){
            case DEBUG:
                MelyClient.logger.debug(exception);
                break;
            case INFO:
                MelyClient.logger.info(exception);
                break;
            case ERROR:
                MelyClient.logger.error(exception);
                break;
            case WARNING:
                MelyClient.logger.warn(exception);
                break;
        }
    }
}
