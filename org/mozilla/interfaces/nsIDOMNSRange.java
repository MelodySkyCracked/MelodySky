/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocumentFragment;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSRange
extends nsISupports {
    public static final String NS_IDOMNSRANGE_IID = "{a6cf90f2-15b3-11d2-932e-00805f8add32}";
    public static final int NODE_BEFORE = 0;
    public static final int NODE_AFTER = 1;
    public static final int NODE_BEFORE_AND_AFTER = 2;
    public static final int NODE_INSIDE = 3;

    public nsIDOMDocumentFragment createContextualFragment(String var1);

    public boolean isPointInRange(nsIDOMNode var1, int var2);

    public short comparePoint(nsIDOMNode var1, int var2);

    public boolean intersectsNode(nsIDOMNode var1);

    public int compareNode(nsIDOMNode var1);

    public void nSDetach();
}

