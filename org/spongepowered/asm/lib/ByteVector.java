/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

public class ByteVector {
    byte[] data;
    int length;

    public ByteVector() {
        this.data = new byte[64];
    }

    public ByteVector(int n) {
        this.data = new byte[n];
    }

    public ByteVector putByte(int n) {
        int n2 = this.length;
        if (n2 + 1 > this.data.length) {
            this.enlarge(1);
        }
        this.data[n2++] = (byte)n;
        this.length = n2;
        return this;
    }

    ByteVector put11(int n, int n2) {
        int n3 = this.length;
        if (n3 + 2 > this.data.length) {
            this.enlarge(2);
        }
        byte[] byArray = this.data;
        byArray[n3++] = (byte)n;
        byArray[n3++] = (byte)n2;
        this.length = n3;
        return this;
    }

    public ByteVector putShort(int n) {
        int n2 = this.length;
        if (n2 + 2 > this.data.length) {
            this.enlarge(2);
        }
        byte[] byArray = this.data;
        byArray[n2++] = (byte)(n >>> 8);
        byArray[n2++] = (byte)n;
        this.length = n2;
        return this;
    }

    ByteVector put12(int n, int n2) {
        int n3 = this.length;
        if (n3 + 3 > this.data.length) {
            this.enlarge(3);
        }
        byte[] byArray = this.data;
        byArray[n3++] = (byte)n;
        byArray[n3++] = (byte)(n2 >>> 8);
        byArray[n3++] = (byte)n2;
        this.length = n3;
        return this;
    }

    public ByteVector putInt(int n) {
        int n2 = this.length;
        if (n2 + 4 > this.data.length) {
            this.enlarge(4);
        }
        byte[] byArray = this.data;
        byArray[n2++] = (byte)(n >>> 24);
        byArray[n2++] = (byte)(n >>> 16);
        byArray[n2++] = (byte)(n >>> 8);
        byArray[n2++] = (byte)n;
        this.length = n2;
        return this;
    }

    public ByteVector putLong(long l2) {
        int n = this.length;
        if (n + 8 > this.data.length) {
            this.enlarge(8);
        }
        byte[] byArray = this.data;
        int n2 = (int)(l2 >>> 32);
        byArray[n++] = (byte)(n2 >>> 24);
        byArray[n++] = (byte)(n2 >>> 16);
        byArray[n++] = (byte)(n2 >>> 8);
        byArray[n++] = (byte)n2;
        n2 = (int)l2;
        byArray[n++] = (byte)(n2 >>> 24);
        byArray[n++] = (byte)(n2 >>> 16);
        byArray[n++] = (byte)(n2 >>> 8);
        byArray[n++] = (byte)n2;
        this.length = n;
        return this;
    }

    public ByteVector putUTF8(String string) {
        int n = string.length();
        if (n > 65535) {
            throw new IllegalArgumentException();
        }
        int n2 = this.length;
        if (n2 + 2 + n > this.data.length) {
            this.enlarge(2 + n);
        }
        byte[] byArray = this.data;
        byArray[n2++] = (byte)(n >>> 8);
        byArray[n2++] = (byte)n;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c < '\u0001' || c > '\u007f') {
                this.length = n2;
                return this.encodeUTF8(string, i, 65535);
            }
            byArray[n2++] = (byte)c;
        }
        this.length = n2;
        return this;
    }

    ByteVector encodeUTF8(String string, int n, int n2) {
        char c;
        int n3;
        int n4 = string.length();
        int n5 = n;
        for (n3 = n; n3 < n4; ++n3) {
            c = string.charAt(n3);
            if (c >= '\u0001' && c <= '\u007f') {
                ++n5;
                continue;
            }
            if (c > '\u07ff') {
                n5 += 3;
                continue;
            }
            n5 += 2;
        }
        if (n5 > n2) {
            throw new IllegalArgumentException();
        }
        n3 = this.length - n - 2;
        if (n3 >= 0) {
            this.data[n3] = (byte)(n5 >>> 8);
            this.data[n3 + 1] = (byte)n5;
        }
        if (this.length + n5 - n > this.data.length) {
            this.enlarge(n5 - n);
        }
        int n6 = this.length;
        for (int i = n; i < n4; ++i) {
            c = string.charAt(i);
            if (c >= '\u0001' && c <= '\u007f') {
                this.data[n6++] = (byte)c;
                continue;
            }
            if (c > '\u07ff') {
                this.data[n6++] = (byte)(0xE0 | c >> 12 & 0xF);
                this.data[n6++] = (byte)(0x80 | c >> 6 & 0x3F);
                this.data[n6++] = (byte)(0x80 | c & 0x3F);
                continue;
            }
            this.data[n6++] = (byte)(0xC0 | c >> 6 & 0x1F);
            this.data[n6++] = (byte)(0x80 | c & 0x3F);
        }
        this.length = n6;
        return this;
    }

    public ByteVector putByteArray(byte[] byArray, int n, int n2) {
        if (this.length + n2 > this.data.length) {
            this.enlarge(n2);
        }
        if (byArray != null) {
            System.arraycopy(byArray, n, this.data, this.length, n2);
        }
        this.length += n2;
        return this;
    }

    private void enlarge(int n) {
        int n2 = 2 * this.data.length;
        int n3 = this.length + n;
        byte[] byArray = new byte[n2 > n3 ? n2 : n3];
        System.arraycopy(this.data, 0, byArray, 0, this.length);
        this.data = byArray;
    }
}

