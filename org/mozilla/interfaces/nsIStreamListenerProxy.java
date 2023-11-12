/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventQueue;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsIStreamListenerProxy
extends nsIStreamListener {
    public static final String NS_ISTREAMLISTENERPROXY_IID = "{e400e688-6b54-4a84-8c4e-56b40281981a}";

    public void init(nsIStreamListener var1, nsIEventQueue var2, long var3, long var5);
}

