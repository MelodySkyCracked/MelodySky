/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;

public class InjectorTarget {
    private final InjectionInfo info;
    private final Map cache = new HashMap();
    private final Target target;

    public InjectorTarget(InjectionInfo injectionInfo, Target target) {
        this.info = injectionInfo;
        this.target = target;
    }

    public Target getTarget() {
        return this.target;
    }

    public MethodNode getMethod() {
        return this.target.method;
    }

    public InsnList getSlice(String string) {
        ReadOnlyInsnList readOnlyInsnList = (ReadOnlyInsnList)this.cache.get(string);
        if (readOnlyInsnList == null) {
            MethodSlice methodSlice = this.info.getSlice(string);
            readOnlyInsnList = methodSlice != null ? methodSlice.getSlice(this.target.method) : new ReadOnlyInsnList(this.target.method.instructions);
            this.cache.put(string, readOnlyInsnList);
        }
        return readOnlyInsnList;
    }

    public InsnList getSlice(InjectionPoint injectionPoint) {
        return this.getSlice(injectionPoint.getSlice());
    }

    public void dispose() {
        for (ReadOnlyInsnList readOnlyInsnList : this.cache.values()) {
            readOnlyInsnList.dispose();
        }
        this.cache.clear();
    }
}

