/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.MARGINS;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.REBARBANDINFO;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TypedListener;

public class CoolItem
extends Item {
    CoolBar parent;
    Control control;
    int id;
    boolean ideal;
    boolean minimum;

    public CoolItem(CoolBar coolBar, int n) {
        super(coolBar, n);
        this.parent = coolBar;
        this.parent.createItem(this, coolBar.getItemCount());
    }

    public CoolItem(CoolBar coolBar, int n, int n2) {
        super(coolBar, n);
        this.parent = coolBar;
        this.parent.createItem(this, n2);
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
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    public Point computeSize(int n, int n2) {
        this.checkWidget();
        n = n != -1 ? DPIUtil.autoScaleUp(n) : n;
        n2 = n2 != -1 ? DPIUtil.autoScaleUp(n2) : n2;
        return DPIUtil.autoScaleDown(this.computeSizeInPixels(n, n2));
    }

    Point computeSizeInPixels(int n, int n2) {
        int n3 = this.parent.indexOf(this);
        if (n3 == -1) {
            return new Point(0, 0);
        }
        int n4 = n;
        int n5 = n2;
        if (n == -1) {
            n4 = 32;
        }
        if (n2 == -1) {
            n5 = 32;
        }
        if ((this.parent.style & 0x200) != 0) {
            n5 += this.parent.getMargin(n3);
        } else {
            n4 += this.parent.getMargin(n3);
        }
        return new Point(n4, n5);
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    public Rectangle getBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        long l2 = this.parent.handle;
        RECT rECT = new RECT();
        OS.SendMessage(l2, 1033, (long)n, rECT);
        MARGINS mARGINS = new MARGINS();
        OS.SendMessage(l2, 1064, 0L, mARGINS);
        RECT rECT2 = rECT;
        rECT2.left -= mARGINS.cxLeftWidth;
        RECT rECT3 = rECT;
        rECT3.right += mARGINS.cxRightWidth;
        if (!this.parent.isLastItemOfRow(n)) {
            RECT rECT4 = rECT;
            rECT4.right = rECT4.right + ((this.parent.style & 0x800000) == 0 ? 2 : 0);
        }
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        if ((this.parent.style & 0x200) != 0) {
            return new Rectangle(rECT.top, rECT.left, n3, n2);
        }
        return new Rectangle(rECT.left, rECT.top, n2, n3);
    }

    Rectangle getClientArea() {
        this.checkWidget();
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        long l2 = this.parent.handle;
        RECT rECT = new RECT();
        OS.SendMessage(l2, 1058, (long)n, rECT);
        RECT rECT2 = new RECT();
        OS.SendMessage(l2, 1033, (long)n, rECT2);
        int n2 = rECT2.left + rECT.left;
        int n3 = rECT2.top;
        int n4 = rECT2.right - rECT2.left - rECT.left;
        int n5 = rECT2.bottom - rECT2.top;
        if ((this.parent.style & 0x800000) == 0) {
            n3 += rECT.top;
            n4 -= rECT.right;
            n5 -= rECT.top + rECT.bottom;
        }
        if (n == 0) {
            REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
            rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
            rEBARBANDINFO.fMask = 2048;
            OS.SendMessage(l2, 1052, (long)n, rEBARBANDINFO);
            n4 = n4 - rEBARBANDINFO.cxHeader + 1;
        }
        return new Rectangle(n2, n3, Math.max(0, n4), Math.max(0, n5));
    }

    public Control getControl() {
        this.checkWidget();
        return this.control;
    }

    public CoolBar getParent() {
        this.checkWidget();
        return this.parent;
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
        this.id = -1;
        this.control = null;
    }

    public void setControl(Control control) {
        int n;
        this.checkWidget();
        if (control != null) {
            if (control.isDisposed()) {
                this.error(5);
            }
            if (control.parent != this.parent) {
                this.error(32);
            }
        }
        if ((n = this.parent.indexOf(this)) == -1) {
            return;
        }
        if (this.control != null && this.control.isDisposed()) {
            this.control = null;
        }
        Control control2 = this.control;
        Control control3 = control;
        long l2 = this.parent.handle;
        long l3 = control3 != null ? control.topHandle() : 0L;
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 16;
        rEBARBANDINFO.hwndChild = l3;
        this.control = control3;
        long l4 = 0L;
        if (control3 != null) {
            l4 = OS.GetWindow(l3, 3);
        }
        boolean bl = control3 != null && !control3.getVisible();
        boolean bl2 = control2 != null && control2.getVisible();
        OS.SendMessage(l2, 1035, (long)n, rEBARBANDINFO);
        if (bl) {
            control3.setVisible(false);
        }
        if (bl2) {
            control2.setVisible(true);
        }
        if (l4 != 0L && l4 != l3) {
            int n2 = 19;
            OS.SetWindowPos(l3, l4, 0, 0, 0, 0, 19);
        }
    }

    public Point getPreferredSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getPreferredSizeInPixels());
    }

    Point getPreferredSizeInPixels() {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Point(0, 0);
        }
        long l2 = this.parent.handle;
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 544;
        OS.SendMessage(l2, 1052, (long)n, rEBARBANDINFO);
        int n2 = rEBARBANDINFO.cxIdeal + this.parent.getMargin(n);
        if ((this.parent.style & 0x200) != 0) {
            return new Point(rEBARBANDINFO.cyMaxChild, n2);
        }
        return new Point(n2, rEBARBANDINFO.cyMaxChild);
    }

    public void setPreferredSize(int n, int n2) {
        this.checkWidget();
        this.setPreferredSizeInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setPreferredSizeInPixels(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.parent.indexOf(this);
        if (n5 == -1) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.max(0, n2);
        this.ideal = true;
        long l2 = this.parent.handle;
        if ((this.parent.style & 0x200) != 0) {
            n4 = Math.max(0, n2 - this.parent.getMargin(n5));
            n3 = n;
        } else {
            n4 = Math.max(0, n - this.parent.getMargin(n5));
            n3 = n2;
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 32;
        OS.SendMessage(l2, 1052, (long)n5, rEBARBANDINFO);
        rEBARBANDINFO.fMask = 544;
        rEBARBANDINFO.cxIdeal = n4;
        rEBARBANDINFO.cyMaxChild = n3;
        if (!this.minimum) {
            rEBARBANDINFO.cyMinChild = n3;
        }
        OS.SendMessage(l2, 1035, (long)n5, rEBARBANDINFO);
    }

    public void setPreferredSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setPreferredSizeInPixels(point.x, point.y);
    }

    public Point getSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getSizeInPixels());
    }

    Point getSizeInPixels() {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Point(0, 0);
        }
        long l2 = this.parent.handle;
        RECT rECT = new RECT();
        OS.SendMessage(l2, 1033, (long)n, rECT);
        MARGINS mARGINS = new MARGINS();
        OS.SendMessage(l2, 1064, 0L, mARGINS);
        RECT rECT2 = rECT;
        rECT2.left -= mARGINS.cxLeftWidth;
        RECT rECT3 = rECT;
        rECT3.right += mARGINS.cxRightWidth;
        if (!this.parent.isLastItemOfRow(n)) {
            RECT rECT4 = rECT;
            rECT4.right = rECT4.right + ((this.parent.style & 0x800000) == 0 ? 2 : 0);
        }
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        if ((this.parent.style & 0x200) != 0) {
            return new Point(n3, n2);
        }
        return new Point(n2, n3);
    }

    public void setSize(int n, int n2) {
        this.checkWidget();
        this.setSizeInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setSizeInPixels(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = this.parent.indexOf(this);
        if (n6 == -1) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.max(0, n2);
        long l2 = this.parent.handle;
        if ((this.parent.style & 0x200) != 0) {
            n5 = n2;
            n4 = n;
            n3 = Math.max(0, n2 - this.parent.getMargin(n6));
        } else {
            n5 = n;
            n4 = n2;
            n3 = Math.max(0, n - this.parent.getMargin(n6));
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 544;
        OS.SendMessage(l2, 1052, (long)n6, rEBARBANDINFO);
        if (!this.ideal) {
            rEBARBANDINFO.cxIdeal = n3;
        }
        if (!this.minimum) {
            rEBARBANDINFO.cyMinChild = n4;
        }
        rEBARBANDINFO.cyChild = n4;
        if (!this.parent.isLastItemOfRow(n6)) {
            MARGINS mARGINS = new MARGINS();
            OS.SendMessage(l2, 1064, 0L, mARGINS);
            int n7 = (this.parent.style & 0x800000) == 0 ? 2 : 0;
            rEBARBANDINFO.cx = (n5 -= mARGINS.cxLeftWidth + mARGINS.cxRightWidth) - n7;
            REBARBANDINFO rEBARBANDINFO2 = rEBARBANDINFO;
            rEBARBANDINFO2.fMask |= 0x40;
        }
        OS.SendMessage(l2, 1035, (long)n6, rEBARBANDINFO);
    }

    public void setSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setSizeInPixels(point.x, point.y);
    }

    public Point getMinimumSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getMinimumSizeInPixels());
    }

    Point getMinimumSizeInPixels() {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return new Point(0, 0);
        }
        long l2 = this.parent.handle;
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 32;
        OS.SendMessage(l2, 1052, (long)n, rEBARBANDINFO);
        if ((this.parent.style & 0x200) != 0) {
            return new Point(rEBARBANDINFO.cyMinChild, rEBARBANDINFO.cxMinChild);
        }
        return new Point(rEBARBANDINFO.cxMinChild, rEBARBANDINFO.cyMinChild);
    }

    public void setMinimumSize(int n, int n2) {
        this.checkWidget();
        this.setMinimumSizeInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void setMinimumSizeInPixels(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.parent.indexOf(this);
        if (n5 == -1) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.max(0, n2);
        this.minimum = true;
        long l2 = this.parent.handle;
        if ((this.parent.style & 0x200) != 0) {
            n4 = n2;
            n3 = n;
        } else {
            n4 = n;
            n3 = n2;
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 32;
        OS.SendMessage(l2, 1052, (long)n5, rEBARBANDINFO);
        rEBARBANDINFO.cxMinChild = n4;
        rEBARBANDINFO.cyMinChild = n3;
        OS.SendMessage(l2, 1035, (long)n5, rEBARBANDINFO);
    }

    public void setMinimumSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setMinimumSizeInPixels(point.x, point.y);
    }

    boolean getWrap() {
        int n = this.parent.indexOf(this);
        long l2 = this.parent.handle;
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 1;
        OS.SendMessage(l2, 1052, (long)n, rEBARBANDINFO);
        return (rEBARBANDINFO.fStyle & 1) != 0;
    }

    void setWrap(boolean bl) {
        int n = this.parent.indexOf(this);
        long l2 = this.parent.handle;
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 1;
        OS.SendMessage(l2, 1052, (long)n, rEBARBANDINFO);
        if (bl) {
            REBARBANDINFO rEBARBANDINFO2 = rEBARBANDINFO;
            rEBARBANDINFO2.fStyle |= 1;
        } else {
            REBARBANDINFO rEBARBANDINFO3 = rEBARBANDINFO;
            rEBARBANDINFO3.fStyle &= 0xFFFFFFFE;
        }
        OS.SendMessage(l2, 1035, (long)n, rEBARBANDINFO);
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
}

