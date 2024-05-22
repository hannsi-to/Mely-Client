package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;
import java.util.Arrays;

public class EnumSetting extends SettingBase {
    public float maxEnumTextSize = 0.0f;
    public boolean open = false;

    public EnumSetting(Module module, Setting<IEnumSetting> setting, float x, float y) {
        super(module, setting, x, y);
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        maxEnumTextSize = 0.0f;

        for (IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()) {
            float nowSize = ubuntu10.getWidth("AA" + value.getDisplay());

            if (maxEnumTextSize < nowSize) {
                maxEnumTextSize = nowSize;
            }
        }

        NanoVGRenderUtil.drawRoundedRectWH(this.x, this.y - 1, maxEnumTextSize, ubuntu10.getHeight() + 2, ubuntu10.getHeight() / 2f, new Color(30, 30, 30, 255));

        float offsetY = (ubuntu10.getHeight() + 2);
        if (open) {
            int count = 0;

            for (IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()) {
                if (value == ((Setting<IEnumSetting>) setting).getValue()) {
                    continue;
                }

                count++;
            }

            NanoVGRenderUtil.drawRoundedRectWH(this.x, this.y - 1 + ubuntu10.getHeight() + 2, maxEnumTextSize, (ubuntu10.getHeight() + 2) * count, ubuntu10.getHeight() / 2f, new Color(30, 30, 30, 255));

            for (IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()) {
                if (value == ((Setting<IEnumSetting>) setting).getValue()) {
                    continue;
                }

                ubuntu10.drawText(value.getDisplay(), this.x + ubuntu10.getWidth("A"), this.y + offsetY, new Color(255, 255, 255, 255));

                offsetY += (ubuntu10.getHeight() + 2);
            }
        }

        ubuntu10.drawText(((Setting<IEnumSetting>) setting).getValue().getDisplay(), this.x + ubuntu10.getWidth("A"), this.y, new Color(255, 255, 255, 255));

        if (MouseUtil.isHoveringWH(this.x, this.y, maxEnumTextSize + 5 + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(setting.getName(), this.x + maxEnumTextSize + 5, this.y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            ubuntu10.drawText(setting.getName(), this.x + maxEnumTextSize + 5, this.y + 1, new Color(255, 255, 255, 255));
        }

        if (MouseUtil.isHoveringWH(this.x, this.y, maxEnumTextSize, ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription("Left click : Next mode" + ".   Right click : Previous mode" + ".   Middle Click : Mode Menu.");
        }

        if (MouseUtil.isHoveringWH(this.x + maxEnumTextSize, this.y, 5 + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.getINSTANCE().setDescription(setting.getDescription());
        }

        return maxHeight + offsetY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(this.x, this.y, maxEnumTextSize, ubuntu10.getHeight(), mouseX, mouseY)) {
            if (mouseButton == 0) {
                increase();
            } else if (mouseButton == 1) {
                decrease();
            } else if (mouseButton == 2) {
                open = !open;
            }
        }

        if (open) {
            int count = 1;

            for (IEnumSetting value : ((Setting<IEnumSetting>) setting).getValue().getValues()) {
                if (value == ((Setting<IEnumSetting>) setting).getValue()) {
                    continue;
                }

                if (MouseUtil.isHoveringWH(this.x, this.y - 1 + (ubuntu10.getHeight() + 2) * count, maxEnumTextSize, (ubuntu10.getHeight() + 2), mouseX, mouseY)) {
                    ((Setting<IEnumSetting>) setting).setValue(value);
                    break;
                }

                count++;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @SuppressWarnings("unchecked")
    public void increase() {
        int currentIndex = Arrays.asList(((Setting<IEnumSetting>) setting).getValue().getValues()).indexOf(((Setting<IEnumSetting>) setting).getValue());
        int nextIndex = (currentIndex + 1) % ((Setting<IEnumSetting>) setting).getValue().getValues().length;
        ((Setting<IEnumSetting>) setting).setValue(((Setting<IEnumSetting>) setting).getValue().getValues()[nextIndex]);
    }

    @SuppressWarnings("unchecked")
    public void decrease() {
        int currentIndex = Arrays.asList(((Setting<IEnumSetting>) setting).getValue().getValues()).indexOf(((Setting<IEnumSetting>) setting).getValue());
        int previousIndex = (currentIndex - 1 + ((Setting<IEnumSetting>) setting).getValue().getValues().length) % ((Setting<IEnumSetting>) setting).getValue().getValues().length;
        ((Setting<IEnumSetting>) setting).setValue(((Setting<IEnumSetting>) setting).getValue().getValues()[previousIndex]);
    }
}
