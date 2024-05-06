package me.hannsi.melyclient.util.render.nanovg.render.font;

import org.lwjgl.nanovg.NanoVG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static me.hannsi.melyclient.util.render.nanovg.system.NVGUtil.nvg;

public class Font {
    private final String name;
    private final String path;
    private ByteBuffer byteBuffer;

    public Font(String name, String path) {
        this.name = name;
        this.path = path;

        InputStream inputStream = this.getClass().getResourceAsStream(this.path);

        try {
            setByteBuffer(toByteBuffer(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ByteBuffer toByteBuffer(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = input.read(buffer)) {
            output.write(buffer, 0, n);
        }
        byte[] bytes = output.toByteArray();
        ByteBuffer data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();
        return data;
    }

    public void loadFont() {
        NanoVG.nvgCreateFontMem(nvg, name, getByteBuffer(), 0);
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}