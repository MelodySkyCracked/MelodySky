/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProxyInfo
extends nsISupports {
    public static final String NS_IPROXYINFO_IID = "{3fe9308b-1608-4fa0-933c-c5ec2c6175fd}";
    public static final int TRANSPARENT_PROXY_RESOLVES_HOST = 1;

    public String getHost();

    public int getPort();

    public String getType();

    public long getFlags();

    public long getFailoverTimeout();

    public nsIProxyInfo getFailoverProxy();

    public void setFailoverProxy(nsIProxyInfo var1);
}

