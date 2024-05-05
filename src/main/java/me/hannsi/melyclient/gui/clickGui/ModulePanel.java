package me.hannsi.melyclient.gui.clickGui;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingPanel;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.animation.Easing;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class ModulePanel {
    public float x;
    public float y;
    public Category category;
    public Module module;
    public Color color;
    public EasingUtil easingUtil = new EasingUtil(Easing.easeOutExpo);
    public float value;
    public String underText = "";

    public ModulePanel(float x, float y, Category category, Module module, Color color) {
        this.x = x;
        this.y = y;
        this.category = category;
        this.module = module;
        this.color = color;

        if (module.isToggle()) {
            easingUtil.reset();
            easingUtil.setReverse(!easingUtil.isReverse());
            value = 1;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        value = 10 - easingUtil.get(1000) * 10;

        NVGRenderUtil.drawRoundedRectWH(x, y, ((ClickGui.INSTANCE.width / 4f) - 100), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5, 5, new Color(50, 50, 50, 255));

        NVGRenderUtil.drawText(module.getName(), MelyClient.fontManager.ubuntu, x + 5, y + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5) / 2f, 10, new Color(255, 255, 255, 255), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);

        NVGRenderUtil.drawRoundedRectWH(x + ((ClickGui.INSTANCE.width / 4f) - 100) - 25, y + 2.5f, 20, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), 5, new Color(40, 40, 40, 255));

        NVGRenderUtil.drawCircle(x + ((ClickGui.INSTANCE.width / 4f) - 100) - 25 + (5 + value), y + 2.5f + 5, 5, module.isToggle() ? new Color(100, 100, 255, 255) : new Color(30, 30, 30, 255));

        if (MouseUtil.isHoveringWH(x + ((ClickGui.INSTANCE.width / 4f) - 100) + 5, y - (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10) + 7) / 4f, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "u", 10), (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10) + 7), mouseX, mouseY)) {
            NVGRenderUtil.drawText("u", MelyClient.fontManager.bonIcon, x + ((ClickGui.INSTANCE.width / 4f) - 100) + 5, y + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10) + 7) / 2f, 10, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);

            ClickGui.INSTANCE.description = "Open " + module.getName() + " setting.";
        }

        NVGRenderUtil.drawText("u", MelyClient.fontManager.bonIcon, x + ((ClickGui.INSTANCE.width / 4f) - 100) + 5, y + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10) + 7) / 2f, 10, new Color(255, 255, 255, 255), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
    }

    public void drawScreen2(int mouseX, int mouseY, float partialTicks) {
        if (MouseUtil.isHoveringWH(x, y, ((ClickGui.INSTANCE.width / 4f) - 100), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5, mouseX, mouseY)) {
            ClickGui.INSTANCE.description = module.getDescription();
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.isHoveringWH(x, y, ((ClickGui.INSTANCE.width / 4f) - 100), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5, mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle();
                easingUtil.reset();
                easingUtil.setReverse(!easingUtil.isReverse());
            }
        }

        if (MouseUtil.isHoveringWH(x + ((ClickGui.INSTANCE.width / 4f) - 100) + 5, y - (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10) + 7) / 4f, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "u", 10), (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10) + 7), mouseX, mouseY)) {
            if (mouseButton == 0) {
                if (ClickGui.INSTANCE.settingPanel == null) {
                    ClickGui.INSTANCE.settingPanel = new SettingPanel(module, ClickGui.INSTANCE.width / 4f + 110 + ((ClickGui.INSTANCE.width / 4f) - 100) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "u", 10) + 5, ClickGui.INSTANCE.height / 4f + 31);
                } else {
                    SettingPanel nowSettingPanel = ClickGui.INSTANCE.settingPanel;

                    if (nowSettingPanel.getModule() == module) {
                        ClickGui.INSTANCE.settingPanel = null;
                    } else {
                        ClickGui.INSTANCE.settingPanel = new SettingPanel(module, ClickGui.INSTANCE.width / 4f + 110 + ((ClickGui.INSTANCE.width / 4f) - 100) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "u", 10) + 5, ClickGui.INSTANCE.height / 4f + 31);
                    }
                }
            }
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
