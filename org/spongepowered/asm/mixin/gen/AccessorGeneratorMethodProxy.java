/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.gen.AccessorGenerator;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.util.ASMHelper;

public class AccessorGeneratorMethodProxy
extends AccessorGenerator {
    private final MethodNode targetMethod;
    private final Type[] argTypes;
    private final Type returnType;
    private final boolean isInstanceMethod;

    public AccessorGeneratorMethodProxy(AccessorInfo accessorInfo) {
        super(accessorInfo);
        this.targetMethod = accessorInfo.getTargetMethod();
        this.argTypes = accessorInfo.getArgTypes();
        this.returnType = accessorInfo.getReturnType();
        this.isInstanceMethod = !ASMHelper.hasFlag(this.targetMethod, 8);
    }

    @Override
    public MethodNode generate() {
        int n = ASMHelper.getArgsSize(this.argTypes) + this.returnType.getSize();
        MethodNode methodNode = this.createMethod(n, n);
        if (this.isInstanceMethod) {
            methodNode.instructions.add(new VarInsnNode(25, 0));
        }
        ASMHelper.loadArgs(this.argTypes, methodNode.instructions, this.isInstanceMethod ? 1 : 0);
        boolean bl = ASMHelper.hasFlag(this.targetMethod, 2);
        int n2 = this.isInstanceMethod ? (bl ? 183 : 182) : 184;
        methodNode.instructions.add(new MethodInsnNode(n2, this.info.getClassNode().name, this.targetMethod.name, this.targetMethod.desc, false));
        methodNode.instructions.add(new InsnNode(this.returnType.getOpcode(172)));
        return methodNode;
    }
}

