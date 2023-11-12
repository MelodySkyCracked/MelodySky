/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMUserDataHandler;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIDOM3Node
extends nsISupports {
    public static final String NS_IDOM3NODE_IID = "{29fb2a18-1dd2-11b2-8dd9-a6fd5d5ad12f}";
    public static final int DOCUMENT_POSITION_DISCONNECTED = 1;
    public static final int DOCUMENT_POSITION_PRECEDING = 2;
    public static final int DOCUMENT_POSITION_FOLLOWING = 4;
    public static final int DOCUMENT_POSITION_CONTAINS = 8;
    public static final int DOCUMENT_POSITION_CONTAINED_BY = 16;
    public static final int DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;

    public String getBaseURI();

    public int compareDocumentPosition(nsIDOMNode var1);

    public String getTextContent();

    public void setTextContent(String var1);

    public boolean isSameNode(nsIDOMNode var1);

    public String lookupPrefix(String var1);

    public boolean isDefaultNamespace(String var1);

    public String lookupNamespaceURI(String var1);

    public boolean isEqualNode(nsIDOMNode var1);

    public nsISupports getFeature(String var1, String var2);

    public nsIVariant setUserData(String var1, nsIVariant var2, nsIDOMUserDataHandler var3);

    public nsIVariant getUserData(String var1);
}

