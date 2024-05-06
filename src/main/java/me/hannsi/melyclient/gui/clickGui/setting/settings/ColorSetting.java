package me.hannsi.melyclient.gui.clickGui.setting.settings;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.ClickGui;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingBase;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.MathUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;

public class ColorSetting extends SettingBase {
    public Setting<Color> colorSetting;
    public boolean open;
    public boolean typed;
    public boolean pickerBoxDrag;
    public boolean redBarDrag;
    public boolean greenBarDrag;
    public boolean blueBarDrag;
    public boolean alphaBarDrag;
    public boolean saturationDrag;
    public boolean brightnessDrag;
    public boolean delayBarDrag;
    public Color defaultColor;
    public Color maxColor;
    public float circleX;
    public float circleY;
    public int lastMouseX;
    public int lastMouseY;
    public float tempMouseX;
    public float tempMouseY;
    public float fontHeight;
    public float pickerBoxScale;
    public float offsetY;
    public float fontSize;

    public ColorSetting(Module module, Setting<Color> setting, float x, float y) {
        super(module, setting, x, y);

        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        colorSetting = setting;
        open = false;
        typed = false;
        pickerBoxDrag = false;
        defaultColor = colorSetting.getValue();
        maxColor = defaultColor;
        circleX = x + ubuntu10.getHeight() / 2f;
        circleY = y + ubuntu10.getHeight() + 5 + ubuntu10.getHeight() / 2f;
        lastMouseX = 0;
        lastMouseY = 0;
        fontHeight = ubuntu10.getHeight();
        pickerBoxScale = 100 - ((fontHeight / 2f) * 2);
        tempMouseX = x + fontHeight / 2f + pickerBoxScale + 1;
        tempMouseY = y + fontHeight + 5 + fontHeight / 2f + pickerBoxScale;
        offsetY = 0;
        fontSize = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public float drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getHeight() + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ubuntu10.drawBlurText(setting.getName(), x + ubuntu10.getHeight() + 5, y + 1, new Color(255, 255, 255, 255), 5f, ColorUtil.getRainbow(20, 255, 255));
        } else {
            ubuntu10.drawText(setting.getName(), x + ubuntu10.getHeight() + 5, y + 1, new Color(255, 255, 255, 255));
        }

        NVGRenderUtil.drawRectWH(x, y, ubuntu10.getHeight(), ubuntu10.getHeight(), new Color(255, 255, 255, 255));

        NVGRenderUtil.drawRectWH(x + 0.5f, y + 0.5f, ubuntu10.getHeight() - 1, ubuntu10.getHeight() - 1, new Color(30, 30, 30, 255));

        NVGRenderUtil.drawRectWH(x + 0.5f, y + 0.5f, ubuntu10.getHeight() - 1, ubuntu10.getHeight() - 1, colorSetting.getValue());

        if (open) {
            drawPicker(mouseX, mouseY);
        }

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getHeight() + 5 + ubuntu10.getWidth(setting.getName()), ubuntu10.getHeight(), mouseX, mouseY)) {
            ClickGui.INSTANCE.description = setting.getDescription();
        }

        setting = colorSetting;

        return maxHeight + ubuntu10.getHeight() + (open ? 105 : 0);
    }

    private void drawPicker(int mouseX, int mouseY) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        offsetY = 0.0f;
        NVGRenderUtil.drawRoundedRectWH(x, y + fontHeight + 5, 200, 100, fontHeight / 2f, new Color(30, 30, 30, 255));
        drawRainbowCheckBox(mouseX, mouseY);
        drawPickerBox(mouseX, mouseY);
        if (colorSetting.isRainbow()) {
            drawRainbowSetting(mouseX, mouseY);
        } else {
            drawBar(mouseX, mouseY);
        }

        float tempX = x + ubuntu10.getHeight() / 2f;
        float tempY = y + ubuntu10.getHeight() + 5 + ubuntu10.getHeight() / 2f;
        float tempW = tempX + 100 - ((ubuntu10.getHeight() / 2f) * 2) + 1;
        float tempH = tempY + 100 - ((ubuntu10.getHeight() / 2f) * 2);
        if (pickerBoxDrag) {
            circleX = mouseX;
            circleY = mouseY;

            if (mouseX <= tempX) {
                circleX = tempX;
            }
            if (mouseY <= tempY) {
                circleY = tempY;
            }
            if (tempW <= mouseX) {
                circleX = tempW;
            }
            if (tempH <= mouseY) {
                circleY = tempH;
            }

            tempMouseX = circleX - tempX;
            tempMouseY = circleY - tempY;

            defaultColor = getColor((int) (tempMouseX), (int) (tempMouseY));
            colorSetting.setValue(defaultColor);
        } else {
            circleX = tempX + tempMouseX;
            circleY = tempY + tempMouseY;

            if (redBarDrag || greenBarDrag || blueBarDrag || alphaBarDrag) {
                circleX = tempW;
                circleY = tempH;
                tempMouseX = circleX - tempX;
                tempMouseY = circleY - tempY;
            }
        }
    }

    public void drawRainbowCheckBox(int mouseX, int mouseY) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (colorSetting.isRainbow()) {
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

        ubuntu10.drawText("Rainbow", x + fontHeight / 2f + pickerBoxScale + 1 + 5 + ubuntu10.getHeight() + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));

        NVGRenderUtil.drawRectWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, ubuntu10.getHeight(), ubuntu10.getHeight(), new Color(255, 255, 255, 255));

        NVGRenderUtil.drawRectWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 0.5f, y + fontHeight + 5 + fontHeight / 2f + offsetY + 0.5f, ubuntu10.getHeight() - 1, ubuntu10.getHeight() - 1, new Color(30, 30, 30, 255));

        bonIcon10.setSize(fontSize);
        bonIcon10.drawBlurTextCenter(BonIcon.CHECK, x + fontHeight / 2f + pickerBoxScale + 1 + 5 + (ubuntu10.getHeight() / 2f), y + fontHeight + 5 + fontHeight / 2f + offsetY + (ubuntu10.getHeight() / 2f) + 1, new Color(255, 255, 255, 255), 5, ColorUtil.getRainbow(20, 255, 255));
        bonIcon10.setSize(10f);

        offsetY += ubuntu10.getHeight() + 5;
    }

    public void drawPickerBox(int mouseX, int mouseY) {
        if (colorSetting.isRainbow()) {
            maxColor = ColorUtil.getRainbow(colorSetting.getDelay(), colorSetting.getSaturation(), colorSetting.getBrightness());
            defaultColor = ColorUtil.setAlpha(maxColor, colorSetting.getValue().getAlpha());
            colorSetting.setValue(defaultColor);
        }

        NVGRenderUtil.drawLinearGradientRect(x + fontHeight / 2f, y + fontHeight + 5 + fontHeight / 2f, pickerBoxScale + 1, pickerBoxScale, new Color(0, 0, 0, 255), ColorUtil.setAlpha(maxColor, 255));

        NVGRenderUtil.drawCircle(circleX, circleY, 2, new Color(100, 100, 255, 255));
    }

    public void drawBar(int mouseX, int mouseY) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        ubuntu10.drawText("Red : " + colorSetting.getValue().getRed(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawLinearGradientRoundedRect(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(0, 0, 0, 255), new Color(255, 0, 0, 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getValue().getRed()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (redBarDrag) {
            int red;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                red = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                red = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                red = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            maxColor = ColorUtil.setRed(colorSetting.getValue(), red);
            colorSetting.setValue(maxColor);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        ubuntu10.drawText("Green : " + colorSetting.getValue().getGreen(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawLinearGradientRoundedRect(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(0, 0, 0, 255), new Color(0, 255, 0, 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getValue().getGreen()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (greenBarDrag) {
            int green;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                green = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                green = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                green = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            maxColor = ColorUtil.setGreen(colorSetting.getValue(), green);
            colorSetting.setValue(maxColor);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        ubuntu10.drawText("Blue : " + colorSetting.getValue().getBlue(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawLinearGradientRoundedRect(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(0, 0, 0, 255), new Color(0, 0, 255, 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getValue().getBlue()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (blueBarDrag) {
            int blue;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                blue = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                blue = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                blue = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            maxColor = ColorUtil.setBlue(colorSetting.getValue(), blue);
            colorSetting.setValue(maxColor);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        ubuntu10.drawText("Alpha : " + colorSetting.getValue().getAlpha(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawLinearGradientRoundedRect(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(30, 30, 30, 255), ColorUtil.setAlpha(colorSetting.getValue(), 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getValue().getAlpha()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (alphaBarDrag) {
            int alpha;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                alpha = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                alpha = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                alpha = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            maxColor = ColorUtil.setAlpha(colorSetting.getValue(), alpha);
            colorSetting.setValue(maxColor);
        }
    }

    public void drawRainbowSetting(int mouseX, int mouseY) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        ubuntu10.drawText("Saturation : " + colorSetting.getSaturation(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawRoundedRectWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(20, 20, 20, 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getSaturation()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (saturationDrag) {
            int saturation;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                saturation = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                saturation = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                saturation = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            colorSetting.setSaturation(saturation);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        ubuntu10.drawText("Brightness : " + colorSetting.getBrightness(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawRoundedRectWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(20, 20, 20, 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getBrightness()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (brightnessDrag) {
            int brightness;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                brightness = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                brightness = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                brightness = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            colorSetting.setBrightness(brightness);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        ubuntu10.drawText("Delay : " + colorSetting.getDelay(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawRoundedRectWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(20, 20, 20, 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getDelayBarX(colorSetting.getDelay()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (delayBarDrag) {
            int delay;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                delay = getDelayFromDelayBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                delay = getDelayFromDelayBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                delay = getDelayFromDelayBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            colorSetting.setDelay(delay);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        ubuntu10.drawText("Alpha : " + colorSetting.getValue().getAlpha(), x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, new Color(255, 255, 255, 255));
        NVGRenderUtil.drawLinearGradientRoundedRect(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, 2.5f, new Color(30, 30, 30, 255), ColorUtil.setAlpha(colorSetting.getValue(), 255));
        NVGRenderUtil.drawCircle(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + getColorBarX(colorSetting.getValue().getAlpha()), y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + 2.5f + offsetY, 2.5f, new Color(100, 100, 255, 255));

        if (alphaBarDrag) {
            int alpha;
            if (mouseX <= x + fontHeight / 2f + pickerBoxScale + 1 + 5) {
                alpha = getColorFromColorBarX(0);
            } else if (mouseX >= x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90) {
                alpha = getColorFromColorBarX(x + fontHeight / 2f + pickerBoxScale + 1 + 5 + 90);
            } else {
                alpha = getColorFromColorBarX(mouseX - (x + fontHeight / 2f + pickerBoxScale + 1 + 5));
            }

            maxColor = ColorUtil.setAlpha(colorSetting.getValue(), alpha);
            colorSetting.setValue(maxColor);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        if (MouseUtil.isHoveringWH(x, y, ubuntu10.getHeight(), ubuntu10.getHeight(), mouseX, mouseY)) {
            open = !open;
        }

        offsetY = 0.0f;
        if (MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + offsetY, ubuntu10.getHeight(), ubuntu10.getHeight(), mouseX, mouseY)) {
            colorSetting.setRainbow(!colorSetting.isRainbow());
        }
        if (colorSetting.isRainbow()) {
            offsetY += ubuntu10.getHeight() + 5;
            saturationDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);

            offsetY += ubuntu10.getHeight() + 5 + 5;
            brightnessDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);

            offsetY += ubuntu10.getHeight() + 5 + 5;
            delayBarDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);
        } else {
            offsetY += ubuntu10.getHeight() + 5;
            pickerBoxDrag = MouseUtil.isHoveringWH(x + ubuntu10.getHeight() / 2f, y + ubuntu10.getHeight() + 5 + ubuntu10.getHeight() / 2f, 100 - ((ubuntu10.getHeight() / 2f) * 2) + 1, 100 - ((ubuntu10.getHeight() / 2f) * 2), mouseX, mouseY);

            redBarDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);

            offsetY += ubuntu10.getHeight() + 5 + 5;
            greenBarDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);

            offsetY += ubuntu10.getHeight() + 5 + 5;
            blueBarDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);
        }

        offsetY += ubuntu10.getHeight() + 5 + 5;
        alphaBarDrag = MouseUtil.isHoveringWH(x + fontHeight / 2f + pickerBoxScale + 1 + 5, y + fontHeight + 5 + fontHeight / 2f + ubuntu10.getHeight() + offsetY, 90, 5, mouseX, mouseY);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseReleased(int mouseX, int mouseY, int state) {
        pickerBoxDrag = false;
        redBarDrag = false;
        greenBarDrag = false;
        blueBarDrag = false;
        alphaBarDrag = false;
        saturationDrag = false;
        brightnessDrag = false;
        delayBarDrag = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    public Color getColor(int x, int y) {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        int maxRed = maxColor.getRed() / 2;
        int maxGreen = maxColor.getGreen() / 2;
        int maxBlue = maxColor.getBlue() / 2;

        int redX = (int) (maxRed * x / (100 - ((ubuntu10.getHeight() / 2f) * 2)));
        int redY = (int) (maxRed * y / (100 - ((ubuntu10.getHeight() / 2f) * 2)));
        int greenX = (int) (maxGreen * x / (100 - ((ubuntu10.getHeight() / 2f) * 2)));
        int greenY = (int) (maxGreen * y / (100 - ((ubuntu10.getHeight() / 2f) * 2)));
        int blueX = (int) (maxBlue * x / (100 - ((ubuntu10.getHeight() / 2f) * 2)));
        int blueY = (int) (maxBlue * y / (100 - ((ubuntu10.getHeight() / 2f) * 2)));
        int red = (redX + redY);
        int green = (greenX + greenY);
        int blue = (blueX + blueY);

        if (red <= 0) {
            red = 0;
        } else if (red >= 255) {
            red = 255;
        }
        if (green <= 0) {
            green = 0;
        } else if (green >= 255) {
            green = 255;
        }
        if (blue <= 0) {
            blue = 0;
        } else if (blue >= 255) {
            blue = 255;
        }

        return new Color(red, green, blue, colorSetting.getValue().getAlpha());
    }

    public float getColorBarX(int colorValue) {
        return (colorValue * 90) / 255f;
    }

    public float getDelayBarX(int delayValue) {


        return (float) (delayValue * 90) / colorSetting.getMaxDelay();
    }

    public int getDelayFromDelayBarX(float barX) {
        int value = (int) ((colorSetting.getMaxDelay() * barX) / 90);
        value = MathUtil.adjustToClosestMultipleI(value, colorSetting.getStepDelay());
        if (value <= colorSetting.getMinDelay()) {
            value = colorSetting.getMinDelay();
        } else if (value >= colorSetting.getMaxDelay()) {
            value = colorSetting.getMaxDelay();
        }
        return value;
    }

    public int getColorFromColorBarX(float barX) {
        int value = (int) ((255 * barX) / 90);
        if (value <= 0) {
            value = 0;
        } else if (value >= 255) {
            value = 255;
        }
        return value;
    }
}
