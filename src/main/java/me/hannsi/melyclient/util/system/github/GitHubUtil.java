package me.hannsi.melyclient.util.system.github;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class GitHubUtil {
    public static GHRepository getGhRepository(GitHub gitHub, String userName, String repositoryName) {
        try {
            return gitHub.getRepository(userName + "/" + repositoryName);
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static GitHub getGitHub(String gitHubToken) {
        try {
            return GitHub.connectUsingOAuth(gitHubToken);
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }
}
