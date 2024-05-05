package me.hannsi.melyclient.util.player;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class PacketUtil implements InterfaceMinecraft {
    public static void sendPacket(Packet<?> packet) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        mc.player.connection.sendPacket(packet);
    }

    public static void sendCPacketSneakState(boolean sneaking) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (sneaking) {
            sendCPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING);
        } else {
            sendCPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING);
        }
    }

    public static void sendCPacketEntityAction(Entity entityIn, CPacketEntityAction.Action actionIn) {
        sendPacket(new CPacketEntityAction(entityIn, actionIn));
    }

    public static void sendCPacketPlayerPosition(double xIn, double yIn, double zIn, boolean onGroundIn) {
        sendPacket(new CPacketPlayer.Position(xIn, yIn, zIn, onGroundIn));
    }

    public static void sendCPacketPlayer(boolean onGroundIn) {
        sendPacket(new CPacketPlayer(onGroundIn));
    }

    public static void sendCPacketPlayerDigging(CPacketPlayerDigging.Action actionIn, BlockPos posIn, EnumFacing facingIn) {
        sendPacket(new CPacketPlayerDigging(actionIn, posIn, facingIn));
    }

    public static void sendCPacketPlayerTryUseItem(EnumHand handIn) {
        sendPacket(new CPacketPlayerTryUseItem(handIn));
    }

    public static void sendCPacketPlayerTryUseItemOnBlock(BlockPos posIn, EnumFacing placedBlockDirectionIn, EnumHand handIn, float facingXIn, float facingYIn, float facingZIn) {
        sendPacket(new CPacketPlayerTryUseItemOnBlock(posIn, placedBlockDirectionIn, handIn, facingXIn, facingYIn, facingZIn));
    }

    public static void sendCPacketPlayerPositionRotation(double xIn, double yIn, double zIn, float yawIn, float pitchIn, boolean onGroundIn) {
        sendPacket(new CPacketPlayer.PositionRotation(xIn, yIn, zIn, yawIn, pitchIn, onGroundIn));
    }
}
