/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class InvokeDynamicInsnNode
extends AbstractInsnNode {
    public String name;
    public String desc;
    public Handle bsm;
    public Object[] bsmArgs;

    public InvokeDynamicInsnNode(String string, String string2, Handle handle, Object ... objectArray) {
        super(186);
        this.name = string;
        this.desc = string2;
        this.bsm = handle;
        this.bsmArgs = objectArray;
    }

    public int getType() {
        return 6;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitInvokeDynamicInsn(this.name, this.desc, this.bsm, this.bsmArgs);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        return new InvokeDynamicInsnNode(this.name, this.desc, this.bsm, this.bsmArgs).cloneAnnotations(this);
    }
}

