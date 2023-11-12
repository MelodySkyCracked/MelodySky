/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPCall;
import org.mozilla.interfaces.nsISOAPResponse;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPResponseListener
extends nsISupports {
    public static final String NS_ISOAPRESPONSELISTENER_IID = "{99ec6692-535f-11d4-9a58-000064657374}";

    public boolean handleResponse(nsISOAPResponse var1, nsISOAPCall var2, long var3, boolean var5);
}

