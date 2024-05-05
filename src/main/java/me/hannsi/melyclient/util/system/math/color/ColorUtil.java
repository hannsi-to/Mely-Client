package me.hannsi.melyclient.util.system.math.color;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.math.MathUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorUtil implements InterfaceMinecraft {
    public static Color getRainbow(int delay, int timing, float saturation, float brightness) {
        double rainbowState = Math.ceil((double) (System.currentTimeMillis() + (long) delay) / 20.0 + timing);
        return Color.getHSBColor((float) (rainbowState % 360.0 / 360.0), saturation / 255.0f, brightness / 255.0f);
    }

    public static Color getRainbow(int delay, float saturation, float brightness) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), saturation / 255.0f, brightness / 255.0f);
    }

    public static int toARGB(final int r, final int g, final int b, final int a) {
        return fixColorRange(r, g, b, a).getRGB();
    }

    public static int toRGBA(final int r, final int g, final int b) {
        return toRGBA(r, g, b, 255);
    }

    public static int toRGBA(final int r, final int g, final int b, final int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }

    public static int toRGBA(final float r, final float g, final float b, final float a) {
        return toRGBA((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
    }

    public static Color setRed(Color color, float red) {
        return fixColorRange((int) (red * 255.0f), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color setGreen(Color color, float green) {
        return fixColorRange(color.getRed(), (int) (green * 255.0f), color.getBlue(), color.getAlpha());
    }

    public static Color setBlue(Color color, float blue) {
        return fixColorRange(color.getRed(), color.getGreen(), (int) (blue * 255.0f), color.getAlpha());
    }

    public static Color setAlpha(Color color, float alpha) {
        return fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255.0f));
    }

    public static Color setRed(Color color, int red) {
        return fixColorRange(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color setGreen(Color color, int green) {
        return fixColorRange(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    public static Color setBlue(Color color, int blue) {
        return fixColorRange(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    public static Color setAlpha(Color color, int alpha) {
        return fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static Color getColorForDisplayPoint(int x, int y) {
        Color color = null;
        try {
            color = new Robot().getPixelColor(x, y);
        } catch (AWTException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return color;
    }

    public static boolean checkColorRange(int checkRed, int checkGreen, int checkBlue, int checkAlpha) {
        List<Integer> checkColors = new ArrayList<>();
        checkColors.add(checkRed);
        checkColors.add(checkGreen);
        checkColors.add(checkBlue);
        checkColors.add(checkAlpha);

        for (Integer integer : checkColors) {
            return MathUtil.isWithinRange(integer, 0, 255);
        }

        return true;
    }

    public static Color fixColorRange(int fixRed, int fixGreen, int fixBlue, int fixAlpha) {
        if (checkColorRange(fixRed, fixGreen, fixBlue, fixAlpha)) {
            return new Color(fixRed, fixGreen, fixBlue, fixAlpha);
        } else {
            return new Color(MathUtil.clampI(fixRed, 0, 255), MathUtil.clampI(fixGreen, 0, 255), MathUtil.clampI(fixBlue, 0, 255), MathUtil.clampI(fixAlpha, 0, 255));
        }
    }
}
