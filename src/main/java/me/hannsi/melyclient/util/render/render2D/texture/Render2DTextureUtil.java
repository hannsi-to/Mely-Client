package me.hannsi.melyclient.util.render.render2D.texture;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Render2DTextureUtil implements InterfaceMinecraft {
    public static ResourceLocation getResourceLocationFromResourcePackRepositoryEntry(ResourcePackRepository.Entry entry) {
        ResourceLocation resourceLocation = null;
        IResourcePack resourcePack = entry.getResourcePack();
        try {
            BufferedImage packImage = resourcePack.getPackImage();
            DynamicTexture dynamicTexture = new DynamicTexture(packImage);
            resourceLocation = mc.getTextureManager().getDynamicTextureLocation("pack_image", dynamicTexture);
        } catch (IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return resourceLocation;
    }

    public static void drawPlayerHeadImage(float x, float y, float width, float height, AbstractClientPlayer entity) {
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(entity.getLocationSkin());
        drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64, 64);
        GlStateManager.popMatrix();
    }

    public static void drawPlayerHeadImage(float x, float y, float width, float height, ResourceLocation resourceLocation) {
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(resourceLocation);
        drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64, 64);
        GlStateManager.popMatrix();
    }

    public static void drawCustomImage(float x, float y, float width, float height, ResourceLocation image) {
        GL11.glPushMatrix();
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

    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0D).tex(u * f, (v + vHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0D).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0D).tex((u + uWidth) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }
}
