/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngFileReadState;
import org.eclipse.swt.internal.image.PngIhdrChunk;

class PngIdatChunk
extends PngChunk {
    static final int HEADER_BYTES_LENGTH = 2;
    static final int ADLER_FIELD_LENGTH = 4;
    static final int HEADER_BYTE1_DATA_OFFSET = 8;
    static final int HEADER_BYTE2_DATA_OFFSET = 9;
    static final int ADLER_DATA_OFFSET = 10;

    PngIdatChunk(byte by, byte by2, byte[] byArray, int n) {
        super(byArray.length + 2 + 4);
        this.setType(TYPE_IDAT);
        this.reference[8] = by;
        this.reference[9] = by2;
        System.arraycopy(byArray, 0, this.reference, 8, byArray.length);
        this.setInt32(10, n);
        this.setCRC(this.computeCRC());
    }

    PngIdatChunk(byte[] byArray) {
        super(byArray);
    }

    @Override
    int getChunkType() {
        return 2;
    }

    @Override
    void validate(PngFileReadState pngFileReadState, PngIhdrChunk pngIhdrChunk) {
        if (!pngFileReadState.readIHDR || pngIhdrChunk.getMustHavePalette() && !pngFileReadState.readPLTE || pngFileReadState.readIEND) {
            SWT.error(40);
        } else {
            pngFileReadState.readIDAT = true;
        }
        super.validate(pngFileReadState, pngIhdrChunk);
    }

    byte getDataByteAtOffset(int n) {
        return this.reference[8 + n];
    }
}

