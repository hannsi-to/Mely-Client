package me.hannsi.melyclient.mixin.net.minecraft.client.gui;

import me.hannsi.melyclient.util.system.chat.ChatData;
import me.hannsi.melyclient.util.system.chat.ChatUtil;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {
    @Inject(method = "printChatMessage", at = @At("HEAD"))
    public void onPrintChatMessage(ITextComponent chatComponent, CallbackInfo ci) {
        ChatUtil.messageLog.add(new ChatData(chatComponent.getFormattedText()));
    }
}
