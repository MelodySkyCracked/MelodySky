/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;

public interface nsIBufferedInputStream
extends nsIInputStream {
    public static final String NS_IBUFFEREDINPUTSTREAM_IID = "{616f5b48-da09-11d3-8cda-0060b0fc14a3}";

    public void init(nsIInputStream var1, long var2);
}

