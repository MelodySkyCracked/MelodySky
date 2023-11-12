/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsISSLSocketControl
extends nsISupports {
    public static final String NS_ISSLSOCKETCONTROL_IID = "{8b3e8488-1dd2-11b2-b547-956290be347c}";

    public nsIInterfaceRequestor getNotificationCallbacks();

    public void setNotificationCallbacks(nsIInterfaceRequestor var1);

    public boolean getForceHandshake();

    public void setForceHandshake(boolean var1);

    public void proxyStartSSL();

    public void startTLS();
}

