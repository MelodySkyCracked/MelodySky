/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITreeBoxObject;

public interface nsITreeSelection
extends nsISupports {
    public static final String NS_ITREESELECTION_IID = "{f8a13364-184e-4da3-badf-5c04837537f8}";

    public nsITreeBoxObject getTree();

    public void setTree(nsITreeBoxObject var1);

    public boolean getSingle();

    public int getCount();

    public boolean isSelected(int var1);

    public void select(int var1);

    public void timedSelect(int var1, int var2);

    public void toggleSelect(int var1);

    public void rangedSelect(int var1, int var2, boolean var3);

    public void clearRange(int var1, int var2);

    public void clearSelection();

    public void invertSelection();

    public void selectAll();

    public int getRangeCount();

    public void getRangeAt(int var1, int[] var2, int[] var3);

    public void invalidateSelection();

    public void adjustSelection(int var1, int var2);

    public boolean getSelectEventsSuppressed();

    public void setSelectEventsSuppressed(boolean var1);

    public int getCurrentIndex();

    public void setCurrentIndex(int var1);

    public int getShiftSelectPivot();
}

