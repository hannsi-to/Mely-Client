package me.hannsi.melyclient.util.render;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class DisplayUtil implements InterfaceMinecraft {
    public static ScaledResolution scaledResolution;
    public static float width;
    public static float height;
    public static GraphicsEnvironment graphicsEnvironment;
    public static GraphicsDevice[] graphicsDevices;

    static {
        scaledResolution = new ScaledResolution(mc);
        width = scaledResolution.getScaledWidth();
        height = scaledResolution.getScaledHeight();
        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevices = graphicsEnvironment.getScreenDevices();
    }

    public static int getMaxRefreshRate() {
        int maxRefreshRate = 0;

        for (GraphicsDevice graphicsDevice : graphicsDevices) {
            int refreshRate = getMaxRefreshRateFormGraphicsDevice(graphicsDevice);
            if (maxRefreshRate <= refreshRate) {
                maxRefreshRate = refreshRate;
            }
        }

        return maxRefreshRate;
    }

    public static int getMaxRefreshRateFormGraphicsDevice(GraphicsDevice graphicsDevice) {
        DisplayMode displayMode = graphicsDevice.getDisplayMode();
        int refreshRate = displayMode.getRefreshRate();

        if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
            new DebugLog("The refresh rate of the monitor is unknown.", DebugLevel.ERROR);
            return 0;
        } else {
            return refreshRate;
        }
    }

    public static int getRefreshRate() {
        return Minecraft.getDebugFPS();
    }

    public static float getScaledResolutionWidthF() {
        return scaledResolution.getScaledWidth();
    }

    public static float getScaledResolutionHeightF() {
        return scaledResolution.getScaledHeight();
    }

    public static int getScaledResolutionWidthI() {
        return scaledResolution.getScaledWidth();
    }

    public static int getScaledResolutionHeightI() {
        return scaledResolution.getScaledHeight();
    }

    public static float[] getGuiScaleF() {
        int scaledResolutionWidth = getScaledResolutionWidthI();
        int scaledResolutionHeight = getScaledResolutionHeightI();

        return new float[]{scaledResolutionWidth, scaledResolutionHeight};
    }

    public static int[] getGuiScaleI() {
        float[] scales = getGuiScaleF();

        return new int[]{(int) scales[0], (int) scales[1]};
    }

    public static void upDateGuiScales() {
        float[] scales = getGuiScaleF();

        width = scales[0];
        height = scales[1];
    }

    public static ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
}
