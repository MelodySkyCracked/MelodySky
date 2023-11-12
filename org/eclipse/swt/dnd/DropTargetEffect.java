/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class DropTargetEffect
extends DropTargetAdapter {
    Control control;

    public DropTargetEffect(Control control) {
        if (control == null) {
            SWT.error(4);
        }
        this.control = control;
    }

    public Control getControl() {
        return this.control;
    }

    public Widget getItem(int n, int n2) {
        if (this.control instanceof Table) {
            return this.getItem((Table)this.control, n, n2);
        }
        if (this.control instanceof Tree) {
            return this.getItem((Tree)this.control, n, n2);
        }
        return null;
    }

    Widget getItem(Table table, int n, int n2) {
        Point point = new Point(n, n2);
        TableItem tableItem = table.getItem(point = table.toControl(point));
        if (tableItem != null) {
            return tableItem;
        }
        Rectangle rectangle = table.getClientArea();
        int n3 = rectangle.y + rectangle.height;
        int n4 = table.getItemCount();
        for (int i = table.getTopIndex(); i < n4; ++i) {
            tableItem = table.getItem(i);
            Rectangle rectangle2 = tableItem.getBounds();
            rectangle2.x = rectangle.x;
            rectangle2.width = rectangle.width;
            if (rectangle2.contains(point)) {
                return tableItem;
            }
            if (rectangle2.y > n3) break;
        }
        return null;
    }

    Widget getItem(Tree tree, int n, int n2) {
        Rectangle rectangle;
        Point point = new Point(n, n2);
        TreeItem treeItem = tree.getItem(point = tree.toControl(point));
        if (treeItem == null && (rectangle = tree.getClientArea()).contains(point)) {
            int n3 = rectangle.y + rectangle.height;
            treeItem = tree.getTopItem();
            while (treeItem != null) {
                Rectangle rectangle2 = treeItem.getBounds();
                int n4 = rectangle2.y + rectangle2.height;
                if (rectangle2.y <= point.y && point.y < n4) {
                    return treeItem;
                }
                if (n4 > n3) break;
                treeItem = this.nextItem(tree, treeItem);
            }
            return null;
        }
        return treeItem;
    }

    TreeItem nextItem(Tree tree, TreeItem treeItem) {
        int n;
        if (treeItem == null) {
            return null;
        }
        if (treeItem.getExpanded() && treeItem.getItemCount() > 0) {
            return treeItem.getItem(0);
        }
        TreeItem treeItem2 = treeItem;
        TreeItem treeItem3 = treeItem2.getParentItem();
        int n2 = treeItem3 == null ? tree.indexOf(treeItem2) : treeItem3.indexOf(treeItem2);
        int n3 = n = treeItem3 == null ? tree.getItemCount() : treeItem3.getItemCount();
        while (n2 + 1 >= n) {
            if (treeItem3 == null) {
                return null;
            }
            treeItem2 = treeItem3;
            n2 = (treeItem3 = treeItem2.getParentItem()) == null ? tree.indexOf(treeItem2) : treeItem3.indexOf(treeItem2);
            n = treeItem3 == null ? tree.getItemCount() : treeItem3.getItemCount();
        }
        return treeItem3 == null ? tree.getItem(n2 + 1) : treeItem3.getItem(n2 + 1);
    }

    TreeItem previousItem(Tree tree, TreeItem treeItem) {
        int n;
        if (treeItem == null) {
            return null;
        }
        TreeItem treeItem2 = treeItem;
        TreeItem treeItem3 = treeItem2.getParentItem();
        int n2 = n = treeItem3 == null ? tree.indexOf(treeItem2) : treeItem3.indexOf(treeItem2);
        if (n == 0) {
            return treeItem3;
        }
        TreeItem treeItem4 = treeItem3 == null ? tree.getItem(n - 1) : treeItem3.getItem(n - 1);
        int n3 = treeItem4.getItemCount();
        while (n3 > 0 && treeItem4.getExpanded()) {
            treeItem4 = treeItem4.getItem(n3 - 1);
            n3 = treeItem4.getItemCount();
        }
        return treeItem4;
    }
}

