package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.auth.LoginMode;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginModeButton {
    public LoginMode loginMode;
    public ResourceLocation resourceLocation;
    public float x;
    public float y;
    public String description;

    public LoginModeButton(LoginMode loginMode, float x, float y, String description) {
        this.loginMode = loginMode;
        this.x = x;
        this.y = y;

        if (loginMode == LoginMode.MICROSOFT) {
            resourceLocation = new ResourceLocation("mely/img/clickgui2/microsoft.jpg");
        } else if (loginMode == LoginMode.MINECRAFT) {
            resourceLocation = new ResourceLocation("textures/gui/title/minecraft.png");
        }

        this.description = description;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.isHoveringWH(x, y, ClickGui2.INSTANCE.width / 9f, ClickGui2.INSTANCE.height / 3f, mouseX, mouseY)) {
            if (loginMode == LoginMode.MICROSOFT) {
                new Thread(() -> {
                    Session session = MelyClient.altManager.loginWithWebView();
                    if (session != null) {
                        AltManagerScreen.screen = AccountScreen.SelectAccountScreen;
                        AltManagerScreen.tempAccount.setSession(session);
                        AltManagerScreen.tempAccount.setLoginMode(loginMode);
                        AltManagerScreen.accountButtons.add(new AccountButton(AltManagerScreen.tempAccount, 0, 0));
                    } else {
                        AltManagerScreen.screen = AccountScreen.LoginModeScreen;
                    }
                }).start();

                AltManagerScreen.screen = AccountScreen.CheckingAccountScreen;
            } else if (loginMode == LoginMode.MINECRAFT) {
                AltManagerScreen.tempAccount.setLoginMode(loginMode);
                AltManagerScreen.screen = AccountScreen.InputEmailAndPasswordScreen;
            }
        }
    }

    public void draw(int mouseX, int mouseY, float width, float height) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);

        NVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, width / 9f + 2, height / 3f + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

        NVGRenderUtil.drawRectWH(x, y, width / 9f, height / 3f, new Color(0, 0, 0, 255));

        if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
            NVGRenderUtil.drawOutLineRectWH(x, y, width / 9f, height / 3f, 1.0f, new Color(91, 91, 91, 255));

            NVGRenderUtil.drawRadialGradientRect(x, y, width / 9f, height / 3f, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
        }

        ubuntu12.drawText(loginMode.getDisplay(), x + 5, y + 2.5f, new Color(255, 255, 255, 255));

        String[] descriptionArray = description.split("");
        String tempDescription = "";
        List<String> nDescription = new ArrayList<>();

        for (String s : descriptionArray) {
            tempDescription = tempDescription + s;

            float descriptionWidth = ubuntu10.getWidth(tempDescription);

            if (width / 9f - 15 < descriptionWidth) {
                nDescription.add(tempDescription);
                tempDescription = "";
            }
        }
        nDescription.add(tempDescription);

        float textOffsetY = 2.5f + ubuntu12.getHeight();
        for (String text : nDescription) {
            ubuntu10.drawText(text, x + 5, y + textOffsetY, new Color(91, 91, 91, 255));

            textOffsetY += ubuntu10.getHeight();
        }
    }

    public LoginMode getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(LoginMode loginMode) {
        this.loginMode = loginMode;
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
