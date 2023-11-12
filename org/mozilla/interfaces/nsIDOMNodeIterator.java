/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNodeIterator
extends nsISupports {
    public static final String NS_IDOMNODEITERATOR_IID = "{354b5f02-1dd2-11b2-b053-b8c2997022a0}";

    public nsIDOMNode getRoot();

    public long getWhatToShow();

    public nsIDOMNodeFilter getFilter();

    public boolean getExpandEntityReferences();

    public nsIDOMNode nextNode();

    public nsIDOMNode previousNode();

    public void detach();
}

