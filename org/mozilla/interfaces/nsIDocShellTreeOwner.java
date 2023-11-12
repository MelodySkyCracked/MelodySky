/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShellTreeItem;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocShellTreeOwner
extends nsISupports {
    public static final String NS_IDOCSHELLTREEOWNER_IID = "{9e508466-5ebb-4618-abfa-9ad47bed0b2e}";

    public nsIDocShellTreeItem findItemWithName(String var1, nsIDocShellTreeItem var2, nsIDocShellTreeItem var3);

    public void contentShellAdded(nsIDocShellTreeItem var1, boolean var2, String var3);

    public nsIDocShellTreeItem getPrimaryContentShell();

    public void sizeShellTo(nsIDocShellTreeItem var1, int var2, int var3);

    public void setPersistence(boolean var1, boolean var2, boolean var3);

    public void getPersistence(boolean[] var1, boolean[] var2, boolean[] var3);
}

