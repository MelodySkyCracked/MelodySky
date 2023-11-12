/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.FileFormat;
import org.eclipse.swt.internal.image.JPEGAppn;
import org.eclipse.swt.internal.image.JPEGArithmeticConditioningTable;
import org.eclipse.swt.internal.image.JPEGComment;
import org.eclipse.swt.internal.image.JPEGDecoder;
import org.eclipse.swt.internal.image.JPEGEndOfImage;
import org.eclipse.swt.internal.image.JPEGFrameHeader;
import org.eclipse.swt.internal.image.JPEGHuffmanTable;
import org.eclipse.swt.internal.image.JPEGQuantizationTable;
import org.eclipse.swt.internal.image.JPEGRestartInterval;
import org.eclipse.swt.internal.image.JPEGScanHeader;
import org.eclipse.swt.internal.image.JPEGSegment;
import org.eclipse.swt.internal.image.JPEGStartOfImage;
import org.eclipse.swt.internal.image.LEDataInputStream;

public final class JPEGFileFormat
extends FileFormat {
    int restartInterval;
    JPEGFrameHeader frameHeader;
    int imageWidth;
    int imageHeight;
    int interleavedMcuCols;
    int interleavedMcuRows;
    int maxV;
    int maxH;
    boolean progressive;
    int samplePrecision;
    int nComponents;
    int[][] frameComponents;
    int[] componentIds;
    byte[][] imageComponents;
    int[] dataUnit;
    int[][][] dataUnits;
    int[] precedingDCs;
    JPEGScanHeader scanHeader;
    byte[] dataBuffer;
    int currentBitCount;
    int bufferCurrentPosition;
    int restartsToGo;
    int nextRestartNumber;
    JPEGHuffmanTable[] acHuffmanTables;
    JPEGHuffmanTable[] dcHuffmanTables;
    int[][] quantizationTables;
    int currentByte;
    int encoderQFactor = 75;
    int eobrun = 0;
    public static final int DCTSIZE = 8;
    public static final int DCTSIZESQR = 64;
    public static final int FIX_0_899976223 = 7373;
    public static final int FIX_1_961570560 = 16069;
    public static final int FIX_2_053119869 = 16819;
    public static final int FIX_0_298631336 = 2446;
    public static final int FIX_1_847759065 = 15137;
    public static final int FIX_1_175875602 = 9633;
    public static final int FIX_3_072711026 = 25172;
    public static final int FIX_0_765366865 = 6270;
    public static final int FIX_2_562915447 = 20995;
    public static final int FIX_0_541196100 = 4433;
    public static final int FIX_0_390180644 = 3196;
    public static final int FIX_1_501321110 = 12299;
    public static final int APP0 = 65504;
    public static final int APP15 = 65519;
    public static final int COM = 65534;
    public static final int DAC = 65484;
    public static final int DHP = 65502;
    public static final int DHT = 65476;
    public static final int DNL = 65500;
    public static final int DRI = 65501;
    public static final int DQT = 65499;
    public static final int EOI = 65497;
    public static final int EXP = 65503;
    public static final int JPG = 65480;
    public static final int JPG0 = 65520;
    public static final int JPG13 = 65533;
    public static final int RST0 = 65488;
    public static final int RST1 = 65489;
    public static final int RST2 = 65490;
    public static final int RST3 = 65491;
    public static final int RST4 = 65492;
    public static final int RST5 = 65493;
    public static final int RST6 = 65494;
    public static final int RST7 = 65495;
    public static final int SOF0 = 65472;
    public static final int SOF1 = 65473;
    public static final int SOF2 = 65474;
    public static final int SOF3 = 65475;
    public static final int SOF5 = 65477;
    public static final int SOF6 = 65478;
    public static final int SOF7 = 65479;
    public static final int SOF9 = 65481;
    public static final int SOF10 = 65482;
    public static final int SOF11 = 65483;
    public static final int SOF13 = 65485;
    public static final int SOF14 = 65486;
    public static final int SOF15 = 65487;
    public static final int SOI = 65496;
    public static final int SOS = 65498;
    public static final int TEM = 65281;
    public static final int TQI = 0;
    public static final int HI = 1;
    public static final int VI = 2;
    public static final int CW = 3;
    public static final int CH = 4;
    public static final int DC = 0;
    public static final int AC = 1;
    public static final int ID_Y = 0;
    public static final int ID_CB = 1;
    public static final int ID_CR = 2;
    public static final RGB[] RGB16;
    public static final int[] ExtendTest;
    public static final int[] ExtendOffset;
    public static final int[] ZigZag8x8;
    public static final int[] CrRTable;
    public static final int[] CbBTable;
    public static final int[] CrGTable;
    public static final int[] CbGTable;
    public static final int[] RYTable;
    public static final int[] GYTable;
    public static final int[] BYTable;
    public static final int[] RCbTable;
    public static final int[] GCbTable;
    public static final int[] BCbTable;
    public static final int[] RCrTable;
    public static final int[] GCrTable;
    public static final int[] BCrTable;
    public static final int[] NBitsTable;

    void compress(ImageData imageData, byte[] byArray, byte[] byArray2, byte[] byArray3) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8 = imageData.width;
        int n9 = imageData.height;
        int n10 = this.maxV * this.maxH;
        this.imageComponents = new byte[this.nComponents][];
        for (int i = 0; i < this.nComponents; ++i) {
            int[] nArray = this.frameComponents[this.componentIds[i]];
            this.imageComponents[i] = new byte[nArray[3] * nArray[4]];
        }
        int[] nArray = this.frameComponents[this.componentIds[0]];
        for (n7 = 0; n7 < n9; ++n7) {
            n6 = n7 * n8;
            n5 = n7 * nArray[3];
            System.arraycopy(byArray, n6, this.imageComponents[0], n5, n8);
        }
        nArray = this.frameComponents[this.componentIds[1]];
        for (n7 = 0; n7 < n9 / this.maxV; ++n7) {
            n6 = n7 * nArray[3];
            for (n5 = 0; n5 < n8 / this.maxH; ++n5) {
                n4 = 0;
                for (n3 = 0; n3 < this.maxV; ++n3) {
                    n2 = (n7 * this.maxV + n3) * n8 + n5 * this.maxH;
                    for (n = 0; n < this.maxH; ++n) {
                        n4 += byArray2[n2 + n] & 0xFF;
                    }
                }
                this.imageComponents[1][n6 + n5] = (byte)(n4 / n10);
            }
        }
        nArray = this.frameComponents[this.componentIds[2]];
        for (n7 = 0; n7 < n9 / this.maxV; ++n7) {
            n6 = n7 * nArray[3];
            for (n5 = 0; n5 < n8 / this.maxH; ++n5) {
                n4 = 0;
                for (n3 = 0; n3 < this.maxV; ++n3) {
                    n2 = (n7 * this.maxV + n3) * n8 + n5 * this.maxH;
                    for (n = 0; n < this.maxH; ++n) {
                        n4 += byArray3[n2 + n] & 0xFF;
                    }
                }
                this.imageComponents[2][n6 + n5] = (byte)(n4 / n10);
            }
        }
        for (n7 = 0; n7 < this.nComponents; ++n7) {
            int n11;
            int n12;
            int n13;
            byte[] byArray4 = this.imageComponents[n7];
            nArray = this.frameComponents[this.componentIds[n7]];
            n5 = nArray[1];
            n4 = nArray[2];
            n3 = nArray[3];
            n2 = nArray[4];
            n = n8 / (this.maxH / n5);
            int n14 = n9 / (this.maxV / n4);
            if (n < n3) {
                n13 = n3 - n;
                for (n12 = 0; n12 < n14; ++n12) {
                    n11 = (n12 + 1) * n3 - n13;
                    int n15 = byArray4[n11 > 0 ? n11 - 1 : 0] & 0xFF;
                    for (int i = 0; i < n13; ++i) {
                        byArray4[n11 + i] = (byte)n15;
                    }
                }
            }
            if (n14 >= n2) continue;
            n13 = n14 > 0 ? (n14 - 1) * n3 : 1;
            int n16 = n12 = n14 > 0 ? n14 : 1;
            while (n12 <= n2) {
                n11 = (n12 - 1) * n3;
                System.arraycopy(byArray4, n13, byArray4, n11, n3);
                ++n12;
            }
        }
    }

    void convert4BitRGBToYCbCr(ImageData imageData) {
        int n;
        int n2;
        Object object;
        int n3;
        RGB[] rGBArray = imageData.getRGBs();
        int n4 = rGBArray.length;
        byte[] byArray = new byte[n4];
        byte[] byArray2 = new byte[n4];
        byte[] byArray3 = new byte[n4];
        int n5 = imageData.width;
        int n6 = imageData.height;
        for (n3 = 0; n3 < n4; ++n3) {
            byte[] byArray4;
            object = rGBArray[n3];
            int n7 = ((RGB)object).red;
            int n8 = ((RGB)object).green;
            int n9 = ((RGB)object).blue;
            n2 = RYTable[n7] + GYTable[n8] + BYTable[n9];
            byArray[n3] = (byte)(n2 >> 16);
            if (n2 < 0 && (n2 & 0xFFFF) != 0) {
                byArray4 = byArray;
                int n10 = n = n3;
                byArray4[n10] = (byte)(byArray4[n10] - 1);
            }
            n2 = RCbTable[n7] + GCbTable[n8] + BCbTable[n9];
            byArray2[n3] = (byte)(n2 >> 16);
            if (n2 < 0 && (n2 & 0xFFFF) != 0) {
                byArray4 = byArray2;
                int n11 = n = n3;
                byArray4[n11] = (byte)(byArray4[n11] - 1);
            }
            n2 = RCrTable[n7] + GCrTable[n8] + BCrTable[n9];
            byArray3[n3] = (byte)(n2 >> 16);
            if (n2 >= 0 || (n2 & 0xFFFF) == 0) continue;
            byArray4 = byArray3;
            int n12 = n = n3;
            byArray4[n12] = (byte)(byArray4[n12] - 1);
        }
        n3 = n5 * n6;
        object = new byte[n3];
        byte[] byArray5 = new byte[n3];
        byte[] byArray6 = new byte[n3];
        byte[] byArray7 = imageData.data;
        n2 = imageData.bytesPerLine;
        int n13 = n5 >> 1;
        for (n = 0; n < n6; ++n) {
            for (int i = 0; i < n13; ++i) {
                int n14 = n * n2 + i;
                int n15 = n * n5 + i * 2;
                int n16 = byArray7[n14] & 0xFF;
                int n17 = n16 >> 4;
                object[n15] = byArray[n17];
                byArray5[n15] = byArray2[n17];
                byArray6[n15] = byArray3[n17];
                object[n15 + 1] = byArray[n16 &= 0xF];
                byArray5[n15 + 1] = byArray2[n16];
                byArray6[n15 + 1] = byArray3[n16];
            }
        }
        this.compress(imageData, (byte[])object, byArray5, byArray6);
    }

    void convert8BitRGBToYCbCr(ImageData imageData) {
        byte[] byArray;
        int n;
        int n2;
        int n3;
        RGB[] rGBArray = imageData.getRGBs();
        int n4 = rGBArray.length;
        byte[] byArray2 = new byte[n4];
        byte[] byArray3 = new byte[n4];
        byte[] byArray4 = new byte[n4];
        int n5 = imageData.width;
        int n6 = imageData.height;
        for (n3 = 0; n3 < n4; ++n3) {
            int n7;
            RGB rGB = rGBArray[n3];
            n2 = rGB.red;
            n = rGB.green;
            int n8 = rGB.blue;
            int n9 = RYTable[n2] + GYTable[n] + BYTable[n8];
            byArray2[n3] = (byte)(n9 >> 16);
            if (n9 < 0 && (n9 & 0xFFFF) != 0) {
                byArray = byArray2;
                int n10 = n7 = n3;
                byArray[n10] = (byte)(byArray[n10] - 1);
            }
            n9 = RCbTable[n2] + GCbTable[n] + BCbTable[n8];
            byArray3[n3] = (byte)(n9 >> 16);
            if (n9 < 0 && (n9 & 0xFFFF) != 0) {
                byArray = byArray3;
                int n11 = n7 = n3;
                byArray[n11] = (byte)(byArray[n11] - 1);
            }
            n9 = RCrTable[n2] + GCrTable[n] + BCrTable[n8];
            byArray4[n3] = (byte)(n9 >> 16);
            if (n9 >= 0 || (n9 & 0xFFFF) == 0) continue;
            byArray = byArray4;
            int n12 = n7 = n3;
            byArray[n12] = (byte)(byArray[n12] - 1);
        }
        n3 = imageData.width;
        int n13 = n6;
        n2 = n5 + 3 >> 2 << 2;
        n = n3 * n13;
        byte[] byArray5 = new byte[n];
        byte[] byArray6 = new byte[n];
        byArray = new byte[n];
        byte[] byArray7 = imageData.data;
        for (int i = 0; i < n6; ++i) {
            int n14 = i * n2;
            int n15 = i * n3;
            for (int j = 0; j < n5; ++j) {
                int n16 = byArray7[n14 + j] & 0xFF;
                int n17 = n15 + j;
                byArray5[n17] = byArray2[n16];
                byArray6[n17] = byArray3[n16];
                byArray[n17] = byArray4[n16];
            }
        }
        this.compress(imageData, byArray5, byArray6, byArray);
    }

    byte[] convertCMYKToRGB() {
        return new byte[0];
    }

    void convertImageToYCbCr(ImageData imageData) {
        switch (imageData.depth) {
            case 4: {
                this.convert4BitRGBToYCbCr(imageData);
                return;
            }
            case 8: {
                this.convert8BitRGBToYCbCr(imageData);
                return;
            }
            case 16: 
            case 24: 
            case 32: {
                this.convertMultiRGBToYCbCr(imageData);
                return;
            }
        }
        SWT.error(38);
    }

    void convertMultiRGBToYCbCr(ImageData imageData) {
        int n = imageData.width;
        int n2 = imageData.height;
        int n3 = n * n2;
        byte[] byArray = new byte[n3];
        byte[] byArray2 = new byte[n3];
        byte[] byArray3 = new byte[n3];
        PaletteData paletteData = imageData.palette;
        int[] nArray = new int[n];
        if (paletteData.isDirect) {
            int n4 = paletteData.redMask;
            int n5 = paletteData.greenMask;
            int n6 = paletteData.blueMask;
            int n7 = paletteData.redShift;
            int n8 = paletteData.greenShift;
            int n9 = paletteData.blueShift;
            for (int i = 0; i < n2; ++i) {
                imageData.getPixels(0, i, n, nArray, 0);
                int n10 = i * n;
                for (int j = 0; j < n; ++j) {
                    int n11 = nArray[j];
                    int n12 = n10 + j;
                    int n13 = n11 & n4;
                    n13 = n7 < 0 ? n13 >>> -n7 : n13 << n7;
                    int n14 = n11 & n5;
                    n14 = n8 < 0 ? n14 >>> -n8 : n14 << n8;
                    int n15 = n11 & n6;
                    n15 = n9 < 0 ? n15 >>> -n9 : n15 << n9;
                    byArray[n12] = (byte)(RYTable[n13] + GYTable[n14] + BYTable[n15] >> 16);
                    byArray2[n12] = (byte)(RCbTable[n13] + GCbTable[n14] + BCbTable[n15] >> 16);
                    byArray3[n12] = (byte)(RCrTable[n13] + GCrTable[n14] + BCrTable[n15] >> 16);
                }
            }
        } else {
            for (int i = 0; i < n2; ++i) {
                imageData.getPixels(0, i, n, nArray, 0);
                int n16 = i * n;
                for (int j = 0; j < n; ++j) {
                    int n17 = nArray[j];
                    int n18 = n16 + j;
                    RGB rGB = paletteData.getRGB(n17);
                    int n19 = rGB.red;
                    int n20 = rGB.green;
                    int n21 = rGB.blue;
                    byArray[n18] = (byte)(RYTable[n19] + GYTable[n20] + BYTable[n21] >> 16);
                    byArray2[n18] = (byte)(RCbTable[n19] + GCbTable[n20] + BCbTable[n21] >> 16);
                    byArray3[n18] = (byte)(RCrTable[n19] + GCrTable[n20] + BCrTable[n21] >> 16);
                }
            }
        }
        this.compress(imageData, byArray, byArray2, byArray3);
    }

    byte[] convertYToRGB() {
        int n = this.frameComponents[this.componentIds[0]][3];
        int n2 = ((this.imageWidth * 8 + 7) / 8 + 3) / 4 * 4;
        byte[] byArray = new byte[n2 * this.imageHeight];
        byte[] byArray2 = this.imageComponents[0];
        int n3 = 0;
        for (int i = 0; i < this.imageHeight; ++i) {
            int n4 = i * n;
            for (int j = 0; j < n2; ++j) {
                int n5 = byArray2[n4] & 0xFF;
                if (n5 < 0) {
                    n5 = 0;
                } else if (n5 > 255) {
                    n5 = 255;
                }
                if (j >= this.imageWidth) {
                    n5 = 0;
                }
                byArray[n3] = (byte)n5;
                ++n4;
                ++n3;
            }
        }
        return byArray;
    }

    byte[] convertYCbCrToRGB() {
        int n = this.imageWidth * this.imageHeight * this.nComponents;
        byte[] byArray = new byte[n];
        int n2 = 0;
        this.expandImageComponents();
        byte[] byArray2 = this.imageComponents[0];
        byte[] byArray3 = this.imageComponents[1];
        byte[] byArray4 = this.imageComponents[2];
        int n3 = this.frameComponents[this.componentIds[0]][3];
        for (int i = 0; i < this.imageHeight; ++i) {
            int n4 = i * n3;
            for (int j = 0; j < this.imageWidth; ++j) {
                int n5 = byArray2[n4] & 0xFF;
                int n6 = byArray3[n4] & 0xFF;
                int n7 = byArray4[n4] & 0xFF;
                int n8 = n5 + CrRTable[n7];
                int n9 = n5 + (CbGTable[n6] + CrGTable[n7] >> 16);
                int n10 = n5 + CbBTable[n6];
                if (n8 < 0) {
                    n8 = 0;
                } else if (n8 > 255) {
                    n8 = 255;
                }
                if (n9 < 0) {
                    n9 = 0;
                } else if (n9 > 255) {
                    n9 = 255;
                }
                if (n10 < 0) {
                    n10 = 0;
                } else if (n10 > 255) {
                    n10 = 255;
                }
                byArray[n2] = (byte)n10;
                byArray[n2 + 1] = (byte)n9;
                byArray[n2 + 2] = (byte)n8;
                n2 += 3;
                ++n4;
            }
        }
        return byArray;
    }

    void decodeACCoefficients(int[] nArray, int n) {
        int[] nArray2 = this.scanHeader.componentParameters[this.componentIds[n]];
        JPEGHuffmanTable jPEGHuffmanTable = this.acHuffmanTables[nArray2[1]];
        int n2 = 1;
        while (n2 < 64) {
            int n3 = this.decodeUsingTable(jPEGHuffmanTable);
            int n4 = n3 >> 4;
            int n5 = n3 & 0xF;
            if (n5 == 0) {
                if (n4 != 15) break;
                n2 += 16;
                continue;
            }
            int n6 = this.receive(n5);
            nArray[JPEGFileFormat.ZigZag8x8[n2 += n4]] = this.extendBy(n6, n5);
            ++n2;
        }
    }

    void decodeACFirstCoefficients(int[] nArray, int n, int n2, int n3, int n4) {
        if (this.eobrun > 0) {
            --this.eobrun;
            return;
        }
        int[] nArray2 = this.scanHeader.componentParameters[this.componentIds[n]];
        JPEGHuffmanTable jPEGHuffmanTable = this.acHuffmanTables[nArray2[1]];
        int n5 = n2;
        while (n5 <= n3) {
            int n6 = this.decodeUsingTable(jPEGHuffmanTable);
            int n7 = n6 >> 4;
            int n8 = n6 & 0xF;
            if (n8 == 0) {
                if (n7 != 15) {
                    this.eobrun = (1 << n7) + this.receive(n7) - 1;
                    break;
                }
                n5 += 16;
                continue;
            }
            int n9 = this.receive(n8);
            nArray[JPEGFileFormat.ZigZag8x8[n5 += n7]] = this.extendBy(n9, n8) << n4;
            ++n5;
        }
    }

    void decodeACRefineCoefficients(int[] nArray, int n, int n2, int n3, int n4) {
        int[] nArray2 = this.scanHeader.componentParameters[this.componentIds[n]];
        JPEGHuffmanTable jPEGHuffmanTable = this.acHuffmanTables[nArray2[1]];
        int n5 = n2;
        while (n5 <= n3) {
            int n6;
            int n7;
            int n8;
            if (this.eobrun > 0) {
                while (n5 <= n3) {
                    n8 = ZigZag8x8[n5];
                    if (nArray[n8] != 0) {
                        nArray[n8] = this.refineAC(nArray[n8], n4);
                    }
                    ++n5;
                }
                --this.eobrun;
                continue;
            }
            n8 = this.decodeUsingTable(jPEGHuffmanTable);
            int n9 = n8 >> 4;
            int n10 = n8 & 0xF;
            if (n10 == 0) {
                if (n9 == 15) {
                    n7 = 0;
                    while (n7 < 16 && n5 <= n3) {
                        n6 = ZigZag8x8[n5];
                        if (nArray[n6] != 0) {
                            nArray[n6] = this.refineAC(nArray[n6], n4);
                        } else {
                            ++n7;
                        }
                        ++n5;
                    }
                    continue;
                }
                this.eobrun = (1 << n9) + this.receive(n9);
                continue;
            }
            n7 = this.receive(n10);
            n6 = 0;
            int n11 = ZigZag8x8[n5];
            while ((n6 < n9 || nArray[n11] != 0) && n5 <= n3) {
                if (nArray[n11] != 0) {
                    nArray[n11] = this.refineAC(nArray[n11], n4);
                } else {
                    ++n6;
                }
                n11 = ZigZag8x8[++n5];
            }
            nArray[n11] = n7 != 0 ? 1 << n4 : -1 << n4;
            ++n5;
        }
    }

    int refineAC(int n, int n2) {
        int n3;
        if (n > 0) {
            int n4 = this.nextBit();
            if (n4 != 0) {
                n += 1 << n2;
            }
        } else if (n < 0 && (n3 = this.nextBit()) != 0) {
            n += -1 << n2;
        }
        return n;
    }

    void decodeDCCoefficient(int[] nArray, int n, boolean bl, int n2) {
        int[] nArray2 = this.scanHeader.componentParameters[this.componentIds[n]];
        JPEGHuffmanTable jPEGHuffmanTable = this.dcHuffmanTables[nArray2[0]];
        int n3 = 0;
        if (this.progressive && !bl) {
            int n4 = this.nextBit();
            n3 = nArray[0] + (n4 << n2);
        } else {
            n3 = this.precedingDCs[n];
            int n5 = this.decodeUsingTable(jPEGHuffmanTable);
            if (n5 != 0) {
                int n6 = this.receive(n5);
                int n7 = this.extendBy(n6, n5);
                this.precedingDCs[n] = n3 += n7;
            }
            if (this.progressive) {
                n3 <<= n2;
            }
        }
        nArray[0] = n3;
    }

    void dequantize(int[] nArray, int n) {
        int[] nArray2 = this.quantizationTables[this.frameComponents[this.componentIds[n]][0]];
        for (int i = 0; i < nArray.length; ++i) {
            int n2;
            int n3 = n2 = ZigZag8x8[i];
            nArray[n3] = nArray[n3] * nArray2[i];
        }
    }

    byte[] decodeImageComponents() {
        if (this.nComponents == 3) {
            return this.convertYCbCrToRGB();
        }
        if (this.nComponents == 4) {
            return this.convertCMYKToRGB();
        }
        return this.convertYToRGB();
    }

    void decodeMCUAtXAndY(int n, int n2, int n3, boolean bl, int n4, int n5, int n6) {
        for (int i = 0; i < n3; ++i) {
            int n7 = i;
            while (this.scanHeader.componentParameters[this.componentIds[n7]] == null) {
                ++n7;
            }
            int[] nArray = this.frameComponents[this.componentIds[n7]];
            int n8 = nArray[1];
            int n9 = nArray[2];
            if (n3 == 1) {
                n8 = 1;
                n9 = 1;
            }
            int n10 = nArray[3];
            for (int j = 0; j < n9; ++j) {
                for (int k = 0; k < n8; ++k) {
                    int n11;
                    if (this.progressive) {
                        n11 = (n2 * n9 + j) * n10 + n * n8 + k;
                        this.dataUnit = this.dataUnits[n7][n11];
                        if (this.dataUnit == null) {
                            this.dataUnit = new int[64];
                            this.dataUnits[n7][n11] = this.dataUnit;
                        }
                    } else {
                        for (n11 = 0; n11 < this.dataUnit.length; ++n11) {
                            this.dataUnit[n11] = 0;
                        }
                    }
                    if (!this.progressive || this.scanHeader.isDCProgressiveScan()) {
                        this.decodeDCCoefficient(this.dataUnit, n7, bl, n6);
                    }
                    if (!this.progressive) {
                        this.decodeACCoefficients(this.dataUnit, n7);
                    } else {
                        if (this.scanHeader.isACProgressiveScan()) {
                            if (bl) {
                                this.decodeACFirstCoefficients(this.dataUnit, n7, n4, n5, n6);
                            } else {
                                this.decodeACRefineCoefficients(this.dataUnit, n7, n4, n5, n6);
                            }
                        }
                        if (this.loader.hasListeners()) {
                            int[] nArray2 = this.dataUnit;
                            this.dataUnit = new int[64];
                            System.arraycopy(nArray2, 0, this.dataUnit, 0, 64);
                        }
                    }
                    if (this.progressive && (!this.progressive || !this.loader.hasListeners())) continue;
                    this.dequantize(this.dataUnit, n7);
                    this.inverseDCT(this.dataUnit);
                    this.storeData(this.dataUnit, n7, n, n2, n8, k, n9, j);
                }
            }
        }
    }

    void decodeScan() {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl;
        if (this.progressive && !this.scanHeader.verifyProgressiveScan()) {
            SWT.error(40);
        }
        int n5 = this.scanHeader.getNumberOfImageComponents();
        int n6 = this.interleavedMcuRows;
        int n7 = this.interleavedMcuCols;
        if (n5 == 1) {
            bl = false;
            while (this.scanHeader.componentParameters[this.componentIds[bl]] == null) {
                bl += 1;
            }
            int[] nArray = this.frameComponents[this.componentIds[bl]];
            n4 = nArray[1];
            n3 = nArray[2];
            n2 = 8 * this.maxH / n4;
            n = 8 * this.maxV / n3;
            n7 = (this.imageWidth + n2 - 1) / n2;
            n6 = (this.imageHeight + n - 1) / n;
        }
        bl = this.scanHeader.isFirstScan();
        int n8 = this.scanHeader.getStartOfSpectralSelection();
        n4 = this.scanHeader.getEndOfSpectralSelection();
        n3 = this.scanHeader.getApproxBitPositionLow();
        this.restartsToGo = this.restartInterval;
        this.nextRestartNumber = 0;
        for (n2 = 0; n2 < n6; ++n2) {
            for (n = 0; n < n7; ++n) {
                if (this.restartInterval != 0) {
                    if (this.restartsToGo == 0) {
                        this.processRestartInterval();
                    }
                    --this.restartsToGo;
                }
                this.decodeMCUAtXAndY(n, n2, n5, bl, n8, n4, n3);
            }
        }
    }

    int decodeUsingTable(JPEGHuffmanTable jPEGHuffmanTable) {
        int n = 0;
        int[] nArray = jPEGHuffmanTable.getDhMaxCodes();
        int[] nArray2 = jPEGHuffmanTable.getDhMinCodes();
        int[] nArray3 = jPEGHuffmanTable.getDhValPtrs();
        int[] nArray4 = jPEGHuffmanTable.getDhValues();
        int n2 = this.nextBit();
        while (n2 > nArray[n]) {
            n2 = n2 * 2 + this.nextBit();
            ++n;
        }
        int n3 = nArray3[n] + n2 - nArray2[n];
        return nArray4[n3];
    }

    void emit(int n, int n2) {
        if (n2 == 0) {
            SWT.error(40);
        }
        int[] nArray = new int[]{1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, Short.MAX_VALUE, 65535, 131125};
        int n3 = (n & nArray[n2 - 1]) << 24 - n2 - this.currentBitCount;
        byte[] byArray = new byte[]{(byte)(n3 & 0xFF), (byte)(n3 >> 8 & 0xFF), (byte)(n3 >> 16 & 0xFF), (byte)(n3 >> 24 & 0xFF)};
        int n4 = n2 - (8 - this.currentBitCount);
        if (n4 < 0) {
            n4 = -n4;
        }
        if (n4 >> 3 > 0) {
            this.currentByte += byArray[2];
            this.emitByte((byte)this.currentByte);
            this.emitByte(byArray[1]);
            this.currentByte = byArray[0];
            this.currentBitCount += n2 - 16;
        } else {
            this.currentBitCount += n2;
            if (this.currentBitCount >= 8) {
                this.currentByte += byArray[2];
                this.emitByte((byte)this.currentByte);
                this.currentByte = byArray[1];
                this.currentBitCount -= 8;
            } else {
                this.currentByte += byArray[2];
            }
        }
    }

    void emitByte(byte by) {
        if (this.bufferCurrentPosition >= 512) {
            this.resetOutputBuffer();
        }
        this.dataBuffer[this.bufferCurrentPosition] = by;
        ++this.bufferCurrentPosition;
        if (by == -1) {
            this.emitByte((byte)0);
        }
    }

    void encodeACCoefficients(int[] nArray, int n) {
        int[] nArray2 = this.scanHeader.componentParameters[n];
        JPEGHuffmanTable jPEGHuffmanTable = this.acHuffmanTables[nArray2[1]];
        int[] nArray3 = jPEGHuffmanTable.ehCodes;
        byte[] byArray = jPEGHuffmanTable.ehCodeLengths;
        int n2 = 0;
        int n3 = 1;
        while (n3 < 64) {
            int n4;
            int n5;
            int n6;
            if ((n6 = nArray[ZigZag8x8[++n3 - 1]]) == 0) {
                if (n3 == 64) {
                    this.emit(nArray3[0], byArray[0] & 0xFF);
                    continue;
                }
                ++n2;
                continue;
            }
            while (n2 > 15) {
                this.emit(nArray3[240], byArray[240] & 0xFF);
                n2 -= 16;
            }
            if (n6 < 0) {
                n5 = n6;
                if (n5 < 0) {
                    n5 = -n5;
                }
                n4 = NBitsTable[n5];
                int n7 = n2 * 16 + n4;
                this.emit(nArray3[n7], byArray[n7] & 0xFF);
                this.emit(0xFFFFFF - n5, n4);
            } else {
                n5 = NBitsTable[n6];
                n4 = n2 * 16 + n5;
                this.emit(nArray3[n4], byArray[n4] & 0xFF);
                this.emit(n6, n5);
            }
            n2 = 0;
        }
    }

    void encodeDCCoefficients(int[] nArray, int n) {
        int[] nArray2 = this.scanHeader.componentParameters[n];
        JPEGHuffmanTable jPEGHuffmanTable = this.dcHuffmanTables[nArray2[0]];
        int n2 = this.precedingDCs[n];
        int n3 = nArray[0];
        int n4 = n3 - n2;
        this.precedingDCs[n] = n3;
        if (n4 < 0) {
            int n5 = 0 - n4;
            int n6 = NBitsTable[n5];
            this.emit(jPEGHuffmanTable.ehCodes[n6], jPEGHuffmanTable.ehCodeLengths[n6]);
            this.emit(0xFFFFFF - n5, n6);
        } else {
            int n7 = NBitsTable[n4];
            this.emit(jPEGHuffmanTable.ehCodes[n7], jPEGHuffmanTable.ehCodeLengths[n7]);
            if (n7 != 0) {
                this.emit(n4, n7);
            }
        }
    }

    void encodeMCUAtXAndY(int n, int n2) {
        int n3 = this.scanHeader.getNumberOfImageComponents();
        this.dataUnit = new int[64];
        for (int i = 0; i < n3; ++i) {
            int[] nArray = this.frameComponents[this.componentIds[i]];
            int n4 = nArray[1];
            int n5 = nArray[2];
            for (int j = 0; j < n5; ++j) {
                for (int k = 0; k < n4; ++k) {
                    this.extractData(this.dataUnit, i, n, n2, k, j);
                    this.forwardDCT(this.dataUnit);
                    this.quantizeData(this.dataUnit, i);
                    this.encodeDCCoefficients(this.dataUnit, i);
                    this.encodeACCoefficients(this.dataUnit, i);
                }
            }
        }
    }

    void encodeScan() {
        for (int i = 0; i < this.interleavedMcuRows; ++i) {
            for (int j = 0; j < this.interleavedMcuCols; ++j) {
                this.encodeMCUAtXAndY(j, i);
            }
        }
        if (this.currentBitCount != 0) {
            this.emitByte((byte)this.currentByte);
        }
        this.resetOutputBuffer();
    }

    void expandImageComponents() {
        for (int i = 0; i < this.nComponents; ++i) {
            int[] nArray = this.frameComponents[this.componentIds[i]];
            int n = nArray[1];
            int n2 = this.maxH / n;
            int n3 = nArray[2];
            int n4 = this.maxV / n3;
            if (n2 * n4 <= 1) continue;
            byte[] byArray = this.imageComponents[i];
            int n5 = nArray[3];
            int n6 = nArray[4];
            int n7 = n5 * n2;
            int n8 = n6 * n4;
            ImageData imageData = new ImageData(n5, n6, 8, new PaletteData(RGB16), 4, byArray);
            ImageData imageData2 = imageData.scaledTo(n7, n8);
            this.imageComponents[i] = imageData2.data;
        }
    }

    int extendBy(int n, int n2) {
        if (n < ExtendTest[n2]) {
            return n + ExtendOffset[n2];
        }
        return n;
    }

    void extractData(int[] nArray, int n, int n2, int n3, int n4, int n5) {
        byte[] byArray = this.imageComponents[n];
        int[] nArray2 = this.frameComponents[this.componentIds[n]];
        int n6 = nArray2[1];
        int n7 = nArray2[2];
        int n8 = nArray2[3];
        int n9 = (n3 * n7 + n5) * n8 * 8 + (n2 * n6 + n4) * 8;
        int n10 = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                nArray[n10] = (byArray[n9 + j] & 0xFF) - 128;
                ++n10;
            }
            n9 += n8;
        }
    }

    void forwardDCT(int[] nArray) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15;
        int n16;
        int n17;
        int n18;
        int n19;
        int n20;
        int n21;
        for (n21 = 0; n21 < 8; ++n21) {
            n20 = n21 * 8;
            n19 = nArray[n20] + nArray[n20 + 7];
            n18 = nArray[n20] - nArray[n20 + 7];
            n17 = nArray[n20 + 1] + nArray[n20 + 6];
            n16 = nArray[n20 + 1] - nArray[n20 + 6];
            n15 = nArray[n20 + 2] + nArray[n20 + 5];
            n14 = nArray[n20 + 2] - nArray[n20 + 5];
            n13 = nArray[n20 + 3] + nArray[n20 + 4];
            n12 = nArray[n20 + 3] - nArray[n20 + 4];
            n11 = n19 + n13;
            n10 = n19 - n13;
            n9 = n17 + n15;
            n8 = n17 - n15;
            nArray[n20] = (n11 + n9) * 4;
            nArray[n20 + 4] = (n11 - n9) * 4;
            n7 = (n8 + n10) * 4433;
            n6 = n7 + n10 * 6270 + 1024;
            nArray[n20 + 2] = n6 >> 11;
            if (n6 < 0 && (n6 & 0x7FF) != 0) {
                int n22 = n5 = n20 + 2;
                nArray[n22] = nArray[n22] - 1;
            }
            n6 = n7 + n8 * -15137 + 1024;
            nArray[n20 + 6] = n6 >> 11;
            if (n6 < 0 && (n6 & 0x7FF) != 0) {
                int n23 = n5 = n20 + 6;
                nArray[n23] = nArray[n23] - 1;
            }
            n7 = n12 + n18;
            n5 = n14 + n16;
            n4 = n12 + n16;
            n3 = n14 + n18;
            n2 = (n4 + n3) * 9633;
            n12 *= 2446;
            n14 *= 16819;
            n16 *= 25172;
            n18 *= 12299;
            n5 *= -20995;
            n4 *= -16069;
            n3 *= -3196;
            n3 += n2;
            n6 = n12 + (n7 *= -7373) + (n4 += n2) + 1024;
            nArray[n20 + 7] = n6 >> 11;
            if (n6 < 0 && (n6 & 0x7FF) != 0) {
                int n24 = n = n20 + 7;
                nArray[n24] = nArray[n24] - 1;
            }
            n6 = n14 + n5 + n3 + 1024;
            nArray[n20 + 5] = n6 >> 11;
            if (n6 < 0 && (n6 & 0x7FF) != 0) {
                int n25 = n = n20 + 5;
                nArray[n25] = nArray[n25] - 1;
            }
            n6 = n16 + n5 + n4 + 1024;
            nArray[n20 + 3] = n6 >> 11;
            if (n6 < 0 && (n6 & 0x7FF) != 0) {
                int n26 = n = n20 + 3;
                nArray[n26] = nArray[n26] - 1;
            }
            n6 = n18 + n7 + n3 + 1024;
            nArray[n20 + 1] = n6 >> 11;
            if (n6 >= 0 || (n6 & 0x7FF) == 0) continue;
            int n27 = n = n20 + 1;
            nArray[n27] = nArray[n27] - 1;
        }
        for (n21 = 0; n21 < 8; ++n21) {
            int n28;
            int n29;
            int n30;
            n20 = n21;
            n19 = n21 + 8;
            n18 = n21 + 16;
            n17 = n21 + 24;
            n16 = n21 + 32;
            n15 = n21 + 40;
            n14 = n21 + 48;
            n13 = n21 + 56;
            n12 = nArray[n20] + nArray[n13];
            n11 = nArray[n20] - nArray[n13];
            n10 = nArray[n19] + nArray[n14];
            n9 = nArray[n19] - nArray[n14];
            n8 = nArray[n18] + nArray[n15];
            n7 = nArray[n18] - nArray[n15];
            n6 = nArray[n17] + nArray[n16];
            n5 = nArray[n17] - nArray[n16];
            n4 = n12 + n6;
            n3 = n12 - n6;
            n2 = n10 + n8;
            n = n10 - n8;
            int n31 = n4 + n2 + 16;
            nArray[n20] = n31 >> 5;
            if (n31 < 0 && (n31 & 0x1F) != 0) {
                int n32 = n30 = n20;
                nArray[n32] = nArray[n32] - 1;
            }
            n31 = n4 - n2 + 16;
            nArray[n16] = n31 >> 5;
            if (n31 < 0 && (n31 & 0x1F) != 0) {
                int n33 = n30 = n16;
                nArray[n33] = nArray[n33] - 1;
            }
            n30 = (n + n3) * 4433;
            n31 = n30 + n3 * 6270 + 131072;
            nArray[n18] = n31 >> 18;
            if (n31 < 0 && (n31 & 0x3FFFF) != 0) {
                int n34 = n29 = n18;
                nArray[n34] = nArray[n34] - 1;
            }
            n31 = n30 + n * -15137 + 131072;
            nArray[n14] = n31 >> 18;
            if (n31 < 0 && (n31 & 0x3FFFF) != 0) {
                int n35 = n29 = n14;
                nArray[n35] = nArray[n35] - 1;
            }
            n30 = n5 + n11;
            n29 = n7 + n9;
            int n36 = n5 + n9;
            int n37 = n7 + n11;
            int n38 = (n36 + n37) * 9633;
            n5 *= 2446;
            n7 *= 16819;
            n9 *= 25172;
            n11 *= 12299;
            n29 *= -20995;
            n36 *= -16069;
            n37 *= -3196;
            n37 += n38;
            n31 = n5 + (n30 *= -7373) + (n36 += n38) + 131072;
            nArray[n13] = n31 >> 18;
            if (n31 < 0 && (n31 & 0x3FFFF) != 0) {
                int n39 = n28 = n13;
                nArray[n39] = nArray[n39] - 1;
            }
            n31 = n7 + n29 + n37 + 131072;
            nArray[n15] = n31 >> 18;
            if (n31 < 0 && (n31 & 0x3FFFF) != 0) {
                int n40 = n28 = n15;
                nArray[n40] = nArray[n40] - 1;
            }
            n31 = n9 + n29 + n36 + 131072;
            nArray[n17] = n31 >> 18;
            if (n31 < 0 && (n31 & 0x3FFFF) != 0) {
                int n41 = n28 = n17;
                nArray[n41] = nArray[n41] - 1;
            }
            n31 = n11 + n30 + n37 + 131072;
            nArray[n19] = n31 >> 18;
            if (n31 >= 0 || (n31 & 0x3FFFF) == 0) continue;
            int n42 = n28 = n19;
            nArray[n42] = nArray[n42] - 1;
        }
    }

    void getAPP0() {
        JPEGAppn jPEGAppn = new JPEGAppn(this.inputStream);
        if (!jPEGAppn.verify()) {
            SWT.error(40);
        }
    }

    void getCOM() {
        new JPEGComment(this.inputStream);
    }

    void getDAC() {
        new JPEGArithmeticConditioningTable(this.inputStream);
    }

    void getDHT() {
        JPEGHuffmanTable jPEGHuffmanTable = new JPEGHuffmanTable(this.inputStream);
        if (!jPEGHuffmanTable.verify()) {
            SWT.error(40);
        }
        if (this.acHuffmanTables == null) {
            this.acHuffmanTables = new JPEGHuffmanTable[4];
        }
        if (this.dcHuffmanTables == null) {
            this.dcHuffmanTables = new JPEGHuffmanTable[4];
        }
        for (JPEGHuffmanTable jPEGHuffmanTable2 : jPEGHuffmanTable.getAllTables()) {
            if (jPEGHuffmanTable2.getTableClass() == 0) {
                this.dcHuffmanTables[jPEGHuffmanTable2.getTableIdentifier()] = jPEGHuffmanTable2;
                continue;
            }
            this.acHuffmanTables[jPEGHuffmanTable2.getTableIdentifier()] = jPEGHuffmanTable2;
        }
    }

    void getDNL() {
        new JPEGRestartInterval(this.inputStream);
    }

    void getDQT() {
        JPEGQuantizationTable jPEGQuantizationTable = new JPEGQuantizationTable(this.inputStream);
        Object object = this.quantizationTables;
        if (object == null) {
            object = new int[4][];
        }
        int[] nArray = jPEGQuantizationTable.getQuantizationTablesKeys();
        int[][] nArray2 = jPEGQuantizationTable.getQuantizationTablesValues();
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            object[n] = nArray2[i];
        }
        this.quantizationTables = object;
    }

    void getDRI() {
        JPEGRestartInterval jPEGRestartInterval = new JPEGRestartInterval(this.inputStream);
        if (!jPEGRestartInterval.verify()) {
            SWT.error(40);
        }
        this.restartInterval = jPEGRestartInterval.getRestartInterval();
    }

    /*
     * Exception decompiling
     */
    void inverseDCT(int[] var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl3 : ILOAD_2 - null : trying to set 2 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            JPEGStartOfImage jPEGStartOfImage = new JPEGStartOfImage(lEDataInputStream);
            lEDataInputStream.unread(jPEGStartOfImage.reference);
            return jPEGStartOfImage.verify();
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    ImageData[] loadFromByteStream() {
        int n;
        int n2;
        if (System.getProperty("org.eclipse.swt.internal.image.JPEGFileFormat_3.2") == null) {
            return JPEGDecoder.loadFromByteStream(this.inputStream, this.loader);
        }
        JPEGStartOfImage jPEGStartOfImage = new JPEGStartOfImage(this.inputStream);
        if (!jPEGStartOfImage.verify()) {
            SWT.error(40);
        }
        this.restartInterval = 0;
        this.processTables();
        this.frameHeader = new JPEGFrameHeader(this.inputStream);
        if (!this.frameHeader.verify()) {
            SWT.error(40);
        }
        this.imageWidth = this.frameHeader.getSamplesPerLine();
        this.imageHeight = this.frameHeader.getNumberOfLines();
        this.maxH = this.frameHeader.getMaxHFactor();
        this.maxV = this.frameHeader.getMaxVFactor();
        int n3 = this.maxH * 8;
        int n4 = this.maxV * 8;
        this.interleavedMcuCols = (this.imageWidth + n3 - 1) / n3;
        this.interleavedMcuRows = (this.imageHeight + n4 - 1) / n4;
        this.progressive = this.frameHeader.isProgressive();
        this.samplePrecision = this.frameHeader.getSamplePrecision();
        this.nComponents = this.frameHeader.getNumberOfImageComponents();
        this.frameComponents = this.frameHeader.componentParameters;
        this.componentIds = this.frameHeader.componentIdentifiers;
        this.imageComponents = new byte[this.nComponents][];
        if (this.progressive) {
            this.dataUnits = new int[this.nComponents][][];
        } else {
            this.dataUnit = new int[64];
        }
        for (n2 = 0; n2 < this.nComponents; ++n2) {
            int[] nArray = this.frameComponents[this.componentIds[n2]];
            n = nArray[3] * nArray[4];
            this.imageComponents[n2] = new byte[n];
            if (!this.progressive) continue;
            this.dataUnits[n2] = new int[n][];
        }
        this.processTables();
        this.scanHeader = new JPEGScanHeader(this.inputStream);
        if (!this.scanHeader.verify()) {
            SWT.error(40);
        }
        n2 = 0;
        boolean bl = false;
        while (!bl) {
            Object object;
            this.resetInputBuffer();
            this.precedingDCs = new int[4];
            this.decodeScan();
            if (this.progressive && this.loader.hasListeners()) {
                ImageData imageData = this.createImageData();
                this.loader.notifyListeners(new ImageLoaderEvent(this.loader, imageData, n2, false));
                ++n2;
            }
            if ((n = 512 - this.bufferCurrentPosition - 1) > 0) {
                object = new byte[n];
                System.arraycopy(this.dataBuffer, this.bufferCurrentPosition + 1, object, 0, n);
                try {
                    this.inputStream.unread((byte[])object);
                }
                catch (IOException iOException) {
                    SWT.error(39, iOException);
                }
            }
            if ((object = (Object)this.processTables()) == null || ((JPEGSegment)object).getSegmentMarker() == 65497) {
                bl = true;
                continue;
            }
            this.scanHeader = new JPEGScanHeader(this.inputStream);
            if (this.scanHeader.verify()) continue;
            SWT.error(40);
        }
        if (this.progressive) {
            for (n = 0; n < this.interleavedMcuRows; ++n) {
                for (int i = 0; i < this.interleavedMcuCols; ++i) {
                    for (int j = 0; j < this.nComponents; ++j) {
                        int[] nArray = this.frameComponents[this.componentIds[j]];
                        int n5 = nArray[1];
                        int n6 = nArray[2];
                        int n7 = nArray[3];
                        for (int k = 0; k < n6; ++k) {
                            for (int i2 = 0; i2 < n5; ++i2) {
                                int n8 = (n * n6 + k) * n7 + i * n5 + i2;
                                this.dataUnit = this.dataUnits[j][n8];
                                this.dequantize(this.dataUnit, j);
                                this.inverseDCT(this.dataUnit);
                                this.storeData(this.dataUnit, j, i, n, n5, i2, n6, k);
                            }
                        }
                    }
                }
            }
            this.dataUnits = null;
        }
        ImageData imageData = this.createImageData();
        if (this.progressive && this.loader.hasListeners()) {
            this.loader.notifyListeners(new ImageLoaderEvent(this.loader, imageData, n2, true));
        }
        return new ImageData[]{imageData};
    }

    ImageData createImageData() {
        return ImageData.internal_new(this.imageWidth, this.imageHeight, this.nComponents * this.samplePrecision, this.setUpPalette(), this.nComponents == 1 ? 4 : 1, this.decodeImageComponents(), 0, null, null, -1, -1, 4, 0, 0, 0, 0);
    }

    int nextBit() {
        byte by;
        if (this.currentBitCount != 0) {
            --this.currentBitCount;
            this.currentByte *= 2;
            if (this.currentByte > 255) {
                this.currentByte -= 256;
                return 1;
            }
            return 0;
        }
        ++this.bufferCurrentPosition;
        if (this.bufferCurrentPosition >= 512) {
            this.resetInputBuffer();
            this.bufferCurrentPosition = 0;
        }
        this.currentByte = this.dataBuffer[this.bufferCurrentPosition] & 0xFF;
        this.currentBitCount = 8;
        if (this.bufferCurrentPosition == 511) {
            this.resetInputBuffer();
            this.currentBitCount = 8;
            by = this.dataBuffer[0];
        } else {
            by = this.dataBuffer[this.bufferCurrentPosition + 1];
        }
        if (this.currentByte == 255) {
            if (by == 0) {
                ++this.bufferCurrentPosition;
                --this.currentBitCount;
                this.currentByte *= 2;
                if (this.currentByte > 255) {
                    this.currentByte -= 256;
                    return 1;
                }
                return 0;
            }
            if ((by & 0xFF) + 65280 == 65500) {
                this.getDNL();
                return 0;
            }
            SWT.error(40);
            return 0;
        }
        --this.currentBitCount;
        this.currentByte *= 2;
        if (this.currentByte > 255) {
            this.currentByte -= 256;
            return 1;
        }
        return 0;
    }

    void processRestartInterval() {
        do {
            ++this.bufferCurrentPosition;
            if (this.bufferCurrentPosition > 511) {
                this.resetInputBuffer();
                this.bufferCurrentPosition = 0;
            }
            this.currentByte = this.dataBuffer[this.bufferCurrentPosition] & 0xFF;
        } while (this.currentByte != 255);
        while (this.currentByte == 255) {
            ++this.bufferCurrentPosition;
            if (this.bufferCurrentPosition > 511) {
                this.resetInputBuffer();
                this.bufferCurrentPosition = 0;
            }
            this.currentByte = this.dataBuffer[this.bufferCurrentPosition] & 0xFF;
        }
        if (this.currentByte != (65488 + this.nextRestartNumber & 0xFF)) {
            SWT.error(40);
        }
        ++this.bufferCurrentPosition;
        if (this.bufferCurrentPosition > 511) {
            this.resetInputBuffer();
            this.bufferCurrentPosition = 0;
        }
        this.currentByte = this.dataBuffer[this.bufferCurrentPosition] & 0xFF;
        this.currentBitCount = 8;
        this.restartsToGo = this.restartInterval;
        this.nextRestartNumber = this.nextRestartNumber + 1 & 7;
        this.precedingDCs = new int[4];
        this.eobrun = 0;
    }

    JPEGSegment processTables() {
        JPEGSegment jPEGSegment = null;
        block10: while (true) {
            if ((jPEGSegment = JPEGFileFormat.seekUnspecifiedMarker(this.inputStream)) == null) {
                return null;
            }
            JPEGFrameHeader jPEGFrameHeader = new JPEGFrameHeader(jPEGSegment.reference);
            if (jPEGFrameHeader.verify()) {
                return jPEGSegment;
            }
            int n = jPEGSegment.getSegmentMarker();
            switch (n) {
                case 65496: {
                    SWT.error(40);
                }
                case 65497: 
                case 65498: {
                    break block10;
                }
                case 65499: {
                    this.getDQT();
                    continue block10;
                }
                case 65476: {
                    this.getDHT();
                    continue block10;
                }
                case 65484: {
                    this.getDAC();
                    continue block10;
                }
                case 65501: {
                    this.getDRI();
                    continue block10;
                }
                case 65504: {
                    this.getAPP0();
                    continue block10;
                }
                case 65534: {
                    this.getCOM();
                    continue block10;
                }
                default: {
                    JPEGFileFormat.skipSegmentFrom(this.inputStream);
                    continue block10;
                }
            }
            break;
        }
        return jPEGSegment;
    }

    void quantizeData(int[] nArray, int n) {
        int[] nArray2 = this.quantizationTables[this.frameComponents[this.componentIds[n]][0]];
        for (int i = 0; i < nArray.length; ++i) {
            int n2 = ZigZag8x8[i];
            int n3 = nArray[n2];
            int n4 = n3 < 0 ? 0 - n3 : n3;
            int n5 = nArray2[i];
            int n6 = n5 >> 1;
            if ((n4 += n6) < n5) {
                nArray[n2] = 0;
                continue;
            }
            n4 /= n5;
            nArray[n2] = n3 >= 0 ? n4 : 0 - n4;
        }
    }

    int receive(int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 = n2 * 2 + this.nextBit();
        }
        return n2;
    }

    void resetInputBuffer() {
        if (this.dataBuffer == null) {
            this.dataBuffer = new byte[512];
        }
        try {
            this.inputStream.read(this.dataBuffer);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        this.currentBitCount = 0;
        this.bufferCurrentPosition = -1;
    }

    void resetOutputBuffer() {
        if (this.dataBuffer == null) {
            this.dataBuffer = new byte[512];
        } else {
            try {
                this.outputStream.write(this.dataBuffer, 0, this.bufferCurrentPosition);
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
        }
        this.bufferCurrentPosition = 0;
    }

    static JPEGSegment seekUnspecifiedMarker(LEDataInputStream lEDataInputStream) {
        byte[] byArray = new byte[2];
        try {
            while (lEDataInputStream.read(byArray, 0, 1) == 1) {
                if (byArray[0] != -1) continue;
                if (lEDataInputStream.read(byArray, 1, 1) != 1) {
                    return null;
                }
                if (byArray[1] == -1 || byArray[1] == 0) continue;
                lEDataInputStream.unread(byArray);
                return new JPEGSegment(byArray);
            }
            return null;
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
            return null;
        }
    }

    PaletteData setUpPalette() {
        if (this.nComponents == 1) {
            RGB[] rGBArray = new RGB[256];
            for (int i = 0; i < 256; ++i) {
                rGBArray[i] = new RGB(i, i, i);
            }
            return new PaletteData(rGBArray);
        }
        return new PaletteData(255, 65280, 0xFF0000);
    }

    static void skipSegmentFrom(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[4];
            JPEGSegment jPEGSegment = new JPEGSegment(byArray);
            if (lEDataInputStream.read(byArray) != byArray.length) {
                SWT.error(40);
            }
            if (byArray[0] != -1 || byArray[1] == 0 || byArray[1] == -1) {
                SWT.error(40);
            }
            int n = jPEGSegment.getSegmentLength() - 2;
            lEDataInputStream.skip(n);
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
    }

    void storeData(int[] nArray, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        byte[] byArray = this.imageComponents[n];
        int[] nArray2 = this.frameComponents[this.componentIds[n]];
        int n8 = nArray2[3];
        int n9 = (n3 * n6 + n7) * n8 * 8 + (n2 * n4 + n5) * 8;
        int n10 = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                int n11 = nArray[n10] + 128;
                if (n11 < 0) {
                    n11 = 0;
                } else if (n11 > 255) {
                    n11 = 255;
                }
                byArray[n9 + j] = (byte)n11;
                ++n10;
            }
            n9 += n8;
        }
    }

    @Override
    void unloadIntoByteStream(ImageLoader imageLoader) {
        int n;
        int n2;
        int[][] nArrayArray;
        int[][] nArrayArray2;
        int n3;
        JPEGAppn jPEGAppn;
        ImageData imageData = imageLoader.data[0];
        if (!new JPEGStartOfImage().writeToStream(this.outputStream)) {
            SWT.error(39);
        }
        if (!(jPEGAppn = new JPEGAppn(new byte[]{-1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0})).writeToStream(this.outputStream)) {
            SWT.error(39);
        }
        this.quantizationTables = new int[4][];
        JPEGQuantizationTable jPEGQuantizationTable = JPEGQuantizationTable.defaultChrominanceTable();
        int n4 = imageLoader.compression >= 1 && imageLoader.compression <= 100 ? imageLoader.compression : 75;
        jPEGQuantizationTable.scaleBy(n4);
        int[] nArray = jPEGQuantizationTable.getQuantizationTablesKeys();
        int[][] nArray2 = jPEGQuantizationTable.getQuantizationTablesValues();
        for (int i = 0; i < nArray.length; ++i) {
            this.quantizationTables[nArray[i]] = nArray2[i];
        }
        JPEGQuantizationTable jPEGQuantizationTable2 = JPEGQuantizationTable.defaultLuminanceTable();
        jPEGQuantizationTable2.scaleBy(n4);
        nArray = jPEGQuantizationTable2.getQuantizationTablesKeys();
        nArray2 = jPEGQuantizationTable2.getQuantizationTablesValues();
        for (n3 = 0; n3 < nArray.length; ++n3) {
            this.quantizationTables[nArray[n3]] = nArray2[n3];
        }
        if (!jPEGQuantizationTable2.writeToStream(this.outputStream)) {
            SWT.error(39);
        }
        if (!jPEGQuantizationTable.writeToStream(this.outputStream)) {
            SWT.error(39);
        }
        if (imageData.depth == 1) {
            n3 = 11;
            nArrayArray2 = new int[][]{{1, 1, 1, 0, 0}};
            nArrayArray = new int[][]{{0, 0}};
            n2 = 8;
            this.nComponents = 1;
            n = 1;
        } else {
            n3 = 17;
            nArrayArray2 = new int[][]{{0, 2, 2, 0, 0}, {1, 1, 1, 0, 0}, {1, 1, 1, 0, 0}};
            nArrayArray = new int[][]{{0, 0}, {1, 1}, {1, 1}};
            n2 = 12;
            this.nComponents = 3;
            n = 8;
        }
        this.imageWidth = imageData.width;
        this.imageHeight = imageData.height;
        this.frameHeader = new JPEGFrameHeader(new byte[19]);
        this.frameHeader.setSegmentMarker(65472);
        this.frameHeader.setSegmentLength(n3);
        this.frameHeader.setSamplePrecision(n);
        this.frameHeader.setSamplesPerLine(this.imageWidth);
        this.frameHeader.setNumberOfLines(this.imageHeight);
        this.frameHeader.setNumberOfImageComponents(this.nComponents);
        this.frameHeader.componentParameters = nArrayArray2;
        this.frameHeader.componentIdentifiers = new int[]{0, 1, 2};
        this.frameHeader.initializeContents();
        if (!this.frameHeader.writeToStream(this.outputStream)) {
            SWT.error(39);
        }
        this.frameComponents = nArrayArray2;
        this.componentIds = this.frameHeader.componentIdentifiers;
        this.maxH = this.frameHeader.getMaxHFactor();
        this.maxV = this.frameHeader.getMaxVFactor();
        int n5 = this.maxH * 8;
        int n6 = this.maxV * 8;
        this.interleavedMcuCols = (this.imageWidth + n5 - 1) / n5;
        this.interleavedMcuRows = (this.imageHeight + n6 - 1) / n6;
        this.acHuffmanTables = new JPEGHuffmanTable[4];
        this.dcHuffmanTables = new JPEGHuffmanTable[4];
        JPEGHuffmanTable[] jPEGHuffmanTableArray = new JPEGHuffmanTable[]{JPEGHuffmanTable.getDefaultDCLuminanceTable(), JPEGHuffmanTable.getDefaultDCChrominanceTable(), JPEGHuffmanTable.getDefaultACLuminanceTable(), JPEGHuffmanTable.getDefaultACChrominanceTable()};
        JPEGHuffmanTable[] jPEGHuffmanTableArray2 = jPEGHuffmanTableArray;
        for (JPEGHuffmanTable jPEGHuffmanTable : jPEGHuffmanTableArray) {
            if (!jPEGHuffmanTable.writeToStream(this.outputStream)) {
                SWT.error(39);
            }
            for (JPEGHuffmanTable jPEGHuffmanTable2 : jPEGHuffmanTable.getAllTables()) {
                if (jPEGHuffmanTable2.getTableClass() == 0) {
                    this.dcHuffmanTables[jPEGHuffmanTable2.getTableIdentifier()] = jPEGHuffmanTable2;
                    continue;
                }
                this.acHuffmanTables[jPEGHuffmanTable2.getTableIdentifier()] = jPEGHuffmanTable2;
            }
        }
        this.precedingDCs = new int[4];
        this.scanHeader = new JPEGScanHeader(new byte[14]);
        this.scanHeader.setSegmentMarker(65498);
        this.scanHeader.setSegmentLength(n2);
        this.scanHeader.setNumberOfImageComponents(this.nComponents);
        this.scanHeader.setStartOfSpectralSelection(0);
        this.scanHeader.setEndOfSpectralSelection(63);
        this.scanHeader.componentParameters = nArrayArray;
        this.scanHeader.initializeContents();
        if (!this.scanHeader.writeToStream(this.outputStream)) {
            SWT.error(39);
        }
        this.convertImageToYCbCr(imageData);
        this.resetOutputBuffer();
        this.currentByte = 0;
        this.currentBitCount = 0;
        this.encodeScan();
        if (!new JPEGEndOfImage().writeToStream(this.outputStream)) {
            SWT.error(39);
        }
    }

    static {
        int n;
        int n2;
        RGB16 = new RGB[]{new RGB(0, 0, 0), new RGB(128, 0, 0), new RGB(0, 128, 0), new RGB(128, 128, 0), new RGB(0, 0, 128), new RGB(128, 0, 128), new RGB(0, 128, 128), new RGB(192, 192, 192), new RGB(128, 128, 128), new RGB(255, 0, 0), new RGB(0, 255, 0), new RGB(255, 255, 0), new RGB(0, 0, 255), new RGB(255, 0, 255), new RGB(0, 255, 255), new RGB(255, 255, 255)};
        ExtendTest = new int[]{0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144};
        ExtendOffset = new int[]{0, -1, -3, -7, -15, -31, -63, -127, -255, -511, -1023, -2047, -4095, -8191, -16383, -32767, -65535, -131071, -262143};
        ZigZag8x8 = new int[]{0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40, 48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30, 37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63};
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        int[] nArray3 = new int[256];
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        int[] nArray8 = new int[256];
        for (int i = 0; i < 256; ++i) {
            nArray[i] = i * 19595;
            nArray2[i] = i * 38470;
            nArray3[i] = i * 7471 + 32768;
            nArray4[i] = i * -11059;
            nArray5[i] = i * -21709;
            nArray6[i] = i * 32768 + 0x800000;
            nArray7[i] = i * -27439;
            nArray8[i] = i * -5329;
        }
        RYTable = nArray;
        GYTable = nArray2;
        BYTable = nArray3;
        RCbTable = nArray4;
        GCbTable = nArray5;
        BCbTable = nArray6;
        RCrTable = nArray6;
        GCrTable = nArray7;
        BCrTable = nArray8;
        int[] nArray9 = new int[256];
        int[] nArray10 = new int[256];
        int[] nArray11 = new int[256];
        int[] nArray12 = new int[256];
        for (n2 = 0; n2 < 256; ++n2) {
            n = 2 * n2 - 255;
            nArray9[n2] = 45941 * n + 32768 >> 16;
            nArray10[n2] = 58065 * n + 32768 >> 16;
            nArray11[n2] = -23401 * n;
            nArray12[n2] = -11277 * n + 32768;
        }
        CrRTable = nArray9;
        CbBTable = nArray10;
        CrGTable = nArray11;
        CbGTable = nArray12;
        n2 = 1;
        n = 2;
        int[] nArray13 = new int[2048];
        nArray13[0] = 0;
        for (int i = 1; i < nArray13.length; ++i) {
            if (i >= n) {
                n *= 2;
            }
            nArray13[i] = ++n2;
        }
        NBitsTable = nArray13;
    }
}

