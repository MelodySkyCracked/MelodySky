/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.gdip.RectF;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.BLENDFUNCTION;
import org.eclipse.swt.internal.win32.GCP_RESULTS;
import org.eclipse.swt.internal.win32.GRADIENT_RECT;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TRIVERTEX;

public final class GC
extends Resource {
    public long handle;
    Drawable drawable;
    GCData data;
    static final int FOREGROUND = 1;
    static final int BACKGROUND = 2;
    static final int FONT = 4;
    static final int LINE_STYLE = 8;
    static final int LINE_WIDTH = 16;
    static final int LINE_CAP = 32;
    static final int LINE_JOIN = 64;
    static final int LINE_MITERLIMIT = 128;
    static final int FOREGROUND_TEXT = 256;
    static final int BACKGROUND_TEXT = 512;
    static final int BRUSH = 1024;
    static final int PEN = 2048;
    static final int NULL_BRUSH = 4096;
    static final int NULL_PEN = 8192;
    static final int DRAW_OFFSET = 16384;
    static final int DRAW = 22777;
    static final int FILL = 9218;
    static final float[] LINE_DOT_ZERO = new float[]{3.0f, 3.0f};
    static final float[] LINE_DASH_ZERO = new float[]{18.0f, 6.0f};
    static final float[] LINE_DASHDOT_ZERO = new float[]{9.0f, 6.0f, 3.0f, 6.0f};
    static final float[] LINE_DASHDOTDOT_ZERO = new float[]{9.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f};

    public GC() {
    }

    public GC(Drawable drawable) {
        this(drawable, 0);
    }

    public GC(Drawable drawable, int n) {
        Device device;
        if (drawable == null) {
            SWT.error(4);
        }
        GCData gCData = new GCData();
        gCData.style = GC.checkStyle(n);
        long l2 = drawable.internal_new_GC(gCData);
        Device device2 = gCData.device;
        if (device2 == null) {
            device2 = Device.getDevice();
        }
        if (device2 == null) {
            SWT.error(4);
        }
        GCData gCData2 = gCData;
        gCData2.device = device = device2;
        this.device = device;
        this.init(drawable, gCData, l2);
        this.init();
    }

    static int checkStyle(int n) {
        if ((n & 0x2000000) != 0) {
            n &= 0xFBFFFFFF;
        }
        return n & 0x6000000;
    }

    void checkGC(int n) {
        Object object;
        int n2 = this.data.state;
        if ((n2 & n) == n) {
            return;
        }
        n2 = (n2 ^ n) & n;
        GCData gCData = this.data;
        gCData.state |= n;
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Object object2;
            int n3;
            int n4;
            Object object3;
            long l3 = this.data.gdipPen;
            float f = this.data.lineWidth;
            if ((n2 & 1) != 0 || l3 == 0L && (n2 & 0xF8) != 0) {
                long l4;
                if (this.data.gdipFgBrush != 0L) {
                    Gdip.SolidBrush_delete(this.data.gdipFgBrush);
                }
                this.data.gdipFgBrush = 0L;
                object3 = this.data.foregroundPattern;
                if (object3 != null) {
                    if (this.data.alpha == 255) {
                        l4 = ((Pattern)object3).handle;
                    } else {
                        this.data.gdipFgPatternBrushAlpha = l4 = this.data.gdipFgPatternBrushAlpha != 0L ? Gdip.Brush_Clone(this.data.gdipFgPatternBrushAlpha) : GC.createAlphaTextureBrush(((Pattern)object3).handle, this.data.alpha);
                    }
                    if ((this.data.style & 0x8000000) != 0) {
                        switch (Gdip.Brush_GetType(l4)) {
                            case 2: {
                                l4 = Gdip.Brush_Clone(l4);
                                if (l4 == 0L) {
                                    SWT.error(2);
                                }
                                Gdip.TextureBrush_ScaleTransform(l4, -1.0f, 1.0f, 0);
                                this.data.gdipFgBrush = l4;
                            }
                        }
                    }
                } else {
                    n4 = this.data.foreground;
                    int n5 = this.data.alpha << 24 | n4 >> 16 & 0xFF | n4 & 0xFF00 | (n4 & 0xFF) << 16;
                    l4 = Gdip.SolidBrush_new(n5);
                    if (l4 == 0L) {
                        SWT.error(2);
                    }
                    this.data.gdipFgBrush = l4;
                }
                if (l3 != 0L) {
                    Gdip.Pen_SetBrush(l3, l4);
                } else {
                    long l5;
                    GCData gCData2 = this.data;
                    gCData2.gdipPen = l5 = Gdip.Pen_new(l4, f);
                    l3 = l5;
                }
            }
            if ((n2 & 0x10) != 0) {
                Gdip.Pen_SetWidth(l3, f);
                switch (this.data.lineStyle) {
                    case 6: {
                        n2 |= 8;
                    }
                }
            }
            if ((n2 & 8) != 0) {
                object3 = null;
                float f2 = 0.0f;
                n3 = 0;
                switch (this.data.lineStyle) {
                    case 3: {
                        n3 = 2;
                        if (f != 0.0f) break;
                        object3 = LINE_DOT_ZERO;
                        break;
                    }
                    case 2: {
                        n3 = 1;
                        if (f != 0.0f) break;
                        object3 = LINE_DASH_ZERO;
                        break;
                    }
                    case 4: {
                        n3 = 3;
                        if (f != 0.0f) break;
                        object3 = LINE_DASHDOT_ZERO;
                        break;
                    }
                    case 5: {
                        n3 = 4;
                        if (f != 0.0f) break;
                        object3 = LINE_DASHDOTDOT_ZERO;
                        break;
                    }
                    case 6: {
                        if (this.data.lineDashes == null) break;
                        f2 = this.data.lineDashesOffset / Math.max(1.0f, f);
                        object3 = new float[this.data.lineDashes.length * 2];
                        for (n4 = 0; n4 < this.data.lineDashes.length; ++n4) {
                            float f3 = this.data.lineDashes[n4] / Math.max(1.0f, f);
                            object3[n4] = f3;
                            object3[n4 + this.data.lineDashes.length] = f3;
                        }
                        break;
                    }
                }
                if (object3 != null) {
                    Gdip.Pen_SetDashPattern(l3, (float[])object3, ((Pattern)object3).length);
                    Gdip.Pen_SetDashStyle(l3, 5);
                    Gdip.Pen_SetDashOffset(l3, f2);
                } else {
                    Gdip.Pen_SetDashStyle(l3, n3);
                }
            }
            if ((n2 & 0x80) != 0) {
                Gdip.Pen_SetMiterLimit(l3, this.data.lineMiterLimit);
            }
            if ((n2 & 0x40) != 0) {
                int n6 = 0;
                switch (this.data.lineJoin) {
                    case 1: {
                        n6 = 0;
                        break;
                    }
                    case 3: {
                        n6 = 1;
                        break;
                    }
                    case 2: {
                        n6 = 2;
                    }
                }
                Gdip.Pen_SetLineJoin(l3, n6);
            }
            if ((n2 & 0x20) != 0) {
                int n7 = 0;
                int n8 = 0;
                switch (this.data.lineCap) {
                    case 1: {
                        n8 = 0;
                        break;
                    }
                    case 2: {
                        n8 = 2;
                        n7 = 2;
                        break;
                    }
                    case 3: {
                        n8 = 1;
                    }
                }
                Gdip.Pen_SetLineCap(l3, n8, n8, n7);
            }
            if ((n2 & 2) != 0) {
                if (this.data.gdipBgBrush != 0L) {
                    Gdip.SolidBrush_delete(this.data.gdipBgBrush);
                }
                this.data.gdipBgBrush = 0L;
                Pattern pattern = this.data.backgroundPattern;
                if (pattern != null) {
                    long l6;
                    if (this.data.alpha == 255) {
                        this.data.gdipBrush = pattern.handle;
                    } else {
                        long l7 = this.data.gdipBgPatternBrushAlpha != 0L ? Gdip.Brush_Clone(this.data.gdipBgPatternBrushAlpha) : GC.createAlphaTextureBrush(pattern.handle, this.data.alpha);
                        GCData gCData3 = this.data;
                        GCData gCData4 = this.data;
                        gCData4.gdipBgBrush = l6 = l7;
                        gCData3.gdipBrush = l6;
                    }
                    if ((this.data.style & 0x8000000) != 0) {
                        switch (Gdip.Brush_GetType(this.data.gdipBrush)) {
                            case 2: {
                                long l8 = Gdip.Brush_Clone(this.data.gdipBrush);
                                if (l8 == 0L) {
                                    SWT.error(2);
                                }
                                Gdip.TextureBrush_ScaleTransform(l8, -1.0f, 1.0f, 0);
                                GCData gCData5 = this.data;
                                GCData gCData6 = this.data;
                                gCData6.gdipBgBrush = l6 = l8;
                                gCData5.gdipBrush = l6;
                                break;
                            }
                        }
                    }
                } else {
                    long l9;
                    int n9 = this.data.background;
                    n3 = this.data.alpha << 24 | n9 >> 16 & 0xFF | n9 & 0xFF00 | (n9 & 0xFF) << 16;
                    long l10 = Gdip.SolidBrush_new(n3);
                    if (l10 == 0L) {
                        SWT.error(2);
                    }
                    GCData gCData7 = this.data;
                    object2 = this.data;
                    ((GCData)object2).gdipBgBrush = l9 = l10;
                    gCData7.gdipBrush = l9;
                }
            }
            if ((n2 & 4) != 0) {
                Font font = this.data.font;
                OS.SelectObject(this.handle, font.handle);
                long[] lArray = new long[]{0L};
                long l11 = GC.createGdipFont(this.handle, font.handle, l2, this.device.fontCollection, null, lArray);
                if (lArray[0] != 0L) {
                    OS.SelectObject(this.handle, lArray[0]);
                }
                if (this.data.hGDIFont != 0L) {
                    OS.DeleteObject(this.data.hGDIFont);
                }
                this.data.hGDIFont = lArray[0];
                if (this.data.gdipFont != 0L) {
                    Gdip.Font_delete(this.data.gdipFont);
                }
                this.data.gdipFont = l11;
            }
            if ((n2 & 0x4000) != 0) {
                float f4;
                GCData gCData8 = this.data;
                GCData gCData9 = this.data;
                float f5 = 0.0f;
                gCData9.gdipYOffset = 0.0f;
                gCData8.gdipXOffset = 0.0f;
                long l12 = Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
                PointF pointF = new PointF();
                object2 = pointF;
                PointF pointF2 = pointF;
                float f6 = 1.0f;
                ((PointF)object2).Y = 1.0f;
                pointF.X = 1.0f;
                Gdip.Graphics_GetTransform(l2, l12);
                Gdip.Matrix_TransformVectors(l12, pointF2, 1);
                Gdip.Matrix_delete(l12);
                float f7 = pointF2.X;
                if (f7 < 0.0f) {
                    f7 = -f7;
                }
                if ((f4 = this.data.lineWidth * f7) == 0.0f || ((int)f4 & 1) == 1) {
                    this.data.gdipXOffset = 0.5f / f7;
                }
                if ((f7 = pointF2.Y) < 0.0f) {
                    f7 = -f7;
                }
                if ((f4 = this.data.lineWidth * f7) == 0.0f || ((int)f4 & 1) == 1) {
                    this.data.gdipYOffset = 0.5f / f7;
                }
            }
            return;
        }
        if ((n2 & 0x79) != 0) {
            long l13;
            Object object4;
            long l14;
            int n10;
            int n11 = this.data.foreground;
            int n12 = (int)this.data.lineWidth;
            object = null;
            int n13 = 0;
            switch (this.data.lineStyle) {
                case 2: {
                    n13 = 1;
                    break;
                }
                case 3: {
                    n13 = 2;
                    break;
                }
                case 4: {
                    n13 = 3;
                    break;
                }
                case 5: {
                    n13 = 4;
                    break;
                }
                case 6: {
                    if (this.data.lineDashes == null) break;
                    n13 = 7;
                    object = new int[this.data.lineDashes.length];
                    for (n10 = 0; n10 < ((Object)object).length; ++n10) {
                        object[n10] = (int)this.data.lineDashes[n10];
                    }
                    break;
                }
            }
            if ((n2 & 8) != 0) {
                OS.SetBkMode(this.handle, this.data.lineStyle == 1 ? 2 : 1);
            }
            n10 = 0;
            switch (this.data.lineJoin) {
                case 1: {
                    n10 = 8192;
                    break;
                }
                case 2: {
                    n10 = 0;
                    break;
                }
                case 3: {
                    n10 = 4096;
                }
            }
            int n14 = 0;
            switch (this.data.lineCap) {
                case 2: {
                    n14 = 0;
                    break;
                }
                case 1: {
                    n14 = 512;
                    break;
                }
                case 3: {
                    n14 = 256;
                }
            }
            int n15 = n13 | n10 | n14;
            if (n12 == 0 && n13 != 7 || n15 == 0) {
                l14 = OS.CreatePen(n15 & 0xF, n12, n11);
            } else {
                object4 = new LOGBRUSH();
                ((LOGBRUSH)object4).lbStyle = 0;
                ((LOGBRUSH)object4).lbColor = n11;
                l14 = OS.ExtCreatePen(n15 | 0x10000, Math.max(1, n12), (LOGBRUSH)object4, object != null ? ((Object)object).length : 0, (int[])object);
            }
            OS.SelectObject(this.handle, l14);
            object4 = this.data;
            ((GCData)object4).state |= 0x800;
            GCData gCData10 = this.data;
            gCData10.state &= 0xFFFFDFFF;
            if (this.data.hPen != 0L) {
                OS.DeleteObject(this.data.hPen);
            }
            GCData gCData11 = this.data;
            GCData gCData12 = this.data;
            gCData12.hOldPen = l13 = l14;
            gCData11.hPen = l13;
        } else if ((n2 & 0x800) != 0) {
            OS.SelectObject(this.handle, this.data.hOldPen);
            GCData gCData13 = this.data;
            gCData13.state &= 0xFFFFDFFF;
        } else if ((n2 & 0x2000) != 0) {
            this.data.hOldPen = OS.SelectObject(this.handle, OS.GetStockObject(8));
            GCData gCData14 = this.data;
            gCData14.state &= 0xFFFFF7FF;
        }
        if ((n2 & 2) != 0) {
            long l15;
            long l16 = OS.CreateSolidBrush(this.data.background);
            OS.SelectObject(this.handle, l16);
            object = this.data;
            ((GCData)object).state |= 0x400;
            GCData gCData15 = this.data;
            gCData15.state &= 0xFFFFEFFF;
            if (this.data.hBrush != 0L) {
                OS.DeleteObject(this.data.hBrush);
            }
            GCData gCData16 = this.data;
            GCData gCData17 = this.data;
            gCData17.hBrush = l15 = l16;
            gCData16.hOldBrush = l15;
        } else if ((n2 & 0x400) != 0) {
            OS.SelectObject(this.handle, this.data.hOldBrush);
            GCData gCData18 = this.data;
            gCData18.state &= 0xFFFFEFFF;
        } else if ((n2 & 0x1000) != 0) {
            this.data.hOldBrush = OS.SelectObject(this.handle, OS.GetStockObject(5));
            GCData gCData19 = this.data;
            gCData19.state &= 0xFFFFFBFF;
        }
        if ((n2 & 0x200) != 0) {
            OS.SetBkColor(this.handle, this.data.background);
        }
        if ((n2 & 0x100) != 0) {
            OS.SetTextColor(this.handle, this.data.foreground);
        }
        if ((n2 & 4) != 0) {
            Font font = this.data.font;
            OS.SelectObject(this.handle, font.handle);
        }
    }

    public void copyArea(Image image, int n, int n2) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.copyAreaInPixels(image, n, n2);
    }

    void copyAreaInPixels(Image image, int n, int n2) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (image == null) {
            SWT.error(4);
        }
        if (image.type != 0 || image.isDisposed()) {
            SWT.error(5);
        }
        Rectangle rectangle = image.getBoundsInPixels();
        long l2 = OS.CreateCompatibleDC(this.handle);
        long l3 = OS.SelectObject(l2, image.handle);
        OS.BitBlt(l2, 0, 0, rectangle.width, rectangle.height, this.handle, n, n2, 0xCC0020);
        OS.SelectObject(l2, l3);
        OS.DeleteDC(l2);
    }

    public void copyArea(int n, int n2, int n3, int n4, int n5, int n6) {
        this.copyArea(n, n2, n3, n4, n5, n6, true);
    }

    public void copyArea(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        n5 = DPIUtil.autoScaleUp(this.drawable, n5);
        n6 = DPIUtil.autoScaleUp(this.drawable, n6);
        this.copyAreaInPixels(n, n2, n3, n4, n5, n6, bl);
    }

    void copyAreaInPixels(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        long l2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if ((l2 = this.data.hwnd) == 0L) {
            OS.BitBlt(this.handle, n5, n6, n3, n4, this.handle, n, n2, 0xCC0020);
        } else {
            RECT rECT = null;
            long l3 = OS.CreateRectRgn(0, 0, 0, 0);
            if (OS.GetClipRgn(this.handle, l3) == 1) {
                rECT = new RECT();
                OS.GetRgnBox(l3, rECT);
            }
            OS.DeleteObject(l3);
            RECT rECT2 = new RECT();
            OS.SetRect(rECT2, n, n2, n + n3, n2 + n4);
            int n7 = bl ? 6 : 0;
            OS.ScrollWindowEx(l2, n5 - n, n6 - n2, rECT2, rECT, 0L, null, n7);
        }
    }

    static long createGdipFont(long l2, long l3, long l4, long l5, long[] lArray, long[] lArray2) {
        long l6 = Gdip.Font_new(l2, l3);
        if (l6 == 0L) {
            SWT.error(2);
        }
        long l7 = 0L;
        if (!Gdip.Font_IsAvailable(l6)) {
            int n;
            Gdip.Font_delete(l6);
            LOGFONT lOGFONT = new LOGFONT();
            OS.GetObject(l3, LOGFONT.sizeof, lOGFONT);
            int n2 = Math.abs(lOGFONT.lfHeight);
            int n3 = 0;
            if (lOGFONT.lfWeight == 700) {
                n3 |= 1;
            }
            if (lOGFONT.lfItalic != 0) {
                n3 |= 2;
            }
            char[] cArray = lOGFONT.lfFaceName;
            for (n = 0; n < cArray.length && cArray[n] != '\u0000'; ++n) {
            }
            String string = new String(cArray, 0, n);
            if (string.equalsIgnoreCase("Courier")) {
                string = "Courier New";
            }
            char[] cArray2 = new char[string.length() + 1];
            string.getChars(0, string.length(), cArray2, 0);
            if (l5 != 0L && !Gdip.FontFamily_IsAvailable(l7 = Gdip.FontFamily_new(cArray2, l5))) {
                Gdip.FontFamily_delete(l7);
                l7 = Gdip.FontFamily_new(cArray2, 0L);
                if (!Gdip.FontFamily_IsAvailable(l7)) {
                    Gdip.FontFamily_delete(l7);
                    l7 = 0L;
                }
            }
            l6 = l7 != 0L ? Gdip.Font_new(l7, n2, n3, 2) : Gdip.Font_new(cArray2, n2, n3, 2, 0L);
            if (lArray2 != null && l6 != 0L) {
                long l8 = OS.GetProcessHeap();
                long l9 = OS.HeapAlloc(l8, 8, LOGFONT.sizeof);
                Gdip.Font_GetLogFontW(l6, l4, l9);
                lArray2[0] = OS.CreateFontIndirect(l9);
                OS.HeapFree(l8, 0, l9);
            }
        }
        if (lArray != null && l6 != 0L) {
            if (l7 == 0L) {
                l7 = Gdip.FontFamily_new();
                Gdip.Font_GetFamily(l6, l7);
            }
            lArray[0] = l7;
        } else if (l7 != 0L) {
            Gdip.FontFamily_delete(l7);
        }
        if (l6 == 0L) {
            SWT.error(2);
        }
        return l6;
    }

    static long createAlphaTextureBrush(long l2, int n) {
        long l3;
        if (Gdip.Brush_GetType(l2) != 2) {
            return Gdip.Brush_Clone(l2);
        }
        long l4 = Gdip.TextureBrush_GetImage(l2);
        if (l4 == 0L) {
            SWT.error(7);
        }
        if ((l3 = Gdip.Image_Clone(l4)) == 0L) {
            SWT.error(2);
        }
        long l5 = Gdip.ImageAttributes_new();
        Gdip.ImageAttributes_SetWrapMode(l5, 0);
        float[] fArray = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (float)n / 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        Gdip.ImageAttributes_SetColorMatrix(l5, fArray, 0, 1);
        Rect rect = new Rect();
        rect.X = 0;
        rect.Y = 0;
        rect.Width = Gdip.Image_GetWidth(l3);
        rect.Height = Gdip.Image_GetHeight(l3);
        long l6 = Gdip.TextureBrush_new(l3, rect, l5);
        if (l2 == 0L) {
            SWT.error(2);
        }
        Gdip.ImageAttributes_delete(l5);
        Gdip.Image_delete(l3);
        return l6;
    }

    static void destroyGdipBrush(long l2) {
        int n = Gdip.Brush_GetType(l2);
        switch (n) {
            case 0: {
                Gdip.SolidBrush_delete(l2);
                break;
            }
            case 1: {
                Gdip.HatchBrush_delete(l2);
                break;
            }
            case 4: {
                Gdip.LinearGradientBrush_delete(l2);
                break;
            }
            case 2: {
                Gdip.TextureBrush_delete(l2);
            }
        }
    }

    @Override
    void destroy() {
        Image image;
        long l2;
        boolean bl = this.data.gdipGraphics != 0L;
        this.disposeGdip();
        if (bl && (this.data.style & 0x8000000) != 0) {
            OS.SetLayout(this.handle, OS.GetLayout(this.handle) | 1);
        }
        if (this.data.hPen != 0L) {
            OS.SelectObject(this.handle, OS.GetStockObject(8));
            OS.DeleteObject(this.data.hPen);
            this.data.hPen = 0L;
        }
        if (this.data.hBrush != 0L) {
            OS.SelectObject(this.handle, OS.GetStockObject(5));
            OS.DeleteObject(this.data.hBrush);
            this.data.hBrush = 0L;
        }
        if ((l2 = this.data.hNullBitmap) != 0L) {
            OS.SelectObject(this.handle, l2);
            this.data.hNullBitmap = 0L;
        }
        if ((image = this.data.image) != null) {
            image.memGC = null;
        }
        if (this.drawable != null) {
            this.drawable.internal_dispose_GC(this.handle, this.data);
        }
        this.drawable = null;
        this.handle = 0L;
        this.data.image = null;
        this.data.ps = null;
        this.data = null;
    }

    void disposeGdip() {
        if (this.data.gdipPen != 0L) {
            Gdip.Pen_delete(this.data.gdipPen);
        }
        if (this.data.gdipBgBrush != 0L) {
            GC.destroyGdipBrush(this.data.gdipBgBrush);
        }
        if (this.data.gdipFgBrush != 0L) {
            GC.destroyGdipBrush(this.data.gdipFgBrush);
        }
        if (this.data.gdipFont != 0L) {
            Gdip.Font_delete(this.data.gdipFont);
        }
        if (this.data.hGDIFont != 0L) {
            OS.DeleteObject(this.data.hGDIFont);
        }
        if (this.data.gdipGraphics != 0L) {
            Gdip.Graphics_delete(this.data.gdipGraphics);
        }
        if (this.data.gdipBgPatternBrushAlpha != 0L) {
            GC.destroyGdipBrush(this.data.gdipBgPatternBrushAlpha);
        }
        if (this.data.gdipFgPatternBrushAlpha != 0L) {
            GC.destroyGdipBrush(this.data.gdipFgPatternBrushAlpha);
        }
        GCData gCData = this.data;
        GCData gCData2 = this.data;
        GCData gCData3 = this.data;
        GCData gCData4 = this.data;
        GCData gCData5 = this.data;
        GCData gCData6 = this.data;
        GCData gCData7 = this.data;
        GCData gCData8 = this.data;
        GCData gCData9 = this.data;
        long l2 = 0L;
        gCData9.gdipFgPatternBrushAlpha = 0L;
        gCData8.gdipBgPatternBrushAlpha = 0L;
        gCData7.hGDIFont = 0L;
        gCData6.gdipPen = 0L;
        gCData5.gdipFont = 0L;
        gCData4.gdipFgBrush = 0L;
        gCData3.gdipBgBrush = 0L;
        gCData2.gdipBrush = 0L;
        gCData.gdipGraphics = 0L;
    }

    public void drawArc(int n, int n2, int n3, int n4, int n5, int n6) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.drawArcInPixels(n, n2, n3, n4, n5, n6);
    }

    void drawArcInPixels(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int n9;
        int n10;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(22777);
        if (n3 < 0) {
            n += n3;
            n3 = -n3;
        }
        if (n4 < 0) {
            n2 += n4;
            n4 = -n4;
        }
        if (n3 == 0 || n4 == 0 || n6 == 0) {
            return;
        }
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
            if (n3 == n4) {
                Gdip.Graphics_DrawArc(l2, this.data.gdipPen, n, n2, n3, n4, -n5, -n6);
            } else {
                long l3;
                long l4 = Gdip.GraphicsPath_new(0);
                if (l4 == 0L) {
                    SWT.error(2);
                }
                if ((l3 = Gdip.Matrix_new(n3, 0.0f, 0.0f, n4, n, n2)) == 0L) {
                    SWT.error(2);
                }
                Gdip.GraphicsPath_AddArc(l4, 0.0f, 0.0f, 1.0f, 1.0f, -n5, -n6);
                Gdip.GraphicsPath_Transform(l4, l3);
                Gdip.Graphics_DrawPath(l2, this.data.gdipPen, l4);
                Gdip.Matrix_delete(l3);
                Gdip.GraphicsPath_delete(l4);
            }
            Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            --n;
        }
        if (n6 >= 360 || n6 <= -360) {
            n9 = n10 = n + n3;
            n7 = n8 = n2 + n4 / 2;
        } else {
            boolean bl = n6 < 0;
            n6 += n5;
            if (bl) {
                int n11 = n5;
                n5 = n6;
                n6 = n11;
            }
            n10 = GC.cos(n5, n3) + n + n3 / 2;
            n8 = -1 * GC.sin(n5, n4) + n2 + n4 / 2;
            n9 = GC.cos(n6, n3) + n + n3 / 2;
            n7 = -1 * GC.sin(n6, n4) + n2 + n4 / 2;
        }
        OS.Arc(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1, n10, n8, n9, n7);
    }

    public void drawFocus(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.drawFocusInPixels(n, n2, n3, n4);
    }

    void drawFocusInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if ((this.data.uiState & 1) != 0) {
            return;
        }
        this.data.focusDrawn = true;
        long l2 = this.handle;
        int n5 = 0;
        long l3 = this.data.gdipGraphics;
        if (l3 != 0L) {
            long l4 = 0L;
            Gdip.Graphics_SetPixelOffsetMode(l3, 3);
            long l5 = Gdip.Region_new();
            if (l5 == 0L) {
                SWT.error(2);
            }
            Gdip.Graphics_GetClip(l3, l5);
            if (!Gdip.Region_IsInfinite(l5, l3)) {
                l4 = Gdip.Region_GetHRGN(l5, l3);
            }
            Gdip.Region_delete(l5);
            Gdip.Graphics_SetPixelOffsetMode(l3, 4);
            float[] fArray = null;
            long l6 = Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
            if (l6 == 0L) {
                SWT.error(2);
            }
            Gdip.Graphics_GetTransform(l3, l6);
            if (!Gdip.Matrix_IsIdentity(l6)) {
                fArray = new float[6];
                Gdip.Matrix_GetElements(l6, fArray);
            }
            Gdip.Matrix_delete(l6);
            l2 = Gdip.Graphics_GetHDC(l3);
            n5 = OS.SaveDC(l2);
            if (fArray != null) {
                OS.SetGraphicsMode(l2, 2);
                OS.SetWorldTransform(l2, fArray);
            }
            if (l4 != 0L) {
                OS.SelectClipRgn(l2, l4);
                OS.DeleteObject(l4);
            }
        }
        OS.SetBkColor(l2, 0xFFFFFF);
        OS.SetTextColor(l2, 0);
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, n + n3, n2 + n4);
        OS.DrawFocusRect(l2, rECT);
        if (l3 != 0L) {
            OS.RestoreDC(l2, n5);
            Gdip.Graphics_ReleaseHDC(l3, l2);
        } else {
            GCData gCData = this.data;
            gCData.state &= 0xFFFFFCFF;
        }
    }

    public void drawImage(Image image, int n, int n2) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawImageInPixels(image, n, n2);
    }

    void drawImageInPixels(Image image, int n, int n2) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (image == null) {
            SWT.error(4);
        }
        if (image.isDisposed()) {
            SWT.error(5);
        }
        this.drawImage(image, 0, 0, -1, -1, n, n2, -1, -1, true);
    }

    public void drawImage(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (n3 == 0 || n4 == 0 || n7 == 0 || n8 == 0) {
            return;
        }
        if (n < 0 || n2 < 0 || n3 < 0 || n4 < 0 || n7 < 0 || n8 < 0) {
            SWT.error(5);
        }
        if (image == null) {
            SWT.error(4);
        }
        if (image.isDisposed()) {
            SWT.error(5);
        }
        Rectangle rectangle = DPIUtil.autoScaleUp(this.drawable, new Rectangle(n, n2, n3, n4));
        Rectangle rectangle2 = DPIUtil.autoScaleUp(this.drawable, new Rectangle(n5, n6, n7, n8));
        int n9 = DPIUtil.getDeviceZoom();
        if (n9 != 100) {
            Rectangle rectangle3 = image.getBoundsInPixels();
            int n10 = rectangle.x + rectangle.width - rectangle3.width;
            int n11 = rectangle.y + rectangle.height - rectangle3.height;
            if (n10 != 0 || n11 != 0) {
                if (n10 <= n9 / 100 && n11 <= n9 / 100) {
                    rectangle.intersect(rectangle3);
                } else {
                    SWT.error(5);
                }
            }
        }
        this.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height, false);
    }

    void drawImage(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        image.refreshImageForZoom();
        if (this.data.gdipGraphics != 0L) {
            long[] lArray = image.createGdipImage();
            long l2 = lArray[0];
            int n9 = Gdip.Image_GetWidth(l2);
            int n10 = Gdip.Image_GetHeight(l2);
            if (bl) {
                n7 = n3 = n9;
                n8 = n4 = n10;
            } else {
                if (n + n3 > n9 || n2 + n4 > n10) {
                    SWT.error(5);
                }
                bl = n == 0 && n2 == 0 && n3 == n7 && n7 == n9 && n4 == n8 && n8 == n10;
            }
            Rect rect = new Rect();
            rect.X = n5;
            rect.Y = n6;
            rect.Width = n7;
            rect.Height = n8;
            long l3 = Gdip.ImageAttributes_new();
            Gdip.ImageAttributes_SetWrapMode(l3, 3);
            if (this.data.alpha != 255) {
                float[] fArray = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, (float)this.data.alpha / 255.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
                Gdip.ImageAttributes_SetColorMatrix(l3, fArray, 0, 1);
            }
            int n11 = 0;
            if ((this.data.style & 0x8000000) != 0) {
                n11 = Gdip.Graphics_Save(this.data.gdipGraphics);
                Gdip.Graphics_ScaleTransform(this.data.gdipGraphics, -1.0f, 1.0f, 0);
                Gdip.Graphics_TranslateTransform(this.data.gdipGraphics, -2 * n5 - n7, 0.0f, 0);
            }
            Gdip.Graphics_DrawImage(this.data.gdipGraphics, l2, rect, n, n2, n3, n4, 2, l3, 0L, 0L);
            if ((this.data.style & 0x8000000) != 0) {
                Gdip.Graphics_Restore(this.data.gdipGraphics, n11);
            }
            Gdip.ImageAttributes_delete(l3);
            Gdip.Bitmap_delete(l2);
            if (lArray[1] != 0L) {
                long l4 = OS.GetProcessHeap();
                OS.HeapFree(l4, 0, lArray[1]);
            }
            return;
        }
        switch (image.type) {
            case 0: {
                this.drawBitmap(image, n, n2, n3, n4, n5, n6, n7, n8, bl);
                break;
            }
            case 1: {
                this.drawIcon(image, n, n2, n3, n4, n5, n6, n7, n8, bl);
            }
        }
    }

    void drawIcon(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        boolean bl2;
        Object object;
        int n9 = OS.GetDeviceCaps(this.handle, 2);
        boolean bl3 = true;
        int n10 = 3;
        int n11 = 0;
        int n12 = 0;
        if ((OS.GetLayout(this.handle) & 1) != 0) {
            n10 |= 0x10;
            object = new POINT();
            OS.GetWindowOrgEx(this.handle, (POINT)object);
            n11 = ((POINT)object).x;
            n12 = ((POINT)object).y;
        }
        if (bl && n9 != 2) {
            if (n11 != 0 || n12 != 0) {
                OS.SetWindowOrgEx(this.handle, 0, 0, null);
            }
            OS.DrawIconEx(this.handle, n5 - n11, n6 - n12, image.handle, 0, 0, 0, 0L, n10);
            if (n11 != 0 || n12 != 0) {
                OS.SetWindowOrgEx(this.handle, n11, n12, null);
            }
            return;
        }
        object = new ICONINFO();
        OS.GetIconInfo(image.handle, (ICONINFO)object);
        long l2 = ((ICONINFO)object).hbmColor;
        if (l2 == 0L) {
            l2 = ((ICONINFO)object).hbmMask;
        }
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l2, BITMAP.sizeof, bITMAP);
        int n13 = bITMAP.bmWidth;
        int n14 = bITMAP.bmHeight;
        if (l2 == ((ICONINFO)object).hbmMask) {
            n14 /= 2;
        }
        if (bl) {
            n7 = n3 = n13;
            n8 = n4 = n14;
        }
        boolean bl4 = bl2 = n + n3 > n13 || n2 + n4 > n14;
        if (!bl2) {
            boolean bl5 = bl = n == 0 && n2 == 0 && n3 == n7 && n4 == n8 && n3 == n13 && n4 == n14;
            if (bl && n9 != 2) {
                if (n11 != 0 || n12 != 0) {
                    OS.SetWindowOrgEx(this.handle, 0, 0, null);
                }
                OS.DrawIconEx(this.handle, n5 - n11, n6 - n12, image.handle, 0, 0, 0, 0L, n10);
                if (n11 != 0 || n12 != 0) {
                    OS.SetWindowOrgEx(this.handle, n11, n12, null);
                }
            } else {
                boolean bl6;
                ICONINFO iCONINFO = new ICONINFO();
                iCONINFO.fIcon = true;
                long l3 = OS.CreateCompatibleDC(this.handle);
                long l4 = OS.CreateCompatibleDC(this.handle);
                int n15 = n2;
                long l5 = ((ICONINFO)object).hbmColor;
                if (l5 == 0L) {
                    l5 = ((ICONINFO)object).hbmMask;
                    n15 += n14;
                }
                long l6 = OS.SelectObject(l3, l5);
                iCONINFO.hbmColor = OS.CreateCompatibleBitmap(l3, n7, n8);
                if (iCONINFO.hbmColor == 0L) {
                    SWT.error(2);
                }
                long l7 = OS.SelectObject(l4, iCONINFO.hbmColor);
                boolean bl7 = bl6 = !bl && (n3 != n7 || n4 != n8);
                if (bl6) {
                    OS.SetStretchBltMode(l4, 3);
                    OS.StretchBlt(l4, 0, 0, n7, n8, l3, n, n15, n3, n4, 0xCC0020);
                } else {
                    OS.BitBlt(l4, 0, 0, n7, n8, l3, n, n15, 0xCC0020);
                }
                OS.SelectObject(l3, ((ICONINFO)object).hbmMask);
                iCONINFO.hbmMask = OS.CreateBitmap(n7, n8, 1, 1, null);
                if (iCONINFO.hbmMask == 0L) {
                    SWT.error(2);
                }
                OS.SelectObject(l4, iCONINFO.hbmMask);
                if (bl6) {
                    OS.StretchBlt(l4, 0, 0, n7, n8, l3, n, n2, n3, n4, 0xCC0020);
                } else {
                    OS.BitBlt(l4, 0, 0, n7, n8, l3, n, n2, 0xCC0020);
                }
                if (n9 == 2) {
                    OS.SelectObject(l3, iCONINFO.hbmColor);
                    OS.SelectObject(l4, iCONINFO.hbmMask);
                    this.drawBitmapTransparentByClipping(l3, l4, 0, 0, n7, n8, n5, n6, n7, n8, true, n7, n8);
                    OS.SelectObject(l3, l6);
                    OS.SelectObject(l4, l7);
                } else {
                    OS.SelectObject(l3, l6);
                    OS.SelectObject(l4, l7);
                    long l8 = OS.CreateIconIndirect(iCONINFO);
                    if (l8 == 0L) {
                        SWT.error(2);
                    }
                    if (n11 != 0 || n12 != 0) {
                        OS.SetWindowOrgEx(this.handle, 0, 0, null);
                    }
                    OS.DrawIconEx(this.handle, n5 - n11, n6 - n12, l8, n7, n8, 0, 0L, n10);
                    if (n11 != 0 || n12 != 0) {
                        OS.SetWindowOrgEx(this.handle, n11, n12, null);
                    }
                    OS.DestroyIcon(l8);
                }
                OS.DeleteObject(iCONINFO.hbmMask);
                OS.DeleteObject(iCONINFO.hbmColor);
                OS.DeleteDC(l4);
                OS.DeleteDC(l3);
            }
        }
        OS.DeleteObject(((ICONINFO)object).hbmMask);
        if (((ICONINFO)object).hbmColor != 0L) {
            OS.DeleteObject(((ICONINFO)object).hbmColor);
        }
        if (bl2) {
            SWT.error(5);
        }
    }

    void drawBitmap(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(image.handle, BITMAP.sizeof, bITMAP);
        int n9 = bITMAP.bmWidth;
        int n10 = bITMAP.bmHeight;
        if (bl) {
            n7 = n3 = n9;
            n8 = n4 = n10;
        } else {
            if (n + n3 > n9 || n2 + n4 > n10) {
                SWT.error(5);
            }
            bl = n == 0 && n2 == 0 && n3 == n7 && n7 == n9 && n4 == n8 && n8 == n10;
        }
        boolean bl2 = false;
        GC gC = image.memGC;
        if (gC != null && gC == false) {
            gC.flush();
            bl2 = true;
            GCData gCData = gC.data;
            if (gCData.hNullBitmap != 0L) {
                OS.SelectObject(gC.handle, gCData.hNullBitmap);
                gCData.hNullBitmap = 0L;
            }
        }
        boolean bl3 = bITMAP.bmBits != 0L;
        int n11 = bITMAP.bmPlanes * bITMAP.bmBitsPixel;
        if (bl3 && n11 == 32) {
            this.drawBitmapAlpha(image, n, n2, n3, n4, n5, n6, n7, n8, bl);
        } else if (image.transparentPixel != -1) {
            this.drawBitmapTransparent(image, n, n2, n3, n4, n5, n6, n7, n8, bl, bITMAP, n9, n10);
        } else {
            this.drawBitmapColor(image, n, n2, n3, n4, n5, n6, n7, n8, bl);
        }
        if (bl2) {
            long l2;
            gC.data.hNullBitmap = l2 = OS.SelectObject(gC.handle, image.handle);
        }
    }

    void drawBitmapAlpha(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        long l2;
        long l3;
        long l4;
        long l5;
        long l6;
        int n9;
        boolean bl2 = true;
        boolean bl3 = OS.GetDeviceCaps(this.handle, 2) == 2;
        int n10 = -1;
        if (bl3 && (n9 = OS.GetDeviceCaps(this.handle, 120)) != 0) {
            l6 = OS.CreateCompatibleDC(this.handle);
            l5 = OS.SelectObject(l6, image.handle);
            l4 = Image.createDIB(n3, n4, 32);
            if (l4 == 0L) {
                SWT.error(2);
            }
            l3 = OS.CreateCompatibleDC(this.handle);
            l2 = OS.SelectObject(l3, l4);
            BITMAP bITMAP = new BITMAP();
            OS.GetObject(l4, BITMAP.sizeof, bITMAP);
            OS.BitBlt(l3, 0, 0, n3, n4, l6, n, n2, 0xCC0020);
            byte[] byArray = new byte[bITMAP.bmWidthBytes * bITMAP.bmHeight];
            OS.MoveMemory(byArray, bITMAP.bmBits, byArray.length);
            int n11 = byArray.length;
            n10 = byArray[3] & 0xFF;
            for (int i = 7; i < n11; i += 4) {
                int n12 = byArray[i] & 0xFF;
                if (n10 == n12) continue;
                n10 = -1;
                break;
            }
            OS.SelectObject(l3, l2);
            OS.DeleteDC(l3);
            OS.DeleteObject(l4);
            OS.SelectObject(l6, l5);
            OS.DeleteDC(l6);
            if (n10 != -1) {
                if (n10 == 0) {
                    return;
                }
                if (n10 == 255) {
                    this.drawBitmapColor(image, n, n2, n3, n4, n5, n6, n7, n8, bl);
                    return;
                }
                bl2 = (n9 & 1) != 0;
            } else {
                boolean bl4 = bl2 = (n9 & 2) != 0;
            }
        }
        if (bl2) {
            BLENDFUNCTION bLENDFUNCTION = new BLENDFUNCTION();
            bLENDFUNCTION.BlendOp = 0;
            l6 = OS.CreateCompatibleDC(this.handle);
            l5 = OS.SelectObject(l6, image.handle);
            bLENDFUNCTION.SourceConstantAlpha = (byte)n10;
            bLENDFUNCTION.AlphaFormat = 1;
            OS.AlphaBlend(this.handle, n5, n6, n7, n8, l6, n, n2, n3, n4, bLENDFUNCTION);
            OS.SelectObject(l6, l5);
            OS.DeleteDC(l6);
            return;
        }
        Rectangle rectangle = this.getClippingInPixels();
        if ((rectangle = rectangle.intersection(new Rectangle(n5, n6, n7, n8))).isEmpty()) {
            return;
        }
        int n13 = n + (rectangle.x - n5) * n3 / n7;
        int n14 = n + (rectangle.x + rectangle.width - n5) * n3 / n7;
        int n15 = n2 + (rectangle.y - n6) * n4 / n8;
        int n16 = n2 + (rectangle.y + rectangle.height - n6) * n4 / n8;
        n5 = rectangle.x;
        n6 = rectangle.y;
        n7 = rectangle.width;
        n8 = rectangle.height;
        n = n13;
        n2 = n15;
        n3 = Math.max(1, n14 - n13);
        n4 = Math.max(1, n16 - n15);
        l4 = OS.CreateCompatibleDC(this.handle);
        l3 = OS.SelectObject(l4, image.handle);
        l2 = OS.CreateCompatibleDC(this.handle);
        long l7 = Image.createDIB(Math.max(n3, n7), Math.max(n4, n8), 32);
        if (l7 == 0L) {
            SWT.error(2);
        }
        long l8 = OS.SelectObject(l2, l7);
        BITMAP bITMAP = new BITMAP();
        OS.GetObject(l7, BITMAP.sizeof, bITMAP);
        int n17 = bITMAP.bmWidthBytes * bITMAP.bmHeight;
        OS.BitBlt(l2, 0, 0, n7, n8, this.handle, n5, n6, 0xCC0020);
        byte[] byArray = new byte[n17];
        OS.MoveMemory(byArray, bITMAP.bmBits, n17);
        OS.BitBlt(l2, 0, 0, n3, n4, l4, n, n2, 0xCC0020);
        byte[] byArray2 = new byte[n17];
        OS.MoveMemory(byArray2, bITMAP.bmBits, n17);
        if (bl3) {
            long l9 = OS.CreateCompatibleDC(this.handle);
            long l10 = Image.createDIB(n7, n8, 32);
            if (l10 == 0L) {
                SWT.error(2);
            }
            long l11 = OS.SelectObject(l9, l10);
            if (!(bl || n3 == n7 && n4 == n8)) {
                OS.SetStretchBltMode(l2, 3);
                OS.StretchBlt(l9, 0, 0, n7, n8, l2, 0, 0, n3, n4, 0xCC0020);
            } else {
                OS.BitBlt(l9, 0, 0, n7, n8, l2, 0, 0, 0xCC0020);
            }
            OS.BitBlt(l2, 0, 0, n7, n8, l9, 0, 0, 0xCC0020);
            OS.SelectObject(l9, l11);
            OS.DeleteObject(l10);
            OS.DeleteDC(l9);
        } else if (!(bl || n3 == n7 && n4 == n8)) {
            OS.SetStretchBltMode(l2, 3);
            OS.StretchBlt(l2, 0, 0, n7, n8, l2, 0, 0, n3, n4, 0xCC0020);
        } else {
            OS.BitBlt(l2, 0, 0, n7, n8, l2, 0, 0, 0xCC0020);
        }
        OS.MoveMemory(byArray2, bITMAP.bmBits, n17);
        int n18 = bITMAP.bmWidthBytes - n7 * 4;
        int n19 = 0;
        for (int i = 0; i < n8; ++i) {
            for (int j = 0; j < n7; ++j) {
                int n20;
                int n21;
                int n22;
                int n23 = byArray2[n19 + 3] & 0xFF;
                byte[] byArray3 = byArray;
                int n24 = n22 = n19;
                byArray3[n24] = (byte)(byArray3[n24] + (byte)((byArray2[n19] & 0xFF) - (byArray[n19] & 0xFF) * n23 / 255));
                byte[] byArray4 = byArray;
                int n25 = n21 = n19 + 1;
                byArray4[n25] = (byte)(byArray4[n25] + (byte)((byArray2[n19 + 1] & 0xFF) - (byArray[n19 + 1] & 0xFF) * n23 / 255));
                byte[] byArray5 = byArray;
                int n26 = n20 = n19 + 2;
                byArray5[n26] = (byte)(byArray5[n26] + (byte)((byArray2[n19 + 2] & 0xFF) - (byArray[n19 + 2] & 0xFF) * n23 / 255));
                n19 += 4;
            }
            n19 += n18;
        }
        OS.MoveMemory(bITMAP.bmBits, byArray, n17);
        OS.BitBlt(this.handle, n5, n6, n7, n8, l2, 0, 0, 0xCC0020);
        OS.SelectObject(l2, l8);
        OS.DeleteDC(l2);
        OS.DeleteObject(l7);
        OS.SelectObject(l4, l3);
        OS.DeleteDC(l4);
    }

    void drawBitmapTransparentByClipping(long l2, long l3, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl, int n9, int n10) {
        int n11;
        int n12;
        long l4 = OS.CreateRectRgn(0, 0, 0, 0);
        for (n12 = 0; n12 < n10; ++n12) {
            for (int i = 0; i < n9; ++i) {
                if (OS.GetPixel(l3, i, n12) != 0) continue;
                long l5 = OS.CreateRectRgn(i, n12, i + 1, n12 + 1);
                OS.CombineRgn(l4, l4, l5, 2);
                OS.DeleteObject(l5);
            }
        }
        if (n7 != n3 || n8 != n4) {
            n12 = OS.GetRegionData(l4, 0, null);
            int[] nArray = new int[n12 / 4];
            OS.GetRegionData(l4, n12, nArray);
            float[] fArray = new float[]{(float)n7 / (float)n3, 0.0f, 0.0f, (float)n8 / (float)n4, 0.0f, 0.0f};
            long l6 = OS.ExtCreateRegion(fArray, n12, nArray);
            OS.DeleteObject(l4);
            l4 = l6;
        }
        OS.OffsetRgn(l4, n5, n6);
        long l7 = OS.CreateRectRgn(0, 0, 0, 0);
        int n13 = OS.GetClipRgn(this.handle, l7);
        if (n13 == 1) {
            OS.CombineRgn(l4, l4, l7, 1);
        }
        OS.SelectClipRgn(this.handle, l4);
        int n14 = n11 = OS.GetROP2(this.handle) == 7 ? 0x660046 : 0xCC0020;
        if (!(bl || n3 == n7 && n4 == n8)) {
            int n15 = OS.SetStretchBltMode(this.handle, 3);
            OS.StretchBlt(this.handle, n5, n6, n7, n8, l2, n, n2, n3, n4, n11);
            OS.SetStretchBltMode(this.handle, n15);
        } else {
            OS.BitBlt(this.handle, n5, n6, n7, n8, l2, n, n2, n11);
        }
        OS.SelectClipRgn(this.handle, n13 == 1 ? l7 : 0L);
        OS.DeleteObject(l7);
        OS.DeleteObject(l4);
    }

    void drawBitmapMask(Image image, long l2, long l3, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl, int n9, int n10, boolean bl2) {
        int n11 = n2;
        if (l2 == 0L) {
            l2 = l3;
            n11 += n10;
        }
        long l4 = OS.CreateCompatibleDC(this.handle);
        long l5 = OS.SelectObject(l4, l2);
        long l6 = this.handle;
        int n12 = n5;
        int n13 = n6;
        long l7 = 0L;
        long l8 = 0L;
        long l9 = 0L;
        int n14 = 0;
        int n15 = 0;
        if (bl2) {
            l7 = OS.CreateCompatibleDC(this.handle);
            l8 = OS.CreateCompatibleBitmap(this.handle, n7, n8);
            l9 = OS.SelectObject(l7, l8);
            OS.BitBlt(l7, 0, 0, n7, n8, this.handle, n5, n6, 0xCC0020);
            l6 = l7;
            n12 = 0;
            n13 = 0;
        } else {
            n14 = OS.SetBkColor(this.handle, 0xFFFFFF);
            n15 = OS.SetTextColor(this.handle, 0);
        }
        if (!(bl || n3 == n7 && n4 == n8)) {
            int n16 = OS.SetStretchBltMode(this.handle, 3);
            OS.StretchBlt(l6, n12, n13, n7, n8, l4, n, n11, n3, n4, 0x660046);
            OS.SelectObject(l4, l3);
            OS.StretchBlt(l6, n12, n13, n7, n8, l4, n, n2, n3, n4, 8913094);
            OS.SelectObject(l4, l2);
            OS.StretchBlt(l6, n12, n13, n7, n8, l4, n, n11, n3, n4, 0x660046);
            OS.SetStretchBltMode(this.handle, n16);
        } else {
            OS.BitBlt(l6, n12, n13, n7, n8, l4, n, n11, 0x660046);
            OS.SetTextColor(l6, 0);
            OS.SelectObject(l4, l3);
            OS.BitBlt(l6, n12, n13, n7, n8, l4, n, n2, 8913094);
            OS.SelectObject(l4, l2);
            OS.BitBlt(l6, n12, n13, n7, n8, l4, n, n11, 0x660046);
        }
        if (bl2) {
            OS.BitBlt(this.handle, n5, n6, n7, n8, l7, 0, 0, 0xCC0020);
            OS.SelectObject(l7, l9);
            OS.DeleteDC(l7);
            OS.DeleteObject(l8);
        } else {
            OS.SetBkColor(this.handle, n14);
            OS.SetTextColor(this.handle, n15);
        }
        OS.SelectObject(l4, l5);
        OS.DeleteDC(l4);
    }

    void drawBitmapTransparent(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl, BITMAP bITMAP, int n9, int n10) {
        int n11;
        boolean bl2 = bITMAP.bmBits != 0L;
        long l2 = image.handle;
        long l3 = OS.CreateCompatibleDC(this.handle);
        long l4 = OS.SelectObject(l3, l2);
        byte[] byArray = null;
        int n12 = image.transparentColor;
        if (n12 == -1) {
            int n13;
            n11 = 0;
            int n14 = 0;
            int n15 = 0;
            boolean bl3 = false;
            if (bITMAP.bmBitsPixel <= 8) {
                if (bl2) {
                    n13 = 1 << bITMAP.bmBitsPixel;
                    byte[] byArray2 = new byte[n13 * 4];
                    OS.GetDIBColorTable(l3, 0, n13, byArray2);
                    int n16 = image.transparentPixel * 4;
                    for (int i = 0; i < byArray2.length; i += 4) {
                        if (i == n16 || byArray2[n16] != byArray2[i] || byArray2[n16 + 1] != byArray2[i + 1] || byArray2[n16 + 2] != byArray2[i + 2]) continue;
                        bl3 = true;
                        break;
                    }
                    if (bl3) {
                        byte[] byArray3 = new byte[byArray2.length];
                        n11 = 255;
                        n15 = 255;
                        n14 = 255;
                        byArray3[n16] = (byte)n11;
                        byArray3[n16 + 1] = (byte)n14;
                        byArray3[n16 + 2] = (byte)n15;
                        OS.SetDIBColorTable(l3, 0, n13, byArray3);
                        byArray = byArray2;
                    } else {
                        n11 = byArray2[n16] & 0xFF;
                        n14 = byArray2[n16 + 1] & 0xFF;
                        n15 = byArray2[n16 + 2] & 0xFF;
                    }
                } else {
                    n13 = 1 << bITMAP.bmBitsPixel;
                    BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
                    bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
                    bITMAPINFOHEADER.biPlanes = bITMAP.bmPlanes;
                    bITMAPINFOHEADER.biBitCount = bITMAP.bmBitsPixel;
                    byte[] byArray4 = new byte[BITMAPINFOHEADER.sizeof + n13 * 4];
                    OS.MoveMemory(byArray4, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
                    OS.GetDIBits(l3, image.handle, 0, 0, null, byArray4, 0);
                    int n17 = BITMAPINFOHEADER.sizeof + 4 * image.transparentPixel;
                    n15 = byArray4[n17 + 2] & 0xFF;
                    n14 = byArray4[n17 + 1] & 0xFF;
                    n11 = byArray4[n17] & 0xFF;
                }
            } else {
                n13 = image.transparentPixel;
                switch (bITMAP.bmBitsPixel) {
                    case 16: {
                        n11 = (n13 & 0x1F) << 3;
                        n14 = (n13 & 0x3E0) >> 2;
                        n15 = (n13 & 0x7C00) >> 7;
                        break;
                    }
                    case 24: {
                        n11 = (n13 & 0xFF0000) >> 16;
                        n14 = (n13 & 0xFF00) >> 8;
                        n15 = n13 & 0xFF;
                        break;
                    }
                    case 32: {
                        n11 = (n13 & 0xFF000000) >>> 24;
                        n14 = (n13 & 0xFF0000) >> 16;
                        n15 = (n13 & 0xFF00) >> 8;
                    }
                }
            }
            n12 = n11 << 16 | n14 << 8 | n15;
            if (!bl3) {
                image.transparentColor = n12;
            }
        }
        if (byArray == null) {
            n11 = OS.SetStretchBltMode(this.handle, 3);
            OS.TransparentBlt(this.handle, n5, n6, n7, n8, l3, n, n2, n3, n4, n12);
            OS.SetStretchBltMode(this.handle, n11);
        } else {
            long l5 = OS.CreateCompatibleDC(this.handle);
            long l6 = OS.CreateBitmap(n9, n10, 1, 1, null);
            long l7 = OS.SelectObject(l5, l6);
            OS.SetBkColor(l3, n12);
            OS.BitBlt(l5, 0, 0, n9, n10, l3, 0, 0, 0xCC0020);
            if (byArray != null) {
                OS.SetDIBColorTable(l3, 0, 1 << bITMAP.bmBitsPixel, byArray);
            }
            if (OS.GetDeviceCaps(this.handle, 2) == 2) {
                this.drawBitmapTransparentByClipping(l3, l5, n, n2, n3, n4, n5, n6, n7, n8, bl, n9, n10);
            } else {
                long l8 = OS.CreateCompatibleDC(this.handle);
                long l9 = OS.CreateCompatibleBitmap(this.handle, n7, n8);
                long l10 = OS.SelectObject(l8, l9);
                OS.BitBlt(l8, 0, 0, n7, n8, this.handle, n5, n6, 0xCC0020);
                if (!(bl || n3 == n7 && n4 == n8)) {
                    OS.SetStretchBltMode(l8, 3);
                    OS.StretchBlt(l8, 0, 0, n7, n8, l3, n, n2, n3, n4, 0x660046);
                    OS.StretchBlt(l8, 0, 0, n7, n8, l5, n, n2, n3, n4, 8913094);
                    OS.StretchBlt(l8, 0, 0, n7, n8, l3, n, n2, n3, n4, 0x660046);
                } else {
                    OS.BitBlt(l8, 0, 0, n7, n8, l3, n, n2, 0x660046);
                    OS.BitBlt(l8, 0, 0, n7, n8, l5, n, n2, 8913094);
                    OS.BitBlt(l8, 0, 0, n7, n8, l3, n, n2, 0x660046);
                }
                OS.BitBlt(this.handle, n5, n6, n7, n8, l8, 0, 0, 0xCC0020);
                OS.SelectObject(l8, l10);
                OS.DeleteDC(l8);
                OS.DeleteObject(l9);
            }
            OS.SelectObject(l5, l7);
            OS.DeleteDC(l5);
            OS.DeleteObject(l6);
        }
        OS.SelectObject(l3, l4);
        if (l2 != image.handle) {
            OS.DeleteObject(l2);
        }
        OS.DeleteDC(l3);
    }

    void drawBitmapColor(Image image, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        int n9;
        long l2 = OS.CreateCompatibleDC(this.handle);
        long l3 = OS.SelectObject(l2, image.handle);
        int n10 = n9 = OS.GetROP2(this.handle) == 7 ? 0x660046 : 0xCC0020;
        if (!(bl || n3 == n7 && n4 == n8)) {
            int n11 = OS.SetStretchBltMode(this.handle, 3);
            OS.StretchBlt(this.handle, n5, n6, n7, n8, l2, n, n2, n3, n4, n9);
            OS.SetStretchBltMode(this.handle, n11);
        } else {
            OS.BitBlt(this.handle, n5, n6, n7, n8, l2, n, n2, n9);
        }
        OS.SelectObject(l2, l3);
        OS.DeleteDC(l2);
    }

    public void drawLine(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.drawLineInPixels(n, n2, n3, n4);
    }

    void drawLineInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(22777);
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
            Gdip.Graphics_DrawLine(l2, this.data.gdipPen, n, n2, n3, n4);
            Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            --n;
            --n3;
        }
        OS.MoveToEx(this.handle, n, n2, 0L);
        OS.LineTo(this.handle, n3, n4);
        if (this.data.lineWidth <= 1.0f) {
            OS.SetPixel(this.handle, n3, n4, this.data.foreground);
        }
    }

    public void drawOval(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.drawOvalInPixels(n, n2, n3, n4);
    }

    void drawOvalInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(22777);
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
            Gdip.Graphics_DrawEllipse(l2, this.data.gdipPen, n, n2, n3, n4);
            Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            --n;
        }
        OS.Ellipse(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1);
    }

    public void drawPath(Path path) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (path == null) {
            SWT.error(4);
        }
        if (path.handle == 0L) {
            SWT.error(5);
        }
        this.initGdip();
        this.checkGC(22777);
        long l2 = this.data.gdipGraphics;
        Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
        Gdip.Graphics_DrawPath(l2, this.data.gdipPen, path.handle);
        Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
    }

    public void drawPoint(int n, int n2) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawPointInPixels(n, n2);
    }

    void drawPointInPixels(int n, int n2) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics != 0L) {
            this.checkGC(22777);
            Gdip.Graphics_FillRectangle(this.data.gdipGraphics, this.getFgBrush(), n, n2, 1, 1);
            return;
        }
        OS.SetPixel(this.handle, n, n2, this.data.foreground);
    }

    public void drawPolygon(int[] nArray) {
        if (nArray == null) {
            SWT.error(4);
        }
        this.drawPolygonInPixels(DPIUtil.autoScaleUp(this.drawable, nArray));
    }

    void drawPolygonInPixels(int[] nArray) {
        int n;
        int n2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(22777);
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
            Gdip.Graphics_DrawPolygon(l2, this.data.gdipPen, nArray, nArray.length / 2);
            Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            for (n2 = 0; n2 < nArray.length; n2 += 2) {
                int n3 = n = n2;
                nArray[n3] = nArray[n3] - 1;
            }
        }
        OS.Polygon(this.handle, nArray, nArray.length / 2);
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            for (n2 = 0; n2 < nArray.length; n2 += 2) {
                int n4 = n = n2;
                nArray[n4] = nArray[n4] + 1;
            }
        }
    }

    public void drawPolyline(int[] nArray) {
        this.drawPolylineInPixels(DPIUtil.autoScaleUp(this.drawable, nArray));
    }

    void drawPolylineInPixels(int[] nArray) {
        int n;
        int n2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (nArray == null) {
            SWT.error(4);
        }
        this.checkGC(22777);
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
            Gdip.Graphics_DrawLines(l2, this.data.gdipPen, nArray, nArray.length / 2);
            Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            for (n2 = 0; n2 < nArray.length; n2 += 2) {
                int n3 = n = n2;
                nArray[n3] = nArray[n3] - 1;
            }
        }
        OS.Polyline(this.handle, nArray, nArray.length / 2);
        n2 = nArray.length;
        if (n2 >= 2 && this.data.lineWidth <= 1.0f) {
            OS.SetPixel(this.handle, nArray[n2 - 2], nArray[n2 - 1], this.data.foreground);
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            for (n = 0; n < nArray.length; n += 2) {
                int n4;
                int n5 = n4 = n;
                nArray[n5] = nArray[n5] + 1;
            }
        }
    }

    public void drawRectangle(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.drawRectangleInPixels(n, n2, n3, n4);
    }

    void drawRectangleInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(22777);
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            if (n3 < 0) {
                n += n3;
                n3 = -n3;
            }
            if (n4 < 0) {
                n2 += n4;
                n4 = -n4;
            }
            Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
            Gdip.Graphics_DrawRectangle(l2, this.data.gdipPen, n, n2, n3, n4);
            Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0) {
            if (this.data.lineWidth > 1.0f) {
                if (this.data.lineWidth % 2.0f == 1.0f) {
                    ++n;
                }
            } else if (this.data.hPen != 0L && OS.GetObject(this.data.hPen, 0, 0L) != OS.LOGPEN_sizeof()) {
                ++n;
            }
        }
        OS.Rectangle(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1);
    }

    public void drawRectangle(Rectangle rectangle) {
        if (rectangle == null) {
            SWT.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(this.drawable, rectangle);
        this.drawRectangleInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void drawRoundRectangle(int n, int n2, int n3, int n4, int n5, int n6) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        n5 = DPIUtil.autoScaleUp(this.drawable, n5);
        n6 = DPIUtil.autoScaleUp(this.drawable, n6);
        this.drawRoundRectangleInPixels(n, n2, n3, n4, n5, n6);
    }

    void drawRoundRectangleInPixels(int n, int n2, int n3, int n4, int n5, int n6) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(22777);
        if (this.data.gdipGraphics != 0L) {
            this.drawRoundRectangleGdip(this.data.gdipGraphics, this.data.gdipPen, n, n2, n3, n4, n5, n6);
            return;
        }
        if ((this.data.style & 0x8000000) != 0 && this.data.lineWidth != 0.0f && this.data.lineWidth % 2.0f == 0.0f) {
            --n;
        }
        OS.RoundRect(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1, n5, n6);
    }

    void drawRoundRectangleGdip(long l2, long l3, int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n;
        int n8 = n2;
        int n9 = n3;
        int n10 = n4;
        int n11 = n5;
        int n12 = n6;
        if (n9 < 0) {
            n9 = 0 - n9;
            n7 -= n9;
        }
        if (n10 < 0) {
            n10 = 0 - n10;
            n8 -= n10;
        }
        if (n11 < 0) {
            n11 = 0 - n11;
        }
        if (n12 < 0) {
            n12 = 0 - n12;
        }
        Gdip.Graphics_TranslateTransform(l2, this.data.gdipXOffset, this.data.gdipYOffset, 0);
        if (n11 == 0 || n12 == 0) {
            Gdip.Graphics_DrawRectangle(l2, this.data.gdipPen, n, n2, n3, n4);
        } else {
            long l4 = Gdip.GraphicsPath_new(0);
            if (l4 == 0L) {
                SWT.error(2);
            }
            if (n9 > n11) {
                if (n10 > n12) {
                    Gdip.GraphicsPath_AddArc(l4, n7 + n9 - n11, n8, n11, n12, 0.0f, -90.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7, n8, n11, n12, -90.0f, -90.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7, n8 + n10 - n12, n11, n12, -180.0f, -90.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7 + n9 - n11, n8 + n10 - n12, n11, n12, -270.0f, -90.0f);
                } else {
                    Gdip.GraphicsPath_AddArc(l4, n7 + n9 - n11, n8, n11, n10, -270.0f, -180.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7, n8, n11, n10, -90.0f, -180.0f);
                }
            } else if (n10 > n12) {
                Gdip.GraphicsPath_AddArc(l4, n7, n8, n9, n12, 0.0f, -180.0f);
                Gdip.GraphicsPath_AddArc(l4, n7, n8 + n10 - n12, n9, n12, -180.0f, -180.0f);
            } else {
                Gdip.GraphicsPath_AddArc(l4, n7, n8, n9, n10, 0.0f, 360.0f);
            }
            Gdip.GraphicsPath_CloseFigure(l4);
            Gdip.Graphics_DrawPath(l2, l3, l4);
            Gdip.GraphicsPath_delete(l4);
        }
        Gdip.Graphics_TranslateTransform(l2, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
    }

    public void drawString(String string, int n, int n2) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawStringInPixels(string, n, n2, false);
    }

    public void drawString(String string, int n, int n2, boolean bl) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawStringInPixels(string, n, n2, bl);
    }

    void drawStringInPixels(String string, int n, int n2, boolean bl) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (string == null) {
            SWT.error(4);
        }
        if (string.isEmpty()) {
            return;
        }
        char[] cArray = string.toCharArray();
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            this.checkGC(5 | (bl ? 0 : 2));
            this.drawText(l2, string, n, n2, bl ? 1 : 0, null);
            return;
        }
        this.checkGC(772);
        int n3 = OS.SetBkMode(this.handle, bl ? 1 : 2);
        RECT rECT = null;
        SIZE sIZE = null;
        int n4 = 0;
        if ((this.data.style & 0x8000000) != 0) {
            if (!bl) {
                sIZE = new SIZE();
                OS.GetTextExtentPoint32(this.handle, cArray, cArray.length, sIZE);
                rECT = new RECT();
                rECT.left = n;
                rECT.right = n + sIZE.cx;
                rECT.top = n2;
                rECT.bottom = n2 + sIZE.cy;
                n4 = 4;
            }
            --n;
        }
        if (OS.GetROP2(this.handle) != 7) {
            OS.ExtTextOut(this.handle, n, n2, n4, rECT, cArray, cArray.length, null);
        } else {
            int n5 = OS.GetTextColor(this.handle);
            if (bl) {
                int n6;
                int n7;
                long l3;
                if (sIZE == null) {
                    sIZE = new SIZE();
                    OS.GetTextExtentPoint32(this.handle, cArray, cArray.length, sIZE);
                }
                if ((l3 = OS.CreateCompatibleBitmap(this.handle, n7 = sIZE.cx, n6 = sIZE.cy)) == 0L) {
                    SWT.error(2);
                }
                long l4 = OS.CreateCompatibleDC(this.handle);
                long l5 = OS.SelectObject(l4, l3);
                OS.PatBlt(l4, 0, 0, n7, n6, 66);
                OS.SetBkMode(l4, 1);
                OS.SetTextColor(l4, n5);
                OS.SelectObject(l4, OS.GetCurrentObject(this.handle, 6));
                OS.ExtTextOut(l4, 0, 0, 0, null, cArray, cArray.length, null);
                OS.BitBlt(this.handle, n, n2, n7, n6, l4, 0, 0, 0x660046);
                OS.SelectObject(l4, l5);
                OS.DeleteDC(l4);
                OS.DeleteObject(l3);
            } else {
                int n8 = OS.GetBkColor(this.handle);
                OS.SetTextColor(this.handle, n5 ^ n8);
                OS.ExtTextOut(this.handle, n, n2, n4, rECT, cArray, cArray.length, null);
                OS.SetTextColor(this.handle, n5);
            }
        }
        OS.SetBkMode(this.handle, n3);
    }

    public void drawText(String string, int n, int n2) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawTextInPixels(string, n, n2);
    }

    void drawTextInPixels(String string, int n, int n2) {
        this.drawTextInPixels(string, n, n2, 6);
    }

    public void drawText(String string, int n, int n2, boolean bl) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawTextInPixels(string, n, n2, bl);
    }

    void drawTextInPixels(String string, int n, int n2, boolean bl) {
        int n3 = 6;
        if (bl) {
            n3 |= 1;
        }
        this.drawTextInPixels(string, n, n2, n3);
    }

    public void drawText(String string, int n, int n2, int n3) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        this.drawTextInPixels(string, n, n2, n3);
    }

    void drawTextInPixels(String string, int n, int n2, int n3) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (string == null) {
            SWT.error(4);
        }
        if (string.isEmpty()) {
            return;
        }
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            this.checkGC(5 | ((n3 & 1) != 0 ? 0 : 2));
            this.drawText(l2, string, n, n2, n3, null);
            return;
        }
        char[] cArray = string.toCharArray();
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, 0x6FFFFFF, 0x6FFFFFF);
        int n4 = 0;
        if ((n3 & 2) == 0) {
            n4 |= 0x20;
        }
        if ((n3 & 4) != 0) {
            n4 |= 0x40;
        }
        if ((n3 & 8) == 0) {
            n4 |= 0x800;
        }
        if ((n3 & 8) != 0 && (this.data.uiState & 2) != 0) {
            n4 |= 0x100000;
        }
        this.checkGC(772);
        int n5 = OS.SetBkMode(this.handle, (n3 & 1) != 0 ? 1 : 2);
        if (OS.GetROP2(this.handle) != 7) {
            OS.DrawText(this.handle, cArray, cArray.length, rECT, n4);
        } else {
            int n6 = OS.GetTextColor(this.handle);
            if ((n3 & 1) != 0) {
                OS.DrawText(this.handle, cArray, cArray.length, rECT, n4 | 0x400);
                int n7 = rECT.right - rECT.left;
                int n8 = rECT.bottom - rECT.top;
                long l3 = OS.CreateCompatibleBitmap(this.handle, n7, n8);
                if (l3 == 0L) {
                    SWT.error(2);
                }
                long l4 = OS.CreateCompatibleDC(this.handle);
                long l5 = OS.SelectObject(l4, l3);
                OS.PatBlt(l4, 0, 0, n7, n8, 66);
                OS.SetBkMode(l4, 1);
                OS.SetTextColor(l4, n6);
                OS.SelectObject(l4, OS.GetCurrentObject(this.handle, 6));
                OS.SetRect(rECT, 0, 0, Short.MAX_VALUE, Short.MAX_VALUE);
                OS.DrawText(l4, cArray, cArray.length, rECT, n4);
                OS.BitBlt(this.handle, n, n2, n7, n8, l4, 0, 0, 0x660046);
                OS.SelectObject(l4, l5);
                OS.DeleteDC(l4);
                OS.DeleteObject(l3);
            } else {
                int n9 = OS.GetBkColor(this.handle);
                OS.SetTextColor(this.handle, n6 ^ n9);
                OS.DrawText(this.handle, cArray, cArray.length, rECT, n4);
                OS.SetTextColor(this.handle, n6);
            }
        }
        OS.SetBkMode(this.handle, n5);
    }

    boolean useGDIP(long l2, char[] cArray) {
        short[] sArray = new short[cArray.length];
        OS.GetGlyphIndices(l2, cArray, cArray.length, sArray, 1);
        block3: for (int i = 0; i < sArray.length; ++i) {
            if (sArray[i] != -1) continue;
            switch (cArray[i]) {
                case '\t': 
                case '\n': 
                case '\r': {
                    continue block3;
                }
                default: {
                    return true;
                }
            }
        }
        return false;
    }

    void drawText(long l2, String string, int n, int n2, int n3, Point point) {
        int n4 = string.length();
        char[] cArray = string.toCharArray();
        long l3 = Gdip.Graphics_GetHDC(l2);
        long l4 = this.data.hGDIFont;
        if (l4 == 0L && this.data.font != null) {
            l4 = this.data.font.handle;
        }
        long l5 = 0L;
        if (l4 != 0L) {
            l5 = OS.SelectObject(l3, l4);
        }
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l3, tEXTMETRIC);
        boolean bl = this.useGDIP(l3, cArray);
        if (l4 != 0L) {
            OS.SelectObject(l3, l5);
        }
        Gdip.Graphics_ReleaseHDC(l2, l3);
        if (bl) {
            this.drawTextGDIP(l2, string, n, n2, n3, point == null, point);
            return;
        }
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = n;
        int n9 = n2;
        int n10 = 0;
        int n11 = -1;
        if ((n3 & 0xE) != 0) {
            int n12 = tEXTMETRIC.tmAveCharWidth * 8;
            block5: while (n5 < n4) {
                char c;
                char[] cArray2 = cArray;
                int n13 = n7++;
                cArray2[n13] = c = cArray[n5++];
                char c2 = c;
                switch (c2) {
                    case '\t': {
                        if ((n3 & 4) == 0) continue block5;
                        int n14 = n7 - n6 - 1;
                        RectF rectF = this.drawText(l2, cArray, n6, n14, n8, n9, n3, n11, tEXTMETRIC, point == null);
                        n8 += (int)Math.ceil(rectF.Width);
                        n8 = n + ((n8 - n) / n12 + 1) * n12;
                        n11 = -1;
                        n6 = n7;
                        continue block5;
                    }
                    case '&': {
                        if ((n3 & 8) == 0) continue block5;
                        if (n5 == n4) {
                            --n7;
                            continue block5;
                        }
                        if (cArray[n5] == '&') {
                            ++n5;
                            continue block5;
                        }
                        n11 = --n7 - n6;
                        continue block5;
                    }
                    case '\n': 
                    case '\r': {
                        if ((n3 & 2) == 0) continue block5;
                        int n14 = n7 - n6 - 1;
                        if (c2 == '\r' && n7 != n4 && cArray[n7] == '\n') {
                            ++n7;
                            ++n5;
                        }
                        RectF rectF = this.drawText(l2, cArray, n6, n14, n8, n9, n3, n11, tEXTMETRIC, point == null);
                        n9 += (int)Math.ceil(rectF.Height);
                        n10 = Math.max(n10, n8 + (int)Math.ceil(rectF.Width));
                        n8 = n;
                        n11 = -1;
                        n6 = n7;
                        continue block5;
                    }
                }
            }
            n4 = n7;
        }
        RectF rectF = this.drawText(l2, cArray, n6, n4 - n6, n8, n9, n3, n11, tEXTMETRIC, point == null);
        if (point != null) {
            point.x = n10 = Math.max(n10, n8 + (int)Math.ceil(rectF.Width));
            point.y = n9 += (int)Math.ceil(rectF.Height);
        }
    }

    RectF drawText(long l2, char[] cArray, int n, int n2, int n3, int n4, int n5, int n6, TEXTMETRIC tEXTMETRIC, boolean bl) {
        long l3;
        long l4;
        boolean bl2;
        boolean bl3 = bl && n6 != -1 && (this.data.uiState & 2) == 0;
        boolean bl4 = bl2 = !bl || bl3 || (n5 & 1) == 0 || (this.data.style & 0x8000000) != 0 || (n5 & 2) != 0;
        if (n2 <= 0) {
            RectF rectF = null;
            if (bl2) {
                rectF = new RectF();
                rectF.Height = tEXTMETRIC.tmHeight;
            }
            return rectF;
        }
        int n7 = n2 * 3 / 2 + 16;
        GCP_RESULTS gCP_RESULTS = new GCP_RESULTS();
        gCP_RESULTS.lStructSize = GCP_RESULTS.sizeof;
        gCP_RESULTS.nGlyphs = n7;
        long l5 = OS.GetProcessHeap();
        GCP_RESULTS gCP_RESULTS2 = gCP_RESULTS;
        gCP_RESULTS2.lpDx = l4 = OS.HeapAlloc(l5, 8, n7 * 4);
        long l6 = l4;
        GCP_RESULTS gCP_RESULTS3 = gCP_RESULTS;
        gCP_RESULTS3.lpGlyphs = l3 = OS.HeapAlloc(l5, 8, n7 * 2);
        long l7 = l3;
        long l8 = 0L;
        int n8 = 50;
        if (bl3) {
            long l9;
            GCP_RESULTS gCP_RESULTS4 = gCP_RESULTS;
            gCP_RESULTS4.lpOrder = l9 = OS.HeapAlloc(l5, 8, n7 * 4);
            l8 = l9;
        }
        long l10 = Gdip.Graphics_GetHDC(l2);
        long l11 = this.data.hGDIFont;
        if (l11 == 0L && this.data.font != null) {
            l11 = this.data.font.handle;
        }
        long l12 = 0L;
        if (l11 != 0L) {
            l12 = OS.SelectObject(l10, l11);
        }
        if (n != 0) {
            char[] cArray2 = new char[n2];
            System.arraycopy(cArray, n, cArray2, 0, n2);
            cArray = cArray2;
        }
        if ((this.data.style & 0x8000000) != 0) {
            OS.SetLayout(l10, OS.GetLayout(l10) | 1);
        }
        OS.GetCharacterPlacement(l10, cArray, n2, 0, gCP_RESULTS, 50);
        if ((this.data.style & 0x8000000) != 0) {
            OS.SetLayout(l10, OS.GetLayout(l10) & 0xFFFFFFFE);
        }
        if (l11 != 0L) {
            OS.SelectObject(l10, l12);
        }
        Gdip.Graphics_ReleaseHDC(l2, l10);
        n7 = gCP_RESULTS.nGlyphs;
        int n9 = n3;
        int n10 = n4 + tEXTMETRIC.tmAscent;
        int[] nArray = new int[n7];
        OS.MoveMemory(nArray, gCP_RESULTS.lpDx, n7 * 4);
        float[] fArray = new float[nArray.length * 2];
        int n11 = 0;
        for (int i = 0; i < nArray.length; ++i) {
            fArray[n11++] = n9;
            fArray[n11++] = n10;
            n9 += nArray[i];
        }
        RectF rectF = null;
        if (bl2) {
            rectF = new RectF();
            Gdip.Graphics_MeasureDriverString(l2, l7, n7, this.data.gdipFont, fArray, 0, 0L, rectF);
        }
        if (bl) {
            long l13;
            if ((n5 & 1) == 0) {
                Gdip.Graphics_FillRectangle(l2, this.data.gdipBrush, n3, n4, (int)Math.ceil(rectF.Width), (int)Math.ceil(rectF.Height));
            }
            int n12 = 0;
            long l14 = this.getFgBrush();
            if ((this.data.style & 0x8000000) != 0) {
                switch (Gdip.Brush_GetType(l14)) {
                    case 4: {
                        Gdip.LinearGradientBrush_ScaleTransform(l14, -1.0f, 1.0f, 0);
                        Gdip.LinearGradientBrush_TranslateTransform(l14, (float)(-2 * n3) - rectF.Width, 0.0f, 0);
                        break;
                    }
                    case 2: {
                        Gdip.TextureBrush_ScaleTransform(l14, -1.0f, 1.0f, 0);
                        Gdip.TextureBrush_TranslateTransform(l14, (float)(-2 * n3) - rectF.Width, 0.0f, 0);
                    }
                }
                n12 = Gdip.Graphics_Save(l2);
                Gdip.Graphics_ScaleTransform(l2, -1.0f, 1.0f, 0);
                Gdip.Graphics_TranslateTransform(l2, (float)(-2 * n3) - rectF.Width, 0.0f, 0);
            }
            Gdip.Graphics_DrawDriverString(l2, l7, gCP_RESULTS.nGlyphs, this.data.gdipFont, l14, fArray, 0, 0L);
            if ((this.data.style & 0x8000000) != 0) {
                switch (Gdip.Brush_GetType(l14)) {
                    case 4: {
                        Gdip.LinearGradientBrush_ResetTransform(l14);
                        break;
                    }
                    case 2: {
                        Gdip.TextureBrush_ResetTransform(l14);
                    }
                }
                Gdip.Graphics_Restore(l2, n12);
            }
            if (bl3 && (l13 = Gdip.Pen_new(l14, 1.0f)) != 0L) {
                int n13;
                int n14;
                int[] nArray2 = new int[]{0};
                OS.MoveMemory(nArray2, gCP_RESULTS.lpOrder + (long)(n6 * 4), 4);
                if ((this.data.style & 0x8000000) != 0) {
                    n14 = (int)Math.ceil(rectF.Width) - (int)fArray[nArray2[0] * 2] + 2 * n3;
                    n13 = n14 - nArray[nArray2[0]];
                } else {
                    n14 = (int)fArray[nArray2[0] * 2];
                    n13 = n14 + nArray[nArray2[0]];
                }
                int n15 = n4 + tEXTMETRIC.tmAscent + 2;
                int n16 = Gdip.Graphics_GetSmoothingMode(l2);
                Gdip.Graphics_SetSmoothingMode(l2, 3);
                Gdip.Graphics_DrawLine(l2, l13, n14, n15, n13, n15);
                Gdip.Graphics_SetSmoothingMode(l2, n16);
                Gdip.Pen_delete(l13);
            }
        }
        if (l8 != 0L) {
            OS.HeapFree(l5, 0, l8);
        }
        OS.HeapFree(l5, 0, l7);
        OS.HeapFree(l5, 0, l6);
        return rectF;
    }

    void drawTextGDIP(long l2, String string, int n, int n2, int n3, boolean bl, Point point) {
        int n4;
        float[] fArray;
        char[] cArray;
        boolean bl2 = !bl || (n3 & 1) == 0;
        int n5 = string.length();
        if (n5 != 0) {
            cArray = string.toCharArray();
        } else {
            if (bl) {
                return;
            }
            cArray = new char[]{' '};
        }
        PointF pointF = new PointF();
        long l3 = Gdip.StringFormat_Clone(Gdip.StringFormat_GenericTypographic());
        int n6 = Gdip.StringFormat_GetFormatFlags(l3) | 0x800;
        if ((this.data.style & 0x8000000) != 0) {
            n6 |= 1;
        }
        Gdip.StringFormat_SetFormatFlags(l3, n6);
        if ((n3 & 4) != 0) {
            float[] fArray2 = new float[1];
            fArray = fArray2;
            fArray2[0] = this.measureSpace(this.data.gdipFont, l3) * 8.0f;
        } else {
            float[] fArray3 = new float[1];
            fArray = fArray3;
            fArray3[0] = 0.0f;
        }
        float[] fArray4 = fArray;
        Gdip.StringFormat_SetTabStops(l3, 0.0f, fArray4.length, fArray4);
        int n7 = n4 = (n3 & 8) != 0 ? 1 : 0;
        if ((n3 & 8) != 0 && (this.data.uiState & 2) != 0) {
            n4 = 2;
        }
        Gdip.StringFormat_SetHotkeyPrefix(l3, n4);
        RectF rectF = null;
        if (bl2) {
            rectF = new RectF();
            Gdip.Graphics_MeasureString(l2, cArray, cArray.length, this.data.gdipFont, pointF, l3, rectF);
        }
        if (bl) {
            if ((n3 & 1) == 0) {
                Gdip.Graphics_FillRectangle(l2, this.data.gdipBrush, n, n2, (int)Math.ceil(rectF.Width), (int)Math.ceil(rectF.Height));
            }
            int n8 = 0;
            long l4 = this.getFgBrush();
            if ((this.data.style & 0x8000000) != 0) {
                switch (Gdip.Brush_GetType(l4)) {
                    case 4: {
                        Gdip.LinearGradientBrush_ScaleTransform(l4, -1.0f, 1.0f, 0);
                        Gdip.LinearGradientBrush_TranslateTransform(l4, -2 * n, 0.0f, 0);
                        break;
                    }
                    case 2: {
                        Gdip.TextureBrush_ScaleTransform(l4, -1.0f, 1.0f, 0);
                        Gdip.TextureBrush_TranslateTransform(l4, -2 * n, 0.0f, 0);
                    }
                }
                n8 = Gdip.Graphics_Save(l2);
                Gdip.Graphics_ScaleTransform(l2, -1.0f, 1.0f, 0);
                Gdip.Graphics_TranslateTransform(l2, -2 * n, 0.0f, 0);
            }
            pointF.X = n;
            pointF.Y = n2;
            Gdip.Graphics_DrawString(l2, cArray, cArray.length, this.data.gdipFont, pointF, l3, l4);
            if ((this.data.style & 0x8000000) != 0) {
                switch (Gdip.Brush_GetType(l4)) {
                    case 4: {
                        Gdip.LinearGradientBrush_ResetTransform(l4);
                        break;
                    }
                    case 2: {
                        Gdip.TextureBrush_ResetTransform(l4);
                    }
                }
                Gdip.Graphics_Restore(l2, n8);
            }
        }
        Gdip.StringFormat_delete(l3);
        if (n5 == 0) {
            rectF.Width = 0.0f;
        }
        if (point != null) {
            point.x = (int)Math.ceil(rectF.Width);
            point.y = (int)Math.ceil(rectF.Height);
        }
    }

    public boolean equals(Object object) {
        return object == this || object instanceof GC && this.handle == ((GC)object).handle;
    }

    public void fillArc(int n, int n2, int n3, int n4, int n5, int n6) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.fillArcInPixels(n, n2, n3, n4, n5, n6);
    }

    void fillArcInPixels(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int n9;
        int n10;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(9218);
        if (n3 < 0) {
            n += n3;
            n3 = -n3;
        }
        if (n4 < 0) {
            n2 += n4;
            n4 = -n4;
        }
        if (n3 == 0 || n4 == 0 || n6 == 0) {
            return;
        }
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            if (n3 == n4) {
                Gdip.Graphics_FillPie(l2, this.data.gdipBrush, n, n2, n3, n4, -n5, -n6);
            } else {
                int n11 = Gdip.Graphics_Save(l2);
                Gdip.Graphics_TranslateTransform(l2, n, n2, 0);
                Gdip.Graphics_ScaleTransform(l2, n3, n4, 0);
                Gdip.Graphics_FillPie(l2, this.data.gdipBrush, 0, 0, 1, 1, -n5, -n6);
                Gdip.Graphics_Restore(l2, n11);
            }
            return;
        }
        if ((this.data.style & 0x8000000) != 0) {
            --n;
        }
        if (n6 >= 360 || n6 <= -360) {
            n9 = n10 = n + n3;
            n7 = n8 = n2 + n4 / 2;
        } else {
            boolean bl = n6 < 0;
            n6 += n5;
            if (bl) {
                int n12 = n5;
                n5 = n6;
                n6 = n12;
            }
            n10 = GC.cos(n5, n3) + n + n3 / 2;
            n8 = -1 * GC.sin(n5, n4) + n2 + n4 / 2;
            n9 = GC.cos(n6, n3) + n + n3 / 2;
            n7 = -1 * GC.sin(n6, n4) + n2 + n4 / 2;
        }
        OS.Pie(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1, n10, n8, n9, n7);
    }

    public void fillGradientRectangle(int n, int n2, int n3, int n4, boolean bl) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.fillGradientRectangleInPixels(n, n2, n3, n4, bl);
    }

    void fillGradientRectangleInPixels(int n, int n2, int n3, int n4, boolean bl) {
        int n5;
        RGB rGB;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (n3 == 0 || n4 == 0) {
            return;
        }
        RGB rGB2 = this.getBackground().getRGB();
        RGB rGB3 = rGB = this.getForeground().getRGB();
        RGB rGB4 = rGB2;
        boolean bl2 = false;
        if (n3 < 0) {
            n += n3;
            n3 = -n3;
            if (!bl) {
                bl2 = true;
            }
        }
        if (n4 < 0) {
            n2 += n4;
            n4 = -n4;
            if (bl) {
                bl2 = true;
            }
        }
        if (bl2) {
            rGB = rGB2;
            rGB4 = rGB3;
        }
        if (rGB.equals(rGB4)) {
            this.fillRectangleInPixels(n, n2, n3, n4);
            return;
        }
        if (this.data.gdipGraphics != 0L) {
            this.initGdip();
            PointF pointF = new PointF();
            PointF pointF2 = new PointF();
            pointF.X = n;
            pointF.Y = n2;
            if (bl) {
                pointF2.X = pointF.X;
                pointF2.Y = pointF.Y + (float)n4;
            } else {
                pointF2.X = pointF.X + (float)n3;
                pointF2.Y = pointF.Y;
            }
            int n6 = this.data.alpha << 24 | (rGB.red & 0xFF) << 16 | (rGB.green & 0xFF) << 8 | rGB.blue & 0xFF;
            int n7 = this.data.alpha << 24 | (rGB4.red & 0xFF) << 16 | (rGB4.green & 0xFF) << 8 | rGB4.blue & 0xFF;
            long l2 = Gdip.LinearGradientBrush_new(pointF, pointF2, n6, n7);
            Gdip.Graphics_FillRectangle(this.data.gdipGraphics, l2, n, n2, n3, n4);
            Gdip.LinearGradientBrush_delete(l2);
            return;
        }
        if (OS.GetROP2(this.handle) != 7 && OS.GetDeviceCaps(this.handle, 2) != 2) {
            long l3 = OS.GetProcessHeap();
            long l4 = OS.HeapAlloc(l3, 8, GRADIENT_RECT.sizeof + TRIVERTEX.sizeof * 2);
            if (l4 == 0L) {
                SWT.error(2);
            }
            long l5 = l4 + (long)GRADIENT_RECT.sizeof;
            GRADIENT_RECT gRADIENT_RECT = new GRADIENT_RECT();
            gRADIENT_RECT.UpperLeft = 0;
            gRADIENT_RECT.LowerRight = 1;
            OS.MoveMemory(l4, gRADIENT_RECT, GRADIENT_RECT.sizeof);
            TRIVERTEX tRIVERTEX = new TRIVERTEX();
            tRIVERTEX.x = n;
            tRIVERTEX.y = n2;
            tRIVERTEX.Red = (short)(rGB.red << 8 | rGB.red);
            tRIVERTEX.Green = (short)(rGB.green << 8 | rGB.green);
            tRIVERTEX.Blue = (short)(rGB.blue << 8 | rGB.blue);
            tRIVERTEX.Alpha = (short)-1;
            OS.MoveMemory(l5, tRIVERTEX, TRIVERTEX.sizeof);
            tRIVERTEX.x = n + n3;
            tRIVERTEX.y = n2 + n4;
            tRIVERTEX.Red = (short)(rGB4.red << 8 | rGB4.red);
            tRIVERTEX.Green = (short)(rGB4.green << 8 | rGB4.green);
            tRIVERTEX.Blue = (short)(rGB4.blue << 8 | rGB4.blue);
            tRIVERTEX.Alpha = (short)-1;
            OS.MoveMemory(l5 + (long)TRIVERTEX.sizeof, tRIVERTEX, TRIVERTEX.sizeof);
            boolean bl3 = OS.GradientFill(this.handle, l5, 2, l4, 1, bl ? 1 : 0);
            OS.HeapFree(l3, 0, l4);
            if (bl3) {
                return;
            }
        }
        int n8 = (n5 = OS.GetDeviceCaps(this.handle, 12)) >= 24 ? 8 : (n5 >= 15 ? 5 : 0);
        ImageData.fillGradientRectangle(this, this.data.device, n, n2, n3, n4, bl, rGB, rGB4, n8, n8, n8);
    }

    public void fillOval(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.fillOvalInPixels(n, n2, n3, n4);
    }

    void fillOvalInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(9218);
        if (this.data.gdipGraphics != 0L) {
            Gdip.Graphics_FillEllipse(this.data.gdipGraphics, this.data.gdipBrush, n, n2, n3, n4);
            return;
        }
        if ((this.data.style & 0x8000000) != 0) {
            --n;
        }
        OS.Ellipse(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1);
    }

    public void fillPath(Path path) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (path == null) {
            SWT.error(4);
        }
        if (path.handle == 0L) {
            SWT.error(5);
        }
        this.initGdip();
        this.checkGC(9218);
        int n = OS.GetPolyFillMode(this.handle) == 2 ? 1 : 0;
        Gdip.GraphicsPath_SetFillMode(path.handle, n);
        Gdip.Graphics_FillPath(this.data.gdipGraphics, this.data.gdipBrush, path.handle);
    }

    public void fillPolygon(int[] nArray) {
        if (nArray == null) {
            SWT.error(4);
        }
        this.fillPolygonInPixels(DPIUtil.autoScaleUp(this.drawable, nArray));
    }

    void fillPolygonInPixels(int[] nArray) {
        int n;
        int n2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(9218);
        if (this.data.gdipGraphics != 0L) {
            int n3 = OS.GetPolyFillMode(this.handle) == 2 ? 1 : 0;
            float f = 0.5f;
            Gdip.Graphics_TranslateTransform(this.data.gdipGraphics, this.data.gdipXOffset + 0.5f, this.data.gdipYOffset + 0.5f, 0);
            Gdip.Graphics_FillPolygon(this.data.gdipGraphics, this.data.gdipBrush, nArray, nArray.length / 2, n3);
            Gdip.Graphics_TranslateTransform(this.data.gdipGraphics, -(this.data.gdipXOffset + 0.5f), -(this.data.gdipYOffset + 0.5f), 0);
            return;
        }
        if ((this.data.style & 0x8000000) != 0) {
            for (n2 = 0; n2 < nArray.length; n2 += 2) {
                int n4 = n = n2;
                nArray[n4] = nArray[n4] - 1;
            }
        }
        OS.Polygon(this.handle, nArray, nArray.length / 2);
        if ((this.data.style & 0x8000000) != 0) {
            for (n2 = 0; n2 < nArray.length; n2 += 2) {
                int n5 = n = n2;
                nArray[n5] = nArray[n5] + 1;
            }
        }
    }

    public void fillRectangle(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.fillRectangleInPixels(n, n2, n3, n4);
    }

    void fillRectangleInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(9218);
        if (this.data.gdipGraphics != 0L) {
            if (n3 < 0) {
                n += n3;
                n3 = -n3;
            }
            if (n4 < 0) {
                n2 += n4;
                n4 = -n4;
            }
            Gdip.Graphics_FillRectangle(this.data.gdipGraphics, this.data.gdipBrush, n, n2, n3, n4);
            return;
        }
        int n5 = OS.GetROP2(this.handle) == 7 ? 5898313 : 15728673;
        OS.PatBlt(this.handle, n, n2, n3, n4, n5);
    }

    public void fillRectangle(Rectangle rectangle) {
        if (rectangle == null) {
            SWT.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(this.drawable, rectangle);
        this.fillRectangleInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void fillRoundRectangle(int n, int n2, int n3, int n4, int n5, int n6) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        n5 = DPIUtil.autoScaleUp(this.drawable, n5);
        n6 = DPIUtil.autoScaleUp(this.drawable, n6);
        this.fillRoundRectangleInPixels(n, n2, n3, n4, n5, n6);
    }

    void fillRoundRectangleInPixels(int n, int n2, int n3, int n4, int n5, int n6) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(9218);
        if (this.data.gdipGraphics != 0L) {
            this.fillRoundRectangleGdip(this.data.gdipGraphics, this.data.gdipBrush, n, n2, n3, n4, n5, n6);
            return;
        }
        if ((this.data.style & 0x8000000) != 0) {
            --n;
        }
        OS.RoundRect(this.handle, n, n2, n + n3 + 1, n2 + n4 + 1, n5, n6);
    }

    void fillRoundRectangleGdip(long l2, long l3, int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n;
        int n8 = n2;
        int n9 = n3;
        int n10 = n4;
        int n11 = n5;
        int n12 = n6;
        if (n9 < 0) {
            n9 = 0 - n9;
            n7 -= n9;
        }
        if (n10 < 0) {
            n10 = 0 - n10;
            n8 -= n10;
        }
        if (n11 < 0) {
            n11 = 0 - n11;
        }
        if (n12 < 0) {
            n12 = 0 - n12;
        }
        if (n11 == 0 || n12 == 0) {
            Gdip.Graphics_FillRectangle(this.data.gdipGraphics, this.data.gdipBrush, n, n2, n3, n4);
        } else {
            long l4 = Gdip.GraphicsPath_new(0);
            if (l4 == 0L) {
                SWT.error(2);
            }
            if (n9 > n11) {
                if (n10 > n12) {
                    Gdip.GraphicsPath_AddArc(l4, n7 + n9 - n11, n8, n11, n12, 0.0f, -90.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7, n8, n11, n12, -90.0f, -90.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7, n8 + n10 - n12, n11, n12, -180.0f, -90.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7 + n9 - n11, n8 + n10 - n12, n11, n12, -270.0f, -90.0f);
                } else {
                    Gdip.GraphicsPath_AddArc(l4, n7 + n9 - n11, n8, n11, n10, -270.0f, -180.0f);
                    Gdip.GraphicsPath_AddArc(l4, n7, n8, n11, n10, -90.0f, -180.0f);
                }
            } else if (n10 > n12) {
                Gdip.GraphicsPath_AddArc(l4, n7, n8, n9, n12, 0.0f, -180.0f);
                Gdip.GraphicsPath_AddArc(l4, n7, n8 + n10 - n12, n9, n12, -180.0f, -180.0f);
            } else {
                Gdip.GraphicsPath_AddArc(l4, n7, n8, n9, n10, 0.0f, 360.0f);
            }
            Gdip.GraphicsPath_CloseFigure(l4);
            Gdip.Graphics_FillPath(l2, l3, l4);
            Gdip.GraphicsPath_delete(l4);
        }
    }

    void flush() {
        if (this.data.gdipGraphics != 0L) {
            Gdip.Graphics_Flush(this.data.gdipGraphics, 0);
            long l2 = Gdip.Graphics_GetHDC(this.data.gdipGraphics);
            Gdip.Graphics_ReleaseHDC(this.data.gdipGraphics, l2);
        }
    }

    public int getAdvanceWidth(char c) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(4);
        int[] nArray = new int[]{0};
        OS.GetCharWidth(this.handle, c, c, nArray);
        return nArray[0];
    }

    public boolean getAdvanced() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.gdipGraphics != 0L;
    }

    public int getAlpha() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.alpha;
    }

    public int getAntialias() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L) {
            return -1;
        }
        int n = Gdip.Graphics_GetSmoothingMode(this.data.gdipGraphics);
        switch (n) {
            case 0: {
                return -1;
            }
            case 1: 
            case 3: {
                return 0;
            }
            case 2: 
            case 4: 
            case 5: {
                return 1;
            }
        }
        return -1;
    }

    public Color getBackground() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return Color.win32_new(this.data.device, this.data.background);
    }

    public Pattern getBackgroundPattern() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.backgroundPattern;
    }

    public int getCharWidth(char c) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(4);
        int[] nArray = new int[3];
        if (OS.GetCharABCWidths(this.handle, c, c, nArray)) {
            return nArray[1];
        }
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(this.handle, tEXTMETRIC);
        SIZE sIZE = new SIZE();
        OS.GetTextExtentPoint32(this.handle, new char[]{c}, 1, sIZE);
        return sIZE.cx - tEXTMETRIC.tmOverhang;
    }

    public Rectangle getClipping() {
        return DPIUtil.autoScaleDown(this.drawable, this.getClippingInPixels());
    }

    Rectangle getClippingInPixels() {
        long l2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if ((l2 = this.data.gdipGraphics) != 0L) {
            Rect rect = new Rect();
            Gdip.Graphics_SetPixelOffsetMode(l2, 3);
            Gdip.Graphics_GetVisibleClipBounds(l2, rect);
            Gdip.Graphics_SetPixelOffsetMode(l2, 4);
            return new Rectangle(rect.X, rect.Y, rect.Width, rect.Height);
        }
        RECT rECT = new RECT();
        OS.GetClipBox(this.handle, rECT);
        return new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
    }

    public void getClipping(Region region) {
        long l2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (region == null) {
            SWT.error(4);
        }
        if (region.isDisposed()) {
            SWT.error(5);
        }
        if ((l2 = this.data.gdipGraphics) != 0L) {
            long l3 = Gdip.Region_new();
            Gdip.Graphics_GetClip(this.data.gdipGraphics, l3);
            if (Gdip.Region_IsInfinite(l3, l2)) {
                Rect rect = new Rect();
                Gdip.Graphics_SetPixelOffsetMode(l2, 3);
                Gdip.Graphics_GetVisibleClipBounds(l2, rect);
                Gdip.Graphics_SetPixelOffsetMode(l2, 4);
                OS.SetRectRgn(region.handle, rect.X, rect.Y, rect.X + rect.Width, rect.Y + rect.Height);
            } else {
                long l4 = Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
                long l5 = Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
                Gdip.Graphics_GetTransform(l2, l4);
                Gdip.Graphics_SetTransform(l2, l5);
                long l6 = Gdip.Region_GetHRGN(l3, this.data.gdipGraphics);
                Gdip.Graphics_SetTransform(l2, l4);
                Gdip.Matrix_delete(l5);
                Gdip.Matrix_delete(l4);
                POINT pOINT = new POINT();
                OS.GetWindowOrgEx(this.handle, pOINT);
                OS.OffsetRgn(l6, pOINT.x, pOINT.y);
                OS.CombineRgn(region.handle, l6, 0L, 5);
                OS.DeleteObject(l6);
            }
            Gdip.Region_delete(l3);
            return;
        }
        POINT pOINT = new POINT();
        OS.GetWindowOrgEx(this.handle, pOINT);
        int n = OS.GetClipRgn(this.handle, region.handle);
        if (n != 1) {
            RECT rECT = new RECT();
            OS.GetClipBox(this.handle, rECT);
            OS.SetRectRgn(region.handle, rECT.left, rECT.top, rECT.right, rECT.bottom);
        } else {
            OS.OffsetRgn(region.handle, pOINT.x, pOINT.y);
        }
        long l7 = OS.CreateRectRgn(0, 0, 0, 0);
        if (OS.GetMetaRgn(this.handle, l7) != 0) {
            OS.OffsetRgn(l7, pOINT.x, pOINT.y);
            OS.CombineRgn(region.handle, l7, region.handle, 1);
        }
        OS.DeleteObject(l7);
        long l8 = this.data.hwnd;
        if (l8 != 0L && this.data.ps != null) {
            long l9 = OS.CreateRectRgn(0, 0, 0, 0);
            if (OS.GetRandomRgn(this.handle, l9, 4) == 1) {
                if ((OS.GetLayout(this.handle) & 1) != 0) {
                    int n2 = OS.GetRegionData(l9, 0, null);
                    int[] nArray = new int[n2 / 4];
                    OS.GetRegionData(l9, n2, nArray);
                    long l10 = OS.ExtCreateRegion(new float[]{-1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f}, n2, nArray);
                    OS.DeleteObject(l9);
                    l9 = l10;
                }
                OS.MapWindowPoints(0L, l8, pOINT, 1);
                OS.OffsetRgn(l9, pOINT.x, pOINT.y);
                OS.CombineRgn(region.handle, l9, region.handle, 1);
            }
            OS.DeleteObject(l9);
        }
    }

    long getFgBrush() {
        return this.data.foregroundPattern != null ? this.data.foregroundPattern.handle : this.data.gdipFgBrush;
    }

    public int getFillRule() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return OS.GetPolyFillMode(this.handle) == 2 ? 2 : 1;
    }

    public Font getFont() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.font;
    }

    public FontMetrics getFontMetrics() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(4);
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(this.handle, tEXTMETRIC);
        return FontMetrics.win32_new(tEXTMETRIC);
    }

    public Color getForeground() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return Color.win32_new(this.data.device, this.data.foreground);
    }

    public Pattern getForegroundPattern() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.foregroundPattern;
    }

    public GCData getGCData() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data;
    }

    public int getInterpolation() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L) {
            return -1;
        }
        int n = Gdip.Graphics_GetInterpolationMode(this.data.gdipGraphics);
        switch (n) {
            case 0: {
                return -1;
            }
            case 5: {
                return 0;
            }
            case 1: 
            case 3: {
                return 1;
            }
            case 2: 
            case 4: 
            case 6: 
            case 7: {
                return 2;
            }
        }
        return -1;
    }

    public LineAttributes getLineAttributes() {
        LineAttributes lineAttributes = this.getLineAttributesInPixels();
        lineAttributes.width = DPIUtil.autoScaleDown(this.drawable, lineAttributes.width);
        return lineAttributes;
    }

    LineAttributes getLineAttributesInPixels() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        float[] fArray = null;
        if (this.data.lineDashes != null) {
            fArray = new float[this.data.lineDashes.length];
            System.arraycopy(this.data.lineDashes, 0, fArray, 0, fArray.length);
        }
        return new LineAttributes(this.data.lineWidth, this.data.lineCap, this.data.lineJoin, this.data.lineStyle, fArray, this.data.lineDashesOffset, this.data.lineMiterLimit);
    }

    public int getLineCap() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.lineCap;
    }

    public int[] getLineDash() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.lineDashes == null) {
            return null;
        }
        int[] nArray = new int[this.data.lineDashes.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = (int)this.data.lineDashes[i];
        }
        return nArray;
    }

    public int getLineJoin() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.lineJoin;
    }

    public int getLineStyle() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.lineStyle;
    }

    public int getLineWidth() {
        return DPIUtil.autoScaleDown(this.drawable, this.getLineWidthInPixels());
    }

    int getLineWidthInPixels() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return (int)this.data.lineWidth;
    }

    public int getStyle() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return this.data.style;
    }

    public int getTextAntialias() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L) {
            return -1;
        }
        int n = Gdip.Graphics_GetTextRenderingHint(this.data.gdipGraphics);
        switch (n) {
            case 0: {
                return -1;
            }
            case 1: 
            case 2: {
                return 0;
            }
            case 3: 
            case 4: 
            case 5: {
                return 1;
            }
        }
        return -1;
    }

    public void getTransform(Transform transform) {
        long l2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (transform == null) {
            SWT.error(4);
        }
        if (transform.isDisposed()) {
            SWT.error(5);
        }
        if ((l2 = this.data.gdipGraphics) != 0L) {
            Gdip.Graphics_GetTransform(l2, transform.handle);
            long l3 = this.identity();
            Gdip.Matrix_Invert(l3);
            Gdip.Matrix_Multiply(transform.handle, l3, 1);
            Gdip.Matrix_delete(l3);
        } else {
            transform.setElements(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        }
    }

    public boolean getXORMode() {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        return OS.GetROP2(this.handle) == 7;
    }

    void initGdip() {
        long l2;
        this.data.device.checkGDIP();
        long l3 = this.data.gdipGraphics;
        if (l3 != 0L) {
            return;
        }
        long l4 = OS.CreateRectRgn(0, 0, 0, 0);
        int n = OS.GetClipRgn(this.handle, l4);
        POINT pOINT = new POINT();
        OS.GetWindowOrgEx(this.handle, pOINT);
        OS.OffsetRgn(l4, pOINT.x, pOINT.y);
        OS.SelectClipRgn(this.handle, 0L);
        if ((this.data.style & 0x8000000) != 0) {
            OS.SetLayout(this.handle, OS.GetLayout(this.handle) & 0xFFFFFFFE);
        }
        GCData gCData = this.data;
        gCData.gdipGraphics = l2 = Gdip.Graphics_new(this.handle);
        l3 = l2;
        if (l3 == 0L) {
            SWT.error(2);
        }
        Gdip.Graphics_SetPageUnit(l3, 2);
        Gdip.Graphics_SetPixelOffsetMode(l3, 4);
        if ((this.data.style & 0x8000000) != 0) {
            long l5 = this.identity();
            Gdip.Graphics_SetTransform(l3, l5);
            Gdip.Matrix_delete(l5);
        }
        if (n == 1) {
            this.setClipping(l4);
        }
        OS.DeleteObject(l4);
        this.data.state = 0;
        if (this.data.hPen != 0L) {
            OS.SelectObject(this.handle, OS.GetStockObject(8));
            OS.DeleteObject(this.data.hPen);
            this.data.hPen = 0L;
        }
        if (this.data.hBrush != 0L) {
            OS.SelectObject(this.handle, OS.GetStockObject(5));
            OS.DeleteObject(this.data.hBrush);
            this.data.hBrush = 0L;
        }
    }

    long identity() {
        if ((this.data.style & 0x8000000) != 0) {
            Object object;
            int n = 0;
            int n2 = OS.GetDeviceCaps(this.handle, 2);
            if (n2 == 2) {
                n = OS.GetDeviceCaps(this.handle, 110);
            } else {
                object = this.data.image;
                if (object != null) {
                    BITMAP bITMAP = new BITMAP();
                    OS.GetObject(((Image)object).handle, BITMAP.sizeof, bITMAP);
                    n = bITMAP.bmWidth;
                } else {
                    long l2 = OS.WindowFromDC(this.handle);
                    if (l2 != 0L) {
                        RECT rECT = new RECT();
                        OS.GetClientRect(l2, rECT);
                        n = rECT.right - rECT.left;
                    } else {
                        long l3 = OS.GetCurrentObject(this.handle, 7);
                        BITMAP bITMAP = new BITMAP();
                        OS.GetObject(l3, BITMAP.sizeof, bITMAP);
                        n = bITMAP.bmWidth;
                    }
                }
            }
            object = new POINT();
            OS.GetWindowOrgEx(this.handle, (POINT)object);
            return Gdip.Matrix_new(-1.0f, 0.0f, 0.0f, 1.0f, n + 2 * ((POINT)object).x, 0.0f);
        }
        return Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }

    void init(Drawable drawable, GCData gCData, long l2) {
        int n;
        int n2 = gCData.foreground;
        if (n2 != -1) {
            gCData.state &= 0xFFFFF6FE;
        } else {
            gCData.foreground = OS.GetTextColor(l2);
        }
        int n3 = gCData.background;
        if (n3 != -1) {
            gCData.state &= 0xFFFFF9FD;
        } else {
            gCData.background = OS.GetBkColor(l2);
        }
        gCData.state &= 0xFFFFCFFF;
        Font font = gCData.font;
        if (font != null) {
            gCData.state &= 0xFFFFFFFB;
        } else {
            gCData.font = Font.win32_new(this.device, OS.GetCurrentObject(l2, 6));
        }
        Image image = gCData.image;
        if (image != null) {
            gCData.hNullBitmap = OS.SelectObject(l2, image.handle);
            image.memGC = this;
        }
        if ((n = gCData.layout) != -1) {
            int n4 = OS.GetLayout(l2);
            if ((n4 & 1) != (n & 1)) {
                OS.SetLayout(l2, (n4 &= 0xFFFFFFFE) | n);
            }
            if ((gCData.style & 0x4000000) != 0) {
                gCData.style |= 0x8000000;
            }
        }
        this.drawable = drawable;
        this.data = gCData;
        this.handle = l2;
    }

    public int hashCode() {
        return (int)this.handle;
    }

    public boolean isClipped() {
        long l2;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if ((l2 = this.data.gdipGraphics) != 0L) {
            long l3 = Gdip.Region_new();
            Gdip.Graphics_GetClip(this.data.gdipGraphics, l3);
            boolean bl = Gdip.Region_IsInfinite(l3, l2);
            Gdip.Region_delete(l3);
            return !bl;
        }
        long l4 = OS.CreateRectRgn(0, 0, 0, 0);
        int n = OS.GetClipRgn(this.handle, l4);
        OS.DeleteObject(l4);
        return n > 0;
    }

    float measureSpace(long l2, long l3) {
        PointF pointF = new PointF();
        RectF rectF = new RectF();
        Gdip.Graphics_MeasureString(this.data.gdipGraphics, new char[]{' '}, 1, l2, pointF, l3, rectF);
        return rectF.Width;
    }

    public void setAdvanced(boolean bl) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (bl && this.data.gdipGraphics != 0L) {
            return;
        }
        if (bl) {
            this.initGdip();
        } else {
            this.disposeGdip();
            this.data.alpha = 255;
            GCData gCData = this.data;
            GCData gCData2 = this.data;
            Object var4_4 = null;
            gCData2.foregroundPattern = var4_4;
            gCData.backgroundPattern = var4_4;
            this.data.state = 0;
            this.setClipping(0L);
            if ((this.data.style & 0x8000000) != 0) {
                OS.SetLayout(this.handle, OS.GetLayout(this.handle) | 1);
            }
        }
    }

    public void setAntialias(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L && n == -1) {
            return;
        }
        int n2 = 0;
        switch (n) {
            case -1: {
                n2 = 0;
                break;
            }
            case 0: {
                n2 = 3;
                break;
            }
            case 1: {
                n2 = 4;
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.initGdip();
        Gdip.Graphics_SetSmoothingMode(this.data.gdipGraphics, n2);
    }

    public void setAlpha(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L && (n & 0xFF) == 255) {
            return;
        }
        this.initGdip();
        this.data.alpha = n & 0xFF;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFFC;
        if (this.data.gdipFgPatternBrushAlpha != 0L) {
            Gdip.TextureBrush_delete(this.data.gdipFgPatternBrushAlpha);
            this.data.gdipFgPatternBrushAlpha = 0L;
        }
        if (this.data.gdipBgPatternBrushAlpha != 0L) {
            Gdip.TextureBrush_delete(this.data.gdipBgPatternBrushAlpha);
            this.data.gdipBgPatternBrushAlpha = 0L;
        }
    }

    public void setBackground(Color color) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (color == null) {
            SWT.error(4);
        }
        if (color.isDisposed()) {
            SWT.error(5);
        }
        if (this.data.backgroundPattern == null && this.data.background == color.handle) {
            return;
        }
        this.data.backgroundPattern = null;
        this.data.background = color.handle;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFDFD;
    }

    public void setBackgroundPattern(Pattern pattern) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (pattern != null && pattern.isDisposed()) {
            SWT.error(5);
        }
        if (this.data.gdipGraphics == 0L && pattern == null) {
            return;
        }
        this.initGdip();
        if (this.data.backgroundPattern == pattern) {
            return;
        }
        this.data.backgroundPattern = pattern;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFFD;
        if (this.data.gdipBgPatternBrushAlpha != 0L) {
            Gdip.TextureBrush_delete(this.data.gdipBgPatternBrushAlpha);
            this.data.gdipBgPatternBrushAlpha = 0L;
        }
    }

    void setClipping(long l2) {
        long l3 = l2;
        long l4 = this.data.gdipGraphics;
        if (l4 != 0L) {
            if (l3 != 0L) {
                long l5 = Gdip.Region_new(l3);
                Gdip.Graphics_SetClip(l4, l5, 0);
                Gdip.Region_delete(l5);
            } else {
                Gdip.Graphics_ResetClip(l4);
            }
        } else {
            POINT pOINT = null;
            if (l3 != 0L) {
                pOINT = new POINT();
                OS.GetWindowOrgEx(this.handle, pOINT);
                OS.OffsetRgn(l3, -pOINT.x, -pOINT.y);
            }
            OS.SelectClipRgn(this.handle, l3);
            if (l3 != 0L) {
                OS.OffsetRgn(l3, pOINT.x, pOINT.y);
            }
        }
    }

    public void setClipping(int n, int n2, int n3, int n4) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        n2 = DPIUtil.autoScaleUp(this.drawable, n2);
        n3 = DPIUtil.autoScaleUp(this.drawable, n3);
        n4 = DPIUtil.autoScaleUp(this.drawable, n4);
        this.setClippingInPixels(n, n2, n3, n4);
    }

    void setClippingInPixels(int n, int n2, int n3, int n4) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        long l2 = OS.CreateRectRgn(n, n2, n + n3, n2 + n4);
        this.setClipping(l2);
        OS.DeleteObject(l2);
    }

    public void setClipping(Path path) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (path != null && path.isDisposed()) {
            SWT.error(5);
        }
        this.setClipping(0L);
        if (path != null) {
            this.initGdip();
            int n = OS.GetPolyFillMode(this.handle) == 2 ? 1 : 0;
            Gdip.GraphicsPath_SetFillMode(path.handle, n);
            Gdip.Graphics_SetClipPath(this.data.gdipGraphics, path.handle);
        }
    }

    public void setClipping(Rectangle rectangle) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (rectangle == null) {
            this.setClipping(0L);
        } else {
            rectangle = DPIUtil.autoScaleUp(this.drawable, rectangle);
            this.setClippingInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    public void setClipping(Region region) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (region != null && region.isDisposed()) {
            SWT.error(5);
        }
        this.setClipping(region != null ? region.handle : 0L);
    }

    public void setFillRule(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        int n2 = 1;
        switch (n) {
            case 2: {
                n2 = 2;
                break;
            }
            case 1: {
                n2 = 1;
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        OS.SetPolyFillMode(this.handle, n2);
    }

    public void setFont(Font font) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (font != null && font.isDisposed()) {
            SWT.error(5);
        }
        this.data.font = font != null ? font : this.data.device.systemFont;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFFB;
    }

    public void setForeground(Color color) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (color == null) {
            SWT.error(4);
        }
        if (color.isDisposed()) {
            SWT.error(5);
        }
        if (this.data.foregroundPattern == null && color.handle == this.data.foreground) {
            return;
        }
        this.data.foregroundPattern = null;
        this.data.foreground = color.handle;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFEFE;
    }

    public void setForegroundPattern(Pattern pattern) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (pattern != null && pattern.isDisposed()) {
            SWT.error(5);
        }
        if (this.data.gdipGraphics == 0L && pattern == null) {
            return;
        }
        this.initGdip();
        if (this.data.foregroundPattern == pattern) {
            return;
        }
        this.data.foregroundPattern = pattern;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFFE;
        if (this.data.gdipFgPatternBrushAlpha != 0L) {
            Gdip.TextureBrush_delete(this.data.gdipFgPatternBrushAlpha);
            this.data.gdipFgPatternBrushAlpha = 0L;
        }
    }

    public void setInterpolation(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L && n == -1) {
            return;
        }
        int n2 = 0;
        switch (n) {
            case -1: {
                n2 = 0;
                break;
            }
            case 0: {
                n2 = 5;
                break;
            }
            case 1: {
                n2 = 1;
                break;
            }
            case 2: {
                n2 = 2;
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.initGdip();
        Gdip.Graphics_SetInterpolationMode(this.data.gdipGraphics, n2);
    }

    public void setLineAttributes(LineAttributes lineAttributes) {
        if (lineAttributes == null) {
            SWT.error(4);
        }
        lineAttributes.width = DPIUtil.autoScaleUp(this.drawable, lineAttributes.width);
        this.setLineAttributesInPixels(lineAttributes);
    }

    void setLineAttributesInPixels(LineAttributes lineAttributes) {
        float f;
        int n;
        int n2;
        int n3;
        if (this.handle == 0L) {
            SWT.error(44);
        }
        int n4 = 0;
        float f2 = lineAttributes.width;
        if (f2 != this.data.lineWidth) {
            n4 |= 0x4010;
        }
        if ((n3 = lineAttributes.style) != this.data.lineStyle) {
            n4 |= 8;
            switch (n3) {
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    break;
                }
                case 6: {
                    if (lineAttributes.dash != null) break;
                    n3 = 1;
                    break;
                }
                default: {
                    SWT.error(5);
                }
            }
        }
        if ((n2 = lineAttributes.join) != this.data.lineJoin) {
            n4 |= 0x40;
            switch (n2) {
                case 1: 
                case 2: 
                case 3: {
                    break;
                }
                default: {
                    SWT.error(5);
                }
            }
        }
        if ((n = lineAttributes.cap) != this.data.lineCap) {
            n4 |= 0x20;
            switch (n) {
                case 1: 
                case 2: 
                case 3: {
                    break;
                }
                default: {
                    SWT.error(5);
                }
            }
        }
        float[] fArray = lineAttributes.dash;
        float[] fArray2 = this.data.lineDashes;
        if (fArray != null && fArray.length > 0) {
            boolean bl = fArray2 == null || fArray2.length != fArray.length;
            for (int i = 0; i < fArray.length; ++i) {
                float f3 = fArray[i];
                if (f3 <= 0.0f) {
                    SWT.error(5);
                }
                if (bl || fArray2[i] == f3) continue;
                bl = true;
            }
            if (bl) {
                float[] fArray3 = new float[fArray.length];
                System.arraycopy(fArray, 0, fArray3, 0, fArray.length);
                fArray = fArray3;
                n4 |= 8;
            } else {
                fArray = fArray2;
            }
        } else if (fArray2 != null && fArray2.length > 0) {
            n4 |= 8;
        } else {
            fArray = fArray2;
        }
        float f4 = lineAttributes.dashOffset;
        if (f4 != this.data.lineDashesOffset) {
            n4 |= 8;
        }
        if ((f = lineAttributes.miterLimit) != this.data.lineMiterLimit) {
            n4 |= 0x80;
        }
        this.initGdip();
        if (n4 == 0) {
            return;
        }
        this.data.lineWidth = f2;
        this.data.lineStyle = n3;
        this.data.lineCap = n;
        this.data.lineJoin = n2;
        this.data.lineDashes = fArray;
        this.data.lineDashesOffset = f4;
        this.data.lineMiterLimit = f;
        GCData gCData = this.data;
        gCData.state &= ~n4;
    }

    public void setLineCap(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.lineCap == n) {
            return;
        }
        switch (n) {
            case 1: 
            case 2: 
            case 3: {
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.data.lineCap = n;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFDF;
    }

    public void setLineDash(int[] nArray) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        float[] fArray = this.data.lineDashes;
        if (nArray != null && nArray.length > 0) {
            int n;
            boolean bl = this.data.lineStyle != 6 || fArray == null || fArray.length != nArray.length;
            for (n = 0; n < nArray.length; ++n) {
                int n2 = nArray[n];
                if (n2 <= 0) {
                    SWT.error(5);
                }
                if (bl || fArray[n] == (float)n2) continue;
                bl = true;
            }
            if (!bl) {
                return;
            }
            this.data.lineDashes = new float[nArray.length];
            for (n = 0; n < nArray.length; ++n) {
                this.data.lineDashes[n] = nArray[n];
            }
            this.data.lineStyle = 6;
        } else {
            if (this.data.lineStyle == 1 && (fArray == null || fArray.length == 0)) {
                return;
            }
            this.data.lineDashes = null;
            this.data.lineStyle = 1;
        }
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFF7;
    }

    public void setLineJoin(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.lineJoin == n) {
            return;
        }
        switch (n) {
            case 1: 
            case 2: 
            case 3: {
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.data.lineJoin = n;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFBF;
    }

    public void setLineStyle(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.lineStyle == n) {
            return;
        }
        switch (n) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                break;
            }
            case 6: {
                if (this.data.lineDashes != null) break;
                n = 1;
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.data.lineStyle = n;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFFFF7;
    }

    public void setLineWidth(int n) {
        n = DPIUtil.autoScaleUp(this.drawable, n);
        this.setLineWidthInPixels(n);
    }

    void setLineWidthInPixels(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.lineWidth == (float)n) {
            return;
        }
        this.data.lineWidth = n;
        GCData gCData = this.data;
        gCData.state &= 0xFFFFBFEF;
    }

    public void setXORMode(boolean bl) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        OS.SetROP2(this.handle, bl ? 7 : 13);
    }

    public void setTextAntialias(int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (this.data.gdipGraphics == 0L && n == -1) {
            return;
        }
        int n2 = 0;
        switch (n) {
            case -1: {
                n2 = 0;
                break;
            }
            case 0: {
                n2 = 1;
                break;
            }
            case 1: {
                int[] nArray = new int[]{0};
                OS.SystemParametersInfo(8202, 0, nArray, 0);
                if (nArray[0] == 2) {
                    n2 = 5;
                    break;
                }
                n2 = 3;
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        this.initGdip();
        Gdip.Graphics_SetTextRenderingHint(this.data.gdipGraphics, n2);
    }

    public void setTransform(Transform transform) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (transform != null && transform.isDisposed()) {
            SWT.error(5);
        }
        if (this.data.gdipGraphics == 0L && transform == null) {
            return;
        }
        this.initGdip();
        long l2 = this.identity();
        if (transform != null) {
            Gdip.Matrix_Multiply(l2, transform.handle, 0);
        }
        Gdip.Graphics_SetTransform(this.data.gdipGraphics, l2);
        Gdip.Matrix_delete(l2);
        GCData gCData = this.data;
        gCData.state &= 0xFFFFBFFF;
    }

    public Point stringExtent(String string) {
        if (string == null) {
            SWT.error(4);
        }
        return DPIUtil.autoScaleDown(this.drawable, this.stringExtentInPixels(string));
    }

    Point stringExtentInPixels(String string) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        this.checkGC(4);
        int n = string.length();
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Point point = new Point(0, 0);
            this.drawText(l2, string, 0, 0, 0, point);
            return point;
        }
        SIZE sIZE = new SIZE();
        if (n == 0) {
            OS.GetTextExtentPoint32(this.handle, new char[]{' '}, 1, sIZE);
            return new Point(0, sIZE.cy);
        }
        char[] cArray = string.toCharArray();
        OS.GetTextExtentPoint32(this.handle, cArray, n, sIZE);
        return new Point(sIZE.cx, sIZE.cy);
    }

    public Point textExtent(String string) {
        return DPIUtil.autoScaleDown(this.drawable, this.textExtentInPixels(string, 6));
    }

    public Point textExtent(String string, int n) {
        return DPIUtil.autoScaleDown(this.drawable, this.textExtentInPixels(string, n));
    }

    Point textExtentInPixels(String string, int n) {
        if (this.handle == 0L) {
            SWT.error(44);
        }
        if (string == null) {
            SWT.error(4);
        }
        this.checkGC(4);
        long l2 = this.data.gdipGraphics;
        if (l2 != 0L) {
            Point point = new Point(0, 0);
            this.drawText(l2, string, 0, 0, n, point);
            return point;
        }
        if (string.length() == 0) {
            SIZE sIZE = new SIZE();
            OS.GetTextExtentPoint32(this.handle, new char[]{' '}, 1, sIZE);
            return new Point(0, sIZE.cy);
        }
        RECT rECT = new RECT();
        char[] cArray = string.toCharArray();
        int n2 = 1024;
        if ((n & 2) == 0) {
            n2 |= 0x20;
        }
        if ((n & 4) != 0) {
            n2 |= 0x40;
        }
        if ((n & 8) == 0) {
            n2 |= 0x800;
        }
        OS.DrawText(this.handle, cArray, cArray.length, rECT, n2);
        return new Point(rECT.right, rECT.bottom);
    }

    public String toString() {
        if (this == false) {
            return "GC {*DISPOSED*}";
        }
        return "GC {" + this.handle;
    }

    public static GC win32_new(Drawable drawable, GCData gCData) {
        GC gC = new GC();
        long l2 = drawable.internal_new_GC(gCData);
        gC.device = gCData.device;
        gC.init(drawable, gCData, l2);
        return gC;
    }

    public static GC win32_new(long l2, GCData gCData) {
        GC gC = new GC();
        gC.device = gCData.device;
        gCData.style |= 0x2000000;
        int n = OS.GetLayout(l2);
        if ((n & 1) != 0) {
            gCData.style |= 0xC000000;
        }
        gC.init(null, gCData, l2);
        return gC;
    }

    private static int cos(int n, int n2) {
        return (int)(Math.cos((double)n * (Math.PI / 180)) * (double)n2);
    }

    private static int sin(int n, int n2) {
        return (int)(Math.sin((double)n * (Math.PI / 180)) * (double)n2);
    }
}

