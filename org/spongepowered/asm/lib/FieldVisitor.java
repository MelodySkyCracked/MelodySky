/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.TypePath;

public abstract class FieldVisitor {
    protected final int api;
    protected FieldVisitor fv;

    public FieldVisitor(int n) {
        this(n, null);
    }

    public FieldVisitor(int n, FieldVisitor fieldVisitor) {
        if (n != 262144 && n != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = n;
        this.fv = fieldVisitor;
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        if (this.fv != null) {
            return this.fv.visitAnnotation(string, bl);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.fv != null) {
            return this.fv.visitTypeAnnotation(n, typePath, string, bl);
        }
        return null;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.fv != null) {
            this.fv.visitAttribute(attribute);
        }
    }

    public void visitEnd() {
        if (this.fv != null) {
            this.fv.visitEnd();
        }
    }
}

