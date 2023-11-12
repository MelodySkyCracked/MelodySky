/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIUnicharStreamListener
extends nsIRequestObserver {
    public static final String NS_IUNICHARSTREAMLISTENER_IID = "{4a7e9b62-fef8-400d-9865-d6820f630b4c}";

    public void onUnicharDataAvailable(nsIRequest var1, nsISupports var2, String var3);
}

