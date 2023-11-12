/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.NOTIFYICONDATA;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class ToolTip
extends Widget {
    Shell parent;
    TrayItem item;
    String text = "";
    String message = "";
    int id;
    int x;
    int y;
    boolean autoHide = true;
    boolean hasLocation;
    boolean visible;
    static final int TIMER_ID = 100;

    public ToolTip(Shell shell, int n) {
        super(shell, ToolTip.checkStyle(n));
        this.parent = shell;
        this.checkOrientation(this.parent);
        shell.createToolTip(this);
    }

    static int checkStyle(int n) {
        int n2 = 11;
        if ((n & 0xB) == 0) {
            return n;
        }
        return Widget.checkBits(n, 2, 8, 1, 0, 0, 0);
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
    void destroyWidget() {
        if (this.parent != null) {
            this.parent.destroyToolTip(this);
        }
        this.releaseHandle();
    }

    public boolean getAutoHide() {
        this.checkWidget();
        return this.autoHide;
    }

    public String getMessage() {
        this.checkWidget();
        return this.message;
    }

    public Shell getParent() {
        this.checkWidget();
        return this.parent;
    }

    public String getText() {
        this.checkWidget();
        return this.text;
    }

    int getWidth() {
        long l2 = this.parent.handle;
        long l3 = OS.MonitorFromWindow(l2, 2);
        MONITORINFO mONITORINFO = new MONITORINFO();
        mONITORINFO.cbSize = MONITORINFO.sizeof;
        OS.GetMonitorInfo(l3, mONITORINFO);
        int n = mONITORINFO.rcWork_right - mONITORINFO.rcWork_left;
        return n / 4;
    }

    long hwndToolTip() {
        return (this.style & 0x1000) != 0 ? this.parent.balloonTipHandle() : this.parent.toolTipHandle();
    }

    public boolean isVisible() {
        this.checkWidget();
        if (this.item != null) {
            return this != null && this.item.getVisible();
        }
        return this.getVisible();
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
        this.item = null;
        this.id = -1;
    }

    @Override
    void releaseWidget() {
        long l2;
        super.releaseWidget();
        if (this.item == null && this.autoHide && OS.SendMessage(l2 = this.hwndToolTip(), 1083, 0L, 0L) != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            if (OS.SendMessage(l2, 1083, 0L, tOOLINFO) != 0L && (tOOLINFO.uFlags & 1) == 0 && tOOLINFO.uId == (long)this.id) {
                OS.SendMessage(l2, 1041, 0L, tOOLINFO);
                OS.SendMessage(l2, 1052, 0L, 0L);
                OS.KillTimer(l2, 100L);
            }
        }
        if (this.item != null && this.item.toolTip == this) {
            this.item.toolTip = null;
        }
        this.item = null;
        Object var1_2 = null;
        this.message = var1_2;
        this.text = var1_2;
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

    public void setAutoHide(boolean bl) {
        this.checkWidget();
        this.autoHide = bl;
    }

    public void setLocation(int n, int n2) {
        this.checkWidget();
        this.setLocationInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setLocationInPixels(int n, int n2) {
        this.x = n;
        this.y = n2;
        this.hasLocation = true;
    }

    public void setLocation(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setLocationInPixels(point.x, point.y);
    }

    public void setMessage(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        this.message = string;
        this.updateMessage();
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        this.text = string;
    }

    void updateMessage() {
        long l2 = this.hwndToolTip();
        if (OS.SendMessage(l2, 1083, 0L, 0L) != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            if (OS.SendMessage(l2, 1083, 0L, tOOLINFO) != 0L) {
                if (this.message != null && this.message.length() > 0) {
                    long l3 = OS.GetProcessHeap();
                    TCHAR tCHAR = new TCHAR(0, this.message, true);
                    int n = tCHAR.length() * 2;
                    tOOLINFO.lpszText = OS.HeapAlloc(l3, 8, n);
                    OS.MoveMemory(tOOLINFO.lpszText, tCHAR, n);
                    OS.SendMessage(l2, 1081, 0L, tOOLINFO);
                    OS.HeapFree(l3, 0, tOOLINFO.lpszText);
                } else {
                    tOOLINFO.lpszText = -1L;
                    OS.SendMessage(l2, 1081, 0L, tOOLINFO);
                }
            }
        }
    }

    public void setVisible(boolean bl) {
        this.checkWidget();
        boolean bl2 = bl;
        if (this != null) {
            return;
        }
        if (this.item == null) {
            int n;
            long l2 = this.parent.handle;
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.uId = this.id;
            tOOLINFO.hwnd = l2;
            long l3 = this.hwndToolTip();
            Shell shell = this.parent.getShell();
            if (this.text.length() != 0) {
                n = 0;
                if ((this.style & 2) != 0) {
                    n = 1;
                }
                if ((this.style & 8) != 0) {
                    n = 2;
                }
                if ((this.style & 1) != 0) {
                    n = 3;
                }
                shell.setToolTipTitle(l3, this.text, n);
            } else {
                shell.setToolTipTitle(l3, null, 0);
            }
            OS.SendMessage(l3, 1048, 0L, this.getWidth());
            if (bl) {
                POINT pOINT;
                n = this.x;
                int n2 = this.y;
                if (!this.hasLocation && OS.GetCursorPos(pOINT = new POINT())) {
                    n = pOINT.x;
                    n2 = pOINT.y;
                }
                long l4 = OS.MAKELPARAM(n, n2);
                OS.SendMessage(l3, 1042, 0L, l4);
                POINT pOINT2 = new POINT();
                OS.GetCursorPos(pOINT2);
                RECT rECT = new RECT();
                OS.GetClientRect(l2, rECT);
                OS.MapWindowPoints(l2, 0L, rECT, 2);
                if (!OS.PtInRect(rECT, pOINT2)) {
                    long l5 = OS.GetCursor();
                    OS.SetCursor(0L);
                    OS.SetCursorPos(rECT.left, rECT.top);
                    OS.SendMessage(l3, 1041, 1L, tOOLINFO);
                    OS.SetCursorPos(pOINT2.x, pOINT2.y);
                    OS.SetCursor(l5);
                } else {
                    OS.SendMessage(l3, 1041, 1L, tOOLINFO);
                }
                int n3 = (int)OS.SendMessage(l3, 1045, 2L, 0L);
                OS.SetTimer(l3, 100L, n3, 0L);
                this.updateMessage();
            } else {
                OS.SendMessage(l3, 1041, 0L, tOOLINFO);
                OS.SendMessage(l3, 1052, 0L, 0L);
                OS.KillTimer(l3, 100L);
            }
            return;
        }
        if (this.item != null && bl) {
            NOTIFYICONDATA nOTIFYICONDATA = new NOTIFYICONDATA();
            char[] cArray = nOTIFYICONDATA.szInfoTitle;
            int n = Math.min(cArray.length - 1, this.text.length());
            this.text.getChars(0, n, cArray, 0);
            char[] cArray2 = nOTIFYICONDATA.szInfo;
            int n4 = Math.min(cArray2.length - 1, this.message.length());
            this.message.getChars(0, n4, cArray2, 0);
            Display display = this.item.getDisplay();
            nOTIFYICONDATA.cbSize = NOTIFYICONDATA.sizeof;
            nOTIFYICONDATA.uID = this.item.id;
            nOTIFYICONDATA.hWnd = display.hwndMessage;
            nOTIFYICONDATA.uFlags = 16;
            if ((this.style & 2) != 0) {
                nOTIFYICONDATA.dwInfoFlags = 1;
            }
            if ((this.style & 8) != 0) {
                nOTIFYICONDATA.dwInfoFlags = 2;
            }
            if ((this.style & 1) != 0) {
                nOTIFYICONDATA.dwInfoFlags = 3;
            }
            this.sendEvent(22);
            this.visible = OS.Shell_NotifyIcon(1, nOTIFYICONDATA);
        }
    }
}

