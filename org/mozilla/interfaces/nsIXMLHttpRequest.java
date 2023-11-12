/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIXMLHttpRequest
extends nsISupports {
    public static final String NS_IXMLHTTPREQUEST_IID = "{7b3b3c62-9d53-4abc-bc89-c33ce78f439f}";

    public nsIChannel getChannel();

    public nsIDOMDocument getResponseXML();

    public String getResponseText();

    public long getStatus();

    public String getStatusText();

    public void abort();

    public String getAllResponseHeaders();

    public String getResponseHeader(String var1);

    public void open(String var1, String var2);

    public void send(nsIVariant var1);

    public void setRequestHeader(String var1, String var2);

    public int getReadyState();

    public void overrideMimeType(String var1);

    public boolean getMultipart();

    public void setMultipart(boolean var1);
}

