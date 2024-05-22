package me.hannsi.melyclient.util.system.file;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.Set;
import java.util.stream.Collectors;

public class ModsUtil implements InterfaceMinecraft {
    public static boolean isModInstalled(String modId) {
        Set<String> modIds = Loader.instance().getModList().stream().map(ModContainer::getModId).collect(Collectors.toSet());
        return modIds.contains(modId);
    }

    public static boolean isOptifineInstalled() {
        return isModInstalled("optifine");
    }
}
