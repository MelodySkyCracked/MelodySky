/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProtocolHandler;
import org.mozilla.interfaces.nsIZipReaderCache;

public interface nsIJARProtocolHandler
extends nsIProtocolHandler {
    public static final String NS_IJARPROTOCOLHANDLER_IID = "{92c3b42c-98c4-11d3-8cd9-0060b0fc14a3}";

    public nsIZipReaderCache getJARCache();
}

