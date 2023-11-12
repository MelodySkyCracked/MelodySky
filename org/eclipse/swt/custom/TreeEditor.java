/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.lIII;
import org.eclipse.swt.custom.ll;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class TreeEditor
extends ControlEditor {
    Tree tree;
    TreeItem item;
    int column = 0;
    ControlListener columnListener;
    TreeListener treeListener;
    Runnable timer;
    static final int TIMEOUT = 1500;

    public TreeEditor(Tree tree) {
        super(tree);
        this.tree = tree;
        this.columnListener = new ll(this);
        this.timer = this::layout;
        this.treeListener = new lIII(this);
        tree.addTreeListener(this.treeListener);
        this.grabVertical = true;
    }

    @Override
    Rectangle computeBounds() {
        Rectangle rectangle;
        if (this.item == null || this.column == -1 || this.item.isDisposed()) {
            return new Rectangle(0, 0, 0, 0);
        }
        Rectangle rectangle2 = this.item.getBounds(this.column);
        Rectangle rectangle3 = this.item.getImageBounds(this.column);
        rectangle2.x = rectangle3.x + rectangle3.width;
        Rectangle rectangle4 = rectangle2;
        rectangle4.width -= rectangle3.width;
        Rectangle rectangle5 = this.tree.getClientArea();
        if (rectangle2.x < rectangle5.x + rectangle5.width && rectangle2.x + rectangle2.width > rectangle5.x + rectangle5.width) {
            rectangle2.width = rectangle5.x + rectangle5.width - rectangle2.x;
        }
        Rectangle rectangle6 = new Rectangle(rectangle2.x, rectangle2.y, this.minimumWidth, this.minimumHeight);
        if (this.grabHorizontal) {
            if (this.tree.getColumnCount() == 0) {
                rectangle2.width = rectangle5.x + rectangle5.width - rectangle2.x;
            }
            rectangle6.width = Math.max(rectangle2.width, this.minimumWidth);
        }
        if (this.grabVertical) {
            rectangle6.height = Math.max(rectangle2.height, this.minimumHeight);
        }
        if (this.horizontalAlignment == 131072) {
            rectangle = rectangle6;
            rectangle.x += rectangle2.width - rectangle6.width;
        } else if (this.horizontalAlignment != 16384) {
            rectangle = rectangle6;
            rectangle.x += (rectangle2.width - rectangle6.width) / 2;
        }
        rectangle6.x = Math.max(rectangle2.x, rectangle6.x);
        if (this.verticalAlignment == 1024) {
            rectangle = rectangle6;
            rectangle.y += rectangle2.height - rectangle6.height;
        } else if (this.verticalAlignment != 128) {
            rectangle = rectangle6;
            rectangle.y += (rectangle2.height - rectangle6.height) / 2;
        }
        return rectangle6;
    }

    @Override
    public void dispose() {
        if (this.tree != null && !this.tree.isDisposed()) {
            if (this.column > -1 && this.column < this.tree.getColumnCount()) {
                TreeColumn treeColumn = this.tree.getColumn(this.column);
                treeColumn.removeControlListener(this.columnListener);
            }
            if (this.treeListener != null) {
                this.tree.removeTreeListener(this.treeListener);
            }
        }
        this.columnListener = null;
        this.treeListener = null;
        this.tree = null;
        this.item = null;
        this.column = 0;
        this.timer = null;
        super.dispose();
    }

    public int getColumn() {
        return this.column;
    }

    public TreeItem getItem() {
        return this.item;
    }

    void resize() {
        this.layout();
        if (this.tree != null) {
            Display display = this.tree.getDisplay();
            display.timerExec(-1, this.timer);
            display.timerExec(1500, this.timer);
        }
    }

    public void setColumn(int n) {
        TreeColumn treeColumn;
        int n2 = this.tree.getColumnCount();
        if (n2 == 0) {
            this.column = n == 0 ? 0 : -1;
            this.resize();
            return;
        }
        if (this.column > -1 && this.column < n2) {
            treeColumn = this.tree.getColumn(this.column);
            treeColumn.removeControlListener(this.columnListener);
            this.column = -1;
        }
        if (n < 0 || n >= this.tree.getColumnCount()) {
            return;
        }
        this.column = n;
        treeColumn = this.tree.getColumn(this.column);
        treeColumn.addControlListener(this.columnListener);
        this.resize();
    }

    public void setItem(TreeItem treeItem) {
        this.item = treeItem;
        this.resize();
    }

    public void setEditor(Control control, TreeItem treeItem, int n) {
        this.setItem(treeItem);
        this.setColumn(n);
        this.setEditor(control);
    }

    @Override
    public void setEditor(Control control) {
        super.setEditor(control);
        this.resize();
    }

    public void setEditor(Control control, TreeItem treeItem) {
        this.setItem(treeItem);
        this.setEditor(control);
    }

    @Override
    public void layout() {
        if (this.tree == null || this.tree.isDisposed()) {
            return;
        }
        if (this.item == null || this.item.isDisposed()) {
            return;
        }
        int n = this.tree.getColumnCount();
        if (n == 0 && this.column != 0) {
            return;
        }
        if (n > 0 && (this.column < 0 || this.column >= n)) {
            return;
        }
        super.layout();
    }
}

