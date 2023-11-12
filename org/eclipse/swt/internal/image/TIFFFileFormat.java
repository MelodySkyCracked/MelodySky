/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.image.FileFormat;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.TIFFDirectory;
import org.eclipse.swt.internal.image.TIFFRandomFileAccess;

public final class TIFFFileFormat
extends FileFormat {
    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[4];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            return byArray[0] == byArray[1] && (byArray[0] == 73 && byArray[2] == 42 && byArray[3] == 0 || byArray[0] == 77 && byArray[2] == 0 && byArray[3] == 42);
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    ImageData[] loadFromByteStream() {
        byte[] byArray = new byte[8];
        ImageData[] imageDataArray = new ImageData[]{};
        TIFFRandomFileAccess tIFFRandomFileAccess = new TIFFRandomFileAccess(this.inputStream);
        try {
            int n;
            tIFFRandomFileAccess.read(byArray);
            if (byArray[0] != byArray[1]) {
                SWT.error(40);
            }
            if (!(byArray[0] == 73 && byArray[2] == 42 && byArray[3] == 0 || byArray[0] == 77 && byArray[2] == 0 && byArray[3] == 42)) {
                SWT.error(40);
            }
            boolean bl = byArray[0] == 73;
            int n2 = n = bl ? byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8 | (byArray[6] & 0xFF) << 16 | (byArray[7] & 0xFF) << 24 : byArray[7] & 0xFF | (byArray[6] & 0xFF) << 8 | (byArray[5] & 0xFF) << 16 | (byArray[4] & 0xFF) << 24;
            while (n != 0) {
                tIFFRandomFileAccess.seek(n);
                TIFFDirectory tIFFDirectory = new TIFFDirectory(tIFFRandomFileAccess, bl, this.loader);
                int[] nArray = new int[]{0};
                ImageData imageData = tIFFDirectory.read(nArray);
                n = nArray[0];
                ImageData[] imageDataArray2 = imageDataArray;
                imageDataArray = new ImageData[imageDataArray2.length + 1];
                System.arraycopy(imageDataArray2, 0, imageDataArray, 0, imageDataArray2.length);
                imageDataArray[imageDataArray.length - 1] = imageData;
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        return imageDataArray;
    }

    @Override
    void unloadIntoByteStream(ImageLoader imageLoader) {
        ImageData imageData = imageLoader.data[0];
        TIFFDirectory tIFFDirectory = new TIFFDirectory(imageData);
        try {
            tIFFDirectory.writeToStream(this.outputStream);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }
}

