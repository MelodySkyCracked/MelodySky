/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedEnumeration
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDENUMERATION_IID = "{73b101bd-797b-470f-9308-c24c64278bcd}";

    public int getBaseVal();

    public void setBaseVal(int var1);

    public int getAnimVal();
}

