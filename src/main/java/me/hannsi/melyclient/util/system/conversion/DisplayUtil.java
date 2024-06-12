package me.hannsi.melyclient.util.system.conversion;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class DisplayUtil implements InterfaceMinecraft {
    public static ScaledResolution scaledResolution = new ScaledResolution(mc);
    public static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static DisplayMode displayMode = graphicsEnvironment.getDefaultScreenDevice().getDisplayMode();

    public static int[] getDisplayScale() {
        int[] displayScale = new int[2];
        displayScale[0] = displayMode.getWidth();
        displayScale[1] = displayMode.getHeight();

        return displayScale;
    }

    public static int[] getGuiScreenScale() {
        int[] guiScreenScale = new int[2];
        guiScreenScale[0] = scaledResolution.getScaledWidth();
        guiScreenScale[1] = scaledResolution.getScaledHeight();

        return guiScreenScale;
    }

    public static int getDisplayX() {
        return getDisplayScale()[0];
    }

    public static int getDisplayY() {
        return getDisplayScale()[1];
    }

    public static int getGuiScreenX() {
        return getGuiScreenScale()[0];
    }

    public static int getGuiScreenY() {
        return getGuiScreenScale()[1];
    }
}
