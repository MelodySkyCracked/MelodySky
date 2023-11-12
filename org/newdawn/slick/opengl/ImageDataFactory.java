/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.security.AccessController;
import org.newdawn.slick.opengl.CompositeImageData;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.PNGImageData;
import org.newdawn.slick.opengl.TGAImageData;
import org.newdawn.slick.opengl.lI;

public class ImageDataFactory {
    private static boolean usePngLoader = true;
    private static boolean pngLoaderPropertyChecked = false;
    private static final String PNG_LOADER = "org.newdawn.slick.pngloader";

    private static void checkProperty() {
        if (!pngLoaderPropertyChecked) {
            pngLoaderPropertyChecked = true;
            try {
                AccessController.doPrivileged(new lI());
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public static LoadableImageData getImageDataFor(String string) {
        ImageDataFactory.checkProperty();
        string = string.toLowerCase();
        if (string.endsWith(".tga")) {
            return new TGAImageData();
        }
        if (string.endsWith(".png")) {
            CompositeImageData compositeImageData = new CompositeImageData();
            if (usePngLoader) {
                compositeImageData.add(new PNGImageData());
            }
            compositeImageData.add(new ImageIOImageData());
            return compositeImageData;
        }
        return new ImageIOImageData();
    }

    static boolean access$002(boolean bl) {
        usePngLoader = bl;
        return usePngLoader;
    }

    static boolean access$000() {
        return usePngLoader;
    }
}

