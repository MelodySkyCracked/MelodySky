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
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class TreeColumn
extends Item {
    Tree parent;
    boolean resizable = true;
    boolean moveable;
    String toolTipText;
    int id;

    public TreeColumn(Tree tree, int n) {
        super(tree, TreeColumn.checkStyle(n));
        this.parent = tree;
        this.parent.createItem(this, tree.getColumnCount());
    }

    public TreeColumn(Tree tree, int n, int n2) {
        super(tree, TreeColumn.checkStyle(n));
        this.parent = tree;
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

    public boolean getMoveable() {
        this.checkWidget();
        return this.moveable;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public Tree getParent() {
        this.checkWidget();
        return this.parent;
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
        long l2 = this.parent.hwndHeader;
        if (l2 == 0L) {
            return 0;
        }
        HDITEM hDITEM = new HDITEM();
        hDITEM.mask = 1;
        OS.SendMessage(l2, 4619, (long)n, hDITEM);
        return hDITEM.cxy;
    }

    public void pack() {
        Object object;
        int n;
        RECT rECT;
        this.checkWidget();
        int n2 = this.parent.indexOf(this);
        if (n2 == -1) {
            return;
        }
        int n3 = 0;
        long l2 = this.parent.handle;
        long l3 = this.parent.hwndHeader;
        RECT rECT2 = new RECT();
        OS.SendMessage(l3, 4615, (long)n2, rECT2);
        long l4 = OS.GetDC(l2);
        long l5 = 0L;
        long l6 = OS.SendMessage(l2, 49, 0L, 0L);
        if (l6 != 0L) {
            l5 = OS.SelectObject(l4, l6);
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 28;
        tVITEM.hItem = OS.SendMessage(l2, 4362, 0L, 0L);
        while (tVITEM.hItem != 0L) {
            OS.SendMessage(l2, 4414, 0L, tVITEM);
            RECT rECT3 = rECT = tVITEM.lParam != -1L ? this.parent.items[(int)tVITEM.lParam] : null;
            if (rECT != null) {
                n = 0;
                if (this.parent.hooks(41)) {
                    int n4 = (tVITEM.state & 2) != 0 ? 2 : 0;
                    Event event = this.parent.sendMeasureItemEvent((TreeItem)((Object)rECT), n2, l4, n4);
                    if (this.isDisposed() || this.parent.isDisposed()) break;
                    object = event.getBoundsInPixels();
                    n = ((Rectangle)object).x + ((Rectangle)object).width;
                } else {
                    long l7 = ((TreeItem)((Object)rECT)).fontHandle(n2);
                    if (l7 != -1L) {
                        l7 = OS.SelectObject(l4, l7);
                    }
                    object = ((TreeItem)((Object)rECT)).getBounds(n2, true, true, false, false, false, l4);
                    if (l7 != -1L) {
                        OS.SelectObject(l4, l7);
                    }
                    n = ((RECT)object).right;
                }
                n3 = Math.max(n3, n - rECT2.left);
            }
            tVITEM.hItem = OS.SendMessage(l2, 4362, 6L, tVITEM.hItem);
        }
        rECT = new RECT();
        n = 3072;
        char[] cArray = this.text.toCharArray();
        OS.DrawText(l4, cArray, cArray.length, rECT, 3072);
        int n5 = rECT.right - rECT.left + 12;
        if (OS.IsAppThemed()) {
            n5 += 3;
        }
        if (this.image != null || this.parent.sortColumn == this) {
            object = null;
            if (this.parent.sortColumn == this && this.parent.sortDirection != 0) {
                n5 += 10;
            } else {
                object = this.image;
            }
            if (object != null) {
                Rectangle rectangle = ((Image)object).getBoundsInPixels();
                n5 += rectangle.width;
            }
            int n6 = 0;
            n6 = l3 != 0L ? (int)OS.SendMessage(l3, 4629, 0L, 0L) : OS.GetSystemMetrics(45) * 3;
            n5 += n6 * 2;
        }
        if (l6 != 0L) {
            OS.SelectObject(l4, l5);
        }
        OS.ReleaseDC(l2, l4);
        int n7 = this.parent.linesVisible ? 1 : 0;
        this.setWidthInPixels(Math.max(n5, n3 + n7));
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
        HDITEM hDITEM;
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
        long l2 = this.parent.hwndHeader;
        if (l2 == 0L) {
            return;
        }
        HDITEM hDITEM2 = new HDITEM();
        hDITEM2.mask = 4;
        OS.SendMessage(l2, 4619, (long)n2, hDITEM2);
        HDITEM hDITEM3 = hDITEM2;
        hDITEM3.fmt &= 0xFFFFFFFC;
        if ((this.style & 0x4000) == 16384) {
            hDITEM = hDITEM2;
            hDITEM.fmt |= 0;
        }
        if ((this.style & 0x1000000) == 0x1000000) {
            hDITEM = hDITEM2;
            hDITEM.fmt |= 2;
        }
        if ((this.style & 0x20000) == 131072) {
            hDITEM = hDITEM2;
            hDITEM.fmt |= 1;
        }
        OS.SendMessage(l2, 4620, (long)n2, hDITEM2);
        if (n2 != 0) {
            long l3 = this.parent.handle;
            this.parent.forceResize();
            RECT rECT = new RECT();
            RECT rECT2 = new RECT();
            OS.GetClientRect(l3, rECT);
            OS.SendMessage(l2, 4615, (long)n2, rECT2);
            rECT.left = rECT2.left;
            rECT.right = rECT2.right;
            OS.InvalidateRect(l3, rECT, true);
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
        long l2 = this.parent.hwndHeader;
        if (l2 == 0L) {
            return;
        }
        HDITEM hDITEM = new HDITEM();
        hDITEM.mask = 52;
        OS.SendMessage(l2, 4619, (long)n, hDITEM);
        HDITEM hDITEM2 = hDITEM;
        hDITEM2.fmt &= 0xFFFFEFFF;
        if (image != null) {
            HDITEM hDITEM3;
            if (bl) {
                hDITEM3 = hDITEM;
                hDITEM3.mask &= 0xFFFFFFDF;
                HDITEM hDITEM4 = hDITEM;
                hDITEM4.fmt &= 0xFFFFF7FF;
                HDITEM hDITEM5 = hDITEM;
                hDITEM5.fmt |= 0x2000;
                hDITEM.hbm = image.handle;
            } else {
                hDITEM3 = hDITEM;
                hDITEM3.mask &= 0xFFFFFFEF;
                HDITEM hDITEM6 = hDITEM;
                hDITEM6.fmt &= 0xFFFFDFFF;
                HDITEM hDITEM7 = hDITEM;
                hDITEM7.fmt |= 0x800;
                hDITEM.iImage = this.parent.imageIndexHeader(image);
            }
            if (bl2) {
                hDITEM3 = hDITEM;
                hDITEM3.fmt |= 0x1000;
            }
        } else {
            HDITEM hDITEM8 = hDITEM;
            hDITEM8.mask &= 0xFFFFFFCF;
            HDITEM hDITEM9 = hDITEM;
            hDITEM9.fmt &= 0xFFFFD7FF;
        }
        OS.SendMessage(l2, 4620, (long)n, hDITEM);
    }

    public void setMoveable(boolean bl) {
        this.checkWidget();
        this.moveable = bl;
    }

    public void setResizable(boolean bl) {
        this.checkWidget();
        this.resizable = bl;
    }

    void setSortDirection(int n) {
        long l2 = this.parent.hwndHeader;
        if (l2 != 0L) {
            Object object;
            int n2 = this.parent.indexOf(this);
            if (n2 == -1) {
                return;
            }
            HDITEM hDITEM = new HDITEM();
            hDITEM.mask = 36;
            OS.SendMessage(l2, 4619, (long)n2, hDITEM);
            switch (n) {
                case 128: {
                    HDITEM hDITEM2 = hDITEM;
                    hDITEM2.fmt &= 0xFFFFF5FF;
                    HDITEM hDITEM3 = hDITEM;
                    hDITEM3.fmt |= 0x400;
                    if (this.image != null) break;
                    object = hDITEM;
                    ((HDITEM)object).mask &= 0xFFFFFFDF;
                    break;
                }
                case 1024: {
                    HDITEM hDITEM2 = hDITEM;
                    hDITEM2.fmt &= 0xFFFFF3FF;
                    HDITEM hDITEM4 = hDITEM;
                    hDITEM4.fmt |= 0x200;
                    if (this.image != null) break;
                    object = hDITEM;
                    ((HDITEM)object).mask &= 0xFFFFFFDF;
                    break;
                }
                case 0: {
                    HDITEM hDITEM2 = hDITEM;
                    hDITEM2.fmt &= 0xFFFFF9FF;
                    if (this.image != null) {
                        HDITEM hDITEM5 = hDITEM;
                        hDITEM5.fmt |= 0x800;
                        hDITEM.iImage = this.parent.imageIndexHeader(this.image);
                        break;
                    }
                    HDITEM hDITEM6 = hDITEM;
                    hDITEM6.fmt &= 0xFFFFF7FF;
                    object = hDITEM;
                    ((HDITEM)object).mask &= 0xFFFFFFDF;
                    break;
                }
            }
            OS.SendMessage(l2, 4620, (long)n2, hDITEM);
            if (OS.IsAppThemed()) {
                long l3 = this.parent.handle;
                this.parent.forceResize();
                object = new RECT();
                RECT rECT = new RECT();
                OS.GetClientRect(l3, (RECT)object);
                OS.SendMessage(l2, 4615, (long)n2, rECT);
                ((RECT)object).left = rECT.left;
                ((RECT)object).right = rECT.right;
                OS.InvalidateRect(l3, (RECT)object, true);
            }
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
        long l2 = OS.GetProcessHeap();
        char[] cArray = this.fixMnemonic(string);
        int n2 = cArray.length * 2;
        long l3 = OS.HeapAlloc(l2, 8, n2);
        OS.MoveMemory(l3, cArray, n2);
        long l4 = this.parent.hwndHeader;
        if (l4 == 0L) {
            return;
        }
        HDITEM hDITEM = new HDITEM();
        hDITEM.mask = 2;
        hDITEM.pszText = l3;
        long l5 = OS.SendMessage(l4, 4620, (long)n, hDITEM);
        if (l3 != 0L) {
            OS.HeapFree(l2, 0, l3);
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
        long l2 = this.parent.hwndHeader;
        if (l2 == 0L) {
            return;
        }
        HDITEM hDITEM = new HDITEM();
        hDITEM.mask = 1;
        hDITEM.cxy = n;
        OS.SendMessage(l2, 4620, (long)n2, hDITEM);
        RECT rECT = new RECT();
        OS.SendMessage(l2, 4615, (long)n2, rECT);
        this.parent.forceResize();
        long l3 = this.parent.handle;
        RECT rECT2 = new RECT();
        OS.GetClientRect(l3, rECT2);
        rECT2.left = rECT.left;
        OS.InvalidateRect(l3, rECT2, true);
        this.parent.setScrollWidth();
    }

    void updateToolTip(int n) {
        RECT rECT;
        long l2;
        long l3 = this.parent.headerToolTipHandle;
        if (l3 != 0L && OS.SendMessage(l2 = this.parent.hwndHeader, 4615, (long)n, rECT = new RECT()) != 0L) {
            TOOLINFO tOOLINFO = new TOOLINFO();
            tOOLINFO.cbSize = TOOLINFO.sizeof;
            tOOLINFO.hwnd = l2;
            tOOLINFO.uId = this.id;
            tOOLINFO.left = rECT.left;
            tOOLINFO.top = rECT.top;
            tOOLINFO.right = rECT.right;
            tOOLINFO.bottom = rECT.bottom;
            OS.SendMessage(l3, 1076, 0L, tOOLINFO);
        }
    }
}

