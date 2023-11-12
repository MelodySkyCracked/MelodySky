/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMHTMLCollection
extends nsISupports {
    public static final String NS_IDOMHTMLCOLLECTION_IID = "{a6cf9083-15b3-11d2-932e-00805f8add32}";

    public long getLength();

    public nsIDOMNode item(long var1);

    public nsIDOMNode namedItem(String var1);
}

