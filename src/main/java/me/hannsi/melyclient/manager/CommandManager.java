package me.hannsi.melyclient.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.command.system.CommandBase;
import me.hannsi.melyclient.util.system.ListUtil;
import me.hannsi.melyclient.util.system.StringUtil;
import me.hannsi.melyclient.util.system.conversion.PackagePath;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.file.ClassUtil;
import me.hannsi.melyclient.util.system.math.time.TimeCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        Set<Class<? extends CommandBase>> subTypes = ClassUtil.getClassesFormPackage(PackagePath.commands, CommandBase.class);

        new DebugLog("Commands loading...", DebugLevel.DEBUG);
        long tookTime = TimeCalculator.calculate(() -> {
            for (Class<? extends CommandBase> subType : subTypes) {
                CommandBase commandBase = ClassUtil.createInstance(subType);

                if (commandBase != null) {
                    register(commandBase);
                }
                new DebugLog("Loaded command : " + subType.getName(), DebugLevel.DEBUG);
            }
        });
        new DebugLog("Commands took " + tookTime + "ms to load!", DebugLevel.DEBUG);
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
