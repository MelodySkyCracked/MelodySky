/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIAppStartup
extends nsISupports {
    public static final String NS_IAPPSTARTUP_IID = "{e9b0f89b-0529-4d96-98a8-eb5b2b9a8383}";
    public static final long eConsiderQuit = 1L;
    public static final long eAttemptQuit = 2L;
    public static final long eForceQuit = 3L;
    public static final long eRestart = 16L;

    public void createHiddenWindow();

    public void run();

    public void enterLastWindowClosingSurvivalArea();

    public void exitLastWindowClosingSurvivalArea();

    public void quit(long var1);
}

