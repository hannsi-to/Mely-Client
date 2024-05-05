package me.hannsi.melyclient.util.render.render2D.blur;

import me.hannsi.melyclient.mixin.net.minecraft.client.shader.IShaderGroup;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class BlurUtil {

    static TimerUtil timer = new TimerUtil();
    private final Minecraft mc = Minecraft.getMinecraft();
    protected ShaderGroup blurShader;
    protected Framebuffer buffer;
    protected int lastScale;
    protected int lastScaleWidth;
    protected int lastScaleHeight;
    private int lastFactor;
    private int lastWidth;
    private int lastHeight;

    public BlurUtil() {
        init();
    }

    public void setShaderConfigs(float intensity, float blurWidth, float blurHeight) {
        Objects.requireNonNull(((IShaderGroup) blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("Radius")).set(intensity);
        Objects.requireNonNull(((IShaderGroup) blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("Radius")).set(intensity);

        Objects.requireNonNull(((IShaderGroup) blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("BlurDir")).set(blurWidth, blurHeight);
        Objects.requireNonNull(((IShaderGroup) blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("BlurDir")).set(blurHeight, blurWidth);
    }

    public void drawBlurryRect(float x, float y, float x1, float y1, int intensity, float widthSize, float heightSize) {
        blurArea((int) x, (int) y, (int) x1 - (int) x, (int) y1 - (int) y, intensity, widthSize, heightSize);
    }

    public void drawBlurryRect(float x, float y, float x1, float y1, int intensity, float size) {
        blurArea((int) x, (int) y, (int) x1 - (int) x, (int) y1 - (int) y, intensity, size, 0);
        blurArea((int) x, (int) y, (int) x1 - (int) x, (int) y1 - (int) y, intensity, 0, size);
    }

    public void blurArea(int x, int y, int width, int height, float intensity, float blurWidth, float blurHeight) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            init();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        if (OpenGlHelper.isFramebufferEnabled()) {

            if (timer.passedMs(1000)) {
                buffer.framebufferClear();
                timer.reset();
            }

            GL11.glScissor(x * factor, (mc.displayHeight - (y * factor) - height * factor), width * factor, (height) * factor);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            setShaderConfigs(intensity, blurWidth, blurHeight);
            buffer.bindFramebuffer(true);
            blurShader.render(mc.getRenderPartialTicks());

            mc.getFramebuffer().bindFramebuffer(true);

            GL11.glDisable(GL11.GL_SCISSOR_TEST);

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
            buffer.framebufferRenderExt(mc.displayWidth, mc.displayHeight, false);
            GlStateManager.disableBlend();
            GL11.glScalef(factor, factor, 0);

        }
    }

    public void init() {
        try {
            if (buffer != null) {
                buffer.deleteFramebuffer();
            }
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), new ResourceLocation("mely/render/blur/blur.json"));
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = ((IShaderGroup) blurShader).getListFramebuffers().get(0);
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
    }
}
