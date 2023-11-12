/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.Serializable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public final class RGBA
implements Serializable {
    public final RGB rgb;
    public int alpha;
    static final long serialVersionUID = 1049467103126495855L;

    public RGBA(int n, int n2, int n3, int n4) {
        if (n4 > 255 || n4 < 0) {
            SWT.error(5);
        }
        this.rgb = new RGB(n, n2, n3);
        this.alpha = n4;
    }

    public RGBA(float f, float f2, float f3, float f4) {
        if (f4 > 255.0f || f4 < 0.0f) {
            SWT.error(5);
        }
        this.rgb = new RGB(f, f2, f3);
        this.alpha = (int)((double)f4 + 0.5);
    }

    public float[] getHSBA() {
        float[] fArray = this.rgb.getHSB();
        return new float[]{fArray[0], fArray[1], fArray[2], this.alpha};
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RGBA)) {
            return false;
        }
        RGBA rGBA = (RGBA)object;
        return rGBA.rgb.red == this.rgb.red && rGBA.rgb.green == this.rgb.green && rGBA.rgb.blue == this.rgb.blue && rGBA.alpha == this.alpha;
    }

    public int hashCode() {
        return this.alpha << 24 | this.rgb.blue << 16 | this.rgb.green << 8 | this.rgb.red;
    }

    public String toString() {
        return "RGBA {" + this.rgb.red + ", " + this.rgb.green + ", " + this.rgb.blue + ", " + this.alpha;
    }
}

