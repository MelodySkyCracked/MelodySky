/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.l;
import org.eclipse.swt.custom.lllIl;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.TypedListener;

public class TreeCursor
extends Canvas {
    Tree tree;
    TreeItem row;
    TreeColumn column;
    Listener listener;
    Listener treeListener;
    Listener resizeListener;
    Listener disposeItemListener;
    Listener disposeColumnListener;
    Color background = null;
    Color foreground = null;
    static final int BACKGROUND = 27;
    static final int FOREGROUND = 26;

    public TreeCursor(Tree tree, int n) {
        super(tree, n);
        ScrollBar scrollBar;
        this.tree = tree;
        this.setBackground(null);
        this.setForeground(null);
        int n22 = 0;
        this.listener = this::lambda$new$0;
        int[] nArray = new int[]{12, 15, 16, 1, 9, 31};
        int[] nArray2 = nArray;
        for (int n22 : nArray) {
            this.addListener(n22, this.listener);
        }
        this.treeListener = this::lambda$new$1;
        this.tree.addListener(18, this.treeListener);
        this.tree.addListener(17, this.treeListener);
        this.tree.addListener(15, this.treeListener);
        this.tree.addListener(3, this.treeListener);
        this.disposeItemListener = this::lambda$new$2;
        this.disposeColumnListener = this::lambda$new$3;
        this.resizeListener = this::lambda$new$4;
        ScrollBar scrollBar2 = this.tree.getHorizontalBar();
        if (scrollBar2 != null) {
            scrollBar2.addListener(13, this.resizeListener);
        }
        if ((scrollBar = this.tree.getVerticalBar()) != null) {
            scrollBar.addListener(13, this.resizeListener);
        }
        this.getAccessible().addAccessibleControlListener(new lllIl(this));
        this.getAccessible().addAccessibleListener(new l(this));
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        TypedListener typedListener = new TypedListener(selectionListener);
        this.addListener(13, typedListener);
        this.addListener(14, typedListener);
    }

    int countSubTreePages(TreeItem treeItem) {
        int n = 1;
        if (treeItem == null) {
            return 0;
        }
        if (treeItem.getItemCount() == 0) {
            return 1;
        }
        if (!treeItem.getExpanded()) {
            return 1;
        }
        for (TreeItem treeItem2 : treeItem.getItems()) {
            n += this.countSubTreePages(treeItem2);
        }
        return n;
    }

    int findIndex(TreeItem[] treeItemArray, TreeItem treeItem) {
        if (treeItemArray == null || treeItem == null) {
            return -1;
        }
        Rectangle rectangle = treeItem.getBounds();
        int n = 0;
        for (int i = 0; i < treeItemArray.length; ++i) {
            TreeItem treeItem2 = null;
            TreeItem treeItem3 = treeItemArray[i];
            if (i > 0) {
                treeItem2 = treeItemArray[i - 1];
            }
            Rectangle rectangle2 = treeItem3.getBounds();
            if (rectangle.y == rectangle2.y) {
                return n;
            }
            if (rectangle.y < rectangle2.y) {
                return n - 1 + this.findIndex(treeItem2.getItems(), treeItem);
            }
            if (rectangle.y > rectangle2.y && i == treeItemArray.length - 1) {
                return n + this.findIndex(treeItem3.getItems(), treeItem);
            }
            if (rectangle.y >= rectangle2.y + (1 + treeItem3.getItemCount()) * this.tree.getItemHeight() && treeItem3.getExpanded()) {
                n += this.countSubTreePages(treeItem3);
                continue;
            }
            ++n;
        }
        return -1;
    }

    TreeItem findItem(TreeItem[] treeItemArray, Point point) {
        Object object;
        Object object2;
        int n = 0;
        int n2 = treeItemArray.length - 1;
        int n3 = n2 / 2;
        while (n2 - n > 1) {
            object2 = treeItemArray[n3];
            object = ((TreeItem)object2).getBounds();
            if (point.y < ((Rectangle)object).y) {
                n2 = n3;
                n3 = (n2 - n) / 2;
                continue;
            }
            n = n3;
            n3 = n + (n2 - n) / 2;
        }
        object2 = treeItemArray[n2].getBounds();
        if (((Rectangle)object2).y < point.y) {
            if (((Rectangle)object2).y + ((Rectangle)object2).height >= point.y) {
                object = this.tree.getColumnOrder();
                Rectangle rectangle = null;
                if (((Object)object).length > 0) {
                    Rectangle rectangle2;
                    Rectangle rectangle3 = treeItemArray[n2].getBounds((int)object[0]);
                    Rectangle rectangle4 = treeItemArray[n2].getBounds((int)object[((Object)object).length - 1]);
                    rectangle = rectangle2 = rectangle3.union(rectangle4);
                    rectangle2.height = rectangle2.height + (this.tree.getLinesVisible() ? this.tree.getGridLineWidth() : 0);
                } else {
                    rectangle = treeItemArray[n2].getBounds();
                }
                return rectangle.contains(point) ? treeItemArray[n2] : null;
            }
            if (!treeItemArray[n2].getExpanded()) {
                return null;
            }
            return this.findItem(treeItemArray[n2].getItems(), point);
        }
        object = treeItemArray[n].getBounds();
        if (((Rectangle)object).y + ((Rectangle)object).height < point.y) {
            return this.findItem(treeItemArray[n].getItems(), point);
        }
        int[] nArray = this.tree.getColumnOrder();
        Rectangle rectangle = null;
        if (nArray.length > 0) {
            Rectangle rectangle5;
            Rectangle rectangle6 = treeItemArray[n].getBounds(nArray[0]);
            Rectangle rectangle7 = treeItemArray[n].getBounds(nArray[nArray.length - 1]);
            rectangle = rectangle5 = rectangle6.union(rectangle7);
            rectangle5.height = rectangle5.height + (this.tree.getLinesVisible() ? this.tree.getGridLineWidth() : 0);
        } else {
            rectangle = treeItemArray[n].getBounds();
        }
        return rectangle.contains(point) ? treeItemArray[n] : null;
    }

    @Override
    public Color getBackground() {
        this.checkWidget();
        if (this.background == null) {
            return this.getDisplay().getSystemColor(27);
        }
        return this.background;
    }

    public int getColumn() {
        this.checkWidget();
        return this.column == null ? 0 : this.tree.indexOf(this.column);
    }

    @Override
    public Color getForeground() {
        this.checkWidget();
        if (this.foreground == null) {
            return this.getDisplay().getSystemColor(26);
        }
        return this.foreground;
    }

    TreeItem getLastVisibleItem(TreeItem[] treeItemArray) {
        if (treeItemArray == null) {
            return null;
        }
        TreeItem treeItem = treeItemArray[treeItemArray.length - 1];
        if (treeItem.getExpanded() && treeItem.getItemCount() > 0) {
            return this.getLastVisibleItem(treeItem.getItems());
        }
        return treeItem;
    }

    TreeItem getNextItem(TreeItem treeItem) {
        if (treeItem == null) {
            return null;
        }
        if (treeItem.getExpanded() && treeItem.getItemCount() > 0) {
            return treeItem.getItem(0);
        }
        TreeItem treeItem2 = treeItem.getParentItem();
        while (treeItem2 != null) {
            int n = treeItem2.indexOf(treeItem);
            if (n == -1) {
                return null;
            }
            if (n < treeItem2.getItemCount() - 1) {
                return treeItem2.getItem(n + 1);
            }
            treeItem = treeItem2;
            treeItem2 = treeItem.getParentItem();
        }
        int n = this.tree.indexOf(treeItem);
        if (n == -1) {
            return null;
        }
        if (n == this.tree.getItemCount() - 1) {
            return null;
        }
        return this.tree.getItem(n + 1);
    }

    TreeItem getPreviousItem(TreeItem treeItem) {
        if (treeItem == null) {
            return null;
        }
        TreeItem treeItem2 = treeItem.getParentItem();
        if (treeItem2 == null) {
            int n = this.tree.indexOf(treeItem);
            if (n == -1 || n == 0) {
                return null;
            }
            treeItem = this.tree.getItem(n - 1);
            if (treeItem.getExpanded() && treeItem.getItemCount() > 0) {
                return this.getLastVisibleItem(treeItem.getItems());
            }
            return treeItem;
        }
        int n = treeItem2.indexOf(treeItem);
        if (n == -1) {
            return null;
        }
        if (n == 0) {
            return treeItem2;
        }
        treeItem = treeItem2.getItem(n - 1);
        if (treeItem.getExpanded() && treeItem.getItemCount() > 0) {
            return this.getLastVisibleItem(treeItem.getItems());
        }
        return treeItem;
    }

    public TreeItem getRow() {
        this.checkWidget();
        return this.row;
    }

    void keyDown(Event event) {
        if (this.row == null) {
            return;
        }
        switch (event.character) {
            case '\r': {
                this.notifyListeners(14, new Event());
                return;
            }
        }
        switch (event.keyCode) {
            case 0x1000001: {
                TreeItem treeItem = this.getPreviousItem(this.row);
                if (treeItem == null) break;
                this.setRowColumn(treeItem, this.column, true);
                break;
            }
            case 0x1000002: {
                TreeItem treeItem = this.getNextItem(this.row);
                if (treeItem == null) break;
                this.setRowColumn(treeItem, this.column, true);
                break;
            }
            case 0x1000003: 
            case 0x1000004: {
                int n;
                if ((event.stateMask & SWT.MOD1) != 0) {
                    this.row.setExpanded(event.keyCode == 0x1000004);
                    break;
                }
                int n2 = this.tree.getColumnCount();
                if (n2 == 0) break;
                int n3 = this.column == null ? 0 : this.tree.indexOf(this.column);
                int[] nArray = this.tree.getColumnOrder();
                for (n = 0; n < nArray.length && nArray[n] != n3; ++n) {
                }
                if (n == nArray.length) {
                    n = 0;
                }
                int n4 = (this.getStyle() & 0x4000000) != 0 ? 0x1000004 : 0x1000003;
                TreeItem treeItem = this.row.getParentItem();
                int n5 = this.tree.indexOf(this.row);
                if (event.keyCode == n4) {
                    if (treeItem != null) {
                        this.setRowColumn(this.row, this.tree.getColumn(nArray[Math.max(0, n - 1)]), true);
                        break;
                    }
                    this.setRowColumn(n5, nArray[Math.max(0, n - 1)], true);
                    break;
                }
                if (treeItem != null) {
                    this.setRowColumn(this.row, this.tree.getColumn(nArray[Math.min(n2 - 1, n + 1)]), true);
                    break;
                }
                this.setRowColumn(n5, nArray[Math.min(n2 - 1, n + 1)], true);
                break;
            }
            case 0x1000007: {
                int n = this.column == null ? 0 : this.tree.indexOf(this.column);
                this.setRowColumn(0, n, true);
                break;
            }
            case 0x1000008: {
                TreeItem[] treeItemArray = this.tree.getItems();
                this.setRowColumn(this.getLastVisibleItem(treeItemArray), this.column, true);
                break;
            }
            case 0x1000005: {
                Rectangle rectangle = this.tree.getClientArea();
                Rectangle rectangle2 = this.tree.getTopItem().getBounds();
                TreeItem treeItem = this.row;
                int n = this.findIndex(this.tree.getItems(), treeItem);
                int n6 = this.tree.getItemHeight();
                Rectangle rectangle3 = rectangle;
                rectangle3.height -= rectangle2.y;
                int n7 = Math.max(1, rectangle.height / n6);
                if (n - n7 <= 0) {
                    TreeItem treeItem2 = this.tree.getItem(0);
                    this.setRowColumn(treeItem2, this.column, true);
                    break;
                }
                for (int i = 0; i < n7; ++i) {
                    treeItem = this.getPreviousItem(treeItem);
                }
                this.setRowColumn(treeItem, this.column, true);
                break;
            }
            case 0x1000006: {
                Rectangle rectangle = this.tree.getClientArea();
                Rectangle rectangle4 = this.tree.getTopItem().getBounds();
                TreeItem treeItem = this.row;
                int n = this.findIndex(this.tree.getItems(), treeItem);
                int n8 = this.tree.getItemHeight();
                Rectangle rectangle5 = rectangle;
                rectangle5.height -= rectangle4.y;
                TreeItem treeItem3 = this.getLastVisibleItem(this.tree.getItems());
                int n9 = Math.max(1, rectangle.height / n8);
                int n10 = this.findIndex(this.tree.getItems(), treeItem3);
                if (n10 <= n + n9) {
                    this.setRowColumn(treeItem3, this.column, true);
                    break;
                }
                for (int i = 0; i < n9; ++i) {
                    treeItem = this.getNextItem(treeItem);
                }
                this.setRowColumn(treeItem, this.column, true);
                break;
            }
        }
    }

    void onDispose(Event event) {
        ScrollBar scrollBar;
        this.removeListener(12, this.listener);
        this.notifyListeners(12, event);
        event.type = 0;
        this.tree.removeListener(18, this.treeListener);
        this.tree.removeListener(17, this.treeListener);
        this.tree.removeListener(15, this.treeListener);
        this.tree.removeListener(3, this.treeListener);
        this.unhookRowColumnListeners();
        ScrollBar scrollBar2 = this.tree.getHorizontalBar();
        if (scrollBar2 != null) {
            scrollBar2.removeListener(13, this.resizeListener);
        }
        if ((scrollBar = this.tree.getVerticalBar()) != null) {
            scrollBar.removeListener(13, this.resizeListener);
        }
    }

    void paint(Event event) {
        Object object;
        if (this.row == null) {
            return;
        }
        int n = this.column == null ? 0 : this.tree.indexOf(this.column);
        int n2 = n;
        int[] nArray = this.tree.getColumnOrder();
        for (int i = 0; i < nArray.length; ++i) {
            if (nArray[i] != n2) continue;
            n = i;
            break;
        }
        GC gC = event.gc;
        gC.setBackground(this.getBackground());
        gC.setForeground(this.getForeground());
        gC.fillRectangle(event.x, event.y, event.width, event.height);
        Image image = this.row.getImage(n2);
        int n3 = 0;
        String string = SWT.getPlatform();
        if (image != null) {
            if ("win32".equals(string)) {
                if (n > 0) {
                    n3 += 2;
                }
            } else {
                n3 += 2;
            }
        }
        Point point = this.getSize();
        if (image != null) {
            object = image.getBounds();
            int n4 = (point.y - ((Rectangle)object).height) / 2;
            gC.drawImage(image, n3, n4);
            n3 += ((Rectangle)object).width;
        }
        if (((String)(object = this.row.getText(n2))).length() > 0) {
            int n5;
            Rectangle rectangle = this.row.getBounds(n2);
            Point point2 = gC.stringExtent((String)object);
            if ("win32".equals(string)) {
                if (this.tree.getColumnCount() == 0 || n == 0) {
                    n3 += image == null ? 2 : 5;
                } else {
                    n5 = this.column.getAlignment();
                    switch (n5) {
                        case 16384: {
                            n3 += image == null ? 5 : 3;
                            break;
                        }
                        case 131072: {
                            n3 = rectangle.width - point2.x - 2;
                            break;
                        }
                        case 0x1000000: {
                            n3 += (int)Math.ceil((double)(rectangle.width - n3 - point2.x) / 2.0);
                        }
                    }
                }
            } else if (this.tree.getColumnCount() == 0) {
                n3 += image == null ? 4 : 3;
            } else {
                n5 = this.column.getAlignment();
                switch (n5) {
                    case 16384: {
                        n3 += image == null ? 5 : 3;
                        break;
                    }
                    case 131072: {
                        n3 = rectangle.width - point2.x - 2;
                        break;
                    }
                    case 0x1000000: {
                        n3 += (rectangle.width - n3 - point2.x) / 2 + 2;
                    }
                }
            }
            n5 = (point.y - point2.y) / 2;
            gC.drawString((String)object, n3, n5);
        }
        if (this.isFocusControl()) {
            Display display = this.getDisplay();
            gC.setBackground(display.getSystemColor(2));
            gC.setForeground(display.getSystemColor(1));
            gC.drawFocus(0, 0, point.x, point.y);
        }
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        this.removeListener(13, selectionListener);
        this.removeListener(14, selectionListener);
    }

    void _resize() {
        if (this.row == null) {
            this.setBounds(-200, -200, 0, 0);
        } else {
            int n = this.column == null ? 0 : this.tree.indexOf(this.column);
            this.setBounds(this.row.getBounds(n));
        }
    }

    @Override
    public void setBackground(Color color) {
        this.background = color;
        super.setBackground(this.getBackground());
        this.redraw();
    }

    @Override
    public void setForeground(Color color) {
        this.foreground = color;
        super.setForeground(this.getForeground());
        this.redraw();
    }

    void setRowColumn(int n, int n2, boolean bl) {
        TreeItem treeItem = n == -1 ? null : this.tree.getItem(n);
        TreeColumn treeColumn = n2 == -1 || this.tree.getColumnCount() == 0 ? null : this.tree.getColumn(n2);
        this.setRowColumn(treeItem, treeColumn, bl);
    }

    void setRowColumn(TreeItem treeItem, TreeColumn treeColumn, boolean bl) {
        TreeItem treeItem2;
        if (this.row != null && this.row != treeItem) {
            for (treeItem2 = this.row; treeItem2 != null; treeItem2 = treeItem2.getParentItem()) {
                treeItem2.removeListener(12, this.disposeItemListener);
            }
            this.row = null;
        }
        if (this.column != null && this.column != treeColumn) {
            this.column.removeListener(12, this.disposeColumnListener);
            this.column.removeListener(10, this.resizeListener);
            this.column.removeListener(11, this.resizeListener);
            this.column = null;
        }
        if (treeItem != null) {
            if (this.row != treeItem) {
                this.row = treeItem;
                for (treeItem2 = treeItem; treeItem2 != null; treeItem2 = treeItem2.getParentItem()) {
                    treeItem2.addListener(12, this.disposeItemListener);
                }
                this.tree.showItem(treeItem);
            }
            if (this.column != treeColumn && treeColumn != null) {
                this.column = treeColumn;
                this.column.addListener(12, this.disposeColumnListener);
                treeColumn.addListener(10, this.resizeListener);
                treeColumn.addListener(11, this.resizeListener);
                this.tree.showColumn(treeColumn);
            }
            int n = treeColumn == null ? 0 : this.tree.indexOf(treeColumn);
            this.setBounds(treeItem.getBounds(n));
            this.redraw();
            if (bl) {
                this.notifyListeners(13, new Event());
            }
        }
    }

    public void setSelection(int n, int n2) {
        int n3;
        this.checkWidget();
        int n4 = this.tree.getColumnCount();
        int n5 = n3 = n4 == 0 ? 0 : n4 - 1;
        if (n < 0 || n >= this.tree.getItemCount() || n2 < 0 || n2 > n3) {
            SWT.error(5);
        }
        this.setRowColumn(n, n2, false);
    }

    public void setSelection(TreeItem treeItem, int n) {
        int n2;
        this.checkWidget();
        int n3 = this.tree.getColumnCount();
        int n4 = n2 = n3 == 0 ? 0 : n3 - 1;
        if (treeItem == null || treeItem.isDisposed() || n < 0 || n > n2) {
            SWT.error(5);
        }
        TreeColumn treeColumn = this.tree.getColumnCount() == 0 ? null : this.tree.getColumn(n);
        this.setRowColumn(treeItem, treeColumn, false);
    }

    @Override
    public void setVisible(boolean bl) {
        this.checkWidget();
        if (bl) {
            this._resize();
        }
        super.setVisible(bl);
    }

    void treeCollapse(Event event) {
        if (this.row == null) {
            return;
        }
        TreeItem treeItem = (TreeItem)event.item;
        for (TreeItem treeItem2 = this.row.getParentItem(); treeItem2 != null; treeItem2 = treeItem2.getParentItem()) {
            if (treeItem2 != treeItem) continue;
            this.setRowColumn(treeItem, this.column, true);
            return;
        }
        this.getDisplay().asyncExec(this::lambda$treeCollapse$5);
    }

    void treeExpand(Event event) {
        this.getDisplay().asyncExec(this::lambda$treeExpand$6);
    }

    void treeFocusIn(Event event) {
        if (this.isVisible()) {
            if (this.row == null && this.column == null) {
                return;
            }
            this.setFocus();
        }
    }

    void treeMouseDown(Event event) {
        TreeItem[] treeItemArray;
        TreeItem[] treeItemArray2;
        int n;
        int n2;
        int n3;
        Item item;
        if (this.tree.getItemCount() == 0) {
            return;
        }
        Point point = new Point(event.x, event.y);
        TreeItem treeItem = this.tree.getItem(point);
        if (treeItem == null && (this.tree.getStyle() & 0x10000) == 0) {
            item = this.tree.getTopItem();
            TreeItem treeItem2 = item.getParentItem();
            while (treeItem2 != null) {
                item = treeItem2;
                treeItem2 = item.getParentItem();
            }
            n3 = this.tree.indexOf((TreeItem)item);
            n2 = this.tree.getClientArea().height / this.tree.getItemHeight();
            n = Math.min(n3 + n2, this.tree.getItemCount() - 1);
            treeItemArray2 = this.tree.getItems();
            treeItemArray = new TreeItem[n - n3 + 1];
            System.arraycopy(treeItemArray2, n3, treeItemArray, 0, n - n3 + 1);
            treeItem = this.findItem(treeItemArray, point);
        }
        if (treeItem == null) {
            return;
        }
        item = null;
        n3 = this.tree.getLinesVisible() ? this.tree.getGridLineWidth() : 0;
        n2 = this.tree.getColumnCount();
        if (n2 > 0) {
            for (n = 0; n < n2; ++n) {
                treeItemArray2 = treeItem.getBounds(n);
                treeItemArray = treeItemArray2;
                treeItemArray2.width += n3;
                TreeItem[] treeItemArray3 = treeItemArray;
                treeItemArray3.height += n3;
                if (!treeItemArray.contains(point)) continue;
                item = this.tree.getColumn(n);
                break;
            }
            if (item == null) {
                item = this.tree.getColumn(0);
            }
        }
        this.setRowColumn(treeItem, (TreeColumn)item, true);
        this.setFocus();
    }

    void unhookRowColumnListeners() {
        if (this.column != null && !this.column.isDisposed()) {
            this.column.removeListener(12, this.disposeColumnListener);
            this.column.removeListener(10, this.resizeListener);
            this.column.removeListener(11, this.resizeListener);
        }
        this.column = null;
        if (this.row != null && !this.row.isDisposed()) {
            for (TreeItem treeItem = this.row; treeItem != null; treeItem = treeItem.getParentItem()) {
                treeItem.removeListener(12, this.disposeItemListener);
            }
        }
        this.row = null;
    }

    private void lambda$treeExpand$6() {
        if (!this.isDisposed()) {
            this.setRowColumn(this.row, this.column, true);
        }
    }

    private void lambda$treeCollapse$5() {
        if (!this.isDisposed()) {
            this.setRowColumn(this.row, this.column, true);
        }
    }

    private void lambda$new$4(Event event) {
        this._resize();
    }

    private void lambda$new$3(Event event) {
        if (this.column != null) {
            if (this.tree.getColumnCount() == 1) {
                this.column = null;
            } else {
                int n;
                int n2 = n = this.tree.indexOf(this.column);
                int[] nArray = this.tree.getColumnOrder();
                for (int i = 0; i < nArray.length; ++i) {
                    if (nArray[i] != n2) continue;
                    n = i;
                    break;
                }
                if (n == nArray.length - 1) {
                    this.setRowColumn(this.row, this.tree.getColumn(nArray[n - 1]), true);
                } else {
                    this.setRowColumn(this.row, this.tree.getColumn(nArray[n + 1]), true);
                }
            }
        }
        this._resize();
    }

    private void lambda$new$2(Event event) {
        TreeItem treeItem;
        for (treeItem = this.row; treeItem != null; treeItem = treeItem.getParentItem()) {
            treeItem.removeListener(12, this.disposeItemListener);
        }
        treeItem = (TreeItem)event.widget;
        TreeItem treeItem2 = treeItem.getParentItem();
        if (treeItem2 != null) {
            this.setRowColumn(treeItem2, this.column, true);
        } else if (this.tree.getItemCount() == 1) {
            this.unhookRowColumnListeners();
        } else {
            TreeItem treeItem3;
            TreeItem treeItem4 = null;
            int n = this.tree.indexOf(treeItem);
            if (n != 0 && !(treeItem3 = this.tree.getItem(n - 1)).isDisposed()) {
                treeItem4 = treeItem3;
            }
            if (treeItem4 == null && n + 1 < this.tree.getItemCount() && !(treeItem3 = this.tree.getItem(n + 1)).isDisposed()) {
                treeItem4 = treeItem3;
            }
            if (treeItem4 != null) {
                this.setRowColumn(treeItem4, this.column, true);
            } else {
                this.unhookRowColumnListeners();
            }
        }
        this._resize();
    }

    private void lambda$new$1(Event event) {
        switch (event.type) {
            case 18: {
                this.treeCollapse(event);
                break;
            }
            case 17: {
                this.treeExpand(event);
                break;
            }
            case 15: {
                this.treeFocusIn(event);
                break;
            }
            case 3: {
                this.treeMouseDown(event);
            }
        }
    }

    private void lambda$new$0(Event event) {
        if (this.row != null) {
            if (this.row.isDisposed()) {
                this.unhookRowColumnListeners();
                this._resize();
                this.tree.setFocus();
                return;
            }
            TreeItem treeItem = this.row;
            TreeItem treeItem2 = this.row.getParentItem();
            while (treeItem2 != null && !treeItem2.getExpanded()) {
                treeItem = treeItem2;
                treeItem2 = treeItem.getParentItem();
            }
            if (treeItem != this.row) {
                this.setRowColumn(treeItem, this.column, false);
            }
        }
        block0 : switch (event.type) {
            case 12: {
                this.onDispose(event);
                break;
            }
            case 15: 
            case 16: {
                this.redraw();
                break;
            }
            case 1: {
                this.keyDown(event);
                break;
            }
            case 9: {
                this.paint(event);
                break;
            }
            case 31: {
                event.doit = true;
                switch (event.detail) {
                    case 4: 
                    case 32: 
                    case 64: {
                        event.doit = false;
                        break block0;
                    }
                }
            }
        }
    }
}

