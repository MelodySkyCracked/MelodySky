/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventQueue;
import org.mozilla.interfaces.nsISupports;

public interface nsIProxyObjectManager
extends nsISupports {
    public static final String NS_IPROXYOBJECTMANAGER_IID = "{eea90d43-b059-11d2-915e-c12b696c9333}";
    public static final int INVOKE_SYNC = 1;
    public static final int INVOKE_ASYNC = 2;
    public static final int FORCE_PROXY_CREATION = 4;

    public nsISupports getProxyForObject(nsIEventQueue var1, String var2, nsISupports var3, int var4);

    public nsISupports getProxy(nsIEventQueue var1, String var2, nsISupports var3, String var4, int var5);
}

