package me.hannsi.melyclient.util.render.render2D;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Render2DUtil implements InterfaceMinecraft {
    public static void drawPlayerHeadImage(int x, int y, int width, int height, AbstractClientPlayer entity) {
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(entity.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64, 64);
        GlStateManager.popMatrix();
    }

    public static void drawCustomImage(float x, float y, float width, float height, ResourceLocation image) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glPopMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0D).tex(u * f, (v + height) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0D).tex((u + width) * f, (v + height) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0D).tex((u + width) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawOutLineGradientRect(float x1, float y1, float x2, float y2, float lineWidth, Color leftTop, Color rightTop, Color leftBottom, Color rightBottom) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.glLineWidth(lineWidth);
        bufferbuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(leftTop);
        bufferbuilder.pos(x1, y1, 0.0D).endVertex();
        Render2DUtil.glsmColor(rightTop);
        bufferbuilder.pos(x1, y2, 0.0D).endVertex();
        Render2DUtil.glsmColor(rightBottom);
        bufferbuilder.pos(x2, y2, 0.0D).endVertex();
        Render2DUtil.glsmColor(leftBottom);
        bufferbuilder.pos(x2, y1, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawOutLineGradientRectWH(float x, float y, float width, float height, float lineWidth, Color leftTop, Color rightTop, Color leftBottom, Color rightBottom) {
        drawOutLineGradientRect(x, y, x + width, y + height, lineWidth, leftTop, rightTop, leftBottom, rightBottom);
    }

    public static void drawOutLineRect(float x1, float y1, float x2, float y2, float lineWidth, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        glsmColor(color);
        GlStateManager.glLineWidth(lineWidth);
        bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(x1, y2, 0.0).endVertex();
        bufferBuilder.pos(x2, y2, 0.0).endVertex();
        bufferBuilder.pos(x2, y1, 0.0).endVertex();
        bufferBuilder.pos(x1, y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawOutLineRectWH(float x, float y, float width, float height, float lineWidth, Color color) {
        drawOutLineRect(x, y, x + width, y + height, lineWidth, color);
    }

    public static void drawRect(float x1, float y1, float x2, float y2, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        glsmColor(color);
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x1, y2, 0.0).endVertex();
        bufferbuilder.pos(x2, y2, 0.0).endVertex();
        bufferbuilder.pos(x2, y1, 0.0).endVertex();
        bufferbuilder.pos(x1, y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectWH(float x, float y, float width, float height, Color color) {
        drawRect(x, y, x + width, y + height, color);
    }

    public static void drawGradientRect(float x1, float y1, float x2, float y2, Color leftTop, Color rightTop, Color leftBottom, Color rightBottom) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(leftTop);
        bufferBuilder.pos(x1, y2, 0.0).endVertex();
        Render2DUtil.glsmColor(rightTop);
        bufferBuilder.pos(x2, y2, 0.0).endVertex();
        Render2DUtil.glsmColor(rightBottom);
        bufferBuilder.pos(x2, y1, 0.0).endVertex();
        Render2DUtil.glsmColor(leftBottom);
        bufferBuilder.pos(x1, y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRectWH(float x, float y, float width, float height, Color leftTop, Color rightTop, Color leftBottom, Color rightBottom) {
        drawGradientRect(x, y, x + width, y + height, leftTop, rightTop, leftBottom, rightBottom);
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

    public static void drawRoundedRectWH(float x, float y, float width, float height, float radius, float slice, Color color) {
        drawRoundedRect(x, y, x + width, y + height, radius, slice, color);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, float slice, Color color) {
        drawRoundedRect(x, y, x1, y1, radius, slice, true, true, true, true, color);
    }

    public static void drawRoundedRectWH(float x, float y, float width, float height, float radius, float slice, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        drawRoundedRect(x, y, x + width, y + height, radius, slice, xy, x1y, x1y1, xy1, color);
    }

    public static void drawRoundedRectWH(float x, float y, float width, float height, float radius, Color color) {
        drawRoundedRect(x, y, x + width, y + height, radius, 1, color);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, Color color) {
        drawRoundedRect(x, y, x1, y1, radius, 1, true, true, true, true, color);
    }

    public static void drawRoundedRectWH(float x, float y, float width, float height, float radius, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        drawRoundedRect(x, y, x + width, y + height, radius, 1, xy, x1y, x1y1, xy1, color);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, float slice, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(color);

        float i;
        if (xy) {
            for (i = 0; i <= 90; i += slice) {
                bufferBuilder.pos(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D, 0.0).endVertex();
            }
        } else {
            bufferBuilder.pos(x, y, 0.0).endVertex();
        }
        if (xy1) {
            for (i = 90; i <= 180; i += slice) {
                bufferBuilder.pos(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D, 0.0).endVertex();
            }
        } else {
            bufferBuilder.pos(x, y1, 0.0).endVertex();
        }
        if (x1y1) {
            for (i = 0; i <= 90; i += slice) {
                bufferBuilder.pos(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius, 0.0).endVertex();
            }
        } else {
            bufferBuilder.pos(x1, y1, 0.0).endVertex();
        }
        if (x1y) {
            for (i = 90; i <= 180; i += slice) {
                bufferBuilder.pos(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius, 0.0).endVertex();
            }
        } else {
            bufferBuilder.pos(x1, y, 0.0).endVertex();
        }

        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawOutLineRoundedRectWH(float x, float y, float width, float height, float lineWidth, float radius, Color color) {
        drawOutLineRoundedRect(x, y, x + width, y + height, lineWidth, radius, 1, color);
    }

    public static void drawOutLineRoundedRect(float x, float y, float x1, float y1, float lineWidth, float radius, float slice, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.glLineWidth(lineWidth);
        bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(color);

        float i;
        for (i = 0; i <= 90; i += slice) {
            bufferBuilder.pos(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D, 0.0).endVertex();
        }
        for (i = 90; i <= 180; i += slice) {
            bufferBuilder.pos(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D, 0.0).endVertex();
        }
        for (i = 0; i <= 90; i += slice) {
            bufferBuilder.pos(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius, 0.0).endVertex();
        }
        for (i = 90; i <= 180; i += slice) {
            bufferBuilder.pos(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius, 0.0).endVertex();
        }

        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLine(float x, float y, float x1, float y1, float width, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.glLineWidth(width);
        bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(color);
        bufferBuilder.pos(x, y, 0.0).endVertex();
        bufferBuilder.pos(x1, y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLineWH(float x, float y, float x1, float y1, float width, Color color) {
        drawLine(x, y, x + x1, y + y1, width, color);
    }

    public static void drawPoint(float x, float y, float size, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferBuilder.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(color);
        bufferBuilder.pos(x, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircle(float x, float y, float radius, float startingAngleDeg, float endAngleDeg, float slices, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        Render2DUtil.glsmColor(color);
        for (float i = startingAngleDeg; i <= endAngleDeg; i += slices) {
            final double x2 = Math.sin(((i * Math.PI) / 180)) * radius;
            final double y2 = Math.cos(((i * Math.PI) / 180)) * radius;
            bufferBuilder.pos(x + x2, y + y2, 0.0).endVertex();
        }
        tessellator.draw();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircle(float x, float y, float radius, float startingAngleDeg, float endAngleDeg, Color color) {
        drawCircle(x, y, radius, startingAngleDeg, endAngleDeg, 1, color);
    }

    public static void drawCircle(float x, float y, float radius, float slices, Color color) {
        drawCircle(x, y, radius, 0, 360, slices, color);
    }

    public static void drawCircle(float x, float y, float radius, Color color) {
        drawCircle(x, y, radius, 0, 360, 1, color);
    }
}
