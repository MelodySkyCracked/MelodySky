/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebNavigation;

public interface nsICharsetResolver
extends nsISupports {
    public static final String NS_ICHARSETRESOLVER_IID = "{d143a084-b626-4614-845f-41f3ca43a674}";

    public String requestCharset(nsIWebNavigation var1, nsIChannel var2, boolean[] var3, nsISupports[] var4);

    public void notifyResolvedCharset(String var1, nsISupports var2);
}

