/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.OS;

public class ImageList {
    long handle;
    int style;
    int refCount;
    Image[] images;

    public ImageList(int n) {
        this(n, 32, 32);
    }

    public ImageList(int n, int n2, int n3) {
        this.style = n;
        int n4 = 33;
        if ((n & 0x4000000) != 0) {
            n4 |= 0x2000;
        }
        this.handle = OS.ImageList_Create(n2, n3, n4, 16, 16);
        this.images = new Image[4];
    }

    public int add(Image image) {
        Image[] imageArray;
        int n;
        int n2 = OS.ImageList_GetImageCount(this.handle);
        for (n = 0; n < n2; ++n) {
            if (this.images[n] != null && this.images[n].isDisposed()) {
                this.images[n] = null;
            }
            if (this.images[n] == null) break;
        }
        if (n2 == 0) {
            imageArray = image.getBoundsInPixels();
            OS.ImageList_SetIconSize(this.handle, imageArray.width, imageArray.height);
        }
        this.set(n, image, n2);
        if (n == this.images.length) {
            imageArray = new Image[this.images.length + 4];
            System.arraycopy(this.images, 0, imageArray, 0, this.images.length);
            this.images = imageArray;
        }
        this.images[n] = image;
        return n;
    }

    public int addRef() {
        return ++this.refCount;
    }

    long copyBitmap(long l2, int n, int n2) {
        long l3;
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l2, BITMAP.sizeof, bITMAP);
        long l4 = OS.GetDC(0L);
        long l5 = OS.CreateCompatibleDC(l4);
        OS.SelectObject(l5, l2);
        long l6 = OS.CreateCompatibleDC(l4);
        if (bITMAP.bmBitsPixel == 32) {
            BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
            bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
            bITMAPINFOHEADER.biWidth = n;
            bITMAPINFOHEADER.biHeight = -n2;
            bITMAPINFOHEADER.biPlanes = 1;
            bITMAPINFOHEADER.biBitCount = (short)24;
            bITMAPINFOHEADER.biCompression = 0;
            byte[] byArray = new byte[BITMAPINFOHEADER.sizeof];
            OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
            long[] lArray = new long[]{0L};
            l3 = OS.CreateDIBSection(0L, byArray, 0, lArray, 0L, 0);
        } else {
            l3 = OS.CreateCompatibleBitmap(l4, n, n2);
        }
        OS.SelectObject(l6, l3);
        if (n != bITMAP.bmWidth || n2 != bITMAP.bmHeight) {
            OS.SetStretchBltMode(l6, 3);
            OS.StretchBlt(l6, 0, 0, n, n2, l5, 0, 0, bITMAP.bmWidth, bITMAP.bmHeight, 0xCC0020);
        } else {
            OS.BitBlt(l6, 0, 0, n, n2, l5, 0, 0, 0xCC0020);
        }
        OS.DeleteDC(l5);
        OS.DeleteDC(l6);
        OS.ReleaseDC(0L, l4);
        return l3;
    }

    long copyIcon(long l2, int n, int n2) {
        long l3 = OS.CopyImage(l2, 1, n, n2, 0);
        return l3 != 0L ? l3 : l2;
    }

    long copyWithAlpha(long l2, int n, byte[] byArray, int n2, int n3) {
        int n4;
        int n5;
        byte by;
        byte by2;
        int n6;
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l2, BITMAP.sizeof, bITMAP);
        int n7 = bITMAP.bmWidth;
        int n8 = bITMAP.bmHeight;
        long l3 = OS.GetDC(0L);
        long l4 = OS.CreateCompatibleDC(l3);
        long l5 = OS.SelectObject(l4, l2);
        long l6 = OS.CreateCompatibleDC(l3);
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biWidth = n7;
        bITMAPINFOHEADER.biHeight = -n8;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)32;
        bITMAPINFOHEADER.biCompression = 0;
        byte[] byArray2 = new byte[BITMAPINFOHEADER.sizeof];
        OS.MoveMemory(byArray2, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        long l7 = OS.CreateDIBSection(0L, byArray2, 0, lArray, 0L, 0);
        if (l7 == 0L) {
            SWT.error(2);
        }
        long l8 = OS.SelectObject(l6, l7);
        BITMAP bITMAP2 = new BITMAP();
        OS.GetObject(l7, BITMAP.sizeof, bITMAP2);
        int n9 = bITMAP2.bmWidthBytes * bITMAP2.bmHeight;
        OS.BitBlt(l6, 0, 0, n7, n8, l4, 0, 0, 0xCC0020);
        byte[] byArray3 = new byte[n9];
        OS.MoveMemory(byArray3, bITMAP2.bmBits, n9);
        if (byArray != null) {
            n6 = bITMAP2.bmWidthBytes - n7 * 4;
            by2 = 0;
            by = 0;
            for (n5 = 0; n5 < n8; ++n5) {
                for (int i = 0; i < n7; ++i) {
                    byte by3 = by2;
                    by2 = (byte)(by2 + 1);
                    n4 = byArray[by3] & 0xFF;
                    if (n4 != 0) {
                        byArray3[by] = (byte)(((byArray3[by] & 0xFF) * 255 + n4 / 2) / n4);
                        byArray3[by + 1] = (byte)(((byArray3[by + 1] & 0xFF) * 255 + n4 / 2) / n4);
                        byArray3[by + 2] = (byte)(((byArray3[by + 2] & 0xFF) * 255 + n4 / 2) / n4);
                    }
                    byArray3[by + 3] = (byte)n4;
                    by = (byte)(by + 4);
                }
                by = (byte)(by + n6);
            }
        } else {
            n6 = n & 0xFF;
            by2 = (byte)(n >> 8 & 0xFF);
            by = (byte)(n >> 16 & 0xFF);
            n5 = bITMAP2.bmWidthBytes - n7 * 4;
            int n10 = 3;
            for (n4 = 0; n4 < n8; ++n4) {
                for (int i = 0; i < n7; ++i) {
                    byArray3[n10] = (byte)(byArray3[n10 - 1] == n6 && byArray3[n10 - 2] == by2 && byArray3[n10 - 3] == by ? 0 : -1);
                    n10 += 4;
                }
                n10 += n5;
            }
        }
        OS.MoveMemory(bITMAP2.bmBits, byArray3, n9);
        if (n7 != n2 || n8 != n3) {
            BITMAPINFOHEADER bITMAPINFOHEADER2 = new BITMAPINFOHEADER();
            bITMAPINFOHEADER2.biSize = BITMAPINFOHEADER.sizeof;
            bITMAPINFOHEADER2.biWidth = n2;
            bITMAPINFOHEADER2.biHeight = -n3;
            bITMAPINFOHEADER2.biPlanes = 1;
            bITMAPINFOHEADER2.biBitCount = (short)32;
            bITMAPINFOHEADER2.biCompression = 0;
            byte[] byArray4 = new byte[BITMAPINFOHEADER.sizeof];
            OS.MoveMemory(byArray4, bITMAPINFOHEADER2, BITMAPINFOHEADER.sizeof);
            long[] lArray2 = new long[]{0L};
            long l9 = OS.CreateDIBSection(0L, byArray4, 0, lArray2, 0L, 0);
            long l10 = OS.CreateCompatibleDC(l3);
            long l11 = OS.SelectObject(l10, l9);
            OS.SetStretchBltMode(l10, 3);
            OS.StretchBlt(l10, 0, 0, n2, n3, l6, 0, 0, n7, n8, 0xCC0020);
            OS.SelectObject(l10, l11);
            OS.DeleteDC(l10);
            OS.SelectObject(l6, l8);
            OS.DeleteDC(l6);
            OS.DeleteObject(l7);
            l7 = l9;
        } else {
            OS.SelectObject(l6, l8);
            OS.DeleteDC(l6);
        }
        OS.SelectObject(l4, l5);
        OS.DeleteDC(l4);
        OS.ReleaseDC(0L, l3);
        return l7;
    }

    long createMaskFromAlpha(ImageData imageData, int n, int n2) {
        int n3 = imageData.width;
        int n4 = imageData.height;
        ImageData imageData2 = ImageData.internal_new(n3, n4, 1, new PaletteData(new RGB(0, 0, 0), new RGB(255, 255, 255)), 2, null, 1, null, null, -1, -1, -1, 0, 0, 0, 0);
        int n5 = 0;
        for (int i = 0; i < imageData2.height; ++i) {
            for (int j = 0; j < imageData2.width; ++j) {
                imageData2.setPixel(j, i, (imageData.alphaData[n5++] & 0xFF) <= 127 ? 1 : 0);
            }
        }
        long l2 = OS.CreateBitmap(n3, n4, 1, 1, imageData2.data);
        if (n3 != n || n4 != n2) {
            long l3 = OS.GetDC(0L);
            long l4 = OS.CreateCompatibleDC(l3);
            OS.SelectObject(l4, l2);
            long l5 = OS.CreateCompatibleDC(l3);
            long l6 = OS.CreateBitmap(n, n2, 1, 1, null);
            OS.SelectObject(l5, l6);
            OS.SetStretchBltMode(l5, 3);
            OS.StretchBlt(l5, 0, 0, n, n2, l4, 0, 0, n3, n4, 0xCC0020);
            OS.DeleteDC(l4);
            OS.DeleteDC(l5);
            OS.ReleaseDC(0L, l3);
            OS.DeleteObject(l2);
            l2 = l6;
        }
        return l2;
    }

    long createMask(long l2, int n, int n2, int n3, int n4) {
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l2, BITMAP.sizeof, bITMAP);
        int n5 = bITMAP.bmWidth;
        int n6 = bITMAP.bmHeight;
        long l3 = OS.CreateBitmap(n, n2, 1, 1, null);
        long l4 = OS.GetDC(0L);
        long l5 = OS.CreateCompatibleDC(l4);
        if (n3 != -1) {
            OS.SelectObject(l5, l2);
            boolean bl = bITMAP.bmBits != 0L;
            byte[] byArray = null;
            if (n4 != -1 && bl && bITMAP.bmBitsPixel <= 8) {
                int n7 = 1 << bITMAP.bmBitsPixel;
                byte[] byArray2 = new byte[n7 * 4];
                OS.GetDIBColorTable(l5, 0, n7, byArray2);
                int n8 = n4 * 4;
                byte[] byArray3 = new byte[byArray2.length];
                byArray3[n8] = -1;
                byArray3[n8 + 1] = -1;
                byArray3[n8 + 2] = -1;
                OS.SetDIBColorTable(l5, 0, n7, byArray3);
                byArray = byArray2;
                OS.SetBkColor(l5, 0xFFFFFF);
            } else {
                OS.SetBkColor(l5, n3);
            }
            long l6 = OS.CreateCompatibleDC(l4);
            OS.SelectObject(l6, l3);
            if (n != n5 || n2 != n6) {
                OS.SetStretchBltMode(l6, 3);
                OS.StretchBlt(l6, 0, 0, n, n2, l5, 0, 0, n5, n6, 0xCC0020);
            } else {
                OS.BitBlt(l6, 0, 0, n, n2, l5, 0, 0, 0xCC0020);
            }
            OS.DeleteDC(l6);
            if (byArray != null) {
                OS.SetDIBColorTable(l5, 0, 1 << bITMAP.bmBitsPixel, byArray);
            }
        } else {
            long l7 = OS.SelectObject(l5, l3);
            OS.PatBlt(l5, 0, 0, n, n2, 66);
            OS.SelectObject(l5, l7);
        }
        OS.ReleaseDC(0L, l4);
        OS.DeleteDC(l5);
        return l3;
    }

    public void dispose() {
        if (this.handle != 0L) {
            OS.ImageList_Destroy(this.handle);
        }
        this.handle = 0L;
        this.images = null;
    }

    public Image get(int n) {
        return this.images[n];
    }

    public int getStyle() {
        return this.style;
    }

    public long getHandle() {
        return this.handle;
    }

    public Point getImageSize() {
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.ImageList_GetIconSize(this.handle, nArray, nArray2);
        return new Point(nArray[0], nArray2[0]);
    }

    public int indexOf(Image image) {
        int n = OS.ImageList_GetImageCount(this.handle);
        for (int i = 0; i < n; ++i) {
            if (this.images[i] == null) continue;
            if (this.images[i].isDisposed()) {
                this.images[i] = null;
            }
            if (this.images[i] == null || !this.images[i].equals(image)) continue;
            return i;
        }
        return -1;
    }

    public void put(int n, Image image) {
        if (0 <= n && n < this.images.length && this.images[n] == image) {
            return;
        }
        int n2 = OS.ImageList_GetImageCount(this.handle);
        if (0 > n || n >= n2) {
            return;
        }
        if (image != null) {
            this.set(n, image, n2);
        }
        this.images[n] = image;
    }

    public void remove(int n) {
        int n2 = OS.ImageList_GetImageCount(this.handle);
        if (0 > n || n >= n2) {
            return;
        }
        OS.ImageList_Remove(this.handle, n);
        System.arraycopy(this.images, n + 1, this.images, n, --n2 - n);
        this.images[n] = null;
    }

    public int removeRef() {
        return --this.refCount;
    }

    void set(int n, Image image, int n2) {
        long l2 = image.handle;
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.ImageList_GetIconSize(this.handle, nArray, nArray2);
        switch (image.type) {
            case 0: {
                long l3 = 0L;
                long l4 = 0L;
                ImageData imageData = image.getImageData(DPIUtil.getDeviceZoom());
                switch (imageData.getTransparencyType()) {
                    case 1: {
                        boolean bl = true;
                        if (imageData.alphaData == null) {
                            bl = false;
                        } else {
                            for (byte by : imageData.alphaData) {
                                if (by == 0) continue;
                                bl = false;
                                break;
                            }
                        }
                        if (!bl) {
                            l3 = this.copyWithAlpha(l2, -1, imageData.alphaData, nArray[0], nArray2[0]);
                            break;
                        }
                        l3 = this.copyBitmap(l2, nArray[0], nArray2[0]);
                        l4 = this.createMaskFromAlpha(imageData, nArray[0], nArray2[0]);
                        break;
                    }
                    case 4: {
                        int n3 = -1;
                        Color color = image.getBackground();
                        if (color != null) {
                            n3 = color.handle;
                        }
                        l3 = this.copyBitmap(l2, nArray[0], nArray2[0]);
                        l4 = this.createMask(l2, nArray[0], nArray2[0], n3, imageData.transparentPixel);
                        break;
                    }
                    default: {
                        l3 = this.copyBitmap(l2, nArray[0], nArray2[0]);
                        if (n == n2) break;
                        l4 = this.createMask(l2, nArray[0], nArray2[0], -1, -1);
                    }
                }
                if (n == n2) {
                    OS.ImageList_Add(this.handle, l3, l4);
                } else {
                    OS.ImageList_Replace(this.handle, n, l3, l4);
                }
                if (l4 != 0L) {
                    OS.DeleteObject(l4);
                }
                if (l3 == l2) break;
                OS.DeleteObject(l3);
                break;
            }
            case 1: {
                long l5 = this.copyIcon(l2, nArray[0], nArray2[0]);
                OS.ImageList_ReplaceIcon(this.handle, n == n2 ? -1 : n, l5);
                OS.DestroyIcon(l5);
                break;
            }
        }
    }

    public int size() {
        int n = 0;
        int n2 = OS.ImageList_GetImageCount(this.handle);
        for (int i = 0; i < n2; ++i) {
            if (this.images[i] == null) continue;
            if (this.images[i].isDisposed()) {
                this.images[i] = null;
            }
            if (this.images[i] == null) continue;
            ++n;
        }
        return n;
    }
}

