/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIProtocolHandler;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIIOService
extends nsISupports {
    public static final String NS_IIOSERVICE_IID = "{bddeda3f-9020-4d12-8c70-984ee9f7935e}";

    public nsIProtocolHandler getProtocolHandler(String var1);

    public long getProtocolFlags(String var1);

    public nsIURI newURI(String var1, String var2, nsIURI var3);

    public nsIURI newFileURI(nsIFile var1);

    public nsIChannel newChannelFromURI(nsIURI var1);

    public nsIChannel newChannel(String var1, String var2, nsIURI var3);

    public boolean getOffline();

    public void setOffline(boolean var1);

    public boolean allowPort(int var1, String var2);

    public String extractScheme(String var1);
}

