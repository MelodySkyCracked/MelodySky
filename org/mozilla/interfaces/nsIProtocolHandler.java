/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIProtocolHandler
extends nsISupports {
    public static final String NS_IPROTOCOLHANDLER_IID = "{15fd6940-8ea7-11d3-93ad-00104ba0fd40}";
    public static final long URI_STD = 0L;
    public static final long URI_NORELATIVE = 1L;
    public static final long URI_NOAUTH = 2L;
    public static final long ALLOWS_PROXY = 4L;
    public static final long ALLOWS_PROXY_HTTP = 8L;

    public String getScheme();

    public int getDefaultPort();

    public long getProtocolFlags();

    public nsIURI newURI(String var1, String var2, nsIURI var3);

    public nsIChannel newChannel(nsIURI var1);

    public boolean allowPort(int var1, String var2);
}

