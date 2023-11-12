/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.ImageFileNameProvider;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.BitmapData;
import org.eclipse.swt.internal.gdip.ColorPalette;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.DIBSECTION;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.OS;

public final class Image
extends Resource
implements Drawable {
    public int type;
    public long handle;
    int transparentPixel = -1;
    int transparentColor = -1;
    GC memGC;
    private ImageFileNameProvider imageFileNameProvider;
    private ImageDataProvider imageDataProvider;
    private int styleFlag = 0;
    private int currentDeviceZoom = 100;
    int width = -1;
    int height = -1;
    static final int DEFAULT_SCANLINE_PAD = 4;

    Image(Device device) {
        super(device);
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
    }

    public Image(Device device, int n, int n2) {
        super(device);
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        this.init(n, n2);
        this.init();
    }

    public Image(Device device, Image image, int n) {
        super(device);
        device = this.device;
        if (image == null) {
            SWT.error(4);
        }
        if (image == false) {
            SWT.error(5);
        }
        Rectangle rectangle = image.getBoundsInPixels();
        this.type = image.type;
        this.imageDataProvider = image.imageDataProvider;
        this.imageFileNameProvider = image.imageFileNameProvider;
        this.styleFlag = image.styleFlag | n;
        this.currentDeviceZoom = image.currentDeviceZoom;
        block0 : switch (n) {
            case 0: {
                switch (this.type) {
                    case 0: {
                        long l2 = device.internal_new_GC(null);
                        long l3 = OS.CreateCompatibleDC(l2);
                        long l4 = OS.CreateCompatibleDC(l2);
                        long l5 = OS.SelectObject(l3, image.handle);
                        BITMAP bITMAP = new BITMAP();
                        OS.GetObject(image.handle, BITMAP.sizeof, bITMAP);
                        this.handle = OS.CreateCompatibleBitmap(l3, rectangle.width, bITMAP.bmBits != 0L ? -rectangle.height : rectangle.height);
                        if (this.handle == 0L) {
                            SWT.error(2);
                        }
                        long l6 = OS.SelectObject(l4, this.handle);
                        OS.BitBlt(l4, 0, 0, rectangle.width, rectangle.height, l3, 0, 0, 0xCC0020);
                        OS.SelectObject(l3, l5);
                        OS.SelectObject(l4, l6);
                        OS.DeleteDC(l3);
                        OS.DeleteDC(l4);
                        device.internal_dispose_GC(l2, null);
                        this.transparentPixel = image.transparentPixel;
                        break;
                    }
                    case 1: {
                        this.handle = OS.CopyImage(image.handle, 1, rectangle.width, rectangle.height, 0);
                        if (this.handle != 0L) break block0;
                        SWT.error(2);
                        break;
                    }
                    default: {
                        SWT.error(40);
                        break;
                    }
                }
                break;
            }
            case 1: {
                ImageData imageData = image.getImageDataAtCurrentZoom();
                PaletteData paletteData = imageData.palette;
                RGB[] rGBArray = new RGB[]{device.getSystemColor(2).getRGB(), device.getSystemColor(18).getRGB(), device.getSystemColor(22).getRGB()};
                ImageData imageData2 = new ImageData(rectangle.width, rectangle.height, 8, new PaletteData(rGBArray));
                imageData2.alpha = imageData.alpha;
                imageData2.alphaData = imageData.alphaData;
                imageData2.maskData = imageData.maskData;
                imageData2.maskPad = imageData.maskPad;
                if (imageData.transparentPixel != -1) {
                    imageData2.transparentPixel = 0;
                }
                int[] nArray = new int[rectangle.width];
                int[] nArray2 = null;
                ImageData imageData3 = null;
                if (imageData.maskData != null) {
                    imageData3 = imageData.getTransparencyMask();
                }
                if (imageData3 != null) {
                    nArray2 = new int[rectangle.width];
                }
                int n2 = paletteData.redMask;
                int n3 = paletteData.greenMask;
                int n4 = paletteData.blueMask;
                int n5 = paletteData.redShift;
                int n6 = paletteData.greenShift;
                int n7 = paletteData.blueShift;
                for (int i = 0; i < rectangle.height; ++i) {
                    int n8 = i * imageData2.bytesPerLine;
                    imageData.getPixels(0, i, rectangle.width, nArray, 0);
                    if (imageData3 != null) {
                        imageData3.getPixels(0, i, rectangle.width, nArray2, 0);
                    }
                    for (int j = 0; j < rectangle.width; ++j) {
                        int n9 = nArray[j];
                        if (!(imageData.transparentPixel != -1 && n9 == imageData.transparentPixel || imageData3 != null && nArray2[j] == 0)) {
                            int n10;
                            int n11;
                            int n12;
                            if (paletteData.isDirect) {
                                n12 = n9 & n2;
                                n12 = n5 < 0 ? n12 >>> -n5 : n12 << n5;
                                n11 = n9 & n3;
                                n11 = n6 < 0 ? n11 >>> -n6 : n11 << n6;
                                n10 = n9 & n4;
                                n10 = n7 < 0 ? n10 >>> -n7 : n10 << n7;
                            } else {
                                n12 = paletteData.colors[n9].red;
                                n11 = paletteData.colors[n9].green;
                                n10 = paletteData.colors[n9].blue;
                            }
                            int n13 = n12 * n12 + n11 * n11 + n10 * n10;
                            imageData2.data[n8] = n13 < 98304 ? 1 : 2;
                        }
                        ++n8;
                    }
                }
                this.init(imageData2);
                break;
            }
            case 2: {
                ImageData imageData = image.getImageDataAtCurrentZoom();
                PaletteData paletteData = imageData.palette;
                ImageData imageData4 = imageData;
                if (!paletteData.isDirect) {
                    RGB[] rGBArray = paletteData.getRGBs();
                    for (int i = 0; i < rGBArray.length; ++i) {
                        int n14;
                        if (imageData.transparentPixel == i) continue;
                        RGB rGB = rGBArray[i];
                        int n15 = rGB.red;
                        int n16 = rGB.green;
                        int n17 = rGB.blue;
                        int n18 = n15 + n15 + n16 + n16 + n16 + n16 + n16 + n17 >> 3;
                        RGB rGB2 = rGB;
                        RGB rGB3 = rGB;
                        RGB rGB4 = rGB;
                        rGB4.blue = n14 = n18;
                        rGB3.green = n14;
                        rGB2.red = n14;
                    }
                    imageData4.palette = new PaletteData(rGBArray);
                } else {
                    RGB[] rGBArray = new RGB[256];
                    for (int i = 0; i < rGBArray.length; ++i) {
                        rGBArray[i] = new RGB(i, i, i);
                    }
                    imageData4 = new ImageData(rectangle.width, rectangle.height, 8, new PaletteData(rGBArray));
                    imageData4.alpha = imageData.alpha;
                    imageData4.alphaData = imageData.alphaData;
                    imageData4.maskData = imageData.maskData;
                    imageData4.maskPad = imageData.maskPad;
                    if (imageData.transparentPixel != -1) {
                        imageData4.transparentPixel = 254;
                    }
                    int[] nArray = new int[rectangle.width];
                    int n19 = paletteData.redMask;
                    int n20 = paletteData.greenMask;
                    int n21 = paletteData.blueMask;
                    int n22 = paletteData.redShift;
                    int n23 = paletteData.greenShift;
                    int n24 = paletteData.blueShift;
                    for (int i = 0; i < rectangle.height; ++i) {
                        int n25 = i * imageData4.bytesPerLine;
                        imageData.getPixels(0, i, rectangle.width, nArray, 0);
                        for (int j = 0; j < rectangle.width; ++j) {
                            int n26 = nArray[j];
                            if (n26 != imageData.transparentPixel) {
                                int n27 = n26 & n19;
                                n27 = n22 < 0 ? n27 >>> -n22 : n27 << n22;
                                int n28 = n26 & n20;
                                n28 = n23 < 0 ? n28 >>> -n23 : n28 << n23;
                                int n29 = n26 & n21;
                                n29 = n24 < 0 ? n29 >>> -n24 : n29 << n24;
                                int n30 = n27 + n27 + n28 + n28 + n28 + n28 + n28 + n29 >> 3;
                                if (imageData4.transparentPixel == n30) {
                                    n30 = 255;
                                }
                                imageData4.data[n25] = (byte)n30;
                            } else {
                                imageData4.data[n25] = -2;
                            }
                            ++n25;
                        }
                    }
                }
                this.init(imageData4);
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.init();
    }

    public Image(Device device, Rectangle rectangle) {
        super(device);
        if (rectangle == null) {
            SWT.error(4);
        }
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        rectangle = DPIUtil.autoScaleUp(rectangle);
        this.init(rectangle.width, rectangle.height);
        this.init();
    }

    public Image(Device device, ImageData imageData) {
        super(device);
        if (imageData == null) {
            SWT.error(4);
        }
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        imageData = DPIUtil.autoScaleUp(device, imageData);
        this.init(imageData);
        this.init();
    }

    public Image(Device device, ImageData imageData, ImageData imageData2) {
        super(device);
        if (imageData == null) {
            SWT.error(4);
        }
        if (imageData2 == null) {
            SWT.error(4);
        }
        if (imageData.width != imageData2.width || imageData.height != imageData2.height) {
            SWT.error(5);
        }
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        imageData = DPIUtil.autoScaleUp(device, imageData);
        imageData2 = DPIUtil.autoScaleUp(device, imageData2);
        imageData2 = ImageData.convertMask(imageData2);
        Image.init(this.device, this, imageData, imageData2);
        this.init();
    }

    public Image(Device device, InputStream inputStream) {
        super(device);
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        ImageData imageData = DPIUtil.autoScaleUp(device, new ImageData(inputStream));
        this.init(imageData);
        this.init();
    }

    public Image(Device device, String string) {
        super(device);
        if (string == null) {
            SWT.error(4);
        }
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        ImageData imageData = DPIUtil.autoScaleUp(device, new ImageData(string));
        this.init(imageData);
        this.init();
    }

    public Image(Device device, ImageFileNameProvider imageFileNameProvider) {
        super(device);
        this.imageFileNameProvider = imageFileNameProvider;
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        boolean[] blArray = new boolean[]{false};
        String string = DPIUtil.validateAndGetImagePathAtZoom(imageFileNameProvider, this.currentDeviceZoom, blArray);
        if (blArray[0]) {
            this.initNative(string);
            if (this.handle == 0L) {
                this.init(new ImageData(string));
            }
        } else {
            ImageData imageData = DPIUtil.autoScaleUp(device, new ImageData(string));
            this.init(imageData);
        }
        this.init();
    }

    public Image(Device device, ImageDataProvider imageDataProvider) {
        super(device);
        this.imageDataProvider = imageDataProvider;
        this.currentDeviceZoom = DPIUtil.getDeviceZoom();
        boolean[] blArray = new boolean[]{false};
        ImageData imageData = DPIUtil.validateAndGetImageDataAtZoom(imageDataProvider, this.currentDeviceZoom, blArray);
        if (blArray[0]) {
            this.init(imageData);
        } else {
            ImageData imageData2 = DPIUtil.autoScaleUp(device, imageData);
            this.init(imageData2);
        }
        this.init();
    }

    boolean refreshImageForZoom() {
        boolean bl = false;
        int n = DPIUtil.getDeviceZoom();
        if (this.imageFileNameProvider != null) {
            if (n != this.currentDeviceZoom) {
                boolean[] blArray = new boolean[]{false};
                String string = DPIUtil.validateAndGetImagePathAtZoom(this.imageFileNameProvider, n, blArray);
                if (blArray[0] || this.currentDeviceZoom != 100) {
                    this.destroy();
                    this.initNative(string);
                    if (this.handle == 0L) {
                        this.init(new ImageData(string));
                    }
                    this.init();
                    bl = true;
                }
                if (!blArray[0]) {
                    this.destroy();
                    ImageData imageData = DPIUtil.autoScaleUp(this.device, new ImageData(string));
                    this.init(imageData);
                    this.init();
                    bl = true;
                }
                this.currentDeviceZoom = n;
            }
        } else if (this.imageDataProvider != null) {
            if (n != this.currentDeviceZoom) {
                boolean[] blArray = new boolean[]{false};
                ImageData imageData = DPIUtil.validateAndGetImageDataAtZoom(this.imageDataProvider, n, blArray);
                if (blArray[0] || this.currentDeviceZoom != 100) {
                    this.destroy();
                    this.init(imageData);
                    this.init();
                    bl = true;
                }
                if (!blArray[0]) {
                    this.destroy();
                    ImageData imageData2 = DPIUtil.autoScaleUp(this.device, imageData);
                    this.init(imageData2);
                    this.init();
                    bl = true;
                }
                this.currentDeviceZoom = n;
            }
        } else if (n != this.currentDeviceZoom) {
            ImageData imageData = this.getImageDataAtCurrentZoom();
            this.destroy();
            ImageData imageData3 = DPIUtil.autoScaleImageData(this.device, imageData, n, this.currentDeviceZoom);
            this.init(imageData3);
            this.init();
            bl = true;
            this.currentDeviceZoom = n;
        }
        return bl;
    }

    void initNative(String string) {
        this.device.checkGDIP();
        boolean bl = true;
        if (bl && C.PTR_SIZEOF == 8 && string.toLowerCase().endsWith(".gif")) {
            bl = false;
        }
        if (OS.WIN32_VERSION >= OS.VERSION(6, 1) && string.toLowerCase().endsWith(".gif")) {
            bl = false;
        }
        if (bl) {
            int n = string.length();
            char[] cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
            long l2 = Gdip.Bitmap_new(cArray, false);
            if (l2 != 0L) {
                int n2 = 2;
                int n3 = Gdip.Image_GetLastStatus(l2);
                if (n3 == 0) {
                    if (string.toLowerCase().endsWith(".ico")) {
                        this.type = 1;
                        long[] lArray = new long[]{0L};
                        n3 = Gdip.Bitmap_GetHICON(l2, lArray);
                        this.handle = lArray[0];
                    } else {
                        this.type = 0;
                        int n4 = Gdip.Image_GetWidth(l2);
                        int n5 = Gdip.Image_GetHeight(l2);
                        int n6 = Gdip.Image_GetPixelFormat(l2);
                        switch (n6) {
                            case 135173: 
                            case 135174: {
                                this.handle = Image.createDIB(n4, n5, 16);
                                break;
                            }
                            case 8207: 
                            case 137224: {
                                this.handle = Image.createDIB(n4, n5, 24);
                                break;
                            }
                            case 139273: 
                            case 925707: 
                            case 0x101004: 
                            case 1060876: 
                            case 1851406: 
                            case 3424269: {
                                this.handle = Image.createDIB(n4, n5, 32);
                            }
                        }
                        if (this.handle != 0L) {
                            long l3 = this.device.internal_new_GC(null);
                            long l4 = OS.CreateCompatibleDC(l3);
                            long l5 = OS.SelectObject(l4, this.handle);
                            long l6 = Gdip.Graphics_new(l4);
                            if (l6 != 0L) {
                                Rect rect = new Rect();
                                rect.Width = n4;
                                rect.Height = n5;
                                n3 = Gdip.Graphics_DrawImage(l6, l2, rect, 0, 0, n4, n5, 2, 0L, 0L, 0L);
                                if (n3 != 0) {
                                    n2 = 40;
                                    OS.DeleteObject(this.handle);
                                    this.handle = 0L;
                                }
                                Gdip.Graphics_delete(l6);
                            }
                            OS.SelectObject(l4, l5);
                            OS.DeleteDC(l4);
                            this.device.internal_dispose_GC(l3, null);
                        } else {
                            long l7 = Gdip.BitmapData_new();
                            if (l7 != 0L) {
                                n3 = Gdip.Bitmap_LockBits(l2, 0L, 0, n6, l7);
                                if (n3 == 0) {
                                    BitmapData bitmapData = new BitmapData();
                                    Gdip.MoveMemory(bitmapData, l7);
                                    int n7 = bitmapData.Stride;
                                    long l8 = bitmapData.Scan0;
                                    int n8 = 0;
                                    int n9 = 4;
                                    int n10 = -1;
                                    switch (bitmapData.PixelFormat) {
                                        case 196865: {
                                            n8 = 1;
                                            break;
                                        }
                                        case 197634: {
                                            n8 = 4;
                                            break;
                                        }
                                        case 198659: {
                                            n8 = 8;
                                            break;
                                        }
                                        case 135173: 
                                        case 135174: 
                                        case 397319: {
                                            n8 = 16;
                                            break;
                                        }
                                        case 137224: {
                                            n8 = 24;
                                            break;
                                        }
                                        case 139273: 
                                        case 2498570: {
                                            n8 = 32;
                                        }
                                    }
                                    if (n8 != 0) {
                                        PaletteData paletteData = null;
                                        switch (bitmapData.PixelFormat) {
                                            case 196865: 
                                            case 197634: 
                                            case 198659: {
                                                int n11 = Gdip.Image_GetPaletteSize(l2);
                                                long l9 = OS.GetProcessHeap();
                                                long l10 = OS.HeapAlloc(l9, 8, n11);
                                                if (l10 == 0L) {
                                                    SWT.error(2);
                                                }
                                                Gdip.Image_GetPalette(l2, l10, n11);
                                                ColorPalette colorPalette = new ColorPalette();
                                                Gdip.MoveMemory(colorPalette, l10, ColorPalette.sizeof);
                                                int[] nArray = new int[colorPalette.Count];
                                                OS.MoveMemory(nArray, l10 + 8L, nArray.length * 4);
                                                OS.HeapFree(l9, 0, l10);
                                                RGB[] rGBArray = new RGB[colorPalette.Count];
                                                paletteData = new PaletteData(rGBArray);
                                                for (int i = 0; i < nArray.length; ++i) {
                                                    if ((nArray[i] >> 24 & 0xFF) == 0 && (colorPalette.Flags & 1) != 0) {
                                                        n10 = i;
                                                    }
                                                    rGBArray[i] = new RGB((nArray[i] & 0xFF0000) >> 16, (nArray[i] & 0xFF00) >> 8, (nArray[i] & 0xFF) >> 0);
                                                }
                                                break;
                                            }
                                            case 135173: 
                                            case 397319: {
                                                paletteData = new PaletteData(31744, 992, 31);
                                                break;
                                            }
                                            case 135174: {
                                                paletteData = new PaletteData(63488, 2016, 31);
                                                break;
                                            }
                                            case 137224: {
                                                paletteData = new PaletteData(255, 65280, 0xFF0000);
                                                break;
                                            }
                                            case 139273: 
                                            case 2498570: {
                                                paletteData = new PaletteData(65280, 0xFF0000, -16777216);
                                            }
                                        }
                                        byte[] byArray = new byte[n7 * n5];
                                        byte[] byArray2 = null;
                                        OS.MoveMemory(byArray, l8, byArray.length);
                                        switch (bitmapData.PixelFormat) {
                                            case 397319: {
                                                byArray2 = new byte[n4 * n5];
                                                int n12 = 1;
                                                int n13 = 0;
                                                while (n12 < byArray.length) {
                                                    byArray2[n13] = (byte)((byArray[n12] & 0x80) != 0 ? 255 : 0);
                                                    n12 += 2;
                                                    ++n13;
                                                }
                                                break;
                                            }
                                            case 2498570: {
                                                byArray2 = new byte[n4 * n5];
                                                int n12 = 3;
                                                int n14 = 0;
                                                while (n12 < byArray.length) {
                                                    byArray2[n14] = byArray[n12];
                                                    n12 += 4;
                                                    ++n14;
                                                }
                                                break;
                                            }
                                        }
                                        ImageData imageData = new ImageData(n4, n5, n8, paletteData, 4, byArray);
                                        imageData.transparentPixel = n10;
                                        imageData.alphaData = byArray2;
                                        this.init(imageData);
                                    }
                                    Gdip.Bitmap_UnlockBits(l2, l7);
                                } else {
                                    n2 = 40;
                                }
                                Gdip.BitmapData_delete(l7);
                            }
                        }
                    }
                }
                Gdip.Bitmap_delete(l2);
                if (n3 == 0 && this.handle == 0L) {
                    SWT.error(n2);
                }
            }
        }
    }

    long[] createGdipImage() {
        switch (this.type) {
            case 0: {
                boolean bl;
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(this.handle, BITMAP.sizeof, bITMAP);
                int n = bITMAP.bmPlanes * bITMAP.bmBitsPixel;
                boolean bl2 = bITMAP.bmBits != 0L;
                boolean bl3 = bl = bl2 && n == 32;
                if (bl || this.transparentPixel != -1) {
                    int n2 = bITMAP.bmWidth;
                    int n3 = bITMAP.bmHeight;
                    long l2 = this.device.internal_new_GC(null);
                    if (l2 == 0L) {
                        SWT.error(2);
                    }
                    long l3 = OS.CreateCompatibleDC(l2);
                    long l4 = OS.CreateCompatibleDC(l2);
                    this.device.internal_dispose_GC(l2, null);
                    if (l3 == 0L) {
                        SWT.error(2);
                    }
                    if (l4 == 0L) {
                        SWT.error(2);
                    }
                    long l5 = OS.SelectObject(l3, this.handle);
                    long l6 = Image.createDIB(n2, n3, 32);
                    if (l6 == 0L) {
                        SWT.error(2);
                    }
                    long l7 = OS.SelectObject(l4, l6);
                    BITMAP bITMAP2 = new BITMAP();
                    OS.GetObject(l6, BITMAP.sizeof, bITMAP2);
                    int n4 = bITMAP2.bmWidthBytes * bITMAP2.bmHeight;
                    OS.BitBlt(l4, 0, 0, n2, n3, l3, 0, 0, 0xCC0020);
                    long l8 = OS.GetProcessHeap();
                    long l9 = OS.HeapAlloc(l8, 8, n4);
                    if (l9 == 0L) {
                        SWT.error(2);
                    }
                    byte by = 0;
                    byte by2 = 0;
                    byte by3 = 0;
                    if (bl) {
                        OS.MoveMemory(l9, bITMAP.bmBits, n4);
                    } else {
                        int n5;
                        int n6;
                        byte[] byArray;
                        if (bITMAP.bmBitsPixel <= 8) {
                            byArray = new byte[4];
                            OS.GetDIBColorTable(l3, this.transparentPixel, 1, byArray);
                            by3 = byArray[0];
                            by2 = byArray[1];
                            by = byArray[2];
                        } else {
                            switch (bITMAP.bmBitsPixel) {
                                case 16: {
                                    int n7 = 31;
                                    n6 = ImageData.getChannelShift(31);
                                    byte[] byArray2 = ImageData.ANY_TO_EIGHT[ImageData.getChannelWidth(31, n6)];
                                    by3 = byArray2[(this.transparentPixel & 0x1F) >> n6];
                                    n5 = 992;
                                    int n8 = ImageData.getChannelShift(992);
                                    byte[] byArray3 = ImageData.ANY_TO_EIGHT[ImageData.getChannelWidth(992, n8)];
                                    by2 = byArray3[(this.transparentPixel & 0x3E0) >> n8];
                                    int n9 = 31744;
                                    int n10 = ImageData.getChannelShift(31744);
                                    byte[] byArray4 = ImageData.ANY_TO_EIGHT[ImageData.getChannelWidth(31744, n10)];
                                    by = byArray4[(this.transparentPixel & 0x7C00) >> n10];
                                    break;
                                }
                                case 24: {
                                    by3 = (byte)((this.transparentPixel & 0xFF0000) >> 16);
                                    by2 = (byte)((this.transparentPixel & 0xFF00) >> 8);
                                    by = (byte)(this.transparentPixel & 0xFF);
                                    break;
                                }
                                case 32: {
                                    by3 = (byte)((this.transparentPixel & 0xFF000000) >>> 24);
                                    by2 = (byte)((this.transparentPixel & 0xFF0000) >> 16);
                                    by = (byte)((this.transparentPixel & 0xFF00) >> 8);
                                }
                            }
                        }
                        byArray = new byte[n4];
                        OS.MoveMemory(byArray, bITMAP2.bmBits, n4);
                        int n11 = 0;
                        for (n6 = 0; n6 < n3; ++n6) {
                            for (n5 = 0; n5 < n2; ++n5) {
                                byArray[n11 + 3] = byArray[n11] == by3 && byArray[n11 + 1] == by2 && byArray[n11 + 2] == by ? 0 : -1;
                                n11 += 4;
                            }
                        }
                        OS.MoveMemory(l9, byArray, n4);
                    }
                    OS.SelectObject(l3, l5);
                    OS.SelectObject(l4, l7);
                    OS.DeleteObject(l3);
                    OS.DeleteObject(l4);
                    OS.DeleteObject(l6);
                    int n12 = bl ? 925707 : 2498570;
                    return new long[]{Gdip.Bitmap_new(n2, n3, bITMAP2.bmWidthBytes, n12, l9), l9};
                }
                return new long[]{Gdip.Bitmap_new(this.handle, 0L), 0L};
            }
            case 1: {
                ICONINFO iCONINFO = new ICONINFO();
                OS.GetIconInfo(this.handle, iCONINFO);
                long l10 = iCONINFO.hbmColor;
                if (l10 == 0L) {
                    l10 = iCONINFO.hbmMask;
                }
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(l10, BITMAP.sizeof, bITMAP);
                int n = bITMAP.bmWidth;
                int n13 = l10 == iCONINFO.hbmMask ? bITMAP.bmHeight / 2 : bITMAP.bmHeight;
                long l11 = 0L;
                long l12 = 0L;
                if (n > n13 || bITMAP.bmBitsPixel == 32) {
                    long l13 = this.device.internal_new_GC(null);
                    long l14 = OS.CreateCompatibleDC(l13);
                    long l15 = OS.SelectObject(l14, l10);
                    long l16 = OS.CreateCompatibleDC(l13);
                    long l17 = Image.createDIB(n, n13, 32);
                    if (l17 == 0L) {
                        SWT.error(2);
                    }
                    long l18 = OS.SelectObject(l16, l17);
                    BITMAP bITMAP3 = new BITMAP();
                    OS.GetObject(l17, BITMAP.sizeof, bITMAP3);
                    OS.BitBlt(l16, 0, 0, n, n13, l14, 0, l10 == iCONINFO.hbmMask ? n13 : 0, 0xCC0020);
                    OS.SelectObject(l16, l18);
                    OS.DeleteObject(l16);
                    byte[] byArray = new byte[bITMAP3.bmWidthBytes * bITMAP3.bmHeight];
                    OS.MoveMemory(byArray, bITMAP3.bmBits, byArray.length);
                    OS.DeleteObject(l17);
                    OS.SelectObject(l14, iCONINFO.hbmMask);
                    int n14 = 3;
                    for (int i = 0; i < n13; ++i) {
                        for (int j = 0; j < n; ++j) {
                            if (byArray[n14] == 0) {
                                byArray[n14] = OS.GetPixel(l14, j, i) != 0 ? 0 : -1;
                            }
                            n14 += 4;
                        }
                    }
                    OS.SelectObject(l14, l15);
                    OS.DeleteObject(l14);
                    this.device.internal_dispose_GC(l13, null);
                    long l19 = OS.GetProcessHeap();
                    l12 = OS.HeapAlloc(l19, 8, byArray.length);
                    if (l12 == 0L) {
                        SWT.error(2);
                    }
                    OS.MoveMemory(l12, byArray, byArray.length);
                    l11 = Gdip.Bitmap_new(n, n13, bITMAP3.bmWidthBytes, 2498570, l12);
                } else {
                    l11 = Gdip.Bitmap_new(this.handle);
                }
                if (iCONINFO.hbmColor != 0L) {
                    OS.DeleteObject(iCONINFO.hbmColor);
                }
                if (iCONINFO.hbmMask != 0L) {
                    OS.DeleteObject(iCONINFO.hbmMask);
                }
                return new long[]{l11, l12};
            }
        }
        SWT.error(40);
        return null;
    }

    @Override
    void destroy() {
        if (this.memGC != null) {
            this.memGC.dispose();
        }
        if (this.type == 1) {
            OS.DestroyIcon(this.handle);
        } else {
            OS.DeleteObject(this.handle);
        }
        this.handle = 0L;
        this.memGC = null;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Image)) {
            return false;
        }
        Image image = (Image)object;
        if (this.device != image.device || this.transparentPixel != image.transparentPixel) {
            return false;
        }
        if (this.imageDataProvider != null && image.imageDataProvider != null) {
            return this.styleFlag == image.styleFlag && this.imageDataProvider.equals(image.imageDataProvider);
        }
        if (this.imageFileNameProvider != null && image.imageFileNameProvider != null) {
            return this.styleFlag == image.styleFlag && this.imageFileNameProvider.equals(image.imageFileNameProvider);
        }
        return this.handle == image.handle;
    }

    public Color getBackground() {
        if (this == false) {
            SWT.error(44);
        }
        if (this.transparentPixel == -1) {
            return null;
        }
        long l2 = this.device.internal_new_GC(null);
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(this.handle, BITMAP.sizeof, bITMAP);
        long l3 = OS.CreateCompatibleDC(l2);
        long l4 = OS.SelectObject(l3, this.handle);
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (bITMAP.bmBitsPixel <= 8) {
            byte[] byArray = new byte[4];
            OS.GetDIBColorTable(l3, this.transparentPixel, 1, byArray);
            n3 = byArray[0] & 0xFF;
            n2 = byArray[1] & 0xFF;
            n = byArray[2] & 0xFF;
        } else {
            switch (bITMAP.bmBitsPixel) {
                case 16: {
                    n3 = (this.transparentPixel & 0x1F) << 3;
                    n2 = (this.transparentPixel & 0x3E0) >> 2;
                    n = (this.transparentPixel & 0x7C00) >> 7;
                    break;
                }
                case 24: {
                    n3 = (this.transparentPixel & 0xFF0000) >> 16;
                    n2 = (this.transparentPixel & 0xFF00) >> 8;
                    n = this.transparentPixel & 0xFF;
                    break;
                }
                case 32: {
                    n3 = (this.transparentPixel & 0xFF000000) >>> 24;
                    n2 = (this.transparentPixel & 0xFF0000) >> 16;
                    n = (this.transparentPixel & 0xFF00) >> 8;
                    break;
                }
                default: {
                    return null;
                }
            }
        }
        OS.SelectObject(l3, l4);
        OS.DeleteDC(l3);
        this.device.internal_dispose_GC(l2, null);
        return Color.win32_new(this.device, n3 << 16 | n2 << 8 | n);
    }

    public Rectangle getBounds() {
        if (this == false) {
            SWT.error(44);
        }
        return this.getBounds(100);
    }

    Rectangle getBounds(int n) {
        Rectangle rectangle = this.getBoundsInPixels();
        if (rectangle != null && n != this.currentDeviceZoom) {
            rectangle = DPIUtil.autoScaleBounds(rectangle, n, this.currentDeviceZoom);
        }
        return rectangle;
    }

    @Deprecated
    public Rectangle getBoundsInPixels() {
        if (this == false) {
            SWT.error(44);
        }
        if (this.width != -1 && this.height != -1) {
            return new Rectangle(0, 0, this.width, this.height);
        }
        switch (this.type) {
            case 0: {
                int n;
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(this.handle, BITMAP.sizeof, bITMAP);
                boolean bl = false;
                boolean bl2 = false;
                this.width = n = bITMAP.bmWidth;
                this.height = bITMAP.bmHeight;
                return new Rectangle(0, 0, n, this.height);
            }
            case 1: {
                int n;
                ICONINFO iCONINFO = new ICONINFO();
                OS.GetIconInfo(this.handle, iCONINFO);
                long l2 = iCONINFO.hbmColor;
                if (l2 == 0L) {
                    l2 = iCONINFO.hbmMask;
                }
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(l2, BITMAP.sizeof, bITMAP);
                if (l2 == iCONINFO.hbmMask) {
                    BITMAP bITMAP2 = bITMAP;
                    bITMAP2.bmHeight /= 2;
                }
                if (iCONINFO.hbmColor != 0L) {
                    OS.DeleteObject(iCONINFO.hbmColor);
                }
                if (iCONINFO.hbmMask != 0L) {
                    OS.DeleteObject(iCONINFO.hbmMask);
                }
                boolean bl = false;
                boolean bl3 = false;
                this.width = n = bITMAP.bmWidth;
                this.height = bITMAP.bmHeight;
                return new Rectangle(0, 0, n, this.height);
            }
        }
        SWT.error(40);
        return null;
    }

    public ImageData getImageData() {
        if (this == false) {
            SWT.error(44);
        }
        return this.getImageData(100);
    }

    public ImageData getImageData(int n) {
        if (this == false) {
            SWT.error(44);
        }
        if (n == this.currentDeviceZoom) {
            return this.getImageDataAtCurrentZoom();
        }
        if (this.imageDataProvider != null) {
            boolean[] blArray = new boolean[]{false};
            ImageData imageData = DPIUtil.validateAndGetImageDataAtZoom(this.imageDataProvider, n, blArray);
            if (blArray[0]) {
                return imageData;
            }
            return DPIUtil.autoScaleImageData(this.device, imageData, n, 100);
        }
        if (this.imageFileNameProvider == null) {
            return DPIUtil.autoScaleImageData(this.device, this.getImageDataAtCurrentZoom(), n, this.currentDeviceZoom);
        }
        boolean[] blArray = new boolean[]{false};
        String string = DPIUtil.validateAndGetImagePathAtZoom(this.imageFileNameProvider, n, blArray);
        if (blArray[0]) {
            return new ImageData(string);
        }
        return DPIUtil.autoScaleImageData(this.device, new ImageData(string), n, 100);
    }

    @Deprecated
    public ImageData getImageDataAtCurrentZoom() {
        if (this == false) {
            SWT.error(44);
        }
        switch (this.type) {
            case 1: {
                int n;
                Object[] objectArray;
                ICONINFO iCONINFO = new ICONINFO();
                OS.GetIconInfo(this.handle, iCONINFO);
                long l2 = iCONINFO.hbmColor;
                if (l2 == 0L) {
                    l2 = iCONINFO.hbmMask;
                }
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(l2, BITMAP.sizeof, bITMAP);
                int n2 = bITMAP.bmPlanes * bITMAP.bmBitsPixel;
                int n3 = bITMAP.bmWidth;
                if (l2 == iCONINFO.hbmMask) {
                    BITMAP bITMAP2 = bITMAP;
                    bITMAP2.bmHeight /= 2;
                }
                int n4 = bITMAP.bmHeight;
                int n5 = 0;
                if (n2 <= 8) {
                    n5 = 1 << n2;
                }
                BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
                bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
                bITMAPINFOHEADER.biWidth = n3;
                bITMAPINFOHEADER.biHeight = -n4;
                bITMAPINFOHEADER.biPlanes = 1;
                bITMAPINFOHEADER.biBitCount = (short)n2;
                bITMAPINFOHEADER.biCompression = 0;
                byte[] byArray = new byte[BITMAPINFOHEADER.sizeof + n5 * 4];
                OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
                long l3 = this.device.internal_new_GC(null);
                long l4 = OS.CreateCompatibleDC(l3);
                long l5 = OS.SelectObject(l4, l2);
                OS.GetDIBits(l4, l2, 0, n4, null, byArray, 0);
                OS.MoveMemory(bITMAPINFOHEADER, byArray, BITMAPINFOHEADER.sizeof);
                int n6 = bITMAPINFOHEADER.biSizeImage;
                byte[] byArray2 = new byte[n6];
                OS.GetDIBits(l4, l2, 0, n4, byArray2, byArray, 0);
                PaletteData paletteData = null;
                if (n2 <= 8) {
                    objectArray = new RGB[n5];
                    n = 40;
                    for (int i = 0; i < n5; ++i) {
                        objectArray[i] = new RGB(byArray[n + 2] & 0xFF, byArray[n + 1] & 0xFF, byArray[n] & 0xFF);
                        n += 4;
                    }
                    paletteData = new PaletteData((RGB[])objectArray);
                } else if (n2 == 16) {
                    paletteData = new PaletteData(31744, 992, 31);
                } else if (n2 == 24) {
                    paletteData = new PaletteData(255, 65280, 0xFF0000);
                } else if (n2 == 32) {
                    paletteData = new PaletteData(65280, 0xFF0000, -16777216);
                } else {
                    SWT.error(38);
                }
                objectArray = null;
                if (iCONINFO.hbmColor == 0L) {
                    objectArray = new byte[n6];
                    OS.GetDIBits(l4, l2, n4, n4, (byte[])objectArray, byArray, 0);
                } else {
                    int n7;
                    int n8;
                    bITMAPINFOHEADER = new BITMAPINFOHEADER();
                    bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
                    bITMAPINFOHEADER.biWidth = n3;
                    bITMAPINFOHEADER.biHeight = -n4;
                    bITMAPINFOHEADER.biPlanes = 1;
                    bITMAPINFOHEADER.biBitCount = 1;
                    bITMAPINFOHEADER.biCompression = 0;
                    byArray = new byte[BITMAPINFOHEADER.sizeof + 8];
                    OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
                    n = BITMAPINFOHEADER.sizeof;
                    byte[] byArray3 = byArray;
                    int n9 = n + 4;
                    byte[] byArray4 = byArray;
                    int n10 = n + 5;
                    byte[] byArray5 = byArray;
                    int n11 = n + 6;
                    int n12 = -1;
                    byArray5[n11] = -1;
                    byArray4[n10] = -1;
                    byArray3[n9] = -1;
                    byArray[n + 7] = 0;
                    OS.SelectObject(l4, iCONINFO.hbmMask);
                    OS.GetDIBits(l4, iCONINFO.hbmMask, 0, n4, null, byArray, 0);
                    OS.MoveMemory(bITMAPINFOHEADER, byArray, BITMAPINFOHEADER.sizeof);
                    n6 = bITMAPINFOHEADER.biSizeImage;
                    objectArray = new byte[n6];
                    OS.GetDIBits(l4, iCONINFO.hbmMask, 0, n4, (byte[])objectArray, byArray, 0);
                    int n13 = 0;
                    while (n13 < objectArray.length) {
                        Object[] objectArray2 = objectArray;
                        int n14 = n8 = n13++;
                        objectArray2[n14] = (RGB)((byte)(~objectArray2[n14]));
                    }
                    n13 = n6 / n4;
                    for (n7 = 1; n7 < 128 && (n8 = ((n3 + 7) / 8 + (n7 - 1)) / n7 * n7) != n13; ++n7) {
                    }
                    objectArray = ImageData.convertPad((byte[])objectArray, n3, n4, 1, n7, 2);
                }
                OS.SelectObject(l4, l5);
                OS.DeleteDC(l4);
                this.device.internal_dispose_GC(l3, null);
                if (iCONINFO.hbmColor != 0L) {
                    OS.DeleteObject(iCONINFO.hbmColor);
                }
                if (iCONINFO.hbmMask != 0L) {
                    OS.DeleteObject(iCONINFO.hbmMask);
                }
                ImageData imageData = new ImageData(n3, n4, n2, paletteData, 4, byArray2);
                imageData.maskData = (byte[])objectArray;
                imageData.maskPad = 2;
                return imageData;
            }
            case 0: {
                int n;
                Object object;
                int n15;
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(this.handle, BITMAP.sizeof, bITMAP);
                int n16 = bITMAP.bmPlanes * bITMAP.bmBitsPixel;
                int n17 = bITMAP.bmWidth;
                int n18 = bITMAP.bmHeight;
                boolean bl = bITMAP.bmBits != 0L;
                long l6 = this.device.internal_new_GC(null);
                DIBSECTION dIBSECTION = null;
                if (bl) {
                    dIBSECTION = new DIBSECTION();
                    OS.GetObject(this.handle, DIBSECTION.sizeof, dIBSECTION);
                }
                int n19 = 0;
                if (n16 <= 8) {
                    n19 = bl ? dIBSECTION.biClrUsed : 1 << n16;
                }
                byte[] byArray = null;
                BITMAPINFOHEADER bITMAPINFOHEADER = null;
                if (!bl) {
                    bITMAPINFOHEADER = new BITMAPINFOHEADER();
                    bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
                    bITMAPINFOHEADER.biWidth = n17;
                    bITMAPINFOHEADER.biHeight = -n18;
                    bITMAPINFOHEADER.biPlanes = 1;
                    bITMAPINFOHEADER.biBitCount = (short)n16;
                    bITMAPINFOHEADER.biCompression = 0;
                    byArray = new byte[BITMAPINFOHEADER.sizeof + n19 * 4];
                    OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
                }
                long l7 = OS.CreateCompatibleDC(l6);
                long l8 = OS.SelectObject(l7, this.handle);
                if (bl) {
                    n15 = dIBSECTION.biSizeImage;
                } else {
                    OS.GetDIBits(l7, this.handle, 0, n18, null, byArray, 0);
                    OS.MoveMemory(bITMAPINFOHEADER, byArray, BITMAPINFOHEADER.sizeof);
                    n15 = bITMAPINFOHEADER.biSizeImage;
                }
                byte[] byArray6 = new byte[n15];
                if (bl) {
                    OS.MoveMemory(byArray6, bITMAP.bmBits, n15);
                } else {
                    OS.GetDIBits(l7, this.handle, 0, n18, byArray6, byArray, 0);
                }
                PaletteData paletteData = null;
                if (n16 <= 8) {
                    int n20;
                    object = new RGB[n19];
                    if (bl) {
                        byte[] byArray7 = new byte[n19 * 4];
                        OS.GetDIBColorTable(l7, 0, n19, byArray7);
                        n20 = 0;
                        for (n = 0; n < ((RGB[])object).length; ++n) {
                            object[n] = new RGB(byArray7[n20 + 2] & 0xFF, byArray7[n20 + 1] & 0xFF, byArray7[n20] & 0xFF);
                            n20 += 4;
                        }
                    } else {
                        int n21 = BITMAPINFOHEADER.sizeof;
                        for (n20 = 0; n20 < n19; ++n20) {
                            object[n20] = new RGB(byArray[n21 + 2] & 0xFF, byArray[n21 + 1] & 0xFF, byArray[n21] & 0xFF);
                            n21 += 4;
                        }
                    }
                    paletteData = new PaletteData((RGB)object);
                } else if (n16 == 16) {
                    paletteData = new PaletteData(31744, 992, 31);
                } else if (n16 == 24) {
                    paletteData = new PaletteData(255, 65280, 0xFF0000);
                } else if (n16 == 32) {
                    paletteData = new PaletteData(65280, 0xFF0000, -16777216);
                } else {
                    SWT.error(38);
                }
                OS.SelectObject(l7, l8);
                OS.DeleteDC(l7);
                this.device.internal_dispose_GC(l6, null);
                object = new ImageData(n17, n18, n16, paletteData, 4, byArray6);
                object.transparentPixel = this.transparentPixel;
                if (bl && n16 == 32) {
                    byte[] byArray8 = new byte[n15];
                    byte[] byArray9 = new byte[n17 * n18];
                    n = 1;
                    int n22 = 0;
                    int n23 = 0;
                    while (n != 0 && n22 < byArray9.length) {
                        int n24 = byArray6[n23] & 0xFF;
                        int n25 = byArray6[n23 + 1] & 0xFF;
                        int n26 = byArray6[n23 + 2] & 0xFF;
                        int n27 = byArray6[n23 + 3] & 0xFF;
                        byArray9[n22] = (byte)n27;
                        int n28 = n = n != 0 && n24 <= n27 && n25 <= n27 && n26 <= n27 ? 1 : 0;
                        if (n27 != 0) {
                            byArray8[n23] = (byte)((n24 * 255 + n27 / 2) / n27);
                            byArray8[n23 + 1] = (byte)((n25 * 255 + n27 / 2) / n27);
                            byArray8[n23 + 2] = (byte)((n26 * 255 + n27 / 2) / n27);
                        }
                        ++n22;
                        n23 += 4;
                    }
                    if (n != 0) {
                        object.data = byArray8;
                        object.alphaData = byArray9;
                    } else {
                        for (n22 = 3; n22 < n15; n22 += 4) {
                            byArray6[n22] = -1;
                        }
                    }
                }
                return object;
            }
        }
        SWT.error(40);
        return null;
    }

    public int hashCode() {
        if (this.imageDataProvider != null) {
            return this.imageDataProvider.hashCode();
        }
        if (this.imageFileNameProvider != null) {
            return this.imageFileNameProvider.hashCode();
        }
        return (int)this.handle;
    }

    void init(int n, int n2) {
        if (n <= 0 || n2 <= 0) {
            SWT.error(5);
        }
        this.type = 0;
        long l2 = this.device.internal_new_GC(null);
        this.handle = OS.CreateCompatibleBitmap(l2, n, n2);
        if (this.handle == 0L) {
            int n3;
            int n4 = OS.GetDeviceCaps(l2, 12);
            int n5 = n4 * (n3 = OS.GetDeviceCaps(l2, 14));
            if (n5 < 16) {
                n5 = 16;
            }
            if (n5 > 24) {
                n5 = 24;
            }
            this.handle = Image.createDIB(n, n2, n5);
        }
        if (this.handle != 0L) {
            long l3 = OS.CreateCompatibleDC(l2);
            long l4 = OS.SelectObject(l3, this.handle);
            OS.PatBlt(l3, 0, 0, n, n2, 15728673);
            OS.SelectObject(l3, l4);
            OS.DeleteDC(l3);
        }
        this.device.internal_dispose_GC(l2, null);
        if (this.handle == 0L) {
            SWT.error(2, null, this.device.getLastError());
        }
    }

    static long createDIB(int n, int n2, int n3) {
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = n;
        bITMAPINFOHEADER.biHeight = -n2;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)n3;
        bITMAPINFOHEADER.biCompression = 0;
        byte[] byArray = new byte[BITMAPINFOHEADER.sizeof];
        OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        return OS.CreateDIBSection(0L, byArray, 0, lArray, 0L, 0);
    }

    static long[] init(Device device, Image image, ImageData imageData) {
        long[] lArray;
        long l2;
        Object object;
        Object object2;
        int n;
        int n2;
        int n3;
        int n4;
        Object object3;
        boolean bl;
        if (imageData.depth == 2) {
            ImageData imageData2 = new ImageData(imageData.width, imageData.height, 4, imageData.palette);
            ImageData.blit(1, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, null, null, null, 255, null, 0, 0, 0, imageData2.data, imageData2.depth, imageData2.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData2.width, imageData2.height, null, null, null, false, false);
            imageData2.transparentPixel = imageData.transparentPixel;
            imageData2.maskPad = imageData.maskPad;
            imageData2.maskData = imageData.maskData;
            imageData2.alpha = imageData.alpha;
            imageData2.alphaData = imageData.alphaData;
            imageData = imageData2;
        }
        boolean bl2 = bl = imageData.alpha != -1 || imageData.alphaData != null;
        if (imageData.palette.isDirect) {
            object3 = imageData.palette;
            n4 = ((PaletteData)object3).redMask;
            n3 = ((PaletteData)object3).greenMask;
            n2 = ((PaletteData)object3).blueMask;
            n = imageData.depth;
            int n5 = 1;
            object2 = null;
            if (bl) {
                n = 32;
                object2 = new PaletteData(65280, 0xFF0000, -16777216);
            } else {
                switch (imageData.depth) {
                    case 8: {
                        int n6 = ImageData.getChannelWidth(n4, ((PaletteData)object3).redShift) + ImageData.getChannelWidth(n3, ((PaletteData)object3).greenShift) + ImageData.getChannelWidth(n2, ((PaletteData)object3).blueShift);
                        if (n6 <= 16) {
                            n = 16;
                            n5 = 0;
                            object2 = new PaletteData(31744, 992, 31);
                            break;
                        }
                        n = 24;
                        object2 = new PaletteData(255, 65280, 0xFF0000);
                        break;
                    }
                    case 16: {
                        n5 = 0;
                        if (n4 == 31744 && n3 == 992 && n2 == 31) break;
                        object2 = new PaletteData(31744, 992, 31);
                        break;
                    }
                    case 24: {
                        if (n4 == 255 && n3 == 65280 && n2 == 0xFF0000) break;
                        object2 = new PaletteData(255, 65280, 0xFF0000);
                        break;
                    }
                    case 32: {
                        n = 24;
                        object2 = new PaletteData(255, 65280, 0xFF0000);
                        break;
                    }
                    default: {
                        SWT.error(38);
                    }
                }
            }
            if (object2 != null) {
                ImageData imageData3 = new ImageData(imageData.width, imageData.height, n, (PaletteData)object2);
                ImageData.blit(1, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, n4, n3, n2, 255, null, 0, 0, 0, imageData3.data, imageData3.depth, imageData3.bytesPerLine, n5, 0, 0, imageData3.width, imageData3.height, ((PaletteData)object2).redMask, ((PaletteData)object2).greenMask, ((PaletteData)object2).blueMask, false, false);
                if (imageData.transparentPixel != -1) {
                    imageData3.transparentPixel = ((PaletteData)object2).getPixel(((PaletteData)object3).getRGB(imageData.transparentPixel));
                }
                imageData3.maskPad = imageData.maskPad;
                imageData3.maskData = imageData.maskData;
                imageData3.alpha = imageData.alpha;
                imageData3.alphaData = imageData.alphaData;
                imageData = imageData3;
            }
        } else if (bl) {
            int n7 = 32;
            PaletteData paletteData = new PaletteData(65280, 0xFF0000, -16777216);
            n3 = 1;
            RGB[] rGBArray = imageData.palette.getRGBs();
            n = rGBArray.length;
            byte[] byArray = new byte[n];
            object2 = new byte[n];
            byte[] byArray2 = new byte[n];
            for (int i = 0; i < rGBArray.length; ++i) {
                RGB rGB = rGBArray[i];
                if (rGB == null) continue;
                byArray[i] = (byte)rGB.red;
                object2[i] = (byte)rGB.green;
                byArray2[i] = (byte)rGB.blue;
            }
            object = new ImageData(imageData.width, imageData.height, 32, paletteData);
            ImageData.blit(1, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, byArray, (byte[])object2, byArray2, 255, null, 0, 0, 0, ((ImageData)object).data, ((ImageData)object).depth, ((ImageData)object).bytesPerLine, 1, 0, 0, ((ImageData)object).width, ((ImageData)object).height, paletteData.redMask, paletteData.greenMask, paletteData.blueMask, false, false);
            if (imageData.transparentPixel != -1) {
                ((ImageData)object).transparentPixel = paletteData.getPixel(imageData.palette.getRGB(imageData.transparentPixel));
            }
            ((ImageData)object).maskPad = imageData.maskPad;
            ((ImageData)object).maskData = imageData.maskData;
            ((ImageData)object).alpha = imageData.alpha;
            ((ImageData)object).alphaData = imageData.alphaData;
            imageData = object;
        }
        if (imageData.alpha != -1) {
            int n8 = imageData.alpha & 0xFF;
            byte[] byArray = imageData.data;
            for (n3 = 0; n3 < imageData.data.length; n3 += 4) {
                n2 = (byArray[n3] & 0xFF) * n8 + 128;
                n2 = n2 + (n2 >> 8) >> 8;
                n = (byArray[n3 + 1] & 0xFF) * n8 + 128;
                n = n + (n >> 8) >> 8;
                int n9 = (byArray[n3 + 2] & 0xFF) * n8 + 128;
                n9 = n9 + (n9 >> 8) >> 8;
                byArray[n3] = (byte)n9;
                byArray[n3 + 1] = (byte)n;
                byArray[n3 + 2] = (byte)n2;
                byArray[n3 + 3] = (byte)n8;
            }
        } else if (imageData.alphaData != null) {
            object3 = imageData.data;
            n4 = 0;
            for (n3 = 0; n3 < imageData.data.length; n3 += 4) {
                n2 = imageData.alphaData[n4] & 0xFF;
                n = (object3[n3] & 0xFF) * n2 + 128;
                n = n + (n >> 8) >> 8;
                int n10 = (object3[n3 + 1] & 0xFF) * n2 + 128;
                n10 = n10 + (n10 >> 8) >> 8;
                int n11 = (object3[n3 + 2] & 0xFF) * n2 + 128;
                n11 = n11 + (n11 >> 8) >> 8;
                object3[n3] = (byte)n;
                object3[n3 + 1] = (byte)n10;
                object3[n3 + 2] = (byte)n11;
                object3[n3 + 3] = (byte)n2;
                ++n4;
            }
        }
        RGB[] rGBArray = imageData.palette.getRGBs();
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = imageData.width;
        bITMAPINFOHEADER.biHeight = -imageData.height;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)imageData.depth;
        bITMAPINFOHEADER.biCompression = 0;
        bITMAPINFOHEADER.biClrUsed = rGBArray == null ? 0 : rGBArray.length;
        byte[] byArray = imageData.palette.isDirect ? new byte[BITMAPINFOHEADER.sizeof] : new byte[BITMAPINFOHEADER.sizeof + rGBArray.length * 4];
        OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        n2 = BITMAPINFOHEADER.sizeof;
        if (!imageData.palette.isDirect) {
            for (RGB rGB : rGBArray) {
                byArray[n2] = (byte)rGB.blue;
                byArray[n2 + 1] = (byte)rGB.green;
                byArray[n2 + 2] = (byte)rGB.red;
                byArray[n2 + 3] = 0;
                n2 += 4;
            }
        }
        if ((l2 = OS.CreateDIBSection(0L, byArray, 0, lArray = new long[]{0L}, 0L, 0)) == 0L) {
            SWT.error(2);
        }
        byte[] byArray3 = imageData.data;
        if (imageData.scanlinePad != 4 && imageData.bytesPerLine % 4 != 0) {
            byArray3 = ImageData.convertPad(byArray3, imageData.width, imageData.height, imageData.depth, imageData.scanlinePad, 4);
        }
        OS.MoveMemory(lArray[0], byArray3, byArray3.length);
        object = null;
        if (imageData.getTransparencyType() == 2) {
            long l3 = device.internal_new_GC(null);
            long l4 = OS.CreateCompatibleDC(l3);
            OS.SelectObject(l4, l2);
            long l5 = OS.CreateCompatibleBitmap(l3, imageData.width, imageData.height);
            if (l5 == 0L) {
                SWT.error(2);
            }
            long l6 = OS.CreateCompatibleDC(l3);
            OS.SelectObject(l6, l5);
            OS.BitBlt(l6, 0, 0, imageData.width, imageData.height, l4, 0, 0, 0xCC0020);
            device.internal_dispose_GC(l3, null);
            byte[] byArray4 = ImageData.convertPad(imageData.maskData, imageData.width, imageData.height, 1, imageData.maskPad, 2);
            long l7 = OS.CreateBitmap(imageData.width, imageData.height, 1, 1, byArray4);
            if (l7 == 0L) {
                SWT.error(2);
            }
            OS.SelectObject(l4, l7);
            OS.PatBlt(l4, 0, 0, imageData.width, imageData.height, 0x550009);
            OS.DeleteDC(l4);
            OS.DeleteDC(l6);
            OS.DeleteObject(l2);
            if (image == null) {
                object = new long[]{l5, l7};
            } else {
                ICONINFO iCONINFO = new ICONINFO();
                iCONINFO.fIcon = true;
                iCONINFO.hbmColor = l5;
                iCONINFO.hbmMask = l7;
                long l8 = OS.CreateIconIndirect(iCONINFO);
                if (l8 == 0L) {
                    SWT.error(2);
                }
                OS.DeleteObject(l5);
                OS.DeleteObject(l7);
                image.handle = l8;
                image.type = 1;
            }
        } else if (image == null) {
            object = new long[]{l2};
        } else {
            image.handle = l2;
            image.type = 0;
            image.transparentPixel = imageData.transparentPixel;
        }
        return object;
    }

    static long[] init(Device device, Image image, ImageData imageData, ImageData imageData2) {
        int n;
        int n2;
        RGB[] rGBArray;
        Object object;
        ImageData imageData3;
        if (imageData.palette.isDirect) {
            imageData3 = new ImageData(imageData.width, imageData.height, imageData.depth, imageData.palette);
        } else {
            RGB[] rGBArray2;
            object = new RGB(0, 0, 0);
            rGBArray = imageData.getRGBs();
            if (imageData.transparentPixel != -1) {
                rGBArray2 = new RGB[rGBArray.length];
                System.arraycopy(rGBArray, 0, rGBArray2, 0, rGBArray.length);
                if (imageData.transparentPixel >= rGBArray2.length) {
                    rGBArray = new RGB[imageData.transparentPixel + 1];
                    System.arraycopy(rGBArray2, 0, rGBArray, 0, rGBArray2.length);
                    for (n2 = rGBArray2.length; n2 <= imageData.transparentPixel; ++n2) {
                        rGBArray[n2] = new RGB(0, 0, 0);
                    }
                } else {
                    rGBArray2[imageData.transparentPixel] = object;
                    rGBArray = rGBArray2;
                }
                n = imageData.transparentPixel;
                imageData3 = new ImageData(imageData.width, imageData.height, imageData.depth, new PaletteData(rGBArray));
            } else {
                for (n = 0; n < rGBArray.length && !rGBArray[n].equals(object); ++n) {
                }
                if (n == rGBArray.length) {
                    if (1 << imageData.depth > rGBArray.length) {
                        rGBArray2 = new RGB[rGBArray.length + 1];
                        System.arraycopy(rGBArray, 0, rGBArray2, 0, rGBArray.length);
                        rGBArray2[rGBArray.length] = object;
                        rGBArray = rGBArray2;
                    } else {
                        n = -1;
                    }
                }
                imageData3 = new ImageData(imageData.width, imageData.height, imageData.depth, new PaletteData(rGBArray));
            }
        }
        if (n == -1) {
            System.arraycopy(imageData.data, 0, imageData3.data, 0, imageData3.data.length);
        } else {
            object = new int[imageData3.width];
            rGBArray = (RGB[])new int[imageData2.width];
            for (int i = 0; i < imageData3.height; ++i) {
                imageData.getPixels(0, i, imageData3.width, (int[])object, 0);
                imageData2.getPixels(0, i, imageData2.width, (int[])rGBArray, 0);
                for (n2 = 0; n2 < ((Object)object).length; ++n2) {
                    if (rGBArray[n2] != false) continue;
                    object[n2] = n;
                }
                imageData3.setPixels(0, i, imageData.width, (int[])object, 0);
            }
        }
        imageData3.maskPad = imageData2.scanlinePad;
        imageData3.maskData = imageData2.data;
        return Image.init(device, image, imageData3);
    }

    void init(ImageData imageData) {
        if (imageData == null) {
            SWT.error(4);
        }
        Image.init(this.device, this, imageData);
    }

    @Override
    public long internal_new_GC(GCData gCData) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.type != 0 || this.memGC != null) {
            SWT.error(5);
        }
        long l2 = this.device.internal_new_GC(null);
        long l3 = OS.CreateCompatibleDC(l2);
        this.device.internal_dispose_GC(l2, null);
        if (l3 == 0L) {
            SWT.error(2);
        }
        if (gCData != null) {
            int n = 0x6000000;
            if ((gCData.style & 0x6000000) != 0) {
                gCData.layout = (gCData.style & 0x4000000) != 0 ? 1 : 0;
            } else {
                gCData.style |= 0x2000000;
            }
            gCData.device = this.device;
            gCData.image = this;
            gCData.font = this.device.systemFont;
        }
        return l3;
    }

    @Override
    public void internal_dispose_GC(long l2, GCData gCData) {
        OS.DeleteDC(l2);
    }

    public void setBackground(Color color) {
        if (this == false) {
            SWT.error(44);
        }
        if (color == null) {
            SWT.error(4);
        }
        if (color.isDisposed()) {
            SWT.error(5);
        }
        if (this.transparentPixel == -1) {
            return;
        }
        this.transparentColor = -1;
        long l2 = this.device.internal_new_GC(null);
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(this.handle, BITMAP.sizeof, bITMAP);
        long l3 = OS.CreateCompatibleDC(l2);
        OS.SelectObject(l3, this.handle);
        int n = 1 << bITMAP.bmBitsPixel;
        byte[] byArray = new byte[n * 4];
        int n2 = OS.GetDIBColorTable(l3, 0, n, byArray);
        int n3 = this.transparentPixel * 4;
        byArray[n3] = (byte)color.getBlue();
        byArray[n3 + 1] = (byte)color.getGreen();
        byArray[n3 + 2] = (byte)color.getRed();
        OS.SetDIBColorTable(l3, 0, n2, byArray);
        OS.DeleteDC(l3);
        this.device.internal_dispose_GC(l2, null);
    }

    public String toString() {
        if (this == false) {
            return "Image {*DISPOSED*}";
        }
        return "Image {" + this.handle;
    }

    public static Image win32_new(Device device, int n, long l2) {
        Image image = new Image(device);
        image.type = n;
        image.handle = l2;
        return image;
    }
}

