/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MCHITTESTINFO;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.SYSTEMTIME;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class DateTime
extends Composite {
    static final int MIN_YEAR = 1752;
    static final int MAX_YEAR = 9999;
    boolean doubleClick;
    boolean ignoreSelection;
    SYSTEMTIME lastSystemTime;
    SYSTEMTIME time = new SYSTEMTIME();
    static final long DateTimeProc;
    static final TCHAR DateTimeClass;
    static final long CalendarProc;
    static final TCHAR CalendarClass;
    static final char SINGLE_QUOTE = '\'';
    static final char DAY_FORMAT_CONSTANT = 'd';
    static final char MONTH_FORMAT_CONSTANT = 'M';
    static final char YEAR_FORMAT_CONSTANT = 'y';
    static final char HOURS_FORMAT_CONSTANT = 'h';
    static final char MINUTES_FORMAT_CONSTANT = 'm';
    static final char SECONDS_FORMAT_CONSTANT = 's';
    static final char AMPM_FORMAT_CONSTANT = 't';

    public DateTime(Composite composite, int n) {
        super(composite, DateTime.checkStyle(n));
        if ((this.style & 0x8000) != 0) {
            String string = (this.style & 0x20) != 0 ? this.getCustomShortDateFormat() : this.getCustomShortTimeFormat();
            TCHAR tCHAR = new TCHAR(0, string, true);
            OS.SendMessage(this.handle, 4146, 0L, tCHAR);
        }
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(selectionListener);
        this.addListener(13, typedListener);
        this.addListener(14, typedListener);
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.CallWindowProc(this.windowProc(), l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        n &= 0xFFFFFCFF;
        n = Widget.checkBits(n, 32, 128, 1024, 0, 0, 0);
        if (((n = Widget.checkBits(n, 65536, 32768, 0x10000000, 0, 0, 0)) & 0x20) == 0) {
            n &= 0xFFFFFFFB;
        }
        return n;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = 0;
        int n4 = 0;
        if (n == -1 || n2 == -1) {
            Object object;
            if ((this.style & 0x400) != 0) {
                object = new RECT();
                OS.SendMessage(this.handle, 4105, 0L, (RECT)object);
                n3 = ((RECT)object).right;
                n4 = ((RECT)object).bottom;
            } else {
                if ((this.style & 0x4000) != 0) {
                    int n5 = OS.GetWindowLong(this.handle, -16);
                    OS.SendMessage(this.handle, 4107, 0L, n5 | 4);
                }
                object = new SIZE();
                OS.SendMessage(this.handle, 4111, 0L, (SIZE)object);
                n3 = ((SIZE)object).cx;
                n4 = ((SIZE)object).cy;
                int n6 = OS.GetSystemMetrics(20) + 7;
                n4 = Math.max(n4, n6);
            }
        }
        if (n3 == 0) {
            n3 = 64;
        }
        if (n4 == 0) {
            n4 = 64;
        }
        if (n != -1) {
            n3 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        int n7 = this.getBorderWidthInPixels();
        return new Point(n3 += n7 * 2, n4 += n7 * 2);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFEFD;
        if ((this.style & 0x800) == 0) {
            int n = OS.GetWindowLong(this.handle, -20);
            OS.SetWindowLong(this.handle, -20, n &= 0xFFFDFDFF);
        }
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(5);
    }

    String getCustomShortDateFormat() {
        TCHAR tCHAR = new TCHAR(this.getCodePage(), 80);
        int n = OS.GetLocaleInfo(1024, 4102, tCHAR, 80);
        return n != 0 ? tCHAR.toString(0, n - 1) : "M/yyyy";
    }

    String getCustomShortTimeFormat() {
        int n;
        StringBuilder stringBuilder = new StringBuilder(this.getTimeFormat());
        int n2 = stringBuilder.length();
        boolean bl = false;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            char c = stringBuilder.charAt(n);
            if (c == '\'') {
                bl = !bl;
                continue;
            }
            if (c != 's' || bl) continue;
            for (n3 = n + 1; n3 < n2 && stringBuilder.charAt(n3) == 's'; ++n3) {
            }
            while (n > 0 && stringBuilder.charAt(n) != 'm') {
                --n;
            }
            ++n;
            break;
        }
        if (n < n3) {
            stringBuilder.delete(n, n3);
        }
        return stringBuilder.toString();
    }

    String getTimeFormat() {
        TCHAR tCHAR = new TCHAR(this.getCodePage(), 80);
        int n = OS.GetLocaleInfo(1024, 4099, tCHAR, 80);
        return n > 0 ? tCHAR.toString(0, n - 1) : "h:mm:ss tt";
    }

    public int getDay() {
        this.checkWidget();
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n, 0L, sYSTEMTIME);
        return sYSTEMTIME.wDay;
    }

    public int getHours() {
        this.checkWidget();
        if ((this.style & 0x400) != 0) {
            return this.time.wHour;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n, 0L, sYSTEMTIME);
        return sYSTEMTIME.wHour;
    }

    public int getMinutes() {
        this.checkWidget();
        if ((this.style & 0x400) != 0) {
            return this.time.wMinute;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n, 0L, sYSTEMTIME);
        return sYSTEMTIME.wMinute;
    }

    public int getMonth() {
        this.checkWidget();
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n, 0L, sYSTEMTIME);
        return sYSTEMTIME.wMonth - 1;
    }

    @Override
    String getNameText() {
        return (this.style & 0x80) != 0 ? this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds() : this.getMonth() + 1 + "/" + this.getDay() + "/" + this.getYear();
    }

    public int getSeconds() {
        this.checkWidget();
        if ((this.style & 0x400) != 0) {
            return this.time.wSecond;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n, 0L, sYSTEMTIME);
        return sYSTEMTIME.wSecond;
    }

    public int getYear() {
        this.checkWidget();
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n, 0L, sYSTEMTIME);
        return sYSTEMTIME.wYear;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.lastSystemTime = null;
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(13, selectionListener);
        this.eventTable.unhook(14, selectionListener);
    }

    public void setDate(int n, int n2, int n3) {
        this.checkWidget();
        if (n < 1752 || n > 9999) {
            return;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n4 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n4, 0L, sYSTEMTIME);
        n4 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wYear = (short)n;
        sYSTEMTIME.wMonth = (short)(n2 + 1);
        sYSTEMTIME.wDay = (short)n3;
        OS.SendMessage(this.handle, n4, 0L, sYSTEMTIME);
        this.lastSystemTime = null;
    }

    public void setDay(int n) {
        this.checkWidget();
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n2 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        n2 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wDay = (short)n;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        this.lastSystemTime = null;
    }

    public void setHours(int n) {
        this.checkWidget();
        if (n < 0 || n > 23) {
            return;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n2 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        n2 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wHour = (short)n;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        if ((this.style & 0x400) != 0 && n >= 0 && n <= 23) {
            this.time.wHour = (short)n;
        }
    }

    public void setMinutes(int n) {
        this.checkWidget();
        if (n < 0 || n > 59) {
            return;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n2 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        n2 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wMinute = (short)n;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        if ((this.style & 0x400) != 0 && n >= 0 && n <= 59) {
            this.time.wMinute = (short)n;
        }
    }

    public void setMonth(int n) {
        this.checkWidget();
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n2 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        n2 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wMonth = (short)(n + 1);
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        this.lastSystemTime = null;
    }

    @Override
    public void setOrientation(int n) {
        if ((this.style & 0x400) != 0) {
            super.setOrientation(n);
        }
    }

    public void setSeconds(int n) {
        this.checkWidget();
        if (n < 0 || n > 59) {
            return;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n2 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        n2 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wSecond = (short)n;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        if ((this.style & 0x400) != 0 && n >= 0 && n <= 59) {
            this.time.wSecond = (short)n;
        }
    }

    public void setTime(int n, int n2, int n3) {
        this.checkWidget();
        if (n < 0 || n > 23 || n2 < 0 || n2 > 59 || n3 < 0 || n3 > 59) {
            return;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n4 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n4, 0L, sYSTEMTIME);
        n4 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wHour = (short)n;
        sYSTEMTIME.wMinute = (short)n2;
        sYSTEMTIME.wSecond = (short)n3;
        OS.SendMessage(this.handle, n4, 0L, sYSTEMTIME);
        if ((this.style & 0x400) != 0 && n >= 0 && n <= 23 && n2 >= 0 && n2 <= 59 && n3 >= 0 && n3 <= 59) {
            this.time.wHour = (short)n;
            this.time.wMinute = (short)n2;
            this.time.wSecond = (short)n3;
        }
    }

    public void setYear(int n) {
        this.checkWidget();
        if (n < 1752 || n > 9999) {
            return;
        }
        SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
        int n2 = (this.style & 0x400) != 0 ? 4097 : 4097;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        n2 = (this.style & 0x400) != 0 ? 4098 : 4098;
        sYSTEMTIME.wYear = (short)n;
        OS.SendMessage(this.handle, n2, 0L, sYSTEMTIME);
        this.lastSystemTime = null;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x10000;
        if ((this.style & 0x4000) != 0) {
            n |= 4;
        }
        if ((this.style & 0x400) != 0) {
            return n | 0x10;
        }
        n &= 0xFDFFFFFF;
        if ((this.style & 0x80) != 0) {
            n |= 9;
        }
        if ((this.style & 0x20) != 0) {
            n |= (this.style & 0x10000) != 0 ? 12 : 4;
            if ((this.style & 4) == 0) {
                n |= 1;
            }
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        return (this.style & 0x400) != 0 ? CalendarClass : DateTimeClass;
    }

    @Override
    long windowProc() {
        return (this.style & 0x400) != 0 ? CalendarProc : DateTimeProc;
    }

    @Override
    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        switch (nMHDR.code) {
            case -753: {
                this.display.captureChanged = true;
                break;
            }
            case -749: {
                if (this.ignoreSelection) break;
                SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
                OS.SendMessage(this.handle, 4097, 0L, sYSTEMTIME);
                this.sendSelectionEvent(13);
                break;
            }
            case -759: {
                SYSTEMTIME sYSTEMTIME = new SYSTEMTIME();
                OS.SendMessage(this.handle, 4097, 0L, sYSTEMTIME);
                if (this.lastSystemTime != null && sYSTEMTIME.wDay == this.lastSystemTime.wDay && sYSTEMTIME.wMonth == this.lastSystemTime.wMonth && sYSTEMTIME.wYear == this.lastSystemTime.wYear) break;
                this.sendSelectionEvent(13);
                if ((this.style & 0x80) != 0) break;
                this.lastSystemTime = sYSTEMTIME;
                break;
            }
        }
        return super.wmNotifyChild(nMHDR, l2, l3);
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 13: {
                this.sendSelectionEvent(14);
            }
            case 9: 
            case 27: {
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDBLCLK(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONDBLCLK(l2, l3);
        if (this.isDisposed()) {
            return LRESULT.ZERO;
        }
        if ((this.style & 0x400) != 0) {
            MCHITTESTINFO mCHITTESTINFO = new MCHITTESTINFO();
            mCHITTESTINFO.cbSize = MCHITTESTINFO.sizeof;
            POINT pOINT = new POINT();
            pOINT.x = OS.GET_X_LPARAM(l3);
            pOINT.y = OS.GET_Y_LPARAM(l3);
            mCHITTESTINFO.pt = pOINT;
            long l4 = OS.SendMessage(this.handle, 4110, 0L, mCHITTESTINFO);
            if ((l4 & 0x20001L) == 131073L) {
                this.doubleClick = true;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONDOWN(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        this.doubleClick = false;
        if ((this.style & 0x400) != 0 && (this.style & 0x80000) == 0) {
            OS.SetFocus(this.handle);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONUP(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONUP(l2, l3);
        if (this.isDisposed()) {
            return LRESULT.ZERO;
        }
        if (this.doubleClick) {
            this.sendSelectionEvent(14);
        }
        this.doubleClick = false;
        return lRESULT;
    }

    @Override
    LRESULT WM_TIMER(long l2, long l3) {
        LRESULT lRESULT = super.WM_TIMER(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        this.ignoreSelection = true;
        long l4 = this.callWindowProc(this.handle, 275, l2, l3);
        this.ignoreSelection = false;
        return l4 == 0L ? LRESULT.ZERO : new LRESULT(l4);
    }

    static {
        DateTimeClass = new TCHAR(0, "SysDateTimePick32", true);
        CalendarClass = new TCHAR(0, "SysMonthCal32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, DateTimeClass, wNDCLASS);
        DateTimeProc = wNDCLASS.lpfnWndProc;
        wNDCLASS.hInstance = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS2 = wNDCLASS;
        wNDCLASS2.style &= 0xFFFFBFFF;
        WNDCLASS wNDCLASS3 = wNDCLASS;
        wNDCLASS3.style |= 8;
        OS.RegisterClass(DateTimeClass, wNDCLASS);
        wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, CalendarClass, wNDCLASS);
        CalendarProc = wNDCLASS.lpfnWndProc;
        wNDCLASS.hInstance = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS4 = wNDCLASS;
        wNDCLASS4.style &= 0xFFFFBFFF;
        WNDCLASS wNDCLASS5 = wNDCLASS;
        wNDCLASS5.style |= 8;
        OS.RegisterClass(CalendarClass, wNDCLASS);
    }
}

