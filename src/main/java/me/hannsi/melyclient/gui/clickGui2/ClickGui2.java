package me.hannsi.melyclient.gui.clickGui2;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.Panels.altManager.AccountScreen;
import me.hannsi.melyclient.gui.clickGui2.Panels.altManager.AltManagerScreen;
import me.hannsi.melyclient.gui.clickGui2.Panels.clientInfo.ClientInfo;
import me.hannsi.melyclient.gui.clickGui2.Panels.texture.Texture;
import me.hannsi.melyclient.util.render.GLUtil;
import me.hannsi.melyclient.util.render.guiScreen.GuiScreenUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
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

    public static void openGLStart() {
        GLUtil.push();
    }

    public static void openGLEnd() {
        GLUtil.pop();
    }

    public static void nanoVGStart() {
        NanoVGUtil.nvgPush();
    }

    public static void nanoVGEnd() {
        NanoVGUtil.nvgPop();
    }

    @Override
    public void initGui() {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);

        homeButtons = new ArrayList<>();
        homeButtons.add(new HomeButton("Modules", "Displays a screen about Module.", BonIcon.RECT, Screen.Modules, 0, 0));
        homeButtons.add(new HomeButton("AltManager", "Displays a screen about your account.      This is changed by reconnecting to the world.", BonIcon.REPEAT, Screen.AltManager, 0, 0));
        homeButtons.add(new HomeButton("Texture", "Displays screens related to textures.", BonIcon.TV, Screen.Texture, 0, 0));
        homeButtons.add(new HomeButton("Console", "Displays screens related to the console.", BonIcon.TUNE, Screen.Console, 0, 0));
        homeButtons.add(new HomeButton("ClientInfo", "Displays a screen about the client's information.", BonIcon.INFO, Screen.ClientInfo, 0, 0));

        menuBarButtons = new ArrayList<>();
        menuBarButtons.add(new MenuBarButton("SinglePlayer", BonIcon.PERSON, new GuiWorldSelection(this), 0, 0));
        menuBarButtons.add(new MenuBarButton("MultiPlayer", BonIcon.GROUPS, new GuiMultiplayer(this), 0, 0));
        menuBarButtons.add(new MenuBarButton("Setting", BonIcon.SETTINGS, new GuiOptions(this, mc.gameSettings), 0, 0));
        menuBarButtons.add(new MenuBarButton("Language", BonIcon.PUBLIC, new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()), 0, 0));

        menuBarWidth = bonIcon15.getWidth(BonIcon.PERSON) + 10;
        menuBarOpen = false;
        offsetX = this.width / 40f;
        nowScreen = Screen.Home;
        INSTANCE = this;

        AltManagerScreen.initGui();
        ClientInfo.initGui();
        Texture.initGui(this.width, this.height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
//
        INSTANCE = this;
//
        nanoVGStart();
//
        NanoVGRenderUtil.drawRectWH(0, 0, this.width, this.height, new Color(4, 4, 4, 255));
//
        NanoVGRenderUtil.drawRectWH(0, 0, menuBarWidth, this.height, new Color(31, 31, 31, 255));
//
        if (MouseUtil.isHoveringWH(0, 0, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, mouseX, mouseY)) {
            NanoVGRenderUtil.drawRadialGradientRect(0, 0, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            NanoVGRenderUtil.drawOutLineRectWH(0, 0, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, 1f, new Color(91, 91, 91, 255));
        }
        bonIcon15.drawText(BonIcon.ARROW_BACK, 5, 5, new Color(255, 255, 255, 255));
//
        if (MouseUtil.isHoveringWH(0, bonIcon15.getHeight() + 10, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, mouseX, mouseY)) {
            NanoVGRenderUtil.drawRadialGradientRect(0, bonIcon15.getHeight() + 10, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            NanoVGRenderUtil.drawOutLineRectWH(0, bonIcon15.getHeight() + 10, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, 1f, new Color(91, 91, 91, 255));
        }
        bonIcon15.drawText(BonIcon.REORDER, 5, 5 + bonIcon15.getHeight() + 10, new Color(255, 255, 255, 255));
//
        if (nowScreen == Screen.Home) {
            ubuntu15.drawText(MelyClient.MOD_NAME, menuBarWidth + offsetX, 5 + bonIcon15.getHeight() + 5, new Color(255, 255, 255, 255));
            ubuntu10.drawText("v" + MelyClient.MOD_VER, menuBarWidth + offsetX, 5 + (bonIcon15.getHeight() * 2) + 10, new Color(91, 91, 91, 255));
//
            float homeButtonOffsetX = menuBarWidth + offsetX;
            for (HomeButton homeButton : homeButtons) {
                homeButton.setX(homeButtonOffsetX);
                homeButton.setY(5 + (bonIcon15.getHeight() * 2) + 10 + offsetX);
                homeButton.draw(mouseX, mouseY, this.width, this.height);
                homeButtonOffsetX += this.width / 9f + this.width / 100f;
            }
        } else if (nowScreen == Screen.AltManager) {
            AltManagerScreen.drawScreen(mouseX, mouseY, this.width, this.height);
        } else if (nowScreen == Screen.ClientInfo) {
            ClientInfo.drawScreen(mouseX, mouseY, this.width, this.height);
        } else if (nowScreen == Screen.Texture) {
            Texture.drawScreen(mouseX, mouseY, this.width, this.height, partialTicks);
        }
//
        float menuBarOffsetY = (5 + bonIcon15.getHeight() + 5 + this.height / 15f) - ubuntu12.getHeight() / 2f + ubuntu12.getHeight();
        for (MenuBarButton menuBarButton : menuBarButtons) {
            menuBarButton.setY(menuBarOffsetY);
            menuBarButton.draw(mouseX, mouseY, this.width, this.height);
//
            menuBarOffsetY += (bonIcon15.getHeight() + ((bonIcon15.getHeight() / 2f)));
        }
//
        if (!MelyClient.moduleManager.getModuleByClass(me.hannsi.melyclient.module.modules.client.ClickGui.class).isToggle()) {
            close();
        }
//
        nanoVGEnd();
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
        if ((keyCode == Keyboard.KEY_ESCAPE)) {
            close();
        }

        if (nowScreen == Screen.AltManager) {
            AltManagerScreen.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);

        if (MouseUtil.isHoveringWH(0, 0, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, mouseX, mouseY)) {
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

        if (MouseUtil.isHoveringWH(0, bonIcon15.getHeight() + 10, bonIcon15.getWidth(BonIcon.REORDER) + 10, bonIcon15.getHeight() + 10, mouseX, mouseY)) {
            menuBarOpen = !menuBarOpen;
            if (menuBarOpen) {
                menuBarWidth = this.width / 6f;
            } else {
                menuBarWidth = bonIcon15.getWidth(BonIcon.PERSON) + 10;
            }
        }

        if (nowScreen == Screen.Home) {
            float homeButtonOffsetX = menuBarWidth + offsetX;
            for (HomeButton homeButton : homeButtons) {
                homeButton.setX(homeButtonOffsetX);
                homeButton.setY(5 + (bonIcon15.getHeight() * 2) + 10 + offsetX);
                homeButton.mouseClicked(mouseX, mouseY, mouseButton);
                homeButtonOffsetX += this.width / 9f + this.width / 100f;
            }
        } else if (nowScreen == Screen.AltManager) {
            AltManagerScreen.mouseClicked(mouseX, mouseY, mouseButton);
        } else if (nowScreen == Screen.ClientInfo) {
            ClientInfo.mouseClicked(mouseX, mouseY, mouseButton);
        } else if (nowScreen == Screen.Texture) {
            Texture.mouseClicked(mouseX, mouseY, mouseButton);
        }

        float menuBarOffsetY = (5 + bonIcon15.getHeight() + 5 + this.height / 15f) - ubuntu12.getHeight() / 2f + ubuntu12.getHeight();
        for (MenuBarButton menuBarButton : menuBarButtons) {
            menuBarButton.setY(menuBarOffsetY);
            menuBarButton.mouseClicked(mouseX, mouseY, mouseButton);
            menuBarOffsetY += (bonIcon15.getHeight() + ((bonIcon15.getHeight() / 2f)));
        }
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
            FontUtil bonIcon40 = new FontUtil(MelyClient.fontManager.bonIcon, 40);
            FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
            FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);

            NanoVGRenderUtil.drawRadialGradientRect(x - 1, y - 1, width / 9f + 2, height / 3f + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));

            NanoVGRenderUtil.drawRectWH(x, y, width / 9f, height / 3f, new Color(0, 0, 0, 255));

            if (MouseUtil.isHoveringWH(x, y, width / 9f, height / 3f, mouseX, mouseY)) {
                NanoVGRenderUtil.drawOutLineRectWH(x, y, width / 9f, height / 3f, 1.0f, new Color(91, 91, 91, 255));

                NanoVGRenderUtil.drawRadialGradientRect(x, y, width / 9f, height / 3f, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
            }

            bonIcon40.drawText(icon, x + 5, y + 2.5f, new Color(0, 120, 212, 255));

            ubuntu12.drawText(name, x + 5, y + 2.5f + bonIcon40.getHeight(), new Color(255, 255, 255, 255));

            String[] descriptionArray = description.split("");
            String tempDescription = "";
            List<String> nDescription = new ArrayList<>();

            for (String s : descriptionArray) {
                tempDescription = tempDescription + s;

                float descriptionWidth = ubuntu10.getWidth(tempDescription);

                if (width / 9f - 15 < descriptionWidth) {
                    nDescription.add(tempDescription);
                    tempDescription = "";
                }
            }
            nDescription.add(tempDescription);

            float textOffsetY = 2.5f + bonIcon40.getHeight() + ubuntu12.getHeight();
            for (String text : nDescription) {
                ubuntu10.drawText(text, x + 5, y + textOffsetY, new Color(91, 91, 91, 255));

                textOffsetY += ubuntu10.getHeight();
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
            FontUtil bonIcon12 = new FontUtil(MelyClient.fontManager.bonIcon, 12);
            FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
            FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);

            if (MouseUtil.isHoveringWH(x, y, menuBarWidth, ubuntu12.getHeight() + (ubuntu12.getHeight()), mouseX, mouseY)) {
                NanoVGRenderUtil.drawRadialGradientRect(x, y, menuBarWidth, ubuntu12.getHeight() + (ubuntu12.getHeight()), mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(31, 31, 31, 255));

                NanoVGRenderUtil.drawOutLineRectWH(x, y, menuBarWidth, ubuntu12.getHeight() + (ubuntu12.getHeight()), 1f, new Color(91, 91, 91, 255));
            }
            bonIcon12.drawText(icon, x + 5, y + ubuntu12.getHeight() / 2f, new Color(255, 255, 255, 255));

            if (menuBarOpen) {
                ubuntu12.drawText(name, x + 5 + bonIcon15.getWidth(BonIcon.HOME) + height / 50f, y + ubuntu12.getHeight() / 2f, new Color(255, 255, 255, 255));
            }
        }

        public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
            FontUtil ubuntu12 = new FontUtil(MelyClient.fontManager.ubuntu, 12);

            if (MouseUtil.isHoveringWH(x, y, menuBarWidth, ubuntu12.getHeight() + (ubuntu12.getHeight()), mouseX, mouseY)) {
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
