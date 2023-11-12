/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode(value="FIELD")
public class BeforeFieldAccess
extends BeforeInvoke {
    private final int opcode;

    public BeforeFieldAccess(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.opcode = injectionPointData.getOpcode(-1, 180, 181, 178, 179, -1);
    }

    @Override
    protected boolean matchesInsn(AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode instanceof FieldInsnNode && (((FieldInsnNode)abstractInsnNode).getOpcode() == this.opcode || this.opcode == -1);
    }
}

