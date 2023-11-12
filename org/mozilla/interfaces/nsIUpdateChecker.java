/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdateCheckListener;

public interface nsIUpdateChecker
extends nsISupports {
    public static final String NS_IUPDATECHECKER_IID = "{877ace25-8bc5-452a-8586-9c1cf2871994}";
    public static final int CURRENT_CHECK = 1;
    public static final int CURRENT_SESSION = 2;
    public static final int ANY_CHECKS = 3;

    public void checkForUpdates(nsIUpdateCheckListener var1, boolean var2);

    public void stopChecking(int var1);
}

