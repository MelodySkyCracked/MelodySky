/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsISelectionListener;
import org.mozilla.interfaces.nsISupports;

public interface nsISelectionPrivate
extends nsISupports {
    public static final String NS_ISELECTIONPRIVATE_IID = "{3225ca54-d7e1-4ff5-8ee9-091b0bfcda1f}";
    public static final short ENDOFPRECEDINGLINE = 0;
    public static final short STARTOFNEXTLINE = 1;
    public static final int TABLESELECTION_NONE = 0;
    public static final int TABLESELECTION_CELL = 1;
    public static final int TABLESELECTION_ROW = 2;
    public static final int TABLESELECTION_COLUMN = 3;
    public static final int TABLESELECTION_TABLE = 4;
    public static final int TABLESELECTION_ALLCELLS = 5;

    public boolean getInterlinePosition();

    public void setInterlinePosition(boolean var1);

    public void startBatchChanges();

    public void endBatchChanges();

    public nsIEnumerator getEnumerator();

    public String toStringWithFormat(String var1, long var2, int var4);

    public void addSelectionListener(nsISelectionListener var1);

    public void removeSelectionListener(nsISelectionListener var1);

    public int getTableSelectionType(nsIDOMRange var1);
}

