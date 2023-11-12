/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

final class TIFFModifiedHuffmanCodec {
    static final short[][][] BLACK_CODE = new short[][][]{new short[][]{{2, 3}, {3, 2}}, new short[][]{{2, 1}, {3, 4}}, new short[][]{{2, 6}, {3, 5}}, new short[][]{{3, 7}}, new short[][]{{4, 9}, {5, 8}}, new short[][]{{4, 10}, {5, 11}, {7, 12}}, new short[][]{{4, 13}, {7, 14}}, new short[][]{{24, 15}}, new short[][]{{8, 18}, {15, 64}, {23, 16}, {24, 17}, {55, 0}}, new short[][]{{0, -1}, {8, 1792}, {23, 24}, {24, 25}, {40, 23}, {55, 22}, {103, 19}, {104, 20}, {108, 21}, {12, 1856}, {13, 1920}}, new short[][]{{18, 1984}, {19, 2048}, {20, 2112}, {21, 2176}, {22, 2240}, {23, 2304}, {28, 2368}, {29, 2432}, {30, 2496}, {31, 2560}, {36, 52}, {39, 55}, {40, 56}, {43, 59}, {44, 60}, {51, 320}, {52, 384}, {53, 448}, {55, 53}, {56, 54}, {82, 50}, {83, 51}, {84, 44}, {85, 45}, {86, 46}, {87, 47}, {88, 57}, {89, 58}, {90, 61}, {91, 256}, {100, 48}, {101, 49}, {102, 62}, {103, 63}, {104, 30}, {105, 31}, {106, 32}, {107, 33}, {108, 40}, {109, 41}, {200, 128}, {201, 192}, {202, 26}, {203, 27}, {204, 28}, {205, 29}, {210, 34}, {211, 35}, {212, 36}, {213, 37}, {214, 38}, {215, 39}, {218, 42}, {219, 43}}, new short[][]{{74, 640}, {75, 704}, {76, 768}, {77, 832}, {82, 1280}, {83, 1344}, {84, 1408}, {85, 1472}, {90, 1536}, {91, 1600}, {100, 1664}, {101, 1728}, {108, 512}, {109, 576}, {114, 896}, {115, 960}, {116, 1024}, {117, 1088}, {118, 1152}, {119, 1216}}};
    static final short[][][] WHITE_CODE = new short[][][]{new short[][]{{7, 2}, {8, 3}, {11, 4}, {12, 5}, {14, 6}, {15, 7}}, new short[][]{{7, 10}, {8, 11}, {18, 128}, {19, 8}, {20, 9}, {27, 64}}, new short[][]{{3, 13}, {7, 1}, {8, 12}, {23, 192}, {24, 1664}, {42, 16}, {43, 17}, {52, 14}, {53, 15}}, new short[][]{{3, 22}, {4, 23}, {8, 20}, {12, 19}, {19, 26}, {23, 21}, {24, 28}, {36, 27}, {39, 18}, {40, 24}, {43, 25}, {55, 256}}, new short[][]{{2, 29}, {3, 30}, {4, 45}, {5, 46}, {10, 47}, {11, 48}, {18, 33}, {19, 34}, {20, 35}, {21, 36}, {22, 37}, {23, 38}, {26, 31}, {27, 32}, {36, 53}, {37, 54}, {40, 39}, {41, 40}, {42, 41}, {43, 42}, {44, 43}, {45, 44}, {50, 61}, {51, 62}, {52, 63}, {53, 0}, {54, 320}, {55, 384}, {74, 59}, {75, 60}, {82, 49}, {83, 50}, {84, 51}, {85, 52}, {88, 55}, {89, 56}, {90, 57}, {91, 58}, {100, 448}, {101, 512}, {103, 640}, {104, 576}}, new short[][]{{152, 1472}, {153, 1536}, {154, 1600}, {155, 1728}, {204, 704}, {205, 768}, {210, 832}, {211, 896}, {212, 960}, {213, 1024}, {214, 1088}, {215, 1152}, {216, 1216}, {217, 1280}, {218, 1344}, {219, 1408}}, new short[0][], new short[][]{{8, 1792}, {12, 1856}, {13, 1920}}, new short[][]{{1, -1}, {18, 1984}, {19, 2048}, {20, 2112}, {21, 2176}, {22, 2240}, {23, 2304}, {28, 2368}, {29, 2432}, {30, 2496}, {31, 2560}}};
    static final int BLACK_MIN_BITS = 2;
    static final int WHITE_MIN_BITS = 4;
    boolean isWhite;
    int whiteValue = 0;
    int blackValue = 1;
    byte[] src;
    byte[] dest;
    int byteOffsetSrc = 0;
    int bitOffsetSrc = 0;
    int byteOffsetDest = 0;
    int bitOffsetDest = 0;
    int code = 0;
    int nbrBits = 0;
    int rowSize;

    TIFFModifiedHuffmanCodec() {
    }

    /*
     * Exception decompiling
     */
    public int decode(byte[] var1, byte[] var2, int var3, int var4, int var5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl28 : IF_ICMPGE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    int decodeRunLength() {
        int n = 0;
        short s = 0;
        short[][][] sArray = this.isWhite ? WHITE_CODE : BLACK_CODE;
        while (true) {
            boolean bl = false;
            this.nbrBits = this.isWhite ? 4 : 2;
            this.code = this.getNextBits(this.nbrBits);
            short[][][] sArray2 = sArray;
            int n2 = sArray2.length;
            for (int i = 0; i < n2; ++i) {
                short[][] sArray3;
                short[][] sArray4 = sArray3 = sArray2[i];
                int n3 = sArray3.length;
                for (int j = 0; j < n3; ++j) {
                    short[] sArray5 = sArray3[j];
                    if (sArray5[0] != this.code) continue;
                    bl = true;
                    s = sArray5[1];
                    if (s == -1) {
                        if (this.byteOffsetSrc != this.src.length - 1) break;
                        return -1;
                    }
                    n += s;
                    if (s >= 64) break;
                    return n;
                }
                if (bl) break;
                this.code = this.code << 1 | this.getNextBit();
            }
            if (bl) continue;
            SWT.error(40);
        }
    }

    int getNextBit() {
        int n = this.src[this.byteOffsetSrc] >>> 7 - this.bitOffsetSrc & 1;
        ++this.bitOffsetSrc;
        if (this.bitOffsetSrc > 7) {
            ++this.byteOffsetSrc;
            this.bitOffsetSrc = 0;
        }
        return n;
    }

    int getNextBits(int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 = n2 << 1 | this.getNextBit();
        }
        return n2;
    }

    void setNextBits(int n, int n2) {
        int n3 = n2;
        while (this.bitOffsetDest > 0 && this.bitOffsetDest <= 7 && n3 > 0) {
            this.dest[this.byteOffsetDest] = n == 1 ? (byte)(this.dest[this.byteOffsetDest] | 1 << 7 - this.bitOffsetDest) : (byte)(this.dest[this.byteOffsetDest] & ~(1 << 7 - this.bitOffsetDest));
            --n3;
            ++this.bitOffsetDest;
        }
        if (this.bitOffsetDest == 8) {
            ++this.byteOffsetDest;
            this.bitOffsetDest = 0;
        }
        while (n3 >= 8) {
            this.dest[this.byteOffsetDest++] = (byte)(n == 1 ? 255 : 0);
            n3 -= 8;
        }
        while (n3 > 0) {
            this.dest[this.byteOffsetDest] = n == 1 ? (byte)(this.dest[this.byteOffsetDest] | 1 << 7 - this.bitOffsetDest) : (byte)(this.dest[this.byteOffsetDest] & ~(1 << 7 - this.bitOffsetDest));
            --n3;
            ++this.bitOffsetDest;
        }
    }
}

