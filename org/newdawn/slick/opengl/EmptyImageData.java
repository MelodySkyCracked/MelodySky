/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;

public class EmptyImageData
implements ImageData {
    private int width;
    private int height;

    public EmptyImageData(int n, int n2) {
        this.width = n;
        this.height = n2;
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
    public ByteBuffer getImageBufferData() {
        return BufferUtils.createByteBuffer((int)(this.getTexWidth() * this.getTexHeight() * 4));
    }

    @Override
    public int getTexHeight() {
        return InternalTextureLoader.get2Fold(this.height);
    }

    @Override
    public int getTexWidth() {
        return InternalTextureLoader.get2Fold(this.width);
    }

    @Override
    public int getWidth() {
        return this.width;
    }
}

