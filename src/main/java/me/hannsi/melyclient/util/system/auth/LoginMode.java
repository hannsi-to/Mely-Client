package me.hannsi.melyclient.util.system.auth;

import me.hannsi.melyclient.module.system.settings.IEnumSetting;

public enum LoginMode implements IEnumSetting {
    MICROSOFT("Microsoft"), MINECRAFT("Minecraft");

    private final String display;

    LoginMode(String display) {
        this.display = display;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    @Override
    public IEnumSetting[] getValues() {
        return new IEnumSetting[0];
    }
}
