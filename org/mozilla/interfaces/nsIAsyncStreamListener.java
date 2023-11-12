/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventQueue;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsIAsyncStreamListener
extends nsIStreamListener {
    public static final String NS_IASYNCSTREAMLISTENER_IID = "{1b012ade-91bf-11d3-8cd9-0060b0fc14a3}";

    public void init(nsIStreamListener var1, nsIEventQueue var2);
}

