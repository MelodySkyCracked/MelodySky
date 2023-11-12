/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdate;

public interface nsIUpdateManager
extends nsISupports {
    public static final String NS_IUPDATEMANAGER_IID = "{fede66a9-9f96-4507-a22a-775ee885577e}";

    public nsIUpdate getUpdateAt(int var1);

    public int getUpdateCount();

    public nsIUpdate getActiveUpdate();

    public void setActiveUpdate(nsIUpdate var1);

    public void saveUpdates();
}

