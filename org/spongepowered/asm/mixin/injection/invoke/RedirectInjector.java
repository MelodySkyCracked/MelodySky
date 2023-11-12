/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.ObjectArrays
 *  com.google.common.primitives.Ints
 */
package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.base.Joiner;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.InvokeInjector;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.ASMHelper;

public class RedirectInjector
extends InvokeInjector {
    private static final String KEY_NOMINATORS = "nominators";
    private static final String KEY_WILD = "wildcard";
    protected Meta meta;
    private Map ctorRedirectors = new HashMap();

    public RedirectInjector(InjectionInfo injectionInfo) {
        this(injectionInfo, "@Redirect");
    }

    protected RedirectInjector(InjectionInfo injectionInfo, String string) {
        super(injectionInfo, string);
        int n = injectionInfo.getContext().getPriority();
        boolean bl = ASMHelper.getVisibleAnnotation(this.methodNode, Final.class) != null;
        this.meta = new Meta(this, n, bl, this.info.toString(), this.methodNode.desc);
    }

    @Override
    protected void checkTarget(Target target) {
    }

    @Override
    protected void addTargetNode(Target target, List list, AbstractInsnNode abstractInsnNode, Set set) {
        Object object;
        InjectionNodes.InjectionNode injectionNode = target.injectionNodes.get(abstractInsnNode);
        ConstructorRedirectData constructorRedirectData = null;
        if (injectionNode != null && (object = (Meta)injectionNode.getDecoration("redirector")) != null && ((Meta)object).getOwner() != this) {
            if (((Meta)object).priority >= this.meta.priority) {
                Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{this.annotationType, this.info, this.meta.priority, ((Meta)object).name, ((Meta)object).priority});
                return;
            }
            if (((Meta)object).isFinal) {
                throw new InvalidInjectionException(this.info, this.annotationType + " conflict: " + this + " failed because target was already remapped by " + ((Meta)object).name);
            }
        }
        for (InjectionPoint injectionPoint : set) {
            if (!(injectionPoint instanceof BeforeNew) || ((BeforeNew)injectionPoint).hasDescriptor()) continue;
            constructorRedirectData = this.getCtorRedirect((BeforeNew)injectionPoint);
        }
        object = target.injectionNodes.add(abstractInsnNode);
        ((InjectionNodes.InjectionNode)object).decorate("redirector", this.meta);
        ((InjectionNodes.InjectionNode)object).decorate(KEY_NOMINATORS, set);
        if (abstractInsnNode instanceof TypeInsnNode && abstractInsnNode.getOpcode() == 187) {
            ((InjectionNodes.InjectionNode)object).decorate(KEY_WILD, constructorRedirectData != null);
            ((InjectionNodes.InjectionNode)object).decorate("ctor", constructorRedirectData);
        }
        list.add(object);
    }

    private ConstructorRedirectData getCtorRedirect(BeforeNew beforeNew) {
        ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData)this.ctorRedirectors.get(beforeNew);
        if (constructorRedirectData == null) {
            constructorRedirectData = new ConstructorRedirectData(this);
            this.ctorRedirectors.put(beforeNew, constructorRedirectData);
        }
        return constructorRedirectData;
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (this != injectionNode) {
            return;
        }
        if (injectionNode.isReplaced()) {
            throw new UnsupportedOperationException("Redirector target failure for " + this.info);
        }
        if (injectionNode.getCurrentTarget() instanceof MethodInsnNode) {
            super.checkTarget(target);
            this.injectAtInvoke(target, injectionNode);
            return;
        }
        if (injectionNode.getCurrentTarget() instanceof FieldInsnNode) {
            super.checkTarget(target);
            this.injectAtFieldAccess(target, injectionNode);
            return;
        }
        if (injectionNode.getCurrentTarget() instanceof TypeInsnNode && injectionNode.getCurrentTarget().getOpcode() == 187) {
            if (!this.isStatic && target.isStatic) {
                throw new InvalidInjectionException(this.info, "non-static callback method " + this + " has a static target which is not supported");
            }
            this.injectAtConstructor(target, injectionNode);
            return;
        }
        throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting an invalid insn in " + target + " in " + this);
    }

    @Override
    protected void postInject(Target target, InjectionNodes.InjectionNode injectionNode) {
        super.postInject(target, injectionNode);
        if (injectionNode.getOriginalTarget() instanceof TypeInsnNode && injectionNode.getOriginalTarget().getOpcode() == 187) {
            ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData)injectionNode.getDecoration("ctor");
            boolean bl = (Boolean)injectionNode.getDecoration(KEY_WILD);
            if (bl && constructorRedirectData.injected == 0) {
                throw new InvalidInjectionException(this.info, this.annotationType + " ctor invocation was not found in " + target);
            }
        }
    }

    @Override
    protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode injectionNode) {
        Object object;
        MethodInsnNode methodInsnNode = (MethodInsnNode)injectionNode.getCurrentTarget();
        boolean bl = methodInsnNode.getOpcode() == 184;
        Type type = Type.getType("L" + methodInsnNode.owner + ";");
        Type type2 = Type.getReturnType(methodInsnNode.desc);
        Object[] objectArray = Type.getArgumentTypes(methodInsnNode.desc);
        Object[] objectArray2 = bl ? objectArray : (Type[])ObjectArrays.concat((Object)type, (Object[])objectArray);
        boolean bl2 = false;
        String string = Injector.printArgs((Type[])objectArray2) + type2;
        if (!string.equals(this.methodNode.desc)) {
            object = Injector.printArgs((Type[])ObjectArrays.concat((Object[])objectArray2, (Object[])target.arguments, Type.class)) + type2;
            if (((String)object).equals(this.methodNode.desc)) {
                bl2 = true;
            } else {
                throw new InvalidInjectionException(this.info, this.annotationType + " handler method " + this + " has an invalid signature, expected " + string + " found " + this.methodNode.desc);
            }
        }
        object = new InsnList();
        int n = ASMHelper.getArgsSize((Type[])objectArray2) + 1;
        int n2 = 1;
        int[] nArray = this.storeArgs(target, (Type[])objectArray2, (InsnList)object, 0);
        if (bl2) {
            int n3 = ASMHelper.getArgsSize(target.arguments);
            n += n3;
            n2 += n3;
            nArray = Ints.concat((int[][])new int[][]{nArray, target.argIndices});
        }
        AbstractInsnNode abstractInsnNode = this.invokeHandlerWithArgs(this.methodArgs, (InsnList)object, nArray);
        target.replaceNode(methodInsnNode, abstractInsnNode, (InsnList)object);
        target.addToLocals(n);
        target.addToStack(n2);
    }

    private void injectAtFieldAccess(Target target, InjectionNodes.InjectionNode injectionNode) {
        FieldInsnNode fieldInsnNode = (FieldInsnNode)injectionNode.getCurrentTarget();
        int n = fieldInsnNode.getOpcode();
        boolean bl = n == 178 || n == 179;
        Type type = Type.getType("L" + fieldInsnNode.owner + ";");
        Type type2 = Type.getType(fieldInsnNode.desc);
        AbstractInsnNode abstractInsnNode = null;
        InsnList insnList = new InsnList();
        if (n == 178 || n == 180) {
            abstractInsnNode = this.injectAtGetField(insnList, target, fieldInsnNode, bl, type, type2);
        } else if (n == 179 || n == 181) {
            abstractInsnNode = this.injectAtPutField(insnList, target, fieldInsnNode, bl, type, type2);
        } else {
            throw new InvalidInjectionException(this.info, "Unspported opcode " + n + " on FieldInsnNode for " + this.info);
        }
        target.replaceNode(fieldInsnNode, abstractInsnNode, insnList);
    }

    private AbstractInsnNode injectAtGetField(InsnList insnList, Target target, FieldInsnNode fieldInsnNode, boolean bl, Type type, Type type2) {
        String string = bl ? ASMHelper.generateDescriptor(type2, new Object[0]) : ASMHelper.generateDescriptor(type2, type);
        boolean bl2 = this.checkDescriptor(string, target, "getter");
        if (!this.isStatic) {
            insnList.add(new VarInsnNode(25, 0));
            if (!bl) {
                insnList.add(new InsnNode(95));
            }
        }
        if (bl2) {
            this.pushArgs(target.arguments, insnList, target.argIndices, 0, target.arguments.length);
            target.addToStack(ASMHelper.getArgsSize(target.arguments));
        }
        target.addToStack(this.isStatic ? 0 : 1);
        return this.invokeHandler(insnList);
    }

    private AbstractInsnNode injectAtPutField(InsnList insnList, Target target, FieldInsnNode fieldInsnNode, boolean bl, Type type, Type type2) {
        String string = bl ? ASMHelper.generateDescriptor(null, type2) : ASMHelper.generateDescriptor(null, type, type2);
        boolean bl2 = this.checkDescriptor(string, target, "setter");
        if (!this.isStatic) {
            if (bl) {
                insnList.add(new VarInsnNode(25, 0));
                insnList.add(new InsnNode(95));
            } else {
                int n = target.allocateLocals(type2.getSize());
                insnList.add(new VarInsnNode(type2.getOpcode(54), n));
                insnList.add(new VarInsnNode(25, 0));
                insnList.add(new InsnNode(95));
                insnList.add(new VarInsnNode(type2.getOpcode(21), n));
            }
        }
        if (bl2) {
            this.pushArgs(target.arguments, insnList, target.argIndices, 0, target.arguments.length);
            target.addToStack(ASMHelper.getArgsSize(target.arguments));
        }
        target.addToStack(!this.isStatic && !bl ? 1 : 0);
        return this.invokeHandler(insnList);
    }

    protected boolean checkDescriptor(String string, Target target, String string2) {
        if (this.methodNode.desc.equals(string)) {
            return false;
        }
        int n = string.indexOf(41);
        String string3 = String.format("%s%s%s", string.substring(0, n), Joiner.on((String)"").join((Object[])target.arguments), string.substring(n));
        if (this.methodNode.desc.equals(string3)) {
            return true;
        }
        throw new InvalidInjectionException(this.info, this.annotationType + " method " + string2 + " " + this + " has an invalid signature. Expected " + string + " but found " + this.methodNode.desc);
    }

    protected void injectAtConstructor(Target target, InjectionNodes.InjectionNode injectionNode) {
        ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData)injectionNode.getDecoration("ctor");
        boolean bl = (Boolean)injectionNode.getDecoration(KEY_WILD);
        TypeInsnNode typeInsnNode = (TypeInsnNode)injectionNode.getCurrentTarget();
        AbstractInsnNode abstractInsnNode = target.get(target.indexOf(typeInsnNode) + 1);
        MethodInsnNode methodInsnNode = this.findInitNode(target, typeInsnNode);
        if (methodInsnNode == null) {
            if (!bl) {
                throw new InvalidInjectionException(this.info, this.annotationType + " ctor invocation was not found in " + target);
            }
            return;
        }
        boolean bl2 = abstractInsnNode.getOpcode() == 89;
        String string = methodInsnNode.desc.replace(")V", ")L" + typeInsnNode.desc + ";");
        boolean bl3 = false;
        try {
            bl3 = this.checkDescriptor(string, target, "constructor");
        }
        catch (InvalidInjectionException invalidInjectionException) {
            if (!bl) {
                throw invalidInjectionException;
            }
            return;
        }
        if (bl2) {
            target.removeNode(abstractInsnNode);
        }
        if (this.isStatic) {
            target.removeNode(typeInsnNode);
        } else {
            target.replaceNode((AbstractInsnNode)typeInsnNode, new VarInsnNode(25, 0));
        }
        InsnList insnList = new InsnList();
        if (bl3) {
            this.pushArgs(target.arguments, insnList, target.argIndices, 0, target.arguments.length);
            target.addToStack(ASMHelper.getArgsSize(target.arguments));
        }
        this.invokeHandler(insnList);
        if (bl2) {
            LabelNode labelNode = new LabelNode();
            insnList.add(new InsnNode(89));
            insnList.add(new JumpInsnNode(199, labelNode));
            this.throwException(insnList, "java/lang/NullPointerException", this.annotationType + " constructor handler " + this + " returned null for " + typeInsnNode.desc.replace('/', '.'));
            insnList.add(labelNode);
            target.addToStack(1);
        } else {
            insnList.add(new InsnNode(87));
        }
        target.replaceNode((AbstractInsnNode)methodInsnNode, insnList);
        ++constructorRedirectData.injected;
    }

    protected MethodInsnNode findInitNode(Target target, TypeInsnNode typeInsnNode) {
        int n = target.indexOf(typeInsnNode);
        ListIterator listIterator = target.insns.iterator(n);
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (!(abstractInsnNode instanceof MethodInsnNode) || abstractInsnNode.getOpcode() != 183) continue;
            MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
            if (!"<init>".equals(methodInsnNode.name) || !methodInsnNode.owner.equals(typeInsnNode.desc)) continue;
            return methodInsnNode;
        }
        return null;
    }

    class ConstructorRedirectData {
        public static final String KEY = "ctor";
        public int injected;
        final RedirectInjector this$0;

        ConstructorRedirectData(RedirectInjector redirectInjector) {
            this.this$0 = redirectInjector;
            this.injected = 0;
        }
    }

    class Meta {
        public static final String KEY = "redirector";
        final int priority;
        final boolean isFinal;
        final String name;
        final String desc;
        final RedirectInjector this$0;

        public Meta(RedirectInjector redirectInjector, int n, boolean bl, String string, String string2) {
            this.this$0 = redirectInjector;
            this.priority = n;
            this.isFinal = bl;
            this.name = string;
            this.desc = string2;
        }

        RedirectInjector getOwner() {
            return this.this$0;
        }
    }
}

