/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIInputStream;

public interface nsIFileInputStream
extends nsIInputStream {
    public static final String NS_IFILEINPUTSTREAM_IID = "{e3d56a20-c7ec-11d3-8cda-0060b0fc14a3}";
    public static final int DELETE_ON_CLOSE = 2;
    public static final int CLOSE_ON_EOF = 4;
    public static final int REOPEN_ON_REWIND = 8;

    public void init(nsIFile var1, int var2, int var3, int var4);
}

