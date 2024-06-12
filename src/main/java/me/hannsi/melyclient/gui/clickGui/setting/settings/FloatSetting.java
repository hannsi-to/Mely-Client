package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.MouseUtil;
import me.hannsi.melyclient.util.system.StringUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.conversion.Keyboard;
import me.hannsi.melyclient.util.system.math.MathUtil;
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
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getWidth(floatSetting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(floatSetting.getName(), x + ubuntu10.getWidth("AA" + floatSetting.getValue()) + 5 + bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD), y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            ubuntu10.drawText(floatSetting.getName(), x + ubuntu10.getWidth("AA" + floatSetting.getValue()) + 5 + bonIcon10.getWidth("RS"), y + 1, new Color(255, 255, 255, 255));
        }

        if (MouseUtil.isHoveringWH(x + ubuntu10.getWidth("AA" + floatSetting.getValue()) + 5 + ubuntu10.getWidth("RS"), y + 1, ubuntu10.getWidth(floatSetting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription(floatSetting.getDescription());
        }

        NanoVGRenderUtil.drawRoundedRectWH(x, y - 1, ubuntu10.getWidth("AA" + (typed ? floatText : floatSetting.getValue() + "")) + bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD), ubuntu10.getHeight() + 2, ubuntu10.getHeight() / 2f, new Color(30, 30, 30, 255));

        if (MouseUtil.isHoveringWH(x, y - 1, ubuntu10.getWidth("AA" + (typed ? floatText : floatSetting.getValue() + "")) + bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD), ubuntu10.getHeight() + 2, mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription("Min : " + floatSetting.getMin() + ".   Max : " + floatSetting.getMax() + ".   Step : " + floatSetting.getStep() + ".");
        }

        ubuntu10.drawText(typed ? floatText : floatSetting.getValue() + "", x + ubuntu10.getWidth("A") + (bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD) / 2f), y, new Color(255, 255, 255, 255));

        bonIcon10.drawText(BonIcon.ARROW_BACK, x + 2.5f, y, new Color(255, 255, 255, 255));

        bonIcon10.drawText(BonIcon.ARROW_FORWARD, x + ubuntu10.getWidth("AA" + (typed ? floatText : floatSetting.getValue() + "")) + bonIcon10.getWidth(BonIcon.ARROW_BACK) - 2.5f, y, new Color(255, 255, 255, 255));

        if (typed) {
            NanoVGRenderUtil.drawLineWH(x + ubuntu10.getWidth("AA" + floatText) + ubuntu10.getWidth(BonIcon.ARROW_BACK) - 5, y, 0, ubuntu10.getHeight(), 1, new Color(255, 255, 255, 255));
        }

        setting = floatSetting;

        return maxHeight + ubuntu10.getHeight() + 1;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(x + 2.5f, y, bonIcon10.getWidth(BonIcon.ARROW_BACK), bonIcon10.getHeight(), mouseX, mouseY)) {
            float value = updateValueForClick(true);
            floatSetting.setValue(value);
        }

        if (MouseUtil.isHoveringWH(x + ubuntu10.getWidth("AA" + floatSetting.getValue()) + bonIcon10.getWidth(BonIcon.ARROW_BACK) - 2.5f, y, bonIcon10.getWidth(BonIcon.ARROW_FORWARD), bonIcon10.getHeight(), mouseX, mouseY)) {
            float value = updateValueForClick(false);
            floatSetting.setValue(value);
        }

        if (MouseUtil.isHoveringWH(x + ubuntu10.getWidth("A") + (bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD) / 2f), y, ubuntu10.getWidth(floatSetting.getValue() + ""), ubuntu10.getHeight(), mouseX, mouseY)) {
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
