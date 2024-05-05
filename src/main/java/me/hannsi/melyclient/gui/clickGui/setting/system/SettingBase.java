package me.hannsi.melyclient.gui.clickGui.setting.system;

import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;

public class SettingBase {
    public static float maxHeight;
    public Module module;
    public Setting<?> setting;
    public float x;
    public float y;
    public float startOffsetY;
    public float endOffsetY;

    public SettingBase(Module module, Setting<?> setting, float x, float y) {
        this.module = module;
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.startOffsetY = 2.5f;
        this.endOffsetY = 2.5f;
        maxHeight = startOffsetY + endOffsetY;
    }

    public static float getMaxHeight() {
        return maxHeight;
    }

    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        return 0.0f;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void keyTyped(char typedChar, int keyCode) {
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

    public Setting<?> getSetting() {
        return setting;
    }

    public void setSetting(Setting<?> setting) {
        this.setting = setting;
    }

    public float getStartOffsetY() {
        return startOffsetY;
    }

    public void setStartOffsetY(float startOffsetY) {
        this.startOffsetY = startOffsetY;
    }

    public float getEndOffsetY() {
        return endOffsetY;
    }

    public void setEndOffsetY(float endOffsetY) {
        this.endOffsetY = endOffsetY;
    }
}
