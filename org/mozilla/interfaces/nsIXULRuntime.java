/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXULRuntime
extends nsISupports {
    public static final String NS_IXULRUNTIME_IID = "{2848ab92-d912-11d9-89f7-001124787b2e}";

    public boolean getInSafeMode();

    public boolean getLogConsoleErrors();

    public void setLogConsoleErrors(boolean var1);

    public String getOS();

    public String getXPCOMABI();
}

