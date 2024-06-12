package me.hannsi.melyclient.util.system;

import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardUtil implements InterfaceMinecraft {
    public static Clipboard getClipboardSystem() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public static String getClipboard() {
        String str = null;

        try {
            str = (String) getClipboardSystem().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return str;
    }

    public static void setClipboard(String setText) {
        setClipboard(StringUtil.getStringSelection(setText));
    }

    public static void setClipboard(StringSelection setText) {
        getClipboardSystem().setContents(setText, null);
    }
}
