/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPathSeg;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGPathSegList
extends nsISupports {
    public static final String NS_IDOMSVGPATHSEGLIST_IID = "{94a6db98-3f34-4529-a35f-89ef49713795}";

    public long getNumberOfItems();

    public void clear();

    public nsIDOMSVGPathSeg initialize(nsIDOMSVGPathSeg var1);

    public nsIDOMSVGPathSeg getItem(long var1);

    public nsIDOMSVGPathSeg insertItemBefore(nsIDOMSVGPathSeg var1, long var2);

    public nsIDOMSVGPathSeg replaceItem(nsIDOMSVGPathSeg var1, long var2);

    public nsIDOMSVGPathSeg removeItem(long var1);

    public nsIDOMSVGPathSeg appendItem(nsIDOMSVGPathSeg var1);
}

