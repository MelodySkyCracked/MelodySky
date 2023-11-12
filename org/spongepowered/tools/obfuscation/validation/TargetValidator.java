/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TargetValidator
extends MixinValidator {
    public TargetValidator(IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        super(iMixinAnnotationProcessor, IMixinValidator.ValidationPass.LATE);
    }

    @Override
    public boolean validate(TypeElement typeElement, AnnotationHandle annotationHandle, Collection collection) {
        if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator"))) {
            return true;
        }
        if (typeElement.getKind() == ElementKind.INTERFACE) {
            this.validateInterfaceMixin(typeElement, collection);
        } else {
            this.validateClassMixin(typeElement, collection);
        }
        return true;
    }

    private void validateInterfaceMixin(TypeElement typeElement, Collection collection) {
        boolean bl = false;
        for (Element object : typeElement.getEnclosedElements()) {
            if (object.getKind() != ElementKind.METHOD) continue;
            boolean bl2 = AnnotationHandle.of(object, Accessor.class).exists();
            boolean bl3 = AnnotationHandle.of(object, Invoker.class).exists();
            bl |= !bl2 && !bl3;
        }
        if (!bl) {
            return;
        }
        for (TypeHandle typeHandle : collection) {
            TypeElement typeElement2 = typeHandle.getElement();
            if (typeElement2 == null || typeElement2.getKind() == ElementKind.INTERFACE) continue;
            this.error("Targetted type '" + typeHandle + " of " + typeElement + " is not an interface", typeElement);
        }
    }

    /*
     * Exception decompiling
     */
    private void validateClassMixin(TypeElement var1, Collection var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl7 : ALOAD - null : trying to set 2 previously set to 0
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

    private boolean validateSuperClassRecursive(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (!(typeMirror instanceof DeclaredType)) {
            return false;
        }
        if (TypeUtils.isAssignable(this.processingEnv, typeMirror, typeMirror2)) {
            return true;
        }
        TypeElement typeElement = (TypeElement)((DeclaredType)typeMirror).asElement();
        TypeMirror typeMirror3 = typeElement.getSuperclass();
        if (typeMirror3.getKind() == TypeKind.NONE) {
            return false;
        }
        TargetValidator targetValidator = this;
        TypeMirror typeMirror4 = typeMirror3;
        if (typeMirror2 != false) {
            return true;
        }
        return this.validateSuperClassRecursive(typeMirror3, typeMirror2);
    }
}

