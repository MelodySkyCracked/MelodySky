/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.SmallSet;
import org.spongepowered.asm.lib.tree.analysis.Value;

public class SourceValue
implements Value {
    public final int size;
    public final Set insns;

    public SourceValue(int n) {
        this(n, SmallSet.emptySet());
    }

    public SourceValue(int n, AbstractInsnNode abstractInsnNode) {
        this.size = n;
        this.insns = new SmallSet(abstractInsnNode, null);
    }

    public SourceValue(int n, Set set) {
        this.size = n;
        this.insns = set;
    }

    public int getSize() {
        return this.size;
    }

    public boolean equals(Object object) {
        if (!(object instanceof SourceValue)) {
            return false;
        }
        SourceValue sourceValue = (SourceValue)object;
        return this.size == sourceValue.size && this.insns.equals(sourceValue.insns);
    }

    public int hashCode() {
        return this.insns.hashCode();
    }
}

