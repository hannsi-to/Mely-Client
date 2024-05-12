package me.hannsi.melyclient.gui.clickGui2.Panels.clientInfo;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.gui.clickGui2.Screen;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.github.CommitStats;
import me.hannsi.melyclient.util.system.github.GitHubCommitUtil;
import org.kohsuke.github.GHCommit;
import org.lwjgl.input.Mouse;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.Objects;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;

public class ClientInfoScreen {
    public static float scrollY;

    public static void init() {
        scrollY = 0.0f;
    }

    public static void drawScreen(int mouseX, int mouseY, int width, int height) {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
        FontUtil notoSansJPRegular10 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 10);
        FontUtil notoSansJPRegular15 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 15);

        ClickGui2.HomeButton clientInfoScreenInfo = null;

        for (ClickGui2.HomeButton homeButton : ClickGui2.INSTANCE.homeButtons) {
            if (homeButton.getScreen() == Screen.ClientInfo) {
                clientInfoScreenInfo = homeButton;
                break;
            }
        }

        if (clientInfoScreenInfo == null) {
            new DebugLog("Can not load alt manager screen.", DebugLevel.WARNING);
            return;
        }

        ubuntu15.drawText(clientInfoScreenInfo.getName(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + bonIcon15.getHeight() + 5, new Color(255, 255, 255, 255));
        ubuntu10.drawText(clientInfoScreenInfo.getDescription(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + (bonIcon15.getHeight() * 2) + 10, new Color(91, 91, 91, 255));

        int scrollCount = Mouse.getDWheel();

        if (scrollCount > 0) {
            if (scrollY != 0) {
                scrollY += 10;
            }
        }

        NanoVG.nvgScissor(nvg, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 3 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2, width, height);
        int commitCount = 0;
        for (GHCommit ignored : MelyClient.gitHubManager.commits) {
            commitCount++;
        }
        float clientInfoOffsetY = 5 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2 + scrollY;
        for (GHCommit ghCommit : MelyClient.gitHubManager.commits) {
            notoSansJPRegular15.drawText(GitHubCommitUtil.getCommitMessage(ghCommit), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular15.getHeight());
            notoSansJPRegular10.drawText("Author  :  " + GitHubCommitUtil.getCommitAuthor(ghCommit), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular10.getHeight());
            notoSansJPRegular10.drawText("URL  :  " + GitHubCommitUtil.getCommitURL(ghCommit).toString(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular10.getHeight());
            CommitStats commitStats = GitHubCommitUtil.getCommitStatistics(ghCommit);
            notoSansJPRegular10.drawText("Additions  :  " + Objects.requireNonNull(commitStats).getAdditions(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular10.getHeight());
            notoSansJPRegular10.drawText("Deletions  :  " + Objects.requireNonNull(commitStats).getDeletions(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular10.getHeight());
            notoSansJPRegular10.drawText("TotalChanges  :  " + Objects.requireNonNull(commitStats).getTotalChanges(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular10.getHeight());
            notoSansJPRegular10.drawText("CommitCount  :  " + commitCount, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular10.getHeight());
            notoSansJPRegular10.drawText("Date  :  " + GitHubCommitUtil.getCommitDate(ghCommit), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, clientInfoOffsetY, new Color(255, 255, 255, 255));
            clientInfoOffsetY += (notoSansJPRegular15.getHeight() * 2);

            commitCount--;
        }
        NanoVG.nvgResetScissor(nvg);

        if (scrollCount < 0) {
            if (scrollY > -(clientInfoOffsetY - scrollY - (5 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2))) {
                scrollY -= 10;
            }
        }
    }
}
