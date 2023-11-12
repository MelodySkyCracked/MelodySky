/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;

final class LEDataInputStream
extends InputStream {
    int position;
    InputStream in;
    protected byte[] buf;
    protected int pos;

    public LEDataInputStream(InputStream inputStream) {
        this(inputStream, 512);
    }

    public LEDataInputStream(InputStream inputStream, int n) {
        this.in = inputStream;
        if (n > 0) {
            this.buf = new byte[n];
            this.pos = n;
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void close() throws IOException {
        this.buf = null;
        if (this.in != null) {
            this.in.close();
            this.in = null;
        }
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public int available() throws IOException {
        if (this.buf == null) {
            throw new IOException();
        }
        return this.buf.length - this.pos + this.in.available();
    }

    @Override
    public int read() throws IOException {
        if (this.buf == null) {
            throw new IOException();
        }
        if (this.pos < this.buf.length) {
            ++this.position;
            return this.buf[this.pos++] & 0xFF;
        }
        int n = this.in.read();
        if (n != -1) {
            ++this.position;
        }
        return n;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        int n4;
        for (n3 = 0; n3 != n2 && (n4 = this.readData(byArray, n, n2 - n3)) != -1; n3 += n4) {
            n += n4;
        }
        this.position += n3;
        if (n3 == 0 && n3 != n2) {
            return -1;
        }
        return n3;
    }

    private int readData(byte[] byArray, int n, int n2) throws IOException {
        if (this.buf == null) {
            throw new IOException();
        }
        if (n < 0 || n > byArray.length || n2 < 0 || n2 > byArray.length - n) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n3 = 0;
        int n4 = n;
        int n5 = this.buf.length - this.pos;
        if (n5 > 0) {
            n3 = n5 >= n2 ? n2 : n5;
            System.arraycopy(this.buf, this.pos, byArray, n4, n3);
            n4 += n3;
            this.pos += n3;
        }
        if (n3 == n2) {
            return n2;
        }
        int n6 = this.in.read(byArray, n4, n2 - n3);
        if (n6 > 0) {
            return n6 + n3;
        }
        if (n3 == 0) {
            return n6;
        }
        return n3;
    }

    public int readInt() throws IOException {
        byte[] byArray = new byte[4];
        this.read(byArray);
        return (byArray[3] & 0xFF) << 24 | (byArray[2] & 0xFF) << 16 | (byArray[1] & 0xFF) << 8 | byArray[0] & 0xFF;
    }

    public short readShort() throws IOException {
        byte[] byArray = new byte[2];
        this.read(byArray);
        return (short)((byArray[1] & 0xFF) << 8 | byArray[0] & 0xFF);
    }

    public void unread(byte[] byArray) throws IOException {
        int n = byArray.length;
        if (n > this.pos) {
            throw new IOException();
        }
        this.position -= n;
        this.pos -= n;
        System.arraycopy(byArray, 0, this.buf, this.pos, n);
    }
}

