/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.FileFormat;
import org.eclipse.swt.internal.image.LEDataInputStream;

public final class OS2BMPFileFormat
extends FileFormat {
    static final int BMPFileHeaderSize = 14;
    static final int BMPHeaderFixedSize = 12;
    int width;
    int height;
    int bitCount;

    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[18];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            int n = byArray[14] & 0xFF | (byArray[15] & 0xFF) << 8 | (byArray[16] & 0xFF) << 16 | (byArray[17] & 0xFF) << 24;
            return byArray[0] == 66 && byArray[1] == 77 && n == 12;
        }
        catch (Exception exception) {
            return false;
        }
    }

    byte[] loadData(byte[] byArray) {
        int n = (this.width * this.bitCount + 7) / 8;
        n = (n + 3) / 4 * 4;
        byte[] byArray2 = this.loadData(byArray, n);
        this.flipScanLines(byArray2, n, this.height);
        return byArray2;
    }

    byte[] loadData(byte[] byArray, int n) {
        int n2 = this.height * n;
        byte[] byArray2 = new byte[n2];
        try {
            if (this.inputStream.read(byArray2) != n2) {
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
        byte[] byArray = new byte[12];
        try {
            this.inputStream.read(byArray);
        }
        catch (Exception exception) {
            SWT.error(39, exception);
        }
        this.width = byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8;
        this.height = byArray[6] & 0xFF | (byArray[7] & 0xFF) << 8;
        this.bitCount = byArray[10] & 0xFF | (byArray[11] & 0xFF) << 8;
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
        int n = 7;
        return new ImageData[]{ImageData.internal_new(this.width, this.height, this.bitCount, paletteData, 4, byArray2, 0, null, null, -1, -1, 7, 0, 0, 0, 0)};
    }

    PaletteData loadPalette(byte[] byArray) {
        if (this.bitCount <= 8) {
            int n = 1 << this.bitCount;
            byte[] byArray2 = new byte[n * 3];
            try {
                if (this.inputStream.read(byArray2) != byArray2.length) {
                    SWT.error(40);
                }
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
            return this.paletteFromBytes(byArray2, n);
        }
        if (this.bitCount == 16) {
            return new PaletteData(31744, 992, 31);
        }
        if (this.bitCount == 24) {
            return new PaletteData(255, 65280, 0xFF0000);
        }
        return new PaletteData(65280, 0xFF0000, -16777216);
    }

    PaletteData paletteFromBytes(byte[] byArray, int n) {
        int n2 = 0;
        RGB[] rGBArray = new RGB[n];
        for (int i = 0; i < n; ++i) {
            rGBArray[i] = new RGB(byArray[n2 + 2] & 0xFF, byArray[n2 + 1] & 0xFF, byArray[n2] & 0xFF);
            n2 += 3;
        }
        return new PaletteData(rGBArray);
    }

    static byte[] paletteToBytes(PaletteData paletteData) {
        int n = paletteData.colors == null ? 0 : (paletteData.colors.length < 256 ? paletteData.colors.length : 256);
        byte[] byArray = new byte[n * 3];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            RGB rGB = paletteData.colors[i];
            byArray[n2] = (byte)rGB.blue;
            byArray[n2 + 1] = (byte)rGB.green;
            byArray[n2 + 2] = (byte)rGB.red;
            n2 += 3;
        }
        return byArray;
    }

    int unloadData(ImageData imageData, OutputStream outputStream) {
        int n = 0;
        try {
            int n2 = (imageData.width * imageData.depth + 7) / 8;
            n = (n2 + 3) / 4 * 4;
            int n3 = 32678 / n;
            byte[] byArray = new byte[n3 * n];
            byte[] byArray2 = imageData.data;
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
                            byArray[n7 + k + 1] = byArray2[n5 + k + 1];
                            byArray[n7 + k] = byArray2[n5 + k];
                        }
                        n7 += n;
                        n5 -= n4;
                    }
                    outputStream.write(byArray, 0, n7);
                }
            } else {
                for (int i = 0; i < imageData.height; i += n3) {
                    int n8 = imageData.height - i;
                    int n9 = n8 < n3 ? n8 : n3;
                    int n10 = 0;
                    for (int j = 0; j < n9; ++j) {
                        System.arraycopy(byArray2, n5, byArray, n10, n2);
                        n10 += n;
                        n5 -= n4;
                    }
                    outputStream.write(byArray, 0, n10);
                }
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        return n * imageData.height;
    }

    @Override
    void unloadIntoByteStream(ImageLoader imageLoader) {
        Object object;
        byte[] byArray;
        int n;
        ImageData imageData = imageLoader.data[0];
        if (imageData.depth != 1 && imageData.depth != 4 && imageData.depth != 8 && imageData.depth != 16 && imageData.depth != 24 && imageData.depth != 32) {
            SWT.error(38);
        }
        PaletteData paletteData = imageData.palette;
        if (imageData.depth == 16 || imageData.depth == 24 || imageData.depth == 32) {
            if (!paletteData.isDirect) {
                SWT.error(40);
            }
            n = 0;
            byArray = null;
        } else {
            if (paletteData.isDirect) {
                SWT.error(40);
            }
            n = paletteData.colors.length;
            byArray = OS2BMPFileFormat.paletteToBytes(paletteData);
        }
        int n2 = 26;
        int[] nArray = new int[]{19778, 0, 0, 0, 26};
        if (byArray != null) {
            object = nArray;
            int n3 = 4;
            Object object2 = object;
            object2[4] = object2[4] + byArray.length;
        }
        object = new ByteArrayOutputStream();
        this.unloadData(imageData, (OutputStream)object);
        byte[] byArray2 = ((ByteArrayOutputStream)object).toByteArray();
        nArray[1] = nArray[4] + byArray2.length;
        try {
            this.outputStream.writeShort(nArray[0]);
            this.outputStream.writeInt(nArray[1]);
            this.outputStream.writeShort(nArray[2]);
            this.outputStream.writeShort(nArray[3]);
            this.outputStream.writeInt(nArray[4]);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        try {
            this.outputStream.writeInt(12);
            this.outputStream.writeShort(imageData.width);
            this.outputStream.writeShort(imageData.height);
            this.outputStream.writeShort(1);
            this.outputStream.writeShort((short)imageData.depth);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if (n > 0) {
            try {
                this.outputStream.write(byArray);
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
        }
        try {
            this.outputStream.write(byArray2);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
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

