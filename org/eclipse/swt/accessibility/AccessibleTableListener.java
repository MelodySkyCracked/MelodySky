/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleTableEvent;
import org.eclipse.swt.internal.SWTEventListener;

public interface AccessibleTableListener
extends SWTEventListener {
    public void deselectColumn(AccessibleTableEvent var1);

    public void deselectRow(AccessibleTableEvent var1);

    @Deprecated
    public void getCaption(AccessibleTableEvent var1);

    public void getCell(AccessibleTableEvent var1);

    public void getColumn(AccessibleTableEvent var1);

    public void getColumnCount(AccessibleTableEvent var1);

    public void getColumnDescription(AccessibleTableEvent var1);

    public void getColumnHeader(AccessibleTableEvent var1);

    public void getColumnHeaderCells(AccessibleTableEvent var1);

    public void getColumns(AccessibleTableEvent var1);

    public void getRow(AccessibleTableEvent var1);

    public void getRowCount(AccessibleTableEvent var1);

    public void getRowDescription(AccessibleTableEvent var1);

    public void getRowHeader(AccessibleTableEvent var1);

    public void getRowHeaderCells(AccessibleTableEvent var1);

    public void getRows(AccessibleTableEvent var1);

    public void getSelectedCellCount(AccessibleTableEvent var1);

    public void getSelectedCells(AccessibleTableEvent var1);

    public void getSelectedColumnCount(AccessibleTableEvent var1);

    public void getSelectedColumns(AccessibleTableEvent var1);

    public void getSelectedRowCount(AccessibleTableEvent var1);

    public void getSelectedRows(AccessibleTableEvent var1);

    @Deprecated
    public void getSummary(AccessibleTableEvent var1);

    public void getVisibleColumns(AccessibleTableEvent var1);

    public void getVisibleRows(AccessibleTableEvent var1);

    public void isColumnSelected(AccessibleTableEvent var1);

    public void isRowSelected(AccessibleTableEvent var1);

    public void selectColumn(AccessibleTableEvent var1);

    public void selectRow(AccessibleTableEvent var1);

    public void setSelectedColumn(AccessibleTableEvent var1);

    public void setSelectedRow(AccessibleTableEvent var1);
}

