package me.hannsi.melyclient.module.system;

import me.hannsi.melyclient.event.events.Render2DEvent;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.DisplayUtil;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGUtil;

public class HudModule extends Module {
    public Setting<Integer> x = register(new Setting<>("X", 0, 0, DisplayUtil.getScaledResolutionWidthI(), 1, "Set drawing coordinate X"));
    public Setting<Integer> y = register(new Setting<>("Y", 0, 0, DisplayUtil.getScaledResolutionHeightI(), 1, "Set drawing coordinate Y"));
    public float width = DisplayUtil.width;
    public float height = DisplayUtil.height;

    public HudModule(String name, String description, int keyBind, boolean toggle) {
        super(name, Category.HUD, description, keyBind, toggle);
    }

    public HudModule(String name, String description, boolean toggle) {
        super(name, Category.HUD, description, toggle);
    }

    public HudModule(String name, String description, int keyBind) {
        super(name, Category.HUD, description, keyBind);
    }

    public HudModule(String name, String description) {
        super(name, Category.HUD, description);
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        width = DisplayUtil.width;
        height = DisplayUtil.height;

        onDraw(event);

        super.onRender2D(event);
    }

    public void onDraw(Render2DEvent event) {

    }

    public void nanoVGStart() {
        NanoVGUtil.push();
        NanoVGUtil.translate(x.getValue(), y.getValue());
    }

    public void nanoVGEnd() {
        NanoVGUtil.resetTranslate();
        NanoVGUtil.pop();
    }

    public void openGLStart() {
        GLUtil.push();
        GLUtil.translate(x.getValue(), y.getValue(), 0);
    }

    public void openGLEnd() {
        GLUtil.translate(-x.getValue(), -y.getValue(), 0);
        GLUtil.pop();
    }
}
