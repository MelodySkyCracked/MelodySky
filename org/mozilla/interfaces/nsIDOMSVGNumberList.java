/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGNumber;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGNumberList
extends nsISupports {
    public static final String NS_IDOMSVGNUMBERLIST_IID = "{59364ec4-faf1-460f-bf58-e6a6a2769a3a}";

    public long getNumberOfItems();

    public void clear();

    public nsIDOMSVGNumber initialize(nsIDOMSVGNumber var1);

    public nsIDOMSVGNumber getItem(long var1);

    public nsIDOMSVGNumber insertItemBefore(nsIDOMSVGNumber var1, long var2);

    public nsIDOMSVGNumber replaceItem(nsIDOMSVGNumber var1, long var2);

    public nsIDOMSVGNumber removeItem(long var1);

    public nsIDOMSVGNumber appendItem(nsIDOMSVGNumber var1);
}

