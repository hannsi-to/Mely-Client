package me.hannsi.melyclient.mixin.net.minecraft.entity.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayer.class)
public interface IEntityPlayer {
    @Accessor("gameProfile")
    void setGameProfile(GameProfile gameProfile);
}
