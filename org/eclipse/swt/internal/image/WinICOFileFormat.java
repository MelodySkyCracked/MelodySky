/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.internal.image.FileFormat;
import org.eclipse.swt.internal.image.LEDataInputStream;
import org.eclipse.swt.internal.image.WinBMPFileFormat;

public final class WinICOFileFormat
extends FileFormat {
    byte[] bitInvertData(byte[] byArray, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            byArray[i] = (byte)(255 - byArray[i - n]);
        }
        return byArray;
    }

    static byte[] convertPad(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
        if (n4 == n5) {
            return byArray;
        }
        int n6 = (n * n3 + 7) / 8;
        int n7 = (n6 + (n4 - 1)) / n4 * n4;
        int n8 = (n6 + (n5 - 1)) / n5 * n5;
        byte[] byArray2 = new byte[n2 * n8];
        int n9 = 0;
        int n10 = 0;
        for (int i = 0; i < n2; ++i) {
            System.arraycopy(byArray, n9, byArray2, n10, n8);
            n9 += n7;
            n10 += n8;
        }
        return byArray2;
    }

    int iconSize(ImageData imageData) {
        int n = (imageData.width * imageData.depth + 31) / 32 * 4;
        int n2 = (imageData.width + 31) / 32 * 4;
        int n3 = (n + n2) * imageData.height;
        int n4 = imageData.palette.colors != null ? imageData.palette.colors.length * 4 : 0;
        return 40 + n4 + n3;
    }

    @Override
    boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[4];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            return byArray[0] == 0 && byArray[1] == 0 && byArray[2] == 1 && byArray[3] == 0;
        }
        catch (Exception exception) {
            return false;
        }
    }

    int loadFileHeader(LEDataInputStream lEDataInputStream) {
        int n;
        int[] nArray = new int[3];
        try {
            nArray[0] = lEDataInputStream.readShort();
            nArray[1] = lEDataInputStream.readShort();
            nArray[2] = lEDataInputStream.readShort();
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if (nArray[0] != 0 || nArray[1] != 1) {
            SWT.error(40);
        }
        if ((n = nArray[2]) <= 0) {
            SWT.error(40);
        }
        return n;
    }

    int loadFileHeader(LEDataInputStream lEDataInputStream, boolean bl) {
        int n;
        int[] nArray = new int[3];
        try {
            if (bl) {
                nArray[0] = lEDataInputStream.readShort();
                nArray[1] = lEDataInputStream.readShort();
            } else {
                nArray[0] = 0;
                nArray[1] = 1;
            }
            nArray[2] = lEDataInputStream.readShort();
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if (nArray[0] != 0 || nArray[1] != 1) {
            SWT.error(40);
        }
        if ((n = nArray[2]) <= 0) {
            SWT.error(40);
        }
        return n;
    }

    @Override
    ImageData[] loadFromByteStream() {
        int n = this.loadFileHeader(this.inputStream);
        int[][] nArray = this.loadIconHeaders(n);
        ImageData[] imageDataArray = new ImageData[nArray.length];
        for (int i = 0; i < imageDataArray.length; ++i) {
            imageDataArray[i] = this.loadIcon(nArray[i]);
        }
        return imageDataArray;
    }

    ImageData loadIcon(int[] nArray) {
        Object object;
        try {
            object = FileFormat.getFileFormat(this.inputStream, "PNG");
            if (object != null) {
                ((FileFormat)object).loader = this.loader;
                return ((FileFormat)object).loadFromStream(this.inputStream)[0];
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        object = this.loadInfoHeader(nArray);
        WinBMPFileFormat winBMPFileFormat = new WinBMPFileFormat();
        winBMPFileFormat.inputStream = this.inputStream;
        PaletteData paletteData = winBMPFileFormat.loadPalette((byte[])object);
        byte[] byArray = winBMPFileFormat.loadData((byte[])object);
        int n = object[4] & 0xFF | (object[5] & 0xFF) << 8 | (object[6] & 0xFF) << 16 | (object[7] & 0xFF) << 24;
        int n2 = object[8] & 0xFF | (object[9] & 0xFF) << 8 | (object[10] & 0xFF) << 16 | (object[11] & 0xFF) << 24;
        if (n2 < 0) {
            n2 = -n2;
        }
        int n3 = object[14] & 0xFF | (object[15] & 0xFF) << 8;
        object[14] = true;
        object[15] = false;
        byte[] byArray2 = winBMPFileFormat.loadData((byte[])object);
        byArray2 = WinICOFileFormat.convertPad(byArray2, n, n2, 1, 4, 2);
        this.bitInvertData(byArray2, 0, byArray2.length);
        return ImageData.internal_new(n, n2, n3, paletteData, 4, byArray, 2, byArray2, null, -1, -1, 3, 0, 0, 0, 0);
    }

    int[][] loadIconHeaders(int n) {
        int[][] nArray = new int[n][7];
        try {
            for (int i = 0; i < n; ++i) {
                nArray[i][0] = this.inputStream.read();
                nArray[i][1] = this.inputStream.read();
                nArray[i][2] = this.inputStream.readShort();
                nArray[i][3] = this.inputStream.readShort();
                nArray[i][4] = this.inputStream.readShort();
                nArray[i][5] = this.inputStream.readInt();
                nArray[i][6] = this.inputStream.readInt();
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        return nArray;
    }

    byte[] loadInfoHeader(int[] nArray) {
        int n = nArray[0];
        int n2 = nArray[1];
        int n3 = nArray[2];
        if (n3 == 0) {
            n3 = 256;
        }
        if (n3 != 2 && n3 != 8 && n3 != 16 && n3 != 32 && n3 != 256) {
            SWT.error(40);
        }
        if (this.inputStream.getPosition() < nArray[6]) {
            try {
                this.inputStream.skip(nArray[6] - this.inputStream.getPosition());
            }
            catch (IOException iOException) {
                SWT.error(39, iOException);
                return null;
            }
        }
        byte[] byArray = new byte[40];
        try {
            this.inputStream.read(byArray);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        if ((byArray[12] & 0xFF | (byArray[13] & 0xFF) << 8) != 1) {
            SWT.error(40);
        }
        int n4 = byArray[4] & 0xFF | (byArray[5] & 0xFF) << 8 | (byArray[6] & 0xFF) << 16 | (byArray[7] & 0xFF) << 24;
        int n5 = byArray[8] & 0xFF | (byArray[9] & 0xFF) << 8 | (byArray[10] & 0xFF) << 16 | (byArray[11] & 0xFF) << 24;
        int n6 = byArray[14] & 0xFF | (byArray[15] & 0xFF) << 8;
        if (n == 0) {
            n = n4;
        }
        if (n2 == 0) {
            n2 = n5 / 2;
        }
        if (n2 == n5 && n6 == 1) {
            n2 /= 2;
        }
        if (n != n4 || n2 * 2 != n5 || n6 != 1 && n6 != 4 && n6 != 8 && n6 != 24 && n6 != 32) {
            SWT.error(40);
        }
        byArray[8] = (byte)(n2 & 0xFF);
        byArray[9] = (byte)(n2 >> 8 & 0xFF);
        byArray[10] = (byte)(n2 >> 16 & 0xFF);
        byArray[11] = (byte)(n2 >> 24 & 0xFF);
        return byArray;
    }

    void unloadIcon(ImageData imageData) {
        int n = ((imageData.width * imageData.depth + 31) / 32 * 4 + (imageData.width + 31) / 32 * 4) * imageData.height;
        try {
            this.outputStream.writeInt(40);
            this.outputStream.writeInt(imageData.width);
            this.outputStream.writeInt(imageData.height * 2);
            this.outputStream.writeShort(1);
            this.outputStream.writeShort((short)imageData.depth);
            this.outputStream.writeInt(0);
            this.outputStream.writeInt(n);
            this.outputStream.writeInt(0);
            this.outputStream.writeInt(0);
            this.outputStream.writeInt(imageData.palette.colors != null ? imageData.palette.colors.length : 0);
            this.outputStream.writeInt(0);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        byte[] byArray = WinBMPFileFormat.paletteToBytes(imageData.palette);
        try {
            this.outputStream.write(byArray);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
        this.unloadShapeData(imageData);
        this.unloadMaskData(imageData);
    }

    void unloadIconHeader(ImageData imageData) {
        int n = 16;
        int n2 = 22;
        int n3 = this.iconSize(imageData);
        try {
            this.outputStream.write(imageData.width);
            this.outputStream.write(imageData.height);
            this.outputStream.writeShort(imageData.palette.colors != null ? imageData.palette.colors.length : 0);
            this.outputStream.writeShort(0);
            this.outputStream.writeShort(0);
            this.outputStream.writeInt(n3);
            this.outputStream.writeInt(22);
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    void unloadIntoByteStream(ImageLoader var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl28 : ALOAD_0 - null : trying to set 0 previously set to 1
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    void unloadMaskData(ImageData imageData) {
        ImageData imageData2 = imageData.getTransparencyMask();
        int n = (imageData.width + 7) / 8;
        int n2 = imageData2.scanlinePad;
        int n3 = (n + n2 - 1) / n2 * n2;
        int n4 = (n + 3) / 4 * 4;
        byte[] byArray = new byte[n4];
        int n5 = (imageData.height - 1) * n3;
        byte[] byArray2 = imageData2.data;
        try {
            for (int i = 0; i < imageData.height; ++i) {
                System.arraycopy(byArray2, n5, byArray, 0, n);
                this.bitInvertData(byArray, 0, n);
                this.outputStream.write(byArray, 0, n4);
                n5 -= n3;
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }

    void unloadShapeData(ImageData imageData) {
        int n = (imageData.width * imageData.depth + 7) / 8;
        int n2 = imageData.scanlinePad;
        int n3 = (n + n2 - 1) / n2 * n2;
        int n4 = (n + 3) / 4 * 4;
        byte[] byArray = new byte[n4];
        int n5 = (imageData.height - 1) * n3;
        byte[] byArray2 = imageData.data;
        try {
            for (int i = 0; i < imageData.height; ++i) {
                System.arraycopy(byArray2, n5, byArray, 0, n);
                this.outputStream.write(byArray, 0, n4);
                n5 -= n3;
            }
        }
        catch (IOException iOException) {
            SWT.error(39, iOException);
        }
    }
}

