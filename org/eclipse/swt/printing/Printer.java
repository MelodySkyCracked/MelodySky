/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.printing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.DEVMODE;
import org.eclipse.swt.internal.win32.DOCINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.printing.PrinterData;

public final class Printer
extends Device {
    public long handle;
    PrinterData data;
    boolean isGCCreated = false;
    static TCHAR profile = new TCHAR(0, "PrinterPorts", true);
    static TCHAR appName = new TCHAR(0, "windows", true);
    static TCHAR keyName = new TCHAR(0, "device", true);

    public static PrinterData[] getPrinterList() {
        int n = 1024;
        TCHAR tCHAR = new TCHAR(0, 1);
        TCHAR tCHAR2 = new TCHAR(0, 1024);
        int n2 = OS.GetProfileString(profile, null, tCHAR, tCHAR2, 1024);
        if (n2 == 0) {
            return new PrinterData[0];
        }
        String[] stringArray = new String[5];
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            if (tCHAR2.tcharAt(i) != 0) continue;
            if (n3 == stringArray.length) {
                String[] stringArray2 = new String[stringArray.length + 5];
                System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
                stringArray = stringArray2;
            }
            stringArray[n3] = tCHAR2.toString(n4, i - n4);
            ++n3;
            n4 = i + 1;
        }
        PrinterData[] printerDataArray = new PrinterData[n3];
        for (int i = 0; i < n3; ++i) {
            String string = stringArray[i];
            String string2 = "";
            if (OS.GetProfileString(profile, new TCHAR(0, string, true), tCHAR, tCHAR2, 1024) > 0) {
                int n5;
                for (n5 = 0; tCHAR2.tcharAt(n5) != 44 && n5 < 1024; ++n5) {
                }
                if (n5 < 1024) {
                    string2 = tCHAR2.toString(0, n5);
                }
            }
            printerDataArray[i] = new PrinterData(string2, string);
        }
        return printerDataArray;
    }

    public static PrinterData getDefaultPrinterData() {
        int n;
        String string = null;
        int n2 = 1024;
        TCHAR tCHAR = new TCHAR(0, 1);
        TCHAR tCHAR2 = new TCHAR(0, 1024);
        int n3 = OS.GetProfileString(appName, keyName, tCHAR, tCHAR2, 1024);
        if (n3 == 0) {
            return null;
        }
        for (n = 0; tCHAR2.tcharAt(n) != 44 && n < 1024; ++n) {
        }
        if (n < 1024) {
            string = tCHAR2.toString(0, n);
        }
        if (string == null) {
            return null;
        }
        String string2 = "";
        if (OS.GetProfileString(profile, new TCHAR(0, string, true), tCHAR, tCHAR2, 1024) > 0) {
            for (n = 0; tCHAR2.tcharAt(n) != 44 && n < 1024; ++n) {
            }
            if (n < 1024) {
                string2 = tCHAR2.toString(0, n);
            }
        }
        return new PrinterData(string2, string);
    }

    static DeviceData checkNull(PrinterData printerData) {
        if (printerData == null) {
            printerData = new PrinterData();
        }
        if (printerData.driver == null || printerData.name == null) {
            PrinterData printerData2 = Printer.getDefaultPrinterData();
            if (printerData2 == null) {
                SWT.error(2);
            }
            printerData.driver = printerData2.driver;
            printerData.name = printerData2.name;
        }
        return printerData;
    }

    public Printer() {
        this(null);
    }

    public Printer(PrinterData printerData) {
        super(Printer.checkNull(printerData));
    }

    @Override
    protected void create(DeviceData deviceData) {
        Object object;
        this.data = (PrinterData)deviceData;
        TCHAR tCHAR = new TCHAR(0, this.data.driver, true);
        TCHAR tCHAR2 = new TCHAR(0, this.data.name, true);
        long l2 = 0L;
        byte[] byArray = this.data.otherData;
        long l3 = OS.GetProcessHeap();
        if (byArray != null && byArray.length != 0) {
            l2 = OS.HeapAlloc(l3, 8, byArray.length);
            OS.MoveMemory(l2, byArray, byArray.length);
        } else {
            object = new long[]{0L};
            OS.OpenPrinter(tCHAR2, object, 0L);
            if (object[0] != 0L) {
                int n;
                int n2 = OS.DocumentProperties(0L, object[0], tCHAR2, 0L, 0L, 0);
                if (n2 >= 0 && (n = OS.DocumentProperties(0L, object[0], tCHAR2, l2 = OS.HeapAlloc(l3, 8, n2), 0L, 2)) != 1) {
                    OS.HeapFree(l3, 0, l2);
                    l2 = 0L;
                }
                OS.ClosePrinter(object[0]);
            }
        }
        if (l2 != 0L) {
            object = new DEVMODE();
            OS.MoveMemory((DEVMODE)object, l2, DEVMODE.sizeof);
            Object object2 = object;
            object2.dmFields |= 1;
            object.dmOrientation = (short)(this.data.orientation == 2 ? 2 : 1);
            if (this.data.copyCount != 1) {
                Object object3 = object;
                object3.dmFields |= 0x100;
                object.dmCopies = (short)this.data.copyCount;
            }
            if (this.data.collate) {
                Object object4 = object;
                object4.dmFields |= 0x8000;
                object.dmCollate = 1;
            }
            if (this.data.duplex != -1) {
                Object object5 = object;
                object5.dmFields |= 0x1000;
                switch (this.data.duplex) {
                    case 2: {
                        object.dmDuplex = (short)3;
                        break;
                    }
                    case 1: {
                        object.dmDuplex = (short)2;
                        break;
                    }
                    default: {
                        object.dmDuplex = 1;
                    }
                }
            }
            OS.MoveMemory(l2, (DEVMODE)object, DEVMODE.sizeof);
        }
        this.handle = OS.CreateDC(tCHAR, tCHAR2, 0L, l2);
        if (l2 != 0L) {
            OS.HeapFree(l3, 0, l2);
        }
        if (this.handle == 0L) {
            SWT.error(2);
        }
    }

    @Override
    public long internal_new_GC(GCData gCData) {
        if (this.handle == 0L) {
            SWT.error(2);
        }
        if (gCData != null) {
            if (this.isGCCreated) {
                SWT.error(5);
            }
            int n = 0x6000000;
            if ((gCData.style & 0x6000000) != 0) {
                gCData.layout = (gCData.style & 0x4000000) != 0 ? 1 : 0;
            } else {
                gCData.style |= 0x2000000;
            }
            gCData.device = this;
            gCData.font = Font.win32_new(this, OS.GetCurrentObject(this.handle, 6));
            this.isGCCreated = true;
        }
        return this.handle;
    }

    @Override
    public void internal_dispose_GC(long l2, GCData gCData) {
        if (gCData != null) {
            this.isGCCreated = false;
        }
    }

    @Override
    public boolean isAutoScalable() {
        return false;
    }

    public boolean startJob(String string) {
        this.checkDevice();
        DOCINFO dOCINFO = new DOCINFO();
        dOCINFO.cbSize = DOCINFO.sizeof;
        long l2 = OS.GetProcessHeap();
        long l3 = 0L;
        if (string != null && string.length() != 0) {
            TCHAR tCHAR = new TCHAR(0, string, true);
            int n = tCHAR.length() * 2;
            l3 = OS.HeapAlloc(l2, 8, n);
            OS.MoveMemory(l3, tCHAR, n);
            dOCINFO.lpszDocName = l3;
        }
        long l4 = 0L;
        if (this.data.printToFile) {
            if (this.data.fileName == null) {
                this.data.fileName = "FILE:";
            }
            TCHAR tCHAR = new TCHAR(0, this.data.fileName, true);
            int n = tCHAR.length() * 2;
            l4 = OS.HeapAlloc(l2, 8, n);
            OS.MoveMemory(l4, tCHAR, n);
            dOCINFO.lpszOutput = l4;
        }
        int n = OS.StartDoc(this.handle, dOCINFO);
        if (l3 != 0L) {
            OS.HeapFree(l2, 0, l3);
        }
        if (l4 != 0L) {
            OS.HeapFree(l2, 0, l4);
        }
        return n > 0;
    }

    public void endJob() {
        this.checkDevice();
        OS.EndDoc(this.handle);
    }

    public void cancelJob() {
        this.checkDevice();
        OS.AbortDoc(this.handle);
    }

    public boolean startPage() {
        this.checkDevice();
        int n = OS.StartPage(this.handle);
        if (n <= 0) {
            OS.AbortDoc(this.handle);
        }
        return n > 0;
    }

    public void endPage() {
        this.checkDevice();
        OS.EndPage(this.handle);
    }

    @Override
    public Point getDPI() {
        this.checkDevice();
        int n = OS.GetDeviceCaps(this.handle, 88);
        int n2 = OS.GetDeviceCaps(this.handle, 90);
        return new Point(n, n2);
    }

    @Override
    public Rectangle getBounds() {
        this.checkDevice();
        int n = OS.GetDeviceCaps(this.handle, 110);
        int n2 = OS.GetDeviceCaps(this.handle, 111);
        return new Rectangle(0, 0, n, n2);
    }

    @Override
    public Rectangle getClientArea() {
        this.checkDevice();
        int n = OS.GetDeviceCaps(this.handle, 8);
        int n2 = OS.GetDeviceCaps(this.handle, 10);
        return new Rectangle(0, 0, n, n2);
    }

    public Rectangle computeTrim(int n, int n2, int n3, int n4) {
        this.checkDevice();
        int n5 = -OS.GetDeviceCaps(this.handle, 112);
        int n6 = -OS.GetDeviceCaps(this.handle, 113);
        int n7 = OS.GetDeviceCaps(this.handle, 8);
        int n8 = OS.GetDeviceCaps(this.handle, 10);
        int n9 = OS.GetDeviceCaps(this.handle, 110);
        int n10 = OS.GetDeviceCaps(this.handle, 111);
        int n11 = n9 - n7;
        int n12 = n10 - n8;
        return new Rectangle(n + n5, n2 + n6, n3 + n11, n4 + n12);
    }

    public PrinterData getPrinterData() {
        return this.data;
    }

    @Override
    protected void checkDevice() {
        if (this.handle == 0L) {
            SWT.error(45);
        }
    }

    @Override
    protected void release() {
        super.release();
        this.data = null;
    }

    @Override
    protected void destroy() {
        if (this.handle != 0L) {
            OS.DeleteDC(this.handle);
        }
        this.handle = 0L;
    }
}

