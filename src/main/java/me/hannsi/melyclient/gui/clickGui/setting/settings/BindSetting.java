package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Bind;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class BindSetting extends SettingBase {
    public boolean setBindMenu = false;

    public BindSetting(Module module, Setting<Bind> setting, float x, float y) {
        super(module, setting, x, y);
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        String text = (setBindMenu ? "Press key or click NONE" : Keyboard.getKeyName(((Setting<Bind>) setting).getValue().getKeyCode()));

        NanoVGRenderUtil.drawRoundedRectWH(this.x, this.y - 1, ubuntu10.getWidth("AA" + text), ubuntu10.getHeight() + 2, ubuntu10.getHeight() / 2f, new Color(30, 30, 30, 255));

        ubuntu10.drawText(text, this.x + ubuntu10.getWidth("A"), this.y, new Color(255, 255, 255, 255));

        if (MouseUtil.isHoveringWH(this.x, this.y, ubuntu10.getWidth("AA" + text) + 5 + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(setting.getName(), this.x + ubuntu10.getWidth("AA" + text) + 5, this.y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            ubuntu10.drawText(setting.getName(), this.x + ubuntu10.getWidth("AA" + text) + 5, this.y + 1, new Color(255, 255, 255, 255));
        }

        if (MouseUtil.isHoveringWH(this.x, this.y, ubuntu10.getWidth("AA" + text) + 5 + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.INSTANCE.description = setting.getDescription();
        }

        return maxHeight + ubuntu10.getHeight() + 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        String text = (setBindMenu ? "Press key or click NONE" : Keyboard.getKeyName(((Setting<Bind>) setting).getValue().getKeyCode()));

        if (MouseUtil.isHoveringWH(this.x, this.y, ubuntu10.getWidth("AA" + text), ubuntu10.getHeight(), mouseX, mouseY)) {
            setBindMenu = !setBindMenu;

            if (!setBindMenu) {
                ((Setting<Bind>) setting).setValue(new Bind(Keyboard.KEY_NONE));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void keyTyped(char typedChar, int keyCode) {
        if (setBindMenu) {
            ((Setting<Bind>) setting).setValue(new Bind(keyCode));
            setBindMenu = false;
        }

        super.keyTyped(typedChar, keyCode);
    }
}
