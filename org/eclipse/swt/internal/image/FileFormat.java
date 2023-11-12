/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.LEDataOutputStream;

public abstract class FileFormat {
    static final String FORMAT_PACKAGE = "org.eclipse.swt.internal.image";
    static final String FORMAT_SUFFIX = "FileFormat";
    static final String[] FORMATS = new String[]{"WinBMP", "WinBMP", "GIF", "WinICO", "JPEG", "PNG", "TIFF", "OS2BMP"};
    LEDataInputStream inputStream;
    LEDataOutputStream outputStream;
    ImageLoader loader;
    int compression;

    static FileFormat getFileFormat(LEDataInputStream lEDataInputStream, String string) throws Exception {
        Class<?> clazz = Class.forName("org.eclipse.swt.internal.image." + string + FORMAT_SUFFIX);
        FileFormat fileFormat = (FileFormat)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        if (fileFormat.isFileFormat(lEDataInputStream)) {
            return fileFormat;
        }
        return null;
    }

    abstract boolean isFileFormat(LEDataInputStream var1);

    abstract ImageData[] loadFromByteStream();

    public ImageData[] loadFromStream(LEDataInputStream lEDataInputStream) {
        try {
            this.inputStream = lEDataInputStream;
            return this.loadFromByteStream();
        }
        catch (Exception exception) {
            if (exception instanceof IOException) {
                SWT.error(39, exception);
            } else {
                SWT.error(40, exception);
            }
            return null;
        }
    }

    public static ImageData[] load(InputStream inputStream, ImageLoader imageLoader) {
        FileFormat fileFormat = null;
        LEDataInputStream lEDataInputStream = new LEDataInputStream(inputStream);
        for (int i = 1; i < FORMATS.length; ++i) {
            if (FORMATS[i] == null) continue;
            try {
                fileFormat = FileFormat.getFileFormat(lEDataInputStream, FORMATS[i]);
                if (fileFormat == null) continue;
                break;
            }
            catch (ClassNotFoundException classNotFoundException) {
                FileFormat.FORMATS[i] = null;
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (fileFormat == null) {
            SWT.error(42);
        }
        fileFormat.loader = imageLoader;
        return fileFormat.loadFromStream(lEDataInputStream);
    }

    public static void save(OutputStream outputStream, int n, ImageLoader imageLoader) {
        if (n < 0 || n >= FORMATS.length) {
            SWT.error(42);
        }
        if (FORMATS[n] == null) {
            SWT.error(42);
        }
        if (imageLoader.data == null || imageLoader.data.length < 1) {
            SWT.error(5);
        }
        LEDataOutputStream lEDataOutputStream = new LEDataOutputStream(outputStream);
        FileFormat fileFormat = null;
        try {
            Class<?> clazz = Class.forName("org.eclipse.swt.internal.image." + FORMATS[n] + FORMAT_SUFFIX);
            fileFormat = (FileFormat)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            SWT.error(42);
        }
        if (n == 1) {
            switch (imageLoader.data[0].depth) {
                case 8: {
                    fileFormat.compression = 1;
                    break;
                }
                case 4: {
                    fileFormat.compression = 2;
                }
            }
        }
        fileFormat.unloadIntoStream(imageLoader, lEDataOutputStream);
    }

    abstract void unloadIntoByteStream(ImageLoader var1);

    public void unloadIntoStream(ImageLoader imageLoader, LEDataOutputStream lEDataOutputStream) {
        try {
            this.outputStream = lEDataOutputStream;
            this.unloadIntoByteStream(imageLoader);
            this.outputStream.flush();
        }
        catch (Exception exception) {
            try {
                this.outputStream.flush();
            }
            catch (Exception exception2) {
                // empty catch block
            }
            SWT.error(39, exception);
        }
    }
}

