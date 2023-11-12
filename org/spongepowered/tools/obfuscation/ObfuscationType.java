/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;

public final class ObfuscationType {
    private static final Map types = new LinkedHashMap();
    private final String key;
    private final ObfuscationTypeDescriptor descriptor;
    private final IMixinAnnotationProcessor ap;
    private final IOptionProvider options;

    private ObfuscationType(ObfuscationTypeDescriptor obfuscationTypeDescriptor, IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        this.key = obfuscationTypeDescriptor.getKey();
        this.descriptor = obfuscationTypeDescriptor;
        this.ap = iMixinAnnotationProcessor;
        this.options = iMixinAnnotationProcessor;
    }

    public final ObfuscationEnvironment createEnvironment() {
        try {
            Class clazz = this.descriptor.getEnvironmentType();
            Constructor constructor = clazz.getDeclaredConstructor(ObfuscationType.class);
            constructor.setAccessible(true);
            return (ObfuscationEnvironment)constructor.newInstance(this);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public String toString() {
        return this.key;
    }

    public String getKey() {
        return this.key;
    }

    public ObfuscationTypeDescriptor getConfig() {
        return this.descriptor;
    }

    public IMixinAnnotationProcessor getAnnotationProcessor() {
        return this.ap;
    }

    public boolean isDefault() {
        String string = this.options.getOption("defaultObfuscationEnv");
        return string == null && this.key.equals("searge") || string != null && this.key.equals(string.toLowerCase());
    }

    public boolean isSupported() {
        return this.getInputFileNames().size() > 0;
    }

    public List getInputFileNames() {
        String string;
        ImmutableList.Builder builder = ImmutableList.builder();
        String string2 = this.options.getOption(this.descriptor.getInputFileOption());
        if (string2 != null) {
            builder.add((Object)string2);
        }
        if ((string = this.options.getOption(this.descriptor.getExtraInputFilesOption())) != null) {
            for (String string3 : string.split(";")) {
                builder.add((Object)string3.trim());
            }
        }
        return builder.build();
    }

    public String getOutputFileName() {
        return this.options.getOption(this.descriptor.getOutputFileOption());
    }

    public static Iterable types() {
        return types.values();
    }

    public static ObfuscationType create(ObfuscationTypeDescriptor obfuscationTypeDescriptor, IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        String string = obfuscationTypeDescriptor.getKey();
        if (types.containsKey(string)) {
            throw new IllegalArgumentException("Obfuscation type with key " + string + " was already registered");
        }
        ObfuscationType obfuscationType = new ObfuscationType(obfuscationTypeDescriptor, iMixinAnnotationProcessor);
        types.put(string, obfuscationType);
        return obfuscationType;
    }

    public static ObfuscationType get(String string) {
        ObfuscationType obfuscationType = (ObfuscationType)types.get(string);
        if (obfuscationType == null) {
            throw new IllegalArgumentException("Obfuscation type with key " + string + " was not registered");
        }
        return obfuscationType;
    }
}

