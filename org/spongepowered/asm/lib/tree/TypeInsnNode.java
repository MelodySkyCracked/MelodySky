/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class TypeInsnNode
extends AbstractInsnNode {
    public String desc;

    public TypeInsnNode(int n, String string) {
        super(n);
        this.desc = string;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 3;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitTypeInsn(this.opcode, this.desc);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
    }
}

