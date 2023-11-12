/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.ByteVector;

public class TypePath {
    public static final int ARRAY_ELEMENT = 0;
    public static final int INNER_TYPE = 1;
    public static final int WILDCARD_BOUND = 2;
    public static final int TYPE_ARGUMENT = 3;
    byte[] b;
    int offset;

    TypePath(byte[] byArray, int n) {
        this.b = byArray;
        this.offset = n;
    }

    public int getLength() {
        return this.b[this.offset];
    }

    public int getStep(int n) {
        return this.b[this.offset + 2 * n + 1];
    }

    public int getStepArgument(int n) {
        return this.b[this.offset + 2 * n + 2];
    }

    public static TypePath fromString(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int n = string.length();
        ByteVector byteVector = new ByteVector(n);
        byteVector.putByte(0);
        int n2 = 0;
        while (n2 < n) {
            char c;
            if ((c = string.charAt(n2++)) == '[') {
                byteVector.put11(0, 0);
                continue;
            }
            if (c == '.') {
                byteVector.put11(1, 0);
                continue;
            }
            if (c == '*') {
                byteVector.put11(2, 0);
                continue;
            }
            if (c < '0' || c > '9') continue;
            int n3 = c - 48;
            while (n2 < n && (c = string.charAt(n2)) >= '0' && c <= '9') {
                n3 = n3 * 10 + c - 48;
                ++n2;
            }
            byteVector.put11(3, n3);
        }
        byteVector.data[0] = (byte)(byteVector.length / 2);
        return new TypePath(byteVector.data, 0);
    }

    public String toString() {
        int n = this.getLength();
        StringBuilder stringBuilder = new StringBuilder(n * 2);
        block6: for (int i = 0; i < n; ++i) {
            switch (this.getStep(i)) {
                case 0: {
                    stringBuilder.append('[');
                    continue block6;
                }
                case 1: {
                    stringBuilder.append('.');
                    continue block6;
                }
                case 2: {
                    stringBuilder.append('*');
                    continue block6;
                }
                case 3: {
                    stringBuilder.append(this.getStepArgument(i));
                    continue block6;
                }
                default: {
                    stringBuilder.append('_');
                }
            }
        }
        return stringBuilder.toString();
    }
}

