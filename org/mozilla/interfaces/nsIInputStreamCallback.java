/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAsyncInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIInputStreamCallback
extends nsISupports {
    public static final String NS_IINPUTSTREAMCALLBACK_IID = "{d1f28e94-3a6e-4050-a5f5-2e81b1fc2a43}";

    public void onInputStreamReady(nsIAsyncInputStream var1);
}

