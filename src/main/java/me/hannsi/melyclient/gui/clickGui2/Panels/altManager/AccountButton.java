package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.auth.AccountData;
import me.hannsi.melyclient.util.system.math.MouseUtil;

import java.awt.*;

public class AccountButton {
    public AccountData accountData;
    public float x;
    public float y;

    public AccountButton(AccountData accountData, float x, float y) {
        this.accountData = accountData;
        this.x = x;
        this.y = y;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu30 = new FontUtil(MelyClient.fontManager.ubuntu, 30);

        if (MouseUtil.isHoveringWH(x, y, ClickGui2.INSTANCE.width / 9f, ClickGui2.INSTANCE.height / 3f, mouseX, mouseY)) {
            if (accountData == null) {
                AltManagerScreen.screen = AccountScreen.LoginModeScreen;
            }
        }

        if (MouseUtil.isHoveringWH(x + 1, y + ClickGui2.INSTANCE.height / 3f - ubuntu30.getHeight() + 1, ClickGui2.INSTANCE.width / 9f - 2, ubuntu30.getHeight() - 2, mouseX, mouseY)) {
            MelyClient.altManager.setSession(accountData.getSession());
            MelyClient.altManager.nowLoginAccountData = accountData;
        }

        if (MouseUtil.isHoveringWH(x + 1, y + ClickGui2.INSTANCE.height / 3f - ubuntu30.getHeight() + 1 - (ubuntu30.getHeight() - 2), ClickGui2.INSTANCE.width / 9f - 2, ubuntu30.getHeight() - 2, mouseX, mouseY)) {
            AltManagerScreen.screen = AccountScreen.AccountInfoScreen;
            AltManagerScreen.infoAccountData = accountData;
        }
    }

    public void draw(int mouseX, int mouseY, float width, float height) {
        FontUtil bonIcon40 = new FontUtil(MelyClient.fontManager.bonIcon, 40);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);
        FontUtil ubuntu30 = new FontUtil(MelyClient.fontManager.ubuntu, 30);
        FontUtil ubuntu50 = new FontUtil(MelyClient.fontManager.ubuntu, 50);

        NVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, width / 9f + 2, height / 3f + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

        NVGRenderUtil.drawRectWH(x, y, width / 9f, height / 3f, new Color(0, 0, 0, 255));

        if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
            NVGRenderUtil.drawOutLineRectWH(x, y, width / 9f, height / 3f, 1.0f, new Color(91, 91, 91, 255));

            NVGRenderUtil.drawRadialGradientRect(x, y, width / 9f, height / 3f, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
        }

        //NVGRenderUtil.drawText(
        //        icon,
        //        MelyClient.fontManager.bonIcon,
        //        x + 5,
        //        y + 2.5f,
        //        40,
        //        new Color(0, 120, 212,255)
        //);

        if (accountData == null) {
            ubuntu50.drawTextCenter("+", x + (width / 9f) / 2f, y + (height / 3f) / 2f, new Color(255, 255, 255, 255));

            if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
                ubuntu10.drawTextCenter("Add", x + (width / 9f) / 2f, y + (height / 3f) / 2f + ubuntu30.getHeight(), new Color(255, 255, 255, 255));
            }
        } else {
            ubuntu12.drawText(accountData.getSession().getUsername(), x + 5, y + 5, new Color(255, 255, 255, 255));

            NVGRenderUtil.drawRadialGradientRect(x + 1, y + height / 3f - ubuntu30.getHeight() + 1, width / 9f - 2, ubuntu30.getHeight() - 2, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            ubuntu12.drawText("Change", x + (width / 9f) / 2f - ubuntu12.getWidth("Change") / 2f, y + height / 3f - (ubuntu30.getHeight() - 2) / 2f - ubuntu12.getHeight() / 2f, new Color(255, 255, 255, 255));

            NVGRenderUtil.drawRadialGradientRect(x + 1, y + height / 3f - ubuntu30.getHeight() + 1 - (ubuntu30.getHeight() - 2), width / 9f - 2, ubuntu30.getHeight() - 2, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            ubuntu12.drawText("Info", x + (width / 9f) / 2f - ubuntu12.getWidth("Info") / 2f, y + height / 3f - (ubuntu30.getHeight() - 2) - (ubuntu30.getHeight() + 1) / 2f - ubuntu12.getHeight() / 2f, new Color(255, 255, 255, 255));
        }
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
