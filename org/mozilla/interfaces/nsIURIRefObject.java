/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIURIRefObject
extends nsISupports {
    public static final String NS_IURIREFOBJECT_IID = "{2226927e-1dd2-11b2-b57f-faab47288563}";

    public nsIDOMNode getNode();

    public void setNode(nsIDOMNode var1);

    public void reset();

    public String getNextURI();

    public void rewriteAllURIs(String var1, String var2, boolean var3);
}

