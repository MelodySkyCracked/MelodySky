/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TypeHandle {
    private final String name;
    private final PackageElement pkg;
    private final TypeElement element;
    private TypeReference reference;

    public TypeHandle(PackageElement packageElement, String string) {
        this.name = string.replace('.', '/');
        this.pkg = packageElement;
        this.element = null;
    }

    public TypeHandle(TypeElement typeElement) {
        this.pkg = TypeUtils.getPackage(typeElement);
        this.name = TypeUtils.getInternalName(typeElement);
        this.element = typeElement;
    }

    public TypeHandle(DeclaredType declaredType) {
        this((TypeElement)declaredType.asElement());
    }

    public final String toString() {
        return this.name.replace('/', '.');
    }

    public final String getName() {
        return this.name;
    }

    public final PackageElement getPackage() {
        return this.pkg;
    }

    public TypeElement getElement() {
        return this.element;
    }

    protected TypeElement getTargetElement() {
        return this.element;
    }

    public AnnotationHandle getAnnotation(Class clazz) {
        return AnnotationHandle.of(this.getTargetElement(), clazz);
    }

    public final List getEnclosedElements() {
        return TypeHandle.getEnclosedElements(this.getTargetElement());
    }

    public List getEnclosedElements(ElementKind ... elementKindArray) {
        return TypeHandle.getEnclosedElements(this.getTargetElement(), elementKindArray);
    }

    public TypeMirror getType() {
        return this.getTargetElement() != null ? this.getTargetElement().asType() : null;
    }

    public TypeHandle getSuperclass() {
        TypeElement typeElement = this.getTargetElement();
        if (typeElement == null) {
            return null;
        }
        TypeMirror typeMirror = typeElement.getSuperclass();
        if (typeMirror == null || typeMirror.getKind() == TypeKind.NONE) {
            return null;
        }
        return new TypeHandle((DeclaredType)typeMirror);
    }

    public List getInterfaces() {
        if (this.getTargetElement() == null) {
            return Collections.emptyList();
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (TypeMirror typeMirror : this.getTargetElement().getInterfaces()) {
            builder.add((Object)new TypeHandle((DeclaredType)typeMirror));
        }
        return builder.build();
    }

    public boolean isPublic() {
        return this.getTargetElement() != null ? this.getTargetElement().getModifiers().contains((Object)Modifier.PUBLIC) : false;
    }

    public boolean isImaginary() {
        return this.getTargetElement() == null;
    }

    public boolean isSimulated() {
        return false;
    }

    public final TypeReference getReference() {
        if (this.reference == null) {
            this.reference = new TypeReference(this);
        }
        return this.reference;
    }

    public MappingMethod getMappingMethod(String string, String string2) {
        return new MappingMethod(this.getName(), string, string2);
    }

    public String findDescriptor(MemberInfo memberInfo) {
        String string = memberInfo.desc;
        if (string == null) {
            for (ExecutableElement executableElement : this.getEnclosedElements(ElementKind.METHOD)) {
                if (!executableElement.getSimpleName().toString().equals(memberInfo.name)) continue;
                string = TypeUtils.getDescriptor(executableElement);
                break;
            }
        }
        return string;
    }

    public final FieldHandle findField(VariableElement variableElement) {
        return this.findField(variableElement, true);
    }

    public final FieldHandle findField(VariableElement variableElement, boolean bl) {
        return this.findField(variableElement.getSimpleName().toString(), TypeUtils.getTypeName(variableElement.asType()), bl);
    }

    public final FieldHandle findField(String string, String string2) {
        return this.findField(string, string2, true);
    }

    /*
     * Exception decompiling
     */
    public FieldHandle findField(String var1, String var2, boolean var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl14 : ALOAD - null : trying to set 6 previously set to 0
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

    public final MethodHandle findMethod(ExecutableElement executableElement) {
        return this.findMethod(executableElement, true);
    }

    public final MethodHandle findMethod(ExecutableElement executableElement, boolean bl) {
        return this.findMethod(executableElement.getSimpleName().toString(), TypeUtils.getJavaSignature(executableElement), bl);
    }

    public final MethodHandle findMethod(String string, String string2) {
        return this.findMethod(string, string2, true);
    }

    public MethodHandle findMethod(String string, String string2, boolean bl) {
        String string3 = TypeUtils.stripGenerics(string2);
        return TypeHandle.findMethod(this.getTargetElement(), string, string2, string3, bl);
    }

    /*
     * Exception decompiling
     */
    protected static MethodHandle findMethod(TypeElement var0, String var1, String var2, String var3, boolean var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl15 : ALOAD - null : trying to set 6 previously set to 0
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

    protected static List getEnclosedElements(TypeElement typeElement, ElementKind ... elementKindArray) {
        if (elementKindArray == null || elementKindArray.length < 1) {
            return TypeHandle.getEnclosedElements(typeElement);
        }
        if (typeElement == null) {
            return Collections.emptyList();
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        block0: for (Element element : typeElement.getEnclosedElements()) {
            for (ElementKind elementKind : elementKindArray) {
                if (element.getKind() != elementKind) continue;
                builder.add((Object)element);
                continue block0;
            }
        }
        return builder.build();
    }

    protected static List getEnclosedElements(TypeElement typeElement) {
        return typeElement != null ? typeElement.getEnclosedElements() : Collections.emptyList();
    }
}

