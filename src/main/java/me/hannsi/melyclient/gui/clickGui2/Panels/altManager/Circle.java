package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.math.animation.Easing;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;

import java.awt.*;

public class Circle {
    public float x;
    public float y;
    public float radius;
    public Color color;
    public int step;
    public EasingUtil easingUtil;

    public Circle(float x, float y, float radius, Color color, int step) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.step = step;

        easingUtil = new EasingUtil(Easing.easeInOutExpo);
        easingUtil.setReverse(false);
    }

    public void drawCircle() {
        NVGRenderUtil.drawCircle(getX(), getY(), getRadius(), getColor());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public EasingUtil getEasingUtil() {
        return easingUtil;
    }

    public void setEasingUtil(EasingUtil easingUtil) {
        this.easingUtil = easingUtil;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}