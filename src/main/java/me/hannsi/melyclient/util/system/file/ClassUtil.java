package me.hannsi.melyclient.util.system.file;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil {
    public static <T> Set<Class<? extends T>> getClassesFormPackage(String packagePath, Class<T> clazz) {
        return new HashSet<>(new Reflections(packagePath).getSubTypesOf(clazz));
    }

    public static <T> T createInstance(Class<? extends T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            new DebugLog("Created instance " + instance.getClass().getName(), DebugLevel.DEBUG);
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            new DebugLog(e, DebugLevel.ERROR);
        }

        return null;
    }
}
