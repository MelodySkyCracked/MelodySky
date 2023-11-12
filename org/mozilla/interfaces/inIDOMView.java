/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface inIDOMView
extends nsISupports {
    public static final String INIDOMVIEW_IID = "{3eb4c760-dffd-4983-94a8-18bcb99100e4}";

    public nsIDOMNode getRootNode();

    public void setRootNode(nsIDOMNode var1);

    public boolean getShowAnonymousContent();

    public void setShowAnonymousContent(boolean var1);

    public boolean getShowSubDocuments();

    public void setShowSubDocuments(boolean var1);

    public boolean getShowWhitespaceNodes();

    public void setShowWhitespaceNodes(boolean var1);

    public long getWhatToShow();

    public void setWhatToShow(long var1);

    public nsIDOMNode getNodeFromRowIndex(int var1);

    public int getRowIndexFromNode(nsIDOMNode var1);

    public void rebuild();
}

