/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProfileStartupListener
extends nsISupports {
    public static final String NS_IPROFILESTARTUPLISTENER_IID = "{6962ca8f-0b8b-11d4-9875-00c04fa0d28b}";

    public void onProfileStartup(String var1);
}

