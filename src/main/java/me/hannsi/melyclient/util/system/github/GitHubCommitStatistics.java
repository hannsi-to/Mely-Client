package me.hannsi.melyclient.util.system.github;

import org.kohsuke.github.GHCommit;

import java.io.IOException;
import java.util.List;

public class GitHubCommitStatistics {
    public static CommitStats calculateCommitStatistics(GHCommit commit) throws IOException {
        List<GHCommit.File> files = commit.getFiles();
        int additions = 0;
        int deletions = 0;
        int totalChanges = 0;

        for (GHCommit.File file : files) {
            additions += file.getLinesAdded();
            deletions += file.getLinesDeleted();
            totalChanges += file.getLinesChanged();
        }

        return new CommitStats(additions, deletions, totalChanges);
    }
}
