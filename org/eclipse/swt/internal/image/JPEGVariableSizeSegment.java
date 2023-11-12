/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.JPEGSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

abstract class JPEGVariableSizeSegment
extends JPEGSegment {
    public JPEGVariableSizeSegment(byte[] byArray) {
        super(byArray);
    }

    public JPEGVariableSizeSegment(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[4];
            lEDataInputStream.read(byArray);
            this.reference = byArray;
            byte[] byArray2 = new byte[this.getSegmentLength() + 2];
            byArray2[0] = byArray[0];
            byArray2[1] = byArray[1];
            byArray2[2] = byArray[2];
            byArray2[3] = byArray[3];
            lEDataInputStream.read(byArray2, 4, byArray2.length - 4);
            this.reference = byArray2;
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
    }
}

