/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package org.spongepowered.tools.obfuscation.mapping;

import com.google.common.base.Objects;
import java.util.LinkedHashSet;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public interface IMappingConsumer {
    public void clear();

    public void addFieldMapping(ObfuscationType var1, MappingField var2, MappingField var3);

    public void addMethodMapping(ObfuscationType var1, MappingMethod var2, MappingMethod var3);

    public MappingSet getFieldMappings(ObfuscationType var1);

    public MappingSet getMethodMappings(ObfuscationType var1);

    public static class MappingSet
    extends LinkedHashSet {
        private static final long serialVersionUID = 1L;

        public static class Pair {
            public final IMapping from;
            public final IMapping to;

            public Pair(IMapping iMapping, IMapping iMapping2) {
                this.from = iMapping;
                this.to = iMapping2;
            }

            public boolean equals(Object object) {
                if (!(object instanceof Pair)) {
                    return false;
                }
                Pair pair = (Pair)object;
                return Objects.equal((Object)this.from, (Object)pair.from) && Objects.equal((Object)this.to, (Object)pair.to);
            }

            public int hashCode() {
                return Objects.hashCode((Object[])new Object[]{this.from, this.to});
            }

            public String toString() {
                return String.format("%s -> %s", this.from, this.to);
            }
        }
    }
}

