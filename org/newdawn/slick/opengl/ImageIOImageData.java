/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import org.newdawn.slick.opengl.LoadableImageData;

public class ImageIOImageData
implements LoadableImageData {
    private static final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8, 8}, true, false, 3, 0);
    private static final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8, 0}, false, false, 1, 0);
    private int depth;
    private int height;
    private int width;
    private int texWidth;
    private int texHeight;
    private boolean edging = true;

    @Override
    public int getDepth() {
        return this.depth;
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
    public ByteBuffer loadImage(InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, true, null);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream, boolean bl, int[] nArray) throws IOException {
        return this.loadImage(inputStream, bl, false, nArray);
    }

    @Override
    public ByteBuffer loadImage(InputStream inputStream, boolean bl, boolean bl2, int[] nArray) throws IOException {
        if (nArray != null) {
            bl2 = true;
        }
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return this.imageToByteBuffer(bufferedImage, bl, bl2, nArray);
    }

    public ByteBuffer imageToByteBuffer(BufferedImage bufferedImage, boolean bl, boolean bl2, int[] nArray) {
        BufferedImage bufferedImage2;
        WritableRaster writableRaster;
        boolean bl3;
        int n;
        ByteBuffer byteBuffer = null;
        int n2 = 2;
        for (n = 2; n < bufferedImage.getWidth(); n *= 2) {
        }
        while (n2 < bufferedImage.getHeight()) {
            n2 *= 2;
        }
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.texHeight = n2;
        this.texWidth = n;
        boolean bl4 = bl3 = bufferedImage.getColorModel().hasAlpha() || bl2;
        if (bl3) {
            this.depth = 32;
            writableRaster = Raster.createInterleavedRaster(0, n, n2, 4, null);
            bufferedImage2 = new BufferedImage(glAlphaColorModel, writableRaster, false, new Hashtable());
        } else {
            this.depth = 24;
            writableRaster = Raster.createInterleavedRaster(0, n, n2, 3, null);
            bufferedImage2 = new BufferedImage(glColorModel, writableRaster, false, new Hashtable());
        }
        Graphics2D graphics2D = (Graphics2D)bufferedImage2.getGraphics();
        if (bl3) {
            graphics2D.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
            graphics2D.fillRect(0, 0, n, n2);
        }
        if (bl) {
            graphics2D.scale(1.0, -1.0);
            graphics2D.drawImage((Image)bufferedImage, 0, -this.height, null);
        } else {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.edging) {
            if (this.height < n2 - 1) {
                this.copyArea(bufferedImage2, 0, 0, this.width, 1, 0, n2 - 1);
                this.copyArea(bufferedImage2, 0, this.height - 1, this.width, 1, 0, 1);
            }
            if (this.width < n - 1) {
                this.copyArea(bufferedImage2, 0, 0, 1, this.height, n - 1, 0);
                this.copyArea(bufferedImage2, this.width - 1, 0, 1, this.height, 1, 0);
            }
        }
        byte[] byArray = ((DataBufferByte)bufferedImage2.getRaster().getDataBuffer()).getData();
        if (nArray != null) {
            for (int i = 0; i < byArray.length; i += 4) {
                boolean bl5 = true;
                for (int j = 0; j < 3; ++j) {
                    int n3;
                    int n4 = n3 = byArray[i + j] < 0 ? 256 + byArray[i + j] : byArray[i + j];
                    if (n3 == nArray[j]) continue;
                    bl5 = false;
                }
                if (!bl5) continue;
                byArray[i + 3] = 0;
            }
        }
        byteBuffer = ByteBuffer.allocateDirect(byArray.length);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(byArray, 0, byArray.length);
        byteBuffer.flip();
        graphics2D.dispose();
        return byteBuffer;
    }

    @Override
    public ByteBuffer getImageBufferData() {
        throw new RuntimeException("ImageIOImageData doesn't store it's image.");
    }

    private void copyArea(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int n5, int n6) {
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.drawImage((Image)bufferedImage.getSubimage(n, n2, n3, n4), n + n5, n2 + n6, null);
    }

    @Override
    public void configureEdging(boolean bl) {
        this.edging = bl;
    }
}

