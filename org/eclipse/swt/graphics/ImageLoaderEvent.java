/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.util.EventObject;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class ImageLoaderEvent
extends EventObject {
    public ImageData imageData;
    public int incrementCount;
    public boolean endOfImage;
    static final long serialVersionUID = 3257284738325558065L;

    public ImageLoaderEvent(ImageLoader imageLoader, ImageData imageData, int n, boolean bl) {
        super(imageLoader);
        this.imageData = imageData;
        this.incrementCount = n;
        this.endOfImage = bl;
    }

    @Override
    public String toString() {
        return "ImageLoaderEvent {source=" + this.source + " imageData=" + this.imageData + " incrementCount=" + this.incrementCount + " endOfImage=" + this.endOfImage;
    }
}

