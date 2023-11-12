/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGFixedSizeSegment;

final class JPEGEndOfImage
extends JPEGFixedSizeSegment {
    public JPEGEndOfImage() {
    }

    public JPEGEndOfImage(byte[] byArray) {
        super(byArray);
    }

    @Override
    public int signature() {
        return 65497;
    }

    @Override
    public int fixedSize() {
        return 2;
    }
}

