/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.LEDataOutputStream;
import org.eclipse.swt.internal.image.PngChunk;

final class PngEncoder {
    static final byte[] SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
    static final byte[] TAG_IHDR = new byte[]{73, 72, 68, 82};
    static final byte[] TAG_PLTE = new byte[]{80, 76, 84, 69};
    static final byte[] TAG_TRNS = new byte[]{116, 82, 78, 83};
    static final byte[] TAG_IDAT = new byte[]{73, 68, 65, 84};
    static final byte[] TAG_IEND = new byte[]{73, 69, 78, 68};
    static final int NO_COMPRESSION = 0;
    static final int BEST_SPEED = 1;
    static final int BEST_COMPRESSION = 9;
    static final int DEFAULT_COMPRESSION = -1;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
    PngChunk chunk;
    ImageLoader loader;
    ImageData data;
    int transparencyType;
    int width;
    int height;
    int bitDepth;
    int colorType;
    int compressionMethod = 0;
    int filterMethod = 0;
    int interlaceMethod = 0;

    public PngEncoder(ImageLoader imageLoader) {
        this.loader = imageLoader;
        this.data = imageLoader.data[0];
        this.transparencyType = this.data.getTransparencyType();
        this.width = this.data.width;
        this.height = this.data.height;
        this.bitDepth = 8;
        this.colorType = 2;
        if (this.data.palette.isDirect) {
            if (this.transparencyType == 1) {
                this.colorType = 6;
            }
        } else {
            this.colorType = 3;
        }
        if (this.colorType != 2 && this.colorType != 3 && this.colorType != 6) {
            SWT.error(40);
        }
    }

    void writeShort(ByteArrayOutputStream byteArrayOutputStream, int n) {
        byte by = (byte)(n >> 8 & 0xFF);
        byte by2 = (byte)(n & 0xFF);
        byte[] byArray = new byte[]{by, by2};
        byteArrayOutputStream.write(byArray, 0, 2);
    }

    void writeInt(ByteArrayOutputStream byteArrayOutputStream, int n) {
        byte by = (byte)(n >> 24 & 0xFF);
        byte by2 = (byte)(n >> 16 & 0xFF);
        byte by3 = (byte)(n >> 8 & 0xFF);
        byte by4 = (byte)(n & 0xFF);
        byte[] byArray = new byte[]{by, by2, by3, by4};
        byteArrayOutputStream.write(byArray, 0, 4);
    }

    void writeChunk(byte[] byArray, byte[] byArray2) {
        int n = byArray2 != null ? byArray2.length : 0;
        this.chunk = new PngChunk(n);
        this.writeInt(this.bytes, n);
        this.bytes.write(byArray, 0, 4);
        this.chunk.setType(byArray);
        if (n != 0) {
            this.bytes.write(byArray2, 0, n);
            this.chunk.setData(byArray2);
        } else {
            this.chunk.setCRC(this.chunk.computeCRC());
        }
        this.writeInt(this.bytes, this.chunk.getCRC());
    }

    void writeSignature() {
        this.bytes.write(SIGNATURE, 0, 8);
    }

    void writeHeader() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(13);
        this.writeInt(byteArrayOutputStream, this.width);
        this.writeInt(byteArrayOutputStream, this.height);
        byteArrayOutputStream.write(this.bitDepth);
        byteArrayOutputStream.write(this.colorType);
        byteArrayOutputStream.write(this.compressionMethod);
        byteArrayOutputStream.write(this.filterMethod);
        byteArrayOutputStream.write(this.interlaceMethod);
        this.writeChunk(TAG_IHDR, byteArrayOutputStream.toByteArray());
    }

    void writePalette() {
        RGB[] rGBArray = this.data.palette.getRGBs();
        if (rGBArray.length > 256) {
            SWT.error(40);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(rGBArray.length);
        for (RGB rGB : rGBArray) {
            byteArrayOutputStream.write((byte)rGB.red);
            byteArrayOutputStream.write((byte)rGB.green);
            byteArrayOutputStream.write((byte)rGB.blue);
        }
        this.writeChunk(TAG_PLTE, byteArrayOutputStream.toByteArray());
    }

    void writeTransparency() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        switch (this.transparencyType) {
            case 1: {
                byte[] byArray = new byte[this.data.palette.getRGBs().length];
                for (int i = 0; i < this.height; ++i) {
                    for (int j = 0; j < this.width; ++j) {
                        int n = this.data.getPixel(j, i);
                        int n2 = this.data.getAlpha(j, i);
                        byArray[n] = (byte)n2;
                    }
                }
                byteArrayOutputStream.write(byArray, 0, byArray.length);
                break;
            }
            case 4: {
                int n;
                int n3 = this.data.transparentPixel;
                if (this.colorType == 2) {
                    int n4 = this.data.palette.redMask;
                    n = this.data.palette.redShift;
                    int n5 = this.data.palette.greenMask;
                    int n6 = this.data.palette.greenShift;
                    int n7 = this.data.palette.blueShift;
                    int n8 = this.data.palette.blueMask;
                    int n9 = n3 & n4;
                    n9 = n < 0 ? n9 >>> -n : n9 << n;
                    int n10 = n3 & n5;
                    n10 = n6 < 0 ? n10 >>> -n6 : n10 << n6;
                    int n11 = n3 & n8;
                    n11 = n7 < 0 ? n11 >>> -n7 : n11 << n7;
                    this.writeShort(byteArrayOutputStream, n9);
                    this.writeShort(byteArrayOutputStream, n10);
                    this.writeShort(byteArrayOutputStream, n11);
                }
                if (this.colorType != 3) break;
                byte[] byArray = new byte[n3 + 1];
                for (n = 0; n < n3; ++n) {
                    byArray[n] = -1;
                }
                byArray[n3] = 0;
                byteArrayOutputStream.write(byArray, 0, byArray.length);
                break;
            }
        }
        this.writeChunk(TAG_TRNS, byteArrayOutputStream.toByteArray());
    }

    void writeImageData() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        DeflaterOutputStream deflaterOutputStream = null;
        switch (this.loader.compression) {
            case 0: {
                deflaterOutputStream = new DeflaterOutputStream((OutputStream)byteArrayOutputStream, new Deflater(0));
                break;
            }
            case 1: {
                deflaterOutputStream = new DeflaterOutputStream((OutputStream)byteArrayOutputStream, new Deflater(1));
                break;
            }
            case 3: {
                deflaterOutputStream = new DeflaterOutputStream((OutputStream)byteArrayOutputStream, new Deflater(9));
                break;
            }
            default: {
                deflaterOutputStream = new DeflaterOutputStream((OutputStream)byteArrayOutputStream, new Deflater(-1));
            }
        }
        if (this.colorType == 3) {
            byte[] byArray = new byte[this.width];
            for (int i = 0; i < this.height; ++i) {
                boolean bl = false;
                ((OutputStream)deflaterOutputStream).write(0);
                this.data.getPixels(0, i, this.width, byArray, 0);
                ((OutputStream)deflaterOutputStream).write(byArray);
            }
        } else {
            int[] nArray = new int[this.width];
            byte[] byArray = null;
            if (this.colorType == 6) {
                byArray = new byte[this.width];
            }
            int n = this.data.palette.redMask;
            int n2 = this.data.palette.redShift;
            int n3 = this.data.palette.greenMask;
            int n4 = this.data.palette.greenShift;
            int n5 = this.data.palette.blueShift;
            int n6 = this.data.palette.blueMask;
            byte[] byArray2 = new byte[this.width * (this.colorType == 6 ? 4 : 3)];
            for (int i = 0; i < this.height; ++i) {
                boolean bl = false;
                ((OutputStream)deflaterOutputStream).write(0);
                this.data.getPixels(0, i, this.width, nArray, 0);
                if (this.colorType == 6) {
                    this.data.getAlphas(0, i, this.width, byArray, 0);
                }
                int n7 = 0;
                for (int j = 0; j < nArray.length; ++j) {
                    int n8 = nArray[j];
                    int n9 = n8 & n;
                    byArray2[n7++] = (byte)(n2 < 0 ? n9 >>> -n2 : n9 << n2);
                    int n10 = n8 & n3;
                    byArray2[n7++] = (byte)(n4 < 0 ? n10 >>> -n4 : n10 << n4);
                    int n11 = n8 & n6;
                    byArray2[n7++] = (byte)(n5 < 0 ? n11 >>> -n5 : n11 << n5);
                    if (this.colorType != 6) continue;
                    byArray2[n7++] = byArray[j];
                }
                ((OutputStream)deflaterOutputStream).write(byArray2);
            }
        }
        ((OutputStream)deflaterOutputStream).flush();
        ((OutputStream)deflaterOutputStream).close();
        this.writeChunk(TAG_IDAT, byteArrayOutputStream.toByteArray());
    }

    void writeEnd() {
        this.writeChunk(TAG_IEND, null);
    }

    public void encode(LEDataOutputStream lEDataOutputStream) {
        try {
            boolean bl;
            this.writeSignature();
            this.writeHeader();
            if (this.colorType == 3) {
                this.writePalette();
            }
            boolean bl2 = this.transparencyType == 1;
            boolean bl3 = this.transparencyType == 4;
            boolean bl4 = this.colorType == 2 && bl3;
            boolean bl5 = bl = this.colorType == 3 && (bl2 || bl3);
            if (bl4 || bl) {
                this.writeTransparency();
            }
            this.writeImageData();
            this.writeEnd();
            lEDataOutputStream.write(this.bytes.toByteArray());
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }
}

