/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGElement;
import org.mozilla.interfaces.nsIDOMSVGMatrix;
import org.mozilla.interfaces.nsIDOMSVGRect;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGLocatable
extends nsISupports {
    public static final String NS_IDOMSVGLOCATABLE_IID = "{9cf4fc9c-90b2-4d66-88f5-35049b558aee}";

    public nsIDOMSVGElement getNearestViewportElement();

    public nsIDOMSVGElement getFarthestViewportElement();

    public nsIDOMSVGRect getBBox();

    public nsIDOMSVGMatrix getCTM();

    public nsIDOMSVGMatrix getScreenCTM();

    public nsIDOMSVGMatrix getTransformToElement(nsIDOMSVGElement var1);
}

