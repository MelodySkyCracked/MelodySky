/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProtocolHandler;

public interface nsIExternalProtocolHandler
extends nsIProtocolHandler {
    public static final String NS_IEXTERNALPROTOCOLHANDLER_IID = "{0e61f3b2-34d7-4c79-bfdc-4860bc7341b7}";

    public boolean externalAppExistsForScheme(String var1);
}

