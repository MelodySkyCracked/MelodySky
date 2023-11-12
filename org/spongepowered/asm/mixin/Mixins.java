/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.Blackboard;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.Config;

public final class Mixins {
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    private static final String CONFIGS_KEY = "mixin.configs.queue";
    private static final Set errorHandlers = new LinkedHashSet();

    private Mixins() {
    }

    public static void addConfigurations(String ... stringArray) {
        MixinEnvironment mixinEnvironment = MixinEnvironment.getDefaultEnvironment();
        for (String string : stringArray) {
            Mixins.createConfiguration(string, mixinEnvironment);
        }
    }

    public static void addConfiguration(String string) {
        Mixins.createConfiguration(string, MixinEnvironment.getDefaultEnvironment());
    }

    @Deprecated
    static void addConfiguration(String string, MixinEnvironment mixinEnvironment) {
        Mixins.createConfiguration(string, mixinEnvironment);
    }

    private static void createConfiguration(String string, MixinEnvironment mixinEnvironment) {
        Config config = null;
        try {
            config = Config.create(string, mixinEnvironment);
        }
        catch (Exception exception) {
            logger.error("Error encountered reading mixin config " + string + ": " + exception.getClass().getName() + " " + exception.getMessage(), (Throwable)exception);
        }
        Mixins.registerConfiguration(config);
    }

    private static void registerConfiguration(Config config) {
        if (config == null) {
            return;
        }
        MixinEnvironment mixinEnvironment = config.getEnvironment();
        if (mixinEnvironment != null) {
            mixinEnvironment.registerConfig(config.getName());
        }
        Mixins.getConfigs().add(config);
    }

    public static Set getConfigs() {
        LinkedHashSet linkedHashSet = (LinkedHashSet)Blackboard.get(CONFIGS_KEY);
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet();
            Launch.blackboard.put(CONFIGS_KEY, linkedHashSet);
        }
        return linkedHashSet;
    }

    public static void registerErrorHandlerClass(String string) {
        if (string != null) {
            errorHandlers.add(string);
        }
    }

    public static Set getErrorHandlerClasses() {
        return Collections.unmodifiableSet(errorHandlers);
    }
}

