/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class TIFFRandomFileAccess {
    LEDataInputStream inputStream;
    int start;
    int current;
    int next;
    byte[][] buffers;
    static final int CHUNK_SIZE = 8192;
    static final int LIST_SIZE = 128;

    public TIFFRandomFileAccess(LEDataInputStream lEDataInputStream) {
        int n;
        this.inputStream = lEDataInputStream;
        this.next = n = this.inputStream.getPosition();
        this.current = n;
        this.start = n;
        this.buffers = new byte[128][];
    }

    void seek(int n) throws IOException {
        if (n == this.current) {
            return;
        }
        if (n < this.start) {
            throw new IOException();
        }
        this.current = n;
        if (this.current > this.next) {
            int n2 = this.current - this.next;
            int n3 = this.next / 8192;
            int n4 = this.next % 8192;
            while (n2 > 0) {
                if (n3 >= this.buffers.length) {
                    byte[][] byArray = this.buffers;
                    byte[][] byArrayArray = new byte[Math.max(n3 + 1, byArray.length + 128)][];
                    this.buffers = byArrayArray;
                    System.arraycopy(byArray, 0, byArrayArray, 0, byArray.length);
                }
                if (this.buffers[n3] == null) {
                    this.buffers[n3] = new byte[8192];
                }
                int n5 = this.inputStream.read(this.buffers[n3], n4, Math.min(n2, 8192 - n4));
                n2 -= n5;
                this.next += n5;
                ++n3;
                n4 = 0;
            }
        }
    }

    void read(byte[] byArray) throws IOException {
        int n;
        int n2;
        int n3;
        int n4 = byArray.length;
        int n5 = Math.min(n4, this.next - this.current);
        int n6 = n4 - this.next + this.current;
        int n7 = 0;
        if (n5 > 0) {
            n3 = this.current / 8192;
            n2 = this.current % 8192;
            while (n5 > 0) {
                n = Math.min(n5, 8192 - n2);
                System.arraycopy(this.buffers[n3], n2, byArray, n7, n);
                n5 -= n;
                n7 += n;
                ++n3;
                n2 = 0;
            }
        }
        if (n6 > 0) {
            n3 = this.next / 8192;
            n2 = this.next % 8192;
            while (n6 > 0) {
                if (n3 >= this.buffers.length) {
                    byte[][] byArray2 = this.buffers;
                    byte[][] byArrayArray = new byte[Math.max(n3, byArray2.length + 128)][];
                    this.buffers = byArrayArray;
                    System.arraycopy(byArray2, 0, byArrayArray, 0, byArray2.length);
                }
                if (this.buffers[n3] == null) {
                    this.buffers[n3] = new byte[8192];
                }
                n = this.inputStream.read(this.buffers[n3], n2, Math.min(n6, 8192 - n2));
                System.arraycopy(this.buffers[n3], n2, byArray, n7, n);
                n6 -= n;
                this.next += n;
                n7 += n;
                ++n3;
                n2 = 0;
            }
        }
        this.current += n4;
    }
}

