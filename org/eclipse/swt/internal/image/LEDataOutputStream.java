/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.OutputStream;

final class LEDataOutputStream
extends OutputStream {
    OutputStream out;

    public LEDataOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
    }

    public void writeByte(byte by) throws IOException {
        this.out.write(by & 0xFF);
    }

    public void writeInt(int n) throws IOException {
        this.out.write(n & 0xFF);
        this.out.write(n >> 8 & 0xFF);
        this.out.write(n >> 16 & 0xFF);
        this.out.write(n >> 24 & 0xFF);
    }

    public void writeShort(int n) throws IOException {
        this.out.write(n & 0xFF);
        this.out.write(n >> 8 & 0xFF);
    }
}

