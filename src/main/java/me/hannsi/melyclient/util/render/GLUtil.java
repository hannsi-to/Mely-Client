package me.hannsi.melyclient.util.render;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.render2D.Render2DUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;

public class GLUtil implements InterfaceMinecraft {
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
        StencilUtil.nvgWrite();
        NVGRenderUtil.drawRoundedRect(x, y, x + width, y + height, round, new Color(255, 255, 255, 255));
        StencilUtil.nvgErase(invert);
    }

    public static void nvgPushScissor(float x, float y, float width, float height, boolean invert) {
        StencilUtil.nvgWrite();
        NVGRenderUtil.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 255));
        StencilUtil.nvgErase(invert);
    }

    public static void glPushScissor(float x, float y, float width, float height, float round, boolean invert) {
        StencilUtil.glWrite(false);
        Render2DUtil.drawRoundedRect(x, y, x + width, y + height, round, new Color(255, 255, 255, 255));
        StencilUtil.glErase(invert);
    }

    public static void glPushScissor(float x, float y, float width, float height, boolean invert) {
        StencilUtil.glWrite(false);
        Render2DUtil.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 255));
        StencilUtil.glErase(invert);
    }

    public static void nvgPopScissor() {
        StencilUtil.nvgDispose();
    }

    public static void glPopScissor() {
        StencilUtil.glDispose();
    }

    public static void gl11PushScissor(float x, float y, float width, float height){
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
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

    public static void gl11PopScissor(){
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void glPop(){
        GlStateManager.popMatrix();
    }

    public static void nvgPop(){
        NVGRenderUtil.nvgFramePop();
    }

    public static void pop(boolean nanoVG){
        glPop();

        if(nanoVG){
            nvgPop();
        }
    }

    public static void glPush(){
        GlStateManager.pushMatrix();
    }

    public static void nvgPush(){
        NVGRenderUtil.nvgFramePush();
    }

    public static void push(boolean nanoVG){
        if(nanoVG){
            nvgPush();
        }

        glPush();
    }

    public static void glScale(float x, float y, float z){
        GlStateManager.scale(x, y, z);
    }

    public static void nvgScale(float x, float y){
        NanoVG.nvgScale(nvg,x,y);
    }

    public static void scale(float x, float y, float z, boolean nanoVG) {
        glScale(x, y, z);

        if (nanoVG) {
            nvgScale(x, y);
        }
    }

    public static void scale(float x,float y,boolean nanoVG){
        scale(x,y,0,nanoVG);
    }

    public static void glTranslate(float x, float y, float z) {
        GlStateManager.translate(x, y, z);
    }

    public static void nvgTranslate(float x, float y) {
        NanoVG.nvgTranslate(nvg, x, y);
    }

    public static void translate(float x, float y, float z, boolean nanoVG) {
        glTranslate(x, y, z);

        if (nanoVG) {
            nvgTranslate(x, y);
        }
    }

    public static void translate(float x, float y, boolean nanoVG) {
        translate(x, y, 0, nanoVG);
    }
}
