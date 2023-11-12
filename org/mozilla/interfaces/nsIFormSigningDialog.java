/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsIFormSigningDialog
extends nsISupports {
    public static final String NS_IFORMSIGNINGDIALOG_IID = "{4fe04d6d-4b66-4023-a0bc-b43ce68b3e15}";

    public boolean confirmSignText(nsIInterfaceRequestor var1, String var2, String var3, String[] var4, String[] var5, long var6, int[] var8, String[] var9);
}

