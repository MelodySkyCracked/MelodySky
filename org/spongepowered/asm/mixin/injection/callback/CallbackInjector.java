/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 */
package org.spongepowered.asm.mixin.injection.callback;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionNodes;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.ASMHelper;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class CallbackInjector
extends Injector {
    private final boolean cancellable;
    private final LocalCapture localCapture;
    private final String identifier;

    public CallbackInjector(InjectionInfo injectionInfo, boolean bl, LocalCapture localCapture, String string) {
        super(injectionInfo);
        this.cancellable = bl;
        this.localCapture = localCapture;
        this.identifier = string;
    }

    @Override
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        if (ASMHelper.methodIsStatic(target.method) != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
        }
        if ("<init>".equals(target.method.name)) {
            for (InjectionPoint injectionPoint : list) {
                if (injectionPoint.getClass().equals(BeforeReturn.class)) continue;
                throw new InvalidInjectionException(this.info, "Found injection point type " + injectionPoint.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target");
            }
        }
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        LocalVariableNode[] localVariableNodeArray = null;
        if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
            localVariableNodeArray = Locals.getLocalsAt(this.classNode, target.method, injectionNode.getCurrentTarget());
        }
        this.inject(new Callback(this, this.methodNode, target, injectionNode, localVariableNodeArray, this.localCapture.isCaptureLocals()));
    }

    private void inject(Callback callback) {
        if (this.localCapture.isPrintLocals()) {
            this.printLocals(callback);
            this.info.addCallbackInvocation(this.methodNode);
            return;
        }
        MethodNode methodNode = this.methodNode;
        if (!callback.checkDescriptor(this.methodNode.desc)) {
            if (this.info.getTargets().size() > 1) {
                return;
            }
            if (callback.canCaptureLocals) {
                MethodNode methodNode2 = ASMHelper.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
                if (methodNode2 != null && ASMHelper.getVisibleAnnotation(methodNode2, Surrogate.class) != null) {
                    methodNode = methodNode2;
                } else {
                    String string = this.generateBadLVTMessage(callback);
                    switch (this.localCapture) {
                        case CAPTURE_FAILEXCEPTION: {
                            Injector.logger.error("Injection error: {}", new Object[]{string});
                            methodNode = this.generateErrorMethod(callback, "org/spongepowered/asm/mixin/injection/InjectionError", string);
                            break;
                        }
                        case CAPTURE_FAILSOFT: {
                            Injector.logger.warn("Injection warning: {}", new Object[]{string});
                            return;
                        }
                        default: {
                            Injector.logger.error("Critical injection failure: {}", new Object[]{string});
                            throw new InjectionError(string);
                        }
                    }
                }
            } else {
                String string = this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
                if (callback.checkDescriptor(string)) {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
                }
                MethodNode methodNode3 = ASMHelper.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
                if (methodNode3 != null && ASMHelper.getVisibleAnnotation(methodNode3, Surrogate.class) != null) {
                    methodNode = methodNode3;
                } else {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + callback.getDescriptor() + " but found " + this.methodNode.desc);
                }
            }
        }
        this.dupReturnValue(callback);
        if (this.cancellable) {
            this.createCallbackInfo(callback, true);
        }
        this.invokeCallback(callback, methodNode);
        this.injectCancellationCode(callback);
        callback.inject();
        this.info.notifyInjected(callback.target);
    }

    private String generateBadLVTMessage(Callback callback) {
        int n = callback.target.method.instructions.indexOf(callback.node);
        List list = CallbackInjector.summariseLocals(this.methodNode.desc, callback.target.arguments.length + 1);
        List list2 = CallbackInjector.summariseLocals(callback.getDescriptorWithAllLocals(), callback.frameSize);
        String string = String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", callback.target, n, this, list, list2);
        return string;
    }

    private MethodNode generateErrorMethod(Callback callback, String string, String string2) {
        MethodNode methodNode = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", callback.getDescriptor());
        methodNode.maxLocals = ASMHelper.getFirstNonArgLocalIndex(Type.getArgumentTypes(callback.getDescriptor()), !this.isStatic);
        methodNode.maxStack = 3;
        InsnList insnList = methodNode.instructions;
        insnList.add(new TypeInsnNode(187, string));
        insnList.add(new InsnNode(89));
        insnList.add(new LdcInsnNode(string2));
        insnList.add(new MethodInsnNode(183, string, "<init>", "(Ljava/lang/String;)V", false));
        insnList.add(new InsnNode(191));
        return methodNode;
    }

    private void printLocals(Callback callback) {
        Type[] typeArray = Type.getArgumentTypes(callback.getDescriptorWithAllLocals());
        SignaturePrinter signaturePrinter = new SignaturePrinter(callback.target.method, callback.argNames);
        SignaturePrinter signaturePrinter2 = new SignaturePrinter(this.methodNode.name, callback.target.returnType, typeArray, callback.argNames);
        signaturePrinter2.setModifiers(this.methodNode);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.kv("Target Class", this.classNode.name.replace('/', '.'));
        prettyPrinter.kv("Target Method", signaturePrinter);
        prettyPrinter.kv("Target Max LOCALS", callback.target.getMaxLocals());
        prettyPrinter.kv("Initial Frame Size", callback.frameSize);
        prettyPrinter.kv("Callback Name", this.methodNode.name);
        prettyPrinter.kv("Instruction", "%s %s", callback.node.getClass().getSimpleName(), ASMHelper.getOpcodeName(callback.node.getOpcode()));
        prettyPrinter.hr();
        if (callback.locals.length > callback.frameSize) {
            prettyPrinter.add("  %s  %20s  %s", "LOCAL", "TYPE", "NAME");
            for (int i = 0; i < callback.locals.length; ++i) {
                String string;
                String string2 = string = i == callback.frameSize ? ">" : " ";
                if (callback.locals[i] != null) {
                    prettyPrinter.add("%s [%3d]  %20s  %-50s %s", string, i, SignaturePrinter.getTypeName(callback.localTypes[i], false), callback.locals[i].name, i >= callback.frameSize ? "<capture>" : "");
                    continue;
                }
                boolean bl = i > 0 && callback.localTypes[i - 1] != null && callback.localTypes[i - 1].getSize() > 1;
                prettyPrinter.add("%s [%3d]  %20s", string, i, bl ? "<top>" : "-");
            }
            prettyPrinter.hr();
        }
        prettyPrinter.add().add("/**").add(" * Expected callback signature").add(" * /");
        prettyPrinter.add("%s {", signaturePrinter2);
        prettyPrinter.add("    // Method body").add("}").add().print(System.err);
    }

    private void createCallbackInfo(Callback callback, boolean bl) {
        callback.add(new TypeInsnNode(187, callback.target.callbackInfoClass), true, !bl);
        callback.add(new InsnNode(89), true, true);
        this.invokeCallbackInfoCtor(callback, bl);
        if (bl) {
            callback.add(new VarInsnNode(58, callback.marshallVar));
        }
    }

    private void loadOrCreateCallbackInfo(Callback callback) {
        if (this.cancellable) {
            callback.add(new VarInsnNode(25, callback.marshallVar), false, true);
        } else {
            this.createCallbackInfo(callback, false);
        }
    }

    private void dupReturnValue(Callback callback) {
        if (!callback.isAtReturn) {
            return;
        }
        callback.add(new InsnNode(89));
        callback.add(new VarInsnNode(callback.target.returnType.getOpcode(54), callback.marshallVar));
    }

    protected void invokeCallbackInfoCtor(Callback callback, boolean bl) {
        callback.add(new LdcInsnNode(this.getIdentifier(callback)), true, !bl);
        callback.add(new InsnNode(this.cancellable ? 4 : 3), true, !bl);
        if (callback.isAtReturn) {
            callback.add(new VarInsnNode(callback.target.returnType.getOpcode(21), callback.marshallVar), true, !bl);
            callback.add(new MethodInsnNode(183, callback.target.callbackInfoClass, "<init>", CallbackInfo.getConstructorDescriptor(callback.target.returnType), false));
        } else {
            callback.add(new MethodInsnNode(183, callback.target.callbackInfoClass, "<init>", CallbackInfo.getConstructorDescriptor(), false));
        }
    }

    private void invokeCallback(Callback callback, MethodNode methodNode) {
        if (!this.isStatic) {
            callback.add(new VarInsnNode(25, 0), false, true);
        }
        if (callback.captureArgs()) {
            ASMHelper.loadArgs(callback.target.arguments, callback, this.isStatic ? 0 : 1);
        }
        this.loadOrCreateCallbackInfo(callback);
        if (callback.canCaptureLocals) {
            Locals.loadLocals(callback.localTypes, callback, callback.frameSize, callback.extraArgs);
        }
        this.invokeHandler(callback, methodNode);
    }

    private String getIdentifier(Callback callback) {
        return Strings.isNullOrEmpty((String)this.identifier) ? callback.target.method.name : this.identifier;
    }

    protected void injectCancellationCode(Callback callback) {
        if (!this.cancellable) {
            return;
        }
        callback.add(new VarInsnNode(25, callback.marshallVar));
        callback.add(new MethodInsnNode(182, callback.target.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), CallbackInfo.getIsCancelledMethodSig(), false));
        LabelNode labelNode = new LabelNode();
        callback.add(new JumpInsnNode(153, labelNode));
        this.injectReturnCode(callback);
        callback.add(labelNode);
    }

    protected void injectReturnCode(Callback callback) {
        if (callback.target.returnType.equals(Type.VOID_TYPE)) {
            callback.add(new InsnNode(177));
        } else {
            callback.add(new VarInsnNode(25, callback.marshallVar));
            String string = CallbackInfoReturnable.getReturnAccessor(callback.target.returnType);
            String string2 = CallbackInfoReturnable.getReturnDescriptor(callback.target.returnType);
            callback.add(new MethodInsnNode(182, callback.target.callbackInfoClass, string, string2, false));
            if (callback.target.returnType.getSort() == 10) {
                callback.add(new TypeInsnNode(192, callback.target.returnType.getInternalName()));
            }
            callback.add(new InsnNode(callback.target.returnType.getOpcode(172)));
        }
    }

    protected boolean isStatic() {
        return this.isStatic;
    }

    private static List summariseLocals(String string, int n) {
        return CallbackInjector.summariseLocals(Type.getArgumentTypes(string), n);
    }

    private static List summariseLocals(Type[] typeArray, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (typeArray != null) {
            while (n < typeArray.length) {
                if (typeArray[n] != null) {
                    arrayList.add(typeArray[n].toString());
                }
                ++n;
            }
        }
        return arrayList;
    }

    private class Callback
    extends InsnList {
        private final MethodNode handler;
        final Target target;
        final AbstractInsnNode node;
        final LocalVariableNode[] locals;
        final Type[] localTypes;
        final int frameSize;
        final int extraArgs;
        final boolean canCaptureLocals;
        final boolean isAtReturn;
        final String desc;
        final String descl;
        final String[] argNames;
        int ctor;
        int invoke;
        final int marshallVar;
        private boolean captureArgs;
        final CallbackInjector this$0;

        Callback(CallbackInjector callbackInjector, MethodNode methodNode, Target target, InjectionNodes.InjectionNode injectionNode, LocalVariableNode[] localVariableNodeArray, boolean bl) {
            this.this$0 = callbackInjector;
            this.captureArgs = true;
            this.handler = methodNode;
            this.target = target;
            this.node = injectionNode.getCurrentTarget();
            this.locals = localVariableNodeArray;
            this.localTypes = localVariableNodeArray != null ? new Type[localVariableNodeArray.length] : null;
            this.frameSize = ASMHelper.getFirstNonArgLocalIndex(target.arguments, !callbackInjector.isStatic());
            ArrayList<String> arrayList = null;
            if (localVariableNodeArray != null) {
                int n = callbackInjector.isStatic() ? 0 : 1;
                arrayList = new ArrayList<String>();
                for (int i = 0; i <= localVariableNodeArray.length; ++i) {
                    if (i == this.frameSize) {
                        arrayList.add(target.returnType == Type.VOID_TYPE ? "ci" : "cir");
                    }
                    if (i >= localVariableNodeArray.length || localVariableNodeArray[i] == null) continue;
                    this.localTypes[i] = Type.getType(localVariableNodeArray[i].desc);
                    if (i < n) continue;
                    arrayList.add(localVariableNodeArray[i].name);
                }
            }
            this.extraArgs = Math.max(0, ASMHelper.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
            this.argNames = arrayList != null ? arrayList.toArray(new String[arrayList.size()]) : null;
            this.canCaptureLocals = bl && localVariableNodeArray != null && localVariableNodeArray.length > this.frameSize;
            this.isAtReturn = this.node instanceof InsnNode && this >= this.node.getOpcode();
            this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
            this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
            this.invoke = target.arguments.length + (this.canCaptureLocals ? this.localTypes.length - this.frameSize : 0);
            this.marshallVar = target.allocateLocal();
        }

        String getDescriptor() {
            return this.canCaptureLocals ? this.descl : this.desc;
        }

        String getDescriptorWithAllLocals() {
            return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, Short.MAX_VALUE);
        }

        void add(AbstractInsnNode abstractInsnNode, boolean bl, boolean bl2) {
            this.add(abstractInsnNode);
            this.ctor += bl ? 1 : 0;
            this.invoke += bl2 ? 1 : 0;
        }

        void inject() {
            this.target.insns.insertBefore(this.node, this);
            this.target.addToStack(Math.max(this.invoke, this.ctor));
        }

        boolean checkDescriptor(String string) {
            if (this.getDescriptor().equals(string)) {
                return true;
            }
            if (this.target.getSimpleCallbackDescriptor().equals(string) && !this.canCaptureLocals) {
                this.captureArgs = false;
                return true;
            }
            if (this.extraArgs > 0) {
                Type[] typeArray;
                Type[] typeArray2 = Type.getArgumentTypes(string);
                if (typeArray2.length != (typeArray = Type.getArgumentTypes(this.descl)).length) {
                    return false;
                }
                for (int i = this.frameSize + 1; i < typeArray.length; ++i) {
                    Type type = typeArray2[i];
                    if (type.equals(typeArray[i])) continue;
                    if (type.getSort() >= 9) {
                        return false;
                    }
                    AnnotationNode annotationNode = ASMHelper.getInvisibleParameterAnnotation(this.handler, Coerce.class, i);
                    if (annotationNode == null) {
                        return false;
                    }
                    boolean bl = Injector.canCoerce(typeArray2[i], typeArray[i]);
                    if (bl) continue;
                    return false;
                }
                return true;
            }
            return false;
        }

        boolean captureArgs() {
            return this.captureArgs;
        }
    }
}

