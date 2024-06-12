package me.hannsi.melyclient.manager;

import me.hannsi.melyclient.MelyClient;
import me.hannsi.melyclient.module.modules.client.ClickGui;
import me.hannsi.melyclient.module.modules.movement.Fly;
import me.hannsi.melyclient.module.modules.movement.Sprint;
import me.hannsi.melyclient.module.modules.player.FastEat;
import me.hannsi.melyclient.module.system.Category;
import me.hannsi.melyclient.module.system.Module;
import me.hannsi.melyclient.util.InterfaceMinecraft;

import java.util.ArrayList;
import java.util.List;

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
        //Combat

        //Exploit

        //Movement
        register(new Sprint());
        register(new Fly());

        //Player
        register(new FastEat());

        //Render

        //Misc

        //Client
        register(new ClickGui());

        //Hud
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
