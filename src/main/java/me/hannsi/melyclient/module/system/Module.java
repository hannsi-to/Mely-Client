package me.hannsi.melyclient.module.system;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.event.events.Render2DEvent;
import me.hannsi.melyclient.event.events.Render3DEvent;
import me.hannsi.melyclient.module.system.settings.Bind;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Module implements InterfaceMinecraft {
    public String name;
    public Category category;
    public String description;
    public Setting<Bind> keyBind;
    public Setting<ToggleMode> toggleMode;
    public Setting<LoopType> loopType;
    public boolean toggle;
    public List<Setting<?>> settings = new ArrayList<>();

    public Module(String name, Category category, String description, int keyBind, boolean toggle) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.keyBind = register(new Setting<>("Bind", new Bind(keyBind), "Set key bindings"));
        this.toggleMode = register(new Setting<>("ToggleMode", ToggleMode.NORMAL, "Change the toggle method"));
        this.loopType = register(new Setting<>("LoopType", LoopType.TICK, "Change the looping method"));
        this.toggle = toggle;
    }

    public Module(String name, Category category, String description, boolean toggle) {
        this(name, category, description, Keyboard.KEY_NONE, toggle);
    }

    public Module(String name, Category category, String description, int keyBind) {
        this(name, category, description, keyBind, false);
    }

    public Module(String name, Category category, String description) {
        this(name, category, description, Keyboard.KEY_NONE, false);
    }

    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register(this);

        sendMessage(this.getName() + ChatFormatting.GREEN + " Enable.");
        MelyClient.notificationManager.addNotification(this.getName(), "Enable.", DebugLevel.INFO);
        onEnable();
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);

        sendMessage(this.getName() + ChatFormatting.RED + " Disable.");
        MelyClient.notificationManager.addNotification(this.getName(), "Disable.", DebugLevel.ERROR);
        onDisable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onUpdate() {
        if (loopType.getValue() == LoopType.UPDATE) {
            onLoop(LoopType.UPDATE);
        }
    }

    public void onTick() {
        if (loopType.getValue() == LoopType.TICK) {
            onLoop(LoopType.TICK);
        }
    }

    public void onLoop(LoopType loopType) {

    }

    public void onKeyInput(InputEvent.KeyInputEvent event) {

    }

    public void onRender2D(Render2DEvent event) {

    }

    public void onRender3D(Render3DEvent event) {

    }

    public void toggle() {
        if (toggle) {
            disable();
        } else {
            enable();
        }

        toggle = !toggle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Setting<Bind> getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(Setting<Bind> keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting<?>> settings) {
        this.settings = settings;
    }

    public void sendMessage(String message) {
        if (nullCheck()) {
            return;
        }
        mc.player.sendMessage(new TextComponentString(MelyClient.MOD_PREFIX + message));
    }

    public <T extends Setting<?>> T register(T setting) {
        settings.add(setting);
        return setting;
    }

    public enum ToggleMode implements IEnumSetting {
        NORMAL("Normal"), PRESS("Press"), RELEASE("Release");

        private final String display;

        ToggleMode(String display) {
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return values();
        }
    }

    public enum LoopType implements IEnumSetting {
        TICK("Tick"), UPDATE("Update");

        private final String display;

        LoopType(String display) {
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return LoopType.values();
        }
    }
}
