package me.hannsi.melyclient.module.modules.client;

import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
    public Setting<Float> step = register(new Setting<>("Step",10.0f,0.0f,20f,0.1f,"Change between settings"));

    public static ClickGui INSTANCE;

    public ClickGui(){
        super("ClickGui", Category.CLIENT,"gui.", Keyboard.KEY_Y);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new me.hannsi.melyclient.gui.clickGui2.ClickGui2());
    }

    @Override
    public void onUpdate() {
        SettingBase.maxHeight = ClickGui.INSTANCE.step.getValue();

        super.onUpdate();
    }
}
