package me.hannsi.melyclient.module.system;

import me.hannsi.melyclient.util.system.conversion.BonIcon;

public enum Category {
    COMBAT("Combat", BonIcon.SWORDS),
    EXPLOIT("Exploit", BonIcon.BOLT),
    MOVEMENT("Movement", BonIcon.Transfer_Within_Station),
    PLAYER("Player", BonIcon.PERSON),
    RENDER("Render", BonIcon.VISIBILITY),
    MISC("Misc", BonIcon.LIST_ALT),
    CLIENT("Client", BonIcon.HOME),
    HUD("Hud", BonIcon.DESKTOP_WINDOWS);

    private final String display;
    private final String icon;

    Category(String display,String icon) {
        this.display = display;
        this.icon = icon;
    }

    public String getDisplay() {
        return display;
    }

    public String getIcon() {
        return icon;
    }
}