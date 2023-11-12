/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.struct.CallbackInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.struct.ModifyArgInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.ModifyConstantInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.ModifyVariableInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.RedirectInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.ASMHelper;

public abstract class InjectionInfo
extends SpecialMethodInfo {
    protected final boolean isStatic;
    protected final Deque targets = new ArrayDeque();
    protected final MethodSlices slices;
    protected final List injectionPoints = new ArrayList();
    protected final Map targetNodes = new LinkedHashMap();
    protected Injector injector;
    protected InjectorGroupInfo group;
    private final List injectedMethods = new ArrayList(0);
    private int expectedCallbackCount = 1;
    private int requiredCallbackCount = 0;
    private int injectedCallbackCount = 0;

    protected InjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
        this.isStatic = ASMHelper.methodIsStatic(methodNode);
        this.slices = MethodSlices.parse(this);
        this.readAnnotation();
    }

    protected void readAnnotation() {
        if (this.annotation == null) {
            return;
        }
        String string = "@" + ASMHelper.getSimpleName(this.annotation);
        List list = this.readInjectionPoints(string);
        this.findMethods(this.parseTarget(string), string);
        this.parseInjectionPoints(list);
        this.parseRequirements();
        this.injector = this.parseInjector(this.annotation);
    }

    protected MemberInfo parseTarget(String string) {
        String string2 = (String)ASMHelper.getAnnotationValue(this.annotation, "method");
        if (string2 == null) {
            throw new InvalidInjectionException(this, string + " annotation on " + this.method.name + " is missing method name");
        }
        try {
            MemberInfo memberInfo = MemberInfo.parseAndValidate(string2, this.mixin);
            if (memberInfo.owner != null && !memberInfo.owner.equals(this.mixin.getTargetClassRef())) {
                throw new InvalidInjectionException(this, string + " annotation on " + this.method.name + " specifies a target class '" + memberInfo.owner + "', which is not supported");
            }
            return memberInfo;
        }
        catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
            throw new InvalidInjectionException(this, string + " annotation on " + this.method.name + ", has invalid target descriptor: \"" + string2 + "\"");
        }
    }

    protected List readInjectionPoints(String string) {
        Object object = ASMHelper.getAnnotationValue(this.annotation, "at");
        if (object instanceof List) {
            return (List)object;
        }
        if (object instanceof AnnotationNode) {
            ArrayList<AnnotationNode> arrayList = new ArrayList<AnnotationNode>();
            arrayList.add((AnnotationNode)object);
            return arrayList;
        }
        throw new InvalidInjectionException(this, string + " annotation on " + this.method.name + " is missing 'at' value(s)");
    }

    protected void parseInjectionPoints(List list) {
        this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, list));
    }

    protected void parseRequirements() {
        Integer n;
        this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
        Integer n2 = (Integer)ASMHelper.getAnnotationValue(this.annotation, "expect");
        if (n2 != null) {
            this.expectedCallbackCount = n2;
        }
        if ((n = (Integer)ASMHelper.getAnnotationValue(this.annotation, "require")) != null && n > -1) {
            this.requiredCallbackCount = n;
        } else if (this.group.isDefault()) {
            this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
        }
    }

    protected abstract Injector parseInjector(AnnotationNode var1);

    public boolean isValid() {
        return this.targets.size() > 0 && this.injectionPoints.size() > 0;
    }

    public void prepare() {
        this.targetNodes.clear();
        for (MethodNode methodNode : this.targets) {
            Target target = this.mixin.getTargetMethod(methodNode);
            InjectorTarget injectorTarget = new InjectorTarget(this, target);
            this.targetNodes.put(target, this.injector.find(injectorTarget, this.injectionPoints));
            injectorTarget.dispose();
        }
    }

    public void inject() {
        for (Map.Entry entry : this.targetNodes.entrySet()) {
            this.injector.inject((Target)entry.getKey(), (List)entry.getValue());
        }
        this.targets.clear();
    }

    public void postInject() {
        for (MethodNode methodNode : this.injectedMethods) {
            this.classNode.methods.add(methodNode);
        }
        if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded", this.getDescription(), this.method.name, this.method.desc, this.mixin, this.expectedCallbackCount, this.injectedCallbackCount));
        }
        if (this.injectedCallbackCount < this.requiredCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded", this.getDescription(), this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.requiredCallbackCount));
        }
    }

    public void notifyInjected(Target target) {
    }

    protected String getDescription() {
        return "Callback method";
    }

    public String toString() {
        return InjectionInfo.describeInjector(this.mixin, this.annotation, this.method);
    }

    public Collection getTargets() {
        return this.targets;
    }

    public MethodSlice getSlice(String string) {
        return this.slices.get(this.getSliceId(string));
    }

    public String getSliceId(String string) {
        return "";
    }

    public int getInjectedCallbackCount() {
        return this.injectedCallbackCount;
    }

    public MethodNode addMethod(int n, String string, String string2) {
        MethodNode methodNode = new MethodNode(327680, n | 0x1000, string, string2, null, null);
        this.injectedMethods.add(methodNode);
        return methodNode;
    }

    public void addCallbackInvocation(MethodNode methodNode) {
        ++this.injectedCallbackCount;
    }

    private void findMethods(MemberInfo memberInfo, String string) {
        this.targets.clear();
        int n = 0;
        for (MethodNode methodNode : this.classNode.methods) {
            boolean bl;
            if (!memberInfo.matches(methodNode.name, methodNode.desc, n)) continue;
            boolean bl2 = bl = ASMHelper.getVisibleAnnotation(methodNode, MixinMerged.class) != null;
            if (memberInfo.matchAll && (ASMHelper.methodIsStatic(methodNode) != this.isStatic || methodNode == this.method || bl)) continue;
            this.checkTarget(methodNode);
            this.targets.add(methodNode);
            ++n;
        }
        if (this.targets.size() == 0) {
            throw new InvalidInjectionException(this, string + " annotation on " + this.method.name + " could not find '" + memberInfo.name + "'");
        }
    }

    private void checkTarget(MethodNode methodNode) {
        AnnotationNode annotationNode = ASMHelper.getVisibleAnnotation(methodNode, MixinMerged.class);
        if (annotationNode == null) {
            return;
        }
        String string = (String)ASMHelper.getAnnotationValue(annotationNode, "mixin");
        int n = (Integer)ASMHelper.getAnnotationValue(annotationNode, "priority");
        if (n >= this.mixin.getPriority() && !string.equals(this.mixin.getClassName())) {
            throw new InvalidInjectionException(this, this + " cannot inject into " + this.classNode.name + "::" + methodNode.name + methodNode.desc + " merged by " + string + " with priority " + n);
        }
        if (ASMHelper.getVisibleAnnotation(methodNode, Final.class) != null) {
            throw new InvalidInjectionException(this, this + " cannot inject into @Final method " + this.classNode.name + "::" + methodNode.name + methodNode.desc + " merged by " + string);
        }
    }

    public static InjectionInfo parse(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        AnnotationNode annotationNode = InjectionInfo.getInjectorAnnotation(mixinTargetContext.getMixin(), methodNode);
        if (annotationNode == null) {
            return null;
        }
        if (annotationNode.desc.endsWith(Inject.class.getSimpleName() + ";")) {
            return new CallbackInjectionInfo(mixinTargetContext, methodNode, annotationNode);
        }
        if (annotationNode.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return new ModifyArgInjectionInfo(mixinTargetContext, methodNode, annotationNode);
        }
        if (annotationNode.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return new RedirectInjectionInfo(mixinTargetContext, methodNode, annotationNode);
        }
        if (annotationNode.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return new ModifyVariableInjectionInfo(mixinTargetContext, methodNode, annotationNode);
        }
        if (annotationNode.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return new ModifyConstantInjectionInfo(mixinTargetContext, methodNode, annotationNode);
        }
        return null;
    }

    public static AnnotationNode getInjectorAnnotation(IMixinInfo iMixinInfo, MethodNode methodNode) {
        AnnotationNode annotationNode = null;
        try {
            annotationNode = ASMHelper.getSingleVisibleAnnotation(methodNode, Inject.class, ModifyArg.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidMixinException(iMixinInfo, "Error parsing annotations on " + methodNode.name + " in " + iMixinInfo.getClassName() + ": " + illegalArgumentException.getMessage());
        }
        return annotationNode;
    }

    public static String getInjectorPrefix(AnnotationNode annotationNode) {
        if (annotationNode != null) {
            if (annotationNode.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
                return "modify";
            }
            if (annotationNode.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
                return "redirect";
            }
            if (annotationNode.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
                return "localvar";
            }
            if (annotationNode.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
                return "constant";
            }
        }
        return "handler";
    }

    static String describeInjector(MixinTargetContext mixinTargetContext, AnnotationNode annotationNode, MethodNode methodNode) {
        return String.format("%s->@%s::%s%s", mixinTargetContext.toString(), ASMHelper.getSimpleName(annotationNode), methodNode.name, methodNode.desc);
    }
}

