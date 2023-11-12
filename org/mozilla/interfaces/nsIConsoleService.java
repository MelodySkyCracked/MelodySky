/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIConsoleListener;
import org.mozilla.interfaces.nsIConsoleMessage;
import org.mozilla.interfaces.nsISupports;

public interface nsIConsoleService
extends nsISupports {
    public static final String NS_ICONSOLESERVICE_IID = "{a647f184-1dd1-11b2-a9d1-8537b201161b}";

    public void logMessage(nsIConsoleMessage var1);

    public void logStringMessage(String var1);

    public void getMessageArray(nsIConsoleMessage[][] var1, long[] var2);

    public void registerListener(nsIConsoleListener var1);

    public void unregisterListener(nsIConsoleListener var1);
}

