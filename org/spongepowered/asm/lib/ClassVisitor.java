/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public abstract class ClassVisitor {
    protected final int api;
    protected ClassVisitor cv;

    public ClassVisitor(int n) {
        this(n, null);
    }

    public ClassVisitor(int n, ClassVisitor classVisitor) {
        if (n != 262144 && n != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = n;
        this.cv = classVisitor;
    }

    public void visit(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        if (this.cv != null) {
            this.cv.visit(n, n2, string, string2, string3, stringArray);
        }
    }

    public void visitSource(String string, String string2) {
        if (this.cv != null) {
            this.cv.visitSource(string, string2);
        }
    }

    public void visitOuterClass(String string, String string2, String string3) {
        if (this.cv != null) {
            this.cv.visitOuterClass(string, string2, string3);
        }
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        if (this.cv != null) {
            return this.cv.visitAnnotation(string, bl);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.cv != null) {
            return this.cv.visitTypeAnnotation(n, typePath, string, bl);
        }
        return null;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.cv != null) {
            this.cv.visitAttribute(attribute);
        }
    }

    public void visitInnerClass(String string, String string2, String string3, int n) {
        if (this.cv != null) {
            this.cv.visitInnerClass(string, string2, string3, n);
        }
    }

    public FieldVisitor visitField(int n, String string, String string2, String string3, Object object) {
        if (this.cv != null) {
            return this.cv.visitField(n, string, string2, string3, object);
        }
        return null;
    }

    public MethodVisitor visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        if (this.cv != null) {
            return this.cv.visitMethod(n, string, string2, string3, stringArray);
        }
        return null;
    }

    public void visitEnd() {
        if (this.cv != null) {
            this.cv.visitEnd();
        }
    }
}

