/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.AnnotatedMixin;
import org.spongepowered.tools.obfuscation.Mappings;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

abstract class AnnotatedMixinElementHandler {
    protected final AnnotatedMixin mixin;
    protected final String classRef;
    protected final IMixinAnnotationProcessor ap;
    protected final IObfuscationManager obf;
    private IMappingConsumer mappings;

    AnnotatedMixinElementHandler(IMixinAnnotationProcessor iMixinAnnotationProcessor, AnnotatedMixin annotatedMixin) {
        this.ap = iMixinAnnotationProcessor;
        this.mixin = annotatedMixin;
        this.classRef = annotatedMixin.getClassRef();
        this.obf = iMixinAnnotationProcessor.getObfuscationManager();
    }

    private IMappingConsumer getMappings() {
        if (this.mappings == null) {
            IMappingConsumer iMappingConsumer = this.mixin.getMappings();
            this.mappings = iMappingConsumer instanceof Mappings ? ((Mappings)iMappingConsumer).asUnique() : iMappingConsumer;
        }
        return this.mappings;
    }

    protected final void addFieldMappings(String string, String string2, ObfuscationData obfuscationData) {
        for (ObfuscationType obfuscationType : obfuscationData) {
            MappingField mappingField = (MappingField)obfuscationData.get(obfuscationType);
            this.addFieldMapping(obfuscationType, string, mappingField.getSimpleName(), string2, mappingField.getDesc());
        }
    }

    protected final void addFieldMapping(ObfuscationType obfuscationType, ShadowElementName shadowElementName, String string, String string2) {
        this.addFieldMapping(obfuscationType, shadowElementName.name(), shadowElementName.obfuscated(), string, string2);
    }

    protected final void addFieldMapping(ObfuscationType obfuscationType, String string, String string2, String string3, String string4) {
        MappingField mappingField = new MappingField(this.classRef, string, string3);
        MappingField mappingField2 = new MappingField(this.classRef, string2, string4);
        this.getMappings().addFieldMapping(obfuscationType, mappingField, mappingField2);
    }

    protected final void addMethodMappings(String string, String string2, ObfuscationData obfuscationData) {
        for (ObfuscationType obfuscationType : obfuscationData) {
            MappingMethod mappingMethod = (MappingMethod)obfuscationData.get(obfuscationType);
            this.addMethodMapping(obfuscationType, string, mappingMethod.getSimpleName(), string2, mappingMethod.getDesc());
        }
    }

    protected final void addMethodMapping(ObfuscationType obfuscationType, ShadowElementName shadowElementName, String string, String string2) {
        this.addMethodMapping(obfuscationType, shadowElementName.name(), shadowElementName.obfuscated(), string, string2);
    }

    protected final void addMethodMapping(ObfuscationType obfuscationType, String string, String string2, String string3, String string4) {
        MappingMethod mappingMethod = new MappingMethod(this.classRef, string, string3);
        MappingMethod mappingMethod2 = new MappingMethod(this.classRef, string2, string4);
        this.getMappings().addMethodMapping(obfuscationType, mappingMethod, mappingMethod2);
    }

    protected final void checkConstraints(ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        try {
            ConstraintParser.Constraint constraint = ConstraintParser.parse((String)annotationHandle.getValue("constraints"));
            try {
                constraint.check(this.ap.getTokenProvider());
            }
            catch (ConstraintViolationException constraintViolationException) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, constraintViolationException.getMessage(), executableElement, annotationHandle.asMirror());
            }
        }
        catch (InvalidConstraintException invalidConstraintException) {
            this.ap.printMessage(Diagnostic.Kind.WARNING, invalidConstraintException.getMessage(), executableElement, annotationHandle.asMirror());
        }
    }

    protected final void validateTarget(Element element, AnnotationHandle annotationHandle, AliasedElementName aliasedElementName, String string) {
        if (element instanceof ExecutableElement) {
            this.validateTargetMethod((ExecutableElement)element, annotationHandle, aliasedElementName, string);
        } else if (element instanceof VariableElement) {
            this.validateTargetField((VariableElement)element, annotationHandle, aliasedElementName, string);
        }
    }

    protected final void validateTargetMethod(ExecutableElement executableElement, AnnotationHandle annotationHandle, AliasedElementName aliasedElementName, String string) {
        String string2 = TypeUtils.getJavaSignature(executableElement);
        for (TypeHandle typeHandle : this.mixin.getTargets()) {
            String string3;
            MethodHandle methodHandle;
            if (typeHandle.isImaginary() || (methodHandle = typeHandle.findMethod(executableElement)) != null || !aliasedElementName.baseName().equals(aliasedElementName.elementName()) && (methodHandle = typeHandle.findMethod(aliasedElementName.baseName(), string2)) != null) continue;
            Iterator iterator = aliasedElementName.getAliases().iterator();
            while (iterator.hasNext() && (methodHandle = typeHandle.findMethod(string3 = (String)iterator.next(), string2)) == null) {
            }
            if (methodHandle != null) continue;
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + string + " method in " + typeHandle, executableElement, annotationHandle.asMirror());
        }
    }

    protected final void validateTargetField(VariableElement variableElement, AnnotationHandle annotationHandle, AliasedElementName aliasedElementName, String string) {
        String string2 = variableElement.asType().toString();
        for (TypeHandle typeHandle : this.mixin.getTargets()) {
            String string3;
            FieldHandle fieldHandle;
            if (typeHandle.isImaginary() || (fieldHandle = typeHandle.findField(variableElement)) != null) continue;
            List list = aliasedElementName.getAliases();
            Iterator iterator = list.iterator();
            while (iterator.hasNext() && (fieldHandle = typeHandle.findField(string3 = (String)iterator.next(), string2)) == null) {
            }
            if (fieldHandle != null) continue;
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + string + " field in " + typeHandle, variableElement, annotationHandle.asMirror());
        }
    }

    protected final void validateReferencedTarget(ExecutableElement executableElement, AnnotationHandle annotationHandle, MemberInfo memberInfo, String string) {
        String string2 = memberInfo.toDescriptor();
        for (TypeHandle typeHandle : this.mixin.getTargets()) {
            MethodHandle methodHandle;
            if (typeHandle.isImaginary() || (methodHandle = typeHandle.findMethod(memberInfo.name, string2)) != null) continue;
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + string + " in " + typeHandle, executableElement, annotationHandle.asMirror());
        }
    }

    protected static ObfuscationData stripOwnerData(ObfuscationData obfuscationData) {
        ObfuscationData obfuscationData2 = new ObfuscationData();
        for (ObfuscationType obfuscationType : obfuscationData) {
            IMapping iMapping = (IMapping)obfuscationData.get(obfuscationType);
            obfuscationData2.add(obfuscationType, iMapping.move(null));
        }
        return obfuscationData2;
    }

    static class ShadowElementName
    extends AliasedElementName {
        private final boolean hasPrefix;
        private final String prefix;
        private final String baseName;
        private String obfuscated;

        ShadowElementName(Element element, AnnotationHandle annotationHandle) {
            super(element, annotationHandle);
            this.prefix = (String)annotationHandle.getValue("prefix", "shadow$");
            boolean bl = false;
            String string = this.originalName;
            if (string.startsWith(this.prefix)) {
                bl = true;
                string = string.substring(this.prefix.length());
            }
            this.hasPrefix = bl;
            this.obfuscated = this.baseName = string;
        }

        public String toString() {
            return this.baseName;
        }

        @Override
        public String baseName() {
            return this.baseName;
        }

        public ShadowElementName setObfuscatedName(IMapping iMapping) {
            this.obfuscated = iMapping.getName();
            return this;
        }

        public ShadowElementName setObfuscatedName(String string) {
            this.obfuscated = string;
            return this;
        }

        public String prefix() {
            return this.hasPrefix ? this.prefix : "";
        }

        public String name() {
            return this.prefix(this.baseName);
        }

        public String obfuscated() {
            return this.prefix(this.obfuscated);
        }

        public String prefix(String string) {
            return this.hasPrefix ? this.prefix + string : string;
        }
    }

    static class AliasedElementName {
        protected final String originalName;
        private final List aliases;
        private boolean caseSensitive;

        public AliasedElementName(Element element, AnnotationHandle annotationHandle) {
            this.originalName = element.getSimpleName().toString();
            this.aliases = annotationHandle.getList("aliases");
        }

        public AliasedElementName setCaseSensitive(boolean bl) {
            this.caseSensitive = bl;
            return this;
        }

        public boolean isCaseSensitive() {
            return this.caseSensitive;
        }

        public boolean hasAliases() {
            return this.aliases.size() > 0;
        }

        public List getAliases() {
            return this.aliases;
        }

        public String elementName() {
            return this.originalName;
        }

        public String baseName() {
            return this.originalName;
        }
    }

    static abstract class AnnotatedElement {
        private final Element element;
        private final AnnotationHandle annotation;
        private final String desc;

        public AnnotatedElement(Element element, AnnotationHandle annotationHandle) {
            this.element = element;
            this.annotation = annotationHandle;
            this.desc = TypeUtils.getDescriptor(element);
        }

        public Element getElement() {
            return this.element;
        }

        public AnnotationHandle getAnnotation() {
            return this.annotation;
        }

        public String getSimpleName() {
            return this.getElement().getSimpleName().toString();
        }

        public String getDesc() {
            return this.desc;
        }

        public final void printMessage(Messager messager, Diagnostic.Kind kind, CharSequence charSequence) {
            messager.printMessage(kind, charSequence, this.element, this.annotation.asMirror());
        }
    }
}

