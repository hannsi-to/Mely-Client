package me.hannsi.melyclient.module.modules.movement;

import me.hannsi.melyclient.event.events.UpdateWalkingPlayerEvent;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.player.movement.fly.Creative;
import me.hannsi.melyclient.util.player.movement.fly.Vanilla;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fly extends Module {
    public static Fly INSTANCE;
    public Setting<Mode> mode = register(new Setting<>("Mode", Mode.CREATIVE, "Sets the way the sky flies"));
    public Setting<Float> flySpeed = register(new Setting<>("FlySpeed", 1.0f, 0.1f, 10.0f, 0.1f, "Set the flying speed", () -> mode.getValue() == Mode.CREATIVE || mode.getValue() == Mode.VANILLA));
    public Setting<VanillaBypassMode> vanillaBypass = register(new Setting<>("VanillaBypassMode", VanillaBypassMode.MOTION, "Change the bypassing method", () -> mode.getValue() == Mode.VANILLA));
    public Creative creative;
    public Vanilla vanilla;

    public Fly() {
        super("Fly", Category.MOVEMENT, "You will be able to fly");
        INSTANCE = this;

        updateSetting();
    }

    public void updateSetting() {
        creative = new Creative(flySpeed);
        vanilla = new Vanilla(flySpeed, vanillaBypass);
    }

    @Override
    public void onEnable() {
        switch (mode.getValue()) {
            case CREATIVE:
                creative.onEnable();
                break;
        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        switch (mode.getValue()) {
            case CREATIVE:
                creative.onDisable();
                break;
            case VANILLA:
                vanilla.onDisable();
                break;
        }

        super.onDisable();
    }

    @Override
    public void onLoop(LoopType loopType) {
        updateSetting();

        switch (mode.getValue()) {
            case CREATIVE:
                creative.onLoop();
                break;
        }

        super.onLoop(loopType);
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayerPreEvent(UpdateWalkingPlayerEvent.Pre event) {
        switch (mode.getValue()) {
            case VANILLA:
                vanilla.onUpdateWalkingPlayerPreEvent(event);
                break;
        }
    }

    public enum Mode implements IEnumSetting {
        CREATIVE("Creative"), VANILLA("Vanilla");

        final String display;

        Mode(String display) {
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return Mode.values();
        }
    }

    public enum VanillaBypassMode implements IEnumSetting {
        OFF("Off"), MOTION("Motion"), PACKET("Packet");

        final String display;

        VanillaBypassMode(String display) {
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return VanillaBypassMode.values();
        }
    }
}
