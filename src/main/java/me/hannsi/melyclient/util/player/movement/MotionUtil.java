package me.hannsi.melyclient.util.player.movement;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.util.math.MathHelper;

public class MotionUtil implements InterfaceMinecraft {
    public static boolean isMoving() {
        return mc.player.moveForward != 0 || mc.player.moveStrafing != 0;
    }

    public static void stop() {
        mc.player.motionX = mc.player.motionZ = 0;
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static double getSpeed() {
        return Math.hypot(mc.player.motionX, mc.player.motionZ);
    }

    public static void strafe(float speed) {
        strafe(speed, (float) getDirection());
    }

    public static void strafe(double speed) {
        strafe((float) speed);
    }

    public static void strafe(float speed, float yaw) {
        if (!isMoving()) {
            return;
        }

        mc.player.motionX = -MathHelper.sin(yaw) * speed;
        mc.player.motionZ = MathHelper.cos(yaw) * speed;
    }

    public static double getDirection() {
        float rotationYaw = mc.player.rotationYaw;

        if (mc.player.moveForward < 0F) {
            rotationYaw += 180F;
        }

        float forward = 1F;

        if (mc.player.moveForward < 0F) {
            forward = -0.5F;
        } else if (mc.player.moveForward > 0F) {
            forward = 0.5F;
        }

        if (mc.player.moveStrafing > 0F) {
            rotationYaw -= 90F * forward;
        }
        if (mc.player.moveStrafing < 0F) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }
}
