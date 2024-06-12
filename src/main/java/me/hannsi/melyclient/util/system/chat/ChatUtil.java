package me.hannsi.melyclient.util.system.chat;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.player.PacketUtil;
import me.hannsi.melyclient.util.system.ListUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatUtil implements InterfaceMinecraft {
    public static int maxSaveLog = 100;
    public static List<ChatData> messageLog = new ArrayList<>(maxSaveLog);
    public static List<ChatData> playerSendMessageLog = new ArrayList<>(maxSaveLog);

    public static List<ChatData> getMessageLog(boolean reverse) {
        List<ChatData> tempMessageLog = messageLog;
        if (reverse) {
            ListUtil.reverse(tempMessageLog);
        }
        return tempMessageLog;
    }

    public static List<ChatData> getPlayerSendMessageLog(boolean reverse) {
        List<ChatData> tempMessageLog = playerSendMessageLog;
        if (reverse) {
            ListUtil.reverse(tempMessageLog);
        }
        return tempMessageLog;
    }

    public static int getMaxSaveLog() {
        return maxSaveLog;
    }

    public static void setMaxSaveLog(int maxSaveLog) {
        ChatUtil.maxSaveLog = maxSaveLog;
    }

    public static void sendMessageChatData(ChatData chatData) {
        String text = chatData.getText();
        TextComponentString textComponentString = chatData.getTextComponentString();

        if (text != null) {
            sendMessage(text);
        }

        if (textComponentString != null) {
            sendMessage(textComponentString);
        }
    }

    public static void sendListMessage(List<String> strings) {
        for (String string : strings) {
            sendMessage(string);
        }
    }

    public static void sendMessages(String... messages) {
        sendListMessage(Arrays.asList(messages));
    }

    public static void sendMessage(String message) {
        sendMessage(new TextComponentString(message));
    }

    public static void sendMessage(TextComponentString textComponentString) {
        if (nullCheck()) {
            return;
        }

        mc.player.sendMessage(textComponentString);
    }

    public static void sendChatMessageChatData(ChatData chatData) {
        String text = chatData.getText();
        TextComponentString textComponentString = chatData.getTextComponentString();

        if (text != null) {
            sendChatMessage(text);
        }

        if (textComponentString != null) {
            sendChatMessage(textComponentString);
        }
    }

    public static void sendListChatMessage(List<String> strings) {
        for (String string : strings) {
            sendChatMessage(string);
        }
    }

    public static void sendChatMessages(String... messages) {
        sendListChatMessage(Arrays.asList(messages));
    }

    public static void sendChatMessage(String message) {
        sendChatMessage(new TextComponentString(message));
    }

    public static void sendChatMessage(TextComponentString textComponentString) {
        if (nullCheck()) {
            new DebugLog("Failed to send chat.\nCause  :  NULL", DebugLevel.ERROR);
            return;
        }

        if (textComponentString.getText().isEmpty()) {
            new DebugLog("Failed to send chat.\nCause  :  Message to be sent is empty.", DebugLevel.ERROR);
            return;
        }

        mc.player.sendChatMessage(textComponentString.getText());
    }

    public static void sendPacketChatMessageChatData(ChatData chatData) {
        String text = chatData.getText();
        TextComponentString textComponentString = chatData.getTextComponentString();

        if (text != null) {
            sendPacketChatMessage(text);
        }

        if (textComponentString != null) {
            sendPacketChatMessage(textComponentString);
        }
    }

    public static void sendPacketListChatMessage(List<String> strings) {
        for (String string : strings) {
            sendPacketChatMessage(string);
        }
    }

    public static void sendPacketChatMessages(String... messages) {
        sendPacketListChatMessage(Arrays.asList(messages));
    }

    public static void sendPacketChatMessage(String message) {
        sendPacketChatMessage(new TextComponentString(message));
    }

    public static void sendPacketChatMessage(TextComponentString textComponentString) {
        if (nullCheck()) {
            new DebugLog("Failed to send chat.\nCause  :  NULL", DebugLevel.ERROR);
            return;
        }

        if (textComponentString.getText().isEmpty()) {
            new DebugLog("Failed to send chat.\nCause  :  Message to be sent is empty.", DebugLevel.ERROR);
            return;
        }

        PacketUtil.sendCPacketChatMessage(textComponentString.getText());
    }

    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }
}
