/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.ASMHelper;

public class ModifyConstantInjector
extends RedirectInjector {
    public ModifyConstantInjector(InjectionInfo injectionInfo) {
        super(injectionInfo, "@ModifyConstant");
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (!this.preInject(injectionNode)) {
            return;
        }
        if (injectionNode.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + this.info);
        }
        if (ASMHelper.isConstant(injectionNode.getCurrentTarget())) {
            this.injectConstantModifier(target, injectionNode);
            return;
        }
        throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting an invalid insn in " + target + " in " + this);
    }

    private void injectConstantModifier(Target target, InjectionNodes.InjectionNode injectionNode) {
        AbstractInsnNode abstractInsnNode = injectionNode.getCurrentTarget();
        Type type = ASMHelper.getConstantType(abstractInsnNode);
        InsnList insnList = new InsnList();
        InsnList insnList2 = new InsnList();
        AbstractInsnNode abstractInsnNode2 = this.invokeConstantHandler(abstractInsnNode, type, target, insnList, insnList2);
        target.wrapNode(abstractInsnNode, abstractInsnNode2, insnList, insnList2);
    }

    private AbstractInsnNode invokeConstantHandler(AbstractInsnNode abstractInsnNode, Type type, Target target, InsnList insnList, InsnList insnList2) {
        String string = ASMHelper.generateDescriptor(type, type);
        boolean bl = this.checkDescriptor(string, target, "getter");
        if (!this.isStatic) {
            insnList.add(new VarInsnNode(25, 0));
            target.addToStack(1);
        }
        if (bl) {
            this.pushArgs(target.arguments, insnList2, target.argIndices, 0, target.arguments.length);
            target.addToStack(ASMHelper.getArgsSize(target.arguments));
        }
        return this.invokeHandler(insnList2);
    }
}

