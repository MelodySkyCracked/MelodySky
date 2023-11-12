/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITreeColumn;
import org.mozilla.interfaces.nsITreeColumns;
import org.mozilla.interfaces.nsITreeView;

public interface nsITreeBoxObject
extends nsISupports {
    public static final String NS_ITREEBOXOBJECT_IID = "{55f3b431-1aa8-4e23-ad3d-a9f5644bdaa6}";

    public nsITreeColumns getColumns();

    public nsITreeView getView();

    public void setView(nsITreeView var1);

    public boolean getFocused();

    public void setFocused(boolean var1);

    public nsIDOMElement getTreeBody();

    public int getRowHeight();

    public int getFirstVisibleRow();

    public int getLastVisibleRow();

    public int getPageLength();

    public void ensureRowIsVisible(int var1);

    public void scrollToRow(int var1);

    public void scrollByLines(int var1);

    public void scrollByPages(int var1);

    public void invalidate();

    public void invalidateColumn(nsITreeColumn var1);

    public void invalidateRow(int var1);

    public void invalidateCell(int var1, nsITreeColumn var2);

    public void invalidateRange(int var1, int var2);

    public int getRowAt(int var1, int var2);

    public void getCellAt(int var1, int var2, int[] var3, nsITreeColumn[] var4, String[] var5);

    public void getCoordsForCellItem(int var1, nsITreeColumn var2, String var3, int[] var4, int[] var5, int[] var6, int[] var7);

    public boolean isCellCropped(int var1, nsITreeColumn var2);

    public void rowCountChanged(int var1, int var2);

    public void beginUpdateBatch();

    public void endUpdateBatch();

    public void clearStyleAndImageCaches();
}

