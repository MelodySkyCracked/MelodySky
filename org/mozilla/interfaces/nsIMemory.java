/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIMemory
extends nsISupports {
    public static final String NS_IMEMORY_IID = "{59e7e77a-38e4-11d4-8cf5-0060b0fc14a3}";

    public void heapMinimize(boolean var1);

    public boolean isLowMemory();
}

