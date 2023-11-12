/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.PathData;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.gdip.RectF;
import org.eclipse.swt.internal.win32.OS;

public class Path
extends Resource {
    public long handle;
    PointF currentPoint = new PointF();
    PointF startPoint = new PointF();

    public Path(Device device) {
        super(device);
        this.device.checkGDIP();
        this.handle = Gdip.GraphicsPath_new(0);
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    public Path(Device device, Path path, float f) {
        super(device);
        if (path == null) {
            SWT.error(4);
        }
        if (path == false) {
            SWT.error(5);
        }
        f = Math.max(0.0f, f);
        this.handle = Gdip.GraphicsPath_Clone(path.handle);
        if (f != 0.0f) {
            Gdip.GraphicsPath_Flatten(this.handle, 0L, f);
        }
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    public Path(Device device, PathData pathData) {
        this(device);
        if (pathData == null) {
            SWT.error(4);
        }
        this.init(pathData);
    }

    public void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        if (f3 == 0.0f || f4 == 0.0f || f6 == 0.0f) {
            return;
        }
        Device device = this.getDevice();
        f = DPIUtil.autoScaleUp((Drawable)device, f);
        f2 = DPIUtil.autoScaleUp((Drawable)device, f2);
        f3 = DPIUtil.autoScaleUp((Drawable)device, f3);
        f4 = DPIUtil.autoScaleUp((Drawable)device, f4);
        this.addArcInPixels(f, f2, f3, f4, f5, f6);
    }

    void addArcInPixels(float f, float f2, float f3, float f4, float f5, float f6) {
        if (this == false) {
            SWT.error(44);
        }
        if (f3 < 0.0f) {
            f += f3;
            f3 = -f3;
        }
        if (f4 < 0.0f) {
            f2 += f4;
            f4 = -f4;
        }
        if (f3 == f4) {
            Gdip.GraphicsPath_AddArc(this.handle, f, f2, f3, f4, -f5, -f6);
        } else {
            long l2;
            long l3 = Gdip.GraphicsPath_new(0);
            if (l3 == 0L) {
                SWT.error(2);
            }
            if ((l2 = Gdip.Matrix_new(f3, 0.0f, 0.0f, f4, f, f2)) == 0L) {
                SWT.error(2);
            }
            Gdip.GraphicsPath_AddArc(l3, 0.0f, 0.0f, 1.0f, 1.0f, -f5, -f6);
            Gdip.GraphicsPath_Transform(l3, l2);
            Gdip.GraphicsPath_AddPath(this.handle, l3, true);
            Gdip.Matrix_delete(l2);
            Gdip.GraphicsPath_delete(l3);
        }
        Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
    }

    public void addPath(Path path) {
        if (this == false) {
            SWT.error(44);
        }
        if (path == null) {
            SWT.error(4);
        }
        if (path == false) {
            SWT.error(5);
        }
        Gdip.GraphicsPath_AddPath(this.handle, path.handle, false);
        this.currentPoint.X = path.currentPoint.X;
        this.currentPoint.Y = path.currentPoint.Y;
    }

    public void addRectangle(float f, float f2, float f3, float f4) {
        Device device = this.getDevice();
        f = DPIUtil.autoScaleUp((Drawable)device, f);
        f2 = DPIUtil.autoScaleUp((Drawable)device, f2);
        f3 = DPIUtil.autoScaleUp((Drawable)device, f3);
        f4 = DPIUtil.autoScaleUp((Drawable)device, f4);
        this.addRectangleInPixels(f, f2, f3, f4);
    }

    void addRectangleInPixels(float f, float f2, float f3, float f4) {
        if (this == false) {
            SWT.error(44);
        }
        RectF rectF = new RectF();
        rectF.X = f;
        rectF.Y = f2;
        rectF.Width = f3;
        rectF.Height = f4;
        Gdip.GraphicsPath_AddRectangle(this.handle, rectF);
        this.currentPoint.X = f;
        this.currentPoint.Y = f2;
    }

    public void addString(String string, float f, float f2, Font font) {
        Device device = this.getDevice();
        f = DPIUtil.autoScaleUp((Drawable)device, f);
        f2 = DPIUtil.autoScaleUp((Drawable)device, f2);
        this.addStringInPixels(string, f, f2, font);
    }

    void addStringInPixels(String string, float f, float f2, Font font) {
        if (this == false) {
            SWT.error(44);
        }
        if (font == null) {
            SWT.error(4);
        }
        if (font.isDisposed()) {
            SWT.error(5);
        }
        char[] cArray = string.toCharArray();
        long l2 = this.device.internal_new_GC(null);
        long[] lArray = new long[]{0L};
        long l3 = GC.createGdipFont(l2, font.handle, 0L, this.device.fontCollection, lArray, null);
        PointF pointF = new PointF();
        pointF.X = f - Gdip.Font_GetSize(l3) / 6.0f;
        pointF.Y = f2;
        int n = Gdip.Font_GetStyle(l3);
        float f3 = Gdip.Font_GetSize(l3);
        Gdip.GraphicsPath_AddString(this.handle, cArray, cArray.length, lArray[0], n, f3, pointF, 0L);
        Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
        Gdip.FontFamily_delete(lArray[0]);
        Gdip.Font_delete(l3);
        this.device.internal_dispose_GC(l2, null);
    }

    public void close() {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.GraphicsPath_CloseFigure(this.handle);
        this.currentPoint.X = this.startPoint.X;
        this.currentPoint.Y = this.startPoint.Y;
    }

    public boolean contains(float f, float f2, GC gC, boolean bl) {
        Device device = this.getDevice();
        f = DPIUtil.autoScaleUp((Drawable)device, f);
        f2 = DPIUtil.autoScaleUp((Drawable)device, f2);
        return this.containsInPixels(f, f2, gC, bl);
    }

    boolean containsInPixels(float f, float f2, GC gC, boolean bl) {
        if (this == false) {
            SWT.error(44);
        }
        if (gC == null) {
            SWT.error(4);
        }
        if (gC.isDisposed()) {
            SWT.error(5);
        }
        gC.initGdip();
        gC.checkGC(120);
        int n = OS.GetPolyFillMode(gC.handle) == 2 ? 1 : 0;
        Gdip.GraphicsPath_SetFillMode(this.handle, n);
        if (bl) {
            return Gdip.GraphicsPath_IsOutlineVisible(this.handle, f, f2, gC.data.gdipPen, gC.data.gdipGraphics);
        }
        return Gdip.GraphicsPath_IsVisible(this.handle, f, f2, gC.data.gdipGraphics);
    }

    public void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        Device device = this.getDevice();
        f = DPIUtil.autoScaleUp((Drawable)device, f);
        f2 = DPIUtil.autoScaleUp((Drawable)device, f2);
        f3 = DPIUtil.autoScaleUp((Drawable)device, f3);
        f4 = DPIUtil.autoScaleUp((Drawable)device, f4);
        f5 = DPIUtil.autoScaleUp((Drawable)device, f5);
        f6 = DPIUtil.autoScaleUp((Drawable)device, f6);
        this.cubicToInPixels(f, f2, f3, f4, f5, f6);
    }

    void cubicToInPixels(float f, float f2, float f3, float f4, float f5, float f6) {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.GraphicsPath_AddBezier(this.handle, this.currentPoint.X, this.currentPoint.Y, f, f2, f3, f4, f5, f6);
        Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
    }

    @Override
    void destroy() {
        Gdip.GraphicsPath_delete(this.handle);
        this.handle = 0L;
    }

    public void getBounds(float[] fArray) {
        if (fArray == null) {
            SWT.error(4);
        }
        this.getBoundsInPixels(fArray);
        float[] fArray2 = DPIUtil.autoScaleDown((Drawable)this.getDevice(), fArray);
        System.arraycopy(fArray2, 0, fArray, 0, 4);
    }

    void getBoundsInPixels(float[] fArray) {
        if (this == false) {
            SWT.error(44);
        }
        if (fArray.length < 4) {
            SWT.error(5);
        }
        RectF rectF = new RectF();
        Gdip.GraphicsPath_GetBounds(this.handle, rectF, 0L, 0L);
        fArray[0] = rectF.X;
        fArray[1] = rectF.Y;
        fArray[2] = rectF.Width;
        fArray[3] = rectF.Height;
    }

    public void getCurrentPoint(float[] fArray) {
        if (fArray == null) {
            SWT.error(4);
        }
        this.getCurrentPointInPixels(fArray);
        float[] fArray2 = DPIUtil.autoScaleDown((Drawable)this.getDevice(), fArray);
        System.arraycopy(fArray2, 0, fArray, 0, 2);
    }

    void getCurrentPointInPixels(float[] fArray) {
        if (this == false) {
            SWT.error(44);
        }
        if (fArray.length < 2) {
            SWT.error(5);
        }
        fArray[0] = this.currentPoint.X;
        fArray[1] = this.currentPoint.Y;
    }

    public PathData getPathData() {
        if (this == false) {
            SWT.error(44);
        }
        PathData pathData = this.getPathDataInPixels();
        pathData.points = DPIUtil.autoScaleDown((Drawable)this.getDevice(), pathData.points);
        return pathData;
    }

    PathData getPathDataInPixels() {
        int n = Gdip.GraphicsPath_GetPointCount(this.handle);
        byte[] byArray = new byte[n];
        float[] fArray = new float[n * 2];
        Gdip.GraphicsPath_GetPathTypes(this.handle, byArray, n);
        Gdip.GraphicsPath_GetPathPoints(this.handle, fArray, n);
        byte[] byArray2 = new byte[n * 2];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            byte by = byArray[n2];
            boolean bl = false;
            switch (by & 7) {
                case 0: {
                    byArray2[n3++] = 1;
                    bl = (by & 0x80) != 0;
                    ++n2;
                    break;
                }
                case 1: {
                    byArray2[n3++] = 2;
                    bl = (by & 0x80) != 0;
                    ++n2;
                    break;
                }
                case 3: {
                    byArray2[n3++] = 4;
                    bl = (byArray[n2 + 2] & 0x80) != 0;
                    n2 += 3;
                    break;
                }
                default: {
                    ++n2;
                }
            }
            if (!bl) continue;
            byArray2[n3++] = 5;
        }
        if (n3 != byArray2.length) {
            byte[] byArray3 = new byte[n3];
            System.arraycopy(byArray2, 0, byArray3, 0, n3);
            byArray2 = byArray3;
        }
        PathData pathData = new PathData();
        pathData.types = byArray2;
        pathData.points = fArray;
        return pathData;
    }

    public void lineTo(float f, float f2) {
        Device device = this.getDevice();
        this.lineToInPixels(DPIUtil.autoScaleUp((Drawable)device, f), DPIUtil.autoScaleUp((Drawable)device, f2));
    }

    void lineToInPixels(float f, float f2) {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.GraphicsPath_AddLine(this.handle, this.currentPoint.X, this.currentPoint.Y, f, f2);
        Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
    }

    void init(PathData pathData) {
        byte[] byArray = pathData.types;
        float[] fArray = pathData.points;
        int n = 0;
        block7: for (int i = 0; i < byArray.length; ++i) {
            switch (byArray[i]) {
                case 1: {
                    this.moveTo(fArray[n++], fArray[n++]);
                    continue block7;
                }
                case 2: {
                    this.lineTo(fArray[n++], fArray[n++]);
                    continue block7;
                }
                case 4: {
                    this.cubicTo(fArray[n++], fArray[n++], fArray[n++], fArray[n++], fArray[n++], fArray[n++]);
                    continue block7;
                }
                case 3: {
                    this.quadTo(fArray[n++], fArray[n++], fArray[n++], fArray[n++]);
                    continue block7;
                }
                case 5: {
                    this.close();
                    continue block7;
                }
                default: {
                    this.dispose();
                    SWT.error(5);
                }
            }
        }
    }

    public void moveTo(float f, float f2) {
        Device device = this.getDevice();
        this.moveToInPixels(DPIUtil.autoScaleUp((Drawable)device, f), DPIUtil.autoScaleUp((Drawable)device, f2));
    }

    void moveToInPixels(float f, float f2) {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.GraphicsPath_StartFigure(this.handle);
        PointF pointF = this.currentPoint;
        this.startPoint.X = f;
        pointF.X = f;
        PointF pointF2 = this.currentPoint;
        this.startPoint.Y = f2;
        pointF2.Y = f2;
    }

    public void quadTo(float f, float f2, float f3, float f4) {
        Device device = this.getDevice();
        f = DPIUtil.autoScaleUp((Drawable)device, f);
        f2 = DPIUtil.autoScaleUp((Drawable)device, f2);
        f3 = DPIUtil.autoScaleUp((Drawable)device, f3);
        f4 = DPIUtil.autoScaleUp((Drawable)device, f4);
        this.quadToInPixels(f, f2, f3, f4);
    }

    void quadToInPixels(float f, float f2, float f3, float f4) {
        if (this == false) {
            SWT.error(44);
        }
        float f5 = this.currentPoint.X + 2.0f * (f - this.currentPoint.X) / 3.0f;
        float f6 = this.currentPoint.Y + 2.0f * (f2 - this.currentPoint.Y) / 3.0f;
        float f7 = f5 + (f3 - this.currentPoint.X) / 3.0f;
        float f8 = f6 + (f4 - this.currentPoint.Y) / 3.0f;
        Gdip.GraphicsPath_AddBezier(this.handle, this.currentPoint.X, this.currentPoint.Y, f5, f6, f7, f8, f3, f4);
        Gdip.GraphicsPath_GetLastPoint(this.handle, this.currentPoint);
    }

    public String toString() {
        if (this == false) {
            return "Path {*DISPOSED*}";
        }
        return "Path {" + this.handle;
    }
}

