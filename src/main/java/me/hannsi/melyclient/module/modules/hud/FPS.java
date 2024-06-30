package me.hannsi.melyclient.module.modules.hud;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.event.events.Render2DEvent;
import me.hannsi.melyclient.module.system.HudModule;
import me.hannsi.melyclient.module.system.settings.IEnumSetting;
import me.hannsi.melyclient.module.system.settings.Setting;
import me.hannsi.melyclient.util.render.DisplayUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.ListUtil;
import me.hannsi.melyclient.util.system.math.time.TimerUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static me.hannsi.melyclient.util.render.nanovg.system.NanoVGSystemUtil.nvg;

public class FPS extends HudModule {
    public Setting<Mode> mode = register(new Setting<>("Mode", Mode.Graph, "Change the drawing method."));
    public Setting<Float> offsetX = register(new Setting<>("OffsetX", 2f, 0f, 5.0f, 0.1f, "Change the width of the X coordinate", () -> mode.getValue() == Mode.Text));
    public Setting<Float> offsetY = register(new Setting<>("OffsetY", 2f, 0f, 5.0f, 0.1f, "Change the width of the Y coordinate", () -> mode.getValue() == Mode.Text));
    public Setting<Color> fontColor = register(new Setting<>("FontColor", new Color(255, 255, 255, 255), "Change the color of the text"));
    public Setting<Float> fontSize = register(new Setting<>("FontSize", 10.0f, 0.0f, 30.0f, 0.1f, "Change the font size"));
    public Setting<Boolean> background = register(new Setting<>("Background", false, "Draws the background"));
    public Setting<Float> backGroundX = register(new Setting<>("BackgroundWidth", 300f, 0.0f, DisplayUtil.getScaledResolutionWidthF(), 0.1f, "Sets the size of the background", () -> background.getValue() && mode.getValue() == Mode.Graph));
    public Setting<Float> backGroundY = register(new Setting<>("BackgroundHeight", 60f, 0.0f, DisplayUtil.getScaledResolutionHeightF(), 0.1f, "Sets the size of the background", () -> background.getValue() && mode.getValue() == Mode.Graph));
    public Setting<Color> backgroundColor = register(new Setting<>("BackgroundColor", new Color(0, 0, 0, 100), "Change the background color", () -> background.getValue()));
    public Setting<Boolean> round = register(new Setting<>("Round", false, "Round the corners of the background", () -> background.getValue()));
    public Setting<Float> roundSize = register(new Setting<>("RoundSize", 1.0f, 0.0f, 10.0f, 0.1f, "Roundness size", () -> background.getValue() && round.getValue()));
    public Setting<Float> lineWidth = register(new Setting<>("LineWidth", 1.0f, 0.1f, 5.0f, 1f, "Change the line thickness", () -> mode.getValue() == Mode.Graph));
    public Setting<Color> lineColor = register(new Setting<>("LineColor", new Color(255, 255, 255, 255), "Changed the color of the lines", () -> mode.getValue() == Mode.Graph));

    public List<FPSPoint> fpsList = new ArrayList<>();
    public TimerUtil timerUtil = new TimerUtil();

    public FPS() {
        super("FPS", "Displays the FPS on the screen.");
    }

    @Override
    public void onDraw(Render2DEvent event) {
        switch (mode.getValue()) {
            case Text:
                drawText(event);
                break;
            case Graph:
                drawGraph(event);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode.getValue());
        }

        super.onDraw(event);
    }

    public void drawGraph(Render2DEvent event) {
        nanoVGStart();

        float width = backGroundX.getValue();
        float height = backGroundY.getValue();
        float offsetY = 10f;
        float graphHeight = height - offsetY;

        FontUtil notoSansJPRegular = new FontUtil(MelyClient.fontManager.notoSansJPRegular, fontSize.getValue());

        float currentY = ((graphHeight / DisplayUtil.getMaxRefreshRate()) * DisplayUtil.getRefreshRate());
        if (timerUtil.passedMs(10)) {
            timerUtil.reset();
            fpsList.add(new FPSPoint(x.getValue() + (width - notoSansJPRegular.getWidth(DisplayUtil.getRefreshRate() + "")), (y.getValue() + graphHeight) - currentY + offsetY - 5f, DisplayUtil.getRefreshRate()));
        }

        while (fpsList.size() > width) {
            fpsList.remove(0);
        }

        if (background.getValue()) {
            NanoVGRenderUtil.drawRectWH(x.getValue(), y.getValue(), width, height, backgroundColor.getValue());
        }

        NanoVG.nvgBeginPath(nvg);

        boolean firstTime = true;
        int listCount = 0;
        ListUtil.reverse(fpsList);
        for (FPSPoint fpsPoint : fpsList) {
            if (firstTime) {
                NanoVG.nvgMoveTo(nvg, fpsPoint.getX() - listCount, fpsPoint.getY());

                firstTime = false;
            } else {
                NanoVG.nvgLineTo(nvg, fpsPoint.getX() - listCount, fpsPoint.getY());
            }
            listCount++;
        }
        ListUtil.reverse(fpsList);

        NanoVG.nvgStrokeWidth(nvg, lineWidth.getValue());
        NanoVGRenderUtil.strokeColor(lineColor.getValue());
        NanoVG.nvgClosePath(nvg);

        FPSPoint nowFpsPoint = fpsList.get(fpsList.size() - 1);
        notoSansJPRegular.drawText(nowFpsPoint.getFps() + "", x.getValue() + (width - notoSansJPRegular.getWidth(nowFpsPoint.getFps() + "")), nowFpsPoint.getY() - (notoSansJPRegular.getHeight() / 2f), fontColor.getValue());

        nanoVGEnd();
    }

    public void drawText(Render2DEvent event) {
        FontUtil notoSansJPRegular = new FontUtil(MelyClient.fontManager.notoSansJPRegular, fontSize.getValue());

        String displayText = "FPS : " + Minecraft.getDebugFPS();

        if (round.getValue()) {
            NanoVGRenderUtil.drawRoundedRectWH(x.getValue() - offsetX.getValue(), y.getValue() - offsetY.getValue(), notoSansJPRegular.getWidth(displayText) + (offsetX.getValue() * 2), notoSansJPRegular.getHeight() + (offsetY.getValue() * 2), roundSize.getValue(), backgroundColor.getValue());
        } else {
            NanoVGRenderUtil.drawRectWH(x.getValue() - offsetX.getValue(), y.getValue() - offsetY.getValue(), notoSansJPRegular.getWidth(displayText) + (offsetX.getValue() * 2), notoSansJPRegular.getHeight() + (offsetY.getValue() * 2), backgroundColor.getValue());
        }
        notoSansJPRegular.drawText(displayText, x.getValue(), y.getValue(), fontColor.getValue());
    }

    public enum Mode implements IEnumSetting {
        Text("Text"), Graph("Graph");

        final String display;

        Mode(String display) {
            this.display = display;
        }

        @Override
        public String getDisplay() {
            return display;
        }

        @Override
        public IEnumSetting[] getValues() {
            return new IEnumSetting[0];
        }
    }

    public static class FPSPoint {
        public float x;
        public float y;
        public long startSystemTime;
        public int fps;

        public FPSPoint(float x, float y, int fps) {
            this.x = x;
            this.y = y;
            this.startSystemTime = System.currentTimeMillis();
            this.fps = fps;
        }

        public long getStartSystemTime() {
            return startSystemTime;
        }

        public void setStartSystemTime(long startSystemTime) {
            this.startSystemTime = startSystemTime;
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

        public int getFps() {
            return fps;
        }

        public void setFps(int fps) {
            this.fps = fps;
        }
    }

}