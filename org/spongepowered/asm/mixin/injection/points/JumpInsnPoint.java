/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode(value="JUMP")
public class JumpInsnPoint
extends InjectionPoint {
    private final int opCode;
    private final int ordinal;

    public JumpInsnPoint(InjectionPointData injectionPointData) {
        this.opCode = injectionPointData.getOpcode(-1, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 198, 199, -1);
        this.ordinal = injectionPointData.getOrdinal();
    }

    @Override
    public boolean find(String string, InsnList insnList, Collection collection) {
        boolean bl = false;
        int n = 0;
        ListIterator listIterator = insnList.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (!(abstractInsnNode instanceof JumpInsnNode) || this.opCode != -1 && abstractInsnNode.getOpcode() != this.opCode) continue;
            if (this.ordinal == -1 || this.ordinal == n) {
                collection.add(abstractInsnNode);
                bl = true;
            }
            ++n;
        }
        return bl;
    }
}

