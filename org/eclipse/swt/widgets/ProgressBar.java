/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

public class ProgressBar
extends Control {
    static final int DELAY = 100;
    static final int TIMER_ID = 100;
    static final int MINIMUM_WIDTH = 100;
    static final long ProgressBarProc;
    static final TCHAR ProgressBarClass;

    public ProgressBar(Composite composite, int n) {
        super(composite, ProgressBar.checkStyle(n));
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.CallWindowProc(ProgressBarProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        return Widget.checkBits(n |= 0x80000, 256, 512, 0, 0, 0, 0);
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
        if (this.display.progressbarUseColors) {
            char[] cArray = new char[]{'\u0000'};
            OS.SetWindowTheme(this.handle, cArray, cArray);
        }
        this.startTimer();
    }

    @Override
    int defaultForeground() {
        return OS.GetSysColor(13);
    }

    public int getMaximum() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1031, 0L, 0L);
    }

    public int getMinimum() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1031, 1L, 0L);
    }

    public int getSelection() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1032, 0L, 0L);
    }

    public int getState() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 1041, 0L, 0L);
        switch (n) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 4;
            }
        }
        return 0;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.stopTimer();
    }

    void startTimer() {
        if ((this.style & 2) != 0) {
            int n = OS.GetWindowLong(this.handle, -16);
            if ((n & 8) == 0) {
                OS.SetTimer(this.handle, 100L, 100, 0L);
            } else {
                OS.SendMessage(this.handle, 1034, 1L, 100L);
            }
        }
    }

    void stopTimer() {
        if ((this.style & 2) != 0) {
            int n = OS.GetWindowLong(this.handle, -16);
            if ((n & 8) == 0) {
                OS.KillTimer(this.handle, 100L);
            } else {
                OS.SendMessage(this.handle, 1034, 0L, 0L);
            }
        }
    }

    @Override
    void setBackgroundPixel(int n) {
        if (n == -1) {
            n = -16777216;
        }
        OS.SendMessage(this.handle, 8193, 0L, n);
    }

    @Override
    void setForegroundPixel(int n) {
        if (n == -1) {
            n = -16777216;
        }
        OS.SendMessage(this.handle, 1033, 0L, n);
    }

    public void setMaximum(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 1031, 1L, 0L);
        if (0 <= n2 && n2 < n) {
            OS.SendMessage(this.handle, 1030, (long)n2, n);
        }
    }

    public void setMinimum(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 1031, 0L, 0L);
        if (0 <= n && n < n2) {
            OS.SendMessage(this.handle, 1030, (long)n, n2);
        }
    }

    public void setSelection(int n) {
        this.checkWidget();
        OS.SendMessage(this.handle, 1026, (long)n, 0L);
        long l2 = OS.SendMessage(this.handle, 1041, 0L, 0L);
        if (l2 != 1L) {
            OS.SendMessage(this.handle, 1026, (long)n, 0L);
        }
    }

    public void setState(int n) {
        this.checkWidget();
        switch (n) {
            case 0: {
                OS.SendMessage(this.handle, 1040, 1L, 0L);
                break;
            }
            case 1: {
                OS.SendMessage(this.handle, 1040, 2L, 0L);
                break;
            }
            case 4: {
                OS.SendMessage(this.handle, 1040, 3L, 0L);
            }
        }
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle();
        if ((this.style & 0x10000) != 0) {
            n |= 1;
        }
        if ((this.style & 0x200) != 0) {
            n |= 4;
        }
        if ((this.style & 2) != 0) {
            n |= 8;
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        return ProgressBarClass;
    }

    @Override
    long windowProc() {
        return ProgressBarProc;
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETDLGCODE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return new LRESULT(256L);
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 2) != 0 && !OS.IsAppThemed()) {
            int n;
            this.forceResize();
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            int n2 = n = OS.GetWindowLong(this.handle, -16);
            n = rECT.right - rECT.left < 100 ? (n &= 0xFFFFFFF7) : (n |= 8);
            if (n != n2) {
                this.stopTimer();
                OS.SetWindowLong(this.handle, -16, n);
                this.startTimer();
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_TIMER(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_TIMER(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 2) != 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 8) == 0 && l2 == 100L) {
            OS.SendMessage(this.handle, 1029, 0L, 0L);
        }
        return lRESULT;
    }

    static {
        ProgressBarClass = new TCHAR(0, "msctls_progress32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, ProgressBarClass, wNDCLASS);
        ProgressBarProc = wNDCLASS.lpfnWndProc;
        wNDCLASS.hInstance = OS.GetModuleHandle(null);
        WNDCLASS wNDCLASS2 = wNDCLASS;
        wNDCLASS2.style &= 0xFFFFBFFF;
        WNDCLASS wNDCLASS3 = wNDCLASS;
        wNDCLASS3.style |= 8;
        OS.RegisterClass(ProgressBarClass, wNDCLASS);
    }
}

