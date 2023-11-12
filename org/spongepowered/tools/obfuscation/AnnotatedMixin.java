/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerAccessor;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerInjector;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerOverwrite;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerSoftImplements;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixin {
    private final AnnotationHandle annotation;
    private final Messager messager;
    private final ITypeHandleProvider typeProvider;
    private final IObfuscationManager obf;
    private final IMappingConsumer mappings;
    private final TypeElement mixin;
    private final TypeHandle handle;
    private final List targets = new ArrayList();
    private final TypeHandle primaryTarget;
    private final String classRef;
    private final boolean remap;
    private final boolean virtual;
    private final AnnotatedMixinElementHandlerOverwrite overwrites;
    private final AnnotatedMixinElementHandlerShadow shadows;
    private final AnnotatedMixinElementHandlerInjector injectors;
    private final AnnotatedMixinElementHandlerAccessor accessors;
    private final AnnotatedMixinElementHandlerSoftImplements softImplements;

    public AnnotatedMixin(IMixinAnnotationProcessor iMixinAnnotationProcessor, TypeElement typeElement) {
        this.typeProvider = iMixinAnnotationProcessor.getTypeProvider();
        this.obf = iMixinAnnotationProcessor.getObfuscationManager();
        this.mappings = this.obf.createMappingConsumer();
        this.messager = iMixinAnnotationProcessor;
        this.mixin = typeElement;
        this.handle = new TypeHandle(typeElement);
        this.virtual = this.handle.getAnnotation(Pseudo.class).exists();
        this.annotation = this.handle.getAnnotation(Mixin.class);
        this.classRef = typeElement.getQualifiedName().toString().replace('.', '/');
        this.primaryTarget = this.initTargets();
        this.remap = this.annotation.getBoolean("remap", true) && this.targets.size() > 0;
        this.overwrites = new AnnotatedMixinElementHandlerOverwrite(iMixinAnnotationProcessor, this);
        this.shadows = new AnnotatedMixinElementHandlerShadow(iMixinAnnotationProcessor, this);
        this.injectors = new AnnotatedMixinElementHandlerInjector(iMixinAnnotationProcessor, this);
        this.accessors = new AnnotatedMixinElementHandlerAccessor(iMixinAnnotationProcessor, this);
        this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(iMixinAnnotationProcessor, this);
    }

    AnnotatedMixin runValidators(IMixinValidator.ValidationPass validationPass, Collection collection) {
        IMixinValidator iMixinValidator;
        Iterator iterator = collection.iterator();
        while (iterator.hasNext() && (iMixinValidator = (IMixinValidator)iterator.next()).validate(validationPass, this.mixin, this.annotation, this.targets)) {
        }
        return this;
    }

    private TypeHandle initTargets() {
        TypeHandle typeHandle;
        TypeHandle typeHandle2 = null;
        try {
            for (Object object : this.annotation.getList()) {
                typeHandle = new TypeHandle((DeclaredType)object);
                if (this.targets.contains(typeHandle)) continue;
                this.addTarget(typeHandle);
                if (typeHandle2 != null) continue;
                typeHandle2 = typeHandle;
            }
        }
        catch (Exception exception) {
            this.printMessage(Diagnostic.Kind.WARNING, "Error processing public targets: " + exception.getClass().getName() + ": " + exception.getMessage(), this);
        }
        try {
            for (Object object : this.annotation.getList("targets")) {
                typeHandle = this.typeProvider.getTypeHandle((String)object);
                if (this.targets.contains(typeHandle)) continue;
                if (this.virtual) {
                    typeHandle = this.typeProvider.getSimulatedHandle((String)object, this.mixin.asType());
                } else {
                    if (typeHandle == null) {
                        this.printMessage(Diagnostic.Kind.ERROR, "Mixin target " + (String)object + " could not be found", this);
                        return null;
                    }
                    if (typeHandle.isPublic()) {
                        this.printMessage(Diagnostic.Kind.WARNING, "Mixin target " + (String)object + " is public and must be specified in value", this);
                        return null;
                    }
                }
                this.addSoftTarget(typeHandle, (String)object);
                if (typeHandle2 != null) continue;
                typeHandle2 = typeHandle;
            }
        }
        catch (Exception exception) {
            this.printMessage(Diagnostic.Kind.WARNING, "Error processing private targets: " + exception.getClass().getName() + ": " + exception.getMessage(), this);
        }
        if (typeHandle2 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Mixin has no targets", this);
        }
        return typeHandle2;
    }

    private void printMessage(Diagnostic.Kind kind, CharSequence charSequence, AnnotatedMixin annotatedMixin) {
        this.messager.printMessage(kind, charSequence, this.mixin, this.annotation.asMirror());
    }

    private void addSoftTarget(TypeHandle typeHandle, String string) {
        ObfuscationData obfuscationData = this.obf.getDataProvider().getObfClass(typeHandle);
        if (!obfuscationData.isEmpty()) {
            this.obf.getReferenceManager().addClassMapping(this.classRef, string, obfuscationData);
        }
        this.addTarget(typeHandle);
    }

    private void addTarget(TypeHandle typeHandle) {
        this.targets.add(typeHandle);
    }

    public String toString() {
        return this.mixin.getSimpleName().toString();
    }

    public AnnotationHandle getAnnotation() {
        return this.annotation;
    }

    public TypeElement getMixin() {
        return this.mixin;
    }

    public TypeHandle getHandle() {
        return this.handle;
    }

    public String getClassRef() {
        return this.classRef;
    }

    public boolean isInterface() {
        return this.mixin.getKind() == ElementKind.INTERFACE;
    }

    @Deprecated
    public TypeHandle getPrimaryTarget() {
        return this.primaryTarget;
    }

    public List getTargets() {
        return this.targets;
    }

    public boolean isMultiTarget() {
        return this.targets.size() > 1;
    }

    public boolean remap() {
        return this.remap;
    }

    public IMappingConsumer getMappings() {
        return this.mappings;
    }

    public void registerOverwrite(ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(executableElement, annotationHandle));
    }

    public void registerShadow(VariableElement variableElement, AnnotationHandle annotationHandle, boolean bl) {
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow = this.shadows;
        annotatedMixinElementHandlerShadow.getClass();
        this.shadows.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowField(annotatedMixinElementHandlerShadow, variableElement, annotationHandle, bl));
    }

    public void registerShadow(ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean bl) {
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow = this.shadows;
        annotatedMixinElementHandlerShadow.getClass();
        this.shadows.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowMethod(annotatedMixinElementHandlerShadow, executableElement, annotationHandle, bl));
    }

    public void registerInjector(ExecutableElement executableElement, AnnotationHandle annotationHandle, InjectorRemap injectorRemap) {
        Object object2;
        this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(executableElement, annotationHandle, injectorRemap));
        List list = annotationHandle.getAnnotationList("at");
        for (Object object2 : list) {
            this.registerInjectionPoint(executableElement, annotationHandle, (AnnotationHandle)object2, injectorRemap, "@At(%s)");
        }
        List list2 = annotationHandle.getAnnotationList("slice");
        object2 = list2.iterator();
        while (object2.hasNext()) {
            AnnotationHandle annotationHandle2;
            AnnotationHandle annotationHandle3 = (AnnotationHandle)object2.next();
            String string = (String)annotationHandle3.getValue("id", "");
            AnnotationHandle annotationHandle4 = annotationHandle3.getAnnotation("from");
            if (annotationHandle4 != null) {
                this.registerInjectionPoint(executableElement, annotationHandle, annotationHandle4, injectorRemap, "@Slice[" + string + "](from=@At(%s))");
            }
            if ((annotationHandle2 = annotationHandle3.getAnnotation("to")) == null) continue;
            this.registerInjectionPoint(executableElement, annotationHandle, annotationHandle2, injectorRemap, "@Slice[" + string + "](to=@At(%s))");
        }
    }

    public void registerInjectionPoint(ExecutableElement executableElement, AnnotationHandle annotationHandle, AnnotationHandle annotationHandle2, InjectorRemap injectorRemap, String string) {
        this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(executableElement, annotationHandle, annotationHandle2, injectorRemap), string);
    }

    public void registerAccessor(ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(executableElement, annotationHandle));
    }

    public void registerInvoker(ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(executableElement, annotationHandle));
    }

    public void registerSoftImplements(AnnotationHandle annotationHandle) {
        this.softImplements.process(annotationHandle);
    }
}

