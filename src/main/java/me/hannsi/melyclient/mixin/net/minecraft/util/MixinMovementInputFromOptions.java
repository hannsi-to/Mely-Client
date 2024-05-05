package me.hannsi.melyclient.mixin.net.minecraft.util;

import me.hannsi.melyclient.event.events.UpdateMoveStateEvent;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions {
    @Inject(method = "updatePlayerMoveState", at = @At("RETURN"))
    public void onUpdatePlayerMoveState(CallbackInfo info) {
        UpdateMoveStateEvent updateMoveStateEvent = new UpdateMoveStateEvent();
        MinecraftForge.EVENT_BUS.post(updateMoveStateEvent);
    }
}