/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGVariableSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGQuantizationTable
extends JPEGVariableSizeSegment {
    public static byte[] DefaultLuminanceQTable = new byte[]{-1, -37, 0, 67, 0, 16, 11, 10, 16, 24, 40, 51, 61, 12, 12, 14, 19, 26, 58, 60, 55, 14, 13, 16, 24, 40, 57, 69, 56, 14, 17, 22, 29, 51, 87, 80, 62, 18, 22, 37, 56, 68, 109, 103, 77, 24, 35, 55, 64, 81, 104, 113, 92, 49, 64, 78, 87, 103, 121, 120, 101, 72, 92, 95, 98, 112, 100, 103, 99};
    public static byte[] DefaultChrominanceQTable = new byte[]{-1, -37, 0, 67, 1, 17, 18, 24, 47, 99, 99, 99, 99, 18, 21, 26, 66, 99, 99, 99, 99, 24, 26, 56, 99, 99, 99, 99, 99, 47, 66, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99};

    public JPEGQuantizationTable(byte[] byArray) {
        super(byArray);
    }

    public JPEGQuantizationTable(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
    }

    public static JPEGQuantizationTable defaultChrominanceTable() {
        byte[] byArray = new byte[DefaultChrominanceQTable.length];
        System.arraycopy(DefaultChrominanceQTable, 0, byArray, 0, byArray.length);
        return new JPEGQuantizationTable(byArray);
    }

    public static JPEGQuantizationTable defaultLuminanceTable() {
        byte[] byArray = new byte[DefaultLuminanceQTable.length];
        System.arraycopy(DefaultLuminanceQTable, 0, byArray, 0, byArray.length);
        return new JPEGQuantizationTable(byArray);
    }

    public int[] getQuantizationTablesKeys() {
        int[] nArray = new int[4];
        int n = 0;
        int n2 = this.getSegmentLength() - 2;
        int n3 = 4;
        while (n2 > 64) {
            int n4 = this.reference[n3] & 0xF;
            int n5 = (this.reference[n3] & 0xFF) >> 4;
            if (n5 == 0) {
                n3 += 65;
                n2 -= 65;
            } else {
                n3 += 129;
                n2 -= 129;
            }
            if (n >= nArray.length) {
                int[] nArray2 = new int[nArray.length + 4];
                System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
                nArray = nArray2;
            }
            nArray[n] = n4;
            ++n;
        }
        int[] nArray3 = new int[n];
        System.arraycopy(nArray, 0, nArray3, 0, n);
        return nArray3;
    }

    public int[][] getQuantizationTablesValues() {
        Object object;
        int[][] nArrayArray = new int[4][];
        int n = 0;
        int n2 = this.getSegmentLength() - 2;
        int n3 = 4;
        while (n2 > 64) {
            int n4;
            object = new int[64];
            int n5 = (this.reference[n3] & 0xFF) >> 4;
            if (n5 == 0) {
                for (n4 = 0; n4 < ((int[])object).length; ++n4) {
                    object[n4] = this.reference[n3 + n4 + 1] & 0xFF;
                }
                n3 += 65;
                n2 -= 65;
            } else {
                for (n4 = 0; n4 < ((int[])object).length; ++n4) {
                    int n6 = (n4 - 1) * 2;
                    object[n4] = (this.reference[n3 + n6 + 1] & 0xFF) * 256 + (this.reference[n3 + n6 + 2] & 0xFF);
                }
                n3 += 129;
                n2 -= 129;
            }
            if (n >= nArrayArray.length) {
                int[][] nArrayArray2 = new int[nArrayArray.length + 4][];
                System.arraycopy(nArrayArray, 0, nArrayArray2, 0, nArrayArray.length);
                nArrayArray = nArrayArray2;
            }
            nArrayArray[n] = object;
            ++n;
        }
        object = new int[n][];
        System.arraycopy(nArrayArray, 0, object, 0, n);
        return object;
    }

    public void scaleBy(int n) {
        int n2 = n;
        if (n2 <= 0) {
            n2 = 1;
        }
        if (n2 > 100) {
            n2 = 100;
        }
        n2 = n2 < 50 ? 5000 / n2 : 200 - n2 * 2;
        int n3 = this.getSegmentLength() - 2;
        int n4 = 4;
        while (n3 > 64) {
            int n5;
            int n6;
            int n7 = (this.reference[n4] & 0xFF) >> 4;
            if (n7 == 0) {
                for (n6 = n4 + 1; n6 <= n4 + 64; ++n6) {
                    n5 = ((this.reference[n6] & 0xFF) * n2 + 50) / 100;
                    if (n5 <= 0) {
                        n5 = 1;
                    }
                    if (n5 > 255) {
                        n5 = 255;
                    }
                    this.reference[n6] = (byte)n5;
                }
                n4 += 65;
                n3 -= 65;
                continue;
            }
            for (n6 = n4 + 1; n6 <= n4 + 128; n6 += 2) {
                n5 = (((this.reference[n6] & 0xFF) * 256 + (this.reference[n6 + 1] & 0xFF)) * n2 + 50) / 100;
                if (n5 <= 0) {
                    n5 = 1;
                }
                if (n5 > Short.MAX_VALUE) {
                    n5 = Short.MAX_VALUE;
                }
                this.reference[n6] = (byte)(n5 >> 8);
                this.reference[n6 + 1] = (byte)(n5 & 0xFF);
            }
            n4 += 129;
            n3 -= 129;
        }
    }

    @Override
    public int signature() {
        return 65499;
    }
}

