/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProperties;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface imgICache
extends nsISupports {
    public static final String IMGICACHE_IID = "{f1b74aae-5661-4753-a21c-66dd644afebc}";

    public void clearCache(boolean var1);

    public void removeEntry(nsIURI var1);

    public nsIProperties findEntryProperties(nsIURI var1);
}

