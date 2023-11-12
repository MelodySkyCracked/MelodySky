/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;
import org.mozilla.interfaces.nsITreeBoxObject;
import org.mozilla.interfaces.nsITreeColumn;
import org.mozilla.interfaces.nsITreeSelection;

public interface nsITreeView
extends nsISupports {
    public static final String NS_ITREEVIEW_IID = "{22f034b7-a879-43ad-baee-ba6fd4d466ce}";
    public static final short DROP_BEFORE = -1;
    public static final short DROP_ON = 0;
    public static final short DROP_AFTER = 1;
    public static final short PROGRESS_NORMAL = 1;
    public static final short PROGRESS_UNDETERMINED = 2;
    public static final short PROGRESS_NONE = 3;

    public int getRowCount();

    public nsITreeSelection getSelection();

    public void setSelection(nsITreeSelection var1);

    public void getRowProperties(int var1, nsISupportsArray var2);

    public void getCellProperties(int var1, nsITreeColumn var2, nsISupportsArray var3);

    public void getColumnProperties(nsITreeColumn var1, nsISupportsArray var2);

    public boolean isContainer(int var1);

    public boolean isContainerOpen(int var1);

    public boolean isContainerEmpty(int var1);

    public boolean isSeparator(int var1);

    public boolean isSorted();

    public boolean canDrop(int var1, int var2);

    public void drop(int var1, int var2);

    public int getParentIndex(int var1);

    public boolean hasNextSibling(int var1, int var2);

    public int getLevel(int var1);

    public String getImageSrc(int var1, nsITreeColumn var2);

    public int getProgressMode(int var1, nsITreeColumn var2);

    public String getCellValue(int var1, nsITreeColumn var2);

    public String getCellText(int var1, nsITreeColumn var2);

    public void setTree(nsITreeBoxObject var1);

    public void toggleOpenState(int var1);

    public void cycleHeader(nsITreeColumn var1);

    public void selectionChanged();

    public void cycleCell(int var1, nsITreeColumn var2);

    public boolean isEditable(int var1, nsITreeColumn var2);

    public void setCellValue(int var1, nsITreeColumn var2, String var3);

    public void setCellText(int var1, nsITreeColumn var2, String var3);

    public void performAction(String var1);

    public void performActionOnRow(String var1, int var2);

    public void performActionOnCell(String var1, int var2, nsITreeColumn var3);
}

