/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIPrompt;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsICookieService
extends nsISupports {
    public static final String NS_ICOOKIESERVICE_IID = "{011c3190-1434-11d6-a618-0010a401eb10}";

    public String getCookieString(nsIURI var1, nsIChannel var2);

    public String getCookieStringFromHttp(nsIURI var1, nsIURI var2, nsIChannel var3);

    public void setCookieString(nsIURI var1, nsIPrompt var2, String var3, nsIChannel var4);

    public void setCookieStringFromHttp(nsIURI var1, nsIURI var2, nsIPrompt var3, String var4, String var5, nsIChannel var6);

    public boolean getCookieIconIsVisible();
}

