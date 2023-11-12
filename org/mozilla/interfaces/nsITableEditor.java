/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsISupports;

public interface nsITableEditor
extends nsISupports {
    public static final String NS_ITABLEEDITOR_IID = "{4805e684-49b9-11d3-9ce4-ed60bd6cb5bc}";
    public static final short eNoSearch = 0;
    public static final short ePreviousColumn = 1;
    public static final short ePreviousRow = 2;

    public void insertTableCell(int var1, boolean var2);

    public void insertTableColumn(int var1, boolean var2);

    public void insertTableRow(int var1, boolean var2);

    public void deleteTable();

    public void deleteTableCellContents();

    public void deleteTableCell(int var1);

    public void deleteTableColumn(int var1);

    public void deleteTableRow(int var1);

    public void selectTableCell();

    public void selectBlockOfCells(nsIDOMElement var1, nsIDOMElement var2);

    public void selectTableRow();

    public void selectTableColumn();

    public void selectTable();

    public void selectAllTableCells();

    public nsIDOMElement switchTableCellHeaderType(nsIDOMElement var1);

    public void joinTableCells(boolean var1);

    public void splitTableCell();

    public void normalizeTable(nsIDOMElement var1);

    public void getCellIndexes(nsIDOMElement var1, int[] var2, int[] var3);

    public void getTableSize(nsIDOMElement var1, int[] var2, int[] var3);

    public nsIDOMElement getCellAt(nsIDOMElement var1, int var2, int var3);

    public void getCellDataAt(nsIDOMElement var1, int var2, int var3, nsIDOMElement[] var4, int[] var5, int[] var6, int[] var7, int[] var8, int[] var9, int[] var10, boolean[] var11);

    public nsIDOMNode getFirstRow(nsIDOMElement var1);

    public nsIDOMNode getNextRow(nsIDOMNode var1);

    public void setSelectionAfterTableEdit(nsIDOMElement var1, int var2, int var3, int var4, boolean var5);

    public nsIDOMElement getSelectedOrParentTableElement(String[] var1, int[] var2);

    public long getSelectedCellsType(nsIDOMElement var1);

    public nsIDOMElement getFirstSelectedCell(nsIDOMRange[] var1);

    public nsIDOMElement getFirstSelectedCellInTable(int[] var1, int[] var2);

    public nsIDOMElement getNextSelectedCell(nsIDOMRange[] var1);
}

