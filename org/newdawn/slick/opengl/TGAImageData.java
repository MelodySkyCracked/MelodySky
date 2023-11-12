/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.LoadableImageData;

public class TGAImageData
implements LoadableImageData {
    private int texWidth;
    private int texHeight;
    private int width;
    private int height;
    private short pixelDepth;

    private short flipEndian(short s) {
        int n = s & 0xFFFF;
        return (short)(n << 8 | (n & 0xFF00) >>> 8);
    }

    @Override
    public int getDepth() {
        return this.pixelDepth;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getTexWidth() {
        return this.texWidth;
    }

    @Override
    public int getTexHeight() {
        return this.texHeight;
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, true, null);
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
        }
        byte by = 0;
        byte by2 = 0;
        byte by3 = 0;
        int n5 = 0;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 100000);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        short s = (short)dataInputStream.read();
        short s2 = (short)dataInputStream.read();
        short s3 = (short)dataInputStream.read();
        short s4 = this.flipEndian(dataInputStream.readShort());
        short s5 = this.flipEndian(dataInputStream.readShort());
        short s6 = (short)dataInputStream.read();
        short s7 = this.flipEndian(dataInputStream.readShort());
        short s8 = this.flipEndian(dataInputStream.readShort());
        if (s3 != 2) {
            throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
        }
        this.width = this.flipEndian(dataInputStream.readShort());
        this.height = this.flipEndian(dataInputStream.readShort());
        this.pixelDepth = (short)dataInputStream.read();
        if (this.pixelDepth == 32) {
            bl2 = false;
        }
        this.texWidth = this.get2Fold(this.width);
        this.texHeight = this.get2Fold(this.height);
        short s9 = (short)dataInputStream.read();
        if ((s9 & 0x20) == 0) {
            boolean bl3 = bl = !bl;
        }
        if (s > 0) {
            bufferedInputStream.skip(s);
        }
        byte[] byArray = null;
        if (this.pixelDepth == 32 || bl2) {
            this.pixelDepth = (short)32;
            byArray = new byte[this.texWidth * this.texHeight * 4];
        } else if (this.pixelDepth == 24) {
            byArray = new byte[this.texWidth * this.texHeight * 3];
        } else {
            throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
        }
        if (this.pixelDepth == 24) {
            if (bl) {
                for (n4 = this.height - 1; n4 >= 0; --n4) {
                    for (n3 = 0; n3 < this.width; ++n3) {
                        by3 = dataInputStream.readByte();
                        by2 = dataInputStream.readByte();
                        by = dataInputStream.readByte();
                        n2 = (n3 + n4 * this.texWidth) * 3;
                        byArray[n2] = by;
                        byArray[n2 + 1] = by2;
                        byArray[n2 + 2] = by3;
                    }
                }
            } else {
                for (n4 = 0; n4 < this.height; ++n4) {
                    for (n3 = 0; n3 < this.width; ++n3) {
                        by3 = dataInputStream.readByte();
                        by2 = dataInputStream.readByte();
                        by = dataInputStream.readByte();
                        n2 = (n3 + n4 * this.texWidth) * 3;
                        byArray[n2] = by;
                        byArray[n2 + 1] = by2;
                        byArray[n2 + 2] = by3;
                    }
                }
            }
        } else if (this.pixelDepth == 32) {
            if (bl) {
                for (n4 = this.height - 1; n4 >= 0; --n4) {
                    for (n3 = 0; n3 < this.width; ++n3) {
                        by3 = dataInputStream.readByte();
                        by2 = dataInputStream.readByte();
                        by = dataInputStream.readByte();
                        n5 = bl2 ? -1 : (int)dataInputStream.readByte();
                        n2 = (n3 + n4 * this.texWidth) * 4;
                        byArray[n2] = by;
                        byArray[n2 + 1] = by2;
                        byArray[n2 + 2] = by3;
                        byArray[n2 + 3] = n5;
                        if (n5 != 0) continue;
                        byArray[n2 + 2] = 0;
                        byArray[n2 + 1] = 0;
                        byArray[n2] = 0;
                    }
                }
            } else {
                for (n4 = 0; n4 < this.height; ++n4) {
                    for (n3 = 0; n3 < this.width; ++n3) {
                        by3 = dataInputStream.readByte();
                        by2 = dataInputStream.readByte();
                        by = dataInputStream.readByte();
                        n5 = bl2 ? -1 : (int)dataInputStream.readByte();
                        n2 = (n3 + n4 * this.texWidth) * 4;
                        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
                            byArray[n2] = by;
                            byArray[n2 + 1] = by2;
                            byArray[n2 + 2] = by3;
                            byArray[n2 + 3] = n5;
                        } else {
                            byArray[n2] = by;
                            byArray[n2 + 1] = by2;
                            byArray[n2 + 2] = by3;
                            byArray[n2 + 3] = n5;
                        }
                        if (n5 != 0) continue;
                        byArray[n2 + 2] = 0;
                        byArray[n2 + 1] = 0;
                        byArray[n2] = 0;
                    }
                }
            }
        }
        inputStream.close();
        if (nArray != null) {
            for (n4 = 0; n4 < byArray.length; n4 += 4) {
                n3 = 1;
                for (n2 = 0; n2 < 3; ++n2) {
                    if (byArray[n4 + n2] == nArray[n2]) continue;
                    n3 = 0;
                }
                if (n3 == 0) continue;
                byArray[n4 + 3] = 0;
            }
        }
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)byArray.length);
        byteBuffer.put(byArray);
        n3 = this.pixelDepth / 8;
        if (this.height < this.texHeight - 1) {
            n2 = (this.texHeight - 1) * (this.texWidth * n3);
            n = (this.height - 1) * (this.texWidth * n3);
            for (int i = 0; i < this.texWidth * n3; ++i) {
                byteBuffer.put(n2 + i, byteBuffer.get(i));
                byteBuffer.put(n + this.texWidth * n3 + i, byteBuffer.get(this.texWidth * n3 + i));
            }
        }
        if (this.width < this.texWidth - 1) {
            for (n2 = 0; n2 < this.texHeight; ++n2) {
                for (n = 0; n < n3; ++n) {
                    byteBuffer.put((n2 + 1) * (this.texWidth * n3) - n3 + n, byteBuffer.get(n2 * (this.texWidth * n3) + n));
                    byteBuffer.put(n2 * (this.texWidth * n3) + this.width * n3 + n, byteBuffer.get(n2 * (this.texWidth * n3) + (this.width - 1) * n3 + n));
                }
            }
        }
        byteBuffer.flip();
        return byteBuffer;
    }

    private int get2Fold(int n) {
        int n2;
        for (n2 = 2; n2 < n; n2 *= 2) {
        }
        return n2;
    }

    @Override
    public ByteBuffer getImageBufferData() {
        throw new RuntimeException("TGAImageData doesn't store it's image.");
    }

    @Override
    public void configureEdging(boolean bl) {
    }
}

