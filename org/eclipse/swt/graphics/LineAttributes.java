/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

public class LineAttributes {
    public float width;
    public int style;
    public int cap;
    public int join;
    public float[] dash;
    public float dashOffset;
    public float miterLimit;

    public LineAttributes(float f) {
        this(f, 1, 1, 1, null, 0.0f, 10.0f);
    }

    public LineAttributes(float f, int n, int n2) {
        this(f, n, n2, 1, null, 0.0f, 10.0f);
    }

    public LineAttributes(float f, int n, int n2, int n3, float[] fArray, float f2, float f3) {
        this.width = f;
        this.cap = n;
        this.join = n2;
        this.style = n3;
        this.dash = fArray;
        this.dashOffset = f2;
        this.miterLimit = f3;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof LineAttributes)) {
            return false;
        }
        LineAttributes lineAttributes = (LineAttributes)object;
        if (lineAttributes.width != this.width) {
            return false;
        }
        if (lineAttributes.cap != this.cap) {
            return false;
        }
        if (lineAttributes.join != this.join) {
            return false;
        }
        if (lineAttributes.style != this.style) {
            return false;
        }
        if (lineAttributes.dashOffset != this.dashOffset) {
            return false;
        }
        if (lineAttributes.miterLimit != this.miterLimit) {
            return false;
        }
        if (lineAttributes.dash != null && this.dash != null) {
            if (lineAttributes.dash.length != this.dash.length) {
                return false;
            }
            for (int i = 0; i < this.dash.length; ++i) {
                if (lineAttributes.dash[i] == this.dash[i]) continue;
                return false;
            }
        } else if (lineAttributes.dash != null || this.dash != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n = Float.floatToIntBits(this.width);
        n = 31 * n + this.cap;
        n = 31 * n + this.join;
        n = 31 * n + this.style;
        n = 31 * n + Float.floatToIntBits(this.dashOffset);
        n = 31 * n + Float.floatToIntBits(this.miterLimit);
        if (this.dash != null) {
            for (float f : this.dash) {
                n = 31 * n + Float.floatToIntBits(f);
            }
        }
        return n;
    }
}

