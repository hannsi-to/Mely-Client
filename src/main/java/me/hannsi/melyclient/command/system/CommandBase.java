package me.hannsi.melyclient.command.system;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.chat.ChatUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;

public class CommandBase implements InterfaceMinecraft {
    protected String name;
    protected String[] commands;

    public CommandBase(String name) {
        this.name = name;
        this.commands = new String[]{""};
    }

    public CommandBase(String name, String[] commands) {
        this.name = name;
        this.commands = commands;
    }

    public static void sendMessage(String message, boolean error) {
        ChatUtil.sendMessage(MelyClient.commandManager.getModPrefix() + " " + ChatFormatting.RESET + (error ? ChatFormatting.RED : "") + message);
    }

    public static void debugAndSendErrorMessage(String errorMessage) {
        sendMessage(errorMessage, true);
        new DebugLog(errorMessage, DebugLevel.ERROR);
    }

    public static String getCommandPrefix() {
        return MelyClient.commandManager.getPrefix();
    }

    public void execute(String[] arguments) {

    }

    public String getName() {
        return this.name;
    }

    public String[] getCommands() {
        return this.commands;
    }
}
