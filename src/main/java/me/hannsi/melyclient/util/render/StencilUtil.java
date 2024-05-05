package me.hannsi.melyclient.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.GL11;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;
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
}