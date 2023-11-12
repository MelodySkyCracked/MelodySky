/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShellTreeOwner;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocShellTreeItem
extends nsISupports {
    public static final String NS_IDOCSHELLTREEITEM_IID = "{7d935d63-6d2a-4600-afb5-9a4f7d68b825}";
    public static final int typeChrome = 0;
    public static final int typeContent = 1;
    public static final int typeContentWrapper = 2;
    public static final int typeChromeWrapper = 3;
    public static final int typeAll = Integer.MAX_VALUE;

    public String getName();

    public void setName(String var1);

    public boolean nameEquals(String var1);

    public int getItemType();

    public void setItemType(int var1);

    public nsIDocShellTreeItem getParent();

    public nsIDocShellTreeItem getSameTypeParent();

    public nsIDocShellTreeItem getRootTreeItem();

    public nsIDocShellTreeItem getSameTypeRootTreeItem();

    public nsIDocShellTreeItem findItemWithName(String var1, nsISupports var2, nsIDocShellTreeItem var3);

    public nsIDocShellTreeOwner getTreeOwner();

    public int getChildOffset();

    public void setChildOffset(int var1);
}

