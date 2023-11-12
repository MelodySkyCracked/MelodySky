/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShellTreeItem;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocShellTreeNode
extends nsISupports {
    public static final String NS_IDOCSHELLTREENODE_IID = "{37f1ab73-f224-44b1-82f0-d2834ab1cec0}";

    public int getChildCount();

    public void addChild(nsIDocShellTreeItem var1);

    public void removeChild(nsIDocShellTreeItem var1);

    public nsIDocShellTreeItem getChildAt(int var1);

    public nsIDocShellTreeItem findChildWithName(String var1, boolean var2, boolean var3, nsIDocShellTreeItem var4, nsIDocShellTreeItem var5);
}

