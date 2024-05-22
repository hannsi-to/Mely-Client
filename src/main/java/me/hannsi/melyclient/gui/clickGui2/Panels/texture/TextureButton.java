package me.hannsi.melyclient.gui.clickGui2.Panels.texture;

import com.google.common.collect.Lists;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.ResourcePackRepository;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextureButton {
    public static PopupMessage popupMessageOpenUrl;
    public String name;
    public float x;
    public float y;
    public float displayWidth;
    public float displayHeight;
    public float maxBorderWidth;
    public float maxBorderHeight;

    public TextureButton(String name, float x, float y, float displayWidth, float displayHeight) {
        this.name = name;
        this.x = x;
        this.y = y;
        popupMessageOpenUrl = new PopupMessage(PopupMessageType.OpenUrl, "Open URL check", displayWidth, displayHeight, null);
        update(displayWidth, displayHeight);
    }

    public float drawScreen(float mouseX, float mouseY, float particleTicks) {
        FontUtil notoSansJPRegular15 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 15);

        float returnOffsetY = 0.0f;

        maxBorderWidth = 0.0f;
        for (TextureButton textureButton : Texture.textureButtons) {
            float textWidth = notoSansJPRegular15.getWidth(textureButton.getName());
            if (maxBorderWidth < textWidth) {
                maxBorderWidth = textWidth;
            }
        }
        maxBorderWidth += 10f;

        maxBorderHeight = notoSansJPRegular15.getHeight() + 10;

        NanoVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, maxBorderWidth + 2, maxBorderHeight + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

        NanoVGRenderUtil.drawRectWH(x, y, maxBorderWidth, maxBorderHeight, new Color(0, 0, 0, 255));

        if (MouseUtil.isHoveringWH(x, y, maxBorderWidth, maxBorderHeight, mouseX, mouseY)) {
            NanoVGRenderUtil.drawOutLineRectWH(x, y, maxBorderWidth, maxBorderHeight, 1.0f, new Color(91, 91, 91, 255));

            NanoVGRenderUtil.drawRadialGradientRect(x, y, maxBorderWidth, maxBorderHeight, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
        }

        notoSansJPRegular15.drawText(name, x + 5, y + 5, new Color(255, 255, 255, 255));
        returnOffsetY += maxBorderHeight;

        popupMessageOpenUrl.drawScreen(mouseX, mouseY, particleTicks);
        popupMessageOpenUrl.update(displayWidth, displayHeight);

        return returnOffsetY;
    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        popupMessageOpenUrl.mouseClicked(mouseX, mouseY, mouseButton);

        if ((ResourcePackButton.popupMessageDelete.getType() != 0 && TextureButton.popupMessageOpenUrl.getType() != 0)) {
            if (MouseUtil.isHoveringWH(x, y, maxBorderWidth, maxBorderHeight, mouseX, mouseY)) {
                switch (name) {
                    case "Done":
                        changeTexture();
                        break;
                    case "OpenFolder":
                        File file = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks();
                        OpenGlHelper.openFile(file);
                        break;
                    case "OpenModrinth":
                        popupMessageOpenUrl.setType(0);
                        break;
                    case "Reload":
                        Texture.availableResourcePacks = new CopyOnWriteArrayList<>();

                        ResourcePackRepository resourcepackrepository = Minecraft.getMinecraft().getResourcePackRepository();
                        resourcepackrepository.updateRepositoryEntriesAll();
                        List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
                        list.removeAll(resourcepackrepository.getRepositoryEntries());

                        ResourcePackRepository.Entry resourcePackRepository2 = resourcepackrepository.getResourcePackEntry();
                        if (resourcePackRepository2 != null) {
                            Texture.availableResourcePacks.add(new ResourcePackButton(0, 0, resourcePackRepository2, true, displayWidth, displayHeight));
                        }

                        for (ResourcePackRepository.Entry resourcePackRepository : Lists.reverse(resourcepackrepository.getRepositoryEntries())) {
                            Texture.availableResourcePacks.add(new ResourcePackButton(0, 0, resourcePackRepository, true, displayWidth, displayHeight));
                        }

                        for (ResourcePackRepository.Entry resourcePackRepository : list) {
                            Texture.availableResourcePacks.add(new ResourcePackButton(0, 0, resourcePackRepository, false, displayWidth, displayHeight));
                        }
                        break;
                }
            }
        }
    }

    public void changeTexture() {
        Minecraft mc = Minecraft.getMinecraft();
        List<ResourcePackRepository.Entry> list = new ArrayList<>();

        for (ResourcePackButton resourcePackButton : Texture.availableResourcePacks) {
            if (resourcePackButton.isSelected()) {
                list.add(resourcePackButton.getResourcePackRepositoryEntry());
            }
        }

        Collections.reverse(list);
        mc.getResourcePackRepository().setRepositories(list);
        mc.gameSettings.resourcePacks.clear();
        mc.gameSettings.incompatibleResourcePacks.clear();

        for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
            mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());

            if (resourcepackrepository$entry.getPackFormat() != 3) {
                mc.gameSettings.incompatibleResourcePacks.add(resourcepackrepository$entry.getResourcePackName());
            }
        }

        mc.gameSettings.saveOptions();
        mc.refreshResources();
    }

    public void update(float displayWidth, float displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
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
}
