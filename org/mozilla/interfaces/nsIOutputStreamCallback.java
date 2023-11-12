/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAsyncOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIOutputStreamCallback
extends nsISupports {
    public static final String NS_IOUTPUTSTREAMCALLBACK_IID = "{40dbcdff-9053-42c5-a57c-3ec910d0f148}";

    public void onOutputStreamReady(nsIAsyncOutputStream var1);
}

