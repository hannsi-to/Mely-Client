package me.hannsi.melyclient.module.modules.player;

import me.hannsi.melyclient.event.events.UpdateWalkingPlayerEvent;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.player.PacketUtil;
import me.hannsi.melyclient.util.player.PlayerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastEat extends Module {
    public Setting<Mode> mode = register(new Setting<>("Mode", Mode.NORMAL, "Change packets to be sent"));
    public Setting<Integer> speed = register(new Setting<>("Speed", 20, 1, 100, 1, "Change speed"));

    public FastEat() {
        super("FastEat", Category.PLAYER, "Speeds up the eating of food");
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayerPreEvent(UpdateWalkingPlayerEvent.Pre event) {
        if (!PlayerUtil.isEating()) {
            return;
        }

        for (int i = 0; i < speed.getValue() / 2f; i++) {
            switch (mode.getValue()) {
                case NORMAL:
                    PacketUtil.sendCPacketPlayer(mc.player.onGround);
                    PacketUtil.sendCPacketPlayerPosition(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.onGround);
                    break;
                case GHOSTLY:
                    PacketUtil.sendCPacketPlayerPositionRotation(mc.player.posX, mc.player.posY + 1E-9, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, false);
                    break;
            }
        }
    }

    public enum Mode implements IEnumSetting {
        NORMAL("Normal"), GHOSTLY("Ghostly");

        final String display;

        Mode(String display) {
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return Mode.values();
        }
    }
}
