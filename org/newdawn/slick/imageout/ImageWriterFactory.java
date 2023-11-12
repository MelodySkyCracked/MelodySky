/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.imageout;

import java.util.HashMap;
import javax.imageio.ImageIO;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.imageout.ImageWriter;
import org.newdawn.slick.imageout.TGAWriter;

public class ImageWriterFactory {
    private static HashMap writers = new HashMap();

    public static void registerWriter(String string, ImageWriter imageWriter) {
        writers.put(string, imageWriter);
    }

    public static String[] getSupportedFormats() {
        return writers.keySet().toArray(new String[0]);
    }

    public static ImageWriter getWriterForFormat(String string) throws SlickException {
        ImageWriter imageWriter = (ImageWriter)writers.get(string);
        if (imageWriter != null) {
            return imageWriter;
        }
        imageWriter = (ImageWriter)writers.get(string.toLowerCase());
        if (imageWriter != null) {
            return imageWriter;
        }
        imageWriter = (ImageWriter)writers.get(string.toUpperCase());
        if (imageWriter != null) {
            return imageWriter;
        }
        throw new SlickException("No image writer available for: " + string);
    }

    static {
        String[] stringArray = ImageIO.getWriterFormatNames();
        ImageIOWriter imageIOWriter = new ImageIOWriter();
        for (int i = 0; i < stringArray.length; ++i) {
            ImageWriterFactory.registerWriter(stringArray[i], imageIOWriter);
        }
        TGAWriter tGAWriter = new TGAWriter();
        ImageWriterFactory.registerWriter("tga", tGAWriter);
    }
}

