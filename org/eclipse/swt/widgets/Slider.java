/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Slider
extends Control {
    int increment;
    int pageIncrement;
    boolean ignoreFocus;
    static final long ScrollBarProc;
    static final TCHAR ScrollBarClass;

    public Slider(Composite composite, int n) {
        super(composite, Slider.checkStyle(n));
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
        switch (n) {
            case 513: 
            case 515: {
                this.display.runDeferredEvents();
            }
        }
        return OS.CallWindowProc(ScrollBarProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        return Widget.checkBits(n, 256, 512, 0, 0, 0, 0);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = this.getBorderWidthInPixels();
        int n4 = n3 * 2;
        int n5 = n3 * 2;
        if ((this.style & 0x100) != 0) {
            n4 += OS.GetSystemMetrics(21) * 10;
            n5 += OS.GetSystemMetrics(3);
        } else {
            n4 += OS.GetSystemMetrics(2);
            n5 += OS.GetSystemMetrics(20) * 10;
        }
        if (n != -1) {
            n4 = n + n3 * 2;
        }
        if (n2 != -1) {
            n5 = n2 + n3 * 2;
        }
        return new Point(n4, n5);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.maybeEnableDarkSystemTheme();
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.increment = 1;
        this.pageIncrement = 10;
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 23;
        sCROLLINFO.nMax = 100;
        sCROLLINFO.nPage = 11;
        OS.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(0);
    }

    @Override
    int defaultForeground() {
        return OS.GetSysColor(15);
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        int n = bl ? 0 : 3;
        OS.EnableScrollBar(this.handle, 2, n);
        this.state = bl ? (this.state &= 0xFFFFFFF7) : (this.state |= 8);
    }

    @Override
    public boolean getEnabled() {
        this.checkWidget();
        return (this.state & 8) == 0;
    }

    public int getIncrement() {
        this.checkWidget();
        return this.increment;
    }

    public int getMaximum() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 1;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        return sCROLLINFO.nMax;
    }

    public int getMinimum() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 1;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        return sCROLLINFO.nMin;
    }

    public int getPageIncrement() {
        this.checkWidget();
        return this.pageIncrement;
    }

    public int getSelection() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 4;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        return sCROLLINFO.nPos;
    }

    public int getThumb() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 2;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        if (sCROLLINFO.nPage != 0) {
            SCROLLINFO sCROLLINFO2 = sCROLLINFO;
            --sCROLLINFO2.nPage;
        }
        return sCROLLINFO.nPage;
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

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5) {
        super.setBoundsInPixels(n, n2, n3, n4, n5);
        if (OS.GetFocus() == this.handle) {
            this.ignoreFocus = true;
            OS.SendMessage(this.handle, 7, 0L, 0L);
            this.ignoreFocus = false;
        }
    }

    public void setIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        this.increment = n;
    }

    public void setMaximum(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 9;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        if (n - sCROLLINFO.nMin - sCROLLINFO.nPage < 1) {
            return;
        }
        sCROLLINFO.nMax = n;
        this.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
    }

    public void setMinimum(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 9;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        if (sCROLLINFO.nMax - n - sCROLLINFO.nPage < 1) {
            return;
        }
        sCROLLINFO.nMin = n;
        this.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
    }

    public void setPageIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        this.pageIncrement = n;
    }

    boolean SetScrollInfo(long l2, int n, SCROLLINFO sCROLLINFO, boolean bl) {
        if ((this.state & 8) != 0) {
            bl = false;
        }
        boolean bl2 = OS.SetScrollInfo(l2, n, sCROLLINFO, bl);
        if ((this.state & 8) != 0) {
            OS.EnableWindow(this.handle, false);
            OS.EnableScrollBar(this.handle, 2, 3);
        }
        if (OS.GetFocus() == this.handle) {
            this.ignoreFocus = true;
            OS.SendMessage(this.handle, 7, 0L, 0L);
            this.ignoreFocus = false;
        }
        return bl2;
    }

    public void setSelection(int n) {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 4;
        sCROLLINFO.nPos = n;
        this.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
    }

    public void setThumb(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 11;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        sCROLLINFO.nPage = n;
        if (sCROLLINFO.nPage != 0) {
            SCROLLINFO sCROLLINFO2 = sCROLLINFO;
            ++sCROLLINFO2.nPage;
        }
        this.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
    }

    public void setValues(int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkWidget();
        if (n2 < 0) {
            return;
        }
        if (n3 < 0) {
            return;
        }
        if (n4 < 1) {
            return;
        }
        if (n5 < 1) {
            return;
        }
        if (n6 < 1) {
            return;
        }
        this.increment = n5;
        this.pageIncrement = n6;
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 15;
        sCROLLINFO.nPos = n;
        sCROLLINFO.nMin = n2;
        sCROLLINFO.nMax = n3;
        sCROLLINFO.nPage = n4;
        if (sCROLLINFO.nPage != 0) {
            SCROLLINFO sCROLLINFO2 = sCROLLINFO;
            ++sCROLLINFO2.nPage;
        }
        this.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
    }

    @Override
    int widgetExtStyle() {
        int n = super.widgetExtStyle();
        if ((this.style & 0x800) != 0) {
            n &= 0xFFFFFDFF;
        }
        return n;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x10000;
        if ((this.style & 0x800) != 0) {
            n &= 0xFF7FFFFF;
        }
        if ((this.style & 0x100) != 0) {
            return n | 0;
        }
        return n | 1;
    }

    @Override
    TCHAR windowClass() {
        return ScrollBarClass;
    }

    @Override
    long windowProc() {
        return ScrollBarProc;
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 0x200) != 0) {
            return lRESULT;
        }
        if ((this.style & 0x8000000) != 0) {
            switch ((int)l2) {
                case 37: 
                case 39: {
                    int n = l2 == 37L ? 39 : 37;
                    long l4 = this.callWindowProc(this.handle, 256, n, l3);
                    return new LRESULT(l4);
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDBLCLK(long l2, long l3) {
        int n = OS.GetWindowLong(this.handle, -16);
        int n2 = n & 0xFFFEFFFF;
        OS.SetWindowLong(this.handle, -16, n2);
        LRESULT lRESULT = super.WM_LBUTTONDBLCLK(l2, l3);
        if (this.isDisposed()) {
            return LRESULT.ZERO;
        }
        OS.SetWindowLong(this.handle, -16, n);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if (OS.GetCapture() == this.handle) {
            OS.ReleaseCapture();
        }
        if (!this.sendMouseEvent(4, 1, this.handle, l3)) {
            return LRESULT.ZERO;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        int n = OS.GetWindowLong(this.handle, -16);
        int n2 = n & 0xFFFEFFFF;
        OS.SetWindowLong(this.handle, -16, n2);
        LRESULT lRESULT = super.WM_LBUTTONDOWN(l2, l3);
        if (this.isDisposed()) {
            return LRESULT.ZERO;
        }
        OS.SetWindowLong(this.handle, -16, n);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if (OS.GetCapture() == this.handle) {
            OS.ReleaseCapture();
        }
        if (!this.sendMouseEvent(4, 1, this.handle, l3)) {
            return LRESULT.ONE;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        if (this.ignoreFocus) {
            return null;
        }
        return super.WM_SETFOCUS(l2, l3);
    }

    @Override
    LRESULT wmScrollChild(long l2, long l3) {
        int n = OS.LOWORD(l2);
        if (n == 8) {
            return null;
        }
        Event event = new Event();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 21;
        OS.GetScrollInfo(this.handle, 2, sCROLLINFO);
        sCROLLINFO.fMask = 4;
        switch (n) {
            case 4: {
                event.detail = 0;
                sCROLLINFO.nPos = sCROLLINFO.nTrackPos;
                break;
            }
            case 5: {
                event.detail = 1;
                sCROLLINFO.nPos = sCROLLINFO.nTrackPos;
                break;
            }
            case 6: {
                event.detail = 0x1000007;
                sCROLLINFO.nPos = sCROLLINFO.nMin;
                break;
            }
            case 7: {
                event.detail = 0x1000008;
                sCROLLINFO.nPos = sCROLLINFO.nMax;
                break;
            }
            case 1: {
                event.detail = 0x1000002;
                SCROLLINFO sCROLLINFO2 = sCROLLINFO;
                sCROLLINFO2.nPos += this.increment;
                break;
            }
            case 0: {
                event.detail = 0x1000001;
                sCROLLINFO.nPos = Math.max(sCROLLINFO.nMin, sCROLLINFO.nPos - this.increment);
                break;
            }
            case 3: {
                event.detail = 0x1000006;
                SCROLLINFO sCROLLINFO3 = sCROLLINFO;
                sCROLLINFO3.nPos += this.pageIncrement;
                break;
            }
            case 2: {
                event.detail = 0x1000005;
                sCROLLINFO.nPos = Math.max(sCROLLINFO.nMin, sCROLLINFO.nPos - this.pageIncrement);
            }
        }
        OS.SetScrollInfo(this.handle, 2, sCROLLINFO, true);
        this.sendSelectionEvent(13, event, true);
        return null;
    }

    static {
        ScrollBarClass = new TCHAR(0, "SCROLLBAR", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, ScrollBarClass, wNDCLASS);
        ScrollBarProc = wNDCLASS.lpfnWndProc;
    }
}

