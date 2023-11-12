/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedString
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDSTRING_IID = "{36f18f12-61a9-4529-8fa9-30050bd6ac00}";

    public String getBaseVal();

    public void setBaseVal(String var1);

    public String getAnimVal();
}

