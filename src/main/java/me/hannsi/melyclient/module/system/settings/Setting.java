package me.hannsi.melyclient.module.system.settings;

import java.util.function.Supplier;

public class Setting<T> {
    private final String name;
    private final T defaultValue;
    private T value;
    private boolean rainbow;
    private int saturation;
    private int brightness;
    private boolean defaultRainbow;
    private int defaultSaturation;
    private int defaultBrightness;
    private int delay;
    private int defaultDelay;
    private int minDelay;
    private int maxDelay;
    private int stepDelay;
    private T min;
    private T max;
    private T step;
    private Supplier<Boolean> visibility;
    private String description;

    public Setting(String name, T defaultValue, String description) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.description = description;
    }

    public Setting(String name, T defaultValue, boolean defaultRainbow, int defaultDelay, int minDelay, int maxDelay, int stepDelay, int defaultBrightness, int defaultSaturation, String description) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.defaultRainbow = defaultRainbow;
        this.rainbow = defaultRainbow;
        this.defaultBrightness = defaultBrightness;
        this.brightness = defaultBrightness;
        this.defaultSaturation = defaultSaturation;
        this.saturation = defaultSaturation;
        this.description = description;
        this.defaultDelay = defaultDelay;
        this.delay = defaultDelay;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.stepDelay = stepDelay;
    }

    public Setting(String name, T defaultValue, T min, T max, T step, String description) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.description = description;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public Setting(String name, T defaultValue, String description, Supplier<Boolean> visibility) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.description = description;
        this.visibility = visibility;
    }

    public Setting(String name, T defaultValue, T min, T max, T step, String description, Supplier<Boolean> visibility) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.description = description;
        this.min = min;
        this.max = max;
        this.step = step;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getStep() {
        return step;
    }

    public void setStep(T step) {
        this.step = step;
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isDefaultRainbow() {
        return defaultRainbow;
    }

    public void setDefaultRainbow(boolean defaultRainbow) {
        this.defaultRainbow = defaultRainbow;
    }

    public int getDefaultSaturation() {
        return defaultSaturation;
    }

    public void setDefaultSaturation(int defaultSaturation) {
        this.defaultSaturation = defaultSaturation;
    }

    public int getDefaultBrightness() {
        return defaultBrightness;
    }

    public void setDefaultBrightness(int defaultBrightness) {
        this.defaultBrightness = defaultBrightness;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDefaultDelay() {
        return defaultDelay;
    }

    public void setDefaultDelay(int defaultDelay) {
        this.defaultDelay = defaultDelay;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(int minDelay) {
        this.minDelay = minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public int getStepDelay() {
        return stepDelay;
    }

    public void setStepDelay(int stepDelay) {
        this.stepDelay = stepDelay;
    }

    public Supplier<Boolean> getVisibility() {
        return visibility;
    }

    public void setVisibility(Supplier<Boolean> visibility) {
        this.visibility = visibility;
    }

    public boolean isVisible() {
        if (this.visibility == null) {
            return true;
        }
        return this.visibility.get();
    }
}
