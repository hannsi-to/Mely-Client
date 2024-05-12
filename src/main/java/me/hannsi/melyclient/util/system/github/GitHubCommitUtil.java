package me.hannsi.melyclient.util.system.github;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitStatus;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class GitHubCommitUtil {
    public static CommitStats getCommitStatistics(GHCommit ghCommit) {
        try {
            return GitHubCommitStatistics.calculateCommitStatistics(ghCommit);
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static String getCommitTreeUrl(GHCommit commit) {
        try {
            return commit.getTree().getUrl().toString();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static List<GHCommit> getCommitParents(GHCommit commit) {
        try {
            return commit.getParents();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static GHCommitStatus getCommitStatus(GHCommit commit) {
        try {
            return commit.getLastStatus();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static List<GHCommit.File> getCommitFiles(GHCommit commit) {
        try {
            return commit.getFiles();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static String getCommitCommitterName(GHCommit ghCommit) {
        try {
            return ghCommit.getCommitShortInfo().getCommitter().getName();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static int getCommitCount(GHCommit ghCommit) {
        try {
            return ghCommit.getCommitShortInfo().getCommentCount();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getCommitSHA(GHCommit ghCommit) {
        try {
            return ghCommit.getCommitShortInfo().getCommitDate();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static URL getCommitURL(GHCommit ghCommit) {
        return ghCommit.getHtmlUrl();
    }

    public static String getCommitAuthor(GHCommit ghCommit) {
        try {
            return ghCommit.getCommitShortInfo().getAuthor().getName();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static String getCommitMessage(GHCommit ghCommit) {
        try {
            return ghCommit.getCommitShortInfo().getMessage();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }

    public static List<GHCommit> getCommits(GHRepository ghRepository) {
        try {
            return ghRepository.listCommits().toList();
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }
}
