package me.hannsi.melyclient.gui.clickGui2.panels.texture;

import com.google.common.collect.Lists;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.gui.clickGui2.Screen;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.file.ModsUtil;
import net.minecraft.client.resources.ResourcePackRepository;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Texture implements InterfaceMinecraft {
    public static ClickGui2.HomeButton texture;
    public static List<ResourcePackButton> availableResourcePacks;
    public static List<TextureButton> textureButtons;
    public static float scrollY;

    public static void initGui(float width, float height) {
        if (!ModsUtil.isOptifineInstalled()) {
            new DebugLog("Optifne is not installed.", DebugLevel.WARNING);
        }

        availableResourcePacks = new CopyOnWriteArrayList<>();

        ResourcePackRepository resourcepackrepository = mc.getResourcePackRepository();
        resourcepackrepository.updateRepositoryEntriesAll();
        List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
        list.removeAll(resourcepackrepository.getRepositoryEntries());

        ResourcePackRepository.Entry resourcePackRepository2 = resourcepackrepository.getResourcePackEntry();
        if (resourcePackRepository2 != null) {
            availableResourcePacks.add(new ResourcePackButton(0, 0, resourcePackRepository2, true, width, height));
        }

        for (ResourcePackRepository.Entry resourcePackRepository : Lists.reverse(resourcepackrepository.getRepositoryEntries())) {
            availableResourcePacks.add(new ResourcePackButton(0, 0, resourcePackRepository, true, width, height));
        }

        for (ResourcePackRepository.Entry resourcePackRepository : list) {
            availableResourcePacks.add(new ResourcePackButton(0, 0, resourcePackRepository, false, width, height));
        }

        textureButtons = new ArrayList<>();
        textureButtons.add(new TextureButton("Reload", 0, 0, width, height));
        textureButtons.add(new TextureButton("OpenFolder", 0, 0, width, height));
        textureButtons.add(new TextureButton("OpenModrinth", 0, 0, width, height));
        textureButtons.add(new TextureButton("Done", 0, 0, width, height));

        scrollY = 0.0f;
    }

    public static void drawScreen(int mouseX, int mouseY, float width, float height, float partialTicks) {
        FontUtil notoSansJPRegular15 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 15);
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);

        for (ClickGui2.HomeButton homeButton : ClickGui2.INSTANCE.homeButtons) {
            if (homeButton.getScreen() == Screen.Texture) {
                texture = homeButton;
                break;
            }
        }

        if (texture == null) {
            new DebugLog("Can not load texture screen.", DebugLevel.WARNING);
            return;
        }

        ubuntu15.drawText(texture.getName(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + bonIcon15.getHeight() + 5, new Color(255, 255, 255, 255));
        ubuntu10.drawText(texture.getDescription(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + (bonIcon15.getHeight() * 2) + 10, new Color(91, 91, 91, 255));

        int scrollCount = Mouse.getDWheel();

        if (scrollCount > 0) {
            if (scrollY != 0) {
                scrollY += 10;
            }
        }
        float offsetY = 10 + (bonIcon15.getHeight() * 4) + scrollY;
        for (ResourcePackButton resourcePackButton : availableResourcePacks) {
            resourcePackButton.setX(ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX);
            resourcePackButton.setY(offsetY);
            offsetY += resourcePackButton.drawScreen(mouseX, mouseY, partialTicks) + 10;
            resourcePackButton.update(width, height);
        }
        if (scrollCount < 0) {
            if (scrollY > -(offsetY - scrollY - (5 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2))) {
                scrollY -= 10;
            }
        }

        float maxBorderWidth = 0.0f;
        for (TextureButton textureButton : textureButtons) {
            float textWidth = notoSansJPRegular15.getWidth(textureButton.getName());
            if (maxBorderWidth < textWidth) {
                maxBorderWidth = textWidth;
            }
        }
        maxBorderWidth += 15f;

        float offsetY2 = 10 + (bonIcon15.getHeight() * 4);
        for (TextureButton textureButton : textureButtons) {
            textureButton.setX(width - maxBorderWidth);
            textureButton.setY(offsetY2);
            offsetY2 += textureButton.drawScreen(mouseX, mouseY, partialTicks) + 10;
            textureButton.update(width, height);
        }
    }

    public static void keyTyped(char typedChar, int keyCode) {

    }

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (ResourcePackButton resourcePackButton : availableResourcePacks) {
            resourcePackButton.mouseClicked(mouseX, mouseY, mouseButton);
        }
        for (TextureButton textureButton : textureButtons) {
            textureButton.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
}
