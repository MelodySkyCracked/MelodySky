/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageDataLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.DPIUtil;

public final class ImageData
implements Cloneable {
    public int width;
    public int height;
    public int depth;
    public int scanlinePad;
    public int bytesPerLine;
    public byte[] data;
    public PaletteData palette;
    public int transparentPixel;
    public byte[] maskData;
    public int maskPad;
    public byte[] alphaData;
    public int alpha;
    public int type;
    public int x;
    public int y;
    public int disposalMethod;
    public int delayTime;
    static final byte[][] ANY_TO_EIGHT = new byte[9][];
    static final byte[] ONE_TO_ONE_MAPPING;
    static final int[][] DITHER_MATRIX;
    static final int BLIT_SRC = 1;
    static final int BLIT_ALPHA = 2;
    static final int BLIT_DITHER = 4;
    static final int ALPHA_OPAQUE = 255;
    static final int ALPHA_TRANSPARENT = 0;
    static final int ALPHA_CHANNEL_SEPARATE = -1;
    static final int ALPHA_CHANNEL_SOURCE = -2;
    static final int ALPHA_MASK_UNPACKED = -3;
    static final int ALPHA_MASK_PACKED = -4;
    static final int ALPHA_MASK_INDEX = -5;
    static final int ALPHA_MASK_RGB = -6;
    static final int LSB_FIRST = 0;
    static final int MSB_FIRST = 1;
    private static final int TYPE_GENERIC_8 = 0;
    private static final int TYPE_GENERIC_16_MSB = 1;
    private static final int TYPE_GENERIC_16_LSB = 2;
    private static final int TYPE_GENERIC_24 = 3;
    private static final int TYPE_GENERIC_32_MSB = 4;
    private static final int TYPE_GENERIC_32_LSB = 5;
    private static final int TYPE_INDEX_8 = 6;
    private static final int TYPE_INDEX_4 = 7;
    private static final int TYPE_INDEX_2 = 8;
    private static final int TYPE_INDEX_1_MSB = 9;
    private static final int TYPE_INDEX_1_LSB = 10;

    public ImageData(int n, int n2, int n3, PaletteData paletteData) {
        this(n, n2, n3, paletteData, 4, null, 0, null, null, -1, -1, -1, 0, 0, 0, 0);
    }

    public ImageData(int n, int n2, int n3, PaletteData paletteData, int n4, byte[] byArray) {
        this(n, n2, n3, paletteData, n4, ImageData.checkData(byArray), 0, null, null, -1, -1, -1, 0, 0, 0, 0);
    }

    public ImageData(InputStream inputStream) {
        ImageData[] imageDataArray = ImageDataLoader.load(inputStream);
        if (imageDataArray.length < 1) {
            SWT.error(40);
        }
        ImageData imageData = imageDataArray[0];
        this.setAllFields(imageData.width, imageData.height, imageData.depth, imageData.scanlinePad, imageData.bytesPerLine, imageData.data, imageData.palette, imageData.transparentPixel, imageData.maskData, imageData.maskPad, imageData.alphaData, imageData.alpha, imageData.type, imageData.x, imageData.y, imageData.disposalMethod, imageData.delayTime);
    }

    public ImageData(String string) {
        ImageData[] imageDataArray = ImageDataLoader.load(string);
        if (imageDataArray.length < 1) {
            SWT.error(40);
        }
        ImageData imageData = imageDataArray[0];
        this.setAllFields(imageData.width, imageData.height, imageData.depth, imageData.scanlinePad, imageData.bytesPerLine, imageData.data, imageData.palette, imageData.transparentPixel, imageData.maskData, imageData.maskPad, imageData.alphaData, imageData.alpha, imageData.type, imageData.x, imageData.y, imageData.disposalMethod, imageData.delayTime);
    }

    ImageData() {
    }

    ImageData(int n, int n2, int n3, PaletteData paletteData, int n4, byte[] byArray, int n5, byte[] byArray2, byte[] byArray3, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        int n13;
        if (paletteData == null) {
            SWT.error(4);
        }
        if (n3 != 1 && n3 != 2 && n3 != 4 && n3 != 8 && n3 != 16 && n3 != 24 && n3 != 32) {
            SWT.error(5);
        }
        if (n <= 0 || n2 <= 0) {
            SWT.error(5);
        }
        if (n4 == 0) {
            SWT.error(7);
        }
        int n14 = ((n * n3 + 7) / 8 + (n4 - 1)) / n4 * n4;
        int n15 = n13 = n8 == 5 ? ((n + 7) / 8 + 3) / 4 * 4 : n14;
        if (byArray != null && byArray.length < n13 * n2) {
            SWT.error(5);
        }
        this.setAllFields(n, n2, n3, n4, n14, byArray != null ? byArray : new byte[n14 * n2], paletteData, n7, byArray2, n5, byArray3, n6, n8, n9, n10, n11, n12);
    }

    void setAllFields(int n, int n2, int n3, int n4, int n5, byte[] byArray, PaletteData paletteData, int n6, byte[] byArray2, int n7, byte[] byArray3, int n8, int n9, int n10, int n11, int n12, int n13) {
        this.width = n;
        this.height = n2;
        this.depth = n3;
        this.scanlinePad = n4;
        this.bytesPerLine = n5;
        this.data = byArray;
        this.palette = paletteData;
        this.transparentPixel = n6;
        this.maskData = byArray2;
        this.maskPad = n7;
        this.alphaData = byArray3;
        this.alpha = n8;
        this.type = n9;
        this.x = n10;
        this.y = n11;
        this.disposalMethod = n12;
        this.delayTime = n13;
    }

    public static ImageData internal_new(int n, int n2, int n3, PaletteData paletteData, int n4, byte[] byArray, int n5, byte[] byArray2, byte[] byArray3, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        return new ImageData(n, n2, n3, paletteData, n4, byArray, n5, byArray2, byArray3, n6, n7, n8, n9, n10, n11, n12);
    }

    ImageData colorMaskImage(int n) {
        ImageData imageData = new ImageData(this.width, this.height, 1, ImageData.bwPalette(), 2, null, 0, null, null, -1, -1, -1, 0, 0, 0, 0);
        int[] nArray = new int[this.width];
        for (int i = 0; i < this.height; ++i) {
            this.getPixels(0, i, this.width, nArray, 0);
            for (int j = 0; j < this.width; ++j) {
                nArray[j] = n != -1 && nArray[j] == n ? 0 : 1;
            }
            imageData.setPixels(0, i, this.width, nArray, 0);
        }
        return imageData;
    }

    static byte[] checkData(byte[] byArray) {
        if (byArray == null) {
            SWT.error(4);
        }
        return byArray;
    }

    public Object clone() {
        byte[] byArray = new byte[this.data.length];
        System.arraycopy(this.data, 0, byArray, 0, this.data.length);
        byte[] byArray2 = null;
        if (this.maskData != null) {
            byArray2 = new byte[this.maskData.length];
            System.arraycopy(this.maskData, 0, byArray2, 0, this.maskData.length);
        }
        byte[] byArray3 = null;
        if (this.alphaData != null) {
            byArray3 = new byte[this.alphaData.length];
            System.arraycopy(this.alphaData, 0, byArray3, 0, this.alphaData.length);
        }
        return new ImageData(this.width, this.height, this.depth, this.palette, this.scanlinePad, byArray, this.maskPad, byArray2, byArray3, this.alpha, this.transparentPixel, this.type, this.x, this.y, this.disposalMethod, this.delayTime);
    }

    public int getAlpha(int n, int n2) {
        if (n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (this.alphaData == null) {
            return 255;
        }
        return this.alphaData[n2 * this.width + n] & 0xFF;
    }

    public void getAlphas(int n, int n2, int n3, byte[] byArray, int n4) {
        if (byArray == null) {
            SWT.error(4);
        }
        if (n3 < 0 || n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (n3 == 0) {
            return;
        }
        if (this.alphaData == null) {
            int n5 = n4 + n3;
            for (int i = n4; i < n5; ++i) {
                byArray[i] = -1;
            }
            return;
        }
        System.arraycopy(this.alphaData, n2 * this.width + n, byArray, n4, n3);
    }

    public int getPixel(int n, int n2) {
        if (n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        switch (this.depth) {
            case 32: {
                int n3 = n2 * this.bytesPerLine + n * 4;
                return ((this.data[n3] & 0xFF) << 24) + ((this.data[n3 + 1] & 0xFF) << 16) + ((this.data[n3 + 2] & 0xFF) << 8) + (this.data[n3 + 3] & 0xFF);
            }
            case 24: {
                int n4 = n2 * this.bytesPerLine + n * 3;
                return ((this.data[n4] & 0xFF) << 16) + ((this.data[n4 + 1] & 0xFF) << 8) + (this.data[n4 + 2] & 0xFF);
            }
            case 16: {
                int n5 = n2 * this.bytesPerLine + n * 2;
                return ((this.data[n5 + 1] & 0xFF) << 8) + (this.data[n5] & 0xFF);
            }
            case 8: {
                int n6 = n2 * this.bytesPerLine + n;
                return this.data[n6] & 0xFF;
            }
            case 4: {
                int n7 = n2 * this.bytesPerLine + (n >> 1);
                int n8 = this.data[n7] & 0xFF;
                if ((n & 1) == 0) {
                    return n8 >> 4;
                }
                return n8 & 0xF;
            }
            case 2: {
                int n9 = n2 * this.bytesPerLine + (n >> 2);
                int n10 = this.data[n9] & 0xFF;
                int n11 = 3 - n % 4;
                int n12 = 3 << n11 * 2;
                return (n10 & n12) >> n11 * 2;
            }
            case 1: {
                int n13 = n2 * this.bytesPerLine + (n >> 3);
                int n14 = this.data[n13] & 0xFF;
                int n15 = 1 << 7 - (n & 7);
                if ((n14 & n15) == 0) {
                    return 0;
                }
                return 1;
            }
        }
        SWT.error(38);
        return 0;
    }

    public void getPixels(int n, int n2, int n3, byte[] byArray, int n4) {
        if (byArray == null) {
            SWT.error(4);
        }
        if (n3 < 0 || n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (n3 == 0) {
            return;
        }
        int n5 = 0;
        int n6 = n3;
        int n7 = n4;
        int n8 = n;
        int n9 = n2;
        switch (this.depth) {
            case 8: {
                int n10 = n2 * this.bytesPerLine + n;
                for (int i = 0; i < n3; ++i) {
                    byArray[n7] = this.data[n10];
                    ++n7;
                    if (++n8 >= this.width) {
                        n10 = ++n9 * this.bytesPerLine;
                        n8 = 0;
                        continue;
                    }
                    ++n10;
                }
                return;
            }
            case 4: {
                int n11;
                int n12 = n2 * this.bytesPerLine + (n >> 1);
                if ((n & 1) == 1) {
                    n11 = this.data[n12] & 0xFF;
                    byArray[n7] = (byte)(n11 & 0xF);
                    ++n7;
                    --n6;
                    if (++n8 >= this.width) {
                        n12 = ++n9 * this.bytesPerLine;
                        n8 = 0;
                    } else {
                        ++n12;
                    }
                }
                while (n6 > 1) {
                    n11 = this.data[n12] & 0xFF;
                    byArray[n7] = (byte)(n11 >> 4);
                    ++n7;
                    --n6;
                    if (++n8 >= this.width) {
                        n12 = ++n9 * this.bytesPerLine;
                        n8 = 0;
                        continue;
                    }
                    byArray[n7] = (byte)(n11 & 0xF);
                    ++n7;
                    --n6;
                    if (++n8 >= this.width) {
                        n12 = ++n9 * this.bytesPerLine;
                        n8 = 0;
                        continue;
                    }
                    ++n12;
                }
                if (n6 > 0) {
                    n11 = this.data[n12] & 0xFF;
                    byArray[n7] = (byte)(n11 >> 4);
                }
                return;
            }
            case 2: {
                int n13 = n2 * this.bytesPerLine + (n >> 2);
                int n14 = this.data[n13] & 0xFF;
                while (n6 > 0) {
                    int n15 = 3 - n8 % 4;
                    n5 = 3 << n15 * 2;
                    byArray[n7] = (byte)((n14 & n5) >> n15 * 2);
                    ++n7;
                    --n6;
                    if (++n8 >= this.width) {
                        n13 = ++n9 * this.bytesPerLine;
                        if (n6 > 0) {
                            n14 = this.data[n13] & 0xFF;
                        }
                        n8 = 0;
                        continue;
                    }
                    if (n15 != 0) continue;
                    n14 = this.data[++n13] & 0xFF;
                }
                return;
            }
            case 1: {
                int n16 = n2 * this.bytesPerLine + (n >> 3);
                int n17 = this.data[n16] & 0xFF;
                while (n6 > 0) {
                    n5 = 1 << 7 - (n8 & 7);
                    byArray[n7] = (n17 & n5) == 0 ? (byte)0 : 1;
                    ++n7;
                    --n6;
                    if (++n8 >= this.width) {
                        n16 = ++n9 * this.bytesPerLine;
                        if (n6 > 0) {
                            n17 = this.data[n16] & 0xFF;
                        }
                        n8 = 0;
                        continue;
                    }
                    if (n5 != 1) continue;
                    ++n16;
                    if (n6 <= 0) continue;
                    n17 = this.data[n16] & 0xFF;
                }
                return;
            }
        }
        SWT.error(38);
    }

    public void getPixels(int n, int n2, int n3, int[] nArray, int n4) {
        if (nArray == null) {
            SWT.error(4);
        }
        if (n3 < 0 || n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (n3 == 0) {
            return;
        }
        int n5 = n3;
        int n6 = n4;
        int n7 = n;
        int n8 = n2;
        switch (this.depth) {
            case 32: {
                int n9 = n2 * this.bytesPerLine + n * 4;
                n6 = n4;
                for (int i = 0; i < n3; ++i) {
                    nArray[n6] = (this.data[n9] & 0xFF) << 24 | (this.data[n9 + 1] & 0xFF) << 16 | (this.data[n9 + 2] & 0xFF) << 8 | this.data[n9 + 3] & 0xFF;
                    ++n6;
                    if (++n7 >= this.width) {
                        n9 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    n9 += 4;
                }
                return;
            }
            case 24: {
                int n10 = n2 * this.bytesPerLine + n * 3;
                for (int i = 0; i < n3; ++i) {
                    nArray[n6] = (this.data[n10] & 0xFF) << 16 | (this.data[n10 + 1] & 0xFF) << 8 | this.data[n10 + 2] & 0xFF;
                    ++n6;
                    if (++n7 >= this.width) {
                        n10 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    n10 += 3;
                }
                return;
            }
            case 16: {
                int n11 = n2 * this.bytesPerLine + n * 2;
                for (int i = 0; i < n3; ++i) {
                    nArray[n6] = ((this.data[n11 + 1] & 0xFF) << 8) + (this.data[n11] & 0xFF);
                    ++n6;
                    if (++n7 >= this.width) {
                        n11 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    n11 += 2;
                }
                return;
            }
            case 8: {
                int n12 = n2 * this.bytesPerLine + n;
                for (int i = 0; i < n3; ++i) {
                    nArray[n6] = this.data[n12] & 0xFF;
                    ++n6;
                    if (++n7 >= this.width) {
                        n12 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    ++n12;
                }
                return;
            }
            case 4: {
                int n13;
                int n14 = n2 * this.bytesPerLine + (n >> 1);
                if ((n & 1) == 1) {
                    n13 = this.data[n14] & 0xFF;
                    nArray[n6] = n13 & 0xF;
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n14 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                    } else {
                        ++n14;
                    }
                }
                while (n5 > 1) {
                    n13 = this.data[n14] & 0xFF;
                    nArray[n6] = n13 >> 4;
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n14 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    nArray[n6] = n13 & 0xF;
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n14 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    ++n14;
                }
                if (n5 > 0) {
                    n13 = this.data[n14] & 0xFF;
                    nArray[n6] = n13 >> 4;
                }
                return;
            }
            case 2: {
                int n15 = n2 * this.bytesPerLine + (n >> 2);
                int n16 = this.data[n15] & 0xFF;
                while (n5 > 0) {
                    int n17 = 3 - n7 % 4;
                    int n18 = 3 << n17 * 2;
                    nArray[n6] = (byte)((n16 & n18) >> n17 * 2);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n15 = ++n8 * this.bytesPerLine;
                        if (n5 > 0) {
                            n16 = this.data[n15] & 0xFF;
                        }
                        n7 = 0;
                        continue;
                    }
                    if (n17 != 0) continue;
                    n16 = this.data[++n15] & 0xFF;
                }
                return;
            }
            case 1: {
                int n19 = n2 * this.bytesPerLine + (n >> 3);
                int n20 = this.data[n19] & 0xFF;
                while (n5 > 0) {
                    int n21 = 1 << 7 - (n7 & 7);
                    nArray[n6] = (n20 & n21) == 0 ? 0 : 1;
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n19 = ++n8 * this.bytesPerLine;
                        if (n5 > 0) {
                            n20 = this.data[n19] & 0xFF;
                        }
                        n7 = 0;
                        continue;
                    }
                    if (n21 != 1) continue;
                    ++n19;
                    if (n5 <= 0) continue;
                    n20 = this.data[n19] & 0xFF;
                }
                return;
            }
        }
        SWT.error(38);
    }

    public RGB[] getRGBs() {
        return this.palette.getRGBs();
    }

    public ImageData getTransparencyMask() {
        int n = this.getTransparencyType();
        switch (n) {
            case 1: {
                return this.getTransparencyMaskFromAlphaData();
            }
            case 2: {
                return new ImageData(this.width, this.height, 1, ImageData.bwPalette(), this.maskPad, this.maskData);
            }
            case 4: {
                return this.colorMaskImage(this.transparentPixel);
            }
        }
        return this.colorMaskImage(this.transparentPixel);
    }

    ImageData getTransparencyMaskFromAlphaData() {
        ImageData imageData = new ImageData(this.width, this.height, 1, ImageData.bwPalette(), 2, null, 0, null, null, -1, -1, -1, 0, 0, 0, 0);
        int n = 0;
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                byte by;
                if ((by = this.alphaData[n++]) == 0) {
                    imageData.setPixel(j, i, 0);
                    continue;
                }
                imageData.setPixel(j, i, 1);
            }
        }
        return imageData;
    }

    public int getTransparencyType() {
        if (this.maskData != null) {
            return 2;
        }
        if (this.transparentPixel != -1) {
            return 4;
        }
        if (this.alphaData != null) {
            return 1;
        }
        return 0;
    }

    int getByteOrder() {
        return this.depth != 16 ? 1 : 0;
    }

    public ImageData scaledTo(int n, int n2) {
        boolean bl;
        boolean bl2;
        boolean bl3 = bl2 = n < 0;
        if (bl2) {
            n = -n;
        }
        boolean bl4 = bl = n2 < 0;
        if (bl) {
            n2 = -n2;
        }
        ImageData imageData = new ImageData(n, n2, this.depth, this.palette, this.scanlinePad, null, 0, null, null, -1, this.transparentPixel, this.type, this.x, this.y, this.disposalMethod, this.delayTime);
        if (this.palette.isDirect) {
            ImageData.blit(1, this.data, this.depth, this.bytesPerLine, this.getByteOrder(), 0, 0, this.width, this.height, 0, 0, 0, 255, null, 0, 0, 0, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, 0, 0, 0, bl2, bl);
        } else {
            ImageData.blit(1, this.data, this.depth, this.bytesPerLine, this.getByteOrder(), 0, 0, this.width, this.height, null, null, null, 255, null, 0, 0, 0, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, null, null, null, bl2, bl);
        }
        if (this.maskData != null) {
            imageData.maskPad = this.maskPad;
            int n3 = (imageData.width + 7) / 8;
            n3 = (n3 + (imageData.maskPad - 1)) / imageData.maskPad * imageData.maskPad;
            imageData.maskData = new byte[n3 * imageData.height];
            int n4 = (this.width + 7) / 8;
            n4 = (n4 + (this.maskPad - 1)) / this.maskPad * this.maskPad;
            ImageData.blit(1, this.maskData, 1, n4, 1, 0, 0, this.width, this.height, null, null, null, 255, null, 0, 0, 0, imageData.maskData, 1, n3, 1, 0, 0, imageData.width, imageData.height, null, null, null, bl2, bl);
        } else if (this.alpha != -1) {
            imageData.alpha = this.alpha;
        } else if (this.alphaData != null) {
            imageData.alphaData = new byte[imageData.width * imageData.height];
            ImageData.blit(1, this.alphaData, 8, this.width, 1, 0, 0, this.width, this.height, null, null, null, 255, null, 0, 0, 0, imageData.alphaData, 8, imageData.width, 1, 0, 0, imageData.width, imageData.height, null, null, null, bl2, bl);
        }
        return imageData;
    }

    public void setAlpha(int n, int n2, int n3) {
        if (n >= this.width || n2 >= this.height || n < 0 || n2 < 0 || n3 < 0 || n3 > 255) {
            SWT.error(5);
        }
        if (this.alphaData == null) {
            this.alphaData = new byte[this.width * this.height];
        }
        this.alphaData[n2 * this.width + n] = (byte)n3;
    }

    public void setAlphas(int n, int n2, int n3, byte[] byArray, int n4) {
        if (byArray == null) {
            SWT.error(4);
        }
        if (n3 < 0 || n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (n3 == 0) {
            return;
        }
        if (this.alphaData == null) {
            this.alphaData = new byte[this.width * this.height];
        }
        System.arraycopy(byArray, n4, this.alphaData, n2 * this.width + n, n3);
    }

    public void setPixel(int n, int n2, int n3) {
        if (n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        switch (this.depth) {
            case 32: {
                int n4 = n2 * this.bytesPerLine + n * 4;
                this.data[n4] = (byte)(n3 >> 24 & 0xFF);
                this.data[n4 + 1] = (byte)(n3 >> 16 & 0xFF);
                this.data[n4 + 2] = (byte)(n3 >> 8 & 0xFF);
                this.data[n4 + 3] = (byte)(n3 & 0xFF);
                return;
            }
            case 24: {
                int n5 = n2 * this.bytesPerLine + n * 3;
                this.data[n5] = (byte)(n3 >> 16 & 0xFF);
                this.data[n5 + 1] = (byte)(n3 >> 8 & 0xFF);
                this.data[n5 + 2] = (byte)(n3 & 0xFF);
                return;
            }
            case 16: {
                int n6 = n2 * this.bytesPerLine + n * 2;
                this.data[n6 + 1] = (byte)(n3 >> 8 & 0xFF);
                this.data[n6] = (byte)(n3 & 0xFF);
                return;
            }
            case 8: {
                int n7 = n2 * this.bytesPerLine + n;
                this.data[n7] = (byte)(n3 & 0xFF);
                return;
            }
            case 4: {
                int n8 = n2 * this.bytesPerLine + (n >> 1);
                this.data[n8] = (n & 1) == 0 ? (byte)(this.data[n8] & 0xF | (n3 & 0xF) << 4) : (byte)(this.data[n8] & 0xF0 | n3 & 0xF);
                return;
            }
            case 2: {
                int n9 = n2 * this.bytesPerLine + (n >> 2);
                byte by = this.data[n9];
                int n10 = 3 - n % 4;
                int n11 = 0xFF ^ 3 << n10 * 2;
                this.data[n9] = (byte)(this.data[n9] & n11 | n3 << n10 * 2);
                return;
            }
            case 1: {
                int n12 = n2 * this.bytesPerLine + (n >> 3);
                byte by = this.data[n12];
                int n13 = 1 << 7 - (n & 7);
                this.data[n12] = (n3 & 1) == 1 ? (byte)(by | n13) : (byte)(by & ~n13);
                return;
            }
        }
        SWT.error(38);
    }

    public void setPixels(int n, int n2, int n3, byte[] byArray, int n4) {
        if (byArray == null) {
            SWT.error(4);
        }
        if (n3 < 0 || n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (n3 == 0) {
            return;
        }
        int n5 = n3;
        int n6 = n4;
        int n7 = n;
        int n8 = n2;
        switch (this.depth) {
            case 8: {
                int n9 = n2 * this.bytesPerLine + n;
                for (int i = 0; i < n3; ++i) {
                    this.data[n9] = (byte)(byArray[n6] & 0xFF);
                    ++n6;
                    if (++n7 >= this.width) {
                        n9 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    ++n9;
                }
                return;
            }
            case 4: {
                boolean bl;
                int n10 = n2 * this.bytesPerLine + (n >> 1);
                boolean bl2 = bl = (n & 1) == 0;
                while (n5 > 0) {
                    int n11 = byArray[n6] & 0xF;
                    this.data[n10] = bl ? (byte)(this.data[n10] & 0xF | n11 << 4) : (byte)(this.data[n10] & 0xF0 | n11);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n10 = ++n8 * this.bytesPerLine;
                        bl = true;
                        n7 = 0;
                        continue;
                    }
                    if (!bl) {
                        ++n10;
                    }
                    bl = !bl;
                }
                return;
            }
            case 2: {
                byte[] byArray2 = new byte[]{-4, -13, -49, 63};
                int n12 = n2 * this.bytesPerLine + (n >> 2);
                int n13 = 3 - n % 4;
                while (n5 > 0) {
                    int n14 = byArray[n6] & 3;
                    this.data[n12] = (byte)(this.data[n12] & byArray2[n13] | n14 << n13 * 2);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n12 = ++n8 * this.bytesPerLine;
                        n13 = 0;
                        n7 = 0;
                        continue;
                    }
                    if (n13 == 0) {
                        ++n12;
                        n13 = 3;
                        continue;
                    }
                    --n13;
                }
                return;
            }
            case 1: {
                int n15 = n2 * this.bytesPerLine + (n >> 3);
                while (n5 > 0) {
                    int n16 = 1 << 7 - (n7 & 7);
                    this.data[n15] = (byArray[n6] & 1) == 1 ? (byte)(this.data[n15] & 0xFF | n16) : (byte)(this.data[n15] & 0xFF & ~n16);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n15 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    if (n16 != 1) continue;
                    ++n15;
                }
                return;
            }
        }
        SWT.error(38);
    }

    public void setPixels(int n, int n2, int n3, int[] nArray, int n4) {
        if (nArray == null) {
            SWT.error(4);
        }
        if (n3 < 0 || n >= this.width || n2 >= this.height || n < 0 || n2 < 0) {
            SWT.error(5);
        }
        if (n3 == 0) {
            return;
        }
        int n5 = n3;
        int n6 = n4;
        int n7 = n;
        int n8 = n2;
        switch (this.depth) {
            case 32: {
                int n9 = n2 * this.bytesPerLine + n * 4;
                for (int i = 0; i < n3; ++i) {
                    int n10 = nArray[n6];
                    this.data[n9] = (byte)(n10 >> 24 & 0xFF);
                    this.data[n9 + 1] = (byte)(n10 >> 16 & 0xFF);
                    this.data[n9 + 2] = (byte)(n10 >> 8 & 0xFF);
                    this.data[n9 + 3] = (byte)(n10 & 0xFF);
                    ++n6;
                    if (++n7 >= this.width) {
                        n9 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    n9 += 4;
                }
                return;
            }
            case 24: {
                int n11 = n2 * this.bytesPerLine + n * 3;
                for (int i = 0; i < n3; ++i) {
                    int n12 = nArray[n6];
                    this.data[n11] = (byte)(n12 >> 16 & 0xFF);
                    this.data[n11 + 1] = (byte)(n12 >> 8 & 0xFF);
                    this.data[n11 + 2] = (byte)(n12 & 0xFF);
                    ++n6;
                    if (++n7 >= this.width) {
                        n11 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    n11 += 3;
                }
                return;
            }
            case 16: {
                int n13 = n2 * this.bytesPerLine + n * 2;
                for (int i = 0; i < n3; ++i) {
                    int n14 = nArray[n6];
                    this.data[n13] = (byte)(n14 & 0xFF);
                    this.data[n13 + 1] = (byte)(n14 >> 8 & 0xFF);
                    ++n6;
                    if (++n7 >= this.width) {
                        n13 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    n13 += 2;
                }
                return;
            }
            case 8: {
                int n15 = n2 * this.bytesPerLine + n;
                for (int i = 0; i < n3; ++i) {
                    this.data[n15] = (byte)(nArray[n6] & 0xFF);
                    ++n6;
                    if (++n7 >= this.width) {
                        n15 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    ++n15;
                }
                return;
            }
            case 4: {
                boolean bl;
                int n16 = n2 * this.bytesPerLine + (n >> 1);
                boolean bl2 = bl = (n & 1) == 0;
                while (n5 > 0) {
                    int n17 = nArray[n6] & 0xF;
                    this.data[n16] = bl ? (byte)(this.data[n16] & 0xF | n17 << 4) : (byte)(this.data[n16] & 0xF0 | n17);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n16 = ++n8 * this.bytesPerLine;
                        bl = true;
                        n7 = 0;
                        continue;
                    }
                    if (!bl) {
                        ++n16;
                    }
                    bl = !bl;
                }
                return;
            }
            case 2: {
                byte[] byArray = new byte[]{-4, -13, -49, 63};
                int n18 = n2 * this.bytesPerLine + (n >> 2);
                int n19 = 3 - n % 4;
                while (n5 > 0) {
                    int n20 = nArray[n6] & 3;
                    this.data[n18] = (byte)(this.data[n18] & byArray[n19] | n20 << n19 * 2);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n18 = ++n8 * this.bytesPerLine;
                        n19 = 3;
                        n7 = 0;
                        continue;
                    }
                    if (n19 == 0) {
                        ++n18;
                        n19 = 3;
                        continue;
                    }
                    --n19;
                }
                return;
            }
            case 1: {
                int n21 = n2 * this.bytesPerLine + (n >> 3);
                while (n5 > 0) {
                    int n22 = 1 << 7 - (n7 & 7);
                    this.data[n21] = (nArray[n6] & 1) == 1 ? (byte)(this.data[n21] & 0xFF | n22) : (byte)(this.data[n21] & 0xFF & ~n22);
                    ++n6;
                    --n5;
                    if (++n7 >= this.width) {
                        n21 = ++n8 * this.bytesPerLine;
                        n7 = 0;
                        continue;
                    }
                    if (n22 != 1) continue;
                    ++n21;
                }
                return;
            }
        }
        SWT.error(38);
    }

    static PaletteData bwPalette() {
        return new PaletteData(new RGB(0, 0, 0), new RGB(255, 255, 255));
    }

    static int getMSBOffset(int n) {
        for (int i = 31; i >= 0; --i) {
            if ((n >> i & 1) == 0) continue;
            return i + 1;
        }
        return 0;
    }

    static int closestMatch(int n, byte by, byte by2, byte by3, int n2, int n3, int n4, byte[] byArray, byte[] byArray2, byte[] byArray3) {
        if (n > 8) {
            int n5 = 32 - ImageData.getMSBOffset(n2);
            int n6 = 32 - ImageData.getMSBOffset(n3);
            int n7 = 32 - ImageData.getMSBOffset(n4);
            return by << 24 >>> n5 & n2 | by2 << 24 >>> n6 & n3 | by3 << 24 >>> n7 & n4;
        }
        int n8 = Integer.MAX_VALUE;
        int n9 = 0;
        int n10 = byArray.length;
        for (int i = 0; i < n10; ++i) {
            int n11 = (byArray[i] & 0xFF) - (by & 0xFF);
            int n12 = (byArray2[i] & 0xFF) - (by2 & 0xFF);
            int n13 = (byArray3[i] & 0xFF) - (by3 & 0xFF);
            int n14 = n11 * n11 + n12 * n12 + n13 * n13;
            if (n14 >= n8) continue;
            n9 = i;
            if (n14 == 0) break;
            n8 = n14;
        }
        return n9;
    }

    static ImageData convertMask(ImageData imageData) {
        int n;
        if (imageData.depth == 1) {
            return imageData;
        }
        PaletteData paletteData = new PaletteData(new RGB(0, 0, 0), new RGB(255, 255, 255));
        ImageData imageData2 = new ImageData(imageData.width, imageData.height, 1, paletteData);
        RGB[] rGBArray = imageData.getRGBs();
        if (rGBArray != null) {
            for (n = 0; n < rGBArray.length && !rGBArray[n].equals(paletteData.colors[0]); ++n) {
            }
        }
        int[] nArray = new int[imageData.width];
        for (int i = 0; i < imageData.height; ++i) {
            imageData.getPixels(0, i, imageData.width, nArray, 0);
            for (int j = 0; j < nArray.length; ++j) {
                nArray[j] = nArray[j] == n ? 0 : 1;
            }
            imageData2.setPixels(0, i, imageData.width, nArray, 0);
        }
        return imageData2;
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
            System.arraycopy(byArray, n9, byArray2, n10, n6);
            n9 += n7;
            n10 += n8;
        }
        return byArray2;
    }

    static void blit(int n, byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, byte[] byArray2, int n13, int n14, int n15, byte[] byArray3, int n16, int n17, int n18, int n19, int n20, int n21, int n22, int n23, int n24, int n25, boolean bl, boolean bl2) {
        if (n21 <= 0 || n22 <= 0 || n12 == 0) {
            return;
        }
        boolean bl3 = false;
        boolean bl4 = false;
        int n26 = n21 - 1;
        int n27 = n26 != 0 ? (int)((((long)n7 << 16) - 1L) / (long)n26) : 0;
        int n28 = n22 - 1;
        int n29 = n28 != 0 ? (int)((((long)n8 << 16) - 1L) / (long)n28) : 0;
        int n30 = 0;
        int n31 = 0;
        switch (n2) {
            case 8: {
                n30 = 1;
                n31 = 0;
                break;
            }
            case 16: {
                n30 = 2;
                n31 = n4 == 1 ? 1 : 2;
                break;
            }
            case 24: {
                n30 = 3;
                n31 = 3;
                break;
            }
            case 32: {
                n30 = 4;
                n31 = n4 == 1 ? 4 : 5;
                break;
            }
            default: {
                return;
            }
        }
        int n32 = n6 * n3 + n5 * n30;
        int n33 = 0;
        int n34 = 0;
        switch (n16) {
            case 8: {
                n33 = 1;
                n34 = 0;
                break;
            }
            case 16: {
                n33 = 2;
                n34 = n18 == 1 ? 1 : 2;
                break;
            }
            case 24: {
                n33 = 3;
                n34 = 3;
                break;
            }
            case 32: {
                n33 = 4;
                n34 = n18 == 1 ? 4 : 5;
                break;
            }
            default: {
                return;
            }
        }
        int n35 = (bl2 ? n20 + n28 : n20) * n17 + (bl ? n19 + n26 : n19) * n33;
        int n36 = bl ? -n33 : n33;
        int n37 = bl2 ? -n17 : n17;
        int n38 = 0;
        if ((n & 2) != 0) {
            switch (n12) {
                case -3: 
                case -1: {
                    if (byArray2 == null) {
                        n12 = 65536;
                    }
                    n38 = n15 * n13 + n14;
                    break;
                }
                case -4: {
                    if (byArray2 == null) {
                        n12 = 65536;
                    }
                    n38 = n15 * (n13 <<= 3) + n14;
                    break;
                }
                case -5: {
                    return;
                }
                case -6: {
                    if (byArray2 == null) {
                        n12 = 65536;
                    }
                    n38 = 0;
                    break;
                }
                default: {
                    n12 = (n12 << 16) / 255;
                }
                case -2: {
                    n38 = 0;
                    break;
                }
            }
        } else {
            n12 = 65536;
            n38 = 0;
        }
        int n39 = n35;
        int n40 = n32;
        if (n12 == 65536 && n31 == n34 && n9 == n23 && n10 == n24 && n11 == n25) {
            switch (n30) {
                case 1: {
                    int n41 = n29;
                    for (int i = n22; i > 0; --i) {
                        int n42 = n21;
                        int n43 = n27;
                        while (n42 > 0) {
                            byArray3[n39] = byArray[n40];
                            n40 += n43 >>> 16;
                            --n42;
                            n39 += n36;
                            n43 = (n43 & 0xFFFF) + n27;
                        }
                        n32 = n40 = n32 + (n41 >>> 16) * n3;
                        n41 = (n41 & 0xFFFF) + n29;
                        n35 = n39 = n35 + n37;
                    }
                    break;
                }
                case 2: {
                    int n44 = n29;
                    for (int i = n22; i > 0; --i) {
                        int n45 = n21;
                        int n46 = n27;
                        while (n45 > 0) {
                            byArray3[n39] = byArray[n40];
                            byArray3[n39 + 1] = byArray[n40 + 1];
                            n40 += (n46 >>> 16) * 2;
                            --n45;
                            n39 += n36;
                            n46 = (n46 & 0xFFFF) + n27;
                        }
                        n32 = n40 = n32 + (n44 >>> 16) * n3;
                        n44 = (n44 & 0xFFFF) + n29;
                        n35 = n39 = n35 + n37;
                    }
                    break;
                }
                case 3: {
                    int n47 = n29;
                    for (int i = n22; i > 0; --i) {
                        int n48 = n21;
                        int n49 = n27;
                        while (n48 > 0) {
                            byArray3[n39] = byArray[n40];
                            byArray3[n39 + 1] = byArray[n40 + 1];
                            byArray3[n39 + 2] = byArray[n40 + 2];
                            n40 += (n49 >>> 16) * 3;
                            --n48;
                            n39 += n36;
                            n49 = (n49 & 0xFFFF) + n27;
                        }
                        n32 = n40 = n32 + (n47 >>> 16) * n3;
                        n47 = (n47 & 0xFFFF) + n29;
                        n35 = n39 = n35 + n37;
                    }
                    break;
                }
                case 4: {
                    int n50 = n29;
                    for (int i = n22; i > 0; --i) {
                        int n51 = n21;
                        int n52 = n27;
                        while (n51 > 0) {
                            byArray3[n39] = byArray[n40];
                            byArray3[n39 + 1] = byArray[n40 + 1];
                            byArray3[n39 + 2] = byArray[n40 + 2];
                            byArray3[n39 + 3] = byArray[n40 + 3];
                            n40 += (n52 >>> 16) * 4;
                            --n51;
                            n39 += n36;
                            n52 = (n52 & 0xFFFF) + n27;
                        }
                        n32 = n40 = n32 + (n50 >>> 16) * n3;
                        n50 = (n50 & 0xFFFF) + n29;
                        n35 = n39 = n35 + n37;
                    }
                    break;
                }
            }
            return;
        }
        if (n12 == 65536 && n31 == 4 && n34 == 4 && n9 == 65280 && n10 == 0xFF0000 && n11 == -16777216 && n23 == 0xFF0000 && n24 == 65280 && n25 == 255) {
            int n53 = n29;
            for (int i = n22; i > 0; --i) {
                int n54 = n21;
                int n55 = n27;
                while (n54 > 0) {
                    byArray3[n39] = byArray[n40 + 3];
                    byArray3[n39 + 1] = byArray[n40 + 2];
                    byArray3[n39 + 2] = byArray[n40 + 1];
                    byArray3[n39 + 3] = byArray[n40];
                    n40 += (n55 >>> 16) * 4;
                    --n54;
                    n39 += n36;
                    n55 = (n55 & 0xFFFF) + n27;
                }
                n32 = n40 = n32 + (n53 >>> 16) * n3;
                n53 = (n53 & 0xFFFF) + n29;
                n35 = n39 = n35 + n37;
            }
            return;
        }
        if (n12 == 65536 && n31 == 3 && n34 == 4 && n9 == 255 && n10 == 65280 && n11 == 0xFF0000 && n23 == 0xFF0000 && n24 == 65280 && n25 == 255) {
            int n56 = n29;
            for (int i = n22; i > 0; --i) {
                int n57 = n21;
                int n58 = n27;
                while (n57 > 0) {
                    byArray3[n39] = 0;
                    byArray3[n39 + 1] = byArray[n40 + 2];
                    byArray3[n39 + 2] = byArray[n40 + 1];
                    byArray3[n39 + 3] = byArray[n40];
                    n40 += (n58 >>> 16) * 3;
                    --n57;
                    n39 += n36;
                    n58 = (n58 & 0xFFFF) + n27;
                }
                n32 = n40 = n32 + (n56 >>> 16) * n3;
                n56 = (n56 & 0xFFFF) + n29;
                n35 = n39 = n35 + n37;
            }
            return;
        }
        int n59 = ImageData.getChannelShift(n9);
        byte[] byArray4 = ANY_TO_EIGHT[ImageData.getChannelWidth(n9, n59)];
        int n60 = ImageData.getChannelShift(n10);
        byte[] byArray5 = ANY_TO_EIGHT[ImageData.getChannelWidth(n10, n60)];
        int n61 = ImageData.getChannelShift(n11);
        byte[] byArray6 = ANY_TO_EIGHT[ImageData.getChannelWidth(n11, n61)];
        int n62 = ImageData.getChannelShift(0);
        byte[] byArray7 = ANY_TO_EIGHT[ImageData.getChannelWidth(0, n62)];
        int n63 = ImageData.getChannelShift(n23);
        int n64 = ImageData.getChannelWidth(n23, n63);
        byte[] byArray8 = ANY_TO_EIGHT[n64];
        int n65 = 8 - n64;
        int n66 = ImageData.getChannelShift(n24);
        int n67 = ImageData.getChannelWidth(n24, n66);
        byte[] byArray9 = ANY_TO_EIGHT[n67];
        int n68 = 8 - n67;
        int n69 = ImageData.getChannelShift(n25);
        int n70 = ImageData.getChannelWidth(n25, n69);
        byte[] byArray10 = ANY_TO_EIGHT[n70];
        int n71 = 8 - n70;
        int n72 = ImageData.getChannelShift(0);
        int n73 = ImageData.getChannelWidth(0, n72);
        byte[] byArray11 = ANY_TO_EIGHT[n73];
        int n74 = 8 - n73;
        int n75 = n38;
        int n76 = n12;
        int n77 = 0;
        int n78 = 0;
        int n79 = 0;
        int n80 = 0;
        int n81 = 0;
        int n82 = 0;
        int n83 = 0;
        int n84 = 0;
        int n85 = n29;
        for (int i = n22; i > 0; --i) {
            int n86 = n21;
            int n87 = n27;
            while (n86 > 0) {
                block84: {
                    int n88;
                    block83: {
                        switch (n31) {
                            case 0: {
                                n88 = byArray[n40] & 0xFF;
                                n40 += n87 >>> 16;
                                n77 = byArray4[(n88 & n9) >>> n59] & 0xFF;
                                n78 = byArray5[(n88 & n10) >>> n60] & 0xFF;
                                n79 = byArray6[(n88 & n11) >>> n61] & 0xFF;
                                n80 = byArray7[(n88 & 0) >>> n62] & 0xFF;
                                break;
                            }
                            case 1: {
                                n88 = (byArray[n40] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF;
                                n40 += (n87 >>> 16) * 2;
                                n77 = byArray4[(n88 & n9) >>> n59] & 0xFF;
                                n78 = byArray5[(n88 & n10) >>> n60] & 0xFF;
                                n79 = byArray6[(n88 & n11) >>> n61] & 0xFF;
                                n80 = byArray7[(n88 & 0) >>> n62] & 0xFF;
                                break;
                            }
                            case 2: {
                                n88 = (byArray[n40 + 1] & 0xFF) << 8 | byArray[n40] & 0xFF;
                                n40 += (n87 >>> 16) * 2;
                                n77 = byArray4[(n88 & n9) >>> n59] & 0xFF;
                                n78 = byArray5[(n88 & n10) >>> n60] & 0xFF;
                                n79 = byArray6[(n88 & n11) >>> n61] & 0xFF;
                                n80 = byArray7[(n88 & 0) >>> n62] & 0xFF;
                                break;
                            }
                            case 3: {
                                n88 = ((byArray[n40] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF) << 8 | byArray[n40 + 2] & 0xFF;
                                n40 += (n87 >>> 16) * 3;
                                n77 = byArray4[(n88 & n9) >>> n59] & 0xFF;
                                n78 = byArray5[(n88 & n10) >>> n60] & 0xFF;
                                n79 = byArray6[(n88 & n11) >>> n61] & 0xFF;
                                n80 = byArray7[(n88 & 0) >>> n62] & 0xFF;
                                break;
                            }
                            case 4: {
                                n88 = (((byArray[n40] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF) << 8 | byArray[n40 + 2] & 0xFF) << 8 | byArray[n40 + 3] & 0xFF;
                                n40 += (n87 >>> 16) * 4;
                                n77 = byArray4[(n88 & n9) >>> n59] & 0xFF;
                                n78 = byArray5[(n88 & n10) >>> n60] & 0xFF;
                                n79 = byArray6[(n88 & n11) >>> n61] & 0xFF;
                                n80 = byArray7[(n88 & 0) >>> n62] & 0xFF;
                                break;
                            }
                            case 5: {
                                n88 = (((byArray[n40 + 3] & 0xFF) << 8 | byArray[n40 + 2] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF) << 8 | byArray[n40] & 0xFF;
                                n40 += (n87 >>> 16) * 4;
                                n77 = byArray4[(n88 & n9) >>> n59] & 0xFF;
                                n78 = byArray5[(n88 & n10) >>> n60] & 0xFF;
                                n79 = byArray6[(n88 & n11) >>> n61] & 0xFF;
                                n80 = byArray7[(n88 & 0) >>> n62] & 0xFF;
                                break;
                            }
                        }
                        block33 : switch (n12) {
                            case -1: {
                                n76 = ((byArray2[n75] & 0xFF) << 16) / 255;
                                n75 += n87 >> 16;
                                break;
                            }
                            case -2: {
                                n76 = (n80 << 16) / 255;
                                break;
                            }
                            case -3: {
                                n76 = byArray2[n75] != 0 ? 65536 : 0;
                                n75 += n87 >> 16;
                                break;
                            }
                            case -4: {
                                n76 = byArray2[n75 >> 3] << (n75 & 7) + 9 & 0x10000;
                                n75 += n87 >> 16;
                                break;
                            }
                            case -6: {
                                n76 = 65536;
                                for (n88 = 0; n88 < byArray2.length; n88 += 3) {
                                    if (n77 != byArray2[n88] || n78 != byArray2[n88 + 1] || n79 != byArray2[n88 + 2]) continue;
                                    n76 = 0;
                                    break block33;
                                }
                                break;
                            }
                        }
                        if (n76 == 65536) break block83;
                        if (n76 == 0) break block84;
                        switch (n34) {
                            case 0: {
                                n88 = byArray3[n39] & 0xFF;
                                n81 = byArray8[(n88 & n23) >>> n63] & 0xFF;
                                n82 = byArray9[(n88 & n24) >>> n66] & 0xFF;
                                n83 = byArray10[(n88 & n25) >>> n69] & 0xFF;
                                n84 = byArray11[(n88 & 0) >>> n72] & 0xFF;
                                break;
                            }
                            case 1: {
                                n88 = (byArray3[n39] & 0xFF) << 8 | byArray3[n39 + 1] & 0xFF;
                                n81 = byArray8[(n88 & n23) >>> n63] & 0xFF;
                                n82 = byArray9[(n88 & n24) >>> n66] & 0xFF;
                                n83 = byArray10[(n88 & n25) >>> n69] & 0xFF;
                                n84 = byArray11[(n88 & 0) >>> n72] & 0xFF;
                                break;
                            }
                            case 2: {
                                n88 = (byArray3[n39 + 1] & 0xFF) << 8 | byArray3[n39] & 0xFF;
                                n81 = byArray8[(n88 & n23) >>> n63] & 0xFF;
                                n82 = byArray9[(n88 & n24) >>> n66] & 0xFF;
                                n83 = byArray10[(n88 & n25) >>> n69] & 0xFF;
                                n84 = byArray11[(n88 & 0) >>> n72] & 0xFF;
                                break;
                            }
                            case 3: {
                                n88 = ((byArray3[n39] & 0xFF) << 8 | byArray3[n39 + 1] & 0xFF) << 8 | byArray3[n39 + 2] & 0xFF;
                                n81 = byArray8[(n88 & n23) >>> n63] & 0xFF;
                                n82 = byArray9[(n88 & n24) >>> n66] & 0xFF;
                                n83 = byArray10[(n88 & n25) >>> n69] & 0xFF;
                                n84 = byArray11[(n88 & 0) >>> n72] & 0xFF;
                                break;
                            }
                            case 4: {
                                n88 = (((byArray3[n39] & 0xFF) << 8 | byArray3[n39 + 1] & 0xFF) << 8 | byArray3[n39 + 2] & 0xFF) << 8 | byArray3[n39 + 3] & 0xFF;
                                n81 = byArray8[(n88 & n23) >>> n63] & 0xFF;
                                n82 = byArray9[(n88 & n24) >>> n66] & 0xFF;
                                n83 = byArray10[(n88 & n25) >>> n69] & 0xFF;
                                n84 = byArray11[(n88 & 0) >>> n72] & 0xFF;
                                break;
                            }
                            case 5: {
                                n88 = (((byArray3[n39 + 3] & 0xFF) << 8 | byArray3[n39 + 2] & 0xFF) << 8 | byArray3[n39 + 1] & 0xFF) << 8 | byArray3[n39] & 0xFF;
                                n81 = byArray8[(n88 & n23) >>> n63] & 0xFF;
                                n82 = byArray9[(n88 & n24) >>> n66] & 0xFF;
                                n83 = byArray10[(n88 & n25) >>> n69] & 0xFF;
                                n84 = byArray11[(n88 & 0) >>> n72] & 0xFF;
                                break;
                            }
                        }
                        n80 = n84 + ((n80 - n84) * n76 >> 16);
                        n77 = n81 + ((n77 - n81) * n76 >> 16);
                        n78 = n82 + ((n78 - n82) * n76 >> 16);
                        n79 = n83 + ((n79 - n83) * n76 >> 16);
                    }
                    n88 = n77 >>> n65 << n63 | n78 >>> n68 << n66 | n79 >>> n71 << n69 | n80 >>> n74 << n72;
                    switch (n34) {
                        case 0: {
                            byArray3[n39] = (byte)n88;
                            break;
                        }
                        case 1: {
                            byArray3[n39] = (byte)(n88 >>> 8);
                            byArray3[n39 + 1] = (byte)(n88 & 0xFF);
                            break;
                        }
                        case 2: {
                            byArray3[n39] = (byte)(n88 & 0xFF);
                            byArray3[n39 + 1] = (byte)(n88 >>> 8);
                            break;
                        }
                        case 3: {
                            byArray3[n39] = (byte)(n88 >>> 16);
                            byArray3[n39 + 1] = (byte)(n88 >>> 8);
                            byArray3[n39 + 2] = (byte)(n88 & 0xFF);
                            break;
                        }
                        case 4: {
                            byArray3[n39] = (byte)(n88 >>> 24);
                            byArray3[n39 + 1] = (byte)(n88 >>> 16);
                            byArray3[n39 + 2] = (byte)(n88 >>> 8);
                            byArray3[n39 + 3] = (byte)(n88 & 0xFF);
                            break;
                        }
                        case 5: {
                            byArray3[n39] = (byte)(n88 & 0xFF);
                            byArray3[n39 + 1] = (byte)(n88 >>> 8);
                            byArray3[n39 + 2] = (byte)(n88 >>> 16);
                            byArray3[n39 + 3] = (byte)(n88 >>> 24);
                        }
                    }
                }
                --n86;
                n39 += n36;
                n87 = (n87 & 0xFFFF) + n27;
            }
            n32 = n40 = n32 + (n85 >>> 16) * n3;
            n38 = n75 = n38 + (n85 >>> 16) * n13;
            n85 = (n85 & 0xFFFF) + n29;
            n35 = n39 = n35 + n37;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static void blit(int n, byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8, byte[] byArray2, byte[] byArray3, byte[] byArray4, int n9, byte[] byArray5, int n10, int n11, int n12, byte[] byArray6, int n13, int n14, int n15, int n16, int n17, int n18, int n19, byte[] byArray7, byte[] byArray8, byte[] byArray9, boolean bl, boolean bl2) {
        int[] nArray;
        int[] nArray2;
        int[] nArray3;
        int n20;
        int n21;
        int n22;
        int n23;
        int n24;
        int n25;
        int n26;
        int n27;
        int n28;
        int n29;
        int n30;
        int n31;
        int n32;
        boolean bl3;
        int n33;
        int n34;
        int n35;
        int n36;
        int n37;
        int n38;
        int n39;
        int n40;
        int n41;
        block131: {
            int n42;
            int n43;
            byte[] byArray10;
            block132: {
                if (n18 <= 0) return;
                if (n19 <= 0) return;
                if (n9 == 0) {
                    return;
                }
                int n44 = n18 - 1;
                n41 = n44 != 0 ? (int)((((long)n7 << 16) - 1L) / (long)n44) : 0;
                int n45 = n19 - 1;
                n40 = n45 != 0 ? (int)((((long)n8 << 16) - 1L) / (long)n45) : 0;
                n39 = 0;
                switch (n2) {
                    case 8: {
                        n39 = 6;
                        break;
                    }
                    case 4: {
                        n3 <<= 1;
                        n39 = 7;
                        break;
                    }
                    case 2: {
                        n3 <<= 2;
                        n39 = 8;
                        break;
                    }
                    case 1: {
                        n3 <<= 3;
                        n39 = n4 == 1 ? 9 : 10;
                        break;
                    }
                    default: {
                        return;
                    }
                }
                n38 = n6 * n3 + n5;
                n37 = 0;
                switch (n13) {
                    case 8: {
                        n37 = 6;
                        break;
                    }
                    case 4: {
                        n14 <<= 1;
                        n37 = 7;
                        break;
                    }
                    case 2: {
                        n14 <<= 2;
                        n37 = 8;
                        break;
                    }
                    case 1: {
                        n14 <<= 3;
                        n37 = n15 == 1 ? 9 : 10;
                        break;
                    }
                    default: {
                        return;
                    }
                }
                n36 = (bl2 ? n17 + n45 : n17) * n14 + (bl ? n16 + n44 : n16);
                n35 = bl ? -1 : 1;
                n34 = bl2 ? -n14 : n14;
                n33 = 0;
                if ((n & 2) != 0) {
                    switch (n9) {
                        case -3: 
                        case -1: {
                            if (byArray5 == null) {
                                n9 = 65536;
                            }
                            n33 = n12 * n10 + n11;
                            break;
                        }
                        case -4: {
                            if (byArray5 == null) {
                                n9 = 65536;
                            }
                            n33 = n12 * (n10 <<= 3) + n11;
                            break;
                        }
                        case -6: 
                        case -5: {
                            if (byArray5 == null) {
                                n9 = 65536;
                            }
                            n33 = 0;
                            break;
                        }
                        default: {
                            n9 = (n9 << 16) / 255;
                        }
                        case -2: {
                            n33 = 0;
                            break;
                        }
                    }
                } else {
                    n9 = 65536;
                    n33 = 0;
                }
                bl3 = (n & 4) != 0;
                n32 = n36;
                n31 = n38;
                n30 = n33;
                n29 = 1 << n13;
                if (byArray7 != null && byArray7.length < n29) {
                    n29 = byArray7.length;
                }
                byArray10 = null;
                boolean bl4 = true;
                switch (n9) {
                    case 65536: {
                        if (n39 == n37 && byArray2 == byArray7 && byArray3 == byArray8 && byArray4 == byArray9) {
                            byArray10 = ONE_TO_ONE_MAPPING;
                            break;
                        }
                        if (byArray2 != null && byArray7 != null && n2 <= n13) {
                            byArray10 = ONE_TO_ONE_MAPPING;
                            break;
                        }
                        byArray10 = new byte[1 << n2];
                        n28 = 255 << n13 >>> 8;
                        for (n27 = 0; n27 < byArray10.length; ++n27) {
                            byArray10[n27] = (byte)(n27 & n28);
                        }
                        break;
                    }
                    case -6: 
                    case -5: 
                    case -4: 
                    case -3: {
                        n28 = 1 << n2;
                        byArray10 = new byte[n28];
                        if (byArray2 != null && byArray2.length < n28) {
                            n28 = byArray2.length;
                        }
                        for (n27 = 0; n27 < n28; ++n27) {
                            n26 = byArray2[n27] & 0xFF;
                            n25 = byArray3[n27] & 0xFF;
                            n24 = byArray4[n27] & 0xFF;
                            n23 = 0;
                            n22 = Integer.MAX_VALUE;
                            for (n43 = 0; n43 < n29; ++n43) {
                                n42 = (byArray7[n43] & 0xFF) - n26;
                                int n46 = (byArray8[n43] & 0xFF) - n25;
                                n21 = (byArray9[n43] & 0xFF) - n24;
                                n20 = n42 * n42 + n46 * n46 + n21 * n21;
                                if (n20 >= n22) continue;
                                n23 = n43;
                                if (n20 == 0) break;
                                n22 = n20;
                            }
                            byArray10[n27] = (byte)n23;
                            if (n22 == 0) continue;
                            bl4 = false;
                        }
                        break;
                    }
                }
                if (byArray10 != null && (bl4 || !bl3)) break block132;
                n28 = n9;
                n27 = 0;
                n26 = 0;
                n25 = 0;
                n24 = -1;
                n23 = -1;
                n22 = -1;
                if (bl3) {
                    nArray3 = new int[n18 + 2];
                    nArray2 = new int[n18 + 2];
                    nArray = new int[n18 + 2];
                    break block131;
                } else {
                    nArray3 = null;
                    nArray2 = null;
                    nArray = null;
                }
                break block131;
            }
            if (n39 == n37 && n9 == 65536) {
                switch (n39) {
                    case 6: {
                        n28 = n19;
                        n27 = n40;
                        while (n28 > 0) {
                            n25 = n41;
                            for (n26 = n18; n26 > 0; n31 += n25 >>> 16, --n26, n32 += n35) {
                                byArray6[n32] = byArray10[byArray[n31] & 0xFF];
                                n25 = (n25 & 0xFFFF) + n41;
                            }
                            --n28;
                            n38 = n31 = n38 + (n27 >>> 16) * n3;
                            n27 = (n27 & 0xFFFF) + n40;
                            n36 = n32 = n36 + n34;
                        }
                        return;
                    }
                    case 7: {
                        n28 = n19;
                        n27 = n40;
                        while (n28 > 0) {
                            n25 = n41;
                            for (n26 = n18; n26 > 0; n31 += n25 >>> 16, --n26, n32 += n35) {
                                n24 = (n31 & 1) != 0 ? byArray10[byArray[n31 >> 1] & 0xF] : byArray[n31 >> 1] >>> 4 & 0xF;
                                byArray6[n32 >> 1] = (n32 & 1) != 0 ? (byte)(byArray6[n32 >> 1] & 0xF0 | n24) : (byte)(byArray6[n32 >> 1] & 0xF | n24 << 4);
                                n25 = (n25 & 0xFFFF) + n41;
                            }
                            --n28;
                            n38 = n31 = n38 + (n27 >>> 16) * n3;
                            n27 = (n27 & 0xFFFF) + n40;
                            n36 = n32 = n36 + n34;
                        }
                        return;
                    }
                    case 8: {
                        n28 = n19;
                        n27 = n40;
                        while (n28 > 0) {
                            n25 = n41;
                            for (n26 = n18; n26 > 0; n31 += n25 >>> 16, --n26, n32 += n35) {
                                n24 = byArray10[byArray[n31 >> 2] >>> 6 - (n31 & 3) * 2 & 3];
                                n23 = 6 - (n32 & 3) * 2;
                                byArray6[n32 >> 2] = (byte)(byArray6[n32 >> 2] & ~(3 << n23) | n24 << n23);
                                n25 = (n25 & 0xFFFF) + n41;
                            }
                            --n28;
                            n38 = n31 = n38 + (n27 >>> 16) * n3;
                            n27 = (n27 & 0xFFFF) + n40;
                            n36 = n32 = n36 + n34;
                        }
                        return;
                    }
                    case 9: {
                        n28 = n19;
                        n27 = n40;
                        while (n28 > 0) {
                            n25 = n41;
                            for (n26 = n18; n26 > 0; n31 += n25 >>> 16, --n26, n32 += n35) {
                                n24 = byArray10[byArray[n31 >> 3] >>> 7 - (n31 & 7) & 1];
                                n23 = 7 - (n32 & 7);
                                byArray6[n32 >> 3] = (byte)(byArray6[n32 >> 3] & ~(1 << n23) | n24 << n23);
                                n25 = (n25 & 0xFFFF) + n41;
                            }
                            --n28;
                            n38 = n31 = n38 + (n27 >>> 16) * n3;
                            n27 = (n27 & 0xFFFF) + n40;
                            n36 = n32 = n36 + n34;
                        }
                        return;
                    }
                    case 10: {
                        n28 = n19;
                        n27 = n40;
                        while (n28 > 0) {
                            n25 = n41;
                            for (n26 = n18; n26 > 0; n31 += n25 >>> 16, --n26, n32 += n35) {
                                n24 = byArray10[byArray[n31 >> 3] >>> (n31 & 7) & 1];
                                n23 = n32 & 7;
                                byArray6[n32 >> 3] = (byte)(byArray6[n32 >> 3] & ~(1 << n23) | n24 << n23);
                                n25 = (n25 & 0xFFFF) + n41;
                            }
                            --n28;
                            n38 = n31 = n38 + (n27 >>> 16) * n3;
                            n27 = (n27 & 0xFFFF) + n40;
                            n36 = n32 = n36 + n34;
                        }
                        return;
                    }
                }
                return;
            }
            n28 = n19;
            n27 = n40;
            while (n28 > 0) {
                n25 = n41;
                for (n26 = n18; n26 > 0; --n26, n32 += n35) {
                    block133: {
                        n24 = 0;
                        switch (n39) {
                            case 6: {
                                n24 = byArray[n31] & 0xFF;
                                n31 += n25 >>> 16;
                                break;
                            }
                            case 7: {
                                n24 = (n31 & 1) != 0 ? byArray[n31 >> 1] & 0xF : byArray[n31 >> 1] >>> 4 & 0xF;
                                n31 += n25 >>> 16;
                                break;
                            }
                            case 8: {
                                n24 = byArray[n31 >> 2] >>> 6 - (n31 & 3) * 2 & 3;
                                n31 += n25 >>> 16;
                                break;
                            }
                            case 9: {
                                n24 = byArray[n31 >> 3] >>> 7 - (n31 & 7) & 1;
                                n31 += n25 >>> 16;
                                break;
                            }
                            case 10: {
                                n24 = byArray[n31 >> 3] >>> (n31 & 7) & 1;
                                n31 += n25 >>> 16;
                                break;
                            }
                            default: {
                                return;
                            }
                        }
                        switch (n9) {
                            case -3: {
                                n23 = byArray5[n30];
                                n30 += n25 >> 16;
                                if (n23 != 0) break;
                                break block133;
                            }
                            case -4: {
                                n23 = byArray5[n30 >> 3] & 1 << (n30 & 7);
                                n30 += n25 >> 16;
                                if (n23 != 0) break;
                                break block133;
                            }
                            case -5: {
                                n23 = 0;
                                while (0 < byArray5.length && n24 != (byArray5[0] & 0xFF)) {
                                }
                                if (0 >= byArray5.length) break;
                                break block133;
                            }
                            case -6: {
                                n23 = byArray2[n24];
                                n22 = byArray3[n24];
                                n43 = byArray4[n24];
                                for (n42 = 0; n42 < byArray5.length && (n23 != byArray5[n42] || n22 != byArray5[n42 + 1] || n43 != byArray5[n42 + 2]); n42 += 3) {
                                }
                                if (n42 < byArray5.length) break block133;
                            }
                        }
                        n24 = byArray10[n24] & 0xFF;
                        switch (n37) {
                            case 6: {
                                byArray6[n32] = (byte)n24;
                                break;
                            }
                            case 7: {
                                if ((n32 & 1) != 0) {
                                    byArray6[n32 >> 1] = (byte)(byArray6[n32 >> 1] & 0xF0 | n24);
                                    break;
                                }
                                byArray6[n32 >> 1] = (byte)(byArray6[n32 >> 1] & 0xF | n24 << 4);
                                break;
                            }
                            case 8: {
                                n23 = 6 - (n32 & 3) * 2;
                                byArray6[n32 >> 2] = (byte)(byArray6[n32 >> 2] & ~(3 << n23) | n24 << n23);
                                break;
                            }
                            case 9: {
                                n23 = 7 - (n32 & 7);
                                byArray6[n32 >> 3] = (byte)(byArray6[n32 >> 3] & ~(1 << n23) | n24 << n23);
                                break;
                            }
                            case 10: {
                                n23 = n32 & 7;
                                byArray6[n32 >> 3] = (byte)(byArray6[n32 >> 3] & ~(1 << n23) | n24 << n23);
                                break;
                            }
                        }
                    }
                    n25 = (n25 & 0xFFFF) + n41;
                }
                --n28;
                n38 = n31 = n38 + (n27 >>> 16) * n3;
                n27 = (n27 & 0xFFFF) + n40;
                n36 = n32 = n36 + n34;
            }
            return;
        }
        n21 = n19;
        n20 = n40;
        while (n21 > 0) {
            int n47 = 0;
            int n48 = 0;
            int n49 = 0;
            int n50 = n41;
            for (int i = n18; i > 0; --i, n32 += n35) {
                block134: {
                    int n51;
                    int n52;
                    int n53;
                    int n54;
                    int n55;
                    int n56;
                    int n57;
                    int n58;
                    block135: {
                        switch (n39) {
                            case 6: {
                                n27 = byArray[n31] & 0xFF;
                                n31 += n50 >>> 16;
                                break;
                            }
                            case 7: {
                                n27 = (n31 & 1) != 0 ? byArray[n31 >> 1] & 0xF : byArray[n31 >> 1] >>> 4 & 0xF;
                                n31 += n50 >>> 16;
                                break;
                            }
                            case 8: {
                                n27 = byArray[n31 >> 2] >>> 6 - (n31 & 3) * 2 & 3;
                                n31 += n50 >>> 16;
                                break;
                            }
                            case 9: {
                                n27 = byArray[n31 >> 3] >>> 7 - (n31 & 7) & 1;
                                n31 += n50 >>> 16;
                                break;
                            }
                            case 10: {
                                n27 = byArray[n31 >> 3] >>> (n31 & 7) & 1;
                                n31 += n50 >>> 16;
                                break;
                            }
                        }
                        n58 = byArray2[n27] & 0xFF;
                        n57 = byArray3[n27] & 0xFF;
                        n56 = byArray4[n27] & 0xFF;
                        switch (n9) {
                            case -1: {
                                n28 = ((byArray5[n30] & 0xFF) << 16) / 255;
                                n30 += n50 >> 16;
                                break;
                            }
                            case -3: {
                                n28 = byArray5[n30] != 0 ? 65536 : 0;
                                n30 += n50 >> 16;
                                break;
                            }
                            case -4: {
                                n28 = byArray5[n30 >> 3] << (n30 & 7) + 9 & 0x10000;
                                n30 += n50 >> 16;
                                break;
                            }
                            case -5: {
                                n55 = 0;
                                while (0 < byArray5.length && n27 != (byArray5[0] & 0xFF)) {
                                }
                                if (0 >= byArray5.length) break;
                                break block134;
                            }
                            case -6: {
                                for (n55 = 0; n55 < byArray5.length && (n58 != (byArray5[n55] & 0xFF) || n57 != (byArray5[n55 + 1] & 0xFF) || n56 != (byArray5[n55 + 2] & 0xFF)); n55 += 3) {
                                }
                                if (n55 < byArray5.length) break block134;
                            }
                        }
                        if (n28 == 65536) break block135;
                        if (n28 == 0) break block134;
                        switch (n37) {
                            case 6: {
                                n26 = byArray6[n32] & 0xFF;
                                break;
                            }
                            case 7: {
                                if ((n32 & 1) != 0) {
                                    n26 = byArray6[n32 >> 1] & 0xF;
                                    break;
                                }
                                n26 = byArray6[n32 >> 1] >>> 4 & 0xF;
                                break;
                            }
                            case 8: {
                                n26 = byArray6[n32 >> 2] >>> 6 - (n32 & 3) * 2 & 3;
                                break;
                            }
                            case 9: {
                                n26 = byArray6[n32 >> 3] >>> 7 - (n32 & 7) & 1;
                                break;
                            }
                            case 10: {
                                n26 = byArray6[n32 >> 3] >>> (n32 & 7) & 1;
                                break;
                            }
                        }
                        n55 = byArray7[n26] & 0xFF;
                        n54 = byArray8[n26] & 0xFF;
                        n53 = byArray9[n26] & 0xFF;
                        n58 = n55 + ((n58 - n55) * n28 >> 16);
                        n57 = n54 + ((n57 - n54) * n28 >> 16);
                        n56 = n53 + ((n56 - n53) * n28 >> 16);
                    }
                    if (bl3) {
                        if ((n58 += nArray3[i] >> 4) < 0) {
                            n58 = 0;
                        } else if (n58 > 255) {
                            n58 = 255;
                        }
                        if ((n57 += nArray2[i] >> 4) < 0) {
                            n57 = 0;
                        } else if (n57 > 255) {
                            n57 = 255;
                        }
                        if ((n56 += nArray[i] >> 4) < 0) {
                            n56 = 0;
                        } else if (n56 > 255) {
                            n56 = 255;
                        }
                        nArray3[i] = n47;
                        nArray2[i] = n48;
                        nArray[i] = n49;
                    }
                    if (n58 != n24 || n57 != n23 || n56 != n22) {
                        n54 = Integer.MAX_VALUE;
                        for (n55 = 0; n55 < n29; ++n55) {
                            n53 = (byArray7[n55] & 0xFF) - n58;
                            n52 = (byArray8[n55] & 0xFF) - n57;
                            n51 = (byArray9[n55] & 0xFF) - n56;
                            int n59 = n53 * n53 + n52 * n52 + n51 * n51;
                            if (n59 >= n54) continue;
                            n25 = n55;
                            if (n59 == 0) break;
                            n54 = n59;
                        }
                        n24 = n58;
                        n23 = n57;
                        n22 = n56;
                    }
                    if (bl3) {
                        int n60;
                        int n61;
                        int n62;
                        int n63;
                        int n64;
                        int n65;
                        int n66;
                        int n67;
                        n55 = i - 1;
                        n54 = i + 1;
                        int[] nArray4 = nArray3;
                        int n68 = n52 = n54;
                        n47 = n58 - (byArray7[n25] & 0xFF);
                        n51 = n47 + n47 + n47;
                        nArray4[n68] = nArray4[n68] + n51;
                        int[] nArray5 = nArray3;
                        int n69 = n67 = i;
                        nArray5[n69] = nArray5[n69] + (n51 += n47 + n47);
                        int[] nArray6 = nArray3;
                        int n70 = n66 = n55;
                        nArray6[n70] = nArray6[n70] + (n51 + n47 + n47);
                        int[] nArray7 = nArray2;
                        int n71 = n65 = n54;
                        n48 = n57 - (byArray8[n25] & 0xFF);
                        n51 = n48 + n48 + n48;
                        nArray7[n71] = nArray7[n71] + n51;
                        int[] nArray8 = nArray2;
                        int n72 = n64 = i;
                        nArray8[n72] = nArray8[n72] + (n51 += n48 + n48);
                        int[] nArray9 = nArray2;
                        int n73 = n63 = n55;
                        nArray9[n73] = nArray9[n73] + (n51 + n48 + n48);
                        int[] nArray10 = nArray;
                        int n74 = n62 = n54;
                        n49 = n56 - (byArray9[n25] & 0xFF);
                        n51 = n49 + n49 + n49;
                        nArray10[n74] = nArray10[n74] + n51;
                        int[] nArray11 = nArray;
                        int n75 = n61 = i;
                        nArray11[n75] = nArray11[n75] + (n51 += n49 + n49);
                        int[] nArray12 = nArray;
                        int n76 = n60 = n55;
                        nArray12[n76] = nArray12[n76] + (n51 + n49 + n49);
                    }
                    switch (n37) {
                        case 6: {
                            byArray6[n32] = (byte)n25;
                            break;
                        }
                        case 7: {
                            if ((n32 & 1) != 0) {
                                byArray6[n32 >> 1] = (byte)(byArray6[n32 >> 1] & 0xF0 | n25);
                                break;
                            }
                            byArray6[n32 >> 1] = (byte)(byArray6[n32 >> 1] & 0xF | n25 << 4);
                            break;
                        }
                        case 8: {
                            n55 = 6 - (n32 & 3) * 2;
                            byArray6[n32 >> 2] = (byte)(byArray6[n32 >> 2] & ~(3 << n55) | n25 << n55);
                            break;
                        }
                        case 9: {
                            n55 = 7 - (n32 & 7);
                            byArray6[n32 >> 3] = (byte)(byArray6[n32 >> 3] & ~(1 << n55) | n25 << n55);
                            break;
                        }
                        case 10: {
                            n55 = n32 & 7;
                            byArray6[n32 >> 3] = (byte)(byArray6[n32 >> 3] & ~(1 << n55) | n25 << n55);
                            break;
                        }
                    }
                }
                n50 = (n50 & 0xFFFF) + n41;
            }
            --n21;
            n38 = n31 = n38 + (n20 >>> 16) * n3;
            n33 = n30 = n33 + (n20 >>> 16) * n10;
            n20 = (n20 & 0xFFFF) + n40;
            n36 = n32 = n36 + n34;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static void blit(int n, byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8, byte[] byArray2, byte[] byArray3, byte[] byArray4, int n9, byte[] byArray5, int n10, int n11, int n12, byte[] byArray6, int n13, int n14, int n15, int n16, int n17, int n18, int n19, int n20, int n21, int n22, boolean bl, boolean bl2) {
        int n23;
        int n24;
        int n25;
        int n26;
        int n27;
        int n28;
        int n29;
        int n30;
        int n31;
        block68: {
            block71: {
                block70: {
                    block69: {
                        if (n18 <= 0 || n19 <= 0 || n9 == 0) {
                            return;
                        }
                        if (n5 != 0 || n6 != 0 || n16 != 0 || n17 != 0 || n18 != n7 || n19 != n8) break block69;
                        if (n13 == 24 && n2 == 8 && (n & 2) == 0 && n20 == 0xFF0000 && n21 == 65280 && n22 == 255) break block70;
                        if (n13 == 32 && n15 == 1 && n2 == 8 && (n & 2) == 0 && n20 == 0xFF0000 && n21 == 65280 && n22 == 255) break block71;
                    }
                    boolean bl3 = false;
                    int n32 = n18 - 1;
                    n31 = n32 != 0 ? (int)((((long)n7 << 16) - 1L) / (long)n32) : 0;
                    int n33 = n19 - 1;
                    n30 = n33 != 0 ? (int)((((long)n8 << 16) - 1L) / (long)n33) : 0;
                    n29 = 0;
                    switch (n2) {
                        case 8: {
                            n29 = 6;
                            break;
                        }
                        case 4: {
                            n3 <<= 1;
                            n29 = 7;
                            break;
                        }
                        case 2: {
                            n3 <<= 2;
                            n29 = 8;
                            break;
                        }
                        case 1: {
                            n3 <<= 3;
                            n29 = n4 == 1 ? 9 : 10;
                            break;
                        }
                        default: {
                            return;
                        }
                    }
                    n28 = n6 * n3 + n5;
                    int n34 = 0;
                    n27 = 0;
                    switch (n13) {
                        case 8: {
                            n34 = 1;
                            n27 = 0;
                            break;
                        }
                        case 16: {
                            n34 = 2;
                            n27 = n15 == 1 ? 1 : 2;
                            break;
                        }
                        case 24: {
                            n34 = 3;
                            n27 = 3;
                            break;
                        }
                        case 32: {
                            n34 = 4;
                            n27 = n15 == 1 ? 4 : 5;
                            break;
                        }
                        default: {
                            return;
                        }
                    }
                    n26 = (bl2 ? n17 + n33 : n17) * n14 + (bl ? n16 + n32 : n16) * n34;
                    n25 = bl ? -n34 : n34;
                    n24 = bl2 ? -n14 : n14;
                    n23 = 0;
                    if ((n & 2) != 0) {
                        switch (n9) {
                            case -3: 
                            case -1: {
                                if (byArray5 == null) {
                                    n9 = 65536;
                                }
                                n23 = n12 * n10 + n11;
                                break;
                            }
                            case -4: {
                                if (byArray5 == null) {
                                    n9 = 65536;
                                }
                                n23 = n12 * (n10 <<= 3) + n11;
                                break;
                            }
                            case -6: 
                            case -5: {
                                if (byArray5 == null) {
                                    n9 = 65536;
                                }
                                n23 = 0;
                                break;
                            }
                            default: {
                                n9 = (n9 << 16) / 255;
                            }
                            case -2: {
                                n23 = 0;
                                break;
                            }
                        }
                        break block68;
                    } else {
                        n9 = 65536;
                        n23 = 0;
                    }
                    break block68;
                }
                int n35 = 0;
                int n36 = 0;
                int n37 = 0;
                int n38 = n3 - n7;
                int n39 = n14 - n18 * 3;
                while (true) {
                    if (n35 >= n19) {
                        return;
                    }
                    for (int i = 0; i < n18; ++i) {
                        int n40 = byArray[n36++] & 0xFF;
                        byArray6[n37++] = byArray2[n40];
                        byArray6[n37++] = byArray3[n40];
                        byArray6[n37++] = byArray4[n40];
                    }
                    ++n35;
                    n36 += n38;
                    n37 += n39;
                }
            }
            int n41 = 0;
            int n42 = 0;
            int n43 = 0;
            int n44 = n3 - n7;
            int n45 = n14 - n18 * 4;
            while (true) {
                if (n41 >= n19) {
                    return;
                }
                for (int i = 0; i < n18; ++n43, ++i) {
                    int n46 = byArray[n42++] & 0xFF;
                    int n47 = ++n43;
                    byArray6[n47] = byArray2[n46];
                    int n48 = ++n43;
                    byArray6[n48] = byArray3[n46];
                    int n49 = ++n43;
                    byArray6[n49] = byArray4[n46];
                }
                ++n41;
                n42 += n44;
                n43 += n45;
            }
        }
        int n50 = ImageData.getChannelShift(n20);
        int n51 = ImageData.getChannelWidth(n20, n50);
        byte[] byArray7 = ANY_TO_EIGHT[n51];
        int n52 = 8 - n51;
        int n53 = ImageData.getChannelShift(n21);
        int n54 = ImageData.getChannelWidth(n21, n53);
        byte[] byArray8 = ANY_TO_EIGHT[n54];
        int n55 = 8 - n54;
        int n56 = ImageData.getChannelShift(n22);
        int n57 = ImageData.getChannelWidth(n22, n56);
        byte[] byArray9 = ANY_TO_EIGHT[n57];
        int n58 = 8 - n57;
        int n59 = ImageData.getChannelShift(0);
        int n60 = ImageData.getChannelWidth(0, n59);
        byte[] byArray10 = ANY_TO_EIGHT[n60];
        int n61 = 8 - n60;
        int n62 = n26;
        int n63 = n28;
        int n64 = n23;
        int n65 = n9;
        int n66 = 0;
        int n67 = 0;
        int n68 = 0;
        int n69 = 0;
        int n70 = 0;
        int n71 = 0;
        int n72 = 0;
        int n73 = 0;
        int n74 = 0;
        int n75 = n19;
        int n76 = n30;
        while (n75 > 0) {
            int n77 = n31;
            for (int i = n18; i > 0; --i, n62 += n25) {
                block72: {
                    int n78;
                    block73: {
                        switch (n29) {
                            case 6: {
                                n70 = byArray[n63] & 0xFF;
                                n63 += n77 >>> 16;
                                break;
                            }
                            case 7: {
                                n70 = (n63 & 1) != 0 ? byArray[n63 >> 1] & 0xF : byArray[n63 >> 1] >>> 4 & 0xF;
                                n63 += n77 >>> 16;
                                break;
                            }
                            case 8: {
                                n70 = byArray[n63 >> 2] >>> 6 - (n63 & 3) * 2 & 3;
                                n63 += n77 >>> 16;
                                break;
                            }
                            case 9: {
                                n70 = byArray[n63 >> 3] >>> 7 - (n63 & 7) & 1;
                                n63 += n77 >>> 16;
                                break;
                            }
                            case 10: {
                                n70 = byArray[n63 >> 3] >>> (n63 & 7) & 1;
                                n63 += n77 >>> 16;
                                break;
                            }
                        }
                        n66 = byArray2[n70] & 0xFF;
                        n67 = byArray3[n70] & 0xFF;
                        n68 = byArray4[n70] & 0xFF;
                        switch (n9) {
                            case -1: {
                                n65 = ((byArray5[n64] & 0xFF) << 16) / 255;
                                n64 += n77 >> 16;
                                break;
                            }
                            case -3: {
                                n65 = byArray5[n64] != 0 ? 65536 : 0;
                                n64 += n77 >> 16;
                                break;
                            }
                            case -4: {
                                n65 = byArray5[n64 >> 3] << (n64 & 7) + 9 & 0x10000;
                                n64 += n77 >> 16;
                                break;
                            }
                            case -5: {
                                n78 = 0;
                                while (0 < byArray5.length && n70 != (byArray5[0] & 0xFF)) {
                                }
                                if (0 >= byArray5.length) break;
                                break block72;
                            }
                            case -6: {
                                for (n78 = 0; n78 < byArray5.length && (n66 != (byArray5[n78] & 0xFF) || n67 != (byArray5[n78 + 1] & 0xFF) || n68 != (byArray5[n78 + 2] & 0xFF)); n78 += 3) {
                                }
                                if (n78 < byArray5.length) break block72;
                            }
                        }
                        if (n65 == 65536) break block73;
                        if (n65 == 0) break block72;
                        switch (n27) {
                            case 0: {
                                n78 = byArray6[n62] & 0xFF;
                                n71 = byArray7[(n78 & n20) >>> n50] & 0xFF;
                                n72 = byArray8[(n78 & n21) >>> n53] & 0xFF;
                                n73 = byArray9[(n78 & n22) >>> n56] & 0xFF;
                                n74 = byArray10[(n78 & 0) >>> n59] & 0xFF;
                                break;
                            }
                            case 1: {
                                n78 = (byArray6[n62] & 0xFF) << 8 | byArray6[n62 + 1] & 0xFF;
                                n71 = byArray7[(n78 & n20) >>> n50] & 0xFF;
                                n72 = byArray8[(n78 & n21) >>> n53] & 0xFF;
                                n73 = byArray9[(n78 & n22) >>> n56] & 0xFF;
                                n74 = byArray10[(n78 & 0) >>> n59] & 0xFF;
                                break;
                            }
                            case 2: {
                                n78 = (byArray6[n62 + 1] & 0xFF) << 8 | byArray6[n62] & 0xFF;
                                n71 = byArray7[(n78 & n20) >>> n50] & 0xFF;
                                n72 = byArray8[(n78 & n21) >>> n53] & 0xFF;
                                n73 = byArray9[(n78 & n22) >>> n56] & 0xFF;
                                n74 = byArray10[(n78 & 0) >>> n59] & 0xFF;
                                break;
                            }
                            case 3: {
                                n78 = ((byArray6[n62] & 0xFF) << 8 | byArray6[n62 + 1] & 0xFF) << 8 | byArray6[n62 + 2] & 0xFF;
                                n71 = byArray7[(n78 & n20) >>> n50] & 0xFF;
                                n72 = byArray8[(n78 & n21) >>> n53] & 0xFF;
                                n73 = byArray9[(n78 & n22) >>> n56] & 0xFF;
                                n74 = byArray10[(n78 & 0) >>> n59] & 0xFF;
                                break;
                            }
                            case 4: {
                                n78 = (((byArray6[n62] & 0xFF) << 8 | byArray6[n62 + 1] & 0xFF) << 8 | byArray6[n62 + 2] & 0xFF) << 8 | byArray6[n62 + 3] & 0xFF;
                                n71 = byArray7[(n78 & n20) >>> n50] & 0xFF;
                                n72 = byArray8[(n78 & n21) >>> n53] & 0xFF;
                                n73 = byArray9[(n78 & n22) >>> n56] & 0xFF;
                                n74 = byArray10[(n78 & 0) >>> n59] & 0xFF;
                                break;
                            }
                            case 5: {
                                n78 = (((byArray6[n62 + 3] & 0xFF) << 8 | byArray6[n62 + 2] & 0xFF) << 8 | byArray6[n62 + 1] & 0xFF) << 8 | byArray6[n62] & 0xFF;
                                n71 = byArray7[(n78 & n20) >>> n50] & 0xFF;
                                n72 = byArray8[(n78 & n21) >>> n53] & 0xFF;
                                n73 = byArray9[(n78 & n22) >>> n56] & 0xFF;
                                n74 = byArray10[(n78 & 0) >>> n59] & 0xFF;
                                break;
                            }
                        }
                        n69 = n74 + ((n69 - n74) * n65 >> 16);
                        n66 = n71 + ((n66 - n71) * n65 >> 16);
                        n67 = n72 + ((n67 - n72) * n65 >> 16);
                        n68 = n73 + ((n68 - n73) * n65 >> 16);
                    }
                    n78 = n66 >>> n52 << n50 | n67 >>> n55 << n53 | n68 >>> n58 << n56 | n69 >>> n61 << n59;
                    switch (n27) {
                        case 0: {
                            byArray6[n62] = (byte)n78;
                            break;
                        }
                        case 1: {
                            byArray6[n62] = (byte)(n78 >>> 8);
                            byArray6[n62 + 1] = (byte)(n78 & 0xFF);
                            break;
                        }
                        case 2: {
                            byArray6[n62] = (byte)(n78 & 0xFF);
                            byArray6[n62 + 1] = (byte)(n78 >>> 8);
                            break;
                        }
                        case 3: {
                            byArray6[n62] = (byte)(n78 >>> 16);
                            byArray6[n62 + 1] = (byte)(n78 >>> 8);
                            byArray6[n62 + 2] = (byte)(n78 & 0xFF);
                            break;
                        }
                        case 4: {
                            byArray6[n62] = (byte)(n78 >>> 24);
                            byArray6[n62 + 1] = (byte)(n78 >>> 16);
                            byArray6[n62 + 2] = (byte)(n78 >>> 8);
                            byArray6[n62 + 3] = (byte)(n78 & 0xFF);
                            break;
                        }
                        case 5: {
                            byArray6[n62] = (byte)(n78 & 0xFF);
                            byArray6[n62 + 1] = (byte)(n78 >>> 8);
                            byArray6[n62 + 2] = (byte)(n78 >>> 16);
                            byArray6[n62 + 3] = (byte)(n78 >>> 24);
                            break;
                        }
                    }
                }
                n77 = (n77 & 0xFFFF) + n31;
            }
            --n75;
            n28 = n63 = n28 + (n76 >>> 16) * n3;
            n23 = n64 = n23 + (n76 >>> 16) * n10;
            n76 = (n76 & 0xFFFF) + n30;
            n26 = n62 = n26 + n24;
        }
        return;
    }

    static void blit(int n, byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, byte[] byArray2, int n13, int n14, int n15, byte[] byArray3, int n16, int n17, int n18, int n19, int n20, int n21, int n22, byte[] byArray4, byte[] byArray5, byte[] byArray6, boolean bl, boolean bl2) {
        int[] nArray;
        int[] nArray2;
        int[] nArray3;
        if (n21 <= 0 || n22 <= 0 || n12 == 0) {
            return;
        }
        boolean bl3 = false;
        int n23 = n21 - 1;
        int n24 = n23 != 0 ? (int)((((long)n7 << 16) - 1L) / (long)n23) : 0;
        int n25 = n22 - 1;
        int n26 = n25 != 0 ? (int)((((long)n8 << 16) - 1L) / (long)n25) : 0;
        int n27 = 0;
        int n28 = 0;
        switch (n2) {
            case 8: {
                n27 = 1;
                n28 = 0;
                break;
            }
            case 16: {
                n27 = 2;
                n28 = n4 == 1 ? 1 : 2;
                break;
            }
            case 24: {
                n27 = 3;
                n28 = 3;
                break;
            }
            case 32: {
                n27 = 4;
                n28 = n4 == 1 ? 4 : 5;
                break;
            }
            default: {
                return;
            }
        }
        int n29 = n6 * n3 + n5 * n27;
        int n30 = 0;
        switch (n16) {
            case 8: {
                n30 = 6;
                break;
            }
            case 4: {
                n17 <<= 1;
                n30 = 7;
                break;
            }
            case 2: {
                n17 <<= 2;
                n30 = 8;
                break;
            }
            case 1: {
                n17 <<= 3;
                n30 = n18 == 1 ? 9 : 10;
                break;
            }
            default: {
                return;
            }
        }
        int n31 = (bl2 ? n20 + n25 : n20) * n17 + (bl ? n19 + n23 : n19);
        int n32 = bl ? -1 : 1;
        int n33 = bl2 ? -n17 : n17;
        int n34 = 0;
        if ((n & 2) != 0) {
            switch (n12) {
                case -3: 
                case -1: {
                    if (byArray2 == null) {
                        n12 = 65536;
                    }
                    n34 = n15 * n13 + n14;
                    break;
                }
                case -4: {
                    if (byArray2 == null) {
                        n12 = 65536;
                    }
                    n34 = n15 * (n13 <<= 3) + n14;
                    break;
                }
                case -5: {
                    return;
                }
                case -6: {
                    if (byArray2 == null) {
                        n12 = 65536;
                    }
                    n34 = 0;
                    break;
                }
                default: {
                    n12 = (n12 << 16) / 255;
                }
                case -2: {
                    n34 = 0;
                    break;
                }
            }
        } else {
            n12 = 65536;
            n34 = 0;
        }
        boolean bl4 = (n & 4) != 0;
        int n35 = ImageData.getChannelShift(n9);
        byte[] byArray7 = ANY_TO_EIGHT[ImageData.getChannelWidth(n9, n35)];
        int n36 = ImageData.getChannelShift(n10);
        byte[] byArray8 = ANY_TO_EIGHT[ImageData.getChannelWidth(n10, n36)];
        int n37 = ImageData.getChannelShift(n11);
        byte[] byArray9 = ANY_TO_EIGHT[ImageData.getChannelWidth(n11, n37)];
        int n38 = ImageData.getChannelShift(0);
        byte[] byArray10 = ANY_TO_EIGHT[ImageData.getChannelWidth(0, n38)];
        int n39 = n31;
        int n40 = n29;
        int n41 = n34;
        int n42 = n12;
        int n43 = 0;
        int n44 = 0;
        int n45 = 0;
        int n46 = 0;
        int n47 = 0;
        int n48 = 0;
        int n49 = -1;
        int n50 = -1;
        int n51 = -1;
        int n52 = 1 << n16;
        if (byArray4 != null && byArray4.length < n52) {
            n52 = byArray4.length;
        }
        if (bl4) {
            nArray3 = new int[n21 + 2];
            nArray2 = new int[n21 + 2];
            nArray = new int[n21 + 2];
        } else {
            nArray3 = null;
            nArray2 = null;
            nArray = null;
        }
        int n53 = n26;
        for (int i = n22; i > 0; --i) {
            int n54 = 0;
            int n55 = 0;
            int n56 = 0;
            int n57 = n21;
            int n58 = n24;
            while (n57 > 0) {
                block79: {
                    int n59;
                    int n60;
                    int n61;
                    int n62;
                    int n63;
                    block78: {
                        switch (n28) {
                            case 0: {
                                n63 = byArray[n40] & 0xFF;
                                n40 += n58 >>> 16;
                                n43 = byArray7[(n63 & n9) >>> n35] & 0xFF;
                                n44 = byArray8[(n63 & n10) >>> n36] & 0xFF;
                                n45 = byArray9[(n63 & n11) >>> n37] & 0xFF;
                                n46 = byArray10[(n63 & 0) >>> n38] & 0xFF;
                                break;
                            }
                            case 1: {
                                n63 = (byArray[n40] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF;
                                n40 += (n58 >>> 16) * 2;
                                n43 = byArray7[(n63 & n9) >>> n35] & 0xFF;
                                n44 = byArray8[(n63 & n10) >>> n36] & 0xFF;
                                n45 = byArray9[(n63 & n11) >>> n37] & 0xFF;
                                n46 = byArray10[(n63 & 0) >>> n38] & 0xFF;
                                break;
                            }
                            case 2: {
                                n63 = (byArray[n40 + 1] & 0xFF) << 8 | byArray[n40] & 0xFF;
                                n40 += (n58 >>> 16) * 2;
                                n43 = byArray7[(n63 & n9) >>> n35] & 0xFF;
                                n44 = byArray8[(n63 & n10) >>> n36] & 0xFF;
                                n45 = byArray9[(n63 & n11) >>> n37] & 0xFF;
                                n46 = byArray10[(n63 & 0) >>> n38] & 0xFF;
                                break;
                            }
                            case 3: {
                                n63 = ((byArray[n40] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF) << 8 | byArray[n40 + 2] & 0xFF;
                                n40 += (n58 >>> 16) * 3;
                                n43 = byArray7[(n63 & n9) >>> n35] & 0xFF;
                                n44 = byArray8[(n63 & n10) >>> n36] & 0xFF;
                                n45 = byArray9[(n63 & n11) >>> n37] & 0xFF;
                                n46 = byArray10[(n63 & 0) >>> n38] & 0xFF;
                                break;
                            }
                            case 4: {
                                n63 = (((byArray[n40] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF) << 8 | byArray[n40 + 2] & 0xFF) << 8 | byArray[n40 + 3] & 0xFF;
                                n40 += (n58 >>> 16) * 4;
                                n43 = byArray7[(n63 & n9) >>> n35] & 0xFF;
                                n44 = byArray8[(n63 & n10) >>> n36] & 0xFF;
                                n45 = byArray9[(n63 & n11) >>> n37] & 0xFF;
                                n46 = byArray10[(n63 & 0) >>> n38] & 0xFF;
                                break;
                            }
                            case 5: {
                                n63 = (((byArray[n40 + 3] & 0xFF) << 8 | byArray[n40 + 2] & 0xFF) << 8 | byArray[n40 + 1] & 0xFF) << 8 | byArray[n40] & 0xFF;
                                n40 += (n58 >>> 16) * 4;
                                n43 = byArray7[(n63 & n9) >>> n35] & 0xFF;
                                n44 = byArray8[(n63 & n10) >>> n36] & 0xFF;
                                n45 = byArray9[(n63 & n11) >>> n37] & 0xFF;
                                n46 = byArray10[(n63 & 0) >>> n38] & 0xFF;
                                break;
                            }
                        }
                        block27 : switch (n12) {
                            case -1: {
                                n42 = ((byArray2[n41] & 0xFF) << 16) / 255;
                                n41 += n58 >> 16;
                                break;
                            }
                            case -2: {
                                n42 = (n46 << 16) / 255;
                                break;
                            }
                            case -3: {
                                n42 = byArray2[n41] != 0 ? 65536 : 0;
                                n41 += n58 >> 16;
                                break;
                            }
                            case -4: {
                                n42 = byArray2[n41 >> 3] << (n41 & 7) + 9 & 0x10000;
                                n41 += n58 >> 16;
                                break;
                            }
                            case -6: {
                                n42 = 65536;
                                for (n63 = 0; n63 < byArray2.length; n63 += 3) {
                                    if (n43 != byArray2[n63] || n44 != byArray2[n63 + 1] || n45 != byArray2[n63 + 2]) continue;
                                    n42 = 0;
                                    break block27;
                                }
                                break;
                            }
                        }
                        if (n42 == 65536) break block78;
                        if (n42 == 0) break block79;
                        switch (n30) {
                            case 6: {
                                n47 = byArray3[n39] & 0xFF;
                                break;
                            }
                            case 7: {
                                if ((n39 & 1) != 0) {
                                    n47 = byArray3[n39 >> 1] & 0xF;
                                    break;
                                }
                                n47 = byArray3[n39 >> 1] >>> 4 & 0xF;
                                break;
                            }
                            case 8: {
                                n47 = byArray3[n39 >> 2] >>> 6 - (n39 & 3) * 2 & 3;
                                break;
                            }
                            case 9: {
                                n47 = byArray3[n39 >> 3] >>> 7 - (n39 & 7) & 1;
                                break;
                            }
                            case 10: {
                                n47 = byArray3[n39 >> 3] >>> (n39 & 7) & 1;
                            }
                        }
                        n63 = byArray4[n47] & 0xFF;
                        n62 = byArray5[n47] & 0xFF;
                        n61 = byArray6[n47] & 0xFF;
                        n43 = n63 + ((n43 - n63) * n42 >> 16);
                        n44 = n62 + ((n44 - n62) * n42 >> 16);
                        n45 = n61 + ((n45 - n61) * n42 >> 16);
                    }
                    if (bl4) {
                        if ((n43 += nArray3[n57] >> 4) < 0) {
                            n43 = 0;
                        } else if (n43 > 255) {
                            n43 = 255;
                        }
                        if ((n44 += nArray2[n57] >> 4) < 0) {
                            n44 = 0;
                        } else if (n44 > 255) {
                            n44 = 255;
                        }
                        if ((n45 += nArray[n57] >> 4) < 0) {
                            n45 = 0;
                        } else if (n45 > 255) {
                            n45 = 255;
                        }
                        nArray3[n57] = n54;
                        nArray2[n57] = n55;
                        nArray[n57] = n56;
                    }
                    if (n43 != n49 || n44 != n50 || n45 != n51) {
                        n62 = Integer.MAX_VALUE;
                        for (n63 = 0; n63 < n52; ++n63) {
                            n61 = (byArray4[n63] & 0xFF) - n43;
                            n60 = (byArray5[n63] & 0xFF) - n44;
                            n59 = (byArray6[n63] & 0xFF) - n45;
                            int n64 = n61 * n61 + n60 * n60 + n59 * n59;
                            if (n64 >= n62) continue;
                            n48 = n63;
                            if (n64 == 0) break;
                            n62 = n64;
                        }
                        n49 = n43;
                        n50 = n44;
                        n51 = n45;
                    }
                    if (bl4) {
                        int n65;
                        int n66;
                        int n67;
                        int n68;
                        int n69;
                        int n70;
                        int n71;
                        int n72;
                        n63 = n57 - 1;
                        n62 = n57 + 1;
                        int[] nArray4 = nArray3;
                        int n73 = n60 = n62;
                        n54 = n43 - (byArray4[n48] & 0xFF);
                        n59 = n54 + n54 + n54;
                        nArray4[n73] = nArray4[n73] + n59;
                        int[] nArray5 = nArray3;
                        int n74 = n72 = n57;
                        nArray5[n74] = nArray5[n74] + (n59 += n54 + n54);
                        int[] nArray6 = nArray3;
                        int n75 = n71 = n63;
                        nArray6[n75] = nArray6[n75] + (n59 + n54 + n54);
                        int[] nArray7 = nArray2;
                        int n76 = n70 = n62;
                        n55 = n44 - (byArray5[n48] & 0xFF);
                        n59 = n55 + n55 + n55;
                        nArray7[n76] = nArray7[n76] + n59;
                        int[] nArray8 = nArray2;
                        int n77 = n69 = n57;
                        nArray8[n77] = nArray8[n77] + (n59 += n55 + n55);
                        int[] nArray9 = nArray2;
                        int n78 = n68 = n63;
                        nArray9[n78] = nArray9[n78] + (n59 + n55 + n55);
                        int[] nArray10 = nArray;
                        int n79 = n67 = n62;
                        n56 = n45 - (byArray6[n48] & 0xFF);
                        n59 = n56 + n56 + n56;
                        nArray10[n79] = nArray10[n79] + n59;
                        int[] nArray11 = nArray;
                        int n80 = n66 = n57;
                        nArray11[n80] = nArray11[n80] + (n59 += n56 + n56);
                        int[] nArray12 = nArray;
                        int n81 = n65 = n63;
                        nArray12[n81] = nArray12[n81] + (n59 + n56 + n56);
                    }
                    switch (n30) {
                        case 6: {
                            byArray3[n39] = (byte)n48;
                            break;
                        }
                        case 7: {
                            if ((n39 & 1) != 0) {
                                byArray3[n39 >> 1] = (byte)(byArray3[n39 >> 1] & 0xF0 | n48);
                                break;
                            }
                            byArray3[n39 >> 1] = (byte)(byArray3[n39 >> 1] & 0xF | n48 << 4);
                            break;
                        }
                        case 8: {
                            n63 = 6 - (n39 & 3) * 2;
                            byArray3[n39 >> 2] = (byte)(byArray3[n39 >> 2] & ~(3 << n63) | n48 << n63);
                            break;
                        }
                        case 9: {
                            n63 = 7 - (n39 & 7);
                            byArray3[n39 >> 3] = (byte)(byArray3[n39 >> 3] & ~(1 << n63) | n48 << n63);
                            break;
                        }
                        case 10: {
                            n63 = n39 & 7;
                            byArray3[n39 >> 3] = (byte)(byArray3[n39 >> 3] & ~(1 << n63) | n48 << n63);
                            break;
                        }
                    }
                }
                --n57;
                n39 += n32;
                n58 = (n58 & 0xFFFF) + n24;
            }
            n29 = n40 = n29 + (n53 >>> 16) * n3;
            n34 = n41 = n34 + (n53 >>> 16) * n13;
            n53 = (n53 & 0xFFFF) + n26;
            n31 = n39 = n31 + n33;
        }
    }

    static int getChannelShift(int n) {
        int n2;
        if (n == 0) {
            return 0;
        }
        for (n2 = 0; (n & 1) == 0 && n2 < 32; ++n2) {
            n >>>= 1;
        }
        return n2;
    }

    static int getChannelWidth(int n, int n2) {
        int n3;
        if (n == 0) {
            return 0;
        }
        n >>>= n2;
        for (n3 = n2; (n & 1) != 0 && n3 < 32; ++n3) {
            n >>>= 1;
        }
        return n3 - n2;
    }

    static byte getChannelField(int n, int n2) {
        int n3 = ImageData.getChannelShift(n2);
        return ANY_TO_EIGHT[ImageData.getChannelWidth(n2, n3)][(n & n2) >>> n3];
    }

    static ImageData createGradientBand(int n, int n2, boolean bl, RGB rGB, RGB rGB2, int n3, int n4, int n5) {
        byte[] byArray;
        int n6;
        int n7;
        int n8;
        PaletteData paletteData;
        if (n3 != 0 && n4 != 0 && n5 != 0) {
            paletteData = new PaletteData(65280, 0xFF0000, -16777216);
            n8 = 32;
            if (n3 >= 8 && n4 >= 8 && n5 >= 8) {
                int n9;
                if (bl) {
                    n7 = 1;
                    n6 = n2;
                    n9 = n6 > 1 ? n6 - 1 : 1;
                } else {
                    n7 = n;
                    n6 = 1;
                    n9 = n7 > 1 ? n7 - 1 : 1;
                }
                int n10 = n7 * 4;
                byArray = new byte[n6 * n10];
                ImageData.buildPreciseGradientChannel(rGB.blue, rGB2.blue, n9, n7, n6, bl, byArray, 0, n10);
                ImageData.buildPreciseGradientChannel(rGB.green, rGB2.green, n9, n7, n6, bl, byArray, 1, n10);
                ImageData.buildPreciseGradientChannel(rGB.red, rGB2.red, n9, n7, n6, bl, byArray, 2, n10);
            } else {
                int n11;
                if (bl) {
                    n7 = n < 8 ? n : 8;
                    n6 = n2;
                    n11 = n6 > 1 ? n6 - 1 : 1;
                } else {
                    n7 = n;
                    n6 = n2 < 8 ? n2 : 8;
                    n11 = n7 > 1 ? n7 - 1 : 1;
                }
                int n12 = n7 * 4;
                byArray = new byte[n6 * n12];
                ImageData.buildDitheredGradientChannel(rGB.blue, rGB2.blue, n11, n7, n6, bl, byArray, 0, n12, n5);
                ImageData.buildDitheredGradientChannel(rGB.green, rGB2.green, n11, n7, n6, bl, byArray, 1, n12, n4);
                ImageData.buildDitheredGradientChannel(rGB.red, rGB2.red, n11, n7, n6, bl, byArray, 2, n12, n3);
            }
        } else {
            int n13;
            paletteData = new PaletteData(rGB, rGB2);
            n8 = 8;
            if (bl) {
                n7 = n < 8 ? n : 8;
                n6 = n2;
                n13 = n6 > 1 ? 0x1040000 / (n6 - 1) + 1 : 1;
            } else {
                n7 = n;
                n6 = n2 < 8 ? n2 : 8;
                n13 = n7 > 1 ? 0x1040000 / (n7 - 1) + 1 : 1;
            }
            int n14 = n7 + 3 & 0xFFFFFFFC;
            byArray = new byte[n6 * n14];
            if (bl) {
                int n15 = 0;
                int n16 = 0;
                int n17 = 0;
                while (n15 < n6) {
                    for (int i = 0; i < n7; ++i) {
                        byArray[n17 + i] = (byte)(n16 + DITHER_MATRIX[n15 & 7][i] >= 0x1000000 ? 1 : 0);
                    }
                    ++n15;
                    n16 += n13;
                    n17 += n14;
                }
            } else {
                int n18 = 0;
                int n19 = 0;
                while (n18 < n7) {
                    int n20 = 0;
                    int n21 = n18;
                    while (n20 < n6) {
                        byArray[n21] = (byte)(n19 + DITHER_MATRIX[n20][n18 & 7] >= 0x1000000 ? 1 : 0);
                        ++n20;
                        n21 += n14;
                    }
                    ++n18;
                    n19 += n13;
                }
            }
        }
        return new ImageData(n7, n6, n8, paletteData, 4, byArray);
    }

    static void buildPreciseGradientChannel(int n, int n2, int n3, int n4, int n5, boolean bl, byte[] byArray, int n6, int n7) {
        int n8 = n << 16;
        int n9 = ((n2 << 16) - n8) / n3 + 1;
        if (bl) {
            int n10 = 0;
            while (n10 < n5) {
                byArray[n6] = (byte)(n8 >>> 16);
                n8 += n9;
                ++n10;
                n6 += n7;
            }
        } else {
            int n11 = 0;
            while (n11 < n4) {
                byArray[n6] = (byte)(n8 >>> 16);
                n8 += n9;
                ++n11;
                n6 += 4;
            }
        }
    }

    static void buildDitheredGradientChannel(int n, int n2, int n3, int n4, int n5, boolean bl, byte[] byArray, int n6, int n7, int n8) {
        int n9 = 65280 >>> n8;
        int n10 = n << 16;
        int n11 = ((n2 << 16) - n10) / n3 + 1;
        if (bl) {
            int n12 = 0;
            while (n12 < n5) {
                int n13 = 0;
                int n14 = n6;
                while (n13 < n4) {
                    int n15 = DITHER_MATRIX[n12 & 7][n13] >>> n8;
                    int n16 = n10 + n15;
                    byArray[n14] = n16 > 0xFFFFFF ? -1 : (byte)(n16 >>> 16 & n9);
                    ++n13;
                    n14 += 4;
                }
                n10 += n11;
                ++n12;
                n6 += n7;
            }
        } else {
            int n17 = 0;
            while (n17 < n4) {
                int n18 = 0;
                int n19 = n6;
                while (n18 < n5) {
                    int n20 = DITHER_MATRIX[n18][n17 & 7] >>> n8;
                    int n21 = n10 + n20;
                    byArray[n19] = n21 > 0xFFFFFF ? -1 : (byte)(n21 >>> 16 & n9);
                    ++n18;
                    n19 += n7;
                }
                n10 += n11;
                ++n17;
                n6 += 4;
            }
        }
    }

    static void fillGradientRectangle(GC gC, Device device, int n, int n2, int n3, int n4, boolean bl, RGB rGB, RGB rGB2, int n5, int n6, int n7) {
        ImageData imageData = ImageData.createGradientBand(n3, n4, bl, rGB, rGB2, n5, n6, n7);
        Image image = new Image(device, imageData);
        if (imageData.width == 1 || imageData.height == 1) {
            gC.drawImage(image, 0, 0, DPIUtil.autoScaleDown(imageData.width), DPIUtil.autoScaleDown(imageData.height), DPIUtil.autoScaleDown(n), DPIUtil.autoScaleDown(n2), DPIUtil.autoScaleDown(n3), DPIUtil.autoScaleDown(n4));
        } else if (bl) {
            for (int i = 0; i < n3; i += imageData.width) {
                int n8 = n3 - i;
                if (n8 > imageData.width) {
                    n8 = imageData.width;
                }
                gC.drawImage(image, 0, 0, DPIUtil.autoScaleDown(n8), DPIUtil.autoScaleDown(imageData.height), DPIUtil.autoScaleDown(i + n), DPIUtil.autoScaleDown(n2), DPIUtil.autoScaleDown(n8), DPIUtil.autoScaleDown(imageData.height));
            }
        } else {
            for (int i = 0; i < n4; i += imageData.height) {
                int n9 = n4 - i;
                if (n9 > imageData.height) {
                    n9 = imageData.height;
                }
                gC.drawImage(image, 0, 0, DPIUtil.autoScaleDown(imageData.width), DPIUtil.autoScaleDown(n9), DPIUtil.autoScaleDown(n), DPIUtil.autoScaleDown(i + n2), DPIUtil.autoScaleDown(imageData.width), DPIUtil.autoScaleDown(n9));
            }
        }
        image.dispose();
    }

    static {
        for (int i = 0; i < 9; ++i) {
            byte[][] byArray = ANY_TO_EIGHT;
            int n = i;
            byte[] byArray2 = new byte[1 << i];
            byArray[n] = byArray2;
            byte[] byArray3 = byArray2;
            if (i == 0) continue;
            int n2 = 0;
            int n3 = 65536;
            while ((n3 >>= i) != 0) {
                n2 |= n3;
            }
            int n4 = 0;
            for (int j = 0; j < 65536; j += n2) {
                byArray3[n4++] = (byte)(j >> 8);
            }
        }
        ONE_TO_ONE_MAPPING = ANY_TO_EIGHT[8];
        DITHER_MATRIX = new int[][]{{0xFC0000, 0x7C0000, 0xDC0000, 0x5C0000, 0xF40000, 0x740000, 0xD40000, 0x540000}, {0x3C0000, 0xBC0000, 0x1C0000, 0x9C0000, 0x340000, 0xB40000, 0x140000, 0x940000}, {0xCC0000, 0x4C0000, 0xEC0000, 0x6C0000, 0xC40000, 0x440000, 0xE40000, 0x640000}, {786432, 0x8C0000, 0x2C0000, 0xAC0000, 262144, 0x840000, 0x240000, 0xA40000}, {0xF00000, 0x700000, 0xD00000, 0x500000, 0xF80000, 0x780000, 0xD80000, 0x580000}, {0x300000, 0xB00000, 0x100000, 0x900000, 0x380000, 0xB80000, 0x180000, 0x980000}, {0xC00000, 0x400000, 0xE00000, 0x600000, 0xC80000, 0x480000, 0xE80000, 0x680000}, {0, 0x800000, 0x200000, 0xA00000, 524288, 0x880000, 0x280000, 0xA80000}};
    }
}

