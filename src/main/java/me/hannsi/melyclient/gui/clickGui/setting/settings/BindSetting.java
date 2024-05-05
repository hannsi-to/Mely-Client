package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Bind;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
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
        String text = (setBindMenu ? "Press key or click NONE" : Keyboard.getKeyName(((Setting<Bind>)setting).getValue().getKeyCode()));

        NVGRenderUtil.drawRoundedRectWH(
                this.x,
                this.y - 1,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + text,10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 2,
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) / 2f,
                new Color(30, 30, 30, 255)
        );

        NVGRenderUtil.drawText(
                text,
                MelyClient.fontManager.ubuntu,
                this.x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "A",10),
                this.y,
                10,
                new Color(255,255,255,255)
        );

        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + text,10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(), 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    this.x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + text,10) + 5,
                    this.y + 1,
                    10,
                    new Color(255,255,255,255),
                    5f,
                    ColorUtil.getRainbow(20, 255, 255)
            );
        }else{
            NVGRenderUtil.drawText(
                    setting.getName(),
                    MelyClient.fontManager.ubuntu,
                    this.x + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + text,10) + 5,
                    this.y + 1,
                    10,
                    new Color(255,255,255,255)
            );
        }

        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + text,10) + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, setting.getName(), 10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            ClickGui.INSTANCE.description = setting.getDescription();
        }

        return maxHeight + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10) + 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        String text = (setBindMenu ? "Press key or click NONE" : Keyboard.getKeyName(((Setting<Bind>)setting).getValue().getKeyCode()));

        if(MouseUtil.isHoveringWH(
                this.x,
                this.y,
                MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, "AA" + text,10),
                MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10),
                mouseX,
                mouseY
        )){
            setBindMenu = !setBindMenu;

            if(!setBindMenu){
                ((Setting<Bind>)setting).setValue(new Bind(Keyboard.KEY_NONE));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void keyTyped(char typedChar, int keyCode) {
        if(setBindMenu){
            ((Setting<Bind>) setting).setValue(new Bind(keyCode));
            setBindMenu = false;
        }

        super.keyTyped(typedChar, keyCode);
    }
}
