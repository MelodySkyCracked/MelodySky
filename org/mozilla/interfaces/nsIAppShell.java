/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIAppShell
extends nsISupports {
    public static final String NS_IAPPSHELL_IID = "{a0757c31-eeac-11d1-9ec1-00aa002fb821}";

    public void create(int[] var1, String[] var2);

    public void run();

    public void spinup();

    public void spindown();

    public void listenToEventQueue(long var1, boolean var3);

    public void getNativeEvent(boolean var1, long var2);

    public void dispatchNativeEvent(boolean var1, long var2);

    public void exit();
}

