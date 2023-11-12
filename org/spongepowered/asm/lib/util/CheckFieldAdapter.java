/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.util.CheckAnnotationAdapter;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.CheckMethodAdapter;

public class CheckFieldAdapter
extends FieldVisitor {
    private boolean end;

    public CheckFieldAdapter(FieldVisitor fieldVisitor) {
        this(327680, fieldVisitor);
        if (this.getClass() != CheckFieldAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckFieldAdapter(int n, FieldVisitor fieldVisitor) {
        super(n, fieldVisitor);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        this.checkEnd();
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(string, bl));
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.checkEnd();
        int n2 = n >>> 24;
        if (n2 != 19) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(n, typePath, string, bl));
    }

    public void visitAttribute(Attribute attribute) {
        this.checkEnd();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }

    public void visitEnd() {
        this.checkEnd();
        this.end = true;
        super.visitEnd();
    }

    private void checkEnd() {
        if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }
}

