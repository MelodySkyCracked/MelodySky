/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransport;

public interface nsITransportEventSink
extends nsISupports {
    public static final String NS_ITRANSPORTEVENTSINK_IID = "{eda4f520-67f7-484b-a691-8c3226a5b0a6}";

    public void onTransportStatus(nsITransport var1, long var2, double var4, double var6);
}

