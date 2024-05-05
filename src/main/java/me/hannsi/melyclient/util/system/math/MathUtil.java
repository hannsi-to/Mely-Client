package me.hannsi.melyclient.util.system.math;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MathUtil {
    public static float clampF(float x, float min, float max) {
        return (x < min) ? min : (Math.min(x, max));
    }

    public static int clampI(int x, int min, int max) {
        return (x < min) ? min : (Math.min(x, max));
    }

    public static boolean isWithinRange(float x, float min, float max) {
        return (x > min && x < max);
    }

    public static boolean isWithinRange(int x, int min, int max) {
        return (x > min && x < max);
    }

    public static void preMultiply(int[] p, int offset, int length) {
        length += offset;

        for (int i = offset; i < length; i++) {

            int rgb = p[i];
            int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;
            float f = a * 0.003921569F;
            r = (int) (r * f);
            g = (int) (g * f);
            b = (int) (b * f);
            p[i] = a << 24 | r << 16 | g << 8 | b;
        }
    }

    public static void unPreMultiply(int[] p, int offset, int length) {
        length += offset;

        for (int i = offset; i < length; i++) {

            int rgb = p[i];
            int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb & 0xFF;

            if (a != 0 && a != 255) {

                float f = 255.0F / a;
                r = (int) (r * f);
                g = (int) (g * f);
                b = (int) (b * f);

                if (r > 255) {
                    r = 255;
                }

                if (g > 255) {
                    g = 255;
                }

                if (b > 255) {
                    b = 255;
                }

                p[i] = a << 24 | r << 16 | g << 8 | b;
            }
        }
    }

    public static double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    public static boolean isMultipleI(int numberToCheck, int multiple) {
        return numberToCheck % multiple == 0;
    }

    public static int getNextMultipleI(int number, int baseMultiple) {
        return (number / baseMultiple + 1) * baseMultiple;
    }

    public static int getPreviousMultipleI(int number, int baseMultiple) {
        return (number / baseMultiple) * baseMultiple;
    }

    public static int adjustToClosestMultipleI(int number, int baseMultiple) {
        if (isMultipleI(number, baseMultiple)) {
            return number;
        }

        int nextMultiple = getNextMultipleI(number, baseMultiple);
        int previousMultiple = getPreviousMultipleI(number, baseMultiple);

        int next1 = nextMultiple - number;
        int previous1 = number - previousMultiple;

        if (next1 < previous1) {
            return previousMultiple;
        } else {
            return nextMultiple;
        }
    }

    public static boolean isMultipleF(float numberToCheck, float multiple) {
        return Math.abs(numberToCheck % multiple) < 0.000001f;
    }

    public static float getNextMultipleF(float number, float baseMultiple) {
        return ((int) (number / baseMultiple) + 1) * baseMultiple;
    }

    public static float getPreviousMultipleF(float number, float baseMultiple) {
        return ((int) (number / baseMultiple)) * baseMultiple;
    }

    public static float adjustToClosestMultipleF(float number, float baseMultiple) {
        if (isMultipleF(number, baseMultiple)) {
            return number;
        }

        float nextMultiple = getNextMultipleF(number, baseMultiple);
        float previousMultiple = getPreviousMultipleF(number, baseMultiple);

        float next1 = nextMultiple - number;
        float previous1 = number - previousMultiple;

        if (next1 < previous1) {
            return previousMultiple;
        } else {
            return nextMultiple;
        }
    }

    public static float round(float value, int places) {
        double scale = Math.pow(10.0, places);
        return (float) (Math.round(value * scale) / scale);
    }

    public static float roundF(float number, int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        bigDecimal = bigDecimal.setScale(scale, RoundingMode.FLOOR);

        return bigDecimal.floatValue();
    }

    public static float bdAdd(float number1, float number2) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        BigDecimal bd2 = BigDecimal.valueOf(number2);
        return bd1.add(bd2).floatValue();
    }

    public static float bdSubtract(float number1, float number2) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        BigDecimal bd2 = BigDecimal.valueOf(number2);
        return bd1.subtract(bd2).floatValue();
    }

    public static float bdMultiply(float number1, float number2) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        BigDecimal bd2 = BigDecimal.valueOf(number2);
        return bd1.multiply(bd2).floatValue();
    }

    public static float bdDivide(float number1, float number2, int scale) {
        return bdDivide(number1, number2, scale, RoundingMode.HALF_UP);
    }

    public static float bdRemainder(float number1, float number2) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        BigDecimal bd2 = BigDecimal.valueOf(number2);
        float value;
        try {
            value = bd1.remainder(bd2).floatValue();
        } catch (ArithmeticException exception) {
            value = 0.0f;
            new DebugLog(exception, DebugLevel.WARNING);
        }
        return value;
    }

    public static float bdDivide(float number1, float number2, int scale, RoundingMode roundingMode) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        BigDecimal bd2 = BigDecimal.valueOf(number2);
        return bd1.divide(bd2, scale, roundingMode).floatValue();
    }

    /**
     * BigDecimal同士の値1と値2を比較した場合、
     * ・値1 < 値2なら返り値は、-1
     * ・値1 = 値2 なら返り値は、0
     * ・値1 > 値2 なら返り値は、1
     * となります。（返り値の型はint型）
     */
    public static int bdCompareTo(float number1, float number2) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        BigDecimal bd2 = BigDecimal.valueOf(number2);
        return bd1.compareTo(bd2);
    }

    public static float bdPow(float number1, int number2) {
        BigDecimal bd1 = BigDecimal.valueOf(number1);
        return bd1.pow(number2).floatValue();
    }

    public static List<Integer> caseInteger(float floatValue) {
        BigDecimal bigDecimalValue = BigDecimal.valueOf(floatValue);
        BigDecimal fractionalPart = bigDecimalValue.remainder(BigDecimal.ONE);
        int fractionalIntValue = fractionalPart.movePointRight(fractionalPart.scale()).intValue();
        int scale = bigDecimalValue.scale();

        List<Integer> returnValue = new ArrayList<>();
        returnValue.add(fractionalIntValue);
        returnValue.add(scale);

        return returnValue;
    }

    public static float caseFloat(int value, int scale) {
        return bdDivide(value, bdPow(10, scale), scale);
    }
}
