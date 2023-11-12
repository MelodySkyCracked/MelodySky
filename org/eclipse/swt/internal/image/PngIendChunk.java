/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngFileReadState;
import org.eclipse.swt.internal.image.PngIhdrChunk;

class PngIendChunk
extends PngChunk {
    PngIendChunk() {
        super(0);
        this.setType(TYPE_IEND);
        this.setCRC(this.computeCRC());
    }

    PngIendChunk(byte[] byArray) {
        super(byArray);
    }

    @Override
    int getChunkType() {
        return 3;
    }

    @Override
    void validate(PngFileReadState pngFileReadState, PngIhdrChunk pngIhdrChunk) {
        if (!pngFileReadState.readIHDR || pngIhdrChunk.getMustHavePalette() && !pngFileReadState.readPLTE || !pngFileReadState.readIDAT || pngFileReadState.readIEND) {
            SWT.error(40);
        } else {
            pngFileReadState.readIEND = true;
        }
        super.validate(pngFileReadState, pngIhdrChunk);
        if (this.getLength() > 0) {
            SWT.error(40);
        }
    }
}

