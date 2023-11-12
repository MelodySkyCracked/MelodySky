/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProxyInfo;
import org.mozilla.interfaces.nsISocketTransport;
import org.mozilla.interfaces.nsISupports;

public interface nsISocketTransportService
extends nsISupports {
    public static final String NS_ISOCKETTRANSPORTSERVICE_IID = "{7b19ac06-a5fb-11d9-9f82-0011246ecd24}";

    public nsISocketTransport createTransport(String[] var1, long var2, String var4, int var5, nsIProxyInfo var6);
}

