package me.hannsi.melyclient.util.system.math.color.theme.parts;

import java.awt.*;

public class Font {
    public Color color;
    public Color blurColor;

    public Font(Color color, Color blurColor) {
        this.color = color;
        this.blurColor = blurColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getBlurColor() {
        return blurColor;
    }

    public void setBlurColor(Color blurColor) {
        this.blurColor = blurColor;
    }
}
