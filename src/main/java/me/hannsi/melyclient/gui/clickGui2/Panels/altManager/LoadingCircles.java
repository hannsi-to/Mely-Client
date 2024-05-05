package me.hannsi.melyclient.gui.clickGui2.Panels.altManager;

import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoadingCircles {
    public List<Circle> circles;
    public float x;
    public float y;
    public float radius;
    public Color color;

    public LoadingCircles(float x, float y, float radius, Color color, int count) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;

        circles = new ArrayList<>();
        int step = 0;
        for (int i = 0; i <= count; i++) {
            circles.add(new Circle(x, y, radius, color, step));
            step += 15;
        }
    }

    public void draw(long millis, float width) {
        for (Circle circle : circles) {
            if (circle.getStep() <= 0) {
                float value = circle.easingUtil.get(millis);

                circle.setX(this.x + (width * value));
                float radiusAndColorValue;
                if (value >= 0.5f) {
                    radiusAndColorValue = 1.0f - value;
                } else {
                    radiusAndColorValue = value;
                }
                circle.setRadius(this.radius * (radiusAndColorValue * 1.5f) + (this.radius / 3f));
                circle.setColor(ColorUtil.setAlpha(circle.getColor(), (int) (this.color.getAlpha() * (radiusAndColorValue * 5))));
                circle.drawCircle();

                if (value >= 1) {
                    circle.easingUtil.reset();
                    circle.setX(this.x);
                    circle.setRadius(this.radius);
                    circle.setColor(this.color);
                }
            } else {
                circle.setStep(circle.getStep() - 1);
            }
        }
    }
}