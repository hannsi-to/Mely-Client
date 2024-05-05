package me.hannsi.melyclient.mixin.org.lwjgl.nanovg;

import me.hannsi.melyclient.util.render.nanovg.system.Lwjgl2FunctionProvider;
import org.lwjgl.system.FunctionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.lwjgl.nanovg.NanoVGGLConfig")
public class MixinNanoVGGLConfig {
    @Inject(method = "getFunctionProvider", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void getFunctionProvider(String className, CallbackInfoReturnable<FunctionProvider> cir) {
        cir.setReturnValue(new Lwjgl2FunctionProvider());
    }
}
