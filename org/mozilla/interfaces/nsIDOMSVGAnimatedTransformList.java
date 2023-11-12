/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGTransformList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedTransformList
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDTRANSFORMLIST_IID = "{fd54c8c4-2eb4-4849-8df6-79985c2491da}";

    public nsIDOMSVGTransformList getBaseVal();

    public nsIDOMSVGTransformList getAnimVal();
}

