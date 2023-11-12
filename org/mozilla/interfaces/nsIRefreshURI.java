/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIRefreshURI
extends nsISupports {
    public static final String NS_IREFRESHURI_IID = "{69efc430-2efe-11d2-9e5d-006008bf092e}";

    public void refreshURI(nsIURI var1, int var2, boolean var3, boolean var4);

    public void setupRefreshURI(nsIChannel var1);

    public void setupRefreshURIFromHeader(nsIURI var1, String var2);

    public void cancelRefreshURITimers();
}

