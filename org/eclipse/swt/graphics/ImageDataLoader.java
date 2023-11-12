/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.InputStream;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

class ImageDataLoader {
    ImageDataLoader() {
    }

    public static ImageData[] load(InputStream inputStream) {
        return new ImageLoader().load(inputStream);
    }

    public static ImageData[] load(String string) {
        return new ImageLoader().load(string);
    }
}

