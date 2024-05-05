package me.hannsi.melyclient.module.modules.movement;

import me.hannsi.melyclient.event.events.LivingUpdateEvent;
import me.hannsi.melyclient.event.events.MotionEvent;
import me.hannsi.melyclient.event.events.UpdateMoveStateEvent;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.player.movement.MotionUtil;
import me.hannsi.melyclient.util.player.PlayerUtil;
import net.minecraft.init.MobEffects;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class Sprint extends Module {
    public Setting<Mode> mode = register(new Setting<>(
            "Mode",
            Mode.RAGE,
            "Change sprint mode"
    ));
    public Setting<CheckMode> checkMode = register(new Setting<>(
            "CheckMode",
            CheckMode.CUSTOM,
            "Change the way you check if you can sprint"
    ));
    public Setting<Boolean> food = register(new Setting<>(
            "Food",
            true,
            "Avoid sprinting when hungry",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Float> foodValue = register(new Setting<>(
            "HungryLevel",
            6.0f,
            0.0f,
            20.0f,
            0.1f,
            "You can specify a numerical value to check your hunger",
            () -> food.getValue() && checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> wall = register(new Setting<>(
            "Wall",
            true,
            "Confirm collision with wall",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> sneak = register(new Setting<>(
            "Sneak",
            true,
            "Check the sneak peek",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> use = register(new Setting<>(
            "Use",
            true,
            "Make sure you are using things",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> liquid = register(new Setting<>(
            "Liquid",
            true,
            "Check to see if it is in liquid",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> water = register(new Setting<>(
            "Water",
            true,
            "Water Check",
            () -> checkMode.getValue() == CheckMode.CUSTOM && liquid.getValue()
    ));
    public Setting<Boolean> lava = register(new Setting<>(
            "Lava",
            true,
            "Lava Check",
            () -> checkMode.getValue() == CheckMode.CUSTOM && liquid.getValue()
    ));
    public Setting<Boolean> web = register(new Setting<>(
            "Web",
            true,
            "Check Web",() -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> portal = register(new Setting<>(
            "Portal",
            false,
            "Check Portal",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> ladder = register(new Setting<>(
            "Ladder",
            true,
            "Check ladder",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));
    public Setting<Boolean> fly = register(new Setting<>(
            "Fly",
            true,
            "Check Fly",
            () -> checkMode.getValue() == CheckMode.CUSTOM
    ));

    public static Sprint INSTANCE;
    public boolean canSprint;

    public Sprint(){
        super("Sprint", Category.MOVEMENT,"Automatically sprints.", Keyboard.KEY_NONE);

        INSTANCE = this;
    }

    @SubscribeEvent
    public void onInput(InputUpdateEvent event) {
        MovementInput movementInput = event.getMovementInput();
        switch (mode.getValue()) {
            case LEGIT:
                canSprint = movementInput.moveForward > 0;
                break;
            case RAGE:
                canSprint = Math.abs(movementInput.moveForward) > 0 || Math.abs(movementInput.moveStrafe) > 0;
                break;
            case NORMAL:
                canSprint = mc.gameSettings.keyBindForward.isKeyDown();
                break;
            case DIRECTIONAL:
            case INSTANT:
                canSprint = true;
                break;
        }
    }

    @SubscribeEvent
    public void onMotion(MotionEvent event){
        if(mode.getValue() == Mode.INSTANT){
            if(sprintCheck()){
                return;
            }

            event.setCanceled(true);

            double baseSpeed = 0.2873;

            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                double amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
                baseSpeed *= 1 + (0.2 * (amplifier + 1));
            }

            if (mc.player.isPotionActive(MobEffects.SLOWNESS)) {
                double amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier();
                baseSpeed /= 1 + (0.2 * (amplifier + 1));
            }

            double moveSpeed = baseSpeed;

            if (mc.player.isSneaking()) {
                moveSpeed = baseSpeed * 0.3;
            }

            float forward = mc.player.movementInput.moveForward;
            float strafe = mc.player.movementInput.moveStrafe;
            float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

            if (!MotionUtil.isMoving()) {
                event.setX(0);
                event.setZ(0);
            }

            if (forward != 0) {
                if (strafe > 0) {
                    yaw += ((forward > 0) ? -45 : 45);
                }

                else if (strafe < 0) {
                    yaw += ((forward > 0) ? 45 : -45);
                }

                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                }

                else if (forward < 0) {
                    forward = -1;
                }
            }

            double cos = Math.cos(Math.toRadians(yaw));
            double sin = -Math.sin(Math.toRadians(yaw));

            event.setX((forward * moveSpeed * sin) + (strafe * moveSpeed * cos));
            event.setZ((forward * moveSpeed * cos) - (strafe * moveSpeed * sin));
        }
    }

    @SubscribeEvent
    public void onUpdateMoveStateEvent(UpdateMoveStateEvent event){
        if(sprintCheck()){
            if(mode.getValue() == Mode.INSTANT){
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.movementInput.moveForward *= (1 / 0.3F);
                    mc.player.movementInput.moveStrafe *= (1 / 0.3F);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event){
        if(sprintCheck()){
            if(MotionUtil.isMoving() && (mode.getValue() == Mode.DIRECTIONAL || mode.getValue() == Mode.INSTANT)){
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void onLoop(LoopType loopType) {
        doSprint();

        super.onLoop(loopType);
    }

    public void doSprint(){
        if(sprintCheck()) {
            mc.player.setSprinting(canSprint);
        }
    }

    public boolean sprintCheck(){
        if((food.getValue() && checkMode.getValue() == CheckMode.CUSTOM) || checkMode.getValue() == CheckMode.STRICT) {
            if (mc.player.getFoodStats().getFoodLevel() <= foodValue.getValue()) {
                return false;
            }
        }
        if((wall.getValue() && checkMode.getValue() == CheckMode.CUSTOM) || checkMode.getValue() == CheckMode.STRICT) {
            if (mc.player.collidedHorizontally) {
                return false;
            }
        }
        if((web.getValue() && checkMode.getValue() == CheckMode.CUSTOM) || checkMode.getValue() == CheckMode.STRICT){
            if(PlayerUtil.getInWeb()){
                return false;
            }
        }
        if((ladder.getValue() && checkMode.getValue() == CheckMode.CUSTOM) || checkMode.getValue() == CheckMode.STRICT){
            if(PlayerUtil.isOnLadder()){
                return false;
            }
        }
        if((fly.getValue() && checkMode.getValue() == CheckMode.CUSTOM) || checkMode.getValue() == CheckMode.STRICT){
            if(PlayerUtil.isFly()){
                return false;
            }
        }
        if(portal.getValue() && checkMode.getValue() == CheckMode.CUSTOM){
            if(PlayerUtil.getInPortal()){
                return false;
            }
        }
        if(liquid.getValue() && checkMode.getValue() == CheckMode.CUSTOM){
            if(lava.getValue()){
                if(PlayerUtil.isInLava()){
                    return false;
                }
            }
            if(water.getValue()){
                if(PlayerUtil.isInWater()){
                    return false;
                }
            }
        }else if(checkMode.getValue() == CheckMode.STRICT){
            if(PlayerUtil.isInLiquid()){
                return false;
            }
        }
        if(sneak.getValue() || checkMode.getValue() == CheckMode.STRICT) {
            if (mc.player.isSneaking()) {
                return false;
            }
        }
        if(use.getValue() || checkMode.getValue() == CheckMode.STRICT) {
            return !PlayerUtil.isHandActive();
        }

        return true;
    }

    public enum Mode implements IEnumSetting {
        LEGIT("Legit"), RAGE("Rage"),INSTANT("Instant"),NORMAL("Normal"),DIRECTIONAL("Directional");

        private final String display;

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

    public enum CheckMode implements IEnumSetting{
        CUSTOM("Custom"),STRICT("Strict");

        private final String display;

        CheckMode(String display){
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return CheckMode.values();
        }
    }
}
