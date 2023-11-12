/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.DPIUtil;

public final class GlyphMetrics {
    public int ascent;
    public int descent;
    public int width;

    public GlyphMetrics(int n, int n2, int n3) {
        if (n < 0 || n2 < 0 || n3 < 0) {
            SWT.error(5);
        }
        this.ascent = n;
        this.descent = n2;
        this.width = n3;
    }

    int getAscentInPixels() {
        return DPIUtil.autoScaleUp(this.ascent);
    }

    int getDescentInPixels() {
        return DPIUtil.autoScaleUp(this.descent);
    }

    int getWidthInPixels() {
        return DPIUtil.autoScaleUp(this.width);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof GlyphMetrics)) {
            return false;
        }
        GlyphMetrics glyphMetrics = (GlyphMetrics)object;
        return glyphMetrics.ascent == this.ascent && glyphMetrics.descent == this.descent && glyphMetrics.width == this.width;
    }

    public int hashCode() {
        return this.ascent ^ this.descent ^ this.width;
    }

    public String toString() {
        return "GlyphMetrics {" + this.ascent + ", " + this.descent + ", " + this.width;
    }
}

