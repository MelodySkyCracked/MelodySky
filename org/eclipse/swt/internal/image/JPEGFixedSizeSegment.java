/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.JPEGSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

abstract class JPEGFixedSizeSegment
extends JPEGSegment {
    public JPEGFixedSizeSegment() {
        this.reference = new byte[this.fixedSize()];
        this.setSegmentMarker(this.signature());
    }

    public JPEGFixedSizeSegment(byte[] byArray) {
        super(byArray);
    }

    public JPEGFixedSizeSegment(LEDataInputStream lEDataInputStream) {
        this.reference = new byte[this.fixedSize()];
        try {
            lEDataInputStream.read(this.reference);
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
    }

    public abstract int fixedSize();

    @Override
    public int getSegmentLength() {
        return this.fixedSize() - 2;
    }

    @Override
    public void setSegmentLength(int n) {
    }
}

