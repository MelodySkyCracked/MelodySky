/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGLineElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGLINEELEMENT_IID = "{4ea07ef3-ed66-4b41-8119-4afc6d0ed5af}";

    public nsIDOMSVGAnimatedLength getX1();

    public nsIDOMSVGAnimatedLength getY1();

    public nsIDOMSVGAnimatedLength getX2();

    public nsIDOMSVGAnimatedLength getY2();
}

