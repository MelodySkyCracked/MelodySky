/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IMappingWriter {
    public void write(String var1, ObfuscationType var2, IMappingConsumer.MappingSet var3, IMappingConsumer.MappingSet var4);
}

