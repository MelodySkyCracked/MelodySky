/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Sash
extends Control {
    boolean dragging;
    int startX;
    int startY;
    int lastX;
    int lastY;
    static final int INCREMENT = 1;
    static final int PAGE_INCREMENT = 9;

    public Sash(Composite composite, int n) {
        super(composite, Sash.checkStyle(n));
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
        return OS.DefWindowProc(l2, n, l3, l4);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state |= 0x100;
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
            n4 += 64;
            n5 += 3;
        } else {
            n4 += 3;
            n5 += 64;
        }
        if (n != -1) {
            n4 = n + n3 * 2;
        }
        if (n2 != -1) {
            n5 = n2 + n3 * 2;
        }
        return new Point(n4, n5);
    }

    void drawBand(int n, int n2, int n3, int n4) {
        if ((this.style & 0x10000) != 0) {
            return;
        }
        long l2 = this.parent.handle;
        byte[] byArray = new byte[]{-86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0};
        long l3 = OS.CreateBitmap(8, 8, 1, 1, byArray);
        long l4 = OS.CreatePatternBrush(l3);
        long l5 = OS.GetDCEx(l2, 0L, 2);
        long l6 = OS.SelectObject(l5, l4);
        OS.PatBlt(l5, n, n2, n3, n4, 5898313);
        OS.SelectObject(l5, l6);
        OS.ReleaseDC(l2, l5);
        OS.DeleteObject(l4);
        OS.DeleteObject(l3);
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
    TCHAR windowClass() {
        return this.display.windowClass;
    }

    @Override
    long windowProc() {
        return this.display.windowProc;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        super.WM_ERASEBKGND(l2, l3);
        this.drawBackground(l2);
        return LRESULT.ONE;
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        switch ((int)l2) {
            case 37: 
            case 38: 
            case 39: 
            case 40: {
                int n;
                if (OS.GetKeyState(1) < 0) {
                    return lRESULT;
                }
                int n2 = n = OS.GetKeyState(17) < 0 ? 1 : 9;
                if ((this.style & 0x200) != 0) {
                    if (l2 == 38L || l2 == 40L) break;
                    if (l2 == 37L) {
                        n = -n;
                    }
                    if ((this.parent.style & 0x8000000) != 0) {
                        n = -n;
                    }
                } else {
                    if (l2 == 37L || l2 == 39L) break;
                    if (l2 == 38L) {
                        n = -n;
                    }
                }
                RECT rECT = new RECT();
                OS.GetWindowRect(this.handle, rECT);
                int n3 = rECT.right - rECT.left;
                int n4 = rECT.bottom - rECT.top;
                long l4 = this.parent.handle;
                RECT rECT2 = new RECT();
                OS.GetClientRect(l4, rECT2);
                int n5 = rECT2.right - rECT2.left;
                int n6 = rECT2.bottom - rECT2.top;
                OS.MapWindowPoints(0L, l4, rECT, 2);
                POINT pOINT = new POINT();
                int n7 = rECT.left;
                int n8 = rECT.top;
                if ((this.style & 0x200) != 0) {
                    n7 = pOINT.x = Math.min(Math.max(rECT2.left, n7 + n), n5 - n3);
                    pOINT.y = rECT.top + n4 / 2;
                } else {
                    pOINT.x = rECT.left + n3 / 2;
                    n8 = pOINT.y = Math.min(Math.max(rECT2.top, n8 + n), n6 - n4);
                }
                if (n7 == rECT.left && n8 == rECT.top) {
                    return lRESULT;
                }
                OS.ClientToScreen(l4, pOINT);
                OS.SetCursorPos(pOINT.x, pOINT.y);
                Event event = new Event();
                event.setBoundsInPixels(new Rectangle(n7, n8, n3, n4));
                this.sendSelectionEvent(13, event, true);
                if (this.isDisposed()) {
                    return LRESULT.ZERO;
                }
                if (event.doit && (this.style & 0x10000) != 0) {
                    this.setBoundsInPixels(event.getBoundsInPixels());
                }
                return lRESULT;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        return new LRESULT(256L);
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONDOWN(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        long l4 = this.parent.handle;
        POINT pOINT = new POINT();
        OS.POINTSTOPOINT(pOINT, l3);
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        OS.MapWindowPoints(this.handle, 0L, pOINT, 1);
        this.startX = pOINT.x - rECT.left;
        this.startY = pOINT.y - rECT.top;
        OS.MapWindowPoints(0L, l4, rECT, 2);
        this.lastX = rECT.left;
        this.lastY = rECT.top;
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        Event event = new Event();
        event.setBoundsInPixels(new Rectangle(this.lastX, this.lastY, n, n2));
        if ((this.style & 0x10000) == 0) {
            event.detail = 1;
        }
        this.sendSelectionEvent(13, event, true);
        if (this.isDisposed()) {
            return LRESULT.ZERO;
        }
        Rectangle rectangle = event.getBoundsInPixels();
        if (event.doit) {
            this.dragging = true;
            this.lastX = rectangle.x;
            this.lastY = rectangle.y;
            this.menuShell().bringToTop();
            if (this.isDisposed()) {
                return LRESULT.ZERO;
            }
            int n3 = 384;
            OS.RedrawWindow(l4, null, 0L, 384);
            this.drawBand(rectangle.x, rectangle.y, n, n2);
            if ((this.style & 0x10000) != 0) {
                this.setBoundsInPixels(rectangle.x, rectangle.y, n, n2);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONUP(long l2, long l3) {
        LRESULT lRESULT = super.WM_LBUTTONUP(l2, l3);
        if (lRESULT == LRESULT.ZERO) {
            return lRESULT;
        }
        if (!this.dragging) {
            return lRESULT;
        }
        this.dragging = false;
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        Event event = new Event();
        event.setBoundsInPixels(new Rectangle(this.lastX, this.lastY, n, n2));
        this.drawBand(this.lastX, this.lastY, n, n2);
        this.sendSelectionEvent(13, event, true);
        if (this.isDisposed()) {
            return lRESULT;
        }
        Rectangle rectangle = event.getBoundsInPixels();
        if (event.doit && (this.style & 0x10000) != 0) {
            this.setBoundsInPixels(rectangle.x, rectangle.y, n, n2);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEMOVE(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_MOUSEMOVE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!this.dragging || (l2 & 1L) == 0L) {
            return lRESULT;
        }
        POINT pOINT = new POINT();
        OS.POINTSTOPOINT(pOINT, l3);
        long l4 = this.parent.handle;
        OS.MapWindowPoints(this.handle, l4, pOINT, 1);
        RECT rECT = new RECT();
        RECT rECT2 = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        OS.GetClientRect(l4, rECT2);
        int n4 = this.lastX;
        int n5 = this.lastY;
        if ((this.style & 0x200) != 0) {
            n = rECT2.right - rECT2.left;
            n4 = Math.min(Math.max(0, pOINT.x - this.startX), n - n2);
        } else {
            n = rECT2.bottom - rECT2.top;
            n5 = Math.min(Math.max(0, pOINT.y - this.startY), n - n3);
        }
        if (n4 == this.lastX && n5 == this.lastY) {
            return lRESULT;
        }
        this.drawBand(this.lastX, this.lastY, n2, n3);
        Event event = new Event();
        event.setBoundsInPixels(new Rectangle(n4, n5, n2, n3));
        if ((this.style & 0x10000) == 0) {
            event.detail = 1;
        }
        this.sendSelectionEvent(13, event, true);
        if (this.isDisposed()) {
            return LRESULT.ZERO;
        }
        if (event.doit) {
            Rectangle rectangle = event.getBoundsInPixels();
            this.lastX = rectangle.x;
            this.lastY = rectangle.y;
        }
        int n6 = 384;
        OS.RedrawWindow(l4, null, 0L, 384);
        this.drawBand(this.lastX, this.lastY, n2, n3);
        if ((this.style & 0x10000) != 0) {
            this.setBoundsInPixels(this.lastX, this.lastY, n2, n3);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETCURSOR(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETCURSOR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        short s = (short)OS.LOWORD(l3);
        if (s == 1) {
            long l4 = 0L;
            l4 = (this.style & 0x100) != 0 ? OS.LoadCursor(0L, 32645L) : OS.LoadCursor(0L, 32644L);
            OS.SetCursor(l4);
            return LRESULT.ONE;
        }
        return lRESULT;
    }
}

