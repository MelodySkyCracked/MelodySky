/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;

public class PngDeflater {
    static final int BASE = 65521;
    static final int WINDOW = 32768;
    static final int MIN_LENGTH = 3;
    static final int MAX_MATCHES = 32;
    static final int HASH = 8209;
    byte[] in;
    int inLength;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
    int adler32 = 1;
    int buffer;
    int bitCount;
    Link[] hashtable = new Link[8209];
    Link[] window = new Link[32768];
    int nextWindow;
    static final short[] mirrorBytes = new short[]{0, 128, 64, 192, 32, 160, 96, 224, 16, 144, 80, 208, 48, 176, 112, 240, 8, 136, 72, 200, 40, 168, 104, 232, 24, 152, 88, 216, 56, 184, 120, 248, 4, 132, 68, 196, 36, 164, 100, 228, 20, 148, 84, 212, 52, 180, 116, 244, 12, 140, 76, 204, 44, 172, 108, 236, 28, 156, 92, 220, 60, 188, 124, 252, 2, 130, 66, 194, 34, 162, 98, 226, 18, 146, 82, 210, 50, 178, 114, 242, 10, 138, 74, 202, 42, 170, 106, 234, 26, 154, 90, 218, 58, 186, 122, 250, 6, 134, 70, 198, 38, 166, 102, 230, 22, 150, 86, 214, 54, 182, 118, 246, 14, 142, 78, 206, 46, 174, 110, 238, 30, 158, 94, 222, 62, 190, 126, 254, 1, 129, 65, 193, 33, 161, 97, 225, 17, 145, 81, 209, 49, 177, 113, 241, 9, 137, 73, 201, 41, 169, 105, 233, 25, 153, 89, 217, 57, 185, 121, 249, 5, 133, 69, 197, 37, 165, 101, 229, 21, 149, 85, 213, 53, 181, 117, 245, 13, 141, 77, 205, 45, 173, 109, 237, 29, 157, 93, 221, 61, 189, 125, 253, 3, 131, 67, 195, 35, 163, 99, 227, 19, 147, 83, 211, 51, 179, 115, 243, 11, 139, 75, 203, 43, 171, 107, 235, 27, 155, 91, 219, 59, 187, 123, 251, 7, 135, 71, 199, 39, 167, 103, 231, 23, 151, 87, 215, 55, 183, 119, 247, 15, 143, 79, 207, 47, 175, 111, 239, 31, 159, 95, 223, 63, 191, 127, 255};
    static final Code[] lengthCodes = new Code[]{new Code(257, 0, 3, 3), new Code(258, 0, 4, 4), new Code(259, 0, 5, 5), new Code(260, 0, 6, 6), new Code(261, 0, 7, 7), new Code(262, 0, 8, 8), new Code(263, 0, 9, 9), new Code(264, 0, 10, 10), new Code(265, 1, 11, 12), new Code(266, 1, 13, 14), new Code(267, 1, 15, 16), new Code(268, 1, 17, 18), new Code(269, 2, 19, 22), new Code(270, 2, 23, 26), new Code(271, 2, 27, 30), new Code(272, 2, 31, 34), new Code(273, 3, 35, 42), new Code(274, 3, 43, 50), new Code(275, 3, 51, 58), new Code(276, 3, 59, 66), new Code(277, 4, 67, 82), new Code(278, 4, 83, 98), new Code(279, 4, 99, 114), new Code(280, 4, 115, 130), new Code(281, 5, 131, 162), new Code(282, 5, 163, 194), new Code(283, 5, 195, 226), new Code(284, 5, 227, 257), new Code(285, 0, 258, 258)};
    static final Code[] distanceCodes = new Code[]{new Code(0, 0, 1, 1), new Code(1, 0, 2, 2), new Code(2, 0, 3, 3), new Code(3, 0, 4, 4), new Code(4, 1, 5, 6), new Code(5, 1, 7, 8), new Code(6, 2, 9, 12), new Code(7, 2, 13, 16), new Code(8, 3, 17, 24), new Code(9, 3, 25, 32), new Code(10, 4, 33, 48), new Code(11, 4, 49, 64), new Code(12, 5, 65, 96), new Code(13, 5, 97, 128), new Code(14, 6, 129, 192), new Code(15, 6, 193, 256), new Code(16, 7, 257, 384), new Code(17, 7, 385, 512), new Code(18, 8, 513, 768), new Code(19, 8, 769, 1024), new Code(20, 9, 1025, 1536), new Code(21, 9, 1537, 2048), new Code(22, 10, 2049, 3072), new Code(23, 10, 3073, 4096), new Code(24, 11, 4097, 6144), new Code(25, 11, 6145, 8192), new Code(26, 12, 8193, 12288), new Code(27, 12, 12289, 16384), new Code(28, 13, 16385, 24576), new Code(29, 13, 24577, 32768)};

    void writeShortLSB(ByteArrayOutputStream byteArrayOutputStream, int n) {
        byte by = (byte)(n & 0xFF);
        byte by2 = (byte)(n >> 8 & 0xFF);
        byte[] byArray = new byte[]{by, by2};
        byteArrayOutputStream.write(byArray, 0, 2);
    }

    void writeInt(ByteArrayOutputStream byteArrayOutputStream, int n) {
        byte by = (byte)(n >> 24 & 0xFF);
        byte by2 = (byte)(n >> 16 & 0xFF);
        byte by3 = (byte)(n >> 8 & 0xFF);
        byte by4 = (byte)(n & 0xFF);
        byte[] byArray = new byte[]{by, by2, by3, by4};
        byteArrayOutputStream.write(byArray, 0, 4);
    }

    void updateAdler(byte by) {
        int n = this.adler32 & 0xFFFF;
        int n2 = this.adler32 >> 16 & 0xFFFF;
        int n3 = by & 0xFF;
        n = (n + n3) % 65521;
        n2 = (n + n2) % 65521;
        this.adler32 = n2 << 16 | n;
    }

    int hash(byte[] byArray) {
        int n = ((byArray[0] & 0xFF) << 24 | (byArray[1] & 0xFF) << 16 | (byArray[2] & 0xFF) << 8) % 8209;
        if (n < 0) {
            n += 8209;
        }
        return n;
    }

    void writeBits(int n, int n2) {
        this.buffer |= n << this.bitCount;
        this.bitCount += n2;
        if (this.bitCount >= 16) {
            this.bytes.write((byte)this.buffer);
            this.bytes.write((byte)(this.buffer >>> 8));
            this.buffer >>>= 16;
            this.bitCount -= 16;
        }
    }

    void alignToByte() {
        if (this.bitCount > 0) {
            this.bytes.write((byte)this.buffer);
            if (this.bitCount > 8) {
                this.bytes.write((byte)(this.buffer >>> 8));
            }
        }
        this.buffer = 0;
        this.bitCount = 0;
    }

    void outputLiteral(byte by) {
        int n = by & 0xFF;
        if (n <= 143) {
            this.writeBits(mirrorBytes[48 + n], 8);
        } else {
            this.writeBits(1 + 2 * mirrorBytes[0 + n], 9);
        }
    }

    Code findCode(int n, Code[] codeArray) {
        int n2;
        int n3 = -1;
        int n4 = codeArray.length;
        while (true) {
            n2 = (n4 + n3) / 2;
            if (n < codeArray[n2].min) {
                n4 = n2;
                continue;
            }
            if (n <= codeArray[n2].max) break;
            n3 = n2;
        }
        return codeArray[n2];
    }

    void outputMatch(int n, int n2) {
        while (n > 0) {
            int n3 = n > 260 ? 258 : (n <= 258 ? n : n - 3);
            n -= n3;
            Code code = this.findCode(n3, lengthCodes);
            if (code.code <= 279) {
                this.writeBits(mirrorBytes[(code.code - 256) * 2], 7);
            } else {
                this.writeBits(mirrorBytes[-88 + code.code], 8);
            }
            if (code.extraBits != 0) {
                this.writeBits(n3 - code.min, code.extraBits);
            }
            Code code2 = this.findCode(n2, distanceCodes);
            this.writeBits(mirrorBytes[code2.code * 8], 5);
            if (code2.extraBits == 0) continue;
            this.writeBits(n2 - code2.min, code2.extraBits);
        }
    }

    Match findLongestMatch(int n, Link link) {
        Link link2 = link;
        int n2 = 0;
        Match match = new Match(-1, -1);
        do {
            int n3;
            if (n - (n3 = link2.value) >= 32768 || n3 == 0) continue;
            int n4 = 1;
            while (n + n4 < this.inLength && this.in[n + n4] == this.in[n3 + n4]) {
                ++n4;
            }
            if (n4 < 3) continue;
            if (n4 > match.length) {
                match.length = n4;
                match.distance = n - n3;
            }
            if (++n2 == 32) break;
        } while ((link2 = link2.next) != null);
        if (match.length < 3 || match.distance < 1 || match.distance > 32768) {
            return null;
        }
        return match;
    }

    void updateHashtable(int n, int n2) {
        byte[] byArray = new byte[3];
        for (int i = n; i < n2 && i + 3 <= this.inLength; ++i) {
            Link link;
            byArray[0] = this.in[i];
            byArray[1] = this.in[i + 1];
            byArray[2] = this.in[i + 2];
            int n3 = this.hash(byArray);
            if (this.window[this.nextWindow].previous != null) {
                this.window[this.nextWindow].previous.next = null;
            } else if (this.window[this.nextWindow].hash != 0) {
                this.hashtable[this.window[this.nextWindow].hash].next = null;
            }
            this.window[this.nextWindow].hash = n3;
            this.window[this.nextWindow].value = i;
            this.window[this.nextWindow].previous = null;
            Link link2 = this.window[this.nextWindow];
            link2.next = link = this.hashtable[n3].next;
            Link link3 = link;
            this.hashtable[n3].next = this.window[this.nextWindow];
            if (link3 != null) {
                link3.previous = this.window[this.nextWindow];
            }
            ++this.nextWindow;
            if (this.nextWindow != 32768) continue;
            this.nextWindow = 0;
        }
    }

    void compress() {
        int n;
        byte[] byArray = new byte[3];
        for (n = 0; n < 8209; ++n) {
            this.hashtable[n] = new Link();
        }
        for (n = 0; n < 32768; ++n) {
            this.window[n] = new Link();
        }
        this.nextWindow = 0;
        n = -1;
        Match match = null;
        this.writeBits(1, 1);
        this.writeBits(1, 2);
        this.outputLiteral(this.in[0]);
        int n2 = 1;
        while (n2 < this.inLength) {
            int n3;
            if (this.inLength - n2 < 3) {
                this.outputLiteral(this.in[n2]);
                ++n2;
                continue;
            }
            byArray[0] = this.in[n2];
            byArray[1] = this.in[n2 + 1];
            byArray[2] = this.in[n2 + 2];
            int n4 = this.hash(byArray);
            Link link = this.hashtable[n4];
            Match match2 = this.findLongestMatch(n2, link);
            this.updateHashtable(n2, n2 + 1);
            if (match2 != null) {
                if (match != null) {
                    if (match2.length > match.length + 1) {
                        this.outputLiteral(this.in[n]);
                        n = n2++;
                        match = match2;
                        continue;
                    }
                    this.outputMatch(match.length, match.distance);
                    n3 = n + match.length;
                    n = -1;
                    match = null;
                    this.updateHashtable(n2 + 1, n3);
                    n2 = n3;
                    continue;
                }
                n = n2++;
                match = match2;
                continue;
            }
            if (match != null) {
                this.outputMatch(match.length, match.distance);
                n3 = n + match.length;
                n = -1;
                match = null;
                this.updateHashtable(n2 + 1, n3);
                n2 = n3;
                continue;
            }
            this.outputLiteral(this.in[n2]);
            ++n2;
        }
        this.writeBits(0, 7);
        this.alignToByte();
    }

    void compressHuffmanOnly() {
        this.writeBits(1, 1);
        this.writeBits(1, 2);
        for (int i = 0; i < this.inLength; ++i) {
            this.outputLiteral(this.in[i]);
        }
        this.writeBits(0, 7);
        this.alignToByte();
    }

    void store() {
        int n = 0;
        int n2 = this.inLength;
        boolean bl = false;
        while (n2 > 0) {
            int n3;
            if (n2 < 65535) {
                n3 = n2;
                bl = true;
            } else {
                n3 = 65535;
                bl = false;
            }
            this.bytes.write((byte)(bl ? 1 : 0));
            this.writeShortLSB(this.bytes, n3);
            this.writeShortLSB(this.bytes, n3 ^ 0xFFFF);
            this.bytes.write(this.in, n, n3);
            n2 -= n3;
            n += n3;
        }
    }

    public byte[] deflate(byte[] byArray) {
        this.in = byArray;
        this.inLength = byArray.length;
        this.bytes.write(120);
        this.bytes.write(-100);
        for (int i = 0; i < this.inLength; ++i) {
            this.updateAdler(this.in[i]);
        }
        this.compress();
        this.writeInt(this.bytes, this.adler32);
        return this.bytes.toByteArray();
    }

    static class Code {
        int code;
        int extraBits;
        int min;
        int max;

        Code(int n, int n2, int n3, int n4) {
            this.code = n;
            this.extraBits = n2;
            this.min = n3;
            this.max = n4;
        }
    }

    static class Match {
        int length;
        int distance;

        Match(int n, int n2) {
            this.length = n;
            this.distance = n2;
        }
    }

    static class Link {
        int hash = 0;
        int value = 0;
        Link previous = null;
        Link next = null;

        Link() {
        }
    }
}

