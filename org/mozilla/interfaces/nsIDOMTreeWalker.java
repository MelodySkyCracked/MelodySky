/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMTreeWalker
extends nsISupports {
    public static final String NS_IDOMTREEWALKER_IID = "{400af3ca-1dd2-11b2-a50a-887ecca2e63a}";

    public nsIDOMNode getRoot();

    public long getWhatToShow();

    public nsIDOMNodeFilter getFilter();

    public boolean getExpandEntityReferences();

    public nsIDOMNode getCurrentNode();

    public void setCurrentNode(nsIDOMNode var1);

    public nsIDOMNode parentNode();

    public nsIDOMNode firstChild();

    public nsIDOMNode lastChild();

    public nsIDOMNode previousSibling();

    public nsIDOMNode nextSibling();

    public nsIDOMNode previousNode();

    public nsIDOMNode nextNode();
}

