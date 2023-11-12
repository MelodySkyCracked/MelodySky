/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.InvalidImplicitDiscriminatorException;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.ASMHelper;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyVariableInjector
extends Injector {
    private final boolean print;
    private final LocalVariableDiscriminator discriminator;

    public ModifyVariableInjector(InjectionInfo injectionInfo, boolean bl, LocalVariableDiscriminator localVariableDiscriminator) {
        super(injectionInfo);
        this.print = bl;
        this.discriminator = localVariableDiscriminator;
    }

    @Override
    protected boolean findTargetNodes(MethodNode methodNode, InjectionPoint injectionPoint, InsnList insnList, Collection collection) {
        if (injectionPoint instanceof ContextualInjectionPoint) {
            return ((ContextualInjectionPoint)injectionPoint).find(this.info.getContext().getTargetMethod(methodNode), collection);
        }
        return injectionPoint.find(methodNode.desc, insnList, collection);
    }

    @Override
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        if (target.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this);
        }
        int n = this.discriminator.getOrdinal();
        if (n < -1) {
            throw new InvalidInjectionException(this.info, "Invalid ordinal " + n + " specified in " + this);
        }
        if (this.discriminator.getIndex() == 0 && !this.isStatic) {
            throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
        }
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (injectionNode.isReplaced()) {
            throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
        }
        Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), target, injectionNode.getCurrentTarget());
        if (this.print) {
            this.printLocals(context);
        }
        try {
            int n = this.discriminator.findLocal(context);
            if (n > -1) {
                this.inject(context, n);
            }
        }
        catch (InvalidImplicitDiscriminatorException invalidImplicitDiscriminatorException) {
            throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, (Throwable)invalidImplicitDiscriminatorException);
        }
        target.insns.insertBefore(context.node, context.insns);
        target.addToStack(this.isStatic ? 1 : 2);
    }

    private void printLocals(Context context) {
        SignaturePrinter signaturePrinter = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[]{"var"});
        signaturePrinter.setModifiers(this.methodNode);
        new PrettyPrinter().kvWidth(20).kv("Target Class", this.classNode.name.replace('/', '.')).kv("Target Method", context.target.method.name).kv("Callback Name", this.methodNode.name).kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false)).kv("Instruction", "%s %s", context.node.getClass().getSimpleName(), ASMHelper.getOpcodeName(context.node.getOpcode())).hr().kv("Match mode", this.discriminator.isImplicit(context) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)").kv("Match ordinal", this.discriminator.getOrdinal() < 0 ? "any" : Integer.valueOf(this.discriminator.getOrdinal())).kv("Match index", this.discriminator.getIndex() < context.baseArgIndex ? "any" : Integer.valueOf(this.discriminator.getIndex())).kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any").kv("Args only", this.discriminator.isArgsOnly()).hr().add(context).print(System.err);
    }

    private void inject(Context context, int n) {
        if (!this.isStatic) {
            context.insns.add(new VarInsnNode(25, 0));
        }
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(21), n));
        this.invokeHandler(context.insns);
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(54), n));
    }

    static abstract class ContextualInjectionPoint
    extends InjectionPoint {
        protected final MixinTargetContext mixin;

        ContextualInjectionPoint(MixinTargetContext mixinTargetContext) {
            this.mixin = mixinTargetContext;
        }

        @Override
        public boolean find(String string, InsnList insnList, Collection collection) {
            throw new InvalidInjectionException((IReferenceMapperContext)this.mixin, "STORE injection point must be used in conjunction with @ModifyVariable");
        }

        abstract boolean find(Target var1, Collection var2);
    }

    static class Context
    extends LocalVariableDiscriminator.Context {
        final InsnList insns = new InsnList();

        public Context(Type type, boolean bl, Target target, AbstractInsnNode abstractInsnNode) {
            super(type, bl, target, abstractInsnNode);
        }
    }
}

