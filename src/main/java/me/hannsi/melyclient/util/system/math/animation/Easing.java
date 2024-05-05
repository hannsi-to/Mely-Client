package me.hannsi.melyclient.util.system.math.animation;

public interface Easing {
    Easing easeLinear = value -> value;
    Easing easeInSine = value -> 1 - Math.cos((value * Math.PI) / 2);
    Easing easeOutSine = value -> Math.sin((value * Math.PI) / 2);
    Easing easeInOutSine = value -> -(Math.cos(Math.PI * value) - 1) / 2;
    Easing easeInQuad = value -> value * value;
    Easing easeOutQuad = value -> 1 - (1 - value) * (1 - value);
    Easing easeInOutQuad = value -> value < 0.5 ? 2 * value * value : 1 - Math.pow(-2 * value + 2, 2) / 2;
    Easing easeInCubic = value -> value * value * value;
    Easing easeOutCubic = value -> 1 - Math.pow(1 - value, 3);
    Easing easeInOutCubic = value -> value < 0.5 ? 4 * value * value * value : 1 - Math.pow(-2 * value + 2, 3) / 2;
    Easing easeInQuart = value -> value * value * value * value;
    Easing easeOutQuart = value -> 1 - Math.pow(1 - value, 4);
    Easing easeInOutQuart = value -> value < 0.5 ? 8 * value * value * value * value : 1 - Math.pow(-2 * value + 2, 4) / 2;
    Easing easeInQuint = value -> value * value * value * value * value;
    Easing easeOutQuint = value -> 1 - Math.pow(1 - value, 5);
    Easing easeInOutQuint = value -> value < 0.5 ? 16 * value * value * value * value * value : 1 - Math.pow(-2 * value + 2, 5) / 2;
    Easing easeInExpo = value -> value == 0 ? 0 : Math.pow(2, 10 * value - 10);
    Easing easeOutExpo = value -> value == 1 ? 1 : 1 - Math.pow(2, -10 * value);
    Easing easeInOutExpo = value -> value == 0 ? 0 : value == 1 ? 1 : value < 0.5 ? Math.pow(2, 20 * value - 10) / 2 : (2 - Math.pow(2, -20 * value + 10)) / 2;
    Easing easeInCirc = value -> 1 - Math.sqrt(1 - Math.pow(value, 2));
    Easing easeOutCirc = value -> Math.sqrt(1 - Math.pow(value - 1, 2));
    Easing easeInOutCirc = value -> value < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * value, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * value + 2, 2)) + 1) / 2;
    Easing easeInBack = value -> (1.70158 + 1) * value * value * value - 1.70158 * value * value;
    Easing easeOutBack = value -> 1 + (1.70158 + 1) * Math.pow(value - 1, 3) + 1.70158 * Math.pow(value - 1, 2);
    Easing easeInOutBack = value -> value < 0.5 ? (Math.pow(2 * value, 2) * (((1.70158 * 1.525) + 1) * 2 * value - (1.70158 * 1.525))) / 2 : (Math.pow(2 * value - 2, 2) * (((1.70158 * 1.525) + 1) * (value * 2 - 2) + (1.70158 * 1.525)) + 2) / 2;
    Easing easeInElastic = value -> value == 0 ? 0 : value == 1 ? 1 : -Math.pow(2, 10 * value - 10) * Math.sin((value * 10 - 10.75) * (2 * Math.PI) / 3);
    Easing easeOutElastic = value -> value == 0 ? 0 : value == 1 ? 1 : Math.pow(2, -10 * value) * Math.sin((value * 10 - 0.75) * (2 * Math.PI) / 3) + 1;
    Easing easeInOutElastic = value -> value == 0 ? 0 : value == 1 ? 1 : value < 0.5 ? -(Math.pow(2, 20 * value - 10) * Math.sin((20 * value - 11.125) * (2 * Math.PI) / 4.5)) / 2 : (Math.pow(2, -20 * value + 10) * Math.sin((20 * value - 11.125) * (2 * Math.PI) / 4.5)) / 2 + 1;
    Easing easeInBounce = value -> {
        float value2 = 1 - value;

        if (value2 < 1 / 2.75) {
            return 7.5625 * value2 * value2;
        } else if (value2 < 2 / 2.75) {
            return 7.5625 * (value2 -= (float) (1.5 / 2.75)) * value2 + 0.75;
        } else if (value2 < 2.5 / 2.75) {
            return 7.5625 * (value2 -= (float) (2.25 / 2.75)) * value2 + 0.9375;
        } else {
            return 7.5625 * (value2 -= (float) (2.625 / 2.75)) * value2 + 0.984375;
        }
    };
    Easing easeOutBounce = value -> {
        if (value < 1 / 2.75) {
            return 7.5625 * value * value;
        } else if (value < 2 / 2.75) {
            return 7.5625 * (value -= (float) (1.5 / 2.75)) * value + 0.75;
        } else if (value < 2.5 / 2.75) {
            return 7.5625 * (value -= (float) (2.25 / 2.75)) * value + 0.9375;
        } else {
            return 7.5625 * (value -= (float) (2.625 / 2.75)) * value + 0.984375;
        }
    };
    Easing easeInOutBounce = value -> value < 0.5 ? (1 - EasingUtil.easeOutBounce(1 - 2 * value)) / 2 : (1 + EasingUtil.easeOutBounce(2 * value - 1)) / 2;

    double ease(float value);
}
