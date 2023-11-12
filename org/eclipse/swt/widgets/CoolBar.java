/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MARGINS;
import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMREBARCHEVRON;
import org.eclipse.swt.internal.win32.NMREBARCHILDSIZE;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.REBARBANDINFO;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;

public class CoolBar
extends Composite {
    CoolItem[] items;
    CoolItem[] originalItems;
    boolean locked;
    boolean ignoreResize;
    static final long ReBarProc;
    static final TCHAR ReBarClass;
    static final int SEPARATOR_WIDTH = 2;
    static final int MAX_WIDTH = Short.MAX_VALUE;
    static final int DEFAULT_COOLBAR_WIDTH = 0;
    static final int DEFAULT_COOLBAR_HEIGHT = 0;

    public CoolBar(Composite composite, int n) {
        super(composite, CoolBar.checkStyle(n));
        if ((n & 0x200) != 0) {
            this.style |= 0x200;
            int n2 = OS.GetWindowLong(this.handle, -16);
            OS.SetWindowLong(this.handle, -16, n2 | 0x80);
        } else {
            this.style |= 0x100;
        }
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.CallWindowProc(ReBarProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        return (n |= 0x80000) & 0xFFFFFCFF;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        int n3;
        int n4 = 0;
        int n5 = 0;
        int n6 = this.getBorderWidthInPixels();
        int n7 = n == -1 ? 16383 : n + n6 * 2;
        int n8 = n2 == -1 ? 16383 : n2 + n6 * 2;
        int n9 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        if (n9 != 0) {
            this.ignoreResize = true;
            n3 = 0;
            if (OS.IsWindowVisible(this.handle)) {
                n3 = 1;
                OS.UpdateWindow(this.handle);
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
            RECT rECT = new RECT();
            OS.GetWindowRect(this.handle, rECT);
            int n10 = rECT.right - rECT.left;
            int n11 = rECT.bottom - rECT.top;
            int n12 = 30;
            OS.SetWindowPos(this.handle, 0L, 0, 0, n7, n8, 30);
            RECT rECT2 = new RECT();
            OS.SendMessage(this.handle, 1033, (long)(n9 - 1), rECT2);
            n5 = Math.max(n5, rECT2.bottom);
            OS.SetWindowPos(this.handle, 0L, 0, 0, n10, n11, 30);
            REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
            rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
            rEBARBANDINFO.fMask = 513;
            int n13 = 0;
            for (int i = 0; i < n9; ++i) {
                OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
                if ((rEBARBANDINFO.fStyle & 1) != 0) {
                    n4 = Math.max(n4, n13);
                    n13 = 0;
                }
                n13 += rEBARBANDINFO.cxIdeal + this.getMargin(i);
            }
            n4 = Math.max(n4, n13);
            if (n3 != 0) {
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
            }
            this.ignoreResize = false;
        }
        if (n4 == 0) {
            n4 = 0;
        }
        if (n5 == 0) {
            n5 = 0;
        }
        if ((this.style & 0x200) != 0) {
            n3 = n4;
            n4 = n5;
            n5 = n3;
        }
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n5 = n2;
        }
        return new Point(n4 += n6 * 2, n5 += n6 * 2);
    }

    @Override
    void createHandle() {
        super.createHandle();
        this.state &= 0xFFFFFEFD;
        long l2 = OS.GetStockObject(13);
        OS.SendMessage(this.handle, 48, l2, 0L);
    }

    void createItem(CoolItem coolItem, int n) {
        boolean bl;
        int n2;
        int n3 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        if (0 > n || n > n3) {
            this.error(6);
        }
        for (n2 = 0; n2 < this.items.length && this.items[n2] != null; ++n2) {
        }
        if (n2 == this.items.length) {
            CoolItem[] coolItemArray = new CoolItem[this.items.length + 4];
            System.arraycopy(this.items, 0, coolItemArray, 0, this.items.length);
            this.items = coolItemArray;
        }
        long l2 = OS.GetProcessHeap();
        long l3 = OS.HeapAlloc(l2, 8, 2);
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 261;
        rEBARBANDINFO.fStyle = 192;
        if ((coolItem.style & 4) != 0) {
            REBARBANDINFO rEBARBANDINFO2 = rEBARBANDINFO;
            rEBARBANDINFO2.fStyle |= 0x200;
        }
        rEBARBANDINFO.lpText = l3;
        rEBARBANDINFO.wID = n2;
        int n4 = this.getLastIndexOfRow(n - 1);
        boolean bl2 = bl = n == n4 + 1;
        if (bl) {
            REBARBANDINFO rEBARBANDINFO3 = rEBARBANDINFO;
            rEBARBANDINFO3.fMask |= 0x40;
            rEBARBANDINFO.cx = Short.MAX_VALUE;
        }
        if (n == 0 && n3 > 0) {
            this.getItem(0).setWrap(false);
        }
        if (OS.SendMessage(this.handle, 1034, (long)n, rEBARBANDINFO) == 0L) {
            this.error(14);
        }
        if (bl) {
            this.resizeToPreferredWidth(n4);
        }
        OS.HeapFree(l2, 0, l3);
        coolItem.id = n2;
        this.items[coolItem.id] = coolItem;
        int n5 = this.originalItems.length;
        CoolItem[] coolItemArray = new CoolItem[n5 + 1];
        System.arraycopy(this.originalItems, 0, coolItemArray, 0, n);
        System.arraycopy(this.originalItems, n, coolItemArray, n + 1, n5 - n);
        coolItemArray[n] = coolItem;
        this.originalItems = coolItemArray;
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.items = new CoolItem[4];
        this.originalItems = new CoolItem[0];
    }

    void destroyItem(CoolItem coolItem) {
        Control control;
        int n;
        int n2 = (int)OS.SendMessage(this.handle, 1040, (long)coolItem.id, 0L);
        int n3 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        if (n3 != 0 && n2 == (n = this.getLastIndexOfRow(n2))) {
            this.resizeToMaximumWidth(n - 1);
        }
        boolean bl = (control = coolItem.control) != null && !control.isDisposed() && control.getVisible();
        CoolItem coolItem2 = null;
        if (coolItem.getWrap() && n2 + 1 < n3) {
            coolItem2 = this.getItem(n2 + 1);
            boolean bl2 = this.ignoreResize = !coolItem2.getWrap();
        }
        if (OS.SendMessage(this.handle, 1026, (long)n2, 0L) == 0L) {
            this.error(15);
        }
        this.items[coolItem.id] = null;
        coolItem.id = -1;
        if (this.ignoreResize) {
            coolItem2.setWrap(true);
            this.ignoreResize = false;
        }
        if (bl) {
            control.setVisible(true);
        }
        for (n2 = 0; n2 < this.originalItems.length && this.originalItems[n2] != coolItem; ++n2) {
        }
        int n4 = this.originalItems.length - 1;
        CoolItem[] coolItemArray = new CoolItem[n4];
        System.arraycopy(this.originalItems, 0, coolItemArray, 0, n2);
        System.arraycopy(this.originalItems, n2 + 1, coolItemArray, n2, n4 - n2);
        this.originalItems = coolItemArray;
    }

    @Override
    void drawThemeBackground(long l2, long l3, RECT rECT) {
        Object object;
        if (OS.IsAppThemed() && this.background == -1 && (this.style & 0x800000) != 0 && (object = this.findBackgroundControl()) != null && ((Control)object).backgroundImage != null) {
            this.fillBackground(l2, ((Control)object).getBackgroundPixel(), rECT);
            return;
        }
        object = new RECT();
        OS.GetClientRect(this.handle, (RECT)object);
        OS.MapWindowPoints(this.handle, l3, (RECT)object, 2);
        POINT pOINT = new POINT();
        OS.SetWindowOrgEx(l2, -((RECT)object).left, -((RECT)object).top, pOINT);
        OS.SendMessage(this.handle, 791, l2, 12L);
        OS.SetWindowOrgEx(l2, pOINT.x, pOINT.y, null);
    }

    @Override
    Control findThemeControl() {
        if ((this.style & 0x800000) != 0) {
            return this;
        }
        return this.background == -1 && this.backgroundImage == null ? this : super.findThemeControl();
    }

    int getMargin(int n) {
        int n2 = 0;
        MARGINS mARGINS = new MARGINS();
        OS.SendMessage(this.handle, 1064, 0L, mARGINS);
        n2 += mARGINS.cxLeftWidth + mARGINS.cxRightWidth;
        RECT rECT = new RECT();
        OS.SendMessage(this.handle, 1058, (long)n, rECT);
        n2 = (this.style & 0x800000) != 0 ? ((this.style & 0x200) != 0 ? (n2 += rECT.top + 4) : (n2 += rECT.left + 4)) : ((this.style & 0x200) != 0 ? (n2 += rECT.top + rECT.bottom) : (n2 += rECT.left + rECT.right));
        if ((this.style & 0x800000) == 0 && this == n) {
            n2 += 2;
        }
        return n2;
    }

    public CoolItem getItem(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        if (0 > n || n >= n2) {
            this.error(6);
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 256;
        OS.SendMessage(this.handle, 1052, (long)n, rEBARBANDINFO);
        return this.items[rEBARBANDINFO.wID];
    }

    public int getItemCount() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
    }

    public int[] getItemOrder() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        int[] nArray = new int[n];
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 256;
        for (int i = 0; i < n; ++i) {
            int n2;
            OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
            CoolItem coolItem = this.items[rEBARBANDINFO.wID];
            for (n2 = 0; n2 < this.originalItems.length && this.originalItems[n2] != coolItem; ++n2) {
            }
            if (n2 == this.originalItems.length) {
                this.error(8);
            }
            nArray[i] = n2;
        }
        return nArray;
    }

    public CoolItem[] getItems() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        CoolItem[] coolItemArray = new CoolItem[n];
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 256;
        for (int i = 0; i < n; ++i) {
            OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
            coolItemArray[i] = this.items[rEBARBANDINFO.wID];
        }
        return coolItemArray;
    }

    public Point[] getItemSizes() {
        this.checkWidget();
        Point[] pointArray = this.getItemSizesInPixels();
        if (pointArray != null) {
            for (int i = 0; i < pointArray.length; ++i) {
                pointArray[i] = DPIUtil.autoScaleDown(pointArray[i]);
            }
        }
        return pointArray;
    }

    Point[] getItemSizesInPixels() {
        int n = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        Point[] pointArray = new Point[n];
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 32;
        int n2 = (this.style & 0x800000) == 0 ? 2 : 0;
        MARGINS mARGINS = new MARGINS();
        for (int i = 0; i < n; ++i) {
            RECT rECT = new RECT();
            OS.SendMessage(this.handle, 1033, (long)i, rECT);
            OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
            OS.SendMessage(this.handle, 1064, 0L, mARGINS);
            RECT rECT2 = rECT;
            rECT2.left -= mARGINS.cxLeftWidth;
            RECT rECT3 = rECT;
            rECT3.right += mARGINS.cxRightWidth;
            if (this == i) {
                RECT rECT4 = rECT;
                rECT4.right += n2;
            }
            pointArray[i] = (this.style & 0x200) != 0 ? new Point(rEBARBANDINFO.cyChild, rECT.right - rECT.left) : new Point(rECT.right - rECT.left, rEBARBANDINFO.cyChild);
        }
        return pointArray;
    }

    int getLastIndexOfRow(int n) {
        int n2 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        if (n2 == 0) {
            return -1;
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 1;
        for (int i = n + 1; i < n2; ++i) {
            OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
            if ((rEBARBANDINFO.fStyle & 1) == 0) continue;
            return i - 1;
        }
        return n2 - 1;
    }

    public boolean getLocked() {
        this.checkWidget();
        return this.locked;
    }

    public int[] getWrapIndices() {
        this.checkWidget();
        CoolItem[] coolItemArray = this.getItems();
        int[] nArray = new int[coolItemArray.length];
        int n = 0;
        for (int i = 0; i < coolItemArray.length; ++i) {
            if (!coolItemArray[i].getWrap()) continue;
            nArray[n++] = i;
        }
        int[] nArray2 = new int[n];
        System.arraycopy(nArray, 0, nArray2, 0, n);
        return nArray2;
    }

    public int indexOf(CoolItem coolItem) {
        this.checkWidget();
        if (coolItem == null) {
            this.error(4);
        }
        if (coolItem.isDisposed()) {
            this.error(5);
        }
        return (int)OS.SendMessage(this.handle, 1040, (long)coolItem.id, 0L);
    }

    void resizeToPreferredWidth(int n) {
        int n2 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        if (0 <= n && n < n2) {
            REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
            rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
            rEBARBANDINFO.fMask = 512;
            OS.SendMessage(this.handle, 1052, (long)n, rEBARBANDINFO);
            RECT rECT = new RECT();
            OS.SendMessage(this.handle, 1058, (long)n, rECT);
            rEBARBANDINFO.cx = rEBARBANDINFO.cxIdeal + rECT.left;
            if ((this.style & 0x800000) == 0) {
                REBARBANDINFO rEBARBANDINFO2 = rEBARBANDINFO;
                rEBARBANDINFO2.cx += rECT.right;
            }
            rEBARBANDINFO.fMask = 64;
            OS.SendMessage(this.handle, 1035, (long)n, rEBARBANDINFO);
        }
    }

    void resizeToMaximumWidth(int n) {
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 64;
        rEBARBANDINFO.cx = Short.MAX_VALUE;
        OS.SendMessage(this.handle, 1035, (long)n, rEBARBANDINFO);
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            for (CoolItem coolItem : this.items) {
                if (coolItem == null || coolItem.isDisposed()) continue;
                coolItem.release(false);
            }
            this.items = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void removeControl(Control control) {
        super.removeControl(control);
        for (CoolItem coolItem : this.items) {
            if (coolItem == null || coolItem.control != control) continue;
            coolItem.setControl(null);
        }
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            for (CoolItem coolItem : this.items) {
                if (coolItem == null) continue;
                coolItem.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    @Override
    void setBackgroundPixel(int n) {
        if (n == -1) {
            n = this.defaultBackground();
        }
        OS.SendMessage(this.handle, 1043, 0L, n);
        this.setItemColors((int)OS.SendMessage(this.handle, 1046, 0L, 0L), n);
        if (!OS.IsWindowVisible(this.handle)) {
            return;
        }
        int n2 = 1157;
        OS.RedrawWindow(this.handle, null, 0L, 1157);
    }

    @Override
    void setForegroundPixel(int n) {
        if (n == -1) {
            n = this.defaultForeground();
        }
        OS.SendMessage(this.handle, 1045, 0L, n);
        this.setItemColors(n, (int)OS.SendMessage(this.handle, 1044, 0L, 0L));
    }

    void setItemColors(int n, int n2) {
        int n3 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 2;
        rEBARBANDINFO.clrFore = n;
        rEBARBANDINFO.clrBack = n2;
        for (int i = 0; i < n3; ++i) {
            OS.SendMessage(this.handle, 1035, (long)i, rEBARBANDINFO);
        }
    }

    public void setItemLayout(int[] nArray, int[] nArray2, Point[] pointArray) {
        this.checkWidget();
        if (pointArray == null) {
            this.error(4);
        }
        Point[] pointArray2 = new Point[pointArray.length];
        for (int i = 0; i < pointArray.length; ++i) {
            pointArray2[i] = DPIUtil.autoScaleUp(pointArray[i]);
        }
        this.setItemLayoutInPixels(nArray, nArray2, pointArray2);
    }

    void setItemLayoutInPixels(int[] nArray, int[] nArray2, Point[] pointArray) {
        this.setRedraw(false);
        this.setItemOrder(nArray);
        this.setWrapIndices(nArray2);
        this.setItemSizes(pointArray);
        this.setRedraw(true);
    }

    void setItemOrder(int[] nArray) {
        int n;
        int n2;
        int n3;
        if (nArray == null) {
            this.error(4);
        }
        if (nArray.length != (n3 = (int)OS.SendMessage(this.handle, 1036, 0L, 0L))) {
            this.error(5);
        }
        boolean[] blArray = new boolean[n3];
        int[] object = nArray;
        int n4 = object.length;
        for (n2 = 0; n2 < n4; ++n2) {
            n = object[n2];
            if (n < 0 || n >= n3) {
                this.error(6);
            }
            if (blArray[n]) {
                this.error(5);
            }
            blArray[n] = true;
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        for (n4 = 0; n4 < nArray.length; ++n4) {
            n2 = this.originalItems[nArray[n4]].id;
            n = (int)OS.SendMessage(this.handle, 1040, (long)n2, 0L);
            if (n == n4) continue;
            int n5 = this.getLastIndexOfRow(n);
            int n6 = this.getLastIndexOfRow(n4);
            if (n == n5) {
                this.resizeToPreferredWidth(n);
            }
            if (n4 == n6) {
                this.resizeToPreferredWidth(n4);
            }
            OS.SendMessage(this.handle, 1063, (long)n, n4);
            if (n == n5 && n - 1 >= 0) {
                this.resizeToMaximumWidth(n - 1);
            }
            if (n4 != n6) continue;
            this.resizeToMaximumWidth(n4);
        }
    }

    void setItemSizes(Point[] pointArray) {
        int n;
        if (pointArray == null) {
            this.error(4);
        }
        if (pointArray.length != (n = (int)OS.SendMessage(this.handle, 1036, 0L, 0L))) {
            this.error(5);
        }
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 256;
        for (int i = 0; i < n; ++i) {
            OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
            this.items[rEBARBANDINFO.wID].setSizeInPixels(pointArray[i].x, pointArray[i].y);
        }
    }

    public void setLocked(boolean bl) {
        this.checkWidget();
        this.locked = bl;
        int n = (int)OS.SendMessage(this.handle, 1036, 0L, 0L);
        REBARBANDINFO rEBARBANDINFO = new REBARBANDINFO();
        rEBARBANDINFO.cbSize = REBARBANDINFO.sizeof;
        rEBARBANDINFO.fMask = 1;
        for (int i = 0; i < n; ++i) {
            REBARBANDINFO rEBARBANDINFO2;
            OS.SendMessage(this.handle, 1052, (long)i, rEBARBANDINFO);
            if (bl) {
                rEBARBANDINFO2 = rEBARBANDINFO;
                rEBARBANDINFO2.fStyle |= 0x100;
            } else {
                rEBARBANDINFO2 = rEBARBANDINFO;
                rEBARBANDINFO2.fStyle &= 0xFFFFFEFF;
            }
            OS.SendMessage(this.handle, 1035, (long)i, rEBARBANDINFO);
        }
    }

    public void setWrapIndices(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            nArray = new int[]{};
        }
        int n = this.getItemCount();
        int[] objectArray = nArray;
        int n2 = objectArray.length;
        for (int n3 = 0; n3 < n2; ++n3) {
            int n4 = objectArray[n3];
            if (n4 >= 0 && n4 < n) continue;
            this.error(6);
        }
        this.setRedraw(false);
        CoolItem[] coolItemArray = this.getItems();
        for (n2 = 0; n2 < coolItemArray.length; ++n2) {
            CoolItem coolItem = coolItemArray[n2];
            if (!coolItem.getWrap()) continue;
            this.resizeToPreferredWidth(n2 - 1);
            coolItem.setWrap(false);
        }
        this.resizeToMaximumWidth(n - 1);
        for (int n5 : nArray) {
            if (0 > n5 || n5 >= coolItemArray.length) continue;
            CoolItem coolItem = coolItemArray[n5];
            coolItem.setWrap(true);
            this.resizeToMaximumWidth(n5 - 1);
        }
        this.setRedraw(true);
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 0x40 | 4;
        n |= 0x8200;
        if ((this.style & 0x800000) == 0) {
            n |= 0x400;
        }
        return n;
    }

    @Override
    TCHAR windowClass() {
        return ReBarClass;
    }

    @Override
    long windowProc() {
        return ReBarProc;
    }

    @Override
    LRESULT WM_COMMAND(long l2, long l3) {
        LRESULT lRESULT = super.WM_COMMAND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_ERASEBKGND(long l2, long l3) {
        LRESULT lRESULT = super.WM_ERASEBKGND(l2, l3);
        if (!OS.IsAppThemed()) {
            this.drawBackground(l2);
            return null;
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_NOTIFY(long l2, long l3) {
        LRESULT lRESULT = super.WM_NOTIFY(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_SETREDRAW(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETREDRAW(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        if (!this.ignoreResize) {
            return super.WM_SIZE(l2, l3);
        }
        long l4 = this.callWindowProc(this.handle, 5, l2, l3);
        if (l4 == 0L) {
            return LRESULT.ZERO;
        }
        return new LRESULT(l4);
    }

    @Override
    LRESULT wmNotifyChild(NMHDR nMHDR, long l2, long l3) {
        block0 : switch (nMHDR.code) {
            case -835: {
                int n;
                int n2 = OS.GetMessagePos();
                POINT pOINT = new POINT();
                OS.POINTSTOPOINT(pOINT, n2);
                OS.ScreenToClient(this.handle, pOINT);
                int n3 = n = this.display.lastButton != 0 ? this.display.lastButton : 1;
                if (this.sendDragEvent(n, pOINT.x, pOINT.y)) break;
                return LRESULT.ONE;
            }
            case -839: {
                NMREBARCHILDSIZE nMREBARCHILDSIZE = new NMREBARCHILDSIZE();
                OS.MoveMemory(nMREBARCHILDSIZE, l3, NMREBARCHILDSIZE.sizeof);
                if (nMREBARCHILDSIZE.uBand == -1) break;
                CoolItem coolItem = this.items[nMREBARCHILDSIZE.wID];
                Control control = coolItem.control;
                if (control == null) break;
                int n = nMREBARCHILDSIZE.rcChild_right - nMREBARCHILDSIZE.rcChild_left;
                int n4 = nMREBARCHILDSIZE.rcChild_bottom - nMREBARCHILDSIZE.rcChild_top;
                control.setBoundsInPixels(nMREBARCHILDSIZE.rcChild_left, nMREBARCHILDSIZE.rcChild_top, n, n4);
                break;
            }
            case -831: {
                if (this.ignoreResize) break;
                Point point = this.getSizeInPixels();
                int n = this.getBorderWidthInPixels();
                int n5 = (int)OS.SendMessage(this.handle, 1051, 0L, 0L);
                if ((this.style & 0x200) != 0) {
                    this.setSizeInPixels(n5 + 2 * n, point.y);
                    break;
                }
                this.setSizeInPixels(point.x, n5 + 2 * n);
                break;
            }
            case -841: {
                NMREBARCHEVRON nMREBARCHEVRON = new NMREBARCHEVRON();
                OS.MoveMemory(nMREBARCHEVRON, l3, NMREBARCHEVRON.sizeof);
                CoolItem coolItem = this.items[nMREBARCHEVRON.wID];
                if (coolItem == null) break;
                Event event = new Event();
                event.detail = 4;
                if ((this.style & 0x200) != 0) {
                    event.setLocationInPixels(nMREBARCHEVRON.right, nMREBARCHEVRON.top);
                } else {
                    event.setLocationInPixels(nMREBARCHEVRON.left, nMREBARCHEVRON.bottom);
                }
                coolItem.sendSelectionEvent(13, event, false);
                break;
            }
            case -12: {
                if (this.findBackgroundControl() == null && (this.style & 0x800000) == 0) break;
                NMCUSTOMDRAW nMCUSTOMDRAW = new NMCUSTOMDRAW();
                OS.MoveMemory(nMCUSTOMDRAW, l3, NMCUSTOMDRAW.sizeof);
                switch (nMCUSTOMDRAW.dwDrawStage) {
                    case 3: {
                        return new LRESULT(68L);
                    }
                    case 4: {
                        this.drawBackground(nMCUSTOMDRAW.hdc);
                        break block0;
                    }
                }
                break;
            }
        }
        return super.wmNotifyChild(nMHDR, l2, l3);
    }

    static {
        ReBarClass = new TCHAR(0, "ReBarWindow32", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, ReBarClass, wNDCLASS);
        ReBarProc = wNDCLASS.lpfnWndProc;
    }
}

