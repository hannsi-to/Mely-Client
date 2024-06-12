package me.hannsi.melyclient.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.command.commands.BindCommand;
import me.hannsi.melyclient.command.commands.HelpCommand;
import me.hannsi.melyclient.command.commands.PrefixCommand;
import me.hannsi.melyclient.command.system.CommandBase;
import me.hannsi.melyclient.util.system.ListUtil;
import me.hannsi.melyclient.util.system.StringUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final String defaultPrefix = "*";
    private String modPrefix;
    private String prefix;
    private List<CommandBase> commandBases;

    public CommandManager() {
        load();
    }

    public void load() {
        modPrefix = MelyClient.MOD_PREFIX + ChatFormatting.AQUA + "<Command>" + ChatFormatting.RESET;
        prefix = defaultPrefix;
        loadCommandBases();
    }

    public void unLoad() {
        MelyClient.commandManager = null;
    }

    public void loadCommandBases() {
        commandBases = new ArrayList<>();
        register(new PrefixCommand());
        register(new HelpCommand());
        register(new BindCommand());
    }

    public void register(CommandBase commandBase) {
        commandBases.add(commandBase);
    }

    public void executeCommand(String command) {
        String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String name = parts[0].substring(1);
        String[] args = ListUtil.removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null) {
                continue;
            }
            args[i] = StringUtil.strip(args[i], "\"");
        }

        for (CommandBase commandBase : this.commandBases) {
            if (!commandBase.getName().equalsIgnoreCase(name)) {
                continue;
            }
            commandBase.execute(parts);
            return;
        }

        String errorMessage = "Unknown command. try 'help' for a list of commands.";
        CommandBase.sendMessage(errorMessage, true);
        new DebugLog(errorMessage, DebugLevel.ERROR);
    }

    public CommandBase getCommandByName(String name) {
        for (CommandBase command : this.commandBases) {
            if (!command.getName().equals(name)) {
                continue;
            }
            return command;
        }
        return null;
    }

    public String getModPrefix() {
        return this.modPrefix;
    }

    public void setModPrefix(String modPrefix) {
        this.modPrefix = modPrefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public List<CommandBase> getCommandBases() {
        return commandBases;
    }

    public void setCommandBases(List<CommandBase> commandBases) {
        this.commandBases = commandBases;
    }
}
