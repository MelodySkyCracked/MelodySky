/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGRectElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGRECTELEMENT_IID = "{1695ca39-e40d-44dc-81db-a51b6fd234fa}";

    public nsIDOMSVGAnimatedLength getX();

    public nsIDOMSVGAnimatedLength getY();

    public nsIDOMSVGAnimatedLength getWidth();

    public nsIDOMSVGAnimatedLength getHeight();

    public nsIDOMSVGAnimatedLength getRx();

    public nsIDOMSVGAnimatedLength getRy();
}

