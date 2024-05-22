package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.StringUtil;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class InputAccountInfoBorder {
    public String inputType;
    public String icon;
    public String error;
    public float x;
    public float y;
    public boolean show;
    public boolean typed;

    public InputAccountInfoBorder(String inputType, String icon, String error, float x, float y) {
        this.inputType = inputType;
        this.icon = icon;
        this.error = error;
        this.x = x;
        this.y = y;
        this.show = false;
        this.typed = false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil bonIcon40 = new FontUtil(MelyClient.fontManager.bonIcon, 40);

        if (MouseUtil.isHovering(x + bonIcon40.getWidth(icon) / 2f, y + bonIcon40.getHeight(), x + bonIcon40.getWidth(icon) / 2f + (ubuntu15.getWidth(AltManagerScreen.tempAccount.getEmail() == null || AltManagerScreen.tempAccount.getEmail().isEmpty() ? "input your email" : AltManagerScreen.tempAccount.getEmail())), y + bonIcon40.getHeight() + (ubuntu15.getHeight()), mouseX, mouseY)) {
            typed = !typed;
        }

        if (inputType.equals("Password")) {
            if (MouseUtil.isHoveringWH(x + ClickGui2.INSTANCE.width / 3f - bonIcon15.getWidth(show ? BonIcon.VISIBILITYOFF : BonIcon.VISIBILITY) - 10, y + 2.5f + bonIcon40.getHeight() - (ubuntu15.getHeight() / 2f) * 3, bonIcon15.getWidth(show ? BonIcon.VISIBILITYOFF : BonIcon.VISIBILITY), bonIcon15.getHeight(), mouseX, mouseY)) {
                show = !show;
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (typed) {
            if (inputType.equals("Email")) {
                switch (keyCode) {
                    case Keyboard.KEY_ESCAPE:
                    case Keyboard.KEY_RETURN:
                        typed = false;
                        break;
                    case Keyboard.KEY_BACK:
                        AltManagerScreen.tempAccount.setEmail(StringUtil.removeLastChar(AltManagerScreen.tempAccount.getEmail()));
                }
                if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                    AltManagerScreen.tempAccount.setEmail(StringUtil.addLastChar(AltManagerScreen.tempAccount.getEmail(), typedChar));
                }
            } else if (inputType.equals("Password")) {
                switch (keyCode) {
                    case Keyboard.KEY_ESCAPE:
                    case Keyboard.KEY_RETURN:
                        typed = false;
                        break;
                    case Keyboard.KEY_BACK:
                        AltManagerScreen.tempAccount.setPassword(StringUtil.removeLastChar(AltManagerScreen.tempAccount.getPassword()));
                }
                if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                    AltManagerScreen.tempAccount.setPassword(StringUtil.addLastChar(AltManagerScreen.tempAccount.getPassword(), typedChar));
                }
            }
        }
    }

    public void draw(int mouseX, int mouseY, float width, float height) {
        if (AltManagerScreen.tempAccount.getEmail() == null) {
            AltManagerScreen.tempAccount.setEmail("");
        }
        if (AltManagerScreen.tempAccount.getPassword() == null) {
            AltManagerScreen.tempAccount.setPassword("");
        }

        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil bonIcon40 = new FontUtil(MelyClient.fontManager.bonIcon, 40);
        FontUtil bonIcon100 = new FontUtil(MelyClient.fontManager.bonIcon, 100);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
        FontUtil ubuntu20 = new FontUtil(MelyClient.fontManager.ubuntu, 20);

        NanoVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, width + 2, height + ubuntu20.getHeight() + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

        NanoVGRenderUtil.drawRectWH(x, y, width, height + ubuntu20.getHeight(), new Color(0, 0, 0, 255));

        if (MouseUtil.isHoveringWH(x, y, width, height + ubuntu20.getHeight(), mouseX, mouseY)) {
            NanoVGRenderUtil.drawOutLineRectWH(x, y, width, height + ubuntu20.getHeight(), 1.0f, new Color(91, 91, 91, 255));

            NanoVGRenderUtil.drawRadialGradientRect(x, y, width, height + ubuntu20.getHeight(), mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
        }

        bonIcon40.drawText(icon, x + 5, y + 2.5f, new Color(0, 120, 212, 255));

        if (inputType.equals("Password")) {
            bonIcon15.drawText(!show ? BonIcon.VISIBILITYOFF : BonIcon.VISIBILITY, x + width - bonIcon15.getWidth(show ? BonIcon.VISIBILITYOFF : BonIcon.VISIBILITY) - 10, y + 2.5f + bonIcon40.getHeight() - (ubuntu15.getHeight() / 2f) * 3, new Color(255, 255, 255, 255));
        }

        ubuntu15.drawText(inputType, x + 10 + bonIcon40.getWidth(icon), y + 2.5f + bonIcon40.getHeight() - (ubuntu15.getHeight() / 2f) * 3, new Color(255, 255, 255, 255));

        String drawText = "";
        if (!show) {
            for (int i = 0; i < AltManagerScreen.tempAccount.getPassword().length(); i++) {
                drawText = StringUtil.addLastChar(drawText, "*");
            }
        } else {
            drawText = AltManagerScreen.tempAccount.getPassword();
        }

        NanoVG.nvgScissor(nvg, x, y, width, height + ubuntu20.getHeight());
        if (inputType.equals("Email")) {
            ubuntu15.drawText(AltManagerScreen.tempAccount.getEmail().isEmpty() ? typed ? "" : "input your email." : AltManagerScreen.tempAccount.getEmail(), x + bonIcon40.getWidth(icon) / 2f, y + height, AltManagerScreen.tempAccount.getEmail().isEmpty() ? new Color(91, 91, 91, 255) : new Color(255, 255, 255, 255));
        } else if (inputType.equals("Password")) {
            ubuntu15.drawText(AltManagerScreen.tempAccount.getPassword().isEmpty() ? typed ? "" : "input your password." : drawText, x + bonIcon40.getWidth(icon) / 2f, y + height, AltManagerScreen.tempAccount.getPassword().isEmpty() ? new Color(91, 91, 91, 255) : new Color(255, 255, 255, 255));
        }

        if (typed) {
            if (inputType.equals("Email")) {
                NanoVGRenderUtil.drawLineWH(x + bonIcon40.getWidth(icon) / 2f + ubuntu15.getWidth(AltManagerScreen.tempAccount.getEmail()) + 2, y + height, 0, ubuntu15.getHeight(), 1f, new Color(255, 255, 255, 255));
            } else if (inputType.equals("Password")) {
                NanoVGRenderUtil.drawLineWH(x + bonIcon40.getWidth(icon) / 2f + ubuntu15.getWidth(drawText) + 2, y + height, 0, ubuntu15.getHeight(), 1f, new Color(255, 255, 255, 255));
            }
        }
        NanoVG.nvgReset(nvg);
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
