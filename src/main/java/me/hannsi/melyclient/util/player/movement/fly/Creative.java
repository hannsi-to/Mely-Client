package me.hannsi.melyclient.util.player.movement.fly;

import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.InterfaceMinecraft;

public class Creative implements InterfaceMinecraft {
    public boolean startFlyingCapability;
    public Setting<Float> flySpeed;

    public Creative(Setting<Float> flySpeed) {
        this.flySpeed = flySpeed;
    }

    public void onEnable() {
        startFlyingCapability = mc.player.capabilities.isFlying;
    }

    public void onDisable() {
        mc.player.capabilities.isFlying = startFlyingCapability;
    }

    public void onLoop() {
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.setFlySpeed(flySpeed.getValue() / 16f);
    }

    public boolean isStartFlyingCapability() {
        return startFlyingCapability;
    }

    public void setStartFlyingCapability(boolean startFlyingCapability) {
        this.startFlyingCapability = startFlyingCapability;
    }

    public Setting<Float> getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(Setting<Float> flySpeed) {
        this.flySpeed = flySpeed;
    }
}
