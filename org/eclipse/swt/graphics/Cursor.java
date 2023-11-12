/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.OS;

public final class Cursor
extends Resource {
    public long handle;
    boolean isIcon;

    Cursor(Device device) {
        super(device);
    }

    public Cursor(Device device, int n) {
        super(device);
        long l2 = 0L;
        switch (n) {
            case 21: {
                l2 = 32649L;
                break;
            }
            case 0: {
                l2 = 32512L;
                break;
            }
            case 1: {
                l2 = 32514L;
                break;
            }
            case 2: {
                l2 = 32515L;
                break;
            }
            case 3: {
                l2 = 32650L;
                break;
            }
            case 4: {
                l2 = 32651L;
                break;
            }
            case 5: {
                l2 = 32646L;
                break;
            }
            case 6: {
                l2 = 32643L;
                break;
            }
            case 7: {
                l2 = 32645L;
                break;
            }
            case 8: {
                l2 = 32642L;
                break;
            }
            case 9: {
                l2 = 32644L;
                break;
            }
            case 10: {
                l2 = 32645L;
                break;
            }
            case 11: {
                l2 = 32645L;
                break;
            }
            case 12: {
                l2 = 32644L;
                break;
            }
            case 13: {
                l2 = 32644L;
                break;
            }
            case 14: {
                l2 = 32643L;
                break;
            }
            case 15: {
                l2 = 32642L;
                break;
            }
            case 16: {
                l2 = 32643L;
                break;
            }
            case 17: {
                l2 = 32642L;
                break;
            }
            case 18: {
                l2 = 32516L;
                break;
            }
            case 19: {
                l2 = 32513L;
                break;
            }
            case 20: {
                l2 = 32648L;
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.handle = OS.LoadCursor(0L, l2);
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    public Cursor(Device device, ImageData imageData, ImageData imageData2, int n, int n2) {
        super(device);
        if (imageData == null) {
            SWT.error(4);
        }
        if (imageData2 == null) {
            if (imageData.getTransparencyType() != 2) {
                SWT.error(4);
            }
            imageData2 = imageData.getTransparencyMask();
        }
        if (imageData2.width != imageData.width || imageData2.height != imageData.height) {
            SWT.error(5);
        }
        if (n >= imageData.width || n < 0 || n2 >= imageData.height || n2 < 0) {
            SWT.error(5);
        }
        imageData2 = ImageData.convertMask(imageData2);
        imageData = ImageData.convertMask(imageData);
        byte[] byArray = ImageData.convertPad(imageData.data, imageData.width, imageData.height, imageData.depth, imageData.scanlinePad, 2);
        byte[] byArray2 = ImageData.convertPad(imageData2.data, imageData2.width, imageData2.height, imageData2.depth, imageData2.scanlinePad, 2);
        long l2 = OS.GetModuleHandle(null);
        this.handle = OS.CreateCursor(l2, n, n2, imageData.width, imageData.height, byArray, byArray2);
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    public Cursor(Device device, ImageData imageData, int n, int n2) {
        super(device);
        Object object;
        if (imageData == null) {
            SWT.error(4);
        }
        if (n >= imageData.width || n < 0 || n2 >= imageData.height || n2 < 0) {
            SWT.error(5);
        }
        long l2 = 0L;
        long l3 = 0L;
        if (imageData.maskData == null && imageData.transparentPixel == -1 && (imageData.alpha != -1 || imageData.alphaData != null)) {
            Object object2;
            object = imageData.palette;
            PaletteData paletteData = new PaletteData(65280, 0xFF0000, -16777216);
            ImageData imageData2 = new ImageData(imageData.width, imageData.height, 32, paletteData);
            if (((PaletteData)object).isDirect) {
                ImageData.blit(1, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, ((PaletteData)object).redMask, ((PaletteData)object).greenMask, ((PaletteData)object).blueMask, 255, null, 0, 0, 0, imageData2.data, imageData2.depth, imageData2.bytesPerLine, imageData2.getByteOrder(), 0, 0, imageData2.width, imageData2.height, paletteData.redMask, paletteData.greenMask, paletteData.blueMask, false, false);
            } else {
                object2 = ((PaletteData)object).getRGBs();
                int n3 = ((RGB[])object2).length;
                byte[] byArray = new byte[n3];
                byte[] byArray2 = new byte[n3];
                byte[] byArray3 = new byte[n3];
                for (int i = 0; i < ((RGB[])object2).length; ++i) {
                    RGB rGB = object2[i];
                    if (rGB == null) continue;
                    byArray[i] = (byte)rGB.red;
                    byArray2[i] = (byte)rGB.green;
                    byArray3[i] = (byte)rGB.blue;
                }
                ImageData.blit(1, imageData.data, imageData.depth, imageData.bytesPerLine, imageData.getByteOrder(), 0, 0, imageData.width, imageData.height, byArray, byArray2, byArray3, 255, null, 0, 0, 0, imageData2.data, imageData2.depth, imageData2.bytesPerLine, imageData2.getByteOrder(), 0, 0, imageData2.width, imageData2.height, paletteData.redMask, paletteData.greenMask, paletteData.blueMask, false, false);
            }
            l2 = Image.createDIB(imageData.width, imageData.height, 32);
            if (l2 == 0L) {
                SWT.error(2);
            }
            object2 = new BITMAP();
            OS.GetObject(l2, BITMAP.sizeof, (BITMAP)object2);
            byte[] byArray = imageData2.data;
            if (imageData.alpha != -1) {
                for (int i = 3; i < byArray.length; i += 4) {
                    byArray[i] = (byte)imageData.alpha;
                }
            } else if (imageData.alphaData != null) {
                int n4 = 3;
                int n5 = 0;
                while (n4 < byArray.length) {
                    byArray[n4] = imageData.alphaData[n5];
                    n4 += 4;
                    ++n5;
                }
            }
            OS.MoveMemory(object2.bmBits, byArray, byArray.length);
            l3 = OS.CreateBitmap(imageData.width, imageData.height, 1, 1, new byte[((imageData.width + 7) / 8 + 3) / 4 * 4 * imageData.height]);
            if (l3 == 0L) {
                SWT.error(2);
            }
        } else {
            object = imageData.getTransparencyMask();
            long[] lArray = Image.init(this.device, null, imageData, (ImageData)object);
            l2 = lArray[0];
            l3 = lArray[1];
        }
        object = new ICONINFO();
        ((ICONINFO)object).fIcon = false;
        ((ICONINFO)object).hbmColor = l2;
        ((ICONINFO)object).hbmMask = l3;
        ((ICONINFO)object).xHotspot = n;
        ((ICONINFO)object).yHotspot = n2;
        this.handle = OS.CreateIconIndirect((ICONINFO)object);
        OS.DeleteObject(l2);
        OS.DeleteObject(l3);
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.isIcon = true;
        this.init();
    }

    @Override
    void destroy() {
        if (this.isIcon) {
            OS.DestroyIcon(this.handle);
        } else {
            OS.DestroyCursor(this.handle);
        }
        this.handle = 0L;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Cursor)) {
            return false;
        }
        Cursor cursor = (Cursor)object;
        return this.device == cursor.device && this.handle == cursor.handle;
    }

    public int hashCode() {
        return (int)this.handle;
    }

    public String toString() {
        if (this == false) {
            return "Cursor {*DISPOSED*}";
        }
        return "Cursor {" + this.handle;
    }

    public static Cursor win32_new(Device device, int n) {
        Cursor cursor = new Cursor(device);
        cursor.handle = n;
        return cursor;
    }
}

