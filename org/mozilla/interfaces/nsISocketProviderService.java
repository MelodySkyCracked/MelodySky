/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISocketProvider;
import org.mozilla.interfaces.nsISupports;

public interface nsISocketProviderService
extends nsISupports {
    public static final String NS_ISOCKETPROVIDERSERVICE_IID = "{8f8a23d0-5472-11d3-bbc8-0000861d1237}";

    public nsISocketProvider getSocketProvider(String var1);
}

