/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGNumberList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedNumberList
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDNUMBERLIST_IID = "{93ebb030-f82d-4f8e-b133-d1b5abb73cf3}";

    public nsIDOMSVGNumberList getBaseVal();

    public nsIDOMSVGNumberList getAnimVal();
}

