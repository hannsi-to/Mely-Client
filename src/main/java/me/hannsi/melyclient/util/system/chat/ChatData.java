package me.hannsi.melyclient.util.system.chat;

import net.minecraft.util.text.TextComponentString;

public class ChatData {
    public String text;
    public TextComponentString textComponentString;

    public ChatData(String text) {
        this.text = text;
        this.textComponentString = new TextComponentString(text);
    }

    public ChatData(TextComponentString textComponentString) {
        this.textComponentString = textComponentString;
        this.text = textComponentString.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextComponentString getTextComponentString() {
        return textComponentString;
    }

    public void setTextComponentString(TextComponentString textComponentString) {
        this.textComponentString = textComponentString;
    }
}
