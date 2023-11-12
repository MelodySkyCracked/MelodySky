/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.AnnotatedMixin;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.Mappings;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow
extends AnnotatedMixinElementHandler {
    AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor iMixinAnnotationProcessor, AnnotatedMixin annotatedMixin) {
        super(iMixinAnnotationProcessor, annotatedMixin);
    }

    public void registerShadow(AnnotatedElementShadow annotatedElementShadow) {
        this.validateTarget(annotatedElementShadow.getElement(), annotatedElementShadow.getAnnotation(), annotatedElementShadow.getName(), "@Shadow");
        if (!annotatedElementShadow.shouldRemap()) {
            return;
        }
        for (TypeHandle typeHandle : this.mixin.getTargets()) {
            this.registerShadowForTarget(annotatedElementShadow, typeHandle);
        }
    }

    private void registerShadowForTarget(AnnotatedElementShadow annotatedElementShadow, TypeHandle typeHandle) {
        ObfuscationData obfuscationData = annotatedElementShadow.getObfuscationData(this.obf.getDataProvider(), typeHandle.getName());
        if (obfuscationData.isEmpty()) {
            String string;
            String string2 = string = this.mixin.isMultiTarget() ? " in target " + typeHandle : "";
            if (typeHandle.isSimulated()) {
                annotatedElementShadow.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + string + " for @Shadow " + annotatedElementShadow);
            } else {
                annotatedElementShadow.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + string + " for @Shadow " + annotatedElementShadow);
            }
            return;
        }
        for (ObfuscationType obfuscationType : obfuscationData) {
            try {
                annotatedElementShadow.addMapping(obfuscationType, (IMapping)obfuscationData.get(obfuscationType));
            }
            catch (Mappings.MappingConflictException mappingConflictException) {
                annotatedElementShadow.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + annotatedElementShadow + ": " + mappingConflictException.getNew().getSimpleName() + " for target " + typeHandle + " conflicts with existing mapping " + mappingConflictException.getOld().getSimpleName());
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    class AnnotatedElementShadowMethod
    extends AnnotatedElementShadow {
        final AnnotatedMixinElementHandlerShadow this$0;

        public AnnotatedElementShadowMethod(AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow, ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean bl) {
            this.this$0 = annotatedMixinElementHandlerShadow;
            super(executableElement, annotationHandle, bl, IMapping.Type.METHOD);
        }

        @Override
        public MappingMethod getMapping(String string, String string2, String string3) {
            return new MappingMethod(string, string2, string3);
        }

        @Override
        public void addMapping(ObfuscationType obfuscationType, IMapping iMapping) {
            this.this$0.addMethodMapping(obfuscationType, this.setObfuscatedName(iMapping), this.getDesc(), iMapping.getDesc());
        }

        @Override
        public IMapping getMapping(String string, String string2, String string3) {
            return this.getMapping(string, string2, string3);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    class AnnotatedElementShadowField
    extends AnnotatedElementShadow {
        final AnnotatedMixinElementHandlerShadow this$0;

        public AnnotatedElementShadowField(AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow, VariableElement variableElement, AnnotationHandle annotationHandle, boolean bl) {
            this.this$0 = annotatedMixinElementHandlerShadow;
            super(variableElement, annotationHandle, bl, IMapping.Type.FIELD);
        }

        @Override
        public MappingField getMapping(String string, String string2, String string3) {
            return new MappingField(string, string2, string3);
        }

        @Override
        public void addMapping(ObfuscationType obfuscationType, IMapping iMapping) {
            this.this$0.addFieldMapping(obfuscationType, this.setObfuscatedName(iMapping), this.getDesc(), iMapping.getDesc());
        }

        @Override
        public IMapping getMapping(String string, String string2, String string3) {
            return this.getMapping(string, string2, string3);
        }
    }

    static abstract class AnnotatedElementShadow
    extends AnnotatedMixinElementHandler.AnnotatedElement {
        private final boolean shouldRemap;
        private final AnnotatedMixinElementHandler.ShadowElementName name;
        private final IMapping.Type type;

        protected AnnotatedElementShadow(Element element, AnnotationHandle annotationHandle, boolean bl, IMapping.Type type) {
            super(element, annotationHandle);
            this.shouldRemap = bl;
            this.name = new AnnotatedMixinElementHandler.ShadowElementName(element, annotationHandle);
            this.type = type;
        }

        public boolean shouldRemap() {
            return this.shouldRemap;
        }

        public AnnotatedMixinElementHandler.ShadowElementName getName() {
            return this.name;
        }

        public IMapping.Type getElementType() {
            return this.type;
        }

        public String toString() {
            return this.getElementType().name().toLowerCase();
        }

        public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping iMapping) {
            return this.setObfuscatedName(iMapping.getSimpleName());
        }

        public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String string) {
            return this.getName().setObfuscatedName(string);
        }

        public ObfuscationData getObfuscationData(IObfuscationDataProvider iObfuscationDataProvider, String string) {
            return iObfuscationDataProvider.getObfEntry(this.getMapping(string, this.getName().toString(), this.getDesc()));
        }

        public abstract IMapping getMapping(String var1, String var2, String var3);

        public abstract void addMapping(ObfuscationType var1, IMapping var2);
    }
}

