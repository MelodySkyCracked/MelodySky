/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICacheEntryDescriptor;
import org.mozilla.interfaces.nsISupports;

public interface nsICacheListener
extends nsISupports {
    public static final String NS_ICACHELISTENER_IID = "{638c3848-778b-4851-8ff3-9400f65b8773}";

    public void onCacheEntryAvailable(nsICacheEntryDescriptor var1, int var2, long var3);
}

