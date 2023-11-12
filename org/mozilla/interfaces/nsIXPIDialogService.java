/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIXPIDialogService
extends nsISupports {
    public static final String NS_IXPIDIALOGSERVICE_IID = "{8cdd8baa-1dd2-11b2-909a-f0178da5c5ff}";

    public boolean confirmInstall(nsIDOMWindow var1, String[] var2, long var3);

    public void openProgressDialog(String[] var1, long var2, nsIObserver var4);
}

