/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.ImageLoaderListener;
import org.eclipse.swt.internal.image.FileFormat;

public class ImageLoader {
    public ImageData[] data;
    public int logicalScreenWidth;
    public int logicalScreenHeight;
    public int backgroundPixel;
    public int repeatCount;
    public int compression;
    List imageLoaderListeners;

    public ImageLoader() {
        this.reset();
    }

    void reset() {
        this.data = null;
        this.logicalScreenWidth = 0;
        this.logicalScreenHeight = 0;
        this.backgroundPixel = -1;
        this.repeatCount = 1;
        this.compression = -1;
    }

    public ImageData[] load(InputStream inputStream) {
        if (inputStream == null) {
            SWT.error(4);
        }
        this.reset();
        this.data = FileFormat.load(inputStream, this);
        return this.data;
    }

    public ImageData[] load(String string) {
        ImageData[] imageDataArray;
        if (string == null) {
            SWT.error(4);
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(string);
            imageDataArray = this.load(fileInputStream);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
            try {
                if (fileInputStream != null) {
                    ((InputStream)fileInputStream).close();
                }
            }
            catch (IOException iOException2) {}
            return null;
        }
        try {
            if (fileInputStream != null) {
                ((InputStream)fileInputStream).close();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return imageDataArray;
    }

    public void save(OutputStream outputStream, int n) {
        if (outputStream == null) {
            SWT.error(4);
        }
        FileFormat.save(outputStream, n, this);
    }

    public void save(String string, int n) {
        if (string == null) {
            SWT.error(4);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(string);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        this.save(fileOutputStream, n);
        try {
            ((OutputStream)fileOutputStream).close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void addImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        if (imageLoaderListener == null) {
            SWT.error(4);
        }
        if (this.imageLoaderListeners == null) {
            this.imageLoaderListeners = new ArrayList();
        }
        this.imageLoaderListeners.add(imageLoaderListener);
    }

    public void removeImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        if (imageLoaderListener == null) {
            SWT.error(4);
        }
        if (this.imageLoaderListeners == null) {
            return;
        }
        this.imageLoaderListeners.remove(imageLoaderListener);
    }

    public void notifyListeners(ImageLoaderEvent imageLoaderEvent) {
        if (this != null) {
            return;
        }
        int n = this.imageLoaderListeners.size();
        for (int i = 0; i < n; ++i) {
            ImageLoaderListener imageLoaderListener = (ImageLoaderListener)this.imageLoaderListeners.get(i);
            imageLoaderListener.imageDataLoaded(imageLoaderEvent);
        }
    }
}

