package me.hannsi.melyclient.util.render;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GLUtil implements InterfaceMinecraft {

    public static void pop() {
        GlStateManager.popMatrix();
    }

    public static void push() {
        GlStateManager.pushMatrix();
    }

    public static void scale(float x, float y, float z) {
        GlStateManager.scale(x, y, z);
    }

    public static void translate(float x, float y, float z) {
        GlStateManager.translate(x, y, z);
    }

    public static void glsmColor(Color color) {
        int c = color.getRGB();
        float alpha = (c >> 24 & 0xFF) / 255.0F;
        float red = (c >> 16 & 0xFF) / 255.0F;
        float green = (c >> 8 & 0xFF) / 255.0F;
        float blue = (c & 0xFF) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(Color color) {
        int c = color.hashCode();
        float alpha = (c >> 24 & 0xFF) / 255.0F;
        float red = (c >> 16 & 0xFF) / 255.0F;
        float green = (c >> 8 & 0xFF) / 255.0F;
        float blue = (c & 0xFF) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
    }
}
