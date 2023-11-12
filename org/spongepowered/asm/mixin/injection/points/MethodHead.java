/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode(value="HEAD")
public class MethodHead
extends InjectionPoint {
    public MethodHead(InjectionPointData injectionPointData) {
        super(injectionPointData);
    }

    @Override
    public boolean find(String string, InsnList insnList, Collection collection) {
        collection.add(insnList.getFirst());
        return true;
    }
}

