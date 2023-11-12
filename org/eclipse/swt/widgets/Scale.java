/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Scale
extends Control {
    boolean ignoreResize;
    boolean ignoreSelection;
    static final long TrackBarProc;
    static final TCHAR TrackBarClass;
    boolean createdAsRTL;

    public Scale(Composite composite, int n) {
        super(composite, Scale.checkStyle(n));
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
        return OS.CallWindowProc(TrackBarProc, l2, n, l3, l4);
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
        RECT rECT = new RECT();
        OS.SendMessage(this.handle, 1049, 0L, rECT);
        if ((this.style & 0x100) != 0) {
            n4 += OS.GetSystemMetrics(21) * 10;
            int n6 = OS.GetSystemMetrics(3);
            n5 += rECT.top * 2 + n6 + n6 / 3;
        } else {
            int n7 = OS.GetSystemMetrics(2);
            n4 += rECT.left * 2 + n7 + n7 / 3;
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
        this.state |= 0x300;
        OS.SendMessage(this.handle, 1032, 0L, 100L);
        OS.SendMessage(this.handle, 1045, 0L, 10L);
        OS.SendMessage(this.handle, 1044, 10L, 0L);
        this.createdAsRTL = (this.style & 0x4000000) != 0;
    }

    @Override
    int defaultForeground() {
        return OS.GetSysColor(15);
    }

    public int getIncrement() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1048, 0L, 0L);
    }

    public int getMaximum() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1026, 0L, 0L);
    }

    public int getMinimum() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1025, 0L, 0L);
    }

    public int getPageIncrement() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1046, 0L, 0L);
    }

    public int getSelection() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1024, 0L, 0L);
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
    void setBackgroundImage(long l2) {
        super.setBackgroundImage(l2);
        this.ignoreResize = true;
        OS.SendMessage(this.handle, 5, 0L, 0L);
        this.ignoreResize = false;
    }

    @Override
    void setBackgroundPixel(int n) {
        super.setBackgroundPixel(n);
        this.ignoreResize = true;
        OS.SendMessage(this.handle, 5, 0L, 0L);
        this.ignoreResize = false;
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5, boolean bl) {
        super.setBoundsInPixels(n, n2, n3, n4, n5 &= 0xFFFFFFDF, true);
    }

    public void setIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.handle, 1025, 0L, 0L);
        int n3 = (int)OS.SendMessage(this.handle, 1026, 0L, 0L);
        if (n > n3 - n2) {
            return;
        }
        OS.SendMessage(this.handle, 1047, 0L, n);
    }

    public void setMaximum(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 1025, 0L, 0L);
        if (0 <= n2 && n2 < n) {
            OS.SendMessage(this.handle, 1032, 1L, n);
        }
    }

    public void setMinimum(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 1026, 0L, 0L);
        if (0 <= n && n < n2) {
            OS.SendMessage(this.handle, 1031, 1L, n);
        }
    }

    public void setPageIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.handle, 1025, 0L, 0L);
        int n3 = (int)OS.SendMessage(this.handle, 1026, 0L, 0L);
        if (n > n3 - n2) {
            return;
        }
        OS.SendMessage(this.handle, 1045, 0L, n);
        OS.SendMessage(this.handle, 1044, (long)n, 0L);
    }

    public void setSelection(int n) {
        this.checkWidget();
        OS.SendMessage(this.handle, 1029, 1L, n);
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x10000 | 8 | 1;
        if ((this.style & 0x100) != 0) {
            return n | 0 | 0x400;
        }
        return n | 2;
    }

    @Override
    TCHAR windowClass() {
        return TrackBarClass;
    }

    @Override
    long windowProc() {
        return TrackBarProc;
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 37: 
            case 39: {
                boolean bl;
                boolean bl2 = bl = (this.style & 0x4000000) != 0;
                if (bl == this.createdAsRTL) break;
                long l4 = this.callWindowProc(this.handle, 256, l2 == 39L ? 37L : 39L, l3);
                return new LRESULT(l4);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEWHEEL(long l2, long l3) {
        LRESULT lRESULT = super.WM_MOUSEWHEEL(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        int n = (int)OS.SendMessage(this.handle, 1024, 0L, 0L);
        this.ignoreSelection = true;
        long l4 = this.callWindowProc(this.handle, 522, l2, l3);
        this.ignoreSelection = false;
        int n2 = (int)OS.SendMessage(this.handle, 1024, 0L, 0L);
        if (n != n2) {
            this.sendSelectionEvent(13, null, true);
        }
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_PAINT(long l2, long l3) {
        boolean bl;
        if ((this.state & 0x1000) != 0) {
            return LRESULT.ZERO;
        }
        boolean bl2 = bl = this.findBackgroundControl() != null;
        if (!bl && OS.IsAppThemed()) {
            Control control = this.findThemeControl();
            boolean bl3 = bl = control != null;
        }
        if (bl) {
            boolean bl4;
            boolean bl5 = bl4 = this.getDrawing() && OS.IsWindowVisible(this.handle);
            if (bl4) {
                OS.SendMessage(this.handle, 11, 0L, 0L);
            }
            this.ignoreResize = true;
            OS.SendMessage(this.handle, 5, 0L, 0L);
            this.ignoreResize = false;
            if (bl4) {
                OS.SendMessage(this.handle, 11, 1L, 0L);
                OS.InvalidateRect(this.handle, null, false);
            }
        }
        return super.WM_PAINT(l2, l3);
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        if (this.ignoreResize) {
            return null;
        }
        return super.WM_SIZE(l2, l3);
    }

    @Override
    LRESULT wmScrollChild(long l2, long l3) {
        int n = OS.LOWORD(l2);
        switch (n) {
            case 4: 
            case 8: {
                return null;
            }
        }
        if (!this.ignoreSelection) {
            Event event = new Event();
            this.sendSelectionEvent(13, event, true);
        }
        return null;
    }

    static {
        TrackBarClass = new TCHAR(0, "msctls_trackbar32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, TrackBarClass, wNDCLASS);
        TrackBarProc = wNDCLASS.lpfnWndProc;
        wNDCLASS.hInstance = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS2 = wNDCLASS;
        wNDCLASS2.style &= 0xFFFFBFFF;
        WNDCLASS wNDCLASS3 = wNDCLASS;
        wNDCLASS3.style |= 8;
        OS.RegisterClass(TrackBarClass, wNDCLASS);
    }
}

