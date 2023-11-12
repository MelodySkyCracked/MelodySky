/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.Serializable;
import org.eclipse.swt.SWT;

public final class RGB
implements Serializable {
    public int red;
    public int green;
    public int blue;
    static final long serialVersionUID = 3258415023461249074L;

    public RGB(int n, int n2, int n3) {
        if (n > 255 || n < 0 || n2 > 255 || n2 < 0 || n3 > 255 || n3 < 0) {
            SWT.error(5);
        }
        this.red = n;
        this.green = n2;
        this.blue = n3;
    }

    public RGB(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f < 0.0f || f > 360.0f || f2 < 0.0f || f2 > 1.0f || f3 < 0.0f || f3 > 1.0f) {
            SWT.error(5);
        }
        float f6 = 0.0f;
        if (f2 == 0.0f) {
            f5 = f3;
            f4 = f3;
            f6 = f3;
        } else {
            if (f == 360.0f) {
                f = 0.0f;
            }
            int n = (int)(f /= 60.0f);
            float f7 = f - (float)n;
            float f8 = f3 * (1.0f - f2);
            float f9 = f3 * (1.0f - f2 * f7);
            float f10 = f3 * (1.0f - f2 * (1.0f - f7));
            switch (n) {
                case 0: {
                    f6 = f3;
                    f4 = f10;
                    f5 = f8;
                    break;
                }
                case 1: {
                    f6 = f9;
                    f4 = f3;
                    f5 = f8;
                    break;
                }
                case 2: {
                    f6 = f8;
                    f4 = f3;
                    f5 = f10;
                    break;
                }
                case 3: {
                    f6 = f8;
                    f4 = f9;
                    f5 = f3;
                    break;
                }
                case 4: {
                    f6 = f10;
                    f4 = f8;
                    f5 = f3;
                    break;
                }
                default: {
                    f6 = f3;
                    f4 = f8;
                    f5 = f9;
                }
            }
        }
        this.red = (int)((double)(f6 * 255.0f) + 0.5);
        this.green = (int)((double)(f4 * 255.0f) + 0.5);
        this.blue = (int)((double)(f5 * 255.0f) + 0.5);
    }

    public float[] getHSB() {
        float f;
        float f2 = (float)this.red / 255.0f;
        float f3 = (float)this.green / 255.0f;
        float f4 = (float)this.blue / 255.0f;
        float f5 = Math.max(Math.max(f2, f3), f4);
        float f6 = Math.min(Math.min(f2, f3), f4);
        float f7 = f5 - f6;
        float f8 = 0.0f;
        float f9 = f5;
        float f10 = f = f5 == 0.0f ? 0.0f : (f5 - f6) / f5;
        if (f7 != 0.0f) {
            f8 = f2 == f5 ? (f3 - f4) / f7 : (f3 == f5 ? 2.0f + (f4 - f2) / f7 : 4.0f + (f2 - f3) / f7);
            if ((f8 *= 60.0f) < 0.0f) {
                f8 += 360.0f;
            }
        }
        return new float[]{f8, f, f9};
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RGB)) {
            return false;
        }
        RGB rGB = (RGB)object;
        return rGB.red == this.red && rGB.green == this.green && rGB.blue == this.blue;
    }

    public int hashCode() {
        return this.blue << 16 | this.green << 8 | this.red;
    }

    public String toString() {
        return "RGB {" + this.red + ", " + this.green + ", " + this.blue;
    }
}

