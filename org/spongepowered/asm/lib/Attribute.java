/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.ByteVector;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Label;

public class Attribute {
    public final String type;
    byte[] value;
    Attribute next;

    protected Attribute(String string) {
        this.type = string;
    }

    public boolean isUnknown() {
        return true;
    }

    public boolean isCodeAttribute() {
        return false;
    }

    protected Label[] getLabels() {
        return null;
    }

    protected Attribute read(ClassReader classReader, int n, int n2, char[] cArray, int n3, Label[] labelArray) {
        Attribute attribute = new Attribute(this.type);
        attribute.value = new byte[n2];
        System.arraycopy(classReader.b, n, attribute.value, 0, n2);
        return attribute;
    }

    protected ByteVector write(ClassWriter classWriter, byte[] byArray, int n, int n2, int n3) {
        ByteVector byteVector = new ByteVector();
        byteVector.data = this.value;
        byteVector.length = this.value.length;
        return byteVector;
    }

    final int getCount() {
        int n = 0;
        Attribute attribute = this;
        while (attribute != null) {
            ++n;
            attribute = attribute.next;
        }
        return n;
    }

    final int getSize(ClassWriter classWriter, byte[] byArray, int n, int n2, int n3) {
        Attribute attribute = this;
        int n4 = 0;
        while (attribute != null) {
            classWriter.newUTF8(attribute.type);
            n4 += attribute.write((ClassWriter)classWriter, (byte[])byArray, (int)n, (int)n2, (int)n3).length + 6;
            attribute = attribute.next;
        }
        return n4;
    }

    final void put(ClassWriter classWriter, byte[] byArray, int n, int n2, int n3, ByteVector byteVector) {
        Attribute attribute = this;
        while (attribute != null) {
            ByteVector byteVector2 = attribute.write(classWriter, byArray, n, n2, n3);
            byteVector.putShort(classWriter.newUTF8(attribute.type)).putInt(byteVector2.length);
            byteVector.putByteArray(byteVector2.data, 0, byteVector2.length);
            attribute = attribute.next;
        }
    }
}

