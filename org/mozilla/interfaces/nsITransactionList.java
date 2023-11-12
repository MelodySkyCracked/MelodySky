/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransaction;

public interface nsITransactionList
extends nsISupports {
    public static final String NS_ITRANSACTIONLIST_IID = "{97f863f3-f886-11d4-9d39-0060b0f8baff}";

    public int getNumItems();

    public boolean itemIsBatch(int var1);

    public nsITransaction getItem(int var1);

    public int getNumChildrenForItem(int var1);

    public nsITransactionList getChildListForItem(int var1);
}

