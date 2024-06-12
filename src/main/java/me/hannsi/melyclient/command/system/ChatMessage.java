package me.hannsi.melyclient.command.system;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMessage extends TextComponentBase {
    private final String text;

    public ChatMessage(String text) {
        Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
        Matcher matcher = pattern.matcher(text);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String replacement = "§" + matcher.group().substring(1);
            matcher.appendReplacement(stringBuffer, replacement);
        }
        matcher.appendTail(stringBuffer);
        this.text = stringBuffer.toString();
    }

    @Nonnull
    public String getUnformattedComponentText() {
        return this.text;
    }

    @Nonnull
    public ITextComponent createCopy() {
        return new ChatMessage(this.text);
    }
}
