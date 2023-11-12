/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPCall;
import org.mozilla.interfaces.nsISOAPResponse;
import org.mozilla.interfaces.nsISOAPResponseListener;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPCallCompletion
extends nsISupports {
    public static final String NS_ISOAPCALLCOMPLETION_IID = "{86114dd8-1dd2-11b2-ab2b-91d0c995e03a}";

    public nsISOAPCall getCall();

    public nsISOAPResponse getResponse();

    public nsISOAPResponseListener getListener();

    public boolean getIsComplete();

    public boolean abort();
}

