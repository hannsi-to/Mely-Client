package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.system.github.GitHubCommitUtil;
import me.hannsi.melyclient.util.system.github.GitHubUtil;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GitHubManager {
    public String gitHubToken;
    public GitHub gitHub;
    public String userName;
    public String repositoryName;
    public GHRepository ghRepository;
    public List<GHCommit> commits;

    public GitHubManager() {
        this.gitHubToken = MelyClient.configManager.gitHubToken;

        MelyClient.logger.info(gitHubToken);
        this.gitHub = GitHubUtil.getGitHub(gitHubToken);
        this.userName = "hannsi-to";
        this.repositoryName = "Mely-Client";
        this.ghRepository = GitHubUtil.getGhRepository(Objects.requireNonNull(gitHub), userName, repositoryName);
        this.commits = new ArrayList<>();
        this.commits = GitHubCommitUtil.getCommits(Objects.requireNonNull(ghRepository));
    }

    public void unLoad() {
        MelyClient.gitHubManager = null;
    }
}
