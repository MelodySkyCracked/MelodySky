/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.AnnotatedMixin;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.ReferenceManager;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixinElementHandlerInjector
extends AnnotatedMixinElementHandler {
    AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor iMixinAnnotationProcessor, AnnotatedMixin annotatedMixin) {
        super(iMixinAnnotationProcessor, annotatedMixin);
    }

    /*
     * Exception decompiling
     */
    public void registerInjector(AnnotatedElementInjector var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl59 : ALOAD - null : trying to set 4 previously set to 0
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

    public void registerInjectionPoint(AnnotatedElementInjectionPoint annotatedElementInjectionPoint, String string) {
        if (this.mixin.isInterface()) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", annotatedElementInjectionPoint.getElement());
        }
        if (!annotatedElementInjectionPoint.getAt().getBoolean("remap", true)) {
            return;
        }
        String string2 = InjectionPointData.parseType((String)annotatedElementInjectionPoint.getAt().getValue("value"));
        String string3 = (String)annotatedElementInjectionPoint.getAt().getValue("target");
        if ("NEW".equals(string2)) {
            this.remapNewTarget(String.format(string, string2 + ".<target>"), string3, annotatedElementInjectionPoint);
            this.remapNewTarget(String.format(string, string2 + ".args[class]"), annotatedElementInjectionPoint.getAtArg("class"), annotatedElementInjectionPoint);
        } else {
            this.remapReference(String.format(string, string2 + ".<target>"), string3, annotatedElementInjectionPoint);
        }
    }

    protected final void remapNewTarget(String string, String string2, AnnotatedElementInjectionPoint annotatedElementInjectionPoint) {
        if (string2 == null) {
            return;
        }
        MemberInfo memberInfo = MemberInfo.parse(string2);
        String string3 = memberInfo.toCtorType();
        if (string3 != null) {
            String string4 = memberInfo.toCtorDesc();
            MappingMethod mappingMethod = new MappingMethod(string3, ".", string4 != null ? string4 : "()V");
            ObfuscationData obfuscationData = this.obf.getDataProvider().getRemappedMethod(mappingMethod);
            if (obfuscationData.isEmpty()) {
                this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find class mapping for " + string + " '" + string3 + "'", annotatedElementInjectionPoint.getElement(), annotatedElementInjectionPoint.getAnnotation().asMirror());
                return;
            }
            ObfuscationData obfuscationData2 = new ObfuscationData();
            for (ObfuscationType obfuscationType : obfuscationData) {
                MappingMethod mappingMethod2 = (MappingMethod)obfuscationData.get(obfuscationType);
                if (string4 == null) {
                    obfuscationData2.add(obfuscationType, mappingMethod2.getOwner());
                    continue;
                }
                obfuscationData2.add(obfuscationType, mappingMethod2.getDesc().replace(")V", ")L" + mappingMethod2.getOwner() + ";"));
            }
            this.obf.getReferenceManager().addClassMapping(this.classRef, string2, obfuscationData2);
        }
        annotatedElementInjectionPoint.notifyRemapped();
    }

    protected final boolean remapReference(String string, String string2, AnnotatedElementInjectionPoint annotatedElementInjectionPoint) {
        if (string2 == null) {
            return false;
        }
        AnnotationMirror annotationMirror = (this.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT ? annotatedElementInjectionPoint.getAt() : annotatedElementInjectionPoint.getAnnotation()).asMirror();
        MemberInfo memberInfo = MemberInfo.parse(string2);
        if (!memberInfo.isFullyQualified()) {
            String string3 = memberInfo.owner == null ? (memberInfo.desc == null ? "owner and signature" : "owner") : "signature";
            this.ap.printMessage(Diagnostic.Kind.ERROR, string + " is not fully qualified, missing " + string3, annotatedElementInjectionPoint.getElement(), annotationMirror);
            return false;
        }
        try {
            memberInfo.validate();
        }
        catch (InvalidMemberDescriptorException invalidMemberDescriptorException) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, invalidMemberDescriptorException.getMessage(), annotatedElementInjectionPoint.getElement(), annotationMirror);
        }
        try {
            if (memberInfo.isField()) {
                ObfuscationData obfuscationData = this.obf.getDataProvider().getObfFieldRecursive(memberInfo);
                if (obfuscationData.isEmpty()) {
                    this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find field mapping for " + string + " '" + string2 + "'", annotatedElementInjectionPoint.getElement(), annotationMirror);
                    return false;
                }
                this.obf.getReferenceManager().addFieldMapping(this.classRef, string2, memberInfo, obfuscationData);
            } else {
                ObfuscationData obfuscationData = this.obf.getDataProvider().getObfMethodRecursive(memberInfo);
                if (obfuscationData.isEmpty() && (memberInfo.owner == null || !memberInfo.owner.startsWith("java/lang/"))) {
                    this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find method mapping for " + string + " '" + string2 + "'", annotatedElementInjectionPoint.getElement(), annotationMirror);
                    return false;
                }
                this.obf.getReferenceManager().addMethodMapping(this.classRef, string2, memberInfo, obfuscationData);
            }
        }
        catch (ReferenceManager.ReferenceConflictException referenceConflictException) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected reference conflict for " + string + ": " + string2 + " -> " + referenceConflictException.getNew() + " previously defined as " + referenceConflictException.getOld(), annotatedElementInjectionPoint.getElement(), annotationMirror);
            return false;
        }
        return true;
    }

    static class AnnotatedElementInjectionPoint
    extends AnnotatedMixinElementHandler.AnnotatedElement {
        private final AnnotationHandle at;
        private Map args;
        private final InjectorRemap state;

        public AnnotatedElementInjectionPoint(ExecutableElement executableElement, AnnotationHandle annotationHandle, AnnotationHandle annotationHandle2, InjectorRemap injectorRemap) {
            super(executableElement, annotationHandle);
            this.at = annotationHandle2;
            this.state = injectorRemap;
        }

        public AnnotationHandle getAt() {
            return this.at;
        }

        public String getAtArg(String string) {
            if (this.args == null) {
                this.args = new HashMap();
                for (String string2 : this.at.getList("args")) {
                    if (string2 == null) continue;
                    int n = string2.indexOf(61);
                    if (n > -1) {
                        this.args.put(string2.substring(0, n), string2.substring(n + 1));
                        continue;
                    }
                    this.args.put(string2, "");
                }
            }
            return (String)this.args.get(string);
        }

        public void notifyRemapped() {
            this.state.notifyRemapped();
        }
    }

    static class AnnotatedElementInjector
    extends AnnotatedMixinElementHandler.AnnotatedElement {
        private final InjectorRemap state;

        public AnnotatedElementInjector(ExecutableElement executableElement, AnnotationHandle annotationHandle, InjectorRemap injectorRemap) {
            super(executableElement, annotationHandle);
            this.state = injectorRemap;
        }

        public boolean shouldRemap() {
            return this.state.shouldRemap();
        }

        public void addMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationHandle annotationHandle) {
            this.state.addMessage(kind, charSequence, element, annotationHandle);
        }

        public String toString() {
            return this.getAnnotation().toString();
        }
    }
}

