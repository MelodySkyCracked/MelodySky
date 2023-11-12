/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIProgressEventSink
extends nsISupports {
    public static final String NS_IPROGRESSEVENTSINK_IID = "{d974c99e-4148-4df9-8d98-de834a2f6462}";

    public void onProgress(nsIRequest var1, nsISupports var2, double var3, double var5);

    public void onStatus(nsIRequest var1, nsISupports var2, long var3, String var5);
}

