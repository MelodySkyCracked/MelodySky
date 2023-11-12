/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIHttpChannel;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsICookieConsent
extends nsISupports {
    public static final String NS_ICOOKIECONSENT_IID = "{f5a34f50-1f39-11d6-a627-0010a401eb10}";

    public int getConsent(nsIURI var1, nsIHttpChannel var2, boolean var3, int[] var4);
}

