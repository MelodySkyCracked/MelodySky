/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.imageout;

import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.imageout.ImageWriter;

public class ImageIOWriter
implements ImageWriter {
    @Override
    public void saveImage(Image image, String string, OutputStream outputStream, boolean bl) throws IOException {
        ComponentColorModel componentColorModel;
        PixelInterleavedSampleModel pixelInterleavedSampleModel;
        Object object;
        int n = 4 * image.getWidth() * image.getHeight();
        if (!bl) {
            n = 3 * image.getWidth() * image.getHeight();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        for (int i = 0; i < image.getHeight(); ++i) {
            for (int j = 0; j < image.getWidth(); ++j) {
                Color color = image.getColor(j, i);
                byteBuffer.put((byte)(color.r * 255.0f));
                byteBuffer.put((byte)(color.g * 255.0f));
                byteBuffer.put((byte)(color.b * 255.0f));
                if (!bl) continue;
                byteBuffer.put((byte)(color.a * 255.0f));
            }
        }
        DataBufferByte dataBufferByte = new DataBufferByte(byteBuffer.array(), n);
        if (bl) {
            object = new int[]{0, 1, 2, 3};
            pixelInterleavedSampleModel = new PixelInterleavedSampleModel(0, image.getWidth(), image.getHeight(), 4, 4 * image.getWidth(), (int[])object);
            componentColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8, 8}, true, false, 3, 0);
        } else {
            object = new int[]{0, 1, 2};
            pixelInterleavedSampleModel = new PixelInterleavedSampleModel(0, image.getWidth(), image.getHeight(), 3, 3 * image.getWidth(), (int[])object);
            componentColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8, 0}, false, false, 1, 0);
        }
        object = Raster.createWritableRaster(pixelInterleavedSampleModel, dataBufferByte, new Point(0, 0));
        BufferedImage bufferedImage = new BufferedImage(componentColorModel, (WritableRaster)object, false, null);
        ImageIO.write((RenderedImage)bufferedImage, string, outputStream);
    }
}

