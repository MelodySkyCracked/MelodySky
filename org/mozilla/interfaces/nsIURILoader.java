/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURIContentListener;

public interface nsIURILoader
extends nsISupports {
    public static final String NS_IURILOADER_IID = "{5cf6420c-74f3-4a7c-bc1d-f5756d79ea07}";

    public void registerContentListener(nsIURIContentListener var1);

    public void unRegisterContentListener(nsIURIContentListener var1);

    public void openURI(nsIChannel var1, boolean var2, nsIInterfaceRequestor var3);

    public void stop(nsISupports var1);
}

