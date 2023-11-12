/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class MultiANewArrayInsnNode
extends AbstractInsnNode {
    public String desc;
    public int dims;

    public MultiANewArrayInsnNode(String string, int n) {
        super(197);
        this.desc = string;
        this.dims = n;
    }

    public int getType() {
        return 13;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitMultiANewArrayInsn(this.desc, this.dims);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
    }
}

