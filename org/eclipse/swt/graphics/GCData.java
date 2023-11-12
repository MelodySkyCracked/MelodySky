/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;

public final class GCData {
    public Device device;
    public int style;
    public int state = -1;
    public int foreground = -1;
    public int background = -1;
    public Font font;
    public Pattern foregroundPattern;
    public long gdipFgPatternBrushAlpha;
    public Pattern backgroundPattern;
    public long gdipBgPatternBrushAlpha;
    public int lineStyle = 1;
    public float lineWidth;
    public int lineCap = 1;
    public int lineJoin = 1;
    public float lineDashesOffset;
    public float[] lineDashes;
    public float lineMiterLimit = 10.0f;
    public int alpha = 255;
    public Image image;
    public PAINTSTRUCT ps;
    public int layout = -1;
    public long hPen;
    public long hOldPen;
    public long hBrush;
    public long hOldBrush;
    public long hNullBitmap;
    public long hwnd;
    public long gdipGraphics;
    public long gdipPen;
    public long gdipBrush;
    public long gdipFgBrush;
    public long gdipBgBrush;
    public long gdipFont;
    public long hGDIFont;
    public float gdipXOffset;
    public float gdipYOffset;
    public int uiState = 0;
    public boolean focusDrawn;
}

