package me.hannsi.melyclient.command.commands;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.command.system.CommandBase;

public class PrefixCommand extends CommandBase {
    public PrefixCommand() {
        super("prefix", new String[]{"<get/set>", "<{char}/default>"});
    }

    @Override
    public void execute(String[] arguments) {
        String errorMessage;

        if (arguments.length == 1) {
            errorMessage = "Missing argument.";
            debugAndSendErrorMessage(errorMessage);
            return;
        }

        switch (arguments[0]) {
            case "get":
                sendMessage(MelyClient.commandManager.getPrefix() + " is currently set as the prefix.", false);
                break;
            case "set":
                if (arguments[1] == null || arguments[1].isEmpty()) {
                    debugAndSendErrorMessage("No new prefix entered.");
                    break;
                }
                if (arguments[1].equals("default")) {
                    MelyClient.commandManager.setPrefix(MelyClient.commandManager.getDefaultPrefix());
                    sendMessage("Prefix set to " + MelyClient.commandManager.getPrefix(), false);
                } else {
                    MelyClient.commandManager.setPrefix(arguments[1]);
                    sendMessage("Prefix set to " + MelyClient.commandManager.getPrefix(), false);
                }

                break;
            default:
                errorMessage = "The argument " + arguments[0] + " does not exist.";
                debugAndSendErrorMessage(errorMessage);
        }
    }
}
