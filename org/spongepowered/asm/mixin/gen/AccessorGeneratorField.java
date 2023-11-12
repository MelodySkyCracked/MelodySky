/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.mixin.gen.AccessorGenerator;
import org.spongepowered.asm.mixin.gen.AccessorInfo;

public abstract class AccessorGeneratorField
extends AccessorGenerator {
    protected final FieldNode targetField;
    protected final Type targetType;
    protected final boolean isInstanceField;

    public AccessorGeneratorField(AccessorInfo accessorInfo) {
        super(accessorInfo);
        this.targetField = accessorInfo.getTargetField();
        this.targetType = accessorInfo.getTargetFieldType();
        this.isInstanceField = (this.targetField.access & 8) == 0;
    }
}

