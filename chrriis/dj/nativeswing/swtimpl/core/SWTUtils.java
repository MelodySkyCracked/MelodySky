/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public class SWTUtils {
    private SWTUtils() {
    }

    public static int translateSWTKeyCode(int n) {
        switch (n) {
            case 65536: {
                return 18;
            }
            case 131072: {
                return 16;
            }
            case 262144: {
                return 17;
            }
            case 0x400000: {
                return 524;
            }
            case 0x1000001: {
                return 38;
            }
            case 0x1000002: {
                return 40;
            }
            case 0x1000003: {
                return 37;
            }
            case 0x1000004: {
                return 39;
            }
            case 0x1000005: {
                return 33;
            }
            case 0x1000006: {
                return 34;
            }
            case 0x1000007: {
                return 36;
            }
            case 0x1000008: {
                return 35;
            }
            case 0x1000009: {
                return 155;
            }
            case 8: {
                return 8;
            }
            case 13: {
                return 10;
            }
            case 127: {
                return 127;
            }
            case 27: {
                return 27;
            }
            case 10: {
                return 10;
            }
            case 9: {
                return 9;
            }
            case 0x100000A: {
                return 112;
            }
            case 0x100000B: {
                return 113;
            }
            case 0x100000C: {
                return 114;
            }
            case 0x100000D: {
                return 115;
            }
            case 0x100000E: {
                return 116;
            }
            case 0x100000F: {
                return 117;
            }
            case 0x1000010: {
                return 118;
            }
            case 0x1000011: {
                return 119;
            }
            case 0x1000012: {
                return 120;
            }
            case 0x1000013: {
                return 121;
            }
            case 0x1000014: {
                return 122;
            }
            case 0x1000015: {
                return 123;
            }
            case 0x1000016: {
                return 61440;
            }
            case 0x1000017: {
                return 61441;
            }
            case 0x1000018: {
                return 61442;
            }
            case 16777258: {
                return 106;
            }
            case 16777259: {
                return 107;
            }
            case 0x1000050: {
                return 10;
            }
            case 16777261: {
                return 109;
            }
            case 16777262: {
                return 110;
            }
            case 16777263: {
                return 111;
            }
            case 0x1000030: {
                return 96;
            }
            case 0x1000031: {
                return 97;
            }
            case 16777266: {
                return 98;
            }
            case 0x1000033: {
                return 99;
            }
            case 16777268: {
                return 100;
            }
            case 16777269: {
                return 101;
            }
            case 16777270: {
                return 102;
            }
            case 16777271: {
                return 103;
            }
            case 16777272: {
                return 104;
            }
            case 16777273: {
                return 105;
            }
            case 16777298: {
                return 20;
            }
            case 16777299: {
                return 144;
            }
            case 16777300: {
                return 145;
            }
            case 0x1000055: {
                return 19;
            }
            case 16777302: {
                return 3;
            }
            case 16777303: {
                return 154;
            }
            case 0x1000051: {
                return 156;
            }
        }
        return 0;
    }

    public static int translateSWTMouseButton(int n) {
        switch (n) {
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
        }
        return 0;
    }

    public static int translateSWTModifiers(int n) {
        int n2 = 0;
        if ((n & 0x20000) != 0) {
            n2 |= 1;
        }
        if ((n & 0x40000) != 0) {
            n2 |= 2;
        }
        if ((n & 0x10000) != 0) {
            n2 |= 8;
        }
        return n2;
    }

    public static ImageData convertAWTImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        ColorModel colorModel = bufferedImage.getColorModel();
        PaletteData paletteData = new PaletteData(0xFF0000, 65280, 255);
        int n = bufferedImage.getWidth();
        ImageData imageData = new ImageData(n, bufferedImage.getHeight(), colorModel.getPixelSize(), paletteData);
        int n2 = bufferedImage.getHeight();
        byte[] byArray = new byte[(n + 7) / 8 * n2];
        for (int i = n - 1; i >= 0; --i) {
            for (int j = n2 - 1; j >= 0; --j) {
                int n3 = bufferedImage.getRGB(i, j);
                int n4 = paletteData.getPixel(new RGB(n3 >> 16 & 0xFF, n3 >> 8 & 0xFF, n3 & 0xFF));
                imageData.setPixel(i, j, n4);
                int n5 = n3 >> 24 & 0xFF;
                imageData.setAlpha(i, j, n5);
                if (n5 == 0) continue;
                int n6 = i + j * ((n + 7) / 8) * 8;
                int n7 = n6 / 8;
                byArray[n7] = (byte)(byArray[n7] | (byte)(1 << 7 - n6 % 8));
            }
        }
        imageData.maskPad = 1;
        imageData.maskData = byArray;
        return imageData;
    }

    public static BufferedImage convertSWTImage(ImageData imageData) {
        Object object;
        IndexColorModel indexColorModel = null;
        PaletteData paletteData = imageData.palette;
        if (paletteData.isDirect) {
            BufferedImage bufferedImage = new BufferedImage(imageData.width, imageData.height, 2);
            ImageData imageData2 = imageData.getTransparencyMask();
            for (int i = imageData.width - 1; i >= 0; --i) {
                for (int j = imageData.height - 1; j >= 0; --j) {
                    int n;
                    RGB rGB = imageData.palette.getRGB(imageData.getPixel(i, j));
                    int n2 = rGB.red << 16 | rGB.green << 8 | rGB.blue;
                    rGB = imageData2.palette.getRGB(imageData2.getPixel(i, j));
                    int n3 = rGB.red << 16 | rGB.green << 8 | rGB.blue;
                    if (n3 == 0 || (n = imageData.getAlpha(i, j)) <= 0) continue;
                    n2 = n2 & 0xFFFFFF | n << 24;
                    bufferedImage.setRGB(i, j, n2);
                }
            }
            return bufferedImage;
        }
        RGB[] rGBArray = paletteData.getRGBs();
        byte[] byArray = new byte[rGBArray.length];
        byte[] byArray2 = new byte[rGBArray.length];
        byte[] byArray3 = new byte[rGBArray.length];
        for (int i = 0; i < rGBArray.length; ++i) {
            object = rGBArray[i];
            byArray[i] = (byte)((RGB)object).red;
            byArray2[i] = (byte)((RGB)object).green;
            byArray3[i] = (byte)((RGB)object).blue;
        }
        indexColorModel = imageData.transparentPixel != -1 ? new IndexColorModel(imageData.depth, rGBArray.length, byArray, byArray2, byArray3, imageData.transparentPixel) : new IndexColorModel(imageData.depth, rGBArray.length, byArray, byArray2, byArray3);
        BufferedImage bufferedImage = new BufferedImage(indexColorModel, ((ColorModel)indexColorModel).createCompatibleWritableRaster(imageData.width, imageData.height), false, null);
        object = bufferedImage.getRaster();
        int[] nArray = new int[1];
        for (int i = 0; i < imageData.height; ++i) {
            for (int j = 0; j < imageData.width; ++j) {
                int n;
                nArray[0] = n = imageData.getPixel(j, i);
                ((WritableRaster)object).setPixel(j, i, nArray);
            }
        }
        return bufferedImage;
    }
}

