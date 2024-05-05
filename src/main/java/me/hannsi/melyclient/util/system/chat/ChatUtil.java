package me.hannsi.melyclient.util.system.chat;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatUtil implements InterfaceMinecraft {
    public static int maxSaveLog = 100;
    public static List<ChatData> messageLog = new ArrayList<>(maxSaveLog);

    public static void sendChatData(ChatData chatData) {
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
        messageLog.add(new ChatData(textComponentString));
    }

    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }
}
