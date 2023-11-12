/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;

public class JumpInsnNode
extends AbstractInsnNode {
    public LabelNode label;

    public JumpInsnNode(int n, LabelNode labelNode) {
        super(n);
        this.label = labelNode;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 7;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitJumpInsn(this.opcode, this.label.getLabel());
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new JumpInsnNode(this.opcode, JumpInsnNode.clone(this.label, map)).cloneAnnotations(this);
    }
}

