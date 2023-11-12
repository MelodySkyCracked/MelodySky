/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.LoadController
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.common.network.NetworkRegistry
 */
package xyz.Melody.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xyz.Melody.Client;

public final class MelodyForceHider {
    public static void init() {
        Client.instance.logger.info("[MelodySky] [CONSOLE] Initializing Self Hider.");
        MelodyForceHider.initModList();
        MelodyForceHider.initModMap();
        MelodyForceHider.initActivedMods();
        MelodyForceHider.initIServerMods();
    }

    private static void initModList() {
        try {
            Loader loader = Loader.instance();
            Class<?> clazz = loader.getClass();
            Field field = clazz.getDeclaredField("mods");
            field.setAccessible(true);
            List list = loader.getModList().stream().collect(Collectors.toList());
            list.removeIf(MelodyForceHider::lambda$initModList$0);
            field.set(loader, list);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void initModMap() {
        try {
            Loader loader = Loader.instance();
            Class<?> clazz = loader.getClass();
            Field field = clazz.getDeclaredField("namedMods");
            field.setAccessible(true);
            HashMap hashMap = new HashMap(loader.getIndexedModList());
            if (hashMap.containsKey("melodysky")) {
                hashMap.remove("melodysky");
            }
            field.set(loader, hashMap);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void initActivedMods() {
        try {
            Loader loader = Loader.instance();
            Class<?> clazz = loader.getClass();
            Field field = clazz.getDeclaredField("modController");
            field.setAccessible(true);
            LoadController loadController = (LoadController)field.get(loader);
            Class<?> clazz2 = loadController.getClass();
            Field field2 = clazz2.getDeclaredField("activeModList");
            field2.setAccessible(true);
            List list = Loader.instance().getActiveModList().stream().collect(Collectors.toList());
            list.removeIf(MelodyForceHider::lambda$initActivedMods$1);
            field2.set(loadController, list);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void initIServerMods() {
        try {
            NetworkRegistry networkRegistry = NetworkRegistry.INSTANCE;
            Class<?> clazz = networkRegistry.getClass();
            Field field = clazz.getDeclaredField("registry");
            field.setAccessible(true);
            HashMap hashMap = new HashMap(networkRegistry.registry());
            ModContainer modContainer = null;
            Set set = hashMap.keySet();
            for (ModContainer modContainer2 : set) {
                if (!modContainer2.getName().toLowerCase().equals("melodysky")) continue;
                modContainer = modContainer2;
            }
            hashMap.remove(modContainer);
            field.set(networkRegistry, hashMap);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static boolean lambda$initActivedMods$1(ModContainer modContainer) {
        return modContainer.getName().toLowerCase().equals("melodysky");
    }

    private static boolean lambda$initModList$0(ModContainer modContainer) {
        return modContainer.getName().toLowerCase().equals("melodysky");
    }
}

