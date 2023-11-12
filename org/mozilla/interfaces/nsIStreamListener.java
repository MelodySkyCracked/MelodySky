/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIStreamListener
extends nsIRequestObserver {
    public static final String NS_ISTREAMLISTENER_IID = "{1a637020-1482-11d3-9333-00104ba0fd40}";

    public void onDataAvailable(nsIRequest var1, nsISupports var2, nsIInputStream var3, long var4, long var6);
}

