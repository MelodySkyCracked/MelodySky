/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ExceptionStash;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.GdiplusStartupInput;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;

public abstract class Device
implements Drawable {
    public static boolean DEBUG;
    boolean debug = DEBUG;
    boolean tracking = DEBUG;
    Error[] errors;
    Object[] objects;
    Object trackingLock;
    Font systemFont;
    int nFonts = 256;
    LOGFONT[] logFonts;
    TEXTMETRIC metrics;
    int[] pixels;
    long[] scripts;
    long[] gdipToken;
    long fontCollection;
    String[] loadedFonts;
    volatile boolean disposed;
    boolean enableAutoScaling = true;
    protected static Device CurrentDevice;
    protected static Runnable DeviceFinder;

    static synchronized Device getDevice() {
        if (DeviceFinder != null) {
            DeviceFinder.run();
        }
        Device device = CurrentDevice;
        CurrentDevice = null;
        return device;
    }

    public Device() {
        this(null);
    }

    public Device(DeviceData deviceData) {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            if (deviceData != null) {
                this.debug = deviceData.debug;
                this.tracking = deviceData.tracking;
            }
            if (this.tracking) {
                this.startTracking();
            }
            this.create(deviceData);
            this.init();
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    public boolean isTracking() {
        this.checkDevice();
        return this.tracking;
    }

    public void setTracking(boolean bl) {
        this.checkDevice();
        if (bl == this.tracking) {
            return;
        }
        this.tracking = bl;
        if (bl) {
            this.startTracking();
        } else {
            this.stopTracking();
        }
    }

    private void startTracking() {
        this.errors = new Error[128];
        this.objects = new Object[128];
        this.trackingLock = new Object();
    }

    private void stopTracking() {
        Object object = this.trackingLock;
        synchronized (object) {
            this.objects = null;
            this.errors = null;
            this.trackingLock = null;
        }
    }

    void addFont(String string) {
        int n;
        if (this.loadedFonts == null) {
            this.loadedFonts = new String[4];
        }
        int n2 = this.loadedFonts.length;
        for (n = 0; n < n2; ++n) {
            if (!string.equals(this.loadedFonts[n])) continue;
            return;
        }
        for (n = 0; n < n2 && this.loadedFonts[n] != null; ++n) {
        }
        if (n == n2) {
            String[] stringArray = new String[n2 + 4];
            System.arraycopy(this.loadedFonts, 0, stringArray, 0, n2);
            this.loadedFonts = stringArray;
        }
        this.loadedFonts[n] = string;
    }

    protected void checkDevice() {
        if (this.disposed) {
            SWT.error(45);
        }
    }

    void checkGDIP() {
        if (this.gdipToken != null) {
            return;
        }
        long[] lArray = new long[]{0L};
        GdiplusStartupInput gdiplusStartupInput = new GdiplusStartupInput();
        gdiplusStartupInput.GdiplusVersion = 1;
        if (Gdip.GdiplusStartup(lArray, gdiplusStartupInput, 0L) != 0) {
            SWT.error(2);
        }
        this.gdipToken = lArray;
        if (this.loadedFonts != null) {
            this.fontCollection = Gdip.PrivateFontCollection_new();
            if (this.fontCollection == 0L) {
                SWT.error(2);
            }
            for (String string : this.loadedFonts) {
                if (string == null) break;
                int n = string.length();
                char[] cArray = new char[n + 1];
                string.getChars(0, n, cArray, 0);
                Gdip.PrivateFontCollection_AddFontFile(this.fontCollection, cArray);
            }
            this.loadedFonts = null;
        }
    }

    protected void create(DeviceData deviceData) {
    }

    int computePixels(float f) {
        long l2 = this.internal_new_GC(null);
        int n = -((int)(0.5f + f * (float)OS.GetDeviceCaps(l2, 90) / 72.0f));
        this.internal_dispose_GC(l2, null);
        return n;
    }

    float computePoints(LOGFONT lOGFONT, long l2) {
        long l3 = this.internal_new_GC(null);
        int n = OS.GetDeviceCaps(l3, 90);
        int n2 = 0;
        if (lOGFONT.lfHeight > 0) {
            long l4 = OS.SelectObject(l3, l2);
            TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
            OS.GetTextMetrics(l3, tEXTMETRIC);
            OS.SelectObject(l3, l4);
            n2 = lOGFONT.lfHeight - tEXTMETRIC.tmInternalLeading;
        } else {
            n2 = -lOGFONT.lfHeight;
        }
        this.internal_dispose_GC(l3, null);
        return (float)n2 * 72.0f / (float)n;
    }

    protected void destroy() {
    }

    public void dispose() {
        Class<Device> clazz = Device.class;
        synchronized (Device.class) {
            block16: {
                ExceptionStash exceptionStash;
                block17: {
                    Throwable throwable;
                    block13: {
                        block14: {
                            block15: {
                                exceptionStash = new ExceptionStash();
                                throwable = null;
                                try {
                                    if (!this.isDisposed()) break block13;
                                    exceptionStash.close();
                                    if (exceptionStash == null) break block14;
                                    if (throwable == null) break block15;
                                }
                                catch (Throwable throwable2) {
                                    throwable = throwable2;
                                    throw throwable2;
                                }
                                try {
                                    exceptionStash.close();
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                                break block14;
                            }
                            exceptionStash.close();
                        }
                        // ** MonitorExit[var1_1] (shouldn't be in output)
                        return;
                    }
                    this.checkDevice();
                    try {
                        this.release();
                    }
                    catch (Error | RuntimeException throwable4) {
                        exceptionStash.stash(throwable4);
                    }
                    this.destroy();
                    this.disposed = true;
                    if (this.tracking) {
                        Object object = this.trackingLock;
                        synchronized (object) {
                            this.printErrors();
                            this.objects = null;
                            this.errors = null;
                            this.trackingLock = null;
                        }
                    }
                    if (exceptionStash == null) break block16;
                    if (throwable == null) break block17;
                    try {
                        exceptionStash.close();
                    }
                    catch (Throwable throwable5) {
                        throwable.addSuppressed(throwable5);
                    }
                    break block16;
                }
                exceptionStash.close();
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    void dispose_Object(Object object) {
        Object object2 = this.trackingLock;
        synchronized (object2) {
            for (int i = 0; i < this.objects.length; ++i) {
                if (this.objects[i] != object) continue;
                this.objects[i] = null;
                this.errors[i] = null;
                return;
            }
        }
    }

    long EnumFontFamProc(long l2, long l3, long l4, long l5) {
        boolean bl;
        boolean bl2 = ((int)l4 & 1) == 0;
        boolean bl3 = bl = l5 == 1L;
        if (bl2 == bl) {
            Object object;
            if (this.nFonts == this.logFonts.length) {
                object = new LOGFONT[this.logFonts.length + 128];
                System.arraycopy(this.logFonts, 0, object, 0, this.nFonts);
                this.logFonts = object;
                int[] nArray = new int[((LOGFONT[])object).length];
                System.arraycopy(this.pixels, 0, nArray, 0, this.nFonts);
                this.pixels = nArray;
            }
            if ((object = this.logFonts[this.nFonts]) == null) {
                object = new LOGFONT();
            }
            OS.MoveMemory((LOGFONT)object, l2, LOGFONT.sizeof);
            this.logFonts[this.nFonts] = object;
            if (object.lfHeight > 0) {
                OS.MoveMemory(this.metrics, l3, TEXTMETRIC.sizeof);
                this.pixels[this.nFonts] = object.lfHeight - this.metrics.tmInternalLeading;
            } else {
                this.pixels[this.nFonts] = -object.lfHeight;
            }
            ++this.nFonts;
        }
        return 1L;
    }

    public Rectangle getBounds() {
        this.checkDevice();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    private Rectangle getBoundsInPixels() {
        long l2 = this.internal_new_GC(null);
        int n = OS.GetDeviceCaps(l2, 8);
        int n2 = OS.GetDeviceCaps(l2, 10);
        this.internal_dispose_GC(l2, null);
        return new Rectangle(0, 0, n, n2);
    }

    public DeviceData getDeviceData() {
        this.checkDevice();
        DeviceData deviceData = new DeviceData();
        deviceData.debug = this.debug;
        deviceData.tracking = this.tracking;
        if (this.tracking) {
            Object object = this.trackingLock;
            synchronized (object) {
                int n;
                int n2 = 0;
                int n3 = this.objects.length;
                for (n = 0; n < n3; ++n) {
                    if (this.objects[n] == null) continue;
                    ++n2;
                }
                n = 0;
                deviceData.objects = new Object[n2];
                deviceData.errors = new Error[n2];
                for (int i = 0; i < n3; ++i) {
                    if (this.objects[i] == null) continue;
                    deviceData.objects[n] = this.objects[i];
                    deviceData.errors[n] = this.errors[i];
                    ++n;
                }
            }
        } else {
            deviceData.objects = new Object[0];
            deviceData.errors = new Error[0];
        }
        return deviceData;
    }

    public Rectangle getClientArea() {
        return this.getBounds();
    }

    public int getDepth() {
        this.checkDevice();
        long l2 = this.internal_new_GC(null);
        int n = OS.GetDeviceCaps(l2, 12);
        int n2 = OS.GetDeviceCaps(l2, 14);
        this.internal_dispose_GC(l2, null);
        return n * n2;
    }

    public Point getDPI() {
        this.checkDevice();
        long l2 = this.internal_new_GC(null);
        int n = OS.GetDeviceCaps(l2, 88);
        int n2 = OS.GetDeviceCaps(l2, 90);
        this.internal_dispose_GC(l2, null);
        return DPIUtil.autoScaleDown(new Point(n, n2));
    }

    int _getDPIx() {
        long l2 = this.internal_new_GC(null);
        int n = OS.GetDeviceCaps(l2, 88);
        this.internal_dispose_GC(l2, null);
        return n;
    }

    public FontData[] getFontList(String string, boolean bl) {
        int n;
        int n2;
        this.checkDevice();
        Callback callback = new Callback(this, "EnumFontFamProc", 4);
        long l2 = callback.getAddress();
        this.metrics = new TEXTMETRIC();
        this.pixels = new int[this.nFonts];
        this.logFonts = new LOGFONT[this.nFonts];
        for (n2 = 0; n2 < this.logFonts.length; ++n2) {
            this.logFonts[n2] = new LOGFONT();
        }
        this.nFonts = 0;
        n2 = 0;
        long l3 = this.internal_new_GC(null);
        if (string == null) {
            OS.EnumFontFamilies(l3, null, l2, bl ? 1L : 0L);
            n2 = this.nFonts;
            for (n = 0; n < n2; ++n) {
                LOGFONT lOGFONT = this.logFonts[n];
                OS.EnumFontFamilies(l3, lOGFONT.lfFaceName, l2, bl ? 1L : 0L);
            }
        } else {
            TCHAR tCHAR = new TCHAR(0, string, true);
            OS.EnumFontFamilies(l3, tCHAR.chars, l2, bl ? 1L : 0L);
        }
        n = OS.GetDeviceCaps(l3, 90);
        this.internal_dispose_GC(l3, null);
        int n3 = 0;
        FontData[] fontDataArray = new FontData[this.nFonts - n2];
        for (int i = n2; i < this.nFonts; ++i) {
            int n4;
            FontData fontData = FontData.win32_new(this.logFonts[i], (float)this.pixels[i] * 72.0f / (float)n);
            for (n4 = 0; n4 < n3 && !fontData.equals(fontDataArray[n4]); ++n4) {
            }
            if (n4 != n3) continue;
            fontDataArray[n3++] = fontData;
        }
        if (n3 != fontDataArray.length) {
            FontData[] fontDataArray2 = new FontData[n3];
            System.arraycopy(fontDataArray, 0, fontDataArray2, 0, n3);
            fontDataArray = fontDataArray2;
        }
        callback.dispose();
        this.logFonts = null;
        this.pixels = null;
        this.metrics = null;
        return fontDataArray;
    }

    String getLastError() {
        int n = OS.GetLastError();
        if (n == 0) {
            return "";
        }
        return " [GetLastError=0x" + Integer.toHexString(n);
    }

    public Color getSystemColor(int n) {
        this.checkDevice();
        int n2 = 0;
        int n3 = 255;
        switch (n) {
            case 37: {
                n3 = 0;
            }
            case 1: {
                n2 = 0xFFFFFF;
                break;
            }
            case 2: {
                n2 = 0;
                break;
            }
            case 3: {
                n2 = 255;
                break;
            }
            case 4: {
                n2 = 128;
                break;
            }
            case 5: {
                n2 = 65280;
                break;
            }
            case 6: {
                n2 = 32768;
                break;
            }
            case 7: {
                n2 = 65535;
                break;
            }
            case 8: {
                n2 = 32896;
                break;
            }
            case 9: {
                n2 = 0xFF0000;
                break;
            }
            case 10: {
                n2 = 0x800000;
                break;
            }
            case 11: {
                n2 = 0xFF00FF;
                break;
            }
            case 12: {
                n2 = 0x800080;
                break;
            }
            case 13: {
                n2 = 0xFFFF00;
                break;
            }
            case 14: {
                n2 = 0x808000;
                break;
            }
            case 15: {
                n2 = 0xC0C0C0;
                break;
            }
            case 16: {
                n2 = 0x808080;
            }
        }
        return Color.win32_new(this, n2, n3);
    }

    public Font getSystemFont() {
        this.checkDevice();
        long l2 = OS.GetStockObject(13);
        return Font.win32_new(this, l2);
    }

    public boolean getWarnings() {
        this.checkDevice();
        return false;
    }

    protected void init() {
        if (this.debug) {
            OS.GdiSetBatchLimit(1);
        }
        this.systemFont = this.getSystemFont();
        long[] lArray = new long[]{0L};
        int[] nArray = new int[]{0};
        OS.ScriptGetProperties(lArray, nArray);
        this.scripts = new long[nArray[0]];
        OS.MoveMemory(this.scripts, lArray[0], this.scripts.length * C.PTR_SIZEOF);
    }

    @Override
    public abstract long internal_new_GC(GCData var1);

    @Override
    public abstract void internal_dispose_GC(long var1, GCData var3);

    public boolean isDisposed() {
        return this.disposed;
    }

    public boolean loadFont(String string) {
        TCHAR tCHAR;
        boolean bl;
        this.checkDevice();
        if (string == null) {
            SWT.error(4);
        }
        boolean bl2 = bl = OS.AddFontResourceEx(tCHAR = new TCHAR(0, string, true), 16, 0L) != 0L;
        if (bl) {
            if (this.gdipToken != null) {
                if (this.fontCollection == 0L) {
                    this.fontCollection = Gdip.PrivateFontCollection_new();
                    if (this.fontCollection == 0L) {
                        SWT.error(2);
                    }
                }
                int n = string.length();
                char[] cArray = new char[n + 1];
                string.getChars(0, n, cArray, 0);
                Gdip.PrivateFontCollection_AddFontFile(this.fontCollection, cArray);
            } else {
                this.addFont(string);
            }
        }
        return bl;
    }

    void new_Object(Object object) {
        Object object2 = this.trackingLock;
        synchronized (object2) {
            for (int i = 0; i < this.objects.length; ++i) {
                if (this.objects[i] != null) continue;
                this.objects[i] = object;
                this.errors[i] = new Error();
                return;
            }
            Object[] objectArray = new Object[this.objects.length + 128];
            System.arraycopy(this.objects, 0, objectArray, 0, this.objects.length);
            objectArray[this.objects.length] = object;
            this.objects = objectArray;
            Error[] errorArray = new Error[this.errors.length + 128];
            System.arraycopy(this.errors, 0, errorArray, 0, this.errors.length);
            errorArray[this.errors.length] = new Error();
            this.errors = errorArray;
        }
    }

    void printErrors() {
        block26: {
            if (!DEBUG) {
                return;
            }
            if (!this.tracking) break block26;
            Object object = this.trackingLock;
            synchronized (object) {
                if (this.objects == null || this.errors == null) {
                    return;
                }
                int n = 0;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                int n11 = 0;
                for (Object object2 : this.objects) {
                    if (object2 == null) continue;
                    ++n;
                    if (object2 instanceof Color) {
                        ++n2;
                    }
                    if (object2 instanceof Cursor) {
                        ++n3;
                    }
                    if (object2 instanceof Font) {
                        ++n4;
                    }
                    if (object2 instanceof GC) {
                        ++n5;
                    }
                    if (object2 instanceof Image) {
                        ++n6;
                    }
                    if (object2 instanceof Path) {
                        ++n7;
                    }
                    if (object2 instanceof Pattern) {
                        ++n8;
                    }
                    if (object2 instanceof Region) {
                        ++n9;
                    }
                    if (object2 instanceof TextLayout) {
                        ++n10;
                    }
                    if (!(object2 instanceof Transform)) continue;
                    ++n11;
                }
                if (n != 0) {
                    Object object3 = "Summary: ";
                    if (n2 != 0) {
                        object3 = (String)object3 + n2 + " Color(s), ";
                    }
                    if (n3 != 0) {
                        object3 = (String)object3 + n3 + " Cursor(s), ";
                    }
                    if (n4 != 0) {
                        object3 = (String)object3 + n4 + " Font(s), ";
                    }
                    if (n5 != 0) {
                        object3 = (String)object3 + n5 + " GC(s), ";
                    }
                    if (n6 != 0) {
                        object3 = (String)object3 + n6 + " Image(s), ";
                    }
                    if (n7 != 0) {
                        object3 = (String)object3 + n7 + " Path(s), ";
                    }
                    if (n8 != 0) {
                        object3 = (String)object3 + n8 + " Pattern(s), ";
                    }
                    if (n9 != 0) {
                        object3 = (String)object3 + n9 + " Region(s), ";
                    }
                    if (n10 != 0) {
                        object3 = (String)object3 + n10 + " TextLayout(s), ";
                    }
                    if (n11 != 0) {
                        object3 = (String)object3 + n11 + " Transforms(s), ";
                    }
                    if (((String)object3).length() != 0) {
                        object3 = ((String)object3).substring(0, ((String)object3).length() - 2);
                        System.err.println((String)object3);
                    }
                    for (Error error : this.errors) {
                        if (error == null) continue;
                        error.printStackTrace(System.err);
                    }
                }
            }
        }
    }

    protected void release() {
        if (this.gdipToken != null) {
            if (this.fontCollection != 0L) {
                Gdip.PrivateFontCollection_delete(this.fontCollection);
            }
            this.fontCollection = 0L;
            Gdip.GdiplusShutdown(this.gdipToken[0]);
        }
        this.gdipToken = null;
        this.scripts = null;
        this.logFonts = null;
        this.nFonts = 0;
    }

    public void setWarnings(boolean bl) {
        this.checkDevice();
    }

    boolean getEnableAutoScaling() {
        return this.enableAutoScaling;
    }

    void setEnableAutoScaling(boolean bl) {
        this.enableAutoScaling = bl;
    }

    protected int getDeviceZoom() {
        return DPIUtil.mapDPIToZoom(this._getDPIx());
    }

    static {
        try {
            Class.forName("org.eclipse.swt.widgets.Display");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }
}

