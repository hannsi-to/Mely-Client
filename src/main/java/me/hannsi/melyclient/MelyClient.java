package me.hannsi.melyclient;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.hannsi.melyclient.manager.AltManager;
import me.hannsi.melyclient.manager.EventManager;
import me.hannsi.melyclient.manager.FontManager;
import me.hannsi.melyclient.manager.ModuleManager;
import me.hannsi.melyclient.util.system.math.time.TimeCalculator;
import me.hannsi.melyclient.util.render.nanovg.system.NVGUtil;
import me.hannsi.melyclient.util.render.shader.ShaderUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.opengl.Display;

@Mod(name = MelyClient.MOD_NAME,version = MelyClient.MOD_VER,modid = MelyClient.MOD_ID)
public class MelyClient {
    public static final String MOD_NAME = "MelyClient";
    public static final String MOD_VER = "1.0-beta";
    public static final String MOD_ID = "melyclient";
    public static final String MOD_PREFIX = ChatFormatting.AQUA + "<" + MelyClient.MOD_NAME + "> " + ChatFormatting.RESET;

    public static Logger logger = LogManager.getLogger(MOD_NAME);

    public static EventManager eventManager;
    public static ModuleManager moduleManager;
    public static FontManager fontManager;
    public static AltManager altManager;

    public static ShaderUtil shaderUtil;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger.info(MOD_NAME + " v" + MOD_VER + " loading...");
        long tookTime = TimeCalculator.calculate(this::load);
        logger.info(MOD_NAME + " v" + MOD_VER + " took " + tookTime + "ms to load!");
    }

    public void load(){
        Display.setTitle(MOD_NAME + " " + MOD_VER);
        Runtime.getRuntime().addShutdownHook(new Thread(MelyClient::unLoad));

        NVGUtil.nvg = NanoVGGL2.nvgCreate(NanoVGGL2.NVG_ANTIALIAS);
        MelyClient.shaderUtil = new ShaderUtil();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        fontManager = new FontManager();
        altManager = new AltManager();
    }

    public static void unLoad(){
        logger.info(MOD_NAME + " v" + MOD_VER + " unloading...");
        long tookTime = TimeCalculator.calculate(() -> {
            eventManager.unLoad();
            moduleManager.unLoad();
            fontManager = null;

            shaderUtil.unLoad();
        });
        logger.info(MOD_NAME + " v" + MOD_VER + " took " + tookTime + "ms to unload!");
    }
}