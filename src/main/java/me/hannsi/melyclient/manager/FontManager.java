package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.util.render.nanovg.render.Font;
import org.lwjgl.nanovg.NanoVG;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;

public class FontManager {
    public Font ubuntu;
    public Font bonIcon;

    public FontManager(){
        ubuntu = new Font("Ubuntu","/assets/minecraft/mely/font/Ubuntu-M.ttf");
        bonIcon = new Font("BonIcon","/assets/minecraft/mely/font/hannsi-BonIcon2.3.ttf");
        ubuntu.loadFont();
        bonIcon.loadFont();
    }

    public float getWidth(Font font,String text,float size){
        NanoVG.nvgFontFace(nvg,font.getName());
        NanoVG.nvgFontSize(nvg,size);
        return NanoVG.nvgTextBounds(nvg,0,0,text, new float[4]);
    }

    public float getHeight(Font font,float size){
        NanoVG.nvgFontFace(nvg,font.getName());
        NanoVG.nvgFontSize(nvg,size);

        float[] lineh = new float[1];

        NanoVG.nvgTextMetrics(nvg,new float[1],new float[1],lineh);

        return lineh[0];
    }
}
