package me.hannsi.melyclient.util.render.nanovg.system;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.FunctionProvider;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class Lwjgl2FunctionProvider implements FunctionProvider {
    private final Method mGetFunctionAddress;

    public Lwjgl2FunctionProvider() {
        try {
            mGetFunctionAddress = GLContext.class.getDeclaredMethod("getFunctionAddress", String.class);
            mGetFunctionAddress.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getFunctionAddress(CharSequence functionName) {
        try {
            return (long) mGetFunctionAddress.invoke(null, functionName.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getFunctionAddress(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException();
    }
}
