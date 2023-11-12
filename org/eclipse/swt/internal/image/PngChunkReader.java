/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.PngChunk;
import org.eclipse.swt.internal.image.PngFileReadState;
import org.eclipse.swt.internal.image.PngIhdrChunk;
import org.eclipse.swt.internal.image.PngPlteChunk;
import org.eclipse.swt.internal.image.PngTrnsChunk;

public class PngChunkReader {
    LEDataInputStream inputStream;
    PngFileReadState readState;
    PngIhdrChunk headerChunk;
    PngPlteChunk paletteChunk;

    PngChunkReader(LEDataInputStream lEDataInputStream) {
        this.inputStream = lEDataInputStream;
        this.readState = new PngFileReadState();
        this.headerChunk = null;
    }

    PngIhdrChunk getIhdrChunk() {
        if (this.headerChunk == null) {
            try {
                PngChunk pngChunk = PngChunk.readNextFromStream(this.inputStream);
                if (pngChunk == null) {
                    SWT.error(40);
                }
                this.headerChunk = (PngIhdrChunk)pngChunk;
                this.headerChunk.validate(this.readState, null);
            }
            catch (ClassCastException classCastException) {
                SWT.error(40);
            }
        }
        return this.headerChunk;
    }

    PngChunk readNextChunk() {
        if (this.headerChunk == null) {
            return this.getIhdrChunk();
        }
        PngChunk pngChunk = PngChunk.readNextFromStream(this.inputStream);
        if (pngChunk == null) {
            SWT.error(40);
        }
        switch (pngChunk.getChunkType()) {
            case 5: {
                ((PngTrnsChunk)pngChunk).validate(this.readState, this.headerChunk, this.paletteChunk);
                break;
            }
            case 1: {
                pngChunk.validate(this.readState, this.headerChunk);
                this.paletteChunk = (PngPlteChunk)pngChunk;
                break;
            }
            default: {
                pngChunk.validate(this.readState, this.headerChunk);
            }
        }
        if (this.readState.readIDAT && pngChunk.getChunkType() != 2) {
            this.readState.readPixelData = true;
        }
        return pngChunk;
    }

    boolean readPixelData() {
        return this.readState.readPixelData;
    }

    boolean hasMoreChunks() {
        return !this.readState.readIEND;
    }
}

