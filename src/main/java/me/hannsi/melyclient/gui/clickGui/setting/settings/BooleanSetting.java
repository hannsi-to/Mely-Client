package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.MouseUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;

public class BooleanSetting extends SettingBase {
    public float fontSize = 0;

    public BooleanSetting(Module module, Setting<Boolean> setting, float x, float y) {
        super(module, setting, x, y);
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (((Setting<Boolean>) setting).getValue()) {
            if (fontSize < 10) {
                fontSize += 1;
            } else {
                fontSize = 10;
            }
        } else {
            if (fontSize > 0) {
                fontSize -= 1;
            } else {
                fontSize = 0;
            }
        }

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getHeight() + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(setting.getName(), x + ubuntu10.getHeight() + 5, y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            ubuntu10.drawText(setting.getName(), x + ubuntu10.getHeight() + 5, y + 1, new Color(255, 255, 255, 255));
        }

        NanoVGRenderUtil.drawRectWH(x, y, ubuntu10.getHeight(), ubuntu10.getHeight(), new Color(255, 255, 255, 255));

        NanoVGRenderUtil.drawRectWH(x + 0.5f, y + 0.5f, ubuntu10.getHeight() - 1, ubuntu10.getHeight() - 1, new Color(30, 30, 30, 255));

        bonIcon10.setSize(fontSize);
        bonIcon10.drawBlurTextCenter(BonIcon.CHECK, x + (ubuntu10.getHeight() / 2f), y + (ubuntu10.getHeight() / 2f) + 1, new Color(255, 255, 255, 255), 5, ColorUtil.getRainbow(20, 255, 255));
        bonIcon10.setSize(10f);

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getHeight() + 5 + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.INSTANCE.description = setting.getDescription();
        }

        return maxHeight + ubuntu10.getHeight();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getHeight(), ubuntu10.getHeight(), mouseX, mouseY)) {
            ((Setting<Boolean>) setting).setValue(!((Setting<Boolean>) setting).getValue());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
