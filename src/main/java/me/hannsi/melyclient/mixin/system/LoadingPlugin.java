package me.hannsi.melyclient.mixin.system;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@IFMLLoadingPlugin.Name("iridescence")
public class LoadingPlugin implements IFMLLoadingPlugin {
    public LoadingPlugin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.melyclient.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");

        try {
            Field transformerExceptions = LaunchClassLoader.class.getDeclaredField("classLoaderExceptions");
            transformerExceptions.setAccessible(true);
            @SuppressWarnings("unchecked") Set<String> o = (Set<String>) transformerExceptions.get(Launch.classLoader);
            o.remove("org.lwjgl.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}