package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.util.render.nanovg.render.font.Font;

public class FontManager {
    public Font ubuntu;
    public Font bonIcon;
    public Font notoSansJPRegular;

    public FontManager() {
        ubuntu = new Font("Ubuntu", "/assets/minecraft/mely/font/Ubuntu-M.ttf");
        bonIcon = new Font("BonIcon", "/assets/minecraft/mely/font/hannsi-BonIcon2.3.ttf");
        notoSansJPRegular = new Font("NotoSansJP-Regular", "/assets/minecraft/mely/font/NotoSansJP-Regular.ttf");
        ubuntu.loadFont();
        bonIcon.loadFont();
        notoSansJPRegular.loadFont();
    }
}
