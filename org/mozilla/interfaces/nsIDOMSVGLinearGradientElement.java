/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGGradientElement;

public interface nsIDOMSVGLinearGradientElement
extends nsIDOMSVGGradientElement {
    public static final String NS_IDOMSVGLINEARGRADIENTELEMENT_IID = "{7e15fce5-b208-43e1-952a-c570ebad0619}";

    public nsIDOMSVGAnimatedLength getX1();

    public nsIDOMSVGAnimatedLength getY1();

    public nsIDOMSVGAnimatedLength getX2();

    public nsIDOMSVGAnimatedLength getY2();
}

