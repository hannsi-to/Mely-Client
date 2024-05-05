package me.hannsi.melyclient.gui.guiScreen.mainMenu.page;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.guiScreen.mainMenu.CustomGuiMainMenu;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.nanovg.render.Font;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class DesignPage {
    private int selectDesignAlpha1 = 100;
    private int selectDesignAlpha2 = 100;
    private int selectTextDesignAlpha1 = 100;
    private int selectTextDesignAlpha2 = 100;

    private final float width;
    private final float height;

    public DesignPage(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Font ubuntu = MelyClient.fontManager.ubuntu;

        if (CustomGuiMainMenu.backgroundShader == MelyClient.shaderUtil.wave1) {
            if (selectDesignAlpha1 + 10 < 255) {
                selectDesignAlpha1 += 10;
            } else {
                selectDesignAlpha1 = 255;
            }
        } else {
            if (selectDesignAlpha1 - 10 > 100) {
                selectDesignAlpha1 -= 10;
            } else {
                selectDesignAlpha1 = 100;
            }
        }
        if (CustomGuiMainMenu.backgroundShader == MelyClient.shaderUtil.wave2) {
            if (selectDesignAlpha2 + 10 < 255) {
                selectDesignAlpha2 += 10;
            } else {
                selectDesignAlpha2 = 255;
            }
        } else {
            if (selectDesignAlpha2 - 10 > 100) {
                selectDesignAlpha2 -= 10;
            } else {
                selectDesignAlpha2 = 100;
            }
        }

        NVGRenderUtil.drawCustomImage((int) (width / 5 + 30), (int) (height / 2 - 50), 100, 100, new ResourceLocation("mely/img/mainmenu/design.png"));

        GlStateManager.pushMatrix();
        ubuntu.drawText("Wallpaper", (width / 5f) * 2, (height / 9f) * 3, 15, new Color(255, 255, 255, 255));
        ubuntu.drawText("Choose your design", (width / 5f) * 2, (height / 8f) * 2, 20, new Color(255, 255, 255, 255), 10.0f, ColorUtil.getRainbow(50, 255, 255));
        GLUtil.translate((width / 5f) * 2 + 10, (height / 5f) * 2, true);
        GLUtil.nvgPushScissor(0f, 0f, width / 6f, height / 6f, 5f, true);
        NVGRenderUtil.drawCustomImage(0, 0, (int) (width / 6), (int) (height / 6), new ResourceLocation("mely/img/mainmenu/wave1.png"));
        if (MouseUtil.isHoveringWH((width / 5f) * 2 + 10, (height / 5f) * 2, width / 6f, height / 6f, mouseX, mouseY)) {
            if (selectTextDesignAlpha1 + 10 < 255) {
                selectTextDesignAlpha1 += 10;
            } else {
                selectTextDesignAlpha1 = 255;
            }
        } else {
            if (selectTextDesignAlpha1 - 10 > 0) {
                selectTextDesignAlpha1 -= 10;
            } else {
                selectTextDesignAlpha1 = 0;
            }
        }
        ubuntu.drawTextCenter("Wave1", (width / 6f) / 2f, (height / 6f) / 2f, 10, new Color(255, 255, 255, selectTextDesignAlpha1));
        GLUtil.nvgPopScissor();
        NVGRenderUtil.drawRoundedRectWH(20, height / 6f + 5, width / 6f - 40, 5, 2, new Color(120, 0, 255, selectDesignAlpha1));
        GLUtil.translate(-((width / 5f) * 2 + 10), -(height / 5f) * 2, true);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GLUtil.translate((width / 5f) * 3 + 10, (height / 5f) * 2, true);
        GLUtil.nvgPushScissor(0f, 0f, width / 6f, height / 6f, 5f, true);
        NVGRenderUtil.drawCustomImage(0, 0, (int) (width / 6), (int) (height / 6), new ResourceLocation("mely/img/mainmenu/wave2.png"));
        if (MouseUtil.isHoveringWH((width / 5f) * 3 + 10, (height / 5f) * 2, width / 6f, height / 6f, mouseX, mouseY)) {
            if (selectTextDesignAlpha2 + 10 < 255) {
                selectTextDesignAlpha2 += 10;
            } else {
                selectTextDesignAlpha2 = 255;
            }
        } else {
            if (selectTextDesignAlpha2 - 10 > 0) {
                selectTextDesignAlpha2 -= 10;
            } else {
                selectTextDesignAlpha2 = 0;
            }
        }
        ubuntu.drawTextCenter("Wave2", (width / 6f) / 2f, (height / 6f) / 2f, 10, new Color(255, 255, 255, selectTextDesignAlpha2));
        GLUtil.nvgPopScissor();
        NVGRenderUtil.drawRoundedRectWH(20, height / 6f + 5, width / 6f - 40, 5, 2, new Color(120, 0, 255, selectDesignAlpha2));
        GLUtil.translate(-((width / 5f) * 3 + 10), -((height / 5f) * 2), true);
        GlStateManager.popMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.isHoveringWH((width / 5f) * 2 + 10, (height / 5f) * 2, width / 6f, height / 6f, mouseX, mouseY)) {
            CustomGuiMainMenu.backgroundShader = MelyClient.shaderUtil.wave1;
        }
        if (MouseUtil.isHoveringWH((width / 5f) * 3 + 10, (height / 5f) * 2, width / 6f, height / 6f, mouseX, mouseY)) {
            CustomGuiMainMenu.backgroundShader = MelyClient.shaderUtil.wave2;
        }
    }
}
