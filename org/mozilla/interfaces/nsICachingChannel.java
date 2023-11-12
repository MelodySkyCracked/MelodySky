/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsICachingChannel
extends nsISupports {
    public static final String NS_ICACHINGCHANNEL_IID = "{b1f95f5e-ee05-4434-9d34-89a935d7feef}";
    public static final long LOAD_BYPASS_LOCAL_CACHE = 0x10000000L;
    public static final long LOAD_BYPASS_LOCAL_CACHE_IF_BUSY = 0x20000000L;
    public static final long LOAD_ONLY_FROM_CACHE = 0x40000000L;
    public static final long LOAD_ONLY_IF_MODIFIED = 0x80000000L;

    public nsISupports getCacheToken();

    public void setCacheToken(nsISupports var1);

    public nsISupports getCacheKey();

    public void setCacheKey(nsISupports var1);

    public boolean getCacheAsFile();

    public void setCacheAsFile(boolean var1);

    public nsIFile getCacheFile();

    public boolean isFromCache();
}

