/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ByteVector;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Item;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

final class AnnotationWriter
extends AnnotationVisitor {
    private final ClassWriter cw;
    private int size;
    private final boolean named;
    private final ByteVector bv;
    private final ByteVector parent;
    private final int offset;
    AnnotationWriter next;
    AnnotationWriter prev;

    AnnotationWriter(ClassWriter classWriter, boolean bl, ByteVector byteVector, ByteVector byteVector2, int n) {
        super(327680);
        this.cw = classWriter;
        this.named = bl;
        this.bv = byteVector;
        this.parent = byteVector2;
        this.offset = n;
    }

    public void visit(String string, Object object) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(string));
        }
        if (object instanceof String) {
            this.bv.put12(115, this.cw.newUTF8((String)object));
        } else if (object instanceof Byte) {
            this.bv.put12(66, this.cw.newInteger((int)((Byte)object).byteValue()).index);
        } else if (object instanceof Boolean) {
            int n = (Boolean)object != false ? 1 : 0;
            this.bv.put12(90, this.cw.newInteger((int)n).index);
        } else if (object instanceof Character) {
            this.bv.put12(67, this.cw.newInteger((int)((Character)object).charValue()).index);
        } else if (object instanceof Short) {
            this.bv.put12(83, this.cw.newInteger((int)((Short)object).shortValue()).index);
        } else if (object instanceof Type) {
            this.bv.put12(99, this.cw.newUTF8(((Type)object).getDescriptor()));
        } else if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            this.bv.put12(91, byArray.length);
            for (int i = 0; i < byArray.length; ++i) {
                this.bv.put12(66, this.cw.newInteger((int)byArray[i]).index);
            }
        } else if (object instanceof boolean[]) {
            boolean[] blArray = (boolean[])object;
            this.bv.put12(91, blArray.length);
            for (int i = 0; i < blArray.length; ++i) {
                this.bv.put12(90, this.cw.newInteger((int)(blArray[i] ? 1 : 0)).index);
            }
        } else if (object instanceof short[]) {
            short[] sArray = (short[])object;
            this.bv.put12(91, sArray.length);
            for (int i = 0; i < sArray.length; ++i) {
                this.bv.put12(83, this.cw.newInteger((int)sArray[i]).index);
            }
        } else if (object instanceof char[]) {
            char[] cArray = (char[])object;
            this.bv.put12(91, cArray.length);
            for (int i = 0; i < cArray.length; ++i) {
                this.bv.put12(67, this.cw.newInteger((int)cArray[i]).index);
            }
        } else if (object instanceof int[]) {
            int[] nArray = (int[])object;
            this.bv.put12(91, nArray.length);
            for (int i = 0; i < nArray.length; ++i) {
                this.bv.put12(73, this.cw.newInteger((int)nArray[i]).index);
            }
        } else if (object instanceof long[]) {
            long[] lArray = (long[])object;
            this.bv.put12(91, lArray.length);
            for (int i = 0; i < lArray.length; ++i) {
                this.bv.put12(74, this.cw.newLong((long)lArray[i]).index);
            }
        } else if (object instanceof float[]) {
            float[] fArray = (float[])object;
            this.bv.put12(91, fArray.length);
            for (int i = 0; i < fArray.length; ++i) {
                this.bv.put12(70, this.cw.newFloat((float)fArray[i]).index);
            }
        } else if (object instanceof double[]) {
            double[] dArray = (double[])object;
            this.bv.put12(91, dArray.length);
            for (int i = 0; i < dArray.length; ++i) {
                this.bv.put12(68, this.cw.newDouble((double)dArray[i]).index);
            }
        } else {
            Item item = this.cw.newConstItem(object);
            this.bv.put12(".s.IFJDCS".charAt(item.type), item.index);
        }
    }

    public void visitEnum(String string, String string2, String string3) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(string));
        }
        this.bv.put12(101, this.cw.newUTF8(string2)).putShort(this.cw.newUTF8(string3));
    }

    public AnnotationVisitor visitAnnotation(String string, String string2) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(string));
        }
        this.bv.put12(64, this.cw.newUTF8(string2)).putShort(0);
        return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
    }

    public AnnotationVisitor visitArray(String string) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(string));
        }
        this.bv.put12(91, 0);
        return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
    }

    public void visitEnd() {
        if (this.parent != null) {
            byte[] byArray = this.parent.data;
            byArray[this.offset] = (byte)(this.size >>> 8);
            byArray[this.offset + 1] = (byte)this.size;
        }
    }

    int getSize() {
        int n = 0;
        AnnotationWriter annotationWriter = this;
        while (annotationWriter != null) {
            n += annotationWriter.bv.length;
            annotationWriter = annotationWriter.next;
        }
        return n;
    }

    void put(ByteVector byteVector) {
        int n = 0;
        int n2 = 2;
        AnnotationWriter annotationWriter = this;
        AnnotationWriter annotationWriter2 = null;
        while (annotationWriter != null) {
            ++n;
            n2 += annotationWriter.bv.length;
            annotationWriter.visitEnd();
            annotationWriter.prev = annotationWriter2;
            annotationWriter2 = annotationWriter;
            annotationWriter = annotationWriter.next;
        }
        byteVector.putInt(n2);
        byteVector.putShort(n);
        annotationWriter = annotationWriter2;
        while (annotationWriter != null) {
            byteVector.putByteArray(annotationWriter.bv.data, 0, annotationWriter.bv.length);
            annotationWriter = annotationWriter.prev;
        }
    }

    static void put(AnnotationWriter[] annotationWriterArray, int n, ByteVector byteVector) {
        int n2;
        int n3 = 1 + 2 * (annotationWriterArray.length - n);
        for (n2 = n; n2 < annotationWriterArray.length; ++n2) {
            n3 += annotationWriterArray[n2] == null ? 0 : annotationWriterArray[n2].getSize();
        }
        byteVector.putInt(n3).putByte(annotationWriterArray.length - n);
        for (n2 = n; n2 < annotationWriterArray.length; ++n2) {
            AnnotationWriter annotationWriter = annotationWriterArray[n2];
            AnnotationWriter annotationWriter2 = null;
            int n4 = 0;
            while (annotationWriter != null) {
                ++n4;
                annotationWriter.visitEnd();
                annotationWriter.prev = annotationWriter2;
                annotationWriter2 = annotationWriter;
                annotationWriter = annotationWriter.next;
            }
            byteVector.putShort(n4);
            annotationWriter = annotationWriter2;
            while (annotationWriter != null) {
                byteVector.putByteArray(annotationWriter.bv.data, 0, annotationWriter.bv.length);
                annotationWriter = annotationWriter.prev;
            }
        }
    }

    static void putTarget(int n, TypePath typePath, ByteVector byteVector) {
        switch (n >>> 24) {
            case 0: 
            case 1: 
            case 22: {
                byteVector.putShort(n >>> 16);
                break;
            }
            case 19: 
            case 20: 
            case 21: {
                byteVector.putByte(n >>> 24);
                break;
            }
            case 71: 
            case 72: 
            case 73: 
            case 74: 
            case 75: {
                byteVector.putInt(n);
                break;
            }
            default: {
                byteVector.put12(n >>> 24, (n & 0xFFFF00) >> 8);
            }
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            int n2 = typePath.b[typePath.offset] * 2 + 1;
            byteVector.putByteArray(typePath.b, typePath.offset, n2);
        }
    }
}

