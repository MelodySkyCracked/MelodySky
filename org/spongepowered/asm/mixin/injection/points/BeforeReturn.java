/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode(value="RETURN")
public class BeforeReturn
extends InjectionPoint {
    private final int ordinal;

    public BeforeReturn(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.ordinal = injectionPointData.getOrdinal();
    }

    @Override
    public boolean find(String string, InsnList insnList, Collection collection) {
        boolean bl = false;
        int n = Type.getReturnType(string).getOpcode(172);
        int n2 = 0;
        ListIterator listIterator = insnList.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (!(abstractInsnNode instanceof InsnNode) || abstractInsnNode.getOpcode() != n) continue;
            if (this.ordinal == -1 || this.ordinal == n2) {
                collection.add(abstractInsnNode);
                bl = true;
            }
            ++n2;
        }
        return bl;
    }
}

