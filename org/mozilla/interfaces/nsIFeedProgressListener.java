/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFeedEntry;
import org.mozilla.interfaces.nsIFeedResult;
import org.mozilla.interfaces.nsIFeedResultListener;

public interface nsIFeedProgressListener
extends nsIFeedResultListener {
    public static final String NS_IFEEDPROGRESSLISTENER_IID = "{ebfd5de5-713c-40c0-ad7c-f095117fa580}";

    public void reportError(String var1, int var2, boolean var3);

    public void handleStartFeed(nsIFeedResult var1);

    public void handleFeedAtFirstEntry(nsIFeedResult var1);

    public void handleEntry(nsIFeedEntry var1, nsIFeedResult var2);
}

