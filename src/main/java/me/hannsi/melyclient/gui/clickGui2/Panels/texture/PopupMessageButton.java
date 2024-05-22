package me.hannsi.melyclient.gui.clickGui2.Panels.texture;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PopupMessageButton {
    public String name;
    public float x;
    public float y;
    public PopupMessageType popupMessageType;
    public TimerUtil timerUtil;
    public ResourcePackButton resourcePackButton;

    public PopupMessageButton(String name, float x, float y, PopupMessageType popupMessageType, ResourcePackButton resourcePackButton) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.popupMessageType = popupMessageType;
        this.timerUtil = new TimerUtil();
        this.resourcePackButton = resourcePackButton;
    }

    public float drawScreen(float mouseX, float mouseY, float particleTicks) {
        float returnOffsetX = 0.0f;
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);

        float borderWidth = ubuntu15.getWidth(name) + 10;
        float borderHeight = ubuntu15.getHeight() + 10;

        NanoVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, borderWidth + 2, borderHeight + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(38, 38, 38, 255));

        NanoVGRenderUtil.drawRectWH(x, y, borderWidth, borderHeight, new Color(38, 38, 38, 255));

        if (MouseUtil.isHoveringWH(x, y, borderWidth, borderHeight, mouseX, mouseY)) {
            NanoVGRenderUtil.drawOutLineRectWH(x, y, borderWidth, borderHeight, 1.0f, new Color(91, 91, 91, 255));

            NanoVGRenderUtil.drawRadialGradientRect(x, y, borderWidth, borderHeight, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(38, 38, 38, 255));
        }

        ubuntu15.drawText(name, x + 5, y + 5, new Color(255, 255, 255, 255));
        returnOffsetX += ubuntu15.getWidth(name) + 20;
        return returnOffsetX;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (timerUtil.passed(100)) {
            timerUtil.reset();
            FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);

            float borderWidth = ubuntu15.getWidth(name) + 10;
            float borderHeight = ubuntu15.getHeight() + 10;

            if (MouseUtil.isHoveringWH(x, y, borderWidth, borderHeight, mouseX, mouseY)) {
                if (name.equals("OK")) {
                    if (popupMessageType == PopupMessageType.OpenUrl) {
                        TextureButton.popupMessageOpenUrl.setType(-1);
                        try {
                            Desktop.getDesktop().browse(new URI("https://modrinth.com/resourcepacks"));
                        } catch (IOException | URISyntaxException e) {
                            new DebugLog(e, DebugLevel.ERROR);
                        }
                    } else {
                        try {
                            Path path = Paths.get("resourcepacks/" + resourcePackButton.getResourcePackRepositoryEntry().getResourcePackName());
                            Files.delete(path);
                        } catch (IOException e) {
                            new DebugLog(e, DebugLevel.ERROR);
                        }
                        Texture.availableResourcePacks.remove(resourcePackButton);
                        ResourcePackButton.popupMessageDelete.setType(-1);
                    }
                } else {
                    if (popupMessageType == PopupMessageType.OpenUrl) {
                        TextureButton.popupMessageOpenUrl.setType(-1);
                    } else {
                        ResourcePackButton.popupMessageDelete.setType(-1);
                    }
                }
            }
        }
    }

    public PopupMessageType getPopupMessageType() {
        return popupMessageType;
    }

    public void setPopupMessageType(PopupMessageType popupMessageType) {
        this.popupMessageType = popupMessageType;
    }

    public TimerUtil getTimerUtil() {
        return timerUtil;
    }

    public void setTimerUtil(TimerUtil timerUtil) {
        this.timerUtil = timerUtil;
    }

    public ResourcePackButton getResourcePackButton() {
        return resourcePackButton;
    }

    public void setResourcePackButton(ResourcePackButton resourcePackButton) {
        this.resourcePackButton = resourcePackButton;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
