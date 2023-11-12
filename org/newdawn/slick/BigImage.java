/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.lIII;
import org.newdawn.slick.lIIl;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.OperationNotSupportedException;
import org.newdawn.slick.util.ResourceLoader;

public class BigImage
extends Image {
    protected static SGL GL = Renderer.get();
    private static Image lastBind;
    private Image[][] images;
    private int xcount;
    private int ycount;
    private int realWidth;
    private int realHeight;

    public static final int getMaxSingleImageSize() {
        IntBuffer intBuffer = BufferUtils.createIntBuffer((int)16);
        GL.glGetInteger(3379, intBuffer);
        return intBuffer.get(0);
    }

    private BigImage() {
        this.inited = true;
    }

    public BigImage(String string) throws SlickException {
        this(string, 2);
    }

    public BigImage(String string, int n) throws SlickException {
        this.build(string, n, BigImage.getMaxSingleImageSize());
    }

    public BigImage(String string, int n, int n2) throws SlickException {
        this.build(string, n, n2);
    }

    public BigImage(LoadableImageData loadableImageData, ByteBuffer byteBuffer, int n) {
        this.build(loadableImageData, byteBuffer, n, BigImage.getMaxSingleImageSize());
    }

    public BigImage(LoadableImageData loadableImageData, ByteBuffer byteBuffer, int n, int n2) {
        this.build(loadableImageData, byteBuffer, n, n2);
    }

    public Image getTile(int n, int n2) {
        return this.images[n][n2];
    }

    private void build(String string, int n, int n2) throws SlickException {
        try {
            LoadableImageData loadableImageData = ImageDataFactory.getImageDataFor(string);
            ByteBuffer byteBuffer = loadableImageData.loadImage(ResourceLoader.getResourceAsStream(string), false, null);
            this.build(loadableImageData, byteBuffer, n, n2);
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load: " + string, iOException);
        }
    }

    private void build(LoadableImageData loadableImageData, ByteBuffer byteBuffer, int n, int n2) {
        int n3 = loadableImageData.getTexWidth();
        int n4 = loadableImageData.getTexHeight();
        this.realWidth = this.width = loadableImageData.getWidth();
        this.realHeight = this.height = loadableImageData.getHeight();
        if (n3 <= n2 && n4 <= n2) {
            this.images = new Image[1][1];
            lIII lIII2 = new lIII(this, loadableImageData, n4, byteBuffer, n3);
            this.images[0][0] = new Image(lIII2, n);
            this.xcount = 1;
            this.ycount = 1;
            this.inited = true;
            return;
        }
        this.xcount = (this.realWidth - 1) / n2 + 1;
        this.ycount = (this.realHeight - 1) / n2 + 1;
        this.images = new Image[this.xcount][this.ycount];
        int n5 = loadableImageData.getDepth() / 8;
        for (int i = 0; i < this.xcount; ++i) {
            for (int j = 0; j < this.ycount; ++j) {
                int n6 = (i + 1) * n2;
                int n7 = (j + 1) * n2;
                int n8 = Math.min(this.realWidth - i * n2, n2);
                int n9 = Math.min(this.realHeight - j * n2, n2);
                int n10 = n2;
                int n11 = n2;
                ByteBuffer byteBuffer2 = BufferUtils.createByteBuffer((int)(n2 * n2 * n5));
                int n12 = i * n2 * n5;
                byte[] byArray = new byte[n10 * n5];
                for (int k = 0; k < n11; ++k) {
                    int n13 = (j * n2 + k) * n3 * n5;
                    byteBuffer.position(n13 + n12);
                    byteBuffer.get(byArray, 0, n10 * n5);
                    byteBuffer2.put(byArray);
                }
                byteBuffer2.flip();
                lIIl lIIl2 = new lIIl(this, loadableImageData, n9, n8, byteBuffer2, n11, n10);
                this.images[i][j] = new Image(lIIl2, n);
            }
        }
        this.inited = true;
    }

    @Override
    public void bind() {
        throw new OperationNotSupportedException("Can't bind big images yet");
    }

    @Override
    public Image copy() {
        throw new OperationNotSupportedException("Can't copy big images yet");
    }

    @Override
    public void draw() {
        this.draw(0.0f, 0.0f);
    }

    @Override
    public void draw(float f, float f2, Color color) {
        this.draw(f, f2, this.width, this.height, color);
    }

    @Override
    public void draw(float f, float f2, float f3, Color color) {
        this.draw(f, f2, (float)this.width * f3, (float)this.height * f3, color);
    }

    @Override
    public void draw(float f, float f2, float f3, float f4, Color color) {
        float f5 = f3 / (float)this.realWidth;
        float f6 = f4 / (float)this.realHeight;
        GL.glTranslatef(f, f2, 0.0f);
        GL.glScalef(f5, f6, 1.0f);
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i = 0; i < this.xcount; ++i) {
            f8 = 0.0f;
            for (int j = 0; j < this.ycount; ++j) {
                Image image = this.images[i][j];
                image.draw(f7, f8, image.getWidth(), image.getHeight(), color);
                f8 += (float)image.getHeight();
                if (j != this.ycount - 1) continue;
                f7 += (float)image.getWidth();
            }
        }
        GL.glScalef(1.0f / f5, 1.0f / f6, 1.0f);
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    @Override
    public void draw(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        int n = (int)(f7 - f5);
        int n2 = (int)(f8 - f6);
        Image image = this.getSubImage((int)f5, (int)f6, n, n2);
        int n3 = (int)(f3 - f);
        int n4 = (int)(f4 - f2);
        image.draw(f, f2, (float)n3, n4);
    }

    @Override
    public void draw(float f, float f2, float f3, float f4, float f5, float f6) {
        int n = (int)(f5 - f3);
        int n2 = (int)(f6 - f4);
        this.draw(f, f2, n, n2, f3, f4, f5, f6);
    }

    @Override
    public void draw(float f, float f2, float f3, float f4) {
        this.draw(f, f2, f3, f4, Color.white);
    }

    @Override
    public void draw(float f, float f2, float f3) {
        this.draw(f, f2, f3, Color.white);
    }

    @Override
    public void draw(float f, float f2) {
        this.draw(f, f2, Color.white);
    }

    @Override
    public void drawEmbedded(float f, float f2, float f3, float f4) {
        float f5 = f3 / (float)this.realWidth;
        float f6 = f4 / (float)this.realHeight;
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i = 0; i < this.xcount; ++i) {
            f8 = 0.0f;
            for (int j = 0; j < this.ycount; ++j) {
                Image image = this.images[i][j];
                if (lastBind == null || image.getTexture() != lastBind.getTexture()) {
                    if (lastBind != null) {
                        lastBind.endUse();
                    }
                    lastBind = image;
                    lastBind.startUse();
                }
                image.drawEmbedded(f7 + f, f8 + f2, image.getWidth(), image.getHeight());
                f8 += (float)image.getHeight();
                if (j != this.ycount - 1) continue;
                f7 += (float)image.getWidth();
            }
        }
    }

    @Override
    public void drawFlash(float f, float f2, float f3, float f4) {
        float f5 = f3 / (float)this.realWidth;
        float f6 = f4 / (float)this.realHeight;
        GL.glTranslatef(f, f2, 0.0f);
        GL.glScalef(f5, f6, 1.0f);
        float f7 = 0.0f;
        float f8 = 0.0f;
        for (int i = 0; i < this.xcount; ++i) {
            f8 = 0.0f;
            for (int j = 0; j < this.ycount; ++j) {
                Image image = this.images[i][j];
                image.drawFlash(f7, f8, image.getWidth(), image.getHeight());
                f8 += (float)image.getHeight();
                if (j != this.ycount - 1) continue;
                f7 += (float)image.getWidth();
            }
        }
        GL.glScalef(1.0f / f5, 1.0f / f6, 1.0f);
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    @Override
    public void drawFlash(float f, float f2) {
        this.drawFlash(f, f2, this.width, this.height);
    }

    @Override
    public void endUse() {
        if (lastBind != null) {
            lastBind.endUse();
        }
        lastBind = null;
    }

    @Override
    public void startUse() {
    }

    @Override
    public void ensureInverted() {
        throw new OperationNotSupportedException("Doesn't make sense for tiled operations");
    }

    @Override
    public Color getColor(int n, int n2) {
        throw new OperationNotSupportedException("Can't use big images as buffers");
    }

    @Override
    public Image getFlippedCopy(boolean bl, boolean bl2) {
        int n;
        int n2;
        Image[][] imageArray;
        BigImage bigImage = new BigImage();
        bigImage.images = this.images;
        bigImage.xcount = this.xcount;
        bigImage.ycount = this.ycount;
        bigImage.width = this.width;
        bigImage.height = this.height;
        bigImage.realWidth = this.realWidth;
        bigImage.realHeight = this.realHeight;
        if (bl) {
            imageArray = bigImage.images;
            bigImage.images = new Image[this.xcount][this.ycount];
            for (n2 = 0; n2 < this.xcount; ++n2) {
                for (n = 0; n < this.ycount; ++n) {
                    bigImage.images[n2][n] = imageArray[this.xcount - 1 - n2][n].getFlippedCopy(true, false);
                }
            }
        }
        if (bl2) {
            imageArray = bigImage.images;
            bigImage.images = new Image[this.xcount][this.ycount];
            for (n2 = 0; n2 < this.xcount; ++n2) {
                for (n = 0; n < this.ycount; ++n) {
                    bigImage.images[n2][n] = imageArray[n2][this.ycount - 1 - n].getFlippedCopy(false, true);
                }
            }
        }
        return bigImage;
    }

    @Override
    public Graphics getGraphics() throws SlickException {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }

    @Override
    public Image getScaledCopy(float f) {
        return this.getScaledCopy((int)(f * (float)this.width), (int)(f * (float)this.height));
    }

    @Override
    public Image getScaledCopy(int n, int n2) {
        BigImage bigImage = new BigImage();
        bigImage.images = this.images;
        bigImage.xcount = this.xcount;
        bigImage.ycount = this.ycount;
        bigImage.width = n;
        bigImage.height = n2;
        bigImage.realWidth = this.realWidth;
        bigImage.realHeight = this.realHeight;
        return bigImage;
    }

    @Override
    public Image getSubImage(int n, int n2, int n3, int n4) {
        BigImage bigImage = new BigImage();
        bigImage.width = n3;
        bigImage.height = n4;
        bigImage.realWidth = n3;
        bigImage.realHeight = n4;
        bigImage.images = new Image[this.xcount][this.ycount];
        float f = 0.0f;
        float f2 = 0.0f;
        int n5 = n + n3;
        int n6 = n2 + n4;
        int n7 = 0;
        int n8 = 0;
        boolean bl = false;
        for (int i = 0; i < this.xcount; ++i) {
            f2 = 0.0f;
            n8 = 0;
            bl = false;
            for (int j = 0; j < this.ycount; ++j) {
                Image image = this.images[i][j];
                int n9 = (int)(f + (float)image.getWidth());
                int n10 = (int)(f2 + (float)image.getHeight());
                int n11 = (int)Math.max((float)n, f);
                int n12 = (int)Math.max((float)n2, f2);
                int n13 = Math.min(n5, n9);
                int n14 = Math.min(n6, n10);
                int n15 = n13 - n11;
                int n16 = n14 - n12;
                if (n15 > 0 && n16 > 0) {
                    Image image2 = image.getSubImage((int)((float)n11 - f), (int)((float)n12 - f2), n13 - n11, n14 - n12);
                    bl = true;
                    bigImage.images[n7][n8] = image2;
                    bigImage.ycount = Math.max(bigImage.ycount, ++n8);
                }
                f2 += (float)image.getHeight();
                if (j != this.ycount - 1) continue;
                f += (float)image.getWidth();
            }
            if (!bl) continue;
            ++n7;
            ++bigImage.xcount;
        }
        return bigImage;
    }

    @Override
    public Texture getTexture() {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }

    @Override
    protected void initImpl() {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }

    @Override
    protected void reinit() {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }

    @Override
    public void setTexture(Texture texture) {
        throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
    }

    public Image getSubImage(int n, int n2) {
        return this.images[n][n2];
    }

    public int getHorizontalImageCount() {
        return this.xcount;
    }

    public int getVerticalImageCount() {
        return this.ycount;
    }

    @Override
    public String toString() {
        return "[BIG IMAGE]";
    }

    @Override
    public void destroy() throws SlickException {
        for (int i = 0; i < this.xcount; ++i) {
            for (int j = 0; j < this.ycount; ++j) {
                Image image = this.images[i][j];
                image.destroy();
            }
        }
    }

    @Override
    public void draw(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        int n = (int)(f7 - f5);
        int n2 = (int)(f8 - f6);
        Image image = this.getSubImage((int)f5, (int)f6, n, n2);
        int n3 = (int)(f3 - f);
        int n4 = (int)(f4 - f2);
        image.draw(f, f2, n3, n4, color);
    }

    @Override
    public void drawCentered(float f, float f2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawEmbedded(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawEmbedded(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawFlash(float f, float f2, float f3, float f4, Color color) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawSheared(float f, float f2, float f3, float f4) {
        throw new UnsupportedOperationException();
    }
}

