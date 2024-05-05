package me.hannsi.melyclient.gui.clickGui2;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.Panels.altManager.AccountScreen;
import me.hannsi.melyclient.gui.clickGui2.Panels.altManager.AltManagerScreen;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.guiScreen.GuiScreenUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NVGRenderUtil;
import me.hannsi.melyclient.util.system.conversion.BonIcon;
import me.hannsi.melyclient.util.system.math.MouseUtil;
import net.minecraft.client.gui.*;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui2 extends GuiScreen {
    public static ClickGui2 INSTANCE;
    public List<HomeButton> homeButtons;
    public List<MenuBarButton> menuBarButtons;
    public float menuBarWidth;
    public boolean menuBarOpen;
    public float offsetX;
    public Screen nowScreen;

    @Override
    public void initGui() {
        homeButtons = new ArrayList<>();
        homeButtons.add(new HomeButton("Modules", "Displays a screen about Module.", BonIcon.RECT, Screen.Modules, 0, 0));
        homeButtons.add(new HomeButton("AltManager", "Displays a screen about your account.", BonIcon.REPEAT, Screen.AltManager, 0, 0));
        homeButtons.add(new HomeButton("Texture", "Displays screens related to textures.", BonIcon.TV, Screen.Texture, 0, 0));
        homeButtons.add(new HomeButton("Shader", "Displays screens related to shaders.", BonIcon.SCREENSHOT, Screen.Shader, 0, 0));
        homeButtons.add(new HomeButton("Console", "Displays screens related to the console.", BonIcon.TUNE, Screen.Console, 0, 0));
        homeButtons.add(new HomeButton("ClientInfo", "Displays a screen about the client's information.", BonIcon.INFO, Screen.ClientInfo, 0, 0));

        menuBarButtons = new ArrayList<>();
        menuBarButtons.add(new MenuBarButton("SinglePlayer", BonIcon.PERSON, new GuiWorldSelection(this), 0, 0));
        menuBarButtons.add(new MenuBarButton("MultiPlayer", BonIcon.GROUPS, new GuiMultiplayer(this), 0, 0));
        menuBarButtons.add(new MenuBarButton("Setting", BonIcon.SETTINGS, new GuiOptions(this, mc.gameSettings), 0, 0));
        menuBarButtons.add(new MenuBarButton("Language", BonIcon.PUBLIC, new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()), 0, 0));

        menuBarWidth = this.width / 6f;
        menuBarOpen = true;
        offsetX = this.width / 40f;
        nowScreen = Screen.Home;
        INSTANCE = this;

        AltManagerScreen.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        INSTANCE = this;

        GLUtil.nvgPush();

        NVGRenderUtil.drawRectWH(0, 0, this.width, this.height, new Color(4, 4, 4, 255));

        NVGRenderUtil.drawRectWH(0, 0, menuBarWidth, this.height, new Color(31, 31, 31, 255));

        if (MouseUtil.isHoveringWH(0, 0, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, mouseX, mouseY)) {
            NVGRenderUtil.drawRadialGradientRect(0, 0, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            NVGRenderUtil.drawOutLineRectWH(0, 0, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, 1f, new Color(91, 91, 91, 255));
        }
        NVGRenderUtil.drawText(BonIcon.ARROW_BACK, MelyClient.fontManager.bonIcon, 5, 5, 15, new Color(255, 255, 255, 255));

        if (MouseUtil.isHoveringWH(0, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, mouseX, mouseY)) {
            NVGRenderUtil.drawRadialGradientRect(0, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            NVGRenderUtil.drawOutLineRectWH(0, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, 1f, new Color(91, 91, 91, 255));
        }
        NVGRenderUtil.drawText(BonIcon.REORDER, MelyClient.fontManager.bonIcon, 5, 5 + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, 15, new Color(255, 255, 255, 255));

        if (nowScreen == Screen.Home) {
            NVGRenderUtil.drawText(MelyClient.MOD_NAME, MelyClient.fontManager.ubuntu, menuBarWidth + offsetX, 5 + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 5, 15, new Color(255, 255, 255, 255));
            NVGRenderUtil.drawText("v" + MelyClient.MOD_VER, MelyClient.fontManager.ubuntu, menuBarWidth + offsetX, 5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10, 10, new Color(91, 91, 91, 255));

            float homeButtonOffsetX = menuBarWidth + offsetX;
            for (HomeButton homeButton : homeButtons) {
                homeButton.setX(homeButtonOffsetX);
                homeButton.setY(5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10 + offsetX);
                homeButton.draw(mouseX, mouseY, this.width, this.height);
                homeButtonOffsetX += this.width / 9f + this.width / 100f;
            }
        } else if (nowScreen == Screen.AltManager) {
            AltManagerScreen.drawScreen(mouseX, mouseY, this.width, this.height);
        }

        float menuBarOffsetY = (5 + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 5 + this.height / 15f) - MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12);
        for (MenuBarButton menuBarButton : menuBarButtons) {
            menuBarButton.setY(menuBarOffsetY);
            menuBarButton.draw(mouseX, mouseY, this.width, this.height);

            menuBarOffsetY += (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + ((MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) / 2f)));
        }

        if (!MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).isToggle()) {
            close();
        }

        GLUtil.nvgPop();
    }

    public void close() {
        GuiScreenUtil.setFocus();
        mc.displayGuiScreen(null);
        if (MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).isToggle()) {
            MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).toggle();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if ((keyCode == MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).getKeyBind().getValue().getKeyCode() || keyCode == Keyboard.KEY_ESCAPE)) {
            close();
        }

        if (nowScreen == Screen.AltManager) {
            AltManagerScreen.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (MouseUtil.isHoveringWH(0, 0, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, mouseX, mouseY)) {
            if (nowScreen == Screen.Home) {
                close();
            } else if (nowScreen == Screen.AltManager) {
                if (AltManagerScreen.screen == AccountScreen.SelectAccountScreen) {
                    nowScreen = Screen.Home;
                } else if (AltManagerScreen.screen == AccountScreen.LoginModeScreen) {
                    AltManagerScreen.screen = AccountScreen.SelectAccountScreen;
                } else if (AltManagerScreen.screen == AccountScreen.InputEmailAndPasswordScreen) {
                    AltManagerScreen.screen = AccountScreen.LoginModeScreen;
                } else if (AltManagerScreen.screen == AccountScreen.CheckingAccountScreen) {
                    AltManagerScreen.screen = AccountScreen.InputEmailAndPasswordScreen;
                } else if (AltManagerScreen.screen == AccountScreen.AccountInfoScreen) {
                    AltManagerScreen.screen = AccountScreen.SelectAccountScreen;
                }
            } else {
                nowScreen = Screen.Home;
            }
        }

        if (MouseUtil.isHoveringWH(0, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.REORDER, 15) + 10, MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 10, mouseX, mouseY)) {
            menuBarOpen = !menuBarOpen;
            if (menuBarOpen) {
                menuBarWidth = this.width / 6f;
            } else {
                menuBarWidth = MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.PERSON, 15) + 10;
            }
        }

        if (nowScreen == Screen.Home) {
            float homeButtonOffsetX = menuBarWidth + offsetX;
            for (HomeButton homeButton : homeButtons) {
                homeButton.setX(homeButtonOffsetX);
                homeButton.setY(5 + (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) * 2) + 10 + offsetX);
                homeButton.mouseClicked(mouseX, mouseY, mouseButton);
                homeButtonOffsetX += this.width / 9f + this.width / 100f;
            }
        } else if (nowScreen == Screen.AltManager) {
            AltManagerScreen.mouseClicked(mouseX, mouseY, mouseButton);
        }

        float menuBarOffsetY = (5 + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + 5 + this.height / 15f) - MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) / 2f + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12);
        for (MenuBarButton menuBarButton : menuBarButtons) {
            menuBarButton.setY(menuBarOffsetY);
            menuBarButton.mouseClicked(mouseX, mouseY, mouseButton);
            menuBarOffsetY += (MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) + ((MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 15) / 2f)));
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public class HomeButton {
        public String name;
        public String description;
        public String icon;
        public Screen screen;
        public float x;
        public float y;

        public HomeButton(String name, String description, String icon, Screen screen, float x, float y) {
            this.name = name;
            this.description = description;
            this.icon = icon;
            this.screen = screen;
            this.x = x;
            this.y = y;
        }

        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
                nowScreen = screen;
            }
        }

        public void draw(int mouseX, int mouseY, float width, float height) {
            NVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, width / 9f + 2, height / 3f + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

            NVGRenderUtil.drawRectWH(x, y, width / 9f, height / 3f, new Color(0, 0, 0, 255));

            if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
                NVGRenderUtil.drawOutLineRectWH(x, y, width / 9f, height / 3f, 1.0f, new Color(91, 91, 91, 255));

                NVGRenderUtil.drawRadialGradientRect(x, y, width / 9f, height / 3f, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            }

            NVGRenderUtil.drawText(icon, MelyClient.fontManager.bonIcon, x + 5, y + 2.5f, 40, new Color(0, 120, 212, 255));

            NVGRenderUtil.drawText(name, MelyClient.fontManager.ubuntu, x + 5, y + 2.5f + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 40), 12, new Color(255, 255, 255, 255));

            String[] descriptionArray = description.split("");
            String tempDescription = "";
            List<String> nDescription = new ArrayList<>();

            for (String s : descriptionArray) {
                tempDescription = tempDescription + s;

                float descriptionWidth = MelyClient.fontManager.getWidth(MelyClient.fontManager.ubuntu, tempDescription, 10);

                if (width / 9f - 15 < descriptionWidth) {
                    nDescription.add(tempDescription);
                    tempDescription = "";
                }
            }
            nDescription.add(tempDescription);

            float textOffsetY = 2.5f + MelyClient.fontManager.getHeight(MelyClient.fontManager.bonIcon, 40) + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12);
            for (String text : nDescription) {
                NVGRenderUtil.drawText(text, MelyClient.fontManager.ubuntu, x + 5, y + textOffsetY, 10, new Color(91, 91, 91, 255));

                textOffsetY += MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 10);
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Screen getScreen() {
            return screen;
        }

        public void setScreen(Screen screen) {
            this.screen = screen;
        }
    }

    public class MenuBarButton {
        public String name;
        public String icon;
        public GuiScreen guiScreen;
        public float x;
        public float y;

        public MenuBarButton(String name, String icon, GuiScreen guiScreen, float x, float y) {
            this.name = name;
            this.icon = icon;
            this.guiScreen = guiScreen;
            this.x = x;
            this.y = y;
        }

        public void draw(int mouseX, int mouseY, float width, float height) {
            if (MouseUtil.isHoveringWH(x, y, menuBarWidth, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12)), mouseX, mouseY)) {
                NVGRenderUtil.drawRadialGradientRect(x, y, menuBarWidth, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12)), mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(31, 31, 31, 255));

                NVGRenderUtil.drawOutLineRectWH(x, y, menuBarWidth, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12)), 1f, new Color(91, 91, 91, 255));
            }
            NVGRenderUtil.drawText(icon, MelyClient.fontManager.bonIcon, x + 5, y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) / 2f, 12, new Color(255, 255, 255, 255));

            if (menuBarOpen) {
                NVGRenderUtil.drawText(name, MelyClient.fontManager.ubuntu, x + 5 + MelyClient.fontManager.getWidth(MelyClient.fontManager.bonIcon, BonIcon.HOME, 15) + height / 50f, y + MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) / 2f, 12, new Color(255, 255, 255, 255));
            }
        }

        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            if (MouseUtil.isHoveringWH(x, y, menuBarWidth, MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12) + (MelyClient.fontManager.getHeight(MelyClient.fontManager.ubuntu, 12)), mouseX, mouseY)) {
                mc.displayGuiScreen(guiScreen);
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public GuiScreen getGuiScreen() {
            return guiScreen;
        }

        public void setGuiScreen(GuiScreen guiScreen) {
            this.guiScreen = guiScreen;
        }
    }
}
