/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGGradientElement;

public interface nsIDOMSVGRadialGradientElement
extends nsIDOMSVGGradientElement {
    public static final String NS_IDOMSVGRADIALGRADIENTELEMENT_IID = "{d0262ae1-31a4-44be-b82e-85e4cfe280fd}";

    public nsIDOMSVGAnimatedLength getCx();

    public nsIDOMSVGAnimatedLength getCy();

    public nsIDOMSVGAnimatedLength getR();

    public nsIDOMSVGAnimatedLength getFx();

    public nsIDOMSVGAnimatedLength getFy();
}

