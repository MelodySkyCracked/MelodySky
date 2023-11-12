/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPoint;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGPointList
extends nsISupports {
    public static final String NS_IDOMSVGPOINTLIST_IID = "{4c12af24-0fc2-4fe7-b71d-5d6b41d463c1}";

    public long getNumberOfItems();

    public void clear();

    public nsIDOMSVGPoint initialize(nsIDOMSVGPoint var1);

    public nsIDOMSVGPoint getItem(long var1);

    public nsIDOMSVGPoint insertItemBefore(nsIDOMSVGPoint var1, long var2);

    public nsIDOMSVGPoint replaceItem(nsIDOMSVGPoint var1, long var2);

    public nsIDOMSVGPoint removeItem(long var1);

    public nsIDOMSVGPoint appendItem(nsIDOMSVGPoint var1);
}

