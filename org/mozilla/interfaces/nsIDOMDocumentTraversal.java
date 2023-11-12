/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsIDOMNodeIterator;
import org.mozilla.interfaces.nsIDOMTreeWalker;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDocumentTraversal
extends nsISupports {
    public static final String NS_IDOMDOCUMENTTRAVERSAL_IID = "{13f236c0-47f8-11d5-b6a3-009027446e84}";

    public nsIDOMNodeIterator createNodeIterator(nsIDOMNode var1, long var2, nsIDOMNodeFilter var4, boolean var5);

    public nsIDOMTreeWalker createTreeWalker(nsIDOMNode var1, long var2, nsIDOMNodeFilter var4, boolean var5);
}

