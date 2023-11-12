/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGUseElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGUSEELEMENT_IID = "{d49a3ac7-e779-46c8-ae92-214420aa1b71}";

    public nsIDOMSVGAnimatedLength getX();

    public nsIDOMSVGAnimatedLength getY();

    public nsIDOMSVGAnimatedLength getWidth();

    public nsIDOMSVGAnimatedLength getHeight();
}

