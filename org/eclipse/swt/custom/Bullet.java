/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

public class Bullet {
    public int type;
    public StyleRange style;
    public String text;
    int[] linesIndices;
    int count;

    public Bullet(StyleRange styleRange) {
        this(1, styleRange);
    }

    public Bullet(int n, StyleRange styleRange) {
        if (styleRange == null) {
            SWT.error(4);
        }
        if (styleRange.metrics == null) {
            SWT.error(4);
        }
        this.type = n;
        this.style = styleRange;
    }

    void addIndices(int n, int n2) {
        if (this.linesIndices == null) {
            this.linesIndices = new int[n2];
            this.count = n2;
            for (int i = 0; i < n2; ++i) {
                this.linesIndices[i] = n + i;
            }
        } else {
            int n3;
            int n4;
            for (n4 = 0; n4 < this.count && n > this.linesIndices[n4]; ++n4) {
            }
            for (n3 = n4; n3 < this.count && n + n2 > this.linesIndices[n3]; ++n3) {
            }
            int n5 = n4 + n2 + this.count - n3;
            if (n5 > this.linesIndices.length) {
                int[] nArray = new int[n5];
                System.arraycopy(this.linesIndices, 0, nArray, 0, this.count);
                this.linesIndices = nArray;
            }
            System.arraycopy(this.linesIndices, n3, this.linesIndices, n4 + n2, this.count - n3);
            for (int i = 0; i < n2; ++i) {
                this.linesIndices[n4 + i] = n + i;
            }
            this.count = n5;
        }
    }

    int indexOf(int n) {
        for (int i = 0; i < this.count; ++i) {
            if (this.linesIndices[i] != n) continue;
            return i;
        }
        return -1;
    }

    public int hashCode() {
        return this.style.hashCode() ^ this.type;
    }

    int[] removeIndices(int n, int n2, int n3, boolean bl) {
        int n4;
        if (this.count == 0) {
            return null;
        }
        if (n > this.linesIndices[this.count - 1]) {
            return null;
        }
        int n5 = n + n2;
        int n6 = n3 - n2;
        for (n4 = 0; n4 < this.count; ++n4) {
            int n7;
            int n8 = this.linesIndices[n4];
            if (n > n8) continue;
            for (n7 = n4; n7 < this.count && this.linesIndices[n7] < n5; ++n7) {
            }
            if (bl) {
                int n9 = n7;
                while (n9 < this.count) {
                    int n10;
                    int[] nArray = this.linesIndices;
                    int n11 = n10 = n9++;
                    nArray[n11] = nArray[n11] + n6;
                }
            }
            int[] nArray = new int[this.count - n7];
            System.arraycopy(this.linesIndices, n7, nArray, 0, this.count - n7);
            System.arraycopy(this.linesIndices, n7, this.linesIndices, n4, this.count - n7);
            this.count -= n7 - n4;
            return nArray;
        }
        n4 = 0;
        while (n4 < this.count) {
            int n12;
            int[] nArray = this.linesIndices;
            int n13 = n12 = n4++;
            nArray[n13] = nArray[n13] + n6;
        }
        return null;
    }

    int size() {
        return this.count;
    }
}

