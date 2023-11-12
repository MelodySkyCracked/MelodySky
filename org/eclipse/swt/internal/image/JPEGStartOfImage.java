/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGFixedSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGStartOfImage
extends JPEGFixedSizeSegment {
    public JPEGStartOfImage() {
    }

    public JPEGStartOfImage(byte[] byArray) {
        super(byArray);
    }

    public JPEGStartOfImage(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
    }

    @Override
    public int signature() {
        return 65496;
    }

    @Override
    public int fixedSize() {
        return 2;
    }
}

