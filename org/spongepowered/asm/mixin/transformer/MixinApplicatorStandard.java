/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.TargetClassContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.ASMHelper;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

class MixinApplicatorStandard {
    protected static final List CONSTRAINED_ANNOTATIONS = ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
    protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[]{177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86};
    protected final Logger logger = LogManager.getLogger((String)"mixin");
    protected final TargetClassContext context;
    protected final String targetName;
    protected final ClassNode targetClass;

    MixinApplicatorStandard(TargetClassContext targetClassContext) {
        this.context = targetClassContext;
        this.targetName = targetClassContext.getClassName();
        this.targetClass = targetClassContext.getClassNode();
    }

    void apply(SortedSet sortedSet) {
        ArrayList<MixinTargetContext> arrayList = new ArrayList<MixinTargetContext>();
        for (Object object : sortedSet) {
            this.logger.log(((MixinInfo)object).getLoggingLevel(), "Mixing {} from {} into {}", new Object[]{((MixinInfo)object).getName(), ((MixinInfo)object).getParent(), this.targetName});
            arrayList.add(((MixinInfo)object).createContextFor(this.context));
        }
        Object object = null;
        try {
            for (MixinTargetContext mixinTargetContext : arrayList) {
                object = mixinTargetContext;
                ((MixinTargetContext)object).preApply(this.targetName, this.targetClass);
            }
            for (ApplicatorPass applicatorPass : ApplicatorPass.values()) {
                for (MixinTargetContext mixinTargetContext : arrayList) {
                    object = mixinTargetContext;
                    this.applyMixin((MixinTargetContext)object, applicatorPass);
                }
            }
            for (MixinTargetContext mixinTargetContext : arrayList) {
                object = mixinTargetContext;
                ((MixinTargetContext)object).postApply(this.targetName, this.targetClass);
            }
        }
        catch (InvalidMixinException invalidMixinException) {
            throw invalidMixinException;
        }
        catch (Exception exception) {
            throw new InvalidMixinException((IReferenceMapperContext)object, "Unexpecteded " + exception.getClass().getSimpleName() + " whilst applying the mixin class: " + exception.getMessage(), (Throwable)exception);
        }
        this.applySourceMap(this.context);
        this.context.processDebugTasks();
    }

    protected final void applyMixin(MixinTargetContext mixinTargetContext, ApplicatorPass applicatorPass) {
        switch (applicatorPass) {
            case MAIN: {
                this.applyInterfaces(mixinTargetContext);
                this.applyAttributes(mixinTargetContext);
                this.applyAnnotations(mixinTargetContext);
                this.applyFields(mixinTargetContext);
                this.applyMethods(mixinTargetContext);
                this.applyInitialisers(mixinTargetContext);
                break;
            }
            case PREINJECT: {
                this.prepareInjections(mixinTargetContext);
                break;
            }
            case INJECT: {
                this.applyAccessors(mixinTargetContext);
                this.applyInjections(mixinTargetContext);
                break;
            }
            default: {
                throw new IllegalStateException("Invalid pass specified " + (Object)((Object)applicatorPass));
            }
        }
    }

    protected void applyInterfaces(MixinTargetContext mixinTargetContext) {
        for (String string : mixinTargetContext.getInterfaces()) {
            if (this.targetClass.interfaces.contains(string)) continue;
            this.targetClass.interfaces.add(string);
            mixinTargetContext.getTargetClassInfo().addInterface(string);
        }
    }

    protected void applyAttributes(MixinTargetContext mixinTargetContext) {
        if (mixinTargetContext.shouldSetSourceFile()) {
            this.targetClass.sourceFile = mixinTargetContext.getSourceFile();
        }
        this.targetClass.version = Math.max(this.targetClass.version, mixinTargetContext.getMinRequiredClassVersion());
    }

    protected void applyAnnotations(MixinTargetContext mixinTargetContext) {
        ClassNode classNode = mixinTargetContext.getClassNode();
        this.mergeAnnotations(classNode, this.targetClass);
    }

    protected void applyFields(MixinTargetContext mixinTargetContext) {
        this.mergeShadowFields(mixinTargetContext);
        this.mergeNewFields(mixinTargetContext);
    }

    protected void mergeShadowFields(MixinTargetContext mixinTargetContext) {
        for (Map.Entry entry : mixinTargetContext.getShadowFields()) {
            FieldNode fieldNode = (FieldNode)entry.getKey();
            FieldNode fieldNode2 = this.findTargetField(fieldNode);
            if (fieldNode2 == null) continue;
            this.mergeAnnotations(fieldNode, fieldNode2);
            if (!((ClassInfo.Field)entry.getValue()).isDecoratedMutable() || ASMHelper.hasFlag(fieldNode2, 2)) continue;
            fieldNode2.access &= 0xFFFFFFEF;
        }
    }

    protected void mergeNewFields(MixinTargetContext mixinTargetContext) {
        for (FieldNode fieldNode : mixinTargetContext.getFields()) {
            FieldNode fieldNode2 = this.findTargetField(fieldNode);
            if (fieldNode2 != null) continue;
            this.targetClass.fields.add(fieldNode);
        }
    }

    protected void applyMethods(MixinTargetContext mixinTargetContext) {
        for (MethodNode methodNode : mixinTargetContext.getShadowMethods()) {
            this.applyShadowMethod(mixinTargetContext, methodNode);
        }
        for (MethodNode methodNode : mixinTargetContext.getMethods()) {
            this.applyNormalMethod(mixinTargetContext, methodNode);
        }
    }

    protected void applyShadowMethod(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        MethodNode methodNode2 = this.findTargetMethod(methodNode);
        if (methodNode2 != null) {
            this.mergeAnnotations(methodNode, methodNode2);
        }
    }

    protected void applyNormalMethod(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        mixinTargetContext.transformMethod(methodNode);
        if (!methodNode.name.startsWith("<")) {
            this.checkMethodVisibility(mixinTargetContext, methodNode);
            this.checkMethodConstraints(mixinTargetContext, methodNode);
            this.mergeMethod(mixinTargetContext, methodNode);
        } else if ("<clinit>".equals(methodNode.name)) {
            this.appendInsns(methodNode);
        }
    }

    /*
     * Exception decompiling
     */
    protected void mergeMethod(MixinTargetContext var1, MethodNode var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl42 : GOTO - null : trying to set 4 previously set to 9
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected void displaceIntrinsic(MixinTargetContext mixinTargetContext, MethodNode methodNode, MethodNode methodNode2) {
        String string = "proxy+" + methodNode2.name;
        ListIterator listIterator = methodNode.instructions.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (!(abstractInsnNode instanceof MethodInsnNode) || abstractInsnNode.getOpcode() == 184) continue;
            MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
            if (!methodInsnNode.owner.equals(this.targetClass.name) || !methodInsnNode.name.equals(methodNode2.name) || !methodInsnNode.desc.equals(methodNode2.desc)) continue;
            methodInsnNode.name = string;
        }
        methodNode2.name = string;
    }

    protected final void appendInsns(MethodNode methodNode) {
        if (Type.getReturnType(methodNode.desc) != Type.VOID_TYPE) {
            throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
        }
        MethodNode methodNode2 = this.findTargetMethod(methodNode);
        if (methodNode2 != null) {
            AbstractInsnNode abstractInsnNode = MixinApplicatorStandard.findInsn(methodNode2, 177);
            if (abstractInsnNode != null) {
                ListIterator listIterator = methodNode.instructions.iterator();
                while (listIterator.hasNext()) {
                    AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode)listIterator.next();
                    if (abstractInsnNode2 instanceof LineNumberNode || abstractInsnNode2.getOpcode() == 177) continue;
                    methodNode2.instructions.insertBefore(abstractInsnNode, abstractInsnNode2);
                }
                methodNode2.maxLocals = Math.max(methodNode2.maxLocals, methodNode.maxLocals);
                methodNode2.maxStack = Math.max(methodNode2.maxStack, methodNode.maxStack);
            }
            return;
        }
        this.targetClass.methods.add(methodNode);
    }

    protected void applyInitialisers(MixinTargetContext mixinTargetContext) {
        MethodNode methodNode = this.getConstructor(mixinTargetContext);
        if (methodNode == null) {
            return;
        }
        Deque deque = this.getInitialiser(mixinTargetContext, methodNode);
        if (deque == null || deque.size() == 0) {
            return;
        }
        for (MethodNode methodNode2 : this.targetClass.methods) {
            if (!"<init>".equals(methodNode2.name)) continue;
            methodNode2.maxStack = Math.max(methodNode2.maxStack, methodNode.maxStack);
            this.injectInitialiser(mixinTargetContext, methodNode2, deque);
        }
    }

    protected MethodNode getConstructor(MixinTargetContext mixinTargetContext) {
        MethodNode methodNode = null;
        for (MethodNode methodNode2 : mixinTargetContext.getMethods()) {
            if (!"<init>".equals(methodNode2.name) || methodNode2 == false) continue;
            if (methodNode == null) {
                methodNode = methodNode2;
                continue;
            }
            this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", mixinTargetContext, methodNode.desc));
        }
        return methodNode;
    }

    private Range getConstructorRange(MethodNode methodNode) {
        boolean bl = false;
        AbstractInsnNode abstractInsnNode = null;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = -1;
        Object object = methodNode.instructions.iterator();
        while (object.hasNext()) {
            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode)object.next();
            if (abstractInsnNode2 instanceof LineNumberNode) {
                n = ((LineNumberNode)abstractInsnNode2).line;
                bl = true;
                continue;
            }
            if (abstractInsnNode2 instanceof MethodInsnNode) {
                if (abstractInsnNode2.getOpcode() != 183 || !"<init>".equals(((MethodInsnNode)abstractInsnNode2).name) || n4 != -1) continue;
                n4 = methodNode.instructions.indexOf(abstractInsnNode2);
                n2 = n;
                continue;
            }
            if (abstractInsnNode2.getOpcode() == 181) {
                bl = false;
                continue;
            }
            if (abstractInsnNode2.getOpcode() != 177) continue;
            if (bl) {
                n3 = n;
                continue;
            }
            n3 = n2;
            abstractInsnNode = abstractInsnNode2;
        }
        if (abstractInsnNode != null) {
            object = new LabelNode(new Label());
            methodNode.instructions.insertBefore(abstractInsnNode, (AbstractInsnNode)object);
            methodNode.instructions.insertBefore(abstractInsnNode, new LineNumberNode(n2, (LabelNode)object));
        }
        return new Range(this, n2, n3, n4);
    }

    protected final Deque getInitialiser(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        Range range = this.getConstructorRange(methodNode);
        if (!range.isValid()) {
            return null;
        }
        int n = 0;
        ArrayDeque<AbstractInsnNode> arrayDeque = new ArrayDeque<AbstractInsnNode>();
        boolean bl = false;
        int n2 = -1;
        LabelNode labelNode = null;
        Object object = methodNode.instructions.iterator(range.marker);
        while (object.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)object.next();
            if (abstractInsnNode instanceof LineNumberNode) {
                n = ((LineNumberNode)abstractInsnNode).line;
                AbstractInsnNode abstractInsnNode2 = methodNode.instructions.get(methodNode.instructions.indexOf(abstractInsnNode) + 1);
                if (n == range.end && abstractInsnNode2.getOpcode() != 177) {
                    bl = true;
                    n2 = 177;
                    continue;
                }
                bl = range.excludes(n);
                n2 = -1;
                continue;
            }
            if (!bl) continue;
            if (labelNode != null) {
                arrayDeque.add(labelNode);
                labelNode = null;
            }
            if (abstractInsnNode instanceof LabelNode) {
                labelNode = (LabelNode)abstractInsnNode;
                continue;
            }
            int n3 = abstractInsnNode.getOpcode();
            if (n3 == n2) {
                n2 = -1;
                continue;
            }
            for (int n4 : INITIALISER_OPCODE_BLACKLIST) {
                if (n3 != n4) continue;
                throw new InvalidMixinException((IReferenceMapperContext)mixinTargetContext, "Cannot handle " + ASMHelper.getOpcodeName(n3) + " opcode (0x" + Integer.toHexString(n3).toUpperCase() + ") in class initialiser");
            }
            arrayDeque.add(abstractInsnNode);
        }
        object = (AbstractInsnNode)arrayDeque.peekLast();
        if (object != null && ((AbstractInsnNode)object).getOpcode() != 181) {
            throw new InvalidMixinException((IReferenceMapperContext)mixinTargetContext, "Could not parse initialiser, expected 0xB5, found 0x" + Integer.toHexString(((AbstractInsnNode)object).getOpcode()) + " in " + mixinTargetContext);
        }
        return arrayDeque;
    }

    protected final void injectInitialiser(MixinTargetContext mixinTargetContext, MethodNode methodNode, Deque deque) {
        Map map = ASMHelper.cloneLabels(methodNode.instructions);
        AbstractInsnNode abstractInsnNode = this.findInitialiserInjectionPoint(mixinTargetContext, methodNode, deque);
        if (abstractInsnNode == null) {
            this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[]{methodNode.desc});
            return;
        }
        for (AbstractInsnNode abstractInsnNode2 : deque) {
            if (abstractInsnNode2 instanceof LabelNode) continue;
            if (abstractInsnNode2 instanceof JumpInsnNode) {
                throw new InvalidMixinException((IReferenceMapperContext)mixinTargetContext, "Unsupported JUMP opcode in initialiser in " + mixinTargetContext);
            }
            AbstractInsnNode abstractInsnNode3 = abstractInsnNode2.clone(map);
            methodNode.instructions.insert(abstractInsnNode, abstractInsnNode3);
            abstractInsnNode = abstractInsnNode3;
        }
    }

    protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext mixinTargetContext, MethodNode methodNode, Deque deque) {
        Object object2;
        HashSet<String> hashSet = new HashSet<String>();
        for (Object object2 : deque) {
            if (((AbstractInsnNode)object2).getOpcode() != 181) continue;
            hashSet.add(MixinApplicatorStandard.fieldKey((FieldInsnNode)object2));
        }
        Object object3 = this.getInitialiserInjectionMode(mixinTargetContext.getEnvironment());
        object2 = mixinTargetContext.getTargetClassInfo().getName();
        String string = mixinTargetContext.getTargetClassInfo().getSuperName();
        AbstractInsnNode abstractInsnNode = null;
        ListIterator listIterator = methodNode.instructions.iterator();
        while (listIterator.hasNext()) {
            String string2;
            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode)listIterator.next();
            if (abstractInsnNode2.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)abstractInsnNode2).name)) {
                string2 = ((MethodInsnNode)abstractInsnNode2).owner;
                if (!string2.equals(object2) && !string2.equals(string)) continue;
                abstractInsnNode = abstractInsnNode2;
                if (object3 != InitialiserInjectionMode.SAFE) continue;
                break;
            }
            if (abstractInsnNode2.getOpcode() != 181 || object3 != InitialiserInjectionMode.DEFAULT || !hashSet.contains(string2 = MixinApplicatorStandard.fieldKey((FieldInsnNode)abstractInsnNode2))) continue;
            abstractInsnNode = abstractInsnNode2;
        }
        return abstractInsnNode;
    }

    private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment mixinEnvironment) {
        String string = mixinEnvironment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
        if (string == null) {
            return InitialiserInjectionMode.DEFAULT;
        }
        try {
            return InitialiserInjectionMode.valueOf(string.toUpperCase());
        }
        catch (Exception exception) {
            this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[]{string});
            return InitialiserInjectionMode.DEFAULT;
        }
    }

    private static String fieldKey(FieldInsnNode fieldInsnNode) {
        return String.format("%s:%s", fieldInsnNode.desc, fieldInsnNode.name);
    }

    protected void prepareInjections(MixinTargetContext mixinTargetContext) {
        mixinTargetContext.prepareInjections();
    }

    protected void applyInjections(MixinTargetContext mixinTargetContext) {
        mixinTargetContext.applyInjections();
    }

    protected void applyAccessors(MixinTargetContext mixinTargetContext) {
        List list = mixinTargetContext.generateAccessors();
        for (MethodNode methodNode : list) {
            if (methodNode.name.startsWith("<")) continue;
            this.mergeMethod(mixinTargetContext, methodNode);
        }
    }

    protected void checkMethodVisibility(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        if (ASMHelper.hasFlag(methodNode, 8) && !ASMHelper.hasFlag(methodNode, 2) && !ASMHelper.hasFlag(methodNode, 4096) && ASMHelper.getVisibleAnnotation(methodNode, Overwrite.class) == null) {
            throw new InvalidMixinException((IReferenceMapperContext)mixinTargetContext, String.format("Mixin %s contains non-private static method %s", mixinTargetContext, methodNode));
        }
    }

    protected void applySourceMap(TargetClassContext targetClassContext) {
        this.targetClass.sourceDebug = targetClassContext.getSourceMap().toString();
    }

    protected void checkMethodConstraints(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        for (Class clazz : CONSTRAINED_ANNOTATIONS) {
            AnnotationNode annotationNode = ASMHelper.getVisibleAnnotation(methodNode, clazz);
            if (annotationNode == null) continue;
            this.checkConstraints(mixinTargetContext, methodNode, annotationNode);
        }
    }

    protected final void checkConstraints(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        try {
            ConstraintParser.Constraint constraint = ConstraintParser.parse(annotationNode);
            MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
            try {
                constraint.check(mixinEnvironment);
            }
            catch (ConstraintViolationException constraintViolationException) {
                String string = String.format("Constraint violation: %s on %s in %s", constraintViolationException.getMessage(), methodNode, mixinTargetContext);
                this.logger.warn(string);
                if (!mixinEnvironment.getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
                    throw new InvalidMixinException(mixinTargetContext, string, (Throwable)constraintViolationException);
                }
            }
        }
        catch (InvalidConstraintException invalidConstraintException) {
            throw new InvalidMixinException((IReferenceMapperContext)mixinTargetContext, invalidConstraintException.getMessage());
        }
    }

    protected final void mergeAnnotations(ClassNode classNode, ClassNode classNode2) {
        classNode2.visibleAnnotations = this.mergeAnnotations(classNode.visibleAnnotations, classNode2.visibleAnnotations, classNode.name);
        classNode2.invisibleAnnotations = this.mergeAnnotations(classNode.invisibleAnnotations, classNode2.invisibleAnnotations, classNode.name);
    }

    protected final void mergeAnnotations(MethodNode methodNode, MethodNode methodNode2) {
        methodNode2.visibleAnnotations = this.mergeAnnotations(methodNode.visibleAnnotations, methodNode2.visibleAnnotations, methodNode.name);
        methodNode2.invisibleAnnotations = this.mergeAnnotations(methodNode.invisibleAnnotations, methodNode2.invisibleAnnotations, methodNode.name);
    }

    protected final void mergeAnnotations(FieldNode fieldNode, FieldNode fieldNode2) {
        fieldNode2.visibleAnnotations = this.mergeAnnotations(fieldNode.visibleAnnotations, fieldNode2.visibleAnnotations, fieldNode.name);
        fieldNode2.invisibleAnnotations = this.mergeAnnotations(fieldNode.invisibleAnnotations, fieldNode2.invisibleAnnotations, fieldNode.name);
    }

    /*
     * Exception decompiling
     */
    private List mergeAnnotations(List var1, List var2, String var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl14 : ALOAD - null : trying to set 1 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected static AbstractInsnNode findInsn(MethodNode methodNode, int n) {
        ListIterator listIterator = methodNode.instructions.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (abstractInsnNode.getOpcode() != n) continue;
            return abstractInsnNode;
        }
        return null;
    }

    protected final MethodNode findTargetMethod(MethodNode methodNode) {
        for (MethodNode methodNode2 : this.targetClass.methods) {
            if (!methodNode2.name.equals(methodNode.name) || !methodNode2.desc.equals(methodNode.desc)) continue;
            return methodNode2;
        }
        return null;
    }

    protected final FieldNode findTargetField(FieldNode fieldNode) {
        for (FieldNode fieldNode2 : this.targetClass.fields) {
            if (!fieldNode2.name.equals(fieldNode.name)) continue;
            return fieldNode2;
        }
        return null;
    }

    class Range {
        final int start;
        final int end;
        final int marker;
        final MixinApplicatorStandard this$0;

        Range(MixinApplicatorStandard mixinApplicatorStandard, int n, int n2, int n3) {
            this.this$0 = mixinApplicatorStandard;
            this.start = n;
            this.end = n2;
            this.marker = n3;
        }

        boolean isValid() {
            return this.start != 0 && this.end != 0 && this.end >= this.start;
        }

        boolean contains(int n) {
            return n >= this.start && n <= this.end;
        }

        boolean excludes(int n) {
            return n < this.start || n > this.end;
        }

        public String toString() {
            return String.format("Range[%d-%d,%d,valid=%s)", this.start, this.end, this.marker, this.isValid());
        }
    }

    static enum InitialiserInjectionMode {
        DEFAULT,
        SAFE;

    }

    static enum ApplicatorPass {
        MAIN,
        PREINJECT,
        INJECT;

    }
}

