/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.imageout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageWriter;
import org.newdawn.slick.imageout.ImageWriterFactory;

public class ImageOut {
    private static final boolean DEFAULT_ALPHA_WRITE = false;
    public static String TGA = "tga";
    public static String PNG = "png";
    public static String JPG = "jpg";

    public static String[] getSupportedFormats() {
        return ImageWriterFactory.getSupportedFormats();
    }

    public static void write(Image image, String string, OutputStream outputStream) throws SlickException {
        ImageOut.write(image, string, outputStream, false);
    }

    public static void write(Image image, String string, OutputStream outputStream, boolean bl) throws SlickException {
        try {
            ImageWriter imageWriter = ImageWriterFactory.getWriterForFormat(string);
            imageWriter.saveImage(image, string, outputStream, bl);
        }
        catch (IOException iOException) {
            throw new SlickException("Unable to write out the image in format: " + string, iOException);
        }
    }

    public static void write(Image image, String string) throws SlickException {
        ImageOut.write(image, string, false);
    }

    public static void write(Image image, String string, boolean bl) throws SlickException {
        try {
            int n = string.lastIndexOf(46);
            if (n < 0) {
                throw new SlickException("Unable to determine format from: " + string);
            }
            String string2 = string.substring(n + 1);
            ImageOut.write(image, string2, new FileOutputStream(string), bl);
        }
        catch (IOException iOException) {
            throw new SlickException("Unable to write to the destination: " + string, iOException);
        }
    }

    public static void write(Image image, String string, String string2) throws SlickException {
        ImageOut.write(image, string, string2, false);
    }

    public static void write(Image image, String string, String string2, boolean bl) throws SlickException {
        try {
            ImageOut.write(image, string, new FileOutputStream(string2), bl);
        }
        catch (IOException iOException) {
            throw new SlickException("Unable to write to the destination: " + string2, iOException);
        }
    }
}

