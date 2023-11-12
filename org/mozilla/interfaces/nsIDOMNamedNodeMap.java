/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNamedNodeMap
extends nsISupports {
    public static final String NS_IDOMNAMEDNODEMAP_IID = "{a6cf907b-15b3-11d2-932e-00805f8add32}";

    public nsIDOMNode getNamedItem(String var1);

    public nsIDOMNode setNamedItem(nsIDOMNode var1);

    public nsIDOMNode removeNamedItem(String var1);

    public nsIDOMNode item(long var1);

    public long getLength();

    public nsIDOMNode getNamedItemNS(String var1, String var2);

    public nsIDOMNode setNamedItemNS(nsIDOMNode var1);

    public nsIDOMNode removeNamedItemNS(String var1, String var2);
}

