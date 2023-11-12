/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProtocolProxyService;
import org.mozilla.interfaces.nsIProxyInfo;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIProtocolProxyFilter
extends nsISupports {
    public static final String NS_IPROTOCOLPROXYFILTER_IID = "{f424abd3-32b4-456c-9f45-b7e3376cb0d1}";

    public nsIProxyInfo applyFilter(nsIProtocolProxyService var1, nsIURI var2, nsIProxyInfo var3);
}

