/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;

public final class Font
extends Resource {
    public long handle;

    Font(Device device) {
        super(device);
    }

    public Font(Device device, FontData fontData) {
        super(device);
        this.init(fontData);
        this.init();
    }

    public Font(Device device, FontData[] fontDataArray) {
        super(device);
        if (fontDataArray == null) {
            SWT.error(4);
        }
        if (fontDataArray.length == 0) {
            SWT.error(5);
        }
        for (FontData fontData : fontDataArray) {
            if (fontData != null) continue;
            SWT.error(5);
        }
        this.init(fontDataArray[0]);
        this.init();
    }

    public Font(Device device, String string, int n, int n2) {
        super(device);
        if (string == null) {
            SWT.error(4);
        }
        this.init(new FontData(string, n, n2));
        this.init();
    }

    Font(Device device, String string, float f, int n) {
        super(device);
        if (string == null) {
            SWT.error(4);
        }
        this.init(new FontData(string, f, n));
        this.init();
    }

    @Override
    void destroy() {
        OS.DeleteObject(this.handle);
        this.handle = 0L;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Font)) {
            return false;
        }
        Font font = (Font)object;
        return this.device == font.device && this.handle == font.handle;
    }

    public FontData[] getFontData() {
        if (this == false) {
            SWT.error(44);
        }
        LOGFONT lOGFONT = new LOGFONT();
        OS.GetObject(this.handle, LOGFONT.sizeof, lOGFONT);
        return new FontData[]{FontData.win32_new(lOGFONT, this.device.computePoints(lOGFONT, this.handle))};
    }

    public int hashCode() {
        return (int)this.handle;
    }

    void init(FontData fontData) {
        if (fontData == null) {
            SWT.error(4);
        }
        LOGFONT lOGFONT = fontData.data;
        int n = lOGFONT.lfHeight;
        lOGFONT.lfHeight = this.device.computePixels(fontData.height);
        this.handle = OS.CreateFontIndirect(lOGFONT);
        lOGFONT.lfHeight = n;
        if (this.handle == 0L) {
            SWT.error(2);
        }
    }

    public String toString() {
        if (this == false) {
            return "Font {*DISPOSED*}";
        }
        return "Font {" + this.handle;
    }

    public static Font win32_new(Device device, long l2) {
        Font font = new Font(device);
        font.handle = l2;
        font.ignoreNonDisposed();
        return font;
    }
}

