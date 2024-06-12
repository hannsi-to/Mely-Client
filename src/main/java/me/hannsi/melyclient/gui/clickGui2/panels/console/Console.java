package me.hannsi.melyclient.gui.clickGui2.panels.console;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.gui.clickGui2.ClickGui2;
import me.hannsi.melyclient.gui.clickGui2.Screen;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.ClipboardUtil;
import me.hannsi.melyclient.util.system.ListUtil;
import me.hannsi.melyclient.util.system.MouseUtil;
import me.hannsi.melyclient.util.system.StringUtil;
import me.hannsi.melyclient.util.system.chat.ChatData;
import me.hannsi.melyclient.util.system.chat.ChatUtil;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class Console implements InterfaceMinecraft {
    public static ClickGui2.HomeButton clientConsoleInfo;
    public static float displayWidth;
    public static float displayHeight;
    public static String typedText;
    public static boolean typed;
    public static float textBoxX;
    public static float textBoxY;
    public static float textBoxWidth;
    public static float textBoxHeight;
    public static boolean clientSide;
    public static boolean packet;
    public static float scrollY;

    public static void initGui(float inDisplayWidth, float inDisplayHeight) {
        update(inDisplayWidth, inDisplayHeight);

        clientConsoleInfo = null;

        typedText = "";
        typed = false;
        clientSide = false;
        packet = false;

        scrollY = 0.0f;
    }

    public static void drawScreen(int mouseX, int mouseY, float inDisplayWidth, float inDisplayHeight, float particleTicks) {
        FontUtil bonIcon15 = new FontUtil(MelyClient.fontManager.bonIcon, 15);
        FontUtil ubuntu10 = new FontUtil(MelyClient.fontManager.ubuntu, 10);
        FontUtil ubuntu15 = new FontUtil(MelyClient.fontManager.ubuntu, 15);
        FontUtil notoSansJPRegular10 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 10);

        for (ClickGui2.HomeButton homeButton : ClickGui2.INSTANCE.homeButtons) {
            if (homeButton.getScreen() == Screen.Console) {
                clientConsoleInfo = homeButton;
                break;
            }
        }

        if (clientConsoleInfo == null) {
            new DebugLog("Can not load console screen.", DebugLevel.WARNING);
            return;
        }

        ubuntu15.drawText(clientConsoleInfo.getName(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + bonIcon15.getHeight() + 5, new Color(255, 255, 255, 255));
        ubuntu10.drawText(clientConsoleInfo.getDescription(), ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 5 + (bonIcon15.getHeight() * 2) + 10, new Color(91, 91, 91, 255));

        update(inDisplayWidth, inDisplayHeight);

        int scrollCount = Mouse.getDWheel();
        if (scrollCount < 0) {
            if (scrollY > 0) {
                scrollY -= 10;
            }
        }

        List<ChatData> chatDataList = ChatUtil.getMessageLog(false);
        ListUtil.reverse(chatDataList);

        float offsetY = -scrollY;
        float maxOffsetY = 0.0f;

        NanoVG.nvgScissor(nvg, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, 3 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2, displayWidth, displayHeight);
        for (ChatData chatData : chatDataList) {
            String[] descriptionArray = StringUtil.split(chatData.getText(), "");
            String tempDescription = "";
            List<String> nDescription = new ArrayList<>();

            for (String s : descriptionArray) {
                tempDescription = tempDescription + s;

                float descriptionWidth = notoSansJPRegular10.getWidth(tempDescription);

                if (displayWidth - (ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX) < descriptionWidth) {
                    nDescription.add(tempDescription);
                    tempDescription = "";
                }
            }
            nDescription.add(tempDescription);

            ListUtil.reverse(nDescription);
            for (String str : nDescription) {
                notoSansJPRegular10.drawText(str, ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX, displayHeight - (notoSansJPRegular10.getHeight() * 4) - offsetY, new Color(255, 255, 255, 255), true);
                offsetY += notoSansJPRegular10.getHeight();
                maxOffsetY += notoSansJPRegular10.getHeight();
            }
        }
        NanoVG.nvgResetScissor(nvg);

        ListUtil.reverse(chatDataList);

        if (scrollCount > 0) {
            if (scrollY < maxOffsetY - (displayHeight - (notoSansJPRegular10.getHeight() * 4) - (3 + (bonIcon15.getHeight() * 2) + 10 + ubuntu10.getHeight() * 2))) {
                scrollY += 10;
            }
        }

        NanoVGRenderUtil.drawRadialGradientRect(textBoxX - 1, textBoxY - 1, textBoxWidth + 2, textBoxHeight + 2, mouseX, mouseY, 0, 70, new Color(91, 91, 91, 255), new Color(4, 4, 4, 255));
        NanoVGRenderUtil.drawRectWH(textBoxX, textBoxY, textBoxWidth, textBoxHeight, new Color(0, 0, 0, 255));

        if (MouseUtil.isHoveringWH(textBoxX, textBoxY, textBoxWidth, textBoxHeight, mouseX, mouseY)) {
            NanoVGRenderUtil.drawOutLineRectWH(textBoxX, textBoxY, textBoxWidth, textBoxHeight, 1.0f, new Color(91, 91, 91, 255));
            NanoVGRenderUtil.drawRadialGradientRect(textBoxX, textBoxY, textBoxWidth, textBoxHeight, mouseX, mouseY, 0, 70, new Color(255, 255, 255, 50), new Color(4, 4, 4, 255));
        }

        String[] descriptionArray = StringUtil.split(typedText, "");
        String tempDescription = "";
        List<String> nDescription = new ArrayList<>();

        for (String s : descriptionArray) {
            tempDescription = tempDescription + s;

            float descriptionWidth = notoSansJPRegular10.getWidth(tempDescription);

            if (textBoxWidth - (notoSansJPRegular10.getHeight() / 4f) - 10 < descriptionWidth) {
                nDescription.add(tempDescription);
                tempDescription = "";
            }
        }
        nDescription.add(tempDescription);

        NanoVG.nvgScissor(nvg, textBoxX, textBoxY, textBoxWidth, textBoxHeight);
        float maxOffsetY2 = -textBoxHeight;
        for (String ignore : nDescription) {
            maxOffsetY2 += textBoxHeight;
        }

        float offsetY2 = 0.0f;
        NanoVGUtil.translate(0, -maxOffsetY2);
        for (String str : nDescription) {
            notoSansJPRegular10.drawText(str, textBoxX + 5, textBoxY + (notoSansJPRegular10.getHeight() / 4f) + offsetY2, new Color(255, 255, 255, 255), true);

            if (typed) {
                NanoVGRenderUtil.drawLineWH(textBoxX + 8 + notoSansJPRegular10.getWidth(str), textBoxY + (notoSansJPRegular10.getHeight() / 4f) + offsetY2, 0, notoSansJPRegular10.getHeight(), 1f, new Color(255, 255, 255, 255));
            }

            offsetY2 += textBoxHeight;
        }
        NanoVGUtil.restTranslate();
        NanoVG.nvgResetScissor(nvg);
    }

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MouseUtil.isHoveringWH(textBoxX, textBoxY, textBoxWidth, textBoxHeight, mouseX, mouseY)) {
            typed = !typed;
        }
    }

    public static void keyTyped(char typedChar, int keyCode) {
        if (typed) {
            if (Keyboard.isKeyDown(Keyboard.KEY_V) && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))) {
                String clipboardText = ClipboardUtil.getClipboard();
                if (clipboardText != null) {
                    typedText = StringUtil.addLastChar(typedText, clipboardText);
                }
                return;
            }

            switch (keyCode) {
                case Keyboard.KEY_ESCAPE:
                case Keyboard.KEY_RETURN:
                    ChatUtil.sendChatMessages(typedText);
                    typed = false;
                    typedText = "";
                    break;
                case Keyboard.KEY_BACK:
                    typedText = StringUtil.removeLastChar(typedText);
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                typedText = StringUtil.addLastChar(typedText, typedChar);
            }
        }
    }

    public static void update(float inDisplayWidth, float inDisplayHeight) {
        FontUtil notoSansJPRegular10 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 10);

        displayWidth = inDisplayWidth;
        displayHeight = inDisplayHeight;
        textBoxX = ClickGui2.INSTANCE.menuBarWidth + ClickGui2.INSTANCE.offsetX;
        textBoxY = displayHeight - ((notoSansJPRegular10.getHeight() / 2f) + notoSansJPRegular10.getHeight() + (notoSansJPRegular10.getHeight() / 2f));
        textBoxWidth = displayWidth - textBoxX - (textBoxX / 2f);
        textBoxHeight = notoSansJPRegular10.getHeight() + (notoSansJPRegular10.getHeight() / 2f);
    }
}
