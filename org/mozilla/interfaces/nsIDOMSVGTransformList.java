/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGMatrix;
import org.mozilla.interfaces.nsIDOMSVGTransform;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGTransformList
extends nsISupports {
    public static final String NS_IDOMSVGTRANSFORMLIST_IID = "{df41474c-a4f8-4ec3-ae79-4342e6f56d8e}";

    public long getNumberOfItems();

    public void clear();

    public nsIDOMSVGTransform initialize(nsIDOMSVGTransform var1);

    public nsIDOMSVGTransform getItem(long var1);

    public nsIDOMSVGTransform insertItemBefore(nsIDOMSVGTransform var1, long var2);

    public nsIDOMSVGTransform replaceItem(nsIDOMSVGTransform var1, long var2);

    public nsIDOMSVGTransform removeItem(long var1);

    public nsIDOMSVGTransform appendItem(nsIDOMSVGTransform var1);

    public nsIDOMSVGTransform createSVGTransformFromMatrix(nsIDOMSVGMatrix var1);

    public nsIDOMSVGTransform consolidate();

    public nsIDOMSVGMatrix getConsolidationMatrix();
}

