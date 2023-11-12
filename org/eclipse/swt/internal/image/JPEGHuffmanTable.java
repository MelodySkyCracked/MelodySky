/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.internal.image.JPEGVariableSizeSegment;
import org.eclipse.swt.internal.image.LEDataInputStream;

final class JPEGHuffmanTable
extends JPEGVariableSizeSegment {
    JPEGHuffmanTable[] allTables;
    int tableClass;
    int tableIdentifier;
    int[] dhMaxCodes;
    int[] dhMinCodes;
    int[] dhValPtrs;
    int[] dhValues;
    int[] ehCodes;
    byte[] ehCodeLengths;
    static byte[] DCLuminanceTable = new byte[]{-1, -60, 0, 31, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    static byte[] DCChrominanceTable = new byte[]{-1, -60, 0, 31, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    static byte[] ACLuminanceTable = new byte[]{-1, -60, 0, -75, 16, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125, 1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, -127, -111, -95, 8, 35, 66, -79, -63, 21, 82, -47, -16, 36, 51, 98, 114, -126, 9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6};
    static byte[] ACChrominanceTable = new byte[]{-1, -60, 0, -75, 17, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119, 0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, -127, 8, 20, 66, -111, -95, -79, -63, 9, 35, 51, 82, -16, 21, 98, 114, -47, 10, 22, 36, 52, -31, 37, -15, 23, 24, 25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -126, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -30, -29, -28, -27, -26, -25, -24, -23, -22, -14, -13, -12, -11, -10, -9, -8, -7, -6};

    public JPEGHuffmanTable(byte[] byArray) {
        super(byArray);
    }

    public JPEGHuffmanTable(LEDataInputStream lEDataInputStream) {
        super(lEDataInputStream);
        this.initialize();
    }

    public JPEGHuffmanTable[] getAllTables() {
        return this.allTables;
    }

    public static JPEGHuffmanTable getDefaultACChrominanceTable() {
        JPEGHuffmanTable jPEGHuffmanTable = new JPEGHuffmanTable(ACChrominanceTable);
        jPEGHuffmanTable.initialize();
        return jPEGHuffmanTable;
    }

    public static JPEGHuffmanTable getDefaultACLuminanceTable() {
        JPEGHuffmanTable jPEGHuffmanTable = new JPEGHuffmanTable(ACLuminanceTable);
        jPEGHuffmanTable.initialize();
        return jPEGHuffmanTable;
    }

    public static JPEGHuffmanTable getDefaultDCChrominanceTable() {
        JPEGHuffmanTable jPEGHuffmanTable = new JPEGHuffmanTable(DCChrominanceTable);
        jPEGHuffmanTable.initialize();
        return jPEGHuffmanTable;
    }

    public static JPEGHuffmanTable getDefaultDCLuminanceTable() {
        JPEGHuffmanTable jPEGHuffmanTable = new JPEGHuffmanTable(DCLuminanceTable);
        jPEGHuffmanTable.initialize();
        return jPEGHuffmanTable;
    }

    public int[] getDhMaxCodes() {
        return this.dhMaxCodes;
    }

    public int[] getDhMinCodes() {
        return this.dhMinCodes;
    }

    public int[] getDhValPtrs() {
        return this.dhValPtrs;
    }

    public int[] getDhValues() {
        return this.dhValues;
    }

    public int getTableClass() {
        return this.tableClass;
    }

    public int getTableIdentifier() {
        return this.tableIdentifier;
    }

    void initialize() {
        int n = this.getSegmentLength() - 2;
        int n2 = 4;
        int[] nArray = new int[16];
        JPEGHuffmanTable[] jPEGHuffmanTableArray = new JPEGHuffmanTable[8];
        int n3 = 0;
        while (n > 0) {
            int[] nArray2;
            int n4;
            int n5;
            int n6 = (this.reference[n2] & 0xFF) >> 4;
            int n7 = this.reference[n2] & 0xF;
            ++n2;
            int n8 = 0;
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = n5 = this.reference[n2 + i] & 0xFF;
                n8 += n5;
            }
            n2 += 16;
            n -= 17;
            int[] nArray3 = new int[n8];
            for (n5 = 0; n5 < n8; ++n5) {
                nArray3[n5] = this.reference[n2 + n5] & 0xFF;
            }
            n2 += n8;
            n -= n8;
            int[] nArray4 = new int[50];
            int n9 = 0;
            for (int i = 0; i < 16; ++i) {
                for (n4 = 0; n4 < nArray[i]; ++n4) {
                    if (n9 >= nArray4.length) {
                        int[] nArray5 = new int[nArray4.length + 50];
                        System.arraycopy(nArray4, 0, nArray5, 0, nArray4.length);
                        nArray4 = nArray5;
                    }
                    nArray4[n9] = i + 1;
                    ++n9;
                }
            }
            if (n9 < nArray4.length) {
                int[] nArray6 = new int[n9];
                System.arraycopy(nArray4, 0, nArray6, 0, n9);
                nArray4 = nArray6;
            }
            int[] nArray7 = new int[50];
            n4 = 0;
            int n10 = 1;
            int n11 = 0;
            int n12 = nArray4[0];
            int n13 = 0;
            while (n13 < n9) {
                while (n13 < n9 && nArray4[n13] == n12) {
                    if (n4 >= nArray7.length) {
                        nArray2 = new int[nArray7.length + 50];
                        System.arraycopy(nArray7, 0, nArray2, 0, nArray7.length);
                        nArray7 = nArray2;
                    }
                    nArray7[n4] = n11++;
                    ++n4;
                    ++n13;
                }
                n11 *= 2;
                ++n12;
            }
            if (n4 < nArray7.length) {
                nArray2 = new int[n4];
                System.arraycopy(nArray7, 0, nArray2, 0, n4);
                nArray7 = nArray2;
            }
            n10 = 0;
            nArray2 = new int[16];
            int[] nArray8 = new int[16];
            int[] nArray9 = new int[16];
            for (int i = 0; i < 16; ++i) {
                int n14 = nArray[i];
                if (n14 == 0) {
                    nArray2[i] = -1;
                    continue;
                }
                nArray9[i] = n10;
                nArray8[i] = nArray7[n10];
                nArray2[i] = nArray7[(n10 += n14) - 1];
            }
            int[] nArray10 = new int[256];
            byte[] byArray = new byte[256];
            for (int i = 0; i < n4; ++i) {
                nArray10[nArray3[i]] = nArray7[i];
                byArray[nArray3[i]] = (byte)nArray4[i];
            }
            JPEGHuffmanTable jPEGHuffmanTable = new JPEGHuffmanTable(this.reference);
            jPEGHuffmanTable.tableClass = n6;
            jPEGHuffmanTable.tableIdentifier = n7;
            jPEGHuffmanTable.dhValues = nArray3;
            jPEGHuffmanTable.dhMinCodes = nArray8;
            jPEGHuffmanTable.dhMaxCodes = nArray2;
            jPEGHuffmanTable.dhValPtrs = nArray9;
            jPEGHuffmanTable.ehCodes = nArray10;
            jPEGHuffmanTable.ehCodeLengths = byArray;
            jPEGHuffmanTableArray[n3] = jPEGHuffmanTable;
            ++n3;
        }
        this.allTables = new JPEGHuffmanTable[n3];
        System.arraycopy(jPEGHuffmanTableArray, 0, this.allTables, 0, n3);
    }

    @Override
    public int signature() {
        return 65476;
    }
}

