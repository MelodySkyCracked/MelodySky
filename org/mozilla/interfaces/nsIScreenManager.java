/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScreen;
import org.mozilla.interfaces.nsISupports;

public interface nsIScreenManager
extends nsISupports {
    public static final String NS_ISCREENMANAGER_IID = "{662e7b78-1dd2-11b2-a3d3-fc1e5f5fb9d4}";

    public nsIScreen screenForRect(int var1, int var2, int var3, int var4);

    public nsIScreen getPrimaryScreen();

    public long getNumberOfScreens();
}

