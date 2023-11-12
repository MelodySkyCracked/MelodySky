/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.PngLzBlockReader;

public class PngDecodingDataStream
extends InputStream {
    InputStream stream;
    byte currentByte;
    int nextBitIndex;
    PngLzBlockReader lzBlockReader;
    int adlerValue;
    static final int PRIME = 65521;
    static final int MAX_BIT = 7;

    PngDecodingDataStream(InputStream inputStream) throws IOException {
        this.stream = inputStream;
        this.nextBitIndex = 8;
        this.adlerValue = 1;
        this.lzBlockReader = new PngLzBlockReader(this);
        this.readCompressedDataHeader();
        this.lzBlockReader.readNextBlockHeader();
    }

    void assertImageDataAtEnd() throws IOException {
        this.lzBlockReader.assertCompressedDataAtEnd();
    }

    @Override
    public void close() throws IOException {
        this.assertImageDataAtEnd();
        this.checkAdler();
    }

    int getNextIdatBits(int n) throws IOException {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 |= this.getNextIdatBit() << i;
        }
        return n2;
    }

    int getNextIdatBit() throws IOException {
        if (this.nextBitIndex > 7) {
            this.currentByte = this.getNextIdatByte();
            this.nextBitIndex = 0;
        }
        return (this.currentByte & 1 << this.nextBitIndex) >> this.nextBitIndex++;
    }

    byte getNextIdatByte() throws IOException {
        byte by = (byte)this.stream.read();
        this.nextBitIndex = 8;
        return by;
    }

    void updateAdler(byte by) {
        int n = this.adlerValue & 0xFFFF;
        int n2 = this.adlerValue >> 16 & 0xFFFF;
        int n3 = by & 0xFF;
        n = (n + n3) % 65521;
        n2 = (n + n2) % 65521;
        this.adlerValue = n2 << 16 | n;
    }

    @Override
    public int read() throws IOException {
        byte by = this.lzBlockReader.getNextByte();
        this.updateAdler(by);
        return by & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        for (int i = 0; i < n2; ++i) {
            int n3 = this.read();
            if (n3 == -1) {
                return i;
            }
            byArray[n + i] = (byte)n3;
        }
        return n2;
    }

    void error() {
        SWT.error(40);
    }

    private void readCompressedDataHeader() throws IOException {
        int n;
        int n2;
        byte by;
        byte by2 = this.getNextIdatByte();
        int n3 = (by2 & 0xFF) << 8 | (by = this.getNextIdatByte()) & 0xFF;
        if (n3 % 31 != 0) {
            this.error();
        }
        if ((n2 = by2 & 0xF) != 8) {
            this.error();
        }
        if ((n = (by2 & 0xF0) >> 4) > 7) {
            this.error();
        }
        int n4 = 1 << n + 8;
        this.lzBlockReader.setWindowSize(n4);
        int n5 = by & 0x20;
        if (n5 != 0) {
            this.error();
        }
    }

    void checkAdler() throws IOException {
        int n = (this.getNextIdatByte() & 0xFF) << 24 | (this.getNextIdatByte() & 0xFF) << 16 | (this.getNextIdatByte() & 0xFF) << 8 | this.getNextIdatByte() & 0xFF;
        if (n != this.adlerValue) {
            this.error();
        }
    }
}

