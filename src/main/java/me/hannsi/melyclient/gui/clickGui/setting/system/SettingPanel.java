package me.hannsi.melyclient.gui.clickGui.setting.system;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.settings.*;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Bind;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingPanel {
    public float scrollY;
    public Module module;
    public float x;
    public float y;
    public List<Setting<?>> settings;
    public List<SettingBase> settingBases = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SettingPanel(Module module,float x,float y){
        this.module = module;
        this.x = x;
        this.y = y;
        scrollY = 0;

        settings = module.getSettings();

        for(Setting<?> setting : settings){
            if(setting.getValue() instanceof Bind){
                settingBases.add(new BindSetting(module, (Setting<Bind>) setting,this.x,this.y));
            }
            if(setting.getValue() instanceof IEnumSetting){
                settingBases.add(new EnumSetting(module, (Setting<IEnumSetting>) setting,this.x,this.y));
            }
            if(setting.getValue() instanceof Boolean){
                settingBases.add(new BooleanSetting(module, (Setting<Boolean>) setting,this.x,this.y));
            }
            if(setting.getValue() instanceof Integer){
                settingBases.add(new IntegerSetting(module,(Setting<Integer>) setting,this.x,this.y));
            }
            if(setting.getValue() instanceof Float){
                settingBases.add(new FloatSetting(module,(Setting<Float>)setting,this.x,this.y));
            }
            if(setting.getValue() instanceof String){
                settingBases.add(new StringSetting(module,(Setting<String>) setting,this.x,this.y));
            }
            if(setting.getValue() instanceof Color){
                settingBases.add(new ColorSetting(module,(Setting<Color>) setting,this.x,this.y));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        NVGRenderUtil.drawText(
                "Setting " + module.getName(),
                MelyClient.fontManager.ubuntu,
                this.x,
                this.y - scrollY,
                15,
                new Color(255,255,255,255)
        );

        float offsetY = this.y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 15) + 5;
        float lastOffsetY = 0.0f;
        for(SettingBase settingBase : settingBases){
            if(!settingBase.setting.isVisible()){
                continue;
            }

            settingBase.setY(offsetY - scrollY);
            offsetY += settingBase.drawScreen(mouseX, mouseY, partialTicks);
            lastOffsetY = settingBase.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (MouseUtil.isHovering(
                505,
                152,
                720,
                353,
                mouseX,
                mouseY
        )){
            if (ClickGui.getINSTANCE().scrollCount > 0) {
                if ((scrollY >= 10)) {
                    scrollY -= 10;
                }
            } else if (ClickGui.getINSTANCE().scrollCount < 0) {
                if ((scrollY <= offsetY - lastOffsetY)) {
                    scrollY += 10;
                }
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for(SettingBase settingBase : settingBases){
            if(!settingBase.setting.isVisible()){
                continue;
            }

            settingBase.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state){
        for(SettingBase settingBase : settingBases){
            if(!settingBase.setting.isVisible()){
                continue;
            }

            settingBase.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        for(SettingBase settingBase : settingBases){
            if(!settingBase.setting.isVisible()){
                continue;
            }

            settingBase.keyTyped(typedChar, keyCode);
        }
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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
}
