/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPointList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedPoints
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDPOINTS_IID = "{ebf334b3-86ef-4bf3-8a92-d775c72defa4}";

    public nsIDOMSVGPointList getPoints();

    public nsIDOMSVGPointList getAnimatedPoints();
}

