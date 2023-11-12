/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIZipEntry
extends nsISupports {
    public static final String NS_IZIPENTRY_IID = "{6ca5e43e-9632-11d3-8cd9-0060b0fc14a3}";

    public String getName();

    public int getCompression();

    public long getSize();

    public long getRealSize();

    public long getCRC32();
}

