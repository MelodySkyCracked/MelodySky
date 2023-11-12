/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIProtocolHandler;
import org.mozilla.interfaces.nsIProxyInfo;
import org.mozilla.interfaces.nsIURI;

public interface nsIProxiedProtocolHandler
extends nsIProtocolHandler {
    public static final String NS_IPROXIEDPROTOCOLHANDLER_IID = "{0a24fed4-1dd2-11b2-a75c-9f8b9a8f9ba7}";

    public nsIChannel newProxiedChannel(nsIURI var1, nsIProxyInfo var2);
}

