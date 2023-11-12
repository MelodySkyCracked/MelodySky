/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPrompt;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIExternalProtocolService
extends nsISupports {
    public static final String NS_IEXTERNALPROTOCOLSERVICE_IID = "{a49813a4-98b7-4bdb-998c-8bd9704af0c0}";

    public boolean externalProtocolHandlerExists(String var1);

    public boolean isExposedProtocol(String var1);

    public void loadUrl(nsIURI var1);

    public void loadURI(nsIURI var1, nsIPrompt var2);

    public String getApplicationDescription(String var1);
}

