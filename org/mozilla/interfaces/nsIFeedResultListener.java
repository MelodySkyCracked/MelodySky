/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFeedResult;
import org.mozilla.interfaces.nsISupports;

public interface nsIFeedResultListener
extends nsISupports {
    public static final String NS_IFEEDRESULTLISTENER_IID = "{4d2ebe88-36eb-4e20-bcd1-997b3c1f24ce}";

    public void handleResult(nsIFeedResult var1);
}

