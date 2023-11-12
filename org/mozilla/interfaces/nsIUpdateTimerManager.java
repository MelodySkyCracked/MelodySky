/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITimerCallback;

public interface nsIUpdateTimerManager
extends nsISupports {
    public static final String NS_IUPDATETIMERMANAGER_IID = "{0765c92c-6145-4253-9db4-594d8023087e}";

    public void registerTimer(String var1, nsITimerCallback var2, long var3);
}

