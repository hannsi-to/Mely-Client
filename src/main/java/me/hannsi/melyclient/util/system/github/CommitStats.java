package me.hannsi.melyclient.util.system.github;

public class CommitStats {
    public int additions;
    public int deletions;
    public int totalChanges;

    public CommitStats(int additions, int deletions, int totalChanges) {
        this.additions = additions;
        this.deletions = deletions;
        this.totalChanges = totalChanges;
    }

    public int getAdditions() {
        return additions;
    }

    public void setAdditions(int additions) {
        this.additions = additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public void setDeletions(int deletions) {
        this.deletions = deletions;
    }

    public int getTotalChanges() {
        return totalChanges;
    }

    public void setTotalChanges(int totalChanges) {
        this.totalChanges = totalChanges;
    }
}
