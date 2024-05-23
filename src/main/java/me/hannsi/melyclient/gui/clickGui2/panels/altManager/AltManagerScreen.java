package me.hannsi.melyclient.gui.clickGui2.panels.altManager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.gui.clickGui2.LoadingCircles;
import me.hannsi.melyclient.gui.clickGui2.Screen;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.auth.AccountData;
import me.hannsi.melyclient.util.system.auth.LoginMode;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AltManagerScreen {
    public static List<AccountButton> accountButtons;
    public static List<LoginModeButton> loginModeButtons;
    public static List<InputAccountInfoBorder> inputAccountInfoBorders;
    public static float inputAccountInfoBorderOffsetY;
    public static AccountScreen screen;
    public static AccountData tempAccount;
    public static String loadText;
    public static float loadingBarX;
    public static float loadingBarWidth;
    public static LoadingCircles loadingCircles;
    public static TimerUtil loadTextTimer;
    public static int loadTextCounter;
    public static boolean loginFailed;
    public static AccountData infoAccountData;

    public static void initGui() {
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);

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
        inputAccountInfoBorderOffsetY = 0f;

        screen = AccountScreen.SelectAccountScreen;

        tempAccount = new AccountData(null, "", "");

        loadText = "Checking Account";
        loadingBarX = ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f - (ubuntu15.getWidth(loadText) / 2f) - 10 - ClickGui2.INSTANCE.menuBarWidth;
        loadingBarWidth = (ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f + (ubuntu15.getWidth(loadText) / 2f)) - (ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f - (ubuntu15.getWidth(loadText) / 2f)) + 20 + ClickGui2.INSTANCE.menuBarWidth * 2;
        loadingCircles = new LoadingCircles(loadingBarX, ClickGui2.INSTANCE.height / 2f + ubuntu15.getHeight() * 2, 10, new Color(255, 255, 255, 255), 7);
        loadTextTimer = new TimerUtil();
        loadTextCounter = 0;

        loginFailed = false;

        infoAccountData = null;
    }

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);

        if (screen == AccountScreen.SelectAccountScreen) {
            float homeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (AccountButton accountButton : accountButtons) {
                accountButton.setX(homeButtonOffsetX);
                accountButton.setY(5 + (bonIcon15.getHeight() * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                accountButton.mouseClicked(mouseX, mouseY, mouseButton);
                homeButtonOffsetX += ClickGui2.INSTANCE.width / 9f + ClickGui2.INSTANCE.width / 100f;
            }
        } else if (screen == AccountScreen.LoginModeScreen) {
            float loginModeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (LoginModeButton loginModeButton : loginModeButtons) {
                loginModeButton.setX(loginModeButtonOffsetX);
                loginModeButton.setY(5 + (bonIcon15.getHeight() * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                loginModeButton.mouseClicked(mouseX, mouseY, mouseButton);
                loginModeButtonOffsetX += ClickGui2.INSTANCE.width / 9f + ClickGui2.INSTANCE.width / 100f;
            }
        } else if (screen == AccountScreen.InputEmailAndPasswordScreen) {
            for (InputAccountInfoBorder inputAccountInfoBorder : inputAccountInfoBorders) {
                inputAccountInfoBorder.mouseClicked(mouseX, mouseY, mouseButton);
            }

            if (MouseUtil.isHoveringWH(ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX + ClickGui2.INSTANCE.width / 3f - ubuntu15.getWidth("    Login    "), inputAccountInfoBorderOffsetY, ubuntu15.getWidth("    Login    "), ubuntu15.getHeight() + 10f, mouseX, mouseY)) {
                new Thread(() -> {
                    boolean checkLogin = MelyClient.altManager.login(tempAccount);
                    if (checkLogin) {
                        screen = AccountScreen.SelectAccountScreen;
                        tempAccount.setSession(MelyClient.altManager.getSession(tempAccount));
                        accountButtons.add(new AccountButton(tempAccount, 0, 0));
                    } else {
                        screen = AccountScreen.InputEmailAndPasswordScreen;
                        new DebugLog("Login Failed.", DebugLevel.ERROR);
                        loginFailed = true;
                    }
                }).start();

                screen = AccountScreen.CheckingAccountScreen;
            }
        }
    }

    public static void drawScreen(int mouseX, int mouseY, float width, float height) {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil bonIcon40 = new FontUtil(MelyClient.fontManager.bonIcon, 40);
        FontUtil bonIcon100 = new FontUtil(MelyClient.fontManager.bonIcon, 100);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
        FontUtil ubuntu20 = new FontUtil(MelyClient.fontManager.ubuntu, 20);

        ClickGui2.HomeButton altManagerInfo = null;

        for (ClickGui2.HomeButton homeButton : ClickGui2.INSTANCE.homeButtons) {
            if (homeButton.getScreen() == Screen.AltManager) {
                altManagerInfo = homeButton;
                break;
            }
        }

        if (altManagerInfo == null) {
            new DebugLog("Can not load alt manager screen.", DebugLevel.WARNING);
            return;
        }

        ubuntu15.drawText(altManagerInfo.getName(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + bonIcon15.getHeight() + 5, new Color(255, 255, 255, 255));
        ubuntu10.drawText(altManagerInfo.getDescription(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + (bonIcon15.getHeight() * 2) + 10, new Color(91, 91, 91, 255));

        if (screen == AccountScreen.SelectAccountScreen) {
            float homeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (AccountButton accountButton : accountButtons) {
                accountButton.setX(homeButtonOffsetX);
                accountButton.setY(5 + (bonIcon15.getHeight() * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                accountButton.draw(mouseX, mouseY, width, height);
                homeButtonOffsetX += width / 9f + width / 100f;
            }
        } else if (screen == AccountScreen.LoginModeScreen) {
            float loginModeButtonOffsetX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
            for (LoginModeButton loginModeButton : loginModeButtons) {
                loginModeButton.setX(loginModeButtonOffsetX);
                loginModeButton.setY(5 + (bonIcon15.getHeight() * 2) + 10 + ClickGui2.INSTANCE.offsetX);
                loginModeButton.draw(mouseX, mouseY, width, height);
                loginModeButtonOffsetX += width / 9f + width / 100f;
            }
        } else if (screen == AccountScreen.InputEmailAndPasswordScreen) {
            inputAccountInfoBorderOffsetY = 5 + (bonIcon15.getHeight() * 2) + 10 + ClickGui2.INSTANCE.offsetX;
            for (InputAccountInfoBorder inputAccountInfoBorder : inputAccountInfoBorders) {
                inputAccountInfoBorder.setX(ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX);
                inputAccountInfoBorder.setY(inputAccountInfoBorderOffsetY);
                inputAccountInfoBorder.draw(mouseX, mouseY, width / 3f, bonIcon40.getHeight());
                inputAccountInfoBorderOffsetY += bonIcon15.getHeight() * 4 + ubuntu20.getHeight();
            }

            inputAccountInfoBorderOffsetY += ubuntu10.getHeight();

            NanoVGRenderUtil.drawRectWH(ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX + width / 3f - ubuntu15.getWidth("    Login    "), inputAccountInfoBorderOffsetY, ubuntu15.getWidth("    Login    "), ubuntu15.getHeight() + 10f, new Color(0, 120, 212, 255));
            ubuntu15.drawText("    Login    ", ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX + width / 3f - ubuntu15.getWidth("    Login    "), inputAccountInfoBorderOffsetY + 5f, new Color(255, 255, 255, 255));

            if (loginFailed) {
                ubuntu10.drawText("Login Failed.", ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX + width / 3f + 5f, inputAccountInfoBorderOffsetY + ubuntu15.getHeight() + 10f - ubuntu10.getHeight(), DebugLevel.ERROR.getColor());
            }
        } else if (screen == AccountScreen.CheckingAccountScreen) {
            bonIcon100.drawTextCenter(BonIcon.DEPLOYEDCODE, ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f, ClickGui2.INSTANCE.height / 2f - (ubuntu15.getHeight() * 4), new Color(255, 255, 255, 255));

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

            ubuntu15.drawTextCenter(loadText, ClickGui2.INSTANCE.menuBarWidth + (ClickGui2.INSTANCE.width - ClickGui2.INSTANCE.menuBarWidth) / 2f, ClickGui2.INSTANCE.height / 2f, new Color(255, 255, 255, 255));

            NanoVGRenderUtil.drawOutLineRoundedRectWH(loadingBarX, ClickGui2.INSTANCE.height / 2f + ubuntu15.getHeight() * 2 - 10, loadingBarWidth, (ClickGui2.INSTANCE.height / 2f + ubuntu15.getHeight() * 2) - (ClickGui2.INSTANCE.height / 2f + ubuntu15.getHeight() * 2) + 20, 10, 1, new Color(255, 255, 255, 255));

            loadingCircles.draw(2500, loadingBarWidth);
        } else if (screen == AccountScreen.AccountInfoScreen) {
            if (infoAccountData == null) {
                return;
            }

            float infoAccountDataOffsetY = 5 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2;
            ubuntu15.drawText("Name  :  " + infoAccountData.getSession().getUsername(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu15.getHeight() + ubuntu10.getHeight() + 2);
            ubuntu10.drawText("ID  :  " + infoAccountData.getSession().getProfile().getId(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu10.getHeight());
            ubuntu10.drawText("PlayerID  :  " + infoAccountData.getSession().getPlayerID(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu10.getHeight() * 2);
            ubuntu10.drawText("LoginMode  :  " + infoAccountData.getLoginMode().getDisplay(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu10.getHeight());
            ubuntu10.drawText("Email  :  " + (LoginMode.MICROSOFT == infoAccountData.getLoginMode() ? "If you are logged in with Microsoft, your email information will not be saved." : infoAccountData.getEmail()), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu10.getHeight());
            ubuntu10.drawText("Password  :  " + (LoginMode.MICROSOFT == infoAccountData.getLoginMode() ? "If you are logged in with Microsoft, your password information will not be saved." : infoAccountData.getEmail()), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu10.getHeight());
            ubuntu10.drawText("Complete  :  " + infoAccountData.getSession().getProfile().isComplete(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
            infoAccountDataOffsetY += (ubuntu10.getHeight());
            ubuntu10.drawText("Legacy  :  " + infoAccountData.getSession().getProfile().isLegacy(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, infoAccountDataOffsetY, new Color(255, 255, 255, 255));
        }
    }

    public static void keyTyped(char typedChar, int keyCode) {
        if (screen == AccountScreen.InputEmailAndPasswordScreen) {
            for (InputAccountInfoBorder inputAccountInfoBorder : inputAccountInfoBorders) {
                inputAccountInfoBorder.keyTyped(typedChar, keyCode);
            }
        }
    }
}
