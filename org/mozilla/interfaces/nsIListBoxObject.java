/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIListBoxObject
extends nsISupports {
    public static final String NS_ILISTBOXOBJECT_IID = "{fde7c970-0b4e-49f4-b1eb-974ae6c96336}";

    public nsIListBoxObject getListboxBody();

    public int getRowCount();

    public int getNumberOfVisibleRows();

    public int getIndexOfFirstVisibleRow();

    public void ensureIndexIsVisible(int var1);

    public void scrollToIndex(int var1);

    public void scrollByLines(int var1);

    public nsIDOMElement getItemAtIndex(int var1);

    public int getIndexOfItem(nsIDOMElement var1);
}

