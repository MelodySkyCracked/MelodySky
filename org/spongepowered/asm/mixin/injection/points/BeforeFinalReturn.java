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
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionPoint.AtCode(value="TAIL")
public class BeforeFinalReturn
extends InjectionPoint {
    private final MixinTargetContext mixin;

    public BeforeFinalReturn(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.mixin = injectionPointData.getMixin();
    }

    @Override
    public boolean find(String string, InsnList insnList, Collection collection) {
        AbstractInsnNode abstractInsnNode = null;
        int n = Type.getReturnType(string).getOpcode(172);
        ListIterator listIterator = insnList.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode)listIterator.next();
            if (!(abstractInsnNode2 instanceof InsnNode) || abstractInsnNode2.getOpcode() != n) continue;
            abstractInsnNode = abstractInsnNode2;
        }
        if (abstractInsnNode == null) {
            throw new InvalidInjectionException((IReferenceMapperContext)this.mixin, "TAIL could not locate a valid RETURN in the target method!");
        }
        collection.add(abstractInsnNode);
        return true;
    }
}

