/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMUPDOWN;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.UDACCEL;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TypedListener;

public class Spinner
extends Composite {
    long hwndText;
    long hwndUpDown;
    boolean ignoreModify;
    boolean ignoreCharacter;
    int pageIncrement;
    int digits;
    static final long EditProc;
    static final TCHAR EditClass;
    static final long UpDownProc;
    static final TCHAR UpDownClass;
    public static final int LIMIT;

    public Spinner(Composite composite, int n) {
        super(composite, Spinner.checkStyle(n));
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        if (l2 == this.hwndText) {
            return OS.CallWindowProc(EditProc, l2, n, l3, l4);
        }
        if (l2 == this.hwndUpDown) {
            return OS.CallWindowProc(UpDownProc, l2, n, l3, l4);
        }
        return OS.DefWindowProc(this.handle, n, l3, l4);
    }

    static int checkStyle(int n) {
        return n & 0xFFFFFCFF;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFEFD;
        long l2 = OS.GetModuleHandle(null);
        int n = 0;
        int n2 = 1409286272;
        if ((this.style & 8) != 0) {
            n2 |= 0x800;
        }
        if ((this.style & 0x4000000) != 0) {
            n |= 0x400000;
        }
        this.hwndText = OS.CreateWindowEx(n, EditClass, null, n2, 0, 0, 0, 0, this.handle, 0L, l2, null);
        if (this.hwndText == 0L) {
            this.error(2);
        }
        OS.SetWindowLongPtr(this.hwndText, -12, this.hwndText);
        int n3 = 0x50000010;
        if ((this.style & 0x40) != 0) {
            n3 |= 1;
        }
        this.hwndUpDown = OS.CreateWindowEx(0, UpDownClass, null, n3, 0, 0, 0, 0, this.handle, 0L, l2, null);
        if (this.hwndUpDown == 0L) {
            this.error(2);
        }
        int n4 = 19;
        OS.SetWindowPos(this.hwndText, this.hwndUpDown, 0, 0, 0, 0, 19);
        OS.SetWindowLongPtr(this.hwndUpDown, -12, this.hwndUpDown);
        OS.SendMessage(this.hwndUpDown, 1135, 0L, 100L);
        OS.SendMessage(this.hwndUpDown, 1137, 0L, 0L);
        this.pageIncrement = 10;
        this.digits = 0;
        OS.SetWindowText(this.hwndText, new char[]{'0', '\u0000'});
    }

    public void addModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(modifyListener);
        this.addListener(24, typedListener);
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

    void addVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(verifyListener);
        this.addListener(25, typedListener);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = 0;
        int n4 = 0;
        if (n == -1 || n2 == -1) {
            int n5;
            Object object;
            long l2 = 0L;
            long l3 = OS.GetDC(this.hwndText);
            long l4 = OS.SendMessage(this.hwndText, 49, 0L, 0L);
            if (l4 != 0L) {
                l2 = OS.SelectObject(l3, l4);
            }
            TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
            OS.GetTextMetrics(l3, tEXTMETRIC);
            n4 = tEXTMETRIC.tmHeight;
            RECT rECT = new RECT();
            int[] nArray = new int[]{0};
            OS.SendMessage(this.hwndUpDown, 1136, null, nArray);
            String string = String.valueOf(nArray[0]);
            if (this.digits > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(this.getDecimalSeparator());
                for (n5 = this.digits - string.length(); n5 >= 0; --n5) {
                    ((StringBuilder)object).append("0");
                }
                string = ((StringBuilder)object).toString();
            }
            object = string.toCharArray();
            n5 = 11264;
            OS.DrawText(l3, (char[])object, ((Object)object).length, rECT, 11264);
            n3 = rECT.right - rECT.left;
            if (l4 != 0L) {
                OS.SelectObject(l3, l2);
            }
            OS.ReleaseDC(this.hwndText, l3);
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
        } else {
            int n6 = (this.style & 0x800) != 0 ? -1 : 3;
            int n7 = OS.GetSystemMetrics(20);
            n4 = Math.max(n4, n7 + n6);
        }
        Rectangle rectangle = this.computeTrimInPixels(0, 0, n3, n4);
        return new Point(rectangle.width, rectangle.height);
    }

    @Override
    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        this.checkWidget();
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, n + n3, n2 + n4);
        int n5 = OS.GetWindowLong(this.handle, -16);
        int n6 = OS.GetWindowLong(this.handle, -20);
        if ((n5 & 0x800000) != 0) {
            n5 &= 0xFF7FFFFF;
            n6 |= 0x200;
        }
        OS.AdjustWindowRectEx(rECT, n5, false, n6);
        n3 = rECT.right - rECT.left;
        n4 = rECT.bottom - rECT.top;
        long l2 = OS.SendMessage(this.hwndText, 212, 0L, 0L);
        n -= OS.LOWORD(l2);
        n3 += OS.LOWORD(l2) + OS.HIWORD(l2);
        if ((this.style & 0x800) != 0) {
            --n;
            --n2;
            n3 += 2;
            n4 += 2;
        }
        return new Rectangle(n, n2, n3 += OS.GetSystemMetrics(2), n4);
    }

    public void copy() {
        this.checkWidget();
        OS.SendMessage(this.hwndText, 769, 0L, 0L);
    }

    public void cut() {
        this.checkWidget();
        if ((this.style & 8) != 0) {
            return;
        }
        OS.SendMessage(this.hwndText, 768, 0L, 0L);
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(5);
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        OS.EnableWindow(this.hwndText, bl);
        OS.EnableWindow(this.hwndUpDown, bl);
    }

    @Override
    void deregister() {
        super.deregister();
        this.display.removeControl(this.hwndText);
        this.display.removeControl(this.hwndUpDown);
    }

    @Override
    boolean hasFocus() {
        long l2 = OS.GetFocus();
        return l2 == this.handle || l2 == this.hwndText || l2 == this.hwndUpDown;
    }

    public int getDigits() {
        this.checkWidget();
        return this.digits;
    }

    String getDecimalSeparator() {
        char[] cArray = new char[4];
        int n = OS.GetLocaleInfo(1024, 14, cArray, 4);
        return n != 0 ? new String(cArray, 0, n - 1) : ".";
    }

    public int getIncrement() {
        this.checkWidget();
        UDACCEL uDACCEL = new UDACCEL();
        OS.SendMessage(this.hwndUpDown, 1132, 1L, uDACCEL);
        return uDACCEL.nInc;
    }

    public int getMaximum() {
        this.checkWidget();
        int[] nArray = new int[]{0};
        OS.SendMessage(this.hwndUpDown, 1136, null, nArray);
        return nArray[0];
    }

    public int getMinimum() {
        this.checkWidget();
        int[] nArray = new int[]{0};
        OS.SendMessage(this.hwndUpDown, 1136, nArray, null);
        return nArray[0];
    }

    public int getPageIncrement() {
        this.checkWidget();
        return this.pageIncrement;
    }

    public int getSelection() {
        this.checkWidget();
        return (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L);
    }

    int getSelectionText(boolean[] blArray) {
        int n = OS.GetWindowTextLength(this.hwndText);
        char[] cArray = new char[n + 1];
        OS.GetWindowText(this.hwndText, cArray, n + 1);
        String string = new String(cArray, 0, n);
        try {
            int n2;
            Object object;
            if (this.digits > 0) {
                object = this.getDecimalSeparator();
                int n3 = string.indexOf((String)object);
                if (n3 != -1) {
                    int n4;
                    int n5;
                    int n6 = string.startsWith("+") || string.startsWith("-") ? 1 : 0;
                    String string2 = n6 != n3 ? string.substring(n6, n3) : "0";
                    String string3 = string.substring(n3 + 1);
                    if (string3.length() > this.digits) {
                        string3 = string3.substring(0, this.digits);
                    } else {
                        n5 = this.digits - string3.length();
                        for (n4 = 0; n4 < n5; ++n4) {
                        }
                    }
                    n5 = Integer.parseInt(string2);
                    n4 = Integer.parseInt(string3);
                    for (int i = 0; i < this.digits; ++i) {
                        n5 *= 10;
                    }
                    n2 = n5 + n4;
                    if (string.startsWith("-")) {
                        n2 = -n2;
                    }
                } else {
                    n2 = Integer.parseInt(string);
                    for (int i = 0; i < this.digits; ++i) {
                        n2 *= 10;
                    }
                }
            } else {
                n2 = Integer.parseInt(string);
            }
            object = new int[]{0};
            int[] nArray = new int[]{0};
            OS.SendMessage(this.hwndUpDown, 1136, nArray, (int[])object);
            if (nArray[0] <= n2 && n2 <= object[0]) {
                return n2;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        blArray[0] = true;
        return -1;
    }

    public String getText() {
        this.checkWidget();
        int n = OS.GetWindowTextLength(this.hwndText);
        if (n == 0) {
            return "";
        }
        char[] cArray = new char[n + 1];
        OS.GetWindowText(this.hwndText, cArray, n + 1);
        return new String(cArray, 0, n);
    }

    public int getTextLimit() {
        this.checkWidget();
        return (int)OS.SendMessage(this.hwndText, 213, 0L, 0L) & Integer.MAX_VALUE;
    }

    boolean isUseWsBorder() {
        return true;
    }

    public void paste() {
        this.checkWidget();
        if ((this.style & 8) != 0) {
            return;
        }
        OS.SendMessage(this.hwndText, 770, 0L, 0L);
    }

    @Override
    void register() {
        super.register();
        this.display.addControl(this.hwndText, this);
        this.display.addControl(this.hwndUpDown, this);
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        long l2 = 0L;
        this.hwndUpDown = 0L;
        this.hwndText = 0L;
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

    void removeVerifyListener(VerifyListener verifyListener) {
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
        if (OS.GetKeyState(1) < 0) {
            return true;
        }
        String string2 = "";
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.hwndText, 176, nArray, nArray2);
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
                int n4 = OS.GetWindowTextLength(this.hwndText);
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
        OS.SendMessage(this.hwndText, 177, (long)nArray[0], nArray2[0]);
        OS.SendMessage(this.hwndText, 194, 0L, tCHAR);
        return false;
    }

    @Override
    void setBackgroundImage(long l2) {
        super.setBackgroundImage(l2);
        OS.InvalidateRect(this.hwndText, null, true);
    }

    @Override
    void setBackgroundPixel(int n) {
        super.setBackgroundPixel(n);
        OS.InvalidateRect(this.hwndText, null, true);
    }

    public void setDigits(int n) {
        this.checkWidget();
        if (n < 0) {
            this.error(5);
        }
        if (n == this.digits) {
            return;
        }
        this.digits = n;
        int n2 = (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L);
        this.setSelection(n2, false, true, false);
    }

    @Override
    void setForegroundPixel(int n) {
        super.setForegroundPixel(n);
        OS.InvalidateRect(this.hwndText, null, true);
    }

    public void setIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        long l2 = OS.GetProcessHeap();
        int n2 = (int)OS.SendMessage(this.hwndUpDown, 1132, 0L, (UDACCEL)null);
        long l3 = OS.HeapAlloc(l2, 8, UDACCEL.sizeof * n2);
        OS.SendMessage(this.hwndUpDown, 1132, (long)n2, l3);
        int n3 = -1;
        UDACCEL uDACCEL = new UDACCEL();
        for (int i = 0; i < n2; ++i) {
            long l4 = l3 + (long)(i * UDACCEL.sizeof);
            OS.MoveMemory(uDACCEL, l4, UDACCEL.sizeof);
            if (n3 == -1) {
                n3 = uDACCEL.nInc;
            }
            uDACCEL.nInc = uDACCEL.nInc / n3 * n;
            OS.MoveMemory(l4, uDACCEL, UDACCEL.sizeof);
        }
        OS.SendMessage(this.hwndUpDown, 1131, (long)n2, l3);
        OS.HeapFree(l2, 0, l3);
    }

    public void setMaximum(int n) {
        this.checkWidget();
        int[] nArray = new int[]{0};
        OS.SendMessage(this.hwndUpDown, 1136, nArray, null);
        if (n < nArray[0]) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L);
        OS.SendMessage(this.hwndUpDown, 1135, (long)nArray[0], n);
        if (n2 > n) {
            this.setSelection(n, true, true, false);
        }
    }

    public void setMinimum(int n) {
        this.checkWidget();
        int[] nArray = new int[]{0};
        OS.SendMessage(this.hwndUpDown, 1136, null, nArray);
        if (n > nArray[0]) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L);
        OS.SendMessage(this.hwndUpDown, 1135, (long)n, nArray[0]);
        if (n2 < n) {
            this.setSelection(n, true, true, false);
        }
    }

    public void setPageIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        this.pageIncrement = n;
    }

    public void setSelection(int n) {
        this.checkWidget();
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[]{0};
        OS.SendMessage(this.hwndUpDown, 1136, nArray2, nArray);
        n = Math.min(Math.max(nArray2[0], n), nArray[0]);
        this.setSelection(n, true, true, false);
    }

    void setSelection(int n, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            OS.SendMessage(this.hwndUpDown, 1137, 0L, n);
        }
        if (bl2) {
            int n2;
            Object object;
            String string;
            if (this.digits == 0) {
                string = String.valueOf(n);
            } else {
                string = String.valueOf(Math.abs(n));
                object = this.getDecimalSeparator();
                int n3 = string.length() - this.digits;
                StringBuilder stringBuilder = new StringBuilder();
                if (n < 0) {
                    stringBuilder.append("-");
                }
                if (n3 > 0) {
                    stringBuilder.append(string.substring(0, n3));
                    stringBuilder.append((String)object);
                    stringBuilder.append(string.substring(n3));
                } else {
                    stringBuilder.append("0");
                    stringBuilder.append((String)object);
                    while (n3++ < 0) {
                        stringBuilder.append("0");
                    }
                    stringBuilder.append(string);
                }
                string = stringBuilder.toString();
            }
            if ((this.hooks(25) || this.filters(25)) && (string = this.verifyText(string, 0, n2 = OS.GetWindowTextLength(this.hwndText), null)) == null) {
                return;
            }
            object = new TCHAR(this.getCodePage(), string, true);
            OS.SetWindowText(this.hwndText, (TCHAR)object);
            OS.SendMessage(this.hwndText, 177, 0L, -1L);
            OS.NotifyWinEvent(32773, this.hwndText, -4, 0);
        }
        if (bl3) {
            this.sendSelectionEvent(13);
        }
    }

    public void setTextLimit(int n) {
        this.checkWidget();
        if (n == 0) {
            this.error(7);
        }
        OS.SendMessage(this.hwndText, 197, (long)n, 0L);
    }

    @Override
    void setToolTipText(Shell shell, String string) {
        shell.setToolTipText(this.hwndText, string);
        shell.setToolTipText(this.hwndUpDown, string);
    }

    public void setValues(int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkWidget();
        if (n3 < n2) {
            return;
        }
        if (n4 < 0) {
            return;
        }
        if (n5 < 1) {
            return;
        }
        if (n6 < 1) {
            return;
        }
        n = Math.min(Math.max(n2, n), n3);
        this.setIncrement(n5);
        this.pageIncrement = n6;
        this.digits = n4;
        OS.SendMessage(this.hwndUpDown, 1135, (long)n2, n3);
        this.setSelection(n, true, true, false);
    }

    @Override
    void subclass() {
        super.subclass();
        long l2 = this.display.windowProc;
        OS.SetWindowLongPtr(this.hwndText, -4, l2);
        OS.SetWindowLongPtr(this.hwndUpDown, -4, l2);
    }

    @Override
    void unsubclass() {
        super.unsubclass();
        OS.SetWindowLongPtr(this.hwndText, -4, EditProc);
        OS.SetWindowLongPtr(this.hwndUpDown, -4, UpDownProc);
    }

    @Override
    void updateOrientation() {
        super.updateOrientation();
        int n = OS.GetWindowLong(this.hwndText, -20);
        int n2 = OS.GetWindowLong(this.hwndText, -16);
        if ((this.style & 0x4000000) != 0) {
            n |= 0x1000;
            n2 |= 2;
        } else {
            n &= 0xFFFFEFFF;
            n2 &= 0xFFFFFFFD;
        }
        OS.SetWindowLong(this.hwndText, -16, n2);
        OS.SetWindowLong(this.hwndText, -20, n);
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n3 = rECT.right - rECT.left;
        int n4 = rECT.bottom - rECT.top;
        OS.SetWindowPos(this.handle, 0L, 0, 0, n3 - 1, n4 - 1, 6);
        OS.SetWindowPos(this.handle, 0L, 0, 0, n3, n4, 6);
    }

    String verifyText(String string, int n, int n2, Event event) {
        Object object;
        Event event2 = new Event();
        event2.text = string;
        event2.start = n;
        event2.end = n2;
        if (event != null) {
            event2.character = event.character;
            event2.keyCode = event.keyCode;
            event2.stateMask = event.stateMask;
        }
        int n3 = 0;
        if (this.digits > 0) {
            object = this.getDecimalSeparator();
            n3 = string.indexOf((String)object);
            if (n3 != -1) {
                string = string.substring(0, n3) + string.substring(n3 + 1);
            }
            n3 = 0;
        }
        if (string.length() > 0) {
            object = new int[]{0};
            OS.SendMessage(this.hwndUpDown, 1136, (int[])object, null);
            if (object[0] < 0 && string.charAt(0) == '-') {
                ++n3;
            }
        }
        while (n3 < string.length() && Character.isDigit(string.charAt(n3))) {
            ++n3;
        }
        event2.doit = n3 == string.length();
        this.sendEvent(25, event2);
        if (!event2.doit || this.isDisposed()) {
            return null;
        }
        return event2.text;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        if (l2 != this.hwndText && l2 != this.hwndUpDown) {
            return super.windowProc(l2, n, l3, l4);
        }
        LRESULT lRESULT = null;
        switch (n) {
            case 258: {
                lRESULT = this.wmChar(l2, l3, l4);
                break;
            }
            case 646: {
                lRESULT = this.wmIMEChar(l2, l3, l4);
                break;
            }
            case 256: {
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
            case 7: {
                lRESULT = this.wmSetFocus(l2, l3, l4);
                break;
            }
            case 8: {
                lRESULT = this.wmKillFocus(l2, l3, l4);
                break;
            }
            case 15: {
                lRESULT = this.wmPaint(l2, l3, l4);
                break;
            }
            case 791: {
                lRESULT = this.wmPrint(l2, l3, l4);
                break;
            }
            case 123: {
                lRESULT = this.wmContextMenu(l2, l3, l4);
                break;
            }
            case 199: 
            case 768: 
            case 770: 
            case 771: 
            case 772: {
                if (l2 != this.hwndText) break;
                lRESULT = this.wmClipboard(l2, n, l3, l4);
            }
        }
        if (lRESULT != null) {
            return lRESULT.value;
        }
        return this.callWindowProc(l2, n, l3, l4);
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        super.WM_ERASEBKGND(l2, l3);
        this.drawBackground(l2);
        return LRESULT.ONE;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        return null;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        OS.SetFocus(this.hwndText);
        OS.SendMessage(this.hwndText, 177, 0L, -1L);
        return null;
    }

    @Override
    LRESULT WM_SETFONT(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFONT(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        OS.SendMessage(this.hwndText, 48, l2, l3);
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (this.isDisposed()) {
            return lRESULT;
        }
        int n = OS.LOWORD(l3);
        int n2 = OS.HIWORD(l3);
        int n3 = OS.GetSystemMetrics(2) - 1;
        int n4 = n - n3;
        int n5 = 0;
        if ((this.style & 0x800) != 0 && !this.display.useWsBorderText) {
            n5 = OS.GetSystemMetrics(46) - OS.GetSystemMetrics(6);
            ++n5;
        }
        int n6 = 52;
        OS.SetWindowPos(this.hwndText, 0L, 0, n5, n4, n2 - n5, 52);
        OS.SetWindowPos(this.hwndUpDown, 0L, n4, 0, n3, n2, 52);
        return lRESULT;
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
    LRESULT wmChar(long l2, long l3, long l4) {
        if (this.ignoreCharacter) {
            return null;
        }
        LRESULT lRESULT = super.wmChar(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l3) {
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

    LRESULT wmClipboard(long l2, int n, long l3, long l4) {
        Object object;
        if ((this.style & 8) != 0) {
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
                if (nArray[0] == nArray2[0]) break;
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
                    int n3 = ((TCHAR)object).length() * 2;
                    long l6 = OS.HeapAlloc(l5, 8, n3);
                    OS.MoveMemory(l6, (TCHAR)object, n3);
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
            case 768: {
                int n2;
                if (this.ignoreModify) break;
                boolean[] blArray = new boolean[]{false};
                int n3 = this.getSelectionText(blArray);
                if (!blArray[0] && (n2 = (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L)) != n3) {
                    this.setSelection(n3, true, false, true);
                }
                this.sendEvent(24);
                if (!this.isDisposed()) break;
                return LRESULT.ZERO;
            }
        }
        return super.wmCommandChild(l2, l3);
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
        UDACCEL uDACCEL = new UDACCEL();
        OS.SendMessage(this.hwndUpDown, 1132, 1L, uDACCEL);
        int n = 0;
        switch ((int)l3) {
            case 38: {
                n = uDACCEL.nInc;
                break;
            }
            case 40: {
                n = -uDACCEL.nInc;
                break;
            }
            case 33: {
                n = this.pageIncrement;
                break;
            }
            case 34: {
                n = -this.pageIncrement;
            }
        }
        if (n != 0) {
            boolean[] blArray = new boolean[]{false};
            int n2 = this.getSelectionText(blArray);
            if (blArray[0]) {
                n2 = (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L);
            }
            int n3 = n2 + n;
            int[] nArray = new int[]{0};
            int[] nArray2 = new int[]{0};
            OS.SendMessage(this.hwndUpDown, 1136, nArray2, nArray);
            if ((this.style & 0x40) != 0) {
                if (n3 < nArray2[0]) {
                    n3 = nArray[0];
                }
                if (n3 > nArray[0]) {
                    n3 = nArray2[0];
                }
            }
            if (n2 != (n3 = Math.min(Math.max(nArray2[0], n3), nArray[0]))) {
                this.setSelection(n3, true, true, true);
            }
        }
        switch ((int)l3) {
            case 38: 
            case 40: {
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmKillFocus(long l2, long l3, long l4) {
        boolean[] blArray = new boolean[]{false};
        int n = this.getSelectionText(blArray);
        if (blArray[0]) {
            n = (int)OS.SendMessage(this.hwndUpDown, 1138, 0L, 0L);
            this.setSelection(n, false, true, false);
        }
        return super.wmKillFocus(l2, l3, l4);
    }

    @Override
    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        switch (nMHDR.code) {
            case -722: {
                NMUPDOWN nMUPDOWN = new NMUPDOWN();
                OS.MoveMemory(nMUPDOWN, l3, NMUPDOWN.sizeof);
                int n = nMUPDOWN.iPos + nMUPDOWN.iDelta;
                int[] nArray = new int[]{0};
                int[] nArray2 = new int[]{0};
                OS.SendMessage(this.hwndUpDown, 1136, nArray2, nArray);
                if ((this.style & 0x40) != 0) {
                    if (n < nArray2[0]) {
                        n = nArray[0];
                    }
                    if (n > nArray[0]) {
                        n = nArray2[0];
                    }
                }
                if ((n = Math.min(Math.max(nArray2[0], n), nArray[0])) != nMUPDOWN.iPos) {
                    this.setSelection(n, true, true, true);
                }
                return LRESULT.ONE;
            }
        }
        return super.wmNotifyChild(nMHDR, l2, l3);
    }

    @Override
    LRESULT wmScrollChild(long l2, long l3) {
        int n = OS.LOWORD(l2);
        switch (n) {
            case 4: {
                this.sendSelectionEvent(13);
            }
        }
        return super.wmScrollChild(l2, l3);
    }

    static {
        EditClass = new TCHAR(0, "EDIT", true);
        UpDownClass = new TCHAR(0, "msctls_updown32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, EditClass, wNDCLASS);
        EditProc = wNDCLASS.lpfnWndProc;
        OS.GetClassInfo(0L, UpDownClass, wNDCLASS);
        UpDownProc = wNDCLASS.lpfnWndProc;
        LIMIT = Integer.MAX_VALUE;
    }
}

