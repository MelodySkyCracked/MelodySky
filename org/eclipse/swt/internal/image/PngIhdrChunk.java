/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngFileReadState;

class PngIhdrChunk
extends PngChunk {
    static final int IHDR_DATA_LENGTH = 13;
    static final int WIDTH_DATA_OFFSET = 8;
    static final int HEIGHT_DATA_OFFSET = 12;
    static final int BIT_DEPTH_OFFSET = 16;
    static final int COLOR_TYPE_OFFSET = 17;
    static final int COMPRESSION_METHOD_OFFSET = 18;
    static final int FILTER_METHOD_OFFSET = 19;
    static final int INTERLACE_METHOD_OFFSET = 20;
    static final byte COLOR_TYPE_GRAYSCALE = 0;
    static final byte COLOR_TYPE_RGB = 2;
    static final byte COLOR_TYPE_PALETTE = 3;
    static final byte COLOR_TYPE_GRAYSCALE_WITH_ALPHA = 4;
    static final byte COLOR_TYPE_RGB_WITH_ALPHA = 6;
    static final int INTERLACE_METHOD_NONE = 0;
    static final int INTERLACE_METHOD_ADAM7 = 1;
    static final int FILTER_NONE = 0;
    static final int FILTER_SUB = 1;
    static final int FILTER_UP = 2;
    static final int FILTER_AVERAGE = 3;
    static final int FILTER_PAETH = 4;
    static final byte[] ValidBitDepths = new byte[]{1, 2, 4, 8, 16};
    static final byte[] ValidColorTypes = new byte[]{0, 2, 3, 4, 6};
    int width;
    int height;
    byte bitDepth;
    byte colorType;
    byte compressionMethod;
    byte filterMethod;
    byte interlaceMethod;

    PngIhdrChunk(int n, int n2, byte by, byte by2, byte by3, byte by4, byte by5) {
        super(13);
        this.setType(TYPE_IHDR);
        this.setWidth(n);
        this.setHeight(n2);
        this.setBitDepth(by);
        this.setColorType(by2);
        this.setCompressionMethod(by3);
        this.setFilterMethod(by4);
        this.setInterlaceMethod(by5);
        this.setCRC(this.computeCRC());
    }

    PngIhdrChunk(byte[] byArray) {
        super(byArray);
        if (byArray.length <= 13) {
            SWT.error(40);
        }
        this.width = this.getInt32(8);
        this.height = this.getInt32(12);
        this.bitDepth = byArray[16];
        this.colorType = byArray[17];
        this.compressionMethod = byArray[18];
        this.filterMethod = byArray[19];
        this.interlaceMethod = byArray[20];
    }

    @Override
    int getChunkType() {
        return 0;
    }

    int getWidth() {
        return this.width;
    }

    void setWidth(int n) {
        this.setInt32(8, n);
        this.width = n;
    }

    int getHeight() {
        return this.height;
    }

    void setHeight(int n) {
        this.setInt32(12, n);
        this.height = n;
    }

    byte getBitDepth() {
        return this.bitDepth;
    }

    void setBitDepth(byte by) {
        this.reference[16] = by;
        this.bitDepth = by;
    }

    byte getColorType() {
        return this.colorType;
    }

    void setColorType(byte by) {
        this.reference[17] = by;
        this.colorType = by;
    }

    byte getCompressionMethod() {
        return this.compressionMethod;
    }

    void setCompressionMethod(byte by) {
        this.reference[18] = by;
        this.compressionMethod = by;
    }

    byte getFilterMethod() {
        return this.filterMethod;
    }

    void setFilterMethod(byte by) {
        this.reference[19] = by;
        this.filterMethod = by;
    }

    byte getInterlaceMethod() {
        return this.interlaceMethod;
    }

    void setInterlaceMethod(byte by) {
        this.reference[20] = by;
        this.interlaceMethod = by;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    void validate(PngFileReadState pngFileReadState, PngIhdrChunk pngIhdrChunk) {
        void var7_11;
        if (pngFileReadState.readIHDR || pngFileReadState.readPLTE || pngFileReadState.readIDAT || pngFileReadState.readIEND) {
            SWT.error(40);
        } else {
            pngFileReadState.readIHDR = true;
        }
        super.validate(pngFileReadState, pngIhdrChunk);
        if (this.length != 13) {
            SWT.error(40);
        }
        if (this.compressionMethod != 0) {
            SWT.error(40);
        }
        if (this.interlaceMethod != 0 && this.interlaceMethod != 1) {
            SWT.error(40);
        }
        boolean bl = false;
        for (byte n : ValidColorTypes) {
            if (n != this.colorType) continue;
            bl = true;
            break;
        }
        if (!bl) {
            SWT.error(40);
        }
        boolean bl2 = false;
        byte[] byArray = ValidBitDepths;
        int n = byArray.length;
        boolean bl3 = false;
        while (var7_11 < n) {
            byte by = byArray[var7_11];
            if (by == this.bitDepth) {
                bl2 = true;
                break;
            }
            ++var7_11;
        }
        if (!bl2) {
            SWT.error(40);
        }
        if ((this.colorType == 2 || this.colorType == 6 || this.colorType == 4) && this.bitDepth < 8) {
            SWT.error(40);
        }
        if (this.colorType == 3 && this.bitDepth > 8) {
            SWT.error(40);
        }
    }

    String getColorTypeString() {
        switch (this.colorType) {
            case 0: {
                return "Grayscale";
            }
            case 2: {
                return "RGB";
            }
            case 3: {
                return "Palette";
            }
            case 4: {
                return "Grayscale with Alpha";
            }
            case 6: {
                return "RGB with Alpha";
            }
        }
        return "Unknown - " + this.colorType;
    }

    String getFilterMethodString() {
        switch (this.filterMethod) {
            case 0: {
                return "None";
            }
            case 1: {
                return "Sub";
            }
            case 2: {
                return "Up";
            }
            case 3: {
                return "Average";
            }
            case 4: {
                return "Paeth";
            }
        }
        return "Unknown";
    }

    String getInterlaceMethodString() {
        switch (this.interlaceMethod) {
            case 0: {
                return "Not Interlaced";
            }
            case 1: {
                return "Interlaced - ADAM7";
            }
        }
        return "Unknown";
    }

    @Override
    void contributeToString(StringBuilder stringBuilder) {
        stringBuilder.append("\n\tWidth: ");
        stringBuilder.append(this.width);
        stringBuilder.append("\n\tHeight: ");
        stringBuilder.append(this.height);
        stringBuilder.append("\n\tBit Depth: ");
        stringBuilder.append(this.bitDepth);
        stringBuilder.append("\n\tColor Type: ");
        stringBuilder.append(this.getColorTypeString());
        stringBuilder.append("\n\tCompression Method: ");
        stringBuilder.append(this.compressionMethod);
        stringBuilder.append("\n\tFilter Method: ");
        stringBuilder.append(this.getFilterMethodString());
        stringBuilder.append("\n\tInterlace Method: ");
        stringBuilder.append(this.getInterlaceMethodString());
    }

    boolean getMustHavePalette() {
        return this.colorType == 3;
    }

    boolean getCanHavePalette() {
        return this.colorType != 0 && this.colorType != 4;
    }

    int getBitsPerPixel() {
        switch (this.colorType) {
            case 6: {
                return 4 * this.bitDepth;
            }
            case 2: {
                return 3 * this.bitDepth;
            }
            case 4: {
                return 2 * this.bitDepth;
            }
            case 0: 
            case 3: {
                return this.bitDepth;
            }
        }
        SWT.error(40);
        return 0;
    }

    int getSwtBitsPerPixel() {
        switch (this.colorType) {
            case 2: 
            case 4: 
            case 6: {
                return 24;
            }
            case 0: 
            case 3: {
                return Math.min(this.bitDepth, 8);
            }
        }
        SWT.error(40);
        return 0;
    }

    int getFilterByteOffset() {
        if (this.bitDepth < 8) {
            return 1;
        }
        return this.getBitsPerPixel() / 8;
    }

    boolean usesDirectColor() {
        switch (this.colorType) {
            case 0: 
            case 2: 
            case 4: 
            case 6: {
                return true;
            }
        }
        return false;
    }

    PaletteData createGrayscalePalette() {
        int n = Math.min(this.bitDepth, 8);
        int n2 = (1 << n) - 1;
        int n3 = 255 / n2;
        int n4 = 0;
        RGB[] rGBArray = new RGB[n2 + 1];
        for (int i = 0; i <= n2; ++i) {
            rGBArray[i] = new RGB(n4, n4, n4);
            n4 += n3;
        }
        return new PaletteData(rGBArray);
    }

    PaletteData getPaletteData() {
        switch (this.colorType) {
            case 0: {
                return this.createGrayscalePalette();
            }
            case 2: 
            case 4: 
            case 6: {
                return new PaletteData(0xFF0000, 65280, 255);
            }
        }
        return null;
    }
}

