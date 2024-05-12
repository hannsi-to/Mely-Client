package me.hannsi.melyclient.mixin.net.minecraft.client;

import me.hannsi.melyclient.MelyClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = {"shutdownMinecraftApplet()V", "displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"}, at = @At(value = "HEAD"))
    private void stopClient(CallbackInfo callbackInfo) {
        MelyClient.unLoad();
    }
}
