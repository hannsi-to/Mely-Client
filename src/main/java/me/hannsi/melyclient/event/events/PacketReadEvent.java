package me.hannsi.melyclient.event.events;

import me.hannsi.melyclient.event.EventStage;
import net.minecraft.network.Packet;

public class PacketReadEvent extends EventStage {
    private final Packet<?> packet;

    public PacketReadEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
