/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

public abstract class AnnotationVisitor {
    protected final int api;
    protected AnnotationVisitor av;

    public AnnotationVisitor(int n) {
        this(n, null);
    }

    public AnnotationVisitor(int n, AnnotationVisitor annotationVisitor) {
        if (n != 262144 && n != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = n;
        this.av = annotationVisitor;
    }

    public void visit(String string, Object object) {
        if (this.av != null) {
            this.av.visit(string, object);
        }
    }

    public void visitEnum(String string, String string2, String string3) {
        if (this.av != null) {
            this.av.visitEnum(string, string2, string3);
        }
    }

    public AnnotationVisitor visitAnnotation(String string, String string2) {
        if (this.av != null) {
            return this.av.visitAnnotation(string, string2);
        }
        return null;
    }

    public AnnotationVisitor visitArray(String string) {
        if (this.av != null) {
            return this.av.visitArray(string);
        }
        return null;
    }

    public void visitEnd() {
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
}

