/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIOutputStream;

public interface nsIFileOutputStream
extends nsIOutputStream {
    public static final String NS_IFILEOUTPUTSTREAM_IID = "{e6f68040-c7ec-11d3-8cda-0060b0fc14a3}";

    public void init(nsIFile var1, int var2, int var3, int var4);
}

