/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIExtensionManager;
import org.mozilla.interfaces.nsIUpdateItem;

public interface nsIExtensionManager_MOZILLA_1_8_BRANCH
extends nsIExtensionManager {
    public static final String NS_IEXTENSIONMANAGER_MOZILLA_1_8_BRANCH_IID = "{0fd5caa9-1ffc-42b1-aea1-3fbe73116070}";

    public void cancelUninstallItem(String var1);

    public nsIUpdateItem[] getDependentItemListForID(String var1, boolean var2, long[] var3);

    public void checkForBlocklistChanges();

    public void sortTypeByProperty(long var1, String var3, boolean var4);
}

