package me.hannsi.melyclient.util.render;

import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.render2D.Render2DUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;
import static net.minecraft.client.renderer.GlStateManager.colorMask;
import static org.lwjgl.opengl.GL11.*;

public class StencilUtil {
    public static void nvgDispose() {
        NanoVG.nvgClosePath(nvg);
        NanoVG.nvgGlobalCompositeOperation(nvg, NanoVG.NVG_SOURCE_OVER);
        NanoVG.nvgClosePath(nvg);
    }

    public static void glDispose() {
        glDisable(GL_STENCIL_TEST);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        GlStateManager.popMatrix();
    }

    public static void nvgErase(boolean invert) {
        NanoVG.nvgPathWinding(nvg, NanoVG.NVG_HOLE);
        NanoVG.nvgGlobalCompositeOperation(nvg, invert ? NanoVG.NVG_XOR : NanoVG.NVG_DESTINATION_IN);
        NanoVG.nvgBeginPath(nvg);
    }

    public static void glErase(boolean invert) {
        glStencilFunc(invert ? GL_EQUAL : GL_NOTEQUAL, 1, 65535);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        colorMask(true, true, true, true);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        glAlphaFunc(GL_GREATER, 0.0f);
    }

    public static void nvgWrite() {
        NanoVG.nvgBeginPath(nvg);
    }

    public static void glWrite(boolean renderClipLayer) {
        GL11.glPushMatrix();
        GlStateManager.pushMatrix();
        checkSetupFBO(Minecraft.getMinecraft().getFramebuffer());
        glClearStencil(0);
        glClear(GL_STENCIL_BUFFER_BIT);
        glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_ALWAYS, 1, 65535);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        if (!renderClipLayer) {
            colorMask(false, false, false, false);
        } else {
            colorMask(true, true, true, true);
        }
    }

    public static void checkSetupFBO(Framebuffer framebuffer) {
        if (framebuffer != null) {
            if (framebuffer.depthBuffer > -1) {
                setupFBO(framebuffer);
                framebuffer.depthBuffer = -1;
            }
        }
    }

    public static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
    }

    public static void nvgScissor(Runnable draw, float x, float y, float width, float height, float round, boolean invert) {
        nvgPushScissor(x, y, width, height, round, invert);
        draw.run();
        nvgPopScissor();
    }

    public static void nvgScissor(Runnable draw, float x, float y, float width, float height, boolean invert) {
        nvgPushScissor(x, y, width, height, invert);
        draw.run();
        nvgPopScissor();
    }

    public static void glScissor(Runnable draw, float x, float y, float width, float height, float round, boolean invert) {
        glPushScissor(x, y, width, height, round, invert);
        draw.run();
        glPopScissor();
    }

    public static void glScissor(Runnable draw, float x, float y, float width, float height, boolean invert) {
        glPushScissor(x, y, width, height, invert);
        draw.run();
        glPopScissor();
    }

    public static void nvgPushScissor(float x, float y, float width, float height, float round, boolean invert) {
        nvgWrite();
        NanoVGRenderUtil.drawRoundedRect(x, y, x + width, y + height, round, new Color(255, 255, 255, 255));
        nvgErase(invert);
    }

    public static void nvgPushScissor(float x, float y, float width, float height, boolean invert) {
        nvgWrite();
        NanoVGRenderUtil.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 255));
        nvgErase(invert);
    }

    public static void glPushScissor(float x, float y, float width, float height, float round, boolean invert) {
        glWrite(false);
        Render2DUtil.drawRoundedRect(x, y, x + width, y + height, round, new Color(255, 255, 255, 255));
        glErase(invert);
    }

    public static void glPushScissor(float x, float y, float width, float height, boolean invert) {
        glWrite(false);
        Render2DUtil.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 255));
        glErase(invert);
    }

    public static void nvgPopScissor() {
        nvgDispose();
    }

    public static void glPopScissor() {
        glDispose();
    }

    public static void gl11PushScissor(float x, float y, float width, float height) {
        glEnable(GL_SCISSOR_TEST);
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int guiScale = mc.gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }
        while (scaleFactor < guiScale && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glScissor((int) (x * scaleFactor), (int) (mc.displayHeight - (y + height) * scaleFactor), (int) (width * scaleFactor), (int) (height * scaleFactor));
    }

    public static void gl11PopScissor() {
        glDisable(GL_SCISSOR_TEST);
    }
}