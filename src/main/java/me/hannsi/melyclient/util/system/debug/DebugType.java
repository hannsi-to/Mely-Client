package me.hannsi.melyclient.util.system.debug;

import me.hannsi.melyclient.module.system.settings.IEnumSetting;

public enum DebugType implements IEnumSetting {
    EXCEPTION("Exception"), TEXT("Text");

    String display;

    DebugType(String display) {
        this.display = display;
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
