package me.hannsi.melyclient.gui.guiScreen.mainMenu;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.guiScreen.mainMenu.page.DesignPage;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.nanovg.render.Font;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.render2D.Render2DUtil;
import me.hannsi.melyclient.util.render.shader.GLSLSandboxShader;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.animation.Easing;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class CustomGuiMainMenu extends GuiScreen {
    public static GLSLSandboxShader backgroundShader = MelyClient.shaderUtil.wave1;
    EasingUtil easeOutExpo1 = new EasingUtil(Easing.easeOutExpo);
    EasingUtil easeOutExpo2 = new EasingUtil(Easing.easeOutExpo);
    DesignPage designPage;
    int page = 0;
    Font ubuntu = MelyClient.fontManager.ubuntu;
    Font bonIcon = MelyClient.fontManager.bonIcon;
    float nextButtonBlur1 = 0.0f;
    float nextButtonBlur2 = 0.0f;

    @Override
    public void initGui() {
        designPage = new DesignPage(this.width, this.height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        NVGRenderUtil.nvgFramePush();
        MelyClient.shaderUtil.drawShader(backgroundShader, mouseX, mouseY);

        //Render2DShadowUtil.drawShadowWH(this.width / 6f, this.height / 6f, (this.width / 6f) * 4, (this.height / 6f) * 4,10,new MColor(0,0,0,255));
        Render2DUtil.drawRoundedRectWH(this.width / 6f, this.height / 6f, (this.width / 6f) * 4, (this.height / 6f) * 4, 10, new Color(20, 20, 20, 200));

        GLUtil.translate((((this.width / 6f) * 5) - ubuntu.getWidth("Next", 15) - bonIcon.getWidth("S   ", 15)), (((this.height / 6f) * 5) - ubuntu.getHeight(15) - bonIcon.getHeight(15)), true);
        ubuntu.drawText("Next", 0, 0, 15, new Color(255, 255, 255, 255), nextButtonBlur1, ColorUtil.getRainbow(50, 255, 255));
        bonIcon.drawText("S", ubuntu.getWidth("Next ", 15), 0, 15, new Color(255, 255, 255, 255), nextButtonBlur1, ColorUtil.getRainbow(50, 255, 255));
        GLUtil.translate(-(((this.width / 6f) * 5) - ubuntu.getWidth("Next", 15) - bonIcon.getWidth("S  ", 15)), -(((this.height / 6f) * 5) - ubuntu.getHeight(15) - bonIcon.getHeight(15)), true);

        bonIcon.drawText("   R", this.width / 6f, this.height / 6f + 10, 15, new Color(255, 255, 255, 255), nextButtonBlur2, ColorUtil.getRainbow(50, 255, 255));
        ubuntu.drawText(" Back", this.width / 6f + bonIcon.getWidth("   R", 15), this.height / 6f + 10, 15, new Color(255, 255, 255, 255), nextButtonBlur2, ColorUtil.getRainbow(50, 255, 255));

        if (MouseUtil.isHoveringWH(((this.width / 6f + bonIcon.getWidth("R", 15))), ((this.height / 6f + 10)), ubuntu.getWidth(" Back", 15) + bonIcon.getWidth("R", 15), ubuntu.getHeight(15), mouseX, mouseY)) {
            if (nextButtonBlur2 + 1.0f < 10.0f) {
                nextButtonBlur2 += 1.0f;
            } else {
                nextButtonBlur2 = 10.0f;
            }
        } else {
            if (nextButtonBlur2 - 1.0f > 0.0f) {
                nextButtonBlur2 -= 1.0f;
            } else {
                nextButtonBlur2 = 0.0f;
            }
        }

        if (MouseUtil.isHoveringWH((((this.width / 6f) * 5) - ubuntu.getWidth("Next ", 15) - bonIcon.getWidth("S   ", 15)), (((this.height / 6f) * 5) - ubuntu.getHeight(15) - bonIcon.getHeight(15)), ubuntu.getWidth("Next ", 15) + bonIcon.getWidth("S", 15), ubuntu.getHeight(15), mouseX, mouseY)) {
            if (nextButtonBlur1 + 1.0f < 10.0f) {
                nextButtonBlur1 += 1.0f;
            } else {
                nextButtonBlur1 = 10.0f;
            }
        } else {
            if (nextButtonBlur1 - 1.0f > 0.0f) {
                nextButtonBlur1 -= 1.0f;
            } else {
                nextButtonBlur1 = 0.0f;
            }
        }

        easeOutExpo1.setReverse(true);
        float width = easeOutExpo1.get(1000);
        MelyClient.logger.info(width);

        //Design
        GlStateManager.pushMatrix();
        GLUtil.translate(0, 0, true);
        designPage.drawScreen(mouseX, mouseY, partialTicks);
        GLUtil.nvgTranslate(0, 0);
        GlStateManager.popMatrix();

        NVGRenderUtil.nvgFramePop();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.isHoveringWH((((this.width / 6f) * 5) - ubuntu.getWidth("Next ", 15) - bonIcon.getWidth("S   ", 15)), (((this.height / 6f) * 5) - ubuntu.getHeight(15) - bonIcon.getHeight(15)), ubuntu.getWidth("Next ", 15) + bonIcon.getWidth("S", 15), ubuntu.getHeight(15), mouseX, mouseY)) {
            easeOutExpo1.reset();
            page++;
        }
        if (MouseUtil.isHoveringWH(((this.width / 6f + bonIcon.getWidth("R", 15))), ((this.height / 6f + 10)), ubuntu.getWidth(" Back", 15) + bonIcon.getWidth("R", 15), ubuntu.getHeight(15), mouseX, mouseY)) {
            if (page != 0) {
                page--;
            }
        }

        if (page == 0) {
            designPage.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
}