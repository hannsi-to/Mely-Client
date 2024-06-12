package me.hannsi.melyclient.command.commands;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.command.system.CommandBase;
import me.hannsi.melyclient.util.system.StringUtil;

public class HelpCommand extends CommandBase {
    public HelpCommand() {
        super("help", new String[]{"<{module}/all>"});
    }

    @Override
    public void execute(String[] arguments) {
        String errorMessage;

        if (arguments.length == 1) {
            errorMessage = "Missing argument.";
            debugAndSendErrorMessage(errorMessage);
            return;
        }

        if (arguments[0].equals("all")) {
            for (CommandBase command : MelyClient.commandManager.getCommandBases()) {
                sendMessage(command.getName() + " " + StringUtil.linkList(command.getCommands(), " "), false);
            }
            return;
        } else if (!arguments[0].isEmpty()) {
            for (CommandBase command : MelyClient.commandManager.getCommandBases()) {
                if (command.getName().equals(arguments[0])) {
                    sendMessage(command.getName() + " " + StringUtil.linkList(command.getCommands(), " "), false);
                    return;
                }
            }

            errorMessage = "Command " + arguments[0] + " does not exist.";
            debugAndSendErrorMessage(errorMessage);
        } else {
            errorMessage = "Missing argument.";
            debugAndSendErrorMessage(errorMessage);
        }

        super.execute(arguments);
    }
}
