/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.io.Serializable;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class Tracker
extends Widget {
    Control parent;
    boolean tracking;
    boolean cancelled;
    boolean stippled;
    Rectangle[] rectangles = new Rectangle[0];
    Rectangle[] proportions = this.rectangles;
    Rectangle bounds;
    long resizeCursor;
    Cursor clientCursor;
    int cursorOrientation = 0;
    boolean inEvent = false;
    boolean drawn;
    long hwndTransparent;
    long hwndOpaque;
    long oldTransparentProc;
    long oldOpaqueProc;
    int oldX;
    int oldY;
    static final int STEPSIZE_SMALL = 1;
    static final int STEPSIZE_LARGE = 9;

    public Tracker(Composite composite, int n) {
        super(composite, Tracker.checkStyle(n));
        this.parent = composite;
    }

    public Tracker(Display display, int n) {
        if (display == null) {
            display = Display.getCurrent();
        }
        if (display == null) {
            display = Display.getDefault();
        }
        if (!display.isValidThread()) {
            this.error(22);
        }
        this.style = Tracker.checkStyle(n);
        this.display = display;
        this.reskinWidget();
    }

    public void addControlListener(ControlListener controlListener) {
        this.checkWidget();
        if (controlListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(controlListener);
        this.addListener(11, typedListener);
        this.addListener(10, typedListener);
    }

    public void addKeyListener(KeyListener keyListener) {
        this.checkWidget();
        if (keyListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(keyListener);
        this.addListener(2, typedListener);
        this.addListener(1, typedListener);
    }

    Point adjustMoveCursor() {
        if (this.bounds == null) {
            return null;
        }
        int n = this.bounds.x + this.bounds.width / 2;
        int n2 = this.bounds.y;
        POINT pOINT = new POINT();
        pOINT.x = n;
        pOINT.y = n2;
        if (this.parent != null) {
            OS.ClientToScreen(this.parent.handle, pOINT);
        }
        OS.SetCursorPos(pOINT.x, pOINT.y);
        return new Point(pOINT.x, pOINT.y);
    }

    Point adjustResizeCursor() {
        if (this.bounds == null) {
            return null;
        }
        int n = (this.cursorOrientation & 0x4000) != 0 ? this.bounds.x : ((this.cursorOrientation & 0x20000) != 0 ? this.bounds.x + this.bounds.width : this.bounds.x + this.bounds.width / 2);
        int n2 = (this.cursorOrientation & 0x80) != 0 ? this.bounds.y : ((this.cursorOrientation & 0x400) != 0 ? this.bounds.y + this.bounds.height : this.bounds.y + this.bounds.height / 2);
        POINT pOINT = new POINT();
        pOINT.x = n;
        pOINT.y = n2;
        if (this.parent != null) {
            OS.ClientToScreen(this.parent.handle, pOINT);
        }
        OS.SetCursorPos(pOINT.x, pOINT.y);
        if (this.clientCursor == null) {
            long l2 = 0L;
            switch (this.cursorOrientation) {
                case 128: {
                    l2 = OS.LoadCursor(0L, 32645L);
                    break;
                }
                case 1024: {
                    l2 = OS.LoadCursor(0L, 32645L);
                    break;
                }
                case 16384: {
                    l2 = OS.LoadCursor(0L, 32644L);
                    break;
                }
                case 131072: {
                    l2 = OS.LoadCursor(0L, 32644L);
                    break;
                }
                case 16512: {
                    l2 = OS.LoadCursor(0L, 32642L);
                    break;
                }
                case 132096: {
                    l2 = OS.LoadCursor(0L, 32642L);
                    break;
                }
                case 17408: {
                    l2 = OS.LoadCursor(0L, 32643L);
                    break;
                }
                case 131200: {
                    l2 = OS.LoadCursor(0L, 32643L);
                    break;
                }
                default: {
                    l2 = OS.LoadCursor(0L, 32646L);
                }
            }
            OS.SetCursor(l2);
            if (this.resizeCursor != 0L) {
                OS.DestroyCursor(this.resizeCursor);
            }
            this.resizeCursor = l2;
        }
        return new Point(pOINT.x, pOINT.y);
    }

    static int checkStyle(int n) {
        if ((n & 0x24480) == 0) {
            n |= 0x24480;
        }
        return n;
    }

    public void close() {
        this.checkWidget();
        this.tracking = false;
    }

    Rectangle computeBounds() {
        if (this.rectangles.length == 0) {
            return null;
        }
        int n = this.rectangles[0].x;
        int n2 = this.rectangles[0].y;
        int n3 = this.rectangles[0].x + this.rectangles[0].width;
        int n4 = this.rectangles[0].y + this.rectangles[0].height;
        for (int i = 1; i < this.rectangles.length; ++i) {
            int n5;
            int n6;
            if (this.rectangles[i].x < n) {
                n = this.rectangles[i].x;
            }
            if (this.rectangles[i].y < n2) {
                n2 = this.rectangles[i].y;
            }
            if ((n6 = this.rectangles[i].x + this.rectangles[i].width) > n3) {
                n3 = n6;
            }
            if ((n5 = this.rectangles[i].y + this.rectangles[i].height) <= n4) continue;
            n4 = n5;
        }
        return new Rectangle(n, n2, n3 - n, n4 - n2);
    }

    Rectangle[] computeProportions(Rectangle[] rectangleArray) {
        Rectangle[] rectangleArray2 = new Rectangle[rectangleArray.length];
        this.bounds = this.computeBounds();
        if (this.bounds != null) {
            for (int i = 0; i < rectangleArray.length; ++i) {
                int n = 0;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                if (this.bounds.width != 0) {
                    n = (rectangleArray[i].x - this.bounds.x) * 100 / this.bounds.width;
                    n3 = rectangleArray[i].width * 100 / this.bounds.width;
                } else {
                    n3 = 100;
                }
                if (this.bounds.height != 0) {
                    n2 = (rectangleArray[i].y - this.bounds.y) * 100 / this.bounds.height;
                    n4 = rectangleArray[i].height * 100 / this.bounds.height;
                } else {
                    n4 = 100;
                }
                rectangleArray2[i] = new Rectangle(n, n2, n3, n4);
            }
        }
        return rectangleArray2;
    }

    void drawRectangles(Rectangle[] rectangleArray, boolean bl) {
        if (this.hwndOpaque != 0L) {
            RECT rECT = new RECT();
            int n = bl ? 3 : 1;
            for (Rectangle rectangle : rectangleArray) {
                rECT.left = rectangle.x - n;
                rECT.top = rectangle.y - n;
                rECT.right = rectangle.x + rectangle.width + n * 2;
                rECT.bottom = rectangle.y + rectangle.height + n * 2;
                OS.MapWindowPoints(0L, this.hwndOpaque, rECT, 2);
                OS.RedrawWindow(this.hwndOpaque, rECT, 0L, 1);
            }
            return;
        }
        int n = 1;
        long l2 = this.parent == null ? OS.GetDesktopWindow() : this.parent.handle;
        long l3 = OS.GetDCEx(l2, 0L, 2);
        long l4 = 0L;
        long l5 = 0L;
        long l6 = 0L;
        if (bl) {
            n = 3;
            byte[] objectArray = new byte[]{-86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0};
            l4 = OS.CreateBitmap(8, 8, 1, 1, objectArray);
            l5 = OS.CreatePatternBrush(l4);
            l6 = OS.SelectObject(l3, l5);
        }
        for (Rectangle rectangle : rectangleArray) {
            OS.PatBlt(l3, rectangle.x, rectangle.y, rectangle.width, n, 5898313);
            OS.PatBlt(l3, rectangle.x, rectangle.y + n, n, rectangle.height - n * 2, 5898313);
            OS.PatBlt(l3, rectangle.x + rectangle.width - n, rectangle.y + n, n, rectangle.height - n * 2, 5898313);
            OS.PatBlt(l3, rectangle.x, rectangle.y + rectangle.height - n, rectangle.width, n, 5898313);
        }
        if (bl) {
            OS.SelectObject(l3, l6);
            OS.DeleteObject(l5);
            OS.DeleteObject(l4);
        }
        OS.ReleaseDC(l2, l3);
    }

    public Rectangle[] getRectangles() {
        this.checkWidget();
        Rectangle[] rectangleArray = this.getRectanglesInPixels();
        for (int i = 0; i < rectangleArray.length; ++i) {
            rectangleArray[i] = DPIUtil.autoScaleDown(rectangleArray[i]);
        }
        return rectangleArray;
    }

    Rectangle[] getRectanglesInPixels() {
        Rectangle[] rectangleArray = new Rectangle[this.rectangles.length];
        for (int i = 0; i < this.rectangles.length; ++i) {
            Rectangle rectangle = this.rectangles[i];
            rectangleArray[i] = new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        return rectangleArray;
    }

    public boolean getStippled() {
        this.checkWidget();
        return this.stippled;
    }

    void moveRectangles(int n, int n2) {
        if (this.bounds == null) {
            return;
        }
        if (n < 0 && (this.style & 0x4000) == 0) {
            n = 0;
        }
        if (n > 0 && (this.style & 0x20000) == 0) {
            n = 0;
        }
        if (n2 < 0 && (this.style & 0x80) == 0) {
            n2 = 0;
        }
        if (n2 > 0 && (this.style & 0x400) == 0) {
            n2 = 0;
        }
        if (n == 0 && n2 == 0) {
            return;
        }
        Rectangle rectangle = this.bounds;
        rectangle.x += n;
        Rectangle rectangle2 = this.bounds;
        rectangle2.y += n2;
        Rectangle[] rectangleArray = this.rectangles;
        int n3 = rectangleArray.length;
        for (int i = 0; i < n3; ++i) {
            Rectangle rectangle3;
            Rectangle rectangle4 = rectangle3 = rectangleArray[i];
            rectangle3.x += n;
            Rectangle rectangle5 = rectangle4;
            rectangle5.y += n2;
        }
    }

    public boolean open() {
        long l2;
        Serializable serializable;
        boolean bl;
        int n;
        this.checkWidget();
        this.cancelled = false;
        this.tracking = true;
        int n2 = this.style & 0x480;
        if (n2 == 128 || n2 == 1024) {
            this.cursorOrientation |= n2;
        }
        if ((n = this.style & 0x24000) == 16384 || n == 131072) {
            this.cursorOrientation |= n;
        }
        Callback callback = null;
        boolean bl2 = bl = OS.GetKeyState(1) < 0;
        if (this.parent == null) {
            serializable = this.display.getBoundsInPixels();
            this.hwndTransparent = OS.CreateWindowEx(0x8080080, this.display.windowClass, null, Integer.MIN_VALUE, serializable.x, serializable.y, serializable.width, serializable.height, 0L, 0L, OS.GetModuleHandle(null), null);
            OS.SetLayeredWindowAttributes(this.hwndTransparent, 0, (byte)1, 2);
            this.hwndOpaque = OS.CreateWindowEx(0x8080080, this.display.windowClass, null, Integer.MIN_VALUE, serializable.x, serializable.y, serializable.width, serializable.height, this.hwndTransparent, 0L, OS.GetModuleHandle(null), null);
            OS.SetLayeredWindowAttributes(this.hwndOpaque, 0xFFFFFF, (byte)0, 3);
            this.drawn = false;
            callback = new Callback(this, "transparentProc", 4);
            l2 = callback.getAddress();
            this.oldTransparentProc = OS.GetWindowLongPtr(this.hwndTransparent, -4);
            OS.SetWindowLongPtr(this.hwndTransparent, -4, l2);
            this.oldOpaqueProc = OS.GetWindowLongPtr(this.hwndOpaque, -4);
            OS.SetWindowLongPtr(this.hwndOpaque, -4, l2);
            OS.ShowWindow(this.hwndTransparent, 4);
            OS.ShowWindow(this.hwndOpaque, 4);
        } else if (!bl) {
            serializable = this.display.getBoundsInPixels();
            this.hwndTransparent = OS.CreateWindowEx(32, this.display.windowClass, null, Integer.MIN_VALUE, serializable.x, serializable.y, serializable.width, serializable.height, 0L, 0L, OS.GetModuleHandle(null), null);
            callback = new Callback(this, "transparentProc", 4);
            l2 = callback.getAddress();
            this.oldTransparentProc = OS.GetWindowLongPtr(this.hwndTransparent, -4);
            OS.SetWindowLongPtr(this.hwndTransparent, -4, l2);
            OS.ShowWindow(this.hwndTransparent, 4);
        }
        this.update();
        this.drawRectangles(this.rectangles, this.stippled);
        serializable = null;
        if (bl) {
            POINT pOINT = new POINT();
            OS.GetCursorPos(pOINT);
            serializable = new Point(pOINT.x, pOINT.y);
        } else {
            serializable = (this.style & 0x10) != 0 ? this.adjustResizeCursor() : this.adjustMoveCursor();
        }
        if (serializable != null) {
            this.oldX = ((Point)serializable).x;
            this.oldY = ((Point)serializable).y;
        }
        Display display = this.display;
        MSG mSG = new MSG();
        while (!(!this.tracking || this.cancelled || this.parent != null && this.parent.isDisposed())) {
            display.runSkin();
            display.runDeferredLayouts();
            display.sendPreExternalEventDispatchEvent();
            OS.GetMessage(mSG, 0L, 0, 0);
            display.sendPostExternalEventDispatchEvent();
            OS.TranslateMessage(mSG);
            switch (mSG.message) {
                case 512: 
                case 514: {
                    this.wmMouse(mSG.message, mSG.wParam, mSG.lParam);
                    break;
                }
                case 646: {
                    this.wmIMEChar(mSG.hwnd, mSG.wParam, mSG.lParam);
                    break;
                }
                case 258: {
                    this.wmChar(mSG.hwnd, mSG.wParam, mSG.lParam);
                    break;
                }
                case 256: {
                    this.wmKeyDown(mSG.hwnd, mSG.wParam, mSG.lParam);
                    break;
                }
                case 257: {
                    this.wmKeyUp(mSG.hwnd, mSG.wParam, mSG.lParam);
                    break;
                }
                case 262: {
                    this.wmSysChar(mSG.hwnd, mSG.wParam, mSG.lParam);
                    break;
                }
                case 260: {
                    this.wmSysKeyDown(mSG.hwnd, mSG.wParam, mSG.lParam);
                    break;
                }
                case 261: {
                    this.wmSysKeyUp(mSG.hwnd, mSG.wParam, mSG.lParam);
                }
            }
            if (256 <= mSG.message && mSG.message <= 264 || 512 <= mSG.message && mSG.message <= 525) continue;
            if (this.hwndOpaque == 0L && mSG.message == 15) {
                this.update();
                this.drawRectangles(this.rectangles, this.stippled);
            }
            OS.DispatchMessage(mSG);
            if (this.hwndOpaque == 0L && mSG.message == 15) {
                this.drawRectangles(this.rectangles, this.stippled);
            }
            display.runAsyncMessages(false);
        }
        if (bl) {
            OS.ReleaseCapture();
        }
        if (!this.isDisposed()) {
            this.update();
            this.drawRectangles(this.rectangles, this.stippled);
        }
        if (this.hwndTransparent != 0L) {
            OS.DestroyWindow(this.hwndTransparent);
            this.hwndTransparent = 0L;
        }
        this.hwndOpaque = 0L;
        if (callback != null) {
            callback.dispose();
            long l3 = 0L;
            this.oldOpaqueProc = 0L;
            this.oldTransparentProc = 0L;
        }
        if (this.resizeCursor != 0L) {
            OS.DestroyCursor(this.resizeCursor);
            this.resizeCursor = 0L;
        }
        this.tracking = false;
        return !this.cancelled;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.parent = null;
        Object var1_1 = null;
        this.proportions = var1_1;
        this.rectangles = var1_1;
        this.bounds = null;
    }

    public void removeControlListener(ControlListener controlListener) {
        this.checkWidget();
        if (controlListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(11, controlListener);
        this.eventTable.unhook(10, controlListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        this.checkWidget();
        if (keyListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(2, keyListener);
        this.eventTable.unhook(1, keyListener);
    }

    void resizeRectangles(int n, int n2) {
        Rectangle[] rectangleArray;
        if (this.bounds == null) {
            return;
        }
        if (n < 0 && (this.style & 0x4000) != 0 && (this.cursorOrientation & 0x20000) == 0) {
            this.cursorOrientation |= 0x4000;
        }
        if (n > 0 && (this.style & 0x20000) != 0 && (this.cursorOrientation & 0x4000) == 0) {
            this.cursorOrientation |= 0x20000;
        }
        if (n2 < 0 && (this.style & 0x80) != 0 && (this.cursorOrientation & 0x400) == 0) {
            this.cursorOrientation |= 0x80;
        }
        if (n2 > 0 && (this.style & 0x400) != 0 && (this.cursorOrientation & 0x80) == 0) {
            this.cursorOrientation |= 0x400;
        }
        if ((this.cursorOrientation & 0x4000) != 0) {
            if (n > this.bounds.width) {
                if ((this.style & 0x20000) == 0) {
                    return;
                }
                this.cursorOrientation |= 0x20000;
                this.cursorOrientation &= 0xFFFFBFFF;
                rectangleArray = this.bounds;
                rectangleArray.x += this.bounds.width;
                n -= this.bounds.width;
                this.bounds.width = 0;
                if (this.proportions.length > 1) {
                    Rectangle[] rectangleArray2 = this.proportions;
                    int n3 = rectangleArray2.length;
                    for (int i = 0; i < n3; ++i) {
                        Rectangle rectangle = rectangleArray2[i];
                        rectangle.x = 100 - rectangle.x - rectangle.width;
                    }
                }
            }
        } else if ((this.cursorOrientation & 0x20000) != 0 && this.bounds.width < -n) {
            if ((this.style & 0x4000) == 0) {
                return;
            }
            this.cursorOrientation |= 0x4000;
            this.cursorOrientation &= 0xFFFDFFFF;
            n += this.bounds.width;
            this.bounds.width = 0;
            if (this.proportions.length > 1) {
                for (Rectangle rectangle : this.proportions) {
                    rectangle.x = 100 - rectangle.x - rectangle.width;
                }
            }
        }
        if ((this.cursorOrientation & 0x80) != 0) {
            if (n2 > this.bounds.height) {
                if ((this.style & 0x400) == 0) {
                    return;
                }
                this.cursorOrientation |= 0x400;
                this.cursorOrientation &= 0xFFFFFF7F;
                rectangleArray = this.bounds;
                rectangleArray.y += this.bounds.height;
                n2 -= this.bounds.height;
                this.bounds.height = 0;
                if (this.proportions.length > 1) {
                    for (Rectangle rectangle : this.proportions) {
                        rectangle.y = 100 - rectangle.y - rectangle.height;
                    }
                }
            }
        } else if ((this.cursorOrientation & 0x400) != 0 && this.bounds.height < -n2) {
            if ((this.style & 0x80) == 0) {
                return;
            }
            this.cursorOrientation |= 0x80;
            this.cursorOrientation &= 0xFFFFFBFF;
            n2 += this.bounds.height;
            this.bounds.height = 0;
            if (this.proportions.length > 1) {
                for (Rectangle rectangle : this.proportions) {
                    rectangle.y = 100 - rectangle.y - rectangle.height;
                }
            }
        }
        if ((this.cursorOrientation & 0x4000) != 0) {
            rectangleArray = this.bounds;
            rectangleArray.x += n;
            Rectangle rectangle = this.bounds;
            rectangle.width -= n;
        } else if ((this.cursorOrientation & 0x20000) != 0) {
            rectangleArray = this.bounds;
            rectangleArray.width += n;
        }
        if ((this.cursorOrientation & 0x80) != 0) {
            rectangleArray = this.bounds;
            rectangleArray.y += n2;
            Rectangle rectangle = this.bounds;
            rectangle.height -= n2;
        } else if ((this.cursorOrientation & 0x400) != 0) {
            rectangleArray = this.bounds;
            rectangleArray.height += n2;
        }
        rectangleArray = new Rectangle[this.rectangles.length];
        for (int i = 0; i < this.rectangles.length; ++i) {
            Rectangle rectangle = this.proportions[i];
            rectangleArray[i] = new Rectangle(rectangle.x * this.bounds.width / 100 + this.bounds.x, rectangle.y * this.bounds.height / 100 + this.bounds.y, rectangle.width * this.bounds.width / 100, rectangle.height * this.bounds.height / 100);
        }
        this.rectangles = rectangleArray;
    }

    public void setCursor(Cursor cursor) {
        this.checkWidget();
        this.clientCursor = cursor;
        if (cursor != null && this.inEvent) {
            OS.SetCursor(this.clientCursor.handle);
        }
    }

    public void setRectangles(Rectangle[] rectangleArray) {
        this.checkWidget();
        if (rectangleArray == null) {
            this.error(4);
        }
        Rectangle[] rectangleArray2 = new Rectangle[rectangleArray.length];
        for (int i = 0; i < rectangleArray.length; ++i) {
            rectangleArray2[i] = DPIUtil.autoScaleUp(rectangleArray[i]);
        }
        this.setRectanglesInPixels(rectangleArray2);
    }

    void setRectanglesInPixels(Rectangle[] rectangleArray) {
        this.rectangles = new Rectangle[rectangleArray.length];
        for (int i = 0; i < rectangleArray.length; ++i) {
            Rectangle rectangle = rectangleArray[i];
            if (rectangle == null) {
                this.error(4);
            }
            this.rectangles[i] = new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        this.proportions = this.computeProportions(rectangleArray);
    }

    public void setStippled(boolean bl) {
        this.checkWidget();
        this.stippled = bl;
    }

    long transparentProc(long l2, long l3, long l4, long l5) {
        switch ((int)l3) {
            case 132: {
                if (!this.inEvent) break;
                return -1L;
            }
            case 32: {
                if (this.clientCursor != null) {
                    OS.SetCursor(this.clientCursor.handle);
                    return 1L;
                }
                if (this.resizeCursor == 0L) break;
                OS.SetCursor(this.resizeCursor);
                return 1L;
            }
            case 15: {
                Object object;
                if (this.hwndOpaque != l2) break;
                PAINTSTRUCT pAINTSTRUCT = new PAINTSTRUCT();
                long l6 = OS.BeginPaint(l2, pAINTSTRUCT);
                long l7 = 0L;
                long l8 = 0L;
                long l9 = 0L;
                long l10 = OS.CreateSolidBrush(0xFFFFFF);
                l9 = OS.SelectObject(l6, l10);
                OS.PatBlt(l6, pAINTSTRUCT.left, pAINTSTRUCT.top, pAINTSTRUCT.right - pAINTSTRUCT.left, pAINTSTRUCT.bottom - pAINTSTRUCT.top, 15728673);
                OS.SelectObject(l6, l9);
                OS.DeleteObject(l10);
                int n = 1;
                if (this.stippled) {
                    n = 3;
                    object = new byte[]{-86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0, -86, 0, 85, 0};
                    l7 = OS.CreateBitmap(8, 8, 1, 1, (byte[])object);
                    l8 = OS.CreatePatternBrush(l7);
                    l9 = OS.SelectObject(l6, l8);
                    OS.SetBkColor(l6, 0xF0F0F0);
                } else {
                    l9 = OS.SelectObject(l6, OS.GetStockObject(4));
                }
                object = new RECT();
                for (Rectangle rectangle : this.rectangles) {
                    ((RECT)object).left = rectangle.x;
                    ((RECT)object).top = rectangle.y;
                    ((RECT)object).right = rectangle.x + rectangle.width;
                    ((RECT)object).bottom = rectangle.y + rectangle.height;
                    OS.MapWindowPoints(0L, this.hwndOpaque, (RECT)object, 2);
                    int n2 = ((RECT)object).right - ((RECT)object).left;
                    int n3 = ((RECT)object).bottom - ((RECT)object).top;
                    OS.PatBlt(l6, ((RECT)object).left, ((RECT)object).top, n2, n, 15728673);
                    OS.PatBlt(l6, ((RECT)object).left, ((RECT)object).top + n, n, n3 - n * 2, 15728673);
                    OS.PatBlt(l6, ((RECT)object).right - n, ((RECT)object).top + n, n, n3 - n * 2, 15728673);
                    OS.PatBlt(l6, ((RECT)object).left, ((RECT)object).bottom - n, n2, n, 15728673);
                }
                OS.SelectObject(l6, l9);
                if (this.stippled) {
                    OS.DeleteObject(l8);
                    OS.DeleteObject(l7);
                }
                OS.EndPaint(l2, pAINTSTRUCT);
                if (!this.drawn) {
                    OS.SetLayeredWindowAttributes(this.hwndOpaque, 0xFFFFFF, (byte)-1, 3);
                    this.drawn = true;
                }
                return 0L;
            }
        }
        return OS.CallWindowProc(l2 == this.hwndTransparent ? this.oldTransparentProc : this.oldOpaqueProc, l2, (int)l3, l4, l5);
    }

    void update() {
        if (this.hwndOpaque != 0L) {
            return;
        }
        if (this.parent != null) {
            if (this.parent.isDisposed()) {
                return;
            }
            Shell shell = this.parent.getShell();
            shell.update(true);
        } else {
            this.display.update();
        }
    }

    @Override
    LRESULT wmKeyDown(long l2, long l3, long l4) {
        LRESULT lRESULT = super.wmKeyDown(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        boolean bl = this.parent != null && (this.parent.style & 0x8000000) != 0;
        int n = OS.GetKeyState(17) < 0 ? 1 : 9;
        int n2 = 0;
        int n3 = 0;
        switch ((int)l3) {
            case 27: {
                this.cancelled = true;
                this.tracking = false;
                break;
            }
            case 13: {
                this.tracking = false;
                break;
            }
            case 37: {
                n2 = bl ? n : -n;
                break;
            }
            case 39: {
                n2 = bl ? -n : n;
                break;
            }
            case 38: {
                n3 = -n;
                break;
            }
            case 40: {
                n3 = n;
            }
        }
        if (n2 != 0 || n3 != 0) {
            Serializable serializable;
            Rectangle[] rectangleArray = this.rectangles;
            boolean bl2 = this.stippled;
            Rectangle[] rectangleArray2 = new Rectangle[this.rectangles.length];
            for (int i = 0; i < this.rectangles.length; ++i) {
                serializable = this.rectangles[i];
                rectangleArray2[i] = new Rectangle(serializable.x, serializable.y, serializable.width, serializable.height);
            }
            Event event = new Event();
            event.setLocationInPixels(this.oldX + n2, this.oldY + n3);
            if ((this.style & 0x10) != 0) {
                this.resizeRectangles(n2, n3);
                this.inEvent = true;
                this.sendEvent(11, event);
                this.inEvent = false;
                if (this.isDisposed()) {
                    this.cancelled = true;
                    return LRESULT.ONE;
                }
                boolean bl3 = false;
                if (this.rectangles != rectangleArray) {
                    int n4 = this.rectangles.length;
                    if (n4 != rectangleArray2.length) {
                        bl3 = true;
                    } else {
                        for (int i = 0; i < n4; ++i) {
                            if (this.rectangles[i].equals(rectangleArray2[i])) continue;
                            bl3 = true;
                            break;
                        }
                    }
                } else {
                    bl3 = true;
                }
                if (bl3) {
                    this.drawRectangles(rectangleArray2, bl2);
                    this.update();
                    this.drawRectangles(this.rectangles, this.stippled);
                }
                serializable = this.adjustResizeCursor();
            } else {
                this.moveRectangles(n2, n3);
                this.inEvent = true;
                this.sendEvent(10, event);
                this.inEvent = false;
                if (this.isDisposed()) {
                    this.cancelled = true;
                    return LRESULT.ONE;
                }
                boolean bl4 = false;
                if (this.rectangles != rectangleArray) {
                    int n5 = this.rectangles.length;
                    if (n5 != rectangleArray2.length) {
                        bl4 = true;
                    } else {
                        for (int i = 0; i < n5; ++i) {
                            if (this.rectangles[i].equals(rectangleArray2[i])) continue;
                            bl4 = true;
                            break;
                        }
                    }
                } else {
                    bl4 = true;
                }
                if (bl4) {
                    this.drawRectangles(rectangleArray2, bl2);
                    this.update();
                    this.drawRectangles(this.rectangles, this.stippled);
                }
                serializable = this.adjustMoveCursor();
            }
            if (serializable != null) {
                this.oldX = ((Point)serializable).x;
                this.oldY = ((Point)serializable).y;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmSysKeyDown(long l2, long l3, long l4) {
        LRESULT lRESULT = super.wmSysKeyDown(l2, l3, l4);
        if (lRESULT != null) {
            return lRESULT;
        }
        this.cancelled = true;
        this.tracking = false;
        return lRESULT;
    }

    LRESULT wmMouse(int n, long l2, long l3) {
        boolean bl = this.parent != null && (this.parent.style & 0x8000000) != 0;
        int n2 = OS.GetMessagePos();
        int n3 = OS.GET_X_LPARAM(n2);
        int n4 = OS.GET_Y_LPARAM(n2);
        if (n3 != this.oldX || n4 != this.oldY) {
            Rectangle[] rectangleArray = this.rectangles;
            boolean bl2 = this.stippled;
            Rectangle[] rectangleArray2 = new Rectangle[this.rectangles.length];
            for (int i = 0; i < this.rectangles.length; ++i) {
                Rectangle rectangle = this.rectangles[i];
                rectangleArray2[i] = new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            }
            Event event = new Event();
            event.setLocationInPixels(n3, n4);
            if ((this.style & 0x10) != 0) {
                Point point;
                if (bl) {
                    this.resizeRectangles(this.oldX - n3, n4 - this.oldY);
                } else {
                    this.resizeRectangles(n3 - this.oldX, n4 - this.oldY);
                }
                this.inEvent = true;
                this.sendEvent(11, event);
                this.inEvent = false;
                if (this.isDisposed()) {
                    this.cancelled = true;
                    return LRESULT.ONE;
                }
                boolean bl3 = false;
                if (this.rectangles != rectangleArray) {
                    int n5 = this.rectangles.length;
                    if (n5 != rectangleArray2.length) {
                        bl3 = true;
                    } else {
                        for (int i = 0; i < n5; ++i) {
                            if (this.rectangles[i].equals(rectangleArray2[i])) continue;
                            bl3 = true;
                            break;
                        }
                    }
                } else {
                    bl3 = true;
                }
                if (bl3) {
                    this.drawRectangles(rectangleArray2, bl2);
                    this.update();
                    this.drawRectangles(this.rectangles, this.stippled);
                }
                if ((point = this.adjustResizeCursor()) != null) {
                    n3 = point.x;
                    n4 = point.y;
                }
            } else {
                if (bl) {
                    this.moveRectangles(this.oldX - n3, n4 - this.oldY);
                } else {
                    this.moveRectangles(n3 - this.oldX, n4 - this.oldY);
                }
                this.inEvent = true;
                this.sendEvent(10, event);
                this.inEvent = false;
                if (this.isDisposed()) {
                    this.cancelled = true;
                    return LRESULT.ONE;
                }
                boolean bl4 = false;
                if (this.rectangles != rectangleArray) {
                    int n6 = this.rectangles.length;
                    if (n6 != rectangleArray2.length) {
                        bl4 = true;
                    } else {
                        for (int i = 0; i < n6; ++i) {
                            if (this.rectangles[i].equals(rectangleArray2[i])) continue;
                            bl4 = true;
                            break;
                        }
                    }
                } else {
                    bl4 = true;
                }
                if (bl4) {
                    this.drawRectangles(rectangleArray2, bl2);
                    this.update();
                    this.drawRectangles(this.rectangles, this.stippled);
                }
            }
            this.oldX = n3;
            this.oldY = n4;
        }
        this.tracking = n != 514;
        return null;
    }
}

