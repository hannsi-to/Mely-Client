package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.conversion.Keyboard;
import me.hannsi.melyclient.util.system.math.MathUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;

import java.awt.*;

public class IntegerSetting extends SettingBase {
    public boolean typed = false;
    public String integerText;
    public Setting<Integer> integerSetting;

    public IntegerSetting(Module module, Setting<Integer> setting, float x, float y) {
        super(module,setting,x,y);
        integerSetting = setting;
        integerText = integerSetting.getValue().toString();
    }

    @Override
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(MouseUtil.isHoveringWH(
                x,
                y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, integerSetting.getName(), 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            NVGRenderUtil.drawText(
                    integerSetting.getName(),
                    MelyClient.fontManager.ubuntu,
                    x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + integerSetting.getValue(),10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD,10),
                    y + 1,
                    10,
                    new Color(255,255,255,255),
                    5f,
                    ColorUtil.getRainbow(20, 255, 255)
            );
        }else{
            NVGRenderUtil.drawText(
                    integerSetting.getName(),
                    MelyClient.fontManager.ubuntu,
                    x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + integerSetting.getValue(),10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "RS",10),
                    y + 1,
                    10,
                    new Color(255,255,255,255)
            );
        }

        if(MouseUtil.isHoveringWH(
                x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + integerSetting.getValue(),10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, "RS",10),
                y + 1,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, integerSetting.getName(),10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            ClickGui.getINSTANCE().setDescription(integerSetting.getDescription());
        }

        NVGRenderUtil.drawRoundedRectWH(
                x,
                y - 1,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + (typed ? integerText : integerSetting.getValue() + ""),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD,10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f,
                new Color(30, 30, 30, 255)
        );

        if(MouseUtil.isHoveringWH(
                x,
                y - 1,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + (typed ? integerText : integerSetting.getValue() + ""),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD,10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2,
                mouseX,
                mouseY
        )){
            ClickGui.getINSTANCE().setDescription("Min : " + integerSetting.getMin() + ".   Max : " + integerSetting.getMax() + ".   Step : " + integerSetting.getStep() + ".");
        }

        NVGRenderUtil.drawText(
                typed ? integerText : integerSetting.getValue() + "",
                MelyClient.fontManager.ubuntu,
                x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10) + (MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD,10) / 2f),
                y,
                10,
                new Color(255,255,255,255)
        );

        NVGRenderUtil.drawText(
                BonIcon.ARROW_BACK,
                MelyClient.fontManager.bonIcon,
                x + 2.5f,
                y,
                10,
                new Color(255,255,255,255)
        );

        NVGRenderUtil.drawText(
                BonIcon.ARROW_FORWARD,
                MelyClient.fontManager.bonIcon,
                x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + (typed ? integerText : integerSetting.getValue() + ""),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK,10) - 2.5f,
                y,
                10,
                new Color(255,255,255,255)
        );

        if(typed) {
            NVGRenderUtil.drawLineWH(
                    x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + integerText,10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK,10) - 5,
                    y,
                    0,
                    MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                    1,
                    new Color(255,255,255,255)
            );
        }

        setting = integerSetting;

        return maxHeight + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 1;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(MouseUtil.isHoveringWH(
                x + 2.5f,
                y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK,10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10),
                mouseX,
                mouseY
        )){
            int value = updateValueForClick(true);
            integerSetting.setValue(value);
        }

        if(MouseUtil.isHoveringWH(
                x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + integerSetting.getValue(),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK,10) - 2.5f,
                y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_FORWARD,10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 10),
                mouseX,
                mouseY
        )){
            int value = updateValueForClick(false);
            integerSetting.setValue(value);
        }

        if(MouseUtil.isHoveringWH(
                x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10) + (MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.ARROW_BACK + BonIcon.ARROW_FORWARD,10) / 2f),
                y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, integerSetting.getValue() + "",10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            typed = !typed;
            integerText = integerSetting.getValue().toString();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(typed){
            switch (keyCode) {
                case Keyboard.KEY_ESCAPE:
                    integerSetting.setValue(integerSetting.getValue());
                    break;
                case Keyboard.KEY_RETURN:
                    integerText = StringUtil.fillIfEmpty(integerText,0);
                    integerSetting.setValue(MathUtil.adjustToClosestMultipleI(Integer.parseInt(integerText),integerSetting.getStep()));
                    typed = false;
                    break;
                case Keyboard.KEY_BACK:
                    integerText = StringUtil.removeLastChar(integerText);
                    integerText = StringUtil.fillIfEmpty(integerText,0);
                    break;
                case Keyboard.KEY_MINUS:
                    integerText = "-" + integerText;
                    checkValue();
                    break;
            }

            for(int i : Keyboard.getNumberKey()){
                if(keyCode == i){
                    if(integerText.equals("0")){
                        integerText = "";
                    }

                    integerText = StringUtil.addLastChar(integerText,typedChar);
                    checkValue();
                    return;
                }
            }
        }
    }

    public void checkValue(){
        if(Integer.parseInt(integerText) <= integerSetting.getMin()){
            integerText = integerSetting.getMin().toString();
        }
        if(Integer.parseInt(integerText) >= integerSetting.getMax()){
            integerText = integerSetting.getMax().toString();
        }
    }

    public int updateValueForClick(boolean minus){
        int value = integerSetting.getValue();

        if(minus){
            if(value == integerSetting.getMin()){
                value = integerSetting.getMin();
            }else{
                value -= integerSetting.getStep();
            }
        }else {
            if(value == integerSetting.getMax()){
                value = integerSetting.getMax();
            }else{
                value += integerSetting.getStep();
            }
        }

        if(!MathUtil.isMultipleI(value,integerSetting.getStep())){
            value = MathUtil.adjustToClosestMultipleI(value,integerSetting.getStep());
        }

        return value;
    }
}
