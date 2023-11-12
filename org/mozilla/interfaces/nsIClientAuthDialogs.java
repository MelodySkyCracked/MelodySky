/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsIClientAuthDialogs
extends nsISupports {
    public static final String NS_ICLIENTAUTHDIALOGS_IID = "{fa4c7520-1433-11d5-ba24-00108303b117}";

    public void chooseCertificate(nsIInterfaceRequestor var1, String var2, String var3, String var4, String[] var5, String[] var6, long var7, int[] var9, boolean[] var10);
}

