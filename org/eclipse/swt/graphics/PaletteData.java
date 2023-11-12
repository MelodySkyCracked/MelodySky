/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public final class PaletteData {
    public boolean isDirect;
    public RGB[] colors;
    public int redMask;
    public int greenMask;
    public int blueMask;
    public int redShift;
    public int greenShift;
    public int blueShift;

    public PaletteData(RGB ... rGBArray) {
        if (rGBArray == null) {
            SWT.error(4);
        }
        this.colors = rGBArray;
        this.isDirect = false;
    }

    public PaletteData(int n, int n2, int n3) {
        this.redMask = n;
        this.greenMask = n2;
        this.blueMask = n3;
        this.isDirect = true;
        this.redShift = this.shiftForMask(n);
        this.greenShift = this.shiftForMask(n2);
        this.blueShift = this.shiftForMask(n3);
    }

    public int getPixel(RGB rGB) {
        if (rGB == null) {
            SWT.error(4);
        }
        if (this.isDirect) {
            int n = 0;
            n |= (this.redShift < 0 ? rGB.red << -this.redShift : rGB.red >>> this.redShift) & this.redMask;
            n |= (this.greenShift < 0 ? rGB.green << -this.greenShift : rGB.green >>> this.greenShift) & this.greenMask;
            return n |= (this.blueShift < 0 ? rGB.blue << -this.blueShift : rGB.blue >>> this.blueShift) & this.blueMask;
        }
        for (int i = 0; i < this.colors.length; ++i) {
            if (!this.colors[i].equals(rGB)) continue;
            return i;
        }
        SWT.error(5);
        return 0;
    }

    public RGB getRGB(int n) {
        if (this.isDirect) {
            int n2 = n & this.redMask;
            n2 = this.redShift < 0 ? n2 >>> -this.redShift : n2 << this.redShift;
            int n3 = n & this.greenMask;
            n3 = this.greenShift < 0 ? n3 >>> -this.greenShift : n3 << this.greenShift;
            int n4 = n & this.blueMask;
            n4 = this.blueShift < 0 ? n4 >>> -this.blueShift : n4 << this.blueShift;
            return new RGB(n2, n3, n4);
        }
        if (n < 0 || n >= this.colors.length) {
            SWT.error(5);
        }
        return this.colors[n];
    }

    public RGB[] getRGBs() {
        return this.colors;
    }

    int shiftForMask(int n) {
        for (int i = 31; i >= 0; --i) {
            if ((n >> i & 1) == 0) continue;
            return 7 - i;
        }
        return 32;
    }
}

