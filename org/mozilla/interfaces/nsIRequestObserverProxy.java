/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventQueue;
import org.mozilla.interfaces.nsIRequestObserver;

public interface nsIRequestObserverProxy
extends nsIRequestObserver {
    public static final String NS_IREQUESTOBSERVERPROXY_IID = "{3c9b532e-db84-4ecf-aa6a-4d38a9c4c5f0}";

    public void init(nsIRequestObserver var1, nsIEventQueue var2);
}

