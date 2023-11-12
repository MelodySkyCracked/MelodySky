/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.tools.obfuscation.ObfuscationData;

public interface IReferenceManager {
    public void write();

    public ReferenceMapper getMapper();

    public void addMethodMapping(String var1, String var2, ObfuscationData var3);

    public void addMethodMapping(String var1, String var2, MemberInfo var3, ObfuscationData var4);

    public void addFieldMapping(String var1, String var2, MemberInfo var3, ObfuscationData var4);

    public void addClassMapping(String var1, String var2, ObfuscationData var3);
}

