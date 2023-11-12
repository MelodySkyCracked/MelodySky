/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.lII;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableEditor
extends ControlEditor {
    Table table;
    TableItem item;
    int column = -1;
    ControlListener columnListener;
    Runnable timer;
    static final int TIMEOUT = 1500;

    public TableEditor(Table table) {
        super(table);
        this.table = table;
        this.columnListener = new lII(this);
        this.timer = this::layout;
        this.grabVertical = true;
    }

    @Override
    Rectangle computeBounds() {
        Rectangle rectangle;
        Rectangle rectangle2;
        if (this.item == null || this.column == -1 || this.item.isDisposed()) {
            return new Rectangle(0, 0, 0, 0);
        }
        Rectangle rectangle3 = this.item.getBounds(this.column);
        Rectangle rectangle4 = this.item.getImageBounds(this.column);
        if (rectangle4.width != 0) {
            int n = Math.max(rectangle4.x - rectangle3.x, 0);
            rectangle3.x = rectangle4.x + rectangle4.width;
            rectangle2 = rectangle3;
            rectangle2.width -= n + rectangle4.width;
        }
        Rectangle rectangle5 = this.table.getClientArea();
        if (rectangle3.x < rectangle5.x + rectangle5.width && rectangle3.x + rectangle3.width > rectangle5.x + rectangle5.width) {
            rectangle3.width = rectangle5.x + rectangle5.width - rectangle3.x;
        }
        rectangle2 = new Rectangle(rectangle3.x, rectangle3.y, this.minimumWidth, this.minimumHeight);
        if (this.grabHorizontal) {
            rectangle2.width = Math.max(rectangle3.width, this.minimumWidth);
        }
        if (this.grabVertical) {
            rectangle2.height = Math.max(rectangle3.height, this.minimumHeight);
        }
        if (this.horizontalAlignment == 131072) {
            rectangle = rectangle2;
            rectangle.x += rectangle3.width - rectangle2.width;
        } else if (this.horizontalAlignment != 16384) {
            rectangle = rectangle2;
            rectangle.x += (rectangle3.width - rectangle2.width) / 2;
        }
        if (this.verticalAlignment == 1024) {
            rectangle = rectangle2;
            rectangle.y += rectangle3.height - rectangle2.height;
        } else if (this.verticalAlignment != 128) {
            rectangle = rectangle2;
            rectangle.y += (rectangle3.height - rectangle2.height) / 2;
        }
        return rectangle2;
    }

    @Override
    public void dispose() {
        if (this.table != null && !this.table.isDisposed() && this.column > -1 && this.column < this.table.getColumnCount()) {
            TableColumn tableColumn = this.table.getColumn(this.column);
            tableColumn.removeControlListener(this.columnListener);
        }
        this.columnListener = null;
        this.table = null;
        this.item = null;
        this.column = -1;
        this.timer = null;
        super.dispose();
    }

    public int getColumn() {
        return this.column;
    }

    public TableItem getItem() {
        return this.item;
    }

    void resize() {
        this.layout();
        if (this.table != null) {
            Display display = this.table.getDisplay();
            display.timerExec(-1, this.timer);
            display.timerExec(1500, this.timer);
        }
    }

    public void setColumn(int n) {
        TableColumn tableColumn;
        int n2 = this.table.getColumnCount();
        if (n2 == 0) {
            this.column = n == 0 ? 0 : -1;
            this.resize();
            return;
        }
        if (this.column > -1 && this.column < n2) {
            tableColumn = this.table.getColumn(this.column);
            tableColumn.removeControlListener(this.columnListener);
            this.column = -1;
        }
        if (n < 0 || n >= this.table.getColumnCount()) {
            return;
        }
        this.column = n;
        tableColumn = this.table.getColumn(this.column);
        tableColumn.addControlListener(this.columnListener);
        this.resize();
    }

    public void setItem(TableItem tableItem) {
        this.item = tableItem;
        this.resize();
    }

    @Override
    public void setEditor(Control control) {
        super.setEditor(control);
        this.resize();
    }

    public void setEditor(Control control, TableItem tableItem, int n) {
        this.setItem(tableItem);
        this.setColumn(n);
        this.setEditor(control);
    }

    @Override
    public void layout() {
        if (this.table == null || this.table.isDisposed()) {
            return;
        }
        if (this.item == null || this.item.isDisposed()) {
            return;
        }
        int n = this.table.getColumnCount();
        if (n == 0 && this.column != 0) {
            return;
        }
        if (n > 0 && (this.column < 0 || this.column >= n)) {
            return;
        }
        super.layout();
    }
}

