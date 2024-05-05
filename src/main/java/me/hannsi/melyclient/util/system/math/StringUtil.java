package me.hannsi.melyclient.util.system.math;

public class StringUtil {
    public static String removeLastChar(String str) {
        String output = "";
        if (str != null && !str.isEmpty()) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }

    public static String addLastChar(String str, String addStr) {
        return str + addStr;
    }

    public static String addLastChar(String str, char addStr) {
        return str + addStr;
    }

    public static String fillIfEmpty(String str, String fillText) {
        if (str.isEmpty()) {
            str = fillText;
        }

        return str;
    }

    public static String fillIfEmpty(String str, int fillText) {
        return fillIfEmpty(str, Integer.toString(fillText));
    }

    public static String fillIfEmpty(String str, float fillText) {
        return fillIfEmpty(str, Float.toString(fillText));
    }

    public static String fillIfEmpty(String str, Double fillText) {
        return fillIfEmpty(str, Double.toString(fillText));
    }
}
