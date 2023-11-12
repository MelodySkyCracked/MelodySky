/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.LaunchClassLoader
 */
package org.spongepowered.asm.util.launchwrapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;

public final class LaunchClassLoaderUtil {
    private static final Map utils = new HashMap();
    private final LaunchClassLoader classLoader;
    private Map cachedClasses;
    private final Set invalidClasses;
    private final Set classLoaderExceptions;
    private final Set transformerExceptions;

    private LaunchClassLoaderUtil(LaunchClassLoader launchClassLoader) {
        this.classLoader = launchClassLoader;
        this.cachedClasses = (Map)LaunchClassLoaderUtil.getField(launchClassLoader, "cachedClasses");
        this.invalidClasses = (Set)LaunchClassLoaderUtil.getField(launchClassLoader, "invalidClasses");
        this.classLoaderExceptions = (Set)LaunchClassLoaderUtil.getField(launchClassLoader, "classLoaderExceptions");
        this.transformerExceptions = (Set)LaunchClassLoaderUtil.getField(launchClassLoader, "transformerExceptions");
    }

    public LaunchClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Set getLoadedClasses() {
        return this.getLoadedClasses(null);
    }

    public Set getLoadedClasses(String string) {
        HashSet<String> hashSet = new HashSet<String>();
        for (String string2 : this.cachedClasses.keySet()) {
            if (string != null && !string2.startsWith(string)) continue;
            hashSet.add(string2);
        }
        return hashSet;
    }

    public boolean isClassLoaded(String string) {
        return this.cachedClasses != null && this.cachedClasses.containsKey(string);
    }

    public boolean isClassExcluded(String string, String string2) {
        for (String string3 : this.getClassLoaderExceptions()) {
            if (!string2.startsWith(string3) && !string.startsWith(string3)) continue;
            return true;
        }
        for (String string3 : this.getTransformerExceptions()) {
            if (!string2.startsWith(string3) && !string.startsWith(string3)) continue;
            return true;
        }
        return false;
    }

    public void registerInvalidClass(String string) {
        if (this.invalidClasses != null) {
            this.invalidClasses.add(string);
        }
    }

    public Set getClassLoaderExceptions() {
        if (this.classLoaderExceptions != null) {
            return this.classLoaderExceptions;
        }
        return Collections.emptySet();
    }

    public Set getTransformerExceptions() {
        if (this.transformerExceptions != null) {
            return this.transformerExceptions;
        }
        return Collections.emptySet();
    }

    private static Object getField(LaunchClassLoader launchClassLoader, String string) {
        try {
            Field field = LaunchClassLoader.class.getDeclaredField(string);
            field.setAccessible(true);
            return field.get(launchClassLoader);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static LaunchClassLoaderUtil forClassLoader(LaunchClassLoader launchClassLoader) {
        LaunchClassLoaderUtil launchClassLoaderUtil = (LaunchClassLoaderUtil)utils.get(launchClassLoader);
        if (launchClassLoaderUtil == null) {
            launchClassLoaderUtil = new LaunchClassLoaderUtil(launchClassLoader);
            utils.put(launchClassLoader, launchClassLoaderUtil);
        }
        return launchClassLoaderUtil;
    }
}

