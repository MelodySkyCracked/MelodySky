/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGForeignObjectElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGFOREIGNOBJECTELEMENT_IID = "{fd9c9871-23fd-48eb-a65b-3842e9b0acbd}";

    public nsIDOMSVGAnimatedLength getX();

    public nsIDOMSVGAnimatedLength getY();

    public nsIDOMSVGAnimatedLength getWidth();

    public nsIDOMSVGAnimatedLength getHeight();
}

