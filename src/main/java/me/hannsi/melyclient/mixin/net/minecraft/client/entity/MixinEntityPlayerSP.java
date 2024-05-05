package me.hannsi.melyclient.mixin.net.minecraft.client.entity;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.event.events.LivingUpdateEvent;
import me.hannsi.melyclient.event.events.MotionEvent;
import me.hannsi.melyclient.event.events.UpdateWalkingPlayerEvent;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer implements InterfaceMinecraft {
    public MixinEntityPlayerSP() {
        super(mc.world, mc.player.getGameProfile());
    }

    @Inject(method = "onUpdateWalkingPlayer()V", at = @At(value = "HEAD"))
    public void onUpdateWalkingPlayer(CallbackInfo ci) {
        UpdateWalkingPlayerEvent.Pre event = new UpdateWalkingPlayerEvent.Pre(posX, posY, posZ, rotationYaw, rotationPitch, onGround);
        MinecraftForge.EVENT_BUS.post(event);

        if(Module.nullCheck()){
            return;
        }
        MelyClient.moduleManager.modules.stream().filter(Module::isToggle).forEach(Module::onUpdate);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At(value = "RETURN"))
    private void postMotion(CallbackInfo info) {
        UpdateWalkingPlayerEvent.Post event = new UpdateWalkingPlayerEvent.Post(posX, posY, posZ, rotationYaw, rotationPitch, onGround);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        MotionEvent motionEvent = new MotionEvent(type, x, y, z);
        MinecraftForge.EVENT_BUS.post(motionEvent);

        if (motionEvent.isCanceled()) {
            super.move(motionEvent.getType(), motionEvent.getX(), motionEvent.getY(), motionEvent.getZ());
        }

        else {
            super.move(type, x, y, z);
        }
    }

    @Redirect(method= "onLivingUpdate" , at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setSprinting(Z)V", ordinal = 2))
    public void onLivingUpdate(EntityPlayerSP entityPlayerSP, boolean sprintUpdate) {
        LivingUpdateEvent livingUpdateEvent = new LivingUpdateEvent(entityPlayerSP, sprintUpdate);
        MinecraftForge.EVENT_BUS.post(livingUpdateEvent);

        if (livingUpdateEvent.isCanceled()) {
            livingUpdateEvent.getEntityPlayerSP().setSprinting(true);
        }

        else {
            entityPlayerSP.setSprinting(sprintUpdate);
        }
    }
}
