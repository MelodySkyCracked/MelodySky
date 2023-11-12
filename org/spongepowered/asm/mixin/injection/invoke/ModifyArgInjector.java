/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.invoke;

import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.InvokeInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;

public class ModifyArgInjector
extends InvokeInjector {
    private final int index;
    private final boolean singleArgMode;

    public ModifyArgInjector(InjectionInfo injectionInfo, int n) {
        super(injectionInfo, "@ModifyArg");
        this.index = n;
        this.singleArgMode = this.methodArgs.length == 1;
    }

    @Override
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        if (this.singleArgMode && !this.methodArgs[0].equals(this.returnType)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type." + " ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
        }
    }

    @Override
    protected void checkTarget(Target target) {
        if (!this.isStatic && target.isStatic) {
            throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
        }
    }

    @Override
    protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode injectionNode) {
        MethodInsnNode methodInsnNode = (MethodInsnNode)injectionNode.getCurrentTarget();
        Type[] typeArray = Type.getArgumentTypes(methodInsnNode.desc);
        int n = this.findArgIndex(target, typeArray);
        InsnList insnList = new InsnList();
        int n2 = 0;
        n2 = this.singleArgMode ? this.injectSingleArgHandler(target, typeArray, n, insnList) : this.injectMultiArgHandler(target, typeArray, n, insnList);
        target.insns.insertBefore((AbstractInsnNode)methodInsnNode, insnList);
        target.addToLocals(n2);
        target.addToStack(2 - (n2 - 1));
    }

    private int injectSingleArgHandler(Target target, Type[] typeArray, int n, InsnList insnList) {
        int[] nArray = this.storeArgs(target, typeArray, insnList, n);
        this.invokeHandlerWithArgs(typeArray, insnList, nArray, n, n + 1);
        this.pushArgs(typeArray, insnList, nArray, n + 1, typeArray.length);
        return nArray[nArray.length - 1] - target.getMaxLocals() + typeArray[typeArray.length - 1].getSize();
    }

    private int injectMultiArgHandler(Target target, Type[] typeArray, int n, InsnList insnList) {
        if (!Arrays.equals(typeArray, this.methodArgs)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + Injector.printArgs(typeArray) + ", expected " + Injector.printArgs(this.methodArgs));
        }
        int[] nArray = this.storeArgs(target, typeArray, insnList, 0);
        this.pushArgs(typeArray, insnList, nArray, 0, n);
        this.invokeHandlerWithArgs(typeArray, insnList, nArray, 0, typeArray.length);
        this.pushArgs(typeArray, insnList, nArray, n + 1, typeArray.length);
        return nArray[nArray.length - 1] - target.getMaxLocals() + typeArray[typeArray.length - 1].getSize();
    }

    protected int findArgIndex(Target target, Type[] typeArray) {
        if (this.index > -1) {
            if (this.index >= typeArray.length || !typeArray[this.index].equals(this.returnType)) {
                throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + Injector.printArgs(typeArray) + ", expected " + this.returnType + " on " + this);
            }
            return this.index;
        }
        int n = -1;
        for (int i = 0; i < typeArray.length; ++i) {
            if (!typeArray[i].equals(this.returnType)) continue;
            if (n != -1) {
                throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + n + ", " + i + "] matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this + ". Please specify index of desired arg.");
            }
            n = i;
        }
        if (n == -1) {
            throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this);
        }
        return n;
    }
}

