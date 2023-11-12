/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIURIContentListener
extends nsISupports {
    public static final String NS_IURICONTENTLISTENER_IID = "{94928ab3-8b63-11d3-989d-001083010e9b}";

    public boolean onStartURIOpen(nsIURI var1);

    public boolean doContent(String var1, boolean var2, nsIRequest var3, nsIStreamListener[] var4);

    public boolean isPreferred(String var1, String[] var2);

    public boolean canHandleContent(String var1, boolean var2, String[] var3);

    public nsISupports getLoadCookie();

    public void setLoadCookie(nsISupports var1);

    public nsIURIContentListener getParentContentListener();

    public void setParentContentListener(nsIURIContentListener var1);
}

