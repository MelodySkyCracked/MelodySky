/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.LVCOLUMN;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class TableColumn
extends Item {
    Table parent;
    boolean resizable = true;
    boolean moveable;
    String toolTipText;
    int id;

    public TableColumn(Table table, int n) {
        super(table, TableColumn.checkStyle(n));
        this.parent = table;
        this.parent.createItem(this, table.getColumnCount());
    }

    public TableColumn(Table table, int n, int n2) {
        super(table, TableColumn.checkStyle(n));
        this.parent = table;
        this.parent.createItem(this, n2);
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
        return Widget.checkBits(n, 16384, 0x1000000, 131072, 0, 0, 0);
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    public int getAlignment() {
        this.checkWidget();
        if ((this.style & 0x4000) != 0) {
            return 16384;
        }
        if ((this.style & 0x1000000) != 0) {
            return 0x1000000;
        }
        if ((this.style & 0x20000) != 0) {
            return 131072;
        }
        return 16384;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public Table getParent() {
        this.checkWidget();
        return this.parent;
    }

    public boolean getMoveable() {
        this.checkWidget();
        return this.moveable;
    }

    public boolean getResizable() {
        this.checkWidget();
        return this.resizable;
    }

    public String getToolTipText() {
        this.checkWidget();
        return this.toolTipText;
    }

    public int getWidth() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getWidthInPixels());
    }

    int getWidthInPixels() {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return 0;
        }
        long l2 = this.parent.handle;
        return (int)OS.SendMessage(l2, 4125, (long)n, 0L);
    }

    private int calcAutoWidth(int n, boolean bl) {
        int n2;
        int n3;
        boolean bl2;
        boolean bl3;
        long l2 = this.parent.handle;
        int n4 = OS.GetWindowLong(l2, -16);
        boolean bl4 = (n4 & 0x10000000) != 0;
        boolean bl5 = this.parent.getDrawing();
        boolean bl6 = bl3 = bl4 && bl5;
        if (bl3) {
            OS.SendMessage(l2, 11, 0L, 0L);
        }
        int n5 = (int)OS.SendMessage(l2, 4125, (long)n, 0L);
        RECT rECT = null;
        boolean bl7 = bl2 = n == this.parent.getColumnCount() - 1;
        if (bl2) {
            rECT = new RECT();
            OS.GetWindowRect(l2, rECT);
            OS.UpdateWindow(l2);
            n3 = 30;
            OS.SetWindowPos(l2, 0L, 0, 0, 0, rECT.bottom - rECT.top, 30);
        }
        n3 = bl ? -2 : -1;
        OS.SendMessage(l2, 4126, (long)n, n3);
        if (bl2) {
            n2 = 22;
            OS.SetWindowPos(l2, 0L, 0, 0, rECT.right - rECT.left, rECT.bottom - rECT.top, 22);
        }
        n2 = (int)OS.SendMessage(l2, 4125, (long)n, 0L);
        OS.SendMessage(l2, 4126, (long)n, n5);
        int n6 = n2;
        if (bl3) {
            OS.SendMessage(l2, 11, 1L, 0L);
        }
        return n6;
    }

    public void pack() {
        this.checkWidget();
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return;
        }
        long l2 = this.parent.handle;
        int n2 = (int)OS.SendMessage(l2, 4125, (long)n, 0L);
        TCHAR tCHAR = new TCHAR(this.parent.getCodePage(), this.text, true);
        int n3 = (int)OS.SendMessage(l2, 4183, 0L, tCHAR) + 12;
        if (OS.IsAppThemed()) {
            n3 += 3;
        }
        boolean bl = false;
        if (this.image != null || this.parent.sortColumn == this) {
            bl = true;
            if (this.parent.sortColumn == this && this.parent.sortDirection != 0) {
                n3 += 10;
            } else if (this.image != null) {
                Rectangle rectangle = this.image.getBoundsInPixels();
                n3 += rectangle.width;
            }
            long l3 = OS.SendMessage(l2, 4127, 0L, 0L);
            int n4 = (int)OS.SendMessage(l3, 4629, 0L, 0L);
            n3 += n4 * 4;
        }
        this.parent.ignoreColumnResize = true;
        int n5 = 0;
        if (this.parent.hooks(41)) {
            RECT rECT = new RECT();
            long l4 = OS.SendMessage(l2, 4127, 0L, 0L);
            OS.SendMessage(l4, 4615, (long)n, rECT);
            OS.MapWindowPoints(l4, l2, rECT, 2);
            long l5 = OS.GetDC(l2);
            long l6 = 0L;
            long l7 = OS.SendMessage(l2, 49, 0L, 0L);
            if (l7 != 0L) {
                l6 = OS.SelectObject(l5, l7);
            }
            int n6 = (int)OS.SendMessage(l2, 4100, 0L, 0L);
            for (int i = 0; i < n6; ++i) {
                TableItem tableItem = this.parent._getItem(i, false);
                if (tableItem == null) continue;
                long l8 = tableItem.fontHandle(n);
                if (l8 != -1L) {
                    l8 = OS.SelectObject(l5, l8);
                }
                Event event = this.parent.sendMeasureItemEvent(tableItem, i, n, l5);
                if (l8 != -1L) {
                    l8 = OS.SelectObject(l5, l8);
                }
                if (this.isDisposed() || this.parent.isDisposed()) break;
                Rectangle rectangle = event.getBoundsInPixels();
                n5 = Math.max(n5, rectangle.x + rectangle.width - rECT.left);
            }
            if (l7 != 0L) {
                OS.SelectObject(l5, l6);
            }
            OS.ReleaseDC(l2, l5);
        } else {
            n5 = this.calcAutoWidth(n, false);
            if (n == 0) {
                long l9;
                if (this.parent.imageList == null) {
                    n5 += 2;
                }
                if ((this.parent.style & 0x20) != 0 && (l9 = OS.SendMessage(l2, 4098, 2L, 0L)) != 0L) {
                    int[] nArray = new int[]{0};
                    int[] nArray2 = new int[]{0};
                    OS.ImageList_GetIconSize(l9, nArray, nArray2);
                    n5 += nArray[0];
                }
            }
        }
        if (n3 > n5) {
            n5 = !bl ? this.calcAutoWidth(n, true) : n3;
        }
        OS.SendMessage(l2, 4126, (long)n, n5);
        this.parent.ignoreColumnResize = false;
        if (n2 != n5) {
            this.updateToolTip(n);
            this.sendEvent(11);
            if (this.isDisposed()) {
                return;
            }
            boolean bl2 = false;
            TableColumn[] tableColumnArray = this.parent.getColumns();
            for (int n7 : this.parent.getColumnOrder()) {
                TableColumn tableColumn = tableColumnArray[n7];
                if (bl2 && !tableColumn.isDisposed()) {
                    tableColumn.updateToolTip(n7);
                    tableColumn.sendEvent(10);
                }
                if (tableColumn != this) continue;
                bl2 = true;
            }
        }
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this.parent.sortColumn == this) {
            this.parent.sortColumn = null;
        }
    }

    public void removeControlListener(ControlListener controlListener) {
        this.checkWidget();
        if (controlListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(10, controlListener);
        this.eventTable.unhook(11, controlListener);
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

    public void setAlignment(int n) {
        this.checkWidget();
        if ((n & 0x1024000) == 0) {
            return;
        }
        int n2 = this.parent.indexOf(this);
        if (n2 == -1 || n2 == 0) {
            return;
        }
        this.style &= 0xFEFDBFFF;
        this.style |= n & 0x1024000;
        long l2 = this.parent.handle;
        LVCOLUMN lVCOLUMN = new LVCOLUMN();
        lVCOLUMN.mask = 1;
        OS.SendMessage(l2, 4191, (long)n2, lVCOLUMN);
        LVCOLUMN lVCOLUMN2 = lVCOLUMN;
        lVCOLUMN2.fmt &= 0xFFFFFFFC;
        int n3 = 0;
        if ((this.style & 0x4000) == 16384) {
            n3 = 0;
        }
        if ((this.style & 0x1000000) == 0x1000000) {
            n3 = 2;
        }
        if ((this.style & 0x20000) == 131072) {
            n3 = 1;
        }
        LVCOLUMN lVCOLUMN3 = lVCOLUMN;
        lVCOLUMN3.fmt |= n3;
        OS.SendMessage(l2, 4192, (long)n2, lVCOLUMN);
        if (n2 != 0) {
            this.parent.forceResize();
            RECT rECT = new RECT();
            RECT rECT2 = new RECT();
            OS.GetClientRect(l2, rECT);
            long l3 = OS.SendMessage(l2, 4127, 0L, 0L);
            OS.SendMessage(l3, 4615, (long)n2, rECT2);
            OS.MapWindowPoints(l3, l2, rECT2, 2);
            rECT.left = rECT2.left;
            rECT.right = rECT2.right;
            OS.InvalidateRect(l2, rECT, true);
        }
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        super.setImage(image);
        if (this.parent.sortColumn != this || this.parent.sortDirection != 0) {
            this.setImage(image, false, false);
        }
    }

    void setImage(Image image, boolean bl, boolean bl2) {
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return;
        }
        long l2 = this.parent.handle;
        LVCOLUMN lVCOLUMN = new LVCOLUMN();
        lVCOLUMN.mask = 17;
        OS.SendMessage(l2, 4191, (long)n, lVCOLUMN);
        if (image != null) {
            LVCOLUMN lVCOLUMN2 = lVCOLUMN;
            lVCOLUMN2.fmt |= 0x800;
            lVCOLUMN.iImage = this.parent.imageIndexHeader(image);
            if (bl2) {
                LVCOLUMN lVCOLUMN3 = lVCOLUMN;
                lVCOLUMN3.fmt |= 0x1000;
            }
        } else {
            LVCOLUMN lVCOLUMN4 = lVCOLUMN;
            lVCOLUMN4.mask &= 0xFFFFFFEF;
            LVCOLUMN lVCOLUMN5 = lVCOLUMN;
            lVCOLUMN5.fmt &= 0xFFFFE7FF;
        }
        OS.SendMessage(l2, 4192, (long)n, lVCOLUMN);
    }

    public void setMoveable(boolean bl) {
        this.checkWidget();
        this.moveable = bl;
        this.parent.updateMoveable();
    }

    public void setResizable(boolean bl) {
        this.checkWidget();
        this.resizable = bl;
    }

    void setSortDirection(int n) {
        RECT rECT;
        Object object;
        int n2 = this.parent.indexOf(this);
        if (n2 == -1) {
            return;
        }
        long l2 = this.parent.handle;
        long l3 = OS.SendMessage(l2, 4127, 0L, 0L);
        HDITEM hDITEM = new HDITEM();
        hDITEM.mask = 36;
        OS.SendMessage(l3, 4619, (long)n2, hDITEM);
        switch (n) {
            case 128: {
                object = hDITEM;
                ((HDITEM)object).fmt &= 0xFFFFF5FF;
                HDITEM hDITEM2 = hDITEM;
                hDITEM2.fmt |= 0x400;
                if (this.image != null) break;
                HDITEM hDITEM3 = hDITEM;
                hDITEM3.mask &= 0xFFFFFFDF;
                break;
            }
            case 1024: {
                object = hDITEM;
                ((HDITEM)object).fmt &= 0xFFFFF3FF;
                HDITEM hDITEM2 = hDITEM;
                hDITEM2.fmt |= 0x200;
                if (this.image != null) break;
                HDITEM hDITEM3 = hDITEM;
                hDITEM3.mask &= 0xFFFFFFDF;
                break;
            }
            case 0: {
                HDITEM hDITEM2;
                object = hDITEM;
                ((HDITEM)object).fmt &= 0xFFFFF9FF;
                if (this.image != null) {
                    hDITEM2 = hDITEM;
                    hDITEM2.fmt |= 0x800;
                    hDITEM.iImage = this.parent.imageIndexHeader(this.image);
                    break;
                }
                hDITEM2 = hDITEM;
                hDITEM2.fmt &= 0xFFFFF7FF;
                HDITEM hDITEM3 = hDITEM;
                hDITEM3.mask &= 0xFFFFFFDF;
                break;
            }
        }
        OS.SendMessage(l3, 4620, (long)n2, hDITEM);
        this.parent.forceResize();
        object = new RECT();
        OS.GetClientRect(l2, (RECT)object);
        if ((int)OS.SendMessage(l2, 4096, 0L, 0L) != -1) {
            int n3 = (int)OS.SendMessage(l2, 4270, 0L, 0L);
            int n4 = n == 0 ? -1 : n2;
            OS.SendMessage(l2, 4236, (long)n4, 0L);
            RECT rECT2 = new RECT();
            if (n3 != -1 && OS.SendMessage(l3, 4615, (long)n3, rECT2) != 0L) {
                OS.MapWindowPoints(l3, l2, rECT2, 2);
                ((RECT)object).left = rECT2.left;
                ((RECT)object).right = rECT2.right;
                OS.InvalidateRect(l2, (RECT)object, true);
            }
        }
        if (OS.SendMessage(l3, 4615, (long)n2, rECT = new RECT()) != 0L) {
            OS.MapWindowPoints(l3, l2, rECT, 2);
            ((RECT)object).left = rECT.left;
            ((RECT)object).right = rECT.right;
            OS.InvalidateRect(l2, (RECT)object, true);
        }
    }

    @Override
    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (string.equals(this.text)) {
            return;
        }
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return;
        }
        super.setText(string);
        long l2 = this.parent.handle;
        LVCOLUMN lVCOLUMN = new LVCOLUMN();
        lVCOLUMN.mask = 1;
        OS.SendMessage(l2, 4191, (long)n, lVCOLUMN);
        long l3 = OS.GetProcessHeap();
        char[] cArray = this.fixMnemonic(string);
        int n2 = cArray.length * 2;
        long l4 = OS.HeapAlloc(l3, 8, n2);
        OS.MoveMemory(l4, cArray, n2);
        LVCOLUMN lVCOLUMN2 = lVCOLUMN;
        lVCOLUMN2.mask |= 4;
        lVCOLUMN.pszText = l4;
        long l5 = OS.SendMessage(l2, 4192, (long)n, lVCOLUMN);
        if (l4 != 0L) {
            OS.HeapFree(l3, 0, l4);
        }
        if (l5 == 0L) {
            this.error(13);
        }
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        this.toolTipText = string;
        long l2 = this.parent.headerToolTipHandle;
        if (l2 == 0L) {
            this.parent.createHeaderToolTips();
            this.parent.updateHeaderToolTips();
        }
    }

    public void setWidth(int n) {
        this.checkWidget();
        this.setWidthInPixels(DPIUtil.autoScaleUp(n));
    }

    void setWidthInPixels(int n) {
        if (n < 0) {
            return;
        }
        int n2 = this.parent.indexOf(this);
        if (n2 == -1) {
            return;
        }
        long l2 = this.parent.handle;
        if (n != (int)OS.SendMessage(l2, 4125, (long)n2, 0L)) {
            OS.SendMessage(l2, 4126, (long)n2, n);
        }
    }

    void updateToolTip(int n) {
        RECT rECT;
        long l2;
        long l3;
        long l4 = this.parent.headerToolTipHandle;
        if (l4 != 0L && OS.SendMessage(l3 = OS.SendMessage(l2 = this.parent.handle, 4127, 0L, 0L), 4615, (long)n, rECT = new RECT()) != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.hwnd = l3;
            tOOLINFO.uId = this.id;
            tOOLINFO.left = rECT.left;
            tOOLINFO.top = rECT.top;
            tOOLINFO.right = rECT.right;
            tOOLINFO.bottom = rECT.bottom;
            OS.SendMessage(l4, 1076, 0L, tOOLINFO);
        }
    }
}

