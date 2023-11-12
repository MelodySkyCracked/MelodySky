/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class VarInsnNode
extends AbstractInsnNode {
    public int var;

    public VarInsnNode(int n, int n2) {
        super(n);
        this.var = n2;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 2;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(this.opcode, this.var);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new VarInsnNode(this.opcode, this.var).cloneAnnotations(this);
    }
}

