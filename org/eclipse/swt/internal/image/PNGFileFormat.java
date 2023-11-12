/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.internal.image.FileFormat;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngChunkReader;
import org.eclipse.swt.internal.image.PngDecodingDataStream;
import org.eclipse.swt.internal.image.PngEncoder;
import org.eclipse.swt.internal.image.PngIdatChunk;
import org.eclipse.swt.internal.image.PngIhdrChunk;
import org.eclipse.swt.internal.image.PngInputStream;
import org.eclipse.swt.internal.image.PngPlteChunk;
import org.eclipse.swt.internal.image.PngTrnsChunk;

public final class PNGFileFormat
extends FileFormat {
    static final int SIGNATURE_LENGTH = 8;
    static final int PRIME = 65521;
    PngIhdrChunk headerChunk;
    PngPlteChunk paletteChunk;
    ImageData imageData;
    byte[] data;
    byte[] alphaPalette;
    byte headerByte1;
    byte headerByte2;
    int adler;

    void readSignature() throws IOException {
        byte[] byArray = new byte[8];
        this.inputStream.read(byArray);
    }

    @Override
    ImageData[] loadFromByteStream() {
        try {
            this.readSignature();
            PngChunkReader pngChunkReader = new PngChunkReader(this.inputStream);
            this.headerChunk = pngChunkReader.getIhdrChunk();
            int n = this.headerChunk.getWidth();
            int n2 = this.headerChunk.getHeight();
            if (n <= 0 || n2 <= 0) {
                SWT.error(40);
            }
            int n3 = this.getAlignedBytesPerRow() * n2;
            this.data = new byte[n3];
            this.imageData = ImageData.internal_new(n, n2, this.headerChunk.getSwtBitsPerPixel(), new PaletteData(0, 0, 0), 4, this.data, 0, null, null, -1, -1, 5, 0, 0, 0, 0);
            if (this.headerChunk.usesDirectColor()) {
                this.imageData.palette = this.headerChunk.getPaletteData();
            }
            while (pngChunkReader.hasMoreChunks()) {
                this.readNextChunk(pngChunkReader);
            }
            return new ImageData[]{this.imageData};
        }
        catch (IOException iOException) {
            SWT.error(40);
            return null;
        }
    }

    void readNextChunk(PngChunkReader pngChunkReader) throws IOException {
        PngChunk pngChunk = pngChunkReader.readNextChunk();
        switch (pngChunk.getChunkType()) {
            case 3: {
                break;
            }
            case 1: {
                if (this.headerChunk.usesDirectColor()) break;
                this.paletteChunk = (PngPlteChunk)pngChunk;
                this.imageData.palette = this.paletteChunk.getPaletteData();
                break;
            }
            case 5: {
                PngTrnsChunk pngTrnsChunk = (PngTrnsChunk)pngChunk;
                if (pngTrnsChunk.getTransparencyType(this.headerChunk) == 0) {
                    this.imageData.transparentPixel = pngTrnsChunk.getSwtTransparentPixel(this.headerChunk);
                    break;
                }
                this.alphaPalette = pngTrnsChunk.getAlphaValues(this.headerChunk, this.paletteChunk);
                int n = 0;
                int n2 = -1;
                for (int i = 0; i < this.alphaPalette.length; ++i) {
                    if ((this.alphaPalette[i] & 0xFF) == 255) continue;
                    ++n;
                    n2 = i;
                }
                if (n == 0) {
                    this.alphaPalette = null;
                    break;
                }
                if (n != true || this.alphaPalette[n2] != 0) break;
                this.alphaPalette = null;
                this.imageData.transparentPixel = n2;
                break;
            }
            case 2: {
                if (pngChunkReader.readPixelData()) {
                    SWT.error(40);
                    break;
                }
                PngIdatChunk pngIdatChunk = (PngIdatChunk)pngChunk;
                this.readPixelData(pngIdatChunk, pngChunkReader);
                break;
            }
            default: {
                if (!pngChunk.isCritical()) break;
                SWT.error(20);
            }
        }
    }

    @Override
    void unloadIntoByteStream(ImageLoader imageLoader) {
        PngEncoder pngEncoder = new PngEncoder(imageLoader);
        pngEncoder.encode(this.outputStream);
    }

    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[8];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            return (byArray[0] & 0xFF) == 137 && (byArray[1] & 0xFF) == 80 && (byArray[2] & 0xFF) == 78 && (byArray[3] & 0xFF) == 71 && (byArray[4] & 0xFF) == 13 && (byArray[5] & 0xFF) == 10 && (byArray[6] & 0xFF) == 26 && (byArray[7] & 0xFF) == 10;
        }
        catch (Exception exception) {
            return false;
        }
    }

    byte[] validateBitDepth(byte[] byArray) {
        if (this.headerChunk.getBitDepth() > 8) {
            byte[] byArray2 = new byte[byArray.length / 2];
            PNGFileFormat.compress16BitDepthTo8BitDepth(byArray, 0, byArray2, 0, byArray2.length);
            return byArray2;
        }
        return byArray;
    }

    void setPixelData(byte[] byArray, ImageData imageData) {
        switch (this.headerChunk.getColorType()) {
            case 4: {
                int n = imageData.width;
                int n2 = imageData.height;
                int n3 = imageData.bytesPerLine;
                int n4 = this.getAlignedBytesPerRow();
                if (this.headerChunk.getBitDepth() > 8) {
                    n4 /= 2;
                }
                byte[] byArray2 = new byte[n3 * n2];
                byte[] byArray3 = new byte[n * n2];
                for (int i = 0; i < n2; ++i) {
                    int n5 = n4 * i;
                    int n6 = n3 * i;
                    int n7 = n * i;
                    for (int j = 0; j < n; ++j) {
                        byte by = byArray[n5];
                        byte by2 = byArray[n5 + 1];
                        byArray2[n6 + 0] = by;
                        byte by3 = by;
                        byArray2[n6 + 1] = by3;
                        byArray2[n6 + 2] = by3;
                        byArray3[n7] = by2;
                        n5 += 2;
                        n6 += 3;
                        ++n7;
                    }
                }
                imageData.data = byArray2;
                imageData.alphaData = byArray3;
                break;
            }
            case 6: {
                int n = imageData.width;
                int n8 = imageData.height;
                int n9 = imageData.bytesPerLine;
                int n10 = this.getAlignedBytesPerRow();
                if (this.headerChunk.getBitDepth() > 8) {
                    n10 /= 2;
                }
                byte[] byArray4 = new byte[n9 * n8];
                byte[] byArray5 = new byte[n * n8];
                for (int i = 0; i < n8; ++i) {
                    int n11 = n10 * i;
                    int n12 = n9 * i;
                    int n13 = n * i;
                    for (int j = 0; j < n; ++j) {
                        byArray4[n12 + 0] = byArray[n11 + 0];
                        byArray4[n12 + 1] = byArray[n11 + 1];
                        byArray4[n12 + 2] = byArray[n11 + 2];
                        byArray5[n13] = byArray[n11 + 3];
                        n11 += 4;
                        n12 += 3;
                        ++n13;
                    }
                }
                imageData.data = byArray4;
                imageData.alphaData = byArray5;
                break;
            }
            case 3: {
                imageData.data = byArray;
                if (this.alphaPalette == null) break;
                int n = imageData.width * imageData.height;
                byte[] byArray6 = new byte[n];
                byte[] byArray7 = new byte[n];
                imageData.getPixels(0, 0, n, byArray7, 0);
                for (int i = 0; i < byArray7.length; ++i) {
                    byArray6[i] = this.alphaPalette[byArray7[i] & 0xFF];
                }
                imageData.alphaData = byArray6;
                break;
            }
            default: {
                int n = imageData.height;
                int n14 = imageData.bytesPerLine;
                int n15 = this.getAlignedBytesPerRow();
                if (this.headerChunk.getBitDepth() > 8) {
                    n15 /= 2;
                }
                if (n14 != n15) {
                    imageData.data = new byte[n14 * n];
                    for (int i = 0; i < n; ++i) {
                        System.arraycopy(byArray, i * n15, imageData.data, i * n14, n15);
                    }
                    break;
                }
                imageData.data = byArray;
                break;
            }
        }
    }

    void setImageDataValues(byte[] byArray, ImageData imageData) {
        byte[] byArray2 = this.validateBitDepth(byArray);
        this.setPixelData(byArray2, imageData);
    }

    void readPixelData(PngIdatChunk pngIdatChunk, PngChunkReader pngChunkReader) throws IOException {
        InputStream inputStream = new PngInputStream(pngIdatChunk, pngChunkReader);
        boolean bl = System.getProperty("org.eclipse.swt.internal.image.PNGFileFormat_3.2") != null;
        BufferedInputStream bufferedInputStream = bl ? null : new BufferedInputStream(new InflaterInputStream(inputStream));
        inputStream = bufferedInputStream != null ? bufferedInputStream : new PngDecodingDataStream(inputStream);
        byte by = this.headerChunk.getInterlaceMethod();
        if (by == 0) {
            this.readNonInterlacedImage(inputStream);
        } else {
            this.readInterlacedImage(inputStream);
        }
        while (inputStream.available() > 0) {
            inputStream.read();
        }
        inputStream.close();
    }

    int getAlignedBytesPerRow() {
        return (this.getBytesPerRow(this.headerChunk.getWidth()) + 3) / 4 * 4;
    }

    int getBytesPerRow() {
        return this.getBytesPerRow(this.headerChunk.getWidth());
    }

    int getBytesPerPixel() {
        int n = this.headerChunk.getBitsPerPixel();
        return (n + 7) / 8;
    }

    int getBytesPerRow(int n) {
        int n2 = this.headerChunk.getBitsPerPixel();
        int n3 = n2 * n;
        int n4 = 8;
        return (n3 + 7) / 8;
    }

    void readInterlaceFrame(InputStream inputStream, int n, int n2, int n3, int n4, int n5) throws IOException {
        int n6 = this.headerChunk.getWidth();
        int n7 = this.getAlignedBytesPerRow();
        int n8 = this.headerChunk.getHeight();
        if (n3 >= n8 || n4 >= n6) {
            return;
        }
        int n9 = (n6 - n4 + n2 - 1) / n2;
        int n10 = this.getBytesPerRow(n9);
        byte[] byArray = new byte[n10];
        byte[] byArray2 = new byte[n10];
        byte[] byArray3 = byArray;
        byte[] byArray4 = byArray2;
        for (int i = n3; i < n8; i += n) {
            int n11;
            int n12;
            int n13;
            int n14;
            byte by = (byte)inputStream.read();
            for (n14 = 0; n14 != n10; n14 += inputStream.read(byArray3, n14, n10 - n14)) {
            }
            this.filterRow(byArray3, byArray4, by);
            if (this.headerChunk.getBitDepth() >= 8) {
                n14 = this.getBytesPerPixel();
                n13 = i * n7 + n4 * n14;
                for (n12 = 0; n12 < byArray3.length; n12 += n14) {
                    for (n11 = 0; n11 < n14; ++n11) {
                        this.data[n13 + n11] = byArray3[n12 + n11];
                    }
                    n13 += n2 * n14;
                }
            } else {
                int n15;
                n14 = this.headerChunk.getBitDepth();
                n13 = 8 / n14;
                n12 = n4;
                n11 = i * n7;
                int n16 = 0;
                for (n15 = 0; n15 < n14; ++n15) {
                    n16 <<= 1;
                    n16 |= 1;
                }
                n15 = 8 - n14;
                for (byte by2 : byArray3) {
                    for (int j = n15; j >= 0; j -= n14) {
                        if (n12 < n6) {
                            int n17;
                            int n18 = n11 + n12 * n14 / 8;
                            int n19 = by2 >> j & n16;
                            int n20 = n15 - n14 * (n12 % n13);
                            byte[] byArray5 = this.data;
                            int n21 = n17 = n18;
                            byArray5[n21] = (byte)(byArray5[n21] | (byte)(n19 << n20));
                        }
                        n12 += n2;
                    }
                }
            }
            byArray3 = byArray3 == byArray ? byArray2 : byArray;
            byArray4 = byArray4 == byArray ? byArray2 : byArray;
        }
        this.setImageDataValues(this.data, this.imageData);
        this.fireInterlacedFrameEvent(n5);
    }

    void readInterlacedImage(InputStream inputStream) throws IOException {
        this.readInterlaceFrame(inputStream, 8, 8, 0, 0, 0);
        this.readInterlaceFrame(inputStream, 8, 8, 0, 4, 1);
        this.readInterlaceFrame(inputStream, 8, 4, 4, 0, 2);
        this.readInterlaceFrame(inputStream, 4, 4, 0, 2, 3);
        this.readInterlaceFrame(inputStream, 4, 2, 2, 0, 4);
        this.readInterlaceFrame(inputStream, 2, 2, 0, 1, 5);
        this.readInterlaceFrame(inputStream, 2, 1, 1, 0, 6);
    }

    void fireInterlacedFrameEvent(int n) {
        if (this.loader.hasListeners()) {
            ImageData imageData = (ImageData)this.imageData.clone();
            boolean bl = n == 6;
            this.loader.notifyListeners(new ImageLoaderEvent(this.loader, imageData, n, bl));
        }
    }

    void readNonInterlacedImage(InputStream inputStream) throws IOException {
        int n = 0;
        int n2 = this.getAlignedBytesPerRow();
        int n3 = this.getBytesPerRow();
        byte[] byArray = new byte[n3];
        byte[] byArray2 = new byte[n3];
        byte[] byArray3 = byArray;
        byte[] byArray4 = byArray2;
        int n4 = this.headerChunk.getHeight();
        for (int i = 0; i < n4; ++i) {
            byte by = (byte)inputStream.read();
            for (int j = 0; j != n3; j += inputStream.read(byArray3, j, n3 - j)) {
            }
            this.filterRow(byArray3, byArray4, by);
            System.arraycopy(byArray3, 0, this.data, n, n3);
            n += n2;
            byArray3 = byArray3 == byArray ? byArray2 : byArray;
            byArray4 = byArray4 == byArray ? byArray2 : byArray;
        }
        this.setImageDataValues(this.data, this.imageData);
    }

    static void compress16BitDepthTo8BitDepth(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            byte by;
            int n4 = n + 2 * i;
            int n5 = n2 + i;
            byArray2[n5] = by = byArray[n4];
        }
    }

    static int compress16BitDepthTo8BitDepth(int n) {
        return n >> 8;
    }

    void filterRow(byte[] byArray, byte[] byArray2, int n) {
        int n2 = this.headerChunk.getFilterByteOffset();
        switch (n) {
            case 1: {
                for (int i = n2; i < byArray.length; ++i) {
                    int n3 = byArray[i] & 0xFF;
                    int n4 = byArray[i - n2] & 0xFF;
                    byArray[i] = (byte)(n3 + n4 & 0xFF);
                }
                break;
            }
            case 2: {
                for (int i = 0; i < byArray.length; ++i) {
                    int n5 = byArray[i] & 0xFF;
                    int n6 = byArray2[i] & 0xFF;
                    byArray[i] = (byte)(n5 + n6 & 0xFF);
                }
                break;
            }
            case 3: {
                for (int i = 0; i < byArray.length; ++i) {
                    int n7 = i < n2 ? 0 : byArray[i - n2] & 0xFF;
                    int n8 = byArray2[i] & 0xFF;
                    int n9 = byArray[i] & 0xFF;
                    byArray[i] = (byte)(n9 + (n7 + n8) / 2 & 0xFF);
                }
                break;
            }
            case 4: {
                for (int i = 0; i < byArray.length; ++i) {
                    int n10 = i < n2 ? 0 : byArray[i - n2] & 0xFF;
                    int n11 = i < n2 ? 0 : byArray2[i - n2] & 0xFF;
                    int n12 = byArray2[i] & 0xFF;
                    int n13 = Math.abs(n12 - n11);
                    int n14 = Math.abs(n10 - n11);
                    int n15 = Math.abs(n10 - n11 + n12 - n11);
                    int n16 = 0;
                    n16 = n13 <= n14 && n13 <= n15 ? n10 : (n14 <= n15 ? n12 : n11);
                    int n17 = byArray[i] & 0xFF;
                    byArray[i] = (byte)(n17 + n16 & 0xFF);
                }
                break;
            }
        }
    }
}

