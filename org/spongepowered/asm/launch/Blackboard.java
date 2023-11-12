/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.Launch
 */
package org.spongepowered.asm.launch;

import net.minecraft.launchwrapper.Launch;

public final class Blackboard {
    private Blackboard() {
    }

    public static Object get(String string) {
        return Launch.blackboard.get(string);
    }

    public static void put(String string, Object object) {
        Launch.blackboard.put(string, object);
    }

    public static Object get(String string, Object object) {
        Object v = Launch.blackboard.get(string);
        return v != null ? v : object;
    }

    public static String getString(String string, String string2) {
        Object v = Launch.blackboard.get(string);
        return v != null ? v.toString() : string2;
    }

    public static final class Keys {
        public static final String TWEAKCLASSES = "TweakClasses";
        public static final String TWEAKS = "Tweaks";
        public static final String INIT = "mixin.initialised";
        public static final String AGENTS = "mixin.agents";
        public static final String CONFIGS = "mixin.configs";
        public static final String TRANSFORMER = "mixin.transformer";
        public static final String FML_LOAD_CORE_MOD = "mixin.launch.fml.loadcoremodmethod";
        public static final String FML_GET_REPARSEABLE_COREMODS = "mixin.launch.fml.reparseablecoremodsmethod";
        public static final String FML_CORE_MOD_MANAGER = "mixin.launch.fml.coremodmanagerclass";
        public static final String FML_GET_IGNORED_MODS = "mixin.launch.fml.ignoredmodsmethod";

        private Keys() {
        }
    }
}

