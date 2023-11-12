/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.mirror;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TypeHandleSimulated
extends TypeHandle {
    private final TypeElement simulatedType;

    public TypeHandleSimulated(String string, TypeMirror typeMirror) {
        this(TypeUtils.getPackage(typeMirror), string, typeMirror);
    }

    public TypeHandleSimulated(PackageElement packageElement, String string, TypeMirror typeMirror) {
        super(packageElement, string);
        this.simulatedType = (TypeElement)((DeclaredType)typeMirror).asElement();
    }

    @Override
    public TypeElement getElement() {
        return null;
    }

    @Override
    protected TypeElement getTargetElement() {
        return this.simulatedType;
    }

    @Override
    public boolean isPublic() {
        return true;
    }

    @Override
    public boolean isImaginary() {
        return false;
    }

    @Override
    public boolean isSimulated() {
        return true;
    }

    @Override
    public AnnotationHandle getAnnotation(Class clazz) {
        return null;
    }

    @Override
    public TypeHandle getSuperclass() {
        return null;
    }

    @Override
    public String findDescriptor(MemberInfo memberInfo) {
        return memberInfo != null ? memberInfo.desc : null;
    }

    @Override
    public FieldHandle findField(String string, String string2, boolean bl) {
        return new FieldHandle(null, string, string2);
    }

    @Override
    public MethodHandle findMethod(String string, String string2, boolean bl) {
        return new MethodHandle(null, string, string2);
    }

    @Override
    public MappingMethod getMappingMethod(String string, String string2) {
        String string3 = new SignaturePrinter(string, string2).setFullyQualified(true).toDescriptor();
        String string4 = TypeUtils.stripGenerics(string3);
        MethodHandle methodHandle = TypeHandleSimulated.findMethodRecursive(this.getTargetElement(), string, string3, string4, true);
        return methodHandle != null ? methodHandle.asMapping(true) : super.getMappingMethod(string, string2);
    }

    private static MethodHandle findMethodRecursive(TypeElement typeElement, String string, String string2, String string3, boolean bl) {
        if (typeElement == null) {
            return null;
        }
        MethodHandle methodHandle = TypeHandle.findMethod(typeElement, string, string2, string3, bl);
        if (methodHandle != null) {
            return methodHandle;
        }
        for (TypeMirror typeMirror : typeElement.getInterfaces()) {
            methodHandle = TypeHandleSimulated.findMethodRecursive(typeMirror, string, string2, string3, bl);
            if (methodHandle == null) continue;
            return methodHandle;
        }
        TypeMirror typeMirror = typeElement.getSuperclass();
        if (typeMirror == null || typeMirror.getKind() == TypeKind.NONE) {
            return null;
        }
        return TypeHandleSimulated.findMethodRecursive(typeMirror, string, string2, string3, bl);
    }

    private static MethodHandle findMethodRecursive(TypeMirror typeMirror, String string, String string2, String string3, boolean bl) {
        if (!(typeMirror instanceof DeclaredType)) {
            return null;
        }
        return TypeHandleSimulated.findMethodRecursive((TypeElement)((DeclaredType)typeMirror).asElement(), string, string2, string3, bl);
    }
}

