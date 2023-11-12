/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGLengthList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedLengthList
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDLENGTHLIST_IID = "{bfa6e42b-bc9d-404d-8688-729fdbfff801}";

    public nsIDOMSVGLengthList getBaseVal();

    public nsIDOMSVGLengthList getAnimVal();
}

