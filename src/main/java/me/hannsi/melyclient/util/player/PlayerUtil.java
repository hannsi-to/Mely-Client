package me.hannsi.melyclient.util.player;

import me.hannsi.melyclient.mixin.net.minecraft.client.entity.IEntity;
import me.hannsi.melyclient.module.modules.movement.Fly;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumAction;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil implements InterfaceMinecraft {
    public static boolean isInLiquid() {
        return isInLava() || isInWater();
    }

    public static boolean isInWater() {
        return mc.player.isInsideOfMaterial(Material.WATER) || mc.player.isInWater();
    }

    public static boolean isInLava() {
        return mc.player.isInsideOfMaterial(Material.LAVA) || mc.player.isInLava();
    }

    public static boolean getInWeb() {
        return ((IEntity) mc.player).getInWeb();
    }

    public static boolean getInPortal() {
        return ((IEntity) mc.player).getInPortal();
    }

    public static boolean isOnLadder() {
        return mc.player.isOnLadder();
    }

    public static boolean isFly() {
        return isNormalFly() || isElytraFlying();
    }

    public static boolean isNormalFly() {
        return mc.player.capabilities.isFlying || mc.player.capabilities.allowFlying || Fly.INSTANCE.isToggle();
    }

    public static boolean isElytraFlying() {
        return mc.player.isElytraFlying();
    }

    public static boolean isHandActive() {
        return mc.player.isHandActive();
    }

    public static boolean isEating() {
        if (mc.player.isHandActive()) {
            return mc.player.getActiveItemStack().getItemUseAction().equals(EnumAction.EAT) || mc.player.getActiveItemStack().getItemUseAction().equals(EnumAction.DRINK);
        }

        return false;
    }

    public static double getHealth() {
        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
    }

    public static BlockPos getPosition() {
        return new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    }
}
