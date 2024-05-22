package me.hannsi.melyclient.util.system.math;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringUtil {
    public static char[] getChars(String string) {
        return string.toCharArray();
    }

    public static List<String> splitlnAsList(String str) {
        return Arrays.asList(splitln(str));
    }

    public static String[] splitln(String str) {
        return str.split("\n");
    }

    public static String extractString(String input, String startDelimiter, String endDelimiter) {
        int startIndex = input.indexOf(startDelimiter);
        int endIndex = input.indexOf(endDelimiter, startIndex + startDelimiter.length());

        if (startIndex == -1 || endIndex == -1) {
            return "";
        }

        return input.substring(startIndex + startDelimiter.length(), endIndex);
    }

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

    public static String getStringFromChars(char[] chars) {
        String str = null;
        for (char ch : chars) {
            str = addLastChar(str, ch);
        }

        return str;
    }

    public static String getStringFromChar(char ch) {
        return Character.toString(ch);
    }

    public static char getRandomCharacter(String input) {
        Random random = new Random();
        int index = random.nextInt(input.length());
        return input.charAt(index);
    }
}
