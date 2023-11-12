/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIServerSocket;
import org.mozilla.interfaces.nsISocketTransport;
import org.mozilla.interfaces.nsISupports;

public interface nsIServerSocketListener
extends nsISupports {
    public static final String NS_ISERVERSOCKETLISTENER_IID = "{836d98ec-fee2-4bde-b609-abd5e966eabd}";

    public void onSocketAccepted(nsIServerSocket var1, nsISocketTransport var2);

    public void onStopListening(nsIServerSocket var1, long var2);
}

