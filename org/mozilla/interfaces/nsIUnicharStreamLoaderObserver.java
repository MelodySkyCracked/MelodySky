/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUnicharInputStream;
import org.mozilla.interfaces.nsIUnicharStreamLoader;

public interface nsIUnicharStreamLoaderObserver
extends nsISupports {
    public static final String NS_IUNICHARSTREAMLOADEROBSERVER_IID = "{e06e8b08-8cdd-4503-a0a0-6f3b943602af}";

    public String onDetermineCharset(nsIUnicharStreamLoader var1, nsISupports var2, String var3, long var4);

    public void onStreamComplete(nsIUnicharStreamLoader var1, nsISupports var2, long var3, nsIUnicharInputStream var5);
}

