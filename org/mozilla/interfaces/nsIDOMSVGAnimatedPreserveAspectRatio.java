/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPreserveAspectRatio;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedPreserveAspectRatio
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDPRESERVEASPECTRATIO_IID = "{afcd7cd4-d74d-492f-b3b1-d71bfa36874f}";

    public nsIDOMSVGPreserveAspectRatio getBaseVal();

    public nsIDOMSVGPreserveAspectRatio getAnimVal();
}

