/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsISupports;

public interface nsISelection
extends nsISupports {
    public static final String NS_ISELECTION_IID = "{b2c7ed59-8634-4352-9e37-5484c8b6e4e1}";

    public nsIDOMNode getAnchorNode();

    public int getAnchorOffset();

    public nsIDOMNode getFocusNode();

    public int getFocusOffset();

    public boolean getIsCollapsed();

    public int getRangeCount();

    public nsIDOMRange getRangeAt(int var1);

    public void collapse(nsIDOMNode var1, int var2);

    public void extend(nsIDOMNode var1, int var2);

    public void collapseToStart();

    public void collapseToEnd();

    public boolean containsNode(nsIDOMNode var1, boolean var2);

    public void selectAllChildren(nsIDOMNode var1);

    public void addRange(nsIDOMRange var1);

    public void removeRange(nsIDOMRange var1);

    public void removeAllRanges();

    public void deleteFromDocument();

    public void selectionLanguageChange(boolean var1);

    public String toString();
}

