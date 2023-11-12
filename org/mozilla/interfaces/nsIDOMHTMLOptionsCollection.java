/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMHTMLOptionsCollection
extends nsISupports {
    public static final String NS_IDOMHTMLOPTIONSCOLLECTION_IID = "{bce0213c-f70f-488f-b93f-688acca55d63}";

    public long getLength();

    public void setLength(long var1);

    public nsIDOMNode item(long var1);

    public nsIDOMNode namedItem(String var1);
}

