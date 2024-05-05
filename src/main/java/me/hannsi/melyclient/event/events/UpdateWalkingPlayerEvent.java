package me.hannsi.melyclient.event.events;

import me.hannsi.melyclient.event.EventStage;

public class UpdateWalkingPlayerEvent extends EventStage {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;

    public UpdateWalkingPlayerEvent(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
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

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public static class Pre extends UpdateWalkingPlayerEvent {
        public Pre(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            super(x, y, z, yaw, pitch, onGround);
        }
    }

    public static class Post extends UpdateWalkingPlayerEvent {
        public Post(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            super(x, y, z, yaw, pitch, onGround);
        }
    }
}
