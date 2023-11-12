/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAccessibleDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessNode
extends nsISupports {
    public static final String NS_IACCESSNODE_IID = "{46820f9b-3088-4046-ab0f-56fdacdc7a82}";

    public nsIDOMNode getDOMNode();

    public int getNumChildren();

    public nsIAccessNode getChildNodeAt(int var1);

    public nsIAccessNode getParentNode();

    public nsIAccessNode getFirstChildNode();

    public nsIAccessNode getLastChildNode();

    public nsIAccessNode getPreviousSiblingNode();

    public nsIAccessNode getNextSiblingNode();

    public nsIAccessibleDocument getAccessibleDocument();

    public String getInnerHTML();

    public String getComputedStyleValue(String var1, String var2);
}

