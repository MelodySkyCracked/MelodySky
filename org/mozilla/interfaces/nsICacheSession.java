/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICacheEntryDescriptor;
import org.mozilla.interfaces.nsICacheListener;
import org.mozilla.interfaces.nsISupports;

public interface nsICacheSession
extends nsISupports {
    public static final String NS_ICACHESESSION_IID = "{ae9e84b5-3e2d-457e-8fcd-5bbd2a8b832e}";

    public boolean getDoomEntriesIfExpired();

    public void setDoomEntriesIfExpired(boolean var1);

    public nsICacheEntryDescriptor openCacheEntry(String var1, int var2, boolean var3);

    public void asyncOpenCacheEntry(String var1, int var2, nsICacheListener var3);

    public void evictEntries();

    public boolean isStorageEnabled();
}

