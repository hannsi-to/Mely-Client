package me.hannsi.melyclient.module.modules.hud;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.event.events.Render2DEvent;
import me.hannsi.melyclient.module.system.HudModule;
import me.hannsi.melyclient.notification.NotificationData;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.render.nanovg.render.NanoVGUtil;
import me.hannsi.melyclient.util.render.nanovg.render.font.FontUtil;
import me.hannsi.melyclient.util.system.math.MathUtil;
import me.hannsi.melyclient.util.system.math.animation.EasingUtil;
import me.hannsi.melyclient.util.system.math.vector.Vector2f;

import java.awt.*;

public class Notification extends HudModule {
    public Notification() {
        super("Notification", "Notify information about the client", true);
    }

    public static boolean isCirceOver(float x, float y, float width, float height, float cX, float cY, float range) {
        Vector2f cVector2f = new Vector2f(cX, cY);

        float dLeftTop = distance(new Vector2f(x, y), cVector2f);
        float dRightTop = distance(new Vector2f(x + width, y), cVector2f);
        float dLeftButton = distance(new Vector2f(x, y + height), cVector2f);
        float dRightButton = distance(new Vector2f(x + width, y + height), cVector2f);

        float dLargest = getLargest(dLeftTop, dRightTop, dLeftButton, dRightButton);

        return (range > dLargest);
    }

    public static float getLargest(float... values) {
        float result = 0.0f;

        for (float value : values) {
            result = Math.max(result, value);
        }

        return result;
    }

    public static float distance(Vector2f vector2f1, Vector2f vector2f2) {
        float[] width = sortValue(vector2f1.getX(), vector2f2.getX());
        float[] height = sortValue(vector2f1.getY(), vector2f2.getY());
        return (float) MathUtil.distance(width[1], height[1], width[0], height[0]);
    }

    public static float[] sortValue(float value1, float value2) {
        float min = Math.min(value1, value2);
        float max = Math.max(value1, value2);
        return new float[]{min, max};
    }

    @Override
    public void onDraw(Render2DEvent event) {
        nanoVGStart();

        for (NotificationData notificationData : MelyClient.notificationManager.getNotificationDataList()) {
            NanoVGUtil.translate(x.getValue(), y.getValue() + (notificationData.getCount() * 40));

            FontUtil notoSansJPRegular13 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 13);
            FontUtil notoSansJPRegular10 = new FontUtil(MelyClient.fontManager.notoSansJPRegular, 10);
            FontUtil bonIcon17 = new FontUtil(MelyClient.fontManager.bonIcon, 17);
            float barWidth = Math.max(notificationData.getMinWidth(), Math.max(notoSansJPRegular13.getWidth(notificationData.getTitle()) + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()) + 10f, notoSansJPRegular10.getWidth(notificationData.getDescription())) + 10f + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()));

            if (notificationData.getUpdateTime() < 1000f) {
                if (notificationData.isFirstTime()) {
                    notificationData.reset();
                    notificationData.setFirstTime(false);
                }

                EasingUtil circleEasing = notificationData.getCircleEasingIn();
                float value = circleEasing.get(1000);

                if (value <= 0) {
                    circleEasing.reset();
                    value = 0;
                } else if (value >= 1) {
                    circleEasing.reset();
                    value = 1;
                }

                float distance = distance(new Vector2f(0, 30 / 2f), new Vector2f(barWidth, 30));
                NanoVGUtil.scissor(0, 0, barWidth * value, 30);

                NanoVGRenderUtil.drawCircle(0, 30 / 2f, distance * value, new Color(0, 0, 0, 50));
                bonIcon17.drawTextCenter(notificationData.getDebugLevel().getIcon(), (bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()) / 2f), (30 / 2f), notificationData.getDebugLevel().getColor());
                notoSansJPRegular13.drawText(notificationData.getTitle(), 2 + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()), 4, new Color(255, 255, 255, 255));
                notoSansJPRegular10.drawText(notificationData.getDescription(), 2 + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()), 4 + notoSansJPRegular13.getHeight(), notificationData.getDebugLevel().getColor());
                NanoVGRenderUtil.drawRoundedRectWH(0, 29f, distance * value, 1f, 0.5f, notificationData.getDebugLevel().getColor());
                NanoVGUtil.resetScissor();
            } else if (notificationData.getUpdateTime() < 5000f) {
                EasingUtil timeBarEasingUtil = notificationData.getTimeBarEasing();
                if (notificationData.isSecondTime()) {
                    notificationData.reset();
                    notificationData.setSecondTime(false);
                }

                float value = timeBarEasingUtil.get(4000);
                if (value <= 0) {
                    timeBarEasingUtil.reset();
                    value = 0;
                } else if (value >= 1) {
                    timeBarEasingUtil.reset();
                    value = 1;
                }

                NanoVGRenderUtil.drawRectWH(0, 0, barWidth, 30, new Color(0, 0, 0, 50));
                bonIcon17.drawTextCenter(notificationData.getDebugLevel().getIcon(), (bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()) / 2f), (30 / 2f), notificationData.getDebugLevel().getColor());
                notoSansJPRegular13.drawText(notificationData.getTitle(), 2 + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()), 4, new Color(255, 255, 255, 255));
                notoSansJPRegular10.drawText(notificationData.getDescription(), 2 + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()), 4 + notoSansJPRegular13.getHeight(), notificationData.getDebugLevel().getColor());
                NanoVGRenderUtil.drawRoundedRectWH(0, 29f, barWidth * value, 1f, 0.5f, notificationData.getDebugLevel().getColor());
            } else if (notificationData.getUpdateTime() < 6000f) {
                EasingUtil circleEasing = notificationData.getCircleEasingOut();
                if (notificationData.isThirdTime()) {
                    notificationData.reset();
                    notificationData.setThirdTime(false);
                }

                float value = circleEasing.get(1000);
                if (value <= 0) {
                    circleEasing.reset();
                    value = 0;
                } else if (value >= 1) {
                    circleEasing.reset();
                    value = 1;
                }

                float distance = distance(new Vector2f(0, 30 / 2f), new Vector2f(0 + barWidth, 30));
                NanoVGUtil.scissor(0, 0, barWidth * value, 30);

                NanoVGRenderUtil.drawCircle(0, 30 / 2f, distance * value, new Color(0, 0, 0, 50));
                bonIcon17.drawTextCenter(notificationData.getDebugLevel().getIcon(), (bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()) / 2f), (30 / 2f), notificationData.getDebugLevel().getColor());
                notoSansJPRegular13.drawText(notificationData.getTitle(), 2 + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()), 4, new Color(255, 255, 255, 255));
                notoSansJPRegular10.drawText(notificationData.getDescription(), 2 + bonIcon17.getWidth(notificationData.getDebugLevel().getIcon()), 4 + notoSansJPRegular13.getHeight(), notificationData.getDebugLevel().getColor());

                NanoVGUtil.resetScissor();
            } else {
                MelyClient.notificationManager.removeNotification(notificationData);
            }

            NanoVGUtil.resetTranslate();

            notificationData.upDate();
        }

        nanoVGEnd();

        super.onDraw(event);
    }
}
