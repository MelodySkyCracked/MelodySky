/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.util.regex.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Widget;

public abstract class Scrollable
extends Control {
    ScrollBar horizontalBar;
    ScrollBar verticalBar;
    static final Pattern CTRL_BS_PATTERN = Pattern.compile("\\r?\\n\\z|[\\p{Punct}]+[\\t ]*\\z|[^\\p{Punct}\\s\\n\\r]*[\\t ]*\\z");

    Scrollable() {
    }

    public Scrollable(Composite composite, int n) {
        super(composite, n);
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.DefWindowProc(l2, n, l3, l4);
    }

    public Rectangle computeTrim(int n, int n2, int n3, int n4) {
        this.checkWidget();
        n = DPIUtil.autoScaleUp(n);
        n2 = DPIUtil.autoScaleUp(n2);
        n3 = DPIUtil.autoScaleUp(n3);
        n4 = DPIUtil.autoScaleUp(n4);
        return DPIUtil.autoScaleDown(this.computeTrimInPixels(n, n2, n3, n4));
    }

    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        RECT rECT;
        long l2 = this.scrolledHandle();
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, n, n2, n + n3, n2 + n4);
        int n5 = OS.GetWindowLong(l2, -16);
        int n6 = OS.GetWindowLong(l2, -20);
        OS.AdjustWindowRectEx(rECT2, n5, false, n6);
        if (this.horizontalBar != null) {
            rECT = rECT2;
            rECT.bottom += OS.GetSystemMetrics(3);
        }
        if (this.verticalBar != null) {
            rECT = rECT2;
            rECT.right += OS.GetSystemMetrics(2);
        }
        int n7 = rECT2.right - rECT2.left;
        int n8 = rECT2.bottom - rECT2.top;
        return new Rectangle(rECT2.left, rECT2.top, n7, n8);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.maybeEnableDarkSystemTheme();
    }

    ScrollBar createScrollBar(int n) {
        ScrollBar scrollBar = new ScrollBar(this, n);
        if ((this.state & 2) != 0) {
            scrollBar.setMaximum(100);
            scrollBar.setThumb(10);
        }
        return scrollBar;
    }

    @Override
    void createWidget() {
        super.createWidget();
        if ((this.style & 0x100) != 0) {
            this.horizontalBar = this.createScrollBar(256);
        }
        if ((this.style & 0x200) != 0) {
            this.verticalBar = this.createScrollBar(512);
        }
    }

    @Override
    void updateBackgroundColor() {
        switch (this.applyThemeBackground()) {
            case 0: {
                this.state &= 0xFFFFFEFF;
                break;
            }
            case 1: {
                this.state |= 0x100;
            }
        }
        super.updateBackgroundColor();
    }

    int applyThemeBackground() {
        return this.backgroundAlpha == 0 ? 1 : 0;
    }

    void destroyScrollBar(int n) {
        long l2 = this.scrolledHandle();
        int n2 = OS.GetWindowLong(l2, -16);
        if ((n & 0x100) != 0) {
            this.style &= 0xFFFFFEFF;
            n2 &= 0xFFEFFFFF;
        }
        if ((n & 0x200) != 0) {
            this.style &= 0xFFFFFDFF;
            n2 &= 0xFFDFFFFF;
        }
        OS.SetWindowLong(l2, -16, n2);
    }

    public Rectangle getClientArea() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getClientAreaInPixels());
    }

    Rectangle getClientAreaInPixels() {
        this.forceResize();
        RECT rECT = new RECT();
        long l2 = this.scrolledHandle();
        OS.GetClientRect(l2, rECT);
        int n = rECT.left;
        int n2 = rECT.top;
        int n3 = rECT.right - rECT.left;
        int n4 = rECT.bottom - rECT.top;
        if (l2 != this.handle) {
            OS.GetClientRect(this.handle, rECT);
            OS.MapWindowPoints(this.handle, l2, rECT, 2);
            n = -rECT.left;
            n2 = -rECT.top;
        }
        return new Rectangle(n, n2, n3, n4);
    }

    public ScrollBar getHorizontalBar() {
        this.checkWidget();
        return this.horizontalBar;
    }

    public int getScrollbarsMode() {
        this.checkWidget();
        return 0;
    }

    public ScrollBar getVerticalBar() {
        this.checkWidget();
        return this.verticalBar;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.horizontalBar != null) {
            this.horizontalBar.release(false);
            this.horizontalBar = null;
        }
        if (this.verticalBar != null) {
            this.verticalBar.release(false);
            this.verticalBar = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void reskinChildren(int n) {
        if (this.horizontalBar != null) {
            this.horizontalBar.reskin(n);
        }
        if (this.verticalBar != null) {
            this.verticalBar.reskin(n);
        }
        super.reskinChildren(n);
    }

    long scrolledHandle() {
        return this.handle;
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x10000;
        if ((this.style & 0x100) != 0) {
            n |= 0x100000;
        }
        if ((this.style & 0x200) != 0) {
            n |= 0x200000;
        }
        return n;
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
    LRESULT WM_HSCROLL(long l2, long l3) {
        LRESULT lRESULT = super.WM_HSCROLL(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.horizontalBar != null && l3 == 0L) {
            return this.wmScroll(this.horizontalBar, (this.state & 2) != 0, this.handle, 276, l2, l3);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_MOUSEWHEEL(long l2, long l3) {
        return this.wmScrollWheel((this.state & 2) != 0, l2, l3, false);
    }

    @Override
    LRESULT WM_MOUSEHWHEEL(long l2, long l3) {
        return this.wmScrollWheel((this.state & 2) != 0, -1L * l2, l3, true);
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        long l4 = this.callWindowProc(this.handle, 5, l2, l3);
        super.WM_SIZE(l2, l3);
        if (l4 == 0L) {
            return LRESULT.ZERO;
        }
        return new LRESULT(l4);
    }

    @Override
    LRESULT WM_VSCROLL(long l2, long l3) {
        LRESULT lRESULT = super.WM_VSCROLL(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.verticalBar != null && l3 == 0L) {
            return this.wmScroll(this.verticalBar, (this.state & 2) != 0, this.handle, 277, l2, l3);
        }
        return lRESULT;
    }

    LRESULT wmScrollWheel(boolean bl, long l2, long l3, boolean bl2) {
        boolean bl3;
        LRESULT lRESULT = super.WM_MOUSEWHEEL(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!bl) {
            Event event;
            int n;
            int n2 = this.verticalBar == null ? 0 : this.verticalBar.getSelection();
            int n3 = this.horizontalBar == null ? 0 : this.horizontalBar.getSelection();
            long l4 = this.callWindowProc(this.handle, 522, l2, l3);
            if (this.verticalBar != null && (n = this.verticalBar.getSelection()) != n2) {
                event = new Event();
                event.detail = n < n2 ? 0x1000005 : 0x1000006;
                this.verticalBar.sendSelectionEvent(13, event, true);
            }
            if (this.horizontalBar != null && (n = this.horizontalBar.getSelection()) != n3) {
                event = new Event();
                event.detail = n < n3 ? 0x1000005 : 0x1000006;
                this.horizontalBar.sendSelectionEvent(13, event, true);
            }
            return new LRESULT(l4);
        }
        if ((l2 & 8L) != 0L) {
            return null;
        }
        if ((l2 & 4L) != 0L) {
            boolean bl4 = bl2 = !bl2;
        }
        if (this.verticalBar != null && this.verticalBar.getEnabled() && !bl2) {
            bl3 = true;
        } else {
            if (this.horizontalBar == null || !this.horizontalBar.getEnabled() || !bl2) {
                return null;
            }
            bl3 = false;
        }
        ScrollBar scrollBar = bl3 ? this.verticalBar : this.horizontalBar;
        Widget.MouseWheelData mouseWheelData = new Widget.MouseWheelData(this, bl3, scrollBar, l2, this.display.scrollRemainderBar);
        if (mouseWheelData.count == 0) {
            return null;
        }
        SCROLLINFO sCROLLINFO = new SCROLLINFO();
        sCROLLINFO.cbSize = SCROLLINFO.sizeof;
        sCROLLINFO.fMask = 4;
        OS.GetScrollInfo(this.handle, scrollBar.scrollBarType(), sCROLLINFO);
        SCROLLINFO sCROLLINFO2 = sCROLLINFO;
        sCROLLINFO2.nPos -= mouseWheelData.count;
        OS.SetScrollInfo(this.handle, scrollBar.scrollBarType(), sCROLLINFO, true);
        int n = bl3 ? 277 : 276;
        OS.SendMessage(this.handle, n, 4L, 0L);
        return LRESULT.ZERO;
    }

    LRESULT wmScroll(ScrollBar scrollBar, boolean bl, long l2, int n, long l3, long l4) {
        LRESULT lRESULT = null;
        if (bl) {
            int n2 = n != 276 ? 1 : 0;
            SCROLLINFO sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 21;
            OS.GetScrollInfo(l2, n2, sCROLLINFO);
            sCROLLINFO.fMask = 4;
            int n3 = OS.LOWORD(l3);
            switch (n3) {
                case 8: {
                    return null;
                }
                case 4: 
                case 5: {
                    sCROLLINFO.nPos = sCROLLINFO.nTrackPos;
                    break;
                }
                case 6: {
                    sCROLLINFO.nPos = sCROLLINFO.nMin;
                    break;
                }
                case 7: {
                    sCROLLINFO.nPos = sCROLLINFO.nMax;
                    break;
                }
                case 1: {
                    SCROLLINFO sCROLLINFO2 = sCROLLINFO;
                    sCROLLINFO2.nPos += scrollBar.getIncrement();
                    break;
                }
                case 0: {
                    int n4 = scrollBar.getIncrement();
                    sCROLLINFO.nPos = Math.max(sCROLLINFO.nMin, sCROLLINFO.nPos - n4);
                    break;
                }
                case 3: {
                    SCROLLINFO sCROLLINFO3 = sCROLLINFO;
                    sCROLLINFO3.nPos += scrollBar.getPageIncrement();
                    break;
                }
                case 2: {
                    int n5 = scrollBar.getPageIncrement();
                    sCROLLINFO.nPos = Math.max(sCROLLINFO.nMin, sCROLLINFO.nPos - n5);
                    break;
                }
            }
            OS.SetScrollInfo(l2, n2, sCROLLINFO, true);
        } else {
            long l5 = this.callWindowProc(l2, n, l3, l4);
            lRESULT = l5 == 0L ? LRESULT.ZERO : new LRESULT(l5);
        }
        scrollBar.wmScrollChild(l3, l4);
        return lRESULT;
    }
}

