package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.command.system.CommandBase;
import me.hannsi.melyclient.event.events.PacketSendEvent;
import me.hannsi.melyclient.event.events.Render2DEvent;
import me.hannsi.melyclient.event.events.Render3DEvent;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.chat.ChatData;
import me.hannsi.melyclient.util.system.chat.ChatUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import static me.hannsi.melyclient.module.system.Module.nullCheck;

public class EventManager implements InterfaceMinecraft {
    public EventManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void unLoad() {
        MinecraftForge.EVENT_BUS.unregister(this);
        MelyClient.eventManager = null;
    }

    @SubscribeEvent
    public void onPacketSendEvent(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof CPacketChatMessage) {
            ChatUtil.playerSendMessageLog.add(new ChatData(((CPacketChatMessage) packet).getMessage()));
        }
    }

    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (nullCheck()) {
            return;
        }

        if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {
            int key = Keyboard.getEventKey();

            for (Module module : MelyClient.moduleManager.modules) {
                if (module.getKeyBind().getValue().getKeyCode() == key && module.toggleMode.getValue() == Module.ToggleMode.NORMAL) {
                    module.toggle();
                }

                module.onKeyInput(event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        if (Module.nullCheck()) {
            return;
        }

        if (event.player != mc.player) {
            return;
        }

        MelyClient.moduleManager.modules.stream().filter(Module::isToggle).forEach(Module::onTick);

    }

    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), event.getResolution());
            MinecraftForge.EVENT_BUS.post(render2DEvent);

            for (Module module : MelyClient.moduleManager.modules) {
                if (module.isToggle()) {
                    module.onRender2D(render2DEvent);
                }
            }

            if (mc.currentScreen != null) {
                return;
            }
            for (Module m : MelyClient.moduleManager.modules) {
                if (m.toggleMode.getValue() == Module.ToggleMode.PRESS) {
                    if (Keyboard.isKeyDown(m.keyBind.getValue().getKeyCode()) && !m.isToggle()) {
                        m.toggle();
                    } else if (!Keyboard.isKeyDown(m.keyBind.getValue().getKeyCode()) && m.isToggle()) {
                        m.toggle();
                    }
                }

                if (m.toggleMode.getValue() == Module.ToggleMode.RELEASE) {
                    if (!Keyboard.isKeyDown(m.keyBind.getValue().getKeyCode()) && !m.isToggle()) {
                        m.toggle();
                    } else if (Keyboard.isKeyDown(m.keyBind.getValue().getKeyCode()) && m.isToggle()) {
                        m.toggle();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        mc.profiler.startSection(MelyClient.MOD_NAME);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0F);

        Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
        MinecraftForge.EVENT_BUS.post(render3dEvent);

        for (Module module : MelyClient.moduleManager.modules) {
            if (module.isToggle()) {
                module.onRender3D(render3dEvent);
            }
        }

        GlStateManager.glLineWidth(1.0F);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        mc.profiler.endSection();
    }

    @SubscribeEvent
    public void onClientChatEvent(ClientChatEvent event) {
        String message = event.getMessage();

        if (message.startsWith(CommandBase.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                EventManager.mc.ingameGUI.getChatGUI().addToSentMessages(message);
                if (message.length() > 1) {
                    MelyClient.commandManager.executeCommand(message.substring(CommandBase.getCommandPrefix().length() - 1));
                } else {
                    String errorMessage = "Please enter a command.";
                    CommandBase.sendMessage(errorMessage, true);
                    new DebugLog(errorMessage, DebugLevel.ERROR);
                }
            } catch (Exception e) {
                if (e instanceof NullPointerException) {
                    CommandBase.debugAndSendErrorMessage("Missing argument.");
                    return;
                }

                new DebugLog(e, DebugLevel.ERROR);

                String errorMessage = "An error occurred while running this command. Check the log.";
                CommandBase.sendMessage(errorMessage, true);
                new DebugLog(errorMessage, DebugLevel.ERROR);
            }
            event.setMessage("");
        }
    }

    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event) {
        GuiScreen guiScreen = event.getGui();

        if (guiScreen instanceof GuiMainMenu) {
            //event.setGui(new CustomGuiMainMenu());
        }
    }
}
