package me.hannsi.melyclient.util.render.nanovg.render;

import org.lwjgl.nanovg.NanoVG;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class NanoVGUtil {
    public static void nvgPop() {
        NanoVGRenderUtil.nvgFramePop();
    }

    public static void nvgPush() {
        NanoVGRenderUtil.nvgFramePush();
    }

    public static void nvgScale(float x, float y) {
        NanoVG.nvgScale(nvg, x, y);
    }

    public static void nvgTranslate(float x, float y) {
        NanoVG.nvgTranslate(nvg, x, y);
    }
}
