package me.hannsi.melyclient.gui.clickGui;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.MouseUtil;
import me.hannsi.melyclient.util.system.math.animation.Easing;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class CategoryPanel {
    public Category category;
    public float x;
    public float y;
    public Color color;
    public EasingUtil easingUtil1;
    public EasingUtil easingUtil2;

    public CategoryPanel(Category category, float x, float y, Color color) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.color = color;
        this.easingUtil1 = new EasingUtil(Easing.easeInOutExpo);
        this.easingUtil2 = new EasingUtil(Easing.easeInOutExpo);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        boolean hover = MouseUtil.isHoveringWH(x, y, bonIcon10.getWidth(category.getIcon() + " ") + ubuntu10.getWidth(category.getDisplay()), ubuntu10.getHeight(), mouseX, mouseY);
        float lineW;

        if (hover) {
            easingUtil1.setReverse(true);
            easingUtil2.reset();
            lineW = easingUtil1.get(500) * (ubuntu10.getWidth(category.getDisplay()) / 2);

            bonIcon10.drawBlurText(category.getIcon() + " ", x, y, new Color(255, 255, 255, 255), 5, ColorUtil.getRainbow(20, 255, 255));
        } else {
            easingUtil2.setReverse(false);
            easingUtil1.reset();
            lineW = easingUtil2.get(500) * (ubuntu10.getWidth(category.getDisplay()) / 2);

            bonIcon10.drawText(category.getIcon() + " ", x, y, new Color(255, 255, 255, 255));
        }
        ubuntu10.drawText(category.getDisplay(), x + bonIcon10.getWidth(category.getIcon() + " "), y, new Color(255, 255, 255, 255));

        if (category == ClickGui.INSTANCE.selectCategory) {
            lineW = (ubuntu10.getWidth(category.getDisplay()) / 2) * 0;
        }

        NanoVGRenderUtil.drawLineWH(x + bonIcon10.getWidth(category.getIcon() + " ") + lineW, y + ubuntu10.getHeight(), ubuntu10.getWidth(category.getDisplay()) - (lineW * 2), 0, 1, new Color(255, 255, 255, 255));

        if (hover) {
            if (Mouse.isButtonDown(0)) {
                ClickGui.INSTANCE.oldSelectCategory = ClickGui.INSTANCE.selectCategory;
                ClickGui.INSTANCE.selectCategory = category;
            }
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
