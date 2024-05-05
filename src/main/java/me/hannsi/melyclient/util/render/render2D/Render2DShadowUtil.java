package me.hannsi.melyclient.util.render.render2D;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.render.render2D.blur.GaussianFilter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Render2DShadowUtil implements InterfaceMinecraft {
    private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();

    public static void drawShadow(float x1, float y1, float x2, float y2, int blurRadius, Color color) {
        drawShadowWH(x1, y1, x2 - x1, y2 - y1, blurRadius, color);
    }

    public static void drawShadowWH(float x, float y, float width, float height, int blurRadius, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        float _X = x - 0.25f;
        float _Y = y + 0.25f;

        int identifier = (int) (width * height + width + color.getRGB() * blurRadius + blurRadius);

        GlStateManager.enableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

        int texId;
        if (shadowCache.containsKey(identifier)) {
            texId = shadowCache.get(identifier);

            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0) width = 1;
            if (height <= 0) height = 1;
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);

            Graphics g = original.getGraphics();
            g.setColor(color);
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();

            GaussianFilter op = new GaussianFilter(blurRadius);

            BufferedImage blurred = op.filter(original, null);


            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);

            shadowCache.put(identifier, texId);
        }

        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); // top left
        GL11.glVertex2f(_X, _Y);

        GL11.glTexCoord2f(0, 1); // bottom left
        GL11.glVertex2f(_X, _Y + height);

        GL11.glTexCoord2f(1, 1); // bottom right
        GL11.glVertex2f(_X + width, _Y + height);

        GL11.glTexCoord2f(1, 0); // top right
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();

        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
