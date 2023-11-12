/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface IObfuscationDataProvider {
    public ObfuscationData getObfEntryRecursive(MemberInfo var1);

    public ObfuscationData getObfEntry(MemberInfo var1);

    public ObfuscationData getObfEntry(IMapping var1);

    public ObfuscationData getObfMethodRecursive(MemberInfo var1);

    public ObfuscationData getObfMethod(MemberInfo var1);

    public ObfuscationData getRemappedMethod(MemberInfo var1);

    public ObfuscationData getObfMethod(MappingMethod var1);

    public ObfuscationData getRemappedMethod(MappingMethod var1);

    public ObfuscationData getObfFieldRecursive(MemberInfo var1);

    public ObfuscationData getObfField(MemberInfo var1);

    public ObfuscationData getObfField(MappingField var1);

    public ObfuscationData getObfClass(TypeHandle var1);

    public ObfuscationData getObfClass(String var1);
}

