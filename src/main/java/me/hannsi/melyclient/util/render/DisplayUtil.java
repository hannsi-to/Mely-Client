package me.hannsi.melyclient.util.render;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.client.gui.ScaledResolution;

public class DisplayUtil implements InterfaceMinecraft {
    public static ScaledResolution scaledResolution = new ScaledResolution(mc);
    public static float width = scaledResolution.getScaledWidth();
    public static float height = scaledResolution.getScaledHeight();

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
}
