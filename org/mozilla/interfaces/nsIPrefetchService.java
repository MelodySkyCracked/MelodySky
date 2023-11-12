/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIPrefetchService
extends nsISupports {
    public static final String NS_IPREFETCHSERVICE_IID = "{933cb52a-2864-4a40-8678-a2d0851b0ef4}";

    public void prefetchURI(nsIURI var1, nsIURI var2, boolean var3);
}

