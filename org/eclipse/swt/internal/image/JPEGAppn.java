/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGVariableSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGAppn
extends JPEGVariableSizeSegment {
    public JPEGAppn(byte[] byArray) {
        super(byArray);
    }

    public JPEGAppn(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
    }

    @Override
    public boolean verify() {
        int n = this.getSegmentMarker();
        return n >= 65504 && n <= 65519;
    }
}

