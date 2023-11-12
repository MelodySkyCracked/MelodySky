/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIProtocolProxyCallback;
import org.mozilla.interfaces.nsIProtocolProxyFilter;
import org.mozilla.interfaces.nsIProxyInfo;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIProtocolProxyService
extends nsISupports {
    public static final String NS_IPROTOCOLPROXYSERVICE_IID = "{e38ab577-786e-4a7f-936b-7ae4c7d877b2}";
    public static final long RESOLVE_NON_BLOCKING = 1L;

    public nsIProxyInfo resolve(nsIURI var1, long var2);

    public nsICancelable asyncResolve(nsIURI var1, long var2, nsIProtocolProxyCallback var4);

    public nsIProxyInfo newProxyInfo(String var1, String var2, int var3, long var4, long var6, nsIProxyInfo var8);

    public nsIProxyInfo getFailoverForProxy(nsIProxyInfo var1, nsIURI var2, long var3);

    public void registerFilter(nsIProtocolProxyFilter var1, long var2);

    public void unregisterFilter(nsIProtocolProxyFilter var1);
}

