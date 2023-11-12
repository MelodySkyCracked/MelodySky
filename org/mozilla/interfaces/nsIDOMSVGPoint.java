/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGMatrix;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGPoint
extends nsISupports {
    public static final String NS_IDOMSVGPOINT_IID = "{45f18f8f-1315-4447-a7d5-8aeca77bdcaf}";

    public float getX();

    public void setX(float var1);

    public float getY();

    public void setY(float var1);

    public nsIDOMSVGPoint matrixTransform(nsIDOMSVGMatrix var1);
}

