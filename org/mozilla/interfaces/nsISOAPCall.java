/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPCallCompletion;
import org.mozilla.interfaces.nsISOAPMessage;
import org.mozilla.interfaces.nsISOAPResponse;
import org.mozilla.interfaces.nsISOAPResponseListener;

public interface nsISOAPCall
extends nsISOAPMessage {
    public static final String NS_ISOAPCALL_IID = "{a8fefe40-52bc-11d4-9a57-000064657374}";

    public String getTransportURI();

    public void setTransportURI(String var1);

    public boolean getVerifySourceHeader();

    public void setVerifySourceHeader(boolean var1);

    public nsISOAPResponse invoke();

    public nsISOAPCallCompletion asyncInvoke(nsISOAPResponseListener var1);
}

