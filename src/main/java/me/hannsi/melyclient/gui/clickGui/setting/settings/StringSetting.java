package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.conversion.Keyboard;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
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
        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(),10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        ) || MouseUtil.isHoveringWH(
                this.x,
                this.y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu,10) + 5,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, ((Setting<String>)setting).getValue(),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA",10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    this.x,
                    this.y + 1,
                    10,
                    new Color(255,255,255,255),
                    5f,
                    ColorUtil.getRainbow(20, 255, 255)
            );

            ClickGui.getINSTANCE().setDescription(setting.getDescription());
        }else{
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    this.x,
                    this.y + 1,
                    10,
                    new Color(255,255,255,255)
            );
        }

        NVGRenderUtil.drawRoundedRectWH(
                this.x,
                this.y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu,10) + 5 - 1,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, ((Setting<String>)setting).getValue(),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA",10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f,
                new Color(30, 30, 30, 255)
        );

        NVGRenderUtil.drawText(
                ((Setting<String>) setting).getValue(),
                MelyClient.fontManager.ubuntu,
                this.x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10),
                this.y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu,10) + 5,
                10,
                new Color(255,255,255,255)
        );

        if(typed) {
            NVGRenderUtil.drawLineWH(
                    x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, ((Setting<String>)setting).getValue(),10) + 1,
                    y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu,10) + 5,
                    0,
                    MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                    1,
                    new Color(255,255,255,255)
            );
        }

        return MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu,10) + 5 - 1 + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2 + maxHeight;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(MouseUtil.isHoveringWH(
                this.x,
                this.y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu,10) + 5,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, ((Setting<String>)setting).getValue(),10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA",10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
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
                    ((Setting<String>)setting).setValue(StringUtil.removeLastChar(((Setting<String>)setting).getValue()));
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                ((Setting<String>)setting).setValue(StringUtil.addLastChar(((Setting<String>)setting).getValue(),typedChar));
            }
        }

        super.keyTyped(typedChar, keyCode);
    }
}
