package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.conversion.Keyboard;
import me.hannsi.melyclient.util.system.math.MathUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;

public class IntegerSetting extends SettingBase {
    public boolean typed = false;
    public String integerText;
    public Setting<Integer> integerSetting;

    public IntegerSetting(Module module, Setting<Integer> setting, float x, float y) {
        super(module, setting, x, y);
        integerSetting = setting;
        integerText = integerSetting.getValue().toString();
    }

    @Override
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getWidth(integerSetting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(integerSetting.getName(), x + ubuntu10.getWidth("AA" + integerSetting.getValue()) + 5 + bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD), y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            ubuntu10.drawText(integerSetting.getName(), x + ubuntu10.getWidth("AA" + integerSetting.getValue()) + 5 + ubuntu10.getWidth("RS"), y + 1, new Color(255, 255, 255, 255));
        }

        if (MouseUtil.isHoveringWH(x + ubuntu10.getWidth("AA" + integerSetting.getValue()) + 5 + ubuntu10.getWidth("RS"), y + 1, ubuntu10.getWidth(integerSetting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription(integerSetting.getDescription());
        }

        NVGRenderUtil.drawRoundedRectWH(x, y - 1, ubuntu10.getWidth("AA" + (typed ? integerText : integerSetting.getValue() + "")) + bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD), ubuntu10.getHeight() + 2, ubuntu10.getHeight() / 2f, new Color(30, 30, 30, 255));

        if (MouseUtil.isHoveringWH(x, y - 1, ubuntu10.getWidth("AA" + (typed ? integerText : integerSetting.getValue() + "")) + bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD), ubuntu10.getHeight() + 2, mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription("Min : " + integerSetting.getMin() + ".   Max : " + integerSetting.getMax() + ".   Step : " + integerSetting.getStep() + ".");
        }

        ubuntu10.drawText(typed ? integerText : integerSetting.getValue() + "", x + ubuntu10.getWidth("A") + (bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD) / 2f), y, new Color(255, 255, 255, 255));

        bonIcon10.drawText(BonIcon.ARROW_BACK, x + 2.5f, y, new Color(255, 255, 255, 255));

        bonIcon10.drawText(BonIcon.ARROW_FORWARD, x + ubuntu10.getWidth("AA" + (typed ? integerText : integerSetting.getValue() + "")) + bonIcon10.getWidth(BonIcon.ARROW_BACK) - 2.5f, y, new Color(255, 255, 255, 255));

        if (typed) {
            NVGRenderUtil.drawLineWH(x + ubuntu10.getWidth("AA" + integerText) + bonIcon10.getWidth(BonIcon.ARROW_BACK) - 5, y, 0, ubuntu10.getHeight(), 1, new Color(255, 255, 255, 255));
        }

        setting = integerSetting;

        return maxHeight + ubuntu10.getHeight() + 1;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(x + 2.5f, y, bonIcon10.getWidth(BonIcon.ARROW_BACK), bonIcon10.getHeight(), mouseX, mouseY)) {
            int value = updateValueForClick(true);
            integerSetting.setValue(value);
        }

        if (MouseUtil.isHoveringWH(x + ubuntu10.getWidth("AA" + integerSetting.getValue()) + bonIcon10.getWidth(BonIcon.ARROW_BACK) - 2.5f, y, bonIcon10.getWidth(BonIcon.ARROW_FORWARD), bonIcon10.getHeight(), mouseX, mouseY)) {
            int value = updateValueForClick(false);
            integerSetting.setValue(value);
        }

        if (MouseUtil.isHoveringWH(x + ubuntu10.getWidth("A") + (bonIcon10.getWidth(BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD) / 2f), y, ubuntu10.getWidth(integerSetting.getValue() + ""), ubuntu10.getHeight(), mouseX, mouseY)) {
            typed = !typed;
            integerText = integerSetting.getValue().toString();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (typed) {
            switch (keyCode) {
                case Keyboard.KEY_ESCAPE:
                    integerSetting.setValue(integerSetting.getValue());
                    break;
                case Keyboard.KEY_RETURN:
                    integerText = StringUtil.fillIfEmpty(integerText, 0);
                    integerSetting.setValue(MathUtil.adjustToClosestMultipleI(Integer.parseInt(integerText), integerSetting.getStep()));
                    typed = false;
                    break;
                case Keyboard.KEY_BACK:
                    integerText = StringUtil.removeLastChar(integerText);
                    integerText = StringUtil.fillIfEmpty(integerText, 0);
                    break;
                case Keyboard.KEY_MINUS:
                    integerText = "-" + integerText;
                    checkValue();
                    break;
            }

            for (int i : Keyboard.getNumberKey()) {
                if (keyCode == i) {
                    if (integerText.equals("0")) {
                        integerText = "";
                    }

                    integerText = StringUtil.addLastChar(integerText, typedChar);
                    checkValue();
                    return;
                }
            }
        }
    }

    public void checkValue() {
        if (Integer.parseInt(integerText) <= integerSetting.getMin()) {
            integerText = integerSetting.getMin().toString();
        }
        if (Integer.parseInt(integerText) >= integerSetting.getMax()) {
            integerText = integerSetting.getMax().toString();
        }
    }

    public int updateValueForClick(boolean minus) {
        int value = integerSetting.getValue();

        if (minus) {
            if (value == integerSetting.getMin()) {
                value = integerSetting.getMin();
            } else {
                value -= integerSetting.getStep();
            }
        } else {
            if (value == integerSetting.getMax()) {
                value = integerSetting.getMax();
            } else {
                value += integerSetting.getStep();
            }
        }

        if (!MathUtil.isMultipleI(value, integerSetting.getStep())) {
            value = MathUtil.adjustToClosestMultipleI(value, integerSetting.getStep());
        }

        return value;
    }
}
