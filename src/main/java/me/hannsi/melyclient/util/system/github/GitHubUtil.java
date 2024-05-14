package me.hannsi.melyclient.util.system.github;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Scanner;

public class GitHubUtil {
    public static String getReadme() {
        try {
            GHContent readme = MelyClient.gitHubManager.ghRepository.getFileContent("README.md");

            Scanner scanner = new Scanner(readme.read());
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

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
