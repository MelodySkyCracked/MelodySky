/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPFault;
import org.mozilla.interfaces.nsISOAPMessage;

public interface nsISOAPResponse
extends nsISOAPMessage {
    public static final String NS_ISOAPRESPONSE_IID = "{99ec6691-535f-11d4-9a58-000064657374}";

    public nsISOAPFault getFault();
}

