/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLBARINFO;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class ScrollBar
extends Widget {
    Scrollable parent;
    int increment;
    int pageIncrement;

    ScrollBar(Scrollable scrollable, int n) {
        super(scrollable, ScrollBar.checkStyle(n));
        this.parent = scrollable;
        this.createWidget();
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

    static int checkStyle(int n) {
        return Widget.checkBits(n, 256, 512, 0, 0, 0, 0);
    }

    void createWidget() {
        this.increment = 1;
        this.pageIncrement = 10;
    }

    @Override
    void destroyWidget() {
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        OS.ShowScrollBar(l2, n, false);
        this.parent.destroyScrollBar(this.style);
        this.releaseHandle();
    }

    Rectangle getBounds() {
        int n;
        int n2;
        this.parent.forceResize();
        RECT rECT = new RECT();
        OS.GetClientRect(this.parent.scrolledHandle(), rECT);
        int n3 = 0;
        int n4 = 0;
        if ((this.style & 0x100) != 0) {
            n4 = rECT.bottom - rECT.top;
            n2 = rECT.right - rECT.left;
            n = OS.GetSystemMetrics(3);
        } else {
            n3 = rECT.right - rECT.left;
            n2 = OS.GetSystemMetrics(2);
            n = rECT.bottom - rECT.top;
        }
        return new Rectangle(n3, n4, n2, n);
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
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        OS.GetScrollInfo(l2, n, sCROLLINFO);
        return sCROLLINFO.nMax;
    }

    public int getMinimum() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 1;
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        OS.GetScrollInfo(l2, n, sCROLLINFO);
        return sCROLLINFO.nMin;
    }

    public int getPageIncrement() {
        this.checkWidget();
        return this.pageIncrement;
    }

    public Scrollable getParent() {
        this.checkWidget();
        return this.parent;
    }

    public int getSelection() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 4;
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        OS.GetScrollInfo(l2, n, sCROLLINFO);
        return sCROLLINFO.nPos;
    }

    public Point getSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getSizeInPixels());
    }

    Point getSizeInPixels() {
        int n;
        int n2;
        this.parent.forceResize();
        RECT rECT = new RECT();
        OS.GetClientRect(this.parent.scrolledHandle(), rECT);
        if ((this.style & 0x100) != 0) {
            n2 = rECT.right - rECT.left;
            n = OS.GetSystemMetrics(3);
        } else {
            n2 = OS.GetSystemMetrics(2);
            n = rECT.bottom - rECT.top;
        }
        return new Point(n2, n);
    }

    public int getThumb() {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 2;
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        OS.GetScrollInfo(l2, n, sCROLLINFO);
        if (sCROLLINFO.nPage != 0) {
            SCROLLINFO sCROLLINFO2 = sCROLLINFO;
            --sCROLLINFO2.nPage;
        }
        return sCROLLINFO.nPage;
    }

    public Rectangle getThumbBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getThumbBoundsInPixels());
    }

    Rectangle getThumbBoundsInPixels() {
        int n;
        int n2;
        int n3;
        int n4;
        this.parent.forceResize();
        SCROLLBARINFO sCROLLBARINFO = new SCROLLBARINFO();
        sCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
        if ((this.style & 0x100) != 0) {
            OS.GetScrollBarInfo(this.parent.handle, -6, sCROLLBARINFO);
            n4 = sCROLLBARINFO.rcScrollBar.left + sCROLLBARINFO.xyThumbTop;
            n3 = sCROLLBARINFO.rcScrollBar.top;
            n2 = sCROLLBARINFO.xyThumbBottom - sCROLLBARINFO.xyThumbTop;
            n = sCROLLBARINFO.rcScrollBar.bottom - sCROLLBARINFO.rcScrollBar.top;
        } else {
            OS.GetScrollBarInfo(this.parent.handle, -5, sCROLLBARINFO);
            n4 = sCROLLBARINFO.rcScrollBar.left;
            n3 = sCROLLBARINFO.rcScrollBar.top + sCROLLBARINFO.xyThumbTop;
            n2 = sCROLLBARINFO.rcScrollBar.right - sCROLLBARINFO.rcScrollBar.left;
            n = sCROLLBARINFO.xyThumbBottom - sCROLLBARINFO.xyThumbTop;
        }
        RECT rECT = new RECT();
        rECT.left = n4;
        rECT.top = n3;
        rECT.right = n4 + n2;
        rECT.bottom = n3 + n;
        OS.MapWindowPoints(0L, this.parent.handle, rECT, 2);
        return new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
    }

    public Rectangle getThumbTrackBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getThumbTrackBoundsInPixels());
    }

    Rectangle getThumbTrackBoundsInPixels() {
        int n;
        int n2;
        int n3;
        this.parent.forceResize();
        SCROLLBARINFO sCROLLBARINFO = new SCROLLBARINFO();
        sCROLLBARINFO.cbSize = SCROLLBARINFO.sizeof;
        int n4 = 0;
        int n5 = 0;
        if ((this.style & 0x100) != 0) {
            OS.GetScrollBarInfo(this.parent.handle, -6, sCROLLBARINFO);
            n3 = OS.GetSystemMetrics(3);
            n5 = sCROLLBARINFO.rcScrollBar.top;
            n2 = sCROLLBARINFO.rcScrollBar.right - sCROLLBARINFO.rcScrollBar.left;
            n = n3;
            if (n2 <= 2 * n3) {
                n4 = sCROLLBARINFO.rcScrollBar.left + n2 / 2;
                n2 = 0;
            } else {
                n4 = sCROLLBARINFO.rcScrollBar.left + n3;
                n2 -= 2 * n3;
            }
        } else {
            OS.GetScrollBarInfo(this.parent.handle, -5, sCROLLBARINFO);
            n3 = OS.GetSystemMetrics(20);
            n4 = sCROLLBARINFO.rcScrollBar.left;
            n2 = n3;
            n = sCROLLBARINFO.rcScrollBar.bottom - sCROLLBARINFO.rcScrollBar.top;
            if (n <= 2 * n3) {
                n5 = sCROLLBARINFO.rcScrollBar.top + n / 2;
                n = 0;
            } else {
                n5 = sCROLLBARINFO.rcScrollBar.top + n3;
                n -= 2 * n3;
            }
        }
        RECT rECT = new RECT();
        rECT.left = n4;
        rECT.top = n5;
        rECT.right = n4 + n2;
        rECT.bottom = n5 + n;
        OS.MapWindowPoints(0L, this.parent.handle, rECT, 2);
        return new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
    }

    long hwndScrollBar() {
        return this.parent.scrolledHandle();
    }

    public boolean isEnabled() {
        this.checkWidget();
        return this == false && this.parent.isEnabled();
    }

    public boolean isVisible() {
        this.checkWidget();
        return this != false && this.parent.isVisible();
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this.parent.horizontalBar == this) {
            this.parent.horizontalBar = null;
        }
        if (this.parent.verticalBar == this) {
            this.parent.verticalBar = null;
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

    int scrollBarType() {
        return (this.style & 0x200) != 0 ? 1 : 0;
    }

    public void setEnabled(boolean bl) {
        this.checkWidget();
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        int n2 = bl ? 0 : 3;
        OS.EnableScrollBar(l2, n, n2);
        this.state = bl ? (this.state &= 0xFFFFFFF7) : (this.state |= 8);
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
        long l2 = this.hwndScrollBar();
        int n2 = this.scrollBarType();
        sCROLLINFO.fMask = 9;
        OS.GetScrollInfo(l2, n2, sCROLLINFO);
        if (n - sCROLLINFO.nMin - sCROLLINFO.nPage < 1) {
            return;
        }
        sCROLLINFO.nMax = n;
        this.SetScrollInfo(l2, n2, sCROLLINFO, true);
    }

    public void setMinimum(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        long l2 = this.hwndScrollBar();
        int n2 = this.scrollBarType();
        sCROLLINFO.fMask = 9;
        OS.GetScrollInfo(l2, n2, sCROLLINFO);
        if (sCROLLINFO.nMax - n - sCROLLINFO.nPage < 1) {
            return;
        }
        sCROLLINFO.nMin = n;
        this.SetScrollInfo(l2, n2, sCROLLINFO, true);
    }

    public void setPageIncrement(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        this.pageIncrement = n;
    }

    boolean SetScrollInfo(long l2, int n, SCROLLINFO sCROLLINFO, boolean bl) {
        boolean bl2 = false;
        boolean bl3 = this.getVisible();
        ScrollBar scrollBar = null;
        switch (n) {
            case 0: {
                scrollBar = this.parent.getVerticalBar();
                break;
            }
            case 1: {
                scrollBar = this.parent.getHorizontalBar();
            }
        }
        boolean bl4 = bl2 = scrollBar != null && scrollBar != false;
        if (!bl3 || (this.state & 8) != 0) {
            bl = false;
        }
        boolean bl5 = OS.SetScrollInfo(l2, n, sCROLLINFO, bl);
        if (!bl3) {
            OS.ShowScrollBar(l2, bl2 ? n : 3, false);
        }
        if (bl3 && scrollBar != null && !bl2) {
            OS.ShowScrollBar(l2, n == 0 ? 1 : 0, false);
        }
        if ((this.state & 8) != 0) {
            OS.EnableScrollBar(l2, n, 3);
        }
        return bl5;
    }

    public void setSelection(int n) {
        this.checkWidget();
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        long l2 = this.hwndScrollBar();
        int n2 = this.scrollBarType();
        sCROLLINFO.fMask = 4;
        sCROLLINFO.nPos = n;
        this.SetScrollInfo(l2, n2, sCROLLINFO, true);
    }

    public void setThumb(int n) {
        this.checkWidget();
        if (n < 1) {
            return;
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        long l2 = this.hwndScrollBar();
        int n2 = this.scrollBarType();
        sCROLLINFO.fMask = 11;
        OS.GetScrollInfo(l2, n2, sCROLLINFO);
        sCROLLINFO.nPage = n;
        if (sCROLLINFO.nPage != 0) {
            SCROLLINFO sCROLLINFO2 = sCROLLINFO;
            ++sCROLLINFO2.nPage;
        }
        this.SetScrollInfo(l2, n2, sCROLLINFO, true);
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
        long l2 = this.hwndScrollBar();
        int n7 = this.scrollBarType();
        this.SetScrollInfo(l2, n7, sCROLLINFO, true);
    }

    public void setVisible(boolean bl) {
        Object object;
        this.checkWidget();
        boolean bl2 = bl;
        if (this != false) {
            return;
        }
        this.state = bl ? this.state & 0xFFFFFFEF : this.state | 0x10;
        long l2 = this.hwndScrollBar();
        int n = this.scrollBarType();
        if (!bl && OS.IsAppThemed()) {
            object = new SCROLLBARINFO();
            ((SCROLLBARINFO)object).cbSize = SCROLLBARINFO.sizeof;
            int n2 = (this.style & 0x200) != 0 ? -6 : -5;
            OS.GetScrollBarInfo(l2, n2, (SCROLLBARINFO)object);
            if ((((SCROLLBARINFO)object).rgstate[0] & 0x8000) != 0) {
                OS.ShowScrollBar(l2, n != 1 ? 1 : 0, true);
                n = 3;
            }
        }
        if (OS.ShowScrollBar(l2, n, bl)) {
            if ((this.state & 8) == 0) {
                object = new SCROLLINFO();
                ((SCROLLINFO)object).cbSize = SCROLLINFO.sizeof;
                ((SCROLLINFO)object).fMask = 3;
                OS.GetScrollInfo(l2, n, (SCROLLINFO)object);
                if (((SCROLLINFO)object).nMax - ((SCROLLINFO)object).nMin - ((SCROLLINFO)object).nPage >= 0) {
                    OS.EnableScrollBar(l2, n, 0);
                }
            }
            this.sendEvent(bl ? 22 : 23);
        }
    }

    LRESULT wmScrollChild(long l2, long l3) {
        int n = OS.LOWORD(l2);
        if (n == 8) {
            return null;
        }
        Event event = new Event();
        switch (n) {
            case 4: {
                event.detail = 0;
                break;
            }
            case 5: {
                event.detail = 1;
                break;
            }
            case 6: {
                event.detail = 0x1000007;
                break;
            }
            case 7: {
                event.detail = 0x1000008;
                break;
            }
            case 1: {
                event.detail = 0x1000002;
                break;
            }
            case 0: {
                event.detail = 0x1000001;
                break;
            }
            case 3: {
                event.detail = 0x1000006;
                break;
            }
            case 2: {
                event.detail = 0x1000005;
            }
        }
        this.sendSelectionEvent(13, event, true);
        return null;
    }
}

