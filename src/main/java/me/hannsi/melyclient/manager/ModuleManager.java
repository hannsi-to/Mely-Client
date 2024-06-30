package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.util.InterfaceMinecraft;
import me.hannsi.melyclient.util.system.conversion.PackagePath;
import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;
import me.hannsi.melyclient.util.system.file.ClassUtil;
import me.hannsi.melyclient.util.system.math.time.TimeCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleManager implements InterfaceMinecraft {
    public List<Module> modules;

    public ModuleManager() {
        load();
    }

    public void load() {
        modules = new ArrayList<>();
        loadModules();
    }

    public void loadModules() {
        Set<Class<? extends Module>> subTypes = ClassUtil.getClassesFormPackage(PackagePath.modules, Module.class);

        new DebugLog("Modules loading...", DebugLevel.DEBUG);
        long tookTime = TimeCalculator.calculate(() -> {
            for (Class<? extends Module> subType : subTypes) {
                Module module = ClassUtil.createInstance(subType);
                if (module != null) {
                    register(module);
                }
                new DebugLog("Loaded module : " + subType.getName(), DebugLevel.DEBUG);
            }
        });
        new DebugLog("Modules took " + tookTime + "ms to load!", DebugLevel.DEBUG);
    }

    public void register(Module module) {
        modules.add(module);
    }

    public void unLoad() {
        MelyClient.moduleManager = null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModuleByName(String name) {
        for (Module module : this.modules) {
            if (module.getName().equals(name)) {
                return (T) module;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModuleByClass(Class<T> clazz) {
        for (Module module : this.modules) {
            if (!clazz.isInstance(module)) {
                continue;
            }

            return (T) module;
        }
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<>();
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

    public int getModulesCountByCategory(Category category) {
        return getModulesByCategory(category).size();
    }
}
