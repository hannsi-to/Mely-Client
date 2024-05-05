package me.hannsi.melyclient.util.player.movement.fly;

import me.hannsi.melyclient.event.events.UpdateWalkingPlayerEvent;
import me.hannsi.melyclient.module.modules.movement.Fly;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.player.movement.MotionUtil;

public class Vanilla implements InterfaceMinecraft {
    public Setting<Float> flySpeed;
    public Setting<Fly.VanillaBypassMode> vanillaBypass;

    public Vanilla(Setting<Float> flySpeed, Setting<Fly.VanillaBypassMode> vanillaBypass) {
        this.flySpeed = flySpeed;
        this.vanillaBypass = vanillaBypass;
    }

    public void onDisable() {
        MotionUtil.stop();
    }

    public void onUpdateWalkingPlayerPreEvent(UpdateWalkingPlayerEvent.Pre event) {
        float kickBypassMotion = (float) 0.0626D;

        if (mc.player.ticksExisted % 2 == 0) {
            kickBypassMotion = -kickBypassMotion;
        }

        mc.player.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? flySpeed.getValue() : mc.gameSettings.keyBindSneak.isKeyDown() ? -flySpeed.getValue() : vanillaBypass.getValue() == Fly.VanillaBypassMode.MOTION ? kickBypassMotion : Math.random() / 1000;

        if (MotionUtil.isMoving()) {
            MotionUtil.strafe(flySpeed.getValue() + Math.random() / 100);
        } else {
            MotionUtil.stop();
        }
    }

    public Setting<Float> getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(Setting<Float> flySpeed) {
        this.flySpeed = flySpeed;
    }

    public Setting<Fly.VanillaBypassMode> getVanillaBypass() {
        return vanillaBypass;
    }

    public void setVanillaBypass(Setting<Fly.VanillaBypassMode> vanillaBypass) {
        this.vanillaBypass = vanillaBypass;
    }
}
