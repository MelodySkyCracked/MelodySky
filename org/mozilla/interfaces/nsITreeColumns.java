/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITreeBoxObject;
import org.mozilla.interfaces.nsITreeColumn;

public interface nsITreeColumns
extends nsISupports {
    public static final String NS_ITREECOLUMNS_IID = "{fcc7b6b5-f7d7-4e57-abd1-080602deb21d}";

    public nsITreeBoxObject getTree();

    public int getCount();

    public nsITreeColumn getFirstColumn();

    public nsITreeColumn getLastColumn();

    public nsITreeColumn getPrimaryColumn();

    public nsITreeColumn getSortedColumn();

    public nsITreeColumn getKeyColumn();

    public nsITreeColumn getColumnFor(nsIDOMElement var1);

    public nsITreeColumn getNamedColumn(String var1);

    public nsITreeColumn getColumnAt(int var1);

    public void invalidateColumns();

    public void restoreNaturalOrder();
}

