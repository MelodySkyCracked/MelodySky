/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.InterfaceInfo;
import org.spongepowered.asm.mixin.transformer.MethodMapper;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.TargetClassContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.ASMHelper;

class MixinPreProcessorStandard {
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    protected final MixinInfo mixin;
    protected final MixinInfo.MixinClassNode classNode;
    private final boolean verboseLogging;
    private final boolean strictUnique;
    private boolean prepared;
    private boolean attached;

    MixinPreProcessorStandard(MixinInfo mixinInfo, MixinInfo.MixinClassNode mixinClassNode) {
        this.mixin = mixinInfo;
        this.classNode = mixinClassNode;
        MixinEnvironment mixinEnvironment = mixinInfo.getParent().getEnvironment();
        this.verboseLogging = mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
        this.strictUnique = mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
    }

    MixinPreProcessorStandard prepare() {
        if (this.prepared) {
            return this;
        }
        this.prepared = true;
        for (Object object : this.classNode.mixinMethods) {
            ClassInfo.Method method = this.mixin.getClassInfo().findMethod((MethodNode)object);
            this.prepareMethod((MixinInfo.MixinMethodNode)object, method);
        }
        for (Object object : this.classNode.fields) {
            this.prepareField((FieldNode)object);
        }
        return this;
    }

    protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethodNode, ClassInfo.Method method) {
        this.prepareShadow(mixinMethodNode, method);
        this.prepareSoftImplements(mixinMethodNode, method);
    }

    protected void prepareShadow(MixinInfo.MixinMethodNode mixinMethodNode, ClassInfo.Method method) {
        AnnotationNode annotationNode = ASMHelper.getVisibleAnnotation(mixinMethodNode, Shadow.class);
        if (annotationNode == null) {
            return;
        }
        String string = (String)ASMHelper.getAnnotationValue(annotationNode, "prefix", Shadow.class);
        if (mixinMethodNode.name.startsWith(string)) {
            ASMHelper.setVisibleAnnotation(mixinMethodNode, MixinRenamed.class, "originalName", mixinMethodNode.name);
            String string2 = mixinMethodNode.name.substring(string.length());
            mixinMethodNode.name = method.renameTo(string2);
        }
    }

    protected void prepareSoftImplements(MixinInfo.MixinMethodNode mixinMethodNode, ClassInfo.Method method) {
        for (InterfaceInfo interfaceInfo : this.mixin.getSoftImplements()) {
            if (!interfaceInfo.renameMethod(mixinMethodNode)) continue;
            method.renameTo(mixinMethodNode.name);
        }
    }

    protected void prepareField(FieldNode fieldNode) {
    }

    MixinPreProcessorStandard conform(TargetClassContext targetClassContext) {
        return this.conform(targetClassContext.getClassInfo());
    }

    MixinPreProcessorStandard conform(ClassInfo classInfo) {
        for (MixinInfo.MixinMethodNode mixinMethodNode : this.classNode.mixinMethods) {
            if (!mixinMethodNode.isInjector()) continue;
            ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode, 10);
            this.conformInjector(classInfo, mixinMethodNode, method);
        }
        return this;
    }

    private void conformInjector(ClassInfo classInfo, MixinInfo.MixinMethodNode mixinMethodNode, ClassInfo.Method method) {
        MethodMapper methodMapper = classInfo.getMethodMapper();
        methodMapper.remapHandlerMethod(this.mixin, mixinMethodNode, method);
    }

    MixinTargetContext createContextFor(TargetClassContext targetClassContext) {
        MixinTargetContext mixinTargetContext = new MixinTargetContext(this.mixin, this.classNode, targetClassContext);
        this.conform(targetClassContext);
        this.attach(mixinTargetContext);
        return mixinTargetContext;
    }

    MixinPreProcessorStandard attach(MixinTargetContext mixinTargetContext) {
        if (this.attached) {
            throw new IllegalStateException("Preprocessor was already attached");
        }
        this.attached = true;
        this.attachMethods(mixinTargetContext);
        this.attachFields(mixinTargetContext);
        this.transform(mixinTargetContext);
        return this;
    }

    /*
     * Exception decompiling
     */
    protected void attachMethods(MixinTargetContext var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl6 : ALOAD_2 - null : trying to set 2 previously set to 0
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

    protected boolean validateMethod(MixinTargetContext mixinTargetContext, MixinInfo.MixinMethodNode mixinMethodNode) {
        return true;
    }

    protected boolean attachInjectorMethod(MixinTargetContext mixinTargetContext, MixinInfo.MixinMethodNode mixinMethodNode) {
        return mixinMethodNode.isInjector();
    }

    protected boolean attachOverwriteMethod(MixinTargetContext mixinTargetContext, MixinInfo.MixinMethodNode mixinMethodNode) {
        return this.attachSpecialMethod(mixinTargetContext, mixinMethodNode, Overwrite.class, true);
    }

    protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode mixinMethodNode, Class clazz) {
        ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode, 10);
        this.checkMethodNotUnique(clazz, method);
        return method;
    }

    protected void checkMethodNotUnique(Class clazz, ClassInfo.Method method) {
        if (method.isUnique()) {
            String string = "@" + ASMHelper.getSimpleName(clazz);
            throw new InvalidMixinException((IMixinInfo)this.mixin, string + " method " + method.getName() + " cannot be @Unique");
        }
    }

    protected void checkMixinNotUnique(MixinInfo.MixinMethodNode mixinMethodNode, Class clazz) {
        if (this.mixin.isUnique()) {
            String string = "@" + ASMHelper.getSimpleName(clazz);
            throw new InvalidMixinException((IMixinInfo)this.mixin, string + " method " + mixinMethodNode.name + " found in a @Unique mixin");
        }
    }

    protected void attachMethod(MixinInfo.MixinMethodNode mixinMethodNode) {
        ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethodNode);
        if (method == null) {
            return;
        }
        ClassInfo.Method method2 = this.mixin.getClassInfo().findMethodInHierarchy(mixinMethodNode, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
        if (method2 != null && method2.isRenamed()) {
            mixinMethodNode.name = method.renameTo(method2.getName());
        }
    }

    /*
     * Exception decompiling
     */
    protected void attachFields(MixinTargetContext var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl6 : ALOAD_2 - null : trying to set 3 previously set to 0
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

    protected void transform(MixinTargetContext mixinTargetContext) {
        for (MethodNode methodNode : this.classNode.methods) {
            ListIterator listIterator = methodNode.instructions.iterator();
            while (listIterator.hasNext()) {
                ClassInfo.Member member;
                AbstractInsnNode abstractInsnNode;
                AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode)listIterator.next();
                if (abstractInsnNode2 instanceof MethodInsnNode) {
                    abstractInsnNode = (MethodInsnNode)abstractInsnNode2;
                    member = ClassInfo.forName(abstractInsnNode.owner).findMethodInHierarchy((MethodInsnNode)abstractInsnNode, ClassInfo.SearchType.ALL_CLASSES, 2);
                    if (member == null || !((ClassInfo.Method)member).isRenamed()) continue;
                    abstractInsnNode.name = ((ClassInfo.Method)member).getName();
                    continue;
                }
                if (!(abstractInsnNode2 instanceof FieldInsnNode)) continue;
                abstractInsnNode = (FieldInsnNode)abstractInsnNode2;
                member = ClassInfo.forName(((FieldInsnNode)abstractInsnNode).owner).findField((FieldInsnNode)abstractInsnNode, 2);
                if (member == null || !member.isRenamed()) continue;
                ((FieldInsnNode)abstractInsnNode).name = member.getName();
            }
        }
    }
}

