/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.PNGFileFormat;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngFileReadState;
import org.eclipse.swt.internal.image.PngIhdrChunk;
import org.eclipse.swt.internal.image.PngPlteChunk;

public class PngTrnsChunk
extends PngChunk {
    static final int TRANSPARENCY_TYPE_PIXEL = 0;
    static final int TRANSPARENCY_TYPE_ALPHAS = 1;
    static final int RGB_DATA_LENGTH = 6;

    PngTrnsChunk(RGB rGB) {
        super(6);
        this.setType(TYPE_tRNS);
        this.setInt16(8, rGB.red);
        this.setInt16(10, rGB.green);
        this.setInt16(12, rGB.blue);
        this.setCRC(this.computeCRC());
    }

    PngTrnsChunk(byte[] byArray) {
        super(byArray);
    }

    @Override
    int getChunkType() {
        return 5;
    }

    void validateLength(PngIhdrChunk pngIhdrChunk, PngPlteChunk pngPlteChunk) {
        boolean bl = false;
        switch (pngIhdrChunk.getColorType()) {
            case 2: {
                bl = this.getLength() == 6;
                break;
            }
            case 3: {
                bl = this.getLength() <= pngPlteChunk.getLength();
                break;
            }
            case 0: {
                bl = this.getLength() == 2;
                break;
            }
            default: {
                bl = false;
            }
        }
        if (!bl) {
            SWT.error(40);
        }
    }

    void validate(PngFileReadState pngFileReadState, PngIhdrChunk pngIhdrChunk, PngPlteChunk pngPlteChunk) {
        if (!pngFileReadState.readIHDR || pngIhdrChunk.getMustHavePalette() && !pngFileReadState.readPLTE || pngFileReadState.readIDAT || pngFileReadState.readIEND) {
            SWT.error(40);
        } else {
            pngFileReadState.readTRNS = true;
        }
        this.validateLength(pngIhdrChunk, pngPlteChunk);
        super.validate(pngFileReadState, pngIhdrChunk);
    }

    int getTransparencyType(PngIhdrChunk pngIhdrChunk) {
        if (pngIhdrChunk.getColorType() == 3) {
            return 1;
        }
        return 0;
    }

    int getSwtTransparentPixel(PngIhdrChunk pngIhdrChunk) {
        switch (pngIhdrChunk.getColorType()) {
            case 0: {
                int n = ((this.reference[8] & 0xFF) << 8) + (this.reference[9] & 0xFF);
                if (pngIhdrChunk.getBitDepth() > 8) {
                    return PNGFileFormat.compress16BitDepthTo8BitDepth(n);
                }
                return n & 0xFF;
            }
            case 2: {
                int n = (this.reference[8] & 0xFF) << 8 | this.reference[9] & 0xFF;
                int n2 = (this.reference[10] & 0xFF) << 8 | this.reference[11] & 0xFF;
                int n3 = (this.reference[12] & 0xFF) << 8 | this.reference[13] & 0xFF;
                if (pngIhdrChunk.getBitDepth() > 8) {
                    n = PNGFileFormat.compress16BitDepthTo8BitDepth(n);
                    n2 = PNGFileFormat.compress16BitDepthTo8BitDepth(n2);
                    n3 = PNGFileFormat.compress16BitDepthTo8BitDepth(n3);
                }
                return n << 16 | n2 << 8 | n3;
            }
        }
        SWT.error(40);
        return -1;
    }

    byte[] getAlphaValues(PngIhdrChunk pngIhdrChunk, PngPlteChunk pngPlteChunk) {
        if (pngIhdrChunk.getColorType() != 3) {
            SWT.error(40);
        }
        byte[] byArray = new byte[pngPlteChunk.getPaletteSize()];
        int n = this.getLength();
        int n2 = 0;
        for (n2 = 0; n2 < n; ++n2) {
            byArray[n2] = this.reference[8 + n2];
        }
        for (int i = n2; i < byArray.length; ++i) {
            byArray[i] = -1;
        }
        return byArray;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

