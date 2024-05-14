package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.file.FileUtil;
import me.hannsi.melyclient.util.system.math.crypto.CryptoUtil;

import java.io.File;

public class ConfigManager {
    public final String baseDirectory = "melyclient/";
    public final String gitHubDirectory = "gitHub/";
    public final String baseGitHubDirectory = baseDirectory + gitHubDirectory;
    public String gitHubToken;

    public ConfigManager() {
        load();
    }

    public void reLoad() {
        load();
    }

    public void load() {
        createDirectory();

        try {
            gitHubLoad();
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
    }

    public void unLoad() {
        save();

        MelyClient.configManager = null;
    }

    public void save() {
        createDirectory();

        try {
            gitHubSave();
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
    }

    public void gitHubLoad() throws Exception {
        File file = new File(baseGitHubDirectory + "crypto");

        if (!file.exists()) {
            file.createNewFile();

            FileUtil.writeString("fKgwA4P4BHEikFkajSS4Si2zNMYBwPEQ/3NiZKGwz5Wf/PzLteMcd8ICwafApwA5", file);
        }

        gitHubToken = CryptoUtil.decrypt(FileUtil.readString(file), CryptoUtil.getKey(MelyClient.MOD_ID));
    }

    public void gitHubSave() throws Exception {
        File file = new File(baseGitHubDirectory + "crypto");
        file.createNewFile();

        FileUtil.writeString(CryptoUtil.encrypt(gitHubToken, CryptoUtil.getKey(MelyClient.MOD_ID)), file);
    }

    public void createDirectory() {
        FileUtil.createDirectory(baseGitHubDirectory);
    }
}
