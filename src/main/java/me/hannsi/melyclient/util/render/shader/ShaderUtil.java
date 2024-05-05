package me.hannsi.melyclient.util.render.shader;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

public class ShaderUtil implements InterfaceMinecraft {
    public final GLSLSandboxShader wave1;
    public final GLSLSandboxShader wave2;
    public final GLSLSandboxShader shader2;
    private final long initTime;

    public ShaderUtil() {
        try {
            wave1 = new GLSLSandboxShader(new ResourceLocation("mely/render/shader/shaders/mainmenu/wave1.fsh"));
            wave2 = new GLSLSandboxShader(new ResourceLocation("mely/render/shader/shaders/mainmenu/wave2.fsh"));
            shader2 = new GLSLSandboxShader(new ResourceLocation("mely/render/shader/shaders/shader2.fsh"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initTime = System.currentTimeMillis();
    }

    public void drawShader(GLSLSandboxShader shader, int x, int y, int width, int height, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        //StencilUtil.pushScissor(0,height,width,height);
        drawShader(shader, width, height, mouseX, mouseY);
        //StencilUtil.popScissor();
        GL11.glPopMatrix();
    }

    public void drawShader(GLSLSandboxShader shader, int mouseX, int mouseY) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        drawShader(shader, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), mouseX, mouseY);
    }

    public void drawShader(GLSLSandboxShader shader, int width, int height, int mouseX, int mouseY) {
        GlStateManager.disableCull();
        shader.useShader(width, height, mouseX, mouseY, (System.currentTimeMillis() - initTime) / 1000f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);
        GL11.glEnd();
        GlStateManager.enableCull();
        GL20.glUseProgram(0);
    }

    public void unLoad() {
        MelyClient.shaderUtil = null;
    }
}
