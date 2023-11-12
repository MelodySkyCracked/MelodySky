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
import org.eclipse.swt.internal.image.LEDataOutputStream;
import org.eclipse.swt.internal.image.TIFFModifiedHuffmanCodec;
import org.eclipse.swt.internal.image.TIFFRandomFileAccess;

final class TIFFDirectory {
    TIFFRandomFileAccess file;
    boolean isLittleEndian;
    ImageLoader loader;
    int depth;
    int subfileType;
    int imageWidth;
    int imageLength;
    int[] bitsPerSample;
    int compression;
    int photometricInterpretation;
    int[] stripOffsets;
    int samplesPerPixel;
    int rowsPerStrip;
    int[] stripByteCounts;
    int t4Options;
    int colorMapOffset;
    ImageData image;
    LEDataOutputStream out;
    static final int NO_VALUE = -1;
    static final short TAG_NewSubfileType = 254;
    static final short TAG_SubfileType = 255;
    static final short TAG_ImageWidth = 256;
    static final short TAG_ImageLength = 257;
    static final short TAG_BitsPerSample = 258;
    static final short TAG_Compression = 259;
    static final short TAG_PhotometricInterpretation = 262;
    static final short TAG_FillOrder = 266;
    static final short TAG_ImageDescription = 270;
    static final short TAG_StripOffsets = 273;
    static final short TAG_Orientation = 274;
    static final short TAG_SamplesPerPixel = 277;
    static final short TAG_RowsPerStrip = 278;
    static final short TAG_StripByteCounts = 279;
    static final short TAG_XResolution = 282;
    static final short TAG_YResolution = 283;
    static final short TAG_PlanarConfiguration = 284;
    static final short TAG_T4Options = 292;
    static final short TAG_ResolutionUnit = 296;
    static final short TAG_Software = 305;
    static final short TAG_DateTime = 306;
    static final short TAG_ColorMap = 320;
    static final int TYPE_BYTE = 1;
    static final int TYPE_ASCII = 2;
    static final int TYPE_SHORT = 3;
    static final int TYPE_LONG = 4;
    static final int TYPE_RATIONAL = 5;
    static final int FILETYPE_REDUCEDIMAGE = 1;
    static final int FILETYPE_PAGE = 2;
    static final int FILETYPE_MASK = 4;
    static final int OFILETYPE_IMAGE = 1;
    static final int OFILETYPE_REDUCEDIMAGE = 2;
    static final int OFILETYPE_PAGE = 3;
    static final int COMPRESSION_NONE = 1;
    static final int COMPRESSION_CCITT_3_1 = 2;
    static final int COMPRESSION_PACKBITS = 32773;
    static final int IFD_ENTRY_SIZE = 12;

    public TIFFDirectory(TIFFRandomFileAccess tIFFRandomFileAccess, boolean bl, ImageLoader imageLoader) {
        this.file = tIFFRandomFileAccess;
        this.isLittleEndian = bl;
        this.loader = imageLoader;
    }

    public TIFFDirectory(ImageData imageData) {
        this.image = imageData;
    }

    int decodePackBits(byte[] byArray, byte[] byArray2, int n) {
        int n2 = n;
        int n3 = 0;
        while (n3 < byArray.length) {
            byte by = byArray[n3];
            if (by >= 0) {
                System.arraycopy(byArray, ++n3, byArray2, n2, by + 1);
                n3 += by + 1;
                n2 += by + 1;
                continue;
            }
            if (by >= -127) {
                byte by2 = byArray[++n3];
                for (int i = 0; i < -by + 1; ++i) {
                    byArray2[n2++] = by2;
                }
                ++n3;
                continue;
            }
            ++n3;
        }
        return n2 - n;
    }

    int getEntryValue(int n, byte[] byArray, int n2) {
        return this.toInt(byArray, n2 + 8, n);
    }

    void getEntryValue(int n, byte[] byArray, int n2, int[] nArray) throws IOException {
        int n3 = n2 + 8;
        int n4 = this.toInt(byArray, n3, 4);
        int n5 = 0;
        switch (n) {
            case 3: {
                n5 = 2;
                break;
            }
            case 4: {
                n5 = 4;
                break;
            }
            case 5: {
                n5 = 8;
                break;
            }
            case 1: 
            case 2: {
                n5 = 1;
                break;
            }
            default: {
                SWT.error(42);
                return;
            }
        }
        if (nArray.length * n5 > 4) {
            byArray = new byte[nArray.length * n5];
            this.file.seek(n4);
            this.file.read(byArray);
            n3 = 0;
        }
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = this.toInt(byArray, n3 + i * n5, n);
        }
    }

    void decodePixels(ImageData imageData) throws IOException {
        byte[] byArray = new byte[(this.imageWidth * this.depth + 7) / 8 * this.imageLength];
        imageData.data = byArray;
        int n = 0;
        int n2 = this.stripOffsets.length;
        for (int i = 0; i < n2; ++i) {
            byte[] byArray2 = new byte[this.stripByteCounts[i]];
            this.file.seek(this.stripOffsets[i]);
            this.file.read(byArray2);
            if (this.compression == 1) {
                System.arraycopy(byArray2, 0, byArray, n, byArray2.length);
                n += byArray2.length;
            } else if (this.compression == 32773) {
                n += this.decodePackBits(byArray2, byArray, n);
            } else if (this.compression == 2 || this.compression == 3) {
                int n3;
                TIFFModifiedHuffmanCodec tIFFModifiedHuffmanCodec = new TIFFModifiedHuffmanCodec();
                int n4 = this.rowsPerStrip;
                if (i == n2 - 1 && (n3 = this.imageLength % this.rowsPerStrip) != 0) {
                    n4 = n3;
                }
                n += tIFFModifiedHuffmanCodec.decode(byArray2, byArray, n, this.imageWidth, n4);
            }
            if (!this.loader.hasListeners()) continue;
            this.loader.notifyListeners(new ImageLoaderEvent(this.loader, imageData, i, i == n2 - 1));
        }
    }

    PaletteData getColorMap() throws IOException {
        int n = 1 << this.bitsPerSample[0];
        int n2 = 6 * n;
        byte[] byArray = new byte[n2];
        this.file.seek(this.colorMapOffset);
        this.file.read(byArray);
        RGB[] rGBArray = new RGB[n];
        int n3 = this.isLittleEndian ? 1 : 0;
        int n4 = 2 * n;
        int n5 = n4 + 2 * n;
        for (int i = 0; i < n; ++i) {
            int n6 = byArray[n3] & 0xFF;
            int n7 = byArray[n4 + n3] & 0xFF;
            int n8 = byArray[n5 + n3] & 0xFF;
            rGBArray[i] = new RGB(n6, n7, n8);
            n3 += 2;
        }
        return new PaletteData(rGBArray);
    }

    PaletteData getGrayPalette() {
        int n = 1 << this.bitsPerSample[0];
        RGB[] rGBArray = new RGB[n];
        for (int i = 0; i < n; ++i) {
            int n2 = i * 255 / (n - 1);
            if (this.photometricInterpretation == 0) {
                n2 = 255 - n2;
            }
            rGBArray[i] = new RGB(n2, n2, n2);
        }
        return new PaletteData(rGBArray);
    }

    PaletteData getRGBPalette(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6 = 0;
        for (n5 = 0; n5 < n3; ++n5) {
            n6 |= 1 << n5;
        }
        n5 = 0;
        for (n4 = n3; n4 < n3 + n2; ++n4) {
            n5 |= 1 << n4;
        }
        n4 = 0;
        for (int i = n3 + n2; i < n3 + n2 + n; ++i) {
            n4 |= 1 << i;
        }
        return new PaletteData(n4, n5, n6);
    }

    int formatStrips(int n, int n2, byte[] byArray, int n3, int n4, int n5, int[][] nArray) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        if (n > n3) {
            n10 = byArray.length / n;
            n9 = 1;
        } else {
            n8 = (byArray.length + n3 - 1) / n3;
            n9 = n2 / n8;
            n10 = (n2 + n9 - 1) / n9;
        }
        n8 = n * n9;
        int[] nArray2 = new int[n10];
        int[] nArray3 = new int[n10];
        int n11 = n10 == 1 ? 0 : n10 * 2 * 4;
        int n12 = n7 = n4 + n5 + n11;
        for (n6 = 0; n6 < n10; ++n6) {
            nArray2[n6] = n7;
            nArray3[n6] = n8;
            n7 += n8;
        }
        n6 = byArray.length % n8;
        if (n6 != 0) {
            nArray3[nArray3.length - 1] = n6;
        }
        nArray[0] = nArray2;
        nArray[1] = nArray3;
        return n9;
    }

    int[] formatColorMap(RGB[] rGBArray) {
        int[] nArray = new int[rGBArray.length * 3];
        int n = rGBArray.length;
        int n2 = rGBArray.length * 2;
        for (int i = 0; i < rGBArray.length; ++i) {
            nArray[i] = rGBArray[i].red << 8 | rGBArray[i].red;
            nArray[i + n] = rGBArray[i].green << 8 | rGBArray[i].green;
            nArray[i + n2] = rGBArray[i].blue << 8 | rGBArray[i].blue;
        }
        return nArray;
    }

    void parseEntries(byte[] byArray) throws IOException {
        block15: for (int i = 0; i < byArray.length; i += 12) {
            int n = this.toInt(byArray, i, 3);
            int n2 = this.toInt(byArray, i + 2, 3);
            int n3 = this.toInt(byArray, i + 4, 4);
            switch (n) {
                case 254: {
                    this.subfileType = this.getEntryValue(n2, byArray, i);
                    continue block15;
                }
                case 255: {
                    int n4 = this.getEntryValue(n2, byArray, i);
                    this.subfileType = n4 == 2 ? 1 : (n4 == 3 ? 2 : 0);
                    continue block15;
                }
                case 256: {
                    this.imageWidth = this.getEntryValue(n2, byArray, i);
                    continue block15;
                }
                case 257: {
                    this.imageLength = this.getEntryValue(n2, byArray, i);
                    continue block15;
                }
                case 258: {
                    if (n2 != 3) {
                        SWT.error(40);
                    }
                    this.bitsPerSample = new int[n3];
                    this.getEntryValue(n2, byArray, i, this.bitsPerSample);
                    continue block15;
                }
                case 259: {
                    this.compression = this.getEntryValue(n2, byArray, i);
                }
                case 262: 
                case 266: {
                    this.photometricInterpretation = this.getEntryValue(n2, byArray, i);
                    continue block15;
                }
                case 273: {
                    if (n2 != 4 && n2 != 3) {
                        SWT.error(40);
                    }
                    this.stripOffsets = new int[n3];
                    this.getEntryValue(n2, byArray, i, this.stripOffsets);
                }
                case 277: {
                    if (n2 != 3) {
                        SWT.error(40);
                    }
                    this.samplesPerPixel = this.getEntryValue(n2, byArray, i);
                    if (this.samplesPerPixel == 1 || this.samplesPerPixel == 3) continue block15;
                    SWT.error(38);
                    continue block15;
                }
                case 278: {
                    this.rowsPerStrip = this.getEntryValue(n2, byArray, i);
                    continue block15;
                }
                case 279: {
                    this.stripByteCounts = new int[n3];
                    this.getEntryValue(n2, byArray, i, this.stripByteCounts);
                }
                case 282: 
                case 283: 
                case 292: {
                    if (n2 != 4) {
                        SWT.error(40);
                    }
                    this.t4Options = this.getEntryValue(n2, byArray, i);
                    if ((this.t4Options & 1) != 1) continue block15;
                    SWT.error(42);
                    continue block15;
                }
                case 296: 
                case 305: 
                case 320: {
                    if (n2 != 3) {
                        SWT.error(40);
                    }
                    this.colorMapOffset = this.getEntryValue(4, byArray, i);
                }
            }
        }
    }

    public ImageData read(int[] nArray) throws IOException {
        this.bitsPerSample = new int[]{1};
        this.colorMapOffset = -1;
        this.compression = 1;
        this.imageLength = -1;
        this.imageWidth = -1;
        this.photometricInterpretation = -1;
        this.rowsPerStrip = Integer.MAX_VALUE;
        this.samplesPerPixel = 1;
        this.stripByteCounts = null;
        this.stripOffsets = null;
        byte[] byArray = new byte[2];
        this.file.read(byArray);
        int n = this.toInt(byArray, 0, 3);
        byArray = new byte[12 * n];
        this.file.read(byArray);
        byte[] byArray2 = new byte[4];
        this.file.read(byArray2);
        nArray[0] = this.toInt(byArray2, 0, 4);
        this.parseEntries(byArray);
        PaletteData paletteData = null;
        this.depth = 0;
        switch (this.photometricInterpretation) {
            case 0: 
            case 1: {
                paletteData = this.getGrayPalette();
                this.depth = this.bitsPerSample[0];
                break;
            }
            case 2: {
                if (this.colorMapOffset != -1) {
                    SWT.error(40);
                }
                paletteData = this.getRGBPalette(this.bitsPerSample[0], this.bitsPerSample[1], this.bitsPerSample[2]);
                this.depth = this.bitsPerSample[0] + this.bitsPerSample[1] + this.bitsPerSample[2];
                break;
            }
            case 3: {
                if (this.colorMapOffset == -1) {
                    SWT.error(40);
                }
                paletteData = this.getColorMap();
                this.depth = this.bitsPerSample[0];
                break;
            }
            default: {
                SWT.error(40);
            }
        }
        ImageData imageData = ImageData.internal_new(this.imageWidth, this.imageLength, this.depth, paletteData, 1, null, 0, null, null, -1, -1, 6, 0, 0, 0, 0);
        this.decodePixels(imageData);
        return imageData;
    }

    int toInt(byte[] byArray, int n, int n2) {
        if (n2 == 4) {
            return this.isLittleEndian ? byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 | (byArray[n + 2] & 0xFF) << 16 | (byArray[n + 3] & 0xFF) << 24 : byArray[n + 3] & 0xFF | (byArray[n + 2] & 0xFF) << 8 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n] & 0xFF) << 24;
        }
        if (n2 == 3) {
            return this.isLittleEndian ? byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 : byArray[n + 1] & 0xFF | (byArray[n] & 0xFF) << 8;
        }
        SWT.error(40);
        return -1;
    }

    void write(int n) throws IOException {
        int n2;
        Object object;
        Object object2;
        boolean bl = n == 2;
        boolean bl2 = n == 3;
        boolean bl3 = n == 0 || n == 1;
        int n3 = this.image.width;
        int n4 = this.image.height;
        int n5 = this.image.bytesPerLine;
        int n6 = bl3 ? 9 : 11;
        int n7 = 2 + 12 * n6 + 4;
        int n8 = 8 + n7;
        int n9 = 16;
        int[] nArray = null;
        if (bl2) {
            object2 = this.image.palette;
            object = ((PaletteData)object2).getRGBs();
            nArray = this.formatColorMap((RGB[])object);
            if (nArray.length != 3 << this.image.depth) {
                SWT.error(42);
            }
            n9 += nArray.length * 2;
        }
        if (bl) {
            n9 += 6;
        }
        object2 = this.image.data;
        object = new int[2][];
        int n10 = this.formatStrips(n5, n4, (byte[])object2, 8192, n8, n9, (int[][])object);
        RGB rGB = object[0];
        RGB rGB2 = object[1];
        int n11 = -1;
        if (bl) {
            n11 = n8;
            n8 += 6;
        }
        int n12 = -1;
        int n13 = -1;
        int n14 = -1;
        int n15 = ((RGB)rGB).length;
        if (n15 > 1) {
            n12 = n8;
            n8 = n13 = n8 + 4 * n15;
            n8 += 4 * n15;
        }
        int n16 = n8;
        int n17 = n8 += 8;
        n8 += 8;
        if (bl2) {
            n14 = n8;
            n8 += nArray.length * 2;
        }
        this.writeHeader();
        this.out.writeShort(n6);
        this.writeEntry((short)256, 4, 1, n3);
        this.writeEntry((short)257, 4, 1, n4);
        if (bl2) {
            this.writeEntry((short)258, 3, 1, this.image.depth);
        }
        if (bl) {
            this.writeEntry((short)258, 3, 3, n11);
        }
        this.writeEntry((short)259, 3, 1, 1);
        this.writeEntry((short)262, 3, 1, n);
        this.writeEntry((short)273, 4, n15, n15 > 1 ? n12 : (int)rGB[0]);
        if (bl) {
            this.writeEntry((short)277, 3, 1, 3);
        }
        this.writeEntry((short)278, 4, 1, n10);
        this.writeEntry((short)279, 4, n15, n15 > 1 ? n13 : (int)rGB2[0]);
        this.writeEntry((short)282, 5, 1, n16);
        this.writeEntry((short)283, 5, 1, n17);
        if (bl2) {
            this.writeEntry((short)320, 3, nArray.length, n14);
        }
        this.out.writeInt(0);
        if (bl) {
            for (n2 = 0; n2 < 3; ++n2) {
                this.out.writeShort(8);
            }
        }
        if (n15 > 1) {
            for (n2 = 0; n2 < n15; ++n2) {
                this.out.writeInt((int)rGB[n2]);
            }
            for (n2 = 0; n2 < n15; ++n2) {
                this.out.writeInt((int)rGB2[n2]);
            }
        }
        for (n2 = 0; n2 < 2; ++n2) {
            this.out.writeInt(300);
            this.out.writeInt(1);
        }
        if (bl2) {
            for (int n18 : nArray) {
                this.out.writeShort(n18);
            }
        }
        this.out.write((byte[])object2);
    }

    void writeEntry(short s, int n, int n2, int n3) throws IOException {
        this.out.writeShort(s);
        this.out.writeShort(n);
        this.out.writeInt(n2);
        this.out.writeInt(n3);
    }

    void writeHeader() throws IOException {
        this.out.write(73);
        this.out.write(73);
        this.out.writeShort(42);
        this.out.writeInt(8);
    }

    void writeToStream(LEDataOutputStream lEDataOutputStream) throws IOException {
        this.out = lEDataOutputStream;
        int n = -1;
        if (this.image.scanlinePad != 1) {
            SWT.error(42);
        }
        switch (this.image.depth) {
            case 1: {
                PaletteData paletteData = this.image.palette;
                RGB[] rGBArray = paletteData.colors;
                if (paletteData.isDirect || rGBArray == null || rGBArray.length != 2) {
                    SWT.error(42);
                }
                RGB rGB = rGBArray[0];
                RGB rGB2 = rGBArray[1];
                if (rGB.red != rGB.green || rGB.green != rGB.blue || rGB2.red != rGB2.green || rGB2.green != rGB2.blue || (rGB.red != 0 || rGB2.red != 255) && (rGB.red != 255 || rGB2.red != 0)) {
                    SWT.error(42);
                }
                n = this.image.palette.colors[0].red != 255 ? 1 : 0;
                break;
            }
            case 4: 
            case 8: {
                n = 3;
                break;
            }
            case 24: {
                n = 2;
                break;
            }
            default: {
                SWT.error(42);
            }
        }
        this.write(n);
    }
}

