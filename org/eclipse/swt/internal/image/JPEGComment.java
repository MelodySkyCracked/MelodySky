/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGVariableSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGComment
extends JPEGVariableSizeSegment {
    public JPEGComment(byte[] byArray) {
        super(byArray);
    }

    public JPEGComment(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
    }

    @Override
    public int signature() {
        return 65534;
    }
}

