/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPCall;
import org.mozilla.interfaces.nsISOAPCallCompletion;
import org.mozilla.interfaces.nsISOAPResponse;
import org.mozilla.interfaces.nsISOAPResponseListener;
import org.mozilla.interfaces.nsISOAPTransportListener;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPTransport
extends nsISupports {
    public static final String NS_ISOAPTRANSPORT_IID = "{99ec6695-535f-11d4-9a58-000064657374}";

    public void syncCall(nsISOAPCall var1, nsISOAPResponse var2);

    public nsISOAPCallCompletion asyncCall(nsISOAPCall var1, nsISOAPResponseListener var2, nsISOAPResponse var3);

    public void addListener(nsISOAPTransportListener var1, boolean var2);

    public void removeListener(nsISOAPTransportListener var1, boolean var2);
}

