/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.util.Arrays;
import java.util.regex.Matcher;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SegmentListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Text
extends Scrollable {
    int tabs;
    int oldStart;
    int oldEnd;
    boolean doubleClick;
    boolean ignoreModify;
    boolean ignoreVerify;
    boolean ignoreCharacter;
    boolean allowPasswordChar;
    String message;
    int[] segments;
    int clearSegmentsCount = 0;
    long hwndActiveIcon;
    static final char LTR_MARK = '\u200e';
    static final char RTL_MARK = '\u200f';
    static final int IDI_SEARCH = 101;
    static final int IDI_CANCEL = 102;
    static final int IDI_SEARCH_DARKTHEME = 103;
    static final int IDI_CANCEL_DARKTHEME = 104;
    public static final int LIMIT = Integer.MAX_VALUE;
    public static final String DELIMITER = "\r\n";
    static final long EditProc;
    static final TCHAR EditClass;

    public Text(Composite composite, int n) {
        super(composite, Text.checkStyle(n));
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        boolean bl = false;
        switch (n) {
            case 20: {
                if (this.findImageControl() == null) break;
                return 0L;
            }
            case 276: 
            case 277: {
                boolean bl2 = bl = this.findImageControl() != null && this.getDrawing() && OS.IsWindowVisible(this.handle);
                if (!bl) break;
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
                break;
            }
            case 15: {
                boolean bl3 = this.findImageControl() != null;
                boolean bl4 = false;
                if ((this.style & 4) != 0 && (this.style & 8) != 0 && this.message.length() > 0) {
                    boolean bl5 = bl4 = l2 != OS.GetFocus() && OS.GetWindowTextLength(this.handle) == 0;
                }
                if (!bl3 && !bl4) break;
                long l5 = 0L;
                PAINTSTRUCT pAINTSTRUCT = new PAINTSTRUCT();
                l5 = OS.BeginPaint(this.handle, pAINTSTRUCT);
                int n2 = pAINTSTRUCT.right - pAINTSTRUCT.left;
                int n3 = pAINTSTRUCT.bottom - pAINTSTRUCT.top;
                if (n2 != 0 && n3 != 0) {
                    RECT rECT;
                    long l6 = l5;
                    long l7 = 0L;
                    long l8 = 0L;
                    POINT pOINT = null;
                    POINT pOINT2 = null;
                    if (bl3) {
                        l6 = OS.CreateCompatibleDC(l5);
                        pOINT = new POINT();
                        pOINT2 = new POINT();
                        OS.SetWindowOrgEx(l6, pAINTSTRUCT.left, pAINTSTRUCT.top, pOINT);
                        OS.SetBrushOrgEx(l6, pAINTSTRUCT.left, pAINTSTRUCT.top, pOINT2);
                        l7 = OS.CreateCompatibleBitmap(l5, n2, n3);
                        l8 = OS.SelectObject(l6, l7);
                        rECT = new RECT();
                        OS.SetRect(rECT, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right, pAINTSTRUCT.bottom);
                        this.drawBackground(l6, rECT);
                    }
                    OS.CallWindowProc(EditProc, l2, 15, l6, l4);
                    if (bl4) {
                        boolean bl6;
                        Object object;
                        rECT = new RECT();
                        OS.GetClientRect(this.handle, rECT);
                        long l9 = OS.SendMessage(this.handle, 212, 0L, 0L);
                        RECT rECT2 = rECT;
                        rECT2.left += OS.LOWORD(l9);
                        RECT rECT3 = rECT;
                        rECT3.right -= OS.HIWORD(l9);
                        if ((this.style & 0x800) != 0) {
                            object = rECT;
                            ++((RECT)object).left;
                            RECT rECT4 = rECT;
                            ++rECT4.top;
                            RECT rECT5 = rECT;
                            --rECT5.right;
                            RECT rECT6 = rECT;
                            --rECT6.bottom;
                        }
                        object = this.message.toCharArray();
                        int n4 = 8192;
                        boolean bl7 = bl6 = (this.style & 0x4000000) != 0;
                        if (bl6) {
                            n4 |= 0x20000;
                        }
                        int n5 = this.style & 0x1024000;
                        switch (n5) {
                            case 16384: {
                                n4 |= bl6 ? 2 : 0;
                                break;
                            }
                            case 0x1000000: {
                                n4 |= 1;
                            }
                            case 131072: {
                                n4 |= bl6 ? 0 : 2;
                            }
                        }
                        long l10 = OS.SendMessage(l2, 49, 0L, 0L);
                        long l11 = OS.SelectObject(l6, l10);
                        OS.SetTextColor(l6, OS.GetSysColor(17));
                        OS.SetBkMode(l6, 1);
                        OS.DrawText(l6, (char[])object, ((Object)object).length, rECT, n4);
                        OS.SelectObject(l6, l11);
                    }
                    if (bl3) {
                        OS.SetWindowOrgEx(l6, pOINT.x, pOINT.y, null);
                        OS.SetBrushOrgEx(l6, pOINT2.x, pOINT2.y, null);
                        OS.BitBlt(l5, pAINTSTRUCT.left, pAINTSTRUCT.top, n2, n3, l6, 0, 0, 0xCC0020);
                        OS.SelectObject(l6, l8);
                        OS.DeleteObject(l7);
                        OS.DeleteObject(l6);
                    }
                }
                OS.EndPaint(this.handle, pAINTSTRUCT);
                return 0L;
            }
        }
        if ((this.style & 0x80) != 0) {
            switch (n) {
                case 512: {
                    POINT pOINT = new POINT();
                    OS.POINTSTOPOINT(pOINT, l4);
                    long l12 = OS.ChildWindowFromPointEx(this.handle, pOINT, 1);
                    if (l12 == this.handle) {
                        l12 = 0L;
                    }
                    if (l12 == this.hwndActiveIcon) break;
                    if (this.hwndActiveIcon != 0L) {
                        OS.InvalidateRect(this.hwndActiveIcon, null, false);
                    }
                    if (l12 != 0L) {
                        OS.InvalidateRect(l12, null, false);
                    }
                    this.hwndActiveIcon = l12;
                    break;
                }
                case 675: {
                    if (this.hwndActiveIcon == 0L) break;
                    OS.InvalidateRect(this.hwndActiveIcon, null, false);
                    this.hwndActiveIcon = 0L;
                    break;
                }
                case 513: {
                    if (this.hwndActiveIcon == 0L) break;
                    OS.InvalidateRect(this.hwndActiveIcon, null, false);
                    return 0L;
                }
                case 514: {
                    if (this.hwndActiveIcon == 0L) break;
                    Event event = new Event();
                    if (this.hwndActiveIcon == OS.GetDlgItem(this.handle, 512)) {
                        event.detail = 512;
                    } else {
                        event.detail = 256;
                        this.setText("");
                    }
                    this.setFocus();
                    this.selectAll();
                    this.sendSelectionEvent(14, event, false);
                    break;
                }
            }
        }
        long l13 = OS.CallWindowProc(EditProc, l2, n, l3, l4);
        switch (n) {
            case 276: 
            case 277: {
                if (!bl) break;
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                OS.InvalidateRect(this.handle, null, true);
            }
        }
        return l13;
    }

    @Override
    void createHandle() {
        long l2 = this.widgetStyle();
        if ((l2 & 0x800000L) == 0L) {
            super.createHandle();
        } else {
            this.style &= 0xFFFFF7FF;
            super.createHandle();
            this.style |= 0x800;
            l2 = OS.GetWindowLongPtr(this.handle, -16);
            OS.SetWindowLongPtr(this.handle, -16, l2 |= 0x800000L);
            OS.SetWindowPos(this.handle, 0L, 0, 0, 0, 0, 39);
        }
        OS.SendMessage(this.handle, 197, 0L, 0L);
        if ((this.style & 8) != 0 && this.applyThemeBackground() == 1) {
            this.state |= 0x100;
        }
        if ((this.style & 0x80) != 0) {
            long l3;
            if (this.display.hIconSearch == 0L) {
                long[] lArray = new long[]{0L};
                int n = this.display.textUseDarkthemeIcons ? 103 : 101;
                int n2 = OS.LoadIconMetric(OS.GetLibraryHandle(), n, 0, lArray);
                if (n2 != 0) {
                    this.error(2);
                }
                this.display.hIconSearch = lArray[0];
                int n3 = this.display.textUseDarkthemeIcons ? 104 : 102;
                n2 = OS.LoadIconMetric(OS.GetLibraryHandle(), n3, 0, lArray);
                if (n2 != 0) {
                    this.error(2);
                }
                this.display.hIconCancel = lArray[0];
            }
            if ((this.style & 0x200) != 0 && (l3 = OS.CreateWindowEx(0, Label.LabelClass, null, 1409286157, 0, 0, 0, 0, this.handle, 512L, OS.GetModuleHandle(null), null)) == 0L) {
                this.error(2);
            }
            if ((this.style & 0x100) != 0) {
                this.state |= 0x2000;
                long l4 = OS.CreateWindowEx(0, Label.LabelClass, null, 0x4400000D, 0, 0, 0, 0, this.handle, 256L, OS.GetModuleHandle(null), null);
                if (l4 == 0L) {
                    this.error(2);
                }
            }
        }
    }

    @Override
    int applyThemeBackground() {
        return this.backgroundAlpha == 0 || (this.style & 0xB00) == 0 ? 1 : 0;
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
        this.clearSegments(true);
        this.applySegments();
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

    public void append(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        string = Display.withCrLf(string);
        int n = OS.GetWindowTextLength(this.handle);
        if ((this.hooks(25) || this.filters(25)) && (string = this.verifyText(string, n, n, null)) == null) {
            return;
        }
        OS.SendMessage(this.handle, 177, (long)n, n);
        this.clearSegments(true);
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        this.ignoreCharacter = true;
        OS.SendMessage(this.handle, 194, 0L, tCHAR);
        this.ignoreCharacter = false;
        OS.SendMessage(this.handle, 183, 0L, 0L);
        if ((this.state & 0x400000) != 0) {
            super.updateTextDirection(0x6000000);
        }
        this.applySegments();
    }

    void applySegments() {
        int n;
        int n2;
        int n3;
        char[] cArray;
        if (this.isDisposed() || --this.clearSegmentsCount != 0) {
            return;
        }
        if (!this.hooks(49) && !this.filters(49)) {
            return;
        }
        int n4 = OS.GetWindowTextLength(this.handle);
        char[] cArray2 = new char[n4 + 1];
        if (n4 > 0) {
            OS.GetWindowText(this.handle, cArray2, n4 + 1);
        }
        String string = new String(cArray2, 0, n4);
        Event event = new Event();
        event.text = string;
        event.segments = this.segments;
        this.sendEvent(49, event);
        this.segments = event.segments;
        if (this.segments == null) {
            return;
        }
        int n5 = this.segments.length;
        if (n5 == 0) {
            return;
        }
        n4 = string == null ? 0 : string.length();
        for (int i = 1; i < n5; ++i) {
            if (event.segments[i] >= event.segments[i - 1] && event.segments[i] <= n4) continue;
            this.error(5);
        }
        char[] cArray3 = event.segmentsChars;
        char[] cArray4 = cArray = cArray3 == null ? null : Display.withCrLf(cArray3);
        if (cArray3 != cArray) {
            int[] nArray = new int[n5 + Math.min(n5, cArray.length - cArray3.length)];
            n3 = 0;
            for (int i = 0; i < cArray3.length && i < n5; ++i) {
                if (cArray3[i] == '\n' && cArray[i + n3] == '\r') {
                    nArray[i + n3++] = this.segments[i];
                }
                nArray[i + n3] = this.segments[i];
            }
            this.segments = nArray;
            n5 = this.segments.length;
            cArray3 = cArray;
        }
        int n6 = (int)OS.SendMessage(this.handle, 213, 0L, 0L) & Integer.MAX_VALUE;
        OS.SendMessage(this.handle, 197, (long)(n6 + Math.min(n5, LIMIT - n6)), 0L);
        char[] cArray5 = new char[(n4 += n5) + 1];
        n3 = 0;
        int n7 = 0;
        int n8 = n2 = this.getOrientation() == 0x4000000 ? 8207 : 8206;
        while (n3 < n4) {
            if (n7 < n5 && n3 - n7 == this.segments[n7]) {
                n = cArray3 != null && cArray3.length > n7 ? cArray3[n7] : n2;
                cArray5[n3++] = n;
                ++n7;
                continue;
            }
            if (string == null) continue;
            cArray5[n3] = string.charAt(n3++ - n7);
        }
        while (n7 < n5) {
            this.segments[n7] = n3 - n7;
            n = cArray3 != null && cArray3.length > n7 ? cArray3[n7] : n2;
            cArray5[n3++] = n;
            ++n7;
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        boolean bl = this.ignoreCharacter;
        boolean bl2 = this.ignoreModify;
        boolean bl3 = this.ignoreVerify;
        boolean bl4 = true;
        this.ignoreVerify = true;
        this.ignoreModify = true;
        this.ignoreCharacter = true;
        cArray5[n4] = '\u0000';
        OS.SendMessage(this.handle, 177, 0L, -1L);
        long l2 = OS.SendMessage(this.handle, 198, 0L, 0L);
        OS.SendMessage(this.handle, 194, l2, cArray5);
        nArray[0] = this.translateOffset(nArray[0]);
        nArray2[0] = this.translateOffset(nArray2[0]);
        OS.SendMessage(this.handle, 177, (long)nArray[0], nArray2[0]);
        this.ignoreCharacter = bl;
        this.ignoreModify = bl2;
        this.ignoreVerify = bl3;
    }

    static int checkStyle(int n) {
        if ((n & 4) != 0 && (n & 2) != 0) {
            n &= 0xFFFFFFFD;
        }
        if (((n = Widget.checkBits(n, 16384, 0x1000000, 131072, 0, 0, 0)) & 0x80) != 0) {
            n |= 0x804;
            n &= 0xFFBFFFBF;
        } else if ((n & 4) != 0) {
            n &= 0xFFFFFCBF;
        }
        if ((n & 0x40) != 0) {
            n |= 2;
            n &= 0xFFFFFEFF;
        }
        if ((n & 2) != 0) {
            n &= 0xFFBFFFFF;
        }
        if ((n & 6) != 0) {
            return n;
        }
        if ((n & 0x300) != 0) {
            return n | 2;
        }
        return n | 4;
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
        int n2 = (int)OS.SendMessage(this.handle, 213, 0L, 0L) & Integer.MAX_VALUE;
        if (n2 < LIMIT) {
            OS.SendMessage(this.handle, 197, (long)Math.max(1, n2 - n), 0L);
        }
        if (!bl) {
            this.segments = null;
            return;
        }
        boolean bl2 = this.ignoreCharacter;
        boolean bl3 = this.ignoreModify;
        boolean bl4 = this.ignoreVerify;
        boolean bl5 = true;
        this.ignoreVerify = true;
        this.ignoreModify = true;
        this.ignoreCharacter = true;
        int n3 = OS.GetWindowTextLength(this.handle);
        int n4 = this.getCodePage();
        TCHAR tCHAR = new TCHAR(n4, n3 + 1);
        if (n3 > 0) {
            OS.GetWindowText(this.handle, tCHAR, n3 + 1);
        }
        tCHAR = this.deprocessText(tCHAR, 0, -1, true);
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        nArray[0] = this.untranslateOffset(nArray[0]);
        nArray2[0] = this.untranslateOffset(nArray2[0]);
        this.segments = null;
        OS.SendMessage(this.handle, 177, 0L, -1L);
        long l2 = OS.SendMessage(this.handle, 198, 0L, 0L);
        OS.SendMessage(this.handle, 194, l2, tCHAR);
        OS.SendMessage(this.handle, 177, (long)nArray[0], nArray2[0]);
        this.ignoreCharacter = bl2;
        this.ignoreModify = bl3;
        this.ignoreVerify = bl4;
    }

    public void clearSelection() {
        this.checkWidget();
        OS.SendMessage(this.handle, 177, -1L, 0L);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = 0;
        int n4 = 0;
        if (n == -1 || n2 == -1) {
            int n5;
            char[] cArray;
            int n6;
            boolean bl2;
            long l2 = 0L;
            long l3 = OS.GetDC(this.handle);
            long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l2 = OS.SelectObject(l3, l4);
            }
            TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
            OS.GetTextMetrics(l3, tEXTMETRIC);
            int n7 = (this.style & 4) != 0 ? 1 : (int)OS.SendMessage(this.handle, 186, 0L, 0L);
            n3 = n7 * tEXTMETRIC.tmHeight;
            RECT rECT = new RECT();
            int n8 = 11264;
            boolean bl3 = bl2 = (this.style & 2) != 0 && (this.style & 0x40) != 0;
            if (bl2 && n != -1) {
                n8 |= 0x10;
                rECT.right = n;
            }
            if ((n6 = OS.GetWindowTextLength(this.handle)) != 0) {
                cArray = new char[n6 + 1];
                OS.GetWindowText(this.handle, cArray, n6 + 1);
                OS.DrawText(l3, cArray, n6, rECT, n8);
                Arrays.fill(cArray, '\u0000');
                n4 = rECT.right - rECT.left;
            }
            if (bl2 && n2 == -1 && (n5 = rECT.bottom - rECT.top) != 0) {
                n3 = n5;
            }
            if ((this.style & 4) != 0 && this.message.length() > 0) {
                OS.SetRect(rECT, 0, 0, 0, 0);
                cArray = this.message.toCharArray();
                OS.DrawText(l3, cArray, cArray.length, rECT, n8);
                n4 = Math.max(n4, rECT.right - rECT.left);
            }
            if (l4 != 0L) {
                OS.SelectObject(l3, l2);
            }
            OS.ReleaseDC(this.handle, l3);
        }
        if (n4 == 0) {
            n4 = 64;
        }
        if (n3 == 0) {
            n3 = 64;
        }
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n3 = n2;
        }
        Rectangle rectangle = this.computeTrimInPixels(0, 0, n4, n3);
        return new Point(rectangle.width, rectangle.height);
    }

    @Override
    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        Rectangle rectangle;
        this.checkWidget();
        Rectangle rectangle2 = super.computeTrimInPixels(n, n2, n3, n4);
        long l2 = OS.SendMessage(this.handle, 212, 0L, 0L);
        Rectangle rectangle3 = rectangle2;
        rectangle3.x -= OS.LOWORD(l2);
        Rectangle rectangle4 = rectangle2;
        rectangle4.width += OS.LOWORD(l2) + OS.HIWORD(l2);
        if ((this.style & 0x100) != 0) {
            rectangle = rectangle2;
            ++rectangle.width;
        }
        if ((this.style & 0x800) != 0) {
            rectangle = rectangle2;
            --rectangle.x;
            Rectangle rectangle5 = rectangle2;
            --rectangle5.y;
            Rectangle rectangle6 = rectangle2;
            rectangle6.width += 2;
            Rectangle rectangle7 = rectangle2;
            rectangle7.height += 2;
            if (this == false) {
                int n5 = OS.GetSystemMetrics(45) - OS.GetSystemMetrics(5);
                int n6 = OS.GetSystemMetrics(46) - OS.GetSystemMetrics(6);
                Rectangle rectangle8 = rectangle2;
                rectangle8.x -= n5;
                Rectangle rectangle9 = rectangle2;
                rectangle9.y -= n6;
                Rectangle rectangle10 = rectangle2;
                rectangle10.width += 2 * n5;
                Rectangle rectangle11 = rectangle2;
                rectangle11.height += 2 * n6;
            }
        }
        return rectangle2;
    }

    public void copy() {
        this.checkWidget();
        OS.SendMessage(this.handle, 769, 0L, 0L);
    }

    @Override
    ScrollBar createScrollBar(int n) {
        return (this.style & 0x80) == 0 ? super.createScrollBar(n) : null;
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.message = "";
        this.doubleClick = true;
        this.tabs = 8;
        this.setTabStops(8);
        this.fixAlignment();
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
        int n = OS.GetWindowLong(this.handle, -16);
        if ((n & 0x800) != 0 || !OS.IsWindowEnabled(this.handle)) {
            return OS.GetSysColor(15);
        }
        return OS.GetSysColor(5);
    }

    TCHAR deprocessText(TCHAR tCHAR, int n, int n2, boolean bl) {
        int n3;
        if (tCHAR == null) {
            return null;
        }
        int n4 = tCHAR.length();
        if (n < 0) {
            n = 0;
        }
        char[] cArray = tCHAR.chars;
        if (tCHAR.chars[n4 - 1] == '\u0000') {
            --n4;
        }
        if (n2 == -1) {
            n2 = n4;
        }
        if (this.segments != null && n2 > this.segments[0] && (n3 = this.segments.length) > 0 && n <= this.segments[n3 - 1]) {
            int n5 = 0;
            while (n - n5 > this.segments[n5]) {
                ++n5;
            }
            int n6 = n5;
            for (int i = n; i < n2; ++i) {
                if (n6 < n3 && i - n6 == this.segments[n6]) {
                    ++n6;
                    continue;
                }
                cArray[i - n6 + n5] = cArray[i];
            }
            n4 = n2 - n - n6 + n5;
        }
        if (n != 0 || n2 != n4) {
            char[] cArray2 = new char[n4];
            System.arraycopy(cArray, n, cArray2, 0, n4);
            return new TCHAR(this.getCodePage(), cArray2, bl);
        }
        return tCHAR;
    }

    @Override
    boolean dragDetect(long l2, int n, int n2, boolean bl, boolean[] blArray, boolean[] blArray2) {
        if (bl) {
            long l3;
            int n3;
            int[] nArray = new int[]{0};
            int[] nArray2 = new int[]{0};
            OS.SendMessage(this.handle, 176, nArray, nArray2);
            if (nArray[0] != nArray2[0] && nArray[0] <= (n3 = OS.LOWORD(OS.SendMessage(this.handle, 215, 0L, l3 = OS.MAKELPARAM(n, n2)))) && n3 < nArray2[0] && super.dragDetect(l2, n, n2, bl, blArray, blArray2)) {
                if (blArray2 != null) {
                    blArray2[0] = true;
                }
                return true;
            }
            return false;
        }
        return super.dragDetect(l2, n, n2, bl, blArray, blArray2);
    }

    @Override
    void maybeEnableDarkSystemTheme() {
        if (this.hasCustomBackground() || this.hasCustomForeground()) {
            super.maybeEnableDarkSystemTheme();
        }
    }

    void fixAlignment() {
        if ((this.style & 0x8000000) != 0) {
            return;
        }
        int n = OS.GetWindowLong(this.handle, -20);
        int n2 = OS.GetWindowLong(this.handle, -16);
        if ((this.style & 0x2000000) != 0) {
            if ((this.style & 0x20000) != 0) {
                n |= 0x1000;
                n2 |= 2;
            }
            if ((this.style & 0x4000) != 0) {
                n &= 0xFFFFEFFF;
                n2 &= 0xFFFFFFFD;
            }
        } else {
            if ((this.style & 0x20000) != 0) {
                n &= 0xFFFFEFFF;
                n2 &= 0xFFFFFFFD;
            }
            if ((this.style & 0x4000) != 0) {
                n |= 0x1000;
                n2 |= 2;
            }
        }
        if ((this.style & 0x1000000) != 0) {
            n2 |= 1;
        }
        OS.SetWindowLong(this.handle, -20, n);
        OS.SetWindowLong(this.handle, -16, n2);
    }

    @Override
    int getBorderWidthInPixels() {
        this.checkWidget();
        return super.getBorderWidthInPixels();
    }

    public int getCaretLineNumber() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 201, -1L, 0L);
    }

    public Point getCaretLocation() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getCaretLocationInPixels());
    }

    Point getCaretLocationInPixels() {
        int n = this.translateOffset(this.getCaretPosition());
        long l2 = OS.SendMessage(this.handle, 214, (long)n, 0L);
        if (l2 == -1L) {
            l2 = 0L;
            if (n >= OS.GetWindowTextLength(this.handle)) {
                int[] nArray = new int[]{0};
                int[] nArray2 = new int[]{0};
                OS.SendMessage(this.handle, 176, nArray, nArray2);
                OS.SendMessage(this.handle, 177, (long)n, n);
                boolean bl = true;
                this.ignoreModify = true;
                this.ignoreCharacter = true;
                OS.SendMessage(this.handle, 194, 0L, new char[]{' ', '\u0000'});
                l2 = OS.SendMessage(this.handle, 214, (long)n, 0L);
                OS.SendMessage(this.handle, 177, (long)n, n + 1);
                OS.SendMessage(this.handle, 194, 0L, new char[]{'\u0000'});
                boolean bl2 = false;
                this.ignoreModify = false;
                this.ignoreCharacter = false;
                OS.SendMessage(this.handle, 177, (long)nArray[0], nArray[0]);
                OS.SendMessage(this.handle, 177, (long)nArray[0], nArray2[0]);
            }
        }
        return new Point(OS.GET_X_LPARAM(l2), OS.GET_Y_LPARAM(l2));
    }

    public int getCaretPosition() {
        this.checkWidget();
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        int n = nArray[0];
        if (nArray[0] != nArray2[0]) {
            int n2;
            int n3 = (int)OS.SendMessage(this.handle, 201, (long)nArray[0], 0L);
            if (n3 == (n2 = (int)OS.SendMessage(this.handle, 201, (long)nArray2[0], 0L))) {
                POINT pOINT;
                int n4 = OS.GetWindowThreadProcessId(this.handle, null);
                GUITHREADINFO gUITHREADINFO = new GUITHREADINFO();
                gUITHREADINFO.cbSize = GUITHREADINFO.sizeof;
                if (OS.GetGUIThreadInfo(n4, gUITHREADINFO) && (gUITHREADINFO.hwndCaret == this.handle || gUITHREADINFO.hwndCaret == 0L) && OS.GetCaretPos(pOINT = new POINT())) {
                    long l2 = OS.SendMessage(this.handle, 214, (long)nArray2[0], 0L);
                    if (l2 == -1L) {
                        long l3 = OS.SendMessage(this.handle, 214, (long)nArray[0], 0L);
                        int n5 = OS.GET_X_LPARAM(l3);
                        if (pOINT.x > n5) {
                            n = nArray2[0];
                        }
                    } else {
                        int n6 = OS.GET_X_LPARAM(l2);
                        if (pOINT.x >= n6) {
                            n = nArray2[0];
                        }
                    }
                }
            } else {
                int n7 = (int)OS.SendMessage(this.handle, 187, -1L, 0L);
                int n8 = (int)OS.SendMessage(this.handle, 201, (long)n7, 0L);
                if (n8 == n2) {
                    n = nArray2[0];
                }
            }
        }
        return this.untranslateOffset(n);
    }

    public int getCharCount() {
        this.checkWidget();
        int n = OS.GetWindowTextLength(this.handle);
        return this.untranslateOffset(n);
    }

    public boolean getDoubleClickEnabled() {
        this.checkWidget();
        return this.doubleClick;
    }

    public char getEchoChar() {
        this.checkWidget();
        return (char)OS.SendMessage(this.handle, 210, 0L, 0L);
    }

    public boolean getEditable() {
        this.checkWidget();
        int n = OS.GetWindowLong(this.handle, -16);
        return (n & 0x800) == 0;
    }

    public int getLineCount() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 186, 0L, 0L);
    }

    public String getLineDelimiter() {
        this.checkWidget();
        return DELIMITER;
    }

    public int getLineHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getLineHeightInPixels());
    }

    int getLineHeightInPixels() {
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.GetTextMetrics(l3, tEXTMETRIC);
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        return tEXTMETRIC.tmHeight;
    }

    @Override
    public int getOrientation() {
        return super.getOrientation();
    }

    public String getMessage() {
        this.checkWidget();
        return this.message;
    }

    int getPosition(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        long l2 = OS.MAKELPARAM(point.x, point.y);
        int n = OS.LOWORD(OS.SendMessage(this.handle, 215, 0L, l2));
        return this.untranslateOffset(n);
    }

    public Point getSelection() {
        this.checkWidget();
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        return new Point(this.untranslateOffset(nArray[0]), this.untranslateOffset(nArray2[0]));
    }

    public int getSelectionCount() {
        this.checkWidget();
        Point point = this.getSelection();
        return point.y - point.x;
    }

    public String getSelectionText() {
        this.checkWidget();
        int n = OS.GetWindowTextLength(this.handle);
        if (n == 0) {
            return "";
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        if (nArray[0] == nArray2[0]) {
            return "";
        }
        TCHAR tCHAR = new TCHAR(this.getCodePage(), n + 1);
        OS.GetWindowText(this.handle, tCHAR, n + 1);
        if (this.segments != null) {
            tCHAR = this.deprocessText(tCHAR, nArray[0], nArray2[0], false);
            return tCHAR.toString();
        }
        return tCHAR.toString(nArray[0], nArray2[0] - nArray[0]);
    }

    public int getTabs() {
        this.checkWidget();
        return this.tabs;
    }

    int getTabWidth(int n) {
        long l2 = 0L;
        RECT rECT = new RECT();
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        int n2 = 3104;
        OS.DrawText(l3, new char[]{' '}, 1, rECT, 3104);
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        return (rECT.right - rECT.left) * n;
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

    public char[] getTextChars() {
        this.checkWidget();
        int n = OS.GetWindowTextLength(this.handle);
        if (n == 0) {
            return new char[0];
        }
        TCHAR tCHAR = new TCHAR(this.getCodePage(), n + 1);
        OS.GetWindowText(this.handle, tCHAR, n + 1);
        if (this.segments != null) {
            tCHAR = this.deprocessText(tCHAR, 0, -1, false);
        }
        char[] cArray = new char[n];
        System.arraycopy(tCHAR.chars, 0, cArray, 0, n);
        tCHAR.clear();
        return cArray;
    }

    public String getText(int n, int n2) {
        this.checkWidget();
        if (n > n2 || 0 > n2) {
            return "";
        }
        int n3 = OS.GetWindowTextLength(this.handle);
        if (n > (n2 = Math.min(n2, this.untranslateOffset(n3) - 1))) {
            return "";
        }
        n = Math.max(0, n);
        return this.getText().substring(n, n2 + 1);
    }

    public int getTextLimit() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 213, 0L, 0L) & Integer.MAX_VALUE;
        if (this.segments != null && n < LIMIT) {
            n = Math.max(1, n - this.segments.length);
        }
        return n;
    }

    public int getTopIndex() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return 0;
        }
        return (int)OS.SendMessage(this.handle, 206, 0L, 0L);
    }

    public int getTopPixel() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getTopPixelInPixels());
    }

    int getTopPixelInPixels() {
        int[] nArray = new int[2];
        long l2 = OS.SendMessage(this.handle, 1245, 0L, nArray);
        if (l2 == 1L) {
            return nArray[1];
        }
        return this.getTopIndex() * this.getLineHeightInPixels();
    }

    public void insert(String string) {
        Object object;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        string = Display.withCrLf(string);
        if (this.hooks(25) || this.filters(25)) {
            object = new int[]{0};
            int[] nArray = new int[]{0};
            OS.SendMessage(this.handle, 176, object, nArray);
            string = this.verifyText(string, object[0], nArray[0], null);
            if (string == null) {
                return;
            }
        }
        this.clearSegments(true);
        object = new TCHAR(this.getCodePage(), string, true);
        this.ignoreCharacter = true;
        OS.SendMessage(this.handle, 194, 0L, (TCHAR)object);
        this.ignoreCharacter = false;
        if ((this.state & 0x400000) != 0) {
            super.updateTextDirection(0x6000000);
        }
        this.applySegments();
    }

    public void paste() {
        this.checkWidget();
        if ((this.style & 8) != 0) {
            return;
        }
        OS.SendMessage(this.handle, 770, 0L, 0L);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.message = null;
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
        this.clearSegments(true);
        this.applySegments();
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

    @Override
    int resolveTextDirection() {
        int n = 0;
        int n2 = OS.GetWindowTextLength(this.handle);
        if (n2 > 0) {
            TCHAR tCHAR = new TCHAR(this.getCodePage(), n2 + 1);
            OS.GetWindowText(this.handle, tCHAR, n2 + 1);
            if (this.segments != null) {
                tCHAR = this.deprocessText(tCHAR, 0, -1, false);
                n = BidiUtil.resolveTextDirection(tCHAR.toString());
            } else {
                n = BidiUtil.resolveTextDirection(tCHAR.toString(0, n2));
            }
            if (n == 0) {
                n = (this.style & 0x4000000) != 0 ? 0x4000000 : 0x2000000;
            }
        }
        return n;
    }

    public void selectAll() {
        this.checkWidget();
        OS.SendMessage(this.handle, 177, 0L, -1L);
    }

    boolean sendKeyEvent(int n, int n2, long l2, long l3, Event event) {
        String string;
        if (!super.sendKeyEvent(n, n2, l2, l3, event)) {
            return false;
        }
        if ((this.style & 8) != 0) {
            return true;
        }
        if (this.ignoreVerify) {
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
        if (OS.GetKeyState(1) < 0 && this.handle == OS.GetCapture()) {
            return true;
        }
        String string2 = "";
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        switch (c) {
            case '\b': {
                if (nArray[0] != nArray2[0]) break;
                if (nArray[0] == 0) {
                    return true;
                }
                int n4 = (int)OS.SendMessage(this.handle, 187, -1L, 0L);
                nArray[0] = nArray[0] == n4 ? nArray[0] - DELIMITER.length() : nArray[0] - 1;
                nArray[0] = Math.max(nArray[0], 0);
                break;
            }
            case '\u007f': {
                if (nArray[0] != nArray2[0]) break;
                int n4 = OS.GetWindowTextLength(this.handle);
                if (nArray[0] == n4) {
                    return true;
                }
                int n5 = (int)OS.SendMessage(this.handle, 201, (long)nArray2[0], 0L);
                int n6 = (int)OS.SendMessage(this.handle, 187, (long)(n5 + 1), 0L);
                nArray2[0] = nArray2[0] == n6 - DELIMITER.length() ? nArray2[0] + DELIMITER.length() : nArray2[0] + 1;
                nArray2[0] = Math.min(nArray2[0], n4);
                break;
            }
            case '\r': {
                if ((this.style & 4) != 0) {
                    return true;
                }
                string2 = DELIMITER;
                break;
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
        string = Display.withCrLf(string);
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        OS.SendMessage(this.handle, 177, (long)nArray[0], nArray2[0]);
        this.ignoreCharacter = true;
        OS.SendMessage(this.handle, 194, 0L, tCHAR);
        this.ignoreCharacter = false;
        return false;
    }

    @Override
    void setBackgroundImage(long l2) {
        int n = 133;
        OS.RedrawWindow(this.handle, null, 0L, 133);
    }

    @Override
    void setBackgroundPixel(int n) {
        this.maybeEnableDarkSystemTheme();
        int n2 = 133;
        OS.RedrawWindow(this.handle, null, 0L, 133);
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5) {
        int n6;
        long l2;
        if ((n5 & 1) == 0 && n3 != 0) {
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            l2 = OS.SendMessage(this.handle, 212, 0L, 0L);
            int n7 = OS.LOWORD(l2) + OS.HIWORD(l2);
            if (rECT.right - rECT.left <= n7) {
                int[] nArray = new int[]{0};
                int[] nArray2 = new int[]{0};
                OS.SendMessage(this.handle, 176, nArray, nArray2);
                if (nArray[0] != 0 || nArray2[0] != 0) {
                    OS.SetWindowPos(this.handle, 0L, n, n2, n3, n4, n5);
                    OS.SendMessage(this.handle, 177, 0L, 0L);
                    OS.SendMessage(this.handle, 177, (long)nArray[0], nArray2[0]);
                    return;
                }
            }
        }
        super.setBoundsInPixels(n, n2, n3, n4, n5);
        if ((n5 & 1) == 0 && ((n6 = OS.GetWindowLong(this.handle, -16)) & 4) != 0) {
            l2 = 0L;
            long l3 = OS.GetDC(this.handle);
            long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l2 = OS.SelectObject(l3, l4);
            }
            TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
            OS.GetTextMetrics(l3, tEXTMETRIC);
            if (l4 != 0L) {
                OS.SelectObject(l3, l2);
            }
            OS.ReleaseDC(this.handle, l3);
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            if (rECT.bottom - rECT.top < tEXTMETRIC.tmHeight) {
                long l5 = OS.SendMessage(this.handle, 212, 0L, 0L);
                RECT rECT2 = rECT;
                rECT2.left += OS.LOWORD(l5);
                RECT rECT3 = rECT;
                rECT3.right -= OS.HIWORD(l5);
                rECT.top = 0;
                rECT.bottom = tEXTMETRIC.tmHeight;
                OS.SendMessage(this.handle, 179, 0L, rECT);
            }
        }
    }

    @Override
    void setDefaultFont() {
        super.setDefaultFont();
        this.setMargins();
    }

    public void setDoubleClickEnabled(boolean bl) {
        this.checkWidget();
        this.doubleClick = bl;
    }

    public void setEchoChar(char c) {
        this.checkWidget();
        if ((this.style & 2) != 0) {
            return;
        }
        this.allowPasswordChar = true;
        OS.SendMessage(this.handle, 204, (long)c, 0L);
        this.allowPasswordChar = false;
        OS.InvalidateRect(this.handle, null, true);
    }

    public void setEditable(boolean bl) {
        this.checkWidget();
        this.style &= 0xFFFFFFF7;
        if (!bl) {
            this.style |= 8;
        }
        OS.SendMessage(this.handle, 207, bl ? 0L : 1L, 0L);
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        super.setFont(font);
        this.setTabStops(this.tabs);
        this.setMargins();
    }

    @Override
    void setForegroundPixel(int n) {
        this.maybeEnableDarkSystemTheme();
        super.setForegroundPixel(n);
    }

    void setMargins() {
        if ((this.style & 0x80) != 0) {
            boolean bl = (this.style & 0x4000000) != 0;
            int n = bl ? 2 : 1;
            int n2 = bl ? 1 : 2;
            int n3 = 0;
            if ((this.style & 0x200) != 0) {
                n3 |= n;
            }
            if ((this.style & 0x100) != 0) {
                n3 |= n2;
            }
            if (n3 != 0) {
                int n4 = OS.GetSystemMetrics(49);
                OS.SendMessage(this.handle, 211, (long)n3, OS.MAKELPARAM(n4, n4));
            }
        }
    }

    public void setMessage(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        this.message = string;
        int n = OS.GetWindowLong(this.handle, -16);
        if ((n & 4) == 0) {
            int n2 = string.length();
            char[] cArray = new char[n2 + 1];
            string.getChars(0, n2, cArray, 0);
            OS.SendMessage(this.handle, 5377, 0L, cArray);
        }
    }

    @Override
    public void setOrientation(int n) {
        super.setOrientation(n);
    }

    public void setSelection(int n) {
        this.checkWidget();
        n = this.translateOffset(n);
        OS.SendMessage(this.handle, 177, (long)n, n);
        OS.SendMessage(this.handle, 183, 0L, 0L);
    }

    public void setSelection(int n, int n2) {
        this.checkWidget();
        n = this.translateOffset(n);
        n2 = this.translateOffset(n2);
        OS.SendMessage(this.handle, 177, (long)n, n2);
        OS.SendMessage(this.handle, 183, 0L, 0L);
    }

    @Override
    public void setRedraw(boolean bl) {
        this.checkWidget();
        super.setRedraw(bl);
        if (!this.getDrawing()) {
            return;
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        if (!bl) {
            this.oldStart = nArray[0];
            this.oldEnd = nArray2[0];
        } else {
            if (this.oldStart == nArray[0] && this.oldEnd == nArray2[0]) {
                return;
            }
            OS.SendMessage(this.handle, 183, 0L, 0L);
        }
    }

    public void setSelection(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        this.setSelection(point.x, point.y);
    }

    public void setTabs(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        this.tabs = n;
        this.setTabStops(this.tabs);
    }

    void setTabStops(int n) {
        int n2 = this.getTabWidth(n) * 4 / OS.LOWORD(OS.GetDialogBaseUnits());
        OS.SendMessage(this.handle, 203, 1L, new int[]{n2});
    }

    public void setText(String string) {
        int n;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        string = Display.withCrLf(string);
        if ((this.hooks(25) || this.filters(25)) && (string = this.verifyText(string, 0, n = OS.GetWindowTextLength(this.handle), null)) == null) {
            return;
        }
        this.clearSegments(false);
        n = (int)OS.SendMessage(this.handle, 213, 0L, 0L) & Integer.MAX_VALUE;
        if (string.length() > n) {
            string = string.substring(0, n);
        }
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        OS.SetWindowText(this.handle, tCHAR);
        if ((this.state & 0x400000) != 0) {
            super.updateTextDirection(0x6000000);
        }
        this.applySegments();
        int n2 = OS.GetWindowLong(this.handle, -16);
        if ((n2 & 4) != 0) {
            this.sendEvent(24);
        }
    }

    public void setTextChars(char[] object) {
        Object object2;
        int n;
        this.checkWidget();
        if (object == null) {
            this.error(4);
        }
        object = Display.withCrLf(object);
        if (this.hooks(25) || this.filters(25)) {
            n = OS.GetWindowTextLength(this.handle);
            object2 = this.verifyText(new String((char[])object), 0, n, null);
            if (object2 == null) {
                return;
            }
            object = new char[((String)object2).length()];
            ((String)object2).getChars(0, ((char[])object).length, (char[])object, 0);
        }
        this.clearSegments(false);
        n = (int)OS.SendMessage(this.handle, 213, 0L, 0L) & Integer.MAX_VALUE;
        if (((char[])object).length > n) {
            object2 = new char[n];
            System.arraycopy(object, 0, object2, 0, n);
            object = object2;
        }
        object2 = new TCHAR(this.getCodePage(), (char[])object, true);
        OS.SetWindowText(this.handle, (TCHAR)object2);
        ((TCHAR)object2).clear();
        if ((this.state & 0x400000) != 0) {
            super.updateTextDirection(0x6000000);
        }
        this.applySegments();
        int n2 = OS.GetWindowLong(this.handle, -16);
        if ((n2 & 4) != 0) {
            this.sendEvent(24);
        }
    }

    public void setTextLimit(int n) {
        this.checkWidget();
        if (n == 0) {
            this.error(7);
        }
        if (this.segments != null && n > 0) {
            OS.SendMessage(this.handle, 197, (long)(n + Math.min(this.segments.length, LIMIT - n)), 0L);
        } else {
            OS.SendMessage(this.handle, 197, (long)n, 0L);
        }
    }

    public void setTopIndex(int n) {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.handle, 186, 0L, 0L);
        n = Math.min(Math.max(n, 0), n2 - 1);
        int n3 = (int)OS.SendMessage(this.handle, 206, 0L, 0L);
        OS.SendMessage(this.handle, 182, 0L, n - n3);
    }

    public void showSelection() {
        this.checkWidget();
        OS.SendMessage(this.handle, 183, 0L, 0L);
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

    int untranslateOffset(int n) {
        if (this.segments == null) {
            return n;
        }
        int n2 = this.segments.length;
        for (int i = 0; i < n2 && n > this.segments[i]; --n, ++i) {
        }
        return n;
    }

    @Override
    void updateMenuLocation(Event event) {
        Point point = this.display.mapInPixels((Control)this, null, this.getCaretLocationInPixels());
        event.setLocationInPixels(point.x, point.y + this.getLineHeightInPixels());
    }

    @Override
    void updateOrientation() {
        int n = OS.GetWindowLong(this.handle, -20);
        n = (this.style & 0x4000000) != 0 ? (n |= 0x6000) : (n &= 0xFFFF9FFF);
        OS.SetWindowLong(this.handle, -20, n);
        this.fixAlignment();
    }

    @Override
    boolean updateTextDirection(int n) {
        if (super.updateTextDirection(n)) {
            this.clearSegments(true);
            this.applySegments();
            return true;
        }
        return false;
    }

    String verifyText(String string, int n, int n2, Event event) {
        if (this.ignoreVerify) {
            return string;
        }
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
    int widgetStyle() {
        int n = super.widgetStyle() | 0x80;
        if ((this.style & 0x80) != 0) {
            n &= 0xFFEFFFFF;
            n &= 0xFFDFFFFF;
        }
        if ((this.style & 0x400000) != 0) {
            n |= 0x20;
        }
        if ((this.style & 0x1000000) != 0) {
            n |= 1;
        }
        if ((this.style & 0x20000) != 0) {
            n |= 2;
        }
        if ((this.style & 8) != 0) {
            n |= 0x800;
        }
        if ((this.style & 0x80) != 0) {
            n |= 0x2000000;
        }
        if ((this.style & 4) != 0) {
            if ((this.style & 8) != 0 && (this.style & 0x400B00) == 0 && OS.IsAppThemed()) {
                n |= 4;
            }
            return n;
        }
        n |= 0x144;
        if ((this.style & 0x40) != 0) {
            n &= 0xFFEFFF7F;
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        return EditClass;
    }

    @Override
    long windowProc() {
        return EditProc;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        int n2;
        boolean bl;
        boolean bl2 = this.hooks(49) || this.filters(49);
        boolean bl3 = false;
        boolean bl4 = bl = (this.state & 0x400000) != 0;
        if (bl2 || bl) {
            switch (n) {
                case 198: {
                    if (bl2) {
                        return 0L;
                    }
                    bl = false;
                    break;
                }
                case 199: 
                case 772: {
                    if (!bl2) break;
                    return 0L;
                }
                case 256: {
                    if (l3 == 46L) break;
                    bl2 = false;
                    bl = false;
                    break;
                }
                case 769: {
                    bl2 = this.segments != null;
                    bl = false;
                    break;
                }
                case 258: {
                    if (!this.ignoreCharacter && OS.GetKeyState(17) >= 0 && OS.GetKeyState(18) >= 0) break;
                    bl2 = false;
                    bl = false;
                    break;
                }
                case 768: 
                case 770: 
                case 771: {
                    break;
                }
                default: {
                    bl2 = false;
                    bl = false;
                }
            }
        }
        if (bl2) {
            if (this.getDrawing() && OS.IsWindowVisible(this.handle)) {
                bl3 = true;
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
            this.clearSegments(true);
        }
        if (n == 199 && ((n2 = OS.GetWindowLong(this.handle, -16)) & 4) == 0) {
            LRESULT lRESULT = this.wmClipboard(199, l3, l4);
            if (lRESULT != null) {
                return lRESULT.value;
            }
            return this.callWindowProc(l2, 199, l3, l4);
        }
        if (n == 204 && !this.allowPasswordChar) {
            return 1L;
        }
        if (n == Display.SWT_RESTORECARET) {
            this.callWindowProc(l2, 8, 0L, 0L);
            this.callWindowProc(l2, 7, 0L, 0L);
            return 1L;
        }
        long l5 = super.windowProc(l2, n, l3, l4);
        if (bl) {
            super.updateTextDirection(0x6000000);
        }
        if (bl2) {
            this.applySegments();
            if (bl3) {
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                OS.InvalidateRect(this.handle, null, true);
            }
            OS.SendMessage(this.handle, 183, 0L, 0L);
        }
        return l5;
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        if (this.ignoreCharacter) {
            return null;
        }
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 127: {
                String string;
                Matcher matcher;
                if (OS.GetKeyState(17) >= 0) break;
                if ((this.style & 8) != 0 || (this.style & 0x400000) != 0) {
                    return LRESULT.ZERO;
                }
                Point point = this.getSelection();
                int n = point.x;
                int n2 = point.y;
                if (n == n2 && (matcher = CTRL_BS_PATTERN.matcher(string = this.getText(0, n - 1))).find()) {
                    n = matcher.start();
                    n2 = matcher.end();
                    OS.SendMessage(this.handle, 177, (long)n, n2);
                }
                if (n < n2) {
                    OS.SendMessage(this.handle, 194, 1L, 0L);
                }
                return LRESULT.ZERO;
            }
        }
        if ((this.style & 4) != 0) {
            switch ((int)l2) {
                case 13: {
                    this.sendSelectionEvent(14);
                }
                case 9: 
                case 27: {
                    return LRESULT.ZERO;
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_CLEAR(long l2, long l3) {
        LRESULT lRESULT = super.WM_CLEAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return this.wmClipboard(771, l2, l3);
    }

    @Override
    LRESULT WM_CUT(long l2, long l3) {
        LRESULT lRESULT = super.WM_CUT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return this.wmClipboard(768, l2, l3);
    }

    @Override
    LRESULT WM_DRAWITEM(long l2, long l3) {
        DRAWITEMSTRUCT dRAWITEMSTRUCT = new DRAWITEMSTRUCT();
        OS.MoveMemory(dRAWITEMSTRUCT, l3, DRAWITEMSTRUCT.sizeof);
        RECT rECT = new RECT();
        OS.SetRect(rECT, dRAWITEMSTRUCT.left, dRAWITEMSTRUCT.top, dRAWITEMSTRUCT.right, dRAWITEMSTRUCT.bottom);
        POINT pOINT = new POINT();
        OS.MapWindowPoints(dRAWITEMSTRUCT.hwndItem, this.handle, pOINT, 1);
        this.drawBackground(dRAWITEMSTRUCT.hDC, rECT, -1, pOINT.x, pOINT.y);
        if (dRAWITEMSTRUCT.CtlID == 256 && dRAWITEMSTRUCT.hwndItem == this.hwndActiveIcon && OS.IsAppThemed()) {
            int n = OS.GetKeyState(1) < 0 ? 3 : 2;
            OS.DrawThemeBackground(this.display.hButtonThemeAuto(), dRAWITEMSTRUCT.hDC, 1, n, rECT, null);
        }
        long l4 = dRAWITEMSTRUCT.CtlID == 512 ? this.display.hIconSearch : this.display.hIconCancel;
        int n = (rECT.bottom - rECT.right) / 2;
        OS.DrawIconEx(dRAWITEMSTRUCT.hDC, 0, n, l4, 0, 0, 0, 0L, 3);
        return LRESULT.ONE;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        Control control;
        int n;
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if ((this.style & 8) != 0 && (this.style & 0xB00) == 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 4) != 0 && (control = this.findBackgroundControl()) == null && this.background == -1 && (this.state & 0x100) != 0 && OS.IsAppThemed() && (control = this.findThemeControl()) != null) {
            RECT rECT = new RECT();
            OS.GetClientRect(this.handle, rECT);
            this.fillThemeBackground(l2, control, rECT);
            return LRESULT.ONE;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETDLGCODE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 8) != 0) {
            long l4 = this.callWindowProc(this.handle, 135, l2, l3);
            return new LRESULT(l4 &= 0xFFFFFFFFFFFFFFF9L);
        }
        return null;
    }

    @Override
    LRESULT WM_GETOBJECT(long l2, long l3) {
        if ((this.style & 0x80) != 0 && this.accessible == null) {
            this.accessible = this.new_Accessible(this);
        }
        return super.WM_GETOBJECT(l2, l3);
    }

    @Override
    LRESULT WM_IME_CHAR(long l2, long l3) {
        Display display = this.display;
        display.lastKey = 0;
        display.lastAscii = (int)l2;
        Display display2 = display;
        Display display3 = display;
        Display display4 = display;
        boolean bl = false;
        display4.lastDead = false;
        display3.lastNull = false;
        display2.lastVirtual = false;
        if (!this.sendKeyEvent(1, 646, l2, l3)) {
            return LRESULT.ZERO;
        }
        this.ignoreCharacter = true;
        long l4 = this.callWindowProc(this.handle, 646, l2, l3);
        MSG mSG = new MSG();
        int n = 10420227;
        while (OS.PeekMessage(mSG, this.handle, 258, 258, 10420227)) {
            OS.TranslateMessage(mSG);
            OS.DispatchMessage(mSG);
        }
        this.ignoreCharacter = false;
        this.sendKeyEvent(2, 646, l2, l3);
        Display display5 = display;
        Display display6 = display;
        boolean bl2 = false;
        display6.lastAscii = 0;
        display5.lastKey = 0;
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_LBUTTONDBLCLK(long l2, long l3) {
        int n;
        int n2;
        LRESULT lRESULT = null;
        this.sendMouseEvent(3, 1, this.handle, l3);
        if (!this.sendMouseEvent(8, 1, this.handle, l3)) {
            lRESULT = LRESULT.ZERO;
        }
        if (!this.display.captureChanged && !this.isDisposed() && OS.GetCapture() != this.handle) {
            OS.SetCapture(this.handle);
        }
        if (!this.doubleClick) {
            return LRESULT.ZERO;
        }
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.handle, 176, nArray, nArray2);
        if (nArray[0] == nArray2[0] && (n2 = OS.GetWindowTextLength(this.handle)) == nArray[0] && (n = (int)OS.SendMessage(this.handle, 193, (long)n2, 0L)) == 0) {
            return LRESULT.ZERO;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_PASTE(long l2, long l3) {
        LRESULT lRESULT = super.WM_PASTE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return this.wmClipboard(770, l2, l3);
    }

    @Override
    LRESULT WM_SETCURSOR(long l2, long l3) {
        short s;
        LRESULT lRESULT = super.WM_SETCURSOR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 0x80) != 0 && (s = (short)OS.LOWORD(l3)) == 1) {
            POINT pOINT = new POINT();
            OS.GetCursorPos(pOINT);
            OS.ScreenToClient(this.handle, pOINT);
            long l4 = OS.ChildWindowFromPointEx(this.handle, pOINT, 1);
            if (l4 != this.handle) {
                OS.SetCursor(OS.LoadCursor(0L, 32512L));
                return LRESULT.ONE;
            }
        }
        return null;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (this.isDisposed()) {
            return lRESULT;
        }
        if ((this.style & 0x80) != 0) {
            boolean bl = (this.style & 0x4000000) != 0;
            long l4 = OS.GetDlgItem(this.handle, bl ? 256 : 512);
            long l5 = OS.GetDlgItem(this.handle, bl ? 512 : 256);
            int n = OS.LOWORD(l3);
            int n2 = OS.HIWORD(l3);
            int n3 = OS.GetSystemMetrics(49);
            int n4 = 276;
            if (l4 != 0L) {
                OS.SetWindowPos(l4, 0L, 0, 0, n3, n2, 276);
            }
            if (l5 != 0L) {
                OS.SetWindowPos(l5, 0L, n - n3, 0, n3, n2, 276);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_UNDO(long l2, long l3) {
        LRESULT lRESULT = super.WM_UNDO(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return this.wmClipboard(772, l2, l3);
    }

    LRESULT wmClipboard(int n, long l2, long l3) {
        int n2;
        boolean bl;
        if ((this.style & 8) != 0) {
            return null;
        }
        if (!this.hooks(25) && !this.filters(25)) {
            return null;
        }
        boolean bl2 = false;
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        String string = null;
        switch (n) {
            case 768: 
            case 771: {
                OS.SendMessage(this.handle, 176, nArray, nArray2);
                if (nArray[0] == nArray2[0]) break;
                string = "";
                bl2 = true;
                break;
            }
            case 770: {
                OS.SendMessage(this.handle, 176, nArray, nArray2);
                string = this.getClipboardText();
                break;
            }
            case 199: 
            case 772: {
                if (OS.SendMessage(this.handle, 198, 0L, 0L) == 0L) break;
                bl = true;
                this.ignoreCharacter = true;
                this.ignoreModify = true;
                this.callWindowProc(this.handle, n, l2, l3);
                n2 = OS.GetWindowTextLength(this.handle);
                int[] nArray3 = new int[]{0};
                int[] nArray4 = new int[]{0};
                OS.SendMessage(this.handle, 176, nArray3, nArray4);
                if (n2 != 0 && nArray3[0] != nArray4[0]) {
                    char[] cArray = new char[n2 + 1];
                    OS.GetWindowText(this.handle, cArray, n2 + 1);
                    string = new String(cArray, nArray3[0], nArray4[0] - nArray3[0]);
                } else {
                    string = "";
                }
                this.callWindowProc(this.handle, n, l2, l3);
                OS.SendMessage(this.handle, 176, nArray, nArray2);
                boolean bl3 = false;
                this.ignoreCharacter = false;
                this.ignoreModify = false;
                break;
            }
        }
        if (string != null) {
            String string2 = string;
            if ((string = this.verifyText(string, nArray[0], nArray2[0], null)) == null) {
                return LRESULT.ZERO;
            }
            if (!string.equals(string2)) {
                if (bl2) {
                    this.callWindowProc(this.handle, n, l2, l3);
                }
                string = Display.withCrLf(string);
                TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
                this.ignoreCharacter = true;
                OS.SendMessage(this.handle, 194, 0L, tCHAR);
                this.ignoreCharacter = false;
                return LRESULT.ZERO;
            }
        }
        if (n == 772) {
            bl = true;
            this.ignoreCharacter = true;
            this.ignoreVerify = true;
            this.callWindowProc(this.handle, 772, l2, l3);
            n2 = 0;
            this.ignoreCharacter = false;
            this.ignoreVerify = false;
            return LRESULT.ONE;
        }
        return null;
    }

    @Override
    LRESULT wmColorChild(long l2, long l3) {
        Control control;
        int n;
        if ((this.style & 8) != 0 && (this.style & 0xB00) == 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 4) != 0 && (control = this.findBackgroundControl()) == null && this.background == -1 && (this.state & 0x100) != 0 && OS.IsAppThemed() && (control = this.findThemeControl()) != null) {
            OS.SetTextColor(l2, this.getForegroundPixel());
            OS.SetBkColor(l2, this.getBackgroundPixel());
            OS.SetBkMode(l2, 1);
            return new LRESULT(OS.GetStockObject(5));
        }
        return super.wmColorChild(l2, l3);
    }

    @Override
    LRESULT wmCommandChild(long l2, long l3) {
        int n = OS.HIWORD(l2);
        switch (n) {
            case 768: {
                if (this.findImageControl() != null) {
                    OS.InvalidateRect(this.handle, null, true);
                }
                if ((this.style & 0x80) != 0) {
                    boolean bl = OS.GetWindowTextLength(this.handle) != 0;
                    long l4 = OS.GetDlgItem(this.handle, 256);
                    if (l4 != 0L) {
                        OS.ShowWindow(l4, bl ? 5 : 0);
                    }
                }
                if (this.ignoreModify) break;
                this.sendEvent(24);
                if (!this.isDisposed()) break;
                return LRESULT.ZERO;
            }
            case 1792: 
            case 1793: {
                this.state &= 0xFFBFFFFF;
                int n2 = OS.GetWindowLong(this.handle, -20);
                if ((n2 & 0x2000) != 0) {
                    this.style &= 0xFDFFFFFF;
                    this.style |= 0x4000000;
                } else {
                    this.style &= 0xFBFFFFFF;
                    this.style |= 0x2000000;
                }
                Event event = new Event();
                event.doit = true;
                this.sendEvent(44, event);
                if (!event.doit) {
                    if (n == 1792) {
                        n2 |= 0x6000;
                        this.style &= 0xFDFFFFFF;
                        this.style |= 0x4000000;
                    } else {
                        n2 &= 0xFFFF9FFF;
                        this.style &= 0xFBFFFFFF;
                        this.style |= 0x2000000;
                    }
                    OS.SetWindowLong(this.handle, -20, n2);
                } else {
                    this.clearSegments(true);
                    this.applySegments();
                }
                this.fixAlignment();
                break;
            }
        }
        return super.wmCommandChild(l2, l3);
    }

    @Override
    LRESULT wmKeyDown(long l2, long l3, long l4) {
        LRESULT lRESULT = super.wmKeyDown(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.segments != null) {
            switch ((int)l3) {
                case 37: 
                case 38: 
                case 39: 
                case 40: {
                    long l5 = 0L;
                    int[] nArray = new int[]{0};
                    int[] nArray2 = new int[]{0};
                    int[] nArray3 = new int[]{0};
                    int[] nArray4 = new int[]{0};
                    OS.SendMessage(this.handle, 176, nArray, nArray2);
                    while (true) {
                        l5 = this.callWindowProc(this.handle, 256, l3, l4);
                        OS.SendMessage(this.handle, 176, nArray3, nArray4);
                        if (nArray3[0] != nArray[0] ? this.untranslateOffset(nArray3[0]) != this.untranslateOffset(nArray[0]) : nArray4[0] == nArray2[0] || this.untranslateOffset(nArray4[0]) != this.untranslateOffset(nArray2[0])) break;
                        nArray[0] = nArray3[0];
                        nArray2[0] = nArray4[0];
                    }
                    lRESULT = l5 == 0L ? LRESULT.ZERO : new LRESULT(l5);
                    break;
                }
            }
        }
        return lRESULT;
    }

    static {
        EditClass = new TCHAR(0, "EDIT", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, EditClass, wNDCLASS);
        EditProc = wNDCLASS.lpfnWndProc;
    }
}

