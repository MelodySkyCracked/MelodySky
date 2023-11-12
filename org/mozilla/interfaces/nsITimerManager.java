/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITimerManager
extends nsISupports {
    public static final String NS_ITIMERMANAGER_IID = "{8fce8c6a-1dd2-11b2-8352-8cdd2b965efc}";

    public boolean getUseIdleTimers();

    public void setUseIdleTimers(boolean var1);

    public boolean hasIdleTimers();

    public void fireNextIdleTimer();
}

