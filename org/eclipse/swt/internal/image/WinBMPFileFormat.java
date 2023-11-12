/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.FileFormat;
import org.eclipse.swt.internal.image.LEDataInputStream;

public final class WinBMPFileFormat
extends FileFormat {
    static final int BMPFileHeaderSize = 14;
    static final int BMPHeaderFixedSize = 40;
    static final int BI_RGB = 0;
    static final int BI_RLE8 = 1;
    static final int BI_RLE4 = 2;
    static final int BI_BITFIELDS = 3;
    int importantColors;
    Point pelsPerMeter = new Point(0, 0);

    int compress(int n, byte[] byArray, int n2, int n3, byte[] byArray2, boolean bl) {
        if (n == 1) {
            return this.compressRLE8Data(byArray, n2, n3, byArray2, bl);
        }
        if (n == 2) {
            return this.compressRLE4Data(byArray, n2, n3, byArray2, bl);
        }
        SWT.error(40);
        return 0;
    }

    int compressRLE4Data(byte[] byArray, int n, int n2, byte[] byArray2, boolean bl) {
        int n3 = n;
        int n4 = n + n2;
        int n5 = 0;
        int n6 = 0;
        while (n3 < n4) {
            int n7;
            int n8;
            int n9 = n4 - n3 - 1;
            if (n9 > 127) {
                n9 = 127;
            }
            for (n8 = 0; n8 < n9 && byArray[n3 + n8] != byArray[n3 + n8 + 1]; ++n8) {
            }
            if (n8 < 127 && n8 == n9) {
                ++n8;
            }
            switch (n8) {
                case 0: {
                    break;
                }
                case 1: {
                    byArray2[n5] = 2;
                    byArray2[++n5] = byArray[n3];
                    ++n5;
                    ++n3;
                    n6 += 2;
                    break;
                }
                default: {
                    byArray2[n5] = 0;
                    byArray2[++n5] = (byte)(n8 + n8);
                    ++n5;
                    for (n7 = n8; n7 > 0; --n7) {
                        byArray2[n5] = byArray[n3];
                        ++n5;
                        ++n3;
                    }
                    n6 += 2 + n8;
                    if ((n8 & 1) == 0) break;
                    byArray2[n5] = 0;
                    ++n5;
                    ++n6;
                }
            }
            n9 = n4 - n3;
            if (n9 > 0) {
                if (n9 > 127) {
                    n9 = 127;
                }
                n7 = byArray[n3];
                for (n8 = 1; n8 < n9 && byArray[n3 + n8] == n7; ++n8) {
                }
                byArray2[n5] = (byte)(n8 + n8);
                byArray2[++n5] = n7;
                ++n5;
            }
            n3 += n8;
            n6 += 2;
        }
        byArray2[n5] = 0;
        ++n5;
        if (bl) {
            byArray2[n5] = 1;
            ++n5;
        } else {
            byArray2[n5] = 0;
            ++n5;
        }
        return n6 += 2;
    }

    int compressRLE8Data(byte[] byArray, int n, int n2, byte[] byArray2, boolean bl) {
        int n3 = n;
        int n4 = n + n2;
        int n5 = 0;
        int n6 = 0;
        while (n3 < n4) {
            int n7;
            int n8;
            int n9 = n4 - n3 - 1;
            if (n9 > 254) {
                n9 = 254;
            }
            for (n8 = 0; n8 < n9 && byArray[n3 + n8] != byArray[n3 + n8 + 1]; ++n8) {
            }
            if (n8 == n9) {
                ++n8;
            }
            switch (n8) {
                case 0: {
                    break;
                }
                case 2: {
                    byArray2[n5] = 1;
                    byArray2[++n5] = byArray[n3];
                    ++n5;
                    ++n3;
                    n6 += 2;
                }
                case 1: {
                    byArray2[n5] = 1;
                    byArray2[++n5] = byArray[n3];
                    ++n5;
                    ++n3;
                    n6 += 2;
                    break;
                }
                default: {
                    byArray2[n5] = 0;
                    byArray2[++n5] = (byte)n8;
                    ++n5;
                    for (n7 = n8; n7 > 0; --n7) {
                        byArray2[n5] = byArray[n3];
                        ++n5;
                        ++n3;
                    }
                    n6 += 2 + n8;
                    if ((n8 & 1) == 0) break;
                    byArray2[n5] = 0;
                    ++n5;
                    ++n6;
                }
            }
            n9 = n4 - n3;
            if (n9 > 0) {
                if (n9 > 255) {
                    n9 = 255;
                }
                n7 = byArray[n3];
                for (n8 = 1; n8 < n9 && byArray[n3 + n8] == n7; ++n8) {
                }
                byArray2[n5] = (byte)n8;
                byArray2[++n5] = n7;
                ++n5;
            }
            n3 += n8;
            n6 += 2;
        }
        byArray2[n5] = 0;
        ++n5;
        if (bl) {
            byArray2[n5] = 1;
            ++n5;
        } else {
            byArray2[n5] = 0;
            ++n5;
        }
        return n6 += 2;
    }

    void convertPixelsToBGR(ImageData imageData, byte[] byArray) {
        byte[] byArray2 = imageData.data;
        PaletteData paletteData = imageData.palette;
        for (int i = 0; i < imageData.height; ++i) {
            int n = 0;
            int n2 = i;
            int n3 = imageData.depth / 8;
            int n4 = i * imageData.bytesPerLine;
            for (int j = 0; j < imageData.width; ++j) {
                int n5;
                int n6;
                int n7;
                int n8 = 0;
                switch (imageData.depth) {
                    case 32: {
                        n8 = (byArray2[n4] & 0xFF) << 24 | (byArray2[n4 + 1] & 0xFF) << 16 | (byArray2[n4 + 2] & 0xFF) << 8 | byArray2[n4 + 3] & 0xFF;
                        break;
                    }
                    case 24: {
                        n8 = (byArray2[n4] & 0xFF) << 16 | (byArray2[n4 + 1] & 0xFF) << 8 | byArray2[n4 + 2] & 0xFF;
                        break;
                    }
                    case 16: {
                        n8 = (byArray2[n4 + 1] & 0xFF) << 8 | byArray2[n4] & 0xFF;
                        break;
                    }
                    default: {
                        SWT.error(38);
                    }
                }
                if (imageData.depth == 16) {
                    n7 = n8 & paletteData.redMask;
                    n7 = paletteData.redShift < 0 ? n7 >>> -paletteData.redShift : n7 << paletteData.redShift;
                    n6 = n8 & paletteData.greenMask;
                    n6 = paletteData.greenShift < 0 ? n6 >>> -paletteData.greenShift : n6 << paletteData.greenShift;
                    n5 = n8 & paletteData.blueMask;
                    n5 = paletteData.blueShift < 0 ? n5 >>> -paletteData.blueShift : n5 << paletteData.blueShift;
                    int n9 = n7 << 7 | (n6 &= 0xF8) << 2 | n5 >> 3;
                    byArray[n4] = (byte)(n9 & 0xFF);
                    byArray[n4 + 1] = (byte)(n9 >> 8 & 0xFF);
                } else {
                    n7 = n8 & paletteData.blueMask;
                    byArray[n4] = (byte)(paletteData.blueShift < 0 ? n7 >>> -paletteData.blueShift : n7 << paletteData.blueShift);
                    n6 = n8 & paletteData.greenMask;
                    byArray[n4 + 1] = (byte)(paletteData.greenShift < 0 ? n6 >>> -paletteData.greenShift : n6 << paletteData.greenShift);
                    n5 = n8 & paletteData.redMask;
                    byArray[n4 + 2] = (byte)(paletteData.redShift < 0 ? n5 >>> -paletteData.redShift : n5 << paletteData.redShift);
                    if (n3 == 4) {
                        byArray[n4 + 3] = 0;
                    }
                }
                if (++n >= imageData.width) {
                    n4 = ++n2 * imageData.bytesPerLine;
                    n = 0;
                    continue;
                }
                n4 += n3;
            }
        }
    }

    void decompressData(byte[] byArray, byte[] byArray2, int n, int n2) {
        if (n2 == 1) {
            if (this.decompressRLE8Data(byArray, byArray.length, n, byArray2, byArray2.length) <= 0) {
                SWT.error(40);
            }
            return;
        }
        if (n2 == 2) {
            if (this.decompressRLE4Data(byArray, byArray.length, n, byArray2, byArray2.length) <= 0) {
                SWT.error(40);
            }
            return;
        }
        SWT.error(40);
    }

    int decompressRLE4Data(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        int n4 = 0;
        int n5 = n;
        int n6 = 0;
        int n7 = n3;
        int n8 = 0;
        int n9 = 0;
        block5: while (n4 < n5) {
            int n10;
            int n11 = byArray[n4] & 0xFF;
            ++n4;
            if (n11 == 0) {
                n11 = byArray[n4] & 0xFF;
                ++n4;
                switch (n11) {
                    case 0: {
                        n8 = 0;
                        if ((n6 = ++n9 * n2) <= n7) continue block5;
                        return -1;
                    }
                    case 1: {
                        return 1;
                    }
                    case 2: {
                        n8 += byArray[n4] & 0xFF;
                        n9 += byArray[++n4] & 0xFF;
                        ++n4;
                        n6 = n9 * n2 + n8 / 2;
                        if (n6 <= n7) continue block5;
                        return -1;
                    }
                }
                if ((n11 & 1) != 0) {
                    return -1;
                }
                n8 += n11;
                if ((n11 /= 2) > n5 - n4) {
                    return -1;
                }
                if (n11 > n7 - n6) {
                    return -1;
                }
                for (n10 = 0; n10 < n11; ++n10) {
                    byArray2[n6] = byArray[n4];
                    ++n6;
                    ++n4;
                }
                if ((n4 & 1) == 0) continue;
                ++n4;
                continue;
            }
            if ((n11 & 1) != 0) {
                return -1;
            }
            n8 += n11;
            n10 = byArray[n4];
            ++n4;
            if ((n11 /= 2) > n7 - n6) {
                return -1;
            }
            for (int i = 0; i < n11; ++i) {
                byArray2[n6] = n10;
                ++n6;
            }
        }
        return 1;
    }

    int decompressRLE8Data(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        int n4 = 0;
        int n5 = n;
        int n6 = 0;
        int n7 = n3;
        int n8 = 0;
        int n9 = 0;
        block5: while (n4 < n5) {
            int n10;
            int n11 = byArray[n4] & 0xFF;
            ++n4;
            if (n11 == 0) {
                n11 = byArray[n4] & 0xFF;
                ++n4;
                switch (n11) {
                    case 0: {
                        n8 = 0;
                        if ((n6 = ++n9 * n2) <= n7) continue block5;
                        return -1;
                    }
                    case 1: {
                        return 1;
                    }
                    case 2: {
                        n8 += byArray[n4] & 0xFF;
                        n9 += byArray[++n4] & 0xFF;
                        ++n4;
                        n6 = n9 * n2 + n8;
                        if (n6 <= n7) continue block5;
                        return -1;
                    }
                }
                if (n11 > n5 - n4) {
                    return -1;
                }
                if (n11 > n7 - n6) {
                    return -1;
                }
                for (n10 = 0; n10 < n11; ++n10) {
                    byArray2[n6] = byArray[n4];
                    ++n6;
                    ++n4;
                }
                if ((n4 & 1) != 0) {
                    ++n4;
                }
                n8 += n11;
                continue;
            }
            n10 = byArray[n4];
            ++n4;
            if (n11 > n7 - n6) {
                return -1;
            }
            for (int i = 0; i < n11; ++i) {
                byArray2[n6] = n10;
                ++n6;
            }
            n8 += n11;
        }
        return 1;
    }

    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[18];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            int n = byArray[14] & 0xFF | (byArray[15] & 0xFF) << 8 | (byArray[16] & 0xFF) << 16 | (byArray[17] & 0xFF) << 24;
            return byArray[0] == 66 && byArray[1] == 77 && n >= 40;
        }
        catch (Exception exception) {
            return false;
        }
    }

    byte[] loadData(byte[] byArray) {
        int n = byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8 | (byArray[6] & 0xFF) << 16 | (byArray[7] & 0xFF) << 24;
        int n2 = byArray[8] & 0xFF | (byArray[9] & 0xFF) << 8 | (byArray[10] & 0xFF) << 16 | (byArray[11] & 0xFF) << 24;
        int n3 = byArray[14] & 0xFF | (byArray[15] & 0xFF) << 8;
        int n4 = (n * n3 + 7) / 8;
        n4 = (n4 + 3) / 4 * 4;
        byte[] byArray2 = this.loadData(byArray, n4);
        this.flipScanLines(byArray2, n4, n2);
        return byArray2;
    }

    byte[] loadData(byte[] byArray, int n) {
        int n2 = byArray[8] & 0xFF | (byArray[9] & 0xFF) << 8 | (byArray[10] & 0xFF) << 16 | (byArray[11] & 0xFF) << 24;
        if (n2 < 0) {
            n2 = -n2;
        }
        int n3 = n2 * n;
        byte[] byArray2 = new byte[n3];
        int n4 = byArray[16] & 0xFF | (byArray[17] & 0xFF) << 8 | (byArray[18] & 0xFF) << 16 | (byArray[19] & 0xFF) << 24;
        if (n4 != 0 && n4 != 3) {
            int n5 = byArray[20] & 0xFF | (byArray[21] & 0xFF) << 8 | (byArray[22] & 0xFF) << 16 | (byArray[23] & 0xFF) << 24;
            byte[] byArray3 = new byte[n5];
            try {
                if (this.inputStream.read(byArray3) != n5) {
                    SWT.error(40);
                }
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
            this.decompressData(byArray3, byArray2, n, n4);
            return byArray2;
        }
        try {
            if (this.inputStream.read(byArray2) != n3) {
                SWT.error(40);
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        return byArray2;
    }

    int[] loadFileHeader() {
        int[] nArray = new int[5];
        try {
            nArray[0] = this.inputStream.readShort();
            nArray[1] = this.inputStream.readInt();
            nArray[2] = this.inputStream.readShort();
            nArray[3] = this.inputStream.readShort();
            nArray[4] = this.inputStream.readInt();
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if (nArray[0] != 19778) {
            SWT.error(40);
        }
        return nArray;
    }

    @Override
    ImageData[] loadFromByteStream() {
        int[] nArray = this.loadFileHeader();
        byte[] byArray = new byte[40];
        try {
            this.inputStream.read(byArray);
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
        int n = byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8 | (byArray[6] & 0xFF) << 16 | (byArray[7] & 0xFF) << 24;
        int n2 = byArray[8] & 0xFF | (byArray[9] & 0xFF) << 8 | (byArray[10] & 0xFF) << 16 | (byArray[11] & 0xFF) << 24;
        if (n2 < 0) {
            n2 = -n2;
        }
        int n3 = byArray[14] & 0xFF | (byArray[15] & 0xFF) << 8;
        this.compression = byArray[16] & 0xFF | (byArray[17] & 0xFF) << 8 | (byArray[18] & 0xFF) << 16 | (byArray[19] & 0xFF) << 24;
        PaletteData paletteData = this.loadPalette(byArray);
        if (this.inputStream.getPosition() < nArray[4]) {
            try {
                this.inputStream.skip(nArray[4] - this.inputStream.getPosition());
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
        }
        byte[] byArray2 = this.loadData(byArray);
        this.importantColors = byArray[36] & 0xFF | (byArray[37] & 0xFF) << 8 | (byArray[38] & 0xFF) << 16 | (byArray[39] & 0xFF) << 24;
        int n4 = byArray[24] & 0xFF | (byArray[25] & 0xFF) << 8 | (byArray[26] & 0xFF) << 16 | (byArray[27] & 0xFF) << 24;
        int n5 = byArray[28] & 0xFF | (byArray[29] & 0xFF) << 8 | (byArray[30] & 0xFF) << 16 | (byArray[31] & 0xFF) << 24;
        this.pelsPerMeter = new Point(n4, n5);
        int n6 = this.compression == 1 || this.compression == 2 ? 1 : 0;
        return new ImageData[]{ImageData.internal_new(n, n2, n3, paletteData, 4, byArray2, 0, null, null, -1, -1, n6, 0, 0, 0, 0)};
    }

    PaletteData loadPalette(byte[] byArray) {
        int n = byArray[14] & 0xFF | (byArray[15] & 0xFF) << 8;
        if (n <= 8) {
            int n2 = byArray[32] & 0xFF | (byArray[33] & 0xFF) << 8 | (byArray[34] & 0xFF) << 16 | (byArray[35] & 0xFF) << 24;
            if (n2 == 0) {
                n2 = 1 << n;
            } else if (n2 > 256) {
                n2 = 256;
            }
            byte[] byArray2 = new byte[n2 * 4];
            try {
                if (this.inputStream.read(byArray2) != byArray2.length) {
                    SWT.error(40);
                }
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
            return this.paletteFromBytes(byArray2, n2);
        }
        if (n == 16) {
            if (this.compression == 3) {
                try {
                    return new PaletteData(this.inputStream.readInt(), this.inputStream.readInt(), this.inputStream.readInt());
                }
                catch (IOException iOException) {
                    SWT.error(39, iOException);
                }
            }
            return new PaletteData(31744, 992, 31);
        }
        if (n == 24) {
            return new PaletteData(255, 65280, 0xFF0000);
        }
        if (this.compression == 3) {
            try {
                int n3 = Integer.reverseBytes(this.inputStream.readInt());
                int n4 = Integer.reverseBytes(this.inputStream.readInt());
                int n5 = Integer.reverseBytes(this.inputStream.readInt());
                return new PaletteData(n3, n4, n5);
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
        }
        return new PaletteData(65280, 0xFF0000, -16777216);
    }

    PaletteData paletteFromBytes(byte[] byArray, int n) {
        int n2 = 0;
        RGB[] rGBArray = new RGB[n];
        for (int i = 0; i < n; ++i) {
            rGBArray[i] = new RGB(byArray[n2 + 2] & 0xFF, byArray[n2 + 1] & 0xFF, byArray[n2] & 0xFF);
            n2 += 4;
        }
        return new PaletteData(rGBArray);
    }

    static byte[] paletteToBytes(PaletteData paletteData) {
        int n = paletteData.colors == null ? 0 : (paletteData.colors.length < 256 ? paletteData.colors.length : 256);
        byte[] byArray = new byte[n * 4];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            RGB rGB = paletteData.colors[i];
            byArray[n2] = (byte)rGB.blue;
            byArray[n2 + 1] = (byte)rGB.green;
            byArray[n2 + 2] = (byte)rGB.red;
            n2 += 4;
        }
        return byArray;
    }

    int unloadData(ImageData imageData, byte[] byArray, OutputStream outputStream, int n) {
        int n2 = 0;
        try {
            if (n == 0) {
                return this.unloadDataNoCompression(imageData, byArray, outputStream);
            }
            int n3 = (imageData.width * imageData.depth + 7) / 8;
            int n4 = (n3 + 3) / 4 * 4;
            int n5 = imageData.bytesPerLine;
            byte[] byArray2 = new byte[n4 * 2];
            int n6 = n5 * (imageData.height - 1);
            if (byArray == null) {
                byArray = imageData.data;
            }
            n2 = 0;
            byte[] byArray3 = new byte[32768];
            int n7 = 0;
            for (int i = imageData.height - 1; i >= 0; --i) {
                int n8 = this.compress(n, byArray, n6, n3, byArray2, i == 0);
                if (n7 + n8 > byArray3.length) {
                    outputStream.write(byArray3, 0, n7);
                    n7 = 0;
                }
                System.arraycopy(byArray2, 0, byArray3, n7, n8);
                n7 += n8;
                n2 += n8;
                n6 -= n5;
            }
            if (n7 > 0) {
                outputStream.write(byArray3, 0, n7);
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        return n2;
    }

    int unloadDataNoCompression(ImageData imageData, byte[] byArray, OutputStream outputStream) {
        int n = 0;
        try {
            int n2 = (imageData.width * imageData.depth + 7) / 8;
            n = (n2 + 3) / 4 * 4;
            int n3 = 32678 / n;
            byte[] byArray2 = new byte[n3 * n];
            if (byArray == null) {
                byArray = imageData.data;
            }
            int n4 = imageData.bytesPerLine;
            int n5 = n4 * (imageData.height - 1);
            if (imageData.depth == 16) {
                for (int i = 0; i < imageData.height; i += n3) {
                    int n6 = imageData.height - i;
                    if (n3 < n6) {
                        n6 = n3;
                    }
                    int n7 = 0;
                    for (int j = 0; j < n6; ++j) {
                        for (int k = 0; k < n2; k += 2) {
                            byArray2[n7 + k + 1] = byArray[n5 + k + 1];
                            byArray2[n7 + k] = byArray[n5 + k];
                        }
                        n7 += n;
                        n5 -= n4;
                    }
                    outputStream.write(byArray2, 0, n7);
                }
            } else {
                for (int i = 0; i < imageData.height; i += n3) {
                    int n8 = imageData.height - i;
                    int n9 = n8 < n3 ? n8 : n3;
                    int n10 = 0;
                    for (int j = 0; j < n9; ++j) {
                        System.arraycopy(byArray, n5, byArray2, n10, n2);
                        n10 += n;
                        n5 -= n4;
                    }
                    outputStream.write(byArray2, 0, n10);
                }
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        return n * imageData.height;
    }

    /*
     * Exception decompiling
     */
    @Override
    void unloadIntoByteStream(ImageLoader var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl285 : RETURN - null : trying to set 0 previously set to 1
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

    void flipScanLines(byte[] byArray, int n, int n2) {
        int n3 = 0;
        int n4 = (n2 - 1) * n;
        for (int i = 0; i < n2 / 2; ++i) {
            for (int j = 0; j < n; ++j) {
                byte by = byArray[j + n3];
                byArray[j + n3] = byArray[j + n4];
                byArray[j + n4] = by;
            }
            n3 += n;
            n4 -= n;
        }
    }
}

