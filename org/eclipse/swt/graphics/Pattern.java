/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.win32.OS;

public class Pattern
extends Resource {
    public long handle;

    public Pattern(Device device, Image image) {
        super(device);
        if (image == null) {
            SWT.error(4);
        }
        if (image.isDisposed()) {
            SWT.error(5);
        }
        this.device.checkGDIP();
        long[] lArray = image.createGdipImage();
        long l2 = lArray[0];
        int n = Gdip.Image_GetWidth(l2);
        int n2 = Gdip.Image_GetHeight(l2);
        this.handle = Gdip.TextureBrush_new(l2, 0, 0.0f, 0.0f, n, n2);
        Gdip.Bitmap_delete(l2);
        if (lArray[1] != 0L) {
            long l3 = OS.GetProcessHeap();
            OS.HeapFree(l3, 0, lArray[1]);
        }
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    public Pattern(Device device, float f, float f2, float f3, float f4, Color color, Color color2) {
        this(device, f, f2, f3, f4, color, 255, color2, 255);
    }

    public Pattern(Device device, float f, float f2, float f3, float f4, Color color, int n, Color color2, int n2) {
        super(device);
        f = DPIUtil.autoScaleUp(f);
        f2 = DPIUtil.autoScaleUp(f2);
        f3 = DPIUtil.autoScaleUp(f3);
        f4 = DPIUtil.autoScaleUp(f4);
        if (color == null) {
            SWT.error(4);
        }
        if (color.isDisposed()) {
            SWT.error(5);
        }
        if (color2 == null) {
            SWT.error(4);
        }
        if (color2.isDisposed()) {
            SWT.error(5);
        }
        this.device.checkGDIP();
        int n3 = color.handle;
        int n4 = (n & 0xFF) << 24 | n3 >> 16 & 0xFF | n3 & 0xFF00 | (n3 & 0xFF) << 16;
        if (f == f3 && f2 == f4) {
            this.handle = Gdip.SolidBrush_new(n4);
            if (this.handle == 0L) {
                SWT.error(2);
            }
        } else {
            int n5 = color2.handle;
            int n6 = (n2 & 0xFF) << 24 | n5 >> 16 & 0xFF | n5 & 0xFF00 | (n5 & 0xFF) << 16;
            PointF pointF = new PointF();
            pointF.X = f;
            pointF.Y = f2;
            PointF pointF2 = new PointF();
            pointF2.X = f3;
            pointF2.Y = f4;
            this.handle = Gdip.LinearGradientBrush_new(pointF, pointF2, n4, n6);
            if (this.handle == 0L) {
                SWT.error(2);
            }
            if (n != 255 || n2 != 255) {
                int n7 = (int)((float)(n & 0xFF) * 0.5f + (float)(n2 & 0xFF) * 0.5f);
                int n8 = (int)((float)((n3 & 0xFF) >> 0) * 0.5f + (float)((n5 & 0xFF) >> 0) * 0.5f);
                int n9 = (int)((float)((n3 & 0xFF00) >> 8) * 0.5f + (float)((n5 & 0xFF00) >> 8) * 0.5f);
                int n10 = (int)((float)((n3 & 0xFF0000) >> 16) * 0.5f + (float)((n5 & 0xFF0000) >> 16) * 0.5f);
                int n11 = n7 << 24 | n8 << 16 | n9 << 8 | n10;
                Gdip.LinearGradientBrush_SetInterpolationColors(this.handle, new int[]{n4, n11, n6}, new float[]{0.0f, 0.5f, 1.0f}, 3);
            }
        }
        this.init();
    }

    @Override
    void destroy() {
        int n = Gdip.Brush_GetType(this.handle);
        switch (n) {
            case 0: {
                Gdip.SolidBrush_delete(this.handle);
                break;
            }
            case 1: {
                Gdip.HatchBrush_delete(this.handle);
                break;
            }
            case 4: {
                Gdip.LinearGradientBrush_delete(this.handle);
                break;
            }
            case 2: {
                Gdip.TextureBrush_delete(this.handle);
            }
        }
        this.handle = 0L;
    }

    public String toString() {
        if (this == false) {
            return "Pattern {*DISPOSED*}";
        }
        return "Pattern {" + this.handle;
    }
}

