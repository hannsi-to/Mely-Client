package me.hannsi.melyclient.util.render.nanovg.render.font;

import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.system.conversion.AlignExtractor;
import me.hannsi.melyclient.util.system.math.StringUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;
import me.hannsi.melyclient.util.system.math.color.MChatFormatting;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.List;

import static me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil.color;
import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class FontUtil {
    public Font font;
    public float size;

    public FontUtil(Font font, float size) {
        this.font = font;
        this.size = size;
    }

    public float getWidth(String text) {
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgFontSize(nvg, size);
        return NanoVG.nvgTextBounds(nvg, 0, 0, text, new float[4]);
    }

    public float getHeight() {
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgFontSize(nvg, size);

        float[] lineh = new float[1];
        NanoVG.nvgTextMetrics(nvg, new float[1], new float[1], lineh);

        return lineh[0];
    }

    public void drawText(String text, float x, float y, Color color) {
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, false);
    }

    public void drawBlurText(String text, float x, float y, Color color, float blur, Color blurColor) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, false);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, false);
    }

    public void drawBlurText(String text, float x, float y, Color color, float blur, Color blurColor, int align) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, align, false);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, align, false);
    }

    public void drawTextCenter(String text, float x, float y, Color color) {
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, false);
    }

    public void drawBlurTextCenter(String text, float x, float y, Color color, float blur, Color blurColor) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, false);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, false);
    }

    public void drawText(String text, float x, float y, Color color, boolean chatFormat) {
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, chatFormat);
    }

    public void drawBlurText(String text, float x, float y, Color color, float blur, Color blurColor, boolean chatFormat) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, chatFormat);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, chatFormat);
    }

    public void drawBlurText(String text, float x, float y, Color color, float blur, Color blurColor, int align, boolean chatFormat) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, align, chatFormat);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, align, chatFormat);
    }

    public void drawTextCenter(String text, float x, float y, Color color, boolean chatFormat) {
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, chatFormat);
    }

    public void drawBlurTextCenter(String text, float x, float y, Color color, float blur, Color blurColor, boolean chatFormat) {
        NanoVG.nvgFontBlur(nvg, blur);
        drawText(text, x, y, blurColor, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, chatFormat);
        NanoVG.nvgFontBlur(nvg, 0f);
        drawText(text, x, y, color, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE, chatFormat);
    }

    public void drawChatFormattingText(String text, float x, float y, Color color, int align) {
        float offsetX = 0.0f;
        float offsetY = 0.0f;
        boolean code = false;
        float heightMiddle = getHeight() / 2f;
        boolean obfuscated = false;
        char obfuscatedChar = '\u0000';
        boolean drawStrikethrough = false;
        float strikethroughLineX = 0.0f;
        float strikethroughLineY = 0.0f;
        float strikethroughLineWidth = 0.0f;
        float strikethroughLineHeight = 0.0f;
        boolean drawUnderline = false;
        float underLineX = 0.0f;
        float underLineY = 0.0f;
        float underLineWidth = 0.0f;
        float underLineHeight = 0.0f;
        float italic = 0.0f;
        float size = 0.0f;
        MChatFormatting mChatFormatting = null;
        boolean bold = false;

        for (char ch : StringUtil.getChars(text)) {
            String str = StringUtil.getStringFromChar(ch);

            if (code) {
                mChatFormatting = MChatFormatting.getMChatFormatting(ch);
                code = false;
                continue;
            }

            if (mChatFormatting != null) {
                if (mChatFormatting.getForegroundColor() != null) {
                    color = ColorUtil.getForegroundColorChatFormatting(mChatFormatting);
                }
                if (mChatFormatting == MChatFormatting.RESET) {
                    drawStrikethrough = false;
                    strikethroughLineX = 0.0f;
                    strikethroughLineY = 0.0f;
                    strikethroughLineWidth = 0.0f;
                    strikethroughLineHeight = 0.0f;
                    drawUnderline = false;
                    underLineX = 0.0f;
                    underLineY = 0.0f;
                    underLineWidth = 0.0f;
                    underLineHeight = 0.0f;
                    italic = 0.0f;
                    size = 0.0f;
                    mChatFormatting = null;
                    obfuscated = false;
                    obfuscatedChar = '\u0000';
                    bold = false;
                }
                if (mChatFormatting == MChatFormatting.OBFUSCATED) {
                    String characters = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}[]";
                    obfuscatedChar = StringUtil.getRandomCharacter(characters);
                    obfuscated = true;
                }
                if (mChatFormatting == MChatFormatting.BOLD) {
                    bold = true;
                    size = 2f;
                }
                if (mChatFormatting == MChatFormatting.STRIKETHROUGH) {
                    List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                    for (Integer integer : alignments) {
                        switch (integer) {
                            case NanoVG.NVG_ALIGN_LEFT:
                                strikethroughLineX = 0.0f;
                                strikethroughLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_CENTER:
                                float widthCenter = getWidth(str) / 2f;
                                strikethroughLineX = -widthCenter;
                                strikethroughLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_RIGHT:
                                strikethroughLineX = -getWidth(str);
                                strikethroughLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_TOP:
                                strikethroughLineY = heightMiddle;
                                strikethroughLineHeight = 0.0f;
                                break;
                            case NanoVG.NVG_ALIGN_MIDDLE:
                                strikethroughLineY = 0;
                                strikethroughLineHeight = 0;
                                break;
                            case NanoVG.NVG_ALIGN_BOTTOM:
                            case NanoVG.NVG_ALIGN_BASELINE:
                                strikethroughLineY = -heightMiddle;
                                strikethroughLineHeight = 0.0f;
                                break;
                        }
                    }

                    drawStrikethrough = true;
                }
                if (mChatFormatting == MChatFormatting.UNDERLINE) {
                    List<Integer> alignments = AlignExtractor.getAlignmentsAsInteger(align);

                    for (Integer integer : alignments) {
                        switch (integer) {
                            case NanoVG.NVG_ALIGN_LEFT:
                                underLineX = 0.0f;
                                underLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_CENTER:
                                float widthCenter = getWidth(str) / 2f;
                                underLineX = -widthCenter;
                                underLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_RIGHT:
                                underLineX = -getWidth(str);
                                underLineWidth = getWidth(str);
                                break;
                            case NanoVG.NVG_ALIGN_TOP:
                                underLineY = getHeight();
                                underLineHeight = 0.0f;
                                break;
                            case NanoVG.NVG_ALIGN_MIDDLE:
                                underLineY = heightMiddle;
                                underLineHeight = 0.0f;
                                break;
                            case NanoVG.NVG_ALIGN_BOTTOM:
                            case NanoVG.NVG_ALIGN_BASELINE:
                                underLineY = 0.0f;
                                underLineHeight = 0.0f;
                                break;
                        }
                    }

                    drawUnderline = true;
                }
                if (mChatFormatting == MChatFormatting.ITALIC) {
                    italic = 0.1f;
                }
                if (mChatFormatting == MChatFormatting.NEWLINE) {
                    offsetX = 0.0f;
                    offsetY += getHeight();
                }
            }

            if (str.equals("§")) {
                code = true;
                continue;
            }

            drawNanoVGText(obfuscated ? StringUtil.getStringFromChar(obfuscatedChar) : str, x + offsetX, y + offsetY, color, align, italic, bold ? size : 0);
            if (drawStrikethrough) {
                NanoVGRenderUtil.drawLineWH(x + offsetX + strikethroughLineX, y + offsetY + strikethroughLineY, strikethroughLineWidth, strikethroughLineHeight, 1.0f, color);
            }
            if (drawUnderline) {
                NanoVGRenderUtil.drawLineWH(x + offsetX + underLineX, y + offsetY + underLineY, underLineWidth, underLineHeight, 1.0f, color);
            }

            offsetX += getWidth(str);
        }
    }

    public void drawText(String text, float x, float y, Color color, int align, boolean chatFormat) {
        if (chatFormat) {
            drawChatFormattingText(text, x, y, color, align);
        } else {
            drawNanoVGText(text, x, y, color, align, 0.0f, 0.0f);
        }
    }

    public void drawNanoVGText(String text, float x, float y, Color color, int align, float italic, float plusSize) {
        NanoVG.nvgBeginPath(nvg);

        NanoVG.nvgRotate(nvg, italic);
        NVGColor nvgColor = NVGColor.calloc();
        color(color, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);

        NanoVG.nvgFontSize(nvg, size + plusSize);
        NanoVG.nvgTextAlign(nvg, align);
        NanoVG.nvgFontFace(nvg, font.getName());
        NanoVG.nvgText(nvg, x, y, text);

        NanoVG.nvgRGBAf(0, 0, 0, 0, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);
        NanoVG.nvgRotate(nvg, 0.0f);

        NanoVG.nvgRotate(nvg, -italic);

        NanoVG.nvgClosePath(nvg);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
