/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.service;

public class ObfuscationTypeDescriptor {
    private final String key;
    private final String inputFileArgName;
    private final String extraInputFilesArgName;
    private final String outFileArgName;
    private final Class environmentType;

    public ObfuscationTypeDescriptor(String string, String string2, String string3, Class clazz) {
        this(string, string2, null, string3, clazz);
    }

    public ObfuscationTypeDescriptor(String string, String string2, String string3, String string4, Class clazz) {
        this.key = string;
        this.inputFileArgName = string2;
        this.extraInputFilesArgName = string3;
        this.outFileArgName = string4;
        this.environmentType = clazz;
    }

    public final String getKey() {
        return this.key;
    }

    public String getInputFileOption() {
        return this.inputFileArgName;
    }

    public String getExtraInputFilesOption() {
        return this.extraInputFilesArgName;
    }

    public String getOutputFileOption() {
        return this.outFileArgName;
    }

    public Class getEnvironmentType() {
        return this.environmentType;
    }
}

