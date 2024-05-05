package me.hannsi.melyclient.mixin.net.minecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMinecraft {
    @Accessor("timer")
    Timer getTimer();

    @Accessor("rightClickDelayTimer")
    void setRightClickDelayTimer(int setRightClickDelayTimer);

    @Accessor("session")
    void setSession(Session session);
}
