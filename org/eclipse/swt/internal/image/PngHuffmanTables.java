/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.internal.image.PngDecodingDataStream;
import org.eclipse.swt.internal.image.PngHuffmanTable;

public class PngHuffmanTables {
    PngHuffmanTable literalTable;
    PngHuffmanTable distanceTable;
    static PngHuffmanTable FixedLiteralTable;
    static PngHuffmanTable FixedDistanceTable;
    static final int LiteralTableSize = 288;
    static final int[] FixedLiteralLengths;
    static final int DistanceTableSize = 32;
    static final int[] FixedDistanceLengths;
    static final int LengthCodeTableSize = 19;
    static final int[] LengthCodeOrder;

    static PngHuffmanTables getDynamicTables(PngDecodingDataStream pngDecodingDataStream) throws IOException {
        return new PngHuffmanTables(pngDecodingDataStream);
    }

    static PngHuffmanTables getFixedTables() {
        return new PngHuffmanTables();
    }

    private PngHuffmanTable getFixedLiteralTable() {
        if (FixedLiteralTable == null) {
            FixedLiteralTable = new PngHuffmanTable(FixedLiteralLengths);
        }
        return FixedLiteralTable;
    }

    private PngHuffmanTable getFixedDistanceTable() {
        if (FixedDistanceTable == null) {
            FixedDistanceTable = new PngHuffmanTable(FixedDistanceLengths);
        }
        return FixedDistanceTable;
    }

    private PngHuffmanTables() {
        this.literalTable = this.getFixedLiteralTable();
        this.distanceTable = this.getFixedDistanceTable();
    }

    private PngHuffmanTables(PngDecodingDataStream pngDecodingDataStream) throws IOException {
        int n = 257 + pngDecodingDataStream.getNextIdatBits(5);
        int n2 = 1 + pngDecodingDataStream.getNextIdatBits(5);
        int n3 = 4 + pngDecodingDataStream.getNextIdatBits(4);
        if (n3 > 19) {
            pngDecodingDataStream.error();
        }
        int[] nArray = new int[19];
        for (int i = 0; i < n3; ++i) {
            nArray[PngHuffmanTables.LengthCodeOrder[i]] = pngDecodingDataStream.getNextIdatBits(3);
        }
        PngHuffmanTable pngHuffmanTable = new PngHuffmanTable(nArray);
        int[] nArray2 = this.readLengths(pngDecodingDataStream, n, pngHuffmanTable, 288);
        int[] nArray3 = this.readLengths(pngDecodingDataStream, n2, pngHuffmanTable, 32);
        this.literalTable = new PngHuffmanTable(nArray2);
        this.distanceTable = new PngHuffmanTable(nArray3);
    }

    private int[] readLengths(PngDecodingDataStream pngDecodingDataStream, int n, PngHuffmanTable pngHuffmanTable, int n2) throws IOException {
        int[] nArray = new int[n2];
        int n3 = 0;
        while (n3 < n) {
            int n4;
            int n5;
            int n6 = pngHuffmanTable.getNextValue(pngDecodingDataStream);
            if (n6 < 16) {
                nArray[n3] = n6;
                ++n3;
                continue;
            }
            if (n6 == 16) {
                n5 = pngDecodingDataStream.getNextIdatBits(2) + 3;
                for (n4 = 0; n4 < n5; ++n4) {
                    nArray[n3] = nArray[n3 - 1];
                    ++n3;
                }
                continue;
            }
            if (n6 == 17) {
                n5 = pngDecodingDataStream.getNextIdatBits(3) + 3;
                for (n4 = 0; n4 < n5; ++n4) {
                    nArray[n3] = 0;
                    ++n3;
                }
                continue;
            }
            if (n6 == 18) {
                n5 = pngDecodingDataStream.getNextIdatBits(7) + 11;
                for (n4 = 0; n4 < n5; ++n4) {
                    nArray[n3] = 0;
                    ++n3;
                }
                continue;
            }
            pngDecodingDataStream.error();
        }
        return nArray;
    }

    int getNextLiteralValue(PngDecodingDataStream pngDecodingDataStream) throws IOException {
        return this.literalTable.getNextValue(pngDecodingDataStream);
    }

    int getNextDistanceValue(PngDecodingDataStream pngDecodingDataStream) throws IOException {
        return this.distanceTable.getNextValue(pngDecodingDataStream);
    }

    static {
        FixedLiteralLengths = new int[]{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8};
        FixedDistanceLengths = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        LengthCodeOrder = new int[]{16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};
    }
}

