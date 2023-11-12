/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsICookie2;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsICookiePermission
extends nsISupports {
    public static final String NS_ICOOKIEPERMISSION_IID = "{91f1c3ec-73a0-4bf0-bdc5-348a1f181b0e}";
    public static final int ACCESS_DEFAULT = 0;
    public static final int ACCESS_ALLOW = 1;
    public static final int ACCESS_DENY = 2;
    public static final int ACCESS_SESSION = 8;

    public void setAccess(nsIURI var1, int var2);

    public int canAccess(nsIURI var1, nsIURI var2, nsIChannel var3);

    public boolean canSetCookie(nsIURI var1, nsIChannel var2, nsICookie2 var3, boolean[] var4, long[] var5);
}

