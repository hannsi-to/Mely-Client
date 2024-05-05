package me.hannsi.melyclient.gui.clickGui;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.math.MouseUtil;
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
        boolean hover = MouseUtil.isHoveringWH(x, y, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, category.getIcon() + " ", 10) + MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, category.getDisplay(), 10), MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), mouseX, mouseY);
        float lineW;

        if (hover) {
            easingUtil1.setReverse(true);
            easingUtil2.reset();
            lineW = easingUtil1.get(500) * (MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, category.getDisplay(), 10) / 2);

            NVGRenderUtil.drawText(category.getIcon() + " ", MelyClient.fontManager.bonIcon, x, y, 10, new Color(255, 255, 255, 255), 5, ColorUtil.getRainbow(20, 255, 255));
        } else {
            easingUtil2.setReverse(false);
            easingUtil1.reset();
            lineW = easingUtil2.get(500) * (MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, category.getDisplay(), 10) / 2);

            NVGRenderUtil.drawText(category.getIcon() + " ", MelyClient.fontManager.bonIcon, x, y, 10, new Color(255, 255, 255, 255));
        }
        NVGRenderUtil.drawText(category.getDisplay(), MelyClient.fontManager.ubuntu, x + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, category.getIcon() + " ", 10), y, 10, new Color(255, 255, 255, 255));

        if (category == ClickGui.INSTANCE.selectCategory) {
            lineW = (MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, category.getDisplay(), 10) / 2) * 0;
        }

        NVGRenderUtil.drawLineWH(x + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, category.getIcon() + " ", 10) + lineW, y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10), MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, category.getDisplay(), 10) - (lineW * 2), 0, 1, new Color(255, 255, 255, 255));

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
