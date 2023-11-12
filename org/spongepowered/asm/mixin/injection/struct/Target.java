/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.struct;

import java.util.Iterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.util.ASMHelper;

public class Target
implements Comparable,
Iterable {
    public final ClassNode classNode;
    public final MethodNode method;
    public final InsnList insns;
    public final boolean isStatic;
    public final Type[] arguments;
    public final int[] argIndices;
    public final Type returnType;
    public final String callbackDescriptor;
    public final String callbackInfoClass;
    public final InjectionNodes injectionNodes = new InjectionNodes();
    private final int maxStack;
    private final int maxLocals;

    public Target(ClassNode classNode, MethodNode methodNode) {
        this.classNode = classNode;
        this.method = methodNode;
        this.insns = methodNode.instructions;
        this.isStatic = ASMHelper.methodIsStatic(methodNode);
        this.arguments = Type.getArgumentTypes(methodNode.desc);
        this.argIndices = this.calcArgIndices(this.isStatic ? 0 : 1);
        this.returnType = Type.getReturnType(methodNode.desc);
        this.maxStack = methodNode.maxStack;
        this.maxLocals = methodNode.maxLocals;
        this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
        this.callbackDescriptor = String.format("(%sL%s;)V", methodNode.desc.substring(1, methodNode.desc.indexOf(41)), this.callbackInfoClass);
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public int getCurrentMaxLocals() {
        return this.method.maxLocals;
    }

    public int getCurrentMaxStack() {
        return this.method.maxStack;
    }

    public int allocateLocal() {
        return this.allocateLocals(1);
    }

    public int allocateLocals(int n) {
        int n2 = this.method.maxLocals;
        this.method.maxLocals += n;
        return n2;
    }

    public void addToLocals(int n) {
        this.setMaxLocals(this.maxLocals + n);
    }

    public void setMaxLocals(int n) {
        if (n > this.method.maxLocals) {
            this.method.maxLocals = n;
        }
    }

    public void addToStack(int n) {
        this.setMaxStack(this.maxStack + n);
    }

    public void setMaxStack(int n) {
        if (n > this.method.maxStack) {
            this.method.maxStack = n;
        }
    }

    public int[] generateArgMap(Type[] typeArray, int n) {
        int n2 = this.maxLocals;
        int[] nArray = new int[typeArray.length];
        for (int i = n; i < typeArray.length; ++i) {
            nArray[i] = n2;
            n2 += typeArray[i].getSize();
        }
        return nArray;
    }

    private int[] calcArgIndices(int n) {
        int[] nArray = new int[this.arguments.length];
        for (int i = 0; i < this.arguments.length; ++i) {
            nArray[i] = n;
            n += this.arguments[i].getSize();
        }
        return nArray;
    }

    public String getSimpleCallbackDescriptor() {
        return String.format("(L%s;)V", this.callbackInfoClass);
    }

    public String getCallbackDescriptor(Type[] typeArray, Type[] typeArray2) {
        return this.getCallbackDescriptor(false, typeArray, typeArray2, 0, Short.MAX_VALUE);
    }

    public String getCallbackDescriptor(boolean bl, Type[] typeArray, Type[] typeArray2, int n, int n2) {
        if (!bl || typeArray == null) {
            return this.callbackDescriptor;
        }
        String string = this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(41));
        for (int i = n; i < typeArray.length && n2 > 0; ++i) {
            if (typeArray[i] == null) continue;
            string = string + typeArray[i].getDescriptor();
            --n2;
        }
        return string + ")V";
    }

    public String toString() {
        return String.format("%s::%s%s", this.classNode.name, this.method.name, this.method.desc);
    }

    public int compareTo(Target target) {
        if (target == null) {
            return Integer.MAX_VALUE;
        }
        return this.toString().compareTo(target.toString());
    }

    public int indexOf(AbstractInsnNode abstractInsnNode) {
        return this.insns.indexOf(abstractInsnNode);
    }

    public AbstractInsnNode get(int n) {
        return this.insns.get(n);
    }

    public Iterator iterator() {
        return this.insns.iterator();
    }

    public void replaceNode(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        this.insns.insertBefore(abstractInsnNode, abstractInsnNode2);
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }

    public void replaceNode(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2, InsnList insnList) {
        this.insns.insertBefore(abstractInsnNode, insnList);
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }

    public void wrapNode(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2, InsnList insnList, InsnList insnList2) {
        this.insns.insertBefore(abstractInsnNode, insnList);
        this.insns.insert(abstractInsnNode, insnList2);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }

    public void replaceNode(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        this.insns.insertBefore(abstractInsnNode, insnList);
        this.removeNode(abstractInsnNode);
    }

    public void removeNode(AbstractInsnNode abstractInsnNode) {
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.remove(abstractInsnNode);
    }

    public int compareTo(Object object) {
        return this.compareTo((Target)object);
    }
}

