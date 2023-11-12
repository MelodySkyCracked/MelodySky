/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class InsnNode
extends AbstractInsnNode {
    public InsnNode(int n) {
        super(n);
    }

    public int getType() {
        return 0;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(this.opcode);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new InsnNode(this.opcode).cloneAnnotations(this);
    }
}

