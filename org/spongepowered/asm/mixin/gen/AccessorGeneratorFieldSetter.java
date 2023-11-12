/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.gen.AccessorGeneratorField;
import org.spongepowered.asm.mixin.gen.AccessorInfo;

public class AccessorGeneratorFieldSetter
extends AccessorGeneratorField {
    public AccessorGeneratorFieldSetter(AccessorInfo accessorInfo) {
        super(accessorInfo);
    }

    @Override
    public MethodNode generate() {
        int n = this.isInstanceField ? 1 : 0;
        int n2 = n + this.targetType.getSize();
        int n3 = n + this.targetType.getSize();
        MethodNode methodNode = this.createMethod(n2, n3);
        if (this.isInstanceField) {
            methodNode.instructions.add(new VarInsnNode(25, 0));
        }
        methodNode.instructions.add(new VarInsnNode(this.targetType.getOpcode(21), n));
        int n4 = this.isInstanceField ? 181 : 179;
        methodNode.instructions.add(new FieldInsnNode(n4, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        methodNode.instructions.add(new InsnNode(177));
        return methodNode;
    }
}

