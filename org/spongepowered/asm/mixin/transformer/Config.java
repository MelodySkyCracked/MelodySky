/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.transformer.MixinConfig;

public class Config {
    private final String name;
    private final MixinConfig config;

    public Config(MixinConfig mixinConfig) {
        this.name = mixinConfig.getName();
        this.config = mixinConfig;
    }

    public String getName() {
        return this.name;
    }

    MixinConfig get() {
        return this.config;
    }

    public IMixinConfig getConfig() {
        return this.config;
    }

    public MixinEnvironment getEnvironment() {
        return this.config.getEnvironment();
    }

    public String toString() {
        return this.config.toString();
    }

    public boolean equals(Object object) {
        return object instanceof Config && this.name.equals(((Config)object).name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @Deprecated
    public static Config create(String string, MixinEnvironment mixinEnvironment) {
        return MixinConfig.create(string, mixinEnvironment);
    }

    public static Config create(String string) {
        return MixinConfig.create(string, MixinEnvironment.getDefaultEnvironment());
    }
}

