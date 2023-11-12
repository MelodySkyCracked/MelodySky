/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.internal.SWTEventListener;

@FunctionalInterface
public interface ImageLoaderListener
extends SWTEventListener {
    public void imageDataLoaded(ImageLoaderEvent var1);
}

