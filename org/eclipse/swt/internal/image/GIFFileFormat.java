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
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.LZWCodec;

public final class GIFFileFormat
extends FileFormat {
    String signature;
    int screenWidth;
    int screenHeight;
    int backgroundPixel;
    int bitsPerPixel;
    int defaultDepth;
    int disposalMethod = 0;
    int delayTime = 0;
    int transparentPixel = -1;
    int repeatCount = 1;
    static final int GIF_APPLICATION_EXTENSION_BLOCK_ID = 255;
    static final int GIF_GRAPHICS_CONTROL_BLOCK_ID = 249;
    static final int GIF_PLAIN_TEXT_BLOCK_ID = 1;
    static final int GIF_COMMENT_BLOCK_ID = 254;
    static final int GIF_EXTENSION_BLOCK_ID = 33;
    static final int GIF_IMAGE_BLOCK_ID = 44;
    static final int GIF_TRAILER_ID = 59;
    static final byte[] GIF89a = new byte[]{71, 73, 70, 56, 57, 97};
    static final byte[] NETSCAPE2_0 = new byte[]{78, 69, 84, 83, 67, 65, 80, 69, 50, 46, 48};

    static PaletteData grayRamp(int n) {
        int n2 = n - 1;
        RGB[] rGBArray = new RGB[n];
        for (int i = 0; i < n; ++i) {
            byte by = (byte)(i * 3 * 256 / n2);
            rGBArray[i] = new RGB(by, by, by);
        }
        return new PaletteData(rGBArray);
    }

    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[3];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            return byArray[0] == 71 && byArray[1] == 73 && byArray[2] == 70;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    ImageData[] loadFromByteStream() {
        byte[] byArray = new byte[3];
        byte[] byArray2 = new byte[3];
        byte[] byArray3 = new byte[7];
        try {
            this.inputStream.read(byArray);
            if (byArray[0] != 71 || byArray[1] != 73 || byArray[2] != 70) {
                SWT.error(40);
            }
            this.inputStream.read(byArray2);
            this.inputStream.read(byArray3);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        this.loader.logicalScreenWidth = this.screenWidth = byArray3[0] & 0xFF | (byArray3[1] & 0xFF) << 8;
        this.loader.logicalScreenHeight = this.screenHeight = byArray3[2] & 0xFF | (byArray3[3] & 0xFF) << 8;
        byte by = byArray3[4];
        this.backgroundPixel = byArray3[5] & 0xFF;
        this.bitsPerPixel = (by >> 4 & 7) + 1;
        this.defaultDepth = (by & 7) + 1;
        PaletteData paletteData = null;
        if ((by & 0x80) != 0) {
            paletteData = this.readPalette(1 << this.defaultDepth);
        } else {
            this.backgroundPixel = -1;
            this.defaultDepth = this.bitsPerPixel;
        }
        this.loader.backgroundPixel = this.backgroundPixel;
        ImageData[] imageDataArray = new ImageData[]{};
        int n = this.readID();
        while (n != 59 && n != -1) {
            if (n == 44) {
                ImageData imageData = this.readImageBlock(paletteData);
                if (this.loader.hasListeners()) {
                    this.loader.notifyListeners(new ImageLoaderEvent(this.loader, imageData, 3, true));
                }
                ImageData[] imageDataArray2 = imageDataArray;
                imageDataArray = new ImageData[imageDataArray2.length + 1];
                System.arraycopy(imageDataArray2, 0, imageDataArray, 0, imageDataArray2.length);
                imageDataArray[imageDataArray.length - 1] = imageData;
            } else if (n == 33) {
                this.readExtension();
            } else {
                if (imageDataArray.length > 0) break;
                SWT.error(40);
            }
            n = this.readID();
            if (n == 0) {
                // empty if block
            }
            n = this.readID();
        }
        return imageDataArray;
    }

    int readID() {
        try {
            return this.inputStream.read();
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
            return -1;
        }
    }

    byte[] readExtension() {
        int n = this.readID();
        if (n == 254) {
            return this.readCommentExtension();
        }
        if (n == 1) {
            return this.readPlainTextExtension();
        }
        if (n == 249) {
            return this.readGraphicsControlExtension();
        }
        if (n == 255) {
            return this.readApplicationExtension();
        }
        try {
            int n2 = this.inputStream.read();
            if (n2 < 0) {
                SWT.error(40);
            }
            byte[] byArray = new byte[n2];
            this.inputStream.read(byArray, 0, n2);
            return byArray;
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
            return null;
        }
    }

    byte[] readCommentExtension() {
        try {
            byte[] byArray = new byte[]{};
            byte[] byArray2 = new byte[255];
            int n = this.inputStream.read();
            while (n > 0 && this.inputStream.read(byArray2, 0, n) != -1) {
                byte[] byArray3 = byArray;
                byArray = new byte[byArray3.length + n];
                System.arraycopy(byArray3, 0, byArray, 0, byArray3.length);
                System.arraycopy(byArray2, 0, byArray, byArray3.length, n);
                n = this.inputStream.read();
            }
            return byArray;
        }
        catch (Exception exception) {
            SWT.error(39, exception);
            return null;
        }
    }

    byte[] readPlainTextExtension() {
        try {
            this.inputStream.read();
            byte[] byArray = new byte[12];
            this.inputStream.read(byArray);
            byte[] byArray2 = new byte[]{};
            byte[] byArray3 = new byte[255];
            int n = this.inputStream.read();
            while (n > 0 && this.inputStream.read(byArray3, 0, n) != -1) {
                byte[] byArray4 = byArray2;
                byArray2 = new byte[byArray4.length + n];
                System.arraycopy(byArray4, 0, byArray2, 0, byArray4.length);
                System.arraycopy(byArray3, 0, byArray2, byArray4.length, n);
                n = this.inputStream.read();
            }
            return byArray2;
        }
        catch (Exception exception) {
            SWT.error(39, exception);
            return null;
        }
    }

    byte[] readGraphicsControlExtension() {
        try {
            this.inputStream.read();
            byte[] byArray = new byte[4];
            this.inputStream.read(byArray);
            byte by = byArray[0];
            this.disposalMethod = by >> 2 & 7;
            this.delayTime = byArray[1] & 0xFF | (byArray[2] & 0xFF) << 8;
            this.transparentPixel = (by & 1) != 0 ? byArray[3] & 0xFF : -1;
            return byArray;
        }
        catch (Exception exception) {
            SWT.error(39, exception);
            return null;
        }
    }

    byte[] readApplicationExtension() {
        try {
            boolean bl;
            int n = this.inputStream.read();
            byte[] byArray = new byte[n];
            this.inputStream.read(byArray);
            byte[] byArray2 = new byte[]{};
            byte[] byArray3 = new byte[255];
            int n2 = this.inputStream.read();
            while (n2 > 0 && this.inputStream.read(byArray3, 0, n2) != -1) {
                byte[] byArray4 = byArray2;
                byArray2 = new byte[byArray4.length + n2];
                System.arraycopy(byArray4, 0, byArray2, 0, byArray4.length);
                System.arraycopy(byArray3, 0, byArray2, byArray4.length, n2);
                n2 = this.inputStream.read();
            }
            n2 = n > 7 && byArray[0] == 78 && byArray[1] == 69 && byArray[2] == 84 && byArray[3] == 83 && byArray[4] == 67 && byArray[5] == 65 && byArray[6] == 80 && byArray[7] == 69 ? 1 : 0;
            boolean bl2 = bl = n > 10 && byArray[8] == 50 && byArray[9] == 46 && byArray[10] == 48;
            if (n2 != 0 && bl && byArray2[0] == 1) {
                this.loader.repeatCount = this.repeatCount = byArray2[1] & 0xFF | (byArray2[2] & 0xFF) << 8;
            }
            return byArray2;
        }
        catch (Exception exception) {
            SWT.error(39, exception);
            return null;
        }
    }

    ImageData readImageBlock(PaletteData paletteData) {
        PaletteData paletteData2;
        int n;
        boolean bl;
        byte[] byArray = new byte[9];
        try {
            this.inputStream.read(byArray);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        int n2 = byArray[0] & 0xFF | (byArray[1] & 0xFF) << 8;
        int n3 = byArray[2] & 0xFF | (byArray[3] & 0xFF) << 8;
        int n4 = byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8;
        int n5 = byArray[6] & 0xFF | (byArray[7] & 0xFF) << 8;
        byte by = byArray[8];
        boolean bl2 = bl = (by & 0x40) != 0;
        if ((by & 0x80) != 0) {
            n = (by & 7) + 1;
            paletteData2 = this.readPalette(1 << n);
        } else {
            n = this.defaultDepth;
            paletteData2 = paletteData;
        }
        if (this.transparentPixel > 1 << n) {
            this.transparentPixel = -1;
        }
        if (n != 1 && n != 4 && n != 8) {
            n = n < 4 ? 4 : 8;
        }
        if (paletteData2 == null) {
            paletteData2 = GIFFileFormat.grayRamp(1 << n);
        }
        int n6 = -1;
        try {
            n6 = this.inputStream.read();
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if (n6 < 0) {
            SWT.error(40);
        }
        ImageData imageData = ImageData.internal_new(n4, n5, n, paletteData2, 4, null, 0, null, null, -1, this.transparentPixel, 2, n2, n3, this.disposalMethod, this.delayTime);
        LZWCodec lZWCodec = new LZWCodec();
        lZWCodec.decode(this.inputStream, this.loader, imageData, bl, n6);
        return imageData;
    }

    PaletteData readPalette(int n) {
        byte[] byArray = new byte[n * 3];
        try {
            if (this.inputStream.read(byArray) != byArray.length) {
                SWT.error(40);
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        RGB[] rGBArray = new RGB[n];
        for (int i = 0; i < n; ++i) {
            rGBArray[i] = new RGB(byArray[i * 3] & 0xFF, byArray[i * 3 + 1] & 0xFF, byArray[i * 3 + 2] & 0xFF);
        }
        return new PaletteData(rGBArray);
    }

    @Override
    void unloadIntoByteStream(ImageLoader imageLoader) {
        int n;
        int n2;
        ImageData[] imageDataArray = imageLoader.data;
        int n3 = imageDataArray.length;
        boolean bl = n3 > 1;
        ImageData imageData = imageDataArray[0];
        int n4 = bl ? imageLoader.logicalScreenWidth : imageData.width;
        int n5 = bl ? imageLoader.logicalScreenHeight : imageData.height;
        int n6 = imageLoader.backgroundPixel;
        int n7 = imageData.depth;
        PaletteData paletteData = imageData.palette;
        RGB[] rGBArray = paletteData.getRGBs();
        int n8 = 1;
        if (n7 != 1 && n7 != 4 && n7 != 8) {
            SWT.error(38);
        }
        block10: for (n2 = 0; n2 < n3; ++n2) {
            if (imageDataArray[n2].palette.isDirect) {
                SWT.error(40);
            }
            if (!bl) continue;
            if (imageDataArray[n2].height > n5 || imageDataArray[n2].width > n4 || imageDataArray[n2].depth != n7) {
                SWT.error(40);
            }
            if (n8 != 1) continue;
            RGB[] rGBArray2 = imageDataArray[n2].palette.getRGBs();
            if (rGBArray2.length != rGBArray.length) {
                n8 = 0;
                continue;
            }
            for (n = 0; n < rGBArray.length; ++n) {
                if (rGBArray2[n].red == rGBArray[n].red && rGBArray2[n].green == rGBArray[n].green && rGBArray2[n].blue == rGBArray[n].blue) continue;
                n8 = 0;
                continue block10;
            }
        }
        try {
            this.outputStream.write(GIF89a);
            n2 = n8 * 128 + (n7 - 1) * 16 + n7 - 1;
            this.outputStream.writeShort((short)n4);
            this.outputStream.writeShort((short)n5);
            this.outputStream.write(n2);
            this.outputStream.write(n6);
            this.outputStream.write(0);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if (n8 == 1) {
            this.writePalette(paletteData, n7);
        }
        if (bl) {
            int n9 = imageLoader.repeatCount;
            try {
                this.outputStream.write(33);
                this.outputStream.write(255);
                this.outputStream.write(NETSCAPE2_0.length);
                this.outputStream.write(NETSCAPE2_0);
                this.outputStream.write(3);
                this.outputStream.write(1);
                this.outputStream.writeShort((short)n9);
                this.outputStream.write(0);
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
        }
        for (int i = 0; i < n3; ++i) {
            if (bl || imageDataArray[i].transparentPixel != -1) {
                this.writeGraphicsControlBlock(imageDataArray[i]);
            }
            int n10 = imageDataArray[i].x;
            n = imageDataArray[i].y;
            int n11 = imageDataArray[i].width;
            int n12 = imageDataArray[i].height;
            try {
                this.outputStream.write(44);
                byte[] byArray = new byte[]{(byte)(n10 & 0xFF), (byte)(n10 >> 8 & 0xFF), (byte)(n & 0xFF), (byte)(n >> 8 & 0xFF), (byte)(n11 & 0xFF), (byte)(n11 >> 8 & 0xFF), (byte)(n12 & 0xFF), (byte)(n12 >> 8 & 0xFF), (byte)(n8 == 0 ? n7 - 1 | 0x80 : 0)};
                this.outputStream.write(byArray);
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
            if (n8 == 0) {
                this.writePalette(imageDataArray[i].palette, n7);
            }
            try {
                this.outputStream.write(n7);
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
            }
            new LZWCodec().encode(this.outputStream, imageDataArray[i]);
        }
        try {
            this.outputStream.write(59);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }

    void writeGraphicsControlBlock(ImageData imageData) {
        try {
            this.outputStream.write(33);
            this.outputStream.write(249);
            byte[] byArray = new byte[]{0, 0, 0, 0};
            if (imageData.transparentPixel != -1) {
                byArray[0] = 1;
                byArray[3] = (byte)imageData.transparentPixel;
            }
            if (imageData.disposalMethod != 0) {
                byte[] byArray2 = byArray;
                boolean bl = false;
                byArray2[0] = (byte)(byArray2[0] | (byte)((imageData.disposalMethod & 7) << 2));
            }
            if (imageData.delayTime != 0) {
                byArray[1] = (byte)(imageData.delayTime & 0xFF);
                byArray[2] = (byte)(imageData.delayTime >> 8 & 0xFF);
            }
            this.outputStream.write((byte)byArray.length);
            this.outputStream.write(byArray);
            this.outputStream.write(0);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }

    void writePalette(PaletteData paletteData, int n) {
        byte[] byArray = new byte[(1 << n) * 3];
        int n2 = 0;
        for (RGB rGB : paletteData.colors) {
            byArray[n2] = (byte)rGB.red;
            byArray[n2 + 1] = (byte)rGB.green;
            byArray[n2 + 2] = (byte)rGB.blue;
            n2 += 3;
        }
        try {
            this.outputStream.write(byArray);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }
}

