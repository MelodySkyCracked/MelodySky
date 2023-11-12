/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class IntInsnNode
extends AbstractInsnNode {
    public int operand;

    public IntInsnNode(int n, int n2) {
        super(n);
        this.operand = n2;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 1;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitIntInsn(this.opcode, this.operand);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new IntInsnNode(this.opcode, this.operand).cloneAnnotations(this);
    }
}

