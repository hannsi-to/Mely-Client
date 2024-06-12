package me.hannsi.melyclient.util.render.nanovg.render;

import org.lwjgl.nanovg.NanoVG;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class NanoVGUtil {
    public static void pop() {
        NanoVGRenderUtil.nvgFramePop();
    }

    public static void push() {
        NanoVGRenderUtil.nvgFramePush();
    }

    public static void scale(float x, float y) {
        NanoVG.nvgScale(nvg, x, y);
    }

    public static void translate(float x, float y) {
        NanoVG.nvgTranslate(nvg, x, y);
    }

    public static void restTranslate() {
        NanoVG.nvgResetTransform(nvg);
    }
}
