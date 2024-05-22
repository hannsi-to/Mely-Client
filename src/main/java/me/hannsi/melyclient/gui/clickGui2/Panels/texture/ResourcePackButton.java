package me.hannsi.melyclient.gui.clickGui2.Panels.texture;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.StencilUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.render.render2D.texture.Render2DTextureUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.ListUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class ResourcePackButton {
    public static PopupMessage popupMessageDelete;
    public float x;
    public float y;
    public ResourcePackRepository.Entry resourcePackRepositoryEntry;
    public ResourceLocation resourceLocation;
    public boolean selected;
    public float displayWidth;
    public float displayHeight;
    public float borderWidth;
    public float resourcePackImageSize;
    public boolean clickCheck;
    public TimerUtil timerUtil;

    public ResourcePackButton(float x, float y, ResourcePackRepository.Entry resourcePackRepositoryEntry, boolean selected, float displayWidth, float displayHeight) {
        this.x = x;
        this.y = y;
        this.resourcePackRepositoryEntry = resourcePackRepositoryEntry;
        this.resourceLocation = Render2DTextureUtil.getResourceLocationFromResourcePackRepositoryEntry(resourcePackRepositoryEntry);
        this.selected = selected;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.clickCheck = false;
        this.timerUtil = new TimerUtil();
        update(displayWidth, displayHeight);
        popupMessageDelete = new PopupMessage(PopupMessageType.Delete, "Delete check", displayWidth, displayHeight, null);
    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil bonIcon14 = new FontUtil(MelyClient.fontManager.bonIcon, 14);
        FontUtil bonIcon20 = new FontUtil(MelyClient.fontManager.bonIcon, 20);

        if (timerUtil.passed(100)) {
            timerUtil.reset();

            if (popupMessageDelete.getType() == 0) {
                popupMessageDelete.mouseClicked(mouseX, mouseY, mouseButton);
            }

            int index = ListUtil.findValueInInt(Texture.availableResourcePacks, this);
            if ((popupMessageDelete.getType() != 0 && TextureButton.popupMessageOpenUrl.getType() != 0)) {
                if (MouseUtil.isHoveringWH(x + borderWidth - 60 + 5, y + this.displayHeight / 10f - bonIcon20.getHeight(), bonIcon20.getWidth(BonIcon.ARROW_DOWNWARD), bonIcon20.getHeight(), mouseX, mouseY)) {
                    if (!ListUtil.isOutOfBounce(Texture.availableResourcePacks, index + 1)) {
                        ListUtil.changeNext(Texture.availableResourcePacks, index);
                    }
                    return;
                }
                if (MouseUtil.isHoveringWH(x + borderWidth - 60 + 5, y + 2, bonIcon20.getWidth(BonIcon.ARROW_UPWARD), bonIcon20.getHeight(), mouseX, mouseY)) {
                    if (!ListUtil.isOutOfBounce(Texture.availableResourcePacks, index - 1)) {
                        ListUtil.changeBack(Texture.availableResourcePacks, index);
                    }
                    return;
                }
                if (MouseUtil.isHoveringWH(x + borderWidth - bonIcon14.getWidth(BonIcon.DELETE), y + resourcePackImageSize - bonIcon14.getHeight(), bonIcon20.getWidth(BonIcon.DELETE), bonIcon20.getHeight(), mouseX, mouseY)) {
                    popupMessageDelete.setResourcePackButton(this);
                    popupMessageDelete.setType(0);
                    return;
                }

                if (MouseUtil.isHoveringWH(x, y, borderWidth, resourcePackImageSize, mouseX, mouseY)) {
                    selected = !selected;
                }
            }
        }
    }

    public float drawScreen(float mouseX, float mouseY, float partialTicks) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil notoSansJPRegular15 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 15);
        FontUtil notoSansJPRegular10 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 10);
        FontUtil bonIcon14 = new FontUtil(MelyClient.fontManager.bonIcon, 14);
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil bonIcon20 = new FontUtil(MelyClient.fontManager.bonIcon, 20);

        float maxResourcePackNameWidth = 0.0f;
        for (ResourcePackButton resourcePackButton : Texture.availableResourcePacks) {
            float resourcePackNameWidth = notoSansJPRegular15.getWidth(resourcePackButton.getResourcePackRepositoryEntry().getResourcePackName());
            if (maxResourcePackNameWidth < resourcePackNameWidth) {
                maxResourcePackNameWidth = resourcePackNameWidth;
            }
        }

        float returnOffsetY = 0.0f;
        resourcePackImageSize = this.displayHeight / 10f;
        borderWidth = resourcePackImageSize + 100 + maxResourcePackNameWidth;

        NanoVG.nvgScissor(nvg, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 3 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2, displayWidth, displayWidth);
        NanoVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, borderWidth + 2, resourcePackImageSize + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

        NanoVGRenderUtil.drawRectWH(x, y, borderWidth, resourcePackImageSize, new Color(0, 0, 0, 255));

        if (MouseUtil.isHoveringWH(x, y, borderWidth, resourcePackImageSize, mouseX, mouseY)) {
            NanoVGRenderUtil.drawOutLineRectWH(x, y, borderWidth, resourcePackImageSize, 1.0f, new Color(91, 91, 91, 255));

            NanoVGRenderUtil.drawRadialGradientRect(x, y, borderWidth, resourcePackImageSize, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
        }

        notoSansJPRegular15.drawText(resourcePackRepositoryEntry.getResourcePackName(), x + resourcePackImageSize + 5, y + 5, new Color(255, 255, 255, 255), true);

        float texturePackDescriptionOffsetY = 0.0f;
        for (String text : StringUtil.splitln(resourcePackRepositoryEntry.getTexturePackDescription())) {
            notoSansJPRegular10.drawText(text, x + resourcePackImageSize + 5, y + notoSansJPRegular15.getHeight() + 5 + texturePackDescriptionOffsetY, new Color(91, 91, 91, 255), true);

            texturePackDescriptionOffsetY += notoSansJPRegular10.getHeight() + 2;
        }
        NanoVG.nvgResetScissor(nvg);

        ClickGui2.nanoVGEnd();
        ClickGui2.openGLStart();

        GLUtil.push();
        StencilUtil.gl11PushScissor(ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 3 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2, displayWidth, displayHeight);
        Render2DTextureUtil.drawCustomImage(x, y, resourcePackImageSize, resourcePackImageSize, resourceLocation);
        StencilUtil.gl11PopScissor();
        GLUtil.pop();

        returnOffsetY += resourcePackImageSize;

        ClickGui2.openGLEnd();
        ClickGui2.nanoVGStart();
        NanoVG.nvgScissor(nvg, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 3 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2, displayWidth, displayWidth);
        NanoVGRenderUtil.drawRoundedRectWH(x + borderWidth - 30, y + returnOffsetY / 2f - 5, 20, 10, 5, new Color(40, 40, 40, 255));
        NanoVGRenderUtil.drawCircle(x + borderWidth - 30 + 5 + (selected ? 10 : 0), y + returnOffsetY / 2f - 5 + (10 / 2f), 5, selected ? new Color(100, 100, 200, 255) : new Color(255, 255, 255, 255));

        bonIcon20.drawText(BonIcon.ARROW_UPWARD, x + borderWidth - 60 + 5, y + 2, new Color(255, 255, 255, 255));

        bonIcon20.drawText(BonIcon.ARROW_DOWNWARD, x + borderWidth - 60 + 5, y + returnOffsetY - bonIcon20.getHeight(), new Color(255, 255, 255, 255));

        bonIcon14.drawText(BonIcon.DELETE, x + borderWidth - bonIcon14.getWidth(BonIcon.DELETE), y + returnOffsetY - bonIcon14.getHeight(), new Color(255, 60, 60, 255));
        NanoVG.nvgResetScissor(nvg);

        popupMessageDelete.drawScreen(mouseX, mouseY, partialTicks);
        popupMessageDelete.update(displayWidth, displayHeight);

        return returnOffsetY;
    }

    public void update(float displayWidth, float displayHeight) {
        setDisplayWidth(displayWidth);
        setDisplayHeight(displayHeight);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public float getResourcePackImageSize() {
        return resourcePackImageSize;
    }

    public void setResourcePackImageSize(float resourcePackImageSize) {
        this.resourcePackImageSize = resourcePackImageSize;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
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

    public ResourcePackRepository.Entry getResourcePackRepositoryEntry() {
        return resourcePackRepositoryEntry;
    }

    public void setResourcePackRepositoryEntry(ResourcePackRepository.Entry resourcePackRepositoryEntry) {
        this.resourcePackRepositoryEntry = resourcePackRepositoryEntry;
    }
}
