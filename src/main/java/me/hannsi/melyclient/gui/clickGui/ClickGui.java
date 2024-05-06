package me.hannsi.melyclient.gui.clickGui;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui.setting.system.SettingPanel;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import me.hannsi.melyclient.util.system.math.animation.Easing;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;

public class ClickGui extends GuiScreen {
    public static ClickGui INSTANCE;
    public Category selectCategory;
    public Category oldSelectCategory;
    public String description;
    public List<ModulePanel> modulePanels;
    public EasingUtil easingUtil;
    public EasingUtil easingUtil2;
    public float fontOffSetX1;
    public TimerUtil timerUtil;
    public TimerUtil timerUtil2;
    public float value;
    public float scrollY;
    public List<CategoryPanel> categoryPanels;
    public SettingPanel settingPanel;
    public boolean loaded;
    public int scrollCount;
    private float offSetY1;

    public static ClickGui getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(ClickGui INSTANCE) {
        ClickGui.INSTANCE = INSTANCE;
    }

    @Override
    public void initGui() {
        loaded = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil bonIcon10 = new FontUtil(MelyClient.fontManager.bonIcon, 10);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);

        GLUtil.nvgPush();

        if (!loaded) {
            load();
        }

        GLUtil.nvgTranslate(this.width / 2f, this.height / 2f);
        GLUtil.nvgScale(easingUtil2.get(300), easingUtil2.get(300));
        GLUtil.nvgTranslate(-this.width / 2f, -this.height / 2f);

        ubuntu15.drawBlurTextCenter(MelyClient.MOD_NAME + " v" + MelyClient.MOD_VER, this.width / 2f, (this.height / 4f) - ubuntu15.getHeight(), new Color(255, 255, 255, 255), 5, ColorUtil.getRainbow(20, 255, 255));

        NVGRenderUtil.drawRoundedRectWH(this.width / 4f, this.height / 4f, 100, (this.height / 4f) * 2, 5, true, false, false, true, new Color(25, 25, 25, 200));

        NVGRenderUtil.drawRectWH(this.width / 4f + 100, this.height / 4f + 25, (this.width / 4f) * 2 - 100, (this.height / 4f) * 2 - 50, new Color(25, 25, 25, 255));

        NVGRenderUtil.drawRoundedRectWH(this.width / 4f + 100, this.height / 4f, (this.width / 4f) * 2 - 100, 25, 5, false, true, false, false, new Color(20, 20, 20, 255));

        NVGRenderUtil.drawRoundedRectWH(this.width / 4f + 100, (this.height / 4f) * 3 - 25, (this.width / 4f) * 2 - 100, 25, 5, false, false, true, false, new Color(20, 20, 20, 255));

        NVGRenderUtil.drawLineWH(this.width / 4f, this.height / 4f + offSetY1, 100, 0, 1, new Color(100, 100, 100, 100));
        NVGRenderUtil.drawLineWH(this.width / 4f + 100, this.height / 4f, 0, (this.height / 4f) * 2, 1, new Color(100, 100, 100, 100));
        NVGRenderUtil.drawLineWH(this.width / 4f + 100, this.height / 4f + 25, (this.width / 4f) * 2 - 100, 0, 1, new Color(100, 100, 100, 100));
        NVGRenderUtil.drawLineWH(this.width / 4f + 100, (this.height / 4f) * 3 - 25, (this.width / 4f) * 2 - 100, 0, 1, new Color(100, 100, 100, 100));

        for (CategoryPanel categoryPanel : categoryPanels) {
            categoryPanel.drawScreen(mouseX, mouseY, partialTicks);
        }

        easingUtil.setReverse(true);

        if (oldSelectCategory == null) {
            oldSelectCategory = selectCategory;
        }

        if (oldSelectCategory != selectCategory) {
            easingUtil.reset();
        }

        value = easingUtil.get(1000) * ((this.width / 4f));

        scrollCount = Mouse.getDWheel();

        float offSetY3 = 0;
        if (MouseUtil.isHoveringWH(ClickGui.this.width / 4f + 105, ClickGui.this.height / 4f + 30, ((ClickGui.this.width / 4f) - 100), (ClickGui.this.height / 4f) * 2 - 50, mouseX, mouseY)) {
            if (scrollCount > 0) {
                if ((scrollY >= 10)) {
                    for (ModulePanel modulePanel : modulePanels) {
                        if (modulePanel.category == selectCategory) {
                            modulePanel.setY(modulePanel.getY() + 10);
                        }
                    }
                    scrollY -= 10;
                }

            } else if (scrollCount < 0) {
                for (Module module : MelyClient.moduleManager.modules) {
                    if (module.getCategory() == selectCategory) {
                        offSetY3 += ubuntu10.getHeight() + 10;
                    }
                }

                if ((scrollY <= offSetY3 - 200)) {
                    for (ModulePanel modulePanel : modulePanels) {
                        if (modulePanel.category == selectCategory) {
                            modulePanel.setY(modulePanel.getY() - 10);
                        }
                    }
                    scrollY += 10;
                }
            }
        }

        NVGRenderUtil.drawLineWH(ClickGui.this.width / 4f + 105 + ((ClickGui.this.width / 4f) - 100) + 5 + bonIcon10.getWidth("u") + 5, ClickGui.this.height / 4f + 26, 0, (ClickGui.this.height / 4f) * 2 - 51, 1, new Color(100, 100, 100, 100));

        if (easingUtil2.get(300) == 1) {
            for (ModulePanel modulePanel : modulePanels) {
                if (modulePanel.category == selectCategory) {
                    modulePanel.drawScreen2(mouseX, mouseY, partialTicks);
                }
            }
        }

        NanoVG.nvgScissor(nvg, ClickGui.this.width / 4f + 100, ClickGui.this.height / 4f + 25, (ClickGui.this.width / 4f) * 2 - 100, (ClickGui.this.height / 4f) * 2 - 50);
        for (ModulePanel modulePanel : modulePanels) {
            if (modulePanel.category == selectCategory) {
                GLUtil.nvgTranslate(-value, 0);
                modulePanel.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
        GLUtil.nvgTranslate(value, 0);
        NanoVG.nvgResetScissor(nvg);

        NanoVG.nvgScissor(nvg, ClickGui.this.width / 4f + 105 + ((ClickGui.this.width / 4f) - 100) + 5 + bonIcon10.getWidth("u") + 5 + (ClickGui.this.value * (MelyClient.moduleManager.getModulesCountByCategory(selectCategory) - 1)), ClickGui.this.height / 4f + 26, 215, 201);
        if (settingPanel == null) {
            ubuntu15.drawText("Setting Panel", ClickGui.this.width / 4f + 110 + ((ClickGui.this.width / 4f) - 100) + 5 + bonIcon10.getWidth("u") + 5, ClickGui.this.height / 4f + 31, new Color(255, 255, 255, 255));
        } else {
            settingPanel.drawScreen(mouseX, mouseY, partialTicks);
        }
        NanoVG.nvgResetScissor(nvg);

        NanoVG.nvgScissor(nvg, ClickGui.this.width / 4f + 100 + (ClickGui.this.value * (MelyClient.moduleManager.getModulesCountByCategory(selectCategory) - 1)), (ClickGui.this.height / 4f) * 3 - 25, (ClickGui.this.width / 4f) * 2 - 100, 25);
        ubuntu10.drawText(description, ClickGui.this.width / 4f + 105 - fontOffSetX1, (ClickGui.this.height / 4f) * 3 - (25 / 2f), new Color(255, 255, 255, 255), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVG.nvgResetScissor(nvg);

        if (!description.isEmpty()) {
            if (timerUtil.passedMs(1000)) {
                if (ubuntu10.getWidth(description) > (ClickGui.this.width / 4f) * 2 - 100 && ubuntu10.getWidth(description) - (ClickGui.this.width / 4f) * 2 + 110 > fontOffSetX1) {
                    fontOffSetX1 += 0.5f;
                    timerUtil2.reset();
                } else {
                    if (timerUtil2.passedMs(1000)) {
                        fontOffSetX1 = 0;
                        timerUtil.reset();
                        timerUtil2.reset();
                    }
                }
            }
        } else {
            fontOffSetX1 = 0;
            timerUtil.reset();
            timerUtil2.reset();
        }

        GLUtil.nvgTranslate(this.width / 2f, this.height / 2f);
        GLUtil.nvgScale(-easingUtil2.get(300), -easingUtil2.get(300));
        GLUtil.nvgPop();

        if (!MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).isToggle() && !easingUtil2.isReverse()) {
            setFocus();
            easingUtil2.setReverse(true);
            easingUtil2.reset();
        }

        if (easingUtil2.isReverse() && easingUtil2.get(300) <= 0) {
            reset();
        }

        loaded = true;
        description = "";

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        PointerInfo pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();
        int displayMouseX = (int) point.getX();
        int displayMouseY = (int) point.getY();
        MelyClient.logger.info(mouseX + " : " + mouseY + "     " + displayMouseX + " : " + displayMouseY + "     " + Mouse.getX() + " : " + Mouse.getY());

        for (ModulePanel modulePanel : modulePanels) {
            if (modulePanel.category == selectCategory) {
                modulePanel.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

        if (settingPanel != null) {
            settingPanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (settingPanel != null) {
            settingPanel.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if ((keyCode == MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).getKeyBind().getValue().getKeyCode() || keyCode == Keyboard.KEY_ESCAPE) && !easingUtil2.isReverse()) {
            setFocus();
            easingUtil2.setReverse(true);
            easingUtil2.reset();
        }

        if (settingPanel != null) {
            settingPanel.keyTyped(typedChar, keyCode);
        }
    }

    public void load() {
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);

        selectCategory = Category.COMBAT;
        oldSelectCategory = null;
        description = "";
        easingUtil = new EasingUtil(Easing.easeOutExpo);
        easingUtil2 = new EasingUtil(Easing.easeInOutExpo);
        fontOffSetX1 = 0;
        timerUtil = new TimerUtil();
        timerUtil2 = new TimerUtil();
        value = 0;
        scrollY = 0;
        categoryPanels = new ArrayList<>();
        modulePanels = new ArrayList<>();
        settingPanel = null;
        offSetY1 = 0;
        scrollCount = 0;
        INSTANCE = this;

        offSetY1 = ubuntu10.getHeight();

        for (Category category : Category.values()) {
            categoryPanels.add(new CategoryPanel(category, this.width / 4f + ubuntu10.getHeight(), this.height / 4f + offSetY1, ColorUtil.getRainbow(20, 255, 255)));
            offSetY1 += ubuntu10.getHeight() * 2;
        }

        for (Category category : Category.values()) {
            float offSetY2 = 0;

            for (Module module : MelyClient.moduleManager.modules) {
                if (module.getCategory() == category) {
                    modulePanels.add(new ModulePanel(width / 4f + 105, height / 4f + 30 + offSetY2, module.getCategory(), module, ColorUtil.getRainbow(20, 255, 255)));
                    offSetY2 += ubuntu10.getHeight() + 10;
                }
            }
        }

        easingUtil2.reset();
    }

    public void reset() {
        INSTANCE = null;
        selectCategory = Category.COMBAT;
        oldSelectCategory = null;
        description = "";
        easingUtil = new EasingUtil(Easing.easeOutExpo);
        easingUtil2 = new EasingUtil(Easing.easeInOutExpo);
        fontOffSetX1 = 0;
        timerUtil = new TimerUtil();
        timerUtil2 = new TimerUtil();
        value = 0;
        scrollY = 0;
        categoryPanels = new ArrayList<>();
        modulePanels = new ArrayList<>();
        settingPanel = null;
        offSetY1 = 0;
        scrollCount = 0;

        mc.displayGuiScreen(null);
        if (MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).isToggle()) {
            MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).toggle();
        }
    }

    public void setFocus() {
        if (Display.isActive()) {
            if (!mc.inGameHasFocus) {
                if (!Minecraft.IS_RUNNING_ON_MAC) {
                    KeyBinding.updateKeyBindState();
                }

                mc.inGameHasFocus = true;
                mc.mouseHelper.grabMouseCursor();
                try {
                    Field field = getField(Minecraft.class, "leftClickCounter", "field_71429_W");
                    field.setAccessible(true);
                    field.set(mc, 10000);
                } catch (IllegalAccessException e) {
                    new DebugLog(e, DebugLevel.ERROR);
                }
            }
        }
    }

    public Field getField(Class<?> targetClass, String... targetFields) {
        for (String targetField : targetFields) {
            try {
                return targetClass.getDeclaredField(targetField);
            } catch (NoSuchFieldException e) {
                new DebugLog(e, DebugLevel.ERROR);
            }
        }
        return null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public Category getSelectCategory() {
        return selectCategory;
    }

    public void setSelectCategory(Category selectCategory) {
        this.selectCategory = selectCategory;
    }

    public Category getOldSelectCategory() {
        return oldSelectCategory;
    }

    public void setOldSelectCategory(Category oldSelectCategory) {
        this.oldSelectCategory = oldSelectCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ModulePanel> getModulePanels() {
        return modulePanels;
    }

    public void setModulePanels(List<ModulePanel> modulePanels) {
        this.modulePanels = modulePanels;
    }

    public EasingUtil getEasingUtil() {
        return easingUtil;
    }

    public void setEasingUtil(EasingUtil easingUtil) {
        this.easingUtil = easingUtil;
    }

    public EasingUtil getEasingUtil2() {
        return easingUtil2;
    }

    public void setEasingUtil2(EasingUtil easingUtil2) {
        this.easingUtil2 = easingUtil2;
    }

    public float getFontOffSetX1() {
        return fontOffSetX1;
    }

    public void setFontOffSetX1(float fontOffSetX1) {
        this.fontOffSetX1 = fontOffSetX1;
    }

    public TimerUtil getTimerUtil() {
        return timerUtil;
    }

    public void setTimerUtil(TimerUtil timerUtil) {
        this.timerUtil = timerUtil;
    }

    public TimerUtil getTimerUtil2() {
        return timerUtil2;
    }

    public void setTimerUtil2(TimerUtil timerUtil2) {
        this.timerUtil2 = timerUtil2;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getScrollY() {
        return scrollY;
    }

    public void setScrollY(float scrollY) {
        this.scrollY = scrollY;
    }

    public List<CategoryPanel> getCategoryPanels() {
        return categoryPanels;
    }

    public void setCategoryPanels(List<CategoryPanel> categoryPanels) {
        this.categoryPanels = categoryPanels;
    }

    public float getOffSetY1() {
        return offSetY1;
    }

    public void setOffSetY1(float offSetY1) {
        this.offSetY1 = offSetY1;
    }

    public SettingPanel getSettingPanel() {
        return settingPanel;
    }

    public void setSettingPanel(SettingPanel settingPanel) {
        this.settingPanel = settingPanel;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
