/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class IincInsnNode
extends AbstractInsnNode {
    public int var;
    public int incr;

    public IincInsnNode(int n, int n2) {
        super(132);
        this.var = n;
        this.incr = n2;
    }

    public int getType() {
        return 10;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitIincInsn(this.var, this.incr);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new IincInsnNode(this.var, this.incr).cloneAnnotations(this);
    }
}

