/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class ObfuscationEnvironment
implements IObfuscationEnvironment {
    protected final ObfuscationType type;
    protected final IMappingProvider mappingProvider;
    protected final IMappingWriter mappingWriter;
    protected final RemapperProxy remapper = new RemapperProxy(this);
    protected final IMixinAnnotationProcessor ap;
    protected final String outFileName;
    protected final List inFileNames;
    private boolean initDone;

    protected ObfuscationEnvironment(ObfuscationType obfuscationType) {
        this.type = obfuscationType;
        this.ap = obfuscationType.getAnnotationProcessor();
        this.inFileNames = obfuscationType.getInputFileNames();
        this.outFileName = obfuscationType.getOutputFileName();
        this.mappingProvider = this.getMappingProvider(this.ap, this.ap.getProcessingEnvironment().getFiler());
        this.mappingWriter = this.getMappingWriter(this.ap, this.ap.getProcessingEnvironment().getFiler());
    }

    public String toString() {
        return this.type.toString();
    }

    protected abstract IMappingProvider getMappingProvider(Messager var1, Filer var2);

    protected abstract IMappingWriter getMappingWriter(Messager var1, Filer var2);

    public ObfuscationType getType() {
        return this.type;
    }

    @Override
    public MappingMethod getObfMethod(MemberInfo memberInfo) {
        MappingMethod mappingMethod = this.getObfMethod(memberInfo.asMethodMapping());
        if (mappingMethod != null || !memberInfo.isFullyQualified()) {
            return mappingMethod;
        }
        TypeHandle typeHandle = this.ap.getTypeProvider().getTypeHandle(memberInfo.owner);
        if (typeHandle == null || typeHandle.isImaginary()) {
            return null;
        }
        TypeMirror typeMirror = typeHandle.getElement().getSuperclass();
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return null;
        }
        String string = ((TypeElement)((DeclaredType)typeMirror).asElement()).getQualifiedName().toString();
        return this.getObfMethod(new MemberInfo(memberInfo.name, string.replace('.', '/'), memberInfo.desc, memberInfo.matchAll));
    }

    @Override
    public MappingMethod getObfMethod(MappingMethod mappingMethod) {
        return this.getObfMethod(mappingMethod, true);
    }

    @Override
    public MappingMethod getObfMethod(MappingMethod mappingMethod, boolean bl) {
        if (this == false) {
            String string;
            boolean bl2 = true;
            MappingMethod mappingMethod2 = mappingMethod.copy();
            MappingMethod mappingMethod3 = this.mappingProvider.getMethodMapping(mappingMethod);
            if (mappingMethod3 == null) {
                if (bl) {
                    return null;
                }
                mappingMethod3 = mappingMethod2;
                bl2 = false;
            }
            if ((string = this.getObfClass(mappingMethod3.getOwner())) == null || string.equals(mappingMethod.getOwner()) || string.equals(mappingMethod3.getOwner())) {
                return bl2 ? mappingMethod3 : null;
            }
            if (bl2) {
                return mappingMethod3.move(string);
            }
            String string2 = ObfuscationUtil.mapDescriptor(mappingMethod3.getDesc(), this.remapper);
            return new MappingMethod(string, mappingMethod3.getSimpleName(), string2);
        }
        return null;
    }

    @Override
    public MemberInfo remapDescriptor(MemberInfo memberInfo) {
        String string;
        String string2;
        boolean bl = false;
        String string3 = memberInfo.owner;
        if (string3 != null && (string2 = this.remapper.map(string3)) != null) {
            string3 = string2;
            bl = true;
        }
        if ((string2 = memberInfo.desc) != null && !(string = ObfuscationUtil.mapDescriptor(memberInfo.desc, this.remapper)).equals(memberInfo.desc)) {
            string2 = string;
            bl = true;
        }
        return bl ? new MemberInfo(memberInfo.name, string3, string2, memberInfo.matchAll) : null;
    }

    @Override
    public String remapDescriptor(String string) {
        return ObfuscationUtil.mapDescriptor(string, this.remapper);
    }

    @Override
    public MappingField getObfField(MemberInfo memberInfo) {
        return this.getObfField(memberInfo.asFieldMapping(), true);
    }

    @Override
    public MappingField getObfField(MappingField mappingField) {
        return this.getObfField(mappingField, true);
    }

    @Override
    public MappingField getObfField(MappingField mappingField, boolean bl) {
        String string;
        if (this == false) {
            return null;
        }
        MappingField mappingField2 = this.mappingProvider.getFieldMapping(mappingField);
        if (mappingField2 == null) {
            if (bl) {
                return null;
            }
            mappingField2 = mappingField;
        }
        if ((string = this.getObfClass(mappingField2.getOwner())) == null || string.equals(mappingField.getOwner()) || string.equals(mappingField2.getOwner())) {
            return mappingField2 != mappingField ? mappingField2 : null;
        }
        return mappingField2.move(string);
    }

    @Override
    public String getObfClass(String string) {
        if (this == false) {
            return null;
        }
        return this.mappingProvider.getClassMapping(string);
    }

    @Override
    public void writeMappings(Collection collection) {
        IMappingConsumer.MappingSet mappingSet = new IMappingConsumer.MappingSet();
        IMappingConsumer.MappingSet mappingSet2 = new IMappingConsumer.MappingSet();
        for (IMappingConsumer iMappingConsumer : collection) {
            mappingSet.addAll(iMappingConsumer.getFieldMappings(this.type));
            mappingSet2.addAll(iMappingConsumer.getMethodMappings(this.type));
        }
        this.mappingWriter.write(this.outFileName, this.type, mappingSet, mappingSet2);
    }

    final class RemapperProxy
    implements ObfuscationUtil.IClassRemapper {
        final ObfuscationEnvironment this$0;

        RemapperProxy(ObfuscationEnvironment obfuscationEnvironment) {
            this.this$0 = obfuscationEnvironment;
        }

        @Override
        public String map(String string) {
            if (this.this$0.mappingProvider == null) {
                return null;
            }
            return this.this$0.mappingProvider.getClassMapping(string);
        }

        @Override
        public String unmap(String string) {
            if (this.this$0.mappingProvider == null) {
                return null;
            }
            return this.this$0.mappingProvider.getClassMapping(string);
        }
    }
}

