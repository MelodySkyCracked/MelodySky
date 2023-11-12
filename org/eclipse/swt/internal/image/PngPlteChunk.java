/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngFileReadState;
import org.eclipse.swt.internal.image.PngIhdrChunk;

class PngPlteChunk
extends PngChunk {
    int paletteSize;

    PngPlteChunk(PaletteData paletteData) {
        super(paletteData.getRGBs().length * 3);
        this.paletteSize = this.length / 3;
        this.setType(TYPE_PLTE);
        this.setPaletteData(paletteData);
        this.setCRC(this.computeCRC());
    }

    PngPlteChunk(byte[] byArray) {
        super(byArray);
        this.paletteSize = this.length / 3;
    }

    @Override
    int getChunkType() {
        return 1;
    }

    int getPaletteSize() {
        return this.paletteSize;
    }

    PaletteData getPaletteData() {
        RGB[] rGBArray = new RGB[this.paletteSize];
        for (int i = 0; i < rGBArray.length; ++i) {
            int n = 8 + i * 3;
            int n2 = this.reference[n] & 0xFF;
            int n3 = this.reference[n + 1] & 0xFF;
            int n4 = this.reference[n + 2] & 0xFF;
            rGBArray[i] = new RGB(n2, n3, n4);
        }
        return new PaletteData(rGBArray);
    }

    void setPaletteData(PaletteData paletteData) {
        RGB[] rGBArray = paletteData.getRGBs();
        for (int i = 0; i < rGBArray.length; ++i) {
            int n = 8 + i * 3;
            this.reference[n] = (byte)rGBArray[i].red;
            this.reference[n + 1] = (byte)rGBArray[i].green;
            this.reference[n + 2] = (byte)rGBArray[i].blue;
        }
    }

    @Override
    void validate(PngFileReadState pngFileReadState, PngIhdrChunk pngIhdrChunk) {
        if (!pngFileReadState.readIHDR || pngFileReadState.readPLTE || pngFileReadState.readTRNS || pngFileReadState.readIDAT || pngFileReadState.readIEND) {
            SWT.error(40);
        } else {
            pngFileReadState.readPLTE = true;
        }
        super.validate(pngFileReadState, pngIhdrChunk);
        if (this.getLength() % 3 != 0) {
            SWT.error(40);
        }
        if (1 << pngIhdrChunk.getBitDepth() < this.paletteSize) {
            SWT.error(40);
        }
        if (256 < this.paletteSize) {
            SWT.error(40);
        }
    }

    @Override
    void contributeToString(StringBuilder stringBuilder) {
        stringBuilder.append("\n\tPalette size:");
        stringBuilder.append(this.paletteSize);
    }
}

