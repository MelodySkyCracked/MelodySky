/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGLength;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedLength
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDLENGTH_IID = "{a52f0322-7f4d-418d-af6d-a7b14abd5cdf}";

    public nsIDOMSVGLength getBaseVal();

    public nsIDOMSVGLength getAnimVal();
}

