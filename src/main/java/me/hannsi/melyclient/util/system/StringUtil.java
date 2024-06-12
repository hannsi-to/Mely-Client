package me.hannsi.melyclient.util.system;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringUtil {
    public static String linkList(String[] list, String space) {
        String result = "";

        for (String str : list) {
            result = addLastChar(result, str + space);
        }

        return result;
    }

    public static String strip(String str, String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }

    public static StringSelection getStringSelection(String str) {
        return new StringSelection(str);
    }

    public static char[] getChars(String string) {
        return string.toCharArray();
    }

    public static List<String> splitlnAsList(String str) {
        return Arrays.asList(splitln(str));
    }

    public static String[] splitln(String str) {
        return split(str, "\n");
    }

    public static List<String> splitAsList(String str, String regex) {
        return Arrays.asList(split(str, regex));
    }

    public static String[] split(String str, String regex) {
        return str.split(regex);
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

    public static List<String> addEndCharInList(String[] strings, String addStr) {
        List<String> list = new ArrayList<>();

        for (String string : strings) {
            list.add(string + addStr);
        }

        return list;
    }

    public static List<String> addEndCharInList(String[] strings, char addStr) {
        return addEndCharInList(strings, String.valueOf(addStr));
    }

    public static List<String> addStartCharInList(String[] strings, String addStr) {
        List<String> list = new ArrayList<>();

        for (String string : strings) {
            list.add(addStr + string);
        }

        return list;
    }

    public static List<String> addStartCharInList(String[] strings, char addStr) {
        return addStartCharInList(strings, String.valueOf(addStr));
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
