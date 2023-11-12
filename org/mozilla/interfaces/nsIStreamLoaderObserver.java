/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIStreamLoader;
import org.mozilla.interfaces.nsISupports;

public interface nsIStreamLoaderObserver
extends nsISupports {
    public static final String NS_ISTREAMLOADEROBSERVER_IID = "{359f7990-d4e9-11d3-a1a5-0050041caf44}";

    public void onStreamComplete(nsIStreamLoader var1, nsISupports var2, long var3, long var5, byte[] var7);
}

