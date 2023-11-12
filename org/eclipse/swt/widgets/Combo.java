/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SegmentListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.COMBOBOXINFO;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Combo
extends Composite {
    boolean noSelection;
    boolean ignoreDefaultSelection;
    boolean ignoreCharacter;
    boolean ignoreModify;
    boolean ignoreResize;
    boolean lockText;
    int scrollWidth;
    int visibleCount;
    long cbtHook;
    String[] items = new String[0];
    int[] segments;
    int clearSegmentsCount = 0;
    boolean stateFlagsUsable;
    static final char LTR_MARK = '\u200e';
    static final char RTL_MARK = '\u200f';
    static final int VISIBLE_COUNT = 5;
    public static final int LIMIT;
    static final int CBID_LIST = 1000;
    static final int CBID_EDIT = 1001;
    static long EditProc;
    static long ListProc;
    static final long ComboProc;
    static final TCHAR ComboClass;
    static final int stateFlagsOffset;
    static final int stateFlagsFirstPaint = 0x2000000;
    private static final Pattern CTRL_BS_PATTERN;

    public Combo(Composite composite, int n) {
        super(composite, Combo.checkStyle(n));
        this.style |= 0x100;
    }

    public void add(String string) {
        TCHAR tCHAR;
        int n;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((n = (int)OS.SendMessage(this.handle, 323, 0L, tCHAR = new TCHAR(this.getCodePage(), string, true))) == -1) {
            this.error(14);
        }
        if (n == -2) {
            this.error(14);
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(tCHAR.chars, true);
        }
    }

    public void add(String string, int n) {
        TCHAR tCHAR;
        int n2;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        int n3 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (0 > n || n > n3) {
            this.error(6);
        }
        if ((n2 = (int)OS.SendMessage(this.handle, 330, (long)n, tCHAR = new TCHAR(this.getCodePage(), string, true))) == -2 || n2 == -1) {
            this.error(14);
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(tCHAR.chars, true);
        }
    }

    public void addModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(modifyListener);
        this.addListener(24, typedListener);
    }

    public void addSegmentListener(SegmentListener segmentListener) {
        this.checkWidget();
        if (segmentListener == null) {
            this.error(4);
        }
        this.addListener(49, new TypedListener(segmentListener));
        int n = -1;
        if (!this.noSelection) {
            n = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
        }
        this.clearSegments(true);
        this.applyEditSegments();
        this.applyListSegments();
        if (n != -1) {
            OS.SendMessage(this.handle, 334, (long)n, 0L);
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

    public void addVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(verifyListener);
        this.addListener(25, typedListener);
    }

    void applyEditSegments() {
        int n;
        int n2;
        int n3;
        this.clearSegmentsCount = n3 = this.clearSegmentsCount - 1;
        if (n3 != 0) {
            return;
        }
        if (!this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0) {
            return;
        }
        long l2 = OS.GetDlgItem(this.handle, 1001);
        int n4 = OS.GetWindowTextLength(l2);
        char[] cArray = new char[n4 + 1];
        if (n4 > 0) {
            OS.GetWindowText(l2, cArray, n4 + 1);
        }
        String string = new String(cArray, 0, n4);
        this.segments = null;
        Event event = this.getSegments(string);
        if (event == null || event.segments == null) {
            return;
        }
        this.segments = event.segments;
        int n5 = this.segments.length;
        if (n5 == 0) {
            return;
        }
        char[] cArray2 = event.segmentsChars;
        int n6 = (int)OS.SendMessage(l2, 213, 0L, 0L) & Integer.MAX_VALUE;
        OS.SendMessage(l2, 197, (long)(n6 + Math.min(n5, LIMIT - n6)), 0L);
        char[] cArray3 = new char[(n4 += n5) + 1];
        int n7 = 0;
        int n8 = 0;
        int n9 = n2 = this.getOrientation() == 0x4000000 ? 8207 : 8206;
        while (n7 < n4) {
            if (n8 < n5 && n7 - n8 == this.segments[n8]) {
                n = cArray2 != null && cArray2.length > n8 ? cArray2[n8] : n2;
                cArray3[n7++] = n;
                ++n8;
                continue;
            }
            if (string == null) continue;
            cArray3[n7] = string.charAt(n7++ - n8);
        }
        while (n8 < n5) {
            this.segments[n8] = n7 - n8;
            n = cArray2 != null && cArray2.length > n8 ? cArray2[n8] : n2;
            cArray3[n7++] = n;
            ++n8;
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(l2, 176, nArray, nArray2);
        boolean bl = this.ignoreCharacter;
        boolean bl2 = this.ignoreModify;
        boolean bl3 = true;
        this.ignoreModify = true;
        this.ignoreCharacter = true;
        cArray3[n4] = '\u0000';
        OS.SendMessage(l2, 177, 0L, -1L);
        long l3 = OS.SendMessage(l2, 198, 0L, 0L);
        OS.SendMessage(l2, 194, l3, cArray3);
        nArray[0] = this.translateOffset(nArray[0]);
        nArray2[0] = this.translateOffset(nArray2[0]);
        if (cArray2 != null && cArray2.length > 0) {
            int n10 = this.state & 0x400000;
            if (cArray2[0] == '\u202b') {
                super.updateTextDirection(0x4000000);
            } else if (cArray2[0] == '\u202a') {
                super.updateTextDirection(0x2000000);
            }
            this.state |= n10;
        }
        OS.SendMessage(l2, 177, (long)nArray[0], nArray2[0]);
        this.ignoreCharacter = bl;
        this.ignoreModify = bl2;
    }

    void applyListSegments() {
        boolean bl;
        int n = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (n == -1) {
            return;
        }
        boolean bl2 = bl = this.items.length != n;
        if (bl) {
            this.items = new String[n];
        }
        int n2 = this.items.length;
        int n3 = -1;
        int n4 = this.getCodePage();
        if (!this.noSelection) {
            n3 = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
        }
        while (n2-- > 0) {
            String string;
            TCHAR tCHAR = null;
            if (bl) {
                int n5 = (int)OS.SendMessage(this.handle, 329, (long)n2, 0L);
                if (n5 == -1) {
                    this.error(1);
                }
                if (OS.SendMessage(this.handle, 328, (long)n2, tCHAR = new TCHAR(n4, n5 + 1)) == -1L) {
                    return;
                }
                string = this.items[n2] = tCHAR.toString(0, n5);
            } else {
                string = this.items[n2];
            }
            if (OS.SendMessage(this.handle, 324, (long)n2, 0L) == -1L) {
                return;
            }
            if (tCHAR == null) {
                tCHAR = new TCHAR(n4, string, true);
            }
            if (OS.SendMessage(this.handle, 330, (long)n2, tCHAR) != -1L) continue;
            return;
        }
        if (n3 != -1) {
            OS.SendMessage(this.handle, 334, (long)n3, 0L);
        }
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if (l2 == this.handle) {
            switch (n) {
                case 5: {
                    this.ignoreResize = true;
                    boolean bl = this.lockText;
                    if ((this.style & 8) == 0) {
                        this.lockText = true;
                    }
                    long l5 = OS.CallWindowProc(ComboProc, l2, n, l3, l4);
                    if ((this.style & 8) == 0) {
                        this.lockText = bl;
                    }
                    this.ignoreResize = false;
                    return l5;
                }
            }
            return OS.CallWindowProc(ComboProc, l2, n, l3, l4);
        }
        long l6 = OS.GetDlgItem(this.handle, 1001);
        if (l2 == l6) {
            if (this.lockText && n == 12) {
                long l7 = OS.GetProcessHeap();
                int n2 = OS.GetWindowTextLength(this.handle);
                char[] cArray = new char[n2 + 1];
                OS.GetWindowText(this.handle, cArray, n2 + 1);
                int n3 = cArray.length * 2;
                long l8 = OS.HeapAlloc(l7, 8, n3);
                OS.MoveMemory(l8, cArray, n3);
                long l9 = OS.CallWindowProc(EditProc, l6, n, l3, l8);
                OS.HeapFree(l7, 0, l8);
                return l9;
            }
            return OS.CallWindowProc(EditProc, l2, n, l3, l4);
        }
        long l10 = OS.GetDlgItem(this.handle, 1000);
        if (l2 == l10) {
            return OS.CallWindowProc(ListProc, l2, n, l3, l4);
        }
        return OS.DefWindowProc(l2, n, l3, l4);
    }

    long CBTProc(long l2, long l3, long l4) {
        int n;
        char[] cArray;
        String string;
        if ((int)l2 == 3 && ((string = new String(cArray = new char[128], 0, n = OS.GetClassName(l3, cArray, cArray.length))).equals("Edit") || string.equals("EDIT"))) {
            int n2 = OS.GetWindowLong(l3, -16);
            OS.SetWindowLong(l3, -16, n2 & 0xFFFFFEFF);
        }
        return OS.CallNextHookEx(this.cbtHook, (int)l2, l3, l4);
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    static int checkStyle(int n) {
        n &= 0xFFFFF7FF;
        n &= 0xFFFFFCFF;
        if (((n = Widget.checkBits(n, 4, 64, 0, 0, 0, 0)) & 0x40) != 0) {
            return n & 0xFFFFFFF7;
        }
        return n;
    }

    void clearSegments(boolean bl) {
        if (this.clearSegmentsCount++ != 0) {
            return;
        }
        if (this.segments == null) {
            return;
        }
        int n = this.segments.length;
        if (n == 0) {
            return;
        }
        long l2 = OS.GetDlgItem(this.handle, 1001);
        int n2 = (int)OS.SendMessage(l2, 213, 0L, 0L) & Integer.MAX_VALUE;
        if (n2 < LIMIT) {
            OS.SendMessage(l2, 197, (long)Math.max(1, n2 - n), 0L);
        }
        if (!bl) {
            this.segments = null;
            return;
        }
        boolean bl2 = this.ignoreCharacter;
        boolean bl3 = this.ignoreModify;
        boolean bl4 = true;
        this.ignoreModify = true;
        this.ignoreCharacter = true;
        int n3 = OS.GetWindowTextLength(l2);
        int n4 = this.getCodePage();
        TCHAR tCHAR = new TCHAR(n4, n3 + 1);
        if (n3 > 0) {
            OS.GetWindowText(l2, tCHAR, n3 + 1);
        }
        tCHAR = this.deprocessText(tCHAR, 0, -1, true);
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(l2, 176, nArray, nArray2);
        nArray[0] = this.untranslateOffset(nArray[0]);
        nArray2[0] = this.untranslateOffset(nArray2[0]);
        this.segments = null;
        OS.SendMessage(l2, 177, 0L, -1L);
        long l3 = OS.SendMessage(l2, 198, 0L, 0L);
        OS.SendMessage(l2, 194, l3, tCHAR);
        OS.SendMessage(l2, 177, (long)nArray[0], nArray2[0]);
        this.ignoreCharacter = bl2;
        this.ignoreModify = bl3;
    }

    public void clearSelection() {
        this.checkWidget();
        OS.SendMessage(this.handle, 322, 0L, -1L);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        int n3;
        long l2;
        this.checkWidget();
        int n4 = 0;
        int n5 = 0;
        if (n == -1) {
            long l3 = 0L;
            l2 = OS.GetDC(this.handle);
            long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l3 = OS.SelectObject(l2, l4);
            }
            int n6 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
            RECT rECT = new RECT();
            int n7 = 3072;
            if ((this.style & 8) == 0) {
                n7 |= 0x2000;
            }
            int n8 = OS.GetWindowTextLength(this.handle);
            char[] cArray = new char[n8 + 1];
            OS.GetWindowText(this.handle, cArray, n8 + 1);
            OS.DrawText(l2, cArray, n8, rECT, n7);
            n4 = Math.max(n4, rECT.right - rECT.left);
            if ((this.style & 0x100) != 0) {
                n4 = Math.max(n4, this.scrollWidth);
            } else {
                for (int i = 0; i < n6; ++i) {
                    int n9;
                    n8 = (int)OS.SendMessage(this.handle, 329, (long)i, 0L);
                    if (n8 == -1) continue;
                    if (n8 + 1 > cArray.length) {
                        cArray = new char[n8 + 1];
                    }
                    if ((n9 = (int)OS.SendMessage(this.handle, 328, (long)i, cArray)) == -1) continue;
                    OS.DrawText(l2, cArray, n8, rECT, n7);
                    n4 = Math.max(n4, rECT.right - rECT.left);
                }
            }
            if (l4 != 0L) {
                OS.SelectObject(l2, l3);
            }
            OS.ReleaseDC(this.handle, l2);
        }
        if (n2 == -1 && (this.style & 0x40) != 0) {
            int n10 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
            n3 = (int)OS.SendMessage(this.handle, 340, 0L, 0L);
            n5 = n10 * n3;
        }
        if (n4 == 0) {
            n4 = 64;
        }
        if (n5 == 0) {
            n5 = 64;
        }
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n5 = n2;
        }
        if ((this.style & 8) != 0) {
            n4 += 8;
        } else {
            long l5 = OS.GetDlgItem(this.handle, 1001);
            if (l5 != 0L) {
                l2 = OS.SendMessage(l5, 212, 0L, 0L);
                int n11 = OS.LOWORD(l2) + OS.HIWORD(l2);
                n4 += n11 + 3;
            }
        }
        COMBOBOXINFO cOMBOBOXINFO = new COMBOBOXINFO();
        cOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
        if ((this.style & 0x40) == 0 && OS.GetComboBoxInfo(this.handle, cOMBOBOXINFO)) {
            n4 += cOMBOBOXINFO.itemLeft + (cOMBOBOXINFO.buttonRight - cOMBOBOXINFO.buttonLeft);
            n5 = cOMBOBOXINFO.buttonBottom - cOMBOBOXINFO.buttonTop + cOMBOBOXINFO.buttonTop * 2;
        } else {
            n3 = OS.GetSystemMetrics(45);
            n4 += OS.GetSystemMetrics(2) + n3 * 2;
            int n12 = (int)OS.SendMessage(this.handle, 340, -1L, 0L);
            n5 = (this.style & 4) != 0 ? n12 + 6 : (n5 += n12 + 10);
        }
        if ((this.style & 0x40) != 0 && (this.style & 0x100) != 0) {
            n5 += OS.GetSystemMetrics(3);
        }
        return new Point(n4, n5);
    }

    public void copy() {
        this.checkWidget();
        OS.SendMessage(this.handle, 769, 0L, 0L);
    }

    @Override
    void createHandle() {
        long l2;
        if ((this.style & 0x48) != 0) {
            super.createHandle();
        } else {
            int n = OS.GetCurrentThreadId();
            Callback callback = new Callback(this, "CBTProc", 3);
            this.cbtHook = OS.SetWindowsHookEx(5, callback.getAddress(), 0L, n);
            super.createHandle();
            if (this.cbtHook != 0L) {
                OS.UnhookWindowsHookEx(this.cbtHook);
            }
            this.cbtHook = 0L;
            callback.dispose();
        }
        this.state &= 0xFFFFFEFD;
        if (this.display.comboUseDarkTheme) {
            OS.AllowDarkModeForWindow(this.handle, true);
            OS.SetWindowTheme(this.handle, "CFD\u0000".toCharArray(), null);
        }
        this.stateFlagsUsable = this.stateFlagsTest();
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l3 != 0L) {
            EditProc = OS.GetWindowLongPtr(l3, -4);
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            ListProc = OS.GetWindowLongPtr(l2, -4);
        }
        if ((this.style & 0x40) != 0) {
            int n = 52;
            OS.SetWindowPos(this.handle, 0L, 0, 0, 16383, 16383, 52);
            OS.SetWindowPos(this.handle, 0L, 0, 0, 0, 0, 52);
        }
    }

    @Override
    void createWidget() {
        int n;
        super.createWidget();
        this.visibleCount = 5;
        if ((this.style & 0x40) == 0 && (n = (int)OS.SendMessage(this.handle, 340, 0L, 0L)) != -1 && n != 0) {
            int n2 = 0;
            long l2 = OS.MonitorFromWindow(this.handle, 2);
            MONITORINFO mONITORINFO = new MONITORINFO();
            mONITORINFO.cbSize = MONITORINFO.sizeof;
            OS.GetMonitorInfo(l2, mONITORINFO);
            n2 = (mONITORINFO.rcWork_bottom - mONITORINFO.rcWork_top) / 3;
            this.visibleCount = Math.max(this.visibleCount, n2 / n);
        }
    }

    public void cut() {
        this.checkWidget();
        if ((this.style & 8) != 0) {
            return;
        }
        OS.SendMessage(this.handle, 768, 0L, 0L);
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(5);
    }

    TCHAR deprocessText(TCHAR tCHAR, int n, int n2, boolean bl) {
        if (tCHAR == null || this.segments == null) {
            return tCHAR;
        }
        int n3 = tCHAR.length();
        if (n3 == 0) {
            return tCHAR;
        }
        int n4 = this.segments.length;
        if (n4 == 0) {
            return tCHAR;
        }
        if (n < 0) {
            n = 0;
        }
        char[] cArray = tCHAR.chars;
        if (tCHAR.chars[n3 - 1] == '\u0000') {
            --n3;
        }
        if (n2 == -1) {
            n2 = n3;
        }
        if (n2 > this.segments[0] && n <= this.segments[n4 - 1]) {
            int n5 = 0;
            while (n - n5 > this.segments[n5]) {
                ++n5;
            }
            int n6 = n5;
            for (int i = n; i < n2; ++i) {
                if (n6 < n4 && i - n6 == this.segments[n6]) {
                    ++n6;
                    continue;
                }
                cArray[i - n6 + n5] = cArray[i];
            }
            n3 = n2 - n - n6 + n5;
        }
        if (n != 0 || n2 != n3) {
            char[] cArray2 = new char[n3];
            System.arraycopy(cArray, n, cArray2, 0, n3);
            return new TCHAR(this.getCodePage(), cArray2, bl);
        }
        return tCHAR;
    }

    @Override
    void deregister() {
        long l2;
        super.deregister();
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l3 != 0L) {
            this.display.removeControl(l3);
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            this.display.removeControl(l2);
        }
    }

    public void deselect(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
        if (n != n2) {
            return;
        }
        OS.SendMessage(this.handle, 334, -1L, 0L);
        this.sendEvent(24);
        this.clearSegments(false);
        --this.clearSegmentsCount;
    }

    public void deselectAll() {
        this.checkWidget();
        OS.SendMessage(this.handle, 334, -1L, 0L);
        this.sendEvent(24);
        this.clearSegments(false);
        --this.clearSegmentsCount;
    }

    @Override
    boolean dragDetect(long l2, int n, int n2, boolean bl, boolean[] blArray, boolean[] blArray2) {
        long l3;
        if (bl && (this.style & 8) == 0 && (l3 = OS.GetDlgItem(this.handle, 1001)) != 0L) {
            long l4;
            int n3;
            int[] nArray = new int[]{0};
            int[] nArray2 = new int[]{0};
            OS.SendMessage(this.handle, 320, nArray, nArray2);
            if (nArray[0] != nArray2[0] && nArray[0] <= (n3 = OS.LOWORD(OS.SendMessage(l3, 215, 0L, l4 = OS.MAKELPARAM(n, n2)))) && n3 < nArray2[0] && super.dragDetect(l2, n, n2, bl, blArray, blArray2)) {
                if (blArray2 != null) {
                    blArray2[0] = true;
                }
                return true;
            }
            return false;
        }
        return super.dragDetect(l2, n, n2, bl, blArray, blArray2);
    }

    public Point getCaretLocation() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getCaretLocationInPixels());
    }

    Point getCaretLocationInPixels() {
        Object object;
        int n = this.translateOffset(this.getCaretPosition());
        long l2 = OS.GetDlgItem(this.handle, 1001);
        long l3 = OS.SendMessage(l2, 214, (long)n, 0L);
        if (l3 == -1L) {
            l3 = 0L;
            if (n >= OS.GetWindowTextLength(l2)) {
                object = new int[]{0};
                int[] nArray = new int[]{0};
                OS.SendMessage(l2, 176, (int[])object, nArray);
                OS.SendMessage(l2, 177, (long)n, n);
                boolean bl = true;
                this.ignoreModify = true;
                this.ignoreCharacter = true;
                OS.SendMessage(l2, 194, 0L, new char[]{' ', '\u0000'});
                l3 = OS.SendMessage(l2, 214, (long)n, 0L);
                OS.SendMessage(l2, 177, (long)n, n + 1);
                OS.SendMessage(l2, 194, 0L, new char[1]);
                boolean bl2 = false;
                this.ignoreModify = false;
                this.ignoreCharacter = false;
                OS.SendMessage(l2, 177, (long)object[0], (long)object[0]);
                OS.SendMessage(l2, 177, (long)object[0], nArray[0]);
            }
        }
        object = new POINT();
        ((POINT)object).x = OS.GET_X_LPARAM(l3);
        ((POINT)object).y = OS.GET_Y_LPARAM(l3);
        OS.MapWindowPoints(l2, this.handle, (POINT)object, 1);
        return new Point(((POINT)object).x, ((POINT)object).y);
    }

    public int getCaretPosition() {
        this.checkWidget();
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        long l2 = OS.GetDlgItem(this.handle, 1001);
        OS.SendMessage(l2, 176, nArray, nArray2);
        int n = nArray[0];
        if (nArray[0] != nArray2[0]) {
            POINT pOINT;
            int n2 = OS.GetWindowThreadProcessId(l2, null);
            GUITHREADINFO gUITHREADINFO = new GUITHREADINFO();
            gUITHREADINFO.cbSize = GUITHREADINFO.sizeof;
            if (OS.GetGUIThreadInfo(n2, gUITHREADINFO) && (gUITHREADINFO.hwndCaret == l2 || gUITHREADINFO.hwndCaret == 0L) && OS.GetCaretPos(pOINT = new POINT())) {
                long l3 = OS.SendMessage(l2, 214, (long)nArray2[0], 0L);
                if (l3 == -1L) {
                    long l4 = OS.SendMessage(l2, 214, (long)nArray[0], 0L);
                    int n3 = OS.GET_X_LPARAM(l4);
                    if (pOINT.x > n3) {
                        n = nArray2[0];
                    }
                } else {
                    int n4 = OS.GET_X_LPARAM(l3);
                    if (pOINT.x >= n4) {
                        n = nArray2[0];
                    }
                }
            }
        }
        return this.untranslateOffset(n);
    }

    public String getItem(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 329, (long)n, 0L);
        if (n2 != -1) {
            if (this.hooks(49) || this.filters(49) || (this.state & 0x400000) != 0) {
                return this.items[n];
            }
            char[] cArray = new char[n2 + 1];
            int n3 = (int)OS.SendMessage(this.handle, 328, (long)n, cArray);
            if (n3 != -1) {
                return new String(cArray, 0, n2);
            }
        }
        int n4 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (0 <= n && n < n4) {
            this.error(8);
        }
        this.error(6);
        return "";
    }

    public int getItemCount() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (n == -1) {
            this.error(36);
        }
        return n;
    }

    public int getItemHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getItemHeightInPixels());
    }

    int getItemHeightInPixels() {
        int n = (int)OS.SendMessage(this.handle, 340, 0L, 0L);
        if (n == -1) {
            this.error(11);
        }
        return n;
    }

    public String[] getItems() {
        this.checkWidget();
        int n = this.getItemCount();
        String[] stringArray = new String[n];
        for (int i = 0; i < n; ++i) {
            stringArray[i] = this.getItem(i);
        }
        return stringArray;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public void setListVisible(boolean bl) {
        this.checkWidget();
        OS.SendMessage(this.handle, 335, bl ? 1L : 0L, 0L);
    }

    @Override
    public int getOrientation() {
        return super.getOrientation();
    }

    Event getSegments(String string) {
        int n;
        Event event = null;
        if (this.hooks(49) || this.filters(49)) {
            event = new Event();
            event.text = string;
            this.sendEvent(49, event);
            if (event != null && event.segments != null) {
                int n2;
                int n3 = event.segments.length;
                int n4 = n2 = string == null ? 0 : string.length();
                for (n = 1; n < n3; ++n) {
                    if (event.segments[n] >= event.segments[n - 1] && event.segments[n] <= n2) continue;
                    SWT.error(5);
                }
            }
        }
        if ((this.state & 0x400000) != 0) {
            n = BidiUtil.resolveTextDirection(string);
            if (n == 0) {
                n = (this.style & 0x4000000) != 0 ? 0x4000000 : 0x2000000;
            }
            int[] nArray = null;
            char[] cArray = null;
            if (event == null) {
                event = new Event();
            } else {
                nArray = event.segments;
                cArray = event.segmentsChars;
            }
            int n5 = nArray == null ? 0 : nArray.length;
            event.segments = new int[n5 + 1];
            event.segmentsChars = new char[n5 + 1];
            if (nArray != null) {
                System.arraycopy(nArray, 0, event.segments, 1, n5);
            }
            if (cArray != null) {
                System.arraycopy(cArray, 0, event.segmentsChars, 1, n5);
            }
            event.segments[0] = 0;
            event.segmentsChars[0] = n == 0x4000000 ? 8235 : 8234;
        }
        return event;
    }

    String getSegmentsText(String string, Event event) {
        int n;
        int n2;
        if (string == null || event == null) {
            return string;
        }
        int[] nArray = event.segments;
        if (nArray == null) {
            return string;
        }
        int n3 = nArray.length;
        if (n3 == 0) {
            return string;
        }
        char[] cArray = event.segmentsChars;
        int n4 = string.length();
        char[] cArray2 = new char[n4];
        string.getChars(0, n4, cArray2, 0);
        char[] cArray3 = new char[n4 + n3];
        int n5 = 0;
        int n6 = 0;
        int n7 = n2 = this.getOrientation() == 0x4000000 ? 8207 : 8206;
        while (n5 < n4) {
            if (n6 < n3 && n5 == nArray[n6]) {
                n = cArray != null && cArray.length > n6 ? cArray[n6] : n2;
                cArray3[n5 + n6++] = n;
                continue;
            }
            cArray3[n5 + n6] = cArray2[n5++];
        }
        while (n6 < n3) {
            nArray[n6] = n5;
            n = cArray != null && cArray.length > n6 ? cArray[n6] : n2;
            cArray3[n5 + n6++] = n;
        }
        return new String(cArray3, 0, cArray3.length);
    }

    public Point getSelection() {
        this.checkWidget();
        if ((this.style & 4) != 0 && (this.style & 8) != 0) {
            return new Point(0, OS.GetWindowTextLength(this.handle));
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 320, nArray, nArray2);
        return new Point(this.untranslateOffset(nArray[0]), this.untranslateOffset(nArray2[0]));
    }

    public int getSelectionIndex() {
        this.checkWidget();
        if (this.noSelection) {
            return -1;
        }
        return (int)OS.SendMessage(this.handle, 327, 0L, 0L);
    }

    public String getText() {
        this.checkWidget();
        int n = OS.GetWindowTextLength(this.handle);
        if (n == 0) {
            return "";
        }
        TCHAR tCHAR = new TCHAR(this.getCodePage(), n + 1);
        OS.GetWindowText(this.handle, tCHAR, n + 1);
        if (this.segments != null) {
            tCHAR = this.deprocessText(tCHAR, 0, -1, false);
            return tCHAR.toString();
        }
        return tCHAR.toString(0, n);
    }

    public int getTextHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getTextHeightInPixels());
    }

    int getTextHeightInPixels() {
        COMBOBOXINFO cOMBOBOXINFO = new COMBOBOXINFO();
        cOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
        if ((this.style & 0x40) == 0 && OS.GetComboBoxInfo(this.handle, cOMBOBOXINFO)) {
            return cOMBOBOXINFO.buttonBottom - cOMBOBOXINFO.buttonTop + cOMBOBOXINFO.buttonTop * 2;
        }
        int n = (int)OS.SendMessage(this.handle, 340, -1L, 0L);
        if (n == -1) {
            this.error(11);
        }
        return (this.style & 4) != 0 ? n + 6 : n + 10;
    }

    public int getTextLimit() {
        this.checkWidget();
        long l2 = OS.GetDlgItem(this.handle, 1001);
        if (l2 == 0L) {
            return LIMIT;
        }
        int n = (int)OS.SendMessage(l2, 213, 0L, 0L) & Integer.MAX_VALUE;
        if (this.segments != null && n < LIMIT) {
            n = Math.max(1, n - this.segments.length);
        }
        return n;
    }

    public int getVisibleItemCount() {
        this.checkWidget();
        return this.visibleCount;
    }

    @Override
    boolean hasFocus() {
        long l2 = OS.GetFocus();
        if (l2 == this.handle) {
            return true;
        }
        if (l2 == 0L) {
            return false;
        }
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l2 == l3) {
            return true;
        }
        long l4 = OS.GetDlgItem(this.handle, 1000);
        return l2 == l4;
    }

    public int indexOf(String string) {
        return this.indexOf(string, 0);
    }

    public int indexOf(String string, int n) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (string.length() == 0) {
            int n2 = this.getItemCount();
            for (int i = n; i < n2; ++i) {
                if (!string.equals(this.getItem(i))) continue;
                return i;
            }
            return -1;
        }
        int n3 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (0 > n || n >= n3) {
            return -1;
        }
        int n4 = n - 1;
        int n5 = 0;
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        do {
            if ((n4 = (int)OS.SendMessage(this.handle, 344, (long)(n5 = n4), tCHAR)) != -1 && n4 > n5) continue;
            return -1;
        } while (!string.equals(this.getItem(n4)));
        return n4;
    }

    public void paste() {
        this.checkWidget();
        if ((this.style & 8) != 0) {
            return;
        }
        OS.SendMessage(this.handle, 770, 0L, 0L);
    }

    void stateFlagsAdd(int n) {
        long l2 = OS.GetWindowLongPtr(this.handle, 0);
        if (l2 == 0L) {
            return;
        }
        long l3 = l2 + (long)stateFlagsOffset;
        int[] nArray = new int[]{0};
        OS.MoveMemory(nArray, l3, 4);
        int[] nArray2 = nArray;
        boolean bl = false;
        nArray2[0] = nArray2[0] | n;
        OS.MoveMemory(l3, nArray, 4);
    }

    boolean stateFlagsTest() {
        long l2 = OS.GetWindowLongPtr(this.handle, 0);
        if (l2 == 0L) {
            return false;
        }
        long l3 = l2 + (long)stateFlagsOffset;
        int[] nArray = new int[]{0};
        OS.MoveMemory(nArray, l3, 4);
        return nArray[0] == 0x2006002;
    }

    @Override
    void register() {
        long l2;
        super.register();
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l3 != 0L) {
            this.display.addControl(l3, this);
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            this.display.addControl(l2, this);
        }
    }

    public void remove(int n) {
        this.checkWidget();
        this.remove(n, true);
    }

    void remove(int n, boolean bl) {
        int n2;
        int n3;
        int n4;
        char[] cArray = null;
        if ((this.style & 0x100) != 0) {
            n4 = (int)OS.SendMessage(this.handle, 329, (long)n, 0L);
            if (n4 == -1) {
                n3 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
                if (0 <= n && n < n3) {
                    this.error(15);
                }
                this.error(6);
            }
            if ((n3 = (int)OS.SendMessage(this.handle, 328, (long)n, cArray = new char[n4 + 1])) == -1) {
                n2 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
                if (0 <= n && n < n2) {
                    this.error(15);
                }
                this.error(6);
            }
        }
        n4 = OS.GetWindowTextLength(this.handle);
        n3 = (int)OS.SendMessage(this.handle, 324, (long)n, 0L);
        if (n3 == -1) {
            n2 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
            if (0 <= n && n < n2) {
                this.error(15);
            }
            this.error(6);
        } else if (n3 == 0) {
            OS.SendMessage(this.handle, 331, 0L, 0L);
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(cArray, true);
        }
        if (bl && n4 != OS.GetWindowTextLength(this.handle)) {
            this.sendEvent(24);
            if (this.isDisposed()) {
                return;
            }
        }
    }

    public void remove(int n, int n2) {
        this.checkWidget();
        if (n > n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (0 > n || n > n2 || n2 >= n3) {
            this.error(6);
        }
        int n4 = OS.GetWindowTextLength(this.handle);
        RECT rECT = null;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        int n5 = 0;
        if ((this.style & 0x100) != 0) {
            rECT = new RECT();
            l2 = OS.GetDC(this.handle);
            l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l3 = OS.SelectObject(l2, l4);
            }
        }
        int n6 = 3104;
        for (int i = n; i <= n2; ++i) {
            int n7;
            int n8;
            char[] cArray = null;
            if ((this.style & 0x100) != 0 && ((n8 = (int)OS.SendMessage(this.handle, 329, (long)n, 0L)) == -1 || (n7 = (int)OS.SendMessage(this.handle, 328, (long)n, cArray = new char[n8 + 1])) == -1)) break;
            n8 = (int)OS.SendMessage(this.handle, 324, (long)n, 0L);
            if (n8 == -1) {
                this.error(15);
            } else if (n8 == 0) {
                OS.SendMessage(this.handle, 331, 0L, 0L);
            }
            if ((this.style & 0x100) == 0) continue;
            OS.DrawText(l2, cArray, -1, rECT, 3104);
            n5 = Math.max(n5, rECT.right - rECT.left);
        }
        if ((this.style & 0x100) != 0) {
            if (l4 != 0L) {
                OS.SelectObject(l2, l3);
            }
            OS.ReleaseDC(this.handle, l2);
            this.setScrollWidth(n5, false);
        }
        if (n4 != OS.GetWindowTextLength(this.handle)) {
            this.sendEvent(24);
            if (this.isDisposed()) {
                return;
            }
        }
    }

    public void remove(String string) {
        int n;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((n = this.indexOf(string, 0)) == -1) {
            this.error(5);
        }
        this.remove(n);
    }

    public void removeAll() {
        this.checkWidget();
        OS.SendMessage(this.handle, 331, 0L, 0L);
        this.sendEvent(24);
        if (this.isDisposed()) {
            return;
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(0);
        }
    }

    public void removeModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(24, modifyListener);
    }

    public void removeSegmentListener(SegmentListener segmentListener) {
        this.checkWidget();
        if (segmentListener == null) {
            this.error(4);
        }
        this.eventTable.unhook(49, segmentListener);
        int n = -1;
        if (!this.noSelection) {
            n = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
        }
        this.clearSegments(true);
        this.applyEditSegments();
        this.applyListSegments();
        if (n != -1) {
            OS.SendMessage(this.handle, 334, (long)n, 0L);
        }
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

    public void removeVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(25, verifyListener);
    }

    boolean sendKeyEvent(int n, int n2, long l2, long l3, Event event) {
        String string;
        if (!super.sendKeyEvent(n, n2, l2, l3, event)) {
            return false;
        }
        if ((this.style & 8) != 0) {
            return true;
        }
        if (n != 1) {
            return true;
        }
        if (n2 != 258 && n2 != 256 && n2 != 646) {
            return true;
        }
        if (event.character == '\u0000') {
            return true;
        }
        if (!this.hooks(25) && !this.filters(25)) {
            return true;
        }
        char c = event.character;
        int n3 = event.stateMask;
        switch (n2) {
            case 258: {
                if (c != '\b' && c != '\u007f' && c != '\r' && c != '\t' && c != '\n') break;
            }
            case 256: {
                if ((n3 & 0x70000) == 0) break;
                return false;
            }
        }
        if (OS.GetKeyState(1) < 0 && OS.GetDlgItem(this.handle, 1001) == OS.GetCapture()) {
            return true;
        }
        String string2 = "";
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        long l4 = OS.GetDlgItem(this.handle, 1001);
        if (l4 == 0L) {
            return true;
        }
        OS.SendMessage(l4, 176, nArray, nArray2);
        switch (c) {
            case '\b': {
                if (nArray[0] != nArray2[0]) break;
                if (nArray[0] == 0) {
                    return true;
                }
                nArray[0] = nArray[0] - 1;
                nArray[0] = Math.max(nArray[0], 0);
                break;
            }
            case '\u007f': {
                if (nArray[0] != nArray2[0]) break;
                int n4 = OS.GetWindowTextLength(l4);
                if (nArray[0] == n4) {
                    return true;
                }
                nArray2[0] = nArray2[0] + 1;
                nArray2[0] = Math.min(nArray2[0], n4);
                break;
            }
            case '\r': {
                return true;
            }
            default: {
                if (c != '\t' && c < ' ') {
                    return true;
                }
                string2 = new String(new char[]{c});
            }
        }
        if ((string = this.verifyText(string2, nArray[0], nArray2[0], event)) == null) {
            return false;
        }
        if (string == string2) {
            return true;
        }
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        OS.SendMessage(l4, 177, (long)nArray[0], nArray2[0]);
        OS.SendMessage(l4, 194, 0L, tCHAR);
        return false;
    }

    public void select(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (0 <= n && n < n2) {
            int n3 = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
            if (OS.WIN32_VERSION < OS.VERSION(6, 2) && this != false && (this.style & 8) != 0 && n2 == 1 && n3 == -1) {
                OS.SendMessage(this.handle, 256, 40L, 0L);
                this.sendEvent(24);
                return;
            }
            int n4 = (int)OS.SendMessage(this.handle, 334, (long)n, 0L);
            if (n4 != -1 && n4 != n3) {
                if (OS.WIN32_VERSION < OS.VERSION(6, 2) && this != false && (this.style & 8) != 0) {
                    int n5 = 38;
                    int n6 = 40;
                    if (n == 0) {
                        n5 = 40;
                        n6 = 38;
                    }
                    OS.SendMessage(this.handle, 256, (long)n5, 0L);
                    OS.SendMessage(this.handle, 256, (long)n6, 0L);
                }
                this.sendEvent(24);
            }
        }
    }

    @Override
    void setBackgroundImage(long l2) {
        long l3;
        super.setBackgroundImage(l2);
        long l4 = OS.GetDlgItem(this.handle, 1001);
        if (l4 != 0L) {
            OS.InvalidateRect(l4, null, true);
        }
        if ((l3 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            OS.InvalidateRect(l3, null, true);
        }
    }

    @Override
    void setBackgroundPixel(int n) {
        long l2;
        super.setBackgroundPixel(n);
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l3 != 0L) {
            OS.InvalidateRect(l3, null, true);
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            OS.InvalidateRect(l2, null, true);
        }
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5) {
        if ((this.style & 4) != 0) {
            int n6 = this.getItemCount() == 0 ? 5 : this.visibleCount;
            n4 = this.getTextHeightInPixels() + this.getItemHeightInPixels() * n6 + 2;
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            if (rECT.right - rECT.left != 0 && OS.SendMessage(this.handle, 338, 0L, rECT) != 0L) {
                int n7 = rECT.right - rECT.left;
                int n8 = rECT.bottom - rECT.top;
                if (n7 == n3 && n8 == n4) {
                    n5 |= 1;
                }
            }
            OS.SetWindowPos(this.handle, 0L, n, n2, n3, n4, n5);
        } else {
            super.setBoundsInPixels(n, n2, n3, n4, n5);
        }
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        boolean bl = this.lockText;
        if ((this.style & 8) == 0) {
            this.lockText = true;
        }
        super.setFont(font);
        if ((this.style & 8) == 0) {
            this.lockText = bl;
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth();
        }
    }

    @Override
    void setForegroundPixel(int n) {
        long l2;
        super.setForegroundPixel(n);
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l3 != 0L) {
            OS.InvalidateRect(l3, null, true);
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            OS.InvalidateRect(l2, null, true);
        }
    }

    public void setItem(int n, String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        int n2 = this.getSelectionIndex();
        this.remove(n, false);
        if (this.isDisposed()) {
            return;
        }
        this.add(string, n);
        if (n2 != -1) {
            this.select(n2);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void setItems(String ... stringArray) {
        this.checkWidget();
        if (stringArray == null) {
            this.error(4);
        }
        for (String string : stringArray) {
            if (string != null) continue;
            this.error(5);
        }
        Object var2_3 = null;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        int n = 0;
        if ((this.style & 0x100) != 0) {
            RECT rECT = new RECT();
            l2 = OS.GetDC(this.handle);
            l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l3 = OS.SelectObject(l2, l4);
            }
            this.setScrollWidth(0);
        }
        OS.SendMessage(this.handle, 331, 0L, 0L);
        int n2 = this.getCodePage();
        for (String string : stringArray) {
            void var2_5;
            TCHAR tCHAR = new TCHAR(n2, string, true);
            int n3 = (int)OS.SendMessage(this.handle, 323, 0L, tCHAR);
            if (n3 == -1) {
                this.error(14);
            }
            if (n3 == -2) {
                this.error(14);
            }
            if ((this.style & 0x100) == 0) continue;
            int n4 = 3104;
            OS.DrawText(l2, tCHAR, -1, (RECT)var2_5, 3104);
            n = Math.max(n, var2_5.right - var2_5.left);
        }
        if ((this.style & 0x100) != 0) {
            if (l4 != 0L) {
                OS.SelectObject(l2, l3);
            }
            OS.ReleaseDC(this.handle, l2);
            this.setScrollWidth(n + 3);
        }
        this.sendEvent(24);
    }

    @Override
    public void setOrientation(int n) {
        super.setOrientation(n);
    }

    void setScrollWidth() {
        int n = 0;
        RECT rECT = new RECT();
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        int n2 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        int n3 = 3104;
        for (int i = 0; i < n2; ++i) {
            char[] cArray;
            int n4;
            int n5 = (int)OS.SendMessage(this.handle, 329, (long)i, 0L);
            if (n5 == -1 || (n4 = (int)OS.SendMessage(this.handle, 328, (long)i, cArray = new char[n5 + 1])) == -1) continue;
            OS.DrawText(l3, cArray, -1, rECT, 3104);
            n = Math.max(n, rECT.right - rECT.left);
        }
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        this.setScrollWidth(n + 3);
    }

    void setScrollWidth(int n) {
        this.scrollWidth = n;
        if ((this.style & 0x40) != 0) {
            OS.SendMessage(this.handle, 350, (long)n, 0L);
            return;
        }
        boolean bl = false;
        int n2 = (int)OS.SendMessage(this.handle, 326, 0L, 0L);
        if (n2 > 3) {
            long l2 = OS.MonitorFromWindow(this.handle, 2);
            MONITORINFO mONITORINFO = new MONITORINFO();
            mONITORINFO.cbSize = MONITORINFO.sizeof;
            OS.GetMonitorInfo(l2, mONITORINFO);
            int n3 = (mONITORINFO.rcWork_right - mONITORINFO.rcWork_left) / 4;
            bl = n > n3;
        }
        boolean bl2 = this.lockText;
        if ((this.style & 8) == 0) {
            this.lockText = true;
        }
        if (bl) {
            OS.SendMessage(this.handle, 352, 0L, 0L);
            OS.SendMessage(this.handle, 350, (long)n, 0L);
        } else {
            OS.SendMessage(this.handle, 352, (long)(n += OS.GetSystemMetrics(3)), 0L);
            OS.SendMessage(this.handle, 350, 0L, 0L);
        }
        if ((this.style & 8) == 0) {
            this.lockText = bl2;
        }
    }

    void setScrollWidth(char[] cArray, boolean bl) {
        RECT rECT = new RECT();
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        int n = 3104;
        OS.DrawText(l3, cArray, -1, rECT, 3104);
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        this.setScrollWidth(rECT.right - rECT.left, bl);
    }

    void setScrollWidth(int n, boolean bl) {
        if (bl) {
            if (n <= this.scrollWidth) {
                return;
            }
            this.setScrollWidth(n + 3);
        } else {
            if (n < this.scrollWidth) {
                return;
            }
            this.setScrollWidth();
        }
    }

    public void setSelection(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        int n = this.translateOffset(point.x);
        int n2 = this.translateOffset(point.y);
        long l2 = OS.MAKELPARAM(n, n2);
        OS.SendMessage(this.handle, 322, 0L, l2);
    }

    public void setText(String string) {
        TCHAR tCHAR;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((this.style & 8) != 0) {
            int n = this.indexOf(string);
            if (n != -1) {
                this.select(n);
            }
            return;
        }
        this.clearSegments(false);
        int n = LIMIT;
        long l2 = OS.GetDlgItem(this.handle, 1001);
        if (l2 != 0L) {
            n = (int)OS.SendMessage(l2, 213, 0L, 0L) & Integer.MAX_VALUE;
        }
        if (string.length() > n) {
            string = string.substring(0, n);
        }
        if (OS.SetWindowText(this.handle, tCHAR = new TCHAR(this.getCodePage(), string, true))) {
            this.applyEditSegments();
            this.sendEvent(24);
        }
    }

    public void setTextLimit(int n) {
        this.checkWidget();
        if (n == 0) {
            this.error(7);
        }
        if (this.segments != null && n > 0) {
            OS.SendMessage(this.handle, 321, (long)(n + Math.min(this.segments.length, LIMIT - n)), 0L);
        } else {
            OS.SendMessage(this.handle, 321, (long)n, 0L);
        }
    }

    @Override
    void setToolTipText(Shell shell, String string) {
        long l2 = OS.GetDlgItem(this.handle, 1001);
        long l3 = OS.GetDlgItem(this.handle, 1000);
        if (l2 != 0L) {
            shell.setToolTipText(l2, string);
        }
        if (l3 != 0L) {
            shell.setToolTipText(l3, string);
        }
        shell.setToolTipText(this.handle, string);
    }

    public void setVisibleItemCount(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        this.visibleCount = n;
        this.updateDropDownHeight();
    }

    @Override
    void subclass() {
        long l2;
        super.subclass();
        long l3 = this.display.windowProc;
        long l4 = OS.GetDlgItem(this.handle, 1001);
        if (l4 != 0L) {
            OS.SetWindowLongPtr(l4, -4, l3);
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) != 0L) {
            OS.SetWindowLongPtr(l2, -4, l3);
        }
    }

    int translateOffset(int n) {
        if (this.segments == null) {
            return n;
        }
        int n2 = this.segments.length;
        for (int i = 0; i < n2 && n - i >= this.segments[i]; ++i) {
            ++n;
        }
        return n;
    }

    @Override
    boolean translateTraversal(MSG mSG) {
        switch ((int)mSG.wParam) {
            case 13: 
            case 27: {
                if ((this.style & 4) == 0 || OS.SendMessage(this.handle, 343, 0L, 0L) == 0L) break;
                return false;
            }
        }
        return super.translateTraversal(mSG);
    }

    @Override
    boolean traverseEscape() {
        if ((this.style & 4) != 0 && OS.SendMessage(this.handle, 343, 0L, 0L) != 0L) {
            OS.SendMessage(this.handle, 335, 0L, 0L);
            return true;
        }
        return super.traverseEscape();
    }

    @Override
    boolean traverseReturn() {
        if ((this.style & 4) != 0 && OS.SendMessage(this.handle, 343, 0L, 0L) != 0L) {
            OS.SendMessage(this.handle, 335, 0L, 0L);
            return true;
        }
        return super.traverseReturn();
    }

    @Override
    void unsubclass() {
        long l2;
        super.unsubclass();
        long l3 = OS.GetDlgItem(this.handle, 1001);
        if (l3 != 0L) {
            // empty if block
        }
        if ((l2 = OS.GetDlgItem(this.handle, 1000)) == 0L) {
            // empty if block
        }
    }

    int untranslateOffset(int n) {
        if (this.segments == null) {
            return n;
        }
        int n2 = this.segments.length;
        for (int i = 0; i < n2 && n > this.segments[i]; --n, ++i) {
        }
        return n;
    }

    void updateDropDownHeight() {
        if ((this.style & 4) != 0) {
            RECT rECT = new RECT();
            OS.SendMessage(this.handle, 338, 0L, rECT);
            int n = this.getItemCount() == 0 ? 5 : this.visibleCount;
            int n2 = this.getTextHeightInPixels() + this.getItemHeightInPixels() * n + 2;
            if (n2 != rECT.bottom - rECT.top) {
                this.forceResize();
                OS.GetWindowRect(this.handle, rECT);
                int n3 = 54;
                OS.SetWindowPos(this.handle, 0L, 0, 0, rECT.right - rECT.left, n2, 54);
            }
        }
    }

    void updateDropDownTheme() {
        COMBOBOXINFO cOMBOBOXINFO = new COMBOBOXINFO();
        cOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
        if (!OS.GetComboBoxInfo(this.handle, cOMBOBOXINFO)) {
            return;
        }
        if (cOMBOBOXINFO.hwndList == 0L) {
            return;
        }
        this.maybeEnableDarkSystemTheme(cOMBOBOXINFO.hwndList);
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            this.clearSegments(true);
            this.applyEditSegments();
            this.applyListSegments();
            return true;
        }
        return false;
    }

    @Override
    void updateOrientation() {
        int n;
        int n2 = OS.GetWindowLong(this.handle, -20);
        n2 = (this.style & 0x4000000) != 0 ? (n2 |= 0x400000) : (n2 &= 0xFFBFFFFF);
        OS.SetWindowLong(this.handle, -20, n2 &= 0xFFFFDFFF);
        long l2 = 0L;
        long l3 = 0L;
        COMBOBOXINFO cOMBOBOXINFO = new COMBOBOXINFO();
        cOMBOBOXINFO.cbSize = COMBOBOXINFO.sizeof;
        if (OS.GetComboBoxInfo(this.handle, cOMBOBOXINFO)) {
            l2 = cOMBOBOXINFO.hwndItem;
            l3 = cOMBOBOXINFO.hwndList;
        }
        if (l2 != 0L) {
            n = OS.GetWindowLong(l2, -20);
            int n3 = OS.GetWindowLong(l2, -16);
            if ((this.style & 0x4000000) != 0) {
                n |= 0x3000;
                n3 |= 2;
            } else {
                n &= 0xFFFFCFFF;
                n3 &= 0xFFFFFFFD;
            }
            OS.SetWindowLong(l2, -20, n);
            OS.SetWindowLong(l2, -16, n3);
            RECT rECT = new RECT();
            OS.GetWindowRect(l2, rECT);
            int n4 = rECT.right - rECT.left;
            int n5 = rECT.bottom - rECT.top;
            OS.GetWindowRect(this.handle, rECT);
            int n6 = rECT.right - rECT.left;
            int n7 = rECT.bottom - rECT.top;
            int n8 = 22;
            OS.SetWindowPos(l2, 0L, 0, 0, n4 - 1, n5 - 1, 22);
            OS.SetWindowPos(this.handle, 0L, 0, 0, n6 - 1, n7 - 1, 22);
            OS.SetWindowPos(l2, 0L, 0, 0, n4, n5, 22);
            OS.SetWindowPos(this.handle, 0L, 0, 0, n6, n7, 22);
            OS.InvalidateRect(this.handle, null, true);
        }
        if (l3 != 0L) {
            n = OS.GetWindowLong(l3, -20);
            n = (this.style & 0x4000000) != 0 ? (n |= 0x400000) : (n &= 0xFFBFFFFF);
            OS.SetWindowLong(l3, -20, n);
        }
    }

    String verifyText(String string, int n, int n2, Event event) {
        Event event2 = new Event();
        event2.text = string;
        event2.start = n;
        event2.end = n2;
        if (event != null) {
            event2.character = event.character;
            event2.keyCode = event.keyCode;
            event2.stateMask = event.stateMask;
        }
        event2.start = this.untranslateOffset(event2.start);
        event2.end = this.untranslateOffset(event2.end);
        this.sendEvent(25, event2);
        if (!event2.doit || this.isDisposed()) {
            return null;
        }
        return event2.text;
    }

    @Override
    int widgetExtStyle() {
        return super.widgetExtStyle() & 0xFFEFFFFF;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x40 | 0x400 | 0x100000 | 0x200000;
        if ((this.style & 0x40) != 0) {
            return n | 1;
        }
        if ((this.style & 8) != 0) {
            return n | 3;
        }
        return n | 2;
    }

    @Override
    TCHAR windowClass() {
        return ComboClass;
    }

    @Override
    long windowProc() {
        return ComboProc;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        long l5;
        if (this.handle == 0L) {
            return 0L;
        }
        if (l2 != this.handle) {
            l5 = OS.GetDlgItem(this.handle, 1001);
            long l6 = OS.GetDlgItem(this.handle, 1000);
            if (l5 != 0L && l2 == l5 || l6 != 0L && l2 == l6) {
                LRESULT lRESULT = null;
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    case 258: {
                        bl = (this.hooks(49) || this.filters(49) || (this.state & 0x400000) != 0) && !this.ignoreCharacter && OS.GetKeyState(17) >= 0 && OS.GetKeyState(18) >= 0;
                        lRESULT = this.wmChar(l2, l3, l4);
                        break;
                    }
                    case 646: {
                        lRESULT = this.wmIMEChar(l2, l3, l4);
                        break;
                    }
                    case 256: {
                        bl = l3 == 46L && (this.hooks(49) || this.filters(49) || (this.state & 0x400000) != 0);
                        lRESULT = this.wmKeyDown(l2, l3, l4);
                        break;
                    }
                    case 257: {
                        lRESULT = this.wmKeyUp(l2, l3, l4);
                        break;
                    }
                    case 262: {
                        lRESULT = this.wmSysChar(l2, l3, l4);
                        break;
                    }
                    case 260: {
                        lRESULT = this.wmSysKeyDown(l2, l3, l4);
                        break;
                    }
                    case 261: {
                        lRESULT = this.wmSysKeyUp(l2, l3, l4);
                        break;
                    }
                    case 533: {
                        lRESULT = this.wmCaptureChanged(l2, l3, l4);
                        break;
                    }
                    case 515: {
                        lRESULT = this.wmLButtonDblClk(l2, l3, l4);
                        break;
                    }
                    case 513: {
                        lRESULT = this.wmLButtonDown(l2, l3, l4);
                        break;
                    }
                    case 514: {
                        lRESULT = this.wmLButtonUp(l2, l3, l4);
                        break;
                    }
                    case 521: {
                        lRESULT = this.wmMButtonDblClk(l2, l3, l4);
                        break;
                    }
                    case 519: {
                        lRESULT = this.wmMButtonDown(l2, l3, l4);
                        break;
                    }
                    case 520: {
                        lRESULT = this.wmMButtonUp(l2, l3, l4);
                        break;
                    }
                    case 673: {
                        lRESULT = this.wmMouseHover(l2, l3, l4);
                        break;
                    }
                    case 675: {
                        lRESULT = this.wmMouseLeave(l2, l3, l4);
                        break;
                    }
                    case 512: {
                        lRESULT = this.wmMouseMove(l2, l3, l4);
                        break;
                    }
                    case 518: {
                        lRESULT = this.wmRButtonDblClk(l2, l3, l4);
                        break;
                    }
                    case 516: {
                        lRESULT = this.wmRButtonDown(l2, l3, l4);
                        break;
                    }
                    case 517: {
                        lRESULT = this.wmRButtonUp(l2, l3, l4);
                        break;
                    }
                    case 525: {
                        lRESULT = this.wmXButtonDblClk(l2, l3, l4);
                        break;
                    }
                    case 523: {
                        lRESULT = this.wmXButtonDown(l2, l3, l4);
                        break;
                    }
                    case 524: {
                        lRESULT = this.wmXButtonUp(l2, l3, l4);
                        break;
                    }
                    case 15: {
                        lRESULT = this.wmPaint(l2, l3, l4);
                        break;
                    }
                    case 123: {
                        lRESULT = this.wmContextMenu(l2, l3, l4);
                        break;
                    }
                    case 198: {
                        if (!this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0) break;
                        return 0L;
                    }
                    case 199: 
                    case 772: {
                        if (this.hooks(49) || this.filters(49) || (this.state & 0x400000) != 0) {
                            return 0L;
                        }
                    }
                    case 768: 
                    case 769: 
                    case 770: 
                    case 771: {
                        bl = this.hooks(49) || this.filters(49) || (this.state & 0x400000) != 0;
                    }
                    case 12: {
                        if (l2 != l5) break;
                        lRESULT = this.wmClipboard(l2, n, l3, l4);
                    }
                }
                if (lRESULT != null) {
                    return lRESULT.value;
                }
                if (bl) {
                    if (this.getDrawing() && OS.IsWindowVisible(l5)) {
                        bl2 = true;
                        OS.DefWindowProc(l5, 11, 0L, 0L);
                    }
                    this.clearSegments(true);
                    long l7 = this.callWindowProc(l2, n, l3, l4);
                    this.applyEditSegments();
                    if (bl2) {
                        OS.DefWindowProc(l5, 11, 1L, 0L);
                        OS.InvalidateRect(l5, null, true);
                        this.forceScrollingToCaret();
                    }
                    return l7;
                }
                return this.callWindowProc(l2, n, l3, l4);
            }
        }
        switch (n) {
            case 334: {
                Object object;
                l5 = -1L;
                int n2 = (int)l3;
                if ((this.style & 8) != 0 && (this.hooks(25) || this.filters(25))) {
                    object = this.getText();
                    String string = null;
                    if (l3 == -1L) {
                        string = "";
                    } else if (0L <= l3 && l3 < (long)this.getItemCount()) {
                        string = this.getItem((int)l3);
                    }
                    if (string != null && !string.equals(object)) {
                        int n3 = OS.GetWindowTextLength(this.handle);
                        object = string;
                        if ((string = this.verifyText(string, 0, n3, null)) == null) {
                            return 0L;
                        }
                        if (!string.equals(object) && (n2 = this.indexOf(string)) != -1 && (long)n2 != l3) {
                            return this.callWindowProc(this.handle, 334, n2, l4);
                        }
                    }
                }
                if (!this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0 || (l5 = super.windowProc(l2, n, l3, l4)) == -1L || l5 == -2L) break;
                object = this.getSegments(this.items[n2]);
                int[] nArray = this.segments = object != null ? ((Event)object).segments : null;
                if (object != null && ((Event)object).segmentsChars != null) {
                    int n4 = this.state & 0x400000;
                    if (((Event)object).segmentsChars[0] == '\u202b') {
                        super.updateTextDirection(0x4000000);
                    } else if (((Event)object).segmentsChars[0] == '\u202a') {
                        super.updateTextDirection(0x2000000);
                    }
                    this.state |= n4;
                }
                return l5;
            }
            case 323: 
            case 330: 
            case 344: {
                if (l4 == 0L || !this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0) break;
                l5 = -1L;
                int n5 = OS.wcslen(l4);
                TCHAR tCHAR = new TCHAR(this.getCodePage(), n5);
                OS.MoveMemory(tCHAR, l4, tCHAR.length() * 2);
                String string = tCHAR.toString(0, n5);
                Event event = this.getSegments(string);
                if (event != null && event.segments != null) {
                    tCHAR = new TCHAR(this.getCodePage(), this.getSegmentsText(string, event), true);
                    long l8 = OS.GetProcessHeap();
                    n5 = tCHAR.length() * 2;
                    long l9 = OS.HeapAlloc(l8, 8, n5);
                    OS.MoveMemory(l9, tCHAR, n5);
                    l5 = super.windowProc(l2, n, l3, l9);
                    OS.HeapFree(l8, 0, l9);
                }
                if (n == 323 || n == 330) {
                    int n6 = n == 323 ? this.items.length : (int)l3;
                    String[] stringArray = new String[this.items.length + 1];
                    System.arraycopy(this.items, 0, stringArray, 0, n6);
                    stringArray[n6] = string;
                    System.arraycopy(this.items, n6, stringArray, n6 + 1, this.items.length - n6);
                    this.items = stringArray;
                }
                if (l5 == -1L || l5 == -2L) break;
                return l5;
            }
            case 324: {
                if (!this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0) break;
                l5 = super.windowProc(l2, n, l3, l4);
                if (l5 != -1L && l5 != -2L) {
                    int n7 = (int)l3;
                    if (this.items.length == 1) {
                        this.items = new String[0];
                    } else if (this.items.length > 1) {
                        String[] stringArray = new String[this.items.length - 1];
                        System.arraycopy(this.items, 0, stringArray, 0, n7);
                        System.arraycopy(this.items, n7 + 1, stringArray, n7, this.items.length - n7 - 1);
                        this.items = stringArray;
                    }
                    if (!this.noSelection && (long)(n7 = (int)OS.SendMessage(this.handle, 327, 0L, 0L)) == l3) {
                        this.clearSegments(false);
                        this.applyEditSegments();
                    }
                }
                return l5;
            }
            case 331: {
                if (!this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0) break;
                if (this.items.length > 0) {
                    this.items = new String[0];
                }
                this.clearSegments(false);
                this.applyEditSegments();
            }
        }
        return super.windowProc(l2, n, l3, l4);
    }

    @Override
    LRESULT wmColorChild(long l2, long l3) {
        boolean bl;
        LRESULT lRESULT = super.wmColorChild(l2, l3);
        boolean bl2 = (this.style & 8) != 0;
        boolean bl3 = bl = lRESULT != null;
        if (bl2 && bl && this.stateFlagsUsable) {
            this.stateFlagsAdd(0x2000000);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_CTLCOLOR(long l2, long l3) {
        return this.wmColorChild(l2, l3);
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        long l4 = this.callWindowProc(this.handle, 135, l2, l3);
        return new LRESULT(l4 | 1L);
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        return null;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        int n;
        int n2 = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
        LRESULT lRESULT = super.WM_LBUTTONDOWN(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if ((this.style & 8) == 0 && n2 != (n = (int)OS.SendMessage(this.handle, 327, 0L, 0L))) {
            this.sendEvent(24);
            if (this.isDisposed()) {
                return LRESULT.ZERO;
            }
            this.sendSelectionEvent(13, null, true);
            if (this.isDisposed()) {
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        return null;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        if (this.ignoreResize) {
            return null;
        }
        if ((this.style & 0x40) != 0) {
            LRESULT lRESULT = super.WM_SIZE(l2, l3);
            if (OS.IsWindowVisible(this.handle)) {
                int n = 133;
                OS.RedrawWindow(this.handle, null, 0L, 133);
            }
            return lRESULT;
        }
        boolean bl = this.lockText;
        if ((this.style & 8) == 0) {
            this.lockText = true;
        }
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if ((this.style & 8) == 0) {
            this.lockText = bl;
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(this.scrollWidth);
        }
        this.forceScrollingToCaret();
        return lRESULT;
    }

    void forceScrollingToCaret() {
        Point point;
        Point point2;
        if ((this.style & 8) == 0 && !(point2 = this.getSelection()).equals(point = new Point(0, 0))) {
            this.setSelection(point);
            this.setSelection(point2);
        }
    }

    @Override
    LRESULT WM_UPDATEUISTATE(long l2, long l3) {
        LRESULT lRESULT = super.WM_UPDATEUISTATE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        OS.InvalidateRect(this.handle, null, true);
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!this.getDrawing()) {
            return lRESULT;
        }
        if (!OS.IsWindowVisible(this.handle)) {
            return lRESULT;
        }
        if (this.ignoreResize) {
            WINDOWPOS wINDOWPOS = new WINDOWPOS();
            OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
            if ((wINDOWPOS.flags & 1) == 0) {
                WINDOWPOS wINDOWPOS2 = wINDOWPOS;
                wINDOWPOS2.flags |= 8;
                OS.MoveMemory(l3, wINDOWPOS, WINDOWPOS.sizeof);
                OS.InvalidateRect(this.handle, null, true);
                RECT rECT = new RECT();
                OS.GetWindowRect(this.handle, rECT);
                int n = rECT.right - rECT.left;
                int n2 = rECT.bottom - rECT.top;
                if (n != 0 && n2 != 0) {
                    long l4 = this.parent.handle;
                    long l5 = OS.GetWindow(l4, 5);
                    OS.MapWindowPoints(0L, l4, rECT, 2);
                    long l6 = OS.CreateRectRgn(rECT.left, rECT.top, rECT.right, rECT.bottom);
                    while (l5 != 0L) {
                        if (l5 != this.handle) {
                            OS.GetWindowRect(l5, rECT);
                            OS.MapWindowPoints(0L, l4, rECT, 2);
                            long l7 = OS.CreateRectRgn(rECT.left, rECT.top, rECT.right, rECT.bottom);
                            OS.CombineRgn(l6, l6, l7, 4);
                            OS.DeleteObject(l7);
                        }
                        l5 = OS.GetWindow(l5, 2);
                    }
                    int n3 = 1029;
                    OS.RedrawWindow(l4, null, l6, 1029);
                    OS.DeleteObject(l6);
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmChar(long l2, long l3, long l4) {
        if (this.ignoreCharacter) {
            return null;
        }
        LRESULT lRESULT = super.wmChar(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l3) {
            case 9: {
                return LRESULT.ZERO;
            }
            case 13: {
                if (!this.ignoreDefaultSelection) {
                    this.sendSelectionEvent(14);
                }
                this.ignoreDefaultSelection = false;
                if (this.getSelectionIndex() == -1 && (this.style & 4) != 0 && (this.style & 8) == 0) {
                    if (OS.SendMessage(this.handle, 343, 0L, 0L) != 0L) {
                        OS.SendMessage(this.handle, 335, 0L, 0L);
                    }
                    return LRESULT.ZERO;
                }
            }
            case 27: {
                if ((this.style & 4) != 0 && OS.SendMessage(this.handle, 343, 0L, 0L) == 0L) {
                    return LRESULT.ZERO;
                }
            }
            case 127: {
                String string;
                Matcher matcher;
                if (OS.GetKeyState(17) >= 0) break;
                if ((this.style & 8) != 0) {
                    return LRESULT.ZERO;
                }
                Point point = this.getSelection();
                long l5 = OS.GetDlgItem(this.handle, 1001);
                int n = point.x;
                int n2 = point.y;
                if (n == n2 && (matcher = CTRL_BS_PATTERN.matcher(string = this.getText().substring(0, n))).find()) {
                    n = matcher.start();
                    n2 = matcher.end();
                    OS.SendMessage(l5, 177, (long)n, n2);
                }
                if (n < n2) {
                    OS.SendMessage(l5, 194, 1L, 0L);
                }
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    LRESULT wmClipboard(long l2, int n, long l3, long l4) {
        Object object;
        if ((this.style & 8) != 0) {
            return null;
        }
        if (!this.hooks(25) && !this.filters(25)) {
            return null;
        }
        boolean bl = false;
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        String string = null;
        switch (n) {
            case 768: 
            case 771: {
                OS.SendMessage(l2, 176, nArray, nArray2);
                if (this.untranslateOffset(nArray[0]) == this.untranslateOffset(nArray2[0])) break;
                string = "";
                bl = true;
                break;
            }
            case 770: {
                OS.SendMessage(l2, 176, nArray, nArray2);
                string = this.getClipboardText();
                break;
            }
            case 199: 
            case 772: {
                if (OS.SendMessage(l2, 198, 0L, 0L) == 0L) break;
                this.ignoreModify = true;
                OS.CallWindowProc(EditProc, l2, n, l3, l4);
                int n2 = OS.GetWindowTextLength(l2);
                object = new int[]{0};
                int[] nArray3 = new int[]{0};
                OS.SendMessage(l2, 176, (int[])object, nArray3);
                if (n2 != 0 && object[0] != nArray3[0]) {
                    char[] cArray = new char[n2 + 1];
                    OS.GetWindowText(l2, cArray, n2 + 1);
                    string = new String(cArray, (int)object[0], nArray3[0] - object[0]);
                } else {
                    string = "";
                }
                OS.CallWindowProc(EditProc, l2, n, l3, l4);
                OS.SendMessage(l2, 176, nArray, nArray2);
                this.ignoreModify = false;
                break;
            }
            case 12: {
                if (this.lockText) {
                    return null;
                }
                nArray2[0] = OS.GetWindowTextLength(l2);
                int n2 = OS.wcslen(l4);
                object = new TCHAR(this.getCodePage(), n2);
                int n3 = ((TCHAR)object).length() * 2;
                OS.MoveMemory((TCHAR)object, l4, n3);
                string = ((TCHAR)object).toString(0, n2);
                break;
            }
        }
        if (string != null) {
            String string2 = string;
            if ((string = this.verifyText(string, nArray[0], nArray2[0], null)) == null) {
                return LRESULT.ZERO;
            }
            if (!string.equals(string2)) {
                if (bl) {
                    OS.CallWindowProc(EditProc, l2, n, l3, l4);
                }
                object = new TCHAR(this.getCodePage(), string, true);
                if (n == 12) {
                    long l5 = OS.GetProcessHeap();
                    int n4 = ((TCHAR)object).length() * 2;
                    long l6 = OS.HeapAlloc(l5, 8, n4);
                    OS.MoveMemory(l6, (TCHAR)object, n4);
                    long l7 = OS.CallWindowProc(EditProc, l2, n, l3, l6);
                    OS.HeapFree(l5, 0, l6);
                    return new LRESULT(l7);
                }
                OS.SendMessage(l2, 194, 0L, (TCHAR)object);
                return LRESULT.ZERO;
            }
        }
        return null;
    }

    @Override
    LRESULT wmCommandChild(long l2, long l3) {
        int n = OS.HIWORD(l2);
        switch (n) {
            case 5: {
                if (this.ignoreModify) break;
                this.noSelection = true;
                this.sendEvent(24);
                if (this.isDisposed()) {
                    return LRESULT.ZERO;
                }
                this.noSelection = false;
                break;
            }
            case 1: {
                int n2 = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
                if (n2 != -1) {
                    OS.SendMessage(this.handle, 334, (long)n2, 0L);
                }
                this.sendEvent(24);
                if (this.isDisposed()) {
                    return LRESULT.ZERO;
                }
                this.sendSelectionEvent(13);
                break;
            }
            case 3: {
                this.sendFocusEvent(15);
                if (!this.isDisposed()) break;
                return LRESULT.ZERO;
            }
            case 7: {
                this.setCursor();
                this.updateDropDownHeight();
                this.updateDropDownTheme();
                break;
            }
            case 4: {
                this.sendFocusEvent(16);
                if (!this.isDisposed()) break;
                return LRESULT.ZERO;
            }
            case 1792: 
            case 1793: {
                Event event = new Event();
                event.doit = true;
                this.sendEvent(44, event);
                if (!event.doit) {
                    long l4 = l3;
                    int n3 = OS.GetWindowLong(l4, -20);
                    int n4 = OS.GetWindowLong(l4, -16);
                    if (n == 1792) {
                        n3 |= 0x3000;
                        n4 |= 2;
                    } else {
                        n3 &= 0xFFFFCFFF;
                        n4 &= 0xFFFFFFFD;
                    }
                    OS.SetWindowLong(l4, -20, n3);
                    OS.SetWindowLong(l4, -16, n4);
                }
                if (!this.hooks(49) && !this.filters(49) && (this.state & 0x400000) == 0) break;
                this.clearSegments(true);
                this.state &= 0xFFBFFFFF;
                this.applyEditSegments();
                break;
            }
        }
        return super.wmCommandChild(l2, l3);
    }

    @Override
    LRESULT wmIMEChar(long l2, long l3, long l4) {
        Display display = this.display;
        display.lastKey = 0;
        display.lastAscii = (int)l3;
        Display display2 = display;
        Display display3 = display;
        Display display4 = display;
        boolean bl = false;
        display4.lastDead = false;
        display3.lastNull = false;
        display2.lastVirtual = false;
        if (!this.sendKeyEvent(1, 646, l3, l4)) {
            return LRESULT.ZERO;
        }
        this.ignoreCharacter = true;
        long l5 = this.callWindowProc(l2, 646, l3, l4);
        MSG mSG = new MSG();
        int n = 10420227;
        while (OS.PeekMessage(mSG, l2, 258, 258, 10420227)) {
            OS.TranslateMessage(mSG);
            OS.DispatchMessage(mSG);
        }
        this.ignoreCharacter = false;
        this.sendKeyEvent(2, 646, l3, l4);
        Display display5 = display;
        Display display6 = display;
        boolean bl2 = false;
        display6.lastAscii = 0;
        display5.lastKey = 0;
        return new LRESULT(l5);
    }

    @Override
    LRESULT wmKeyDown(long l2, long l3, long l4) {
        if (this.ignoreCharacter) {
            return null;
        }
        LRESULT lRESULT = super.wmKeyDown(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        this.ignoreDefaultSelection = false;
        switch ((int)l3) {
            case 37: 
            case 38: 
            case 39: 
            case 40: {
                if (this.segments == null) break;
                long l5 = 0L;
                int[] nArray = new int[]{0};
                int[] nArray2 = new int[]{0};
                int[] nArray3 = new int[]{0};
                int[] nArray4 = new int[]{0};
                OS.SendMessage(this.handle, 320, nArray, nArray2);
                while (true) {
                    l5 = this.callWindowProc(l2, 256, l3, l4);
                    OS.SendMessage(this.handle, 320, nArray3, nArray4);
                    if (nArray3[0] != nArray[0] ? this.untranslateOffset(nArray3[0]) != this.untranslateOffset(nArray[0]) : nArray4[0] == nArray2[0] || this.untranslateOffset(nArray4[0]) != this.untranslateOffset(nArray2[0])) break;
                    nArray[0] = nArray3[0];
                    nArray2[0] = nArray4[0];
                }
                lRESULT = l5 == 0L ? LRESULT.ZERO : new LRESULT(l5);
                break;
            }
            case 13: {
                if ((this.style & 4) == 0 || OS.SendMessage(this.handle, 343, 0L, 0L) == 0L) break;
                this.ignoreDefaultSelection = true;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmSysKeyDown(long l2, long l3, long l4) {
        int n = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
        LRESULT lRESULT = super.wmSysKeyDown(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 8) == 0 && l3 == 40L) {
            long l5 = this.callWindowProc(l2, 260, l3, l4);
            int n2 = (int)OS.SendMessage(this.handle, 327, 0L, 0L);
            if (n != n2) {
                this.sendEvent(24);
                if (this.isDisposed()) {
                    return LRESULT.ZERO;
                }
                this.sendSelectionEvent(13, null, true);
                if (this.isDisposed()) {
                    return LRESULT.ZERO;
                }
            }
            return new LRESULT(l5);
        }
        return lRESULT;
    }

    static {
        CTRL_BS_PATTERN = Pattern.compile("\\r?\\n\\z|[\\p{Punct}]+[\\t ]*\\z|[^\\p{Punct}\\s\\n\\r]*[\\t ]*\\z");
        LIMIT = Integer.MAX_VALUE;
        ComboClass = new TCHAR(0, "COMBOBOX", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, ComboClass, wNDCLASS);
        ComboProc = wNDCLASS.lpfnWndProc;
        stateFlagsOffset = C.PTR_SIZEOF == 8 ? 104 : 84;
    }
}

