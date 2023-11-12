/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsICertPickDialogs
extends nsISupports {
    public static final String NS_ICERTPICKDIALOGS_IID = "{51d59b08-1dd2-11b2-ad4a-a51b92f8a184}";

    public void pickCertificate(nsIInterfaceRequestor var1, String[] var2, String[] var3, long var4, int[] var6, boolean[] var7);
}

