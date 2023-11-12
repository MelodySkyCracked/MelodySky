/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGAnimatedPreserveAspectRatio;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGImageElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGIMAGEELEMENT_IID = "{43ae4efe-2610-4cce-8242-279e556a78fa}";

    public nsIDOMSVGAnimatedLength getX();

    public nsIDOMSVGAnimatedLength getY();

    public nsIDOMSVGAnimatedLength getWidth();

    public nsIDOMSVGAnimatedLength getHeight();

    public nsIDOMSVGAnimatedPreserveAspectRatio getPreserveAspectRatio();
}

