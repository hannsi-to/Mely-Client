package me.hannsi.melyclient.event.events;

import me.hannsi.melyclient.event.EventStage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class Render2DEvent extends EventStage {
    public float partialTicks;
    public ScaledResolution resolution;

    public Render2DEvent(float partialTicks, ScaledResolution resolution) {
        this.partialTicks = partialTicks;
        this.resolution = resolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

    public void setResolution(ScaledResolution resolution) {
        this.resolution = resolution;
    }
}
