/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.ImageData;

public class ImageBuffer
implements ImageData {
    private int width;
    private int height;
    private int texWidth;
    private int texHeight;
    private byte[] rawData;

    public ImageBuffer(int n, int n2) {
        this.width = n;
        this.height = n2;
        this.texWidth = this.get2Fold(n);
        this.texHeight = this.get2Fold(n2);
        this.rawData = new byte[this.texWidth * this.texHeight * 4];
    }

    public byte[] getRGBA() {
        return this.rawData;
    }

    @Override
    public int getDepth() {
        return 32;
    }

    @Override
    public int getHeight() {
        return this.height;
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
    public int getWidth() {
        return this.width;
    }

    @Override
    public ByteBuffer getImageBufferData() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)this.rawData.length);
        byteBuffer.put(this.rawData);
        byteBuffer.flip();
        return byteBuffer;
    }

    public void setRGBA(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n < 0 || n >= this.width || n2 < 0 || n2 >= this.height) {
            throw new RuntimeException("Specified location: " + n + "," + n2 + " outside of image");
        }
        int n7 = (n + n2 * this.texWidth) * 4;
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            this.rawData[n7] = (byte)n5;
            this.rawData[n7 + 1] = (byte)n4;
            this.rawData[n7 + 2] = (byte)n3;
            this.rawData[n7 + 3] = (byte)n6;
        } else {
            this.rawData[n7] = (byte)n3;
            this.rawData[n7 + 1] = (byte)n4;
            this.rawData[n7 + 2] = (byte)n5;
            this.rawData[n7 + 3] = (byte)n6;
        }
    }

    public Image getImage() {
        return new Image(this);
    }

    public Image getImage(int n) {
        return new Image(this, n);
    }

    private int get2Fold(int n) {
        int n2;
        for (n2 = 2; n2 < n; n2 *= 2) {
        }
        return n2;
    }
}

