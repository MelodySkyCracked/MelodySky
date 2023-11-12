/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.ASMHelper;

public abstract class Injector {
    protected static final Logger logger = LogManager.getLogger((String)"mixin");
    protected InjectionInfo info;
    protected final ClassNode classNode;
    protected final MethodNode methodNode;
    protected final Type[] methodArgs;
    protected final Type returnType;
    protected final boolean isStatic;

    public Injector(InjectionInfo injectionInfo) {
        this(injectionInfo.getClassNode(), injectionInfo.getMethod());
        this.info = injectionInfo;
    }

    private Injector(ClassNode classNode, MethodNode methodNode) {
        this.classNode = classNode;
        this.methodNode = methodNode;
        this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
        this.returnType = Type.getReturnType(this.methodNode.desc);
        this.isStatic = ASMHelper.methodIsStatic(this.methodNode);
    }

    public String toString() {
        return String.format("%s::%s", this.classNode.name, this.methodNode.name);
    }

    public final List find(InjectorTarget injectorTarget, List list) {
        this.sanityCheck(injectorTarget.getTarget(), list);
        ArrayList arrayList = new ArrayList();
        for (TargetNode targetNode : this.findTargetNodes(injectorTarget, list)) {
            this.addTargetNode(injectorTarget.getTarget(), arrayList, targetNode.node, targetNode.nominators);
        }
        return arrayList;
    }

    protected void addTargetNode(Target target, List list, AbstractInsnNode abstractInsnNode, Set set) {
        list.add(target.injectionNodes.add(abstractInsnNode));
    }

    public final void inject(Target target, List list) {
        for (InjectionNodes.InjectionNode injectionNode : list) {
            if (injectionNode.isRemoved()) {
                if (!this.info.getContext().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) continue;
                logger.warn("Target node for {} was removed by a previous injector in {}", new Object[]{this.info, target});
                continue;
            }
            this.inject(target, injectionNode);
        }
        for (InjectionNodes.InjectionNode injectionNode : list) {
            this.postInject(target, injectionNode);
        }
    }

    private Collection findTargetNodes(InjectorTarget injectorTarget, List list) {
        HashMap<AbstractInsnNode, TargetNode> hashMap = new HashMap<AbstractInsnNode, TargetNode>();
        ArrayList arrayList = new ArrayList(32);
        for (InjectionPoint injectionPoint : list) {
            arrayList.clear();
            if (!this.findTargetNodes(injectorTarget.getMethod(), injectionPoint, injectorTarget.getSlice(injectionPoint), arrayList)) continue;
            for (AbstractInsnNode abstractInsnNode : arrayList) {
                TargetNode targetNode = (TargetNode)hashMap.get(abstractInsnNode);
                if (targetNode == null) {
                    targetNode = new TargetNode(this, abstractInsnNode);
                    hashMap.put(abstractInsnNode, targetNode);
                }
                targetNode.nominators.add(injectionPoint);
            }
        }
        return hashMap.values();
    }

    protected boolean findTargetNodes(MethodNode methodNode, InjectionPoint injectionPoint, InsnList insnList, Collection collection) {
        return injectionPoint.find(methodNode.desc, insnList, collection);
    }

    protected void sanityCheck(Target target, List list) {
        if (target.classNode != this.classNode) {
            throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
        }
    }

    protected abstract void inject(Target var1, InjectionNodes.InjectionNode var2);

    protected void postInject(Target target, InjectionNodes.InjectionNode injectionNode) {
    }

    protected AbstractInsnNode invokeHandler(InsnList insnList) {
        return this.invokeHandler(insnList, this.methodNode);
    }

    protected AbstractInsnNode invokeHandler(InsnList insnList, MethodNode methodNode) {
        boolean bl;
        boolean bl2 = bl = (methodNode.access & 2) != 0;
        int n = this.isStatic ? 184 : (bl ? 183 : 182);
        MethodInsnNode methodInsnNode = new MethodInsnNode(n, this.classNode.name, methodNode.name, methodNode.desc, false);
        insnList.add(methodInsnNode);
        this.info.addCallbackInvocation(methodNode);
        return methodInsnNode;
    }

    protected void throwException(InsnList insnList, String string, String string2) {
        insnList.add(new TypeInsnNode(187, string));
        insnList.add(new InsnNode(89));
        insnList.add(new LdcInsnNode(string2));
        insnList.add(new MethodInsnNode(183, string, "<init>", "(Ljava/lang/String;)V", false));
        insnList.add(new InsnNode(191));
    }

    protected static String printArgs(Type[] typeArray) {
        return "(" + Joiner.on((String)"").join((Object[])typeArray) + ")";
    }

    public static boolean canCoerce(Type type, Type type2) {
        return Injector.canCoerce(type.getDescriptor(), type2.getDescriptor());
    }

    public static boolean canCoerce(String string, String string2) {
        if (string.length() > 1 || string2.length() > 1) {
            return false;
        }
        return Injector.canCoerce(string.charAt(0), string2.charAt(0));
    }

    public static boolean canCoerce(char c, char c2) {
        return c2 == 'I' && "IBSCZ".indexOf(c) > -1;
    }

    class TargetNode {
        final AbstractInsnNode node;
        final Set nominators;
        final Injector this$0;

        TargetNode(Injector injector, AbstractInsnNode abstractInsnNode) {
            this.this$0 = injector;
            this.nominators = new HashSet();
            this.node = abstractInsnNode;
        }
    }
}

