/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.LEDataOutputStream;

class JPEGSegment {
    public byte[] reference;

    JPEGSegment() {
    }

    public JPEGSegment(byte[] byArray) {
        this.reference = byArray;
    }

    public int signature() {
        return 0;
    }

    public boolean verify() {
        return this.getSegmentMarker() == this.signature();
    }

    public int getSegmentMarker() {
        return (this.reference[0] & 0xFF) << 8 | this.reference[1] & 0xFF;
    }

    public void setSegmentMarker(int n) {
        this.reference[0] = (byte)((n & 0xFF00) >> 8);
        this.reference[1] = (byte)(n & 0xFF);
    }

    public int getSegmentLength() {
        return (this.reference[2] & 0xFF) << 8 | this.reference[3] & 0xFF;
    }

    public void setSegmentLength(int n) {
        this.reference[2] = (byte)((n & 0xFF00) >> 8);
        this.reference[3] = (byte)(n & 0xFF);
    }

    public boolean writeToStream(LEDataOutputStream lEDataOutputStream) {
        try {
            lEDataOutputStream.write(this.reference);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

