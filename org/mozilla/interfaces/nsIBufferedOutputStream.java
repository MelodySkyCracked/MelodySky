/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;

public interface nsIBufferedOutputStream
extends nsIOutputStream {
    public static final String NS_IBUFFEREDOUTPUTSTREAM_IID = "{6476378a-da09-11d3-8cda-0060b0fc14a3}";

    public void init(nsIOutputStream var1, long var2);
}

