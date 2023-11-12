/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNodeList
extends nsISupports {
    public static final String NS_IDOMNODELIST_IID = "{a6cf907d-15b3-11d2-932e-00805f8add32}";

    public nsIDOMNode item(long var1);

    public long getLength();
}

