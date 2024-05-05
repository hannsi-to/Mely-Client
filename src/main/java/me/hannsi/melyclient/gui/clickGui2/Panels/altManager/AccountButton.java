package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
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
        if (MouseUtil.isHoveringWH(x, y, ClickGui2.INSTANCE.width / 9f, ClickGui2.INSTANCE.height / 3f, mouseX, mouseY)) {
            if (accountData == null) {
                AltManagerScreen.screen = AccountScreen.LoginModeScreen;
            }
        }
    }

    public void draw(int mouseX, int mouseY, float width, float height) {
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
            NVGRenderUtil.drawTextCenter("+", MelyClient.fontManager.ubuntu, x + (width / 9f) / 2f, y + (height / 3f) / 2f, 50, new Color(255, 255, 255, 255));

            if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
                NVGRenderUtil.drawTextCenter("Add", MelyClient.fontManager.ubuntu, x + (width / 9f) / 2f, y + (height / 3f) / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 30), 10, new Color(255, 255, 255, 255));
            }
        } else {
            NVGRenderUtil.drawText(accountData.getSession().getUsername(), MelyClient.fontManager.ubuntu, x + 5, y + 2.5f + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 40), 12, new Color(255, 255, 255, 255));

            float textOffsetY = 2.5f + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 40) + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12);
            NVGRenderUtil.drawText("PlayerID : " + accountData.getSession().getPlayerID(), MelyClient.fontManager.ubuntu, x + 5, y + textOffsetY, 10, new Color(91, 91, 91, 255));
            textOffsetY += MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10);
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
