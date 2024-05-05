package me.hannsi.melyclient.module.system.settings;

public class Bind {
    public int keyCode;

    public Bind(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
