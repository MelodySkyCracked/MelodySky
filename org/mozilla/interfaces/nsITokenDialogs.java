/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsITokenDialogs
extends nsISupports {
    public static final String NS_ITOKENDIALOGS_IID = "{bb4bae9c-39c5-11d5-ba26-00108303b117}";

    public void chooseToken(nsIInterfaceRequestor var1, String[] var2, long var3, String[] var5, boolean[] var6);
}

