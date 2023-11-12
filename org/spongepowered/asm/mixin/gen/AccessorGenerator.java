/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.gen;

import java.util.ArrayList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.gen.AccessorInfo;

public abstract class AccessorGenerator {
    protected final AccessorInfo info;

    public AccessorGenerator(AccessorInfo accessorInfo) {
        this.info = accessorInfo;
    }

    protected final MethodNode createMethod(int n, int n2) {
        MethodNode methodNode = this.info.getMethod();
        MethodNode methodNode2 = new MethodNode(327680, methodNode.access & 0xFFFFFBFF, methodNode.name, methodNode.desc, null, null);
        methodNode2.visibleAnnotations = new ArrayList();
        methodNode2.visibleAnnotations.add(this.info.getAnnotation());
        methodNode2.maxLocals = n;
        methodNode2.maxStack = n2;
        return methodNode2;
    }

    public abstract MethodNode generate();
}

