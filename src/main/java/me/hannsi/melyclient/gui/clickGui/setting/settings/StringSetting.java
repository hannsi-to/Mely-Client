package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.conversion.Keyboard;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import net.minecraft.util.ChatAllowedCharacters;

import java.awt.*;

public class StringSetting extends SettingBase {
    boolean typed = false;

    public StringSetting(Module module, Setting<String> setting, float x, float y) {
        super(module, setting, x, y);
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(this.x, this.y, ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY) || MouseUtil.isHoveringWH(this.x, this.y + ubuntu10.getHeight() + 5, ubuntu10.getWidth(((Setting<String>) setting).getValue()) + ubuntu10.getWidth("AA"), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(setting.getName(), this.x, this.y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));

            ClickGui.getINSTANCE().setDescription(setting.getDescription());
        } else {
            ubuntu10.drawText(setting.getName(), this.x, this.y + 1, new Color(255, 255, 255, 255));
        }

        NanoVGRenderUtil.drawRoundedRectWH(this.x, this.y + ubuntu10.getHeight() + 5 - 1, ubuntu10.getWidth(((Setting<String>) setting).getValue()) + ubuntu10.getWidth("AA"), ubuntu10.getHeight() + 2, ubuntu10.getHeight() / 2f, new Color(30, 30, 30, 255));

        ubuntu10.drawText(((Setting<String>) setting).getValue(), this.x + ubuntu10.getWidth("A"), this.y + ubuntu10.getHeight() + 5, new Color(255, 255, 255, 255));

        if (typed) {
            NanoVGRenderUtil.drawLineWH(x + ubuntu10.getWidth("A") + ubuntu10.getWidth(((Setting<String>) setting).getValue()) + 1, y + ubuntu10.getHeight() + 5, 0, ubuntu10.getHeight(), 1, new Color(255, 255, 255, 255));
        }

        return ubuntu10.getHeight() + 5 - 1 + ubuntu10.getHeight() + 2 + maxHeight;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(this.x, this.y + ubuntu10.getHeight() + 5, ubuntu10.getWidth(((Setting<String>) setting).getValue()) + ubuntu10.getWidth("AA"), ubuntu10.getHeight(), mouseX, mouseY)) {
            typed = !typed;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void keyTyped(char typedChar, int keyCode) {
        if (typed) {
            switch (keyCode) {
                case Keyboard.KEY_ESCAPE:
                case Keyboard.KEY_RETURN:
                    typed = false;
                    break;
                case Keyboard.KEY_BACK:
                    ((Setting<String>) setting).setValue(StringUtil.removeLastChar(((Setting<String>) setting).getValue()));
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                ((Setting<String>) setting).setValue(StringUtil.addLastChar(((Setting<String>) setting).getValue(), typedChar));
            }
        }

        super.keyTyped(typedChar, keyCode);
    }
}
