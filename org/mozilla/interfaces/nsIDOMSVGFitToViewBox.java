/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedPreserveAspectRatio;
import org.mozilla.interfaces.nsIDOMSVGAnimatedRect;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGFitToViewBox
extends nsISupports {
    public static final String NS_IDOMSVGFITTOVIEWBOX_IID = "{089410f3-9777-44f1-a882-ab4225696434}";

    public nsIDOMSVGAnimatedRect getViewBox();

    public nsIDOMSVGAnimatedPreserveAspectRatio getPreserveAspectRatio();
}

