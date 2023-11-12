/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGLength;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGLengthList
extends nsISupports {
    public static final String NS_IDOMSVGLENGTHLIST_IID = "{a8760fcd-3de5-446a-a009-5cf877e7a4df}";

    public long getNumberOfItems();

    public void clear();

    public nsIDOMSVGLength initialize(nsIDOMSVGLength var1);

    public nsIDOMSVGLength getItem(long var1);

    public nsIDOMSVGLength insertItemBefore(nsIDOMSVGLength var1, long var2);

    public nsIDOMSVGLength replaceItem(nsIDOMSVGLength var1, long var2);

    public nsIDOMSVGLength removeItem(long var1);

    public nsIDOMSVGLength appendItem(nsIDOMSVGLength var1);
}

