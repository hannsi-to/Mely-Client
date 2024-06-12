package me.hannsi.melyclient.util.system;

public class MouseUtil {
    public static boolean isHoveringWH(float x, float y, float width, float height, float mouseX, float mouseY) {
        return mouseX > x && mouseX <= x + width && mouseY > y && mouseY <= y + height;
    }

    public static boolean isHoveringWH(double x, double y, double width, double height, float mouseX, float mouseY) {
        return mouseX > x && mouseX <= x + width && mouseY > y && mouseY <= y + height;
    }

    public static boolean isHovering(float x1, float y1, float x2, float y2, float mouseX, float mouseY) {
        return mouseX > x1 && mouseX <= x2 && mouseY > y1 && mouseY <= y2;
    }

    public static boolean isHovering(double x1, double y1, double x2, double y2, float mouseX, float mouseY) {
        return mouseX > x1 && mouseX <= x2 && mouseY > y1 && mouseY <= y2;
    }
}
