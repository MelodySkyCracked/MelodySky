/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedNumber
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDNUMBER_IID = "{716e3b11-b03b-49f7-b82d-5383922b0ab3}";

    public float getBaseVal();

    public void setBaseVal(float var1);

    public float getAnimVal();
}

