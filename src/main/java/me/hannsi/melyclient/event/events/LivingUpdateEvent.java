package me.hannsi.melyclient.event.events;

import me.hannsi.melyclient.event.EventStage;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class LivingUpdateEvent extends EventStage {
    public final EntityPlayerSP entityPlayerSP;
    public final boolean sprinting;

    public LivingUpdateEvent(EntityPlayerSP entityPlayerSP, boolean sprinting) {
        this.entityPlayerSP = entityPlayerSP;
        this.sprinting = sprinting;
    }

    public EntityPlayerSP getEntityPlayerSP() {
        return entityPlayerSP;
    }

    public boolean isSprinting() {
        return sprinting;
    }
}
