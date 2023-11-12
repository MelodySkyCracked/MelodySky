/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.invoke;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;

public abstract class InvokeInjector
extends Injector {
    protected final String annotationType;

    public InvokeInjector(InjectionInfo injectionInfo, String string) {
        super(injectionInfo);
        this.annotationType = string;
    }

    @Override
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        this.checkTarget(target);
    }

    protected void checkTarget(Target target) {
        if (target.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
        }
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (!(injectionNode.getCurrentTarget() instanceof MethodInsnNode)) {
            throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting a non-method insn in " + target + " in " + this);
        }
        this.injectAtInvoke(target, injectionNode);
    }

    protected abstract void injectAtInvoke(Target var1, InjectionNodes.InjectionNode var2);

    protected AbstractInsnNode invokeHandlerWithArgs(Type[] typeArray, InsnList insnList, int[] nArray) {
        return this.invokeHandlerWithArgs(typeArray, insnList, nArray, 0, typeArray.length);
    }

    protected AbstractInsnNode invokeHandlerWithArgs(Type[] typeArray, InsnList insnList, int[] nArray, int n, int n2) {
        if (!this.isStatic) {
            insnList.add(new VarInsnNode(25, 0));
        }
        this.pushArgs(typeArray, insnList, nArray, n, n2);
        return this.invokeHandler(insnList);
    }

    protected int[] storeArgs(Target target, Type[] typeArray, InsnList insnList, int n) {
        int[] nArray = target.generateArgMap(typeArray, n);
        this.storeArgs(typeArray, insnList, nArray, n, typeArray.length);
        return nArray;
    }

    protected void storeArgs(Type[] typeArray, InsnList insnList, int[] nArray, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            insnList.add(new VarInsnNode(typeArray[i].getOpcode(54), nArray[i]));
        }
    }

    protected void pushArgs(Type[] typeArray, InsnList insnList, int[] nArray, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            insnList.add(new VarInsnNode(typeArray[i].getOpcode(21), nArray[i]));
        }
    }
}

