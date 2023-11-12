/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.JPEGVariableSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGScanHeader
extends JPEGVariableSizeSegment {
    public int[][] componentParameters;

    public JPEGScanHeader(byte[] byArray) {
        super(byArray);
    }

    public JPEGScanHeader(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
        this.initializeComponentParameters();
    }

    public int getApproxBitPositionHigh() {
        return this.reference[2 * this.getNumberOfImageComponents() + 7] >> 4;
    }

    public int getApproxBitPositionLow() {
        return this.reference[2 * this.getNumberOfImageComponents() + 7] & 0xF;
    }

    public int getEndOfSpectralSelection() {
        return this.reference[2 * this.getNumberOfImageComponents() + 6];
    }

    public int getNumberOfImageComponents() {
        return this.reference[4];
    }

    public int getStartOfSpectralSelection() {
        return this.reference[2 * this.getNumberOfImageComponents() + 5];
    }

    void initializeComponentParameters() {
        int n = this.getNumberOfImageComponents();
        this.componentParameters = new int[0][];
        for (int i = 0; i < n; ++i) {
            int n2 = 5 + i * 2;
            int n3 = this.reference[n2] & 0xFF;
            int n4 = (this.reference[n2 + 1] & 0xFF) >> 4;
            int n5 = this.reference[n2 + 1] & 0xF;
            if (this.componentParameters.length <= n3) {
                int[][] nArrayArray = new int[n3 + 1][];
                System.arraycopy(this.componentParameters, 0, nArrayArray, 0, this.componentParameters.length);
                this.componentParameters = nArrayArray;
            }
            this.componentParameters[n3] = new int[]{n4, n5};
        }
    }

    public void initializeContents() {
        int n = this.getNumberOfImageComponents();
        int[][] nArray = this.componentParameters;
        if (n == 0 || n != nArray.length) {
            SWT.error(40);
        }
        for (int i = 0; i < n; ++i) {
            int n2 = i * 2 + 5;
            int[] nArray2 = nArray[i];
            this.reference[n2] = (byte)(i + 1);
            this.reference[n2 + 1] = (byte)(nArray2[0] * 16 + nArray2[1]);
        }
    }

    public void setEndOfSpectralSelection(int n) {
        this.reference[2 * this.getNumberOfImageComponents() + 6] = (byte)n;
    }

    public void setNumberOfImageComponents(int n) {
        this.reference[4] = (byte)(n & 0xFF);
    }

    public void setStartOfSpectralSelection(int n) {
        this.reference[2 * this.getNumberOfImageComponents() + 5] = (byte)n;
    }

    @Override
    public int signature() {
        return 65498;
    }

    public boolean verifyProgressiveScan() {
        int n = this.getStartOfSpectralSelection();
        int n2 = this.getEndOfSpectralSelection();
        int n3 = this.getApproxBitPositionLow();
        int n4 = this.getApproxBitPositionHigh();
        int n5 = this.getNumberOfImageComponents();
        return (n == 0 && n2 == 0 || n <= n2 && n2 <= 63) && n3 <= 13 && n4 <= 13 && (n4 == 0 || n4 == n3 + 1) && (n == 0 || n > 0 && n5 == 1);
    }

    public boolean isACProgressiveScan() {
        return this.getStartOfSpectralSelection() != 0 && this.getEndOfSpectralSelection() != 0;
    }

    public boolean isDCProgressiveScan() {
        return this.getStartOfSpectralSelection() == 0 && this.getEndOfSpectralSelection() == 0;
    }

    public boolean isFirstScan() {
        return this.getApproxBitPositionHigh() == 0;
    }
}

