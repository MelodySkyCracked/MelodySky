/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIWebScriptsAccessService
extends nsISupports {
    public static final String NS_IWEBSCRIPTSACCESSSERVICE_IID = "{57e2860b-4266-4a85-bfde-ae39d945b014}";

    public boolean canAccess(nsIURI var1, String var2);

    public void invalidateCache(String var1);
}

