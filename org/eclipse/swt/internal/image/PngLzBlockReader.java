/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.internal.image.PngDecodingDataStream;
import org.eclipse.swt.internal.image.PngHuffmanTables;

public class PngLzBlockReader {
    boolean isLastBlock;
    byte compressionType;
    int uncompressedBytesRemaining;
    PngDecodingDataStream stream;
    PngHuffmanTables huffmanTables;
    byte[] window;
    int windowIndex;
    int copyIndex;
    int copyBytesRemaining;
    static final int UNCOMPRESSED = 0;
    static final int COMPRESSED_FIXED = 1;
    static final int COMPRESSED_DYNAMIC = 2;
    static final int END_OF_COMPRESSED_BLOCK = 256;
    static final int FIRST_LENGTH_CODE = 257;
    static final int LAST_LENGTH_CODE = 285;
    static final int FIRST_DISTANCE_CODE = 1;
    static final int LAST_DISTANCE_CODE = 29;
    static final int FIRST_CODE_LENGTH_CODE = 4;
    static final int LAST_CODE_LENGTH_CODE = 19;
    static final int[] lengthBases = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258};
    static final int[] extraLengthBits = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0};
    static final int[] distanceBases = new int[]{1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577};
    static final int[] extraDistanceBits = new int[]{0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13};

    PngLzBlockReader(PngDecodingDataStream pngDecodingDataStream) {
        this.stream = pngDecodingDataStream;
        this.isLastBlock = false;
    }

    void setWindowSize(int n) {
        this.window = new byte[n];
    }

    void readNextBlockHeader() throws IOException {
        this.isLastBlock = this.stream.getNextIdatBit() != 0;
        this.compressionType = (byte)this.stream.getNextIdatBits(2);
        if (this.compressionType > 2) {
            this.stream.error();
        }
        if (this.compressionType == 0) {
            byte by = this.stream.getNextIdatByte();
            byte by2 = this.stream.getNextIdatByte();
            byte by3 = this.stream.getNextIdatByte();
            byte by4 = this.stream.getNextIdatByte();
            if (by != ~by3 || by2 != ~by4) {
                this.stream.error();
            }
            this.uncompressedBytesRemaining = by & 0xFF | (by2 & 0xFF) << 8;
        } else {
            this.huffmanTables = this.compressionType == 2 ? PngHuffmanTables.getDynamicTables(this.stream) : PngHuffmanTables.getFixedTables();
        }
    }

    byte getNextByte() throws IOException {
        if (this.compressionType != 0) {
            return this.getNextCompressedByte();
        }
        if (this.uncompressedBytesRemaining == 0) {
            this.readNextBlockHeader();
            return this.getNextByte();
        }
        --this.uncompressedBytesRemaining;
        return this.stream.getNextIdatByte();
    }

    private void assertBlockAtEnd() throws IOException {
        if (this.compressionType == 0) {
            if (this.uncompressedBytesRemaining > 0) {
                this.stream.error();
            }
        } else if (this.copyBytesRemaining > 0 || this.huffmanTables.getNextLiteralValue(this.stream) != 256) {
            this.stream.error();
        }
    }

    void assertCompressedDataAtEnd() throws IOException {
        this.assertBlockAtEnd();
        while (!this.isLastBlock) {
            this.readNextBlockHeader();
            this.assertBlockAtEnd();
        }
    }

    private byte getNextCompressedByte() throws IOException {
        if (this.copyBytesRemaining > 0) {
            byte by;
            this.window[this.windowIndex] = by = this.window[this.copyIndex];
            --this.copyBytesRemaining;
            ++this.copyIndex;
            ++this.windowIndex;
            if (this.copyIndex == this.window.length) {
                this.copyIndex = 0;
            }
            if (this.windowIndex == this.window.length) {
                this.windowIndex = 0;
            }
            return by;
        }
        int n = this.huffmanTables.getNextLiteralValue(this.stream);
        if (n < 256) {
            this.window[this.windowIndex] = (byte)n;
            ++this.windowIndex;
            if (this.windowIndex >= this.window.length) {
                this.windowIndex = 0;
            }
            return (byte)n;
        }
        if (n == 256) {
            this.readNextBlockHeader();
            return this.getNextByte();
        }
        if (n <= 285) {
            int n2 = extraLengthBits[n - 257];
            int n3 = lengthBases[n - 257];
            if (n2 > 0) {
                n3 += this.stream.getNextIdatBits(n2);
            }
            if ((n = this.huffmanTables.getNextDistanceValue(this.stream)) > 29) {
                this.stream.error();
            }
            n2 = extraDistanceBits[n];
            int n4 = distanceBases[n];
            if (n2 > 0) {
                n4 += this.stream.getNextIdatBits(n2);
            }
            this.copyIndex = this.windowIndex - n4;
            if (this.copyIndex < 0) {
                this.copyIndex += this.window.length;
            }
            this.copyBytesRemaining = n3;
            return this.getNextCompressedByte();
        }
        this.stream.error();
        return 0;
    }
}

