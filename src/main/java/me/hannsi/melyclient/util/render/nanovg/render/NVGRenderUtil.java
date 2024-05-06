package me.hannsi.melyclient.util.render.nanovg.render;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.render.IOUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;
import static org.lwjgl.system.MemoryStack.stackPush;

public class NVGRenderUtil implements InterfaceMinecraft {
    public static void nvgFramePush() {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        NanoVG.nvgBeginFrame(nvg, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), scaledResolution.getScaleFactor());
    }

    public static void nvgFramePop() {
        NanoVG.nvgEndFrame(nvg);
        GL11.glPopAttrib();
    }

    public static void fillColor(Color color) {
        NVGColor nvgColor = NVGColor.calloc();
        color(color, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);
    }

    public static void fillBoxGradientColor(float x, float y, float width, float height, float round, float feather, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgBoxGradient(nvg, x, y, width, height, round, feather, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    public static void fillLinearGradientColor(float x, float y, float width, float height, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgLinearGradient(nvg, x, y, x + width, y + height, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    public static void fillRadialGradientColor(float x, float y, float inRange, float outRange, Color color1, Color color2) {
        NVGColor nvgColor1 = NVGColor.calloc();
        NVGColor nvgColor2 = NVGColor.calloc();
        color(color1, nvgColor1);
        color(color2, nvgColor2);
        NanoVG.nvgFillPaint(nvg, NanoVG.nvgRadialGradient(nvg, x, y, inRange, outRange, nvgColor1, nvgColor2, NVGPaint.calloc()));
        NanoVG.nvgFill(nvg);
    }

    public static void strokeColor(Color color) {
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;
        float a = color.getAlpha() / 255.0f;

        NVGColor nvgColor = NVGColor.calloc();
        NanoVG.nvgRGBAf(r, g, b, a, nvgColor);
        NanoVG.nvgStrokeColor(nvg, nvgColor);
        NanoVG.nvgStroke(nvg);
    }

    public static NVGColor color(Color color, NVGColor nvgColor) {
        return NanoVG.nvgRGBAf(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f, nvgColor);
    }

    public static void drawRoundedRectWH(float x, float y, float width, float height, float radius, Color color) {
        drawRoundedRect(x, y, x + width, y + height, radius, color);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, Color color) {
        drawRoundedRect(x, y, x1, y1, radius, true, true, true, true, color);
    }

    public static void drawRoundedRectWH(float x, float y, float width, float height, float radius, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        drawRoundedRect(x, y, x + width, y + height, radius, xy, x1y, x1y1, xy1, color);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgRoundedRectVarying(nvg, x, y, x1 - x, y1 - y, xy ? radius : 0.0f, x1y ? radius : 0.0f, x1y1 ? radius : 0.0f, xy1 ? radius : 0.0f);

        fillColor(color);

        NanoVG.nvgClosePath(nvg);
    }

    public static void drawOutLineRoundedRectWH(float x, float y, float width, float height, float radius, float lineWidth, Color color) {
        drawOutLineRoundedRect(x, y, x + width, y + height, radius, lineWidth, color);
    }

    public static void drawOutLineRoundedRect(float x, float y, float x1, float y1, float radius, float lineWidth, Color color) {
        drawOutLineRoundedRect(x, y, x1, y1, radius, lineWidth, true, true, true, true, color);
    }

    public static void drawOutLineRoundedRectWH(float x, float y, float width, float height, float radius, float lineWidth, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        drawOutLineRoundedRect(x, y, x + width, y + height, lineWidth, radius, xy, x1y, x1y1, xy1, color);
    }

    public static void drawOutLineRoundedRect(float x, float y, float x1, float y1, float radius, float lineWidth, boolean xy, boolean x1y, boolean x1y1, boolean xy1, Color color) {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgRoundedRectVarying(nvg, x, y, x1 - x, y1 - y, xy ? radius : 0.0f, x1y ? radius : 0.0f, x1y1 ? radius : 0.0f, xy1 ? radius : 0.0f);

        NanoVG.nvgStrokeWidth(nvg, lineWidth);
        strokeColor(color);

        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRectWH(float x, float y, float width, float height, Color color) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRect(nvg, x, y, width, height);
        fillColor(color);
        NanoVG.nvgClosePath(nvg);
    }


    public static void drawRect(float x, float y, float x1, float y1, Color color) {
        drawRectWH(x, y, x1 - x, y1 - y, color);
    }

    public static void drawOutLineRectWH(float x, float y, float width, float height, float lineWidth, Color color) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRect(nvg, x, y, width, height);
        NanoVG.nvgStrokeWidth(nvg, lineWidth);
        strokeColor(color);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawOutLineRect(float x, float y, float x1, float y1, float lineWidth, Color color) {
        drawOutLineRectWH(x, y, x1 - x, y1 - y, lineWidth, color);
    }

    public static void drawPoint(float x, float y, float size, Color color) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgCircle(nvg, x, y, size);
        fillColor(color);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawLine(float x, float y, float x1, float y1, float lineWidth, Color color) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgMoveTo(nvg, x, y);
        NanoVG.nvgLineTo(nvg, x1, y1);
        NanoVG.nvgStrokeWidth(nvg, lineWidth);
        strokeColor(color);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawLineWH(float x, float y, float width, float height, float lineWidth, Color color) {
        drawLine(x, y, x + width, y + height, lineWidth, color);
    }

    public static void drawCircle(float x, float y, float radius, Color color) {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgCircle(nvg, x, y, radius);

        fillColor(color);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawLinearGradientRect(float x, float y, float width, float height, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRect(nvg, x, y, width, height);
        fillLinearGradientColor(x, y, width, height, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawBoxGradientRect(float x, float y, float width, float height, float feather, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRect(nvg, x, y, width, height);
        fillBoxGradientColor(x, y, width, height, 0, feather, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRadialGradientRect(float x, float y, float width, float height, float cx, float cy, float inRange, float outRange, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRect(nvg, x, y, width, height);
        fillRadialGradientColor(cx, cy, inRange, outRange, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRadialGradientRect(float x, float y, float width, float height, float inRange, float outRange, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRect(nvg, x, y, width, height);
        float cx = x + ((width - x) / 2);
        float cy = y + ((height - y) / 2);
        fillRadialGradientColor(cx, cy, inRange, outRange, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawLinearGradientRoundedRect(float x, float y, float width, float height, float round, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRoundedRect(nvg, x, y, width, height, round);
        fillLinearGradientColor(x, y, width, height, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawBoxGradientRoundedRect(float x, float y, float width, float height, float round, float feather, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRoundedRect(nvg, x, y, width, height, round);
        fillBoxGradientColor(x, y, width, height, round, feather, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRadialGradientRoundedRect(float x, float y, float width, float height, float round, float cx, float cy, float inRange, float outRange, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRoundedRect(nvg, x, y, width, height, round);
        fillRadialGradientColor(cx, cy, inRange, outRange, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRadialGradientRoundedRect(float x, float y, float width, float height, float round, float inRange, float outRange, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgRoundedRect(nvg, x, y, width, height, round);
        float cx = x + ((width - x) / 2);
        float cy = y + ((height - y) / 2);
        fillRadialGradientColor(cx, cy, inRange, outRange, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawLinearGradientCircle(float cx, float cy, float range, float x, float y, float width, float height, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgCircle(nvg, cx, cy, range);
        fillLinearGradientColor(x, y, width, height, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawBoxGradientCircle(float cx, float cy, float range, float x, float y, float width, float height, float feather, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgCircle(nvg, cx, cy, range);
        fillBoxGradientColor(x, y, width, height, 0, feather, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRadialGradientCircle(float cx, float cy, float range, float gcx, float gcy, float inRange, float outRange, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgCircle(nvg, cx, cy, range);
        fillRadialGradientColor(gcx, gcy, inRange, outRange, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawRadialGradientCircle(float cx, float cy, float range, float inRange, float outRange, Color color1, Color color2) {
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgCircle(nvg, cx, cy, range);
        fillRadialGradientColor(cx, cy, inRange, outRange, color1, color2);
        NanoVG.nvgClosePath(nvg);
    }

    public static void drawCustomImage(float x, float y, float width, float height, ResourceLocation resourceLocation) {
        ByteBuffer img = loadResource(resourceLocation.getPath(), 32 * 1024);
        int imageID = NanoVG.nvgCreateImageMem(nvg, 0, img);

        NVGPaint nvgPaint = NVGPaint.create();
        NanoVG.nvgBeginPath(nvg);

        try (MemoryStack stack = stackPush()) {
            IntBuffer imgW = stack.mallocInt(1);
            IntBuffer imgH = stack.mallocInt(1);

            NanoVG.nvgImageSize(nvg, imageID, imgW, imgH);
            NanoVG.nvgImagePattern(nvg, x, y, width, height, 0, imageID, 1, nvgPaint);
        }

        NanoVG.nvgClosePath(nvg);
    }

    public static ByteBuffer loadResource(String resource, int bufferSize) {
        try {
            return IOUtil.ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + resource, e);
        }
    }
}
