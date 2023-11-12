/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Widget;

public class Tray
extends Widget {
    int itemCount;
    TrayItem[] items = new TrayItem[4];

    Tray(Display display, int n) {
        this.display = display;
        this.reskinWidget();
    }

    void createItem(TrayItem trayItem, int n) {
        if (0 > n || n > this.itemCount) {
            this.error(6);
        }
        if (this.itemCount == this.items.length) {
            TrayItem[] trayItemArray = new TrayItem[this.items.length + 4];
            System.arraycopy(this.items, 0, trayItemArray, 0, this.items.length);
            this.items = trayItemArray;
        }
        System.arraycopy(this.items, n, this.items, n + 1, this.itemCount++ - n);
        this.items[n] = trayItem;
    }

    void destroyItem(TrayItem trayItem) {
        int n;
        for (n = 0; n < this.itemCount && this.items[n] != trayItem; ++n) {
        }
        if (n == this.itemCount) {
            return;
        }
        System.arraycopy(this.items, n + 1, this.items, n, --this.itemCount - n);
        this.items[this.itemCount] = null;
    }

    public TrayItem getItem(int n) {
        this.checkWidget();
        if (0 > n || n >= this.itemCount) {
            this.error(6);
        }
        return this.items[n];
    }

    public int getItemCount() {
        this.checkWidget();
        return this.itemCount;
    }

    public TrayItem[] getItems() {
        this.checkWidget();
        TrayItem[] trayItemArray = new TrayItem[this.itemCount];
        System.arraycopy(this.items, 0, trayItemArray, 0, trayItemArray.length);
        return trayItemArray;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.items != null) {
            for (TrayItem trayItem : this.items) {
                if (trayItem == null || trayItem.isDisposed()) continue;
                trayItem.release(false);
            }
            this.items = null;
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this.display.tray == this) {
            this.display.tray = null;
        }
    }

    @Override
    void reskinChildren(int n) {
        if (this.items != null) {
            for (TrayItem trayItem : this.items) {
                if (trayItem == null) continue;
                trayItem.reskin(n);
            }
        }
        super.reskinChildren(n);
    }
}

