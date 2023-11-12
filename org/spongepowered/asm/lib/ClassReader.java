/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import java.io.IOException;
import java.io.InputStream;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ByteVector;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Context;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Item;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.MethodWriter;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class ClassReader {
    static final boolean SIGNATURES = true;
    static final boolean ANNOTATIONS = true;
    static final boolean FRAMES = true;
    static final boolean WRITER = true;
    static final boolean RESIZE = true;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    public static final int EXPAND_FRAMES = 8;
    public final byte[] b;
    private final int[] items;
    private final String[] strings;
    private final int maxStringLength;
    public final int header;

    public ClassReader(byte[] byArray) {
        this(byArray, 0, byArray.length);
    }

    public ClassReader(byte[] byArray, int n, int n2) {
        this.b = byArray;
        if (this.readShort(n + 6) > 52) {
            throw new IllegalArgumentException();
        }
        this.items = new int[this.readUnsignedShort(n + 8)];
        int n3 = this.items.length;
        this.strings = new String[n3];
        int n4 = 0;
        int n5 = n + 10;
        for (int i = 1; i < n3; ++i) {
            int n6;
            this.items[i] = n5 + 1;
            switch (byArray[n5]) {
                case 3: 
                case 4: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 18: {
                    n6 = 5;
                    break;
                }
                case 5: 
                case 6: {
                    n6 = 9;
                    ++i;
                    break;
                }
                case 1: {
                    n6 = 3 + this.readUnsignedShort(n5 + 1);
                    if (n6 <= n4) break;
                    n4 = n6;
                    break;
                }
                case 15: {
                    n6 = 4;
                    break;
                }
                default: {
                    n6 = 3;
                }
            }
            n5 += n6;
        }
        this.maxStringLength = n4;
        this.header = n5;
    }

    public int getAccess() {
        return this.readUnsignedShort(this.header);
    }

    public String getClassName() {
        return this.readClass(this.header + 2, new char[this.maxStringLength]);
    }

    public String getSuperName() {
        return this.readClass(this.header + 4, new char[this.maxStringLength]);
    }

    public String[] getInterfaces() {
        int n = this.header + 6;
        int n2 = this.readUnsignedShort(n);
        String[] stringArray = new String[n2];
        if (n2 > 0) {
            char[] cArray = new char[this.maxStringLength];
            for (int i = 0; i < n2; ++i) {
                stringArray[i] = this.readClass(n += 2, cArray);
            }
        }
        return stringArray;
    }

    void copyPool(ClassWriter classWriter) {
        int n;
        char[] cArray = new char[this.maxStringLength];
        int n2 = this.items.length;
        Item[] itemArray = new Item[n2];
        for (n = 1; n < n2; ++n) {
            int n3;
            int n4 = this.items[n];
            byte by = this.b[n4 - 1];
            Item item = new Item(n);
            switch (by) {
                case 9: 
                case 10: 
                case 11: {
                    int n5 = this.items[this.readUnsignedShort(n4 + 2)];
                    item.set(by, this.readClass(n4, cArray), this.readUTF8(n5, cArray), this.readUTF8(n5 + 2, cArray));
                    break;
                }
                case 3: {
                    item.set(this.readInt(n4));
                    break;
                }
                case 4: {
                    item.set(Float.intBitsToFloat(this.readInt(n4)));
                    break;
                }
                case 12: {
                    item.set(by, this.readUTF8(n4, cArray), this.readUTF8(n4 + 2, cArray), null);
                    break;
                }
                case 5: {
                    item.set(this.readLong(n4));
                    ++n;
                    break;
                }
                case 6: {
                    item.set(Double.longBitsToDouble(this.readLong(n4)));
                    ++n;
                    break;
                }
                case 1: {
                    String string = this.strings[n];
                    if (string == null) {
                        n4 = this.items[n];
                        string = this.strings[n] = this.readUTF(n4 + 2, this.readUnsignedShort(n4), cArray);
                    }
                    item.set(by, string, null, null);
                    break;
                }
                case 15: {
                    n3 = this.items[this.readUnsignedShort(n4 + 1)];
                    int n5 = this.items[this.readUnsignedShort(n3 + 2)];
                    item.set(20 + this.readByte(n4), this.readClass(n3, cArray), this.readUTF8(n5, cArray), this.readUTF8(n5 + 2, cArray));
                    break;
                }
                case 18: {
                    if (classWriter.bootstrapMethods == null) {
                        this.copyBootstrapMethods(classWriter, itemArray, cArray);
                    }
                    int n5 = this.items[this.readUnsignedShort(n4 + 2)];
                    item.set(this.readUTF8(n5, cArray), this.readUTF8(n5 + 2, cArray), this.readUnsignedShort(n4));
                    break;
                }
                default: {
                    item.set(by, this.readUTF8(n4, cArray), null, null);
                }
            }
            n3 = item.hashCode % itemArray.length;
            item.next = itemArray[n3];
            itemArray[n3] = item;
        }
        n = this.items[1] - 1;
        classWriter.pool.putByteArray(this.b, n, this.header - n);
        classWriter.items = itemArray;
        classWriter.threshold = (int)(0.75 * (double)n2);
        classWriter.index = n2;
    }

    private void copyBootstrapMethods(ClassWriter classWriter, Item[] itemArray, char[] cArray) {
        int n;
        int n2;
        int n3 = this.getAttributes();
        boolean bl = false;
        for (n2 = this.readUnsignedShort(n3); n2 > 0; --n2) {
            String string = this.readUTF8(n3 + 2, cArray);
            if ("BootstrapMethods".equals(string)) {
                bl = true;
                break;
            }
            n3 += 6 + this.readInt(n3 + 4);
        }
        if (!bl) {
            return;
        }
        n2 = this.readUnsignedShort(n3 + 8);
        int n4 = n3 + 10;
        for (n = 0; n < n2; ++n) {
            int n5 = n4 - n3 - 10;
            int n6 = this.readConst(this.readUnsignedShort(n4), cArray).hashCode();
            for (int i = this.readUnsignedShort(n4 + 2); i > 0; --i) {
                n6 ^= this.readConst(this.readUnsignedShort(n4 + 4), cArray).hashCode();
                n4 += 2;
            }
            n4 += 4;
            Item item = new Item(n);
            item.set(n5, n6 & Integer.MAX_VALUE);
            int n7 = item.hashCode % itemArray.length;
            item.next = itemArray[n7];
            itemArray[n7] = item;
        }
        n = this.readInt(n3 + 4);
        ByteVector byteVector = new ByteVector(n + 62);
        byteVector.putByteArray(this.b, n3 + 10, n - 2);
        classWriter.bootstrapMethodsCount = n2;
        classWriter.bootstrapMethods = byteVector;
    }

    public ClassReader(InputStream inputStream) throws IOException {
        this(ClassReader.readClass(inputStream, false));
    }

    public ClassReader(String string) throws IOException {
        this(ClassReader.readClass(ClassLoader.getSystemResourceAsStream(string.replace('.', '/') + ".class"), true));
    }

    private static byte[] readClass(InputStream inputStream, boolean bl) throws IOException {
        if (inputStream == null) {
            throw new IOException("Class not found");
        }
        byte[] byArray = new byte[inputStream.available()];
        int n = 0;
        while (true) {
            byte[] byArray2;
            int n2;
            if ((n2 = inputStream.read(byArray, n, byArray.length - n)) == -1) {
                byte[] byArray3;
                if (n < byArray.length) {
                    byArray3 = new byte[n];
                    System.arraycopy(byArray, 0, byArray3, 0, n);
                    byArray = byArray3;
                }
                byArray3 = byArray;
                if (bl) {
                    inputStream.close();
                }
                return byArray3;
            }
            if ((n += n2) != byArray.length) continue;
            int n3 = inputStream.read();
            if (n3 < 0) {
                byArray2 = byArray;
                if (bl) {
                    inputStream.close();
                }
                return byArray2;
            }
            byArray2 = new byte[byArray.length + 1000];
            System.arraycopy(byArray, 0, byArray2, 0, n);
            byArray2[n++] = (byte)n3;
            byArray = byArray2;
        }
    }

    public void accept(ClassVisitor classVisitor, int n) {
        this.accept(classVisitor, new Attribute[0], n);
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attributeArray, int n) {
        int n2;
        int n3 = this.header;
        char[] cArray = new char[this.maxStringLength];
        Context context = new Context();
        context.attrs = attributeArray;
        context.flags = n;
        context.buffer = cArray;
        int n4 = this.readUnsignedShort(n3);
        String string = this.readClass(n3 + 2, cArray);
        String string2 = this.readClass(n3 + 4, cArray);
        String[] stringArray = new String[this.readUnsignedShort(n3 + 6)];
        n3 += 8;
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = this.readClass(n3, cArray);
            n3 += 2;
        }
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        Attribute attribute = null;
        n3 = this.getAttributes();
        for (n2 = this.readUnsignedShort(n3); n2 > 0; --n2) {
            String string9 = this.readUTF8(n3 + 2, cArray);
            if ("SourceFile".equals(string9)) {
                string4 = this.readUTF8(n3 + 8, cArray);
            } else if ("InnerClasses".equals(string9)) {
                n9 = n3 + 8;
            } else if ("EnclosingMethod".equals(string9)) {
                string6 = this.readClass(n3 + 8, cArray);
                int n10 = this.readUnsignedShort(n3 + 10);
                if (n10 != 0) {
                    string7 = this.readUTF8(this.items[n10], cArray);
                    string8 = this.readUTF8(this.items[n10] + 2, cArray);
                }
            } else if ("Signature".equals(string9)) {
                string3 = this.readUTF8(n3 + 8, cArray);
            } else if ("RuntimeVisibleAnnotations".equals(string9)) {
                n5 = n3 + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(string9)) {
                n7 = n3 + 8;
            } else if ("Deprecated".equals(string9)) {
                n4 |= 0x20000;
            } else if ("Synthetic".equals(string9)) {
                n4 |= 0x41000;
            } else if ("SourceDebugExtension".equals(string9)) {
                int n11 = this.readInt(n3 + 4);
                string5 = this.readUTF(n3 + 8, n11, new char[n11]);
            } else if ("RuntimeInvisibleAnnotations".equals(string9)) {
                n6 = n3 + 8;
            } else if ("RuntimeInvisibleTypeAnnotations".equals(string9)) {
                n8 = n3 + 8;
            } else if ("BootstrapMethods".equals(string9)) {
                int[] nArray = new int[this.readUnsignedShort(n3 + 8)];
                int n12 = n3 + 10;
                for (int i = 0; i < nArray.length; ++i) {
                    nArray[i] = n12;
                    n12 += 2 + this.readUnsignedShort(n12 + 2) << 1;
                }
                context.bootstrapMethods = nArray;
            } else {
                Attribute attribute2 = this.readAttribute(attributeArray, string9, n3 + 8, this.readInt(n3 + 4), cArray, -1, null);
                if (attribute2 != null) {
                    attribute2.next = attribute;
                    attribute = attribute2;
                }
            }
            n3 += 6 + this.readInt(n3 + 4);
        }
        classVisitor.visit(this.readInt(this.items[1] - 7), n4, string, string3, string2, stringArray);
        if ((n & 2) == 0 && (string4 != null || string5 != null)) {
            classVisitor.visitSource(string4, string5);
        }
        if (string6 != null) {
            classVisitor.visitOuterClass(string6, string7, string8);
        }
        if (n5 != 0) {
            int n13 = n5 + 2;
            for (n2 = this.readUnsignedShort(n5); n2 > 0; --n2) {
                n13 = this.readAnnotationValues(n13 + 2, cArray, true, classVisitor.visitAnnotation(this.readUTF8(n13, cArray), true));
            }
        }
        if (n6 != 0) {
            int n14 = n6 + 2;
            for (n2 = this.readUnsignedShort(n6); n2 > 0; --n2) {
                n14 = this.readAnnotationValues(n14 + 2, cArray, true, classVisitor.visitAnnotation(this.readUTF8(n14, cArray), false));
            }
        }
        if (n7 != 0) {
            int n15 = n7 + 2;
            for (n2 = this.readUnsignedShort(n7); n2 > 0; --n2) {
                n15 = this.readAnnotationTarget(context, n15);
                n15 = this.readAnnotationValues(n15 + 2, cArray, true, classVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(n15, cArray), true));
            }
        }
        if (n8 != 0) {
            int n16 = n8 + 2;
            for (n2 = this.readUnsignedShort(n8); n2 > 0; --n2) {
                n16 = this.readAnnotationTarget(context, n16);
                n16 = this.readAnnotationValues(n16 + 2, cArray, true, classVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(n16, cArray), false));
            }
        }
        while (attribute != null) {
            Attribute attribute3 = attribute.next;
            attribute.next = null;
            classVisitor.visitAttribute(attribute);
            attribute = attribute3;
        }
        if (n9 != 0) {
            n2 = n9 + 2;
            for (int i = this.readUnsignedShort(n9); i > 0; --i) {
                classVisitor.visitInnerClass(this.readClass(n2, cArray), this.readClass(n2 + 2, cArray), this.readUTF8(n2 + 4, cArray), this.readUnsignedShort(n2 + 6));
                n2 += 8;
            }
        }
        n3 = this.header + 10 + 2 * stringArray.length;
        for (n2 = this.readUnsignedShort(n3 - 2); n2 > 0; --n2) {
            n3 = this.readField(classVisitor, context, n3);
        }
        for (n2 = this.readUnsignedShort((n3 += 2) - 2); n2 > 0; --n2) {
            n3 = this.readMethod(classVisitor, context, n3);
        }
        classVisitor.visitEnd();
    }

    private int readField(ClassVisitor classVisitor, Context context, int n) {
        int n2;
        char[] cArray = context.buffer;
        int n3 = this.readUnsignedShort(n);
        String string = this.readUTF8(n + 2, cArray);
        String string2 = this.readUTF8(n + 4, cArray);
        n += 6;
        String string3 = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        Object object = null;
        Attribute attribute = null;
        for (int i = this.readUnsignedShort(n); i > 0; --i) {
            String string4 = this.readUTF8(n + 2, cArray);
            if ("ConstantValue".equals(string4)) {
                n2 = this.readUnsignedShort(n + 8);
                object = n2 == 0 ? null : this.readConst(n2, cArray);
            } else if ("Signature".equals(string4)) {
                string3 = this.readUTF8(n + 8, cArray);
            } else if ("Deprecated".equals(string4)) {
                n3 |= 0x20000;
            } else if ("Synthetic".equals(string4)) {
                n3 |= 0x41000;
            } else if ("RuntimeVisibleAnnotations".equals(string4)) {
                n4 = n + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(string4)) {
                n6 = n + 8;
            } else if ("RuntimeInvisibleAnnotations".equals(string4)) {
                n5 = n + 8;
            } else if ("RuntimeInvisibleTypeAnnotations".equals(string4)) {
                n7 = n + 8;
            } else {
                Attribute attribute2 = this.readAttribute(context.attrs, string4, n + 8, this.readInt(n + 4), cArray, -1, null);
                if (attribute2 != null) {
                    attribute2.next = attribute;
                    attribute = attribute2;
                }
            }
            n += 6 + this.readInt(n + 4);
        }
        n += 2;
        FieldVisitor fieldVisitor = classVisitor.visitField(n3, string, string2, string3, object);
        if (fieldVisitor == null) {
            return n;
        }
        if (n4 != 0) {
            n2 = n4 + 2;
            for (int i = this.readUnsignedShort(n4); i > 0; --i) {
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, fieldVisitor.visitAnnotation(this.readUTF8(n2, cArray), true));
            }
        }
        if (n5 != 0) {
            n2 = n5 + 2;
            for (int i = this.readUnsignedShort(n5); i > 0; --i) {
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, fieldVisitor.visitAnnotation(this.readUTF8(n2, cArray), false));
            }
        }
        if (n6 != 0) {
            n2 = n6 + 2;
            for (int i = this.readUnsignedShort(n6); i > 0; --i) {
                n2 = this.readAnnotationTarget(context, n2);
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, fieldVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(n2, cArray), true));
            }
        }
        if (n7 != 0) {
            n2 = n7 + 2;
            for (int i = this.readUnsignedShort(n7); i > 0; --i) {
                n2 = this.readAnnotationTarget(context, n2);
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, fieldVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(n2, cArray), false));
            }
        }
        while (attribute != null) {
            Attribute attribute3 = attribute.next;
            attribute.next = null;
            fieldVisitor.visitAttribute(attribute);
            attribute = attribute3;
        }
        fieldVisitor.visitEnd();
        return n;
    }

    private int readMethod(ClassVisitor classVisitor, Context context, int n) {
        int n2;
        Object object;
        char[] cArray = context.buffer;
        context.access = this.readUnsignedShort(n);
        context.name = this.readUTF8(n + 2, cArray);
        context.desc = this.readUTF8(n + 4, cArray);
        n += 6;
        int n3 = 0;
        int n4 = 0;
        String[] stringArray = null;
        String string = null;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = n;
        Attribute attribute = null;
        for (int i = this.readUnsignedShort(n); i > 0; --i) {
            object = this.readUTF8(n + 2, cArray);
            if ("Code".equals(object)) {
                if ((context.flags & 1) == 0) {
                    n3 = n + 8;
                }
            } else if ("Exceptions".equals(object)) {
                stringArray = new String[this.readUnsignedShort(n + 8)];
                n4 = n + 10;
                for (n2 = 0; n2 < stringArray.length; ++n2) {
                    stringArray[n2] = this.readClass(n4, cArray);
                    n4 += 2;
                }
            } else if ("Signature".equals(object)) {
                string = this.readUTF8(n + 8, cArray);
            } else if ("Deprecated".equals(object)) {
                context.access |= 0x20000;
            } else if ("RuntimeVisibleAnnotations".equals(object)) {
                n6 = n + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(object)) {
                n8 = n + 8;
            } else if ("AnnotationDefault".equals(object)) {
                n10 = n + 8;
            } else if ("Synthetic".equals(object)) {
                context.access |= 0x41000;
            } else if ("RuntimeInvisibleAnnotations".equals(object)) {
                n7 = n + 8;
            } else if ("RuntimeInvisibleTypeAnnotations".equals(object)) {
                n9 = n + 8;
            } else if ("RuntimeVisibleParameterAnnotations".equals(object)) {
                n11 = n + 8;
            } else if ("RuntimeInvisibleParameterAnnotations".equals(object)) {
                n12 = n + 8;
            } else if ("MethodParameters".equals(object)) {
                n5 = n + 8;
            } else {
                Attribute attribute2 = this.readAttribute(context.attrs, (String)object, n + 8, this.readInt(n + 4), cArray, -1, null);
                if (attribute2 != null) {
                    attribute2.next = attribute;
                    attribute = attribute2;
                }
            }
            n += 6 + this.readInt(n + 4);
        }
        n += 2;
        MethodVisitor methodVisitor = classVisitor.visitMethod(context.access, context.name, context.desc, string, stringArray);
        if (methodVisitor == null) {
            return n;
        }
        if (methodVisitor instanceof MethodWriter) {
            object = (MethodWriter)methodVisitor;
            if (((MethodWriter)object).cw.cr == this && string == ((MethodWriter)object).signature) {
                n2 = 0;
                if (stringArray == null) {
                    n2 = ((MethodWriter)object).exceptionCount == 0 ? 1 : 0;
                } else if (stringArray.length == ((MethodWriter)object).exceptionCount) {
                    n2 = 1;
                    for (int i = stringArray.length - 1; i >= 0; --i) {
                        if (((MethodWriter)object).exceptions[i] == this.readUnsignedShort(n4 -= 2)) continue;
                        n2 = 0;
                        break;
                    }
                }
                if (n2 != 0) {
                    ((MethodWriter)object).classReaderOffset = n13;
                    ((MethodWriter)object).classReaderLength = n - n13;
                    return n;
                }
            }
        }
        if (n5 != 0) {
            int n14 = this.b[n5] & 0xFF;
            n2 = n5 + 1;
            while (n14 > 0) {
                methodVisitor.visitParameter(this.readUTF8(n2, cArray), this.readUnsignedShort(n2 + 2));
                --n14;
                n2 += 4;
            }
        }
        if (n10 != 0) {
            AnnotationVisitor annotationVisitor = methodVisitor.visitAnnotationDefault();
            this.readAnnotationValue(n10, cArray, null, annotationVisitor);
            if (annotationVisitor != null) {
                annotationVisitor.visitEnd();
            }
        }
        if (n6 != 0) {
            n2 = n6 + 2;
            for (int i = this.readUnsignedShort(n6); i > 0; --i) {
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, methodVisitor.visitAnnotation(this.readUTF8(n2, cArray), true));
            }
        }
        if (n7 != 0) {
            n2 = n7 + 2;
            for (int i = this.readUnsignedShort(n7); i > 0; --i) {
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, methodVisitor.visitAnnotation(this.readUTF8(n2, cArray), false));
            }
        }
        if (n8 != 0) {
            n2 = n8 + 2;
            for (int i = this.readUnsignedShort(n8); i > 0; --i) {
                n2 = this.readAnnotationTarget(context, n2);
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, methodVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(n2, cArray), true));
            }
        }
        if (n9 != 0) {
            n2 = n9 + 2;
            for (int i = this.readUnsignedShort(n9); i > 0; --i) {
                n2 = this.readAnnotationTarget(context, n2);
                n2 = this.readAnnotationValues(n2 + 2, cArray, true, methodVisitor.visitTypeAnnotation(context.typeRef, context.typePath, this.readUTF8(n2, cArray), false));
            }
        }
        if (n11 != 0) {
            this.readParameterAnnotations(methodVisitor, context, n11, true);
        }
        if (n12 != 0) {
            this.readParameterAnnotations(methodVisitor, context, n12, false);
        }
        while (attribute != null) {
            Attribute attribute3 = attribute.next;
            attribute.next = null;
            methodVisitor.visitAttribute(attribute);
            attribute = attribute3;
        }
        if (n3 != 0) {
            methodVisitor.visitCode();
            this.readCode(methodVisitor, context, n3);
        }
        methodVisitor.visitEnd();
        return n;
    }

    private void readCode(MethodVisitor methodVisitor, Context context, int n) {
        int n2;
        int n3;
        int n4;
        Object object;
        int n5;
        int n6;
        byte[] byArray = this.b;
        char[] cArray = context.buffer;
        int n7 = this.readUnsignedShort(n);
        int n8 = this.readUnsignedShort(n + 2);
        int n9 = this.readInt(n + 4);
        int n10 = n += 8;
        int n11 = n + n9;
        context.labels = new Label[n9 + 2];
        Label[] labelArray = context.labels;
        this.readLabel(n9 + 1, labelArray);
        block29: while (n < n11) {
            n6 = n - n10;
            int n12 = byArray[n] & 0xFF;
            switch (ClassWriter.TYPE[n12]) {
                case 0: 
                case 4: {
                    ++n;
                    continue block29;
                }
                case 9: {
                    this.readLabel(n6 + this.readShort(n + 1), labelArray);
                    n += 3;
                    continue block29;
                }
                case 10: {
                    this.readLabel(n6 + this.readInt(n + 1), labelArray);
                    n += 5;
                    continue block29;
                }
                case 17: {
                    n12 = byArray[n + 1] & 0xFF;
                    if (n12 == 132) {
                        n += 6;
                        continue block29;
                    }
                    n += 4;
                    continue block29;
                }
                case 14: {
                    int n13;
                    n = n + 4 - (n6 & 3);
                    this.readLabel(n6 + this.readInt(n), labelArray);
                    for (n13 = this.readInt(n + 8) - this.readInt(n + 4) + 1; n13 > 0; --n13) {
                        this.readLabel(n6 + this.readInt(n + 12), labelArray);
                        n += 4;
                    }
                    n += 12;
                    continue block29;
                }
                case 15: {
                    int n13;
                    n = n + 4 - (n6 & 3);
                    this.readLabel(n6 + this.readInt(n), labelArray);
                    for (n13 = this.readInt(n + 4); n13 > 0; --n13) {
                        this.readLabel(n6 + this.readInt(n + 12), labelArray);
                        n += 8;
                    }
                    n += 8;
                    continue block29;
                }
                case 1: 
                case 3: 
                case 11: {
                    n += 2;
                    continue block29;
                }
                case 2: 
                case 5: 
                case 6: 
                case 12: 
                case 13: {
                    n += 3;
                    continue block29;
                }
                case 7: 
                case 8: {
                    n += 5;
                    continue block29;
                }
            }
            n += 4;
        }
        for (n6 = this.readUnsignedShort(n); n6 > 0; --n6) {
            Label label = this.readLabel(this.readUnsignedShort(n + 2), labelArray);
            Label label2 = this.readLabel(this.readUnsignedShort(n + 4), labelArray);
            Label label3 = this.readLabel(this.readUnsignedShort(n + 6), labelArray);
            String string = this.readUTF8(this.items[this.readUnsignedShort(n + 8)], cArray);
            methodVisitor.visitTryCatchBlock(label, label2, label3, string);
            n += 8;
        }
        n += 2;
        int[] nArray = null;
        int[] nArray2 = null;
        int n14 = 0;
        int n15 = 0;
        int n16 = -1;
        int n17 = -1;
        int n18 = 0;
        int n19 = 0;
        boolean bl = true;
        boolean bl2 = (context.flags & 8) != 0;
        int n20 = 0;
        int n21 = 0;
        int n22 = 0;
        Context context2 = null;
        Attribute attribute = null;
        for (n5 = this.readUnsignedShort(n); n5 > 0; --n5) {
            object = this.readUTF8(n + 2, cArray);
            if ("LocalVariableTable".equals(object)) {
                if ((context.flags & 2) == 0) {
                    n18 = n + 8;
                    n4 = n;
                    for (n3 = this.readUnsignedShort(n + 8); n3 > 0; --n3) {
                        n2 = this.readUnsignedShort(n4 + 10);
                        if (labelArray[n2] == null) {
                            this.readLabel((int)n2, (Label[])labelArray).status |= 1;
                        }
                        if (labelArray[n2 += this.readUnsignedShort(n4 + 12)] == null) {
                            this.readLabel((int)n2, (Label[])labelArray).status |= 1;
                        }
                        n4 += 10;
                    }
                }
            } else if ("LocalVariableTypeTable".equals(object)) {
                n19 = n + 8;
            } else if ("LineNumberTable".equals(object)) {
                if ((context.flags & 2) == 0) {
                    n4 = n;
                    for (n3 = this.readUnsignedShort(n + 8); n3 > 0; --n3) {
                        n2 = this.readUnsignedShort(n4 + 10);
                        if (labelArray[n2] == null) {
                            this.readLabel((int)n2, (Label[])labelArray).status |= 1;
                        }
                        labelArray[n2].line = this.readUnsignedShort(n4 + 12);
                        n4 += 4;
                    }
                }
            } else if ("RuntimeVisibleTypeAnnotations".equals(object)) {
                nArray = this.readTypeAnnotations(methodVisitor, context, n + 8, true);
                n16 = nArray.length == 0 || this.readByte(nArray[0]) < 67 ? -1 : this.readUnsignedShort(nArray[0] + 1);
            } else if ("RuntimeInvisibleTypeAnnotations".equals(object)) {
                nArray2 = this.readTypeAnnotations(methodVisitor, context, n + 8, false);
                n17 = nArray2.length == 0 || this.readByte(nArray2[0]) < 67 ? -1 : this.readUnsignedShort(nArray2[0] + 1);
            } else if ("StackMapTable".equals(object)) {
                if ((context.flags & 4) == 0) {
                    n20 = n + 10;
                    n21 = this.readInt(n + 4);
                    n22 = this.readUnsignedShort(n + 8);
                }
            } else if ("StackMap".equals(object)) {
                if ((context.flags & 4) == 0) {
                    bl = false;
                    n20 = n + 10;
                    n21 = this.readInt(n + 4);
                    n22 = this.readUnsignedShort(n + 8);
                }
            } else {
                for (n3 = 0; n3 < context.attrs.length; ++n3) {
                    Attribute attribute2;
                    if (!context.attrs[n3].type.equals(object) || (attribute2 = context.attrs[n3].read(this, n + 8, this.readInt(n + 4), cArray, n10 - 8, labelArray)) == null) continue;
                    attribute2.next = attribute;
                    attribute = attribute2;
                }
            }
            n += 6 + this.readInt(n + 4);
        }
        n += 2;
        if (n20 != 0) {
            context2 = context;
            context2.offset = -1;
            context2.mode = 0;
            context2.localCount = 0;
            context2.localDiff = 0;
            context2.stackCount = 0;
            context2.local = new Object[n8];
            context2.stack = new Object[n7];
            if (bl2) {
                this.getImplicitFrame(context);
            }
            for (n5 = n20; n5 < n20 + n21 - 2; ++n5) {
                int n23;
                if (byArray[n5] != 8 || (n23 = this.readUnsignedShort(n5 + 1)) < 0 || n23 >= n9 || (byArray[n10 + n23] & 0xFF) != 187) continue;
                this.readLabel(n23, labelArray);
            }
        }
        n = n10;
        while (n < n11) {
            n5 = n - n10;
            object = labelArray[n5];
            if (object != null) {
                methodVisitor.visitLabel((Label)object);
                if ((context.flags & 2) == 0 && ((Label)object).line > 0) {
                    methodVisitor.visitLineNumber(((Label)object).line, (Label)object);
                }
            }
            while (context2 != null && (context2.offset == n5 || context2.offset == -1)) {
                if (context2.offset != -1) {
                    if (!bl || bl2) {
                        methodVisitor.visitFrame(-1, context2.localCount, context2.local, context2.stackCount, context2.stack);
                    } else {
                        methodVisitor.visitFrame(context2.mode, context2.localDiff, context2.local, context2.stackCount, context2.stack);
                    }
                }
                if (n22 > 0) {
                    n20 = this.readFrame(n20, bl, bl2, context2);
                    --n22;
                    continue;
                }
                context2 = null;
            }
            n3 = byArray[n] & 0xFF;
            switch (ClassWriter.TYPE[n3]) {
                case 0: {
                    methodVisitor.visitInsn(n3);
                    ++n;
                    break;
                }
                case 4: {
                    if (n3 > 54) {
                        methodVisitor.visitVarInsn(54 + ((n3 -= 59) >> 2), n3 & 3);
                    } else {
                        methodVisitor.visitVarInsn(21 + ((n3 -= 26) >> 2), n3 & 3);
                    }
                    ++n;
                    break;
                }
                case 9: {
                    methodVisitor.visitJumpInsn(n3, labelArray[n5 + this.readShort(n + 1)]);
                    n += 3;
                    break;
                }
                case 10: {
                    methodVisitor.visitJumpInsn(n3 - 33, labelArray[n5 + this.readInt(n + 1)]);
                    n += 5;
                    break;
                }
                case 17: {
                    n3 = byArray[n + 1] & 0xFF;
                    if (n3 == 132) {
                        methodVisitor.visitIincInsn(this.readUnsignedShort(n + 2), this.readShort(n + 4));
                        n += 6;
                        break;
                    }
                    methodVisitor.visitVarInsn(n3, this.readUnsignedShort(n + 2));
                    n += 4;
                    break;
                }
                case 14: {
                    n = n + 4 - (n5 & 3);
                    n4 = n5 + this.readInt(n);
                    n2 = this.readInt(n + 4);
                    int n24 = this.readInt(n + 8);
                    Label[] labelArray2 = new Label[n24 - n2 + 1];
                    n += 12;
                    for (int i = 0; i < labelArray2.length; ++i) {
                        labelArray2[i] = labelArray[n5 + this.readInt(n)];
                        n += 4;
                    }
                    methodVisitor.visitTableSwitchInsn(n2, n24, labelArray[n4], labelArray2);
                    break;
                }
                case 15: {
                    n = n + 4 - (n5 & 3);
                    n4 = n5 + this.readInt(n);
                    n2 = this.readInt(n + 4);
                    int[] nArray3 = new int[n2];
                    Label[] labelArray3 = new Label[n2];
                    n += 8;
                    for (int i = 0; i < n2; ++i) {
                        nArray3[i] = this.readInt(n);
                        labelArray3[i] = labelArray[n5 + this.readInt(n + 4)];
                        n += 8;
                    }
                    methodVisitor.visitLookupSwitchInsn(labelArray[n4], nArray3, labelArray3);
                    break;
                }
                case 3: {
                    methodVisitor.visitVarInsn(n3, byArray[n + 1] & 0xFF);
                    n += 2;
                    break;
                }
                case 1: {
                    methodVisitor.visitIntInsn(n3, byArray[n + 1]);
                    n += 2;
                    break;
                }
                case 2: {
                    methodVisitor.visitIntInsn(n3, this.readShort(n + 1));
                    n += 3;
                    break;
                }
                case 11: {
                    methodVisitor.visitLdcInsn(this.readConst(byArray[n + 1] & 0xFF, cArray));
                    n += 2;
                    break;
                }
                case 12: {
                    methodVisitor.visitLdcInsn(this.readConst(this.readUnsignedShort(n + 1), cArray));
                    n += 3;
                    break;
                }
                case 6: 
                case 7: {
                    n4 = this.items[this.readUnsignedShort(n + 1)];
                    n2 = byArray[n4 - 1] == 11 ? 1 : 0;
                    String string = this.readClass(n4, cArray);
                    n4 = this.items[this.readUnsignedShort(n4 + 2)];
                    String string2 = this.readUTF8(n4, cArray);
                    String string3 = this.readUTF8(n4 + 2, cArray);
                    if (n3 < 182) {
                        methodVisitor.visitFieldInsn(n3, string, string2, string3);
                    } else {
                        methodVisitor.visitMethodInsn(n3, string, string2, string3, n2 != 0);
                    }
                    if (n3 == 185) {
                        n += 5;
                        break;
                    }
                    n += 3;
                    break;
                }
                case 8: {
                    n4 = this.items[this.readUnsignedShort(n + 1)];
                    n2 = context.bootstrapMethods[this.readUnsignedShort(n4)];
                    Handle handle = (Handle)this.readConst(this.readUnsignedShort(n2), cArray);
                    int n25 = this.readUnsignedShort(n2 + 2);
                    Object[] objectArray = new Object[n25];
                    n2 += 4;
                    for (int i = 0; i < n25; ++i) {
                        objectArray[i] = this.readConst(this.readUnsignedShort(n2), cArray);
                        n2 += 2;
                    }
                    n4 = this.items[this.readUnsignedShort(n4 + 2)];
                    String string = this.readUTF8(n4, cArray);
                    String string4 = this.readUTF8(n4 + 2, cArray);
                    methodVisitor.visitInvokeDynamicInsn(string, string4, handle, objectArray);
                    n += 5;
                    break;
                }
                case 5: {
                    methodVisitor.visitTypeInsn(n3, this.readClass(n + 1, cArray));
                    n += 3;
                    break;
                }
                case 13: {
                    methodVisitor.visitIincInsn(byArray[n + 1] & 0xFF, byArray[n + 2]);
                    n += 3;
                    break;
                }
                default: {
                    methodVisitor.visitMultiANewArrayInsn(this.readClass(n + 1, cArray), byArray[n + 3] & 0xFF);
                    n += 4;
                }
            }
            while (nArray != null && n14 < nArray.length && n16 <= n5) {
                if (n16 == n5) {
                    n4 = this.readAnnotationTarget(context, nArray[n14]);
                    this.readAnnotationValues(n4 + 2, cArray, true, methodVisitor.visitInsnAnnotation(context.typeRef, context.typePath, this.readUTF8(n4, cArray), true));
                }
                n16 = ++n14 >= nArray.length || this.readByte(nArray[n14]) < 67 ? -1 : this.readUnsignedShort(nArray[n14] + 1);
            }
            while (nArray2 != null && n15 < nArray2.length && n17 <= n5) {
                if (n17 == n5) {
                    n4 = this.readAnnotationTarget(context, nArray2[n15]);
                    this.readAnnotationValues(n4 + 2, cArray, true, methodVisitor.visitInsnAnnotation(context.typeRef, context.typePath, this.readUTF8(n4, cArray), false));
                }
                n17 = ++n15 >= nArray2.length || this.readByte(nArray2[n15]) < 67 ? -1 : this.readUnsignedShort(nArray2[n15] + 1);
            }
        }
        if (labelArray[n9] != null) {
            methodVisitor.visitLabel(labelArray[n9]);
        }
        if ((context.flags & 2) == 0 && n18 != 0) {
            int[] nArray4 = null;
            if (n19 != 0) {
                n = n19 + 2;
                nArray4 = new int[this.readUnsignedShort(n19) * 3];
                int n26 = nArray4.length;
                while (n26 > 0) {
                    nArray4[--n26] = n + 6;
                    nArray4[--n26] = this.readUnsignedShort(n + 8);
                    nArray4[--n26] = this.readUnsignedShort(n);
                    n += 10;
                }
            }
            n = n18 + 2;
            for (int i = this.readUnsignedShort(n18); i > 0; --i) {
                n3 = this.readUnsignedShort(n);
                n4 = this.readUnsignedShort(n + 2);
                n2 = this.readUnsignedShort(n + 8);
                String string = null;
                if (nArray4 != null) {
                    for (int j = 0; j < nArray4.length; j += 3) {
                        if (nArray4[j] != n3 || nArray4[j + 1] != n2) continue;
                        string = this.readUTF8(nArray4[j + 2], cArray);
                        break;
                    }
                }
                methodVisitor.visitLocalVariable(this.readUTF8(n + 4, cArray), this.readUTF8(n + 6, cArray), string, labelArray[n3], labelArray[n3 + n4], n2);
                n += 10;
            }
        }
        if (nArray != null) {
            for (int i = 0; i < nArray.length; ++i) {
                if (this.readByte((int)nArray[i]) >> 1 != 32) continue;
                int n27 = this.readAnnotationTarget(context, nArray[i]);
                n27 = this.readAnnotationValues(n27 + 2, cArray, true, methodVisitor.visitLocalVariableAnnotation(context.typeRef, context.typePath, context.start, context.end, context.index, this.readUTF8(n27, cArray), true));
            }
        }
        if (nArray2 != null) {
            for (int i = 0; i < nArray2.length; ++i) {
                if (this.readByte((int)nArray2[i]) >> 1 != 32) continue;
                int n28 = this.readAnnotationTarget(context, nArray2[i]);
                n28 = this.readAnnotationValues(n28 + 2, cArray, true, methodVisitor.visitLocalVariableAnnotation(context.typeRef, context.typePath, context.start, context.end, context.index, this.readUTF8(n28, cArray), false));
            }
        }
        while (attribute != null) {
            Attribute attribute3 = attribute.next;
            attribute.next = null;
            methodVisitor.visitAttribute(attribute);
            attribute = attribute3;
        }
        methodVisitor.visitMaxs(n7, n8);
    }

    private int[] readTypeAnnotations(MethodVisitor methodVisitor, Context context, int n, boolean bl) {
        char[] cArray = context.buffer;
        int[] nArray = new int[this.readUnsignedShort(n)];
        n += 2;
        for (int i = 0; i < nArray.length; ++i) {
            int n2;
            nArray[i] = n;
            int n3 = this.readInt(n);
            switch (n3 >>> 24) {
                case 0: 
                case 1: 
                case 22: {
                    n += 2;
                    break;
                }
                case 19: 
                case 20: 
                case 21: {
                    ++n;
                    break;
                }
                case 64: 
                case 65: {
                    for (n2 = this.readUnsignedShort(n + 1); n2 > 0; --n2) {
                        int n4 = this.readUnsignedShort(n + 3);
                        int n5 = this.readUnsignedShort(n + 5);
                        this.readLabel(n4, context.labels);
                        this.readLabel(n4 + n5, context.labels);
                        n += 6;
                    }
                    n += 3;
                    break;
                }
                case 71: 
                case 72: 
                case 73: 
                case 74: 
                case 75: {
                    n += 4;
                    break;
                }
                default: {
                    n += 3;
                }
            }
            n2 = this.readByte(n);
            if (n3 >>> 24 == 66) {
                TypePath typePath = n2 == 0 ? null : new TypePath(this.b, n);
                n += 1 + 2 * n2;
                n = this.readAnnotationValues(n + 2, cArray, true, methodVisitor.visitTryCatchAnnotation(n3, typePath, this.readUTF8(n, cArray), bl));
                continue;
            }
            n = this.readAnnotationValues(n + 3 + 2 * n2, cArray, true, null);
        }
        return nArray;
    }

    private int readAnnotationTarget(Context context, int n) {
        int n2;
        int n3 = this.readInt(n);
        switch (n3 >>> 24) {
            case 0: 
            case 1: 
            case 22: {
                n3 &= 0xFFFF0000;
                n += 2;
                break;
            }
            case 19: 
            case 20: 
            case 21: {
                n3 &= 0xFF000000;
                ++n;
                break;
            }
            case 64: 
            case 65: {
                n3 &= 0xFF000000;
                n2 = this.readUnsignedShort(n + 1);
                context.start = new Label[n2];
                context.end = new Label[n2];
                context.index = new int[n2];
                n += 3;
                for (int i = 0; i < n2; ++i) {
                    int n4 = this.readUnsignedShort(n);
                    int n5 = this.readUnsignedShort(n + 2);
                    context.start[i] = this.readLabel(n4, context.labels);
                    context.end[i] = this.readLabel(n4 + n5, context.labels);
                    context.index[i] = this.readUnsignedShort(n + 4);
                    n += 6;
                }
                break;
            }
            case 71: 
            case 72: 
            case 73: 
            case 74: 
            case 75: {
                n3 &= 0xFF0000FF;
                n += 4;
                break;
            }
            default: {
                n3 &= n3 >>> 24 < 67 ? -256 : -16777216;
                n += 3;
            }
        }
        n2 = this.readByte(n);
        context.typeRef = n3;
        context.typePath = n2 == 0 ? null : new TypePath(this.b, n);
        return n + 1 + 2 * n2;
    }

    private void readParameterAnnotations(MethodVisitor methodVisitor, Context context, int n, boolean bl) {
        AnnotationVisitor annotationVisitor;
        int n2;
        int n3 = this.b[n++] & 0xFF;
        int n4 = Type.getArgumentTypes(context.desc).length - n3;
        for (n2 = 0; n2 < n4; ++n2) {
            annotationVisitor = methodVisitor.visitParameterAnnotation(n2, "Ljava/lang/Synthetic;", false);
            if (annotationVisitor == null) continue;
            annotationVisitor.visitEnd();
        }
        char[] cArray = context.buffer;
        while (n2 < n3 + n4) {
            int n5 = this.readUnsignedShort(n);
            n += 2;
            while (n5 > 0) {
                annotationVisitor = methodVisitor.visitParameterAnnotation(n2, this.readUTF8(n, cArray), bl);
                n = this.readAnnotationValues(n + 2, cArray, true, annotationVisitor);
                --n5;
            }
            ++n2;
        }
    }

    private int readAnnotationValues(int n, char[] cArray, boolean bl, AnnotationVisitor annotationVisitor) {
        int n2 = this.readUnsignedShort(n);
        n += 2;
        if (bl) {
            while (n2 > 0) {
                n = this.readAnnotationValue(n + 2, cArray, this.readUTF8(n, cArray), annotationVisitor);
                --n2;
            }
        } else {
            while (n2 > 0) {
                n = this.readAnnotationValue(n, cArray, null, annotationVisitor);
                --n2;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return n;
    }

    private int readAnnotationValue(int n, char[] cArray, String string, AnnotationVisitor annotationVisitor) {
        if (annotationVisitor == null) {
            switch (this.b[n] & 0xFF) {
                case 101: {
                    return n + 5;
                }
                case 64: {
                    return this.readAnnotationValues(n + 3, cArray, true, null);
                }
                case 91: {
                    return this.readAnnotationValues(n + 1, cArray, false, null);
                }
            }
            return n + 3;
        }
        block5 : switch (this.b[n++] & 0xFF) {
            case 68: 
            case 70: 
            case 73: 
            case 74: {
                annotationVisitor.visit(string, this.readConst(this.readUnsignedShort(n), cArray));
                n += 2;
                break;
            }
            case 66: {
                annotationVisitor.visit(string, new Byte((byte)this.readInt(this.items[this.readUnsignedShort(n)])));
                n += 2;
                break;
            }
            case 90: {
                annotationVisitor.visit(string, this.readInt(this.items[this.readUnsignedShort(n)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
                n += 2;
                break;
            }
            case 83: {
                annotationVisitor.visit(string, new Short((short)this.readInt(this.items[this.readUnsignedShort(n)])));
                n += 2;
                break;
            }
            case 67: {
                annotationVisitor.visit(string, new Character((char)this.readInt(this.items[this.readUnsignedShort(n)])));
                n += 2;
                break;
            }
            case 115: {
                annotationVisitor.visit(string, this.readUTF8(n, cArray));
                n += 2;
                break;
            }
            case 101: {
                annotationVisitor.visitEnum(string, this.readUTF8(n, cArray), this.readUTF8(n + 2, cArray));
                n += 4;
                break;
            }
            case 99: {
                annotationVisitor.visit(string, Type.getType(this.readUTF8(n, cArray)));
                n += 2;
                break;
            }
            case 64: {
                n = this.readAnnotationValues(n + 2, cArray, true, annotationVisitor.visitAnnotation(string, this.readUTF8(n, cArray)));
                break;
            }
            case 91: {
                int n2 = this.readUnsignedShort(n);
                n += 2;
                if (n2 == 0) {
                    return this.readAnnotationValues(n - 2, cArray, false, annotationVisitor.visitArray(string));
                }
                switch (this.b[n++] & 0xFF) {
                    case 66: {
                        byte[] byArray = new byte[n2];
                        for (int i = 0; i < n2; ++i) {
                            byArray[i] = (byte)this.readInt(this.items[this.readUnsignedShort(n)]);
                            n += 3;
                        }
                        annotationVisitor.visit(string, byArray);
                        --n;
                        break block5;
                    }
                    case 90: {
                        boolean[] blArray = new boolean[n2];
                        for (int i = 0; i < n2; ++i) {
                            blArray[i] = this.readInt(this.items[this.readUnsignedShort(n)]) != 0;
                            n += 3;
                        }
                        annotationVisitor.visit(string, blArray);
                        --n;
                        break block5;
                    }
                    case 83: {
                        short[] sArray = new short[n2];
                        for (int i = 0; i < n2; ++i) {
                            sArray[i] = (short)this.readInt(this.items[this.readUnsignedShort(n)]);
                            n += 3;
                        }
                        annotationVisitor.visit(string, sArray);
                        --n;
                        break block5;
                    }
                    case 67: {
                        char[] cArray2 = new char[n2];
                        for (int i = 0; i < n2; ++i) {
                            cArray2[i] = (char)this.readInt(this.items[this.readUnsignedShort(n)]);
                            n += 3;
                        }
                        annotationVisitor.visit(string, cArray2);
                        --n;
                        break block5;
                    }
                    case 73: {
                        int[] nArray = new int[n2];
                        for (int i = 0; i < n2; ++i) {
                            nArray[i] = this.readInt(this.items[this.readUnsignedShort(n)]);
                            n += 3;
                        }
                        annotationVisitor.visit(string, nArray);
                        --n;
                        break block5;
                    }
                    case 74: {
                        long[] lArray = new long[n2];
                        for (int i = 0; i < n2; ++i) {
                            lArray[i] = this.readLong(this.items[this.readUnsignedShort(n)]);
                            n += 3;
                        }
                        annotationVisitor.visit(string, lArray);
                        --n;
                        break block5;
                    }
                    case 70: {
                        float[] fArray = new float[n2];
                        for (int i = 0; i < n2; ++i) {
                            fArray[i] = Float.intBitsToFloat(this.readInt(this.items[this.readUnsignedShort(n)]));
                            n += 3;
                        }
                        annotationVisitor.visit(string, fArray);
                        --n;
                        break block5;
                    }
                    case 68: {
                        double[] dArray = new double[n2];
                        for (int i = 0; i < n2; ++i) {
                            dArray[i] = Double.longBitsToDouble(this.readLong(this.items[this.readUnsignedShort(n)]));
                            n += 3;
                        }
                        annotationVisitor.visit(string, dArray);
                        --n;
                        break block5;
                    }
                }
                n = this.readAnnotationValues(n - 3, cArray, false, annotationVisitor.visitArray(string));
            }
        }
        return n;
    }

    private void getImplicitFrame(Context context) {
        String string = context.desc;
        Object[] objectArray = context.local;
        int n = 0;
        if ((context.access & 8) == 0) {
            objectArray[n++] = "<init>".equals(context.name) ? Opcodes.UNINITIALIZED_THIS : this.readClass(this.header + 2, context.buffer);
        }
        int n2 = 1;
        block8: while (true) {
            int n3 = n2;
            switch (string.charAt(n2++)) {
                case 'B': 
                case 'C': 
                case 'I': 
                case 'S': 
                case 'Z': {
                    objectArray[n++] = Opcodes.INTEGER;
                    continue block8;
                }
                case 'F': {
                    objectArray[n++] = Opcodes.FLOAT;
                    continue block8;
                }
                case 'J': {
                    objectArray[n++] = Opcodes.LONG;
                    continue block8;
                }
                case 'D': {
                    objectArray[n++] = Opcodes.DOUBLE;
                    continue block8;
                }
                case '[': {
                    while (string.charAt(n2) == '[') {
                        ++n2;
                    }
                    if (string.charAt(n2) == 'L') {
                        ++n2;
                        while (string.charAt(n2) != ';') {
                            ++n2;
                        }
                    }
                    objectArray[n++] = string.substring(n3, ++n2);
                    continue block8;
                }
                case 'L': {
                    while (string.charAt(n2) != ';') {
                        ++n2;
                    }
                    objectArray[n++] = string.substring(n3 + 1, n2++);
                    continue block8;
                }
            }
            break;
        }
        context.localCount = n;
    }

    private int readFrame(int n, boolean bl, boolean bl2, Context context) {
        int n2;
        int n3;
        char[] cArray = context.buffer;
        Label[] labelArray = context.labels;
        if (bl) {
            n3 = this.b[n++] & 0xFF;
        } else {
            n3 = 255;
            context.offset = -1;
        }
        context.localDiff = 0;
        if (n3 < 64) {
            n2 = n3;
            context.mode = 3;
            context.stackCount = 0;
        } else if (n3 < 128) {
            n2 = n3 - 64;
            n = this.readFrameType(context.stack, 0, n, cArray, labelArray);
            context.mode = 4;
            context.stackCount = 1;
        } else {
            n2 = this.readUnsignedShort(n);
            n += 2;
            if (n3 == 247) {
                n = this.readFrameType(context.stack, 0, n, cArray, labelArray);
                context.mode = 4;
                context.stackCount = 1;
            } else if (n3 >= 248 && n3 < 251) {
                context.mode = 2;
                context.localDiff = 251 - n3;
                context.localCount -= context.localDiff;
                context.stackCount = 0;
            } else if (n3 == 251) {
                context.mode = 3;
                context.stackCount = 0;
            } else if (n3 < 255) {
                int n4 = bl2 ? context.localCount : 0;
                for (int i = n3 - 251; i > 0; --i) {
                    n = this.readFrameType(context.local, n4++, n, cArray, labelArray);
                }
                context.mode = 1;
                context.localDiff = n3 - 251;
                context.localCount += context.localDiff;
                context.stackCount = 0;
            } else {
                context.mode = 0;
                int n5 = this.readUnsignedShort(n);
                n += 2;
                context.localDiff = n5;
                context.localCount = n5;
                int n6 = 0;
                while (n5 > 0) {
                    n = this.readFrameType(context.local, n6++, n, cArray, labelArray);
                    --n5;
                }
                n5 = this.readUnsignedShort(n);
                n += 2;
                context.stackCount = n5;
                n6 = 0;
                while (n5 > 0) {
                    n = this.readFrameType(context.stack, n6++, n, cArray, labelArray);
                    --n5;
                }
            }
        }
        context.offset += n2 + 1;
        this.readLabel(context.offset, labelArray);
        return n;
    }

    private int readFrameType(Object[] objectArray, int n, int n2, char[] cArray, Label[] labelArray) {
        int n3 = this.b[n2++] & 0xFF;
        switch (n3) {
            case 0: {
                objectArray[n] = Opcodes.TOP;
                break;
            }
            case 1: {
                objectArray[n] = Opcodes.INTEGER;
                break;
            }
            case 2: {
                objectArray[n] = Opcodes.FLOAT;
                break;
            }
            case 3: {
                objectArray[n] = Opcodes.DOUBLE;
                break;
            }
            case 4: {
                objectArray[n] = Opcodes.LONG;
                break;
            }
            case 5: {
                objectArray[n] = Opcodes.NULL;
                break;
            }
            case 6: {
                objectArray[n] = Opcodes.UNINITIALIZED_THIS;
                break;
            }
            case 7: {
                objectArray[n] = this.readClass(n2, cArray);
                n2 += 2;
                break;
            }
            default: {
                objectArray[n] = this.readLabel(this.readUnsignedShort(n2), labelArray);
                n2 += 2;
            }
        }
        return n2;
    }

    protected Label readLabel(int n, Label[] labelArray) {
        if (labelArray[n] == null) {
            labelArray[n] = new Label();
        }
        return labelArray[n];
    }

    private int getAttributes() {
        int n;
        int n2;
        int n3 = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;
        for (n2 = this.readUnsignedShort(n3); n2 > 0; --n2) {
            for (n = this.readUnsignedShort(n3 + 8); n > 0; --n) {
                n3 += 6 + this.readInt(n3 + 12);
            }
            n3 += 8;
        }
        for (n2 = this.readUnsignedShort(n3 += 2); n2 > 0; --n2) {
            for (n = this.readUnsignedShort(n3 + 8); n > 0; --n) {
                n3 += 6 + this.readInt(n3 + 12);
            }
            n3 += 8;
        }
        return n3 + 2;
    }

    private Attribute readAttribute(Attribute[] attributeArray, String string, int n, int n2, char[] cArray, int n3, Label[] labelArray) {
        for (int i = 0; i < attributeArray.length; ++i) {
            if (!attributeArray[i].type.equals(string)) continue;
            return attributeArray[i].read(this, n, n2, cArray, n3, labelArray);
        }
        return new Attribute(string).read(this, n, n2, null, -1, null);
    }

    public int getItemCount() {
        return this.items.length;
    }

    public int getItem(int n) {
        return this.items[n];
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public int readByte(int n) {
        return this.b[n] & 0xFF;
    }

    public int readUnsignedShort(int n) {
        byte[] byArray = this.b;
        return (byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF;
    }

    public short readShort(int n) {
        byte[] byArray = this.b;
        return (short)((byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF);
    }

    public int readInt(int n) {
        byte[] byArray = this.b;
        return (byArray[n] & 0xFF) << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }

    public long readLong(int n) {
        long l2 = this.readInt(n);
        long l3 = (long)this.readInt(n + 4) & 0xFFFFFFFFL;
        return l2 << 32 | l3;
    }

    public String readUTF8(int n, char[] cArray) {
        int n2 = this.readUnsignedShort(n);
        if (n == 0 || n2 == 0) {
            return null;
        }
        String string = this.strings[n2];
        if (string != null) {
            return string;
        }
        n = this.items[n2];
        this.strings[n2] = this.readUTF(n + 2, this.readUnsignedShort(n), cArray);
        return this.strings[n2];
    }

    private String readUTF(int n, int n2, char[] cArray) {
        int n3 = n + n2;
        byte[] byArray = this.b;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (n < n3) {
            int n7 = byArray[n++];
            switch (n5) {
                case 0: {
                    if ((n7 &= 0xFF) < 128) {
                        cArray[n4++] = (char)n7;
                        break;
                    }
                    if (n7 < 224 && n7 > 191) {
                        n6 = (char)(n7 & 0x1F);
                        n5 = 1;
                        break;
                    }
                    n6 = (char)(n7 & 0xF);
                    n5 = 2;
                    break;
                }
                case 1: {
                    cArray[n4++] = (char)(n6 << 6 | n7 & 0x3F);
                    n5 = 0;
                    break;
                }
                case 2: {
                    n6 = (char)(n6 << 6 | n7 & 0x3F);
                    n5 = 1;
                }
            }
        }
        return new String(cArray, 0, n4);
    }

    public String readClass(int n, char[] cArray) {
        return this.readUTF8(this.items[this.readUnsignedShort(n)], cArray);
    }

    public Object readConst(int n, char[] cArray) {
        int n2 = this.items[n];
        switch (this.b[n2 - 1]) {
            case 3: {
                return new Integer(this.readInt(n2));
            }
            case 4: {
                return new Float(Float.intBitsToFloat(this.readInt(n2)));
            }
            case 5: {
                return new Long(this.readLong(n2));
            }
            case 6: {
                return new Double(Double.longBitsToDouble(this.readLong(n2)));
            }
            case 7: {
                return Type.getObjectType(this.readUTF8(n2, cArray));
            }
            case 8: {
                return this.readUTF8(n2, cArray);
            }
            case 16: {
                return Type.getMethodType(this.readUTF8(n2, cArray));
            }
        }
        int n3 = this.readByte(n2);
        int[] nArray = this.items;
        int n4 = nArray[this.readUnsignedShort(n2 + 1)];
        String string = this.readClass(n4, cArray);
        n4 = nArray[this.readUnsignedShort(n4 + 2)];
        String string2 = this.readUTF8(n4, cArray);
        String string3 = this.readUTF8(n4 + 2, cArray);
        return new Handle(n3, string, string2, string3);
    }
}

