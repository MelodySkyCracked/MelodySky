/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.PNGDecoder;

public class PNGImageData
implements LoadableImageData {
    private int width;
    private int height;
    private int texHeight;
    private int texWidth;
    private PNGDecoder decoder;
    private int bitDepth;
    private ByteBuffer scratch;

    @Override
    public int getDepth() {
        return this.bitDepth;
    }

    @Override
    public ByteBuffer getImageBufferData() {
        return this.scratch;
    }

    @Override
    public int getTexHeight() {
        return this.texHeight;
    }

    @Override
    public int getTexWidth() {
        return this.texWidth;
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, false, null);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream, boolean bl, int[] nArray) throws IOException {
        return this.loadImage(inputStream, bl, false, nArray);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream, boolean bl, boolean bl2, int[] nArray) throws IOException {
        int n;
        int n2;
        int n3;
        int n4;
        if (nArray != null) {
            bl2 = true;
            throw new IOException("Transparent color not support in custom PNG Decoder");
        }
        PNGDecoder pNGDecoder = new PNGDecoder(inputStream);
        if (!pNGDecoder.isRGB()) {
            throw new IOException("Only RGB formatted images are supported by the PNGLoader");
        }
        this.width = pNGDecoder.getWidth();
        this.height = pNGDecoder.getHeight();
        this.texWidth = this.get2Fold(this.width);
        this.texHeight = this.get2Fold(this.height);
        int n5 = pNGDecoder.hasAlpha() ? 4 : 3;
        this.bitDepth = pNGDecoder.hasAlpha() ? 32 : 24;
        this.scratch = BufferUtils.createByteBuffer((int)(this.texWidth * this.texHeight * n5));
        pNGDecoder.decode(this.scratch, this.texWidth * n5, n5 == 4 ? PNGDecoder.RGBA : PNGDecoder.RGB);
        if (this.height < this.texHeight - 1) {
            n4 = (this.texHeight - 1) * (this.texWidth * n5);
            n3 = (this.height - 1) * (this.texWidth * n5);
            for (n2 = 0; n2 < this.texWidth; ++n2) {
                for (n = 0; n < n5; ++n) {
                    this.scratch.put(n4 + n2 + n, this.scratch.get(n2 + n));
                    this.scratch.put(n3 + this.texWidth * n5 + n2 + n, this.scratch.get(n3 + n2 + n));
                }
            }
        }
        if (this.width < this.texWidth - 1) {
            for (n4 = 0; n4 < this.texHeight; ++n4) {
                for (n3 = 0; n3 < n5; ++n3) {
                    this.scratch.put((n4 + 1) * (this.texWidth * n5) - n5 + n3, this.scratch.get(n4 * (this.texWidth * n5) + n3));
                    this.scratch.put(n4 * (this.texWidth * n5) + this.width * n5 + n3, this.scratch.get(n4 * (this.texWidth * n5) + (this.width - 1) * n5 + n3));
                }
            }
        }
        if (!pNGDecoder.hasAlpha() && bl2) {
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)(this.texWidth * this.texHeight * 4));
            for (n3 = 0; n3 < this.texWidth; ++n3) {
                for (n2 = 0; n2 < this.texHeight; ++n2) {
                    n = n2 * 3 + n3 * this.texHeight * 3;
                    int n6 = n2 * 4 + n3 * this.texHeight * 4;
                    byteBuffer.put(n6, this.scratch.get(n));
                    byteBuffer.put(n6 + 1, this.scratch.get(n + 1));
                    byteBuffer.put(n6 + 2, this.scratch.get(n + 2));
                    if (n3 < this.getHeight() && n2 < this.getWidth()) {
                        byteBuffer.put(n6 + 3, (byte)-1);
                        continue;
                    }
                    byteBuffer.put(n6 + 3, (byte)0);
                }
            }
            this.bitDepth = 32;
            this.scratch = byteBuffer;
        }
        if (nArray != null) {
            for (int i = 0; i < this.texWidth * this.texHeight * 4; i += 4) {
                n3 = 1;
                for (n2 = 0; n2 < 3; ++n2) {
                    if (this.toInt(this.scratch.get(i + n2)) == nArray[n2]) continue;
                    n3 = 0;
                }
                if (n3 == 0) continue;
                this.scratch.put(i + 3, (byte)0);
            }
        }
        this.scratch.position(0);
        return this.scratch;
    }

    private int toInt(byte by) {
        if (by < 0) {
            return 256 + by;
        }
        return by;
    }

    private int get2Fold(int n) {
        int n2;
        for (n2 = 2; n2 < n; n2 *= 2) {
        }
        return n2;
    }

    @Override
    public void configureEdging(boolean bl) {
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}

