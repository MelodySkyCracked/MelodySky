/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.util.Printer;
import org.spongepowered.asm.lib.util.Textifier;
import org.spongepowered.asm.lib.util.TraceAnnotationVisitor;
import org.spongepowered.asm.lib.util.TraceFieldVisitor;
import org.spongepowered.asm.lib.util.TraceMethodVisitor;

public final class TraceClassVisitor
extends ClassVisitor {
    private final PrintWriter pw;
    public final Printer p;

    public TraceClassVisitor(PrintWriter printWriter) {
        this(null, printWriter);
    }

    public TraceClassVisitor(ClassVisitor classVisitor, PrintWriter printWriter) {
        this(classVisitor, new Textifier(), printWriter);
    }

    public TraceClassVisitor(ClassVisitor classVisitor, Printer printer, PrintWriter printWriter) {
        super(327680, classVisitor);
        this.pw = printWriter;
        this.p = printer;
    }

    public void visit(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        this.p.visit(n, n2, string, string2, string3, stringArray);
        super.visit(n, n2, string, string2, string3, stringArray);
    }

    public void visitSource(String string, String string2) {
        this.p.visitSource(string, string2);
        super.visitSource(string, string2);
    }

    public void visitOuterClass(String string, String string2, String string3) {
        this.p.visitOuterClass(string, string2, string3);
        super.visitOuterClass(string, string2, string3);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        Printer printer = this.p.visitClassAnnotation(string, bl);
        AnnotationVisitor annotationVisitor = this.cv == null ? null : this.cv.visitAnnotation(string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        Printer printer = this.p.visitClassTypeAnnotation(n, typePath, string, bl);
        AnnotationVisitor annotationVisitor = this.cv == null ? null : this.cv.visitTypeAnnotation(n, typePath, string, bl);
        return new TraceAnnotationVisitor(annotationVisitor, printer);
    }

    public void visitAttribute(Attribute attribute) {
        this.p.visitClassAttribute(attribute);
        super.visitAttribute(attribute);
    }

    public void visitInnerClass(String string, String string2, String string3, int n) {
        this.p.visitInnerClass(string, string2, string3, n);
        super.visitInnerClass(string, string2, string3, n);
    }

    public FieldVisitor visitField(int n, String string, String string2, String string3, Object object) {
        Printer printer = this.p.visitField(n, string, string2, string3, object);
        FieldVisitor fieldVisitor = this.cv == null ? null : this.cv.visitField(n, string, string2, string3, object);
        return new TraceFieldVisitor(fieldVisitor, printer);
    }

    public MethodVisitor visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        Printer printer = this.p.visitMethod(n, string, string2, string3, stringArray);
        MethodVisitor methodVisitor = this.cv == null ? null : this.cv.visitMethod(n, string, string2, string3, stringArray);
        return new TraceMethodVisitor(methodVisitor, printer);
    }

    public void visitEnd() {
        this.p.visitClassEnd();
        if (this.pw != null) {
            this.p.print(this.pw);
            this.pw.flush();
        }
        super.visitEnd();
    }
}

