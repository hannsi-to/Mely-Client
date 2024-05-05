package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.conversion.Keyboard;
import me.hannsi.melyclient.util.system.math.MathUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;

public class FloatSetting extends SettingBase {
    public boolean typed = false;
    public String floatText;
    public Setting<Float> floatSetting;

    public FloatSetting(Module module, Setting<Float> setting, float x, float y) {
        super(module, setting, x, y);
        floatSetting = setting;
        floatText = floatSetting.getValue().toString();
    }

    @Override
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (MouseUtil.isHoveringWH(x, y, MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, floatSetting.getName(), 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), mouseX, mouseY)) {
            NVGRenderUtil.drawText(floatSetting.getName(), MelyClient.fontManager.ubuntu, x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + floatSetting.getValue(), 10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD, 10), y + 1, 10, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            NVGRenderUtil.drawText(floatSetting.getName(), MelyClient.fontManager.ubuntu, x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + floatSetting.getValue(), 10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "RS", 10), y + 1, 10, new Color(255, 255, 255, 255));
        }

        if (MouseUtil.isHoveringWH(x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + floatSetting.getValue(), 10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "RS", 10), y + 1, MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, floatSetting.getName(), 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription(floatSetting.getDescription());
        }

        NVGRenderUtil.drawRoundedRectWH(x, y - 1, MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + (typed ? floatText : floatSetting.getValue() + ""), 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD, 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f, new Color(30, 30, 30, 255));

        if (MouseUtil.isHoveringWH(x, y - 1, MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + (typed ? floatText : floatSetting.getValue() + ""), 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD, 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2, mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription("Min : " + floatSetting.getMin() + ".   Max : " + floatSetting.getMax() + ".   Step : " + floatSetting.getStep() + ".");
        }

        NVGRenderUtil.drawText(typed ? floatText : floatSetting.getValue() + "", MelyClient.fontManager.ubuntu, x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A", 10) + (MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD, 10) / 2f), y, 10, new Color(255, 255, 255, 255));

        NVGRenderUtil.drawText(BonIcon.ARROW_BACK, MelyClient.fontManager.bonIcon, x + 2.5f, y, 10, new Color(255, 255, 255, 255));

        NVGRenderUtil.drawText(BonIcon.ARROW_FORWARD, MelyClient.fontManager.bonIcon, x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + (typed ? floatText : floatSetting.getValue() + ""), 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK, 10) - 2.5f, y, 10, new Color(255, 255, 255, 255));

        if (typed) {
            NVGRenderUtil.drawLineWH(x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + floatText, 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK, 10) - 5, y, 0, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), 1, new Color(255, 255, 255, 255));
        }

        setting = floatSetting;

        return maxHeight + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 1;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.isHoveringWH(x + 2.5f, y, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK, 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10), mouseX, mouseY)) {
            float value = updateValueForClick(true);
            floatSetting.setValue(value);
        }

        if (MouseUtil.isHoveringWH(x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + floatSetting.getValue(), 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK, 10) - 2.5f, y, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_FORWARD, 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10), mouseX, mouseY)) {
            float value = updateValueForClick(false);
            floatSetting.setValue(value);
        }

        if (MouseUtil.isHoveringWH(x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A", 10) + (MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD, 10) / 2f), y, MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, floatSetting.getValue() + "", 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), mouseX, mouseY)) {
            typed = !typed;
            floatText = floatSetting.getValue().toString();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (typed) {
            switch (keyCode) {
                case Keyboard.KEY_ESCAPE:
                    floatSetting.setValue(floatSetting.getValue());
                    break;
                case Keyboard.KEY_RETURN:
                    floatText = StringUtil.fillIfEmpty(floatText, 0);
                    floatSetting.setValue(Float.parseFloat(floatText));
                    typed = false;
                    break;
                case Keyboard.KEY_BACK:
                    floatText = StringUtil.removeLastChar(floatText);
                    floatText = StringUtil.fillIfEmpty(floatText, 0);
                    break;
                case Keyboard.KEY_MINUS:
                    floatText = "-" + floatText;
                    checkValue();
                    break;
                case Keyboard.KEY_PERIOD:
                    floatText = StringUtil.addLastChar(floatText, ".");
                    break;
            }

            for (int i : Keyboard.getNumberKey()) {
                if (keyCode == i) {
                    if (floatText.equals("0")) {
                        floatText = "";
                    }

                    floatText = StringUtil.addLastChar(floatText, typedChar);
                    checkValue();
                    return;
                }
            }
        }
    }

    public void checkValue() {
        if (Float.parseFloat(floatText) <= floatSetting.getMin()) {
            floatText = floatSetting.getMin().toString();
        }
        if (Float.parseFloat(floatText) >= floatSetting.getMax()) {
            floatText = floatSetting.getMax().toString();
        }
    }

    public float updateValueForClick(boolean minus) {
        float value = floatSetting.getValue();

        if (minus) {
            if (value == floatSetting.getMin()) {
                value = floatSetting.getMin();
            } else {
                value = MathUtil.bdSubtract(value, floatSetting.getStep());
            }
        } else {
            if (value == floatSetting.getMax()) {
                value = floatSetting.getMax();
            } else {
                value = MathUtil.bdAdd(value, floatSetting.getStep());
            }
        }

        value = Math.round(value * 10) / 10f;

        return value;
    }

    public float checkStep(float value) {
        return MathUtil.adjustToClosestMultipleF(value, floatSetting.getStep());
    }
}
