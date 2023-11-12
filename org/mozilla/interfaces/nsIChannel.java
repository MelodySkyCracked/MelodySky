/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIChannel
extends nsIRequest {
    public static final String NS_ICHANNEL_IID = "{c63a055a-a676-4e71-bf3c-6cfa11082018}";
    public static final long LOAD_DOCUMENT_URI = 65536L;
    public static final long LOAD_RETARGETED_DOCUMENT_URI = 131072L;
    public static final long LOAD_REPLACE = 262144L;
    public static final long LOAD_INITIAL_DOCUMENT_URI = 524288L;
    public static final long LOAD_TARGETED = 0x100000L;
    public static final long LOAD_CALL_CONTENT_SNIFFERS = 0x200000L;

    public nsIURI getOriginalURI();

    public void setOriginalURI(nsIURI var1);

    public nsIURI getURI();

    public nsISupports getOwner();

    public void setOwner(nsISupports var1);

    public nsIInterfaceRequestor getNotificationCallbacks();

    public void setNotificationCallbacks(nsIInterfaceRequestor var1);

    public nsISupports getSecurityInfo();

    public String getContentType();

    public void setContentType(String var1);

    public String getContentCharset();

    public void setContentCharset(String var1);

    public int getContentLength();

    public void setContentLength(int var1);

    public nsIInputStream open();

    public void asyncOpen(nsIStreamListener var1, nsISupports var2);
}

