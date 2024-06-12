package me.hannsi.melyclient.mixin.net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import me.hannsi.melyclient.event.events.PacketReadEvent;
import me.hannsi.melyclient.event.events.PacketSendEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At(value = "HEAD"), cancellable = true)
    public void readChannel(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo ci) {
        PacketReadEvent packetReadEvent = new PacketReadEvent(p_channelRead0_2_);
        MinecraftForge.EVENT_BUS.post(packetReadEvent);

        if (packetReadEvent.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At(value = "HEAD"), cancellable = true)
    public void sendPacket(Packet<?> packetIn, CallbackInfo ci) {
        PacketSendEvent packetSendEvent = new PacketSendEvent(packetIn);
        MinecraftForge.EVENT_BUS.post(packetSendEvent);
        if (packetSendEvent.isCanceled()) {
            ci.cancel();
        }
    }
}
