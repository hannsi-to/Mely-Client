package me.hannsi.melyclient.event.events;

import me.hannsi.melyclient.event.EventStage;
import net.minecraft.entity.MoverType;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class MotionEvent extends EventStage {
    public MoverType type;
    public double x;
    public double y;
    public double z;

    public MotionEvent(MoverType type, double x, double y, double z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoverType getType() {
        return type;
    }

    public void setType(MoverType type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
