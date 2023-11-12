/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.AnnotationWriter;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ByteVector;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

final class FieldWriter
extends FieldVisitor {
    private final ClassWriter cw;
    private final int access;
    private final int name;
    private final int desc;
    private int signature;
    private int value;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private Attribute attrs;

    FieldWriter(ClassWriter classWriter, int n, String string, String string2, String string3, Object object) {
        super(327680);
        if (classWriter.firstField == null) {
            classWriter.firstField = this;
        } else {
            classWriter.lastField.fv = this;
        }
        classWriter.lastField = this;
        this.cw = classWriter;
        this.access = n;
        this.name = classWriter.newUTF8(string);
        this.desc = classWriter.newUTF8(string2);
        if (string3 != null) {
            this.signature = classWriter.newUTF8(string3);
        }
        if (object != null) {
            this.value = classWriter.newConstItem((Object)object).index;
        }
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
        if (bl) {
            annotationWriter.next = this.anns;
            this.anns = annotationWriter;
        } else {
            annotationWriter.next = this.ianns;
            this.ianns = annotationWriter;
        }
        return annotationWriter;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (bl) {
            annotationWriter.next = this.tanns;
            this.tanns = annotationWriter;
        } else {
            annotationWriter.next = this.itanns;
            this.itanns = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitAttribute(Attribute attribute) {
        attribute.next = this.attrs;
        this.attrs = attribute;
    }

    public void visitEnd() {
    }

    int getSize() {
        int n = 8;
        if (this.value != 0) {
            this.cw.newUTF8("ConstantValue");
            n += 8;
        }
        if ((this.access & 0x1000) != 0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
            this.cw.newUTF8("Synthetic");
            n += 6;
        }
        if ((this.access & 0x20000) != 0) {
            this.cw.newUTF8("Deprecated");
            n += 6;
        }
        if (this.signature != 0) {
            this.cw.newUTF8("Signature");
            n += 8;
        }
        if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            n += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            n += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            n += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            n += 8 + this.itanns.getSize();
        }
        if (this.attrs != null) {
            n += this.attrs.getSize(this.cw, null, 0, -1, -1);
        }
        return n;
    }

    void put(ByteVector byteVector) {
        int n = 64;
        int n2 = 0x60000 | (this.access & 0x40000) / 64;
        byteVector.putShort(this.access & ~n2).putShort(this.name).putShort(this.desc);
        int n3 = 0;
        if (this.value != 0) {
            ++n3;
        }
        if ((this.access & 0x1000) != 0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
            ++n3;
        }
        if ((this.access & 0x20000) != 0) {
            ++n3;
        }
        if (this.signature != 0) {
            ++n3;
        }
        if (this.anns != null) {
            ++n3;
        }
        if (this.ianns != null) {
            ++n3;
        }
        if (this.tanns != null) {
            ++n3;
        }
        if (this.itanns != null) {
            ++n3;
        }
        if (this.attrs != null) {
            n3 += this.attrs.getCount();
        }
        byteVector.putShort(n3);
        if (this.value != 0) {
            byteVector.putShort(this.cw.newUTF8("ConstantValue"));
            byteVector.putInt(2).putShort(this.value);
        }
        if ((this.access & 0x1000) != 0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
            byteVector.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.access & 0x20000) != 0) {
            byteVector.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
        }
        if (this.signature != 0) {
            byteVector.putShort(this.cw.newUTF8("Signature"));
            byteVector.putInt(2).putShort(this.signature);
        }
        if (this.anns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(byteVector);
        }
        if (this.ianns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(byteVector);
        }
        if (this.tanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(byteVector);
        }
        if (this.itanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(byteVector);
        }
        if (this.attrs != null) {
            this.attrs.put(this.cw, null, 0, -1, -1, byteVector);
        }
    }
}

