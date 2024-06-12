package me.hannsi.melyclient.command.commands;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.command.system.CommandBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Bind;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.conversion.Keyboard;

public class BindCommand extends CommandBase {
    public BindCommand() {
        super("bind", new String[]{"<module>", "<set/get>", "<key/none>"});
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(String[] arguments) {
        String errorMessage = "";

        if (arguments.length == 1) {
            errorMessage = "Missing argument.";
            debugAndSendErrorMessage(errorMessage);
            return;
        }

        if (!arguments[0].isEmpty()) {
            Module module = MelyClient.moduleManager.getModuleByName(arguments[0]);

            if (module != null) {
                if (!arguments[1].isEmpty()) {
                    switch (arguments[1]) {
                        case "set":
                            if (!arguments[2].isEmpty()) {
                                for (Setting<?> setting : module.getSettings()) {
                                    if (setting.getValue() instanceof Bind && setting.getName().equals("Bind")) {
                                        if (arguments[2].equals("none")) {
                                            ((Setting<Bind>) setting).setValue(new Bind(Keyboard.KEY_NONE));
                                            sendMessage("Key bindings have been set to " + Keyboard.getKeyName(((Setting<Bind>) setting).getValue().getKeyCode()), false);
                                            return;
                                        } else {
                                            int keyCode = Keyboard.getKeyIndex(arguments[2].toUpperCase());
                                            if (keyCode == Keyboard.KEY_NONE) {
                                                errorMessage = "There is no Key called " + arguments[2];
                                            } else {
                                                ((Setting<Bind>) setting).setValue(new Bind(keyCode));
                                                sendMessage("Key bindings have been set to " + Keyboard.getKeyName(((Setting<Bind>) setting).getValue().getKeyCode()), false);
                                                return;
                                            }
                                        }
                                    }
                                }
                            } else {
                                errorMessage = "No key entered";
                            }
                            break;
                        case "get":
                            for (Setting<?> setting : module.getSettings()) {
                                if (setting.getValue() instanceof Bind && setting.getName().equals("Bind")) {
                                    int keyCode = ((Bind) setting.getValue()).getKeyCode();
                                    String keyName;

                                    if (Keyboard.KEY_NONE == keyCode) {
                                        keyName = "none";
                                    } else {
                                        keyName = Keyboard.getKeyName(keyCode);
                                    }

                                    sendMessage(keyName + " key is bound to module " + module.getName(), false);
                                    return;
                                }
                            }
                            break;
                        default:
                            errorMessage = "No argument " + arguments[1] + " is set for the Bind command";
                            break;
                    }
                } else {
                    errorMessage = "Missing argument";
                }
            } else {
                errorMessage = "The module " + arguments[0] + " entered does not exist";
            }
        } else {
            errorMessage = "Missing argument";
        }

        debugAndSendErrorMessage(errorMessage);
    }
}
