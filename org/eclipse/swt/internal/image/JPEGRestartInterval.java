/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGFixedSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGRestartInterval
extends JPEGFixedSizeSegment {
    public JPEGRestartInterval(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
    }

    @Override
    public int signature() {
        return 65501;
    }

    public int getRestartInterval() {
        return (this.reference[4] & 0xFF) << 8 | this.reference[5] & 0xFF;
    }

    @Override
    public int fixedSize() {
        return 6;
    }
}

