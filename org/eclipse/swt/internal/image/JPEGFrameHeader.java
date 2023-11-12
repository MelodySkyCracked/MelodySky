/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.JPEGVariableSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGFrameHeader
extends JPEGVariableSizeSegment {
    int maxVFactor;
    int maxHFactor;
    public int[] componentIdentifiers;
    public int[][] componentParameters;

    public JPEGFrameHeader(byte[] byArray) {
        super(byArray);
    }

    public JPEGFrameHeader(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
        this.initializeComponentParameters();
    }

    public int getSamplePrecision() {
        return this.reference[4] & 0xFF;
    }

    public int getNumberOfLines() {
        return (this.reference[5] & 0xFF) << 8 | this.reference[6] & 0xFF;
    }

    public int getSamplesPerLine() {
        return (this.reference[7] & 0xFF) << 8 | this.reference[8] & 0xFF;
    }

    public int getNumberOfImageComponents() {
        return this.reference[9] & 0xFF;
    }

    public void setSamplePrecision(int n) {
        this.reference[4] = (byte)(n & 0xFF);
    }

    public void setNumberOfLines(int n) {
        this.reference[5] = (byte)((n & 0xFF00) >> 8);
        this.reference[6] = (byte)(n & 0xFF);
    }

    public void setSamplesPerLine(int n) {
        this.reference[7] = (byte)((n & 0xFF00) >> 8);
        this.reference[8] = (byte)(n & 0xFF);
    }

    public void setNumberOfImageComponents(int n) {
        this.reference[9] = (byte)(n & 0xFF);
    }

    public int getMaxHFactor() {
        return this.maxHFactor;
    }

    public int getMaxVFactor() {
        return this.maxVFactor;
    }

    public void setMaxHFactor(int n) {
        this.maxHFactor = n;
    }

    public void setMaxVFactor(int n) {
        this.maxVFactor = n;
    }

    void initializeComponentParameters() {
        int n;
        int n2;
        int n3;
        int n4;
        int n5 = this.getNumberOfImageComponents();
        this.componentIdentifiers = new int[n5];
        int[][] nArrayArray = new int[][]{};
        int n6 = 1;
        int n7 = 1;
        for (n4 = 0; n4 < n5; ++n4) {
            int n8;
            n3 = n4 * 3 + 10;
            this.componentIdentifiers[n4] = n8 = this.reference[n3] & 0xFF;
            n2 = (this.reference[n3 + 1] & 0xFF) >> 4;
            int n9 = this.reference[n3 + 1] & 0xF;
            n = this.reference[n3 + 2] & 0xFF;
            if (n2 > n6) {
                n6 = n2;
            }
            if (n9 > n7) {
                n7 = n9;
            }
            int[] nArray = new int[]{n, n2, n9, 0, 0};
            if (nArrayArray.length <= n8) {
                int[][] nArrayArray2 = new int[n8 + 1][];
                System.arraycopy(nArrayArray, 0, nArrayArray2, 0, nArrayArray.length);
                nArrayArray = nArrayArray2;
            }
            nArrayArray[n8] = nArray;
        }
        n4 = this.getSamplesPerLine();
        n3 = this.getNumberOfLines();
        int[] nArray = new int[]{8, 16, 24, 32};
        for (n2 = 0; n2 < n5; ++n2) {
            int[] nArray2 = nArrayArray[this.componentIdentifiers[n2]];
            n = nArray2[1];
            int n10 = nArray2[2];
            int n11 = (n4 * n + n6 - 1) / n6;
            int n12 = (n3 * n10 + n7 - 1) / n7;
            int n13 = this.roundUpToMultiple(n11, nArray[n - 1]);
            int n14 = this.roundUpToMultiple(n12, nArray[n10 - 1]);
            nArray2[3] = n13;
            nArray2[4] = n14;
        }
        this.setMaxHFactor(n6);
        this.setMaxVFactor(n7);
        this.componentParameters = nArrayArray;
    }

    public void initializeContents() {
        int n;
        int[] nArray;
        int n2;
        int n3;
        int n4 = this.getNumberOfImageComponents();
        if (n4 == 0 || n4 != this.componentParameters.length) {
            SWT.error(40);
        }
        int n5 = 0;
        int n6 = 0;
        int[][] nArray2 = this.componentParameters;
        for (n3 = 0; n3 < n4; ++n3) {
            n2 = n3 * 3 + 10;
            nArray = nArray2[this.componentIdentifiers[n3]];
            n = nArray[1];
            int n7 = nArray[2];
            if (n * n7 > 4) {
                SWT.error(40);
            }
            this.reference[n2] = (byte)(n3 + 1);
            this.reference[n2 + 1] = (byte)(n * 16 + n7);
            this.reference[n2 + 2] = (byte)nArray[0];
            if (n > n5) {
                n5 = n;
            }
            if (n7 <= n6) continue;
            n6 = n7;
        }
        n3 = this.getSamplesPerLine();
        n2 = this.getNumberOfLines();
        nArray = new int[]{8, 16, 24, 32};
        for (n = 0; n < n4; ++n) {
            int[] nArray3 = nArray2[this.componentIdentifiers[n]];
            int n8 = nArray3[1];
            int n9 = nArray3[2];
            int n10 = (n3 * n8 + n5 - 1) / n5;
            int n11 = (n2 * n9 + n6 - 1) / n6;
            int n12 = this.roundUpToMultiple(n10, nArray[n8 - 1]);
            int n13 = this.roundUpToMultiple(n11, nArray[n9 - 1]);
            nArray3[3] = n12;
            nArray3[4] = n13;
        }
        this.setMaxHFactor(n5);
        this.setMaxVFactor(n6);
    }

    int roundUpToMultiple(int n, int n2) {
        int n3 = n + n2 - 1;
        return n3 - n3 % n2;
    }

    @Override
    public boolean verify() {
        int n = this.getSegmentMarker();
        return n >= 65472 && n <= 65475 || n >= 65477 && n <= 65479 || n >= 65481 && n <= 65483 || n >= 65485 && n <= 65487;
    }

    public boolean isProgressive() {
        int n = this.getSegmentMarker();
        return n == 65474 || n == 65478 || n == 65482 || n == 65486;
    }

    public boolean isArithmeticCoding() {
        return this.getSegmentMarker() >= 65481;
    }
}

