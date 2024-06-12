package me.hannsi.melyclient.util.system;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListUtil {
    public static String[] removeElement(String[] input, int indexToDelete) {
        LinkedList<String> result = new LinkedList<>();
        for (int i = 0; i < input.length; ++i) {
            if (i == indexToDelete) {
                continue;
            }
            result.add(input[i]);
        }
        return result.toArray(input);
    }

    public static <T> int findValueInInt(List<T> list, T value) {
        int count = 0;
        for (T t : list) {
            if (t == value) {
                return count;
            }
            count++;
        }
        return -1;
    }

    public static <T> void change(List<T> list, int index1, int index2) {
        Collections.swap(list, index1, index2);
    }

    public static <T> void changeNext(List<T> list, int index1) {
        try {
            change(list, index1, index1 + 1);
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
    }

    public static <T> void changeBack(List<T> list, int index1) {
        try {
            change(list, index1, index1 - 1);
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
    }

    public static <T> boolean isOutOfBounce(List<T> list, int i) {
        try {
            list.get(i);
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    public static <T> void reverse(List<T> list) {
        Collections.reverse(list);
    }
}
