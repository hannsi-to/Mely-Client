package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;

import java.awt.*;

public class BooleanSetting extends SettingBase {
    public float fontSize = 0;

    public BooleanSetting(Module module, Setting<Boolean> setting, float x, float y) {
        super(module,setting,x,y);
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(((Setting<Boolean>) setting).getValue()){
            if(fontSize < 10){
                fontSize += 1;
            }else{
                fontSize = 10;
            }
        }else{
            if(fontSize > 0){
                fontSize -= 1;
            }else{
                fontSize = 0;
            }
        }

        if(MouseUtil.isHoveringWH(
                x,
                y,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(),10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    x + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5,
                    y + 1,
                    10,
                    new Color(255,255,255,255),
                    5f,
                    ColorUtil.getRainbow(20, 255, 255)
            );
        }else {
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    x + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5,
                    y + 1,
                    10,
                    new Color(255, 255, 255, 255)
            );
        }

        NVGRenderUtil.drawRectWH(
                x,
                y,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                new Color(255,255,255,255)
        );

        NVGRenderUtil.drawRectWH(
                x + 0.5f,
                y + 0.5f,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) - 1,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) - 1,
                new Color(30, 30, 30, 255)
        );

        NVGRenderUtil.drawTextCenter(
                BonIcon.CHECK,
                MelyClient.fontManager.bonIcon,
                x + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f),
                y + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f) + 1,
                fontSize,
                new Color(255, 255, 255, 255),
                5,
                ColorUtil.getRainbow(20, 255, 255)
        );

        if(MouseUtil.isHoveringWH(
                x,
                y,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(),10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            ClickGui.INSTANCE.description = setting.getDescription();
        }

        return maxHeight + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(MouseUtil.isHoveringWH(
                x,
                y,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            ((Setting<Boolean>) setting).setValue(!((Setting<Boolean>) setting).getValue());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
