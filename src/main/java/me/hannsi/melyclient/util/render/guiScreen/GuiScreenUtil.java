package me.hannsi.melyclient.util.render.guiScreen;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.opengl.Display;

import java.lang.reflect.Field;
import java.util.Objects;

public class GuiScreenUtil implements InterfaceMinecraft {
    public static void setFocus() {
        if (Display.isActive()) {
            if (!mc.inGameHasFocus) {
                if (!Minecraft.IS_RUNNING_ON_MAC) {
                    KeyBinding.updateKeyBindState();
                }

                mc.inGameHasFocus = true;
                mc.mouseHelper.grabMouseCursor();
                try {
                    Field field = getField(Minecraft.class, "leftClickCounter", "field_71429_W");
                    Objects.requireNonNull(field).setAccessible(true);
                    field.set(mc, 10000);
                } catch (IllegalAccessException e) {
                    new DebugLog(e, DebugLevel.WARNING);
                }
            }
        }
    }

    public static Field getField(Class<?> targetClass, String... targetFields) {
        for (String targetField : targetFields) {
            try {
                return targetClass.getDeclaredField(targetField);
            } catch (NoSuchFieldException e) {
                new DebugLog(e, DebugLevel.WARNING);
            }
        }
        return null;
    }
}
