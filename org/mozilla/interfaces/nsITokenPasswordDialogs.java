/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsITokenPasswordDialogs
extends nsISupports {
    public static final String NS_ITOKENPASSWORDDIALOGS_IID = "{be26b580-1dd1-11b2-9946-c598d0d07727}";

    public void setPassword(nsIInterfaceRequestor var1, String var2, boolean[] var3);

    public void getPassword(nsIInterfaceRequestor var1, String var2, String[] var3, boolean[] var4);
}

