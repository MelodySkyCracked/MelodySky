/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.util.Printer;
import org.spongepowered.asm.lib.util.TraceAnnotationVisitor;

public final class TraceMethodVisitor
extends MethodVisitor {
    public final Printer p;

    public TraceMethodVisitor(Printer printer) {
        this(null, printer);
    }

    public TraceMethodVisitor(MethodVisitor methodVisitor, Printer printer) {
        super(327680, methodVisitor);
        this.p = printer;
    }

    public void visitParameter(String string, int n) {
        this.p.visitParameter(string, n);
        super.visitParameter(string, n);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        Printer printer = this.p.visitMethodAnnotation(string, bl);
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitAnnotation(string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        Printer printer = this.p.visitMethodTypeAnnotation(n, typePath, string, bl);
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitTypeAnnotation(n, typePath, string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public void visitAttribute(Attribute attribute) {
        this.p.visitMethodAttribute(attribute);
        super.visitAttribute(attribute);
    }

    public AnnotationVisitor visitAnnotationDefault() {
        Printer printer = this.p.visitAnnotationDefault();
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitAnnotationDefault();
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        Printer printer = this.p.visitParameterAnnotation(n, string, bl);
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitParameterAnnotation(n, string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public void visitCode() {
        this.p.visitCode();
        super.visitCode();
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        this.p.visitFrame(n, n2, objectArray, n3, objectArray2);
        super.visitFrame(n, n2, objectArray, n3, objectArray2);
    }

    public void visitInsn(int n) {
        this.p.visitInsn(n);
        super.visitInsn(n);
    }

    public void visitIntInsn(int n, int n2) {
        this.p.visitIntInsn(n, n2);
        super.visitIntInsn(n, n2);
    }

    public void visitVarInsn(int n, int n2) {
        this.p.visitVarInsn(n, n2);
        super.visitVarInsn(n, n2);
    }

    public void visitTypeInsn(int n, String string) {
        this.p.visitTypeInsn(n, string);
        super.visitTypeInsn(n, string);
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.p.visitFieldInsn(n, string, string2, string3);
        super.visitFieldInsn(n, string, string2, string3);
    }

    @Deprecated
    public void visitMethodInsn(int n, String string, String string2, String string3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, string, string2, string3);
            return;
        }
        this.p.visitMethodInsn(n, string, string2, string3);
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, string, string2, string3);
        }
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, string, string2, string3, bl);
            return;
        }
        this.p.visitMethodInsn(n, string, string2, string3, bl);
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, string, string2, string3, bl);
        }
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.p.visitInvokeDynamicInsn(string, string2, handle, objectArray);
        super.visitInvokeDynamicInsn(string, string2, handle, objectArray);
    }

    public void visitJumpInsn(int n, Label label) {
        this.p.visitJumpInsn(n, label);
        super.visitJumpInsn(n, label);
    }

    public void visitLabel(Label label) {
        this.p.visitLabel(label);
        super.visitLabel(label);
    }

    public void visitLdcInsn(Object object) {
        this.p.visitLdcInsn(object);
        super.visitLdcInsn(object);
    }

    public void visitIincInsn(int n, int n2) {
        this.p.visitIincInsn(n, n2);
        super.visitIincInsn(n, n2);
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        this.p.visitTableSwitchInsn(n, n2, label, labelArray);
        super.visitTableSwitchInsn(n, n2, label, labelArray);
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        this.p.visitLookupSwitchInsn(label, nArray, labelArray);
        super.visitLookupSwitchInsn(label, nArray, labelArray);
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.p.visitMultiANewArrayInsn(string, n);
        super.visitMultiANewArrayInsn(string, n);
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        Printer printer = this.p.visitInsnAnnotation(n, typePath, string, bl);
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitInsnAnnotation(n, typePath, string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        this.p.visitTryCatchBlock(label, label2, label3, string);
        super.visitTryCatchBlock(label, label2, label3, string);
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        Printer printer = this.p.visitTryCatchAnnotation(n, typePath, string, bl);
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitTryCatchAnnotation(n, typePath, string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        this.p.visitLocalVariable(string, string2, string3, label, label2, n);
        super.visitLocalVariable(string, string2, string3, label, label2, n);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        Printer printer = this.p.visitLocalVariableAnnotation(n, typePath, labelArray, labelArray2, nArray, string, bl);
        AnnotationVisitor annotationVisitor = this.mv == null ? null : this.mv.visitLocalVariableAnnotation(n, typePath, labelArray, labelArray2, nArray, string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public void visitLineNumber(int n, Label label) {
        this.p.visitLineNumber(n, label);
        super.visitLineNumber(n, label);
    }

    public void visitMaxs(int n, int n2) {
        this.p.visitMaxs(n, n2);
        super.visitMaxs(n, n2);
    }

    public void visitEnd() {
        this.p.visitMethodEnd();
        super.visitEnd();
    }
}

