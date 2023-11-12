/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.lIlIl;
import org.eclipse.swt.custom.lllII;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TypedListener;

public class TableCursor
extends Canvas {
    Table table;
    TableItem row = null;
    TableColumn column = null;
    Listener listener;
    Listener tableListener;
    Listener resizeListener;
    Listener disposeItemListener;
    Listener disposeColumnListener;
    Color background = null;
    Color foreground = null;
    static final int BACKGROUND = 27;
    static final int FOREGROUND = 26;

    public TableCursor(Table table, int n) {
        super(table, n);
        ScrollBar scrollBar;
        this.table = table;
        this.setBackground(null);
        this.setForeground(null);
        int n22 = 0;
        this.listener = this::lambda$new$0;
        int[] nArray = new int[]{12, 15, 16, 1, 9, 31};
        int[] nArray2 = nArray;
        for (int n22 : nArray) {
            this.addListener(n22, this.listener);
        }
        this.tableListener = this::lambda$new$1;
        this.table.addListener(15, this.tableListener);
        this.table.addListener(3, this.tableListener);
        this.disposeItemListener = this::lambda$new$2;
        this.disposeColumnListener = this::lambda$new$3;
        this.resizeListener = this::lambda$new$4;
        ScrollBar scrollBar2 = this.table.getHorizontalBar();
        if (scrollBar2 != null) {
            scrollBar2.addListener(13, this.resizeListener);
        }
        if ((scrollBar = this.table.getVerticalBar()) != null) {
            scrollBar.addListener(13, this.resizeListener);
        }
        this.getAccessible().addAccessibleControlListener(new lIlIl(this));
        this.getAccessible().addAccessibleListener(new lllII(this));
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

    void onDispose(Event event) {
        ScrollBar scrollBar;
        this.removeListener(12, this.listener);
        this.notifyListeners(12, event);
        event.type = 0;
        this.table.removeListener(15, this.tableListener);
        this.table.removeListener(3, this.tableListener);
        this.unhookRowColumnListeners();
        ScrollBar scrollBar2 = this.table.getHorizontalBar();
        if (scrollBar2 != null) {
            scrollBar2.removeListener(13, this.resizeListener);
        }
        if ((scrollBar = this.table.getVerticalBar()) != null) {
            scrollBar.removeListener(13, this.resizeListener);
        }
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
        int n = this.table.indexOf(this.row);
        int n2 = this.column == null ? 0 : this.table.indexOf(this.column);
        switch (event.keyCode) {
            case 0x1000001: {
                this.setRowColumn(Math.max(0, n - 1), n2, true);
                break;
            }
            case 0x1000002: {
                this.setRowColumn(Math.min(n + 1, this.table.getItemCount() - 1), n2, true);
                break;
            }
            case 0x1000003: 
            case 0x1000004: {
                int n3;
                int n4;
                int n5 = this.table.getColumnCount();
                if (n5 == 0) break;
                int[] nArray = this.table.getColumnOrder();
                for (n4 = 0; n4 < nArray.length && nArray[n4] != n2; ++n4) {
                }
                if (n4 == nArray.length) {
                    n4 = 0;
                }
                int n6 = n3 = (this.getStyle() & 0x4000000) != 0 ? 0x1000004 : 0x1000003;
                if (event.keyCode == n3) {
                    this.setRowColumn(n, nArray[Math.max(0, n4 - 1)], true);
                    break;
                }
                this.setRowColumn(n, nArray[Math.min(n5 - 1, n4 + 1)], true);
                break;
            }
            case 0x1000007: {
                this.setRowColumn(0, n2, true);
                break;
            }
            case 0x1000008: {
                int n7 = this.table.getItemCount() - 1;
                this.setRowColumn(n7, n2, true);
                break;
            }
            case 0x1000005: {
                int n8 = this.table.getTopIndex();
                if (n8 == n) {
                    Rectangle rectangle = this.table.getClientArea();
                    TableItem tableItem = this.table.getItem(n8);
                    Rectangle rectangle2 = tableItem.getBounds(0);
                    Rectangle rectangle3 = rectangle;
                    rectangle3.height -= rectangle2.y;
                    int n9 = this.table.getItemHeight();
                    int n10 = Math.max(1, rectangle.height / n9);
                    n8 = Math.max(0, n8 - n10 + 1);
                }
                this.setRowColumn(n8, n2, true);
                break;
            }
            case 0x1000006: {
                int n11 = this.table.getTopIndex();
                Rectangle rectangle = this.table.getClientArea();
                TableItem tableItem = this.table.getItem(n11);
                Rectangle rectangle4 = tableItem.getBounds(0);
                Rectangle rectangle5 = rectangle;
                rectangle5.height -= rectangle4.y;
                int n12 = this.table.getItemHeight();
                int n13 = Math.max(1, rectangle.height / n12);
                int n14 = this.table.getItemCount() - 1;
                n11 = Math.min(n14, n11 + n13 - 1);
                if (n11 == n) {
                    n11 = Math.min(n14, n11 + n13 - 1);
                }
                this.setRowColumn(n11, n2, true);
                break;
            }
        }
    }

    void paint(Event event) {
        Object object;
        if (this.row == null) {
            return;
        }
        int n = this.column == null ? 0 : this.table.indexOf(this.column);
        GC gC = event.gc;
        gC.setBackground(this.getBackground());
        gC.setForeground(this.getForeground());
        gC.fillRectangle(event.x, event.y, event.width, event.height);
        int n2 = 0;
        Point point = this.getSize();
        Image image = this.row.getImage(n);
        if (image != null) {
            object = image.getBounds();
            int n3 = (point.y - ((Rectangle)object).height) / 2;
            gC.drawImage(image, n2, n3);
            n2 += ((Rectangle)object).width;
        }
        if (((String)(object = this.row.getText(n))).length() > 0) {
            int n4;
            Rectangle rectangle = this.row.getBounds(n);
            Point point2 = gC.stringExtent((String)object);
            String string = SWT.getPlatform();
            if ("win32".equals(string)) {
                if (this.table.getColumnCount() == 0 || n == 0) {
                    n2 += 2;
                } else {
                    n4 = this.column.getAlignment();
                    switch (n4) {
                        case 16384: {
                            n2 += 6;
                            break;
                        }
                        case 131072: {
                            n2 = rectangle.width - point2.x - 6;
                            break;
                        }
                        case 0x1000000: {
                            n2 += (rectangle.width - n2 - point2.x) / 2;
                        }
                    }
                }
            } else if (this.table.getColumnCount() == 0) {
                n2 += 5;
            } else {
                n4 = this.column.getAlignment();
                switch (n4) {
                    case 16384: {
                        n2 += 5;
                        break;
                    }
                    case 131072: {
                        n2 = rectangle.width - point2.x - 2;
                        break;
                    }
                    case 0x1000000: {
                        n2 += (rectangle.width - n2 - point2.x) / 2 + 2;
                    }
                }
            }
            n4 = (point.y - point2.y) / 2;
            gC.drawString((String)object, n2, n4);
        }
        if (this.isFocusControl()) {
            Display display = this.getDisplay();
            gC.setBackground(display.getSystemColor(2));
            gC.setForeground(display.getSystemColor(1));
            gC.drawFocus(0, 0, point.x, point.y);
        }
    }

    void tableFocusIn(Event event) {
        if (this.isDisposed()) {
            return;
        }
        if (this.isVisible()) {
            if (this.row == null && this.column == null) {
                return;
            }
            this.setFocus();
        }
    }

    void tableMouseDown(Event event) {
        Object object;
        Object object2;
        Rectangle rectangle;
        int n;
        if (this.isDisposed() || !this.isVisible()) {
            return;
        }
        Point point = new Point(event.x, event.y);
        int n2 = this.table.getLinesVisible() ? this.table.getGridLineWidth() : 0;
        Object object3 = this.table.getItem(point);
        if ((this.table.getStyle() & 0x10000) != 0) {
            if (object3 == null) {
                return;
            }
        } else {
            int n3 = object3 != null ? this.table.indexOf((TableItem)object3) : this.table.getTopIndex();
            n = this.table.getItemCount();
            rectangle = this.table.getClientArea();
            for (int i = n3; i < n; ++i) {
                object2 = this.table.getItem(i);
                object = ((TableItem)object2).getBounds(0);
                if (point.y >= ((Rectangle)object).y && point.y < ((Rectangle)object).y + ((Rectangle)object).height + n2) {
                    object3 = object2;
                    break;
                }
                if (((Rectangle)object).y <= rectangle.y + rectangle.height) continue;
                return;
            }
            if (object3 == null) {
                return;
            }
        }
        TableColumn tableColumn = null;
        n = this.table.getColumnCount();
        if (n == 0) {
            if ((this.table.getStyle() & 0x10000) == 0) {
                Rectangle rectangle2 = rectangle = ((TableItem)object3).getBounds(0);
                rectangle.width += n2;
                object2 = rectangle2;
                ((Rectangle)object2).height += n2;
                if (!rectangle2.contains(point)) {
                    return;
                }
            }
        } else {
            for (int i = 0; i < n; ++i) {
                Rectangle rectangle3 = ((TableItem)object3).getBounds(i);
                object2 = rectangle3;
                rectangle3.width += n2;
                object = object2;
                ((Rectangle)object).height += n2;
                if (!((Rectangle)object2).contains(point)) continue;
                tableColumn = this.table.getColumn(i);
                break;
            }
            if (tableColumn == null) {
                if ((this.table.getStyle() & 0x10000) == 0) {
                    return;
                }
                tableColumn = this.table.getColumn(0);
            }
        }
        this.setRowColumn((TableItem)object3, tableColumn, true);
        this.setFocus();
    }

    void setRowColumn(int n, int n2, boolean bl) {
        TableItem tableItem = n == -1 ? null : this.table.getItem(n);
        TableColumn tableColumn = n2 == -1 || this.table.getColumnCount() == 0 ? null : this.table.getColumn(n2);
        this.setRowColumn(tableItem, tableColumn, bl);
    }

    void setRowColumn(TableItem tableItem, TableColumn tableColumn, boolean bl) {
        if (this.row == tableItem && this.column == tableColumn) {
            return;
        }
        if (this.row != null && this.row != tableItem) {
            this.row.removeListener(12, this.disposeItemListener);
            this.row = null;
        }
        if (this.column != null && this.column != tableColumn) {
            this.column.removeListener(12, this.disposeColumnListener);
            this.column.removeListener(10, this.resizeListener);
            this.column.removeListener(11, this.resizeListener);
            this.column = null;
        }
        if (tableItem != null) {
            if (this.row != tableItem) {
                this.row = tableItem;
                this.row.addListener(12, this.disposeItemListener);
                this.table.showItem(tableItem);
            }
            if (this.column != tableColumn && tableColumn != null) {
                this.column = tableColumn;
                this.column.addListener(12, this.disposeColumnListener);
                tableColumn.addListener(10, this.resizeListener);
                tableColumn.addListener(11, this.resizeListener);
                this.table.showColumn(tableColumn);
            }
            int n = tableColumn == null ? 0 : this.table.indexOf(tableColumn);
            this.setBounds(tableItem.getBounds(n));
            this.redraw();
            if (bl) {
                this.notifyListeners(13, new Event());
            }
        }
        this.getAccessible().setFocus(-1);
    }

    @Override
    public void setVisible(boolean bl) {
        this.checkWidget();
        if (bl) {
            this._resize();
        }
        super.setVisible(bl);
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
            int n = this.column == null ? 0 : this.table.indexOf(this.column);
            this.setBounds(this.row.getBounds(n));
        }
    }

    public int getColumn() {
        this.checkWidget();
        return this.column == null ? 0 : this.table.indexOf(this.column);
    }

    @Override
    public Color getBackground() {
        this.checkWidget();
        if (this.background == null) {
            return this.getDisplay().getSystemColor(27);
        }
        return this.background;
    }

    @Override
    public Color getForeground() {
        this.checkWidget();
        if (this.foreground == null) {
            return this.getDisplay().getSystemColor(26);
        }
        return this.foreground;
    }

    public TableItem getRow() {
        this.checkWidget();
        return this.row;
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

    public void setSelection(int n, int n2) {
        int n3;
        this.checkWidget();
        int n4 = this.table.getColumnCount();
        int n5 = n3 = n4 == 0 ? 0 : n4 - 1;
        if (n < 0 || n >= this.table.getItemCount() || n2 < 0 || n2 > n3) {
            SWT.error(5);
        }
        this.setRowColumn(n, n2, false);
    }

    public void setSelection(TableItem tableItem, int n) {
        int n2;
        this.checkWidget();
        int n3 = this.table.getColumnCount();
        int n4 = n2 = n3 == 0 ? 0 : n3 - 1;
        if (tableItem == null || tableItem.isDisposed() || n < 0 || n > n2) {
            SWT.error(5);
        }
        this.setRowColumn(this.table.indexOf(tableItem), n, false);
    }

    void unhookRowColumnListeners() {
        if (this.column != null) {
            this.column.removeListener(12, this.disposeColumnListener);
            this.column.removeListener(10, this.resizeListener);
            this.column.removeListener(11, this.resizeListener);
            this.column = null;
        }
        if (this.row != null) {
            this.row.removeListener(12, this.disposeItemListener);
            this.row = null;
        }
    }

    private void lambda$new$4(Event event) {
        this._resize();
    }

    private void lambda$new$3(Event event) {
        this.unhookRowColumnListeners();
        this.row = null;
        this.column = null;
        this._resize();
    }

    private void lambda$new$2(Event event) {
        this.unhookRowColumnListeners();
        this.row = null;
        this.column = null;
        this._resize();
    }

    private void lambda$new$1(Event event) {
        switch (event.type) {
            case 3: {
                this.tableMouseDown(event);
                break;
            }
            case 15: {
                this.tableFocusIn(event);
            }
        }
    }

    private void lambda$new$0(Event event) {
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

