package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;

import java.awt.*;
import java.util.Arrays;

public class EnumSetting extends SettingBase {
    public float maxEnumTextSize = 0.0f;
    public boolean open = false;

    public EnumSetting(Module module, Setting<IEnumSetting> setting, float x, float y) {
        super(module, setting, x, y);
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        maxEnumTextSize = 0.0f;

        for(IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()){
            float nowSize = MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu,"AA" + value.getDisplay(),10);

            if(maxEnumTextSize < nowSize){
                maxEnumTextSize = nowSize;
            }
        }

        NVGRenderUtil.drawRoundedRectWH(
                this.x,
                this.y - 1,
                maxEnumTextSize,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f,
                new Color(30, 30, 30, 255)
        );

        float offsetY = (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2);
        if(open){
            int count = 0;

            for(IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()){
                if(value == ((Setting<IEnumSetting>) setting).getValue()){
                    continue;
                }

                count++;
            }

            NVGRenderUtil.drawRoundedRectWH(
                    this.x,
                    this.y - 1 + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2,
                    maxEnumTextSize,
                    (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2) * count,
                    MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f,
                    new Color(30, 30, 30, 255)
            );

            for(IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()){
                if(value == ((Setting<IEnumSetting>) setting).getValue()){
                    continue;
                }

                NVGRenderUtil.drawText(
                        value.getDisplay(),
                        MelyClient.fontManager.ubuntu,
                        this.x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10),
                        this.y + offsetY,
                        10,
                        new Color(255,255,255,255)
                );

                offsetY += (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2);
            }
        }

        NVGRenderUtil.drawText(
                ((Setting<IEnumSetting>) setting).getValue().getDisplay(),
                MelyClient.fontManager.ubuntu,
                this.x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10),
                this.y,
                10,
                new Color(255,255,255,255)
        );

        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                maxEnumTextSize + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(), 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    this.x + maxEnumTextSize + 5,
                    this.y + 1,
                    10,
                    new Color(255,255,255,255),
                    5f,
                    ColorUtil.getRainbow(20, 255, 255)
            );
        }else{
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    this.x + maxEnumTextSize + 5,
                    this.y + 1,
                    10,
                    new Color(255,255,255,255)
            );
        }

        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                maxEnumTextSize,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            ClickGui.getINSTANCE().setDescription("Left click : Next mode" + ".   Right click : Previous mode" + ".   Middle Click : Mode Menu.");
        }

        if(MouseUtil.isHoveringWH(
                this.x + maxEnumTextSize,
                this.y,
                5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(), 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            ClickGui.getINSTANCE().setDescription(setting.getDescription());
        }

        return maxHeight + offsetY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                maxEnumTextSize,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            if(mouseButton == 0){
                increase();
            }else if(mouseButton == 1){
                decrease();
            }else if(mouseButton == 2){
                open = !open;
            }
        }

        if(open){
            int count = 1;

            for(IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()){
                if(value == ((Setting<IEnumSetting>) setting).getValue()){
                    continue;
                }

                if(MouseUtil.isHoveringWH(
                        this.x,
                        this.y - 1 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2) * count,
                        maxEnumTextSize,
                        (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2),
                        mouseX,
                        mouseY
                )){
                    ((Setting<IEnumSetting>) setting).setValue(value);
                    break;
                }

                count++;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @SuppressWarnings("unchecked")
    public void increase() {
        int currentIndex = Arrays.asList(((Setting<IEnumSetting>) setting).getValue().getValues()).indexOf(((Setting<IEnumSetting>) setting).getValue());
        int nextIndex = (currentIndex + 1) % ((Setting<IEnumSetting>) setting).getValue().getValues().length;
        ((Setting<IEnumSetting>) setting).setValue(((Setting<IEnumSetting>) setting).getValue().getValues()[nextIndex]);
    }

    @SuppressWarnings("unchecked")
    public void decrease() {
        int currentIndex = Arrays.asList(((Setting<IEnumSetting>) setting).getValue().getValues()).indexOf(((Setting<IEnumSetting>) setting).getValue());
        int previousIndex = (currentIndex - 1 + ((Setting<IEnumSetting>) setting).getValue().getValues().length) % ((Setting<IEnumSetting>) setting).getValue().getValues().length;
        ((Setting<IEnumSetting>) setting).setValue(((Setting<IEnumSetting>) setting).getValue().getValues()[previousIndex]);
    }
}
