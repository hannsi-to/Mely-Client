package me.hannsi.melyclient.util.system.debug;

import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.util.system.conversion.BonIcon;

import java.awt.*;

public enum DebugLevel implements IEnumSetting {
    DEBUG(0, "Debug", new Color(255, 255, 255, 255), BonIcon.CODE), INFO(1, "Info", new Color(0, 128, 255, 255), BonIcon.INFO), ERROR(2, "Error", new Color(255, 0, 0, 255), BonIcon.ERROR), WARNING(3, "Warning", new Color(255, 255, 0, 255), BonIcon.WARNING);

    int levelInt;
    String display;
    Color color;
    String icon;

    DebugLevel(int levelInt, String display, Color color, String icon) {
        this.levelInt = levelInt;
        this.display = display;
        this.color = color;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(int levelInt) {
        this.levelInt = levelInt;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public IEnumSetting[] getValues() {
        return values();
    }
}
