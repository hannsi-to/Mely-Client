package me.hannsi.melyclient.mixin.net.minecraft.util;

import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Session.class)
public interface ISession {
    @Accessor("sessionType")
    Session.Type getSessionType();
}
