/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPMessage;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPTransportListener
extends nsISupports {
    public static final String NS_ISOAPTRANSPORTLISTENER_IID = "{99ec6696-535f-11d4-9a58-000064657374}";

    public boolean handleMessage(nsISOAPMessage var1, boolean var2);
}

