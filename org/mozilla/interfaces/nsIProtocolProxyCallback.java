/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIProxyInfo;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIProtocolProxyCallback
extends nsISupports {
    public static final String NS_IPROTOCOLPROXYCALLBACK_IID = "{a9967200-f95e-45c2-beb3-9b060d874bfd}";

    public void onProxyAvailable(nsICancelable var1, nsIURI var2, nsIProxyInfo var3, long var4);
}

