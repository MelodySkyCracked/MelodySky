/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class MethodInsnNode
extends AbstractInsnNode {
    public String owner;
    public String name;
    public String desc;
    public boolean itf;

    @Deprecated
    public MethodInsnNode(int n, String string, String string2, String string3) {
        this(n, string, string2, string3, n == 185);
    }

    public MethodInsnNode(int n, String string, String string2, String string3, boolean bl) {
        super(n);
        this.owner = string;
        this.name = string2;
        this.desc = string3;
        this.itf = bl;
    }

    public void setOpcode(int n) {
        this.opcode = n;
    }

    public int getType() {
        return 5;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitMethodInsn(this.opcode, this.owner, this.name, this.desc, this.itf);
    }

    public AbstractInsnNode clone(Map map) {
        return new MethodInsnNode(this.opcode, this.owner, this.name, this.desc, this.itf);
    }
}

