package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.gui.clickGui2.Screen;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.auth.AccountData;
import me.hannsi.melyclient.util.system.auth.LoginMode;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AltManagerScreen {
    public static List<AccountButton> accountButtons;
    public static List<LoginModeButton> loginModeButtons;
    public static List<InputAccountInfoBorder> inputAccountInfoBorders;
    public static AccountScreen screen;
    public static AccountData tempAccount;
    public static String loadText;
    public static float loadingBarX;
    public static float loadingBarWidth;
    public static LoadingCircles loadingCircles;
    public static TimerUtil loadTextTimer;
    public static int loadTextCounter;

    public static void initGui() {
        accountButtons = new ArrayList<>();
        for (AccountData accountData : MelyClient.altManager.accountDataList) {
            accountButtons.add(new AccountButton(accountData, 0, 0));
        }
        accountButtons.add(new AccountButton(null, 0, 0));

        loginModeButtons = new ArrayList<>();
        loginModeButtons.add(new LoginModeButton(LoginMode.MICROSOFT, 0, 0, "Login to Minecraft using your Microsoft account."));
        loginModeButtons.add(new LoginModeButton(LoginMode.MINECRAFT, 0, 0, "Login to Minecraft using your Minecraft account. (This is deprecated)"));

        inputAccountInfoBorders = new ArrayList<>();
        inputAccountInfoBorders.add(new InputAccountInfoBorder("Email", BonIcon.MAIL, "", 0, 0));
        inputAccountInfoBorders.add(new InputAccountInfoBorder("Password", BonIcon.PASSWORD, "", 0, 0));

        screen = AccountScreen.SelectAccountScreen;

        tempAccount = new AccountData(null, null, null, null);

        loadText = "Checking Account";
        loadingBarX = ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f - (MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, loadText, 15) / 2f) - 10 - ClickGui2.INSTANCE.menuBarWidth;
        loadingBarWidth = (ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f + (MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, loadText, 15) / 2f)) - (ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f - (MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, loadText, 15) / 2f)) + 20 + ClickGui2.INSTANCE.menuBarWidth * 2;
        loadingCircles = new LoadingCircles(loadingBarX, ClickGui2.INSTANCE.height / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 15) * 2, 10, new Color(255, 255, 255, 255), 7);

        loadTextTimer = new TimerUtil();
        loadTextCounter = 0;
    }

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (screen == AccountScreen.SelectAccountScreen) {
            float homeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (AccountButton accountButton : accountButtons) {
                accountButton.setX(homeButtonOffsetX);
                accountButton.setY(5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                accountButton.mouseClicked(mouseX, mouseY, mouseButton);
                homeButtonOffsetX += ClickGui2.INSTANCE.width / 9f + ClickGui2.INSTANCE.width / 100f;
            }
        } else if (screen == AccountScreen.LoginModeScreen) {
            float loginModeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (LoginModeButton loginModeButton : loginModeButtons) {
                loginModeButton.setX(loginModeButtonOffsetX);
                loginModeButton.setY(5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                loginModeButton.mouseClicked(mouseX, mouseY, mouseButton);
                loginModeButtonOffsetX += ClickGui2.INSTANCE.width / 9f + ClickGui2.INSTANCE.width / 100f;
            }
        } else if (screen == AccountScreen.InputEmailAndPasswordScreen) {

        }
    }

    public static void drawScreen(int mouseX, int mouseY, float width, float height) {
        ClickGui2.HomeButton altManagerInfo = null;

        for (ClickGui2.HomeButton homeButton : ClickGui2.INSTANCE.homeButtons) {
            if (homeButton.getScreen() == Screen.AltManager) {
                altManagerInfo = homeButton;
                break;
            }
        }

        if (altManagerInfo == null) {
            MelyClient.logger.error("Can not load alt manager screen.");
            return;
        }

        NVGRenderUtil.drawText(altManagerInfo.getName(), MelyClient.fontManager.ubuntu, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 5, 15, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawText(altManagerInfo.getDescription(), MelyClient.fontManager.ubuntu, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10, 10, new Color(91, 91, 91, 255));

        if (screen == AccountScreen.SelectAccountScreen) {
            float homeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (AccountButton accountButton : accountButtons) {
                accountButton.setX(homeButtonOffsetX);
                accountButton.setY(5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                accountButton.draw(mouseX, mouseY, width, height);
                homeButtonOffsetX += width / 9f + width / 100f;
            }
        } else if (screen == AccountScreen.LoginModeScreen) {
            float loginModeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (LoginModeButton loginModeButton : loginModeButtons) {
                loginModeButton.setX(loginModeButtonOffsetX);
                loginModeButton.setY(5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                loginModeButton.draw(mouseX, mouseY, width, height);
                loginModeButtonOffsetX += width / 9f + width / 100f;
            }
        } else if (screen == AccountScreen.InputEmailAndPasswordScreen) {

        } else if (screen == AccountScreen.CheckingAccountScreen) {
            NVGRenderUtil.drawTextCenter(BonIcon.DEPLOYEDCODE, MelyClient.fontManager.bonIcon, ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f, ClickGui2.INSTANCE.height / 2f - (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 15) * 4), 100, new Color(255, 255, 255, 255));

            if (loadTextTimer.passed(1000)) {
                loadTextCounter++;
                loadTextTimer.reset();

                if (loadTextCounter < 4) {
                    loadText = loadText + ".";
                } else {
                    loadTextCounter = 0;
                    loadText = loadText.substring(0, loadText.length() - 3);
                }
            }

            NVGRenderUtil.drawTextCenter(loadText, MelyClient.fontManager.ubuntu, ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f, ClickGui2.INSTANCE.height / 2f, 15, new Color(255, 255, 255, 255));

            NVGRenderUtil.drawOutLineRoundedRectWH(loadingBarX, ClickGui2.INSTANCE.height / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 15) * 2 - 10, loadingBarWidth, (ClickGui2.INSTANCE.height / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 15) * 2) - (ClickGui2.INSTANCE.height / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 15) * 2) + 20, 10, 1, new Color(255, 255, 255, 255));

            loadingCircles.draw(2500, loadingBarWidth);
        }
    }

    public static void keyTyped(char typedChar, int keyCode) {
        for (LoginModeButton loginModeButton : loginModeButtons) {
            loginModeButton.keyTyped(typedChar, keyCode);
        }
    }
}
