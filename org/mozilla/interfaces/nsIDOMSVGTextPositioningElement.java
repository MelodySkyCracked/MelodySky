/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLengthList;
import org.mozilla.interfaces.nsIDOMSVGAnimatedNumberList;
import org.mozilla.interfaces.nsIDOMSVGTextContentElement;

public interface nsIDOMSVGTextPositioningElement
extends nsIDOMSVGTextContentElement {
    public static final String NS_IDOMSVGTEXTPOSITIONINGELEMENT_IID = "{5d052835-8cb0-442c-9754-a8e616db1f89}";

    public nsIDOMSVGAnimatedLengthList getX();

    public nsIDOMSVGAnimatedLengthList getY();

    public nsIDOMSVGAnimatedLengthList getDx();

    public nsIDOMSVGAnimatedLengthList getDy();

    public nsIDOMSVGAnimatedNumberList getRotate();
}

