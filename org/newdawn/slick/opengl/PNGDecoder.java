/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.newdawn.slick.opengl.I;

public class PNGDecoder {
    public static Format ALPHA = new Format(1, true, null);
    public static Format LUMINANCE = new Format(1, false, null);
    public static Format LUMINANCE_ALPHA = new Format(2, true, null);
    public static Format RGB = new Format(3, false, null);
    public static Format RGBA = new Format(4, true, null);
    public static Format BGRA = new Format(4, true, null);
    public static Format ABGR = new Format(4, true, null);
    private static final byte[] SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
    private static final int IHDR = 1229472850;
    private static final int PLTE = 1347179589;
    private static final int tRNS = 1951551059;
    private static final int IDAT = 1229209940;
    private static final int IEND = 1229278788;
    private static final byte COLOR_GREYSCALE = 0;
    private static final byte COLOR_TRUECOLOR = 2;
    private static final byte COLOR_INDEXED = 3;
    private static final byte COLOR_GREYALPHA = 4;
    private static final byte COLOR_TRUEALPHA = 6;
    private final InputStream input;
    private final CRC32 crc;
    private final byte[] buffer;
    private int chunkLength;
    private int chunkType;
    private int chunkRemaining;
    private int width;
    private int height;
    private int bitdepth;
    private int colorType;
    private int bytesPerPixel;
    private byte[] palette;
    private byte[] paletteA;
    private byte[] transPixel;

    /*
     * Exception decompiling
     */
    public PNGDecoder(InputStream var1) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl24 : IF_ICMPGE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean hasAlpha() {
        return this.colorType == 6 || this.paletteA != null || this.transPixel != null;
    }

    public boolean isRGB() {
        return this.colorType == 6 || this.colorType == 2 || this.colorType == 3;
    }

    public Format decideTextureFormat(Format format) {
        switch (this.colorType) {
            case 2: {
                if (format == ABGR || format == RGBA || format == BGRA || format == RGB) {
                    return format;
                }
                return RGB;
            }
            case 6: {
                if (format == ABGR || format == RGBA || format == BGRA || format == RGB) {
                    return format;
                }
                return RGBA;
            }
            case 0: {
                if (format == LUMINANCE || format == ALPHA) {
                    return format;
                }
                return LUMINANCE;
            }
            case 4: {
                return LUMINANCE_ALPHA;
            }
            case 3: {
                if (format == ABGR || format == RGBA || format == BGRA) {
                    return format;
                }
                return RGBA;
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void decode(ByteBuffer byteBuffer, int n, Format format) throws IOException {
        int n2 = byteBuffer.position();
        int n3 = (this.width * this.bitdepth + 7) / 8 * this.bytesPerPixel;
        byte[] byArray = new byte[n3 + 1];
        byte[] byArray2 = new byte[n3 + 1];
        byte[] byArray3 = this.bitdepth < 8 ? new byte[this.width + 1] : null;
        Inflater inflater = new Inflater();
        for (int i = 0; i < this.height; ++i) {
            this.readChunkUnzip(inflater, byArray, 0, byArray.length);
            this.unfilter(byArray, byArray2);
            byteBuffer.position(n2 + i * n);
            switch (this.colorType) {
                case 2: {
                    if (format == ABGR) {
                        this.copyRGBtoABGR(byteBuffer, byArray);
                        break;
                    }
                    if (format == RGBA) {
                        this.copyRGBtoRGBA(byteBuffer, byArray);
                        break;
                    }
                    if (format == BGRA) {
                        this.copyRGBtoBGRA(byteBuffer, byArray);
                        break;
                    }
                    if (format == RGB) {
                        this.copy(byteBuffer, byArray);
                        break;
                    }
                    throw new UnsupportedOperationException("Unsupported format for this image");
                }
                case 6: {
                    if (format == ABGR) {
                        this.copyRGBAtoABGR(byteBuffer, byArray);
                        break;
                    }
                    if (format == RGBA) {
                        this.copy(byteBuffer, byArray);
                        break;
                    }
                    if (format == BGRA) {
                        this.copyRGBAtoBGRA(byteBuffer, byArray);
                        break;
                    }
                    if (format == RGB) {
                        this.copyRGBAtoRGB(byteBuffer, byArray);
                        break;
                    }
                    throw new UnsupportedOperationException("Unsupported format for this image");
                }
                case 0: {
                    if (format == LUMINANCE || format == ALPHA) {
                        this.copy(byteBuffer, byArray);
                        break;
                    }
                    throw new UnsupportedOperationException("Unsupported format for this image");
                }
                case 4: {
                    if (format == LUMINANCE_ALPHA) {
                        this.copy(byteBuffer, byArray);
                        break;
                    }
                    throw new UnsupportedOperationException("Unsupported format for this image");
                }
                case 3: {
                    switch (this.bitdepth) {
                        case 8: {
                            byArray3 = byArray;
                            break;
                        }
                        case 4: {
                            this.expand4(byArray, byArray3);
                            break;
                        }
                        case 2: {
                            this.expand2(byArray, byArray3);
                            break;
                        }
                        case 1: {
                            this.expand1(byArray, byArray3);
                            break;
                        }
                        default: {
                            throw new UnsupportedOperationException("Unsupported bitdepth for this image");
                        }
                    }
                    if (format == ABGR) {
                        this.copyPALtoABGR(byteBuffer, byArray3);
                        break;
                    }
                    if (format == RGBA) {
                        this.copyPALtoRGBA(byteBuffer, byArray3);
                        break;
                    }
                    if (format == BGRA) {
                        this.copyPALtoBGRA(byteBuffer, byArray3);
                        break;
                    }
                    throw new UnsupportedOperationException("Unsupported format for this image");
                }
                default: {
                    throw new UnsupportedOperationException("Not yet implemented");
                }
            }
            byte[] byArray4 = byArray;
            byArray = byArray2;
            byArray2 = byArray4;
        }
        inflater.end();
    }

    private void copy(ByteBuffer byteBuffer, byte[] byArray) {
        byteBuffer.put(byArray, 1, byArray.length - 1);
    }

    private void copyRGBtoABGR(ByteBuffer byteBuffer, byte[] byArray) {
        if (this.transPixel != null) {
            byte by = this.transPixel[1];
            byte by2 = this.transPixel[3];
            byte by3 = this.transPixel[5];
            int n = byArray.length;
            for (int i = 1; i < n; i += 3) {
                byte by4 = byArray[i];
                byte by5 = byArray[i + 1];
                byte by6 = byArray[i + 2];
                byte by7 = -1;
                if (by4 == by && by5 == by2 && by6 == by3) {
                    by7 = 0;
                }
                byteBuffer.put(by7).put(by6).put(by5).put(by4);
            }
        } else {
            int n = byArray.length;
            for (int i = 1; i < n; i += 3) {
                byteBuffer.put((byte)-1).put(byArray[i + 2]).put(byArray[i + 1]).put(byArray[i]);
            }
        }
    }

    private void copyRGBtoRGBA(ByteBuffer byteBuffer, byte[] byArray) {
        if (this.transPixel != null) {
            byte by = this.transPixel[1];
            byte by2 = this.transPixel[3];
            byte by3 = this.transPixel[5];
            int n = byArray.length;
            for (int i = 1; i < n; i += 3) {
                byte by4 = byArray[i];
                byte by5 = byArray[i + 1];
                byte by6 = byArray[i + 2];
                byte by7 = -1;
                if (by4 == by && by5 == by2 && by6 == by3) {
                    by7 = 0;
                }
                byteBuffer.put(by4).put(by5).put(by6).put(by7);
            }
        } else {
            int n = byArray.length;
            for (int i = 1; i < n; i += 3) {
                byteBuffer.put(byArray[i]).put(byArray[i + 1]).put(byArray[i + 2]).put((byte)-1);
            }
        }
    }

    private void copyRGBtoBGRA(ByteBuffer byteBuffer, byte[] byArray) {
        if (this.transPixel != null) {
            byte by = this.transPixel[1];
            byte by2 = this.transPixel[3];
            byte by3 = this.transPixel[5];
            int n = byArray.length;
            for (int i = 1; i < n; i += 3) {
                byte by4 = byArray[i];
                byte by5 = byArray[i + 1];
                byte by6 = byArray[i + 2];
                byte by7 = -1;
                if (by4 == by && by5 == by2 && by6 == by3) {
                    by7 = 0;
                }
                byteBuffer.put(by6).put(by5).put(by4).put(by7);
            }
        } else {
            int n = byArray.length;
            for (int i = 1; i < n; i += 3) {
                byteBuffer.put(byArray[i + 2]).put(byArray[i + 1]).put(byArray[i]).put((byte)-1);
            }
        }
    }

    private void copyRGBAtoABGR(ByteBuffer byteBuffer, byte[] byArray) {
        int n = byArray.length;
        for (int i = 1; i < n; i += 4) {
            byteBuffer.put(byArray[i + 3]).put(byArray[i + 2]).put(byArray[i + 1]).put(byArray[i]);
        }
    }

    private void copyRGBAtoBGRA(ByteBuffer byteBuffer, byte[] byArray) {
        int n = byArray.length;
        for (int i = 1; i < n; i += 4) {
            byteBuffer.put(byArray[i + 2]).put(byArray[i + 1]).put(byArray[i + 0]).put(byArray[i + 3]);
        }
    }

    private void copyRGBAtoRGB(ByteBuffer byteBuffer, byte[] byArray) {
        int n = byArray.length;
        for (int i = 1; i < n; i += 4) {
            byteBuffer.put(byArray[i]).put(byArray[i + 1]).put(byArray[i + 2]);
        }
    }

    private void copyPALtoABGR(ByteBuffer byteBuffer, byte[] byArray) {
        if (this.paletteA != null) {
            int n = byArray.length;
            for (int i = 1; i < n; ++i) {
                int n2 = byArray[i] & 0xFF;
                byte by = this.palette[n2 * 3 + 0];
                byte by2 = this.palette[n2 * 3 + 1];
                byte by3 = this.palette[n2 * 3 + 2];
                byte by4 = this.paletteA[n2];
                byteBuffer.put(by4).put(by3).put(by2).put(by);
            }
        } else {
            int n = byArray.length;
            for (int i = 1; i < n; ++i) {
                int n3 = byArray[i] & 0xFF;
                byte by = this.palette[n3 * 3 + 0];
                byte by5 = this.palette[n3 * 3 + 1];
                byte by6 = this.palette[n3 * 3 + 2];
                byte by7 = -1;
                byteBuffer.put(by7).put(by6).put(by5).put(by);
            }
        }
    }

    private void copyPALtoRGBA(ByteBuffer byteBuffer, byte[] byArray) {
        if (this.paletteA != null) {
            int n = byArray.length;
            for (int i = 1; i < n; ++i) {
                int n2 = byArray[i] & 0xFF;
                byte by = this.palette[n2 * 3 + 0];
                byte by2 = this.palette[n2 * 3 + 1];
                byte by3 = this.palette[n2 * 3 + 2];
                byte by4 = this.paletteA[n2];
                byteBuffer.put(by).put(by2).put(by3).put(by4);
            }
        } else {
            int n = byArray.length;
            for (int i = 1; i < n; ++i) {
                int n3 = byArray[i] & 0xFF;
                byte by = this.palette[n3 * 3 + 0];
                byte by5 = this.palette[n3 * 3 + 1];
                byte by6 = this.palette[n3 * 3 + 2];
                byte by7 = -1;
                byteBuffer.put(by).put(by5).put(by6).put(by7);
            }
        }
    }

    private void copyPALtoBGRA(ByteBuffer byteBuffer, byte[] byArray) {
        if (this.paletteA != null) {
            int n = byArray.length;
            for (int i = 1; i < n; ++i) {
                int n2 = byArray[i] & 0xFF;
                byte by = this.palette[n2 * 3 + 0];
                byte by2 = this.palette[n2 * 3 + 1];
                byte by3 = this.palette[n2 * 3 + 2];
                byte by4 = this.paletteA[n2];
                byteBuffer.put(by3).put(by2).put(by).put(by4);
            }
        } else {
            int n = byArray.length;
            for (int i = 1; i < n; ++i) {
                int n3 = byArray[i] & 0xFF;
                byte by = this.palette[n3 * 3 + 0];
                byte by5 = this.palette[n3 * 3 + 1];
                byte by6 = this.palette[n3 * 3 + 2];
                byte by7 = -1;
                byteBuffer.put(by6).put(by5).put(by).put(by7);
            }
        }
    }

    private void expand4(byte[] byArray, byte[] byArray2) {
        int n = byArray2.length;
        for (int i = 1; i < n; i += 2) {
            int n2 = byArray[1 + (i >> 1)] & 0xFF;
            switch (n - i) {
                default: {
                    byArray2[i + 1] = (byte)(n2 & 0xF);
                }
                case 1: 
            }
            byArray2[i] = (byte)(n2 >> 4);
        }
    }

    private void expand2(byte[] byArray, byte[] byArray2) {
        int n = byArray2.length;
        for (int i = 1; i < n; i += 4) {
            int n2 = byArray[1 + (i >> 2)] & 0xFF;
            switch (n - i) {
                default: {
                    byArray2[i + 3] = (byte)(n2 & 3);
                }
                case 3: {
                    byArray2[i + 2] = (byte)(n2 >> 2 & 3);
                }
                case 2: {
                    byArray2[i + 1] = (byte)(n2 >> 4 & 3);
                }
                case 1: 
            }
            byArray2[i] = (byte)(n2 >> 6);
        }
    }

    private void expand1(byte[] byArray, byte[] byArray2) {
        int n = byArray2.length;
        for (int i = 1; i < n; i += 8) {
            int n2 = byArray[1 + (i >> 3)] & 0xFF;
            switch (n - i) {
                default: {
                    byArray2[i + 7] = (byte)(n2 & 1);
                }
                case 7: {
                    byArray2[i + 6] = (byte)(n2 >> 1 & 1);
                }
                case 6: {
                    byArray2[i + 5] = (byte)(n2 >> 2 & 1);
                }
                case 5: {
                    byArray2[i + 4] = (byte)(n2 >> 3 & 1);
                }
                case 4: {
                    byArray2[i + 3] = (byte)(n2 >> 4 & 1);
                }
                case 3: {
                    byArray2[i + 2] = (byte)(n2 >> 5 & 1);
                }
                case 2: {
                    byArray2[i + 1] = (byte)(n2 >> 6 & 1);
                }
                case 1: 
            }
            byArray2[i] = (byte)(n2 >> 7);
        }
    }

    private void unfilter(byte[] byArray, byte[] byArray2) throws IOException {
        switch (byArray[0]) {
            case 0: {
                break;
            }
            case 1: {
                this.unfilterSub(byArray);
                break;
            }
            case 2: {
                this.unfilterUp(byArray, byArray2);
                break;
            }
            case 3: {
                this.unfilterAverage(byArray, byArray2);
                break;
            }
            case 4: {
                this.unfilterPaeth(byArray, byArray2);
                break;
            }
            default: {
                throw new IOException("invalide filter type in scanline: " + byArray[0]);
            }
        }
    }

    private void unfilterSub(byte[] byArray) {
        int n = this.bytesPerPixel;
        int n2 = byArray.length;
        for (int i = n + 1; i < n2; ++i) {
            int n3 = i;
            byArray[n3] = (byte)(byArray[n3] + byArray[i - n]);
        }
    }

    private void unfilterUp(byte[] byArray, byte[] byArray2) {
        int n = this.bytesPerPixel;
        int n2 = byArray.length;
        for (int i = 1; i < n2; ++i) {
            int n3 = i;
            byArray[n3] = (byte)(byArray[n3] + byArray2[i]);
        }
    }

    private void unfilterAverage(byte[] byArray, byte[] byArray2) {
        int n;
        int n2 = this.bytesPerPixel;
        for (n = 1; n <= n2; ++n) {
            int n3 = n;
            byArray[n3] = (byte)(byArray[n3] + (byte)((byArray2[n] & 0xFF) >>> 1));
        }
        int n4 = byArray.length;
        while (n < n4) {
            int n5 = n;
            byArray[n5] = (byte)(byArray[n5] + (byte)((byArray2[n] & 0xFF) + (byArray[n - n2] & 0xFF) >>> 1));
            ++n;
        }
    }

    private void unfilterPaeth(byte[] byArray, byte[] byArray2) {
        int n;
        int n2 = this.bytesPerPixel;
        for (n = 1; n <= n2; ++n) {
            int n3 = n;
            byArray[n3] = (byte)(byArray[n3] + byArray2[n]);
        }
        int n4 = byArray.length;
        while (n < n4) {
            int n5;
            int n6;
            int n7 = byArray[n - n2] & 0xFF;
            int n8 = byArray2[n] & 0xFF;
            int n9 = byArray2[n - n2] & 0xFF;
            int n10 = n7 + n8 - n9;
            int n11 = n10 - n7;
            if (n11 < 0) {
                n11 = -n11;
            }
            if ((n6 = n10 - n8) < 0) {
                n6 = -n6;
            }
            if ((n5 = n10 - n9) < 0) {
                n5 = -n5;
            }
            if (n11 <= n6 && n11 <= n5) {
                n9 = n7;
            } else if (n6 <= n5) {
                n9 = n8;
            }
            int n12 = n++;
            byArray[n12] = (byte)(byArray[n12] + (byte)n9);
        }
    }

    private void readIHDR() throws IOException {
        this.checkChunkLength(13);
        this.readChunk(this.buffer, 0, 13);
        this.width = this.readInt(this.buffer, 0);
        this.height = this.readInt(this.buffer, 4);
        this.bitdepth = this.buffer[8] & 0xFF;
        this.colorType = this.buffer[9] & 0xFF;
        block0 : switch (this.colorType) {
            case 0: {
                if (this.bitdepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitdepth);
                }
                this.bytesPerPixel = 1;
                break;
            }
            case 4: {
                if (this.bitdepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitdepth);
                }
                this.bytesPerPixel = 2;
                break;
            }
            case 2: {
                if (this.bitdepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitdepth);
                }
                this.bytesPerPixel = 3;
                break;
            }
            case 6: {
                if (this.bitdepth != 8) {
                    throw new IOException("Unsupported bit depth: " + this.bitdepth);
                }
                this.bytesPerPixel = 4;
                break;
            }
            case 3: {
                switch (this.bitdepth) {
                    case 1: 
                    case 2: 
                    case 4: 
                    case 8: {
                        this.bytesPerPixel = 1;
                        break block0;
                    }
                }
                throw new IOException("Unsupported bit depth: " + this.bitdepth);
            }
            default: {
                throw new IOException("unsupported color format: " + this.colorType);
            }
        }
        if (this.buffer[10] != 0) {
            throw new IOException("unsupported compression method");
        }
        if (this.buffer[11] != 0) {
            throw new IOException("unsupported filtering method");
        }
        if (this.buffer[12] != 0) {
            throw new IOException("unsupported interlace method");
        }
    }

    private void readPLTE() throws IOException {
        int n = this.chunkLength / 3;
        if (n < 1 || n > 256 || this.chunkLength % 3 != 0) {
            throw new IOException("PLTE chunk has wrong length");
        }
        this.palette = new byte[n * 3];
        this.readChunk(this.palette, 0, this.palette.length);
    }

    private void readtRNS() throws IOException {
        switch (this.colorType) {
            case 0: {
                this.checkChunkLength(2);
                this.transPixel = new byte[2];
                this.readChunk(this.transPixel, 0, 2);
                break;
            }
            case 2: {
                this.checkChunkLength(6);
                this.transPixel = new byte[6];
                this.readChunk(this.transPixel, 0, 6);
                break;
            }
            case 3: {
                if (this.palette == null) {
                    throw new IOException("tRNS chunk without PLTE chunk");
                }
                this.paletteA = new byte[this.palette.length / 3];
                Arrays.fill(this.paletteA, (byte)-1);
                this.readChunk(this.paletteA, 0, this.paletteA.length);
                break;
            }
        }
    }

    private void closeChunk() throws IOException {
        if (this.chunkRemaining > 0) {
            this.skip(this.chunkRemaining + 4);
        } else {
            this.readFully(this.buffer, 0, 4);
            int n = this.readInt(this.buffer, 0);
            int n2 = (int)this.crc.getValue();
            if (n2 != n) {
                throw new IOException("Invalid CRC");
            }
        }
        this.chunkRemaining = 0;
        this.chunkLength = 0;
        this.chunkType = 0;
    }

    private void openChunk() throws IOException {
        this.readFully(this.buffer, 0, 8);
        this.chunkLength = this.readInt(this.buffer, 0);
        this.chunkType = this.readInt(this.buffer, 4);
        this.chunkRemaining = this.chunkLength;
        this.crc.reset();
        this.crc.update(this.buffer, 4, 4);
    }

    private void openChunk(int n) throws IOException {
        this.openChunk();
        if (this.chunkType != n) {
            throw new IOException("Expected chunk: " + Integer.toHexString(n));
        }
    }

    private void checkChunkLength(int n) throws IOException {
        if (this.chunkLength != n) {
            throw new IOException("Chunk has wrong size");
        }
    }

    private int readChunk(byte[] byArray, int n, int n2) throws IOException {
        if (n2 > this.chunkRemaining) {
            n2 = this.chunkRemaining;
        }
        this.readFully(byArray, n, n2);
        this.crc.update(byArray, n, n2);
        this.chunkRemaining -= n2;
        return n2;
    }

    private void refillInflater(Inflater inflater) throws IOException {
        while (this.chunkRemaining == 0) {
            this.closeChunk();
            this.openChunk(1229209940);
        }
        int n = this.readChunk(this.buffer, 0, this.buffer.length);
        inflater.setInput(this.buffer, 0, n);
    }

    private void readChunkUnzip(Inflater inflater, byte[] byArray, int n, int n2) throws IOException {
        try {
            do {
                int n3;
                if ((n3 = inflater.inflate(byArray, n, n2)) <= 0) {
                    if (inflater.finished()) {
                        throw new EOFException();
                    }
                    if (inflater.needsInput()) {
                        this.refillInflater(inflater);
                        continue;
                    }
                    throw new IOException("Can't inflate " + n2 + " bytes");
                }
                n += n3;
                n2 -= n3;
            } while (n2 > 0);
        }
        catch (DataFormatException dataFormatException) {
            throw (IOException)new IOException("inflate error").initCause(dataFormatException);
        }
    }

    private void readFully(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        do {
            if ((n3 = this.input.read(byArray, n, n2)) < 0) {
                throw new EOFException();
            }
            n += n3;
        } while ((n2 -= n3) > 0);
    }

    private int readInt(byte[] byArray, int n) {
        return byArray[n] << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }

    private void skip(long l2) throws IOException {
        while (l2 > 0L) {
            long l3 = this.input.skip(l2);
            if (l3 < 0L) {
                throw new EOFException();
            }
            l2 -= l3;
        }
    }

    public static class Format {
        final int numComponents;
        final boolean hasAlpha;

        private Format(int n, boolean bl) {
            this.numComponents = n;
            this.hasAlpha = bl;
        }

        public int getNumComponents() {
            return this.numComponents;
        }

        public boolean isHasAlpha() {
            return this.hasAlpha;
        }

        Format(int n, boolean bl, I i) {
            this(n, bl);
        }
    }
}

