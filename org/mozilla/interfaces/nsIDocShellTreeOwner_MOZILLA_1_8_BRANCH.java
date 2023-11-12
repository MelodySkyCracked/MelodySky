/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShellTreeItem;
import org.mozilla.interfaces.nsIDocShellTreeOwner;

public interface nsIDocShellTreeOwner_MOZILLA_1_8_BRANCH
extends nsIDocShellTreeOwner {
    public static final String NS_IDOCSHELLTREEOWNER_MOZILLA_1_8_BRANCH_IID = "{3c2a6927-e923-4ea8-bbda-a335c768ce4e}";

    public void contentShellAdded2(nsIDocShellTreeItem var1, boolean var2, boolean var3, String var4);

    public void contentShellRemoved(nsIDocShellTreeItem var1);
}

