/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGCircleElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGCIRCLEELEMENT_IID = "{0f89f2a4-b168-4602-90f5-1874418c0a6a}";

    public nsIDOMSVGAnimatedLength getCx();

    public nsIDOMSVGAnimatedLength getCy();

    public nsIDOMSVGAnimatedLength getR();
}

