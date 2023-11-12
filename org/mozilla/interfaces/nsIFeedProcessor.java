/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFeedResultListener;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsIURI;

public interface nsIFeedProcessor
extends nsIStreamListener {
    public static final String NS_IFEEDPROCESSOR_IID = "{8a0b2908-21b0-45d7-b14d-30df0f92afc7}";

    public nsIFeedResultListener getListener();

    public void setListener(nsIFeedResultListener var1);

    public void parseFromStream(nsIInputStream var1, nsIURI var2);

    public void parseFromString(String var1, nsIURI var2);

    public void parseAsync(nsIRequestObserver var1, nsIURI var2);
}

