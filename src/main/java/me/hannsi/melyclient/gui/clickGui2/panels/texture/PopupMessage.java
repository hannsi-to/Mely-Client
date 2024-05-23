package me.hannsi.melyclient.gui.clickGui2.panels.texture;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopupMessage {
    public PopupMessageType popupMessageType;
    public String name;
    public float displayWidth;
    public float displayHeight;
    public ResourcePackButton resourcePackButton;
    public int type;
    public List<PopupMessageButton> popupMessageButtonList;
    public List<String> description;

    public PopupMessage(PopupMessageType popupMessageType, String name, float displayWidth, float displayHeight, ResourcePackButton resourcePackButton) {
        this.popupMessageType = popupMessageType;
        this.name = name;
        this.type = -1;
        this.resourcePackButton = resourcePackButton;
        update(displayWidth, displayHeight);
        popupMessageButtonList = new ArrayList<>();
        popupMessageButtonList.add(new PopupMessageButton("OK", 0, 0, popupMessageType, resourcePackButton));
        popupMessageButtonList.add(new PopupMessageButton("Cancel", 0, 0, popupMessageType, resourcePackButton));
        description = new ArrayList<>();
        description.add("Trying to open website from minecraft.\nTo open, click the (OK) button.\nTo close, click the (Cancel) button or the X button.\nSite -> https://modrinth.com/resourcepacks");
        description.add("Trying to remove texture packs.\nClick the (OK) button to delete.\nTo close, click the (Cancel) button or the X button.\nFile -> ");
    }

    public void drawScreen(float mouseX, float mouseY, float particleTicks) {
        if (type != 0) {
            return;
        }

        for (PopupMessageButton popupMessageButton : popupMessageButtonList) {
            if (popupMessageButton.getResourcePackButton() == null) {
                popupMessageButton.setResourcePackButton(resourcePackButton);
            }
        }

        FontUtil bonIcon20 = new FontUtil(MelyClient.fontManager.bonIcon, 20);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        float x = this.displayWidth / 4f;
        float y = this.displayHeight / 4f;
        float width = x * 2;
        float height = y * 2;
        NanoVGRenderUtil.drawRoundedRectWH(x, y, width, height, 5f, new Color(31, 31, 31, 255));
        NanoVGRenderUtil.drawOutLineRoundedRectWH(x, y, width, height, 5f, 1f, new Color(38, 38, 38, 255));

        String[] messages;
        if (popupMessageType == PopupMessageType.OpenUrl) {
            ubuntu15.drawText("Trying to open a website.", x + 10, y + 10, new Color(255, 255, 255, 255));
            messages = StringUtil.splitln(description.get(0));
        } else {
            ubuntu15.drawText("Trying to remove texture packs.", x + 10, y + 10, new Color(255, 255, 255, 255));
            messages = StringUtil.splitln(description.get(1));
        }

        float offsetX = ubuntu15.getHeight() * 2;
        int count = messages.length;
        for (String message : messages) {
            ubuntu10.drawText(message + (count == 1 && popupMessageType == PopupMessageType.Delete ? resourcePackButton.getResourcePackRepositoryEntry().getResourcePackName() : ""), x + 20, y + 10 + offsetX, new Color(255, 255, 255, 255));
            offsetX += ubuntu15.getHeight() + 10;
            count--;
        }

        float maxButtonWidth = 0.0f;
        for (PopupMessageButton popupMessageButton : popupMessageButtonList) {
            maxButtonWidth += ubuntu15.getWidth(popupMessageButton.getName()) + 20;
        }
        float offsetX2 = x + width - maxButtonWidth;
        for (PopupMessageButton popupMessageButton : popupMessageButtonList) {
            popupMessageButton.setX(offsetX2);
            popupMessageButton.setY(y + height - ubuntu15.getHeight() - 15);
            offsetX2 += popupMessageButton.drawScreen(mouseX, mouseY, particleTicks);
        }

        bonIcon20.drawText(BonIcon.CLOSE, x + width - bonIcon20.getWidth(BonIcon.CLOSE) - 5, y + 5, new Color(255, 10, 10, 255));
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil bonIcon20 = new FontUtil(MelyClient.fontManager.bonIcon, 20);

        float x = this.displayWidth / 4f;
        float y = this.displayHeight / 4f;
        float width = x * 2;

        if (MouseUtil.isHoveringWH(x + width - bonIcon20.getWidth(BonIcon.CLOSE) - 5, y + 5, bonIcon20.getWidth(BonIcon.CLOSE), bonIcon20.getHeight(), mouseX, mouseY)) {
            if (popupMessageType == PopupMessageType.OpenUrl) {
                TextureButton.popupMessageOpenUrl.setType(-1);
            } else {
                ResourcePackButton.popupMessageDelete.setType(-1);
            }
        }

        for (PopupMessageButton popupMessageButton : popupMessageButtonList) {
            popupMessageButton.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public ResourcePackButton getResourcePackButton() {
        return resourcePackButton;
    }

    public void setResourcePackButton(ResourcePackButton resourcePackButton) {
        this.resourcePackButton = resourcePackButton;
    }

    public List<PopupMessageButton> getPopupMessageButtonList() {
        return popupMessageButtonList;
    }

    public void setPopupMessageButtonList(List<PopupMessageButton> popupMessageButtonList) {
        this.popupMessageButtonList = popupMessageButtonList;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void update(float displayWidth, float displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
    }

    public PopupMessageType getPopupMessageType() {
        return popupMessageType;
    }

    public void setPopupMessageType(PopupMessageType popupMessageType) {
        this.popupMessageType = popupMessageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(float displayWidth) {
        this.displayWidth = displayWidth;
    }

    public float getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(float displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
