/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGRect;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedRect
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDRECT_IID = "{ca45959e-f1da-46f6-af19-1ecdc322285a}";

    public nsIDOMSVGRect getBaseVal();

    public nsIDOMSVGRect getAnimVal();
}

