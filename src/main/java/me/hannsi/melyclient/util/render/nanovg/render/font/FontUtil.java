package me.hannsi.melyclient.util.render.nanovg.render.font;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

import static me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil.color;
import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;

public class FontUtil {
    public Font font;
    public float size;

    public FontUtil(Font font, float size) {
        this.font = font;
        this.size = size;
    }

    public float getWidth(String text) {
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgFontSize(nvg, size);
        return NanoVG.nvgTextBounds(nvg, 0, 0, text, new float[4]);
    }

    public float getHeight() {
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgFontSize(nvg, size);

        float[] lineh = new float[1];
        NanoVG.nvgTextMetrics(nvg, new float[1], new float[1], lineh);

        return lineh[0];
    }

    public void drawText(String text, float x, float y, Color color) {
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
    }

    public void drawBlurText(String text, float x, float y, Color color, float blur, Color blurColor) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
    }

    public void drawBlurText(String text, float x, float y, Color color, float blur, Color blurColor, int align) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, align);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, align);
    }

    public void drawTextCenter(String text, float x, float y, Color color) {
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
    }

    public void drawBlurTextCenter(String text, float x, float y, Color color, float blur, Color blurColor) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
    }

    public void drawText(String text, float x, float y, Color color, int align) {
        NanoVG.nvgBeginPath(nvg);

        NVGColor nvgColor = NVGColor.calloc();
        color(color, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);

        NanoVG.nvgFontSize(nvg, size);
        NanoVG.nvgTextAlign(nvg, align);
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgText(nvg, x, y, text);

        NanoVG.nvgRGBAf(0, 0, 0, 0, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);

        NanoVG.nvgClosePath(nvg);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
