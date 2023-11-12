/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventTarget;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIAsyncStreamCopier
extends nsIRequest {
    public static final String NS_IASYNCSTREAMCOPIER_IID = "{eaa49141-c21c-4fe8-a79b-77860a3910aa}";

    public void init(nsIInputStream var1, nsIOutputStream var2, nsIEventTarget var3, boolean var4, boolean var5, long var6);

    public void asyncCopy(nsIRequestObserver var1, nsISupports var2);
}

