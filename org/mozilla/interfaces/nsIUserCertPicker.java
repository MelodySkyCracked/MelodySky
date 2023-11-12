/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsIUserCertPicker
extends nsISupports {
    public static final String NS_IUSERCERTPICKER_IID = "{06d018e0-d41b-4629-a4fc-daaa6029888e}";

    public nsIX509Cert pickByUsage(nsIInterfaceRequestor var1, String var2, int var3, boolean var4, boolean var5, boolean[] var6);
}

